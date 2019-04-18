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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
 * repository are located on disk uncompressed.
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

	public static boolean isRunningFromSource() {
		URI myLocation = findMyLocation();
		return myLocation != null && myLocation.isFile();
	}

	// FIXME consider using UtilN4#findN4jsRepoRootPath() instead!
	public static Path findN4jsLibsLocation() {
		URI myLocation = findMyLocation();
		if (myLocation == null || !myLocation.isFile()) {
			// throw new IllegalStateException(
			// "cannot obtain location of n4js-libs: current application not running from source");
			// FIXME temporary: (required for UI case in case of Xpect output plugin[UI] tests)
			return UtilN4.findN4jsRepoRootPath().resolve(N4JS_LIBS_NAME).resolve("packages");
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
		return n4jsLibsLocation.resolve("packages");
	}

	public static Path findN4jsLibs(String projectName) {
		Path base = findN4jsLibsLocation();
		return findN4jsLibs(base, projectName, false);
	}

	private static Path findN4jsLibs(Path n4jsLibs, String projectName, boolean searchNodeModules) {
		Path result = n4jsLibs.resolve(projectName);
		if (searchNodeModules && !Files.exists(result)) {
			// 2nd attempt: check in node_modules folder of the containing yarn workspace
			result = n4jsLibs.getParent().resolve(N4JSGlobals.NODE_MODULES).resolve(projectName);
		}
		if (!Files.exists(result)) {
			throw new IllegalArgumentException("cannot find a project among n4js-libs "
					+ (searchNodeModules ? "or the node_modules folder of the containing yarn workspace " : "")
					+ "with name: " + projectName);
		}
		return result;
	}

	public static List<Path> findAllN4jsLibs() {
		Path location = findN4jsLibsLocation();
		try {
			return Files.list(location).filter(p -> isNpmPackage(p)).collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException("cannot obtain list of n4js-libs: " + e.getMessage(), e);
		}
	}

	public static void installAllN4jsLibs(Path targetPath, boolean includeDependencies, boolean useSymbolicLinks,
			boolean deleteOnExit) throws IOException {
		installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit, Predicates.alwaysTrue());
	}

	public static void installN4jsLibs(Path targetPath, boolean includeDependencies, boolean useSymbolicLinks,
			boolean deleteOnExit, Predicate<String> predicate) throws IOException {
		List<Path> allN4jsLibs = findAllN4jsLibs();
		List<Path> toBeInstalled = allN4jsLibs.stream()
				.filter(p -> predicate.test(p.getFileName().toString()))
				.collect(Collectors.toList());
		installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit, toBeInstalled);
	}

	public static void installN4jsLibs(Path targetPath, boolean includeDependencies, boolean useSymbolicLinks,
			boolean deleteOnExit, String... projectNames) throws IOException {
		Path n4jsLibsLocation = findN4jsLibsLocation();
		List<Path> toBeInstalled = new ArrayList<>();
		for (String projectName : projectNames) {
			Path projectPath = findN4jsLibs(n4jsLibsLocation, projectName, false);
			toBeInstalled.add(projectPath);
		}
		installN4jsLibs(targetPath, includeDependencies, useSymbolicLinks, deleteOnExit, toBeInstalled);
	}

	private static void installN4jsLibs(Path targetPath, boolean includeDependencies, boolean useSymbolicLinks,
			boolean deleteOnExit, Collection<Path> projectPaths) throws IOException {
		Set<Path> toBeInstalled = new HashSet<>();
		// add projects in 'projectNames' to 'toBeInstalled'
		toBeInstalled.addAll(projectPaths);
		// add dependencies of projects in 'toBeInstalled' to 'toBeInstalled' (if requested)
		if (includeDependencies) {
			Path n4jsLibsLocation = N4jsLibsAccess.findN4jsLibsLocation();
			for (Path projectPath : new ArrayList<>(toBeInstalled)) { // will be modified in next line!
				collectDependencies(n4jsLibsLocation, projectPath, toBeInstalled);
			}
		}
		// actually install projects in 'toBeInstalled'
		for (Path projectPath : toBeInstalled) {
			String projectName = projectPath.getFileName().toString();
			Path targetProjectPath = targetPath.resolve(projectName);
			if (useSymbolicLinks && !SYMBOLIC_LINKS_DISABLED) {
				Files.createDirectories(targetPath); // i.e. parent of targetProjectPath
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
	}

	private static void collectDependencies(Path n4jsLibsLocation, Path projectPath, Set<Path> addHere)
			throws IOException {
		Set<String> dependencyNames = loadDepenencies(projectPath, false, true, true);
		for (String dependencyName : dependencyNames) {
			Path dependencyPath = findN4jsLibs(n4jsLibsLocation, dependencyName, true);
			if (addHere.add(dependencyPath)) {
				collectDependencies(n4jsLibsLocation, dependencyPath, addHere);
			}
		}
	}

	private static Set<String> loadDepenencies(Path projectPath, boolean includeDevDependencies,
			boolean excludeNestedProjects, boolean includeDepsOfNestedProjects) throws IOException {
		Set<String> result = new LinkedHashSet<>();
		Path nodeModulesPath = projectPath.resolve(N4JSGlobals.NODE_MODULES);
		List<String> nestedProjectNames = Files.exists(nodeModulesPath)
				? Files.list(nodeModulesPath).filter(p -> isNpmPackage(p)).map(p -> p.getFileName().toString())
						.collect(Collectors.toList())
				: Collections.emptyList();
		// add dependencies of project at 'projectPath'
		List<String> dependencyNames = loadDepenencies(projectPath, includeDevDependencies);
		result.addAll(dependencyNames);
		// add dependencies of nested projects (if requested)
		if (includeDepsOfNestedProjects) {
			for (String nestedProjectName : nestedProjectNames) {
				Set<String> nestedDependencyNames = loadDepenencies(nodeModulesPath.resolve(nestedProjectName),
						false, // never include devDependencies of nested projects!
						excludeNestedProjects, includeDepsOfNestedProjects);
				result.addAll(nestedDependencyNames);
			}
		}
		// remove nested projects (if requested)
		if (excludeNestedProjects) {
			result.removeAll(nestedProjectNames);
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

	private static boolean isNpmPackage(Path path) {
		return !path.getFileName().toString().startsWith(".")
				&& Files.isDirectory(path)
				&& Files.isReadable(path.resolve(N4JSGlobals.PACKAGE_JSON));
	}

	private static URI findMyLocation() {
		URL url = N4jsLibsAccess.class.getResource(N4jsLibsAccess.class.getSimpleName() + ".class");
		return url != null ? URI.createURI(url.toString()) : null;
	}
}
