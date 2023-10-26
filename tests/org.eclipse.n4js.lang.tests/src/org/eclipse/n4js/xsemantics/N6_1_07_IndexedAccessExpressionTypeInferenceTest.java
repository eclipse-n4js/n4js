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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for indexed access type inference.
 *
 * @see N6_1_04_ArrayLiteralTypesystemTest array literal type inference test
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_07_IndexedAccessExpressionTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

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
			""";

	public void assertIndexedAccessExpressionType(String expectedElementType, String arrayliteral) {
		try {
			Script script = parseHelper.parse("""
					%s
					var testArray = %s;
					testArray[0];
					""".formatted(scriptPrefix, arrayliteral));
			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();
			assertTrue("Expected IndexedAccessExpression, was " + expr.eClass().getName(),
					expr instanceof IndexedAccessExpression);
			TypeRef typeExpr = checkedType(G, expr);
			assertEquals(expectedElementType, typeExpr.getTypeRefAsString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	public void assertGenericIndexedAccessExpressionType(String expectedElementType, String arrayType) {
		try {

			Script script = parseHelper.parse("""
					%s
					var testArray: Array<%s> = null;
					testArray[0];
					""".formatted(scriptPrefix, arrayType));
			assertNotNull(script);
			Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();
			assertTypeName(expectedElementType, expr);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testUndefinedElementType() {
		assertIndexedAccessExpressionType("any", "[]");
		assertIndexedAccessExpressionType("any", "[,]");
		assertIndexedAccessExpressionType("any", "[,,,]");
	}

	@Test
	public void testSingleElementType() {
		assertIndexedAccessExpressionType("string", "[\"Walter\"]");
		assertIndexedAccessExpressionType("string", "[\"Walter\"\"]"); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("string", "[\"Walter\", \"Werner\"]");
		assertIndexedAccessExpressionType("int", "[1]");
		assertIndexedAccessExpressionType("int", "[1,2,3]");
		assertIndexedAccessExpressionType("number", "[n1]");
		assertIndexedAccessExpressionType("number", "[n1,n2]");
		assertIndexedAccessExpressionType("number", "[n1,2,3]");
		assertIndexedAccessExpressionType("string", "[s1]");
		assertIndexedAccessExpressionType("string", "[s1,s2]");
		assertIndexedAccessExpressionType("A", "[a]");
		assertIndexedAccessExpressionType("A", "[a,a,a]");
	}

	@Test
	public void testUnionElementType() {
		assertIndexedAccessExpressionType("union{string,int}", "[\"Walter\", 1]");
		assertIndexedAccessExpressionType("union{string,int}", "[\"Walter\", 1\"]"); // syntax error was intended (I
																						// guess)
		assertIndexedAccessExpressionType("union{string,A,int}", "[\"Walter\", a, 1]");
		assertIndexedAccessExpressionType("union{string,A,int}", "[\"Walter\", a, 1\"]"); // syntax error was intended
																							// (I guess)
		assertIndexedAccessExpressionType("A", "[a,b]");
		assertIndexedAccessExpressionType("A", "[a,b,a]");
		assertIndexedAccessExpressionType("A", "[a,b,a,b,a,a,b]");
	}

	@Test
	public void testElementTypeWithIgnoredPadding() {
		assertIndexedAccessExpressionType("A", "[a,]");
		assertIndexedAccessExpressionType("A", "[,a]");
		assertIndexedAccessExpressionType("A", "[,a,]");
		assertIndexedAccessExpressionType("A", "[,a,,,,]");

		assertIndexedAccessExpressionType("union{string,int}", "[,\"Walter\", 1]");
		assertIndexedAccessExpressionType("union{string,int}", "[,\"Walter\", 1\"]"); // syntax error was intended (I
																						// guess)
		assertIndexedAccessExpressionType("union{string,A,int}", "[\"Walter\", a, 1,]");
		assertIndexedAccessExpressionType("union{string,A,int}", "[\"Walter\", a, 1\",]"); // syntax error was intended
																							// (I guess)
		assertIndexedAccessExpressionType("A", "[a,b,,]");
		assertIndexedAccessExpressionType("A", "[a,b,a,,,]");
		assertIndexedAccessExpressionType("A", "[a,b,a,,a,,b]");
	}

	@Test
	public void testElementTypeWithGeneric() {
		assertGenericIndexedAccessExpressionType("A", "A");
	}
}
