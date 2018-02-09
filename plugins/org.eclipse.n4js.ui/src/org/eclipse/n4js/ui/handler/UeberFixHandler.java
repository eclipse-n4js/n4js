package org.eclipse.n4js.ui.handler;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.ui.PlatformUI.isWorkbenchRunning;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.n4js.ui.external.ExternalLibraraiesHelper;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.dependencies.FillesLocator;
import org.eclipse.n4js.utils.StatusHelper;

import com.google.inject.Inject;

/**
 * Handler for user requesting to fix all problems with projects / dependencies. In particular, we delete all external
 * libraries, install them again, clear build everything.
 */
public class UeberFixHandler extends AbstractHandler {
	/** early return for debugging on large workspace */
	private static boolean TMP_RETURN = true;
	private boolean wasAutoBuilding;

	@Inject
	private DependneciesHelper dependneciesHelper;

	@Inject
	private ExternalLibraraiesHelper externals;

	@Inject
	private StatusHelper statusHelper;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			final DependneciesDialog dependneciesDialog = new DependneciesDialog(UIUtils.getShell());
			IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InterruptedException {
					setupWorkspaceDependnecies(monitor, dependneciesDialog);
					monitor.done();
				}

			};
			dependneciesDialog.run(true, true, iRunnableWithProgress);
		} catch (InvocationTargetException | InterruptedException err) {
			// TODO handle errors
			err.printStackTrace();
		}

		return null;
	}

	/**
	 * long running operation that scans workspace for missing dependencies and user config files. Installs missing
	 * dependencies with NPM based on calculated data. In case of ambiguities, asks user for inptu, but user
	 * interactions should be kept minimal.
	 *
	 */
	public void setupWorkspaceDependnecies(IProgressMonitor pmonitor, DependneciesDialog dependneciesDialog)
			throws InterruptedException {
		final SubMonitor monitor = SubMonitor.convert(pmonitor, 100);
		final MultiStatus multistatus = statusHelper
				.createMultiStatus("Status of setting up dependnecies.");
		wasAutoBuilding = getAutobuildSetting();
		if (wasAutoBuilding)
			turnOffAutobuild();

		// TODO make refreshWorkspace part of the monitor task
		// it gets blocked by auto build, so it can take a while
		// on large workspace
		System.out.println("refreshing workspace");
		refreshWorkspace();
		System.out.println("finished refreshing workspace");

		// search for .npmrc, targetplatform.n4tp
		final SubMonitor subMonitor1 = monitor.split(10);
		FillesLocator files = FillesLocator.findFiles(subMonitor1);

		Collection<File> fNPMRC = files.getNPMRCs();
		Collection<File> fN4TP = files.getN4TPs();
		// TODO add to fNPMRC file from preferences
		// process found files

		File selectedNPMRC = null;
		File selectedN4TP = null;

		if (!fNPMRC.isEmpty() || !fN4TP.isEmpty()) {
			System.out.println("will wait");
			Map<String, String> npmrcs = new HashMap<>();
			Map<String, String> n4tps = new HashMap<>();

			fNPMRC.forEach(f -> npmrcs.put(f.getName(), f.getAbsolutePath()));
			fN4TP.forEach(f -> n4tps.put(f.getName(), f.getAbsolutePath()));

			UIUtils.getDisplay().asyncExec(() -> dependneciesDialog.updateConfigs(npmrcs, n4tps, this));

			// at this point UI is updated with detected settings,
			// UI should also create new button to proceed,
			// and this thread should wait for the user to click on proceed
			// than we check UI for the options selected by the user
			// and proceed based on that selection
			synchronized (this) {
				wait();
			}

			// TODO get selection from the UI

		}

		System.out.println("proceed");

		if (TMP_RETURN)
			return;

		// TODO if .npmrc was selected should we save it in preferences?
		// if no, how do we pass this to the npm process?

		// remove npm cache
		final SubMonitor subMonitor2 = monitor.split(1);
		externals.maintenanceCleanNpmCache(multistatus, subMonitor2);
		// reset type definitions
		externals.maintenanceResetTypeDefinitions(multistatus);
		// remove npms
		externals.maintenanceDeleteNpms(multistatus);

		// TODO combine with "*.n4tp" file
		Map<String, String> versionedPackages = dependneciesHelper.calculateMissingDependnecies();

		System.out.println("missing dependnecies found: ");
		versionedPackages.forEach((id, v) -> System.out.println(" - " + id + "@" + v));

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
