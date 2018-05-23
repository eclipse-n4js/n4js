/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.xtext.service.OperationCanceledManager;

/**
 * Utility methods for dealing with Eclipse API. Plain SWT and JFace utilities are in {@link UIUtils}.
 */
public class EclipseUtils {

	/**
	 * Runs the given runnable in a {@link ProgressMonitorDialog}. Must be called from the UI thread.
	 * <p>
	 * <b>Before using this method</b>, ensure that in your use case it makes sense to have a modal dialog instead of
	 * using an Eclipse {@link Job} (by default, always use the job framework!) and that this method's exact behavior is
	 * appropriate for your use case (e.g. calling the <code>run()</code> method with 'fork' set to <code>true</code>,
	 * ignoring invocation target exceptions).
	 *
	 * @param ocm
	 *            an operation canceled manager as obtained via injection.
	 * @param runnable
	 *            the runnable to execute.
	 */
	public static void runInModalDialog(OperationCanceledManager ocm, IRunnableWithProgress runnable) {
		runInModalDialog(ocm, runnable, null);
	}

	/**
	 * Same as {@link #runInModalDialog(OperationCanceledManager, IRunnableWithProgress)}, but allows reacting to
	 * exceptions.
	 *
	 * @param throwableHandler
	 *            will be invoked on the runnable's thread in case the runnable throws an exception other than a
	 *            {@link OperationCanceledManager#isOperationCanceledException(Throwable) cancellation exception}. May
	 *            be <code>null</code> if no handling of exceptions is required.
	 */
	public static void runInModalDialog(OperationCanceledManager ocm, IRunnableWithProgress runnable,
			Consumer<Throwable> throwableHandler) {
		try {
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			final ProgressMonitorDialog dlg = new ProgressMonitorDialog(shell);
			dlg.run(true, true, monitor -> {
				try {
					runnable.run(monitor);
				} catch (Throwable th) {
					// translate cancellation exceptions from Eclipse/Xtext to SWT/JFace world
					if (ocm.isOperationCanceledException(th)) {
						throw new InterruptedException();
					}
					if (throwableHandler != null) {
						throwableHandler.accept(th);
					}
					throw th;
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// ignore
		}
	}

	/**
	 * Get or create the console with the given name in the console view.
	 */
	public static MessageConsole getOrCreateConsole(String name, boolean create, boolean reveal) {
		MessageConsole result = null;
		final ConsolePlugin plugin = ConsolePlugin.getDefault();
		final IConsoleManager mgr = plugin.getConsoleManager();
		final IConsole[] consoles = mgr.getConsoles();
		for (int i = 0; i < consoles.length; i++) {
			final IConsole currConsole = consoles[i];
			if (currConsole instanceof MessageConsole && name.equals(currConsole.getName())) {
				result = (MessageConsole) currConsole;
				break;
			}
		}
		if (result == null && create) {
			final MessageConsole newConsole = new MessageConsole(name, null);
			mgr.addConsoles(new IConsole[] { newConsole });
			result = newConsole;
		}
		if (result != null && reveal) {
			revealConsole(result);
		}
		return result;
	}

	/**
	 * Reveal the given console. If necessary, this will open the console view, bring it to the front, and reveal the
	 * given console inside the console view.
	 */
	public static void revealConsole(IConsole console) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					final IConsoleView view = (IConsoleView) page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
					view.display(console);
				} catch (PartInitException e) {
					// ignore
				}
			}
		});
	}
}
