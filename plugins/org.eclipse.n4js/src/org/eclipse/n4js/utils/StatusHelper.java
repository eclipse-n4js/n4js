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
package org.eclipse.n4js.utils;

import static org.eclipse.core.runtime.IStatus.CANCEL;
import static org.eclipse.core.runtime.IStatus.ERROR;
import static org.eclipse.core.runtime.IStatus.INFO;
import static org.eclipse.n4js.N4JSPluginId.N4JS_PLUGIN_ID;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.google.common.base.Strings;
import com.google.inject.Singleton;

/**
 * Helper class for creating {@link IStatus status} instances.
 */
@Singleton
public class StatusHelper {

	private static final int NO_CODE = 0;

	/**
	 * Returns with the {@link Status#OK_STATUS OK status} instance.
	 *
	 * @return the shared singleton OK status.
	 */
	public IStatus OK() {
		return Status.OK_STATUS;
	}

	/**
	 * Returns with the {@link Status#CANCEL_STATUS CANCEL status} instance.
	 *
	 * @return the shared singleton CANCEL status.
	 */
	public IStatus cancel() {
		return Status.CANCEL_STATUS;
	}

	/**
	 * Creates a new {@link IStatus#CANCEL status} status with the given message.
	 *
	 * @param message
	 *            the message of the new status.
	 * @return a new info status with the given message.
	 */
	public IStatus createCancel(final String message) {
		return new Status(CANCEL, N4JS_PLUGIN_ID, message);
	}

	/**
	 * Creates a new {@link IStatus#INFO info} status with the given message.
	 *
	 * @param message
	 *            the message of the new status.
	 * @return a new info status with the given message.
	 */
	public IStatus createInfo(final String message) {
		return new Status(INFO, N4JS_PLUGIN_ID, message);
	}

	/**
	 * Creates a new {@link IStatus#ERROR error} status with the given message.
	 */
	public IStatus createError(final String message) {
		return createError(message, NO_CODE);
	}

	/**
	 * Creates a new {@link IStatus#ERROR error} status with the given message and status code.
	 */
	public IStatus createError(final String message, int code) {
		return createError(message, code, null);
	}

	/**
	 * Creates a new error status with the {@link Throwable#getMessage() message} of the given cause.
	 */
	public IStatus createError(final Throwable t) {
		return createError(Strings.nullToEmpty(t.getMessage()), t);
	}

	/**
	 * Creates a new {@link IStatus#ERROR error} status with the given message and the throwable cause. <br>
	 * The throwable argument is optional, hence it can be {@code null}.
	 */
	public IStatus createError(final String message, final Throwable t) {
		return createError(message, NO_CODE, t);
	}

	/**
	 * Creates a new {@link IStatus#ERROR error} status with the given message and the throwable cause. <br>
	 * The throwable argument is optional, hence it can be {@code null}.
	 */
	public IStatus createError(final String message, int code, final Throwable t) {
		return new Status(ERROR, N4JS_PLUGIN_ID, code, message, t);
	}

	/**
	 * Creates a new {@link MultiStatus multi-status} with the given error message error severity. The new status will
	 * have zero child status instances and {@code null} cause.
	 */
	public MultiStatus createMultiError(final String message) {
		return createMultiError(message, null);
	}

	/**
	 * Creates a new {@link MultiStatus multi-status} with error severity with the given message and {@link Throwable
	 * cause}. The new multi-status will have zero child status instances.
	 */
	public MultiStatus createMultiError(final String message, final Throwable cause) {
		return new MultiStatus(N4JS_PLUGIN_ID, ERROR, message, cause);
	}

	/**
	 * Creates a new {@link MultiStatus multi-status} with the given message and {@link IStatus#OK} severity. The new
	 * status will have zero child status instances and {@code null} cause. Intended as optimistic start point for multi
	 * status operations.
	 */
	public MultiStatus createMultiStatus(final String message) {
		return new MultiStatus(N4JS_PLUGIN_ID, NO_CODE, message, null);
	}

}
