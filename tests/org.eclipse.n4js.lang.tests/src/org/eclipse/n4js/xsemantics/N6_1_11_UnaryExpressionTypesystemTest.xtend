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
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.JavaScriptVariant.*
import org.eclipse.n4js.validation.JavaScriptVariant

/**
 * Test class for operator test (6.1.10- 6.1.18)
 * 
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_11_UnaryExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	@Test
	def void testType_Delete() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {

			// TODO handle object literals
			// assertOperatorType(mode, "boolean", "delete ol.prop");
			// assertOperatorType(mode, "boolean", "delete ol.unknownprop"); // cannot bind
			// assertOperatorType(mode, "boolean", "delete s1.unknownprop"); // cannot bind
			assertOperatorType(mode, "boolean", "delete 1");
		}
	}

	@Test
	def void testExpectedTypeIn_Delete() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertUnaryOperatorExpectedType(mode, "union{any,void}", "delete n1");
		}
	}

	@Test
	def void testType_Void() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "undefined", "void s1");
			assertOperatorType(mode, "undefined", "void n1");
			assertOperatorType(mode, "undefined", "void f1");
			assertOperatorType(mode, "undefined", "void a");
			assertOperatorType(mode, "undefined", "void 1");
			assertOperatorType(mode, "undefined", "void (1+2)");
		}
	}

	@Test
	def void testExpectedTypeIn_Void() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertUnaryOperatorExpectedType(mode, "union{any,void}", "void n1");
		}
	}

	@Test
	def void testType_Typof() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "string", "typeof s1");
			assertOperatorType(mode, "string", "typeof n1");
			assertOperatorType(mode, "string", "typeof f1");
			assertOperatorType(mode, "string", "typeof a");
			assertOperatorType(mode, "string", "typeof 1");
			assertOperatorType(mode, "string", "typeof (1+2)");
		}
	}

	@Test
	def void testExpectedTypeIn_Typeof() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertUnaryOperatorExpectedType(mode, "union{any,void}", "typeof n1");
		}
	}

	@Test
	def void testType_Inc() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''++n1''');
			assertOperatorType(mode, "number", '''++s1'''); // failure, type should still be number
		}
	}

	@Test
	def void testExpectedTypeIn_PrefixInc() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "++n1");
		assertUnaryOperatorExpectedType(strict, "any", "++n1");
		assertUnaryOperatorExpectedType(n4js, "number", "++n1");

	}

	@Test
	def void testType_Dec() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''--n1''');
			assertOperatorType(mode, "number", '''--s1'''); // failure, type should still be number
		}
	}

	@Test
	def void testExpectedTypeIn_PrefixDec() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "--n1");
		assertUnaryOperatorExpectedType(strict, "any", "--n1");
		assertUnaryOperatorExpectedType(n4js, "number", "--n1");
	}

	@Test
	def void testType_Pos() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''+n1''');
			assertOperatorType(mode, "number", '''+s1'''); // failure, type should still be number
		}
	}

	@Test
	def void testExpectedTypeIn_PrefixPos() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "+n1");
		assertUnaryOperatorExpectedType(strict, "any", "+n1");
		assertUnaryOperatorExpectedType(n4js, "number", "+n1");
	}

	@Test
	def void testType_Neg() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''-n1''');
			assertOperatorType(mode, "number", '''-s1'''); // failure, type should still be number
		}
	}

	@Test
	def void testExpectedTypeIn_PrefixMinus() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "-n1");
		assertUnaryOperatorExpectedType(strict, "any", "-n1");
		assertUnaryOperatorExpectedType(n4js, "number", "-n1");
	}

	@Test
	def void testType_BitwiseNot() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''~n1''');
			assertOperatorType(mode, "number", '''~s1''');
		}
	}

	@Test
	def void testExpectedTypeIn_BitwiseNot() {
		assertUnaryOperatorExpectedType(unrestricted, "any", "~n1");
		assertUnaryOperatorExpectedType(strict, "any", "~n1");
		assertUnaryOperatorExpectedType(n4js, "number", "~n1");
	}

	@Test
	def void testType_Not() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "boolean", '''!n1''');
			assertOperatorType(mode, "boolean", '''!s1''');
			assertOperatorType(mode, "boolean", '''!f1''');
			assertOperatorType(mode, "boolean", '''!a''');

			// cannot handle object literals yet
			// assertOperatorType(mode, "boolean", '''!ol''');
			assertOperatorType(mode, "boolean", '''!true''');
			assertOperatorType(mode, "boolean", '''!false''');
			assertOperatorType(mode, "boolean", '''!0''');
		}
	}

	@Test
	def void testExpectedTypeIn_Not() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertUnaryOperatorExpectedType(mode, "any", "! f1");
			assertUnaryOperatorExpectedType(mode, "any", "! n1");
			assertUnaryOperatorExpectedType(mode, "any", "! s1");
		}
	}

}
