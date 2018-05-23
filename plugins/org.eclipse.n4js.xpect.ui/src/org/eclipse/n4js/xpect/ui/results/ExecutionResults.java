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
package org.eclipse.n4js.xpect.ui.results;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

/**
 * Keeps track of test execution status. Single test result is kept directly. Statuses for test suites are calculated.
 */
public class ExecutionResults {
	Map<Description, ExecutionStatus> allStatuses = new HashMap<>();
	Map<Description, Failure> allFailures = new HashMap<>();

	Map<Description, Description> parents = new HashMap<>();

	/**
	 * Initializes results based on provided {@link Description} object (assumes it is a root of the description tree).
	 *
	 * If any previous results were initialized in this instance, they will be deleted.
	 */
	public void initResults(Description desc) {
		allStatuses.clear();
		allFailures.clear();
		parents.clear();
		collectStatusesAndParents(desc);
	}

	private void collectStatusesAndParents(Description description) {
		allStatuses.put(description, ExecutionStatus.PENDING);
		description.getChildren().forEach(childDescription -> {
			allStatuses.put(childDescription, ExecutionStatus.PENDING);
			parents.put(childDescription, description);
			collectStatusesAndParents(childDescription);
		});
	}

	/**
	 * return execution status for given test or test suite.
	 */
	public ExecutionStatus getStatus(Description description) {
		return allStatuses.get(description);
	}

	/**
	 * Finds parent of provided {@link Description} and updates it status. Calls itself recursively to update whole
	 * parent chain.
	 *
	 * If provided status is {@link ExecutionStatus#FAILED} propagates failure through whole parent chain. Otherwise
	 * checks if all children of the parent are not {@link ExecutionStatus#PENDING} in which case updates parent to non
	 * {@link ExecutionStatus#PENDING} state (and applies the same for whole parent chain).
	 *
	 * Assumes that only test suites have children (tests).
	 */
	private void updateParents(Description description, ExecutionStatus status) {
		Description parent = parents.get(description);
		if (parent != null) {

			// propagate failure to parents
			if (status.equals(ExecutionStatus.FAILED)) {
				allStatuses.put(parent, status);
				updateParents(parent, status);
			} else {
				boolean allChildrenFinished = parent.getChildren().stream().map(desc -> allStatuses.get(desc))
						.noneMatch(st -> st.equals(ExecutionStatus.PENDING));
				if (allChildrenFinished) {
					boolean noBrokenChildren = parent.getChildren().stream().map(desc -> allStatuses.get(desc))
							.noneMatch(st -> st.equals(ExecutionStatus.FAILED));
					if (noBrokenChildren) {
						allStatuses.put(parent, ExecutionStatus.PASSED);
						updateParents(parent, ExecutionStatus.PASSED);
					} else {
						allStatuses.put(parent, ExecutionStatus.FAILED);
						updateParents(parent, ExecutionStatus.FAILED);
					}
				}
			}
		}
	}

	/** Mark execution of given {@link Description} as failed. Parent descriptions may be updated accordingly. */
	public void executionFailed(Failure failure) {
		Description description = failure.getDescription();
		allStatuses.put(description, ExecutionStatus.FAILED);
		allFailures.put(description, failure);
		updateParents(description, ExecutionStatus.FAILED);
	}

	/** Mark execution of given {@link Description} as started. Parent descriptions may be updated accordingly. */
	public void executionStarted(Description description) {
		allStatuses.put(description, ExecutionStatus.STARTED);
		updateParents(description, ExecutionStatus.STARTED);
	}

	/** Mark execution of given {@link Description} as Ignored. Parent descriptions may be updated accordingly. */
	public void executionIgnored(Description description) {
		allStatuses.put(description, ExecutionStatus.IGNORED);
		updateParents(description, ExecutionStatus.IGNORED);
	}

	/** Mark execution of given {@link Description} as passed. Parent descriptions may be updated accordingly. */
	public void testPassed(Description description) {
		allStatuses.put(description, ExecutionStatus.PASSED);
		updateParents(description, ExecutionStatus.PASSED);
	}

	/** Check if execution of given {@link Description} has failed. */
	public boolean hasFailed(Description description) {
		return allFailures.containsKey(description);
	}

	/** @return {{@link Failure} for given {@link Description} or null */
	public Failure getFailure(Description description) {
		return allFailures.get(description);
	}

}
