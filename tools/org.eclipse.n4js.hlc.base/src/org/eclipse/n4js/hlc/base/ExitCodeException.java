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

/**
 * Class Wrapping the information to shutdown VM in error-case. Remember to do user-output before throwing an instance
 * of this class.
 */
public class ExitCodeException extends Exception {

	private final ErrorExitCode exitCode;

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
	public ExitCodeException(ErrorExitCode exitCode) {
		super();
		this.exitCode = exitCode;
	}

	/**
	 */
	public ExitCodeException(ErrorExitCode exitCode, String string) {
		super(string);
		this.exitCode = exitCode;
	}

	/**
	 * @param exitCode
	 *            code to shutdown VM with
	 * @param cause
	 *            wrapped Exception.
	 */
	public ExitCodeException(ErrorExitCode exitCode, Throwable cause) {
		super(cause);
		this.exitCode = exitCode;
	}

	/**
	 * @param exitCode
	 *            code to shutdown VM with
	 * @param message
	 *            user presentable cause
	 * @param cause
	 *            wrapped Exception.
	 */
	public ExitCodeException(ErrorExitCode exitCode, String message, Throwable cause) {
		super(message, cause);
		this.exitCode = exitCode;
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 */
	public ExitCodeException(int code) {
		super();
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 */
	public ExitCodeException(int code, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 */
	public ExitCodeException(int code, String string) {
		super(string);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 * @param message
	 *            user presentable cause
	 * @param cause
	 *            wrapped Exception.
	 */
	public ExitCodeException(int code, String message, Throwable cause) {
		super(message, cause);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 * @param code
	 *            code to shutdown VM with
	 * @param cause
	 *            wrapped Exception.
	 */
	public ExitCodeException(int code, Throwable cause) {
		super(cause);
		this.exitCode = lookupExitCodeForInt(code);
	}

	/**
	 * @return if a message is set.
	 */
	public boolean hasMessage() {
		return getMessage() != null && getMessage().length() > 0;
	}

	final private static ErrorExitCode lookupExitCodeForInt(int code) {
		ErrorExitCode exitCode = ErrorExitCode.fromInt(code);
		if (exitCode == null) {
			throw new RuntimeException("Unrecognized compiler exit code " + code);
		}
		return exitCode;
	}

	/**
	 * @return explanation of the exit code
	 */
	public String explanationOfExitCode() {
		return " (" + this.exitCode.getExplanation() + ")";
	}

}