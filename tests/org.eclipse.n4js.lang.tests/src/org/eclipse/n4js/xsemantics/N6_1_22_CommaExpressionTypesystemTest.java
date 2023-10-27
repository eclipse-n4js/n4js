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

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test class for comma expression
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_22_CommaExpressionTypesystemTest extends AbstractTypesystemTest {

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

	void assertCommaExpressionType(String expectedTypeName, String... expressions) {
		try {

			List<String> exprList = Arrays.asList(expressions);
			Script script = parseHelper.parse(scriptPrefix + last(exprList) + ";\n" + join(",", exprList));

			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			int elemSize = script.getScriptElements().size();
			Expression cexpr = ((ExpressionStatement) script.getScriptElements().get(elemSize - 1)).getExpression();
			Expression expr = ((ExpressionStatement) script.getScriptElements().get(elemSize - 2)).getExpression();
			assertTrue(cexpr instanceof CommaExpression);
			TypeRef typeExpr = checkedType(G, expr);
			TypeRef typePExpr = checkedType(G, cexpr);
			assertEquals(expectedTypeName, typePExpr.getTypeRefAsString());
			assertEquals("Failed comparing with last expression", typeExpr.getTypeRefAsString(),
					typePExpr.getTypeRefAsString());
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
	public void testCommaExpressionType() {
		assertCommaExpressionType("2", "1", "2");
		assertCommaExpressionType("\"hello\"", "1", "2", "\"hello\"");
		assertCommaExpressionType("true", "1", "\"string\"", "2", "true");
		assertCommaExpressionType("A", "1", "a");
		assertCommaExpressionType("number", "1", "\"hello\"", "10-5");

		assertCommaExpressionType("2", "\"x\"", "2");
		assertCommaExpressionType("2", "\"x\"", "2.0");
		assertCommaExpressionType("\"hello\"", "\"x\"", "\"hello\"");
		assertCommaExpressionType("true", "\"x\"", "true");
		assertCommaExpressionType("A", "\"x\"", "a");
		assertCommaExpressionType("number", "\"x\"", "10-5");
	}

	@Test
	public void testValidateLoopWithCommas() {
		Script script = createScript(JavaScriptVariant.unrestricted,
				"""
						var i,k;
						for (i=0, k=0; i<10; i++, k+=10) {
							var x = i + " * 10 = " + k; // console.log(i + " * 10 = " + k);
						}
						""");
		valTestHelper.assertNoErrors(script, N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER);
	}

	@Test
	public void testValidatePlusPlus() {
		Script script = createScript(JavaScriptVariant.unrestricted,
				"""
							var i;
							i++
						""");
		valTestHelper.assertNoErrors(script, N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER);
	}
}
