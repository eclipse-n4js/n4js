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
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;

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

	private static URI findMyLocation() {
		URL url = N4jsLibsAccess.class.getResource(N4jsLibsAccess.class.getSimpleName() + ".class");
		return url != null ? URI.createURI(url.toString()) : null;
	}
}
