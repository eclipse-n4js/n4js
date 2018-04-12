package org.eclipse.n4js.ui.handler;

import static org.eclipse.jface.dialogs.MessageDialog.openError;
import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.dependencies.ProjectsSettingsFilesLocator;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.swt.SWTException;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Handler for user requesting to fix all problems with projects / dependencies. In particular, we delete all external
 * libraries, install them again, clear build everything.
 */
public class LibrariesFixHandler extends AbstractHandler {
	private static final Logger LOGGER = Logger.getLogger(LibrariesFixHandler.class);

	private final Object lock = new Object();

	/** Logger that logs to the npm console which is visible by the user in his IDE. */
	@Inject
	private NpmLogger userLogger;

	@Inject
	private ProjectDependenciesHelper dependneciesHelper;

	@Inject
	private ExternalLibrariesActionsHelper externals;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private BinariesPreferenceStore preferenceStore;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			final DependenciesDialog dependenciesDialog = new DependenciesDialog(UIUtils.getShell());
			IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					IStatus status = setupWorkspaceDependnecies(monitor, dependenciesDialog);
					if (!status.isOK())
						userLogger.logError(status);
					monitor.done();
				}

			};
			dependenciesDialog.run(true, true, iRunnableWithProgress);
		} catch (InvocationTargetException | InterruptedException | SWTException err) {
			LOGGER.error("unhandled error while setting up dependencies", err);
			getDisplay().asyncExec(() -> openError(
					UIUtils.getShell(),
					"Setting up external libraries failed.",
					"Error while setting up external libraries.\n"
							+ "Please check your Error Log view for the detailed log about the failure.\n" +
							" (note that autobuild is " + AutobuildUtils.get() + ")"));
		}

		return null;
	}

	/**
	 * long running operation that scans workspace for missing dependencies and user config files. Installs missing
	 * dependencies with NPM based on calculated data. In case of ambiguities, asks user for input, but user
	 * interactions should be kept minimal.
	 */
	public IStatus setupWorkspaceDependnecies(IProgressMonitor pmonitor, DependenciesDialog dependneciesDialog) {
		final SubMonitor monitor = SubMonitor.convert(pmonitor, 100);
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of setting up dependencies.");

		final boolean wasAutoBuilding = AutobuildUtils.get();
		AutobuildUtils.turnOff();

		final SubMonitor subMonitor0 = monitor.split(5);
		refreshWorkspace(subMonitor0);

		// search for .npmrc, targetplatform.n4tp
		final SubMonitor subMonitor1 = monitor.split(5);
		ProjectsSettingsFilesLocator files = ProjectsSettingsFilesLocator.findFiles(subMonitor1);

		Collection<File> fNPMRCs = files.getNPMRCs();

		File selectedNPMRC = null;

		if (!fNPMRCs.isEmpty()) {
			userLogger.logInfo("detected custom settings, needs user input");
			Map<String, String> npmrcs = new HashMap<>();

			fNPMRCs.forEach(f -> npmrcs.put(f.getName(), f.getAbsolutePath()));

			UIUtils.getDisplay().asyncExec(() -> dependneciesDialog.updateConfigs(npmrcs, lock));

			// at this point UI is updated with detected settings,
			// and this thread waits to be notified by the UI thread
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					multistatus.add(statusHelper.createError("Interrupted while waiting for the user input. ", e));
					return multistatus;
				}
			}

			if (pmonitor.isCanceled()) {
				userLogger.logInfo("Operation was cancelled.");
				multistatus.add(statusHelper.createInfo("Operation was cancelled."));
				return multistatus;
			}

			// get selection from the UI
			selectedNPMRC = getFileOrNull(dependneciesDialog.getNPMRC());

		}

		processSettingsForNPM(multistatus, selectedNPMRC);
		if (!multistatus.isOK())
			return multistatus;

		final SubMonitor subMonitor2 = monitor.split(1);

		calculateAndInstallDependencies(subMonitor2, multistatus);

		// turn on autobuild
		if (wasAutoBuilding)
			AutobuildUtils.turnOn();

		return multistatus;
	}

	/** Streamlined process of calculating and installing the dependencies. */
	public void calculateAndInstallDependencies(SubMonitor monitor, MultiStatus multistatus) {
		final SubMonitor subMonitor2 = monitor.split(1);

		// remove npm cache
		externals.maintenanceCleanNpmCache(multistatus, subMonitor2);

		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);

		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		// install npms from target platform
		Map<String, String> versionedPackages = dependneciesHelper.calculateDependenciesToInstall();
		final SubMonitor subMonitor3 = monitor.split(45);

		externals.installNoUpdate(versionedPackages, multistatus, subMonitor3);

		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(35);
		externals.maintenanceUpateState(multistatus, subMonitor4);
	}

	/**
	 * information about {@code .npmrc} is deep in the NodeProcessBuilder and by design it is not exposed. We could
	 * redesign that part and expose it, but it makes sense to assume user selected {@code .npmrc} file while setting up
	 * the workspace should be used for further dependencies setups (e.g. quickfixes in manifests) in this workspace
	 * hence we save provided {@code .npmrc} file in the preferences.
	 *
	 * @param multistatus
	 *            used to accumulate operations result, if any
	 * @param selectedNPMRC
	 *            npmrc file to process
	 */
	private void processSettingsForNPM(final MultiStatus multistatus, File selectedNPMRC) {
		if (selectedNPMRC != null) {
			NpmrcBinary npmrcBinary = npmrcBinaryProvider.get();
			URI oldLocation = npmrcBinary.getUserConfiguredLocation();
			File npmrcFolder = selectedNPMRC.getParentFile();
			if (npmrcFolder != null) {
				URI newLocation = npmrcFolder.toURI();
				if (!newLocation.equals(oldLocation)) {
					userLogger.logInfo("dropping old npmrc : " + oldLocation);
					userLogger.logInfo("setting new npmrc : " + newLocation);
					preferenceStore.setPath(npmrcBinaryProvider.get(), newLocation);
					IStatus save = preferenceStore.save();
					multistatus.add(save);
				}
			}
		}
	}

	/**
	 * Checks if file exists under path specified by the provided string.
	 *
	 * @return {@code File} instance or {@code null}
	 */
	private File getFileOrNull(String filePath) {
		if (!Strings.isNullOrEmpty(filePath)) {
			File fNPMRC = new File(filePath);
			if (fNPMRC.isFile())
				return fNPMRC;
		}
		return null;
	}

	private void refreshWorkspace(IProgressMonitor monitor) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, projects.length);
		for (IProject project : projects) {
			try {
				subMonitor.beginTask("refreshing " + project.getName(), 1);
				project.refreshLocal(IResource.DEPTH_INFINITE, subMonitor);
			} catch (CoreException e) {
				LOGGER.error("Error when refreshing workspace", e);
			}
		}
	}

}
