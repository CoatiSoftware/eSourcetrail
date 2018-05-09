package io.coati.eSourcetrail.core;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final TCPServerWorker w = new TCPServerWorker(workbench.getDisplay());
		w.start();
	}
}