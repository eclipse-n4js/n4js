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
package org.eclipse.n4js.tester.tests.resource;

import com.google.inject.Inject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import org.eclipse.n4js.tester.TesterModule
import org.eclipse.n4js.tester.events.TestEndedEvent
import org.eclipse.n4js.tester.events.TestEvent
import org.eclipse.n4js.tester.events.TestPingedEvent
import org.eclipse.n4js.tester.events.TestStartedEvent
import org.eclipse.n4js.tester.tests.InjectedModules
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner
import org.eclipse.n4js.tester.tests.LoggingMockResourceTesterModule
import org.eclipse.n4js.tester.tests.LoggingMockResourceTesterModule.LoggingFsmRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static com.google.common.collect.ImmutableMap.*
import static com.jayway.restassured.RestAssured.*
import static java.util.Collections.*
import static java.util.UUID.randomUUID
import static javax.servlet.http.HttpServletResponse.*
import static org.eclipse.n4js.tester.server.resources.ContentType.*

/**
 * Class for testing the session RESTful resources.
 */
@RunWith(JUnitGuiceClassRunner)
@InjectedModules(baseModules =#[TesterModule], overrides = #[LoggingMockResourceTesterModule])
public class TestIdParsingTest extends BaseResourcesTest {
	
	@Inject
	LoggingFsmRegistry registry;
	
	/**
	 * Tests that the encoded test and session ID is correctly parsed
	 * from the test start request URL.
	 */
	@Test
	def void testPostStartExpectTestId() {
		val testId = encodeTestId("A/B/C/D#method");
		val sessionId = randomUUID.toString;
		
		given
			.contentType(START_TEST)
			.body(builder.put('timeout', 1000L).put('properties', singletonMap('someProperty', 'someValue')).build)
		.when
			.post('''«URL»«sessionId»/tests/«testId»/start''')
		.then
			.expectStatusCode(SC_OK);
			
		assertEvent("Created test start event has correct test ID.", registry.events.last, TestStartedEvent, sessionId, testId);
	}
	
	/**
	 * Tests that the encoded test and session ID is correctly parsed
	 * from the test end request URL.
	 */
	@Test
	def void testPostTestEndExpectTestId() {
		val testId = encodeTestId("A/B/C/D#method")
		val sessionId = randomUUID.toString;
		
		given
			.contentType(END_TEST)
			.body(newTestResult)
		.when
			.post('''«URL»«sessionId»/tests/«testId»/end''')
		.then
			.expectStatusCode(SC_OK)
			
		assertEvent("Create test ended event has correct test ID.", registry.events.last, TestEndedEvent, sessionId, testId);
	}
	
	/**
	 * Tests that the encoded test and session ID is correctly parsed
	 * from the test ping request URL.
	 */
	@Test
	def void testPostTestPingExpectTestId() {
		val testId = encodeTestId("A/B/C/D#method")
		val sessionId = randomUUID.toString;
		
		given
			.contentType(PING_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«sessionId»/tests/«testId»/ping''')
		.then
			.expectStatusCode(SC_OK)
			
		assertEvent("Created test ping event has correct test ID.", registry.events.last, TestPingedEvent, sessionId, testId);
	}


	/**
	 * Asserts the given test event to match the given session ID and if applicable the test ID.
	 */
	private def void assertEvent(String message, TestEvent e, Class<? extends TestEvent> eventClass, String sessionId, String testId) {
		Assert.assertTrue(message + " Event class doesn't match.", eventClass.isInstance(e));
		Assert.assertEquals(message + " Session ID doesn't match.", e.sessionId, sessionId);
		
		if (e instanceof TestStartedEvent) {
			Assert.assertEquals(message + " Test ID doesn't match.", e.testId, testId);
		} else if (e instanceof TestPingedEvent) {
			Assert.assertEquals(message + " Test ID doesn't match.", e.testId, testId);
		} else if (e instanceof TestEndedEvent) {
			Assert.assertEquals(message + " Test ID doesn't match.", e.testId, testId);
		}
	}
	
	/**
	 * Returns the URL-encoded version of the given test id.
	 */
	private def String encodeTestId(String testId) {
		try {
			return URLEncoder.encode(testId, StandardCharsets.UTF_8.toString);
		} catch (UnsupportedEncodingException e) {
			Assert.fail("Failed to URL-encode test ID '" + testId + "'");
			return ""; // this will never be execute
		}
	}
}
