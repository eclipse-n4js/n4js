/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.hlc.base.testing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.tester.events.TestEndedEvent;

/**
 * Simple object for capturing test session results. Simplistic implementation that captures whole
 * {@link TestEndedEvent}s and ignores all other information.
 */
public class TestResults {

	private final List<TestEndedEvent> allResults = new LinkedList<>();
	private final List<TestEndedEvent> skiped = new LinkedList<>();
	private final List<TestEndedEvent> errors = new LinkedList<>();

	/** Returns list of {@link TestEndedEvent} for tests that were skipped. */
	public List<TestEndedEvent> getSkipped() {
		return Collections.unmodifiableList(skiped);
	}

	/** Returns list of {@link TestEndedEvent} for tests that failed or had errors. */
	public List<TestEndedEvent> getFailed() {
		return Collections.unmodifiableList(errors);
	}

	/** Registers test event to the list of all events. */
	public void register(TestEndedEvent testResult) {
		allResults.add(testResult);
	}

	/** Registers test event to the list of all events and list of skipped events. */
	public void registerSkipped(TestEndedEvent testResult) {
		allResults.add(testResult);
		skiped.add(testResult);
	}

	/** Registers test event to the list of all events and list of failed events. */
	public void registerFailed(TestEndedEvent testResult) {
		allResults.add(testResult);
		errors.add(testResult);
	}

}
