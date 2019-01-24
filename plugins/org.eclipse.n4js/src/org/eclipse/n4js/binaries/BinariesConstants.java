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

import static java.io.File.separator;
import static org.eclipse.n4js.utils.OSInfo.isWindows;

import java.io.File;

import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;

/**
 * Contains constants for binaries all over the system.
 */
public final class BinariesConstants {

	/**
	 * VM argument for configuring the default {@code Node.js} path. That default {@code Node.js} binary path will be
	 * used when no user specified binary location is given. This VM argument could come handy when for instance running
	 * when there is no way to provide any user specific binary path settings and the default {@code Node.js} path is
	 * differs from the expected one.
	 *
	 * <p>
	 * When the using this VM argument make sure that absolute path is given and the path points to the folder which
	 * contains the {@code Node.js} binary.
	 *
	 * <p>
	 * On Windows systems the file separators (backslashes [&#x5c;]) have to escaped, hence double backslashes has to be
	 * used.
	 *
	 * <p>
	 * For instance on Unix system the usage should be something like below:
	 *
	 * <pre>
	 * -Dorg.eclipse.n4js.defaultNodePath=/absolute/path/to/the/node/binary/folder
	 * </pre>
	 *
	 * and on Windows systems the following pattern should be followed:
	 *
	 * <pre>
	 * -Dorg.eclipse.n4js.defaultNodePath=C:\\absolute\\path\\to\\the\\node\binary\\folder
	 * </pre>
	 */
	public static final String DEFAULT_NODE_PATH_VM_ARG = "org.eclipse.n4js.defaultNodePath";

	/**
	 * Jenkins environment variable for the {@code Node.js} binary path. Points to the actual binary (with an absolute
	 * path) instead of pointing to the folder containing the binary.
	 *
	 * <p>
	 * Even if it is available the {@link #DEFAULT_NODE_PATH_VM_ARG <code>org.eclipse.n4js.defaultNodePath</code>} VM
	 * argument might override this configuration.
	 */
	public static final String NODEJS_PATH_ENV = "NODEJS_PATH";

	/**
	 * The (fallback) built-in default {@code Node.js} path if the above VM or ENV property is not specified.
	 *
	 * <ul>
	 * <li>On Windows systems: {@code C:\Program Files\nodejs}</li>
	 * <li>On Unix systems: {@code /usr/local/bin}</li>
	 * </ul>
	 */
	public static final String BUILT_IN_DEFAULT_NODE_PATH = isWindows()
			? new File("C:" + separator + "Program Files" + separator + "nodejs").getAbsolutePath()
			: new File(separator + "usr" + File.separator + "local" + separator + "bin").getAbsolutePath();

	/** The minimum {@code Node.js} version. */
	public static final VersionNumber NODE_MIN_VERSION = SemverUtils.createVersionNumber(10, 13, 0);
	/** The label for {@code Node.js}. */
	public static final String NODE_LABEL = "Node.js";
	/** The {@code Node.js} binary name (OS specific). */
	public static final String NODE_BINARY_NAME = isWindows() ? "node.exe" : "node";
	/** The argument for node.js to print out the version number */
	public static final String VERSION_ARGUMENT = "-v";

	/** The minimum {@code npm} version. */
	public static final VersionNumber NPM_MIN_VERSION = SemverUtils.createVersionNumber(6, 4, 0);
	/** The label for {@code npm}. */
	public static final String NPM_LABEL = "npm";
	/** The {@code npm} binary name. */
	public static final String NPM_BINARY_NAME = "npm";
	/** The label for {@code npmrc}. */
	public static final String NPMRC_LABEL = "npmrc";
	/** The {@code npmrc} binary name. */
	public static final String NPMRC_BINARY_NAME = ".npmrc";

	/** The label for {@code yarn}. */
	public static final String YARN_LABEL = "Yarn";
	/** The {@code yarn} binary name (OS specific). */
	public static final String YARN_BINARY_NAME = isWindows() ? "yarn.exe" : "yarn";
	/** The minimum {@code yarn} version. */
	public static final VersionNumber YARN_MIN_VERSION = SemverUtils.createVersionNumber(1, 13, 0);
	/** The argument for yarn to print out the version number */
	public static final String YARN_VERSION_ARGUMENT = "-v";

	private BinariesConstants() {
		// No initialization.
	}

}
