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

import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.Window;
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
public class InstallNpmDependencyButtonListener extends SelectionAdapter {

	final private Supplier<IInputValidator> packageNameValidator;
	final private Supplier<IInputValidator> packageVersionValidator;
	final private StatusHelper statusHelper;
	final private BiFunction<Map<String, String>, IProgressMonitor, IStatus> installAction;

	InstallNpmDependencyButtonListener(BiFunction<Map<String, String>, IProgressMonitor, IStatus> installAction,
			Supplier<IInputValidator> packageNameValidator, Supplier<IInputValidator> packageVersionValidator,
			StatusHelper statusHelper) {

		this.installAction = installAction;
		this.packageNameValidator = packageNameValidator;
		this.packageVersionValidator = packageVersionValidator;
		this.statusHelper = statusHelper;
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {

		InstallNpmDependencyDialog dialog = new InstallNpmDependencyDialog(getShell(), packageNameValidator.get(),
				packageVersionValidator.get());
		dialog.open();

		final String packageName = dialog.getPackageName();
		final MultiStatus multistatus = statusHelper.createMultiStatus("Installing npm '" + packageName + "'.");

		if (!StringExtensions.isNullOrEmpty(packageName) && dialog.getReturnCode() == Window.OK) {
			try {
				final String packageVersion = dialog.getVersionConstraint();
				new ProgressMonitorDialog(getShell()).run(true, true, monitor -> {
					Map<String, String> singletonMap = Collections.singletonMap(packageName, packageVersion);
					multistatus.merge(installAction.apply(singletonMap, monitor));
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
}
