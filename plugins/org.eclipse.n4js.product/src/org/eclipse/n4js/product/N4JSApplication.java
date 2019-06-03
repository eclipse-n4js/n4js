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
package org.eclipse.n4js.product;

import static org.eclipse.jface.dialogs.MessageDialog.INFORMATION;
import static org.eclipse.jface.dialogs.MessageDialog.WARNING;
import static org.eclipse.jface.dialogs.MessageDialog.openError;
import static org.eclipse.ui.PlatformUI.createAndRunWorkbench;
import static org.eclipse.ui.PlatformUI.createDisplay;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_versionMessage_newerWorkspace;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_versionMessage_olderWorkspace;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_versionTitle_newerWorkspace;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_versionTitle_olderWorkspace;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceCannotBeSetTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceCannotLockMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceCannotLockTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceEmptyMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceEmptyTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceInUseMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceInUseTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceInvalidMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceInvalidTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceMandatoryMessage;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.IDEApplication_workspaceMandatoryTitle;
import static org.eclipse.ui.internal.ide.IDEWorkbenchMessages.InternalError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.ChooseWorkspaceData;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * This class controls all aspects of the application's execution.
 * <p>
 * Based on IDEApplication.
 */
@SuppressWarnings("restriction")
public class N4JSApplication implements IApplication {

	/**
	 * The name of the folder containing metadata information for the workspace.
	 */
	public static final String METADATA_FOLDER = ".metadata"; //$NON-NLS-1$

	private static final String VERSION_FILENAME = "version.ini"; //$NON-NLS-1$

