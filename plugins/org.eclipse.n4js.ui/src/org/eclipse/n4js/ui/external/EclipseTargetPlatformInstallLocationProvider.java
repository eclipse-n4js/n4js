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
package org.eclipse.n4js.ui.external;

import static com.google.common.base.Preconditions.checkState;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.external.libraries.ExternalLibrariesActivator;

import com.google.inject.Singleton;

/**
 * Target platform install location and target platform file location provider that is used when the platform is
 * running, hence the Eclipse workspace exists and available.
 */
@Singleton
public class EclipseTargetPlatformInstallLocationProvider implements TargetPlatformInstallLocationProvider {

	@Override
	public File getTargetPlatformInstallFolder() {
		checkState(Platform.isRunning(), "Injection problem? Expected running platform.");
		final File location = ExternalLibrariesActivator.N4_NPM_FOLDER_SUPPLIER.get();
		return location;
	}

	@Override
	public URI getTargetPlatformFileLocation() {
		return new File(getTargetPlatformInstallFolder(), N4JSGlobals.PACKAGE_JSON).toURI();
	}
}
