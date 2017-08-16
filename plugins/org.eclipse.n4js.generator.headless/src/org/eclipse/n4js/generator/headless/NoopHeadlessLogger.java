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

import com.google.inject.Singleton;

/**
 * Silent logger, that is not logging anything and returns false for any inquiry if a given logging is enabled.
 */
@Singleton
public final class NoopHeadlessLogger extends HeadlessAbstractLogger {

	/**
	 * Indicates whether or not debug information should be printed.
	 */
	@Override
	public boolean isCreateDebugOutput() {
		return false;
	}

	/**
	 * Indicates whether verbose logging is enabled.
	 */
	@Override
	public boolean isVerbose() {
		return false;
	}

	@Override
	protected void println(String message) {
		// Noop
	}

}
