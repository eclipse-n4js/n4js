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
package org.eclipse.n4js.smoke.tests;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.smoketest.IgnoredBySmokeTest;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * This is not a collection, but used for single test cases basically during development (for debugging etc.). Checked
 * in, so that we do not have to copy the infrastructure again.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class GeneratedSmokeTestCases0 {

	@Inject
	ParseHelper<Script> parseHelper;

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression);
	}

	@IgnoredBySmokeTest("Only for debugging")
	@Test
	public void test_1238() throws Exception {
		assertNoException("""
				public interface I <T extends A> {
					<S> foo(union{A,C} p)
					abstract bar()
					baz(p: A?)
				}
				""");
	}

}
