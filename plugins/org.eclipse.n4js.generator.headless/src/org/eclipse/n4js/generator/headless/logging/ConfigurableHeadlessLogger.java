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
package org.eclipse.n4js.generator.headless.logging;

import com.google.inject.Singleton;

/**
 * Headless logger whose verbosity can be configured. Logs all messages to {@link System#out}.
 */
@Singleton
public final class ConfigurableHeadlessLogger extends HeadlessAbstractLogger {
	/** if set to true prints out processed files to standard out */
	private final boolean verbose;

	/** if set to true prints to standard out inform about what is currently processed. */
	private final boolean createDebugOutput;

	/** Configures behavior with passed parameters. Debug level implies verbose. */
	public ConfigurableHeadlessLogger(boolean verbose, boolean createDebugOutput) {
		if (createDebugOutput) {
			this.verbose = true;
			this.createDebugOutput = true;
		} else {
			this.verbose = verbose;
			this.createDebugOutput = createDebugOutput;
		}
	}

	/**
	 * Indicates whether or not debug information should be printed.
	 */
	@Override
	public boolean isCreateDebugOutput() {
		return createDebugOutput;
	}

	/**
	 * Indicates whether verbose logging is enabled.
	 */
	@Override
	public boolean isVerbose() {
		return verbose;
	}

	@Override
	protected void println(String message) {
		System.out.println(message);
	}

}
