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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.last;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Test class for function expression, see 6.1.10. Function Expression
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class N6_1_10_FunctionExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testFunctionExpressionAsArgumentDefaultReturnVoid() throws Exception {
		Script script = parseHelper.parse("""
				function f1(f: {function(number):void}) {}
				f1(function(i: number){});
				""");

		ParameterizedCallExpression call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class)
				.get(0);
		FunctionExpression funcExpr = (FunctionExpression) call.getArguments().get(0).getExpression();
		assertTypeByName("{function(number):void}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionExpressionAsArgumentDefaultReturnAny() throws Exception {
		Script script = parseHelper.parse("""
				function f1(f: {function(number):void}) {}
				f1(function(i: number){return null});
				""");

		ParameterizedCallExpression call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class)
				.get(0);
		FunctionExpression funcExpr = (FunctionExpression) call.getArguments().get(0).getExpression();
		assertTypeName("{function(number):any}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionExpressionAsArgument2() throws Exception {
		Script script = parseHelper.parse("""
				function f1(f: {function(number):string}) {}
				f1(function(i: number):string{return null;});
				""");

		ParameterizedCallExpression call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class)
				.get(0);
		FunctionExpression funcExpr = (FunctionExpression) call.getArguments().get(0).getExpression();
		assertTypeByName("{function(number):string}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionExpressionAsArgumentWithDefaultTypes() throws Exception {
		Script script = parseHelper.parse("""
				function f1(f: {function(any):any}) {}
				f1(function(i){return null;});
				""");

		ParameterizedCallExpression call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class)
				.get(0);
		FunctionExpression funcExpr = (FunctionExpression) call.getArguments().get(0).getExpression();
		assertTypeByName("{function(any):any}", funcExpr);

		// eventually:
		assertNoValidationErrors(script);
	}

	@Test
	public void testFunctionExpressionAsArgumentWithError() throws Exception {
		Script script = parseHelper.parse("""
				function f1(f: {function(number):void}) {}
				f1(function(s: string){});
				""");

		ParameterizedCallExpression call = EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class)
				.get(0);
		FunctionExpression funcExpr = (FunctionExpression) call.getArguments().get(0).getExpression();

		assertTypeName("{function(string):void}", funcExpr);
		// eventually:
		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(1, issues);
	}

	@Test
	public void testFunctionExpressionWithInferredTypeForParameter() {
		assertTypeOfFunctionExpressionInCallExpression("""
				function f1(f: {function(number):void}) {}
				f1(function(s){});
				""", "{function(number):void}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredReturnType() {
		assertTypeOfFunctionExpressionInCallExpression("""
				function f1(f: {function(number):string}) {}
				f1(function(s){return null;});
				""", "{function(number):string}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredReturnNullType() {
		assertTypeOfFunctionExpressionInCallExpression("""
				function f1(f: {function(number):string}) {}
				f1(function(s){return null;});
				""", "{function(number):string}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredReturnUnionTypeNotSubtype() {
		assertTypeOfFunctionExpressionInCallExpression("""
				class A {}
				class B {}
				function f1(f: {function(number):A}) {}
				f1(function(s){
					if (true)
						return new A();
					else
						return new B();
				});
				""", "{function(number):A}", 1);
		// assertTypeOfFunctionExpressionInCallExpression("{function(number):union{A,B}}", 1)
		// union{A,B} is not subtype of A
	}

	@Test
	public void testFunctionExpressionWithInferredReturnNonUnionType() {
		assertTypeOfFunctionExpressionInCallExpression("""
				class A {}
				class B extends A {}
				function f1(f: {function(number):A}) {}
				f1(function(s){
					if (true)
						return new A();
					else
						return new B();
				});
				""", "{function(number):A}", 0);
		// assertTypeOfFunctionExpressionInCallExpression("{function(number):union{A,B}}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredReturnUnionTypeSimplified() {
		assertTypeOfFunctionExpressionInCallExpression("""
				class A {}
				function f1(f: {function(number):A}) {}
				f1(function(s){
					if (true)
						return new A();
					else
						return new A();
				});
				""", "{function(number):A}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredParameterType() {
		// the inferred return type depends on the function expected type
		assertTypeOfFunctionExpressionInCallExpression("""
				function f1(f: {function(number):number}) {}
				f1(function(i){
					return i;
				});
				""", "{function(number):number}", 0);
	}

	@Test
	public void testFunctionExpressionWithInferredParameterType2() {
		// the validation of parameter i depends on the inferred type
		// of the containing FunctionExpression
		assertTypeOfFunctionExpressionInCallExpression("""
				function foo(f: {function(number)}) {}
				function bar(x: number) {}
				foo(function(i){ bar(i);});
				""", "{function(number):void}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics() {
		assertTypeOfFunctionExpressionInCallExpression("""
				function <T> f1(p: T, f: {function(T):T}) : T {return null;}

				f1("hello",
					function(i){
						return i;
					}
				);
				""", "{function(string):string}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics2() {
		assertTypeOfFunctionExpressionInCallExpression("""
				function <T> f1(f: {function(T):T}, p: T) : T {return null;}

				f1(
					function(i){
						return i;
					},
					"hello"
				);
				""", "{function(string):string}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics3() {
		assertTypeOfFunctionExpressionInCallExpression("""
				class G<T> {}
				class A {}

				function <T> f1(p: G<T>, f: {function(T)}) : T {return null;}

				var a: G<A>;

				f1(a,
					function(i){
						return i;
					}
				);
				""", "{function(A):any}", 0);
		// assertTypeOfFunctionExpressionInCallExpression("{function(A):A}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics4() {
		assertTypeOfFunctionExpressionInCallExpression("""
				class G<T> {}

				function <T> f1(p: T, f: {function(G<T>)}) : T {return null;}

				f1("hello",
					function(i){
						return i;
					}
				);
				""", "{function(G<string>):any}", 0);
		// assertTypeOfFunctionExpressionInCallExpression("{function(G<string>):G<string>}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics5() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""", "{function(G<string>):G<string>}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics5a() throws Exception {
		Script script = parseHelper.parse("""
				class G<T> {
					a: T;
				}

				function <T> f1(f: {function():G<T>}) : T {
					return f().a;
				}
				""");
		valTestHelper.validate(script);

		ParameterizedCallExpression call = last(filter(script.eAllContents(), ParameterizedCallExpression.class));

		// val type = ts.type(call).value; // will cause an NPE internally
		TypeRef typeG = ts.type(RuleEnvironmentExtensions.newRuleEnvironment(call), call);
		TypeRef typeI = ts.tau(call);

		assertEquals(typeG.getDeclaredType(), typeI.getDeclaredType());

		valTestHelper.assertNoErrors(script);

	}

	@Test
	public void testFunctionExpressionWithGenerics6() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""", "{function(G<string>):string}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenerics7() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""", "{function(G<G<G<A>>>):void}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenericsAndTypeVarBindingFromSupertypes() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""",
				// old expectation:
				// "{function(G<intersection{C,G<B>}>):void}"
				// TODO IDE-2226 revisit test case
				"{function(G<«unknown»>):void}",
				1);
		// validation error: A is not a subtype of G<intersection{C,G<B>}>
	}

	@Test
	public void testFunctionExpressionWithGenerics8() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""", "{function(G<{function(G<A>):any}>):void}", 0);
		// assertTypeOfFunctionExpressionInCallExpression("{function(G<{function(G<A>):A}>):void}", 0);
	}

	@Test
	public void testFunctionExpressionWithGenericsAndUnion() {
		assertTypeOfFunctionExpressionInCallExpression("""
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
				""", "{function(G<union{{function(G<A>):any},{function(G<B>):any}}>):void}", 0);
		// assertTypeOfFunctionExpressionInCallExpression("{function(G<union{{function(G<A>):A},{function(G<B>):B}}>):void}",
		// 0);
	}

	@Test
	public void testFunctionExpressionInVarDecl() {
		assertTypeOfFunctionExpression("""
				var f = function () {};
				""", "{function():void}", 0);
	}

	@Test
	public void testFunctionExpressionInVarDecl2() {
		assertTypeOfFunctionExpression("""
				var f = function (i) {return i;};
				""", "{function(any):any}", 0);
	}

	@Test
	public void testFunctionExpressionInVarDecl3() {
		assertTypeOfFunctionExpression("""
				var f = function (i: number) {return i;};
				""", "{function(number):any}", 0);
	}

	@Test
	public void testFunctionExpressionInVarDeclWithExpectations() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = function (i) {return null;};
				""", "{function(number):string}", 0);
	}

	@Test
	public void testFunctionExpressionInVarDeclWrong() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = function (i) {return i;};
				""", "{function(number):string}", 1);
		// assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	public void testFunctionExpressionInAssignment() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = null;
				f = function (i) {return null;};
				""", "{function(number):string}", 0);
	}

	@Test
	public void testFunctionExpressionInAssignmentWithError() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = null;
				f = function (i) {return i;};
				""", "{function(number):string}", 1); // number i not <: string
		// assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	public void testFunctionExpressionWithError() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = null;
				f = function (i: number): string {return i;};
				""", "{function(number):string}", 1);
		// assertTypeOfFunctionExpression("{function(number):number}", 1)
	}

	@Test
	public void testFunctionExpressionWithName() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = null;
				f = function foo (i) {return i;};
				""", "{function(number):string}", 1);
	}

	@Test
	public void testFunctionExpressionRecursive() {
		assertTypeOfFunctionExpression("""
				var f: {function(number):string} = null;
				f = function foo (i) {return f(i);};
				""", "{function(number):string}", 0);
	}

	private void assertTypeOfFunctionExpressionInCallExpression(CharSequence scriptInput, String expectedTypeName,
			int expectedIssues) {
		Script script = createScript(JavaScriptVariant.n4js, scriptInput.toString());
		ParameterizedCallExpression call = last(
				filter(EcoreUtil2.getAllContentsOfType(script, ParameterizedCallExpression.class),
						callExpr -> exists(map(callExpr.getArguments(), arg -> arg.getExpression()),
								expr -> expr instanceof FunctionExpression)));

		FunctionExpression funcExpr = last(
				filter(map(call.getArguments(), arg -> arg.getExpression()), FunctionExpression.class));

		assertTypeName(expectedTypeName, funcExpr);

		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(expectedIssues, issues);
	}

	private void assertTypeOfFunctionExpression(CharSequence scriptInput, String expectedTypeName, int expectedIssues) {
		Script script = createScript(JavaScriptVariant.n4js, scriptInput.toString());
		FunctionExpression funcExpr = last(EcoreUtil2.getAllContentsOfType(script, FunctionExpression.class));

		assertTypeName(expectedTypeName, funcExpr);

		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(expectedIssues, issues);
	}
}
