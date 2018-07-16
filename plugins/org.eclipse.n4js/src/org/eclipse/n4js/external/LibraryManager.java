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
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.eclipse.n4js.external.LibraryChange.LibraryChangeType.Install;
import static org.eclipse.n4js.external.LibraryChange.LibraryChangeType.Uninstall;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.external.version.VersionConstraintFormatUtil;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.Version;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Pair;

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

	private static String NO_VERSION = "";

	private static DataCollector dcLibMngr = DataCollectors.INSTANCE.getOrCreateDataCollector("Library Manager");

	@Inject
	private NpmPackageToProjectAdapter npmPackageToProjectAdapter;

	@Inject
	private TargetPlatformInstallLocationProvider locationProvider;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private NpmLogger logger;

	@Inject
	private NpmCLI npmCli;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private FileBasedExternalPackageManager filebasedPackageManager;

	@Inject
	private IN4JSCore n4jsCore;

	// @Inject
	// private ProjectDescriptionHelper projectDescriptionHelper;

	/**
	 * see {@link ExternalIndexSynchronizer#isProjectsSynchronized()}.
	 */
	public boolean isProjectsSynchronized() {
		return indexSynchronizer.isProjectsSynchronized();
	}

	/**
	 * This method will query the current build state of all external projects that are available through
	 * {@link ExternalLibraryWorkspace} or {@link IN4JSCore}.
	 *
	 * @return true iff all external projects have been built successfully.
	 */
	public boolean isProjectsBuilt() {
		return true;
	}

	/**
	 * Call this method to synchronize the information in the Xtext index with all external projects in the external
	 * library folders.
	 */
	public void synchronizeNpms(IProgressMonitor monitor) {
		indexSynchronizer.synchronizeNpms(monitor);
	}

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPM(String packageName, IProgressMonitor monitor) {
		return installNPM(packageName, NO_VERSION, monitor);
	}

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPM(String packageName, String packageVersion, IProgressMonitor monitor) {
		return installNPMs(Collections.singletonMap(packageName, packageVersion), monitor);
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
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPMs(Collection<String> unversionedPackages, IProgressMonitor monitor) {
		Map<String, String> versionedPackages = unversionedPackages.stream()
				.collect(Collectors.toMap((String name) -> name, (String name) -> NO_VERSION));
		return installNPMs(versionedPackages, monitor);
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
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installNPMs(Map<String, String> versionedNPMs, IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> installNPMsInternal(versionedNPMs, monitor));
	}

	private IStatus installNPMsInternal(Map<String, String> versionedNPMs, IProgressMonitor monitor) {
		String msg = "Installing NPM(s): " + String.join(", ", versionedNPMs.keySet());
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		IStatus binaryStatus = checkNPM();
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (ClosableMeasurement mes = dcLibMngr.getClosableMeasurement("installDependenciesInternal");) {
			Map<String, String> npmsToInstall = versionedNPMs;
			Set<LibraryChange> actualChanges = new HashSet<>();
			do {
				List<LibraryChange> deltaChanges = installUninstallNPMs(monitor, status, npmsToInstall, emptyList());
				actualChanges.addAll(deltaChanges);
				npmsToInstall = getDependenciesOfNPMs(deltaChanges);

				// obtain list of all available project IDs (external and workspace)
				Set<String> allProjectsIds = StreamSupport.stream(n4jsCore.findAllProjects().spliterator(), false)
						.map(p -> p.getProjectId()).collect(Collectors.toSet());

				// Note: need to make sure the projects in npmsToInstall are not in the workspace yet; method
				// #installUninstallNPMs() (which will be invoked in a moment) is doing this as well, but that method
				// does not consider the shipped code. For example, without the next line, "n4js-runtime-node" would be
				// installed even though it is already available via the shipped code.
				npmsToInstall.keySet().removeAll(allProjectsIds);

				if (!npmsToInstall.isEmpty()) {
					msg = "Installing transitive dependencies: " + String.join(", ", npmsToInstall.keySet());
					logger.logInfo(msg);
				}
			} while (!npmsToInstall.isEmpty());

			indexSynchronizer.synchronizeNpms(monitor, actualChanges);

			return status;

		} finally {
			monitor.done();
		}
	}

	/**
	 * This method returns all transitive dependencies that are implied by the given list of {@link LibraryChange}s.
	 *
	 * For non-N4JS npm packages, these are the dependencies of the corresponding type definitions (if present) and for
	 * N4JS npm packages, these are all package dependencies.
	 * <p>
	 * GH-862: This must be revisited when solving GH-821
	 */
	private Map<String, String> getDependenciesOfNPMs(List<LibraryChange> actualChanges) {
		Map<String, String> dependencies = new HashMap<>();
		for (LibraryChange libChange : actualChanges) {
			if (libChange.type == LibraryChangeType.Added) {
				final org.eclipse.emf.common.util.URI npmLocation = libChange.location;

				// We need to consider three different cases here (see 1, 2, 3):

				// 1. The package has a package-fragment.json: Make sure to only collect
				// transitive dependencies declared in the fragment (type definition dependencies)
				if (filebasedPackageManager.isExternalProjectWithFragment(npmLocation)) {
					ProjectDescription description = filebasedPackageManager
							.loadFragmentProjectDescriptionFromProjectRoot(npmLocation);
					collectDependencies(description, dependencies);
					continue;
				}

				// obtain project description of the added project (package.json + fragment)
				final ProjectDescription pd = filebasedPackageManager
						.loadProjectDescriptionFromProjectRoot(npmLocation);

				if (pd == null) {
					LOGGER.error("Cannot obtain project description of npm at " + npmLocation
							+ ". Installation results may be inaccurate.");
					continue;
				}

				// 2. The package represents an actual N4JS project (with .n4js resources), which
				// needs to be built. In that case we must install all of its dependencies, as declared
				// in its package.json file.
				if (pd.isHasN4JSNature()) {
					collectDependencies(pd, dependencies);
					continue;
				}

				// 3. The package is a plain npm package w/o fragment (type definitions): In this case we do not collect
				// its dependencies transitively (no type definitions required)
			}
		}

		return dependencies;
	}

	/**
	 * Reads all dependencies from the given project {@code description} and puts them in {@code dependencies}.
	 *
	 * @param description
	 *            The project description to collect dependencies from.
	 * @param dependencies
	 *            The map to store the collected dependencies in. Stores a mapping of the dependency name to the version
	 *            constraint.
	 */
	private void collectDependencies(ProjectDescription description, Map<String, String> dependencies) {
		for (ProjectDependency pDep : description.getProjectDependencies()) {
			String name = pDep.getProjectId();
			String version = NO_VERSION;
			if (pDep.getVersionConstraint() != null) {
				version = VersionConstraintFormatUtil.npmFormat(pDep.getVersionConstraint());
			}
			dependencies.put(name, version);
		}
	}

	private List<LibraryChange> installUninstallNPMs(IProgressMonitor monitor, MultiStatus status,
			Map<String, String> installRequested, Collection<String> removeRequested) {

		monitor.setTaskName("Installing packages... [step 1 of 4]");
		SubMonitor subMonitor = SubMonitor.convert(monitor, 3);

		Collection<LibraryChange> requestedChanges = getRequestedChanges(installRequested, removeRequested);
		List<LibraryChange> actualChanges = new LinkedList<>();

		// remove
		actualChanges.addAll(npmCli.batchUninstall(subMonitor, status, requestedChanges));
		subMonitor.worked(1);

		// install
		actualChanges.addAll(npmCli.batchInstall(subMonitor, status, requestedChanges));
		subMonitor.worked(1);

		// adapt installed
		adaptNPMPackages(monitor, status, actualChanges);
		subMonitor.worked(1);

		return actualChanges;
	}

	private Collection<LibraryChange> getRequestedChanges(Map<String, String> installRequested,
			Collection<String> removeRequested) {

		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> installedNpms = indexSynchronizer.findNpmsInFolder();
		Collection<LibraryChange> requestedChanges = new LinkedList<>();

		for (Map.Entry<String, String> reqestedNpm : installRequested.entrySet()) {
			String name = reqestedNpm.getKey();
			String versionRequestedString = reqestedNpm.getValue();
			Version versionRequested = new Version(versionRequestedString);
			if (installedNpms.containsKey(name)) {
				org.eclipse.emf.common.util.URI location = installedNpms.get(name).getKey();
				String versionInstalledString = Strings.emptyIfNull(installedNpms.get(name).getValue());
				Version versionInstalled = new Version(versionInstalledString);
				if (versionInstalledString.isEmpty() || versionRequested.compareTo(versionInstalled) == 0) {
					// already installed
				} else {
					// wrong version installed -> update (uninstall, then install)
					requestedChanges.add(new LibraryChange(Uninstall, location, name, versionInstalledString));
					requestedChanges.add(new LibraryChange(Install, location, name, versionRequestedString));
				}
			} else {
				requestedChanges.add(new LibraryChange(Install, null, name, versionRequestedString));
			}
		}

		for (String name : removeRequested) {
			if (installedNpms.containsKey(name)) {
				requestedChanges.add(new LibraryChange(Uninstall, null, name, ""));
			} else {
				// already removed
			}
		}

		return requestedChanges;
	}

	private Collection<File> adaptNPMPackages(IProgressMonitor monitor, MultiStatus status,
			Collection<LibraryChange> changes) {

		monitor.setTaskName("Adapting npm package structure to N4JS project structure... [step 3 of 4]");
		List<String> installedNpmNames = new LinkedList<>();
		for (LibraryChange change : changes) {
			if (change.type == LibraryChangeType.Added) {
				installedNpmNames.add(change.name);
			}
		}
		org.eclipse.xtext.util.Pair<IStatus, Collection<File>> result;
		result = npmPackageToProjectAdapter.adaptPackages(installedNpmNames);

		IStatus adaptionStatus = result.getFirst();

		// log possible errors, but proceed with the process
		// assume that at least some packages were installed correctly and can be adapted
		if (!adaptionStatus.isOK()) {
			logger.logError(adaptionStatus);
			status.merge(adaptionStatus);
		}

		Collection<File> adaptedPackages = result.getSecond();
		monitor.worked(2);
		return adaptedPackages;
	}

	/**
	 * Uninstalls the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be uninstalled via package manager.
	 * @param monitor
	 *            the monitor for the blocking uninstall process.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallNPM(String packageName, IProgressMonitor monitor) {
		return uninstallNPM(Arrays.asList(packageName), monitor);
	}

	/**
	 * Uninstalls the given npm packages in a blocking fashion.
	 *
	 * This method tries to uninstall all packages even if uninstalling for some of them fails. In such cases it will
	 * try to log encountered errors but it will try to proceed for all remaining packages. Details about issues are in
	 * the returned status.
	 *
	 * @param packageNames
	 *            the names of the packages that has to be uninstalled via package manager.
	 * @param monitor
	 *            the monitor for the blocking uninstall process.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallNPM(Collection<String> packageNames, IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> uninstallDependenciesInternal(packageNames, monitor));
	}

	private IStatus uninstallDependenciesInternal(Collection<String> packageNames, IProgressMonitor monitor) {
		String msg = "Uninstalling NPM(s): " + String.join(", ", packageNames);
		MultiStatus status = statusHelper.createMultiStatus(msg);
		logger.logInfo(msg);

		IStatus binaryStatus = checkNPM();
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (ClosableMeasurement mes = dcLibMngr.getClosableMeasurement("uninstallDependenciesInternal");) {

			List<LibraryChange> actualChanges = installUninstallNPMs(monitor, status, emptyMap(), packageNames);
			indexSynchronizer.synchronizeNpms(monitor, actualChanges);

			return status;

		} finally {
			monitor.done();
		}
	}

	/**
	 * Reloads the external libraries by re-indexing all external projects that not shadowed from projects in the
	 * workspace. Performs a {@code git pull} before the actual refresh process. Returns with an {@link IStatus status}
	 * representing the outcome of the refresh operation.
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus reloadAllExternalProjects(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> reloadAllExternalProjectsInternal(monitor));
	}

	private IStatus reloadAllExternalProjectsInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");

		MultiStatus refreshStatus = statusHelper.createMultiStatus("Refreshing npm type definitions.");

		Collection<String> packageNames = getAllNpmProjectsMapping().keySet();
		if (packageNames.isEmpty()) {
			return statusHelper.OK();
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, 10);
		try {

			refreshAllInstalledPackages(refreshStatus, packageNames, subMonitor.newChild(1));
			indexSynchronizer.reindexAllExternalProjects(subMonitor.newChild(9));

			return refreshStatus;

		} finally {
			subMonitor.done();
		}
	}

	private void refreshAllInstalledPackages(MultiStatus refreshStatus, Collection<String> packageNames,
			SubMonitor subMonitor) {

		logger.logInfo("Refreshing installed all external projects (including NPMs).");
		SubMonitor subsubMonitor = SubMonitor.convert(subMonitor, packageNames.size() + 1);
		subsubMonitor.setTaskName("Refreshing npm type definitions...");

		performGitPull(subsubMonitor.newChild(1, SubMonitor.SUPPRESS_ALL_LABELS));

		for (String packageName : packageNames) {
			IStatus status = refreshInstalledNpmPackage(packageName, subsubMonitor.newChild(1));
			if (!status.isOK()) {
				logger.logError(status);
				refreshStatus.merge(status);
			}
		}
	}

	private IStatus refreshInstalledNpmPackage(String packageName, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, 2);

		String taskName = "Refreshing npm type definitions for '" + packageName + "' ...";
		progress.setTaskName(taskName);

		try {

			URI uri = getAllNpmProjectsMapping().get(packageName);
			if (null == uri) {
				// No project with the given package name. Nothing to do.
				return statusHelper.OK();
			}

			File definitionsFolder = npmPackageToProjectAdapter.getNpmsTypeDefinitionsFolder(false);
			if (null == definitionsFolder) {
				// No definitions are available at the moment.
				return statusHelper.OK();
			}

			File packageRoot = new File(uri);

			IStatus status = npmPackageToProjectAdapter.addTypeDefinitions(
					packageRoot,
					definitionsFolder);

			if (!status.isOK()) {
				logger.logError(status);
			}

			return status;

		} finally {
			monitor.done();
		}
	}

	/**
	 * Cleans npm cache. Please note:
	 * <p>
	 * <i>"It should never be necessary to clear the cache for any reason other than reclaiming disk space"</i> (see
	 * <a href="https://docs.npmjs.com/cli/cache}">NPM Doc</a>)
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus cleanCache(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> npmCli.cleanCacheInternal(monitor));
	}

	/**
	 * Checks the npm binary.
	 */
	private IStatus checkNPM() {
		NpmBinary npmBinary = npmBinaryProvider.get();
		IStatus npmBinaryStatus = npmBinary.validate();
		if (!npmBinaryStatus.isOK()) {
			return statusHelper.createError("npm binary invalid",
					new IllegalBinaryStateException(npmBinary, npmBinaryStatus));
		}
		return statusHelper.OK();
	}

	/**
	 * A map of project (npm package) names to project location mappings.
	 */
	private Map<String, URI> getAllNpmProjectsMapping() {
		Map<String, URI> mappings = newHashMap();

		for (IProject project : externalLibraryWorkspace.getProjects()) {
			if (project.isAccessible() && project instanceof ExternalProject) {
				URI location = ((ExternalProject) project).getExternalResource().toURI();
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

	private void performGitPull(IProgressMonitor monitor) {
		URI repositoryLocation = locationProvider.getTargetPlatformLocalGitRepositoryLocation();
		GitUtils.pull(new File(repositoryLocation).toPath(), monitor);
	}
}
