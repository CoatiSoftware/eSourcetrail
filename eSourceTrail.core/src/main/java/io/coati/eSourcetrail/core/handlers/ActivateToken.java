package io.coati.eSourcetrail.core.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import io.coati.eSourcetrail.core.preferences.PreferenceConstants;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ActivateToken extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ActivateToken() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		IEditorPart editorPart = io.coati.eSourcetrail.core.Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		if (editorPart instanceof AbstractTextEditor)
		{
			int row = 0;
			int col = 0;

			IEditorInput input = ((AbstractTextEditor) editorPart).getEditorInput();
			IResource resource = ResourceUtil.getResource(input);
			IPath path = resource.getRawLocation();
			
			if (path  == null)
			{
				MessageDialog.openError(window.getShell(), "CoatiPluginError", "File is not in your Project");
				return null;
			}

			Object control = editorPart.getAdapter(Control.class);

			// For a text editor the control will be StyledText
			if (control instanceof StyledText) {
				StyledText text = (StyledText)control;

				int offset = text.getCaretOffset();
				row = text.getLineAtOffset(offset);
				col = offset - text.getOffsetAtLine(row);
				row += 1;
			}

			String ip = "";
			Integer port = 0;
			try
			{
				IPreferenceStore store = io.coati.eSourcetrail.core.Activator.getDefault().getPreferenceStore();
				ip = store.getString(PreferenceConstants.P_IP);
				port = store.getInt(PreferenceConstants.P_ECLIPSE_TO_COATI_PORT);

				Socket socket = new Socket(ip, port);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				String MESSAGE_SPLIT_STRING = ">>";
		        String text = "setActiveToken" + MESSAGE_SPLIT_STRING + path.toString() + MESSAGE_SPLIT_STRING + row + MESSAGE_SPLIT_STRING + col + "<EOM>";
		        writer.write(text);
		        writer.flush();
		        socket.close();
			}
			catch(Exception e)
			{
				String errorMsg = 
						"No connection to a Coati instance\n\n Make sure Coati is running and the right address is used(" + ip + ":" + port + ")";
				MessageDialog.openError(window.getShell(), "CoatiPluginError", errorMsg);
				e.printStackTrace();
			}
			
		}
		return null;
	}
}
