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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * N4JS Spec Test: 6.1.6. Parenthesized Expression and Grouping Operator, Type Inference
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_06_ParenthesizedExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	@Inject
	TypeCompareHelper typeCompareHelper;

	final static CharSequence scriptPrefix = """
			class A{}
			class B extends A{}
			var f: boolean;
			var a: A;
			var b: B;
			""";

	public void assertParenthesisedExpressionType(String expression) {
		try {

			Script script = parseHelper.parse(scriptPrefix + expression + ";\n" + "(" + expression + ")");

			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			int size = script.getScriptElements().size();
			Expression pexpr = ((ExpressionStatement) script.getScriptElements().get(size - 1)).getExpression();
			Expression expr = ((ExpressionStatement) script.getScriptElements().get(size - 2)).getExpression();
			assertTrue(pexpr instanceof ParenExpression);
			TypeRef typeExpr = checkedType(G, expr);
			TypeRef typePExpr = checkedType(G, pexpr);
			assertEquals(typeExpr.getTypeRefAsString(), typePExpr.getTypeRefAsString());
			assertTrue(
					typeExpr.getTypeRefAsString()
							+ " is correct, but something went wrong as type is not compared as equivalent",
					typeCompareHelper.compare(typeExpr, typePExpr) == 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testParenthesisedExpressionType() {
		assertParenthesisedExpressionType("10");
		assertParenthesisedExpressionType("\"hello\"");
		assertParenthesisedExpressionType("true");
		assertParenthesisedExpressionType("a");
		assertParenthesisedExpressionType("10-5");
		// assertParenthesisedExpressionType("f?a:b"); see IDE-348 (6.1.19. Conditional Expression)
	}

	@Test
	public void testValidateSpecExample() {
		try {
			Script script = parseHelper.parse("""
							class A{} class B extends A{}
							var f: boolean; var a: A; var b: B;

							/* simple		<->		parenthesized */
							10;						(10);
							"hello";				("hello");
							true;					(true);
							a;						(a);
							10-5;					(10-5);
					//		f?a:b					(f?a:b); see IDE-348 (6.1.19. Conditional Expression)
							""");
			valTestHelper.assertNoErrors(script);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
