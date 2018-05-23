/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.binaries;

import org.eclipse.ui.internal.ide.IDEWorkbenchErrorHandler;
import org.eclipse.ui.internal.statushandlers.IStatusDialogConstants;
import org.eclipse.ui.internal.statushandlers.WorkbenchStatusDialogManagerImpl;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.WorkbenchErrorHandler;
import org.eclipse.ui.statushandlers.WorkbenchStatusDialogManager;

/**
 * Customized status handler for N4JS IDE.
 */
@SuppressWarnings("restriction")
public class N4StatusHandler extends WorkbenchErrorHandler {

	private final WorkbenchErrorHandler delegate;

	/**
	 * Creates a new status handler for the N4JS IDE application.
	 */
	public N4StatusHandler() {
		delegate = new IDEWorkbenchErrorHandler(null) {

			@Override
			protected void configureStatusDialog(final WorkbenchStatusDialogManager statusDialog) {
				N4StatusHandler.this.configureStatusDialog(statusDialog);
			}

		};
	}

	@Override
	public void handle(final StatusAdapter statusAdapter, final int style) {
		delegate.handle(statusAdapter, style);
	}

	@Override
	protected void configureStatusDialog(final WorkbenchStatusDialogManager statusDialog) {
		final Object property = statusDialog.getProperty(IStatusDialogConstants.MANAGER_IMPL);
		if (property instanceof WorkbenchStatusDialogManagerImpl) {
			final WorkbenchStatusDialogManagerImpl manager = (WorkbenchStatusDialogManagerImpl) property;
			statusDialog.setDetailsAreaProvider(new N4StatusAreaProvider(manager));
		}
		super.configureStatusDialog(statusDialog);
	}

}
