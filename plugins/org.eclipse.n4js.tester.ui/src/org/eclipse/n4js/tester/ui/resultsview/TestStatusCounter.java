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
package org.eclipse.n4js.tester.ui.resultsview;

import static org.eclipse.n4js.tester.domain.TestStatus.ERROR;
import static org.eclipse.n4js.tester.domain.TestStatus.FAILED;
import static org.eclipse.n4js.tester.domain.TestStatus.PASSED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_FIXME;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_IGNORE;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_NOT_IMPLEMENTED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED_PRECONDITION;
import static org.eclipse.n4js.tester.domain.TestStatus.values;

import java.util.Arrays;

import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.swt.SWT;

/**
 * Counter to keep track of how many tests have passed, failed, etc. Such a counter will be used for every non-leaf node
 * in a tree of {@link ResultNode}s.
 */
public class TestStatusCounter {
	private final int[] count = new int[values().length];

	/**
	 * Reset all counters to 0.
	 */
	public void clear() {
		Arrays.fill(count, 0);
	}

	/**
	 * Increment counter for given status. Status may not be <code>null</code>.
	 */
	public void increment(TestStatus status) {
		count[status.ordinal()]++;
	}

	/**
	 * Increment all counters by the corresponding values in <code>other</code>.
	 */
	public void increment(TestStatusCounter other) {
		for (int i = 0; i < count.length; i++)
			count[i] += other.count[i];
	}

	/**
	 * Get counter value for given status. Status may not be <code>null</code>.
	 */
	public int getCount(TestStatus... testStatus) {
		int sum = 0;
		for (TestStatus status : testStatus) {
			sum += count[status.ordinal()];
		}
		return sum;
	}

	/**
	 * Get total across all counters.
	 */
	public int getTotal() {
		int result = 0;
		for (int i = 0; i < count.length; i++)
			result += count[i];
		return result;
	}

	/**
	 * Get resulting overall status, i.e. the "worst" status with a non-zero counter value.
	 */
	public TestStatus getAggregatedStatus() {
		if (getCount(ERROR) > 0) {
			return ERROR;
		}
		if (getCount(FAILED) > 0) {
			return FAILED;
		}
		if (getCount(SKIPPED_FIXME) > 0) {
			return SKIPPED_FIXME;
		}
		if (getCount(PASSED) > 0) {
			return PASSED;
		}
		if (getCount(SKIPPED) > 0) {
			return SKIPPED;
		}
		if (getCount(SKIPPED_NOT_IMPLEMENTED) > 0) {
			return SKIPPED_NOT_IMPLEMENTED;
		}
		if (getCount(SKIPPED_PRECONDITION) > 0) {
			return SKIPPED_PRECONDITION;
		}
		if (getCount(SKIPPED_IGNORE) > 0) {
			return SKIPPED_IGNORE;
		}
		return null;
	}

	/**
	 * Returns the first SKIP like status
	 */
	public TestStatus containsSkipped() {
		if (getCount(SKIPPED_FIXME) > 0) {
			return SKIPPED_FIXME;
		}
		if (getCount(SKIPPED) > 0) {
			return SKIPPED;
		}
		if (getCount(SKIPPED_NOT_IMPLEMENTED) > 0) {
			return SKIPPED_NOT_IMPLEMENTED;
		}
		if (getCount(SKIPPED_PRECONDITION) > 0) {
			return SKIPPED_PRECONDITION;
		}
		if (getCount(SKIPPED_IGNORE) > 0) {
			return SKIPPED_IGNORE;
		}
		if (getCount(PASSED) > 0) {
			return PASSED;
		}
		return null;
	}

	@Override
	public String toString() {
		return toString(true, -1, SWT.RIGHT);
	}

	/**
	 * Same as {@link #toString()}, but formatting can be fine tuned and number of pending tests can be included in
	 * string (optional).
	 *
	 * @param showSkipped
	 *            If true, will also show how many tests have been skipped.
	 * @param pending
	 *            If greater than 0, will also show this value in the string as the number of pending tests. Use 0 or
	 *            negative value to hide this information.
	 * @param alignment
	 *            Alignment of the additional information on skipped and pending tests: one of {@link SWT#LEFT},
	 *            {@link SWT#RIGHT}, or {@link SWT#NONE} (will hide all extra information).
	 */
	public String toString(boolean showSkipped, int pending, int alignment) {
		final int skipped = showSkipped
				? getCount(SKIPPED, SKIPPED_NOT_IMPLEMENTED, SKIPPED_PRECONDITION, SKIPPED_IGNORE, SKIPPED_FIXME)
				: 0;
		final StringBuffer sb = new StringBuffer();
		if (alignment == SWT.LEFT && (skipped > 0 || pending > 0)) {
			sb.append("(");
			if (pending > 0)
				sb.append(pending + " pending");
			if (pending > 0 && skipped > 0)
				sb.append(", ");
			if (skipped > 0)
				sb.append(skipped + " skipped");
			sb.append(")  ");
		}
		sb.append(getCount(PASSED));
		sb.append(" | ");
		sb.append(getCount(FAILED));
		sb.append(" | ");
		sb.append(getCount(ERROR));
		if (alignment == SWT.RIGHT && (skipped > 0 || pending > 0)) {
			sb.append("  (");
			if (skipped > 0)
				sb.append("skipped " + skipped);
			if (pending > 0 && skipped > 0)
				sb.append(", ");
			if (pending > 0)
				sb.append("pending " + pending);
			sb.append(")");
		}
		return sb.toString();
	}
}
