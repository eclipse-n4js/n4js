/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.external;

import java.io.File;

import org.eclipse.n4js.N4JSGlobals;

/**
 * Utilities and core rules for external libraries.
 */
public final class ExternalLibraryUtils {

	private ExternalLibraryUtils() {
	}

	/**
	 * Returns {@code true} iff the given {@link File} represents a project directory in the workspace that is available
	 * to the projects in the N4JS workspace in that workspace projects may declare dependencies on them.
	 *
	 * This excludes packages that have been installed to the external workspace as transitive dependency of a package
	 * that has been explicitly installed on user request.
	 */
	public static boolean isExternalProjectDirectory(File projectDirectory) {
		if (!projectDirectory.isDirectory()) {
			return false;
		}
		// check whether package.json and package.marker files exists
		// (we here require package.marker in order to return false for packages that have been installed as
		// transitive dependency, i.e. indirectly by "npm install"; see N4JSGlobals#PACKAGE_MARKER for details)
		final File packageJsonFile = new File(projectDirectory, N4JSGlobals.PACKAGE_JSON);
		final File packageMarkerFile = new File(projectDirectory, N4JSGlobals.PACKAGE_MARKER);
		return packageJsonFile.isFile() && packageMarkerFile.isFile();
	}
}
