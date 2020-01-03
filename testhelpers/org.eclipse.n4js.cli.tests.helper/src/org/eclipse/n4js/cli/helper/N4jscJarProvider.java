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

import static com.google.common.base.Preconditions.checkState;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Utility methods for accessing the {@code n4jsc.jar} built in maven project {@code org.eclipse.n4js.hlc} during the
 * same overall maven build. This is required mainly for integration tests.
 */
public class N4jscJarProvider {
	private static final Logger LOGGER = Logger.getLogger(N4jscJarProvider.class);

	/** Environment variable to pass in the location of the {@code n4jsc.jar}. */
	public static final String PROVIDED_N4JSC_JAR_ENV = "PROVIDED_N4JSC_JAR";

	/**
	 * Path and file name of default n4jsc.jar to use if environment variable {@link #PROVIDED_N4JSC_JAR_ENV} is unset.
	 */
	public static final String DEFAULT_N4JSC_JAR = "target/n4jsc.jar";

	/**
	 * Returns with the absolute file resource representing the location of the {@code n4jsc.jar}. If no location is
	 * provided by the {@value #PROVIDED_N4JSC_JAR_ENV} variable then it will fall back to the {@code /target/n4jsc.jar}
	 * location. This method never returns with a file pointing to an non-existing resource but throws a runtime
	 * exception instead.
	 *
	 * @return the runnable file of n4jsc.
	 */
	public static File getAbsoluteRunnableN4jsc() {
		final File jar;
		final String providedJar = System.getenv(PROVIDED_N4JSC_JAR_ENV);
		if (null == providedJar || "".equals(providedJar.trim()) || "null".equals(providedJar)) {
			LOGGER.info("Environment variable \"" + PROVIDED_N4JSC_JAR_ENV + "\" is unset; using default \""
					+ DEFAULT_N4JSC_JAR + "\"");
			jar = new File(DEFAULT_N4JSC_JAR).getAbsoluteFile();
		} else {
			LOGGER.info("Environment variable \"" + PROVIDED_N4JSC_JAR_ENV + "\" is set to: " + providedJar);
			jar = new File(providedJar).getAbsoluteFile();
		}
		LOGGER.info("Using n4jsc.jar at: " + jar.getAbsolutePath());
		checkState(jar.exists(), "n4jsc.jar does not exist at location: " + jar);
		return jar;
	}

}
