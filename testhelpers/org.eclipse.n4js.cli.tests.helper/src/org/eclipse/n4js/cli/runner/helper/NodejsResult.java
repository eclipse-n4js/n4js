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
 *
 */
public class NodejsResult {

	String stdOut;
	String errOut;
	Exception exception;
	int exitCode;

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

}
