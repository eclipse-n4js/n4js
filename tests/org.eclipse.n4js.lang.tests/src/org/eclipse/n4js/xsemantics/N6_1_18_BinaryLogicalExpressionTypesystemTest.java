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

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for operator test (6.1.10- 6.1.18)
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_18_BinaryLogicalExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	@Test
	public void testType() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			String dyn = (mode.isECMAScript()) ? "+" : "";

			for (String op : List.of("&&", "||")) {
				assertOperatorType(mode, "number" + dyn, "n1 " + op + " n2");
				assertOperatorType(mode, "string" + dyn, "s1 " + op + " s2");
				assertOperatorType(mode, "boolean" + dyn, "f1 " + op + " f2");
				assertOperatorType(mode, "union{number" + dyn + ",string" + dyn + "}", "s1 " + op + " n2");
				assertOperatorType(mode, "union{A" + dyn + ",B" + dyn + "}", "a " + op + " b");
				assertOperatorType(mode, "A" + dyn, "a " + op + " a");
				assertOperatorType(mode, "A" + dyn, "undefined " + op + " a");
				assertOperatorType(mode, "A" + dyn, "a " + op + " undefined");
				assertOperatorType(mode, "A" + dyn, "null " + op + " a");
				assertOperatorType(mode, "A" + dyn, "a " + op + " null");
			}
		}
	}

	@Test
	public void testExpectedType_NonStrict() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			for (String op : List.of("&&", "||")) {
				assertBinaryOperatorExpectedType(mode, "any", "any", "n1 " + op + " n2");
				assertBinaryOperatorExpectedType(mode, "any", "any", "s1 " + op + " s2");
				assertBinaryOperatorExpectedType(mode, "any", "any", "s1 " + op + " f1");
				assertBinaryOperatorExpectedType(mode, "any", "any", "s1 " + op + " a");
				assertBinaryOperatorExpectedType(mode, "any", "any", "a " + op + " a");
				assertBinaryOperatorExpectedType(mode, "any", "any", "a " + op + " b");
			}
		}
	}
}
