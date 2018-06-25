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
package org.eclipse.n4js.tester.tests.testtree;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.tester.TestTreeTransformer;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.internal.DefaultTestTreeTransformer;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * Class for testing the {@link DefaultTestTreeTransformer}.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = { DefaultTestTreeTransformerModule.class })
public class TestTreeTransformerTest extends AbstractTestTreeTest {

	@Inject
	private ObjectMapper mapper;

	@Inject
	private TestTreeTransformer transformer;

	/***/
	@Test
	public void checkInjection() {
		assertNotNull(mapper);
		assertNotNull(transformer);
		assertTrue(transformer instanceof DefaultTestTreeTransformer);
	}

	/***/
	@Test
	public void testOneSuiteWithOneTestCase() {
		final Multimap<String, String> map = HashMultimap.create();
		map.put("suiteName", "testCaseName");
		final String actual = asString(transformer.apply(createTestTree(map)));
		assertContains(actual, "class.name.suiteName");
		assertContains(actual, "\"testMethods\":[\"testCaseName\"]");
	}

	/***/
	@Test
	public void testMultipleSuitesWithMultipleTestCases() {
		final Multimap<String, String> map = HashMultimap.create();

		map.put("suite.name.b", "test.case.g");
		map.put("suite.name.b", "test.case.h");

		map.put("suite.name.a", "test.case.b");
		map.put("suite.name.a", "test.case.a");
		map.put("suite.name.a", "test.case.c");

		map.put("suite.name.c", "test.case.z");
		map.put("suite.name.c", "test.case.x");
		map.put("suite.name.c", "test.case.y");

		final String actual = asString(transformer.apply(createTestTree(map)));
		assertContains(actual, "\"endpoint\":\"http://localhost:9415\"");
		assertContains(actual, "\"testMethods\":[\"test.case.g\",\"test.case.h\"]");
		assertContains(actual, "\"testMethods\":[\"test.case.a\",\"test.case.b\",\"test.case.c\"]");
		assertContains(actual, "\"testMethods\":[\"test.case.x\",\"test.case.y\",\"test.case.z\"]");

		assertTrue(actual.indexOf("suite.name.a") > 0);
		assertTrue(actual.indexOf("suite.name.b") > 0);
		assertTrue(actual.indexOf("suite.name.c") > 0);

		assertTrue(actual.indexOf("suite.name.a") < actual.indexOf("suite.name.b"));
		assertTrue(actual.indexOf("suite.name.b") < actual.indexOf("suite.name.c"));
		assertTrue(actual.indexOf("suite.name.a") < actual.indexOf("suite.name.c"));
	}

	private void assertContains(final String in, final String expected) {
		assertThat(in, new BaseMatcher<String>() {

			@Override
			public boolean matches(final Object obj) {
				return obj instanceof String && obj.toString().contains(expected);
			}

			@Override
			public void describeTo(final Description desc) {
				desc.appendText("<" + expected + ">");
			}
		});
	}

	private String asString(final Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private TestTree createTestTree(final Multimap<String, String> suiteToCasesMapping) {
		checkArgument(!suiteToCasesMapping.isEmpty());
		final TestTree tree = new TestTree(new ID(randomUUID().toString()));
		final List<TestSuite> suites = newArrayList();
		suiteToCasesMapping.asMap().entrySet().forEach(entry -> {
			final TestSuite suite = new TestSuite(entry.getKey());
			entry.getValue().forEach(testCaseName -> suite.add(new TestCase(
					new ID(testCaseName),
					"class.name." + suite.getName(),
					"project.origin." + suite.getName(),
					testCaseName,
					"display.name." + testCaseName,
					URI.createURI("testURI_" + testCaseName))));
			suites.add(suite);
		});
		tree.getSuites().addAll(suites);
		return tree;
	}

}
