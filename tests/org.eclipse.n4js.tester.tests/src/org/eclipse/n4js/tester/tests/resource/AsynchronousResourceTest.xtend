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
package org.eclipse.n4js.tester.tests.resource

import org.eclipse.n4js.tester.TesterModule
import org.eclipse.n4js.tester.tests.InjectedModules
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner
import org.eclipse.n4js.tester.tests.MockTesterModule
import org.junit.Test
import org.junit.runner.RunWith

import static java.lang.Thread.*
import static java.util.concurrent.TimeUnit.*
import org.junit.rules.TestName
import org.junit.Rule
import com.google.inject.Inject

/**
 * Class for testing the logic and the behavior of the test sessions and the associated tester
 * finite state machines by invoking the RESTful API via a mock tester module binding.
 */
@RunWith(JUnitGuiceClassRunner)
@InjectedModules(baseModules = #[TesterModule], overrides = #[MockTesterModule])
class AsynchronousResourceTest extends BaseResourcesTest {

	@Rule
	@Inject
	public TestName testName;
	
	def private String id(String suffix) {
		return testName.methodName + "-" + suffix;
	} 

	@Test
	public def void testHappyPath() throws InterruptedException {

		val sessionId = id("sessionId");
		val testId = id("testId");
		val timeout = 1000L;

		queue.init(6);

		startSession(sessionId);
		startTest(sessionId, testId, timeout);
		pingTest(sessionId, testId, timeout);
		endTest(sessionId, testId);
		endSession(sessionId);

		queue.assertSucceeded('''«sessionId»''', events.get('''«sessionId»'''));
	}

	@Test
	public def void testParallelHappyPath() {
		val sessionId_1 = id("sessionId_1");
		val sessionId_2 = id("sessionId_2");
		val sessionId_3 = id("sessionId_3");
		val testId_1 = id("testId_1");
		val testId_2 = id("testId_2");
		val timeout = 1000L;

		queue.init(24);

		startSession(sessionId_1);
		startSession(sessionId_2);
		startSession(sessionId_3);

		startTest(sessionId_1, testId_1, timeout);
		startTest(sessionId_2, testId_1, timeout);
		startTest(sessionId_3, testId_1, timeout);

		pingTest(sessionId_1, testId_1, timeout);
		pingTest(sessionId_2, testId_1, timeout);
		pingTest(sessionId_3, testId_1, timeout);

		endTest(sessionId_1, testId_1);
		endTest(sessionId_2, testId_1);
		endTest(sessionId_3, testId_1);

		startTest(sessionId_1, testId_2, timeout);
		startTest(sessionId_2, testId_2, timeout);
		startTest(sessionId_3, testId_2, timeout);

		endTest(sessionId_1, testId_2);
		endTest(sessionId_2, testId_2);
		endTest(sessionId_3, testId_2);

		endSession(sessionId_1);
		endSession(sessionId_2);
		endSession(sessionId_3);

		queue.assertSucceeded('''«sessionId_1»''', events.get('''«sessionId_1»'''));
		queue.assertSucceeded('''«sessionId_2»''', events.get('''«sessionId_2»'''));
		queue.assertSucceeded('''«sessionId_3»''', events.get('''«sessionId_3»'''));
	}

	@Test
	public def void testHappyPathWithPing() throws InterruptedException {

		val sessionId = id("sessionId");
		val testId = id("testId");
		val timeout = 1000L;

		queue.init(9);

		startSession(sessionId);
		pingSession(sessionId, 500L);
		sleep(300L);
		pingSession(sessionId, 1000L);
		sleep(800L)
		startTest(sessionId, testId, timeout);
		pingTest(sessionId, testId, 2000L);
		sleep(1500L)
		pingTest(sessionId, testId, 1000L);
		sleep(50L);
		endTest(sessionId, testId);
		endSession(sessionId)

		queue.assertSucceeded('''«sessionId»''', events.get('''«sessionId»'''));
	}

	@Test
	public def void testStartMultipleTestsParallelForSingleSession() {

		val sessionId = id("sessionId");
		val testId_1 = id("testId_1");
		val testId_2 = id("testId_2");
		val testId_3 = id("testId_3");
		val timeout = 1000L;

		queue.init(12);

		startSession(sessionId);
		startTest(sessionId, testId_1, timeout);
		startTest(sessionId, testId_2, timeout);
		startTest(sessionId, testId_3, timeout);
		pingTest(sessionId, testId_1, timeout);
		pingTest(sessionId, testId_2, timeout);
		pingTest(sessionId, testId_3, timeout);
		endTest(sessionId, testId_1);
		endTest(sessionId, testId_2);
		endTest(sessionId, testId_3);
		endSession(sessionId);

		queue.assertSucceeded('''«sessionId»''', events.get('''«sessionId»'''));
	}

	@Test
	public def void testStartMultipleTestsParallelForSingleSessionReceiveEndEarlierThanStart() {

		val sessionId_1 = id("sessionId_1");
		val testId_1 = id("testId_1");
		val testId_2 = id("testId_2");
		val timeout = 1000L;

		queue.init(4);

		startSession(sessionId_1);
		startTest(sessionId_1, testId_1, timeout);
		endTest(sessionId_1, testId_2);

		queue.assertFailed('''«sessionId_1»''')
	}

	@Test
	public def void testStartMultipleTestsParallelForSingleSessionReceivePingEarlierThanStart() {

		val sessionId_1 = id("sessionId_1");
		val testId_1 = id("testId_1");
		val testId_2 = id("testId_2");
		val timeout = 1000L;

		queue.init(4);

		startSession(sessionId_1);
		startTest(sessionId_1, testId_1, timeout);
		pingTest(sessionId_1, testId_2, timeout);

		queue.assertFailed('''«sessionId_1»''')
	}

	@Test
	public def void testSetupTimeout() throws InterruptedException {

		val sessionId = id("sessionId");

		queue.init(2);

		startSession(sessionId);
		sleep(SECONDS.toMillis(2L))

		queue.assertFailed('''«sessionId»''');
	}

	@Test
	public def void testPingWithSetupTimeoutAdjustment() throws InterruptedException {

		val sessionId = id("sessionId");

		queue.init(4);

		startSession(sessionId);
		pingSession(sessionId, 500L);
		sleep(300L)
		pingSession(sessionId, 1000L);
		sleep(1500L)

		queue.assertFailed('''«sessionId»''')

	}

	@Test
	public def void testDefaultTimeout() throws InterruptedException {

		val sessionId = id("sessionId");
		val testId = id("testId");
		val timeout = 1000L;

		queue.init(3);

		startSession(sessionId);
		startTest(sessionId, testId, timeout)

		sleep(SECONDS.toMillis(2L))

		queue.assertFailed('''«sessionId»''');
	}

	@Test
	public def void testPingWithDefaultTimeoutAdjustment() throws InterruptedException {

		val sessionId = id("sessionId");
		val testId = id("testId");
		val timeout = 1000L;

		queue.init(5);

		startSession(sessionId);
		startTest(sessionId, testId, timeout);
		pingTest(sessionId, testId, 1200L);
		sleep(100L);
		pingTest(sessionId, testId, 100L);
		sleep(200L);
		endTest(sessionId, testId);

		queue.assertFailed('''«sessionId»''');
	}

	@Test
	public def void testIncorrectFsmStateTransition() {

		val sessionId = id("sessionId");

		queue.init(3);

		startSession(sessionId);
		startSession(sessionId);

		queue.assertFailed('''«sessionId»''')
	}

	@Test
	public def void testRunSameTestTwice() {

		val sessionId = id("sessionId");
		val testId = id("testId");
		val timeout = 1000L;

		queue.init(5);

		startSession(sessionId);
		startTest(sessionId, testId, timeout);
		endTest(sessionId, testId);
		startTest(sessionId, testId, timeout);

		queue.assertFailed('''«sessionId»''')
	}

}
