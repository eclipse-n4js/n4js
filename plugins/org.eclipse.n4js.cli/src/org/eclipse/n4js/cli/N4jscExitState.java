/**
 * Copyright (c) 2020 NumberFour AG.
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
 * Exit state hold exit code and message information. An exit state will not terminate the n4jsc process abruptly, but
 * instead gracefully. In case the {@link N4jscExitState} is other than {@link #SUCCESS}, the message its message is
 * printed out and the process terminates with its exit code (can be other than zero).
 */
public class N4jscExitState {
	/** Singleton for plain success state without message for the user. */
	final static public N4jscExitState SUCCESS = new N4jscExitState(N4jscExitCode.SUCCESS);

	final N4jscExitCode exitCode;
	final String message;

	/** Constructor */
	public N4jscExitState(N4jscExitCode exitCode, String message) {
		this.exitCode = exitCode;
		this.message = message;
	}

	/** Constructor. Message is null. */
	public N4jscExitState(N4jscExitCode exitCode) {
		this(exitCode, null);
	}

	/** @return a message string for user output */
	public String getMessage() {
		return this.message;
	}

	/** @return if a message is set. */
	public boolean hasMessage() {
		return getMessage() != null && getMessage().length() > 0;
	}

	/** @return a string used for the user output */
	public String toUserString() {
		String s = exitCode.toUserString();
		if (hasMessage()) {
			s += ":  " + getMessage();
		}
		return s;
	}

}
