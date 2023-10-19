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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.n4js.n4JS.N4JSASTUtils.getContainingFunction;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for this scoping, combined with type system test.
 *
 * <p>
 * Note: In this test, the this literal needs to be followed by a property access in order to force the scoping to
 * actually create the type. That is, we use "this.s" instead of "this".
 * </p>
 *
 * @see AT_185_ThisScopingTest
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class ThisScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	N4JSTypeSystem ts;

	void assertThisLiteralBindingToObjectLiteral(CharSequence scriptSrc, boolean expectedErrors) {
		Script script = null;
		try {
			script = parseHelper.parse(scriptSrc);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return;
		}
		valTestHelper.validate(script);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		for (ObjectLiteral objectLiteral : EcoreUtil2.eAllOfType(script, ObjectLiteral.class)) {
			Type objectLiteralType = objectLiteral.getDefinedType();

			for (ThisLiteral thisLiteral : toList(
					filter(EcoreUtilN4.getAllContentsFiltered(objectLiteral, (ol -> !(ol instanceof ObjectLiteral))),
							ThisLiteral.class))) {
				assertNotNull("No type derived from object literal", objectLiteralType);
				TypeRef boundType = ts.type(G, thisLiteral);
				if (getContainingFunction(thisLiteral) != null &&
						!(getContainingFunction(thisLiteral).eContainer() instanceof PropertyAssignment)) { // nested
																											// function

					assertEquals("nested", getContainingFunction(thisLiteral).getName());
					assertNotEquals(objectLiteralType,
							boundType instanceof StructuralTypeRef ? ((StructuralTypeRef) boundType).getStructuralType()
									: null); // global or undef, not the object literal
				} else {
					assertTrue(boundType instanceof StructuralTypeRef);
					assertEquals(objectLiteralType, ((StructuralTypeRef) boundType).getStructuralType());
				}
			}
		}

		if (!expectedErrors) {
			valTestHelper.assertNoErrors(script);
		}
	}

	void assertThisLiteralBindingToClass(CharSequence scriptSrc, boolean expectedErrors) {
		Script script = null;
		try {
			script = parseHelper.parse(scriptSrc);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return;
		}
		valTestHelper.validate(script);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		for (N4ClassifierDeclaration classifierDecl : EcoreUtil2.eAllOfType(script, N4ClassifierDeclaration.class)) {
			Type classifierType = classifierDecl.getDefinedType();

			for (ThisLiteral thisLiteral : toList(
					filter(EcoreUtilN4.getAllContentsFiltered(classifierDecl,
							cd -> !(cd instanceof N4ClassifierDeclaration)),
							ThisLiteral.class))) {

				assertNotNull("No type defined for classifier", classifierType);
				TypeRef boundType = ts.type(G, thisLiteral);
				assertNotNull(boundType);
				if (getContainingFunction(thisLiteral) != null &&
						!(getContainingFunction(thisLiteral).eContainer() instanceof N4ClassifierDeclaration)) { // nested
																													// function

					assertEquals("nested", getContainingFunction(thisLiteral).getName());
					assertNotEquals(classifierType, boundType); // global or undef, not the object literal
				} else {
					TypeRef upperBoundResult = ts.upperBoundWithReopen(G, boundType);
					assertNotNull(
							"Cannot retrieve upper bound of this type ref: " + boundType,
							upperBoundResult);
					TypeRef upperBound = upperBoundResult;
					assertTrue(upperBound instanceof ParameterizedTypeRef);
					assertEquals(classifierType, ((ParameterizedTypeRef) upperBound).getDeclaredType());
				}
			}
		}
		if (!expectedErrors) {
			valTestHelper.assertNoErrors(script);
		}
	}

	@Test
	public void testThisLiteralInFunction() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						var ol = {
							s: "hello",
							f: function() {
								this.s;
							}
						}
						""", false);
	}

	@Test
	public void testThisLiteralInGetter() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						var ol = {
							s: "hello",
							get x() {
								this.s;
								return null;
							}
						}
						""", false);
	}

	@Test
	public void testThisLiteralInSetter() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						var ol = {
							s: "hello",
							set x(y) {
								this.s;
							}
						}
						""", false);
	}

	@Test
	public void testThisLiteralInFunctionWitNestedFunction() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						var ol = {
							s: "hello",
							f: function() {
								this.s; // force object literal type creation on demand
								var g = function nested () {
									this.s;
								};
							}
						}
						""", true);
	}

	@Test
	public void testThisLiteralInFunctionNestedOL() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						var ol = {
							s: {
								s:"Hello",
								f: function() {
									this.s;
								}
							}
						}
						""", false);
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	public void testThisLiteralInFunctionWithAnnotation() {
		assertThisLiteralBindingToObjectLiteral(
				"""
						class A { s: string; }
						@This(A) function f() { this.s }
						""", false);
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	public void testThisLiteralInMethod() {
		assertThisLiteralBindingToClass(
				"""
						class A {
							s: string;
							foo() { this.s }
						}
						""", false);
	}

	/**
	 * Also see AcceptanceTest_260_TypeRefInAnnotationArg
	 */
	@Test
	public void testThisLiteralInMethod2() {
		assertThisLiteralBindingToClass(
				"""
						class A extends B {
							foo() { this.s }
						}
						class B {
							s: string;
						}
						""", false);
	}

	@Test
	public void testThisLiteralInMethodWithRoles() {
		assertThisLiteralBindingToClass(
				"""
						interface A {
							s: string;
							foo() { this.s }
						}
						""", false);
	}

	@Test
	public void testThisLiteralInMethodWithRoles2() {
		assertThisLiteralBindingToClass(
				"""
						interface B {
							s: string;
						}
						interface A extends B {
							foo() { this.s }
						}
						""", false);
	}

}
