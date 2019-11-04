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
package org.eclipse.n4js.binaries.nodejs;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.BinariesConstants;
import org.eclipse.n4js.binaries.BinariesLocatorHelper;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Representation of a {@code yarn} binary.
 */
@Singleton
public class JavaBinary extends Binary {

	private String memoizedCalculatedYarnPath = null;

	@Inject
	private BinariesValidator validator;

	@Inject
	private BinariesLocatorHelper binariesLocatorHelper;

	@Override
	public String getId() {
		return JavaBinary.class.getName();
	}

	@Override
	public String getLabel() {
		return BinariesConstants.JAVA_LABEL;
	}

	@Override
	public String getDescription() {
		return "Configuration of the folder location of the Yarn executable "
				+ "can be provided here. If not given, then the '" + getDefaultJavaPath() // FIXME adjust this!!!!!
				+ "' location will be used as the default location. The required minimum version for Yarn is '"
				+ SemverSerializer.serialize(getMinimumVersion()) + "'.";
	}

	@Override
	public VersionNumber getMinimumVersion() {
		return BinariesConstants.JAVA_MIN_VERSION;
	}

	@Override
	public String getBinaryDirectory() {
		return getUserYarnPathOrDefault();
	}

	@Override
	public String getBinaryFileName() {
		return BinariesConstants.JAVA_BINARY_NAME;
	}

	@Override
	public String getVersionArgument() {
		return BinariesConstants.JAVA_VERSION_ARGUMENT;
	}

	@Override
	public IStatus validate() {
		return validator.validate(this);
	}

	@Override
	public Optional<String[]> getCacheCleanCommand() {
		throw new UnsupportedOperationException("Not supported by Java binary");
	}

	@Override
	public String getNpmInstallCommand(boolean readPackagesFromPackageJson) {
		throw new UnsupportedOperationException("Not supported by Java binary");
	}

	@Override
	public String getNpmUninstallCommand() {
		throw new UnsupportedOperationException("Not supported by Java binary");
	}

	@Override
	public Optional<Pair<String, String>> getNpmSaveOptions() {
		throw new UnsupportedOperationException("Not supported by Java binary");
	}

	/**
	 * Returns user-provided or default location of the binary.
	 *
	 * @return the user-configured absolute path to the binary or the default one.
	 */
	public String getUserYarnPathOrDefault() {
		final URI userConfiguredLocation = getUserConfiguredLocation();
		return null == userConfiguredLocation ? getDefaultJavaPath()
				: new File(userConfiguredLocation).getAbsolutePath();
	}

	private String getDefaultJavaPath() {
		if (memoizedCalculatedYarnPath == null) {
			memoizedCalculatedYarnPath = binariesLocatorHelper.findJavaPath().toString();
		}
		return memoizedCalculatedYarnPath;
	}

}
