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
package org.eclipse.n4js.binaries;

import static java.util.Collections.emptyList;

import java.io.File;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.OSInfo;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Representation of a binary.
 */
abstract public class Binary {

	/** Preference store of binaries */
	@Inject
	protected BinariesPreferenceStore preferenceStore;

	/**
	 * The {@code PATH} environment variable.
	 */
	String PATH = "PATH";

	/**
	 * Returns with the application specific unique ID of the binary. This ID can be used to uniquely identify the
	 * binary.
	 *
	 * @return the application specific unique identifier of the binary.
	 */
	abstract public String getId();

	/**
	 * Returns with the human readable name of the binary.
	 *
	 * @return the human readable name of the binary.
	 */
	abstract public String getLabel();

	/**
	 * Returns with a description about the current binary. This description should provide some hint for the end used.
	 * For instance this could include some information about required version or about any other restrictions
	 * requirements.
	 *
	 * <p>
	 * By default returns with {@code null}.
	 *
	 * @return description for the binary.
	 */
	public String getDescription() {
		return null;
	}

	/**
	 * Returns with the desired minimum version of the binary. If the this method returns with either {@code null}
	 * version, then no requirements are specified with respect to the minimum version.
	 *
	 * @return the minimum desired version or {@code null} or {@code MISSING} if no requirements are specified.
	 */
	abstract public VersionNumber getMinimumVersion();

	/**
	 * The actual binary absolute path as a string that has to be called to execute the binary.
	 *
	 * @return the path string of the binary to execute.
	 */
	public String getBinaryAbsolutePath() {
		return getBinaryDirectory() + File.separator + getBinaryFileName();
	}

	/**
	 * The actual binary directory where the executable can be found.
	 *
	 * @return the path string of the directory of the binary.
	 */
	abstract public String getBinaryDirectory();

	/**
	 * The actual binary name.
	 *
	 * @return the name of the binary.
	 */
	abstract public String getBinaryFileName();

	/**
	 * The version argument that is used to check and validate the version of the current binary. In lot cases this is
	 * {@code -v} or {@code --version}. If empty string or {@code null}, the the version cannot be validated for the
	 * current binary.
	 *
	 * @return the version argument for the binary.
	 */
	abstract public String getVersionArgument();

	/**
	 * Returns with the parent dependency of the binary. If a binary has a parent that means, that a binary is valid, if
	 * its parent binary is valid as well. May return with {@code null} if the binary has no parent binary.
	 *
	 * @return the parent binary , or {@code null} if has no parent.
	 */
	public Binary getParent() {
		return null;
	}

	/**
	 * Returns with the children binaries or an empty iterable if the binary has no child binaries. For instance
	 * {@code Node.js} has a child binary {@code npm}, that means, {@code npm} cannot be run if its parent
	 * {@code Node.js} is not available or not valid.
	 *
	 * @return an iterable of children binaries.
	 */
	public Iterable<Binary> getChildren() {
		return emptyList();
	}

	/**
	 * Merges any specific properties to the environment map given as the argument and returns with the updated
	 * argument.
	 *
	 * @param environment
	 *            a map of key-values representing the environment.
	 * @return the argument.
	 */
	public Map<String, String> updateEnvironment(Map<String, String> environment) {
		final String additionalPath = getBinaryDirectory();
		final String actualPathPropertyName = findActualPropertyNameOrDefault(environment, PATH);
		final String newValue = environment.containsKey(actualPathPropertyName)
				? environment.get(actualPathPropertyName) + File.pathSeparator + additionalPath
				: additionalPath;

		environment.put(actualPathPropertyName, newValue);
		return environment;
	}

	/** Deals with case-insensitivity on environment variables on windows platform */
	protected String findActualPropertyNameOrDefault(Map<String, String> environment, String defaultName) {
		if (environment.containsKey(defaultName)) {
			return defaultName;
		}
		if (OSInfo.isWindows()) {
			if (System.getenv(defaultName) == null) {
				return defaultName;
			}

			for (String prop : environment.keySet()) {
				if (defaultName.equalsIgnoreCase(prop)) {
					return prop;
				}
			}
		}
		return defaultName;
	}

	/**
	 * Returns with the custom configured path for the binary. May return with {@code null}. If returns with
	 * {@code null}, then no custom user configuration is specified for the binary.
	 *
	 * @return the user configured path where the binary exist, when no user configuration is available.
	 */
	public URI getUserConfiguredLocation() {
		return preferenceStore.getPath(this);
	}

	/**
	 * Validates the binaries whether it is available, the version fulfills the requirements and the binary can be
	 * executed. Returns with a status indicating the outcome of the validation.
	 *
	 * @return a status representing the outcome of the validation process.
	 */
	abstract public IStatus validate();

	/**
	 * Returns the command for cleaning this binary's cache if applicable and supported; otherwise
	 * {@link Optional#absent()} is returned.
	 */
	public Optional<String[]> getCacheCleanCommand() {
		return Optional.absent();
	}

	/**
	 * Tells whether this binary can be used to install and uninstall npm packages, as is the case for tools such as
	 * <code>npm</code> or <code>yarn</code>.
	 */
	public boolean canInstallNpmPackages() {
		return false;
	}

	/**
	 * If {@link #canInstallNpmPackages()} returns <code>true</code>, this method returns the command for installing npm
	 * packages.
	 *
	 * @param readPackagesFromPackageJson
	 *            caller should pass in <code>true</code> to obtain the command for installing all packages defined as
	 *            dependencies in the project's package.json file; <code>false</code> to obtain the command for
	 *            installing one or more individual packages given as command line arguments on the command line.
	 * @throws UnsupportedOperationException
	 *             iff {@link #canInstallNpmPackages()} returns <code>false</code>.
	 */
	public String getNpmInstallCommand(boolean readPackagesFromPackageJson) {
		throw new UnsupportedOperationException("(un-)installation of npm packages not supported by this tool");
	}

	/**
	 * If {@link #canInstallNpmPackages()} returns <code>true</code>, this method returns the command for uninstalling
	 * npm packages.
	 *
	 * @throws UnsupportedOperationException
	 *             iff {@link #canInstallNpmPackages()} returns <code>false</code>.
	 */
	public String getNpmUninstallCommand() {
		throw new UnsupportedOperationException("(un-)installation of npm packages not supported by this tool");
	}

	/**
	 * If {@link #canInstallNpmPackages()} returns <code>true</code>, this method returns the command line options for
	 * controlling whether an installed / uninstalled dependency should be added to / removed from the package.json (the
	 * pair's key holds the option for turning this on, they value holds the option for turning this off) or
	 * {@link Optional#absent() absent} if this is not supported by the tool.
	 *
	 * @throws UnsupportedOperationException
	 *             iff {@link #canInstallNpmPackages()} returns <code>false</code>.
	 */
	public Optional<Pair<String, String>> getNpmSaveOptions() {
		throw new UnsupportedOperationException("(un-)installation of npm packages not supported by this tool");
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
		if (!(obj instanceof Binary)) {
			return false;
		}
		final Binary other = (Binary) obj;
		return Objects.equals(getId(), other.getId());
	}
}
