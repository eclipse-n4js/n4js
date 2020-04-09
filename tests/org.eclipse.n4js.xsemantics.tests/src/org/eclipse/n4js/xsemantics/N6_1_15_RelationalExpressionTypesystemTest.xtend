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
class N6_1_15_RelationalExpressionTypesystemTest extends AbstractOperatorExpressionTypesystemTest {

	@Test
	def void testType() {
		for (mode : JavaScriptVariant.nonDepricatedValues()) {
			for (op : #["<", "<=", ">", ">=", "instanceof", "in"]) {
				assertOperatorType(mode, "boolean", '''n1 «op» n2''')
				assertOperatorType(mode, "boolean", '''s1 «op» s2''')

			}
		}
	}

	@Test
	def void testExpectedType_LessEtc() {
		for (op : #["<", "<=", ">", ">="]) {
			assertBinaryOperatorExpectedType(unrestricted, "any", "any", '''n1 «op» n2''');
			assertBinaryOperatorExpectedType(strict, "any", "any", '''n1 «op» n2''');
			assertBinaryOperatorExpectedType(n4js, "number", "number", '''n1 «op» n2''');
			assertBinaryOperatorExpectedType(n4js, "string", "string", '''s1 «op» s2''');
			assertBinaryOperatorExpectedType(n4js, "boolean", "boolean", '''f1 «op» f2''');
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{number,string,boolean}", '''undefined «op» f2''');
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{number,string,boolean}", '''null «op» f2''');
			assertBinaryOperatorExpectedType(n4js, "boolean", "union{number,string,boolean}", '''a «op» f2''');

		}
	}

	@Test
	def void testExpectedType_InstanceOf() {
		assertBinaryOperatorExpectedType(unrestricted, "any",
			"union{Function,type{Object},type{N4Enum}}", '''n1 instanceof n2''');
		assertBinaryOperatorExpectedType(strict, "any",
			"union{Function,type{Object},type{N4Enum}}", '''n1 instanceof n2''');
		assertBinaryOperatorExpectedType(n4js, "any",
			"union{Function,type{Object},type{N4Enum}}", '''n1 instanceof n2''');
	}

	@Test
	def void testExpectedType_In() {
		assertBinaryOperatorExpectedType(unrestricted, "any", "Object", '''n1 in n2''');
		assertBinaryOperatorExpectedType(strict, "any", "Object", '''n1 in n2''');
		assertBinaryOperatorExpectedType(n4js, "union{number,string,symbol}", "Object", '''n1 in n2''');
	}
}
