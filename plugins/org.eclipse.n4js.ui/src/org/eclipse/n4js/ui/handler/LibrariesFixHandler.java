package org.eclipse.n4js.ui.handler;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
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
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.ui.external.ExternalLibrariesActionsHelper;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.dependencies.ProjectsSettingsFillesLocator;
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

	static private final DataCollector DC_SETUP = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Setup Dependencies");
	static private final DataCollector DC_INTALL_NPMS = DataCollectors.INSTANCE
			.getOrCreateDataCollector("install npms", DC_SETUP);
	static private final DataCollector DC_BUILD_NPMS = DataCollectors.INSTANCE
			.getOrCreateDataCollector("build npms", DC_SETUP);

	private boolean wasAutoBuilding;

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
				.createMultiStatus("Status of setting up dependnecies.");
		wasAutoBuilding = getAutobuildSetting();
		if (wasAutoBuilding)
			turnOffAutobuild();

		final SubMonitor subMonitor0 = monitor.split(5);
		refreshWorkspace(subMonitor0);

		// search for .npmrc, targetplatform.n4tp
		final SubMonitor subMonitor1 = monitor.split(5);
		ProjectsSettingsFillesLocator files = ProjectsSettingsFillesLocator.findFiles(subMonitor1);

		Collection<File> fNPMRCs = files.getNPMRCs();
		Collection<File> fN4TPs = files.getN4TPs();

		File selectedNPMRC = null;
		File selectedN4TP = null;

		if (!fNPMRCs.isEmpty() || !fN4TPs.isEmpty()) {
			userLogger.logInfo("detected custom settings, needs user input");
			Map<String, String> npmrcs = new HashMap<>();
			Map<String, String> n4tps = new HashMap<>();

			fNPMRCs.forEach(f -> npmrcs.put(f.getName(), f.getAbsolutePath()));
			fN4TPs.forEach(f -> n4tps.put(f.getName(), f.getAbsolutePath()));

			UIUtils.getDisplay().asyncExec(() -> dependneciesDialog.updateConfigs(npmrcs, n4tps, lock));

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
			selectedN4TP = getFileOrNull(dependneciesDialog.getN4TP());
			selectedNPMRC = getFileOrNull(dependneciesDialog.getNPMRC());

		}

		processSettingsForNPM(multistatus, selectedNPMRC);
		if (!multistatus.isOK())
			return multistatus;

		Measurement measurement = DC_SETUP.getMeasurement("setup npms " + Instant.now());
		final SubMonitor subMonitor2 = monitor.split(1);

		// remove npm cache
		externals.maintenanceCleanNpmCache(multistatus, subMonitor2);

		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);

		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		Measurement measurement2 = DC_INTALL_NPMS.getMeasurement("install npms " + Instant.now());
		// install npms from target platform
		Map<String, String> versionedPackages = dependneciesHelper.calculateDependenciesToInstall(selectedN4TP);
		final SubMonitor subMonitor3 = monitor.split(45);

		externals.installNoUpdate(versionedPackages, multistatus, subMonitor3);
		measurement2.end();

		Measurement measurement3 = DC_BUILD_NPMS.getMeasurement("build npms " + Instant.now());
		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(35);
		externals.maintenanceUpateState(multistatus, subMonitor4);
		measurement3.end();

		// turn on autobuild
		if (wasAutoBuilding)
			turnOnAutobuild();

		measurement.end();
		return multistatus;
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

	private boolean getAutobuildSetting() {
		return getWorkspace().getDescription().isAutoBuilding();
	}

	private void turnOffAutobuild() {
		toggleAutobuild(false);
	}

	private void turnOnAutobuild() {
		toggleAutobuild(true);
	}

	private void toggleAutobuild(final boolean enable) {
		if (isWorkbenchRunning()) {
			final IWorkspaceDescription workspaceDescription = getWorkspace().getDescription();
			if (null != workspaceDescription) {
				if (workspaceDescription.isAutoBuilding() != enable) {
					workspaceDescription.setAutoBuilding(enable);
					try {
						getWorkspace().setDescription(workspaceDescription);
					} catch (final CoreException e) {
						throw new IllegalStateException("Error while trying to turn workspace autobuild "
								+ (enable ? "on" : "off") + ".", e);
					}
				}
			}

		}
	}

}