	// Use the branding plug-in of the platform feature since this is most likely
	// to change on an update of the IDE.
	private static final String WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME = "org.eclipse.platform"; //$NON-NLS-1$
	private static final Version WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION;
	static {
		final Bundle bundle = Platform.getBundle(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
		WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION = bundle != null ? bundle.getVersion() : null/* not installed */;
	}

	private static final String WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY = "org.eclipse.core.runtime"; //$NON-NLS-1$
	private static final String WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED = "2"; //$NON-NLS-1$ legacy version=1

	private static final String PROP_EXIT_CODE = "eclipse.exitcode"; //$NON-NLS-1$

	/**
	 * A special return code that will be recognized by the launcher and used to restart the workbench.
	 */
	private static final Integer EXIT_RELAUNCH = Integer.valueOf(24);

	/**
	 * A special return code that will be recognized by the PDE launcher and used to show an error dialog if the
	 * workspace is locked.
	 */
	private static final Integer EXIT_WORKSPACE_LOCKED = Integer.valueOf(15);

	/**
	 * The ID of the application plug-in
	 */
	public static final String PLUGIN_ID = "org.eclipse.ui.ide.application"; //$NON-NLS-1$

	@Override
	public Object start(final IApplicationContext appContext) throws Exception {
		final Display display = createDisplay();

		try {

			// look and see if there's a splash shell we can parent off of
			final Shell shell = WorkbenchPlugin.getSplashShell(display);
			if (shell != null) {
				// should should set the icon and message for this shell to be the
				// same as the chooser dialog - this will be the guy that lives in
				// the task bar and without these calls you'd have the default icon
				// with no message.
				shell.setText(ChooseWorkspaceDialog.getWindowTitle());
				shell.setImages(Window.getDefaultImages());
			}

			final Object instanceLocationCheck = checkInstanceLocation(shell, appContext.getArguments());
			if (instanceLocationCheck != null) {
				WorkbenchPlugin.unsetSplashShell(display);
				appContext.applicationRunning();
				return instanceLocationCheck;
			}

			// create the workbench with this advisor and run it until it exits
			// N.B. createWorkbench remembers the advisor, and also registers
			// the workbench globally so that all UI plug-ins can find it using
			// PlatformUI.getWorkbench() or AbstractUIPlugin.getWorkbench()
			final int returnCode = createAndRunWorkbench(display, new N4JSApplicationWorkbenchAdvisor());

			// the workbench doesn't support relaunch yet (bug 61809) so
			// for now restart is used, and exit data properties are checked
			// here to substitute in the relaunch return code if needed
			if (returnCode != PlatformUI.RETURN_RESTART) {
				return EXIT_OK;
			}

			// if the exit code property has been set to the relaunch code, then
			// return that code now, otherwise this is a normal restart
			return EXIT_RELAUNCH.equals(Integer.getInteger(PROP_EXIT_CODE)) ? EXIT_RELAUNCH
					: EXIT_RESTART;
		} finally {
			if (display != null) {
				display.dispose();
			}
			final Location instanceLoc = Platform.getInstanceLocation();
			if (instanceLoc != null)
				instanceLoc.release();
		}
	}

	/**
	 * Return <code>null</code> if a valid workspace path has been set and an exit code otherwise. Prompt for and set
	 * the path if possible and required.
	 *
	 * @param applicationArguments
	 *            the command line arguments
	 * @return <code>null</code> if a valid instance location has been set and an exit code otherwise
	 */
	private Object checkInstanceLocation(final Shell shell, final Map<?, ?> applicationArguments) {
		// -data @none was specified but an ide requires workspace
		final Location instanceLoc = Platform.getInstanceLocation();
		if (instanceLoc == null) {
			openError(
					shell,
					IDEApplication_workspaceMandatoryTitle,
					IDEApplication_workspaceMandatoryMessage);
			return EXIT_OK;
		}

		// -data "/valid/path", workspace already set
		if (instanceLoc.isSet()) {
			// make sure the meta data version is compatible (or the user has
			// chosen to overwrite it).
			if (!checkValidWorkspace(shell, instanceLoc.getURL())) {
				return EXIT_OK;
			}

			// at this point its valid, so try to lock it and update the
			// metadata version information if successful
			try {
				if (instanceLoc.lock()) {
					writeWorkspaceVersion();
					return null;
				}

				// we failed to create the directory.
				// Two possibilities:
				// 1. directory is already in use
				// 2. directory could not be created
				final File workspaceDirectory = new File(instanceLoc.getURL().getFile());
				if (workspaceDirectory.exists()) {
					if (isDevLaunchMode(applicationArguments)) {
						return EXIT_WORKSPACE_LOCKED;
					}
					openError(
							shell,
							IDEApplication_workspaceCannotLockTitle,
							NLS.bind(IDEApplication_workspaceCannotLockMessage, workspaceDirectory.getAbsolutePath()));
				} else {
					openError(
							shell,
							IDEApplication_workspaceCannotBeSetTitle,
							IDEApplication_workspaceCannotBeSetMessage);
				}
			} catch (final IOException e) {
				N4ProductActivator.log("Could not obtain lock for workspace location", //$NON-NLS-1$
						e);
				openError(
						shell,
						InternalError,
						e.getMessage());
			}
			return EXIT_OK;
		}

		// -data @noDefault or -data not specified, prompt and set
		final ChooseWorkspaceData launchData = new ChooseWorkspaceData(instanceLoc
				.getDefault());

		boolean force = false;
		while (true) {
			final URL workspaceUrl = promptForWorkspace(shell, launchData, force);
			if (workspaceUrl == null) {
				return EXIT_OK;
			}

			// if there is an error with the first selection, then force the
			// dialog to open to give the user a chance to correct
			force = true;

			try {
				// the operation will fail if the url is not a valid
				// instance data area, so other checking is unneeded
				if (instanceLoc.set(workspaceUrl, true)) {
					launchData.writePersistedData();
					writeWorkspaceVersion();
					return null;
				}
			} catch (final IllegalStateException e) {
				MessageDialog
						.openError(
								shell,
								IDEApplication_workspaceCannotBeSetTitle,
								IDEApplication_workspaceCannotBeSetMessage);
				return EXIT_OK;
			} catch (final IOException e) {
				MessageDialog
						.openError(
								shell,
								IDEApplication_workspaceCannotBeSetTitle,
								IDEApplication_workspaceCannotBeSetMessage);
			}

			// by this point it has been determined that the workspace is
			// already in use -- force the user to choose again
			openError(shell, IDEApplication_workspaceInUseTitle,
					NLS.bind(IDEApplication_workspaceInUseMessage, workspaceUrl.getFile()));
		}
	}

	private static boolean isDevLaunchMode(final Map<?, ?> args) {
		// see org.eclipse.pde.internal.core.PluginPathFinder.isDevLaunchMode()
		if (Boolean.getBoolean("eclipse.pde.launch")) //$NON-NLS-1$
			return true;
		return args.containsKey("-pdelaunch"); //$NON-NLS-1$
	}

	/**
	 * Open a workspace selection dialog on the argument shell, populating the argument data with the user's selection.
	 * Perform first level validation on the selection by comparing the version information. This method does not
	 * examine the runtime state (e.g., is the workspace already locked?).
	 *
	 * @param shell
	 *            the shell for the dialog.
	 * @param launchData
	 *            launch data for choosing the initial workspace.
	 * @param force
	 *            setting to true makes the dialog open regardless of the showDialog value
	 * @return An URL storing the selected workspace or null if the user has canceled the launch operation.
	 */
	private URL promptForWorkspace(final Shell shell, final ChooseWorkspaceData launchData,
			boolean force) {
		URL url = null;
		do {
			// okay to use the shell now - this is the splash shell
			new ChooseWorkspaceDialog(shell, launchData, false, true).prompt(force);
			final String instancePath = launchData.getSelection();
			if (instancePath == null) {
				return null;
			}

			// the dialog is not forced on the first iteration, but is on every
			// subsequent one -- if there was an error then the user needs to be
			// allowed to fix it
			force = true;

			// 70576: don't accept empty input
			if (instancePath.length() <= 0) {
				MessageDialog
						.openError(
								shell,
								IDEApplication_workspaceEmptyTitle,
								IDEApplication_workspaceEmptyMessage);
				continue;
			}

			// create the workspace if it does not already exist
			final File workspace = new File(instancePath);
			if (!workspace.exists()) {
				workspace.mkdir();
			}

			try {
				// Don't use File.toURL() since it adds a leading slash that Platform does not
				// handle properly. See bug 54081 for more details.
				final String path = workspace.getAbsolutePath().replace(
						File.separatorChar, '/');
				url = new URL("file", null, path); //$NON-NLS-1$
			} catch (final MalformedURLException e) {
				MessageDialog
						.openError(
								shell,
								IDEApplication_workspaceInvalidTitle,
								IDEApplication_workspaceInvalidMessage);
				continue;
			}
		} while (!checkValidWorkspace(shell, url));

		return url;
	}

	/**
	 * Return true if the argument directory is ok to use as a workspace and false otherwise. A version check will be
	 * performed, and a confirmation box may be displayed on the argument shell if an older version is detected.
	 *
	 * @return true if the argument URL is ok to use as a workspace and false otherwise.
	 */
	private boolean checkValidWorkspace(final Shell shell, final URL url) {
		// a null url is not a valid workspace
		if (url == null) {
			return false;
		}

		if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
			// no reference bundle installed, no check possible
			return true;
		}

		final Version version = readWorkspaceVersion(url);
		// if the version could not be read, then there is not any existing
		// workspace data to trample, e.g., perhaps its a new directory that
		// is just starting to be used as a workspace
		if (version == null) {
			return true;
		}

		final Version ide_version = toMajorMinorVersion(WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION);
		final Version workspace_version = toMajorMinorVersion(version);
		final int versionCompareResult = workspace_version.compareTo(ide_version);

		// equality test is required since any version difference (newer
		// or older) may result in data being trampled
		if (versionCompareResult == 0) {
			return true;
		}

		// At this point workspace has been detected to be from a version
		// other than the current ide version -- find out if the user wants
		// to use it anyhow.
		int severity;
		String title;
		String message;
		if (versionCompareResult < 0) {
			// Workspace < IDE. Update must be possible without issues,
			// so only inform user about it.
			severity = INFORMATION;
			title = IDEApplication_versionTitle_olderWorkspace;
			message = NLS.bind(IDEApplication_versionMessage_olderWorkspace, url.getFile());
		} else {
			// Workspace > IDE. It must have been opened with a newer IDE version.
			// Downgrade might be problematic, so warn user about it.
			severity = WARNING;
			title = IDEApplication_versionTitle_newerWorkspace;
			message = NLS.bind(IDEApplication_versionMessage_newerWorkspace, url.getFile());
		}

		final MessageDialog dialog = new MessageDialog(shell, title, null, message, severity,
				new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
		return dialog.open() == Window.OK;
	}

	/**
	 * Look at the argument URL for the workspace's version information. Return that version if found and null
	 * otherwise.
	 */
	private static Version readWorkspaceVersion(final URL workspace) {
		final File versionFile = getVersionFile(workspace, false);
		if (versionFile == null || !versionFile.exists()) {
			return null;
		}

		try {
			// Although the version file is not spec'ed to be a Java properties
			// file, it happens to follow the same format currently, so using
			// Properties to read it is convenient.
			final Properties props = new Properties();
			final FileInputStream is = new FileInputStream(versionFile);
			try {
				props.load(is);
			} finally {
				is.close();
			}

			String versionString = props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
			if (versionString != null) {
				return Version.parseVersion(versionString);
			}
			versionString = props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY);
			if (versionString != null) {
				return Version.parseVersion(versionString);
			}
			return null;
		} catch (final IOException e) {
			IDEWorkbenchPlugin.log("Could not read version file " + versionFile, new Status( //$NON-NLS-1$
					IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
					IStatus.ERROR,
					e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
					e));
			return null;
		} catch (final IllegalArgumentException e) {
			IDEWorkbenchPlugin.log("Could not parse version in " + versionFile, new Status( //$NON-NLS-1$
					IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
					IStatus.ERROR,
					e.getMessage() == null ? "" : e.getMessage(), //$NON-NLS-1$
					e));
			return null;
		}
	}

