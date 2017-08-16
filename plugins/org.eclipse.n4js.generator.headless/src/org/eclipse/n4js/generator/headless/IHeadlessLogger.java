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
package org.eclipse.n4js.generator.headless;

import org.eclipse.xtext.validation.Issue;

import com.google.inject.ImplementedBy;

/**
 * Interfaces of the logger(s) used in the headless case.
 */
@ImplementedBy(NoopHeadlessLogger.class)
public interface IHeadlessLogger {

	/**
	 * Prints the given debug message. Does not consider {@link #isCreateDebugOutput()}. This responsibility falls to
	 * the caller.
	 *
	 * @param message
	 *            the message to print
	 */
	public void debug(String message);

	/**
	 * Prints the given info message.
	 *
	 * @param message
	 *            the message to print
	 */
	public void info(String message);

	/**
	 * Prints the given issue.
	 *
	 * @param issue
	 *            the issue to print
	 */
	public void issue(Issue issue);

	/**
	 * Prints the given warning.
	 *
	 * @param message
	 *            the warning to print
	 */
	public void warn(String message);

	/**
	 * Prints the given error message.
	 *
	 * @param message
	 *            the message to print
	 */
	public void error(String message);

	/**
	 * Indicates whether or not debug information should be printed.
	 */
	public boolean isCreateDebugOutput();

	/**
	 * Indicates whether verbose logging is enabled.
	 */
	public boolean isVerbose();

}
