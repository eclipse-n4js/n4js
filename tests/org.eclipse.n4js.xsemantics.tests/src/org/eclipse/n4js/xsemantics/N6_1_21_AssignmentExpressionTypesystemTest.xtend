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

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test class for assignment expressions (6.1.21)
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_21_AssignmentExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	val String TOP_TYPE = "any"; // the top type is currently type 'any'
	val String BOTTOM_TYPE = "undefined"; // the bottom type is currently type 'undefined'

	@Test
	def void testSimpleAssignmentType() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			val dyn = if(mode.isECMAScript) "+" else "";

			assertOperatorType(mode, "number"+dyn, '''s1 = n2''')
			assertOperatorType(mode, "string"+dyn, '''s1 = s2''')
			assertOperatorType(mode, "boolean"+dyn, '''s1 = f2''')
			assertOperatorType(mode, "A"+dyn, '''s1 = a''')
			assertOperatorType(mode, "B"+dyn, '''s1 = b''')

			assertOperatorType(mode, "number"+dyn, '''n1 = n2''')
			assertOperatorType(mode, "string"+dyn, '''n1 = s2''')
			assertOperatorType(mode, "boolean"+dyn, '''n1 = f2''')
			assertOperatorType(mode, "A"+dyn, '''n1 = a''')
			assertOperatorType(mode, "B"+dyn, '''n1 = b''')

			assertOperatorType(mode, "int", '''n1 = 1''')
			assertOperatorType(mode, "number", '''n1 = 1.0''')
			assertOperatorType(mode, "string", '''n1 = "hello"''')
			assertOperatorType(mode, "boolean", '''n1 = true''')
		}
	}

	@Test
	def void testAddCompoundAssignmentType() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "string", '''n1 += s1''')

			assertOperatorType(mode, "string", '''s1 += n2''')
			assertOperatorType(mode, "string", '''s1 += s2''')
			assertOperatorType(mode, "string", '''s1 += f2''')
			assertOperatorType(mode, "string", '''s1 += a''')
			assertOperatorType(mode, "string", '''s1 += b''')

			assertOperatorType(mode, "number", '''n1 += n2''')
			assertOperatorType(mode, "string", '''n1 += s2''')
			assertOperatorType(mode, "number", '''n1 += f2''')
			assertOperatorType(mode, "string", '''n1 += a''')
			assertOperatorType(mode, "string", '''n1 += b''')

			assertOperatorType(mode, "number", '''n1 += 1''')
			assertOperatorType(mode, "string", '''n1 += "hello"''')
			assertOperatorType(mode, "number", '''n1 += true''')
		}
	}

	@Test
	def void testOtherCompundAssignments() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			for (op : #["*=", "/=", "%=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|="]) {
				assertOperatorType(mode, "number", '''s1 «op» n2''')
				assertOperatorType(mode, "number", '''s1 «op» s2''')
				assertOperatorType(mode, "number", '''s1 «op» f2''')
				assertOperatorType(mode, "number", '''s1 «op» a''')
				assertOperatorType(mode, "number", '''s1 «op» b''')

				assertOperatorType(mode, "number", '''n1 «op» n2''')
				assertOperatorType(mode, "number", '''n1 «op» s2''')
				assertOperatorType(mode, "number", '''n1 «op» f2''')
				assertOperatorType(mode, "number", '''n1 «op» a''')
				assertOperatorType(mode, "number", '''n1 «op» b''')

				assertOperatorType(mode, "number", '''n1 «op» 1''')
				assertOperatorType(mode, "number", '''n1 «op» "hello"''')
				assertOperatorType(mode, "number", '''n1 «op» true''')
			}
		}
	}
	
	private def void assertExpectedType(JavaScriptVariant variant, String expectedLeftType, String expectedRightType,
		String expression) {
			assertBinaryOperatorExpectedType(variant,
				if (variant.isTypeAwareVariant) expectedLeftType else BOTTOM_TYPE,
				if (variant.isTypeAwareVariant) expectedRightType else TOP_TYPE,
				expression
			);
	}
	
	/** Returns {@code true} if the {@code variant} is considered 
	 * type-aware in the context of this test. */
	private def boolean isTypeAwareVariant(JavaScriptVariant variant) {
		return #[JavaScriptVariant.n4js, JavaScriptVariant.n4idl].contains(variant);
	}

	@Test
	def void testExpectedTypeSimpleAssignment() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertExpectedType(mode, BOTTOM_TYPE, "string", '''s1 = n2''')
			assertExpectedType(mode, BOTTOM_TYPE, "string", '''s1 = s2''')
			assertExpectedType(mode, BOTTOM_TYPE, "string", '''s1 = f2''')
			assertExpectedType(mode, BOTTOM_TYPE, "string", '''s1 = a''')
			assertExpectedType(mode, BOTTOM_TYPE, "string", '''s1 = b''')

			assertExpectedType(mode, BOTTOM_TYPE, "number", '''n1 = n2''')
			assertExpectedType(mode, BOTTOM_TYPE, "number", '''n1 = s2''')
			assertExpectedType(mode, BOTTOM_TYPE, "number", '''n1 = f2''')
			assertExpectedType(mode, BOTTOM_TYPE, "number", '''n1 = a''')
			assertExpectedType(mode, BOTTOM_TYPE, "number", '''n1 = b''')
		}
	}

	@Test
	def void testExpectedTypeAddCompoundAssignmentType() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertExpectedType(mode,  "intersection{number,string}", "any", '''s1 += n2''')
			assertExpectedType(mode,  "intersection{number,string}", "any", '''s1 += s2''')
			assertExpectedType(mode,  "intersection{number,string}", "any", '''s1 += f2''')
			assertExpectedType(mode,  "intersection{number,string}", "any", '''s1 += a''')
			assertExpectedType(mode,  "intersection{number,string}", "any", '''s1 += b''')

			assertExpectedType(mode,  "intersection{number,string}", "number", '''n1 += n2''')
			assertExpectedType(mode,  "intersection{number,string}", "number", '''n1 += s2''')
			assertExpectedType(mode,  "intersection{number,string}", "number", '''n1 += f2''')
			assertExpectedType(mode,  "intersection{number,string}", "number", '''n1 += a''')
			assertExpectedType(mode,  "intersection{number,string}", "number", '''n1 += b''')
		}
	}

	@Test
	def void testExpectedTypeOtherCompundAssignments() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			for (op : #["*=", "/=", "%=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|="]) {
				assertExpectedType(mode,  "number", "number", '''s1 «op» n2''')
				assertExpectedType(mode,  "number", "number", '''s1 «op» s2''')
				assertExpectedType(mode,  "number", "number", '''s1 «op» f2''')
				assertExpectedType(mode,  "number", "number", '''s1 «op» a''')

				assertExpectedType(mode,  "number", "number", '''n1 «op» n2''')
				assertExpectedType(mode,  "number", "number", '''n1 «op» s2''')
				assertExpectedType(mode,  "number", "number", '''n1 «op» f2''')
				assertExpectedType(mode,  "number", "number", '''n1 «op» a''')
			}
		}
	}

}
