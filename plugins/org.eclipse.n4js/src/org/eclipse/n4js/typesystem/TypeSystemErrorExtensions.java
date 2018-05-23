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
package org.eclipse.n4js.typesystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xsemantics.runtime.RuleFailedException;
import org.eclipse.xsemantics.runtime.TraceUtils;

import com.google.common.base.Joiner;

/**
 * Some methods for handling errors in Xsemantics, error messages in particular. Main logic is in method
 * {@link #compileMessage(RuleFailedException)}.
 */
public class TypeSystemErrorExtensions {

	/**
	 * By default, the first error message in a chain of RuleFailedExceptions will be used; use this value as data to
	 * your error message to make it appear instead! If there exist several such messages, they will be joined with
	 * "Caused by:" as separator.
	 */
	public static final Object PRIORITY_ERROR = new Object();

	/**
	 * Returns true iff the type system error represented by exception 'e' is a priority error, i.e. if the error data
	 * is {@link #PRIORITY_ERROR}.
	 */
	public static final boolean isPriorityError(RuleFailedException e) {
		return e.getErrorInformations().stream().anyMatch(info -> info.getData() == PRIORITY_ERROR);
	}

	/**
	 * Returns true iff any exception in the given chain of {@link RuleFailedException}s (i.e. either 'e' itself or any
	 * of its {@link RuleFailedException#getPrevious() previous} RuleFailedExceptions) is a priority error as defined by
	 * {@link #isPriorityError(RuleFailedException)}.
	 */
	public static final boolean isOrCausedByPriorityError(RuleFailedException e) {
		return getFailureChainAsList(e).stream().anyMatch(currE -> isPriorityError(currE));
	}

	/**
	 * Will return the error message for the given chain of {@link RuleFailedException}s (i.e. exception <code>ex</code>
	 * and the exceptions chained via {@link RuleFailedException#getCause()}). This returns the priority messages
	 * (joined with "Caused by:" as separator) or, if there are no priority messages, the top-level message (i.e. the
	 * message of <code>ex</code>).
	 */
	public static final String compileMessage(RuleFailedException ex) {
		// collect all priority messages
		final List<String> prioMsgs = new ArrayList<>();
		Throwable curr = ex;
		while (curr instanceof RuleFailedException) {
			if (curr.getMessage() != null) {
				if (isPriorityError((RuleFailedException) curr)) {
					prioMsgs.add(prepareMessage(curr.getMessage()));
				}
			}
			curr = curr.getCause();
		}
		// if we found one or more priority messages, return those; otherwise simply take the top-level message
		return !prioMsgs.isEmpty() ? Joiner.on("\nCaused by:\n").join(prioMsgs) : prepareMessage(ex.getMessage());
	}

	private static final String prepareMessage(String message) {
		String result = message;
		result = UtilN4.trimPrefix(result, "failed: ");
		if (!result.endsWith(".")) {
			result += ".";
		}
		return result;
	}

	/**
	 * Same as {@link TraceUtils#failureAsList(RuleFailedException)}, but as a static method that does not require
	 * dependency injection.
	 */
	private static final List<RuleFailedException> getFailureChainAsList(RuleFailedException e) {
		final LinkedList<RuleFailedException> result = new LinkedList<>();
		RuleFailedException curr = e;
		while (curr != null) {
			if (curr.getMessage() != null)
				result.add(curr);
			curr = curr.getPrevious();
		}
		return result;
	}
}
