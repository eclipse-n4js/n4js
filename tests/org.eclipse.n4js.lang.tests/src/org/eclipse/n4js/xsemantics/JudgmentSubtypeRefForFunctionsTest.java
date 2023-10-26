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
package org.eclipse.n4js.xsemantics;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class JudgmentSubtypeRefForFunctionsTest extends AbstractJudgmentSubtypeTest {

	@Test
	public void testSubTypeFunctionRefsEmpty() {
		assertSubType("/*1*/{function()}", "/*2*/{function()}");
	}

	@Test
	public void testSubTypeFunctionRefsWithReturn() {
		assertSubType("/*1*/{function():A}", "/*2*/{function():A}");
		assertSubType("{function():B}", "{function():A}");
		assertSubType("{function():A}", "{function():B}", false);
	}

	@Test
	public void testSubTypeFunctionRefsWithParameters() {
		assertSubType("/*1*/{function(A)}", "/*2*/{function(A)}");
		assertSubType("{function(A)}", "{function(B)}");
		assertSubType("{function(B)}", "{function(A)}", false);
	}

}
