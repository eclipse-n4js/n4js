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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression

/*
 * Tests for property access type inference; the test script used in this test
 * is not necessarily valid (i.e., don't call validation on that after parsing).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_07_PropertyAccessExpressionTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Test
	def void testBaseTypeField() {
		"s: string;".assertTypeNameOfPropertyAccess("g.s", "string")
	}

	@Test
	def void testUnknownFieldType() {
		"".assertTypeNameOfPropertyAccess("g.doesNotExist", "[unknown]")
	}

	@Test
	def void testObjectTypeField() {
		"b: B;".assertTypeNameOfPropertyAccess("g.b", "B")
	}

	@Test
	def void testUnboundTypeVarField() {
		"t: T;".assertTypeNameOfPropertyAccess("g.t", "T")
	}

	@Test
	def void testBoundTypeVarField() {
		"t: T;".assertTypeNameOfPropertyAccess("ga.t", "A")
	}

	@Test
	def void testParameterizedFieldPropertyAccess() {
		// a TField does not have type variables
		"t: T;".assertTypeNameOfPropertyAccess("g.<A>t", "T")
	}

	@Test
	def void testMethodPropertyAccess() {
		"m(): B { return null }".assertTypeNameOfPropertyAccess("g.m", "{function():B}")
	}

	@Test
	def void testUnboundTypeVarMethodPropertyAccess() {
		"m(): T { return null }".assertTypeNameOfPropertyAccess("g.m", "{function():T}")
	}

	@Test
	def void testMethodPropertyAccessWithExpectedType() {
		"<FT> m(): FT { return null }".assertTypeNameOfPropertyAccess("var x: {function():B} = g.m", "{function<FT>():FT}")
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// "<FT> FT m() { return null }".assertTypeNameOfPropertyAccess("var {function():B} x = g.m", "{function():B}")
	}

	@Test
	def void testMethodPropertyAccessWithExpectedType2() {
		"<FT> m(a: A): FT { return null }".assertTypeNameOfPropertyAccess("var x: {function():B} = g.m", "{function<FT>(A):FT}")
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// "<FT> FT m(A a) { return null }".assertTypeNameOfPropertyAccess("var {function():B} x = g.m", "{function(A):B}")
	}

	@Test
	def void testMethodPropertyAccessWithExpectedType3() {
		"<FT,FU> m(p: FU): FT { return null }".assertTypeNameOfPropertyAccess("var x: {function():B} = g.m", "{function<FT,FU>(FU):FT}")
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// "<FT,FU> FT m(FU p) { return null }".assertTypeNameOfPropertyAccess("var {function():B} x = g.m", "{function<FU>(FU):B}")
	}

	@Test
	def void testMethodPropertyAccessWithExpectedType4() {
		"<FT,FU> m(p: FU): FT { return null }".assertTypeNameOfPropertyAccess("var x: {function(A):B} = g.m", "{function<FT,FU>(FU):FT}")
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// "<FT,FU> FT m(FU p) { return null }".assertTypeNameOfPropertyAccess("var {function(A):B} x = g.m", "{function(A):B}")
	}

	@Test
	def void testParameterizedMethodPropertyAccess() {
		// T is a class parameter thus it is not considered in the parameterized access
		"m(): T { return null }".assertTypeNameOfPropertyAccess("g.<A>m", "{function():T}")
	}

	@Test
	def void testParameterizedMethodPropertyAccess2() {
		// T and U are class parameters thus they are not considered in the parameterized access
		"m(p: U): T { return null }".assertTypeNameOfPropertyAccess("g.<A,B>m", "{function(U):T}")
	}

	@Test
	def void testParameterizedMethodPropertyAccess3() {
		"<FT,FU> m(p: FU): FT { return null }".assertTypeNameOfPropertyAccess("g.<A,B>m", "{function(B):A}")
	}

	@Test
	def void testParameterizedMethodPropertyAccess4() {
		// type args respect the order of type vars
		"<FU,FT> m(p: FU): FT { return null }".assertTypeNameOfPropertyAccess("g.<A,B>m", "{function(A):B}")
	}

	@Test
	def void testParameterizedMethodPropertyAccessMixed() {
		"<FT,FU> m(c: C, p: FU): FT { return null }".assertTypeNameOfPropertyAccess("g.<A,B>m", "{function(C,B):A}")
	}

//	def private void assertTypeNameOfInnerFunctionCall(String callExpression, String expectedTypeName) {
//		assertTypeName(expectedTypeName, callExpression.callExpressionToType.callExpressions.last)
//	}

	/**
	 * Asserts that the inferred type of the ParameterizedPropertyAccessExpression equals the expected type name. Usually, the property access
	 * expression contains some reference to the declared member. This declared member is a member of a generic class {@code G<T>}.
	 */
	def private void assertTypeNameOfPropertyAccess(String memberDeclaration, String ParameterizedPropertyAccessExpression, String expectedTypeName) {
		assertTypeName(expectedTypeName, memberDeclaration.propertyAccessExpressionToType(ParameterizedPropertyAccessExpression))
	}

	def private propertyAccessExpressionToType(String memberDeclaration, String ParameterizedPropertyAccessExpression) {
		val lastElement = memberDeclaration.testScript(ParameterizedPropertyAccessExpression).scriptElements.last

		val lastExpression =
		switch (lastElement) {
			ExpressionStatement: lastElement.expression
			VariableStatement: lastElement.varDecl.head
		}

		switch (lastExpression) {
			AssignmentExpression : lastExpression.rhs
			VariableDeclaration : lastExpression.expression
			default: lastExpression
		} as ParameterizedPropertyAccessExpression
	}

	def private testScript(String property, String ParameterizedPropertyAccessExpression) {
		'''
			class A {}
			class B {}
			class C {}
			class G<T,U> {
				«property»
			}


			var g: G;
			var ga: G<A>;

			«ParameterizedPropertyAccessExpression»;

			'''.parse()
	}
}
