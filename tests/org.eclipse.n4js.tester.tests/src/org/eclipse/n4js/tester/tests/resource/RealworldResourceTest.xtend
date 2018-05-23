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

import com.google.inject.Inject
import org.eclipse.n4js.tester.TesterFacade
import org.eclipse.n4js.tester.TesterModule
import org.eclipse.n4js.tester.tests.InjectedModules
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner
import org.junit.Test
import org.junit.runner.RunWith

import static java.util.UUID.randomUUID

/**
 * Class for testing the logic and the behavior of the test sessions and the associated tester
 * finite state machines by invoking the RESTful API with the production module binding.
 */
@RunWith(JUnitGuiceClassRunner)
@InjectedModules(baseModules = #[TesterModule], overrides = #[])
class RealworldResourceTest extends BaseResourcesTest {

	@Inject
	private TesterFacade facade;

	@Test
	public def void testInvalidTestTreeAfterSessionFinished() {

		val sessionId = randomUUID;
		val testId = randomUUID;
		val timeout = 1000L;

		queue.init(4);

		startSession(sessionId);
		initializeTests(sessionId, testId);
		startTest(sessionId, testId, timeout);
		endSession(sessionId);

		queue.assertFailed('''«sessionId»''');
	}

	@Test
	public def void testHappyPath() {

		val sessionId = randomUUID;
		val testId = randomUUID;
		val timeout = 1000L;

		queue.init(6);

		startSession(sessionId);
		initializeTests(sessionId, testId);
		startTest(sessionId, testId, timeout);
		pingTest(sessionId, testId, timeout);
		endTest(sessionId, testId);
		endSession(sessionId);

		queue.assertSucceeded('''«sessionId»''', events.get('''«sessionId»'''));
	}

	@Test
	public def void testHappyPathWithMultipleSessions() {

		val sessionId_1 = randomUUID;
		val sessionId_2 = randomUUID;
		val testId_1 = randomUUID;
		val testId_2 = randomUUID;
		val timeout = 1000L;

		queue.init(12);

		startSession(sessionId_1);
		startSession(sessionId_2);
		initializeTests(sessionId_1, testId_1);
		initializeTests(sessionId_2, testId_2);
		startTest(sessionId_1, testId_1, timeout);
		startTest(sessionId_2, testId_2, timeout);
		pingTest(sessionId_1, testId_1, timeout);
		pingTest(sessionId_2, testId_2, timeout);
		endTest(sessionId_1, testId_1);
		endTest(sessionId_2, testId_2);
		endSession(sessionId_1);
		endSession(sessionId_2);

		queue.assertSucceeded('''«sessionId_1»''', events.get('''«sessionId_1»'''));
		queue.assertSucceeded('''«sessionId_2»''', events.get('''«sessionId_2»'''));
	}

	@Test
	public def void testHappyPathWithParallelTestsForSingleSession() {

		val sessionId_1 = randomUUID;
		val testId_1 = randomUUID;
		val testId_2 = randomUUID;
		val testId_3 = randomUUID;
		val timeout = 1000L;

		queue.init(12);

		startSession(sessionId_1);
		initializeTests(sessionId_1, testId_2, testId_3, testId_1);

		startTest(sessionId_1, testId_2, timeout);
		startTest(sessionId_1, testId_1, timeout);
		startTest(sessionId_1, testId_3, timeout);

		pingTest(sessionId_1, testId_1, timeout);
		pingTest(sessionId_1, testId_1, timeout);
		pingTest(sessionId_1, testId_2, timeout);

		endTest(sessionId_1, testId_3);
		endTest(sessionId_1, testId_2);
		endTest(sessionId_1, testId_1);

		endSession(sessionId_1);

		queue.assertSucceeded('''«sessionId_1»''', events.get('''«sessionId_1»'''));
	}

	private def initializeTests(Object sessionId, Object... testIds) {
		facade.prepareTestSession(newTestTree(sessionId, testIds));
	}

}
