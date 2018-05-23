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

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.external.libraries.PackageJson.PACKAGE_JSON;
import static org.eclipse.n4js.n4mf.utils.N4MFConstants.N4MF_MANIFEST;
import static org.eclipse.xtext.util.Tuples.pair;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.binaries.BinaryCommandFactory;
import org.eclipse.n4js.external.libraries.PackageJson;
import org.eclipse.n4js.external.libraries.TargetPlatformFactory;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.resource.ManifestMerger;
import org.eclipse.n4js.n4mf.utils.N4MFConstants;
import org.eclipse.n4js.utils.LightweightException;
import org.eclipse.n4js.utils.OSInfo;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.Version;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.git.GitUtils;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.process.ProcessResult;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Adapts given npm package to n4js project form
 */
public class NpmPackageToProjectAdapter {

	private static Logger LOGGER = Logger.getLogger(NpmPackageToProjectAdapter.class);

	@Inject
	private NpmLogger logger;

	@Inject
	private N4JSNpmManifestContentProvider manifestContentProvider;

	@Inject
	private StatusHelper statusHelper;

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private ManifestMerger manifestMerger;

	@Inject
	private BinaryCommandFactory commandFactory;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private GitCloneSupplier gitCloneSupplier;

	/** Default filter for manifest fragments */
	private final static FileFilter ONLY_MANIFEST_FRAGMENTS = new FileFilter() {
		private final static String MANIFEST_FRAGMENT = "manifest.fragment";

		@Override
		public boolean accept(File pathname) {
			return pathname.toPath().endsWith(MANIFEST_FRAGMENT);
		}
	};

	/** Default filter for copying N4JSD project contents during adaptation */
	private final static Predicate<Path> COPY_N4JSD_PREDICATE = new Predicate<Path>() {
		private final static String SUFFIX = "." + N4JSGlobals.N4JSD_FILE_EXTENSION;

		@Override
		public boolean apply(Path path) {
			Path fileName = path.getFileName();
			return fileName != null && fileName.toString().endsWith(SUFFIX);
		}
	};

	/**
	 * Adapts npm packages in provided folder to the N4JS project structure. Only package folders which match requested
	 * packages are adapted. It is expected that npm flattens packages structures, therefore it is assumed that other
	 * folders are dependencies (also transitive) of the requested packages.
	 *
	 * Requested npm packages already look like N4JS projects (i.e. have N4MF manifest file), those packages are not
	 * adapted (proper structure is assumed), but they will be returned in to the caller to allow further processing
	 * (i.e. passing them to the builder).
	 *
	 * Returned set of N4JS project folders will not include those installed by the npm but without matching names in
	 * provided set of expected packages. Those packages are treated as transitive dependencies and are not return to
	 * the caller.
	 *
	 * @param namesOfPackagesToAdapt
	 *            names of the expected packages
	 * @return pair of overall adaptation status and folders of successfully adapted npm packages
	 */
	public Pair<IStatus, Collection<File>> adaptPackages(Collection<String> namesOfPackagesToAdapt) {
		final MultiStatus status = statusHelper.createMultiStatus("Status of adapting npm packages");
		final Collection<File> adaptedProjects = newHashSet();
		final File nodeModulesFolder = new File(installLocationProvider.getTargetPlatformNodeModulesLocation());
		final Collection<String> names = newHashSet(namesOfPackagesToAdapt);
		final File[] packageRoots = nodeModulesFolder.listFiles(packageName -> names.contains(packageName.getName()));
		final File n4jsdsFolder = getNpmsTypeDefinitionsFolder();

		for (File packageRoot : packageRoots) {
			try {
				PackageJson packageJson = getPackageJson(packageRoot);

				final File manifest = new File(packageRoot, N4MF_MANIFEST);
				// looks like n4js project skip adaptation
				if (manifest.exists() && manifest.isFile()) {
					if (!names.remove(packageRoot.getName())) {
						throw new IOException("Unexpected error occurred while adapting '"
								+ packageRoot.getName() + "' npm package into N4JS format.");
					}
					adaptedProjects.add(packageRoot);
				} else {

					if (manifest.isDirectory()) {
						throw new IOException("The manifest location is occupied by the folder '" + manifest + "'.");
					}

					manifest.createNewFile();

					try {
						String mainModule = computeMainModule(packageRoot);
						generateManifestContent(packageRoot, packageJson, mainModule, manifest);
						if (!names.remove(packageRoot.getName())) {
							throw new IOException("Unexpected error occurred while adapting '" + packageRoot.getName()
									+ "' npm package into N4JS format.");
						}
						adaptedProjects.add(packageRoot);
					} catch (final Exception e) {
						try {
							FileDeleter.delete(manifest);
						} catch (IOException ioe) {
							// Intentionally swallowed to get the original cause.
							LOGGER.error("Error while trying to clean up corrupted " + manifest + " file.", e);
						}
						throw e;
					}
				}

				if (n4jsdsFolder != null && adaptedProjects.contains(packageRoot)) {
					addTypeDefinitions(packageRoot, packageJson, manifest, n4jsdsFolder);
				}
			} catch (final Exception e) {
				status.merge(
						statusHelper.createError("Unexpected error occurred while adapting '" + packageRoot.getName()
								+ "' npm package into N4JS format.", e));
			}
		}

		return pair(status, adaptedProjects);

	}

