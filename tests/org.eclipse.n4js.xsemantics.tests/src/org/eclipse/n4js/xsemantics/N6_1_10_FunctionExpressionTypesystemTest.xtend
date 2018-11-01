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
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Test class for function expression, see  6.1.10. Function Expression
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class N6_1_10_FunctionExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject	extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void testFunctionExpressionAsArgumentDefaultReturnVoid() {
		val script = '''
			function f1(f: {function(number):void}) {}
			f1(function(i: number){});
		'''.parse()

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		val funcExpr = call.arguments.head.expression as FunctionExpression;
		assertTypeByName("{function(number):void}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	def void testFunctionExpressionAsArgumentDefaultReturnAny() {
		val script = '''
			function f1(f: {function(number):void}) {}
			f1(function(i: number){return null});
		'''.parse()

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		val funcExpr = call.arguments.head.expression as FunctionExpression;
		assertTypeName("{function(number):any}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	def void testFunctionExpressionAsArgument2() {
		val script = '''
			function f1(f: {function(number):string}) {}
			f1(function(i: number):string{return null;});
		'''.parse()

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		val funcExpr = call.arguments.head.expression as FunctionExpression;
		assertTypeByName("{function(number):string}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	def void testFunctionExpressionAsArgumentWithDefaultTypes() {
		val script = '''
			function f1(f: {function(any):any}) {}
			f1(function(i){return null;});
		'''.parse()

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		val funcExpr = call.arguments.head.expression as FunctionExpression;
		assertTypeByName("{function(any):any}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}


	@Test
	def void testFunctionExpressionAsArgumentWithError() {
		val script = '''
			function f1(f: {function(number):void}) {}
			f1(function(s: string){});
		'''.parse()

		val call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression).head;
		val funcExpr = call.arguments.head.expression as FunctionExpression;


		assertTypeName("{function(string):void}", funcExpr);
		// eventually:
		val issues = script.validate();
		assertIssueCount(1, issues);
	}

	@Test
	def void testFunctionExpressionWithInferredTypeForParameter() {
		'''
			function f1(f: {function(number):void}) {}
			f1(function(s){});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):void}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredReturnType() {
		'''
			function f1(f: {function(number):string}) {}
			f1(function(s){return null;});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):string}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredReturnNullType() {
		'''
			function f1(f: {function(number):string}) {}
			f1(function(s){return null;});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):string}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredReturnUnionTypeNotSubtype() {
		'''
			class A {}
			class B {}
			function f1(f: {function(number):A}) {}
			f1(function(s){
				if (true)
					return new A();
				else
					return new B();
			});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):A}", 1)
		// assertTypeOfFunctionExpressionInCallExpression("{function(number):union{A,B}}", 1)
		// union{A,B} is not subtype of A
	}

	@Test
	def void testFunctionExpressionWithInferredReturnNonUnionType() {
		'''
			class A {}
			class B extends A {}
			function f1(f: {function(number):A}) {}
			f1(function(s){
				if (true)
					return new A();
				else
					return new B();
			});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):A}", 0)
		//assertTypeOfFunctionExpressionInCallExpression("{function(number):union{A,B}}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredReturnUnionTypeSimplified() {
		'''
			class A {}
			function f1(f: {function(number):A}) {}
			f1(function(s){
				if (true)
					return new A();
				else
					return new A();
			});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):A}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredParameterType() {
		// the inferred return type depends on the function expected type
		'''
			function f1(f: {function(number):number}) {}
			f1(function(i){
				return i;
			});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):number}", 0)
	}

	@Test
	def void testFunctionExpressionWithInferredParameterType2() {
		// the validation of parameter i depends on the inferred type
		// of the containing FunctionExpression
		'''
			function foo(f: {function(number)}) {}
			function bar(x: number) {}
			foo(function(i){ bar(i);});
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(number):void}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics() {
		'''
			function <T> f1(p: T, f: {function(T):T}) : T {return null;}

			f1("hello",
				function(i){
					return i;
				}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(string):string}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics2() {
		'''
			function <T> f1(f: {function(T):T}, p: T) : T {return null;}

			f1(
				function(i){
					return i;
				},
				"hello"
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(string):string}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics3() {
		'''
			class G<T> {}
			class A {}

			function <T> f1(p: G<T>, f: {function(T)}) : T {return null;}

			var a: G<A>;

			f1(a,
				function(i){
					return i;
				}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(A):any}", 0)
		//assertTypeOfFunctionExpressionInCallExpression("{function(A):A}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics4() {
		'''
			class G<T> {}

			function <T> f1(p: T, f: {function(G<T>)}) : T {return null;}

			f1("hello",
				function(i){
					return i;
				}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<string>):any}", 0)
		//assertTypeOfFunctionExpressionInCallExpression("{function(G<string>):G<string>}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics5() {
		'''
			class G<T> {
				a: T;
			}

			function <T> f1(p: T, f: {function(G<T>):G<T>}) : T {
				return f(null).a;
			}

			f1("hello",
				function(i){
					return i;
				}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<string>):G<string>}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics5a() {
		val script = '''
			class G<T> {
				a: T;
			}

			function <T> f1(f: {function():G<T>}) : T {
				return f().a;
			}
		'''.parse;
		script.validate;

		val call = script.eAllContents.filter(ParameterizedCallExpression).last

//		val type = ts.type(call).value; // will cause an NPE internally
		val typeG = ts.type(RuleEnvironmentExtensions.newRuleEnvironment(call), call);
		val typeI = ts.tau(call);

		assertEquals(typeG.declaredType, typeI.declaredType);


		script.assertNoErrors;



	}

	@Test
	def void testFunctionExpressionWithGenerics6() {
		'''
			class G<T> {
				a: T;
			}

			function <T> f1(p: T, f: {function(G<T>):T}) : T {
				return f(null);
			}

			f1("hello",
				function(i: G<string>){
					return i.a;
				}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<string>):string}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenerics7() {
		'''
			class G<T> {
				a: T;
			}

			class A {}

			function <T> f1(p: T, f: {function(G<T>)}) : T {
				return null;
			}

			var gga: G<G<A>>;

			f1(gga,
				function(i){}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<G<G<A>>>):void}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenericsAndTypeVarBindingFromSupertypes() {
		'''
			class B {}
			class C {}

			interface G<T> {}

			interface I1 extends G<G<B>> {}
			interface I2 extends G<C> {}

			class A implements I1, I2 {}

			function <T> f1(p: G<T>, f: {function(G<T>)}): T {
				return null;
			}

			var a: A;

			f1(a,
				function(i){}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression(
			// old expectation:
			// "{function(G<intersection{C,G<B>}>):void}"
			// TODO IDE-2226 revisit test case
			"{function(G<[unknown]>):void}",
			1
		)
		// validation error: A is not a subtype of G<intersection{C,G<B>}>
	}

	@Test
	def void testFunctionExpressionWithGenerics8() {
		'''
			class G<T> {
				a: T;
			}

			class A {}

			function <T> f1(p: T, f: {function(G<T>)}): T {
				return null;
			}

			f1(
				function first(p: G<A>){ return p.a; },
				function second(i){}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<{function(G<A>):any}>):void}", 0)
		//assertTypeOfFunctionExpressionInCallExpression("{function(G<{function(G<A>):A}>):void}", 0)
	}

	@Test
	def void testFunctionExpressionWithGenericsAndUnion() {
		'''
			class G<T> {
				a: T;
			}

			class A {}
			class B {}

			function <T> f1(p: T, p2: T, f: {function(G<T>)}): T {
				return null;
			}

			f1(
				function(p: G<A>){ return p.a; },
				function(p: G<B>){ return p.a; },
				function(i){}
			);
		'''.
		assertTypeOfFunctionExpressionInCallExpression("{function(G<union{{function(G<A>):any},{function(G<B>):any}}>):void}", 0)
		//assertTypeOfFunctionExpressionInCallExpression("{function(G<union{{function(G<A>):A},{function(G<B>):B}}>):void}", 0)
	}

	@Test
	def void testFunctionExpressionInVarDecl() {
		'''
			var f = function () {};
		'''.
		assertTypeOfFunctionExpression("{function():void}", 0)
	}

	@Test
	def void testFunctionExpressionInVarDecl2() {
		'''
			var f = function (i) {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(any):any}", 0)
	}

	@Test
	def void testFunctionExpressionInVarDecl3() {
		'''
			var f = function (i: number) {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(number):any}", 0)
	}

	@Test
	def void testFunctionExpressionInVarDeclWithExpectations() {
		'''
			var f: {function(number):string} = function (i) {return null;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 0)
	}

	@Test
	def void testFunctionExpressionInVarDeclWrong() {
		'''
			var f: {function(number):string} = function (i) {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 1)
		//assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	def void testFunctionExpressionInAssignment() {
		'''
			var f: {function(number):string} = null;
			f = function (i) {return null;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 0)
	}

	@Test
	def void testFunctionExpressionInAssignmentWithError() {
		'''
			var f: {function(number):string} = null;
			f = function (i) {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 1) // number i not <: string
		//assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	def void testFunctionExpressionWithError() {
		'''
			var f: {function(number):string} = null;
			f = function (i: number): string {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 1)
		//assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	def void testFunctionExpressionWithName() {
		'''
			var f: {function(number):string} = null;
			f = function foo (i) {return i;};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 1)
	}

	@Test
	def void testFunctionExpressionRecursive() {
		'''
			var f: {function(number):string} = null;
			f = function foo (i) {return f(i);};
		'''.
		assertTypeOfFunctionExpression("{function(number):string}", 0)
	}

	def private void assertTypeOfFunctionExpressionInCallExpression(CharSequence scriptInput, String expectedTypeName, int expectedIssues) {
		val script = createScript(JavaScriptVariant.n4js, scriptInput.toString)
		val call = EcoreUtil2.getAllContentsOfType(
			script,
			ParameterizedCallExpression
		).filter[arguments.map[expression].exists[it instanceof FunctionExpression]].last;
		val funcExpr = call.arguments.map[expression].filter(FunctionExpression).last;

		assertTypeName(expectedTypeName, funcExpr);

		val issues = script.validate();
		assertIssueCount(expectedIssues, issues);
	}

	def private void assertTypeOfFunctionExpression(CharSequence scriptInput, String expectedTypeName, int expectedIssues) {
		val script = createScript(JavaScriptVariant.n4js, scriptInput.toString)
		val funcExpr = EcoreUtil2.getAllContentsOfType(
			script,
			FunctionExpression
		).last

		assertTypeName(expectedTypeName, funcExpr);

		val issues = script.validate();
		assertIssueCount(expectedIssues, issues);
	}
}
