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
package org.eclipse.n4js.utils;

/**
 * Lightweight exception, stacktrace is not created when this exception is thrown, also its stacktrace cannot be
 * {@link #fillInStackTrace() filled in}.
 */
public class LightweightException extends Exception {

	/** see {@link Throwable#addSuppressed} */
	private static boolean ENABLE_SUPPRESSION = true;
	/** see {@link Throwable#fillInStackTrace()} */
	private static boolean WRITABLE_STACKTRACE = false;

	/** Creates exception with the provided message and {@link Throwable#getCause() null cause} */
	public LightweightException(String message) {
		this(message, null, ENABLE_SUPPRESSION, WRITABLE_STACKTRACE);
	}

	/** Creates exception with the provided cause and {@link Throwable#getMessage() null message} */
	public LightweightException(Throwable cause) {
		this(null, cause, ENABLE_SUPPRESSION, WRITABLE_STACKTRACE);
	}

	/** Creates exception with the provided cause and {@link Throwable#getMessage() null message} */
	public LightweightException(String message, Throwable cause) {
		this(message, cause, ENABLE_SUPPRESSION, WRITABLE_STACKTRACE);
	}

	private LightweightException(String message, Throwable cause,
			boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@SuppressWarnings("sync-override")
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return new StackTraceElement[0];
	}

}
