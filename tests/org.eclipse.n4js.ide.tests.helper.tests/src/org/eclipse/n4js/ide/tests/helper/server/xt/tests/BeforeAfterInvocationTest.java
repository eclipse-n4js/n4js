/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileRunner;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtParentRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import com.google.common.collect.Lists;

/**
 * Tests that <code>@BeforeClass</code>, <code>@Before</code>, <code>@After</code>, and <code>@AfterClass</code> methods
 * <em>of the IDE test class</em> (not the Xt test class) are properly executed.
 */
public class BeforeAfterInvocationTest extends AbstractXtParentRunnerTest {

	private static List<String> invocationRecorder = null;

	/***/
	@Test
	public void test() throws Exception {
		assertNull(invocationRecorder);
		invocationRecorder = new ArrayList<>();

		run(new BeforeAfterTestRunSimulator(), "probands/BeforeAfterInvocation");
		assertEventNames("testRunStarted\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testRunFinished\n"
				+ "testRunStarted\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testStarted\n"
				+ "testFinished\n"
				+ "testRunFinished");
		assertResults("Passed: noerrors~0:  〔probands/BeforeAfterInvocation/BeforeAfterInvocation1.n4js.xt〕\n"
				+ "Passed: noerrors~1:  〔probands/BeforeAfterInvocation/BeforeAfterInvocation1.n4js.xt〕\n"
				+ "Passed: noerrors~0:  〔probands/BeforeAfterInvocation/BeforeAfterInvocation2.n4js.xt〕\n"
				+ "Passed: noerrors~1:  〔probands/BeforeAfterInvocation/BeforeAfterInvocation2.n4js.xt〕");

		assertEquals("incorrect order of before/after-method invocations", Lists.newArrayList(
				// @BeforeClass methods must not be invoked during creation of file runners
				// Rationale: when Xt tests are located together with other kinds of test in the same bundle, we might
				// end up with this execution order:
				// 1) creation of all file runners (including those of the Xt tests)
				// 2) execution of other tests
				// 3) execution of the Xt tests
				// The other tests in 2) should not run with the special configuration for IDE tests in the @BeforeClass
				// methods of AbstractIdeTest and its subclasses!
				"==== START: creating file runners",
				"==== END: creating file runners",
				"==== running tests", // <-- assert @BeforeClass methods are not invoked before running actually starts
				"#someBeforeClassMethod() in super class",
				"#someBeforeClassMethod() in subclass",
				"==== running test file: BeforeAfterInvocation1.n4js.xt: probands/BeforeAfterInvocation",
				"#anotherBeforeMethod() in super class",
				"#someBeforeMethod() in sub class",
				"#anotherAfterMethod() in sub class",
				"#someAfterMethod() in super class",
				"==== running test file: BeforeAfterInvocation2.n4js.xt: probands/BeforeAfterInvocation",
				"#anotherBeforeMethod() in super class",
				"#someBeforeMethod() in sub class",
				"#anotherAfterMethod() in sub class",
				"#someAfterMethod() in super class",
				"#someAfterClassMethod() in subclass",
				"#someAfterClassMethod() in super class"), invocationRecorder);

		invocationRecorder = null;
	}

	@SuppressWarnings("javadoc")
	public static class SuperBeforeAfterXtIdeTest extends XtIdeTest {

		@BeforeClass
		public static void someBeforeClassMethod() {
			invocationRecorder.add("#someBeforeClassMethod() in super class");
		}

		@Before
		public void someBeforeMethod() {
			invocationRecorder.add("#someBeforeMethod() in super class");
		}

		@Before
		public void anotherBeforeMethod() {
			invocationRecorder.add("#anotherBeforeMethod() in super class");
		}

		@After
		public void someAfterMethod() {
			invocationRecorder.add("#someAfterMethod() in super class");
		}

		@After
		public void anotherAfterMethod() {
			invocationRecorder.add("#anotherAfterMethod() in super class");
		}

		@AfterClass
		public static void someAfterClassMethod() {
			invocationRecorder.add("#someAfterClassMethod() in super class");
		}
	}

	@SuppressWarnings("javadoc")
	public static class SubBeforeAfterXtIdeTest extends SuperBeforeAfterXtIdeTest {

		@BeforeClass
		public static void someBeforeClassMethod() { // note: reusing same method name as in super class
			invocationRecorder.add("#someBeforeClassMethod() in subclass");
		}

		@Before
		@Override
		public void someBeforeMethod() { // note: overriding method of super class
			invocationRecorder.add("#someBeforeMethod() in sub class");
		}

		@After
		@Override
		public void anotherAfterMethod() { // note: overriding method of super class
			invocationRecorder.add("#anotherAfterMethod() in sub class");
		}

		@AfterClass
		public static void someAfterClassMethod() { // note: reusing same method name as in super class
			invocationRecorder.add("#someAfterClassMethod() in subclass");
		}
	}

	// the following classes are only required to configure our custom subclasses of XtIdeTest to be used:

	private class BeforeAfterTestRunSimulator extends TestRunSimulator {

		@Override
		protected XtParentRunner createParentRunner() throws InitializationError {
			return new BeforeAfterXtParentRunner(XtTestSetupTestMockup.class);
		}
	}

	private static class BeforeAfterXtParentRunner extends XtParentRunner {

		public BeforeAfterXtParentRunner(Class<?> testClass) throws InitializationError {
			super(testClass);
		}

		@Override
		protected Class<? extends XtIdeTest> getXtIdeTestClass() {
			return SubBeforeAfterXtIdeTest.class;
		}

		@Override
		protected List<XtFileRunner> createFileRunners() {
			try {
				invocationRecorder.add("==== START: creating file runners");
				return super.createFileRunners();
			} finally {
				invocationRecorder.add("==== END: creating file runners");
			}
		}

		@Override
		public void run(RunNotifier notifier) {
			invocationRecorder.add("==== running tests");
			super.run(notifier);
		}

		@Override
		public void runChild(XtFileRunner child, RunNotifier notifier) {
			invocationRecorder.add("==== running test file: " + child.getName());
			super.runChild(child, notifier);
		}
	}
}
