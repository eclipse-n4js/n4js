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
package org.eclipse.n4js.runner.internal;

import java.io.File;
import java.net.URI;

import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;

/**
 * Stub for {@code org.eclipse.n4js.internal.N4JSModel#installLocationProvider}
 */
class RunnerTargetPlatformInstallLocationProvider implements TargetPlatformInstallLocationProvider {

	@Override
	public File getTargetPlatformInstallFolder() {
		return null;
	}

	@Override
	public URI getTargetPlatformFileLocation() {
		return null;
	}

}
