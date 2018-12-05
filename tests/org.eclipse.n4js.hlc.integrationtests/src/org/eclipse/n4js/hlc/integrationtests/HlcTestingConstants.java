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
package org.eclipse.n4js.hlc.integrationtests;

import java.io.File;

/**
 * Constants for parameters used during testing.
 */
public class HlcTestingConstants {

	/**
	 * Standard Name of the executable jar. In standard maven builds it should reside in the
	 * {@link HlcTestingConstants#TARGET_FOLDER}
	 */
	static final String N4JSC_JAR = "n4jsc.jar";

	/** Standard target folder-name, the base of maven-compile results. */
	static final String TARGET = "target";

	/** Standard target folder (name+"/"), the base of maven-compile results. */
	static final String TARGET_FOLDER = TARGET + "/";

	/** Sub folder in target folder. */
	protected static final String WORKSPACE_FOLDER = "wsp";

	/**
	 * Environment variable for the path of the generated {@code n4jsc.jar}.
	 */
	// FIXME: is currently not evaluated
	static final String N4_N4JSC_JAR_ENV = "N4_N4JSC_JAR";
	/**
	 * Environment variable defined the name of the transpiler-specific sub-folder within a project's output folder. For
	 * example, this was "es5" for the old transpiler and is now "es" for the new transpiler. Required by the lde and
	 * some shell scripts.
	 */
	static final String N4_JSFORMAT_ENV = "N4_JSFORMAT";
	/**
	 * Environment variable for the path of the generated {@code generator.jar}.
	 */
	static final String N4_N4JS_GENERATOR_JAR_ENV = "N4_N4JS_GENERATOR_JAR";
	/**
	 * Environment variable used for disabling the {@code stdlib} server integration tests if the value of the variable
	 * is either {@code false} or {@code ignore}.
	 */
	static final String N4_TEST_SERVER_INTEGRATION_ENV = "N4_TEST_SERVER_INTEGRATION";
	/**
	 * Workspace variable used by the Jenkins job.
	 */
	static final String WORKSPACE_ENV = "WORKSPACE";
	/**
	 * Absolute path of the {@code n4jsc.jar}. Default in cases where {@link #N4_N4JSC_JAR_ENV} is not given.
	 */
	static final String N4JSC_PATH = new File(TARGET_FOLDER + N4JSC_JAR).getAbsolutePath();

}
