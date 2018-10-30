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
package org.eclipse.n4js.typesystem.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xsemantics.runtime.RuleFailedException;

import com.google.common.base.Joiner;

public final class Result<T> {

	private final boolean success;
	private final T value;
	private final String failureMessage;
	private final boolean custom;
	private final Result<?> cause;

	private Result(T value) {
		this.success = true;
		this.value = value;
		this.failureMessage = null;
		this.custom = false;
		this.cause = null;
	}

	private Result(String failureMessage, boolean custom, Result<?> cause) {
		this.success = false;
		this.value = null;
		this.failureMessage = failureMessage;
		this.custom = custom;
		this.cause = cause;
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFailure() {
		return !success;
	}

	public T getValue() {
		return value;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public boolean isCustom() {
		return custom;
	}

	public Result<?> getCause() {
		return cause;
	}

	public boolean isOrIsCausedByCustom() {
		return getCustomFailure() != null;
	}

	public String getPreferredFailureMessage() {
		Result<?> customFailure = getCustomFailure();
		String customMessage = customFailure != null ? customFailure.getFailureMessage() : null;
		return customMessage != null ? customMessage : failureMessage;
	}

	/**
	 * Will return the error message for the given chain of {@link RuleFailedException}s (i.e. exception <code>ex</code>
	 * and the exceptions chained via {@link RuleFailedException#getCause()}). This returns the priority messages
	 * (joined with "Caused by:" as separator) or, if there are no priority messages, the top-level message (i.e. the
	 * message of <code>ex</code>).
	 */
	public String getCombinedFailureMessage() {
		// collect all priority messages
		final List<String> prioMsgs = new ArrayList<>();
		Result<?> curr = this;
		while (curr != null) {
			if (curr.isFailure() && curr.isCustom()) {
				final String currMsg = curr.getFailureMessage();
				if (currMsg != null) {
					prioMsgs.add(prepareMessage(currMsg));
				}
			}
			curr = curr.getCause();
		}
		// if we found one or more priority messages, return those; otherwise simply take the top-level message
		return !prioMsgs.isEmpty() ? Joiner.on("\nCaused by:\n").join(prioMsgs) : prepareMessage(getFailureMessage());
	}

	private static final String prepareMessage(String message) {
		String result = message;
		result = UtilN4.trimPrefix(result, "failed: ");
		if (!result.endsWith(".")) {
			result += ".";
		}
		return result;
	}

	public Result<?> getCustomFailure() {
		if (!success && custom) {
			return this;
		}
		if (cause != null) {
			return cause.getCustomFailure();
		}
		return null;
	}

	public Result<T> trimCauses() {
		return isSuccess() ? this : new Result<>(failureMessage, custom, null);
	}

	public static Result<Boolean> success() {
		return success(Boolean.TRUE);
	}

	public static <T> Result<T> success(T value) {
		return new Result<>(value);
	}

	public static <T> Result<T> failure(String failureMessage, boolean custom, Result<?> cause) {
		return new Result<>(failureMessage, custom, cause);
	}

	public static <T> Result<T> failure(String failureMessage, Result<?> template) {
		if (!template.isFailure()) {
			throw new IllegalArgumentException("template is not a failure");
		}
		if (template.getFailureMessage() == null) {
			return new Result<>(failureMessage, template.custom, template.cause);
		}
		return new Result<>(failureMessage, false, template);
	}
}
