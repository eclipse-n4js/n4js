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

import java.io.File;

/**
 * Wrapper around data for setting up external libraries. This represent locations created on the file system that will
 * be created and destroyed during test life cycle. Might not suitable to parallel executions.
 */
@SuppressWarnings("javadoc")
public class TargetPlatformFiles {
	public File targetPlatformInstallLocation;
	public File root;
}
