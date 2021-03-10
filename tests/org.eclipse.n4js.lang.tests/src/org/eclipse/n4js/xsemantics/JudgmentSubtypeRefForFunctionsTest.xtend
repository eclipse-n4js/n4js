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
package org.eclipse.n4js.xsemantics

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JudgmentSubtypeRefForFunctionsTest extends AbstractJudgmentSubtypeTest {

	@Test
	def void testSubTypeFunctionRefsEmpty() {
		assertSubType("/*1*/{function()}", "/*2*/{function()}");
	}

	@Test
	def void testSubTypeFunctionRefsWithReturn() {
		assertSubType("/*1*/{function():A}", "/*2*/{function():A}");
		assertSubType("{function():B}", "{function():A}");
		assertSubType("{function():A}", "{function():B}", false);
	}

	@Test
	def void testSubTypeFunctionRefsWithParameters() {
		assertSubType("/*1*/{function(A)}", "/*2*/{function(A)}");
		assertSubType("{function(A)}", "{function(B)}");
		assertSubType("{function(B)}", "{function(A)}", false);
	}

}
