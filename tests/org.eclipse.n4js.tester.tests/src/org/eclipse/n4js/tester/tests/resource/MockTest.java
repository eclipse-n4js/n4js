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

import static com.google.common.collect.ContiguousSet.create;
import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Range.closed;
import static java.lang.Integer.highestOneBit;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonMap;
import static java.util.UUID.randomUUID;
import static java.util.stream.StreamSupport.stream;
import static org.apache.log4j.Logger.getLogger;
import static org.apache.log4j.Logger.getRootLogger;
import static org.eclipse.n4js.tester.domain.TestStatus.ERROR;
import static org.eclipse.n4js.tester.domain.TestStatus.FAILED;
import static org.eclipse.n4js.tester.domain.TestStatus.PASSED;
import static org.eclipse.n4js.tester.domain.TestStatus.SKIPPED;
import static org.eclipse.n4js.tester.server.resources.ContentType.END_SESSION;
import static org.eclipse.n4js.tester.server.resources.ContentType.END_TEST;
import static org.eclipse.n4js.tester.server.resources.ContentType.PING_TEST;
import static org.eclipse.n4js.tester.server.resources.ContentType.START_SESSION;
import static org.eclipse.n4js.tester.server.resources.ContentType.START_TEST;
import static org.eclipse.n4js.tester.server.resources.HttpMethod.POST;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.server.resources.ContentType;
import org.eclipse.n4js.tester.server.resources.HttpMethod;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.TesterConstants;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Test for a mock test session.
 */
@Ignore("IDE-2725")
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = {})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // These test methods should never be executed in parallel. IDE-2725
public class MockTest {

	private static final long TIME_OUT = 5_000L;
	private static final int HTTP_CODE_OK = 200;
	private static final Logger LOGGER = getLogger(MockTest.class);
	private static final boolean DEBUG = false;

	private static final int SERIAL_TEST_CASE_COUNT_FACTOR = 100;
	private static final int PARALLEL_TEST_CASE_COUNT_FACTOR = 100;

	@Inject
	private TesterFacade facade;

	@Inject
	private HttpServerManager serverManager;

	@Inject
	private ObjectMapper mapper;

	int actualPort = -1;

	/***/
	@BeforeClass
	public static void beforeClass() {
		if (!DEBUG) {
			getRootLogger().removeAllAppenders();
			getRootLogger().addAppender(new NullAppender());
		}
	}

	/***/
	@Before
	public void before() {
		if (actualPort != -1) {
			serverManager.stopServer(actualPort);
		}
	}

	/***/
	@After
	public void after() {
		if (actualPort != -1) {
			serverManager.stopServer(actualPort);
		}
	}

	/**
	 * Mocks a test session in parallel test execution fashion.
	 */
	@Test
	public void testParallelMock() {
		log("begin of testParallelMock.");
		testMock(true, PARALLEL_TEST_CASE_COUNT_FACTOR);
		log("end of testParallelMock.");
	}

	/**
	 * Mocks a test session in serial test execution fashion.
	 */
	@Test
	public void testSerialMock() {
		log("begin of testSerialMock.");
		testMock(false, SERIAL_TEST_CASE_COUNT_FACTOR);
		log("end of testSerialMock.");
	}

	private void testMock(final boolean parallel, int testCaseCountFactor) {
		final String sessionId = valueOf(randomUUID());
		final TestTree testTree = createNewTestTree(sessionId, testCaseCountFactor);
		actualPort = facade.prepareTestSession(testTree);
		testMock(testTree, parallel);
	}

	private Stream<TestCase> getTestCaseStream(final TestTree tree, final boolean parallel) {
		return stream(tree.spliterator(), parallel);
	}

	private String URL() {
		return "http://localhost:" + /* PORT */actualPort + TesterConstants.CONTEXT_ROOT + TesterConstants.CONTEXT_PATH;
	}

	/**
	 * Mocks the {@link TestTree test tree} argument with fake test results.
	 *
	 * @param testTree
	 *            the test tree to generate test result.
	 * @param parallel
	 *            {@code true} if the mock test should be executed in parallel fashion. For serial execution this flag
	 *            is {@code false}.
	 */
	public void testMock(final TestTree testTree, final boolean parallel) {

		final String sessionId = testTree.getSessionId().getValue();

		final String mode = parallel ? "parallel" : "synchronous";
		log("Starting " + mode + " mock test session.");
		sendToServer(URL() + sessionId + "/start/", START_SESSION, POST, null);

		final AtomicInteger i = new AtomicInteger();
		final AtomicInteger percentage = new AtomicInteger();
		final int numberOfTests = size(testTree);

		getTestCaseStream(testTree, parallel).forEach(new Consumer<TestCase>() {

			@Override
			public void accept(final TestCase testCase) {
				final String testId = testCase.getId().getValue();
				if (0 < numberOfTests && 0 < i.get()) {
					final int value = (int) (((double) i.get() / (double) numberOfTests) * 100.0);
					if (value > percentage.get()) {
						synchronized (MockTest.class) {
							if (value > percentage.get()) {
								percentage.set(value);
								log(format("Running " + mode + " mock tests... [%2s%%]", value));
							}
						}
					}
				}
				sendToServer(URL() + sessionId + "/tests/" + testId + "/start/", START_TEST, POST,
						createTimeoutBody(TIME_OUT));
				final long timeout = getMockTestExecutionTime(i.get());
				sendToServer(URL() + sessionId + "/tests/" + testId + "/ping/", PING_TEST, POST,
						createTimeoutBody(timeout + TIME_OUT));
				sendToServer(URL() + sessionId + "/tests/" + testId + "/end/", END_TEST, POST,
						createTestResult(timeout, i.incrementAndGet()));
			}
		});

		log("Ending " + mode + " mock test session.");
		sendToServer(URL() + sessionId + "/end/", END_SESSION, POST, null);
	}

