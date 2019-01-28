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

import static java.util.Collections.emptyList;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.BinariesConstants;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.semver.Semver.VersionNumber;

import com.google.common.base.StandardSystemProperty;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Representation of a {@code npmrc}. While not being binary itself, it exploits current design to allow user to add
 * extra configuration to the other binaries, in particular it reconfigures calls to the {@code npm}.
 *
 * Note, that {@code npm} is not binary itself, but an executable (script) file added by the {@code npm} library.
 */
@Singleton
public class NpmrcBinary implements Binary {

	private static final String NPM_CONFIG_USERCONFIG = "NPM_CONFIG_userconfig";

	/** don't access directly, use {@link #getDefaultNpmrcPath()} */
	private String memoizedCalculatedNpmrcPath = null;

	@Inject
	private BinariesValidator validator;

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private BinariesPreferenceStore preferenceStore;

	@Override
	public String getId() {
		return NpmrcBinary.class.getName();
	}

	@Override
	public String getLabel() {
		return BinariesConstants.NPMRC_LABEL;
	}

	@Override
	public String getDescription() {
		return "Configuration of the folder location of the .npmrc file "
				+ "can be provided here. If not given, then the location will be resolved to '"
				+ getDefaultNpmrcPath() + "'. The required minimum version npm is '"
				+ BinariesConstants.NPM_MIN_VERSION + "'.";
	}

	@Override
	public VersionNumber getMinimumVersion() {
		return null;
	}

	@Override
	public String getBinaryAbsolutePath() {
		return getUserNodePathOrDefault() + File.separator + BinariesConstants.NPMRC_BINARY_NAME;
	}

	@Override
	public String getVersionArgument() {
		return BinariesConstants.VERSION_ARGUMENT;
	}

	@Override
	public Binary getParent() {
		return npmBinaryProvider.get();
	}

	@Override
	public Iterable<Binary> getChildren() {
		return emptyList();
	}

	@Override
	public Map<String, String> updateEnvironment(final Map<String, String> environment) {
		final String additionalNodePath = getUserNodePathOrDefault() + File.separator
				+ BinariesConstants.NPMRC_BINARY_NAME;

		// overwrite
		environment.put(NPM_CONFIG_USERCONFIG, additionalNodePath);

		return environment;
	}

	@Override
	public URI getUserConfiguredLocation() {
		return preferenceStore.getPath(this);
	}

	@Override
	public IStatus validate() {
		final Binary parent = getParent();
		if (null != parent) {
			final IStatus parentStatus = parent.validate();
			if (!parentStatus.isOK()) {
				return parentStatus;
			}
		}
		return validator.validateBinaryFile(this);
	}

	/**
	 * Custom hashcode, used to persist settings in the map {@link BinariesPreferenceStore} internal map. Key part about
	 * that hashCode is that it will be the same for every instance of this class, allowing to easily serialize
	 * {@code Binary -> URI} setting even between platform runs.
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NpmrcBinary)) {
			return false;
		}
		final NpmrcBinary other = (NpmrcBinary) obj;
		return Objects.equals(getId(), other.getId());
	}

	/**
	 * Returns user provided or the default location of the binary.
	 *
	 * @return the user configured absolute path to the binary or the default one.
	 */
	String getUserNodePathOrDefault() {
		final URI userConfiguredLocation = getUserConfiguredLocation();
		return null == userConfiguredLocation ? getDefaultNpmrcPath()
				: new File(userConfiguredLocation).getAbsolutePath();
	}

	private String getDefaultNpmrcPath() {
		if (memoizedCalculatedNpmrcPath == null) {
			memoizedCalculatedNpmrcPath = StandardSystemProperty.USER_HOME.value();
		}
		return memoizedCalculatedNpmrcPath;
	}
}
