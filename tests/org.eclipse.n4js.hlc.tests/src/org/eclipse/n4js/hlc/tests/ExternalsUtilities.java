/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.tests;

import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.n4js.utils.io.FileUtils.createDirectory;
import static org.eclipse.n4js.utils.io.FileUtils.createTempDirectory;

import java.nio.file.Path;

import org.eclipse.n4js.utils.io.FileUtils;

/**
 * Utility for setting up external libraries for HLC tests.
 */
public class ExternalsUtilities {

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	public static void setupExternals(TargetPlatformFiles platformFiles, String tmpPrefix) {
		checkState(null == platformFiles.targetPlatformInstallLocation);

		final Path tempRoot = createTempDirectory("hlcTest-time-" + System.currentTimeMillis());
		platformFiles.root = tempRoot.toFile();
		platformFiles.targetPlatformInstallLocation = createDirectory(tempRoot, tmpPrefix).toFile();
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	public static void cleanupExternals(TargetPlatformFiles platformFiles) {
		// Following commented files are related to discovered issue GH-521
		// When uncommenting them every single test runs fine, but running
		// all tests in org.eclipse.n4js.hlc.tests make some of them fail.
		// Looks like there is issue with injection of TargetPlatformInstallLocationProvider,
		// i.e. even in independent test classes Validators use the same instance of the provider
		// (called from N4JSModel)/

		if (null != platformFiles.targetPlatformInstallLocation) {
			// TODO GH-521 location re-used between tests
			FileUtils.deleteFileOrFolder(platformFiles.targetPlatformInstallLocation);
			platformFiles.targetPlatformInstallLocation = null;
		}
		// TODO GH-521 location re-used between tests
		// if (null != platformFiles.root) {
		// FileUtils.deleteFileOrFolder(platformFiles.root);
		// platformFiles.root = null;
		// }
	}

}
