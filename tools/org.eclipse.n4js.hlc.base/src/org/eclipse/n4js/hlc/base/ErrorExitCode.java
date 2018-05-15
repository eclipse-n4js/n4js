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
package org.eclipse.n4js.hlc.base;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for headless compiler exit codes.
 *
 * Consider this enum <b>unordered</b> and do not rely order of declaration, i.e. do not use its ordinal, instead use
 * {@link ErrorExitCode#getExitCodeValue()} for int comparisons.
 */
public enum ErrorExitCode {
	/** Exit with 1, wrong parameter set given */
	EXITCODE_WRONG_CMDLINE_OPTIONS(1, "wrong parameter set given"),
	/** Exit with 2, if compilation did not succeed. */
	EXITCODE_COMPILE_ERROR(2, "compilation did not succeed"),
	/** Exit with 3, if configuration is erroneous. */
	EXITCODE_CONFIGURATION_ERROR(3, "configuration is erroneous"),
	/** Exit with 4, if required runner could not be loaded. */
	EXITCODE_RUNNER_NOT_FOUND(4, "required runner could not be loaded"),
	/** Exit with 5, if module to run doesn't exist. */
	EXITCODE_MODULE_TO_RUN_NOT_FOUND(5, "module to run doesn't exist"),
	/** Exit code 6 indicating an unsuccessful execution of the runner. */
	EXITCODE_RUNNER_STOPPED_WITH_ERROR(6, "unsuccessful execution of the runner"),
	/** Exit code 7 indicating that assembling the test catalog failed for compiler projects. */
	EXITCODE_TEST_CATALOG_ASSEMBLATION_ERROR(7, "assembling the test catalog failed for compiler projects"),
	/** Exit with 8, if cleaning did not succeed. */
	EXITCODE_CLEAN_ERROR(8, "cleaning projects encountered error"),
	/** Exit code 9 indicating an unsuccessful execution of the tests. */
	EXITCODE_TESTER_STOPPED_WITH_ERROR(9, "unsuccessful execution of the tests"),
	/** Exit with 10, if required tester could not be loaded. */
	EXITCODE_TESTER_NOT_FOUND(10, "required tester could not be loaded"),
	/** Exit with 11, if required tester could not be loaded. */
	EXITCODE_DEPENDENCY_NOT_FOUND(11, "required dependency is missing"),
	/** Exit with 12, if srcFiles are invalid. */
	EXITCODE_SRCFILES_INVALID(12,
			"At least one source file or project (last parameter) is neither file nor directory.");

	private static final Map<Integer, ErrorExitCode> lookup = new HashMap<>();

	static {
		for (ErrorExitCode ec : EnumSet.allOf(ErrorExitCode.class))
			lookup.put(ec.getExitCodeValue(), ec);
	}

	private final String explanation;
	private final int code;

	private ErrorExitCode(int code, String explanation) {
		this.code = code;
		this.explanation = explanation;
	}

	/** Get user readible explanation of the message. */
	public String getExplanation() {
		return this.explanation;
	}

	/** Get numeric value of the exit code. Note that this is different from enum ordinal. */
	public int getExitCodeValue() {
		return this.code;
	}

	/** Returns {@link ErrorExitCode} for the provided int code, or null there is no exit code for that value. */
	public static ErrorExitCode fromInt(int code) {
		return lookup.get(code);
	}
}
