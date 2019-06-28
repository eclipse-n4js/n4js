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

import com.google.common.collect.Multimap
import com.google.inject.Inject
import com.google.inject.Provider
import com.jayway.restassured.response.ValidatableResponse
import com.jayway.restassured.specification.RequestSpecification
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.tester.domain.ID
import org.eclipse.n4js.tester.domain.TestCase
import org.eclipse.n4js.tester.domain.TestResult
import org.eclipse.n4js.tester.domain.TestSuite
import org.eclipse.n4js.tester.domain.TestTree
import org.eclipse.n4js.tester.events.SessionEndedEvent
import org.eclipse.n4js.tester.events.SessionPingedEvent
import org.eclipse.n4js.tester.events.SessionStartedEvent
import org.eclipse.n4js.tester.events.TestEndedEvent
import org.eclipse.n4js.tester.events.TestPingedEvent
import org.eclipse.n4js.tester.events.TestStartedEvent
import org.eclipse.n4js.tester.server.HttpServerManager
import org.eclipse.n4js.tester.server.resources.ContentType
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest
import org.eclipse.n4js.tester.tests.TesterTestsConfiguration
import org.eclipse.n4js.tester.tests.helper.TestEventQueue
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.junit.After
import org.junit.Before

import com.google.common.collect.ArrayListMultimap
import static com.google.common.collect.Multimaps.synchronizedMultimap
import static com.jayway.restassured.RestAssured.*
import static java.util.Collections.*
import static java.util.UUID.randomUUID
import static org.eclipse.n4js.tester.domain.TestStatus.*
import static org.eclipse.n4js.tester.server.resources.ContentType.*

/**
 * Base class of all classes testing the behavior of the tester RESTful API.
 */
abstract class BaseResourcesTest extends AbstractTestTreeTest{

	@Inject
	protected Provider<TestEventQueue> provider;

	protected TestEventQueue queue;
	protected Multimap<String, String> events;

	@Inject
	private HttpServerManager serverManager;

	@Inject
	public extension TesterTestsConfiguration ttConfig;


	@Before
	public def void before() {
		computePortAndValidConfig();
		serverManager.startServer(VALID_CONFIG);
		if (null !== queue) {
			queue.dispose();
		}
		queue = provider.get();
		events = synchronizedMultimap(ArrayListMultimap.create);
	}


	@After
	public def void after() {
		queue?.dispose();
		serverManager.stopServer(port);
	}

	protected def newTestResult() {
		new TestResult(PASSED) => [
			actual = 'actual message'
			expected = 'expected value'
			message = 'some message'
			trace = #['trace_1', 'trace_2', 'trace_3']
			elapsedTime = 300L
		]
	}

	protected def newTestTree(Object sessionId, Object... testIds) {
		new TestTree(new ID('''«sessionId»'''), #[newTestSuite(testIds.toList)]);
	}

	protected def newTestSuite(Iterable<Object> testIds) {
		new TestSuite('''testSuiteName_«randomUUID»''') => [
			testCases = testIds.map[newTestCase(it)].toList
		];
	}

	protected def newTestCase(Object testId) {
		new TestCase(
			new ID('''«testId»'''),
			'''className_«testId»''',
			'''origin_className_«testId»_0.0.0''',
			'''name_«testId»''',
			'''displayName_«testId»''',
			URI.createURI('''testURI_«testId»''')
		);
	}

	protected def contentType(RequestSpecification spec, ContentType type) {
		spec.contentType('''«type»''');
	}

	protected def endSession(Object sessionId) {
		given.contentType(END_SESSION).post('''«URL»«sessionId»/end''');
		events.put('''«sessionId»''', '''«new SessionEndedEvent('''«sessionId»''')»''');
	}

	protected def endTest(Object sessionId, Object testId) {
		given.contentType(END_TEST).with.body(newTestResult).post('''«URL»«sessionId»/tests/«testId»/end''');
		events.put('''«sessionId»''', '''«new TestEndedEvent('''«sessionId»''', '''«testId»''', null)»''');
	}

	protected def pingTest(Object sessionId, Object testId, long timeout) {
		given.contentType(PING_TEST).with.body(newTimeout(timeout)).post('''«URL»«sessionId»/tests/«testId»/ping''');
		events.put('''«sessionId»''', '''«new TestPingedEvent('''«sessionId»''', '''«testId»''', timeout)»''');
	}

	protected def startTest(Object sessionId, Object testId, long timeout) {
		given.contentType(START_TEST).with.body(newTimeout(timeout)).post('''«URL»«sessionId»/tests/«testId»/start''');
		events.put('''«sessionId»''', '''«new TestStartedEvent('''«sessionId»''', '''«testId»''', timeout)»''');
	}

	protected def pingSession(Object sessionId, long timeout) {
		given.contentType(PING_SESSION).with.body(newTimeout(timeout)).post('''«URL»«sessionId»/ping''');
		events.put('''«sessionId»''', '''«new SessionPingedEvent('''«sessionId»''', timeout)»''');
	}

	protected def startSession(Object sessionId) {
		given.contentType(START_SESSION).post('''«URL»«sessionId»/start''');
		events.put('''«sessionId»''', '''«new SessionStartedEvent('''«sessionId»''')»''')
	}

	protected def newTimeout(long timeout) {
		singletonMap('timeout', timeout);
	}

	protected def expectStatusCode(ValidatableResponse resp, int statusCode) {
		resp.statusCode(new BaseMatcher<Integer>() {

			override matches(Object item) {
				if (item instanceof Integer) {
					return statusCode == item.intValue
				}
				return false;
			}

			override describeTo(Description description) {
				description.appendText('''Expected status code «statusCode».''')
			}

		})
	}

	protected def startsWith(String prefix) {
		return new BaseMatcher<Object>() {
			override matches(Object item) {
				if (item instanceof String) {
					return item.startsWith(prefix);
				}
				return false;
			}
			override describeTo(Description description) {
				description.appendText('''Expected string starting with «prefix».''');
			}
		};
	}

	protected def notEmptyString() {
		return new BaseMatcher<Object>() {
			override matches(Object item) {
				return item instanceof String && "" != item;
			}
			override describeTo(Description description) {
				description.appendText("Expected a non-empty string.");
			}
		};
	}

}
