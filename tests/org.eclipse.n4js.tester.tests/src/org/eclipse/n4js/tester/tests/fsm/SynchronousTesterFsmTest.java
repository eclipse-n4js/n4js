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
package org.eclipse.n4js.tester.tests.fsm;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.synchronizedCollection;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.events.SessionFailedEvent;
import org.eclipse.n4js.tester.events.SessionFinishedEvent;
import org.eclipse.n4js.tester.fsm.TestFsm;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.SyncMockTesterModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Synchronously tests the FSM through the {@link TestFsmRegistry FSM registry}.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { SyncMockTesterModule.class })
public class SynchronousTesterFsmTest extends AbstractTestTreeTest {

	private static final Logger LOGGER = Logger.getLogger(SynchronousTesterFsmTest.class);

	private static final String SESSION_ID_1 = "session_id_1";
	private static final String TEST_ID_1 = "test_id_1";
	private static final String TEST_ID_2 = "test_id_2";
	private static final String TEST_ID_3 = "test_id_3";

	/** Used to access the test method's name for logging purposes. */
	@Rule
	public TestName name = new TestName();

	@Inject
	private TestFsmRegistry registry;

	@Inject
	private TesterEventBus bus;

	private final Collection<String> completedSessionIds = synchronizedCollection(newHashSet());
	private final Collection<String> failedSessionIds = synchronizedCollection(newHashSet());
	private CountDownLatch latch;

	/**
	 * Clears the session ID caches. Registers the test to the event bus.
	 */
	@Before
	public void before() {
		LOGGER.info("*********  " + name.getMethodName() + "  *********");
		latch = new CountDownLatch(1);
		completedSessionIds.clear();
		failedSessionIds.clear();
		bus.register(this);
	}

	/**
	 * Unregisters the test from the event bus.
	 */
	@After
	public void after() {
		bus.unregister(this);
		LOGGER.info("\n\n\n");
	}

	/**
	 * Test for testing happy path in the FSM.
	 */
	@Test
	public void testHappyPath() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.startTest(TEST_ID_1, 1200L).endTest(TEST_ID_1)
				.endSession(SESSION_ID_1);

		await(10L);
		assertTrue(completedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * For checking the happy path test case with ping transitions.
	 */
	@Test
	public void testHappyPathWithPing() {
		final TestFsm fsm = registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.pingSession(SESSION_ID_1, 500L);
		sleep(300L);
		fsm.pingSession(SESSION_ID_1, 1000L);
		sleep(800L);
		fsm.startTest(TEST_ID_1, 1200L).pingTest(TEST_ID_1, 2000L);
		sleep(1500L);
		fsm.pingTest(TEST_ID_1, 100L);
		sleep(50L);
		fsm.endTest(TEST_ID_1).endSession(SESSION_ID_1);

		await(10L);
		assertTrue(completedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Testing the setup timeout of the FSM.
	 */
	@Test
	public void testSetupTimout() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1);

		sleep(SECONDS.toMillis(2L));
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test expecting a FSM failure due to setup timeout after incorrectly decreasing the default timeout.
	 */
	@Test
	public void testPingWithSetupTimeoutAdjustment() {
		final TestFsm fsm = registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.pingSession(SESSION_ID_1, 500L);
		sleep(300L);
		fsm.pingSession(SESSION_ID_1, 1000L);
		sleep(1500L);

		await(10L);
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * For testing the default timeout in the FSM.
	 */
	@Test
	public void testDefaultTimeout() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1);

		sleep(SECONDS.toMillis(2L));
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test expecting a FSM failure due to default timeout after incorrectly decreasing the default timeout.
	 */
	@Test
	public void testPingWithDefaultTimeoutAdjustment() {
		final TestFsm fsm = registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1);
		fsm.startTest(TEST_ID_1, 1200L).pingTest(TEST_ID_1, 1200L);
		sleep(100L);
		fsm.pingTest(TEST_ID_1, 100L);
		sleep(200L);
		fsm.endTest(TEST_ID_1).endSession(SESSION_ID_1);

		await(10L);
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * For testing the incorrect state transitions.
	 */
	@Test
	public void testIncorrectFstStateTransition() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1).startSession(SESSION_ID_1);

		await(10L);
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test for checking that a test cannot run more than once.
	 */
	@Test
	public void testRunSameTestTwice() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1).startTest(TEST_ID_1, 1200L).endTest(TEST_ID_1)
				.startTest(TEST_ID_1, 1200L);

		await(10L);
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test for checking the state transition behavior in the FSM by starting parallel tests.
	 */
	@Test
	public void testParallelFsmStateTransitions() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.startTest(TEST_ID_1, 1000L)
				.startTest(TEST_ID_2, 1000L).startTest(TEST_ID_3, 1000L).endTest(TEST_ID_3).endTest(TEST_ID_2)
				.endTest(TEST_ID_1).endSession(SESSION_ID_1);

		await(10L);
		assertTrue(completedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test for checking the state transition behavior in the FSM by starting parallel tests with test pinging
	 */
	@Test
	public void testParallelFsmStateTransitionsWithPingAdjustment() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.startTest(TEST_ID_1, 1000L)
				.startTest(TEST_ID_2, 1000L).startTest(TEST_ID_3, 1000L).pingTest(TEST_ID_2, 1000L)
				.pingTest(TEST_ID_1, 1000L).pingTest(TEST_ID_3, 1000L).endTest(TEST_ID_3).endTest(TEST_ID_2)
				.endTest(TEST_ID_1).endSession(SESSION_ID_1);

		await(10L);
		assertTrue(completedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Test for checking the state transition behavior in the FSM by starting parallel tests with test pinging
	 */
	@Test
	public void testParallelFsmStateTransitionsExpectFailureDueToEndsBeforeThanStarts() {
		registry.registerFsm(SESSION_ID_1).startSession(SESSION_ID_1)
				.startTest(TEST_ID_1, 1000L)
				.startTest(TEST_ID_2, 1000L).endTest(TEST_ID_3).startTest(TEST_ID_3, 1000L).endTest(TEST_ID_2)
				.endTest(TEST_ID_1).endSession(SESSION_ID_1);

		await(10L);
		assertTrue(failedSessionIds.contains(SESSION_ID_1));
	}

	/**
	 * Percepts a test session failure event, registers the session ID of the failed session.
	 *
	 * @param event
	 *            the failure event.
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void sessionFailed(final SessionFailedEvent event) {
		LOGGER.info("Received test session failed event. "
				+ (event.getComment().isPresent() ? event.getComment().get() : ""));
		failedSessionIds.add(event.getSessionId());
		countDown();
	}

	/**
	 * Percepts a test session completed event, registers the session ID of the completed session.
	 *
	 * @param event
	 *            the session completed event.
	 */
	@Subscribe
	@AllowConcurrentEvents
	public void sessionCompleted(final SessionFinishedEvent event) {
		LOGGER.info("Received test session finished event. Session ID: '" + event.getSessionId() + "'.");
		completedSessionIds.add(event.getSessionId());
		countDown();
	}

	private void await(final long seconds) {
		try {
			latch.await(seconds, SECONDS);
		} catch (final InterruptedException e) {
			throw new RuntimeException("Time outed while waiting for event from tester event bus.", e);
		}
	}

	private void countDown() {
		latch.countDown();
	}

	private void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			throw new RuntimeException("Interrupted while sleeping '" + millis + "' milliseconds.", e);
		}
	}

	@Override
	public String toString() {
		return "Tester FSM test";
	}

}
