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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayNType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Predicates;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class PolyProcessorTest extends AbstractTypesystemTest {

	private static String preamble = """

				class A {}
				class B extends A {}
				class B1 extends A {}
				class B2 extends A {}
				class C extends B {}

				class X {}

				class G<T> {}
				class G2<S,T> {}

				var a: A;
				var b: B;
				var b1: B1;
				var b2: B2;
				var c: C;

				var ga: G<A>;
				var gb: G<B>;
				var gc: G<C>;

			""";

	RuleEnvironment _G;

	Type A;
	Type B;
	Type B1;
	Type B2;
	Type C;
	Type G;
	Type G2;

	private Script parseAndValidate(CharSequence code) {
		Script script = createAndValidateScript(JavaScriptVariant.n4js, (preamble + code).toString());

		_G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		N4JSResource.postProcessContainingN4JSResourceOf(script);

		A = findFirst(script.getModule().getTypes(), t -> "A".equals(t.getName()));
		B = findFirst(script.getModule().getTypes(), t -> "B".equals(t.getName()));
		B1 = findFirst(script.getModule().getTypes(), t -> "B1".equals(t.getName()));
		B2 = findFirst(script.getModule().getTypes(), t -> "B2".equals(t.getName()));
		C = findFirst(script.getModule().getTypes(), t -> "C".equals(t.getName()));

		G = findFirst(script.getModule().getTypes(), t -> "G".equals(t.getName()));
		G2 = findFirst(script.getModule().getTypes(), t -> "G2".equals(t.getName()));

		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(B1);
		assertNotNull(B2);
		assertNotNull(C);

		assertNotNull(G);
		assertNotNull(G2);

		return script;
	}

	private ArrayLiteral firstArrayLiteral(Script script) {
		return first(script, ArrayLiteral.class, Predicates.alwaysTrue());
	}

	// private ObjectLiteral firstObjectLiteral(Script script) {
	// script.first(ObjectLiteral,[true]);
	// }
	private ParameterizedCallExpression firstCallExpression(Script script) {
		return first(script, ParameterizedCallExpression.class, Predicates.alwaysTrue());
	}

	private FunctionExpression firstFunctionExpression(Script script) {
		return first(script, FunctionExpression.class, Predicates.alwaysTrue());
	}

	// private FormalParameter firstFpar(Script script, String name) {
	// script.first(FormalParameter,[it.name==name])
	// }
	private <T extends EObject> T first(Script script, Class<T> nodeType, Predicate<T> pred) {
		T result = head(filter(filter(script.eAllContents(), nodeType), eo -> pred.test(eo)));
		assertNotNull("no expression of type " + nodeType.getName() + " in script", result);
		return result;
	}

	private Script assertType(Expression expression, TypeRef expectedTypeRef) {
		TypeRef actualTypeRef = getTypeFromTypingCache(expression);

		assertTrue(
				"types not equal; expected: " + (expectedTypeRef == null ? "" : expectedTypeRef.getTypeRefAsString())
						+ ", actual: " + (actualTypeRef == null ? "" : actualTypeRef.getTypeRefAsString()),
				ts.equaltypeSucceeded(_G, expectedTypeRef, actualTypeRef)
		// TypeEqualityUtils.isEqual(expectedTypeRef, actualTypeRef)
		);

		// return the containing script to allow chaining calls of firstXYZ().assertType()...
		return EcoreUtil2.getContainerOfType(expression, Script.class);
	}

	// private Script assertType(FormalParameter fpar, TypeRef expectedTypeRef) {
	// val funExpr = fpar.eContainer as FunctionExpression;
	// val actualTypeRef = getTypeFromPolyComputer(funExpr) as FunctionTypeExprOrRef;
	// val idx = funExpr.fpars.indexOf(fpar);
	// val fparTypeRef = actualTypeRef.getFparForArgIdx()
	//
	// assertTrue(
	// '''types not equal; expected: «expectedTypeRef?.typeRefAsString», actual: «actualTypeRef?.typeRefAsString»''',
	// TypeEqualityUtils.isEqual(expectedTypeRef, actualTypeRef)
	// );
	//
	// // return the containing script to allow chaining calls of firstXYZ().assertType()...
	// return EcoreUtil2.getContainerOfType(expression, Script);
	// }
	private TypeRef getTypeFromTypingCache(Expression expression) {
		// NOTE: not using N4JSTypeSystem#type() here to make 100% sure we are just reading from the cache
		return ASTMetaInfoUtils.getTypeFailSafe(expression);
	}

	@Test
	public void test_CallExpression_simple() {
		assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(p: T) : T {return null;}
				foo( a );
				""")),
				ref(A));
	}

	@Test
	public void test_CallExpression_nested_simple() {
		assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(p: T) : T {return null;}
				foo(foo(foo( a )));
				""")),
				ref(A));
	}

	@Test
	public void test_CallExpression_nested_upwardFlow() {
		assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(p: T) : G<T> {return null;}
				foo(foo(foo( a )));
				""")),
				of(G, of(G, of(G, A))));
	}

	@Test
	public void test_CallExpression_bidirectionalFlow() {
		assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(p: T) : G<T> {return null;}
				var test: G<G<G<B>>> = foo(foo(foo( c )));
				""")),
				of(G, of(G, of(G, B))));
	}

	@Test
	public void test_FunctionExpression_nonPoly() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun = function(p: A):B {return null;};
				""")),
				functionType(B, A) // {function(A):B}
		);
	}

	@Test
	public void test_FunctionExpression_undeclaredFparType_withExpectation() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun: {function(A):void} = function(p) {};
				""")),
				functionType(voidType(_G), A) // {function(A):void}
		);
	}

	@Test
	public void test_FunctionExpression_undeclaredFparType_withExpectation_withBody() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun: {function(A):void} = function(p) {
					var a: A = p;
				};
				""")),
				functionType(voidType(_G), A) // {function(A):void}
		);
	}

	@Test
	public void test_FunctionExpression_undeclaredFparType_noExpectation() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun = function(p) {};
				""")),
				functionType(voidType(_G), anyType(_G)) // {function(any):void}
		);
	}

	@Test
	public void test_FunctionExpression_undeclaredReturnType_withExpectation() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun: {function():A} = function() {return null;};
				""")),
				functionType(A) // {function():A}
		);
	}

	@Test
	public void test_FunctionExpression_undeclaredReturnType_noExpectation() {
		assertType(firstFunctionExpression(parseAndValidate("""
				var fun = function() {return null;};
				""")),
				functionType(anyType(_G)) // {function():any}
		);
	}

	@Test
	public void test_CallExpression_x_FunctionExpression_nonPoly() { // note: only the function expression is non-poly
																		// (the outer call expression is poly)
		assertType(firstCallExpression(parseAndValidate("""

				function <P,R> foo(fn: {function(P):R}) : G2<P,R> {return null;}
						foo( function(p: A):B {return null;} );
						""")),
				of(G2, A, B));
	}

	@Test
	public void test_CallExpression_x_FunctionExpression_undeclaredFparType() {
		assertType(firstFunctionExpression(assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(fn: {function(T)}) : T {return null;}
				var test: A = foo( function(p) {} );
				""")),
				ref(A))),
				functionType(voidType(_G), A) // {function(A):void}
		);
	}

	@Test
	public void test_CallExpression_x_FunctionExpression_undeclaredReturnType() {
		assertType(firstFunctionExpression(assertType(firstCallExpression(parseAndValidate("""
				function <T> foo(fn: {function():T}) : T {return null;}
				var test: A = foo( function() {return null;} );
				""")),
				ref(A))),
				functionType(A) // {function():A}
		);
	}

	@Test
	public void test_ArrayLiteral_noExpectation() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr = ["hello", 42];
				""")),
				of(arrayNType(_G, 2), stringTypeRef(_G), numberTypeRef(_G)) // Array2<string,number>
		);
	}

	@Test
	public void test_ArrayLiteral_withExpectation_exact_builtIn() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: Array<union{string,number}> = ["hello", 42];
				""")),
				of(arrayType(_G), union(stringType(_G), numberType(_G))) // Array<union{string,number}>
		);
	}

	@Test
	public void test_ArrayLiteral_withExpectation_exact_custom() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: Array<union{B1,B2}> = [b1, b2];
				""")),
				of(arrayType(_G), union(B1, B2)) // Array<union{B1,B2}>
		);
	}

	@Test
	public void test_ArrayLiteral_withExpectation_super_builtIn() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: Array<any> = ["hello", 42];
				""")),
				of(arrayType(_G), anyTypeRef(_G)) // Array<any>
		);
	}

	@Test
	public void test_ArrayLiteral_withExpectation_super_custom() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: Array<A> = [b1, b2];
				""")),
				of(arrayType(_G), ref(A)) // Array<A>
		);
	}

	@Test
	public void test_ArrayLiteral_withExpectation_wildcard() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: Array<?> = [b1, b2];
				""")),
				of(arrayType(_G), union(B1, B2)) // Array<union{B1,B2}>
		);
	}

	@Test
	public void test_ArrayLiteral_unionAsExpectation() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: number | Array<B> = [c];
				""")),
				of(arrayType(_G), B) // Array<B>
		);
	}

	@Test
	public void test_ArrayLiteral_unionAsExpectation_subtypeOfIterableOrArrayIsInvalidOption() {
		assertType(firstArrayLiteral(parseAndValidate("""
				class MyArray<T> extends Array<T> {}
				var arr: MyArray<A> | Iterable2<A,B> = [b,c];
				""")),
				of(arrayNType(_G, 2), B, C) // Array2<B,C>
		);
	}

	@Test
	public void test_ArrayLiteral_unionAsExpectation_subtypeOfIterableOrArrayIsInvalidOption_string01() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: string | Iterable2<A,B> = [b,c];
				""")),
				of(arrayNType(_G, 2), B, C) // Array2<B,C>
		);
	}

	@Test
	public void test_ArrayLiteral_unionAsExpectation_subtypeOfIterableOrArrayIsInvalidOption_string02() {
		assertType(firstArrayLiteral(parseAndValidate("""
				var arr: string | Iterable2<string,string> = ["hello", "world"];
				""")),
				of(arrayNType(_G, 2), stringTypeRef(_G), stringTypeRef(_G)) // Array2<string,string>
		);
	}

	@Test
	public void test_ArrayLiteral_unionAsExpectation_subtypeOfIterableOrArrayIsInvalidOption_string03() {
		assertType(firstArrayLiteral(parseAndValidate("""
				@StringBased
				enum Status {
					ON, OFF
				}
				var arr: string | Array2<string,string> = [Status.ON, "world"];
				""")),
				of(arrayNType(_G, 2), stringTypeRef(_G), stringTypeRef(_G)) // Array2<string,string>
		);
	}

	// @Test
	// public void test_ObjectLiteral() {
	// '''
	// var ~Object with {string s; number n;} ol = {
	// s: "hello",
	// n: 42
	// };
	// '''
	// .parse
	// .firstObjectLiteral.assertType(
	// _G.objectTypeRef // FIXME!
	// )
	// }

	// TODO:
	// function <T> foo(Array<T> p1, G<T> p2) : T {}
	// <A>foo( [ new B() ], ga);
	// foo( [ new B() ], ga);

	@Test
	public void testTEMP() {
		// empty
	}
}
