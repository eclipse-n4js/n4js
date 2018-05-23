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

import org.eclipse.n4js.tester.TesterModule
import org.eclipse.n4js.tester.tests.InjectedModules
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner
import org.eclipse.n4js.tester.tests.MockResourceTesterModule
import org.junit.Test
import org.junit.runner.RunWith

import static com.google.common.collect.ImmutableMap.*
import static com.jayway.restassured.RestAssured.*
import static org.eclipse.n4js.tester.server.HttpConstants.*
import static org.eclipse.n4js.tester.server.resources.ContentType.*
import static java.util.Collections.*
import static java.util.UUID.randomUUID
import static javax.servlet.http.HttpServletResponse.*

/**
 * Class for testing the session RESTful resources.
 */
@RunWith(JUnitGuiceClassRunner)
@InjectedModules(baseModules =#[TesterModule], overrides = #[MockResourceTesterModule])
public class ResourcesTest extends BaseResourcesTest {

	@Test
	def void testPutSessionStartWithoutBodyExpect405() {
		given
			.contentType(START_SESSION)
		.when
			.put('''«URL»«randomUUID»/start''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED);
	}

	@Test
	def void testPutSessionStartWithValidBodyExpect405() {
		given
			.contentType(START_SESSION)
			.body(singletonMap('properties', singletonMap('someProperty', 'someValue')))
		.when
			.put('''«URL»«randomUUID»/start''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED);
	}

	@Test
	def void testPostSessionStartWithInvalidBodyExpect422() {
		given
			.contentType(START_SESSION)
			.body('someInvalidBodyFormat')
		.when
			.post('''«URL»«randomUUID»/start''')
		.then
			.expectStatusCode(SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	def void testPostSessionStartExpect200() {
		given
			.contentType(START_SESSION)
		.when
			.post('''«URL»«randomUUID»/start''')
		.then
			.expectStatusCode(SC_OK);
	}

	@Test
	def void testPostSessionStartWithNotAllowedContetTypeExpect415() {
		given
			.contentType(END_SESSION)
		.when
			.post('''«URL»«randomUUID»/start''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE);
	}

	@Test
	def void testPutSessionStartWithIncorrectRequestUriExpect404() {
		given
			.contentType(END_SESSION)
		.when
			.put('''«URL»«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND);
	}

	@Test
	def void testPostSessionPingExpect200() {
		given
			.contentType(PING_SESSION)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_OK);
	}

	@Test
	def void testPostSessionPingWithoutBodyExpect400() {
		given
			.contentType(PING_SESSION)
		.when
			.post('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_BAD_REQUEST);
	}

	@Test
	def void testPostSessionPingWithInvalidBodyExpect422() {
		given
			.contentType(PING_SESSION)
			.body(singletonMap('invalidProperty', 1000L))
		.when
			.post('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	def void testPostSessionPingWithIncorrectBodyExpect400() {
		given
			.contentType(PING_SESSION)
			.body(singletonMap('timeout', 'I am not a number.'))
		.when
			.post('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_BAD_REQUEST);
	}

	@Test
	def void testPutSessionPingExpect405() {
		given
			.contentType(PING_SESSION)
			.body(singletonMap('timeout', 1000L))
		.when
			.put('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED);
	}

	@Test
	def void testPostSessionPingWithNotAllowedContetTypeExpect415() {
		given
			.contentType(START_SESSION)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE);
	}

	@Test
	def void testPostSessionPingWithIncorrectRequestUriExpect404() {
		given
			.contentType(PING_SESSION)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND);
	}

	@Test
	def void testPostSessionEndExpect200() {
		given
			.contentType(END_SESSION)
		.when
			.post('''«URL»«randomUUID»/end''')
		.then
			.expectStatusCode(SC_OK)
	}

	@Test
	def void testPutSessionEndExpect405() {
		given
			.contentType(END_SESSION)
		.when
			.put('''«URL»«randomUUID»/end''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED)
	}

	@Test
	def void testPostSessionEndWithNotAllowedContentTypeExpect415() {
		given
			.contentType(START_SESSION)
		.when
			.post('''«URL»«randomUUID»/end''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE)
	}

	@Test
	def void testPostSessionEndWithIncorrectRequestUriExpect404() {
		given
			.contentType(END_SESSION)
		.when
			.post('''«URL»«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND)
	}

	@Test
	def void testPostTestStartExpect200() {
		given
			.contentType(START_TEST)
			.body(builder.put('timeout', 1000L).put('properties', singletonMap('someProperty', 'someValue')).build)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_OK)
			.body(notEmptyString)
			.contentType(startsWith('application/vnd.n4js.start_test_res.tm+json'))
	}

	@Test
	def void testPostTestStartWithoutPropertiesBodyExpect200() {
		given
			.contentType(START_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_OK)
			.body(notEmptyString)
			.contentType(startsWith('application/vnd.n4js.start_test_res.tm+json'))
	}

	@Test
	def void testPostTestStartWithWithoutBodyExpect400() {
			given
			.contentType(START_TEST)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_BAD_REQUEST)
	}

	@Test
	def void testPostTestStartWithInvalidBodyExpect422() {
		given
			.contentType(START_TEST)
			.body(singletonMap('invalidProperty', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_UNPROCESSABLE_ENTITY)
	}

	@Test
	def void testPostTestStartWithIncorrectBodyExpect400() {
		given
			.contentType(START_TEST)
			.body(singletonMap('timeout', 'I am not a number'))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_BAD_REQUEST)
	}

	@Test
	def void testPutTestStartExpect405() {
		given
			.contentType(START_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED)
	}

	@Test
	def void testPostTestStartWithNotAllowedContentTypeExpect415() {
		given
			.contentType(END_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/start''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE)
	}

	@Test
	def void testPostTestStartWithIncorrectUriFragmentExpect404() {
		given
			.contentType(START_TEST)
			.body(builder.put('timeout', 1000L).put('properties', singletonMap('someProperty', 'someValue')).build)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND)
	}

	@Test
	def void testPutTestPingExpect405() {
		given
			.contentType(PING_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED);
	}

		@Test
	def void testPutTestPingWithCommentBodyExpect405() {
		given
			.contentType(PING_TEST)
			.body(builder.put('timeout', 1000L).put('comment', 'someComment').build)
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED);
	}

	@Test
	def void testPostTestPingWithoutBodyExpect400() {
		given
			.contentType(PING_TEST)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_BAD_REQUEST);
	}

	@Test
	def void testPostTestPingWithInvalidBodyExpect422() {
		given
			.contentType(PING_TEST)
			.body(singletonMap('invalidProperty', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_UNPROCESSABLE_ENTITY);
	}

	@Test
	def void testPostTestPingWithIncorrectBodyExpect400() {
		given
			.contentType(PING_TEST)
			.body(singletonMap('timeout', 'I am not a number.'))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_BAD_REQUEST);
	}

	@Test
	def void testPostTestPingExpect200() {
		given
			.contentType(PING_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_OK);
	}

	@Test
	def void testPostTestPingWithNotAllowedContetTypeExpect415() {
		given
			.contentType(START_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/ping''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE);
	}

	@Test
	def void testPutTestPingWithIncorrectRequestUriExpect404() {
		given
			.contentType(PING_TEST)
			.body(singletonMap('timeout', 1000L))
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND);
	}

	@Test
	def void testPutTestEndExpect405() {
		given
			.contentType(END_TEST)
			.body(newTestResult)
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/end''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED)
	}

	@Test
	def void testPostTestEndWithoutBodyExpect400() {
		given
			.contentType(END_TEST)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/end''')
		.then
			.expectStatusCode(SC_BAD_REQUEST)
	}

	@Test
	def void testPostTestEndWithInvalidBodyExpect422() {
		given
			.contentType(END_TEST)
			.body('''
					{
					   "invalid property":"some message",
					   "expected":"expected value",
					   "actual":"actual message",
					   "testStatus":"PASSED",
					   "elapsedTime":"I am not a number.",
					   "trace":[
					      "trace_1",
					      "trace_2",
					      "trace_3"
					   ]
					}
				''')
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/end''')
		.then
			.expectStatusCode(SC_UNPROCESSABLE_ENTITY)
	}

	@Test
	def void testPostTestEndExpect200() {
		given
			.contentType(END_TEST)
			.body(newTestResult)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/end''')
		.then
			.expectStatusCode(SC_OK)
	}

	@Test
	def void testPostTestEndWithNotAllowedContentTypeExpect415() {
		given
			.contentType(START_TEST)
			.body(newTestResult)
		.when
			.post('''«URL»«randomUUID»/tests/«randomUUID»/end''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE)
	}

	@Test
	def void testPutTestEndWithIncorrectUriFragmentExpect404() {
		given
			.contentType(END_TEST)
			.body(newTestResult)
		.when
			.put('''«URL»«randomUUID»/tests/«randomUUID»/incorrectUriFragment''')
		.then
			.expectStatusCode(SC_NOT_FOUND)
	}

	@Test
	def void testGetTestCatalogExpect200() {
		given
			.contentType(ASSEMBLE_TEST_CATALOG)
		.when
			.get('''«URL»testcatalog''')
		.then
			.expectStatusCode(SC_OK)
	}

	@Test
	def void testGetTestCatalogWithNotAllowedContentTypeExpect415() {
		given
			.contentType(END_TEST)
		.when
			.get('''«URL»testcatalog''')
		.then
			.expectStatusCode(SC_UNSUPPORTED_MEDIA_TYPE)
	}

	@Test
	def void testPutTestCatalogExpect405() {
		given
			.contentType(ASSEMBLE_TEST_CATALOG)
		.when
			.put('''«URL»testcatalog''')
		.then
			.expectStatusCode(SC_METHOD_NOT_ALLOWED)
	}

}
