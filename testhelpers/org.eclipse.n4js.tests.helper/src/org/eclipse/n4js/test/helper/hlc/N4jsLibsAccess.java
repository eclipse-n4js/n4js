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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.io.FileCopier;

/**
 * Provides access to the projects located in the N4JS Git repository under top-level folder "n4js-libs". Assumes that
 * the code invoking the methods in this class is run from source, i.e. that the entire contents of the N4JS Git
 * repository are located on disk uncompressed.
 */
public class N4jsLibsAccess {

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
	public static Path findN4jsLibs() {
		URI myLocation = findMyLocation();
		if (myLocation == null || !myLocation.isFile()) {
			throw new IllegalStateException(
					"cannot obtain location of n4js-libs: current application not running from source");
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
		return n4jsLibsLocation;
	}

	public static Path findN4jsLibs(String projectName, boolean includeDependencies) {
		Path base = findN4jsLibs();
		return findN4jsLibs(base, projectName, includeDependencies);
	}

	private static Path findN4jsLibs(Path n4jsLibs, String projectName, boolean includeDependencies) {
		Path result = n4jsLibs.resolve("packages").resolve(projectName);
		if (includeDependencies && !Files.exists(result)) {
			// 2nd attempt: check in node_modules folder of the containing yarn workspace
			result = n4jsLibs.resolve(N4JSGlobals.NODE_MODULES).resolve(projectName);
		}
		if (!Files.exists(result)) {
			throw new IllegalArgumentException("cannot find a project among n4js-libs "
					+ (includeDependencies ? "and their dependencies " : "") + "with name: " + projectName);
		}
		return result;
	}

	public static void installN4jsLibs(Path targetPath, boolean includeDependencies, boolean useSymbolicLinks,
			boolean deleteOnExit, String... projectNames) throws IOException {
		Path n4jsLibsPath = N4jsLibsAccess.findN4jsLibs();
		Files.createDirectories(targetPath);
		for (String projectName : projectNames) {
			Path from = findN4jsLibs(n4jsLibsPath, projectName, includeDependencies);
			Path to = targetPath.resolve(projectName);
			if (useSymbolicLinks) {
				Path symLinkPath = Files.createSymbolicLink(to, from);
				if (deleteOnExit) {
					symLinkPath.toFile().deleteOnExit();
				}
			} else {
				FileCopier.copy(from, to);
				if (deleteOnExit) {
					Files.walk(to).forEach(path -> path.toFile().deleteOnExit());
				}
			}
		}
	}

	private static URI findMyLocation() {
		URL url = N4jsLibsAccess.class.getResource(N4jsLibsAccess.class.getSimpleName() + ".class");
		return url != null ? URI.createURI(url.toString()) : null;
	}
}