	/**
	 * Reads, parses and returns with the content of the {@code package.json} file as a POJO for the given npm package
	 * root location.
	 *
	 * @param packageRoot
	 *            the root location of the npm package.
	 *
	 * @return the POJO instance that represents the read, parsed content of the {@code package.json} file.
	 *
	 * @throws IOException
	 *             if {@code package.json} file does not exists, hence the content cannot be read.
	 */
	PackageJson getPackageJson(File packageRoot) throws IOException {

		final File packageJsonResource = new File(packageRoot, PACKAGE_JSON);
		if (!packageJsonResource.exists() || !packageJsonResource.isFile()) {
			throw new IOException("Cannot read package.json content for package '" + packageJsonResource.getName()
					+ "' at '" + packageJsonResource + "'.");
		}
		return PackageJson.readValue(packageJsonResource.toURI());
	}

	private static String NPM_DEFINITIONS_FOLDER_NAME = "npm";

	private File getNpmsTypeDefinitionsFolder() {
		return getNpmsTypeDefinitionsFolder(true);
	}

	/**
	 * Returns with the root folder of all available npm package definitions. Or returns with {@code null} if no
	 * definitions are available. Also performs an on demand {@code git pull}.
	 *
	 * @param performGitPull
	 *            {@code true} if a git pull has to be performed in the local clone.
	 *
	 * @return the root folder of all npm package definitions or {@code null} if missing.
	 */
	File getNpmsTypeDefinitionsFolder(final boolean performGitPull) {

		File repositoryLocation = new File(installLocationProvider.getTargetPlatformLocalGitRepositoryLocation());

		if (performGitPull && gitCloneSupplier.remoteRepoAvailable()) {
			// pull changes
			GitUtils.pull(repositoryLocation.toPath());
		}

		final File definitionsRoot = new File(repositoryLocation, NPM_DEFINITIONS_FOLDER_NAME);
		if (definitionsRoot.exists() && definitionsRoot.isDirectory()) {
			return definitionsRoot;
		} else {
			String msg = "Cannot locate local git repository clone for N4JS definition files: " + definitionsRoot + ".";
			LOGGER.error(msg);
			return null;
		}
	}

