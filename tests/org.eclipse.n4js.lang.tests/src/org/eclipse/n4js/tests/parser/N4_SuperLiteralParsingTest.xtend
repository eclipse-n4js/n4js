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
package org.eclipse.n4js.tests.parser

import org.junit.Test

/**
 */
class N4_SuperLiteralParsingTest extends AbstractParserTest {

	@Test
	def void testSuperLiteralInScript_01() {
		val script = '''
			super;
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInScript_02() {
		val script = '''
			super();
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInScript_03() {
		val script = '''
			super.m();
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInBlock_01() {
		val script = '''
			{ super; }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInBlock_02() {
		val script = '''
			{ super(); }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInBlock_03() {
		val script = '''
			{ super.m(); }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInObjectLiteral_01() {
		val script = '''
			{ a: super; }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInObjectLiteral_02() {
		val script = '''
			{ a: super(); }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInObjectLiteral_03() {
		val script = '''
			{ a: super.m(); }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInFunction_01() {
		val script = '''
			(function() { super; })
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInFunction_02() {
		val script = '''
			(function() { super(); })
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInFunction_03() {
		val script = '''
			(function() { super.m(); })
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInMethod_01() {
		val script = '''
			class C { m() { super; } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
		val msg = errors.head.message
		// TODO wording could be better
		assertEquals('Super member access requires a declared super type.', msg)
	}

	@Test
	def void testSuperLiteralInMethod_02() {
		val script = '''
			class C { m() { super(); } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInMethod_03() {
		'''
			class C { m() { super.a(); } }
		'''.parseESSuccessfully
	}

	@Test
	def void testSuperLiteralInConstructor_01() {
		val script = '''
			class C { constructor() { super; } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
		val msg = errors.head.message
		// TODO wording could be better
		assertEquals('Super member access requires a declared super type.', msg)
	}

	@Test
	def void testSuperLiteralInConstructor_02() {
		val script = '''
			class C { constructor() { super(); } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
		val msg = errors.head.message
		// TODO wording could be better
		assertEquals('Super calls may only be used in constructors.', msg)
	}

	@Test
	def void testSuperLiteralInConstructor_03() {
		'''
			class C { constructor() { super.a(); } }
		'''.parseESSuccessfully
	}

	@Test
	def void testSuperLiteralInInterface_01() {
		val script = '''
			interface C { m() { super; } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInInterface_02() {
		val script = '''
			interface C { m() { super(); } }
		'''.parseESWithError

		val errors = script.eResource.errors
		assertEquals(errors.toString, 1, errors.size)
	}

	@Test
	def void testSuperLiteralInInterface_03() {
		'''
			interface C { m() { super.a(); } }
		'''.parseESSuccessfully
	}
}
