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
package org.eclipse.n4js.hlc.tests;

import static java.util.Collections.emptyMap;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

/**
 * Base test class for checking the external/third party project capabilities in the headless mode.
 */
public abstract class BaseN4jscExternalTest extends AbstractN4jscTest {

	private TargetPlatformFiles platformFiles;

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	@Before
	public void beforeTest() throws IOException {
		ExternalsUtiities.setupExternals(platformFiles, description.getMethodName(),
				getNpmDependencies());
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	@After
	public void afterTest() {
		ExternalsUtiities.cleanupExternals(platformFiles);
	}

	/**
	 * Returns with a map of desired npm package dependencies. Keys are the package identifier, values are the desired
	 * package versions. Values (versions), can be {@code null}. In such cases the version will not be specified for the
	 * corresponding dependency. By default returns with an empty map.
	 *
	 * @return a map of npm dependencies. Keys are package names, values are the package versions.
	 */
	protected Map<String, String> getNpmDependencies() {
		return emptyMap();
	}

	/**
	 * Returns with the target platform file.
	 */
	protected File getTargetPlatformFile() {
		return platformFiles.targetPlatformFile;
	}

	/**
	 * Returns with the target platform install location.
	 */
	protected File getTargetPlatformInstallLocation() {
		return platformFiles.targetPlatformInstallLocation;
	}

}
