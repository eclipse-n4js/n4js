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

/**
 * Class Wrapping the information to shutdown VM in error-case. Remember to do user-output before throwing an instance
 * of this class.
 */
public class N4jscException extends Exception {

	private final N4jscExitCode exitCode;

	/**
	 * @return code to exit VM with.
	 */
	public int getExitCode() {
		return exitCode.getExitCodeValue();
	}

	/**
	 * @param exitCode
	 *            code to shutdown VM with
	 */
	public N4jscException(N4jscExitCode exitCode) {
		super();
		this.exitCode = exitCode;
	}

	/**
	 */
	public N4jscException(N4jscExitCode exitCode, String string) {
		super(string);
		this.exitCode = exitCode;
	}

	/**
	 * @param exitCode
	 *            code to shutdown VM with
	 * @param cause
	 *            wrapped Exception.
	 */
	public N4jscException(N4jscExitCode exitCode, Throwable cause) {
		super(cause);
		this.exitCode = exitCode;
	}

	/**
	 * @param exitCode
	 *            code to shutdown VM with
	 * @param message
	 *            description for user
	 * @param cause
	 *            wrapped Exception.
	 */
	public N4jscException(N4jscExitCode exitCode, String message, Throwable cause) {
		super(message, cause);
		this.exitCode = exitCode;
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 */
	public N4jscException(int code) {
		super();
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 */
	public N4jscException(int code, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 */
	public N4jscException(int code, String string) {
		super(string);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 * @param message
	 *            description for user
	 * @param cause
	 *            wrapped Exception.
	 */
	public N4jscException(int code, String message, Throwable cause) {
		super(message, cause);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 * @param cause
	 *            wrapped Exception.
	 */
	public N4jscException(int code, Throwable cause) {
		super(cause);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/** @return if a message is set. */
	public boolean hasMessage() {
		return getMessage() != null && getMessage().length() > 0;
	}

	final private static N4jscExitCode lookupExitCodeForInt(int code) {
		N4jscExitCode exitCode = N4jscExitCode.fromInt(code);
		if (exitCode == null) {
			throw new RuntimeException("Unrecognized compiler exit code " + code);
		}
		return exitCode;
	}

	/** @return explanation of the exit code */
	public String explanationOfExitCode() {
		return " (" + this.exitCode.getExplanation() + ")";
	}

	/** @return a string used for the user output */
	public String toUserString() {
		String s = exitCode.toUserString();
		if (hasMessage()) {
			String msg = getMessage();
			if (getCause() != null) {
				msg = getCause().getMessage();
			}
			s += ":  " + msg;
		}
		return s;
	}

}
