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

import static java.util.Collections.singletonList;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.BinariesConstants;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Representation of a {@code npm} binary.
 */
@Singleton
public class NpmBinary extends Binary {

	@Inject
	private BinariesValidator validator;

	@Inject
	private Provider<NodeJsBinary> nodeJsBinaryProvider;

	@Inject
	private Provider<NpmrcBinary> npmrcBinaryProvider;

	@Override
	public String getId() {
		return NpmBinary.class.getName();
	}

	@Override
	public String getLabel() {
		return BinariesConstants.NPM_LABEL;
	}

	@Override
	public String getDescription() {
		return "Configuration of the folder location of the npm library "
				+ "can be provided here. If not given, then the location will be resolved by used Node.js\u00AE. "
				+ "The required minimum version npm is '" + BinariesConstants.NPM_MIN_VERSION + "'.";
	}

	@Override
	public VersionNumber getMinimumVersion() {
		return BinariesConstants.NPM_MIN_VERSION;
	}

	@Override
	public String getBinaryDirectory() {
		final NodeJsBinary nodeJsBinary = nodeJsBinaryProvider.get();
		return nodeJsBinary.getUserNodePathOrDefault();
	}

	@Override
	public String getBinaryFileName() {
		return BinariesConstants.NPM_BINARY_NAME;
	}

	@Override
	public String getVersionArgument() {
		return BinariesConstants.VERSION_ARGUMENT;
	}

	@Override
	public Binary getParent() {
		return nodeJsBinaryProvider.get();
	}

	@Override
	public Iterable<Binary> getChildren() {
		return singletonList(npmrcBinaryProvider.get());
	}

	@Override
	public Map<String, String> updateEnvironment(final Map<String, String> environment) {
		final Binary parent = getParent();
		if (null != parent) {
			parent.updateEnvironment(environment);
		}
		return environment;
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
		return "install"; // same command in both cases
	}

	@Override
	public String getNpmUninstallCommand() {
		return "uninstall";
	}

	@Override
	public Optional<Pair<String, String>> getNpmSaveOptions() {
		return Optional.of(new Pair<>("--save", "--no-save"));
	}

}
