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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.n4JS.N4JSASTUtils.*

/**
 * Tests for this scoping, combined with type system test.
 *
 * <p>Note: In this test, the this literal needs to be followed by a property access in order
 * to force the scoping to actually create the type. That is, we use "this.s" instead of "this".</p>
 *
 * @see AT_185_ThisScopingTest
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ThisScopingTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper
	@Inject N4JSTypeSystem ts;

	def void assertThisLiteralBindingToObjectLiteral(CharSequence scriptSrc, boolean expectedErrors) {
		val script = scriptSrc.parse
		script.validate;

		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		EcoreUtil2.eAllOfType(script, ObjectLiteral).forEach [ objectLiteral |
			val objectLiteralType = objectLiteral.definedType
			EcoreUtilN4.getAllContentsFiltered(objectLiteral, [! (it instanceof ObjectLiteral)]).filter(ThisLiteral).
				forEach [ thisLiteral |
					assertNotNull("No type derived from object literal", objectLiteralType)
					val boundType = ts.type(G, thisLiteral)
					if (thisLiteral.containingFunction !== null &&
						! (thisLiteral.containingFunction.eContainer instanceof PropertyAssignment)) { // nested function
						assertEquals("nested", thisLiteral.containingFunction.name)
						assertNotEquals(objectLiteralType, if(boundType instanceof StructuralTypeRef) boundType.structuralType else null); // global or undef, not the object literal
					} else {
						assertTrue(boundType instanceof StructuralTypeRef)
						assertEquals(objectLiteralType, (boundType as StructuralTypeRef).structuralType);
					}
				]
		]

		if (!expectedErrors) {
			script.assertNoErrors
		}
	}

	def void assertThisLiteralBindingToClass(CharSequence scriptSrc, boolean expectedErrors) {
		val script = scriptSrc.parse
		script.validate;

		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		EcoreUtil2.eAllOfType(script, N4ClassifierDeclaration).forEach [ classifierDecl |
			val classifierType = classifierDecl.definedType
			EcoreUtilN4.getAllContentsFiltered(classifierDecl, [! (it instanceof N4ClassifierDeclaration)]).filter(
				ThisLiteral).forEach [ thisLiteral |
				assertNotNull("No type defined for classifier", classifierType)
				val boundType = ts.type(G, thisLiteral);
				assertNotNull(boundType);
				if (thisLiteral.containingFunction !== null &&
					! (thisLiteral.containingFunction.eContainer instanceof N4ClassifierDeclaration)) { // nested function
					assertEquals("nested", thisLiteral.containingFunction.name)
					assertNotEquals(classifierType, boundType); // global or undef, not the object literal
				} else {
					val upperBoundResult = ts.upperBound(G, boundType);
					assertNotNull(
						"Cannot retrieve upper bound of this type ref: " + upperBoundResult.failureMessage,
						upperBoundResult.value
					);
					val upperBound = upperBoundResult.value;
					assertTrue(upperBound instanceof ParameterizedTypeRef)
					assertEquals(classifierType, (upperBound as ParameterizedTypeRef).declaredType);
				}
			]
		]

		if (!expectedErrors) {
			script.assertNoErrors
		}
	}

	@Test
	def void testThisLiteralInFunction() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				var ol = {
					s: "hello",
					f: function() {
						this.s;
					}
				}
			''', false)
	}

	@Test
	def void testThisLiteralInGetter() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				var ol = {
					s: "hello",
					get x() {
						this.s;
						return null;
					}
				}
			''', false)
	}

	@Test
	def void testThisLiteralInSetter() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				var ol = {
					s: "hello",
					set x(y) {
						this.s;
					}
				}
			''', false)
	}

	@Test
	def void testThisLiteralInFunctionWitNestedFunction() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				var ol = {
					s: "hello",
					f: function() {
						this.s; // force object literal type creation on demand
						var g = function nested () {
							this.s;
						};
					}
				}
			''', true)
	}

	@Test
	def void testThisLiteralInFunctionNestedOL() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				var ol = {
					s: {
						s:"Hello",
						f: function() {
							this.s;
						}
					}
				}
			''', false)
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	def void testThisLiteralInFunctionWithAnnotation() {
		assertThisLiteralBindingToObjectLiteral(
			'''
				class A { s: string; }
				@This(A) function f() { this.s }
			''', false)
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	def void testThisLiteralInMethod() {
		assertThisLiteralBindingToClass(
			'''
				class A {
					s: string;
					foo() { this.s }
				}
			''', false)
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	def void testThisLiteralInMethod2() {
		assertThisLiteralBindingToClass(
			'''
				class A extends B {
					foo() { this.s }
				}
				class B {
					s: string;
				}
			''', false)
	}

	@Test
	def void testThisLiteralInMethodWithRoles() {
		assertThisLiteralBindingToClass(
			'''
				interface A {
					s: string;
					foo() { this.s }
				}
			''', false)
	}

	@Test
	def void testThisLiteralInMethodWithRoles2() {
		assertThisLiteralBindingToClass(
			'''
				interface B {
					s: string;
				}
				interface A extends B {
					foo() { this.s }
				}
			''', false)
	}

}
