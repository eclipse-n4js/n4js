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

import org.apache.log4j.Logger;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;

/**
 *
 */
@SuppressWarnings("restriction")
public class EclipseUIUtils {
	private static Logger LOGGER = Logger.getLogger(EclipseUIUtils.class);

	/** Obtains active workbench window. Can be null. */
	public static IWorkbenchWindow getWorkbenchWindow() {
		if (!ProjectTestsUtils.runsInUIThread())
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
		try {
			IWorkbenchPage activePage = getActivePage();
			return activePage.showView(id);
		} catch (final PartInitException e) {
			final String message = "Error occurred while initializing view with ID: '" + id + "'.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

}
