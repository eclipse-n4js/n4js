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
package org.eclipse.n4js.tester.tests.domain;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Iterables.size;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.asList;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;
import static org.eclipse.n4js.tester.domain.TestStatus.ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.tester.tests.MockTesterModule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Test class for testing the serialization, deserialization and cloning behavior of the domain objects.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { MockTesterModule.class })
public class TesterDomainTest extends AbstractTestTreeTest {

	@Inject
	private ObjectMapper mapper;

	/***/
	@Test
	public void testSerializeAndCloneId() throws Exception {
		final ID actual = mapper.readValue("{\"value\":\"someId\"}", ID.class);

		assertNotNull(actual);
		assertTrue("someId".equals(actual.getValue()));
		final ID expected = new ID("someId");
		assertEquals(expected, actual);
		assertEquals(expected.getValue(), actual.getValue());

		final ID clone = actual.clone();
		assertEquals(actual, clone);
		assertEquals(actual.getValue(), clone.getValue());
		assertFalse(actual == clone);

	}

	/***/
	@Test
	public void testTestTreeClone() throws Exception {
		final List<TestSuite> originalTestSuites = newArrayList();
		final TestSuite originalTestSuite = new TestSuite("name");
		final TestCase originalTestCase = new TestCase(new ID("testId"), "testId", "testId", "testId", "testId",
				URI.createURI("testURI_testId"));
		originalTestSuite.add(originalTestCase);
		originalTestSuites.add(originalTestSuite);
		final TestTree originalTestTree = new TestTree(new ID("value"), originalTestSuites);
		final TestTree copyTestTree = originalTestTree.clone();
		assertEquals(originalTestTree, copyTestTree);
		assertFalse(originalTestTree == copyTestTree);
		assertFalse(originalTestSuites == copyTestTree.getSuites());
		assertFalse(originalTestSuite == getOnlyElement(copyTestTree.getSuites()));
		assertEquals(originalTestCase, getOnlyElement(getOnlyElement(copyTestTree.getSuites())
				.getTestCases()));
		assertFalse(originalTestCase == getOnlyElement(getOnlyElement(copyTestTree.getSuites())
				.getTestCases()));
	}

	/***/
	@Test
	public void testSerializeTestResult() throws Exception {
		final TestResult expected = new TestResult(ERROR);
		expected.setActual("actual result");
		expected.setExpected("expected result");
		expected.setTrace(newArrayList("trace 1", "trace 2", "trace 3"));
		expected.setMessage("some message");
		expected.setElapsedTime(1000L);
		final String json = "{\"message\":\"some message\",\"expected\":\"expected result\",\"actual\":\"actual result\",\"trace\":[\"trace 1\",\"trace 2\",\"trace 3\"],\"testStatus\":\"ERROR\",\"elapsedTime\":1000}";
		final TestResult actual = mapper.readValue(json, TestResult.class);
		assertResultEquals(expected, actual);

		final TestResult clone = actual.clone();
		assertResultEquals(actual, clone);
		assertFalse(actual == clone);

		final String sparseJson = "{\"actual\":\"actual result\",\"trace\":[\"trace 1\",\"trace 2\",\"trace 3\"],\"testStatus\":\"ERROR\",\"elapsedTime\":1000}";
		final TestResult sparseResult = mapper.readValue(sparseJson, TestResult.class);
		assertNull(sparseResult.getMessage());
		assertNull(sparseResult.getExpected());

	}

