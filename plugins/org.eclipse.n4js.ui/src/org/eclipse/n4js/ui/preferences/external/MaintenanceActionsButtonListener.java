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
package org.eclipse.n4js.ui.preferences.external;

import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;

import java.util.function.BiFunction;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.StatusUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

/**
 * Button selection listener for opening up an {@link MessageDialog yes/no dialog}, where user can decide to delete type
 * definitions and clone them again.
 *
 * Note: this class is not static, so it will hold reference to all services. Make sure to dispose it.
 *
 */
public class MaintenanceActionsButtonListener extends SelectionAdapter {

	final private BiFunction<MaintenanceActionsChoice, IProgressMonitor, MultiStatus> runActions;
	final private StatusHelper statusHelper;

	static final String ACTION_NPM_RELOAD = "Re-register all npms (cleans and builds npms from disk).";
	static final String ACTION_NPM_CACHE_CLEAN = "Clean npm cache. (npm cache clean --force)";
	static final String ACTION_NPM_PACKAGES_DELETE = "Delete all npms (deletes node_modules folder).";

	MaintenanceActionsButtonListener(BiFunction<MaintenanceActionsChoice, IProgressMonitor, MultiStatus> runActions,
			StatusHelper statusHelper) {
		this.runActions = runActions;
		this.statusHelper = statusHelper;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {

		String[] options = new String[] {
				ACTION_NPM_CACHE_CLEAN,
				ACTION_NPM_RELOAD,
				ACTION_NPM_PACKAGES_DELETE };
		String msg = "Select maintenance actions to perform.";
		Shell shell = UIUtils.getShell();
		ArrayContentProvider contentProvider = ArrayContentProvider.getInstance();
		ListSelectionDialog dialog = new ListSelectionDialog(shell, options, contentProvider, new LabelProvider(), msg);
		dialog.setTitle("External libraries maintenance actions.");

		if (dialog.open() == Window.OK) {
			boolean cleanCache = false;
			boolean deleteNPM = false;
			boolean reload = false;

			Object[] result = dialog.getResult();
			if (result == null) {
				return; // happens when quitting the IDE
			}
			for (int i = 0; i < result.length; i++) {
				String dialogItem = (String) result[i];

				switch (dialogItem) {
				case ACTION_NPM_RELOAD:
					reload = true;
					break;
				case ACTION_NPM_CACHE_CLEAN:
					cleanCache = true;
					break;
				case ACTION_NPM_PACKAGES_DELETE:
					deleteNPM = true;
					break;
				}
			}

			final MaintenanceActionsChoice userChoice = new MaintenanceActionsChoice(cleanCache, deleteNPM, reload);
			String statusText = "Perform Maintenance Actions: " + userChoice;
			final MultiStatus multistatus = statusHelper.createMultiStatus(statusText);

			try {
				new ProgressMonitorDialog(shell).run(true, true, monitor -> {
					multistatus.merge(runActions.apply(userChoice, monitor));
				});

			} catch (final InterruptedException | OperationCanceledException exc) {
				// canceled by user
			} catch (final Exception exc) {
				String statusMsg = "Error while performing maintenance actions: " + userChoice + ".";
				Throwable causingExc = exc.getCause() == null ? exc : exc.getCause();
				multistatus.merge(statusHelper.createError(statusMsg, causingExc));

			} finally {

				if (!multistatus.isOK()) {
					N4JSActivator.getInstance().getLog().log(multistatus);
					getDisplay().asyncExec(() -> {
						String title = "Maintenance Action Failed";
						String descr = StatusUtils.getErrorMessage(multistatus, true);
						ErrorDialog.openError(shell, title, descr, multistatus);
					});
				}
			}

		}
	}
}
