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

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.external.libraries.TargetPlatformModel.TP_FILTER_EXTENSION;
import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;
import static org.eclipse.jface.dialogs.MessageDialog.openError;
import static org.eclipse.swt.SWT.OPEN;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Map;
import java.util.function.BiFunction;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

import org.eclipse.n4js.external.libraries.TargetPlatformModel;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.utils.StatusHelper;

/**
 * Selection listener for importing the target platform file from the UI.
 */
public class ImportButtonListener extends SelectionAdapter {

	final private StatusHelper statusHelper;
	final private BiFunction<Map<String, String>, IProgressMonitor, IStatus> installAction;

	ImportButtonListener(BiFunction<Map<String, String>, IProgressMonitor, IStatus> installAction,
			StatusHelper statusHelper) {
		this.statusHelper = statusHelper;
		this.installAction = installAction;
	}

	@Override
	public void widgetSelected(final SelectionEvent ignored) {
		final MultiStatus multistatus = statusHelper.createMultiStatus("Status of importing target platform.");
		final FileDialog dialog = new FileDialog(UIUtils.getShell(), OPEN);
		dialog.setFilterExtensions(new String[] { TP_FILTER_EXTENSION });
		dialog.setFileName(TargetPlatformModel.TP_FILE_NAME);
		dialog.setText("Import N4JS Target Platform");
		final String value = dialog.open();
		if (!isNullOrEmpty(value)) {

			final File file = new File(value);
			try {
				if (!file.exists()) {
					multistatus.merge(statusHelper.createError("Error while importing target platform file."));
					return;
				}
				final URI platformFileLocation = file.toURI();
				final Map<String, String> packages = TargetPlatformModel
						.npmVersionedPackageNamesFrom(platformFileLocation);

				if (!packages.isEmpty()) {
					try {
						new ProgressMonitorDialog(UIUtils.getShell()).run(true, false, monitor -> {
							multistatus.merge(installAction.apply(packages, monitor));
						});
					} catch (final InvocationTargetException | InterruptedException exc) {
						multistatus.merge(
								statusHelper.createError("Error while installing npm dependency: '" + packages + "'.",
										exc));
					}
				} else {
					MessageDialog.openInformation(UIUtils.getShell(), "Empty target platform file",
							"Specified target platform file contains no packaged to install.");
				}
			} catch (final IOException e) {
				multistatus.merge(statusHelper.createError("Error while importing target platform file.", e));
			} finally {
				if (!multistatus.isOK()) {
					N4JSActivator.getInstance().getLog().log(multistatus);
					getDisplay().asyncExec(() -> openError(
							UIUtils.getShell(),
							"n4tp Install Failed",
							"Error while installing from '" + value
									+ "' target platform file.\nPlease check your Error Log view for the detailed information about the failure."));
				}
			}
		}
	}
}
