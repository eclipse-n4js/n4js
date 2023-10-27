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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for function call type inference; the test script used in this test is not necessarily valid (i.e., don't call
 * validation on that after parsing).
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_09_FunctionCallTypeInferenceTest extends AbstractTypesystemTest {

	@Test
	public void testFunctionCall() {
		assertTypeNameOfFunctionCall("a.foo()", "B");
	}

	@Test
	public void testUnknownTargetFunctionCall() {
		assertTypeNameOfFunctionCall("a.unknown()", "«unknown»");
	}

	@Test
	public void testFunctionCallOnNonFunctionType() {
		assertTypeNameOfFunctionCall("''()", "«unknown»");
	}

	@Test
	public void testFunctionCallWithParametrizedReturnTypeAndNoHint() {
		assertTypeNameOfFunctionCall("a.fWithParametrizedReturnType()", "any");
	}

	@Test
	public void testFunctionCallWithParametrizedParamType() {
		assertTypeNameOfFunctionCall("a.fWithParametrizedParameter('f')", "string");
		assertTypeNameOfFunctionCall("a.fWithParametrizedParameter(someString)", "string");
		assertTypeNameOfFunctionCall("a.fWithParametrizedParameter(b)", "B");
	}

	@Test
	public void testFunctionCallWithTwoParametrizedParameters() {
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameters1('f', b)", "string");
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameters1(someString, b)", "string");
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameters2('f', b)", "B");
	}

	@Test
	public void testFunctionCallWithExpectedReturnTypeFromVarDecl() {
		assertTypeNameOfFunctionCall("var x: B = a.fWithParametrizedReturnType()", "B");
	}

	@Test
	public void testFunctionCallWithExpectedReturnTypeFromAssignment() {
		// b is declared as var b: B;
		assertTypeNameOfFunctionCall("b = a.fWithParametrizedReturnType()", "B");
	}

	@Test
	public void testFunctionCallWithExpectedReturnTypeFromArgInFunctionCall() {
		// bar expects a B;
		// thus the type of the inner call expression uses this hint
		assertTypeNameOfInnerFunctionCall("a.bar(a.fWithParametrizedReturnType())", "B");
	}

	@Test
	public void testFunctionCallWithAlreadyBoundReturnTypeVar() {
		assertTypeNameOfFunctionCall("ga.get()", "A");
	}

	@Test
	public void testFunctionCallWithAlreadyBoundReturnTypeVar2() {
		// type expectation A is not used, since T is already bound to B
		assertTypeNameOfFunctionCall("var a: A = gb.get()", "B");
	}

	@Test
	public void testFunctionCallWithAlreadyBoundParamTypeVar() {
		// type expectation A is not used, since T is already bound to B
		assertTypeNameOfFunctionCall("var x: A = gb.get2(a)", "B");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParam() {
		// FT is bound using the type of the argument ga (: G<A>) that is A
		assertTypeNameOfFunctionCall("a.fWithGenericInParam(ga)", "A");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParam2() {
		// not union{A,B}, because inference from argument types has priority over inference from expected return type
		assertTypeNameOfFunctionCall("var x: B = a.fWithGenericInParam(ga)", "FT");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParam3() {
		// FT is bound using the type of the argument gga (: G<G<A>>) that is G<A>
		assertTypeNameOfFunctionCall("a.fWithGenericInParam(gga)", "G<A>");
	}

	@Test
	public void testFunctionCallWithParameterizedUnknownGenericParam() {
		assertTypeNameOfFunctionCall("a.fWithGenericInReturn(unknown)", "G<«unknown»>");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParamAndReturn() {
		// <FT> H<FT> fWithGenericInParamAndReturn(G<FT> p)
		assertTypeNameOfFunctionCall("a.fWithGenericInParamAndReturn(ga)", "H<A>");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParamAndReturn2() {
		// <FT> H<FT> fWithGenericInParamAndReturn(G<FT> p)
		// in H<T> { T get() }
		assertTypeNameOfFunctionCall("a.fWithGenericInParamAndReturn(ga).get()", "A");
	}

	@Test
	public void testFunctionCallWithParametrizeGenericParamNested() {
		// FT is bound using the type of the argument of the argument of gga (: G<G<A>>) that is A
		assertTypeNameOfFunctionCall("a.fWithGenericInParamNested(gga)", "A");
	}

	@Test
	public void testFunctionCallWithGenericAndUpperBound() {
		// class B{}
		// class A{
		// <FT extends A> FT fWithGenericAndUpperBound() { return null; }
		// }
		// var a: A;
		// var b: B;
		assertTypeNameOfFunctionCall("var x: B = a.fWithGenericAndUpperBound()", "intersection{B,A}");
	}

	@Test
	public void testFunctionCallWithGenericAndUpperBoundAndNoHint() {
		// class A{
		// <FT extends A> FT fWithGenericAndUpperBound() { return null; }
		// }
		// var a: A;
		assertTypeNameOfFunctionCall("a.fWithGenericAndUpperBound()", "A");
	}

	@Test
	public void testFunctionCallWithGenericAndUpperBoundAndNoHint2() {
		// class A{
		// <FT extends A> G<FT> fWithGenericAndUpperBound2() { return null; }
		// }
		// var a: A;
		assertTypeNameOfFunctionCall("a.fWithGenericAndUpperBound2()", "G<A>");
	}

	@Test
	public void testFunctionCallItselfInner() {
		// check for no loop
		// <T> T fWithParametrizedReturnType() cannot make any guess on T
		assertTypeNameOfInnerFunctionCall("a.fWithParametrizedParameter(a.fWithParametrizedParameter())", "any");
	}

	@Test
	public void testFunctionCallItself() {
		// check for no loop
		// <T> T fWithParametrizedReturnType() cannot make any guess on T
		assertTypeNameOfFunctionCall("a.fWithParametrizedParameter(a.fWithParametrizedParameter())", "any");
	}

	@Test
	public void testMethodCallParameterized() {
		assertTypeNameOfFunctionCall("a.<A>fWithParametrizedReturnType()", "A");
	}

	@Test
	public void testMethodCallParameterized2() {
		assertTypeNameOfFunctionCall("a.<A,B>fWithTwoParametrizedParameters1()", "A");
	}

	@Test
	public void testMethodCallParameterized3() {
		// <T,U> U fWithTwoParametrizedParameters2(T p)
		// thus A refers to T and B refers to U
		assertTypeNameOfFunctionCall("a.<A,B>fWithTwoParametrizedParameters2()", "B");
	}

	@Test
	public void testMethodCallParameterized4() {
		assertTypeNameOfFunctionCall("a.<B,A>fWithTwoParametrizedParameters1()", "B");
	}

	@Test
	public void testFunctionCallParameterized() {
		assertTypeNameOfFunctionCall("""
				function <T> func(): T { return null;}
				var inferred =<A> func()
				""", "A");
	}

	@Test
	public void testFunctionCallParameterized2() {
		assertTypeNameOfFunctionCall("""
				function <T,U> func(p: T): U { return null;}
				var inferred =<A,B> func()
				""", "B");
	}

	@Test
	public void testFunctionCallWithParametrizedParamTypeUnion() {
		// <T> T fWithTwoParametrizedParameter(T p, T p2) { return null; }
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameter('f', 1)", "union{string,int}");
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameter(someString, someInt)", "union{string,int}");
	}

	@Test
	public void testFunctionCallWithParametrizedParamTypeUnion2() {
		// <T> T fWithTwoParametrizedParameter(T p, T p2) { return null; }
		assertTypeNameOfFunctionCall("a.fWithTwoParametrizedParameter(new B1(), new B2())", "union{B1,B2}");
	}

	@Test
	public void testFunctionCallWithParametrizedParamTypeUnion3() {
		// <FT> FT fWithGenericInParam(G<FT> p) { return null; }
		// not union{B1,B2}, because inference from argument types has priority over inference from expected return type
		assertTypeNameOfFunctionCall("var x: B1 = a.fWithGenericInParam(gb2)", "FT");
	}

	@Test
	public void testFunctionCallWithArgExtendingParameterizedClass() {
		// class AGB extends G<B> {}
		// var agb: AGB;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to B
		assertTypeNameOfFunctionCall("a.fWithGenericInParam(agb)", "B");
	}

	@Test
	public void testFunctionCallWithArgExtendingParameterizedClass2() {
		// class AGB extends G<B> {}
		// class AGB2 extends AGB {}
		// var agb2: AGB2;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to B
		assertTypeNameOfFunctionCall("a.fWithGenericInParam(agb2)", "B");
	}

	@Test
	public void testFunctionCallWithArgExtendingParameterizedClassUnion() {
		// interface I1 extends G<B> {}
		// interface I2 extends G<A> {}
		// class AGCI1I2 extends G<C> implements I1, I2 {}
		// var agci1i2: AGCI1I2;
		// <FT> FT fWithGenericInParam(G<FT> p)
		// FT is bound to the intersection of A,B,C
		assertTypeNameOfFunctionCall("a.fWithGenericInParam(agci1i2)",
				// old expectation:
				// "intersection{A,B,C}"
				// TODO IDE-2226 revisit test case
				"«unknown»");
	}

	private void assertTypeNameOfInnerFunctionCall(String callExpression, String expectedTypeName) {
		assertTypeName(expectedTypeName, last(callExpressions(callExpressionToType(callExpression))));
	}

	private void assertTypeNameOfFunctionCall(CharSequence callExpression, String expectedTypeName) {
		assertTypeName(expectedTypeName, callExpressionToType(callExpression));
	}

	private ParameterizedCallExpression callExpressionToType(CharSequence callExpression) {
		ScriptElement lastElement = last(testScript(callExpression).getScriptElements());

		EObject lastExpression = null;
		if (lastElement instanceof ExpressionStatement) {
			lastExpression = ((ExpressionStatement) lastElement).getExpression();
		} else if (lastElement instanceof VariableStatement) {
			lastExpression = ((VariableStatement) lastElement).getVarDecl().get(0);
		}

		if (lastExpression instanceof AssignmentExpression) {
			return (ParameterizedCallExpression) ((AssignmentExpression) lastExpression).getRhs();
		}
		if (lastExpression instanceof VariableDeclaration) {
			return (ParameterizedCallExpression) ((VariableDeclaration) lastExpression).getExpression();
		}
		return (ParameterizedCallExpression) lastExpression;
	}

	private Script testScript(CharSequence ParameterizedCallExpression) {
		return createScript(JavaScriptVariant.n4js,
				"""
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

						var someInt: int;
						var someString: string;

						%s;

						""".formatted(ParameterizedCallExpression));
	}
}
