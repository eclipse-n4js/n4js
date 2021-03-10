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
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/*
 * Tests for generics, see n4js.xsemantics for judgment, axiom and rules.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class GenericsTest extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper

	@Test
	def void testTypeVars() {

		// class A{}
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A{}

				class G<T> {
					field: T;
				}

				var a: G<A> = null;
				var x = a.field;
			''')

		val G = newRuleEnvironment(script);

		val issues = script.validate();

		//		println(issues)
		assertEquals(0, issues.size) // TODO improve issue checking

		val fieldAccess = (script.scriptElements.last as VariableStatement).varDecl.head.expression

		val result = ts.type(G, fieldAccess)

		// println(result.failureMessage)
		assertNotNull(result)
		assertFalse(result instanceof UnknownTypeRef)
		assertEquals("A", (result as ParameterizedTypeRef).declaredType.name)
	}

	@Test
	def void testUpperLowerBoundOfWildcard() {
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A {}
				class G<T>{}

				var g1: G<A>;
				var g2: G<?>;
				var g3: G<? extends A>;
				var g4: G<? super A>;

			''')

		val G = newRuleEnvironment(script);

		val w1 = ((script.scriptElements.get(3) as VariableStatement).varDecl.head.declaredTypeRef as ParameterizedTypeRef).
			typeArgs.get(0)
		val w2 = ((script.scriptElements.get(4) as VariableStatement).varDecl.head.declaredTypeRef as ParameterizedTypeRef).
			typeArgs.get(0)
		val w3 = ((script.scriptElements.get(5) as VariableStatement).varDecl.head.declaredTypeRef as ParameterizedTypeRef).
			typeArgs.get(0)
		val w4 = ((script.scriptElements.get(6) as VariableStatement).varDecl.head.declaredTypeRef as ParameterizedTypeRef).
			typeArgs.get(0)

		val A = (script.scriptElements.get(0) as N4ClassDeclaration).definedType
		val bot = G.predefinedTypes.builtInTypeScope.undefinedType
		val top = G.predefinedTypes.builtInTypeScope.anyType

		var TypeRef result;
		result = ts.lowerBound(G, w1);
		assertEquals(A, (result as ParameterizedTypeRef).declaredType);
		result = ts.upperBound(G, w1);
		assertEquals(A, (result as ParameterizedTypeRef).declaredType);

		result = ts.upperBound(G, w2);
		assertEquals(top, (result as ParameterizedTypeRef).declaredType);
		result = ts.lowerBound(G, w2);
		assertEquals(bot, (result as ParameterizedTypeRef).declaredType);

		result = ts.upperBound(G, w3);
		assertEquals(A, (result as ParameterizedTypeRef).declaredType);
		result = ts.lowerBound(G, w3);
		assertEquals(bot, (result as ParameterizedTypeRef).declaredType);

		result = ts.upperBound(G, w4);
		assertEquals(top, (result as ParameterizedTypeRef).declaredType);
		result = ts.lowerBound(G, w4);
		assertEquals(A, (result as ParameterizedTypeRef).declaredType);

	}

	@Test
	def void testSubtypeWithGenerics() {

		// class A{}
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
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
			''')

		val G = newRuleEnvironment(script);

		val g1 = (script.scriptElements.get(3) as VariableStatement).varDecl.head.declaredTypeRef
		val g2 = (script.scriptElements.get(4) as VariableStatement).varDecl.head.declaredTypeRef
		val g3 = (script.scriptElements.get(5) as VariableStatement).varDecl.head.declaredTypeRef

		// val g4 = (script.scriptElements.get(6) as VariableStatement).varDecl.head.declaredTypeRef
		val g5 = (script.scriptElements.get(7) as VariableStatement).varDecl.head.declaredTypeRef
		val g6 = (script.scriptElements.get(8) as VariableStatement).varDecl.head.declaredTypeRef
		val g7 = (script.scriptElements.get(9) as VariableStatement).varDecl.head.declaredTypeRef

		var result = ts.subtype(G, g1, g2)
		assertSubtype(result, true)

		// with trace:
		result = ts.subtype(G, g3, g1)

		// println(trace.traceAsString())
		assertSubtype(result, false)

		result = ts.subtype(G, g1, g5)

		//		println(trace.traceAsString())
		assertSubtype(result, true)

		result = ts.subtype(G, g5, g1)
		assertSubtype(result, false)

		// G<? extends A> <: G<? extends B> // --> false
		result = ts.subtype(G, g7, g6)
		assertSubtype(result, false)

		// G<? extends B> <: G<? extends A> // --> true
		result = ts.subtype(G, g6, g7)

		// println(result.ruleFailedException.failureTraceAsString())
		assertSubtype(result, true)

	}

	@Test
	def void testSubtypeWithSimpleWildcard() {

		// class A{}
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class B extends A {}
				class G<T>{}

				var g1: G<A>;
				var g2: G<?>;
			''')

		val G = newRuleEnvironment(script);

		val g1 = (script.scriptElements.get(3) as VariableStatement).varDecl.head.declaredTypeRef
		val g2 = (script.scriptElements.get(4) as VariableStatement).varDecl.head.declaredTypeRef

		assertSubtype(G, g1, g2, true)
		assertSubtype(G, g2, g1, false)
	}

	@Test
	def void testSubtypeWithExistentialTypes() {

		// class A{}
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
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
			''')

		var G = newRuleEnvironment(script);

		val g1 = (script.scriptElements.get(3) as VariableStatement).varDecl.head.declaredTypeRef
		val g2 = (script.scriptElements.get(4) as VariableStatement).varDecl.head.declaredTypeRef
		val ga = (script.scriptElements.get(5) as VariableStatement).varDecl.head.declaredTypeRef

		var result = ts.subtype(G, g1, g2)

		//		println("Trace: " + trace.traceAsString())
		//		if (result.ruleFailedException!=null) {
		//			result.ruleFailedException.printStackTrace
		//		}
		assertSubtype(result, true) // we can use g1 wherever we use g2!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g2, g1)
		assertSubtype(result, true) // and vice versa

		G = newRuleEnvironment(script);
		result = ts.subtype(G, ga, g1)

		//		println("Trace: " + trace.traceAsString())
		//		if (result.ruleFailedException!=null) {
		//			result.ruleFailedException.printStackTrace
		//		}
		assertSubtype(result, true) // we can use ga wherever we use g1!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g1, ga)

		//		println("Trace: " + trace.traceAsString())
		//		if (result.ruleFailedException!=null) {
		//			result.ruleFailedException.printStackTrace
		//		}
		assertSubtype(result, false) // we cannot use g1 wherever we use ga!

		G = newRuleEnvironment(script);
		result = ts.subtype(G, g2, g1)
		assertSubtype(result, true) // and vice versa

		// but the last line should create an error:
		val issues = script.validate();
		assertIssueCount(0, issues);

	}

	@Test
	def void testParameterizedClassFunctionReturnType() {
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class G<T>{
					get(): T { return null;}

				}
				var ga: G<A>;
				var a: A;
				a = ga.get();
			''');

		var G = newRuleEnvironment(script);
		val assignment = (script.scriptElements.get(4) as ExpressionStatement).expression as AssignmentExpression;

		val typeLeft = ts.type(G, assignment.lhs);
		val typeRight = ts.type(G, assignment.rhs);
		assertType(typeRight, (typeLeft as ParameterizedTypeRef).declaredType);

		// but the last line should create an error:
		val issues = script.validate();
		assertIssueCount(0, issues);
	}

	@Test
	def void testParameterizedClassFunctionFparType() {
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class G<T>{
					set(t: T): void {}

				}
				var ga: G<A>;
				var a: A;
				ga.set(a);
			''');

		var G = newRuleEnvironment(script);
		val call = (script.scriptElements.get(4) as ExpressionStatement).expression as ParameterizedCallExpression;

		val arg0 = call.arguments.head;
		val expectedType = ts.expectedType(G, arg0, arg0.expression);
		val actualType = ts.type(G, arg0.expression);

		assertType(expectedType, (actualType as ParameterizedTypeRef).declaredType);

		// but the last line should create an error:
		val issues = script.validate();
		assertIssueCount(0, issues);
	}

	@Test
	def void testNestedParameterizedClassFunctionReturnType() {
		val script = createAndValidateScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class G<T>{
					get(): G<T> { return null;}

				}
				var ga: G<A>;
				var a: A;
				ga = ga.get();
			''');

		var G = newRuleEnvironment(script);
		val assignment = (script.scriptElements.get(4) as ExpressionStatement).expression as AssignmentExpression;

		val typeLeft = ts.type(G, assignment.lhs);
		val typeRight = ts.type(G, assignment.rhs);
		assertType(typeRight, (typeLeft as ParameterizedTypeRef).declaredType);

		// but the last line should create an error:
		val issues = script.validate();
		assertIssueCount(0, issues);
	}

	@Test
	def void testNested2ParameterizedClassFunctionReturnType() {
		val script = createScript(JavaScriptVariant.n4js,
			'''
				class A{}
				class G<T>{
					get(): G<G<T>> { return null;}
					foo(): G<T> { return null; }
				}
				var ga: G<A>;
				var a: A;
				ga = ga.get().foo();
			''');

		val assignment = (script.scriptElements.get(4) as ExpressionStatement).expression as AssignmentExpression;

		"G<A>".assertTypeName(assignment.lhs);
		"G<G<A>>".assertTypeName(assignment.rhs);

		// but the last line should create an error:
		val issues = script.validate();

		// the error is due to ga.get().foo() being of type G<G<A>>
		// which cannot be assigned to G<A>
		assertIssueCount(1, issues);
	}

	@Test
	def void testSubtypeWithExistentialTypesAndVariance() {

		// class A{}
		val script = createScript(JavaScriptVariant.n4js,
			'''
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
			''')

		var G = newRuleEnvironment(script);
		val call = (script.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression;

		val arg0 = call.arguments.head;
		val expectedType = ts.expectedType(G, arg0, arg0.expression);
		val actualType = ts.type(G, arg0.expression);

		val result = ts.subtype(G, actualType, expectedType);
		assertTrue(result.failure);

		val issues = script.validate();
		assertIssueCount(6, issues);
	}

	@Test
	def void testUnboundTypeVariableInsideGenerics() {
		'''
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
		'''.assertValidationErrors("")
	}

	@Test
	def void testUnboundTypeVariableInsideGenerics2() {
		'''
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
		'''.assertValidationErrors("A is not a subtype of T.")
	}

	@Test
	def void testUnboundTypeVariableInsideGenerics3() {
		'''
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
		'''.assertValidationErrors(
'''
T is not a subtype of B.
A is not a subtype of T.
'''
		)
	}

	def private assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		val script = createScript(JavaScriptVariant.n4js,input.toString)
		val issues = script.validate();
		issues.assertErrorMessages(expectedErrors)
	}
}
