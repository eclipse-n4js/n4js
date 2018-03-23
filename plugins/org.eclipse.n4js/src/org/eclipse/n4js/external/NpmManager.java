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
import static org.eclipse.core.runtime.SubMonitor.convert;
import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.io.IOException;
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.binaries.IllegalBinaryStateException;
import org.eclipse.n4js.binaries.nodejs.NpmBinary;
import org.eclipse.n4js.external.NodeModulesFolderListener.LibraryChange;
import org.eclipse.n4js.external.NodeModulesFolderListener.LibraryChangeType;
import org.eclipse.n4js.external.libraries.PackageJson;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.utils.ProcessExecutionCommandStatus;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Class for installing npm dependencies into the external library.
 */
@Singleton
public class NpmManager {

	private static String NO_VERSION = "";

	private static String LINE_DOUBLE = "================================================================";

	private static String LINE_SINGLE = "----------------------------------------------------------------";

	private static DataCollector dcLibMngr = DataCollectors.INSTANCE.getOrCreateDataCollector("Library Manager");

	@Inject
	private BinaryCommandFactory commandFactory;

	@Inject
	private NpmPackageToProjectAdapter npmPackageToProjectAdapter;

	@Inject
	private ProcessExecutionCommandStatus executor;

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
	private NodeModulesIndexSynchronizer indexSynchronizer;

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Installs the given npm package in a blocking fashion.
	 *
	 * @param packageName
	 *            the name of the package that has to be installed via package manager.
	 * @param monitor
	 *            the monitor for the blocking install process.
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installDependency(String packageName, IProgressMonitor monitor) {
		return installDependency(packageName, NO_VERSION, monitor);
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
	public IStatus installDependency(String packageName, String packageVersion, IProgressMonitor monitor) {
		return installDependencies(Collections.singletonMap(packageName, packageVersion), monitor);
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
	public IStatus installDependencies(Collection<String> unversionedPackages, IProgressMonitor monitor) {

		Map<String, String> versionedPackages = unversionedPackages.stream()
				.collect(Collectors.toMap((String name) -> name, (String name) -> NO_VERSION));
		return installDependencies(versionedPackages, monitor);
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
	public IStatus installDependencies(Map<String, String> versionedNPMs, IProgressMonitor monitor) {
		return installDependencies(versionedNPMs, monitor, true);
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
	 * @param triggerCleanbuild
	 *            if true, a clean build is triggered on all affected workspace projects.
	 * @return a status representing the outcome of the install process.
	 */
	public IStatus installDependencies(Map<String, String> versionedNPMs, IProgressMonitor monitor,
			boolean triggerCleanbuild) {
		return runWithWorkspaceLock(() -> installDependenciesInternal(versionedNPMs, monitor, triggerCleanbuild));
	}

	private IStatus installDependenciesInternal(Map<String, String> versionedNPMs, IProgressMonitor monitor,
			boolean triggerCleanbuild) {

		MultiStatus status = statusHelper.createMultiStatus("Status of installing multiple npm dependencies.");

		IStatus binaryStatus = checkNPM();
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (ClosableMeasurement mes = dcLibMngr.getClosableMeasurement("installDependenciesInternal");) {

			installUninstallNPMs(monitor, status, versionedNPMs, Collections.emptyList());
			synchronizeNpms(monitor, status, triggerCleanbuild);

			return status;

		} finally {
			monitor.done();
		}
	}

	private List<LibraryChange> identifyChangeSet() {
		List<LibraryChange> changes = new LinkedList<>();

		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> npmsOfIndex = findNpmsInIndex();
		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> npmsOfFolder = findNpmsInFolder();

		Set<String> differences = new HashSet<>();
		differences.addAll(npmsOfIndex.keySet());
		differences.addAll(npmsOfFolder.keySet());
		SetView<String> intersection = Sets.intersection(npmsOfIndex.keySet(), npmsOfFolder.keySet());
		differences.removeAll(intersection);

		for (String diff : differences) {
			LibraryChangeType changeType = null;
			String name = diff;
			org.eclipse.emf.common.util.URI location = null;
			String version = null;

			if (npmsOfFolder.containsKey(diff)) {
				// new in folder, not in index
				changeType = LibraryChangeType.Added;
				location = npmsOfFolder.get(name).getKey();
				version = npmsOfFolder.get(name).getValue();
			}
			if (npmsOfIndex.containsKey(diff)) {
				// removed in folder, still in index
				changeType = LibraryChangeType.Removed;
				location = npmsOfIndex.get(name).getKey();
				version = npmsOfIndex.get(name).getValue();
			}
			changes.add(new LibraryChange(changeType, location, name, version));
		}

		for (String name : npmsOfIndex.keySet()) {
			if (npmsOfFolder.containsKey(name)) {
				String versionIndex = npmsOfIndex.get(name).getValue();
				String versionFolder = npmsOfFolder.get(name).getValue();
				org.eclipse.emf.common.util.URI locationIndex = npmsOfIndex.get(name).getKey();
				org.eclipse.emf.common.util.URI locationFolder = npmsOfFolder.get(name).getKey();

				Preconditions.checkState(locationFolder.equals(locationIndex));

				if (versionIndex != null && !versionIndex.equals(versionFolder)) {
					changes.add(new LibraryChange(LibraryChangeType.Updated, locationFolder, name, versionFolder));
				}
			}
		}

		return changes;
	}

	private Map<String, Pair<org.eclipse.emf.common.util.URI, String>> findNpmsInIndex() {
		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> npmsIndex = new HashMap<>();

		String nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation().toString();
		ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.absent());
		IResourceDescriptions index = n4jsCore.getXtextIndex(resourceSet);

		for (IResourceDescription res : index.getAllResourceDescriptions()) {
			String resLocation = res.getURI().toString();
			boolean isNPM = resLocation.startsWith(nodeModulesLocation);

			if (isNPM) {
				addToIndex(npmsIndex, nodeModulesLocation, res, resLocation);
			}
		}

		return npmsIndex;
	}

	private void addToIndex(Map<String, Pair<org.eclipse.emf.common.util.URI, String>> npmsIndex,
			String nodeModulesLocation, IResourceDescription res, String resLocation) {

		String version = "";
		int endOfProjectFolder = resLocation.indexOf(File.separator, nodeModulesLocation.length() + 1);
		String locationString = resLocation.substring(0, endOfProjectFolder);
		org.eclipse.emf.common.util.URI location = org.eclipse.emf.common.util.URI
				.createURI(locationString);
		String name = locationString.substring(nodeModulesLocation.length());

		boolean isManifest = true;
		isManifest &= resLocation.endsWith(IN4JSProject.N4MF_MANIFEST);
		isManifest &= resLocation.substring(resLocation.length()).split(File.separator).length == 1;
		if (isManifest) {
			Iterable<IEObjectDescription> pds = res
					.getExportedObjectsByType(N4mfPackage.Literals.PROJECT_DESCRIPTION);

			IEObjectDescription pDescription = pds.iterator().next();
			String nameFromManifest = pDescription.getUserData(N4MFResourceDescriptionStrategy.PROJECT_ID_KEY);
			Preconditions.checkState(name.equals(nameFromManifest));

			version = pDescription.getUserData(N4MFResourceDescriptionStrategy.PROJECT_VERSION_KEY);
		}

		// continue here: consider every entry, not only manifest files. assume corrupt index!
		// continue on workspace: some problem when uninstalling react, because it finds react-intl in the
		// index! strange!

		if (!npmsIndex.containsKey(name) || isManifest) {
			npmsIndex.put(name, Pair.of(location, version));
		}
	}

	private Map<String, Pair<org.eclipse.emf.common.util.URI, String>> findNpmsInFolder() {
		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> npmsFolder = new HashMap<>();

		URI nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation();
		File nodeModulesFolder = new File(nodeModulesLocation.getPath());
		if (nodeModulesFolder.exists() && nodeModulesFolder.isDirectory()) {
			for (File npmLibrary : nodeModulesFolder.listFiles()) {
				if (npmLibrary.exists() && npmLibrary.isDirectory()) {
					String npmName = npmLibrary.getName();
					File manifest = npmLibrary.toPath().resolve(IN4JSProject.N4MF_MANIFEST).toFile();
					String version = getVersionFromManifest(manifest);
					if (version != null) {
						String path = npmLibrary.getAbsolutePath();
						org.eclipse.emf.common.util.URI location = org.eclipse.emf.common.util.URI.createFileURI(path);
						npmsFolder.put(npmName, Pair.of(location, version));
					}
				}
			}
		}

		return npmsFolder;
	}

	private String getVersionFromManifest(File manifest) {
		ProjectDescription pDescr = getProjectDescription(manifest);
		if (pDescr != null) {
			DeclaredVersion pV = pDescr.getProjectVersion();
			String version = pV.getMajor() + "." + pV.getMinor() + "." + pV.getMicro();
			if (pV.getQualifier() != null) {
				version += ":" + pV.getQualifier();
			}
			return version;
		}

		return null;
	}

	private ProjectDescription getProjectDescription(File manifest) {
		if (!manifest.exists() || !manifest.isFile()) {
			return null;
		}

		ResourceSet resourceSet = n4jsCore.createResourceSet(Optional.absent());
		String pathStr = manifest.getPath();
		org.eclipse.emf.common.util.URI manifestURI = org.eclipse.emf.common.util.URI.createFileURI(pathStr);
		Resource resource = resourceSet.getResource(manifestURI, true);
		if (resource != null) {
			List<EObject> contents = resource.getContents();
			if (contents.isEmpty() || !(contents.get(0) instanceof ProjectDescription)) {
				return null;
			}
			ProjectDescription pDescr = (ProjectDescription) contents.get(0);
			return pDescr;
		}
		return null;
	}

	private void installUninstallNPMs(IProgressMonitor monitor, MultiStatus status,
			Map<String, String> installRequested, Collection<String> removeRequested) {

		monitor.setTaskName("Installing packages... [step 1 of 4]");

		Map<String, Pair<org.eclipse.emf.common.util.URI, String>> installedNpms = findNpmsInFolder();
		List<String> toBeInstalled = new LinkedList<>();
		List<String> toBeRemoved = new LinkedList<>();

		for (Map.Entry<String, String> reqestedNpm : installRequested.entrySet()) {
			String name = reqestedNpm.getKey();
			String versionRequested = Strings.emptyIfNull(reqestedNpm.getValue());
			if (installedNpms.containsKey(name)) {
				String versionInstalled = installedNpms.get(name).getValue();
				if (versionRequested.equals(Strings.emptyIfNull(versionInstalled))) {
					// already installed
				} else {
					// wrong version installed -> update (uninstall, then install)
					toBeRemoved.add(name);
				}
			} else {
				String toInstallString = name + versionRequested;
				toBeInstalled.add(toInstallString);
			}
		}

		for (String name : removeRequested) {
			if (installedNpms.containsKey(name)) {
				toBeRemoved.add(name);
			} else {
				// already removed
			}
		}

		// remove
		IStatus removeStatus = npmCli.batchInstallUninstall(monitor, toBeRemoved, null, false);
		if (!removeStatus.isOK()) {
			logger.logInfo("Some packages could not be removed due to errors, see log for details.");
			status.merge(removeStatus);
		}
		monitor.worked(1);

		// install
		IStatus installStatus = npmCli.batchInstallUninstall(monitor, toBeInstalled, installRequested, true);
		if (!installStatus.isOK()) {
			logger.logInfo("Some packages could not be installed due to errors, see log for details.");
			status.merge(installStatus);
		}
		monitor.worked(1);

		// adapt installed
		adaptNPMPackages(monitor, status, toBeInstalled);
		monitor.worked(1);
	}

	private Collection<File> adaptNPMPackages(IProgressMonitor monitor, MultiStatus status,
			Collection<String> addedDependencies) {

		monitor.setTaskName("Adapting npm package structure to N4JS project structure... [step 3 of 4]");
		org.eclipse.xtext.util.Pair<IStatus, Collection<File>> result = npmPackageToProjectAdapter
				.adaptPackages(addedDependencies);

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
	 * @param triggerWorkspace
	 *            if true affected workspace projects are rebuild.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallDependency(String packageName, IProgressMonitor monitor, boolean triggerWorkspace) {
		return uninstallDependencies(Arrays.asList(packageName), monitor, triggerWorkspace);
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
	 * @param triggerWorkspace
	 *            if true affected workspace projects are rebuild.
	 * @return a status representing the outcome of the uninstall process.
	 */
	public IStatus uninstallDependencies(Collection<String> packageNames, IProgressMonitor monitor,
			boolean triggerWorkspace) {
		return runWithWorkspaceLock(() -> uninstallDependenciesInternal(packageNames, monitor, triggerWorkspace));
	}

	private IStatus uninstallDependenciesInternal(Collection<String> packageNames, IProgressMonitor monitor,
			boolean triggerWorkspace) {
		MultiStatus status = statusHelper.createMultiStatus("Status of uninstalling multiple npm dependencies.");

		IStatus binaryStatus = checkNPM();
		if (!binaryStatus.isOK()) {
			status.merge(binaryStatus);
			return status;
		}

		try (ClosableMeasurement mes = dcLibMngr.getClosableMeasurement("uninstallDependenciesInternal");) {

			installUninstallNPMs(monitor, status, Collections.emptyMap(), packageNames);
			synchronizeNpms(monitor, status, triggerWorkspace);

			return status;

		} finally {
			monitor.done();
		}
	}

	private void synchronizeNpms(IProgressMonitor monitor, MultiStatus status, boolean triggerCleanbuild) {
		MultiStatus statusSync = statusHelper.createMultiStatus("Status of synchronizing npm dependencies.");
		SubMonitor subMonitor = convert(monitor, 1);

		try (ClosableMeasurement mes = dcLibMngr.getClosableMeasurement("synchronizeDependenciesInternal");) {

			List<LibraryChange> changeSet = identifyChangeSet();
			indexSynchronizer.cleanOutdatedIndex(subMonitor, status, changeSet);
			externalLibraryWorkspace.updateState();
			indexSynchronizer.synchronizeIndex(subMonitor, status, changeSet, triggerCleanbuild);

		} finally {
			subMonitor.done();
			status.merge(statusSync);
		}
	}

	/**
	 * Refreshes the type definitions for all installed, available {@code npm} packages in the external workspace.
	 * Performs a {@code git pull} before the actual refresh process. Returns with an {@link IStatus status}
	 * representing the outcome of the refresh operation.
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus refreshInstalledNpmPackages(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> refreshInstalledNpmPackagesInternal(monitor));
	}

	private IStatus refreshInstalledNpmPackagesInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");

		Collection<String> packageNames = getAllNpmProjectsMapping().keySet();

		if (packageNames.isEmpty()) {
			return statusHelper.OK();
		}

		SubMonitor subMonitor = SubMonitor.convert(monitor, packageNames.size() + 1);
		try {

			logger.logInfo(LINE_DOUBLE);
			logger.logInfo("Refreshing installed npm packages.");
			subMonitor.setTaskName("Refreshing cache for type definitions files...");

			performGitPull(subMonitor.newChild(1, SubMonitor.SUPPRESS_ALL_LABELS));

			MultiStatus refreshStatus = statusHelper
					.createMultiStatus("Status of refreshing definitions for npm packages.");
			for (String packageName : packageNames) {
				IStatus status = refreshInstalledNpmPackage(packageName, false, subMonitor.newChild(1));
				if (!status.isOK()) {
					logger.logError(status);
					refreshStatus.merge(status);
				}
			}
			logger.logInfo("Installed npm packages have been refreshed.");
			logger.logInfo(LINE_DOUBLE);
			return refreshStatus;

		} finally {
			subMonitor.done();
		}
	}

	/**
	 * Refreshes the type definitions for all installed, available {@code npm} packages in the external workspace.
	 * Performs a {@code git pull} before the actual refresh process. Returns with an {@link IStatus status}
	 * representing the outcome of the refresh operation.
	 *
	 * @param monitor
	 *            the monitor for the progress.
	 * @return a status representing the outcome of the operation.
	 */
	public IStatus cleanCache(IProgressMonitor monitor) {
		return runWithWorkspaceLock(() -> cleanCacheInternal(monitor));
	}

	private IStatus cleanCacheInternal(IProgressMonitor monitor) {
		checkNotNull(monitor, "monitor");

		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		try {

			subMonitor.setTaskName("Cleaning npm cache");

			performGitPull(subMonitor.newChild(1, SubMonitor.SUPPRESS_ALL_LABELS));
			File targetInstallLocation = new File(locationProvider.getTargetPlatformInstallLocation());
			return clean(targetInstallLocation);

		} finally {
			subMonitor.done();
		}
	}

	private IStatus refreshInstalledNpmPackage(String packageName, boolean performGitPull,
			IProgressMonitor monitor) {

		SubMonitor progress = SubMonitor.convert(monitor, 2);

		logger.logInfo(LINE_SINGLE);
		String taskName = "Refreshing type definitions for '" + packageName + "' npm package...";
		logger.logInfo(taskName);
		progress.setTaskName(taskName);

		try {

			URI uri = getAllNpmProjectsMapping().get(packageName);
			if (null == uri) {
				// No project with the given package name. Nothing to do.
				return statusHelper.OK();
			}

			File definitionsFolder = npmPackageToProjectAdapter.getNpmsTypeDefinitionsFolder(performGitPull);
			if (null == definitionsFolder) {
				// No definitions are available at the moment.
				return statusHelper.OK();
			}

			if (performGitPull) {
				performGitPull(progress.newChild(1));
			}

			File packageRoot = new File(uri);
			PackageJson packageJson = npmPackageToProjectAdapter.getPackageJson(packageRoot);
			File manifest = new File(packageRoot, N4MF_MANIFEST);
			if (!manifest.isFile()) {
				String message = "Cannot locate N4JS manifest for '" + packageName + "' package at '" + manifest + "'.";
				IStatus error = statusHelper.createError(message);
				logger.logError(error);
			}

			IStatus status = npmPackageToProjectAdapter.addTypeDefinitions(
					packageRoot,
					packageJson,
					manifest,
					definitionsFolder);

			if (status.isOK()) {
				logger.logInfo("Successfully refreshed the type definitions for '" + packageName + "' npm package.");
				logger.logInfo(LINE_SINGLE);
			} else {
				logger.logError(status);
			}

			return status;

		} catch (IOException e) {
			String message = "Error while refreshing the definitions for '" + packageName + "' npm package.";
			IStatus error = statusHelper.createError(message, e);
			logger.logError(error);
			return error;
		} finally {
			monitor.done();
		}
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
	 * Cleans npm cache. Note that normally this has global side effects, i.e. it will delete npm cache for the user
	 * settings. While provided path does not have impact on effects of clean, it is used as working directory for
	 * invoking the command.
	 *
	 * @param cleanPath
	 *            to be uninstalled
	 */
	private IStatus clean(File cleanPath) {
		return executor.execute(
				() -> commandFactory.createCacheCleanCommand(cleanPath),
				"Error while cleaning npm cache.");
	}

	/**
	 * A map of project (npm package) names to project location mappings.
	 */
	private Map<String, URI> getAllNpmProjectsMapping() {
		URI nodeModulesLocation = locationProvider.getTargetPlatformNodeModulesLocation();
		Map<String, URI> mappings = newHashMap();

		// Intentionally might include projects that are already in the workspace
		for (IProject project : externalLibraryWorkspace.getProjectsIn(nodeModulesLocation)) {
			if (project.isAccessible() && project instanceof ExternalProject) {
				URI location = ((ExternalProject) project).getExternalResource().toURI();
				mappings.put(project.getName(), location);
			}
		}

		return ImmutableMap.copyOf(mappings);
	}

	private void performGitPull(IProgressMonitor monitor) {
		URI repositoryLocation = locationProvider.getTargetPlatformLocalGitRepositoryLocation();
		GitUtils.pull(new File(repositoryLocation).toPath(), monitor);
	}

	private static <T> T runWithWorkspaceLock(Supplier<T> operation) {
		if (Platform.isRunning()) {
			ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRoot();
			try {
				Job.getJobManager().beginRule(rule, null);
				return operation.get();
			} finally {
				Job.getJobManager().endRule(rule);
			}
		} else {
			// locking not available/required in headless case
			return operation.get();
		}
	}

}
