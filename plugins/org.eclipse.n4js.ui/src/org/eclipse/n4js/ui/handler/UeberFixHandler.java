package org.eclipse.n4js.ui.handler;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.nodejs.NpmrcBinary;
import org.eclipse.n4js.external.NpmLogger;
import org.eclipse.n4js.external.libraries.TargetPlatformModel;
import org.eclipse.n4js.ui.external.ExternalLibraraiesHelper;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.dependencies.FillesLocator;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Handler for user requesting to fix all problems with projects / dependencies. In particular, we delete all external
 * libraries, install them again, clear build everything.
 */
public class UeberFixHandler extends AbstractHandler {
	private static final Logger LOGGER = Logger.getLogger(UeberFixHandler.class);

	private boolean wasAutoBuilding;

	/** Logger that logs to the npm console which is visible by the user in his IDE. */
	@Inject
	private NpmLogger userLogger;

	@Inject
	private DependneciesHelper dependneciesHelper;

	@Inject
	private ExternalLibraraiesHelper externals;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private BinariesPreferenceStore preferenceStore;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			final DependneciesDialog dependneciesDialog = new DependneciesDialog(UIUtils.getShell());
			IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					IStatus status = setupWorkspaceDependnecies(monitor, dependneciesDialog);
					if (!status.isOK())
						userLogger.logError(status);
					monitor.done();
				}

			};
			dependneciesDialog.run(true, true, iRunnableWithProgress);
		} catch (InvocationTargetException | InterruptedException err) {
			userLogger.logError("unhandled error while setting up dependnecies", err);
		}

		return null;
	}

	/**
	 * long running operation that scans workspace for missing dependencies and user config files. Installs missing
	 * dependencies with NPM based on calculated data. In case of ambiguities, asks user for inptu, but user
	 * interactions should be kept minimal.
	 *
	 */
	public IStatus setupWorkspaceDependnecies(IProgressMonitor pmonitor, DependneciesDialog dependneciesDialog) {
		final SubMonitor monitor = SubMonitor.convert(pmonitor, 100);
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of setting up dependnecies.");
		wasAutoBuilding = getAutobuildSetting();
		if (wasAutoBuilding)
			turnOffAutobuild();

		// TODO make refreshWorkspace part of the monitor task
		// it gets blocked by auto build, so it can take a while on large workspace
		userLogger.logInfo("force workspace refresh");
		refreshWorkspace();
		userLogger.logInfo("finished workspace refresh");

		// search for .npmrc, targetplatform.n4tp
		final SubMonitor subMonitor1 = monitor.split(10);
		FillesLocator files = FillesLocator.findFiles(subMonitor1);

		Collection<File> fNPMRCs = files.getNPMRCs();
		Collection<File> fN4TPs = files.getN4TPs();
		// TODO add to fNPMRC file from preferences
		// process found files

		File selectedNPMRC = null;
		File selectedN4TP = null;

		if (!fNPMRCs.isEmpty() || !fN4TPs.isEmpty()) {
			userLogger.logInfo("detected custom settings, needs user input");
			Map<String, String> npmrcs = new HashMap<>();
			Map<String, String> n4tps = new HashMap<>();

			fNPMRCs.forEach(f -> npmrcs.put(f.getName(), f.getAbsolutePath()));
			fN4TPs.forEach(f -> n4tps.put(f.getName(), f.getAbsolutePath()));

			UIUtils.getDisplay().asyncExec(() -> dependneciesDialog.updateConfigs(npmrcs, n4tps, this));

			// at this point UI is updated with detected settings,
			// UI should also create new button to proceed,
			// and this thread should wait for the user to click on proceed
			// than we check UI for the options selected by the user
			// and proceed based on that selection
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					multistatus.add(statusHelper.createError("Interrupted while waiting for the user input. ", e));
					return multistatus;
				}
			}

			// get selection from the UI
			String n4tp = dependneciesDialog.getN4TP();
			if (!Strings.isNullOrEmpty(n4tp)) {
				File fN4TP = new File(n4tp);
				if (fN4TP.isFile())
					selectedN4TP = fN4TP;
			}
			String npmrc = dependneciesDialog.getNPMRC();
			if (!Strings.isNullOrEmpty(npmrc)) {
				File fNPMRC = new File(npmrc);
				if (fNPMRC.isFile())
					selectedNPMRC = fNPMRC;
			}

		}

		// information about .npmrc is deep in the NodeProcessBuilder and by designe
		// it is not exposed. We could redesign that part and expose it, but it makes sense
		// to assume if user points to a specific .npmrc file while setting up the workspace
		// then this .npmrc should be used for further dependencies setups in this workspace
		// hence set it in preferences, so that other invocations of npm install will use it.
		// TODO if .npmrc was selected should we save it in preferences?

		if (selectedNPMRC != null) {
			// TODO check if it is "equal" then skip setting
			NpmrcBinary npmrcBinary = npmrcBinaryProvider.get();
			URI oldLocation = npmrcBinary.getUserConfiguredLocation();
			URI newLocation = selectedNPMRC.getParentFile().toURI();
			if (newLocation.compareTo(oldLocation) != 0) {
				userLogger.logInfo("dropping old npmrc : " + oldLocation);
				userLogger.logInfo("setting new npmrc : " + newLocation);
				preferenceStore.setPath(npmrcBinaryProvider.get(), newLocation);
				IStatus save = preferenceStore.save();
				if (!save.isOK()) {
					multistatus.add(save);
					return multistatus;
				}
			}
		}

		// remove npm cache
		final SubMonitor subMonitor2 = monitor.split(1);
		externals.maintenanceCleanNpmCache(multistatus, subMonitor2);
		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);
		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		// TODO combine with "*.n4tp" file
		Map<String, String> versionedPackages = dependneciesToInstall(selectedN4TP);

		// install npms from target platform
		final SubMonitor subMonitor3 = monitor.split(35);
		externals.intallAndUpdate(versionedPackages, multistatus, subMonitor3);

		// rebuild externals & schedule full rebuild
		final SubMonitor subMonitor4 = monitor.split(45);
		externals.maintenanceUpateState(multistatus, subMonitor4);
		monitor.done();

		// turn on autobuild
		if (wasAutoBuilding)
			turnOnAutobuild();

		return multistatus;
	}

	/**
	 * Creates map of dependency-version calculated based on the selected target platform file, workspace projects and
	 * installed external libraries. Note that list of dependencies is combined, but in case of conflicting versions
	 * info, workspace based data wins, e.g.
	 *
	 * <pre>
	 *  <ul>
	 *   <li> platform files requests "express" but no project depends on "express" then express is installed (in latest version)</li>
	 *   <li> platform files requests "express@2.0.0" but workspace project depends on "express@1.0.0"  then express is installed in version "1.0.0"</li>
	 *  <ul>
	 * </pre>
	 */
	private Map<String, String> dependneciesToInstall(File selectedN4TP) {
		Map<String, String> versionedPackages = new HashMap<>();
		populateFromPlatformFile(versionedPackages, selectedN4TP);
		versionedPackages.putAll(dependneciesHelper.calculateMissingDependnecies());
		// TODO remove logging
		if (LOGGER.isDebugEnabled()) {
			StringJoiner messages = new StringJoiner(System.lineSeparator());
			messages.add("dependencies to install: ");
			versionedPackages.forEach((id, v) -> messages.add(" - " + id + v));
			LOGGER.debug(messages);
		}
		return versionedPackages;
	}

	/** Updates dependencies to install with information from platform file. */
	private void populateFromPlatformFile(Map<String, String> versionedPackages, File n4tp) {
		if (n4tp != null) {
			try {
				final URI platformFileLocation = n4tp.toURI();
				Map<String, String> n4tpPackages;
				n4tpPackages = TargetPlatformModel
						.npmVersionedPackageNamesFrom(platformFileLocation);
				versionedPackages.putAll(n4tpPackages);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void refreshWorkspace() {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
