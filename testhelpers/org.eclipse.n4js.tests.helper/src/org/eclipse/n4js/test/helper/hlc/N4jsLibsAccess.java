/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.test.helper.hlc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.utils.io.FileCopier;

import com.google.common.base.Charsets;
import com.google.common.base.Predicates;

/**
 * Provides access to the projects located in the N4JS Git repository under top-level folder "n4js-libs". Assumes that
 * the code invoking the methods in this class is run from source, i.e. that the entire contents of the N4JS Git
 * repository are located on disk uncompressed. Only intended for testing purposes, obviously.
 */
public class N4jsLibsAccess {

	// FIXME GH-1281 disable use of symbolic links to see if this causes test failures that occur only on Jenkins
	public static boolean SYMBOLIC_LINKS_DISABLED = true;

	/** Name of the N4JS Git repository. */
	private static final String N4JS_REPO_NAME = "n4js";
	/** Name of the top-level folder containing test helpers inside the N4JS Git repository. */
	private static final String TEST_HELPERS_NAME = "testhelpers";
	/** Name of the top-level folder containing the "n4js-libs" inside the N4JS Git repository. */
	private static final String N4JS_LIBS_NAME = "n4js-libs";

	/**
	 * Returns the absolute path to the location of the "n4js-libs" in the N4JS Git repository clone on the local file
	 * system. This will be the "packages" subfolder inside the top-level folder "n4js-libs" in the N4JS repository.
	 *
	 * @throws IllegalStateException
	 *             if that path cannot be obtained.
	 */
	public static Path findN4jsLibsLocation() {
		URI myLocation = findMyLocation();
		if (myLocation == null || !myLocation.isFile()) {
			// required for UI case in case of Xpect output plugin[UI] tests:
			return UtilN4.findN4jsRepoRootPath().resolve(N4JS_LIBS_NAME).resolve("packages").toAbsolutePath();
		}

		String myLocationStr = myLocation.toFileString();
		String searchStr = File.separator + N4JS_REPO_NAME + File.separator + TEST_HELPERS_NAME
				+ File.separator;
		int idx = myLocationStr.indexOf(searchStr);
		if (idx < 0) {
			throw new IllegalStateException(
					"cannot obtain location of n4js-libs: unable to find segments '..." + searchStr
							+ "...' in location path of class " + N4jsLibsAccess.class.getSimpleName());
		}

		String repoLocationStr = myLocationStr.substring(0, idx); // parent folder of N4JS Git repository
		String n4jsLibsLocationStr = repoLocationStr + File.separator + N4JS_REPO_NAME + File.separator
				+ N4JS_LIBS_NAME;
		Path n4jsLibsLocation;
		try {
			n4jsLibsLocation = Paths.get(n4jsLibsLocationStr);
		} catch (InvalidPathException e) {
			throw new IllegalStateException("cannot obtain location of n4js-libs: invalid path", e);
		}
		return n4jsLibsLocation.resolve("packages").toAbsolutePath();
	}

	/**
	 * Returns the absolute path to the root folder of the N4JS library with the given name (i.e. one of the npm
	 * packages located under "n4js-libs/packages" in the N4JS Git repository on the local file system).
	 *
	 * @throws IllegalStateException
	 *             if the {@link #findN4jsLibsLocation() n4js-libs location} cannot be obtained or there is not library
	 *             with the given name.
	 */
	public static Path findN4jsLib(String projectName) {
		Path base = findN4jsLibsLocation();
		return findN4jsLib(base, projectName, false);
	}

	private static Path findN4jsLib(Path n4jsLibs, String projectName, boolean searchNodeModules) {
		Path result = toProjectPath(n4jsLibs, projectName);
		if (searchNodeModules && !Files.exists(result)) {
			// 2nd attempt: check in node_modules folder of the containing yarn workspace
			Path nodeModules = n4jsLibs.getParent().resolve(N4JSGlobals.NODE_MODULES);
			result = toProjectPath(nodeModules, projectName);
		}
		if (!Files.exists(result)) {
			throw new IllegalArgumentException("cannot find a project among n4js-libs "
					+ (searchNodeModules ? "or the node_modules folder of the containing yarn workspace " : "")
					+ "with name: " + projectName);
		}
		return result;
	}

	/**
	 * Returns all N4JS libraries (i.e. the npm packages located under "n4js-libs/packages" in the N4JS Git repository
	 * on the local file system) as a map from package name to absolute path on local disk.
	 *
	 * @throws IllegalStateException
	 *             if the {@link #findN4jsLibsLocation() n4js-libs location} cannot be obtained or some other havoc
	 *             occurred.
	 */
	public static Map<String, Path> findAllN4jsLibs() {
		Path location = findN4jsLibsLocation();
		try {
			return Files.list(location)
					.filter(p -> isNpmPackage(p))
					.collect(Collectors.toMap(p -> p.getFileName().toString(), Function.identity()));
		} catch (IOException e) {
			throw new IllegalStateException("cannot obtain list of n4js-libs: " + e.getMessage(), e);
		}
	}

