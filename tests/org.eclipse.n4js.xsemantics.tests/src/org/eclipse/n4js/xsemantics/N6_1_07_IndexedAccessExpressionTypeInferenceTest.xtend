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
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.n4JS.IndexedAccessExpression
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions

/*
 * Tests for indexed access type inference.
 *
 * @see N6_1_04_ArrayLiteralTypesystemTest array literal type inference test
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_07_IndexedAccessExpressionTypeInferenceTest extends AbstractTypesystemTest {

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

	def void assertIndexedAccessExpressionType(String expectedElementType, String arrayliteral) {
		val script = ('''
			«scriptPrefix»
			var testArray = «arrayliteral»;
			testArray[0];
			''').parse();
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val expr = (script.scriptElements.last as ExpressionStatement).expression;
		assertTrue("Expected IndexedAccessExpression, was " + expr.eClass.name, expr instanceof IndexedAccessExpression)
		val typeExpr = checkedType(G, expr)
		assertEquals(expectedElementType, typeExpr.typeRefAsString);
	}

	def void assertGenericIndexedAccessExpressionType(String expectedElementType, String arrayType) {
		val script = ('''
			«scriptPrefix»
			var testArray: Array<«arrayType»> = null;
			testArray[0];
			''').parse();
		assertNotNull(script);
		val expr = (script.scriptElements.last as ExpressionStatement).expression;
		assertTypeName(expectedElementType, expr)
	}

	@Test
	def void testUndefinedElementType() {
		assertIndexedAccessExpressionType("any", '''[]''');
		assertIndexedAccessExpressionType("any", '''[,]''');
		assertIndexedAccessExpressionType("any", '''[,,,]''');
	}

	@Test
	def void testSingleElementType() {
		assertIndexedAccessExpressionType("string", '''["Walter"]''');
		assertIndexedAccessExpressionType("string", '''["Walter""]'''); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("string", '''["Walter", "Werner"]''');
		assertIndexedAccessExpressionType("int", '''[1]''');
		assertIndexedAccessExpressionType("int", '''[1,2,3]''');
		assertIndexedAccessExpressionType("number", '''[n1]''');
		assertIndexedAccessExpressionType("number", '''[n1,n2]''');
		assertIndexedAccessExpressionType("union{int,number}", '''[n1,2,3]''');
		assertIndexedAccessExpressionType("string", '''[s1]''');
		assertIndexedAccessExpressionType("string", '''[s1,s2]''');
		assertIndexedAccessExpressionType("A", '''[a]''');
		assertIndexedAccessExpressionType("A", '''[a,a,a]''');
	}

	@Test
	def void testUnionElementType() {
		assertIndexedAccessExpressionType("union{int,string}", '''["Walter", 1]''');
		assertIndexedAccessExpressionType("union{number,string}", '''["Walter", 1"]'''); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("union{A,int,string}", '''["Walter", a, 1]''');
		assertIndexedAccessExpressionType("union{A,number,string}", '''["Walter", a, 1"]'''); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b]''');
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b,a]''');
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b,a,b,a,a,b]''');
	}

	@Test
	def void testElementTypeWithIgnoredPadding() {
		assertIndexedAccessExpressionType("A", '''[a,]''');
		assertIndexedAccessExpressionType("A", '''[,a]''');
		assertIndexedAccessExpressionType("A", '''[,a,]''');
		assertIndexedAccessExpressionType("A", '''[,a,,,,]''');

		assertIndexedAccessExpressionType("union{int,string}", '''[,"Walter", 1]''');
		assertIndexedAccessExpressionType("union{number,string}", '''[,"Walter", 1"]'''); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("union{A,int,string}", '''["Walter", a, 1,]''');
		assertIndexedAccessExpressionType("union{A,number,string}", '''["Walter", a, 1",]'''); // syntax error was intended (I guess)
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b,,]''');
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b,a,,,]''');
		assertIndexedAccessExpressionType("union{A,B}", '''[a,b,a,,a,,b]''');
	}

	@Test
	def void testElementTypeWithGeneric() {
		assertGenericIndexedAccessExpressionType("A", "A")
	}
}
