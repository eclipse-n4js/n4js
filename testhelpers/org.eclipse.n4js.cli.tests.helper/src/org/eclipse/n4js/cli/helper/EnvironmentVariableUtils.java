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
package org.eclipse.n4js.cli.helper;

import org.apache.log4j.Logger;

/**
 * Utility class for dealing with environment variables.
 */
public abstract class EnvironmentVariableUtils {

	private static final Logger LOGGER = Logger.getLogger(EnvironmentVariableUtils.class);

	/**
	 * Jenkins environment variable for the {@code Node.js} binary path. Points to the actual binary (with an absolute
	 * path) instead of pointing to the folder containing the binary.
	 *
	 * <p>
	 * Even if it is available the {@Code org.eclipse.n4js.defaultNodePath} VM argument might override this
	 * configuration.
	 */
	private static final String NODEJS_PATH_ENV = "NODEJS_PATH";

	/**
	 * Merges the current environment variables to the environment variables of the process builder argument
	 *
	 * @param pb
	 *            the process builder who's environment variables has to be updated with the NODEJS_PATH variable.
	 * @return the argument.
	 */
	public static ProcessBuilder inheritNodeJsPathEnvVariableUtils(ProcessBuilder pb) {
		if (null != pb) {
			final String nodeJsPath = System.getenv(NODEJS_PATH_ENV);
			if (null == nodeJsPath || 0 == nodeJsPath.trim().length()) {
				info("No " + NODEJS_PATH_ENV + " environment variable was specified. Nothing to merge.");
			} else {
				if (!pb.environment().containsKey(NODEJS_PATH_ENV)) {
					info("Setting " + NODEJS_PATH_ENV + " environment variable to process builder.");
					pb.environment().put(NODEJS_PATH_ENV, nodeJsPath);
				} else {
					info("Process builder already has " + NODEJS_PATH_ENV + " environment variable with value: "
							+ pb.environment().get(NODEJS_PATH_ENV) + ". Nothing to do.");
				}
			}
		}
		return pb;
	}

	private static void info(Object message) {
		LOGGER.info(message);
		System.out.println(message);
	}

}
