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
package org.eclipse.n4js.tests.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.ts.types.TypeAccessModifier.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class AT_556_TypeAccessModifierTest {
	@Inject
	extension ParseHelper<Script>

	private def void typeIs(CharSequence script, TypeAccessModifier mod) {
		val parsed = script.parse
		val type = parsed.module.types.head
		Assert.assertEquals(mod, type.typeAccessModifier)
	}

	private def void varIs(CharSequence script, TypeAccessModifier mod) {
		val parsed = script.parse
		val variable = parsed.module.exportedVariables.head
		Assert.assertEquals(mod, variable?.typeAccessModifier)
	}

	private def void functionIs(CharSequence script, TypeAccessModifier mod) {
		val parsed = script.parse
		val function = parsed.module.types.head
		Assert.assertEquals(mod, function?.typeAccessModifier)
	}

	@Test
	def void test_privateType() {
		'class C {}'.typeIs(PRIVATE)
	}

	@Test
	def void test_projectType() {
		'export class C {}'.typeIs(PROJECT)
	}

	@Test
	def void test_explicitProjectType() {
		'export project class C {}'.typeIs(PROJECT)
	}

	@Test
	def void test_explicitPublicType() {
		'export @Internal public class C {}'.typeIs(PUBLIC_INTERNAL)
	}

	@Test
	def void test_explicitPublicApiType() {
		'export public class C {}'.typeIs(PUBLIC)
	}

	@Test
	def void test_privateVar() {
		'var any c'.varIs(null) // private vars are not exposed in the module
	}

	@Test
	def void test_projectVar() {
		'export var any c'.varIs(PROJECT)
	}

	@Test
	def void test_explicitProjectVar() {
		'export project var any c'.varIs(PROJECT)
	}

	@Test
	def void test_explicitPublicVar() {
		'export @Internal public var any c'.varIs(PUBLIC_INTERNAL)
	}

	@Test
	def void test_explicitPublicApiVar() {
		'export public var any c'.varIs(PUBLIC)
	}

	@Test
	def void test_privateFunction() {
		'function c() {}'.functionIs(PRIVATE)
	}

	@Test
	def void test_projectFunction() {
		'export function c() {}'.functionIs(PROJECT)
	}

	@Test
	def void test_explicitProjectFunction() {
		'export project function c() {}'.functionIs(PROJECT)
	}

	@Test
	def void test_explicitPublicFunction() {
		'export @Internal public function c() {}'.functionIs(PUBLIC_INTERNAL)
	}

	@Test
	def void test_explicitPublicApiFunction() {
		'export public function c() {}'.functionIs(PUBLIC)
	}
}
