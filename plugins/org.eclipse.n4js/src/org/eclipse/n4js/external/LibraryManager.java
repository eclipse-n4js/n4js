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
package org.eclipse.n4js.external;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.n4js.external.LibraryChange.LibraryChangeType.Install;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.binaries.nodejs.YarnBinary;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.StatusUtils;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Class for installing npm dependencies into the external library.
 */
@Singleton
public class LibraryManager {

	private static final Logger LOGGER = Logger.getLogger(LibraryManager.class);

	private static final NPMVersionRequirement NO_VERSION_REQUIREMENT = SemverUtils.createEmptyVersionRequirement();

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private Provider<YarnBinary> yarnBinaryProvider;

	@Inject
	private NpmLogger logger;

	@Inject
	private NpmCLI npmCli;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryPreferenceStore extLibPreferenceStore;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	/**
	 * Call this method to synchronize the information in the Xtext index with all external projects in the external
	 * library folders.
	 */
	public void synchronizeNpms(IProgressMonitor monitor) {
		indexSynchronizer.synchronizeNpms(monitor);
	}

	/** Deletes all 'node_modules' folders (and their npm projects). Afterwards, the ext. library state is updated. */
	public IStatus deleteAllNodeModulesFolders(IProgressMonitor monitor) {
		MultiStatus multistatus = statusHelper.createMultiStatus("Delete all node_modules folders");
		for (SafeURI<?> nodeModulesLoc : extLibPreferenceStore.getNodeModulesLocations()) {
			// delete whole target platform folder
			if (nodeModulesLoc.exists()) {
				nodeModulesLoc.delete((IOException ioe) -> multistatus.merge(
						statusHelper.createError("Exception during deletion of the npm folder.", ioe)));
			}

			if (nodeModulesLoc.exists()) {
				// should never happen
				multistatus.merge(statusHelper
						.createError("Could not verify deletion of " + nodeModulesLoc.getAbsolutePath()));
			}
		}

		// other actions like reinstall depends on this state
		externalLibraryWorkspace.updateState();
		indexSynchronizer.synchronizeNpms(monitor);
		return multistatus;
	}

	/** Runs 'npm/yarn install' in a given folder. Afterwards, re-registers all npms. */
	public IStatus runNpmYarnInstall(PlatformResourceURI target, IProgressMonitor monitor) {
		IN4JSProject project = n4jsCore.findProject(target.toURI()).orNull();
		File projectFolder = project.getLocation().toJavaIoFile();

		boolean usingYarn = npmCli.isYarnUsed(projectFolder);

		String msg = "Running '" + (usingYarn ? "yarn" : "npm") + " install' on " + project.getProjectName();
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		IStatus binaryStatus = checkBinary(usingYarn);
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, 2);

		SubMonitor subMonitor1 = subMonitor.split(1);
		subMonitor1.setTaskName("Building installed packages...");

		// Calculate the folder in which npm/yarn should be executed, either local project folder
		// or yarn root folder
		File folderInWhichToExecute = nodeModulesDiscoveryHelper
				.getNodeModulesFolder(projectFolder.toPath()).nodeModulesFolder.getParentFile();

		npmCli.runNpmYarnInstall(folderInWhichToExecute);

		SubMonitor subMonitor2 = subMonitor.split(1);
		subMonitor2.setTaskName("Registering packages...");
		indexSynchronizer.reindexAllExternalProjects(subMonitor2);

