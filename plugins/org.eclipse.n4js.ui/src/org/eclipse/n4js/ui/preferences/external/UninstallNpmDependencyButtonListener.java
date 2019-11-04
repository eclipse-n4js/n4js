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

import java.util.function.Supplier;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.StatusUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * Button selection listener for opening up an {@link InputDialog input dialog}, where user can specify npm package name
 * that will be uninstalled from the external libraries.
 *
 * Note: this class is not static, so it will hold reference to all services. Make sure to dispose it.
 *
 */
public class UninstallNpmDependencyButtonListener extends SelectionAdapter {

	final private LibraryManager libManager;
	final private NpmNameAndVersionValidatorHelper validatorHelper;
	final private StatusHelper statusHelper;
	final private Runnable updateLocations;
	final private Supplier<IN4JSProject> getSelectedNpm;

	UninstallNpmDependencyButtonListener(Runnable updateLocations, LibraryManager libManager,
			NpmNameAndVersionValidatorHelper validatorHelper, StatusHelper statusHelper,
			Supplier<IN4JSProject> getSelectedNpm) {
		this.statusHelper = statusHelper;
		this.validatorHelper = validatorHelper;
		this.libManager = libManager;
		this.updateLocations = updateLocations;
		this.getSelectedNpm = getSelectedNpm;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {

		final InputDialog dialog = new InputDialog(UIUtils.getShell(), "npm Uninstall",
				"Specify an npm package name to uninstall:", getSelectedNpm.get().getProjectName().getName(),
				validatorHelper.getPackageNameToUninstallValidator());

		dialog.open();
		final String packageName = dialog.getValue();
		final MultiStatus multistatus = statusHelper.createMultiStatus("Uninstalling npm '" + packageName + "'.");

		if (StringExtensions.isNullOrEmpty(packageName) || dialog.getReturnCode() != Window.OK) {
			return;
		}

		try {
			IN4JSProject npmProject = getSelectedNpm.get();
			new ProgressMonitorDialog(UIUtils.getShell()).run(true, true, monitor -> {
				try {
					FileURI location = (FileURI) npmProject.getLocation();
					IStatus status = libManager.uninstallNPM(location, monitor);
					multistatus.merge(status);
				} finally {
					updateLocations.run();
				}
			});

		} catch (final InterruptedException | OperationCanceledException exc) {
			// canceled by user
		} catch (final Exception exc) {
			String msg = "Error while uninstalling npm dependency: '" + packageName + "'.";
			Throwable causingExc = exc.getCause() == null ? exc : exc.getCause();
			multistatus.merge(statusHelper.createError(msg, causingExc));

		} finally {
			if (!multistatus.isOK()) {
				N4JSActivator.getInstance().getLog().log(multistatus);
				getDisplay().asyncExec(() -> {
					String descr = StatusUtils.getErrorMessage(multistatus, true);
					ErrorDialog.openError(UIUtils.getShell(), "NPM Uninstall Failed", descr, multistatus);
				});
			}
		}
	}

}
