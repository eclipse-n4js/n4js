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
package org.eclipse.n4js.utils.process;

import com.google.common.base.Strings;

/**
 * Representation of the result of a terminated process.
 */
public class ProcessResult {

	static String LN = System.lineSeparator();

	int exitCode;
	String stdOut;
	String stdErr;

	ProcessResult(int exitCode, String stdOut, String stdErr) {
		this.exitCode = exitCode;
		this.stdOut = Strings.nullToEmpty(stdOut);
		this.stdErr = Strings.nullToEmpty(stdErr);
	}

	/** Returns with {@code true} if the exit code is {@code 0}, otherwise {@code false}. */
	public boolean isOK() {
		return 0 == exitCode;
	}

	@Override
	public String toString() {
		return "Exit code:" + exitCode + LN + "Standard out:" + LN + stdOut + LN + "Standard error:" + LN + stdErr;
	}

	/**
	 * Creates {@link Throwable} instance with provided custom message and {@link #getStdErr error output} of this
	 * result.
	 */
	public Throwable toThrowable(String message) {
		Exception exc = new Exception(message + LN + LN + stdErr);
		exc.setStackTrace(new StackTraceElement[0]);
		return exc;
	}

	/***/
	public static String getLN() {
		return LN;
	}

	/***/
	public int getExitCode() {
		return exitCode;
	}

	/***/
	public String getStdOut() {
		return stdOut;
	}

	/***/
	public String getStdErr() {
		return stdErr;
	}

}
