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

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.ContiguousSet.create;
import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.primitives.Ints.toArray;
import static java.lang.String.valueOf;
import static java.util.Arrays.sort;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.eclipse.n4js.tester.domain.TestStatus.PASSED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;
import org.eclipse.n4js.tester.tests.AbstractTestTreeTest;
import org.eclipse.n4js.tester.tests.InjectedModules;
import org.eclipse.n4js.tester.tests.JUnitGuiceClassRunner;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Function;
import com.google.inject.Inject;

/**
 * Class for testing the {@link TestTreeRegistry test tree registry}.
 */
@RunWith(JUnitGuiceClassRunner.class)
@InjectedModules(baseModules = { TesterModule.class }, overrides = {})
public class TestTreeRegistryTest extends AbstractTestTreeTest {

	@Inject
	private TestTreeRegistry treeRegistry;

	@Inject
	private InternalTestTreeRegistry internalTreeRegistry;

	@Inject
	private TestFsmRegistry fsmRegistry;

	/***/
	@Before
	public void before() {
		internalTreeRegistry.purge();
	}

	/***/
	@Test
	public void tetsInjection() {
		assertNotNull(treeRegistry);
		assertNotNull(internalTreeRegistry);
	}

	/***/
	@Test
	public void testSingleton() {
		assertTrue(treeRegistry == internalTreeRegistry);
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testRegisterTestTreeWithoutSession() {
		final String sessionId = valueOf(randomUUID());
		treeRegistry.registerTestTree(newTestTree(sessionId));
	}

	/***/
	@Test
	public void testRegisterTestTree() {
		final String sessionId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId));
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testRegisterTestTreeWithMismatchingSessionId() {
		final String sessionId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(valueOf(randomUUID())));
	}

	/***/
	@Test
	public void testRegisterTestTreeThenLookupAndAssertWithOriginal() {
		final String sessionId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		final TestTree original = newTestTree(sessionId);
		treeRegistry.registerTestTree(original);
		final TestTree copy = treeRegistry.getTestTree(sessionId);
		assertNotNull(copy);
		assertEquals(original, copy);
		assertFalse(copy == original);
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testRegisterTestTreeThenPurgeAssertNotFound() {
		final String sessionId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		final TestTree original = newTestTree(sessionId);
		treeRegistry.registerTestTree(original);
		assertNotNull(treeRegistry.getTestTree(sessionId));
		internalTreeRegistry.purgeTestTree(sessionId);
		treeRegistry.getTestTree(sessionId);
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testValidateTestTreeWithoutSession() {
		final String sessionId = valueOf(randomUUID());
		internalTreeRegistry.validateTestTree(sessionId);
	}

	/***/
	@Test
	public void testValidateEmptyTestTree() {
		final String sessionId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId));
		internalTreeRegistry.validateTestTree(sessionId);
	}

	/***/
	@Test
	public void testValidateIncompleteTestTree() {
		final String sessionId = valueOf(randomUUID());
		final String testId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId, testId));
		assertFalse(internalTreeRegistry.validateTestTree(sessionId));
	}

	/***/
	@Test
	public void testValidateCompleteTestTree() {
		final String sessionId = valueOf(randomUUID());
		final String testId_1 = valueOf(randomUUID());
		final String testId_2 = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId, testId_1, testId_2));
		internalTreeRegistry.putTestResult(sessionId, testId_1, newEmptyTestResult());
		internalTreeRegistry.putTestResult(sessionId, testId_2, newEmptyTestResult());
		assertTrue(internalTreeRegistry.validateTestTree(sessionId));
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testPutTestResultWithoutSession() {
		final String sessionId = valueOf(randomUUID());
		final String testId = valueOf(randomUUID());
		internalTreeRegistry.putTestResult(sessionId, testId, newEmptyTestResult());
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void testPutTestResultWithoutMatchingTest() {
		final String sessionId = valueOf(randomUUID());
		final String testId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId, testId));
		internalTreeRegistry.putTestResult(sessionId, "some other test ID", newEmptyTestResult());
	}

	/***/
	@Test
	public void testPutTestResult() {
		final String sessionId = valueOf(randomUUID());
		final String testId = valueOf(randomUUID());
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId, testId));
		internalTreeRegistry.putTestResult(sessionId, testId, newEmptyTestResult());
	}

	/***/
	@Test(expected = IllegalStateException.class)
	public void purgeTestTreeWithoutSession() {
		final String sessionId = valueOf(randomUUID());
		internalTreeRegistry.purgeTestTree(sessionId);
	}

	/***/
	@Test
	public void concurrentPutTestResults() throws InterruptedException {
		final String sessionId = valueOf(randomUUID());
		final String[] testIds = generateTestIdRangeClosed(0, 10);
		fsmRegistry.registerFsm(sessionId);
		treeRegistry.registerTestTree(newTestTree(sessionId, testIds));
		final CountDownLatch latch = new CountDownLatch(testIds.length);
		final List<Thread> putResultThreads = Arrays2.transform(testIds,
				testId -> new Thread(new Runnable() {
					@Override
					public void run() {
						internalTreeRegistry.putTestResult(sessionId, testId, newEmptyTestResult());
						latch.countDown();
					}
				}));

		putResultThreads.parallelStream().forEach(t -> t.start());
		latch.await();
		assertTrue(internalTreeRegistry.validateTestTree(sessionId));
	}

	private final String[] generateTestIdRangeClosed(final int lower, final int upper) {
		return generateTestIdRangeClosed(lower, upper, null);
	}

	private final String[] generateTestIdRangeClosed(final int lower, final int upper, final String suffix) {
		final int[] ints = toArray(create(closed(lower, upper), integers()));
		sort(ints);
		final String[] ids = new String[ints.length];
		for (int i = 0; i < ints.length; i++) {
			ids[i] = ints[i] + nullToEmpty(suffix);
		}
		return ids;
	}

	private TestResult newEmptyTestResult() {
		return new TestResult(PASSED);
	}

	private TestTree newTestTree(final String sessionId, final String... testIds) {
		final List<TestSuite> testSuites = newArrayList(transform(newHashSet(testIds),
				new Function<String, TestSuite>() {
					@Override
					public TestSuite apply(String testId) {
						final TestSuite suite = new TestSuite("TestSuite_for_Test_" + testId);
						final TestCase testCase = new TestCase(
								new ID(testId),
								"testClassName_" + testId,
								"origin_" + testId + "name_" + testId + "_0.0.0",
								"name_" + testId,
								"displayName_" + testId,
								URI.createURI("testURI_" + testId));
						suite.setTestCases(singletonList(testCase));
						return suite;
					}
				}));
		return new TestTree(new ID(sessionId), testSuites);
	}
}