		return status;
	}

	/** Runs 'npm/yarn install' in all user projects. Afterwards, re-registers all npms. */
	public IStatus runNpmYarnInstallOnAllProjects(IProgressMonitor monitor) {
		String msg = "Running 'npm/yarn install' on all projects";
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		Iterable<? extends IN4JSProject> allProjects = n4jsCore.findAllProjects();

		// 1) collect workspace roots for projects contained in a yarn workspace
		Set<File> yarnWorkspaceRoots = new LinkedHashSet<>();
		List<IN4JSProject> projectsOutsideAnyYarnWorkspace = new ArrayList<>();
		for (IN4JSProject project : allProjects) {
			if (!project.exists()) {
				continue;
			}
			Path projectPath = project.getLocation().toFileSystemPath();
			NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectPath);
			if (nodeModulesFolder.isYarnWorkspace) {
				yarnWorkspaceRoots.add(nodeModulesFolder.nodeModulesFolder.getParentFile());
			} else {
				projectsOutsideAnyYarnWorkspace.add(project);
			}
		}

		// 2) check the binaries that are required
		IStatus binaryStatus;
		if (!yarnWorkspaceRoots.isEmpty()) {
			binaryStatus = checkBinary(true);
			if (!binaryStatus.isOK()) {
				status.merge(binaryStatus);
			}
		}
		if (!projectsOutsideAnyYarnWorkspace.isEmpty()) {
			binaryStatus = checkBinary(false);
			if (!binaryStatus.isOK()) {
				status.merge(binaryStatus);
			}
		}
		if (!status.isOK()) {
			return status;
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor,
				1 + yarnWorkspaceRoots.size() + projectsOutsideAnyYarnWorkspace.size());

		// 3) run 'npm/yarn install' in workspace roots and non-workspace projects
		for (File yarnWorkspaceRoot : yarnWorkspaceRoots) {
			msg = "Running 'yarn install' on yarn workspace root " + yarnWorkspaceRoot;
			SubMonitor subMonitorInstall = subMonitor.split(1);
			subMonitorInstall.setTaskName(msg);
			logger.logInfo(msg);
			IStatus currStatus = npmCli.runNpmYarnInstall(yarnWorkspaceRoot);
			status.merge(currStatus);
		}
		for (IN4JSProject project : projectsOutsideAnyYarnWorkspace) {
			File projectFolder = project.getLocation().toJavaIoFile();
			boolean usingYarn = npmCli.isYarnUsed(projectFolder);
			msg = "Running '" + (usingYarn ? "yarn" : "npm") + " install' on " + project.getProjectName();
			SubMonitor subMonitorInstall = subMonitor.split(1);
			subMonitorInstall.setTaskName(msg);
			logger.logInfo(msg);
			IStatus currStatus = npmCli.runNpmYarnInstall(projectFolder);
			status.merge(currStatus);
		}

		SubMonitor subMonitorRegisterNpms = subMonitor.split(1);
		subMonitorRegisterNpms.setTaskName("Registering packages...");
		indexSynchronizer.reindexAllExternalProjects(subMonitorRegisterNpms);

		return status;
	}

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPM(N4JSProjectName packageName, FileURI target, IProgressMonitor monitor) {
		return installNPM(packageName, NO_VERSION_REQUIREMENT, target, monitor);
	}

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return a status representing the outcome of the install process.
	 * @throws IllegalArgumentException
	 *             if the given version string cannot be parsed to an {@link NPMVersionRequirement}.
	 */
	public IStatus installNPM(N4JSProjectName packageName, String packageVersionStr, FileURI target,
			IProgressMonitor monitor) {
		NPMVersionRequirement packageVersion = semverHelper.parse(packageVersionStr);
		if (packageVersion == null) {
			throw new IllegalArgumentException("unable to parse version requirement: " + packageVersionStr);
		}
		return installNPM(packageName, packageVersion, target, monitor);
	}

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPM(N4JSProjectName packageName, NPMVersionRequirement packageVersion,
			FileURI target,
			IProgressMonitor monitor) {
		return installNPMs(Collections.singletonMap(packageName, packageVersion), false, target, monitor);
	}

	/**
	 * Installs the given npm packages in a blocking fashion.
	 *
	 * This method tries to install all packages even if installation for some of them fail. In such cases it will try
	 * log encountered errors but it will try to proceed for all remaining packages. Details about issues are in the
	 * returned status.
	 *
	 * @param unversionedPackages
	 *            map of name to version data for the packages to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPMs(Collection<N4JSProjectName> unversionedPackages, FileURI target,
			IProgressMonitor monitor) {
		Map<N4JSProjectName, NPMVersionRequirement> versionedPackages = unversionedPackages.stream()
				.collect(Collectors.toMap((N4JSProjectName name) -> name,
						(N4JSProjectName name) -> NO_VERSION_REQUIREMENT));
		return installNPMs(versionedPackages, false, target, monitor);
	}

	/**
	 * Installs the given npm packages in a blocking fashion.
	 *
	 * This method tries to install all packages even if installation for some of them fail. In such cases it will try
	 * log encountered errors but it will try to proceed for all remaining packages. Details about issues are in the
	 * returned status.
	 *
	 * @param versionedNPMs
	 *            map of name to version data for the packages to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @param forceReloadAll
	 *            Specifies whether after the installation all external libraries in the external library workspace
	 *            should be reloaded and rebuilt (cf. {@link #registerAllExternalProjects(IProgressMonitor)}). If
	 *            {@code false}, only the set of packages that was created and/or updated by this install call will be
	 *            scheduled for a reload.
	 * @param target
	 *            target folder that contains both a node_modules folder and a package.json
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPMs(Map<N4JSProjectName, NPMVersionRequirement> versionedNPMs, boolean forceReloadAll,
			FileURI target, IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> installNPMsInternal(versionedNPMs, forceReloadAll, target, monitor));
	}

	/**
	 * NOTE: if the target points to a member project in a yarn workspace, this method will *NOT* switch to the yarn
	 * workspace root folder for executing yarn! Rationale: in case an explicitly mentioned npm package is installed
	 * (e.g. "yarn add lodash") it makes a difference whether that command is being executed in the member project's
	 * root folder or the yarn workspace root folder (i.e. the newly installed npm package will be added to a different
	 * package.json file in each case) and we want to support both cases.
	 */
	private IStatus installNPMsInternal(Map<N4JSProjectName, NPMVersionRequirement> versionedNPMs,
			boolean forceReloadAll,
			FileURI target, IProgressMonitor monitor) {

		String msg = getMessage(versionedNPMs);
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		boolean usingYarn = npmCli.isYarnUsed(target);

		IStatus binaryStatus = checkBinary(usingYarn);
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (Measurement mes = N4JSDataCollectors.dcLibMngr.getMeasurement("installDependenciesInternal");) {
			final int steps = forceReloadAll ? 3 : 2;
			SubMonitor subMonitor = SubMonitor.convert(monitor, steps + 4);
			Map<N4JSProjectName, NPMVersionRequirement> npmsToInstall = new LinkedHashMap<>(versionedNPMs);

			SubMonitor subMonitor1 = subMonitor.split(2);
			subMonitor1.setTaskName("Installing packages... [step 1 of " + steps + "]");

			List<LibraryChange> actualChanges = installNPMs(subMonitor1, status, npmsToInstall,
					target);

			if (!status.isOK()) {
				return status;
			}

			// if forceReloadAll, unregister all currently-registered projects from
			// the workspace and remove them from the index
			if (forceReloadAll) {
				SubMonitor subMonitor2 = subMonitor.split(1);
				subMonitor2.setTaskName("Clean all packages... [step 2 of 3]");
				externalLibraryWorkspace.deregisterAllProjects(subMonitor2);
			}

			try (Measurement m = N4JSDataCollectors.dcIndexSynchronizer.getMeasurement("synchronizeNpms")) {
				SubMonitor subMonitor3 = subMonitor.split(4);
				subMonitor3.setTaskName("Building installed packages... [step " + steps + " of " + steps + "]");
				indexSynchronizer.synchronizeNpms(subMonitor3, actualChanges);
			}

			return status;

		} finally {
			monitor.done();
		}
	}

	private String getMessage(Map<N4JSProjectName, NPMVersionRequirement> versionedNPMs) {
		String msg = "Installing NPM(s): ";

		for (Iterator<Map.Entry<N4JSProjectName, NPMVersionRequirement>> entryIter = versionedNPMs.entrySet()
				.iterator(); entryIter.hasNext();) {

			Map.Entry<N4JSProjectName, NPMVersionRequirement> entry = entryIter.next();
			msg += entry.getKey(); // packageName

			NPMVersionRequirement versionRequirement = entry.getValue();
			if (versionRequirement != null && !SemverUtils.isEmptyVersionRequirement(versionRequirement)) {
				msg += "@" + versionRequirement;
			}

			if (entryIter.hasNext()) {
				msg += ", ";
			}
		}

		return msg;
	}

	private List<LibraryChange> installNPMs(IProgressMonitor monitor, MultiStatus status,
			Map<N4JSProjectName, NPMVersionRequirement> installRequested, FileURI target) {

		List<LibraryChange> actualChanges = new LinkedList<>();
		try (Measurement m = N4JSDataCollectors.dcNpmInstall.getMeasurement("batchInstall")) {
			Collection<LibraryChange> requestedChanges = new LinkedList<>();

			for (Map.Entry<N4JSProjectName, NPMVersionRequirement> reqestedNpm : installRequested.entrySet()) {
				N4JSProjectName name = reqestedNpm.getKey();
				NPMVersionRequirement requestedVersion = reqestedNpm.getValue();
				String requestedVersionStr = SemverSerializer.serialize(requestedVersion);
				requestedChanges.add(new LibraryChange(Install, null, name, requestedVersionStr));
			}

			// install
			actualChanges.addAll(npmCli.batchInstall(monitor, status, requestedChanges, target));
		}

		return actualChanges;
	}

	/**
	 * Uninstalls the given npm package in a blocking fashion. Note that this will uninstall *all* npms with the given
	 * name.
	 *
	 * @param npmName
	 *            the name of the npm projects that have to be uninstalled via package manager.
	 * @param monitor
	 *            the monitor for the blocking uninstall process.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallNPM(N4JSProjectName npmName, IProgressMonitor monitor) {
		List<N4JSExternalProject> npmProjects = externalLibraryWorkspace.getProjectsForName(npmName);
		MultiStatus multiStatus = statusHelper.createMultiStatus("Uninstall all npms with the name: " + npmName);
		for (N4JSExternalProject npm : npmProjects) {
			IStatus status = uninstallNPM(npm.getSafeLocation(), monitor);
			multiStatus.merge(status);
		}
		return multiStatus;
	}

	/**
	 * Uninstalls the given npm package in a blocking fashion.
	 *
	 * @param packageURI
	 *            the name of the package that has to be uninstalled via package manager.
	 * @param monitor
	 *            the monitor for the blocking uninstall process.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallNPM(FileURI packageURI, IProgressMonitor monitor) {
		String msg = "Uninstalling NPM: " + packageURI;
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		// trim package name and "node_modules" segments from packageURI:
		FileURI containingProjectURI = getParentOfMatchingLocation(packageURI,
				name -> N4JSGlobals.NODE_MODULES.equals(name));
		boolean usingYarn = npmCli.isYarnUsed(containingProjectURI.toJavaIoFile());

		IStatus binaryStatus = checkBinary(usingYarn);
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (Measurement mes = N4JSDataCollectors.dcLibMngr.getMeasurement("uninstallDependenciesInternal");) {
			N4JSExternalProject externalProject = externalLibraryWorkspace.getProject(packageURI);
			if (externalProject == null) {
				return status;
			}

			IN4JSProject iProject = externalProject.getIProject();
			LibraryChange requestedChange = new LibraryChange(LibraryChangeType.Uninstall, packageURI,
					iProject.getProjectName(), iProject.getVersion().toString());
			Collection<LibraryChange> actualChanges = npmCli.uninstall(monitor, status, requestedChange);

			try (Measurement m = N4JSDataCollectors.dcIndexSynchronizer.getMeasurement("synchronizeNpms")) {
				indexSynchronizer.synchronizeNpms(monitor, actualChanges);
			}

			return status;

		} finally {
			monitor.done();
		}
	}

	private FileURI getParentOfMatchingLocation(FileURI uri, Predicate<? super String> predicate) {
		while (uri != null && !predicate.test(uri.getName())) {
			uri = uri.getParent();
		}
		if (uri != null) {
			return uri.getParent();
		}
		return null;

	}

	/**
	 * Reloads the external libraries by re-indexing all external projects that not shadowed from projects in the
	 * workspace. Returns with an {@link IStatus status} representing the outcome of the refresh operation.
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus registerAllExternalProjects(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> registerAllExternalProjectsInternal(monitor));
	}

	private IStatus registerAllExternalProjectsInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		try {
			MultiStatus refreshStatus = statusHelper.createMultiStatus("Refreshing npm type definitions.");
			indexSynchronizer.reindexAllExternalProjects(subMonitor.split(1));

			return refreshStatus;

		} finally {
			subMonitor.done();
		}
	}

	/**
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus registerUnregisteredNpms(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> registerUnregisteredNpmsInternal(monitor));
	}

	private IStatus registerUnregisteredNpmsInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");
		MultiStatus refreshStatus = statusHelper.createMultiStatus("Register not registered NPM(s).");

		List<N4JSExternalProject> unregisteredProjects = new LinkedList<>();
		for (N4JSExternalProject p : externalLibraryWorkspace.getProjects()) {
			if (!indexSynchronizer.isInIndex(p)) {
				unregisteredProjects.add(p);
			}
		}

		indexSynchronizer.synchronizeNpms(monitor);

		Collection<String> packageNames = getAllNpmProjectsMapping().keySet();
		if (packageNames.isEmpty()) {
			return statusHelper.OK();
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		try {
			indexSynchronizer.reindexAllExternalProjects(subMonitor.split(1));

			return refreshStatus;

		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Checks the npm/yarn binary.
	 */
	private IStatus checkBinary(boolean usingYarn) {
		Binary binary = (usingYarn ? yarnBinaryProvider : npmBinaryProvider).get();
		IStatus binaryStatus = binary.validate();
		if (!binaryStatus.isOK()) {
			return statusHelper.createError(
					binary.getLabel() + " binary invalid: " + StatusUtils.getErrorMessage(binaryStatus, false),
					new IllegalBinaryStateException(binary, binaryStatus));
		}
		return statusHelper.OK();
	}

	/**
	 * A map of project (npm package) names to project location mappings.
	 */
	private Map<String, java.net.URI> getAllNpmProjectsMapping() {
		Map<String, java.net.URI> mappings = newHashMap();

		for (IProject project : externalLibraryWorkspace.getProjects()) {
			if (project.isAccessible() && project instanceof ExternalProject) {
				java.net.URI location = ((ExternalProject) project).getExternalResource().toURI();
				mappings.put(project.getName(), location);
			}
		}

		return ImmutableMap.copyOf(mappings);
	}

	private IStatus runWithWorkspaceLock(Supplier<IStatus> operation) {
		if (Platform.isRunning()) {
			ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRoot();
			try {
				Job.getJobManager().beginRule(rule, null);
				return operation.get();
			} catch (final OperationCanceledException e) {
				LOGGER.info("User cancelled operation.");
				return statusHelper.createCancel("User cancelled operation.");
			} finally {
				Job.getJobManager().endRule(rule);
			}
		} else {
			// locking not available/required in headless case
			return operation.get();
		}
	}
}
