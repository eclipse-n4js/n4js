/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.utils;

import java.io.File;
import java.util.Map;

import org.eclipse.n4js.utils.OSInfo;

/**
 * Utility methods for finding the binaries of certain external tools, e.g. Java, node.
 */
public class BinariesUtils {

	/**
	 * The {@code PATH} environment variable.
	 */
	public static final String PATH = "PATH";

	/**
	 * Jenkins environment variable for the {@code Node.js} binary path. Points to the actual binary (with an absolute
	 * path) instead of pointing to the folder containing the binary.
	 *
	 * <p>
	 * Even if it is available the {@Code org.eclipse.n4js.defaultNodePath} VM argument might override this
	 * configuration.
	 */
	private static final String NODEJS_PATH_ENV = "NODEJS_PATH";

	/** Adds the current nodejs environment variable to the environment variables of the process builder argument */
	static public Map<String, String> inheritNodeJsPathEnvVariable(Map<String, String> environment) {
		if (environment != null) {
			final String nodeJsPath = System.getenv(NODEJS_PATH_ENV);
			if (nodeJsPath != null && nodeJsPath.trim().length() > 0) {
				if (!environment.containsKey(NODEJS_PATH_ENV)) {
					environment.put(NODEJS_PATH_ENV, nodeJsPath);
				}
			}
		}
		return environment;
	}

	/**
	 * Merges the environment variables of {@code envB} into {@code envA}.
	 *
	 * @return merged environment variables {@code envA}
	 */
	public static Map<String, String> mergeEnvironments(Map<String, String> envA, Map<String, String> envB) {
		for (Map.Entry<String, String> entry : envB.entrySet()) {
			String envKey = entry.getKey();
			String envValue = entry.getValue();
			String actualKeyName = findActualPropertyNameOrDefault(envA, envKey);
			String newValue = envA.containsKey(actualKeyName)
					? envA.get(actualKeyName) + File.pathSeparator + envValue
					: envValue;
			envA.put(actualKeyName, newValue);
		}
		return envA;
	}

	/** Deals with case-insensitivity on environment variables on windows platform */
	private static String findActualPropertyNameOrDefault(Map<String, String> environment, String defaultName) {
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
}
