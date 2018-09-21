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

import static org.eclipse.n4js.utils.io.FileUtils.createDirectory;
import static org.eclipse.n4js.utils.io.FileUtils.createTempDirectory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.libraries.ExternalLibraryFolderUtils;
import org.eclipse.n4js.internal.N4JSModel;

import com.google.inject.Singleton;

/**
 * Headless target platform install location provider. This implementation is designed for cases when neither the
 * platform nor the workbench is available and the location information should be provided by the user. For instance in
 * case of the headless compiler.
 */
@Singleton
public class HlcTargetPlatformInstallLocationProvider implements TargetPlatformInstallLocationProvider {

	private File targetPlatformInstallLocation;

	private URI targetPlatformFileLocation;

	private URI tempRoot;

	@Override
	public File getTargetPlatformInstallFolder() {
		return targetPlatformInstallLocation;
	}

	@Override
	public URI getTargetPlatformFileLocation() {
		return targetPlatformFileLocation;
	}

	/**
	 * (non-API)
	 *
	 * Sets the target platform install location according to the argument.
	 *
	 * @param targetPlatformInstallLocation
	 *            the location of the desired target platform install location.
	 */
	public void setTargetPlatformInstallLocation(final File targetPlatformInstallLocation) {
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

	/**
	 * (non-API)
	 *
	 * Sets the target platform install location ant the target platform file location in newly create location in the
	 * {@code temp} folder. Callers should take care to clean up those locations. Caller can use {@link #getTempRoot()}
	 * method to get root {@code URI} of the configurations. When cleaning up locations caller should also set stored
	 * locations to {@code null} (or just call {@link #resetState()}.
	 *
	 */
	public void configureWithTempFolders() throws IOException {
		// create temp location
		final Path root = createTempDirectory("hlcTmpDepsLocation_" + System.currentTimeMillis());
		final Path n4npm = createDirectory(root, ExternalLibraryFolderUtils.NPM_ROOT);
		final File packageJson = new File(n4npm.toFile(), N4JSGlobals.PACKAGE_JSON);

		packageJson.createNewFile();

		try (final FileWriter fw = new FileWriter(packageJson)) {
			fw.write(ExternalLibraryFolderUtils.createTargetPlatformPackageJson());
			fw.flush();
		}
		packageJson.deleteOnExit();
		this.tempRoot = root.toUri();
		this.targetPlatformInstallLocation = n4npm.toFile();
		this.targetPlatformFileLocation = packageJson.toURI();
	}

	/**
	 * (non-API)
	 *
	 * Returns URI of root with configs if this instance was configured with {@link #configureWithTempFolders()}.
	 * Returns {@code null} otherwise.
	 *
	 */
	public URI getTempRoot() {
		return this.tempRoot;
	}

	/**
	 * (non-API)
	 *
	 * Resets state to fresh (as if instance was just created).
	 *
	 * We need to have this method as it seems that when running multiple HLC tests {@link N4JSModel} will use this
	 * instance (bound via {@link TargetPlatformInstallLocationProvider}. The model is singleton and is obtained in
	 * {@code org.eclipse.n4js.generator.headless.N4HeadlessCompiler} via generic Xtext facilities. In short we always
	 * get the same model (it is a {@code Singleton}) with the same instance of this class. As a result locations set up
	 * for one test are used in other test.
	 *
	 * @see <a href="https://github.com/eclipse/n4js/issues/521">GH-521</a>
	 */
	public void resetState() {
		this.targetPlatformInstallLocation = null;
		this.targetPlatformFileLocation = null;
		this.tempRoot = null;
	}

}
