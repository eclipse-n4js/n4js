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

import static org.eclipse.n4js.N4JSPluginId.N4JS_PLUGIN_ID;
import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.jface.dialogs.MessageDialog.openError;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;

/**
 * Button selection listener for opening up an {@link MessageDialog yes/no dialog}, where user can decide to delete type
 * definitions and clone them again.
 *
 * Note: this class is not static, so it will hold reference to all services. Make sure to dispose it.
 *
 */
public class MaintenanceActionsButtonListener extends SelectionAdapter {

	final private BiFunction<MaintenanceActionsChoice, IProgressMonitor, MultiStatus> runActions;

	private static final String ACTION_NPM_RELOAD = "Reload npm libraries from disk.";
	private static final String ACTION_NPM_REINSTALL = "Reinstall npm libraries.";
	private static final String ACTION_NPM_CACHE_CLEAN = "Clean npm cache.";
	private static final String ACTION_NPM_PACKAGES_DELETE = "Delete npm packages (deletes npm folder).";
	private static final String ACTION_TYPE_DEFINITIONS_RESET = "Reset type definitions (creates fresh clone).";

	MaintenanceActionsButtonListener(BiFunction<MaintenanceActionsChoice, IProgressMonitor, MultiStatus> runActions) {
		this.runActions = runActions;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {

		ListSelectionDialog dialog = new ListSelectionDialog(UIUtils.getShell(),
				new String[] {
						ACTION_NPM_CACHE_CLEAN,
						ACTION_NPM_RELOAD,
						ACTION_NPM_REINSTALL,
						ACTION_TYPE_DEFINITIONS_RESET,
						ACTION_NPM_PACKAGES_DELETE },
				ArrayContentProvider.getInstance(), new LabelProvider(),
				"Select maintenance actions to perform.");
		dialog.setTitle("External libraries maintenance actions.");

		if (dialog.open() == Window.OK) {
			boolean cleanCache = false;
			boolean deleteNPM = false;
			boolean reinstall = false;
			boolean reclone = false;
			boolean reload = false;
			Object[] result = dialog.getResult();
			for (int i = 0; i < result.length; i++) {
				String dialogItem = (String) result[i];

				switch (dialogItem) {
				case ACTION_NPM_RELOAD:
					reload = true;
					break;
				case ACTION_NPM_REINSTALL:
					reinstall = true;
					break;
				case ACTION_NPM_CACHE_CLEAN:
					cleanCache = true;
					break;
				case ACTION_NPM_PACKAGES_DELETE:
					deleteNPM = true;
					break;
				case ACTION_TYPE_DEFINITIONS_RESET:
					reclone = true;
					break;
				}

			}

			final MaintenanceActionsChoice userChoice = new MaintenanceActionsChoice(reclone, cleanCache, reinstall,
					deleteNPM, reload);

			final AtomicReference<MultiStatus> actionsStatus = new AtomicReference<>();
			try {
				new ProgressMonitorDialog(UIUtils.getShell()).run(true, false, monitor -> {
					actionsStatus.set(runActions.apply(userChoice, monitor));
				});
			} catch (final InvocationTargetException | InterruptedException exc) {
				MultiStatus status = actionsStatus.get();
				status.merge(
						new Status(ERROR, N4JS_PLUGIN_ID, ERROR, "Error while executing maintenance actions.", exc));
			} finally {
				MultiStatus status = actionsStatus.get();
				if (!status.isOK()) {
					N4JSActivator.getInstance().getLog().log(status);
					getDisplay().asyncExec(() -> openError(
							UIUtils.getShell(),
							"external libraries maintenance Failed",
							"Error while performing external libraries maintenance actions.\nPlease check your Error Log view for the detailed log about the failure."));
				}
			}

		}
	}
}
