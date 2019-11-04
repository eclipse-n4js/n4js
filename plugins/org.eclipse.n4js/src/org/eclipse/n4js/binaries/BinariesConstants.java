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
	 * Default yarn path, similar to {@code DEFAULT_NODE_PATH_VM_ARG}
	 */
	public static final String DEFAULT_YARN_PATH_VM_ARG = "org.eclipse.n4js.defaultYarnPath";

	/**
	 * Default java path, similar to {@code DEFAULT_NODE_PATH_VM_ARG}
	 */
	public static final String DEFAULT_JAVA_PATH_VM_ARG = "org.eclipse.n4js.defaultJavaPath";

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
	 * Jenkins environment variable for the {@code yarn} binary path. Points to the actual binary (with an absolute
	 * path) instead of pointing to the folder containing the binary.
	 *
	 * <p>
	 * Even if it is available the {@link #DEFAULT_YARN_PATH_VM_ARG <code>org.eclipse.n4js.defaultYarnPath</code>} VM
	 * argument might override this configuration.
	 */
	public static final String YARN_PATH_ENV = "YARN_PATH";

	/**
	 * Jenkins environment variable for the {@code java} binary path. Points to the actual binary (with an absolute
	 * path) instead of pointing to the folder containing the binary.
	 *
	 * <p>
	 * Even if it is available the {@link #DEFAULT_JAVA_PATH_VM_ARG <code>org.eclipse.n4js.defaultJavaPath</code>} VM
	 * argument might override this configuration.
	 */
	public static final String JAVA_PATH_ENV = "JAVA_HOME";

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

	/**
	 * The (fallback) built-in default {@code yarn} path if the above VM or ENV property is not specified.
	 *
	 * <ul>
	 * <li>On Windows systems: {@code C:\Program Files\yarn}</li>
	 * <li>On Unix systems: {@code /usr/local/bin}</li>
	 * </ul>
	 */
	public static final String BUILT_IN_DEFAULT_YARN_PATH = isWindows()
			? new File("C:" + separator + "Program Files" + separator + "yarn").getAbsolutePath()
			: new File(separator + "usr" + File.separator + "local" + separator + "bin").getAbsolutePath();

	/**
	 * The (fallback) built-in default {@code java} path if the above VM or ENV property is not specified.
	 *
	 * <ul>
	 * <li>On Windows systems: {@code C:\Program Files\Java}</li>
	 * <li>On Unix systems: {@code /usr/bin/java}</li>
	 * </ul>
	 */
	public static final String BUILT_IN_DEFAULT_JAVA_PATH = isWindows()
			? new File("C:" + separator + "Program Files" + separator + "Java").getAbsolutePath()
			: new File(separator + "usr" + File.separator + "bin" + separator + "java").getAbsolutePath();

	/** The minimum {@code Node.js} version. */
	public static final VersionNumber NODE_MIN_VERSION = SemverUtils.createVersionNumber(10, 13, 0);
	/** The label for {@code Node.js}. */
	public static final String NODE_LABEL = "Node.js";
	/** The {@code Node.js} binary name without file extension. */
	public static final String NODE_BINARY_NAME = "node";
	/** The argument for node.js to print out the version number */
	public static final String VERSION_ARGUMENT = "-v";

	/** The minimum {@code npm} version. */
	public static final VersionNumber NPM_MIN_VERSION = SemverUtils.createVersionNumber(6, 4, 0);
	/** The label for {@code npm}. */
	public static final String NPM_LABEL = "npm";
	/** The {@code npm} binary name without file extension. */
	public static final String NPM_BINARY_NAME = "npm";
	/** The label for {@code npmrc}. */
	public static final String NPMRC_LABEL = "npmrc";
	/** The {@code npmrc} binary name. */
	public static final String NPMRC_BINARY_NAME = ".npmrc";

	/** The label for {@code yarn}. */
	public static final String YARN_LABEL = "Yarn";
	/** The {@code yarn} binary name without file extension. */
	public static final String YARN_BINARY_NAME = "yarn";
	/** The minimum {@code yarn} version. */
	public static final VersionNumber YARN_MIN_VERSION = SemverUtils.createVersionNumber(1, 13, 0);
	/** The argument for yarn to print out the version number */
	public static final String YARN_VERSION_ARGUMENT = "-v";

	/** The label for {@code java}. */
	public static final String JAVA_LABEL = "Java";
	/** The {@code java} binary name without file extension. */
	public static final String JAVA_BINARY_NAME = "java";
	/** The minimum {@code java} version. */
	public static final VersionNumber JAVA_MIN_VERSION = SemverUtils.createVersionNumber(11, 0, 3);
	/** The argument for java to print out the version number */
	public static final String JAVA_VERSION_ARGUMENT = "-version";

	/** File extensions for executable files on windows platform */
	public static final String[] EXECS_WINDOWS = { ".bat", ".cmd", ".exe" };
	/** File extensions for executable files on non-windows platforms */
	public static final String[] EXECS_OTHERS = { "" };
	/** File extensions for executable files (OS specific) */
	public static final String[] EXECUTABLE_FILE_EXTENSIONS = isWindows() ? EXECS_WINDOWS : EXECS_OTHERS;

	private BinariesConstants() {
		// No initialization.
	}

}
