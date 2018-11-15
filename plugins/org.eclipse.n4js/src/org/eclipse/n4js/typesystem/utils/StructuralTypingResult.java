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
package org.eclipse.n4js.typesystem.utils;

import java.util.List;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import com.google.common.base.Strings;

/**
 * Result of a structural typing computed by {@link StructuralTypingComputer}, basically a message with some additional
 * hints.
 */
public class StructuralTypingResult {

	/** The result value. */
	public final boolean value;
	/** The failure message or <code>null</code>. */
	public final String message;

	final boolean isN4ObjectOnLeftWithDefSite;

	/**
	 * Convenience factory method, similar to {@code new StructuralTypingResult(false, message, true)}.
	 */
	public static StructuralTypingResult failureDefSiteWithN4Object(String message) {
		return new StructuralTypingResult(false, message, true);
	}

	/**
	 * Convenience factory method, similar to {@code new StructuralTypingResult(false, message, false)}.
	 */
	public static StructuralTypingResult failure(String message) {
		return new StructuralTypingResult(false, message, false);
	}

	/**
	 * Creates a result with failure (value=false) with appropriate error message, if the passed lists are non-empty;
	 * otherwise a success is assumed (value=true).
	 */
	public static StructuralTypingResult result(
			TypeRef left, TypeRef right,
			List<String> missingMembers, List<String> wrongMembersErrors) {
		if (missingMembers.isEmpty() && wrongMembersErrors.isEmpty()) {
			return success();
		} else {
			final StringBuilder sb = new StringBuilder();
			sb.append(left.getTypeRefAsString() + " is not a structural subtype of " + right.getTypeRefAsString()
					+ ": ");
			if (!missingMembers.isEmpty()) {
				sb.append("missing " + missingMembers.get(0));
				if (missingMembers.size() > 1) {
					sb.append(" and " + (missingMembers.size() - 1) + " more");
				}
			}
			if (!wrongMembersErrors.isEmpty()) {
				if (!missingMembers.isEmpty()) {
					sb.append("; ");
				}
				sb.append(wrongMembersErrors.get(0));
				if (wrongMembersErrors.size() > 1) {
					sb.append(" and " + (wrongMembersErrors.size() - 1) + " more problems");
				}
			}
			return failure(sb.toString());
		}
	}

	/**
	 * Convenience factory method, similar to {@code new StructuralTypingResult(true)}.
	 */
	public static StructuralTypingResult success() {
		return new StructuralTypingResult(true);
	}

	StructuralTypingResult(boolean value) {
		this(value, null, false);
	}

	StructuralTypingResult(boolean value, String message, boolean isN4ObjectOnLeftWithDefSite) {
		super();
		this.value = value;
		this.message = message;
		this.isN4ObjectOnLeftWithDefSite = isN4ObjectOnLeftWithDefSite;
	}

	/**
	 * If true, the result indicates a successful computation, otherwise it's a failure.
	 */
	public boolean isValue() {
		return value;
	}

	/**
	 * Convenience method returning true if message is null or empty.
	 */
	public boolean hasMessage() {
		return !Strings.isNullOrEmpty(message);
	}

	/**
	 * The message, may be null (e.g., in case of success).
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Special hint that computation failed due to an N4Object derived class used on the right hand side. This reflects
	 * the special constraints for preventing N4Object classes used with structural typing when it would be possible to
	 * explicitly declare the class to implement/extend an interface or class.
	 */
	public boolean isN4ObjectOnLeftWithDefSite() {
		return isN4ObjectOnLeftWithDefSite;
	}

}
