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
class N6_1_13_AdditiveExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	/**
	 * see examples in 6_1_13
	 */
	@Test
	def void testType_Add() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''3+4''')
			assertOperatorType(mode, "string", '''"hello"+"world"''')

			assertOperatorType(mode, "number", '''n1+n2''')
			assertOperatorType(mode, "number", '''n1+null''')
			assertOperatorType(mode, "number", '''n1+undefined''')
			assertOperatorType(mode, "number", '''null+n1''')
			assertOperatorType(mode, "number", '''undefined+n1''')
			assertOperatorType(mode, "number", '''undefined+f1''')
			assertOperatorType(mode, "number", '''null+f1''')
			assertOperatorType(mode, "number", '''f1+null''')

			assertOperatorType(mode, "string", '''s1+s2''')
			assertOperatorType(mode, "string", '''s1+n1''')
			assertOperatorType(mode, "string", '''s1+f1''')
			assertOperatorType(mode, "string", '''n1+s1''')
			assertOperatorType(mode, "string", '''f1+s1''')
			assertOperatorType(mode, "string", '''null+s1''')
			assertOperatorType(mode, "string", '''undefined+s1''')
			assertOperatorType(mode, "string", '''s1+undefined''')
			assertOperatorType(mode, "string", '''s1+null''')
		}
	}

	@Test
	def void testExpectedType_Add() {
		assertBinaryOperatorExpectedType(unrestricted, "any", "any", "n1+n2");
		assertBinaryOperatorExpectedType(strict, "any", "any", "n1+n2");
		assertBinaryOperatorExpectedType(n4js, "any", "any", "n1+n2");
	}

	/**
	 * see examples in 6_1_13
	 */
	@Test
	def void testType_Sub() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			assertOperatorType(mode, "number", '''3-4''')
			assertOperatorType(mode, "number", '''"a"-"b"''')
			assertOperatorType(mode, "number", '''n1-n2''')
			assertOperatorType(mode, "number", '''s1-s2''')
			assertOperatorType(mode, "number", '''f1-f2''')
			assertOperatorType(mode, "number", '''s1-n2''')
		}
	}

	@Test
	def void testExpectedType_Sub() {
		assertBinaryOperatorExpectedType(unrestricted, "any", "any", "n1-n2");
		assertBinaryOperatorExpectedType(strict, "any", "any", "n1-n2");
		assertBinaryOperatorExpectedType(n4js, "any", "any", "n1-n2");
	}
}
