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
package org.eclipse.n4js.tooling.compare;

import com.google.common.base.Joiner;

/**
 * Tells if and how an implementation element differs from its corresponding API element.
 * <p>
 * Result of comparing the API and a particular implementation of a particular {@link ProjectComparisonEntry} via method
 * {@link ProjectCompareHelper#compareApiImpl(ProjectComparisonEntry, int)}.
 */
public class ProjectCompareResult {

	/** @see ProjectCompareResult */
	public enum Status {
		/** Implementation is equals to API in all relevant aspects. */
		EQUAL,
		/** Implementation differs from API, but in a legal way. */
		COMPLIANT,
		/** Implementation differs from API in an illegal way. */
		ERROR
	}

	/** The status. */
	public final Status status;
	/** The description or <code>null</code> if not available. */
	public final String description;

	private ProjectCompareResult(Status status, String... description) {
		this.status = status;
		this.description = description != null && description.length > 0 ? Joiner.on('\n').join(description) : null;
	}

	/** Create an instance with status {@link Status#EQUAL} and the given description. */
	public static final ProjectCompareResult equal(String... description) {
		return new ProjectCompareResult(Status.EQUAL, description);
	}

	/** Create an instance with status {@link Status#COMPLIANT} and the given description. */
	public static final ProjectCompareResult compliant(String... description) {
		return new ProjectCompareResult(Status.COMPLIANT, description);
	}

	/** Create an instance with status {@link Status#ERROR} and the given description. */
	public static final ProjectCompareResult error(String... description) {
		return new ProjectCompareResult(Status.ERROR, description);
	}
}
