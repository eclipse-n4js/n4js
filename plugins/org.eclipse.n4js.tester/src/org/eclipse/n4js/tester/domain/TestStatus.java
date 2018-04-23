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
package org.eclipse.n4js.tester.domain;

/**
 * Enumeration of test statuses.
 */
public enum TestStatus {

	/** Test state representing that the test successfully passed. */
	PASSED("Passed"),

	/** State representing that the test was not executed while the test running phase. */
	SKIPPED("Skipped"),

	/** State representing a test skipped event due to missing implementation. */
	SKIPPED_NOT_IMPLEMENTED("Not implemented"),

	/** State indicating that a test was skipped because the preconditions did not match. */
	SKIPPED_PRECONDITION("Precondition not met"),

	/** State indicating that the test case was manually ignored from execution. */
	SKIPPED_IGNORE("Ignored"),

	/** State indicating that a test case assertion was reversed due some unimplemented feature or unfixed bug. */
	SKIPPED_FIXME("Skipped FIXME"),

	/** State representing that the test failed. */
	FAILED("Failed"),

	/** State indicating that the test failed with unexpected error. */
	ERROR("Error");

	private final String status;

	private TestStatus(final String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}

	/**
	 * Returns true if this status is ERROR or FAILED
	 */
	public boolean isFailedOrError() {
		return this == ERROR || this == FAILED;
	}

}
