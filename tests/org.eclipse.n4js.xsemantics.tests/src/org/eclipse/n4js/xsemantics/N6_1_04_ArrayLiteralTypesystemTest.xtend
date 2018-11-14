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
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions

/**
 * N4JS Spec Test: 6.1.4. Array Literal, Type Inference
 *
 * * @see N6_1_07_IndexedAccessExpressionTypeInferenceTest indexed access test
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_04_ArrayLiteralTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

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
	'''

	def void assertArrayLiteralType(String expectedType, String literal) {
		val script = (scriptPrefix + literal + ";").parse();
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val expr = (script.scriptElements.last as ExpressionStatement).expression;
		assertTrue("Expected ArrayLiteral, was " + expr.eClass.name, expr instanceof ArrayLiteral)
		val typeExpr = checkedType(G, expr)
		assertEquals(expectedType, typeExpr.typeRefAsString);
	}

	@Test
	def void testArrayUndefinedElementType() {
		assertArrayLiteralType("Array<any>", '''[]''');
		assertArrayLiteralType("Array<any>", '''[,]''');
		assertArrayLiteralType("Array<any>", '''[,,,]''');
	}

	@Test
	def void testArraySingleElementType() {
		assertArrayLiteralType("Array<string>", '''["Walter""]'''); // syntax error was intended (I guess)
		assertArrayLiteralType("Array<string>", '''["Walter", "Werner"]''');
		assertArrayLiteralType("Array<int>", '''[1]''');
		assertArrayLiteralType("Array<int>", '''[1,2,3]''');
		assertArrayLiteralType("Array<number>", '''[n1]''');
		assertArrayLiteralType("Array<number>", '''[n1,n2]''');
		assertArrayLiteralType("Array<union{int,number}>", '''[n1,2,3]''');
		assertArrayLiteralType("Array<string>", '''[s1]''');
		assertArrayLiteralType("Array<string>", '''[s1,s2]''');
		assertArrayLiteralType("Array<A>", '''[a]''');
		assertArrayLiteralType("Array<A>", '''[a,a,a]''');
	}

	@Test
	def void testArrayUnionElementType() {
		assertArrayLiteralType("Array<union{number,string}>", '''["Walter", 1"]'''); // syntax error was intended (I guess)
		assertArrayLiteralType("Array<union{A,number,string}>", '''["Walter", a, 1"]'''); // syntax error was intended (I guess)
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b]''');
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b,a]''');
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b,a,b,a,a,b]''');
	}

	@Test
	def void testArrayElementTypeWithIgnoredPadding() {
		assertArrayLiteralType("Array<A>", '''[a,]''');
		assertArrayLiteralType("Array<A>", '''[,a]''');
		assertArrayLiteralType("Array<A>", '''[,a,]''');
		assertArrayLiteralType("Array<A>", '''[,a,,,,]''');

		assertArrayLiteralType("Array<union{number,string}>", '''[,"Walter", 1"]'''); // syntax error was intended (I guess)
		assertArrayLiteralType("Array<union{A,number,string}>", '''["Walter", a, 1",]'''); // syntax error was intended (I guess)
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b,,]''');
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b,a,,,]''');
		assertArrayLiteralType("Array<union{A,B}>", '''[a,b,a,,a,,b]''');
	}
}
