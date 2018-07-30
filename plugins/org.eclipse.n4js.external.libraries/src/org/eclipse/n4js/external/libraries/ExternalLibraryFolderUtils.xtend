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
package org.eclipse.n4js.external.libraries

/**
 * Utilities for dealing with the folder containing the external libraries.
 */
class ExternalLibraryFolderUtils {

	/** The name of NPM's package json file. */
	public static final String PACKAGE_JSON = "package.json";

	/** Unique name of the root npm folder for N4JS. */
	public static final String NPM_ROOT = ".n4npm";

	/**
	 * Returns the initial contents of the "package.json" file located in the root of the external libraries
	 * folder, next to the "node_modules" folder. Required by npm.
	 * <p>
	 * This is just a minimal stub. We never add/remove dependencies to this file ourselves; instead, npm will
	 * update this file over time when we invoke "npm install", etc.
	 */
	def public static String createTargetPlatformPackageJson() {
		// NOTE: Xtend's template string literals use the OS-dependent line separator, so nothing to convert here
		return '''
			{
				"name": "targetplatform"
			}
		''';
	}
}