	/** Same as {@link #installN4jsLibs(Path, boolean, boolean, boolean, String...)} but will install all n4js-libs. */
	public static Map<String, Path> installAllN4jsLibs(Path targetPath, boolean includeDependencies,
			boolean useSymbolicLinks, boolean deleteOnExit) throws IOException {
		return installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit,
				Predicates.alwaysTrue());
	}

	/** Same as {@link #installN4jsLibs(Path, boolean, boolean, boolean, String...)} but a predicate can be supplied. */
	public static Map<String, Path> installN4jsLibs(Path targetPath, boolean includeDependencies,
			boolean useSymbolicLinks, boolean deleteOnExit, Predicate<String> n4jsLibsToInstall) throws IOException {
		Map<String, Path> allN4jsLibs = findAllN4jsLibs();
		Map<String, Path> toBeInstalled = allN4jsLibs.entrySet().stream()
				.filter(e -> n4jsLibsToInstall.test(e.getKey()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		return installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit, toBeInstalled);
	}

	/**
	 * Installs one or more N4JS libraries (i.e. the npm packages located under "n4js-libs/packages" in the N4JS Git
	 * repository on the local file system) by copying or symbolically linking them from their location in the local
	 * N4JS Git repository clone.
	 * <p>
	 * This method should be preferred over an installation from the remote npm registry to use the current version of
	 * the N4JS libraries of the currently running build. For tests that do not require execution of N4JS code (just
	 * compilation), method {@code ProjectTestsUtils#createDummyN4JSRuntime(Path)} might be a simpler alternative.
	 *
	 * @param targetPath
	 *            where to install.
	 * @param includeDependencies
	 *            if dependencies of the installed N4JS libraries should be installed, too. This includes both other
	 *            N4JS libraries or third-party dependencies that reside under folder "n4js-libs/node_modules" and will
	 *            be installed by MWE2 workflow BuildN4jsLibs in the early stages of the N4JS build.
	 * @param useSymbolicLinks
	 *            whether symbolic links should be used instead of copying the files.<br>
	 *            WARNING: if your tests changes the installed packages, this will affect a global location that will
	 *            influence other tests!
	 * @param deleteOnExit
	 *            whether the copied files / created symbolic links should be removed when the JVM shuts down.
	 * @param n4jsLibsNames
	 *            One or more package names of N4JS libraries that are to be installed.
	 * @return actually installed packages (including dependencies, if requested) as a map from package name to absolute
	 *         path.
	 * @throws IOException
	 *             in case of trouble.
	 */
	public static Map<String, Path> installN4jsLibs(Path targetPath, boolean includeDependencies,
			boolean useSymbolicLinks, boolean deleteOnExit, String... n4jsLibsNames) throws IOException {
		Path n4jsLibsLocation = findN4jsLibsLocation();
		Map<String, Path> toBeInstalled = new HashMap<>();
		for (String projectName : n4jsLibsNames) {
			Path projectPath = findN4jsLib(n4jsLibsLocation, projectName, false);
			toBeInstalled.put(projectName, projectPath);
		}
		return installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit, toBeInstalled);
	}

	private static Map<String, Path> installN4jsLibs(Path targetPath, boolean includeDependencies,
			boolean useSymbolicLinks, boolean deleteOnExit, Map<String, Path> n4jsLibsToBeInstalled)
			throws IOException {
		Map<String, Path> toBeInstalled = new LinkedHashMap<>();
		// add projects in 'projectNames' to 'toBeInstalled'
		toBeInstalled.putAll(n4jsLibsToBeInstalled);
		// add dependencies of projects in 'toBeInstalled' to 'toBeInstalled' (if requested)
		if (includeDependencies) {
			Path n4jsLibsLocation = N4jsLibsAccess.findN4jsLibsLocation();
			for (Path projectPath : new ArrayList<>(toBeInstalled.values())) { // will be modified in next line!
				collectDependencies(n4jsLibsLocation, projectPath, toBeInstalled);
			}
		}
		// actually install projects in 'toBeInstalled'
		for (Entry<String, Path> projectEntry : toBeInstalled.entrySet()) {
			String projectName = projectEntry.getKey();
			Path projectPath = projectEntry.getValue();
			Path targetProjectPath = toProjectPath(targetPath, projectName);
			if (useSymbolicLinks && !SYMBOLIC_LINKS_DISABLED) {
				Files.createDirectories(targetPath); // i.e. create parent of targetProjectPath
				Path symLinkPath = Files.createSymbolicLink(targetProjectPath, projectPath);
				if (deleteOnExit) {
					symLinkPath.toFile().deleteOnExit();
				}
			} else {
				Files.createDirectories(targetProjectPath);
				FileCopier.copy(projectPath, targetProjectPath);
				if (deleteOnExit) {
					Files.walk(targetProjectPath).forEach(path -> path.toFile().deleteOnExit());
				}
			}
		}
		return toBeInstalled;
	}

	private static void collectDependencies(Path n4jsLibsLocation, Path projectPath, Map<String, Path> addHere)
			throws IOException {
		Set<String> dependencyNames = loadDepenencies(projectPath, false, true, true);
		for (String dependencyName : dependencyNames) {
			Path dependencyPath = findN4jsLib(n4jsLibsLocation, dependencyName, true);
			if (addHere.putIfAbsent(dependencyName, dependencyPath) == null) {
				collectDependencies(n4jsLibsLocation, dependencyPath, addHere);
			}
		}
	}

	private static Set<String> loadDepenencies(Path projectPath, boolean includeDevDependencies,
			boolean excludeNestedProjects, boolean includeDepsOfNestedProjects) throws IOException {
		Set<String> result = new LinkedHashSet<>();
		Map<String, Path> nestedProjects = getNestedProjects(projectPath);
		// add dependencies of project at 'projectPath'
		List<String> dependencyNames = loadDepenencies(projectPath, includeDevDependencies);
		result.addAll(dependencyNames);
		// add dependencies of nested projects (if requested)
		if (includeDepsOfNestedProjects) {
			for (Path nestedProjectPath : nestedProjects.values()) {
				Set<String> nestedDependencyNames = loadDepenencies(nestedProjectPath,
						false, // never include devDependencies of nested projects!
						excludeNestedProjects, includeDepsOfNestedProjects);
				result.addAll(nestedDependencyNames);
			}
		}
		// remove nested projects (if requested)
		if (excludeNestedProjects) {
			result.removeAll(nestedProjects.keySet());
		}
		return result;
	}

	private static List<String> loadDepenencies(Path projectPath, boolean includeDevDependencies) throws IOException {
		List<String> result = new ArrayList<>();
		Path packageJsonPath = projectPath.resolve(N4JSGlobals.PACKAGE_JSON);
		JSONDocument packageJsonDoc = JSONModelUtils.loadJSON(packageJsonPath, Charsets.UTF_8);
		JSONObject dependenciesObj = (JSONObject) JSONModelUtils.getProperty(packageJsonDoc,
				PackageJsonProperties.DEPENDENCIES.name).orElse(null);
		if (dependenciesObj != null) {
			result.addAll(dependenciesObj.getNameValuePairs().stream().map(nvp -> nvp.getName())
					.collect(Collectors.toList()));
		}
		if (includeDevDependencies) {
			JSONObject devDependenciesObj = (JSONObject) JSONModelUtils.getProperty(packageJsonDoc,
					PackageJsonProperties.DEV_DEPENDENCIES.name).orElse(null);
			if (devDependenciesObj != null) {
				result.addAll(devDependenciesObj.getNameValuePairs().stream().map(nvp -> nvp.getName())
						.collect(Collectors.toList()));
			}
		}
		return result;
	}

	/**
	 * Returns name/path of all projects nested inside the node_modules folder of the project at the given location.
	 */
	private static Map<String, Path> getNestedProjects(Path projectPath) {
		Map<String, Path> result = new HashMap<>();
		Path nodeModulesPath = projectPath.resolve(N4JSGlobals.NODE_MODULES);
		if (Files.exists(nodeModulesPath)) {
			for (File childDir : nodeModulesPath.toFile().listFiles(File::isDirectory)) {
				if (childDir.getName().startsWith("@")) {
					for (File grandChildDir : childDir.listFiles(File::isDirectory)) {
						if (isNpmPackage(grandChildDir.toPath())) {
							result.put(childDir.getName() + '/' + grandChildDir.getName(), grandChildDir.toPath());
						}
					}
				} else if (isNpmPackage(childDir.toPath())) {
					result.put(childDir.getName(), childDir.toPath());
				}
			}
		}
		return result;
	}

	private static boolean isNpmPackage(Path path) {
		return !path.getFileName().toString().startsWith(".")
				&& Files.isDirectory(path)
				&& Files.isReadable(path.resolve(N4JSGlobals.PACKAGE_JSON));
	}

	private static Path toProjectPath(Path location, String projectName) {
		String projectNameAsPath = projectName.replace('/', File.separatorChar);
		return location.resolve(projectNameAsPath);
	}

	private static URI findMyLocation() {
		URL url = N4jsLibsAccess.class.getResource(N4jsLibsAccess.class.getSimpleName() + ".class");
		return url != null ? URI.createURI(url.toString()) : null;
	}
}
