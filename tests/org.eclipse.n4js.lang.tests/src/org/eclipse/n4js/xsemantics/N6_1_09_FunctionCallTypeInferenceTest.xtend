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
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for function call type inference; the test script used in this test
 * is not necessarily valid (i.e., don't call validation on that after parsing).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_09_FunctionCallTypeInferenceTest extends AbstractTypesystemTest {

	@Test
	def void testFunctionCall() {
		"a.foo()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testUnknownTargetFunctionCall() {
		"a.unknown()".assertTypeNameOfFunctionCall("[unknown]")
	}

	@Test
	def void testFunctionCallOnNonFunctionType() {
		"''()".assertTypeNameOfFunctionCall("[unknown]")
	}

	@Test
	def void testFunctionCallWithParametrizedReturnTypeAndNoHint() {
		"a.fWithParametrizedReturnType()".assertTypeNameOfFunctionCall("any")
	}

	@Test
	def void testFunctionCallWithParametrizedParamType() {
		"a.fWithParametrizedParameter('f')".assertTypeNameOfFunctionCall("string")
		"a.fWithParametrizedParameter(b)".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithTwoParametrizedParameters() {
		"a.fWithTwoParametrizedParameters1('f', b)".assertTypeNameOfFunctionCall("string")
		"a.fWithTwoParametrizedParameters2('f', b)".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithExpectedReturnTypeFromVarDecl() {
		"var x: B = a.fWithParametrizedReturnType()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithExpectedReturnTypeFromAssignment() {
		// b is declared as var b: B;
		"b = a.fWithParametrizedReturnType()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithExpectedReturnTypeFromArgInFunctionCall() {
		// bar expects a B;
		// thus the type of the inner call expression uses this hint
		"a.bar(a.fWithParametrizedReturnType())".assertTypeNameOfInnerFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithAlreadyBoundReturnTypeVar() {
		"ga.get()".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallWithAlreadyBoundReturnTypeVar2() {
		// type expectation A is not used, since T is already bound to B
		"var a: A = gb.get()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithAlreadyBoundParamTypeVar() {
		// type expectation A is not used, since T is already bound to B
		"var x: A = gb.get2(a)".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParam() {
		// FT is bound using the type of the argument ga (: G<A>) that is A
		"a.fWithGenericInParam(ga)".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParam2() {
		// not union{A,B}, because inference from argument types has priority over inference from expected return type
		"var x: B = a.fWithGenericInParam(ga)".assertTypeNameOfFunctionCall("FT")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParam3() {
		// FT is bound using the type of the argument gga (: G<G<A>>) that is G<A>
		"a.fWithGenericInParam(gga)".assertTypeNameOfFunctionCall("G<A>")
	}

	@Test
	def void testFunctionCallWithParameterizedUnknownGenericParam() {
		"a.fWithGenericInReturn(unknown)".assertTypeNameOfFunctionCall("G<[unknown]>")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParamAndReturn() {
		// <FT> H<FT> fWithGenericInParamAndReturn(G<FT> p)
		"a.fWithGenericInParamAndReturn(ga)".assertTypeNameOfFunctionCall("H<A>")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParamAndReturn2() {
		// <FT> H<FT> fWithGenericInParamAndReturn(G<FT> p)
		// in H<T> { T get() }
		"a.fWithGenericInParamAndReturn(ga).get()".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallWithParametrizeGenericParamNested() {
		// FT is bound using the type of the argument of the argument of gga (: G<G<A>>) that is A
		"a.fWithGenericInParamNested(gga)".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallWithGenericAndUpperBound() {
		// class B{}
		// class A{
		//     <FT extends A> FT fWithGenericAndUpperBound() { return null; }
		// }
		// var a: A;
		// var b: B;
		"var x: B = a.fWithGenericAndUpperBound()".assertTypeNameOfFunctionCall("intersection{A,B}")
	}

	@Test
	def void testFunctionCallWithGenericAndUpperBoundAndNoHint() {
		// class A{
		//     <FT extends A> FT fWithGenericAndUpperBound() { return null; }
		// }
		// var a: A;
		"a.fWithGenericAndUpperBound()".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallWithGenericAndUpperBoundAndNoHint2() {
		// class A{
		//     <FT extends A> G<FT> fWithGenericAndUpperBound2() { return null; }
		// }
		// var a: A;
		"a.fWithGenericAndUpperBound2()".assertTypeNameOfFunctionCall("G<A>")
	}

	@Test
	def void testFunctionCallItselfInner() {
		// check for no loop
		// <T> T fWithParametrizedReturnType() cannot make any guess on T
		"a.fWithParametrizedParameter(a.fWithParametrizedParameter())".assertTypeNameOfInnerFunctionCall("any")
	}

	@Test
	def void testFunctionCallItself() {
		// check for no loop
		// <T> T fWithParametrizedReturnType() cannot make any guess on T
		"a.fWithParametrizedParameter(a.fWithParametrizedParameter())".assertTypeNameOfFunctionCall("any")
	}

	@Test
	def void testMethodCallParameterized() {
		"a.<A>fWithParametrizedReturnType()".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testMethodCallParameterized2() {
		"a.<A,B>fWithTwoParametrizedParameters1()".assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testMethodCallParameterized3() {
		// <T,U> U fWithTwoParametrizedParameters2(T p)
		// thus A refers to T and B refers to U
		"a.<A,B>fWithTwoParametrizedParameters2()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testMethodCallParameterized4() {
		"a.<B,A>fWithTwoParametrizedParameters1()".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallParameterized() {
		'''
		function <T> func(): T { return null;}
		var inferred =<A> func()'''.assertTypeNameOfFunctionCall("A")
	}

	@Test
	def void testFunctionCallParameterized2() {
		'''
		function <T,U> func(p: T): U { return null;}
		var inferred =<A,B> func()'''.assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithParametrizedParamTypeUnion() {
		// <T> T fWithTwoParametrizedParameter(T p, T p2) { return null; }
		"a.fWithTwoParametrizedParameter('f', 1)".assertTypeNameOfFunctionCall("union{int,string}")
	}

	@Test
	def void testFunctionCallWithParametrizedParamTypeUnion2() {
		// <T> T fWithTwoParametrizedParameter(T p, T p2) { return null; }
		"a.fWithTwoParametrizedParameter(new B1(), new B2())".assertTypeNameOfFunctionCall("union{B1,B2}")
	}

	@Test
	def void testFunctionCallWithParametrizedParamTypeUnion3() {
		// <FT> FT fWithGenericInParam(G<FT> p) { return null; }
		// not union{B1,B2}, because inference from argument types has priority over inference from expected return type
		"var x: B1 = a.fWithGenericInParam(gb2)".assertTypeNameOfFunctionCall("FT")
	}

	@Test
	def void testFunctionCallWithArgExtendingParameterizedClass() {
		// class AGB extends G<B> {}
		// var agb: AGB;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to B
		"a.fWithGenericInParam(agb)".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithArgExtendingParameterizedClass2() {
		// class AGB extends G<B> {}
		// class AGB2 extends AGB {}
		// var agb2: AGB2;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to B
		"a.fWithGenericInParam(agb2)".assertTypeNameOfFunctionCall("B")
	}

	@Test
	def void testFunctionCallWithArgExtendingParameterizedClassUnion() {
		// interface I1 extends G<B> {}
		// interface I2 extends G<A> {}
		// class AGCI1I2 extends G<C> implements I1, I2 {}
		// var agci1i2: AGCI1I2;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to the intersection of A,B,C
		"a.fWithGenericInParam(agci1i2)".assertTypeNameOfFunctionCall(
			// old expectation:
			// "intersection{A,B,C}"
			// TODO IDE-2226 revisit test case
			"[unknown]"
		)
	}

	def private void assertTypeNameOfInnerFunctionCall(String callExpression, String expectedTypeName) {
		assertTypeName(expectedTypeName, callExpression.callExpressionToType.callExpressions.last)
	}

	def private void assertTypeNameOfFunctionCall(CharSequence callExpression, String expectedTypeName) {
		assertTypeName(expectedTypeName, callExpression.callExpressionToType)
	}

	def private ParameterizedCallExpression callExpressionToType(CharSequence callExpression) {
		val lastElement = callExpression.testScript.scriptElements.last

		val lastExpression =
		switch (lastElement) {
			ExpressionStatement: lastElement.expression
			VariableStatement: lastElement.varDecl.head
		}

		switch (lastExpression) {
			AssignmentExpression : lastExpression.rhs
			VariableDeclaration : lastExpression.expression
			default: lastExpression
		} as ParameterizedCallExpression
	}

	def private testScript(CharSequence ParameterizedCallExpression) {
		createScript(JavaScriptVariant.n4js,
		'''
			class Base {}
			class B1 extends Base {}
			class B2 extends Base {}

			class C{}
			class B{}
			class A{
				foo(): B { return null;}
				bar(b: B b): void { }

				<T> fWithParametrizedReturnType(): T { return null; }
				<T> fWithParametrizedParameter(p: T): T { return null; }

				<T> fWithTwoParametrizedParameter(p: T, p2: T): T { return null; }

				<T,U> fWithTwoParametrizedParameters1(p1: T, p2: U): T { return null; }
				<T,U> fWithTwoParametrizedParameters2(p1: T, p2: U): U { return null; }

				<FT> fWithGenericInParam(p: G<FT>): FT { return null; }
				<FT> fWithGenericInReturn(p: FT): G<FT> { return null; }

				<FT> fWithGenericInParamAndReturn(p: G<FT>): H<FT> { return null; }

				<FT> fWithGenericInParamNested(p: G<G<FT>>): FT { return null; }

				<FT extends A> fWithGenericAndUpperBound(): FT { return null; }
				<FT extends A> fWithGenericAndUpperBound2(): G<FT> { return null; }
			}

			class G<T>{
				t: T;
				b: B;

				get(): T { return null;}
				get2(p: T): T { return null;}
				set(t: T): void { }
				getB(): B { return null; }
			}
			class H<T> {
				get(): T { return null;}
			}

			interface I1 extends G<B> {}
			interface I2 extends G<A> {}
			class AGCI1I2 extends G<C> implements I1, I2 {}

			class AGB extends G<B> {}
			class AGB2 extends AGB {}

			var a: A;
			var b: B;

			var ga: G<A>;
			var gb: G<B>;

			var gga: G<G<A>>;

			var gb2: G<B2>

			var agb: AGB;
			var agb2: AGB2;
			var agci1i2: AGCI1I2;

			«ParameterizedCallExpression»;

			''')
	}
}
