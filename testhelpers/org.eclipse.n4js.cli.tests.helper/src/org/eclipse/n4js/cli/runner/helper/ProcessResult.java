/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.runner.helper;

/**
 * Data class that holds all information after {@code node.js} was executed
 */
public class ProcessResult {
	String command;
	String stdOut;
	String errOut;
	Exception exception;
	int exitCode;

	/** Constructor */
	public ProcessResult(String command) {
		this.command = command;
	}

	/** @return output of the nodejs process */
	public String getStdOut() {
		return stdOut;
	}

	/** @return error output of the nodejs process */
	public String getErrOut() {
		return errOut;
	}

	/** @return exception thrown while executing the nodejs process */
	public Exception getException() {
		return exception;
	}

	/** @return exit code of nodejs process */
	public int getExitCode() {
		return exitCode;
	}

	@Override
	public String toString() {
		String s = "Result:\n";
		s += "    command:   " + command + "\n";
		s += "    exit code: " + exitCode + "\n";
		s += exception == null ? "" : "    Exception: " + exception.getMessage() + "\n";
		s += "    std out:\n";
		s += (stdOut == null || stdOut.isBlank() ? "" : ">>>>\n" + stdOut + "\n<<<<\n");
		s += "    err out:\n";
		s += (errOut == null || errOut.isBlank() ? "" : ">>>>\n" + errOut + "\n<<<<\n");
		s += "Result End.\n";
		return s;
	}
}
