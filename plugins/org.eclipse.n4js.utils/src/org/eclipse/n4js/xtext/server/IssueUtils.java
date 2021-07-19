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
package org.eclipse.n4js.xtext.server;

import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

import com.google.common.base.Objects;

/**
 * Utility methods for {@link Issue}
 */
public class IssueUtils {

	/** Missing {@link Object#equals(Object)} override of {@link IssueImpl} */
	static public boolean equals(Object obj1, Object obj2) {
		if (!(obj1 instanceof Issue)) {
			return false;
		}
		if (!(obj2 instanceof Issue)) {
			return false;
		}
		Issue n4Issue1 = (Issue) obj1;
		Issue n4Issue2 = (Issue) obj2;
		boolean equals = true;
		equals &= Objects.equal(n4Issue1.getOffset(), n4Issue2.getOffset());
		equals &= Objects.equal(n4Issue1.getLength(), n4Issue2.getLength());
		equals &= Objects.equal(n4Issue1.getColumn(), n4Issue2.getColumn());
		equals &= Objects.equal(n4Issue1.getColumnEnd(), n4Issue2.getColumnEnd());
		equals &= Objects.equal(n4Issue1.getLineNumber(), n4Issue2.getLineNumber());
		equals &= Objects.equal(n4Issue1.getLineNumberEnd(), n4Issue2.getLineNumberEnd());
		equals &= Objects.equal(n4Issue1.getCode(), n4Issue2.getCode());
		equals &= Objects.equal(n4Issue1.getMessage(), n4Issue2.getMessage());
		equals &= n4Issue1.getUriToProblem() == n4Issue2.getUriToProblem();
		equals &= n4Issue1.getSeverity() == n4Issue2.getSeverity();
		equals &= n4Issue1.getType() == n4Issue2.getType();
		return equals;
	}

	/** Missing {@link Object#hashCode()} override of {@link IssueImpl} */
	static public int hashCode(Issue issue) {
		return Objects.hashCode(issue.getOffset(), issue.getLength(), issue.getColumn(), issue.getColumnEnd(),
				issue.getLineNumber(), issue.getLineNumberEnd(), issue.getCode(), issue.getMessage(),
				issue.getUriToProblem(), issue.getSeverity(), issue.getType());
	}
}
