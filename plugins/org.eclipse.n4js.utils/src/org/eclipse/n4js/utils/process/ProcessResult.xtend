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
package org.eclipse.n4js.utils.process

import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*
import com.google.common.base.Strings

/**
 * Representation of the result of a terminated process.
 */
@Accessors(AccessorType.PUBLIC_GETTER)
class ProcessResult {

	val static LN = System.lineSeparator;

	val int exitCode;
	val String stdOut;
	val String stdErr;

	package new(int exitCode, String stdOut, String stdErr) {
		this.exitCode = exitCode;
		this.stdOut = Strings.nullToEmpty(stdOut);
		this.stdErr = Strings.nullToEmpty(stdErr);
	}

	/** Returns with {@code true} if the exit code is {@code 0}, otherwise {@code false}. */
	def isOK() {
		return 0 === exitCode;
	}

	@Override
	override toString() {
		"Exit code:" + exitCode + LN + "Standard out:" + LN + stdOut + LN + "Standard error:" + LN + stdErr;
	}

	/** Creates {@link Throwable} instance with provided custom message and {@link #getStdErr error output} of this result. */
	def Throwable toThrowable(String message) {
		val Exception exc = new Exception(message + LN + LN + stdErr);
		exc.setStackTrace(#[]);
		return exc;
	}

}
