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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import java.util.function.Predicate
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class PolyProcessorTest extends AbstractTypesystemTest {

	@Inject
	private N4JSTypeSystem ts;
//	@Inject
//	private extension ValidationTestHelper


	val private static preamble = '''

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

	''';

	var RuleEnvironment _G;

	var Type A;
	var Type B;
	var Type B1;
	var Type B2;
	var Type C;
	var Type G;
	var Type G2;

	def private Script parse(CharSequence code) {
		val script = createScript(JavaScriptVariant.n4js, (preamble + code).toString);

		_G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		N4JSResource.postProcessContainingN4JSResourceOf(script);
		// FIXME make sure we only work with valid models
		//val issues = script.validate();
		//assertEquals(Arrays.toString(issues.toArray), 0, issues.size);

		A = script.module.topLevelTypes.findFirst[name=="A"];
		B = script.module.topLevelTypes.findFirst[name=="B"];
		B1 = script.module.topLevelTypes.findFirst[name=="B1"];
		B2 = script.module.topLevelTypes.findFirst[name=="B2"];
		C = script.module.topLevelTypes.findFirst[name=="C"];

		G = script.module.topLevelTypes.findFirst[name=="G"];
		G2 = script.module.topLevelTypes.findFirst[name=="G2"];

		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(B1);
		assertNotNull(B2);
		assertNotNull(C);

		assertNotNull(G);
		assertNotNull(G2);

		return script;
	}


	def private ArrayLiteral firstArrayLiteral(Script script) {
		script.first(ArrayLiteral,[true]);
	}
//	def private ObjectLiteral firstObjectLiteral(Script script) {
//		script.first(ObjectLiteral,[true]);
//	}
	def private ParameterizedCallExpression firstCallExpression(Script script) {
		script.first(ParameterizedCallExpression,[true])
	}
	def private FunctionExpression firstFunctionExpression(Script script) {
		script.first(FunctionExpression,[true])
	}
//	def private FormalParameter firstFpar(Script script, String name) {
//		script.first(FormalParameter,[it.name==name])
//	}
	def private <T extends EObject> T first(Script script, Class<T> nodeType, Predicate<T> pred) {
		val result = script.eAllContents.filter(nodeType).filter(pred).head;
		assertNotNull("no expression of type "+nodeType.name+" in script", result);
		return result;
	}


	def private Script assertType(Expression expression, TypeRef expectedTypeRef) {
		val actualTypeRef = getTypeFromTypingCache(expression);

		assertTrue(
			'''types not equal; expected: «expectedTypeRef?.typeRefAsString», actual: «actualTypeRef?.typeRefAsString»''',
			ts.equaltypeSucceeded(_G, expectedTypeRef, actualTypeRef)
//			TypeEqualityUtils.isEqual(expectedTypeRef, actualTypeRef)
		);

		// return the containing script to allow chaining calls of firstXYZ().assertType()...
		return EcoreUtil2.getContainerOfType(expression, Script);
	}
//	def private Script assertType(FormalParameter fpar, TypeRef expectedTypeRef) {
//		val funExpr = fpar.eContainer as FunctionExpression;
//		val actualTypeRef = getTypeFromPolyComputer(funExpr) as FunctionTypeExprOrRef;
//		val idx = funExpr.fpars.indexOf(fpar);
//		val fparTypeRef = actualTypeRef.getFparForArgIdx()
//
//		assertTrue(
//			'''types not equal; expected: «expectedTypeRef?.typeRefAsString», actual: «actualTypeRef?.typeRefAsString»''',
//			TypeEqualityUtils.isEqual(expectedTypeRef, actualTypeRef)
//		);
//
//		// return the containing script to allow chaining calls of firstXYZ().assertType()...
//		return EcoreUtil2.getContainerOfType(expression, Script);
//	}
	def private TypeRef getTypeFromTypingCache(Expression expression) {
		// NOTE: not using N4JSTypeSystem#type() here to make 100% sure we are just reading from the cache
		return ASTMetaInfoUtils.getTypeFailSafe(expression);
	}


	@Test
	def void test_CallExpression_simple() {
		'''
			function <T> foo(p: T) : T {return null;}
			foo( a );
		'''
		.parse
		.firstCallExpression.assertType(
			A.ref
		)
	}
	@Test
	def void test_CallExpression_nested_simple() {
		'''
			function <T> foo(p: T) : T {return null;}
			foo(foo(foo( a )));
		'''
		.parse
		.firstCallExpression.assertType(
			A.ref
		)
	}
	@Test
	def void test_CallExpression_nested_upwardFlow() {
		'''
			function <T> foo(p: T) : G<T> {return null;}
			foo(foo(foo( a )));
		'''
		.parse
		.firstCallExpression.assertType(
			G.of(G.of(G.of(A)))
		)
	}
	@Test
	def void test_CallExpression_bidirectionalFlow() {
		'''
			function <T> foo(p: T) : G<T> {return null;}
			var test: G<G<G<B>>> = foo(foo(foo( c )));
		'''
		.parse
		.firstCallExpression.assertType(
			G.of(G.of(G.of(B)))
		)
	}




	@Test
	def void test_FunctionExpression_nonPoly() {
		'''
			var fun = function(p: A):B {};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(B,A) // {function(A):B}
		)
	}
	@Test
	def void test_FunctionExpression_undeclaredFparType_withExpectation() {
		'''
			var fun: {function(A):void} = function(p) {};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(_G.voidType, A) // {function(A):void}
		)
	}
	@Test
	def void test_FunctionExpression_undeclaredFparType_withExpectation_withBody() {
		'''
			var fun: {function(A):void} = function(p) {
				var a: A = p;
			};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(_G.voidType, A) // {function(A):void}
		)
	}
	@Test
	def void test_FunctionExpression_undeclaredFparType_noExpectation() {
		'''
			var fun = function(p) {};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(_G.voidType, _G.anyType) // {function(any):void}
		)
	}
	@Test
	def void test_FunctionExpression_undeclaredReturnType_withExpectation() {
		'''
			var fun: {function():A} = function() {return null;};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(A) // {function():A}
		)
	}
	@Test
	def void test_FunctionExpression_undeclaredReturnType_noExpectation() {
		'''
			var fun = function() {return null;};
		'''
		.parse
		.firstFunctionExpression.assertType(
			functionType(_G.anyType) // {function():any}
		)
	}


	@Test
	def void test_CallExpression_x_FunctionExpression_nonPoly() { // note: only the function expression is non-poly (the outer call expression is poly)
		'''
			function <P,R> foo(fn: {function(P):R}) : G2<P,R> {return null;}
			foo( function(p: A):B {} );
		'''
		.parse
		.firstCallExpression.assertType(
			G2.of(A,B)
		)
	}
	@Test
	def void test_CallExpression_x_FunctionExpression_undeclaredFparType() {
		'''
			function <T> foo(fn: {function(T)}) : T {return null;}
			var test: A = foo( function(p) {} );
		'''
		.parse
		.firstCallExpression.assertType(
			A.ref
		)
		.firstFunctionExpression.assertType(
			functionType(_G.voidType, A) // {function(A):void}
		)
	}
	@Test
	def void test_CallExpression_x_FunctionExpression_undeclaredReturnType() {
		'''
			function <T> foo(fn: {function():T}) : T {return null;}
			var test: A = foo( function() {return null;} );
		'''
		.parse
		.firstCallExpression.assertType(
			A.ref
		)
		.firstFunctionExpression.assertType(
			functionType(A) // {function():A}
		)
	}




	@Test
	def void test_ArrayLiteral_noExpectation() {
		'''
			var arr = ["hello", 42];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(union(_G.numberType, _G.stringType)) // Array<union{number,string}>
		)
	}
	@Test
	def void test_ArrayLiteral_withExpectation_exact_builtIn() {
		'''
			var arr: Array<union{string,number}> = ["hello", 42];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(union(_G.stringType, _G.numberType)) // Array<union{string,number}>
		)
	}
	@Test
	def void test_ArrayLiteral_withExpectation_exact_custom() {
		'''
			var arr: Array<union{B1,B2}> = [b1, b2];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(union(B1, B2)) // Array<union{B1,B2}>
		)
	}
	@Test
	def void test_ArrayLiteral_withExpectation_super_builtIn() {
		'''
			var arr: Array<any> = ["hello", 42];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(_G.anyTypeRef) // Array<any>
		)
	}
	@Test
	def void test_ArrayLiteral_withExpectation_super_custom() {
		'''
			var arr: Array<A> = [b1, b2];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(A.ref) // Array<A>
		)
	}
	@Test
	def void test_ArrayLiteral_withExpectation_wildcard() {
		'''
			var arr: Array<?> = [b1, b2];
		'''
		.parse
		.firstArrayLiteral.assertType(
			_G.arrayType.of(union(B1, B2)) // Array<union{B1,B2}>
		)
	}




//	@Test
//	def void test_ObjectLiteral() {
//		'''
//			var ~Object with {string s; number n;} ol = {
//				s: "hello",
//				n: 42
//			};
//		'''
//		.parse
//		.firstObjectLiteral.assertType(
//			_G.objectTypeRef // FIXME!
//		)
//	}


// TODO:
//			function <T> foo(Array<T> p1, G<T> p2) : T {}
//			<A>foo( [ new B() ], ga);
//			foo( [ new B() ], ga);



	@Test
	def void testTEMP() {
	}
}
