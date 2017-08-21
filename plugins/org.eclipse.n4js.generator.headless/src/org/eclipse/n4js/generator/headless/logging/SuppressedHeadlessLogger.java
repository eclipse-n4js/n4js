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
 * Suppressed logger, i.e. it is not logging anything and returns {@code false} for any inquiry if a given logging type
 * is enabled.
 */
@Singleton
public final class SuppressedHeadlessLogger extends HeadlessAbstractLogger {

	@Override
	public boolean isCreateDebugOutput() {
		return false;
	}

	@Override
	public boolean isVerbose() {
		return false;
	}

	@Override
	protected void println(String message) {
		// Noop
	}
}