	private void log(String msg) {
		if (DEBUG) {
			LOGGER.info(msg);
		} else {
			System.out.println(msg);
		}
	}

	private void sendToServer(final String url, final ContentType contentType, final HttpMethod method,
			final Object body) {
		HttpURLConnection req = null;
		try {
			req = (HttpURLConnection) new URL(url).openConnection();
			req.setRequestMethod(valueOf(method));
			req.setRequestProperty("Content-Type", valueOf(contentType));
			req.setDoInput(true);
			req.setDoOutput(true);
			req.setConnectTimeout(0);
			req.setReadTimeout(0);

			if (null != body) {
				try (final OutputStream os = req.getOutputStream();
						final OutputStreamWriter osw = new OutputStreamWriter(os)) {
					osw.write(mapper.writeValueAsString(body));
					osw.flush();
				}
			}

			assertThat("URL = " + url + ", request = " + req + ", Content-Type = " + contentType + ", body = "
					+ mapper.writeValueAsString(body), req.getResponseCode(), equalTo(HTTP_CODE_OK));
		} catch (final Exception e) {
			LOGGER.error("Error while performing HTTP " + method + " to " + url + ".", e);
			throw new RuntimeException("Error while performing HTTP " + method + " to " + url + ".", e);
		} finally {
			if (null != req) {
				try {
					// Close input stream so that the HttpURLConnection instance is cleaned up and put into connection
					// cache for reuse.
					// https://mttkay.github.io/blog/2013/03/02/herding-http-requests-or-why-your-keep-alive-connection-may-be-dead/
					// IDE-2725
					req.getInputStream().close();
				} catch (IOException e) {
					// Do nothing
				}
			}
		}
	}

	private long getMockTestExecutionTime(final int i) {
		return 1L * i % 5;
	}

	private Object createTestResult(final long timeout, final int i) {
		final String expected = valueOf(i);
		final String actual;
		final TestStatus status;
		if (0 == i % 19) {
			status = FAILED;
			actual = "mod 19 for " + i;
		} else if (0 == i % 31) {
			status = ERROR;
			actual = "mod 31 for " + i;
		} else if (highestOneBit(i) == i) {
			actual = "power of 2 for " + i;
			status = SKIPPED;
		} else {
			actual = expected;
			status = PASSED;
		}
		final TestResult result = new TestResult(status);
		result.setElapsedTime(timeout);
		result.setActual(actual);
		result.setExpected(expected);
		return result;
	}

	private Object createTimeoutBody(final long timeout) {
		return singletonMap("timeout", timeout);
	}

	private TestTree createNewTestTree(final String sessionId, final int testCaseCountFactor) {
		final List<TestSuite> testSuites = newArrayList();
		create(closed(1, testCaseCountFactor), integers()).forEach(
				i -> {
					final String suiteName = format("%05d", i) + "_TestSuite";
					final TestSuite suite = new TestSuite(suiteName);
					create(closed(1, testCaseCountFactor), integers()).forEach(
							j -> {
								final String testCaseId = getTestCaseId(i, j);
								suite.add(new TestCase(
										new ID(testCaseId),
										"origin." + suiteName + "." + testCaseId + ".0.0.1",
										suiteName + "." + testCaseId,
										testCaseId,
										testCaseId, URI.createURI("testURI_" + testCaseId)));
							});
					testSuites.add(suite);
				});
		return new TestTree(new ID(sessionId), testSuites);
	}

	private String getTestCaseId(final Integer i, final Integer j) {
		return new StringBuilder().append(format("%05d", i)).append("_").append(format("%05d", j))
				.append("_TestMethod").toString();
	}

	private Matcher<Integer> equalTo(final int expected) {
		return new BaseMatcher<>() {

			@Override
			public boolean matches(final Object actual) {
				return actual instanceof Integer && ((Integer) actual).intValue() == expected;
			}

			@Override
			public void describeTo(final Description desc) {
				desc.appendText("<" + expected + ">");
			}
		};
	}

}
