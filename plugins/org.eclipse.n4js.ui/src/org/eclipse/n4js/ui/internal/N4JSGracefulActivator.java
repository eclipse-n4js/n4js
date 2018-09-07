/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.internal;

import java.util.concurrent.Semaphore;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.n4js.ui.N4JSUiMessages;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 *
 */
public class N4JSGracefulActivator extends N4JSActivator {
	private final Semaphore semaphore = new Semaphore(1);

	@Override
	public Injector getInjector(String language) {
		synchronized (semaphore) {
			if (semaphore.availablePermits() < 1) {
				throw new InjectorNotYetAvailableException();
			}
			try {
				semaphore.acquire();
				return super.getInjector(language);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				semaphore.release();
			}
		}
	}

	@Override
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
			return Guice.createInjector(mergedModule);
		} catch (Exception e) {
			// An exception occurring here might be related to Guice:
			// https://stackoverflow.com/questions/39918622/why-is-guice-throwing-computationexception-from-uncaughtexceptionhandler-in-mai.
			e.printStackTrace();
			UIUtils.showError(e);
			System.exit(-1);
			return null;
		}
	}

	/**
	 * Returns the active workbench shell or <code>null</code> if none
	 *
	 * @return the active workbench shell or <code>null</code> if none
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow window = getInstance().getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			return window.getShell();
		}
		return null;
	}

	/**
	 * Opens a message dialog showing the status.
	 */
	public static void statusDialog(IStatus status) {
		switch (status.getSeverity()) {
		case IStatus.ERROR:
			statusDialog(N4JSUiMessages.msgN4JSUI_ERROR_TITLE(), status);
			break;
		case IStatus.WARNING:
			statusDialog(N4JSUiMessages.msgN4JSUI_WARNING_TITLE(), status);
			break;
		case IStatus.INFO:
			statusDialog(N4JSUiMessages.msgN4JSUI_INFO_TITLE(), status);
			break;
		}
	}

	/**
	 * Opens a message dialog showing the status with given title. If you do not have a title, just call
	 * {@link #statusDialog(IStatus)}.
	 */
	public static void statusDialog(String title, IStatus status) {
		Shell shell = getActiveWorkbenchShell();
		if (shell != null) {
			switch (status.getSeverity()) {
			case IStatus.ERROR:
				ErrorDialog.openError(shell, title, null, status);
				break;
			case IStatus.WARNING:
				MessageDialog.openWarning(shell, title, status.getMessage());
				break;
			case IStatus.INFO:
				MessageDialog.openInformation(shell, title, status.getMessage());
				break;
			}
		}
	}

}