	/**
	 * Add type definitions (N4JSDs) to the npm package. Types are added only if matching version is found.
	 *
	 * This method suppresses any potential issues as adding type definitions to some npm package does not affect
	 * overall npm usage. Still, errors are {@link #LOGGER logged} to help troubleshooting potential issues and returns
	 * with an {@link IStatus status} instance that represents the problem if any.
	 *
	 * @param packageRoot
	 *            npm package folder.
	 * @param packageJson
	 *            {@link TargetPlatformFactory package.json} of that package.
	 * @param manifest
	 *            file that will be adjusted according to manifest fragments.
	 * @param definitionsFolder
	 *            root folder for npm type definitions.
	 *
	 * @return a status representing the outcome of performed the operation.
	 */
	IStatus addTypeDefinitions(File packageRoot, PackageJson packageJson, File manifest,
			File definitionsFolder) {

		String packageName = packageRoot.getName();
		File packageN4JSDsRoot = new File(definitionsFolder, packageName);
		if (!(packageN4JSDsRoot.exists() && packageN4JSDsRoot.isDirectory())) {
			LOGGER.info("No type definitions found for '" + packageRoot + "' npm package at '" + packageN4JSDsRoot + "'"
					+ (!packageN4JSDsRoot.isDirectory() ? " (which is not a directory)" : "") + ".");
			return statusHelper.OK();
		}

		String packageJsonVersion = packageJson.version;
		Version packageVersion = Version.createFromString(packageJsonVersion);
		String[] list = packageN4JSDsRoot.list();
		Set<Version> availableTypeDefinitionsVersions = new HashSet<>();
		for (int i = 0; i < list.length; i++) {
			String version = list[i];
			Version availableTypeDefinitionsVersion = Version.createFromString(version);
			if (!Version.MISSING.equals(availableTypeDefinitionsVersion)) {
				availableTypeDefinitionsVersions.add(availableTypeDefinitionsVersion);
			}
		}

		Version closestMatchingVersion = Version.findClosestMatching(availableTypeDefinitionsVersions, packageVersion);
		if (Version.MISSING.equals(closestMatchingVersion)) {
			String details = "";
			if (availableTypeDefinitionsVersions.isEmpty()) {
				details = " Cannot find any type definitions for  '" + packageName + "'.";
			} else if (1 == availableTypeDefinitionsVersions.size()) {
				final Version head = availableTypeDefinitionsVersions.iterator().next();
				details = " Type definitions are available only in version : " + head + ".";
			} else {
				final String versions = Iterables.toString(availableTypeDefinitionsVersions);
				details = " Type definitions are available only in versions : " + versions + ".";
			}
			logger.logInfo("Type definitions for '" + packageName + "' npm package in version " + packageVersion
					+ " are not available." + details);
			return statusHelper.OK();
		}

		if (!(definitionsFolder.exists() && definitionsFolder.isDirectory())) {
			final String message = "Cannot find type definitions folder for '" + packageName
					+ "' npm package for version '" + closestMatchingVersion + "'.";
			LOGGER.error(message);
			return statusHelper.createError(message);
		}

		File packageVersionedN4JSDProjectRoot = new File(packageN4JSDsRoot, closestMatchingVersion.toString());
		try {
			Path sourcePath = packageVersionedN4JSDProjectRoot.toPath();
			Path targetPath = packageRoot.toPath();

			FileCopier.copy(sourcePath, targetPath, COPY_N4JSD_PREDICATE);
		} catch (IOException e) {
			final String message = "Error while trying to update type definitions content for '" + packageName
					+ "' npm package.";
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}

		// adjust manifest according to type definitions manifest fragments
		try {
			File[] manifestFragments = prepareManifestFragments(packageVersionedN4JSDProjectRoot, packageRoot);
			return adjustManifest(manifest, manifestFragments);
		} catch (IOException e) {
			final String message = "Error while trying to prepare manifest fragments for '" + packageName
					+ "' npm package.";
			LOGGER.error(message);
			return statusHelper.createError(message, e);
		}
	}

	/**
	 * Take the manifest.fragment file from the N4JSD project root folder and copy it to fragment.n4mf in the NPM
	 * package root folder.
	 *
	 * @param n4jsdRoot
	 *            The N4JSD project root folder.
	 * @param packageRoot
	 *            The NPM package root folder.
	 * @return An array containing the full absolute path to the fragment.n4mf file.
	 * @throws IOException
	 *             if an error occurs while copying the file
	 */
	private File[] prepareManifestFragments(File n4jsdRoot, File packageRoot)
			throws IOException {
		File[] sourceFragments = n4jsdRoot.listFiles(ONLY_MANIFEST_FRAGMENTS);
		if (sourceFragments.length > 0) {
			File sourceFile = sourceFragments[0];
			File targetFile = new File(packageRoot, N4MFConstants.MANIFEST_FRAGMENT);
			FileCopier.copy(sourceFile.toPath(), targetFile.toPath());
			return new File[] { targetFile };
		}

		return new File[] {};
	}

