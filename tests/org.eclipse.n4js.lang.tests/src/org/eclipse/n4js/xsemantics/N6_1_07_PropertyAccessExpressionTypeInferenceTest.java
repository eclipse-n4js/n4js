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
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for property access type inference; the test script used in this test is not necessarily valid (i.e., don't
 * call validation on that after parsing).
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_07_PropertyAccessExpressionTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testBaseTypeField() {
		assertTypeNameOfPropertyAccess("s: string;", "g.s", "string");
	}

	@Test
	public void testUnknownFieldType() {
		assertTypeNameOfPropertyAccess("", "g.doesNotExist", "«unknown»");
	}

	@Test
	public void testObjectTypeField() {
		assertTypeNameOfPropertyAccess("b: B;", "g.b", "B");
	}

	@Test
	public void testUnboundTypeVarField() {
		assertTypeNameOfPropertyAccess("t: T;", "g.t", "T");
	}

	@Test
	public void testBoundTypeVarField() {
		assertTypeNameOfPropertyAccess("t: T;", "ga.t", "A");
	}

	@Test
	public void testParameterizedFieldPropertyAccess() {
		// a TField does not have type variables
		assertTypeNameOfPropertyAccess("t: T;", "g.<A>t", "T");
	}

	@Test
	public void testMethodPropertyAccess() {
		assertTypeNameOfPropertyAccess("m(): B { return null }", "g.m", "{function():B}");
	}

	@Test
	public void testUnboundTypeVarMethodPropertyAccess() {
		assertTypeNameOfPropertyAccess("m(): T { return null }", "g.m", "{function():T}");
	}

	@Test
	public void testMethodPropertyAccessWithExpectedType() {
		assertTypeNameOfPropertyAccess("<FT> m(): FT { return null }", "var x: {function():B} = g.m",
				"{function<FT>():FT}");
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// assertTypeNameOfPropertyAccess("<FT> FT m() { return null }", "var {function():B} x = g.m",
		// "{function():B}");
	}

	@Test
	public void testMethodPropertyAccessWithExpectedType2() {
		assertTypeNameOfPropertyAccess("<FT> m(a: A): FT { return null }", "var x: {function():B} = g.m",
				"{function<FT>(A):FT}");
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// assertTypeNameOfPropertyAccess("<FT> FT m(A a) { return null }", "var {function():B} x = g.m",
		// "{function(A):B}");
	}

	@Test
	public void testMethodPropertyAccessWithExpectedType3() {
		assertTypeNameOfPropertyAccess("<FT,FU> m(p: FU): FT { return null }", "var x: {function():B} = g.m",
				"{function<FT,FU>(FU):FT}");
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// assertTypeNameOfPropertyAccess("<FT,FU> FT m(FU p) { return null }", "var {function():B} x = g.m",
		// "{function<FU>(FU):B}");
	}

	@Test
	public void testMethodPropertyAccessWithExpectedType4() {
		assertTypeNameOfPropertyAccess("<FT,FU> m(p: FU): FT { return null }", "var x: {function(A):B} = g.m",
				"{function<FT,FU>(FU):FT}");
		// wrong expectation (excessive type argument inference in PropertyAccessExpression, cf. IDEBUG-133/166):
		// assertTypeNameOfPropertyAccess("<FT,FU> FT m(FU p) { return null }", "var {function(A):B} x = g.m",
		// "{function(A):B}");
	}

	@Test
	public void testParameterizedMethodPropertyAccess() {
		// T is a class parameter thus it is not considered in the parameterized access
		assertTypeNameOfPropertyAccess("m(): T { return null }", "g.<A>m", "{function():T}");
	}

	@Test
	public void testParameterizedMethodPropertyAccess2() {
		// T and U are class parameters thus they are not considered in the parameterized access
		assertTypeNameOfPropertyAccess("m(p: U): T { return null }", "g.<A,B>m", "{function(U):T}");
	}

	@Test
	public void testParameterizedMethodPropertyAccess3() {
		assertTypeNameOfPropertyAccess("<FT,FU> m(p: FU): FT { return null }", "g.<A,B>m", "{function(B):A}");
	}

	@Test
	public void testParameterizedMethodPropertyAccess4() {
		// type args respect the order of type vars
		assertTypeNameOfPropertyAccess("<FU,FT> m(p: FU): FT { return null }", "g.<A,B>m", "{function(A):B}");
	}

	@Test
	public void testParameterizedMethodPropertyAccessMixed() {
		assertTypeNameOfPropertyAccess("<FT,FU> m(c: C, p: FU): FT { return null }", "g.<A,B>m", "{function(C,B):A}");
	}

	// private void assertTypeNameOfInnerFunctionCall(String callExpression, String expectedTypeName) {
	// assertTypeName(expectedTypeName, callExpression.callExpressionToType.callExpressions.last)
	// }

	/**
	 * Asserts that the inferred type of the ParameterizedPropertyAccessExpression equals the expected type name.
	 * Usually, the property access expression contains some reference to the declared member. This declared member is a
	 * member of a generic class {@code G<T>}.
	 */
	private void assertTypeNameOfPropertyAccess(String memberDeclaration, String ParameterizedPropertyAccessExpression,
			String expectedTypeName) {

		assertTypeName(expectedTypeName,
				propertyAccessExpressionToType(memberDeclaration, ParameterizedPropertyAccessExpression));
	}

	private ParameterizedPropertyAccessExpression propertyAccessExpressionToType(String memberDeclaration,
			String ParameterizedPropertyAccessExpression) {
		ScriptElement lastElement = last(
				testScript(memberDeclaration, ParameterizedPropertyAccessExpression).getScriptElements());

		EObject lastExpression = null;
		if (lastElement instanceof ExpressionStatement) {
			lastExpression = ((ExpressionStatement) lastElement).getExpression();
		} else if (lastElement instanceof VariableStatement) {
			lastExpression = ((VariableStatement) lastElement).getVarDecl().get(0);
		}

		if (lastExpression instanceof AssignmentExpression) {
			return (ParameterizedPropertyAccessExpression) ((AssignmentExpression) lastExpression).getRhs();
		}
		if (lastExpression instanceof VariableDeclaration) {
			return (ParameterizedPropertyAccessExpression) ((VariableDeclaration) lastExpression).getExpression();
		}
		return (ParameterizedPropertyAccessExpression) lastExpression;
	}

	private Script testScript(String property, String ParameterizedPropertyAccessExpression) {
		try {
			return parseHelper.parse("""
					class A {}
					class B {}
					class C {}
					class G<T,U> {
						%s
					}


					var g: G;
					var ga: G<A>;

					%s;

					""".formatted(property, ParameterizedPropertyAccessExpression));
		} catch (Exception e) {
			Assert.fail();
			return null;
		}
	}
}
