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

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Collections.emptyList;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.BinariesConstants;
import org.eclipse.n4js.binaries.BinariesLocatorHelper;
import org.eclipse.n4js.binaries.BinariesPreferenceStore;
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
public class YarnBinary implements Binary {

	/** don't access directly, use {@link #getDefaultYarnPath()} */
	private String memoizedCalculatedYarnPath = null;

	@Inject
	private BinariesValidator validator;

	@Inject
	private BinariesPreferenceStore preferenceStore;

	@Inject
	private BinariesLocatorHelper binariesLocatorHelper;

	@Override
	public String getId() {
		return YarnBinary.class.getName();
	}

	@Override
	public String getLabel() {
		return BinariesConstants.YARN_LABEL;
	}

	@Override
	public String getDescription() {
		return "Configuration of the folder location of the Yarn executable "
				+ "can be provided here. If not given, then the '" + getDefaultYarnPath() // FIXME adjust this!!!!!
				+ "' location will be used as the default location. The required minimum version for Yarn is '"
				+ SemverSerializer.serialize(getMinimumVersion()) + "'.";
	}

	@Override
	public VersionNumber getMinimumVersion() {
		return BinariesConstants.YARN_MIN_VERSION;
	}

	@Override
	public String getBinaryAbsolutePath() {
		return getUserYarnPathOrDefault() + File.separator + BinariesConstants.YARN_BINARY_NAME;
	}

	@Override
	public String getVersionArgument() {
		return BinariesConstants.YARN_VERSION_ARGUMENT;
	}

	@Override
	public Binary getParent() {
		return null;
	}

	@Override
	public Iterable<Binary> getChildren() {
		return emptyList();
	}

	@Override
	public Map<String, String> updateEnvironment(final Map<String, String> environment) {
		final String additionalNodePath = getUserYarnPathOrDefault();
		final String currentPathValue = environment.get(PATH);
		if (isNullOrEmpty(currentPathValue)) {
			environment.put(PATH, additionalNodePath);
		} else {
			environment.put(PATH, currentPathValue + File.pathSeparator + additionalNodePath);
		}
		return environment;
	}

	@Override
	public URI getUserConfiguredLocation() {
		return preferenceStore.getPath(this);
	}

	@Override
	public IStatus validate() {
		return validator.validate(this);
	}

	@Override
	public Optional<String[]> getCacheCleanCommand() {
		return Optional.of(new String[] { "cache", "clean", "--force" });
	}

	@Override
	public boolean canInstallNpmPackages() {
		return true;
	}

	@Override
	public String getNpmInstallCommand(boolean readPackagesFromPackageJson) {
		return readPackagesFromPackageJson ? "install" : "add";
	}

	@Override
	public String getNpmUninstallCommand() {
		return "remove";
	}

	@Override
	public Optional<Pair<String, String>> getNpmSaveOptions() {
		return Optional.of(new Pair<>("--save", "--no-save"));
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
		if (!(obj instanceof YarnBinary)) {
			return false;
		}
		final YarnBinary other = (YarnBinary) obj;
		return Objects.equals(getId(), other.getId());
	}

	/**
	 * Returns user-provided or default location of the binary.
	 *
	 * @return the user-configured absolute path to the binary or the default one.
	 */
	public String getUserYarnPathOrDefault() {
		final URI userConfiguredLocation = getUserConfiguredLocation();
		return null == userConfiguredLocation ? getDefaultYarnPath()
				: new File(userConfiguredLocation).getAbsolutePath();
	}

	private String getDefaultYarnPath() {
		if (memoizedCalculatedYarnPath == null) {
			memoizedCalculatedYarnPath = binariesLocatorHelper.findYarnPath().toString();
		}
		return memoizedCalculatedYarnPath;
	}

}
