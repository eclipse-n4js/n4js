/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.utils;

/** Same purpose as {@link InterruptedException}, but as a runtime exception. */
public final class InterruptedRuntimeException extends RuntimeException {
	/** See {@link InterruptedRuntimeException}. */
	public InterruptedRuntimeException(String message) {
		super(message);
	}
}
