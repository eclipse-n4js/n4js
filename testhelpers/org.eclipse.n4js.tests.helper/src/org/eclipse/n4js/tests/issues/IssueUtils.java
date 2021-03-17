/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.issues;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.Issue;

/**
 * Utility methods for dealing with {@link Issue}s.
 */
public class IssueUtils {

	/**
	 * Uses the same format as {@link Issue#toString()}, but only emits last segment of 'uriToProblem', in order to
	 * avoid including an absolute path that might depend on the environment.
	 */
	public static String toString(Issue issue) {
		if (issue == null) {
			return "null";
		}
		StringBuilder result = new StringBuilder();
		Severity severity = issue.getSeverity();
		result.append(severity != null ? severity.name() : null);
		result.append(":").append(issue.getMessage());
		result.append(" (");
		URI uri = issue.getUriToProblem();
		if (uri != null) {
			result.append(uri.lastSegment());
		}
		result.append(" line : ").append(issue.getLineNumber())
				.append(" column : ").append(issue.getColumn());
		result.append(")");
		return result.toString();
	}
}
