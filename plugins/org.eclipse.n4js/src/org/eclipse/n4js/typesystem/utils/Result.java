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

import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.UtilN4;

import com.google.common.base.Joiner;

/**
 * Result of subtype checking with {@link N4JSTypeSystem#subtype(RuleEnvironment, TypeArgument, TypeArgument)} and
 * similar methods. In case of failure, it might provide a human-readable failure message and a cause in form of another
 * instance of this class.
 */
public final class Result {

	private final boolean success;
	private final String failureMessage;
	private final boolean priority;
	private final Result cause;

	/** Creates an instance representing success. */
	private Result() {
		this.success = true;
		this.failureMessage = null;
		this.priority = false;
		this.cause = null;
	}

	/** Creates an instance representing failure. Message and cause are optional, i.e. may be <code>null</code>. */
	private Result(String failureMessage, boolean priority, Result cause) {
		this.success = false;
		this.failureMessage = failureMessage;
		this.priority = priority;
		this.cause = cause;
	}

	/** Tells if this result represents success. */
	public boolean isSuccess() {
		return success;
	}

	/** Tells if this result represents failure. */
	public boolean isFailure() {
		return !success;
	}

	/**
	 * Returns a human-readable failure message or <code>null</code> iff this result represents success OR no message is
	 * available.
	 */
	public String getFailureMessage() {
		return failureMessage;
	}

	/**
	 * Tells if this result is a failure AND was marked as a having a higher priority. Priority failures have their
	 * failure messages show up in the UI even if they are only the {@link #getCause() causes} of other, non-priority
	 * failures. For details about when exactly a priority failure's message will show up, see
	 * {@link #getCompiledFailureMessage()}.
	 */
	public boolean isPriority() {
		return priority;
	}

	/**
	 * Returns another failure that caused the receiving failure or <code>null</code> if the receiving result represents
	 * success OR does not have a cause.
	 */
	public Result getCause() {
		return cause;
	}

	/**
	 * Tells if this result represents failure AND either is a priority failure itself or is directly or indirectly
	 * caused by a priority failure.
	 */
	public boolean isOrIsCausedByPriority() {
		return getPriorityFailure() != null;
	}

	/**
	 * If {@link #isOrIsCausedByPriority()} returns true, this method will return the first priority failure (maybe the
	 * receiving result itself); otherwise <code>null</code> is returned.
	 */
	public Result getPriorityFailure() {
		if (!success && priority) {
			return this;
		}
		if (cause != null) {
			return cause.getPriorityFailure();
		}
		return null;
	}

	/**
	 * Returns the failure message of the result returned by {@link #getPriorityFailure()} (if non-<code>null</code>) or
	 * otherwise the receiving failure's message.
	 */
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

	/**
	 * Adds a default failure message to the receiving failure, either by
	 * <ul>
	 * <li>creating a copy of the receiving failure and setting its message to the given message (if the receiving
	 * failure does not have a failure message, i.e. {@link #getFailureMessage()} returns <code>null</code>), or by
	 * <li>creating a new failure result with the given message and setting the receiving failure as its cause (if the
	 * receiving failure has a non-<code>null</code> failure message).
	 * </ul>
	 * If the receiving result is not a failure, simply returns the receiving result unchanged.
	 */
	public Result setDefaultFailureMessage(String newFailureMessage) {
		if (!isFailure()) {
			return this;
		}
		return getFailureMessage() == null
				? new Result(newFailureMessage, priority, cause) // new result replacing 'this'
				: new Result(newFailureMessage, false, this); // new result added and refers to 'this' as its cause
	}

	/**
	 * If this result is a failure with a cause, returns a copy of this result without a cause; otherwise this result is
	 * returned unchanged.
	 */
	public Result trimCauses() {
		return isFailure() && getCause() != null ? new Result(failureMessage, priority, null) : this;
	}

	/**
	 * Create an instance representing success.
	 */
	public static Result success() {
		return new Result();
	}

	/**
	 * Create an instance representing failure.
	 *
	 * @param failureMessage
	 *            a human-readable message explaining the failure or <code>null</code> if not available.
	 * @param priority
	 *            whether this failure's message should be presented to the end-user even if this failure is only the
	 *            cause of another, higher-level failure. See {@link #getCompiledFailureMessage()}.
	 * @param cause
	 *            another failure that caused the newly created failure or <code>null</code> if not applicable.
	 */
	public static Result failure(String failureMessage, boolean priority, Result cause) {
		return new Result(failureMessage, priority, cause);
	}
}
