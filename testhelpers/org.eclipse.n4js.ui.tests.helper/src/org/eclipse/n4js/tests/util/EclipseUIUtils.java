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
package org.eclipse.n4js.tests.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.ui.utils.TimeoutRuntimeException;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;

import com.google.common.base.Stopwatch;

/**
 *
 */
@SuppressWarnings("restriction")
public class EclipseUIUtils {
	private static Logger LOGGER = Logger.getLogger(EclipseUIUtils.class);

	/** Obtains active workbench window. Can be null. */
	public static IWorkbenchWindow getWorkbenchWindow() {
		if (!UIUtils.runsInUIThread())
			LOGGER.warn("Eclipse UI utilities work correctly only when called from the UI thread.");

		IWorkbenchWindow window = null;
		if (Workbench.getInstance() != null) {
			IWorkbench wb = PlatformUI.getWorkbench();
			window = wb.getActiveWorkbenchWindow();
		}
		return window;
	}

	/** Obtains active page from workbench. Can be null. */
	public static IWorkbenchPage getActivePage() {
		IWorkbenchPage page = null;

		IWorkbenchWindow workbenchWindow = getWorkbenchWindow();
		if (workbenchWindow != null)
			page = workbenchWindow.getActivePage();

		return page;
	}

	/**
	 * Shows then returns with the view with the given unique view part identifier. May return with {@code null} if the
	 * view cannot be found.
	 *
	 * @param id
	 *            the unique ID of the view part to show.
	 * @return the view part or {@code null} if the view part cannot be shown.
	 */
	public static IViewPart showView(final String id) {
		checkNotNull(id, "Provided view ID was null.");

		final AtomicReference<IViewPart> result = new AtomicReference<>();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(() -> {
			try {
				result.set(getActivePage().showView(id));
			} catch (PartInitException e) {
				final String message = "Error occurred while initializing view with ID: '" + id + "'.";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			}
		});

		return result.get();
	}

	/** Waits for a given editor to be active in a given workbench page. */
	public static void waitForEditorToBeActive(final IWorkbenchPage page, IEditorPart editor) {
		if (editor == null)
			throw new IllegalArgumentException("Provided editor was null.");

		final boolean runsInUI = UIUtils.runsInUIThread();
		if (runsInUI)
			LOGGER.warn("Waiting for editor runs in the UI thread which can lead to UI thread starvation.");

		final long maxWait = 5000;
		boolean editorIsActive = false;
		boolean wasInterrupted = false;

		Stopwatch sw = Stopwatch.createStarted();
		do {

			editorIsActive = page.getActiveEditor() == editor;
			if (!editorIsActive) {
				try {
					if (runsInUI)
						UIUtils.waitForUiThread();
					else
						Thread.sleep(100);
				} catch (InterruptedException e) {
					wasInterrupted = true;
					LOGGER.error("Waiting for editor was interrupted after " + sw + ".", e);
				}
			}
		} while (sw.elapsed(TimeUnit.MILLISECONDS) < maxWait && editorIsActive == false && wasInterrupted == false);
		sw.stop();
		if (editorIsActive == false && wasInterrupted == false) {
			throw new TimeoutRuntimeException("Provided editor " + editor + " was not active after " + sw + ".");
		}
	}

	/** Opens given file in a editor with given ID within given workbench page. Returns opened editor on null. */
	public static IEditorPart openFileEditor(final IFile file, final IWorkbenchPage page, String editorId) {
		checkNotNull(file, "Provided file was null.");
		checkNotNull(page, "Provided page was null.");
		checkNotNull(editorId, "Provided editor ID was null.");

		AtomicReference<IEditorPart> refFileEditor = new AtomicReference<>();

		UIUtils.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					refFileEditor.set(IDE.openEditor(page, file, editorId, true));
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
		return refFileEditor.get();
	}

	/**
	 * Turns auto build on/off. Delegating to {@link AutobuildUtils#set(boolean)}.
	 *
	 * Consider using {@link AutobuildUtils#suppressAutobuild()} if you want to disable auto build only temporarily.
	 */
	public static void toggleAutobuild(final boolean enable) {
		AutobuildUtils.set(enable);
	}
}
