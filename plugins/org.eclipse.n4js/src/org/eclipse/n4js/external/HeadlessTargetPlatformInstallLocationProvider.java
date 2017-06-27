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
package org.eclipse.n4js.external;

import java.net.URI;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Headless target platform install location provider. This implementation is designed for cases when neither the
 * platform nor the workbench is available and the location information should be provided by the user. For instance in
 * case of the headless compiler.
 */
@Singleton
public class HeadlessTargetPlatformInstallLocationProvider implements TargetPlatformInstallLocationProvider {

	@Inject
	private TypeDefinitionGitLocationProvider gitLocationProvider;

	private URI targetPlatformInstallLocation;

	private URI targetPlatformFileLocation;

	@Override
	public URI getTargetPlatformInstallLocation() {
		return targetPlatformInstallLocation;
	}

	@Override
	public URI getTargetPlatformFileLocation() {
		return targetPlatformFileLocation;
	}

	@Override
	public String getGitRepositoryName() {
		return gitLocationProvider.getGitLocation().getRepositoryName();
	}

	/**
	 * (non-API)
	 *
	 * Sets the target platform install location according to the argument.
	 *
	 * @param targetPlatformInstallLocation
	 *            the location of the desired target platform install location.
	 */
	public void setTargetPlatformInstallLocation(final URI targetPlatformInstallLocation) {
		this.targetPlatformInstallLocation = targetPlatformInstallLocation;
	}

	/**
	 * (non-API)
	 *
	 * Sets the target platform file location according to the argument.
	 *
	 * @param targetPlatformFileLocation
	 *            the location of the desired target platform file location.
	 */
	public void setTargetPlatformFileLocation(final URI targetPlatformFileLocation) {
		this.targetPlatformFileLocation = targetPlatformFileLocation;
	}

}
