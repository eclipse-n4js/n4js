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

import org.eclipse.xtext.validation.Issue;

/**
 * Base logic of the headless logger. Allows customization in the concrete subtypes.
 */
abstract class HeadlessAbstractLogger implements IHeadlessLogger {

	/**
	 * Prints the given debug message (only if {@link #isCreateDebugOutput()} returns {@code true}).
	 *
	 * @param message
	 *            the message to print
	 */
	@Override
	public final void debug(String message) {
		if (isCreateDebugOutput())
			println("DEBUG: " + message);
	}

	/**
	 * Prints the given info message (only if {@link #isVerbose()} returns {@code true}).
	 *
	 * @param message
	 *            the message to print
	 */
	@Override
	public final void info(String message) {
		if (isVerbose())
			println("INFO: " + message);
	}

	/**
	 * Prints the given issue.
	 *
	 * @param issue
	 *            the issue to print
	 */
	@Override
	public final void issue(Issue issue) {
		println("@issue: " + issue);
	}

	/**
	 * Prints the given warning.
	 *
	 * @param message
	 *            the warning to print
	 */
	@Override
	public final void warn(String message) {
		println("WARN: " + message);
	}

	/**
	 * Prints the given error message.
	 *
	 * @param message
	 *            the message to print
	 */
	@Override
	public final void error(String message) {
		println("ERROR: " + message);
	}

	/** Allows concrete loggers to customize output logging. */
	protected abstract void println(String message);

}
