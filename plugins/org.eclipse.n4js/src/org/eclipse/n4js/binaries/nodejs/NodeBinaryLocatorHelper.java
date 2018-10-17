/**
 * Copyright (c) 2017 NumberFour AG.
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
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.n4js.utils.ExecutableLookupUtil;
import org.eclipse.n4js.utils.process.OutputRedirection;
import org.eclipse.n4js.utils.process.ProcessExecutor;
import org.eclipse.n4js.utils.process.ProcessResult;

import com.google.inject.Inject;

/**
 * Helper used to locate default node binary in the system. This helper looks only for specific JVM arguments,
 * environment and underlying OS configurations. Checking other sources, e.g. eclipse preference store, is up to the
 * caller.
 */
public class NodeBinaryLocatorHelper {
	private static final Logger LOGGER = Logger.getLogger(NodeBinaryLocatorHelper.class);

	/** debug api */
	private static final boolean LOG_SYSTEM_PROPERTIES = false;
	/** debug api */
	private static final boolean LOG_ENV_VARIABLES = false;
	/** debug api */
	private static final boolean LOG_TO_STD_OUT = false;

	@Inject
	private ProcessExecutor processExecutor;

	/**
	 * Performs lookup of the node binary. Uses {@link NodeBinariesConstants} properties to perform lookup. When binary
	 * not found, will ask OS to locate node binary via {@link #lookForNode}. If everything else fails returns (not
	 * verified) path configured by {@link NodeBinariesConstants#BUILT_IN_DEFAULT_NODE_PATH}
	 *
	 * @return string with absolute path to the binary
	 */
	public String findNodePath() {

		logSystemProperties();
		logEnvironmentVariables();

		String nodePathCandidate = null;

		// 1. lookup by DEFAULT_NODE_PATH_VM_ARG
		nodePathCandidate = resolveFolderContaingNode(
				tryGetEnvOrSystemVariable(NodeBinariesConstants.DEFAULT_NODE_PATH_VM_ARG));
		if (!isNullOrEmptyOrNullString(nodePathCandidate)) {
			info("User specified default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the '" + NodeBinariesConstants.DEFAULT_NODE_PATH_VM_ARG + "' VM argument.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from '" + NodeBinariesConstants.DEFAULT_NODE_PATH_VM_ARG
				+ "' VM argument.");

		// 2. lookup by NODEJS_PATH_ENV
		nodePathCandidate = resolveFolderContaingNode(
				tryGetEnvOrSystemVariable(NodeBinariesConstants.NODEJS_PATH_ENV));
		if (!isNullOrEmptyOrNullString(nodePathCandidate)) {
			info("User specified default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the '" + NodeBinariesConstants.NODEJS_PATH_ENV + "' VM argument.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from '" + NodeBinariesConstants.NODEJS_PATH_ENV
				+ "' VM argument.");

		// 3. lookup by PATH
		nodePathCandidate = resolveFolderContaingNode(
				ExecutableLookupUtil.findInPath(NodeBinariesConstants.NODE_BINARY_NAME));
		if (!isNullOrEmptyOrNullString(nodePathCandidate)) {
			info("Obtained default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the OS PATH.");
			return nodePathCandidate;
		}
		debug("Could not resolve node path from OS PATH variable.");

		// 4. lookup by OS query
		nodePathCandidate = resolveFolderContaingNode(lookForNode(NodeBinariesConstants.NODE_BINARY_NAME));
		if (!isNullOrEmptyOrNullString(nodePathCandidate)) {
			info("Obtained default Node.js path will be used: '" + nodePathCandidate
					+ ".' based on the OS dynamic lookup.");
			return nodePathCandidate;

		}
		debug("Could not resolve node path from OS dynamic lookup.");

		// 5. use default, whether it is correct or not.
		info("Could not resolve node path. Falling back to default path: " + nodePathCandidate);
		nodePathCandidate = NodeBinariesConstants.BUILT_IN_DEFAULT_NODE_PATH;
		return nodePathCandidate;
	}

	private String lookForNode(String binaryName) {
		ProcessResult processResult = processExecutor.createAndExecute(
				ExecutableLookupUtil.getExebutableLookupProcessBuilder(binaryName), "look for " + binaryName,
				OutputRedirection.SUPPRESS);
		// if found, result will have trailing new line, sanitize whole result
		return processResult.getStdOut().trim();
	}

	/**
	 * Tries to get the {@code propertyName} variable from the available environment variables, if it does not exist,
	 * then falls back to system properties.
	 */
	private static String tryGetEnvOrSystemVariable(String propertyName) {
		final String nodeJsPath = System.getenv(propertyName);
		if (isNullOrEmptyOrNullString(nodeJsPath)) {
			return System.getProperty(propertyName);
		}
		return nodeJsPath;
	}

	/** Tries to resolve node folder from provided string. Returns path to the folder as string or null. */
	private static String resolveFolderContaingNode(String nodePathCandidate) {
		if (isNullOrEmptyOrNullString(nodePathCandidate)) {
			debug("provided potential node directory path was null");
			return null;
		}
		File nodeDir = new File(nodePathCandidate);
		return resolveNodeFolderPath(nodeDir);
	}

	/** Tries to resolve node folder from provided file. Returns path to the folder as string or null. */
	private static String resolveNodeFolderPath(File nodeDir) {
		if (nodeDir.isFile()) {
			debug("provided potential node directory is actually a file, obtaining parent");
			nodeDir = nodeDir.getParentFile();

		} else if (!nodeDir.exists()) {
			debug("cannot obtain file system object from provided string");
			return null;

		} else if (!nodeDir.isDirectory()) {
			debug("could not safely resolve node directory");
			return null;
		}

		return nodeDir.getAbsolutePath();
	}

	/**
	 * Returns with {@code true} if one of the followings are {@code true}
	 * <ul>
	 * <li>Argument {@code s} is {@code null} or</li>
	 * <li>Argument {@code s} is an empty string or</li>
	 * <li>Argument {@code s} contains only whitespaces or</li>
	 * <li>Argument {@code s} equals with the <i>null</i> string.</li>
	 * </ul>
	 * Otherwise returns with {@code false}.
	 */
	private static boolean isNullOrEmptyOrNullString(final String s) {
		return null == s || 0 == s.trim().length() || "null".equals(s);
	}

	private static void logSystemProperties() {
		if (LOG_SYSTEM_PROPERTIES) {
			info("---------------------- System Properties ----------------------------------");
			final Properties p = System.getProperties();
			final Enumeration<Object> keys = p.keys();
			while (keys.hasMoreElements()) {
				final String key = (String) keys.nextElement();
				final String value = (String) p.get(key);
				info(key + ": " + value);
			}
			info("---------------------- End of System Properties ----------------------------------");
		}
	}

	private static void logEnvironmentVariables() {
		if (LOG_ENV_VARIABLES) {
			info("---------------------- Environment Variables ----------------------------------");
			for (final Entry<String, String> e : System.getenv().entrySet()) {
				info(e.getKey() + ": " + e.getValue());
			}
			info("---------------------- End of Environment Variables ----------------------------------");
		}
	}

	private static void info(final Object message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info(message);
		}
		if (LOG_TO_STD_OUT) {
			System.out.println(message);
		}
	}

	private static void debug(final Object message) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(message);
		}
		if (LOG_TO_STD_OUT) {
			System.out.println(message);
		}
	}
}
