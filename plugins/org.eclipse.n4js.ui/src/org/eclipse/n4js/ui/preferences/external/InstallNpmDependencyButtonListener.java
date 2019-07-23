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
import static org.eclipse.n4js.ui.utils.UIUtils.getShell;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.StatusUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * Button selection listener for opening up an {@link InputDialog input dialog}, where user can specify npm package name
 * that will be downloaded and installed to the external libraries.
 *
 * Note: this class is not static, so it will hold reference to all services. Make sure to dispose it.
 *
 */
class InstallNpmDependencyButtonListener extends SelectionAdapter {

	final private LibraryManager libManager;
	final private NpmNameAndVersionValidatorHelper validatorHelper;
	final private SemverHelper semverHelper;
	final private StatusHelper statusHelper;
	final private Runnable updateLocations;
	final private Supplier<SafeURI<?>> getSelectedNodeModulesURI;

	/**
	 * Constructor
	 *
	 * @param updateLocations
	 *            the runnable that provides location
	 * @param libManager
	 *            the library manager
	 * @param validatorHelper
	 *            the validator that validates npm package (e.g. name, version)
	 *
	 * @param semverHelper
	 *            the semver helper
	 *
	 * @param statusHelper
	 *            the helper for handling status
	 * @param getSelectedNodeModulesURI
	 *            the supplier that provides the selected node_modules URI. Important: its getter must be called on the
	 *            UI thread!
	 */
	public InstallNpmDependencyButtonListener(Runnable updateLocations, LibraryManager libManager,
			NpmNameAndVersionValidatorHelper validatorHelper, SemverHelper semverHelper, StatusHelper statusHelper,
			Supplier<SafeURI<?>> getSelectedNodeModulesURI) {

		this.updateLocations = updateLocations;
		this.libManager = libManager;
		this.validatorHelper = validatorHelper;
		this.semverHelper = semverHelper;
		this.statusHelper = statusHelper;
		this.getSelectedNodeModulesURI = getSelectedNodeModulesURI;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {

		InstallNpmDependencyDialog dialog = new InstallNpmDependencyDialog(getShell(),
				validatorHelper.getPackageNameToInstallValidator(),
				validatorHelper.getPackageVersionValidator());
		dialog.open();

		final String packageName = dialog.getPackageName();

		if (StringExtensions.isNullOrEmpty(packageName) || dialog.getReturnCode() != Window.OK) {
			return;
		}

		final MultiStatus multistatus = statusHelper.createMultiStatus("Installing npm '" + packageName + "'.");
		try {
			final String packageVersionStr = dialog.getVersionConstraint();
			final NPMVersionRequirement packageVersion = semverHelper.parseVersionRangeSet(packageVersionStr);
			if (packageVersion == null) { // null should never happen, since we have a validator in place
				return;
			}

			// Assume that node_modules in a direct directory of the project root folder
			// Call getSelectedNodeModulesURI.get() on the UI thread!
			File prjRootDir = getSelectedNodeModulesURI.get().getParent().toJavaIoFile();
			new ProgressMonitorDialog(getShell()).run(true, true, monitor -> {
				Map<N4JSProjectName, NPMVersionRequirement> singletonMap = Collections.singletonMap(
						new N4JSProjectName(packageName),
						packageVersion);
				try {
					FileURI projectRootURI = new FileURI(prjRootDir);

					IStatus status = libManager.installNPMs(singletonMap, false, projectRootURI, monitor);
					multistatus.merge(status);
				} finally {
					updateLocations.run();
				}
			});

		} catch (final InterruptedException | OperationCanceledException exc) {
			// canceled by user
		} catch (final Exception exc) {
			String msg = "Error while installing npm dependency: '" + packageName + "'.";
			Throwable causingExc = exc.getCause() == null ? exc : exc.getCause();
			multistatus.merge(statusHelper.createError(msg, causingExc));

		} finally {
			if (!multistatus.isOK()) {
				N4JSActivator.getInstance().getLog().log(multistatus);

				getDisplay().asyncExec(() -> {
					String descr = StatusUtils.getErrorMessage(multistatus, true);
					ErrorDialog.openError(UIUtils.getShell(), "NPM Install Failed", descr, multistatus);
				});
			}
		}
	}

}
