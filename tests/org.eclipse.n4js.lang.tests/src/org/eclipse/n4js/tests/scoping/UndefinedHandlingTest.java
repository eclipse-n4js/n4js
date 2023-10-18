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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for this handling undefined type.
 *
 * @see ThisScopingTest
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class UndefinedHandlingTest {

	@Inject
	N4JSParseHelper parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	N4JSTypeSystem ts;

	@Test
	public void testGlobalObjectUndefined() throws Exception {
		assertUndefined("undefined.selector;");
	}

	@Test
	public void testDeclaredUndefined() throws Exception {
		assertUndefined("""
				var	x: undefined;
				x.selector;
				""");
	}

	@Test
	public void testStrictThisRef() throws Exception {
		assertAny("""
				function f() {
					"use strict"
					this.selector;
				}
				""");
	}

	@Test
	public void testUnrestrictedThisRefToUndefined() throws Exception {
		valTestHelper.assertNoErrors(parseHelper.parse("""
				function f() {
					this.undefined;
				}
				""", JavaScriptVariant.unrestricted), N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				Diagnostic.LINKING_DIAGNOSTIC);
	}

	@Test
	@Ignore("see IDE-496")
	public void testUnrestrictedThisRef() throws Exception {
		valTestHelper.assertNoErrors(parseHelper.parse("""
				var selector
				function f() {
					this.selector;
				}
				"""));
	}

	private void assertUndefined(CharSequence scriptSrc) throws Exception {
		assertUndefinedOrAny(scriptSrc, false);
	}

	private void assertAny(CharSequence scriptSrc) throws Exception {
		assertUndefinedOrAny(scriptSrc, true);
	}

	private void assertUndefinedOrAny(CharSequence scriptSrc, boolean useAny) throws Exception {
		Script script = parseHelper.parse(scriptSrc);
		ParameterizedPropertyAccessExpression access = head(
				filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));
		assertNotNull("bogus test, no property access found", access);
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(script.eResource().getResourceSet());
		Type type = useAny ? builtInTypeScope.getAnyType() : builtInTypeScope.getUndefinedType();
		assertNotNull("bogus test, no predefined any type found", type);
		Type receiverType = ts.tau(access.getTarget()).getDeclaredType();
		assertEquals("wrong type inferred", type, receiverType);
		assertTrue("property must not be bound", access.getProperty().eIsProxy());

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				Diagnostic.LINKING_DIAGNOSTIC);
	}
}
