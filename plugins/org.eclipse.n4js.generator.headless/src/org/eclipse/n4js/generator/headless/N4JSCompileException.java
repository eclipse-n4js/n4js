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
package org.eclipse.n4js.generator.headless;

import java.io.PrintStream;

/**
 */
public class N4JSCompileException extends Exception {

	/**
	 *
	 */
	public N4JSCompileException() {
	}

	/**
	 */
	public N4JSCompileException(String message) {
		super(message);
	}

	/**
	 */
	public N4JSCompileException(Throwable cause) {
		super(cause);
	}

	/**
	 */
	public N4JSCompileException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 */
	public N4JSCompileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param stream
	 *            stream to print to
	 */
	public void userDump(PrintStream stream) {
		stream.println(getMessage());
	}

}
