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
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class GeneratedSmokeTestCases4 {

	@Inject
	ParseHelper<Script> parseHelper;

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression);
	}

	@Test
	public void test_1126() throws Exception {
		assertNoException("""
					([a,...b]=null); // simple case
					([a,{prop:[b,...c]},[x,[...d],y]]=null); // nesting cases
				""");
	}

	@Test
	public void test_1076() throws Exception {
		assertNoException("""
					var x = @This(any) function <T> () {
					}
					var y = function <T> () {
					}
					(@This(any) function <T> () {
					})
					(function <T> () {
					})
				""");
	}

	@Test
	public void test_GH2186() throws Exception {
		assertNoException("""
					class G<T> {}
					new G<
					;
					let x = 42;
				""");
	}
}
