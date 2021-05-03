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
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests for this handling undefined type.
 *
 * @see ThisScopingTest
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class UndefinedHandlingTest {

	@Inject extension N4JSParseHelper
	@Inject extension ValidationTestHelper
	@Inject N4JSTypeSystem ts


	@Test
	def void testGlobalObjectUndefined() {
		'''undefined.selector;'''.assertUndefined()
	}

	@Test
	def void testDeclaredUndefined() {
		'''
		var	x: undefined;
		x.selector;'''.assertUndefined()
	}

	@Test
	def void testStrictThisRef() {
		'''
		function f() {
			"use strict"
			this.selector;
		}
		'''.assertAny()
	}

	@Test
	def void testUnrestrictedThisRefToUndefined() {
		'''
		function f() {
			this.undefined;
		}
		'''.parse(JavaScriptVariant.unrestricted)
			.assertNoErrors(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				Diagnostic.LINKING_DIAGNOSTIC);
	}

	@Test
	@Ignore("see IDE-496")
	def void testUnrestrictedThisRef() {
		'''
		var selector
		function f() {
			this.selector;
		}
		'''.parse.assertNoErrors
	}

	def private void assertUndefined(CharSequence scriptSrc) {
		assertUndefinedOrAny(scriptSrc, false);
	}

	def private void assertAny(CharSequence scriptSrc) {
		assertUndefinedOrAny(scriptSrc, true);
	}

	def private void assertUndefinedOrAny(CharSequence scriptSrc, boolean useAny) {
		val script = scriptSrc.parse
		val access = script.eAllContents.filter(ParameterizedPropertyAccessExpression).head
		assertNotNull("bogus test, no property access found", access);
		val builtInTypeScope = BuiltInTypeScope.get(script.eResource.resourceSet);
		val type = if (useAny) builtInTypeScope.anyType else builtInTypeScope.undefinedType;
		assertNotNull("bogus test, no predefined any type found", type);
		val receiverType = ts.tau(access.target).declaredType;
		assertEquals("wrong type inferred", type, receiverType);
		assertTrue("property must not be bound", access.property.eIsProxy)

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				Diagnostic.LINKING_DIAGNOSTIC
		);
	}
}
