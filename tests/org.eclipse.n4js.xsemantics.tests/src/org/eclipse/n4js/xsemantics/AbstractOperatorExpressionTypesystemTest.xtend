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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Base test class for operator test (6.1.10- 6.1.18)
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractOperatorExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject extension ValidationTestHelper

	final static CharSequence scriptPrefix = '''
	class A{}
	class B extends A{}
	var a: A;
	var b: B;
	var n1: number;
	var n2: number;
	var s1: string;
	var s2: string;
	var f1: boolean;
	var f2: boolean;
	«««			var Object ol = { prop: "hello" };
	'''

	def void assertOperatorType(JavaScriptVariant variant, String expectedTypeAsString, String expression) {
		val script = createScript(variant, scriptPrefix + expression + ";\n");
		// assertNoErrors(script); we ignore errors: type should be computed even if there are errors
		assertNotNull("Syntax Error at " + expression + " // " + variant, script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val expr = (script.scriptElements.reverseView.head as ExpressionStatement).expression;

		val typeExpr = ts.type(G, expr);

		assertEquals("Assert at " + expression + " // " + variant, expectedTypeAsString, typeExpr.typeRefAsString);
	}

	def void assertOperatorFailure(JavaScriptVariant variant, String expression) {
		val script = createScript(variant, scriptPrefix + expression + ";\n");
		assertFalse(script.validate.empty);
	}

	def void assertOperatorSuccess(JavaScriptVariant variant, String expression) {
		val script = createScript(variant, scriptPrefix + expression + ";\n");
		val issues = script.validate
		assertTrue(issues.toString, issues.empty);
	}

	def void assertUnaryOperatorExpectedType(JavaScriptVariant variant, String expectedSubType, String expression) {
		assertOperatorExpectedType(variant, #[expectedSubType], expression);
	}

	def void assertBinaryOperatorExpectedType(JavaScriptVariant variant, String expectedLeftType, String expectedRightType,
		String expression) {
		assertOperatorExpectedType(variant, #[expectedLeftType, expectedRightType], expression);
	}

	def void assertOperatorExpectedType(JavaScriptVariant variant, String[] expectedSubTypes, String expression) {
		val script = createScript(variant, scriptPrefix + expression + ";\n");
//		assertNoErrors(script); we only want to test the expected type, there may be other errors
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val expr = (script.scriptElements.reverseView.head as ExpressionStatement).expression;

		val subExpr = expr.eContents.filter(Expression)
		assertEquals("Unexpected number of sub expressions", expectedSubTypes.length, subExpr.size);
		var i = 0;
		while (i < subExpr.length) {
			val actualExpectedTypeResult = ts.expectedType(G, expr, subExpr.get(i));
			assertEquals(expression+", operand " + i + " in mode " + variant.name, 
				expectedSubTypes.get(i), actualExpectedTypeResult.typeRefAsString);
			i = i + 1
		}
	}


}
