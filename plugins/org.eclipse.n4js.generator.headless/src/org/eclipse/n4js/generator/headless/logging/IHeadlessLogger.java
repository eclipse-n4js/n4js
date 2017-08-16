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

import com.google.inject.ImplementedBy;

/**
 * Interface for the logger used in the headless case.
 */
@ImplementedBy(SuppressedHeadlessLogger.class)
public interface IHeadlessLogger {

	/** Prints the given debug message. */
	public void debug(String message);

	/** Prints the given info message. */
	public void info(String message);

	/** Prints the given issue. */
	public void issue(Issue issue);

	/** Prints the given warning. */
	public void warn(String message);

	/** Prints the given error message. */
	public void error(String message);

	/**
	 * Indicates whether or not debug logging is enabled.
	 */
	public boolean isCreateDebugOutput();

	/**
	 * Indicates whether verbose logging is enabled.
	 */
	public boolean isVerbose();

}
