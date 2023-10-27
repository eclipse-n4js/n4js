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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Base test class for operator test (6.1.10- 6.1.18)
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
abstract public class AbstractOperatorExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	final static CharSequence scriptPrefix = """
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
			//			var Object ol = { prop: "hello" };
			""";

	protected void assertOperatorType(JavaScriptVariant variant, String expectedTypeAsString, String expression) {
		Script script = createScript(variant, scriptPrefix + expression + ";\n");
		// assertNoErrors(script); we ignore errors: type should be computed even if there are errors
		assertNotNull("Syntax Error at " + expression + " // " + variant, script);
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();

		TypeRef typeExpr = ts.type(G, expr);

		assertEquals("Assert at " + expression + " // " + variant, expectedTypeAsString, typeExpr.getTypeRefAsString());
	}

	protected void assertOperatorFailure(JavaScriptVariant variant, String expression) {
		Script script = createScript(variant, scriptPrefix + expression + ";\n");
		assertFalse(valTestHelper.validate(script).isEmpty());
	}

	protected void assertOperatorSuccess(JavaScriptVariant variant, String expression) {
		Script script = createScript(variant, scriptPrefix + expression + ";\n");
		List<Issue> issues = valTestHelper.validate(script);
		assertTrue(issues.toString(), issues.isEmpty());
	}

	protected void assertUnaryOperatorExpectedType(JavaScriptVariant variant, String expectedSubType,
			String expression) {
		assertOperatorExpectedType(variant, new String[] { expectedSubType }, expression);
	}

	protected void assertBinaryOperatorExpectedType(JavaScriptVariant variant, String expectedLeftType,
			String expectedRightType, String expression) {
		assertOperatorExpectedType(variant, new String[] { expectedLeftType, expectedRightType }, expression);
	}

	protected void assertOperatorExpectedType(JavaScriptVariant variant, String[] expectedSubTypes, String expression) {
		Script script = createScript(variant, scriptPrefix + expression + ";\n");
		// assertNoErrors(script); we only want to test the expected type, there may be other errors
		assertNotNull(script);
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();

		List<Expression> subExpr = toList(filter(expr.eContents(), Expression.class));
		assertEquals("Unexpected number of sub expressions", expectedSubTypes.length, subExpr.size());

		for (int i = 0; i < subExpr.size(); i++) {
			TypeRef actualExpectedTypeResult = ts.expectedType(G, expr, subExpr.get(i));
			assertEquals(expression + ", operand " + i + " in mode " + variant.name(),
					expectedSubTypes[i], actualExpectedTypeResult.getTypeRefAsString());
		}
	}

}