	/**
	 * Write the version of the metadata into a known file overwriting any existing file contents. Writing the version
	 * file isn't really crucial, so the function is silent about failure
	 */
	private static void writeWorkspaceVersion() {
		if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION == null) {
			// no reference bundle installed, no check possible
			return;
		}

		final Location instanceLoc = Platform.getInstanceLocation();
		if (instanceLoc == null || instanceLoc.isReadOnly()) {
			return;
		}

		final File versionFile = getVersionFile(instanceLoc.getURL(), true);
		if (versionFile == null) {
			return;
		}

		OutputStream output = null;
		try {
			output = new FileOutputStream(versionFile);
			final Properties props = new Properties();

			// write new property
			props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME,
					WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());

			// write legacy property with an incremented version,
			// so that pre-4.4 IDEs will also warn about the workspace
			props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED);

			props.store(output, null);
		} catch (final IOException e) {
			IDEWorkbenchPlugin.log("Could not write version file", //$NON-NLS-1$
					StatusUtil.newStatus(IStatus.ERROR, e.getMessage(), e));
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (final IOException e) {
				// do nothing
			}
		}
	}

	/**
	 * The version file is stored in the metadata area of the workspace. This method returns an URL to the file or null
	 * if the directory or file does not exist (and the create parameter is false).
	 *
	 * @param create
	 *            If the directory and file does not exist this parameter controls whether it will be created.
	 * @return An url to the file or null if the version file does not exist or could not be created.
	 */
	private static File getVersionFile(final URL workspaceUrl, final boolean create) {
		if (workspaceUrl == null) {
			return null;
		}

		try {
			// make sure the directory exists
			final File metaDir = new File(workspaceUrl.getPath(), METADATA_FOLDER);
			if (!metaDir.exists() && (!create || !metaDir.mkdir())) {
				return null;
			}

			// make sure the file exists
			final File versionFile = new File(metaDir, VERSION_FILENAME);
			if (!versionFile.exists()
					&& (!create || !versionFile.createNewFile())) {
				return null;
			}

			return versionFile;
		} catch (final IOException e) {
			// cannot log because instance area has not been set
			return null;
		}
	}

	/**
	 * @return the major and minor parts of the given version
	 */
	private static Version toMajorMinorVersion(final Version version) {
		return new Version(version.getMajor(), version.getMinor(), 0);
	}

	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
