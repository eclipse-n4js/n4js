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
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
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
 * N4JS Spec Test: 6.1.4. Array Literal, Type Inference
 *
 * @see N6_1_07_IndexedAccessExpressionTypeInferenceTest indexed access test
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_04_ArrayLiteralTypesystemTest extends AbstractTypesystemTest {

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

	public void assertArrayLiteralType(String expectedType, String literal) {
		try {
			Script script = parseHelper.parse(scriptPrefix + literal + ";");
			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			Expression expr = ((ExpressionStatement) last(script.getScriptElements())).getExpression();
			assertTrue("Expected ArrayLiteral, was " + expr.eClass().getName(), expr instanceof ArrayLiteral);
			TypeRef typeExpr = checkedType(G, expr);
			assertEquals(expectedType, typeExpr.getTypeRefAsString());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testArrayUndefinedElementType() {
		assertArrayLiteralType("Array<any>", "[]");
		assertArrayLiteralType("Array<any>", "[,]");
		assertArrayLiteralType("Array<any>", "[,,,]");
	}

	@Test
	public void testArraySingleElementType() {
		// syntax error was intended (I guess)
		assertArrayLiteralType("Array<string>", "[\"Walter\"\"]");
		assertArrayLiteralType("Array<string>", "[\"Walter\", \"Werner\"]");
		assertArrayLiteralType("Array<int>", "[1]");
		assertArrayLiteralType("Array<int>", "[1,2,3]");
		assertArrayLiteralType("Array<number>", "[n1]");
		assertArrayLiteralType("Array<number>", "[n1,n2]");
		assertArrayLiteralType("Array<number>", "[n1,2,3]");
		assertArrayLiteralType("Array<string>", "[s1]");
		assertArrayLiteralType("Array<string>", "[s1,s2]");
		assertArrayLiteralType("Array<A>", "[a]");
		assertArrayLiteralType("Array<A>", "[a,a,a]");
	}

	@Test
	public void testArrayUnionElementType() {
		// syntax error was intended (I guess)
		assertArrayLiteralType("Array2<string,int>", "[\"Walter\", 1\"]");
		// syntax error was intended (I guess)
		assertArrayLiteralType("Array3<string,A,int>", "[\"Walter\", a, 1\"]");
		assertArrayLiteralType("Array2<A,B>", "[a,b]");
		assertArrayLiteralType("Array3<A,B,A>", "[a,b,a]");
		assertArrayLiteralType("Array7<A,B,A,B,A,A,B>", "[a,b,a,b,a,a,b]");
	}

	@Test
	public void testArrayElementTypeWithIgnoredPadding() {
		assertArrayLiteralType("Array<A>", "[a,]");
		assertArrayLiteralType("Array2<any,A>", "[,a]");
		assertArrayLiteralType("Array2<any,A>", "[,a,]"); // trailing comma => length is 2!
		assertArrayLiteralType("Array3<any,A,any>", "[,a,,,,]");

		// syntax error was intended (I guess)
		assertArrayLiteralType("Array3<any,string,int>", "[,\"Walter\", 1\"]");
		// syntax error was intended (I guess)
		assertArrayLiteralType("Array3<string,A,int>", "[\"Walter\", a, 1\",]");
		assertArrayLiteralType("Array3<A,B,any>", "[a,b,,]");
		assertArrayLiteralType("Array4<A,B,A,any>", "[a,b,a,,,]");
		assertArrayLiteralType("Array7<A,B,A,any,A,any,B>", "[a,b,a,,a,,b]");
	}
}
