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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for generics, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class GenericsTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testTypeVars() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A{}

						class G<T> {
							field: T;
						}

						var a: G<A> = null;
						var x = a.field;
						""");

		RuleEnvironment G = newRuleEnvironment(script);

		List<Issue> issues = valTestHelper.validate(script);

		// println(issues)
		assertEquals(0, issues.size()); // TODO improve issue checking

		Expression fieldAccess = ((VariableStatement) last(script.getScriptElements())).getVarDecl().get(0)
				.getExpression();

		TypeRef result = ts.type(G, fieldAccess);

		// println(result.failureMessage)
		assertNotNull(result);
		assertFalse(result instanceof UnknownTypeRef);
		assertEquals("A", ((ParameterizedTypeRef) result).getDeclaredType().getName());
	}

	@Test
	public void testUpperLowerBoundOfWildcard() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A {}
						class G<T>{}

						var g1: G<A>;
						var g2: G<?>;
						var g3: G<? extends A>;
						var g4: G<? super A>;

						""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeArgument w1 = ((ParameterizedTypeRef) ((VariableStatement) script.getScriptElements().get(3)).getVarDecl()
				.get(0).getDeclaredTypeRef()).getDeclaredTypeArgs().get(0);
		TypeArgument w2 = ((ParameterizedTypeRef) ((VariableStatement) script.getScriptElements().get(4)).getVarDecl()
				.get(0).getDeclaredTypeRef()).getDeclaredTypeArgs().get(0);
		TypeArgument w3 = ((ParameterizedTypeRef) ((VariableStatement) script.getScriptElements().get(5)).getVarDecl()
				.get(0).getDeclaredTypeRef()).getDeclaredTypeArgs().get(0);
		TypeArgument w4 = ((ParameterizedTypeRef) ((VariableStatement) script.getScriptElements().get(6)).getVarDecl()
				.get(0).getDeclaredTypeRef()).getDeclaredTypeArgs().get(0);

		Type A = ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		Type bot = getPredefinedTypes(G).builtInTypeScope.getUndefinedType();
		Type top = getPredefinedTypes(G).builtInTypeScope.getAnyType();

		TypeRef result;
		result = ts.lowerBound(G, w1);
		assertEquals(A, ((ParameterizedTypeRef) result).getDeclaredType());
		result = ts.upperBound(G, w1);
		assertEquals(A, ((ParameterizedTypeRef) result).getDeclaredType());

		result = ts.upperBound(G, w2);
		assertEquals(top, ((ParameterizedTypeRef) result).getDeclaredType());
		result = ts.lowerBound(G, w2);
		assertEquals(bot, ((ParameterizedTypeRef) result).getDeclaredType());

		result = ts.upperBound(G, w3);
		assertEquals(A, ((ParameterizedTypeRef) result).getDeclaredType());
		result = ts.lowerBound(G, w3);
		assertEquals(bot, ((ParameterizedTypeRef) result).getDeclaredType());

		result = ts.upperBound(G, w4);
		assertEquals(top, ((ParameterizedTypeRef) result).getDeclaredType());
		result = ts.lowerBound(G, w4);
		assertEquals(A, ((ParameterizedTypeRef) result).getDeclaredType());

	}

	@Test
	public void testSubtypeWithGenerics() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A {}
						class G<T>{}

						var g1: G<A>;
						var g2: G<A>;
						var g3: G<B>;
						var g4: G<B>;
						var g5: G<?>;

						var g6: G<? extends B>;
						var g7: G<? extends A>;
						""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef g1 = ((VariableStatement) script.getScriptElements().get(3)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g2 = ((VariableStatement) script.getScriptElements().get(4)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g3 = ((VariableStatement) script.getScriptElements().get(5)).getVarDecl().get(0).getDeclaredTypeRef();

		// TypeRef g4 = ((VariableStatement)script.getScriptElements().get(6) ).getVarDecl().get(0).getDeclaredTypeRef()
		TypeRef g5 = ((VariableStatement) script.getScriptElements().get(7)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g6 = ((VariableStatement) script.getScriptElements().get(8)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g7 = ((VariableStatement) script.getScriptElements().get(9)).getVarDecl().get(0).getDeclaredTypeRef();

		Result result = ts.subtype(G, g1, g2);
		assertSubtype(result, true);

		// with trace:
		result = ts.subtype(G, g3, g1);

		// println(trace.traceAsString())
		assertSubtype(result, false);

		result = ts.subtype(G, g1, g5);

		// println(trace.traceAsString())
		assertSubtype(result, true);

		result = ts.subtype(G, g5, g1);
		assertSubtype(result, false);

		// G<? extends A> <: G<? extends B> // --> false
		result = ts.subtype(G, g7, g6);
		assertSubtype(result, false);

		// G<? extends B> <: G<? extends A> // --> true
		result = ts.subtype(G, g6, g7);

		// println(result.ruleFailedException.failureTraceAsString())
		assertSubtype(result, true);

	}

	@Test
	public void testSubtypeWithSimpleWildcard() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A {}
						class G<T>{}

						var g1: G<A>;
						var g2: G<?>;
						""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef g1 = ((VariableStatement) script.getScriptElements().get(3)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g2 = ((VariableStatement) script.getScriptElements().get(4)).getVarDecl().get(0).getDeclaredTypeRef();

		assertSubtype(G, g1, g2, true);
		assertSubtype(G, g2, g1, false);
	}

	@Test
	public void testSubtypeWithExistentialTypes() {

		// class A{}
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A {}
						class G<T>{
							get(): T { return null;}
							set(t: T): void { }
						}

						var g1: G<? extends A>;
						var g2: G<? extends A>;
						var ga: G<A>;

						var g1get = g1.get();
						g2.get();
						""");

		RuleEnvironment G = newRuleEnvironment(script);

		TypeRef g1 = ((VariableStatement) script.getScriptElements().get(3)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef g2 = ((VariableStatement) script.getScriptElements().get(4)).getVarDecl().get(0).getDeclaredTypeRef();
		TypeRef ga = ((VariableStatement) script.getScriptElements().get(5)).getVarDecl().get(0).getDeclaredTypeRef();

		Result result = ts.subtype(G, g1, g2);

		// println("Trace: " + trace.traceAsString())
		// if (result.ruleFailedException!=null) {
		// result.ruleFailedException.printStackTrace
		// }
		assertSubtype(result, true); // we can use g1 wherever we use g2!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g2, g1);
		assertSubtype(result, true); // and vice versa

		G = newRuleEnvironment(script);
		result = ts.subtype(G, ga, g1);

		// println("Trace: " + trace.traceAsString())
		// if (result.ruleFailedException!=null) {
		// result.ruleFailedException.printStackTrace
		// }
		assertSubtype(result, true); // we can use ga wherever we use g1!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g1, ga);

		// println("Trace: " + trace.traceAsString())
		// if (result.ruleFailedException!=null) {
		// result.ruleFailedException.printStackTrace
		// }
		assertSubtype(result, false); // we cannot use g1 wherever we use ga!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g2, g1);
		assertSubtype(result, true); // and vice versa

		// but the last line should create an error:
		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(0, issues);

	}

	@Test
	public void testParameterizedClassFunctionReturnType() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class G<T>{
							get(): T { return null;}

						}
						var ga: G<A>;
						var a: A;
						a = ga.get();
						""");

		RuleEnvironment G = newRuleEnvironment(script);
		AssignmentExpression assignment = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements()
				.get(4)).getExpression();

		TypeRef typeLeft = ts.type(G, assignment.getLhs());
		TypeRef typeRight = ts.type(G, assignment.getRhs());
		assertType(typeRight, ((ParameterizedTypeRef) typeLeft).getDeclaredType());

		// but the last line should create an error:
		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(0, issues);
	}

	@Test
	public void testParameterizedClassFunctionFparType() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class G<T>{
							set(t: T): void {}

						}
						var ga: G<A>;
						var a: A;
						ga.set(a);
						""");

		RuleEnvironment G = newRuleEnvironment(script);
		ParameterizedCallExpression call = (ParameterizedCallExpression) ((ExpressionStatement) script
				.getScriptElements().get(4)).getExpression();

		Argument arg0 = call.getArguments().get(0);
		TypeRef expectedType = ts.expectedType(G, arg0, arg0.getExpression());
		TypeRef actualType = ts.type(G, arg0.getExpression());

		assertType(expectedType, ((ParameterizedTypeRef) actualType).getDeclaredType());

		// but the last line should create an error:
		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(0, issues);
	}

	@Test
	public void testNestedParameterizedClassFunctionReturnType() {
		Script script = createAndValidateScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class G<T>{
							get(): G<T> { return null;}

						}
						var ga: G<A>;
						var a: A;
						ga = ga.get();
						""");

		RuleEnvironment G = newRuleEnvironment(script);
		AssignmentExpression assignment = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements()
				.get(4)).getExpression();

		TypeRef typeLeft = ts.type(G, assignment.getLhs());
		TypeRef typeRight = ts.type(G, assignment.getRhs());
		assertType(typeRight, ((ParameterizedTypeRef) typeLeft).getDeclaredType());

		// but the last line should create an error:
		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(0, issues);
	}

	@Test
	public void testNested2ParameterizedClassFunctionReturnType() {
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class G<T>{
							get(): G<G<T>> { return null;}
							foo(): G<T> { return null; }
						}
						var ga: G<A>;
						var a: A;
						ga = ga.get().foo();
						""");

		AssignmentExpression assignment = (AssignmentExpression) ((ExpressionStatement) script.getScriptElements()
				.get(4)).getExpression();

		assertTypeName("G<A>", assignment.getLhs());
		assertTypeName("G<G<A>>", assignment.getRhs());

		// but the last line should create an error:
		List<Issue> issues = valTestHelper.validate(script);

		// the error is due to ga.get().foo() being of type G<G<A>>
		// which cannot be assigned to G<A>
		assertIssueCount(1, issues);
	}

	@Test
	public void testSubtypeWithExistentialTypesAndVariance() {

		// class A{}
		Script script = createScript(JavaScriptVariant.n4js,
				"""
						class A{}
						class B extends A {}
						class C extends B {}
						class G<T>{
							t: T;
							b: B;

							get(): T { return null;}
							set(t: T): void { }
							getB(): B { return null; }
						}

						var g1: G<? extends B>;
						var g2: G<? extends B>;
						var ga: G<A>;
						var gb: G<B>;
						var gc: G<C>;

						var a: A;
						var b: B;
						var c: C;

						b = ga.get(); 	// error 0
						c = ga.getB();  // error 1: ParameterizedTypeRef 'TClass 'B'' is not a subtype of C
						c = ga.t;		// error 2: failed: A is not a subtype of C
						c = ga.b;		// error 3: failed: ParameterizedTypeRef 'TClass 'B'' is not a subtype of C

						a = g1.get();
						b = g1.get();
						c = g1.get();	// error 4

						g1.set(g2.get());	// error 5: failed: ExistentialTypeRef '? extends B' is not a subtype of ExistentialTypeRef '? extends B'
						""");

		RuleEnvironment G = newRuleEnvironment(script);
		ParameterizedCallExpression call = (ParameterizedCallExpression) ((ExpressionStatement) last(
				script.getScriptElements())).getExpression();

		Argument arg0 = call.getArguments().get(0);
		TypeRef expectedType = ts.expectedType(G, arg0, arg0.getExpression());
		TypeRef actualType = ts.type(G, arg0.getExpression());

		Result result = ts.subtype(G, actualType, expectedType);
		assertTrue(result.isFailure());

		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(6, issues);
	}

	@Test
	public void testUnboundTypeVariableInsideGenerics() {
		assertValidationErrors("""
				class A {
				   foo() {}
				}
				class G<T extends A> {
					items: Array<T>;
				    a: T;

					getItem(): T {
						this.a.foo();
						return this.items[0];
					}
				}
				""", "");
	}

	@Test
	public void testUnboundTypeVariableInsideGenerics2() {
		assertValidationErrors("""
				class A {
				   foo(): A { return null; }
				}
				class G<T extends A> {
					items: Array<T>;
				    a: T;

					getItem(): T {
						return this.a.foo();
					}
				}
				""", "A is not a subtype of T.");
	}

	@Test
	public void testUnboundTypeVariableInsideGenerics3() {
		assertValidationErrors("""
				class A {
				}
				class B {
				}
				class G<T extends A> {
					t: T;
					a: A;
					b: B;

					getItem(): T {
						this.a = this.t; // must work, as (T extends A) <: A
						this.b = this.t; // must not work, as (T extends A) <: B
						this.t = this.a; // must not work, as A <: (T extends A) is false in general
						return null;
					}
				}
				""", """

				T is not a subtype of B.
				A is not a subtype of T.
				""");
	}

	private void assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		Script script = createScript(JavaScriptVariant.n4js, input.toString());
		List<Issue> issues = valTestHelper.validate(script);
		assertErrorMessages(issues, expectedErrors);
	}
}