	/***/
	@Test
	public void testTreeContainsTest() {
		final TestTree tree = new TestTree(new ID(valueOf(randomUUID())));
		final TestSuite a = new TestSuite("A");
		a.addAll(newTestCase("1", "2", "3"));
		final TestSuite b = new TestSuite("B");
		b.addAll(newTestCase("4", "5", "6"));

		final TestSuite a1 = new TestSuite("A.1");
		a1.addAll(newTestCase("7", "8"));

		final TestSuite a2 = new TestSuite("A.2");
		a2.addAll(newTestCase("9", "10"));

		final TestSuite a11 = new TestSuite("A.1.1");
		a11.addAll(newTestCase("11", "12"));

		a1.getChildren().add(a11);

		a.getChildren().add(a1);
		a.getChildren().add(a2);

		tree.getSuites().add(a);
		tree.getSuites().add(b);

		assertNotNull(tree.getTestCase("1"));
		assertNotNull(tree.getTestCase("2"));
		assertNotNull(tree.getTestCase("3"));
		assertNotNull(tree.getTestCase("4"));
		assertNotNull(tree.getTestCase("5"));
		assertNotNull(tree.getTestCase("6"));
		assertNotNull(tree.getTestCase("7"));
		assertNotNull(tree.getTestCase("8"));
		assertNotNull(tree.getTestCase("9"));
		assertNotNull(tree.getTestCase("10"));
		assertNotNull(tree.getTestCase("11"));
		assertNotNull(tree.getTestCase("12"));

		assertNull(tree.getTestCase("13"));
		assertNull(tree.getTestCase((String) null));
		assertNull(tree.getTestCase((ID) null));
	}

	/***/
	@Test
	public void testTestTreeIterator() {
		final TestTree tree = new TestTree(new ID(valueOf(randomUUID())));
		final TestSuite a = new TestSuite("A");
		a.addAll(newTestCase("1", "2", "3"));
		final TestSuite b = new TestSuite("B");
		b.addAll(newTestCase("4", "5", "6"));

		final TestSuite a1 = new TestSuite("A.1");
		a1.addAll(newTestCase("7", "8"));

		final TestSuite a2 = new TestSuite("A.2");
		a2.addAll(newTestCase("9", "10"));

		final TestSuite a11 = new TestSuite("A.1.1");
		a11.addAll(newTestCase("11", "12"));

		a1.getChildren().add(a11);

		a.getChildren().add(a1);
		a.getChildren().add(a2);

		tree.getSuites().add(a);
		tree.getSuites().add(b);

		assertEquals(4, size(a1));
		assertEquals(
				newHashSet("7", "8", "11", "12"),
				newHashSet(transform(a1, testCase -> testCase.getId().getValue())));

		assertEquals(9, size(a));
		assertEquals(
				newHashSet("1", "2", "3", "7", "8", "9", "10", "11", "12"),
				newHashSet(transform(a, testCase -> testCase.getId().getValue())));

		assertEquals(12, size(tree));
		assertEquals(
				newHashSet("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"),
				newHashSet(transform(tree, testCase -> testCase.getId().getValue())));

	}

	/***/
	@Test
	public void testParseTestResult() throws JsonParseException, JsonMappingException, IOException {
		// @formatter:off
		final String content = "{ \n" +
				"    \"testStatus\": \"FAILED\", \n" +
				"    \"message\": \"AssertionError: 1 and '1' are equal in a truthy way but not in a strict way( 1 not == 2 )\", \n" +
				"    \"expected\": \"2\", \n" +
				"    \"actual\": \"1\", \n" +
				"    \"elapsedTime\": 4, \n" +
				"    \"trace\": [\"trace_1\", \"trace_2\"]" +
				"  }";
		// @formatter:on
		assertNotNull(mapper.readValue(content, TestResult.class));

	}

	private Collection<? extends TestCase> newTestCase(final String testId, final String... otherIds) {
		final List<TestCase> cases = newArrayList();
		asList(testId, otherIds).forEach(id -> cases.add(
				new TestCase(
						new ID(id),
						"className." + id,
						"origin." + id,
						"name." + id,
						"displayName." + id,
						URI.createURI("testURI_" + id))));
		return cases;
	}

	private void assertResultEquals(final TestResult expected, final TestResult actual) {
		assertEquals(expected.getActual(), actual.getActual());
		assertEquals(expected.getExpected(), actual.getExpected());
		assertEquals(expected.getTrace(), actual.getTrace());
		assertEquals(expected.getMessage(), actual.getMessage());
		assertEquals(expected.getElapsedTime(), actual.getElapsedTime());
		assertEquals(expected.getTestStatus(), actual.getTestStatus());
	}

}
