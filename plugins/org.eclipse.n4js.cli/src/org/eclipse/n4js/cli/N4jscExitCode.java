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
package org.eclipse.n4js.cli;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

/**
 * Enum for headless compiler exit codes.
 *
 * Consider this enum <b>unordered</b> and do not rely order of declaration, i.e. do not use its ordinal, instead use
 * {@link N4jscExitCode#getExitCodeValue()} for int comparisons.
 */
public enum N4jscExitCode {
	/** Success */
	SUCCESS(0, ""),

	// General errors

	/** Unexpected error */
	ERROR_UNEXPECTED(1, "Unexpected error"),
	/** Feature is not implemented */
	NOT_IMPLEMENTED(2, "Feature is not implemented"),

	// Errors on N4jsc frontend

	/** Invalid command line options and/or arguments */
	CMD_LINE_PARSE_INVALID(10, "Invalid command line options and/or arguments"),
	/** Invalid argument */
	ARGUMENT_INVALID(11, "Invalid argument"),
	/** Invalid option */
	OPTION_INVALID(12, "Invalid option"),

	// Errors on N4jsc goal compile

	/** Error when assembling the test catalog */
	TEST_CATALOG_ASSEMBLATION_ERROR(110, "Error when assembling the test catalog"),

	;

	private static final Map<Integer, N4jscExitCode> lookup = new HashMap<>();

	static {
		for (N4jscExitCode ec : EnumSet.allOf(N4jscExitCode.class))
			lookup.put(ec.getExitCodeValue(), ec);
	}

	private final String explanation;
	private final int code;

	private N4jscExitCode(int code, String explanation) {
		this.code = code;
		this.explanation = explanation;
	}

	/** Get user readable explanation of the message. */
	public String getExplanation() {
		return this.explanation;
	}

	/** Get numeric value of the exit code. Note that this is different from enum ordinal. */
	public int getExitCodeValue() {
		return this.code;
	}

	/** @returns {@link N4jscExitCode} for the provided int code, or null there is no exit code for that value. */
	public static N4jscExitCode fromInt(int code) {
		return lookup.get(code);
	}

	/** @return a string used for the user output */
	public String toUserString() {
		String s = this == SUCCESS ? "SUCCESS" : "ERROR-" + code;
		s += Strings.isNullOrEmpty(explanation) ? "" : " (" + explanation + ")";
		return s;
	}
}
