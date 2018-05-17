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
package org.eclipse.n4js.ui.wizard.dependencies;

import javax.inject.Provider;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.ui.utils.UIUtils;

import com.google.inject.Inject;

/**
 * Wizard for user requesting to fix all problems with projects / dependencies. In particular, we delete all external
 * libraries, install them again, clear build everything.
 */
public class ExternalLibrariesWizard extends Wizard {
	private static final Logger LOGGER = Logger.getLogger(ExternalLibrariesWizard.class);

	@Inject
	private Provider<RunnableInstallDependencies> installDependneciesRunnable;

	/** Public for SWTBot tests */
	public static final String WINDOW_TITLE = "Setup External Libraries";
	private StartPage start;
	private SetupOptionsPage options;

	/** Setups wizard with {@link #setNeedsProgressMonitor(boolean)} set to {@code true} */
	public ExternalLibrariesWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return WINDOW_TITLE;
	}

	@Override
	public void addPages() {
		start = new StartPage();
		options = new SetupOptionsPage();
		addPage(start);
		addPage(options);
	}

	@Override
	public boolean performFinish() {

		try {
			InstallOptions installOptions = new InstallOptions();
			options.saveOptions(installOptions);

			RunnableInstallDependencies installDependnecies = installDependneciesRunnable.get();
			installDependnecies.setInstallOptions(installOptions);
			getContainer().run(true, true, installDependnecies);
			IStatus resultStatus = installDependnecies.getResultStatus();
			if (!resultStatus.isOK())
				switch (resultStatus.getSeverity()) {
				case IStatus.ERROR:
					LOGGER.error(resultStatus.toString());
					showErrorMessage();
					break;
				case IStatus.CANCEL:
					LOGGER.info(resultStatus.toString());
					showWarnMessage();
					break;
				case IStatus.WARNING:
					LOGGER.warn(resultStatus.toString());
					break;
				case IStatus.INFO:
					LOGGER.info(resultStatus.toString());
					break;
				case IStatus.OK:
					break;
				default:
					LOGGER.debug("Unhandled status " + resultStatus.getSeverity());
					break;
				}
		} catch (Throwable throwable) {
			LOGGER.error("unhandled error while setting up dependencies", throwable);
			showErrorMessage();
		}

		return true;
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	private static void showErrorMessage() {
		UIUtils.getDisplay().asyncExec(() -> MessageDialog.openError(
				UIUtils.getShell(),
				"Setting up external libraries failed.",
				"Error while setting up external libraries.\n"
						+ "Please check your Error Log view for the detailed log about the failure.\n"
						+ " (note that autobuild is " + (AutobuildUtils.get() ? "on" : "off") + ")"));
	}

	private static void showWarnMessage() {
		UIUtils.getDisplay().asyncExec(() -> MessageDialog.openWarning(
				UIUtils.getShell(),
				"Setting up external was cancelled.",
				"Due to cancellation not all libraries were installed.\n"
						+ " (note that autobuild is " + (AutobuildUtils.get() ? "on" : "off") + ")"));
	}
}
