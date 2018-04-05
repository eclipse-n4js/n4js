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

import java.io.File;

import org.junit.After;
import org.junit.Before;

/**
 * Base test class for checking the external/third party project capabilities in the headless mode.
 */
public abstract class BaseN4jscExternalTest extends AbstractN4jscTest {

	private final TargetPlatformFiles platformFiles = new TargetPlatformFiles();

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	@Before
	public void beforeTest() {
		ExternalsUtilities.setupExternals(platformFiles, description.getMethodName());
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	@After
	public void afterTest() {
		ExternalsUtilities.cleanupExternals(platformFiles);
	}

	/**
	 * Returns with the target platform install location.
	 */
	protected File getTargetPlatformInstallLocation() {
		return platformFiles.targetPlatformInstallLocation;
	}

}
