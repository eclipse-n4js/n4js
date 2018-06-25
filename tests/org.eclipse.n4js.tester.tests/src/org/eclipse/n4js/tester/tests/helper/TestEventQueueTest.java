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
package org.eclipse.n4js.tester.tests.helper;

import static java.lang.Thread.sleep;

import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.events.SessionEndedEvent;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionStartedEvent;
import org.eclipse.n4js.tester.events.TestEndedEvent;
import org.eclipse.n4js.tester.events.TestPingedEvent;
import org.eclipse.n4js.tester.events.TestStartedEvent;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.MockResourceTesterModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Class for testing the {@link TestEventQueue test event queue} behavior.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { MockResourceTesterModule.class })
public class TestEventQueueTest extends AbstractTestTreeTest {

	@Inject
	private Provider<TestEventQueue> provider;

	@Inject
	private TesterEventBus bus;

	private TestEventQueue queue;

	/***/
	@Before
	public void before() {
		queue = provider.get();
	}

	/***/
	@Test
	public void testHappy() {
		final String sessionId = "sessionId";
		final String testId = "testId";
		queue.init(5);
		post(new SessionStartedEvent(sessionId));
		post(new TestStartedEvent(sessionId, testId, 0L));
		post(new TestPingedEvent(sessionId, testId, 0L));
		post(new TestEndedEvent(sessionId, testId, null));
		post(new SessionEndedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				"SessionStartedEvent|SID:sessionId|",
				"TestStartedEvent|SID:sessionId|TID:testId|",
				"TestPingedEvent|SID:sessionId|TID:testId|",
				"TestEndedEvent|SID:sessionId|TID:testId|",
				"SessionEndedEvent|SID:sessionId|");
	}

	/***/
	@Test
	public void testHappyPathAssertTwice() {
		final String sessionId = "sessionId";
		queue.init(1);
		post(new SessionStartedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				"SessionStartedEvent|SID:sessionId|");

		queue.assertEquals(
				sessionId,
				"SessionStartedEvent|SID:sessionId|");
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testQueueIsNotInitialized() {
		final String sessionId = "sessionId";
		queue.assertEquals(sessionId);
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testExpectedIsMoreThanActual() {
		final String sessionId = "sessionId";
		queue.init(1);
		post(new SessionStartedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				"SessionStartedEvent|SID:sessionId|",
				"SessionStartedEvent|SID:sessionId|");
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testExpectedIsLessThanActual() {
		final String sessionId = "sessionId";
		queue.init(2);
		post(new SessionStartedEvent(sessionId));
		post(new SessionStartedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				"SessionStartedEvent|SID:sessionId|");
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testExpectedNotEqualsActual() {
		final String sessionId = "sessionId";
		queue.init(1);
		post(new SessionStartedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				"SessionEndedEvent|SID:sessionId|");
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testExpectedIsNull() {
		final String sessionId = "sessionId";
		queue.init(1);
		post(new SessionStartedEvent(sessionId));
		queue.assertEquals(
				sessionId,
				(String) null);
	}

	/***/
	@Test
	public void testAssertFailedHappyPath() {
		final String sessionId = "sessionId";
		queue.init(2);
		post(new SessionStartedEvent(sessionId));
		post(new SessionFailedEvent(sessionId));
		queue.assertFailed(sessionId);
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testAssertFailedNoEvents() {
		final String sessionId = "sessionId";
		queue.init(0);
		queue.assertFailed(sessionId);
	}

	/***/
	@Test(expected = AssertionError.class)
	public void testAssertFailedNonMatchingTailEvent() {
		final String sessionId = "sessionId";
		queue.init(1);
		post(new SessionStartedEvent(sessionId));
		queue.assertFailed(sessionId);
	}

	private void post(Object event) {
		try {
			bus.post(event);
			sleep(100L);
		} catch (InterruptedException e) {
			throw new RuntimeException("Interrupted while sleeping thread after posting message on event bus.");
		}
	}

}
