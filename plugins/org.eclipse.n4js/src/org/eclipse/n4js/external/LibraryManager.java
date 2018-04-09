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
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.external.libraries.PackageJson;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.utils.StatusHelper;
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

			List<LibraryChange> actualChanges = installUninstallNPMs(monitor, status, versionedNPMs, emptyList());
			indexSynchronizer.synchronizeNpms(monitor, actualChanges);

			return status;

		} finally {
			monitor.done();
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
			String versionRequested = Strings.emptyIfNull(reqestedNpm.getValue());
			if (installedNpms.containsKey(name)) {
				org.eclipse.emf.common.util.URI location = installedNpms.get(name).getKey();
				String versionInstalled = installedNpms.get(name).getValue();
				if (versionRequested.equals(Strings.emptyIfNull(versionInstalled))) {
					// already installed
				} else {
					// wrong version installed -> update (uninstall, then install)
					LibraryChangeType uninstall = LibraryChangeType.Uninstall;
					requestedChanges.add(new LibraryChange(uninstall, location, name, versionRequested));
				}
			} else {
				requestedChanges.add(new LibraryChange(LibraryChangeType.Install, null, name, versionRequested));
			}
		}

		for (String name : removeRequested) {
			if (installedNpms.containsKey(name)) {
				requestedChanges.add(new LibraryChange(LibraryChangeType.Uninstall, null, name, ""));
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
		org.eclipse.xtext.util.Pair<IStatus, Collection<File>> result = npmPackageToProjectAdapter
				.adaptPackages(installedNpmNames);

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

		SubMonitor subMonitor = SubMonitor.convert(monitor, packageNames.size() + 1);
		try {

			logger.logInfo("Refreshing installed all external projects (including NPMs).");
			subMonitor.setTaskName("Refreshing npm type definitions...");

			performGitPull(subMonitor.newChild(1, SubMonitor.SUPPRESS_ALL_LABELS));

			for (String packageName : packageNames) {
				IStatus status = refreshInstalledNpmPackage(packageName, subMonitor.newChild(1));
				if (!status.isOK()) {
					logger.logError(status);
					refreshStatus.merge(status);
				}
			}

			indexSynchronizer.reindexAllExternalProjects(subMonitor.newChild(1));

			return refreshStatus;

		} finally {
			subMonitor.done();
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
			PackageJson packageJson = npmPackageToProjectAdapter.getPackageJson(packageRoot);
			File manifest = new File(packageRoot, N4MF_MANIFEST);
			if (!manifest.isFile()) {
				String message = "Cannot locate N4JS manifest for '" + packageName + "' at '" + manifest + "'.";
				IStatus error = statusHelper.createError(message);
				logger.logError(error);
			}

			IStatus status = npmPackageToProjectAdapter.addTypeDefinitions(
					packageRoot,
					packageJson,
					manifest,
					definitionsFolder);

			if (!status.isOK()) {
				logger.logError(status);
			}

			return status;

		} catch (IOException e) {
			String message = "Error while refreshing npm type definitions for '" + packageName + "'.";
			IStatus error = statusHelper.createError(message, e);
			logger.logError(error);
			return error;
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
				return statusHelper.createInfo("User cancelled operation.");
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
