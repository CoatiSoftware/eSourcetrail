package io.coati.eSourcetrail.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import io.coati.eSourcetrail.core.preferences.PreferenceConstants;

public class TCPServerWorker extends Thread {
	private Display display;

	private void logError(String msg, Exception e)
	{
		Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Status.OK, msg, e));
	}
	private void logWarning(String msg, Exception e )
	{
		Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, Status.OK, msg, e));
	}
	
	private void sendPing()
	{
		String ip = "localhost";
		Integer port = 0;
		try
		{
			IPreferenceStore store = io.coati.eSourcetrail.core.Activator.getDefault().getPreferenceStore();
			port = store.getInt(PreferenceConstants.P_ECLIPSE_TO_SOURCETRAIL_PORT);

			Socket socket = new Socket(ip, port);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));				       
	        writer.write("ping>>Eclipse<EOM>");
	        writer.flush();
	        socket.close();
		}
		catch(Exception e)
		{
			String errorMsg = 
					"No connection to a Sourcetrail instance\n\n Make sure Sourcetrail is running and the right address is used(" + ip + ":" + port + ")";
			display.asyncExec(new Runnable()
			{
				@Override
				public void run() {
					MessageDialog.openError(null, "SourcetrailPluginError", errorMsg);
				}
			});

			e.printStackTrace();
		}
	}

	public TCPServerWorker( Display display)
	{
		if (display == null)
		{
			System.out.println("display null");
		}
		this.display = display;
	}

	@Override
	public void run()
	{
		ServerSocket server = null;
		try
		{
			IPreferenceStore store = io.coati.eSourcetrail.core.Activator.getDefault().getPreferenceStore();
			String ip = "localhost";
			int port = store.getInt(PreferenceConstants.P_SOURCETRAIL_TO_ECLIPSE_PORT);

			server = new ServerSocket(port, 5, InetAddress.getByName(ip));
			sendPing();
			while(true)
			{
				final Socket client = server.accept();
				if(client == null)
				{
					logWarning("client is null", null);
					continue;
				}
				BufferedReader inFromClient =
			               new BufferedReader(new InputStreamReader(client.getInputStream()));
				String message = inFromClient.readLine();
				if(message == null)
				{
					logWarning("message is null", null);
					continue;
				}
				if (message.contains("<EOM>"))
				{
					message = message.replace("<EOM>", "");
					String [] split = message.split("\\>\\>");
					if(split[0].equals("moveCursor"))
					{
						display.asyncExec(new Runnable()
						{
							@Override
							public void run() {
								try
								{
									File fileToOpen = new File(split[1]);

									IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
									if(fileStore == null)
									{
										throw new PartInitException("filestorage is null");
									}

									IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
									if(page == null)
									{
										throw new PartInitException("page is null");
									}

									IEditorPart part = IDE.openEditorOnFileStore(page, fileStore);
									if(part == null)
									{
										throw new PartInitException("part is null");
									}
									ITextEditor editor = (ITextEditor) part;

									Object control = part.getAdapter(Control.class);
									if(control == null)
									{
										throw new PartInitException("control is null");
									}

									// For a text editor the control will be StyledText
									if (control instanceof StyledText) {
										StyledText text = (StyledText)control;
										int row = Integer.parseInt(split[2]) - 1;
										int col = Integer.parseInt(split[3]);
										int offset = text.getOffsetAtLine(row) + col;
										editor.selectAndReveal(offset, 1);
										text.setCaretOffset(offset);
									}

								}
								catch(PartInitException e)
								{
									logWarning("Failed to open file", e);
								}
							}
						});

					}
					if ( split[0].equals("ping") )
					{
						sendPing();
					}
				}
				else
				{
					logWarning("No EOM in the message", null);
				}

				client.close();
			}
		}
		catch(final Exception e)
		{
			logError("Tcp server crashed", e);
		}
		finally
		{
			if (server != null)
			{
				try
				{
					server.close();
				}
				catch(IOException e)
				{
					logError("Could not close server", e);
				}
			}

		}
	}


}
