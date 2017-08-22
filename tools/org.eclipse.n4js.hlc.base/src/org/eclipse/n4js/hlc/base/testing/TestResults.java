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
 *
 */
public class TestResults {

	private final List<TestEndedEvent> allResults = new LinkedList<>();
	private final List<TestEndedEvent> skiped = new LinkedList<>();
	private final List<TestEndedEvent> errors = new LinkedList<>();

	public List<TestEndedEvent> getSkipped() {
		return Collections.unmodifiableList(skiped);
	}

	public List<TestEndedEvent> getFailed() {
		return Collections.unmodifiableList(errors);
	}

	public void register(TestEndedEvent testResult) {
		allResults.add(testResult);
	}

	public void registerSkipped(TestEndedEvent testResult) {
		allResults.add(testResult);
		skiped.add(testResult);
	}

	public void registerFailed(TestEndedEvent testResult) {
		allResults.add(testResult);
		errors.add(testResult);
	}

}
