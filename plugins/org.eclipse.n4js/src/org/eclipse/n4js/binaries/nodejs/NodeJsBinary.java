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

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.binaries.BinariesConstants;
import org.eclipse.n4js.binaries.BinariesLocatorHelper;
import org.eclipse.n4js.binaries.BinariesValidator;
import org.eclipse.n4js.binaries.Binary;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.model.SemverSerializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Representation of a {@code Node.js} binary.
 */
@Singleton
public class NodeJsBinary extends Binary {

	/** don't access directly, use {@link #getDefaultNodePath()} */
	private String memoizedCalculatedNodePath = null;

	@Inject
	private BinariesValidator validator;

	@Inject
	private Provider<NpmBinary> npmBinaryProvider;

	@Inject
	private BinariesLocatorHelper nodeBinaryLocatorHelper;

	@Override
	public String getId() {
		return NodeJsBinary.class.getName();
	}

	@Override
	public String getLabel() {
		return BinariesConstants.NODE_LABEL;
	}

	@Override
	public String getDescription() {
		return "Configuration of the folder location of the Node.js\u00AE executable "
				+ "can be provided here. If not given, then the '" + getDefaultNodePath()
				+ "' location will be used as the default location. The required minimum version for Node.js is '"
				+ SemverSerializer.serialize(getMinimumVersion()) + "'.";
	}

	@Override
	public VersionNumber getMinimumVersion() {
		return BinariesConstants.NODE_MIN_VERSION;
	}

	@Override
	public String getBinaryDirectory() {
		return getUserNodePathOrDefault();
	}

	@Override
	public String getBinaryFileName() {
		return BinariesConstants.NODE_BINARY_NAME;
	}

	@Override
	public String getVersionArgument() {
		return BinariesConstants.VERSION_ARGUMENT;
	}

	@Override
	public Iterable<Binary> getChildren() {
		return singletonList(npmBinaryProvider.get());
	}

	@Override
	public IStatus validate() {
		return validator.validate(this);
	}

	/**
	 * Returns user provided or the default location of the binary.
	 *
	 * @return the user configured absolute path to the binary or the default one.
	 */
	public String getUserNodePathOrDefault() {
		final URI userConfiguredLocation = getUserConfiguredLocation();
		return null == userConfiguredLocation ? getDefaultNodePath()
				: new File(userConfiguredLocation).getAbsolutePath();
	}

	private String getDefaultNodePath() {
		if (memoizedCalculatedNodePath == null) {
			memoizedCalculatedNodePath = nodeBinaryLocatorHelper.findNodePath().toString();
		}
		return memoizedCalculatedNodePath;
	}

}
