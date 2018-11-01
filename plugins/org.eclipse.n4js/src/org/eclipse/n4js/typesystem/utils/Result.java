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

import com.google.common.base.Joiner;

public final class Result {

	private final boolean success;
	private final String failureMessage;
	private final boolean priority;
	private final Result cause;

	private Result() {
		this.success = true;
		this.failureMessage = null;
		this.priority = false;
		this.cause = null;
	}

	private Result(String failureMessage, boolean priority, Result cause) {
		this.success = false;
		this.failureMessage = failureMessage;
		this.priority = priority;
		this.cause = cause;
	}

	public boolean isSuccess() {
		return success;
	}

	public boolean isFailure() {
		return !success;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public boolean isPriority() {
		return priority;
	}

	public Result getCause() {
		return cause;
	}

	public boolean isOrIsCausedByPriority() {
		return getPriorityFailure() != null;
	}

	public String getPriorityFailureMessage() {
		Result priorityFailure = getPriorityFailure();
		String priorityMessage = priorityFailure != null ? priorityFailure.getFailureMessage() : null;
		return priorityMessage != null ? priorityMessage : failureMessage;
	}

	/**
	 * Will return the error message for this chain of failures (i.e. the receiving failure and the failures chained via
	 * {@link #getCause()}). This returns the priority messages (joined with "Caused by:" as separator) or, if there are
	 * no priority messages, the top-level message (i.e. the message of the receiving failure). Returns
	 * <code>null</code> if the receiving result is not a failure.
	 */
	public String getCompiledFailureMessage() {
		// collect all priority messages
		final List<String> prioMsgs = new ArrayList<>();
		Result curr = this;
		while (curr != null) {
			if (curr.isFailure() && curr.isPriority()) {
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
		if (message == null) {
			return null;
		}
		String result = message;
		result = UtilN4.trimPrefix(result, "failed: ");
		if (!result.endsWith(".")) {
			result += ".";
		}
		return result;
	}

	public Result getPriorityFailure() {
		if (!success && priority) {
			return this;
		}
		if (cause != null) {
			return cause.getPriorityFailure();
		}
		return null;
	}

	public Result trimCauses() {
		return isSuccess() ? this : new Result(failureMessage, priority, null);
	}

	public static Result success() {
		return new Result();
	}

	public static Result failure(String failureMessage, boolean priority, Result cause) {
		return new Result(failureMessage, priority, cause);
	}

	public static Result failure(String failureMessage, Result template) {
		if (!template.isFailure()) {
			throw new IllegalArgumentException("template is not a failure");
		}
		if (template.getFailureMessage() == null) {
			return new Result(failureMessage, template.priority, template.cause);
		}
		return new Result(failureMessage, false, template);
	}
}
