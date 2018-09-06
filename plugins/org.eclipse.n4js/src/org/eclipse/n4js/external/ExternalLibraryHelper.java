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
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

/**
 * Utilities and core rules for external libraries.
 */
public final class ExternalLibraryHelper {

	/**
	 * Returns {@code true} iff the given {@link File} represents a project directory in the workspace that is available
	 * to the projects in the N4JS workspace in that workspace projects may declare dependencies on them.
	 *
	 * This excludes packages that have been installed to the external workspace as transitive dependency of a package
	 * that has been explicitly installed on user request.
	 */
	public boolean isExternalProjectDirectory(File projectDirectory) {
		if (!projectDirectory.isDirectory()) {
			return false;
		}

		// check whether package.json exists
		final File packageJsonFile = new File(projectDirectory, N4JSGlobals.PACKAGE_JSON);
		return packageJsonFile.isFile();
	}

	/**
	 * Returns {@code true} iff the given {@link File} represents a directory that is considered an npm scope directory.
	 */
	public boolean isScopeDirectory(File scopeDirectory) {
		final String name = scopeDirectory.getName();
		return name.startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX) &&
				ProjectDescriptionUtils.isValidScopeName(name);
	}
}
