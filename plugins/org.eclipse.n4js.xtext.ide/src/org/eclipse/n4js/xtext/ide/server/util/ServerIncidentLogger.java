/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.util;

import org.eclipse.n4js.xtext.ide.server.DebugService;

import com.google.inject.Singleton;

/**
 * Writes diagnosis information as returned by {@link DebugService#getDebugInfo()} to disk when one of the
 * report-methods is invoked.
 * <p>
 * TODO GH-2002: this is TEMPORARY functionality for debugging that will be removed in the future.
 */
@Singleton
public class ServerIncidentLogger {

	/**
	 * Write a report file.
	 *
	 * @param msg
	 *            message to report
	 * @param includeDebugInfo
	 *            iff true the debug info will be written to the output as well
	 */
	public void report(String msg, boolean includeDebugInfo) {
		// report nothing, by default
	}

	/**
	 * Same as {@link #report(String, boolean)}, with additional parameter:
	 *
	 * @param baseFileName
	 *            file name prefix of generated log file
	 * @param msg
	 *            message to report
	 * @param includeDebugInfo
	 *            iff true the debug info will be written to the output as well
	 */
	public void reportWithFileBaseName(String baseFileName, String msg, boolean includeDebugInfo) {
		// report nothing, by default
	}

	/**
	 * Same as {@link #report(String, boolean)}, with {@code includeDebugInfo} set to {@code true}. Also including the
	 * stack trace of the given throwable.
	 *
	 * @param msg
	 *            message to report
	 * @param cause
	 *            a {@link Throwable} to report.
	 */
	public void reportError(String msg, Throwable cause) {
		// report nothing, by default
	}
}
