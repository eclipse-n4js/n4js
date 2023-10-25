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
 * Test class for assignment expressions (6.1.21)
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_21_AssignmentExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	String TOP_TYPE = "any"; // the top type is currently type 'any'
	String BOTTOM_TYPE = "undefined"; // the bottom type is currently type 'undefined'

	@Test
	public void testSimpleAssignmentType() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			String dyn = mode.isECMAScript() ? "+" : "";

			assertOperatorType(mode, "number" + dyn, "s1 = n2");
			assertOperatorType(mode, "string" + dyn, "s1 = s2");
			assertOperatorType(mode, "boolean" + dyn, "s1 = f2");
			assertOperatorType(mode, "A" + dyn, "s1 = a");
			assertOperatorType(mode, "B" + dyn, "s1 = b");

			assertOperatorType(mode, "number" + dyn, "n1 = n2");
			assertOperatorType(mode, "string" + dyn, "n1 = s2");
			assertOperatorType(mode, "boolean" + dyn, "n1 = f2");
			assertOperatorType(mode, "A" + dyn, "n1 = a");
			assertOperatorType(mode, "B" + dyn, "n1 = b");

			assertOperatorType(mode, "int", "n1 = 1");
			assertOperatorType(mode, "number", "n1 = 1.0");
			assertOperatorType(mode, "string", "n1 = \"hello\"");
			assertOperatorType(mode, "boolean", "n1 = true");
		}
	}

	@Test
	public void testAddCompoundAssignmentType() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "string", "n1 += s1");

			assertOperatorType(mode, "string", "s1 += n2");
			assertOperatorType(mode, "string", "s1 += s2");
			assertOperatorType(mode, "string", "s1 += f2");
			assertOperatorType(mode, "string", "s1 += a");
			assertOperatorType(mode, "string", "s1 += b");

			assertOperatorType(mode, "number", "n1 += n2");
			assertOperatorType(mode, "string", "n1 += s2");
			assertOperatorType(mode, "number", "n1 += f2");
			assertOperatorType(mode, "string", "n1 += a");
			assertOperatorType(mode, "string", "n1 += b");

			assertOperatorType(mode, "number", "n1 += 1");
			assertOperatorType(mode, "string", "n1 += \"hello\"");
			assertOperatorType(mode, "number", "n1 += true");
		}
	}

	@Test
	public void testOtherCompundAssignments() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			for (String op : List.of("*=", "/=", "%=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|=")) {
				assertOperatorType(mode, "number", "s1 " + op + " n2");
				assertOperatorType(mode, "number", "s1 " + op + " s2");
				assertOperatorType(mode, "number", "s1 " + op + " f2");
				assertOperatorType(mode, "number", "s1 " + op + " a");
				assertOperatorType(mode, "number", "s1 " + op + " b");

				assertOperatorType(mode, "number", "n1 " + op + " n2");
				assertOperatorType(mode, "number", "n1 " + op + " s2");
				assertOperatorType(mode, "number", "n1 " + op + " f2");
				assertOperatorType(mode, "number", "n1 " + op + " a");
				assertOperatorType(mode, "number", "n1 " + op + " b");

				assertOperatorType(mode, "number", "n1 " + op + " 1");
				assertOperatorType(mode, "number", "n1 " + op + " \"hello\"");
				assertOperatorType(mode, "number", "n1 " + op + " true");
			}
		}
	}

	private void assertExpectedType(JavaScriptVariant variant, String expectedLeftType, String expectedRightType,
			String expression) {

		assertBinaryOperatorExpectedType(variant,
				(isTypeAwareVariant(variant) ? expectedLeftType : BOTTOM_TYPE),
				(isTypeAwareVariant(variant) ? expectedRightType : TOP_TYPE),
				expression);
	}

	/**
	 * Returns {@code true} if the {@code variant} is considered type-aware in the context of this test.
	 */
	private boolean isTypeAwareVariant(JavaScriptVariant variant) {
		return JavaScriptVariant.n4js == variant;
	}

	@Test
	public void testExpectedTypeSimpleAssignment() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			assertExpectedType(mode, BOTTOM_TYPE, "string", "s1 = n2");
			assertExpectedType(mode, BOTTOM_TYPE, "string", "s1 = s2");
			assertExpectedType(mode, BOTTOM_TYPE, "string", "s1 = f2");
			assertExpectedType(mode, BOTTOM_TYPE, "string", "s1 = a");
			assertExpectedType(mode, BOTTOM_TYPE, "string", "s1 = b");

			assertExpectedType(mode, BOTTOM_TYPE, "number", "n1 = n2");
			assertExpectedType(mode, BOTTOM_TYPE, "number", "n1 = s2");
			assertExpectedType(mode, BOTTOM_TYPE, "number", "n1 = f2");
			assertExpectedType(mode, BOTTOM_TYPE, "number", "n1 = a");
			assertExpectedType(mode, BOTTOM_TYPE, "number", "n1 = b");
		}
	}

	@Test
	public void testExpectedTypeAddCompoundAssignmentType() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			assertExpectedType(mode, "intersection{number,string}", "any", "s1 += n2");
			assertExpectedType(mode, "intersection{number,string}", "any", "s1 += s2");
			assertExpectedType(mode, "intersection{number,string}", "any", "s1 += f2");
			assertExpectedType(mode, "intersection{number,string}", "any", "s1 += a");
			assertExpectedType(mode, "intersection{number,string}", "any", "s1 += b");

			assertExpectedType(mode, "intersection{number,string}", "number", "n1 += n2");
			assertExpectedType(mode, "intersection{number,string}", "number", "n1 += s2");
			assertExpectedType(mode, "intersection{number,string}", "number", "n1 += f2");
			assertExpectedType(mode, "intersection{number,string}", "number", "n1 += a");
			assertExpectedType(mode, "intersection{number,string}", "number", "n1 += b");
		}
	}

	@Test
	public void testExpectedTypeOtherCompundAssignments() {
		for (JavaScriptVariant mode : JavaScriptVariant.nonDepricatedValues()) {
			for (String op : List.of("*=", "/=", "%=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|=")) {
				assertExpectedType(mode, "number", "number", "s1 " + op + " n2");
				assertExpectedType(mode, "number", "number", "s1 " + op + " s2");
				assertExpectedType(mode, "number", "number", "s1 " + op + " f2");
				assertExpectedType(mode, "number", "number", "s1 " + op + " a");

				assertExpectedType(mode, "number", "number", "n1 " + op + " n2");
				assertExpectedType(mode, "number", "number", "n1 " + op + " s2");
				assertExpectedType(mode, "number", "number", "n1 " + op + " f2");
				assertExpectedType(mode, "number", "number", "n1 " + op + " a");
			}
		}
	}

}