	/**
	 * Adjust manifests based on provided manifest fragments.
	 *
	 * @param manifest
	 *            file to be adjusted
	 * @param manifestFragments
	 *            that will be used to adjust the manifest
	 */
	private IStatus adjustManifest(final File manifest, final File... manifestFragments) {

		if (Arrays2.isEmpty(manifestFragments)) {
			// Nothing to merge.
			return statusHelper.OK();
		}

		final URI manifestURI = URI.createFileURI(manifest.getAbsolutePath());

		ProjectDescription pd = null;
		for (int i = 0; i < manifestFragments.length; i++) {
			File fragment = manifestFragments[i];
			if (fragment.exists() && fragment.isFile()) {
				URI manifestFragmentURI = URI.createFileURI(fragment.getAbsolutePath());
				pd = manifestMerger.mergeContent(manifestFragmentURI, manifestURI);
				fragment.delete();
			} else {
				LOGGER.warn("Broken manifest fragment: " + fragment + ".");
			}
		}

		if (pd != null) {
			ResourceSet resourceSet = resourceSetProvider.get();
			Resource resource = resourceSet.getResource(manifestURI, true);
			List<EObject> contents = resource.getContents();
			contents.clear();
			contents.add(pd);
			try {
				resource.save(null);
				return statusHelper.OK();
			} catch (IOException e) {
				final String message = "Error while trying to write N4JS manifest content for: " + manifestURI + ".";
				LOGGER.error(message, e);
				return statusHelper.createError(message, e);
			}
		} else {
			final String message = "Failed to merge N4JS manifest fragments into '" + manifestURI + "'.";
			LOGGER.error(message);
			return statusHelper.createError(message);
		}
	}

	/**
	 * Writes contents of the {@link N4MFConstants#N4MF_MANIFEST manifest file} for a given npm package.
	 *
	 * @param projectFolder
	 *            root folder of the npm package in which manifest is written
	 * @param packageJSON
	 *            that will be used as manifest data source
	 * @param mainModule
	 *            the main module
	 * @param manifest
	 *            file to which contents should be written
	 *
	 */
	private void generateManifestContent(File projectFolder, PackageJson packageJSON, String mainModule,
			File manifest)
			throws IOException {

		String projectId = packageJSON.name;
		String projectVersion = packageJSON.version;

		if (!projectFolder.getName().equals(projectId)) {
			LOGGER.warn("project folder and project name are different : " + projectFolder.getName() + " <> + "
					+ packageJSON.name);
		}

		try (FileWriter fw = new FileWriter(manifest)) {
			fw.write(manifestContentProvider.getContent(projectId, ".", ".", mainModule, projectVersion));
		}
	}

	String computeMainModule(File projectFolder) {

		String mainModule = resolveMainModule(projectFolder);

		if (Strings.isNullOrEmpty(mainModule))
			return mainModule;

		File main = new File(mainModule);

		if (!main.isFile()) {
			throw Exceptions
					.sneakyThrow(new LightweightException("Cannot locate main module with path " + main.toString()));
		}

		Path packagePath = projectFolder.toPath();
		Path packageMainModulePath = main.toPath();

		Path mainmoduleRelative = packagePath.relativize(packageMainModulePath);

		String mainSpecifier = mainmoduleRelative.toString();

		// strip extension
		int dotIndex = mainSpecifier.lastIndexOf('.');
		String ext = (dotIndex == -1) ? "" : mainSpecifier.substring(dotIndex);
		mainSpecifier = mainSpecifier.substring(0, (mainSpecifier.length() - ext.length()));

		// replace windows path separators
		if (OSInfo.isWindows())
			mainSpecifier = mainSpecifier.replace(File.separator, "/");

		// strip relative start part
		if (mainSpecifier.startsWith("./"))
			mainSpecifier = mainSpecifier.substring(2);

		return mainSpecifier;
	}

	/**
	 * Calls node process to resolve main module of the provided npm package.
	 *
	 * @param packageRoot
	 *            package root folder
	 * @return string with absolute path to the package main module
	 */
	private String resolveMainModule(File packageRoot) {

		ProcessResult per = commandFactory.createResolveMainModuleCommand(packageRoot).execute();

		if (per.isOK()) {
			// happy case string with full path to the main module (terminated with line ending)
			return per.getStdOut().trim();
		} else {
			// unhappy case, maybe package is broken, maybe it is library with no single facade.
			LOGGER.warn(
					"Cannot resolve npm package main module, generated project will NOT have MainModule compatible with module import.");
			return null;
		}
	}

}
