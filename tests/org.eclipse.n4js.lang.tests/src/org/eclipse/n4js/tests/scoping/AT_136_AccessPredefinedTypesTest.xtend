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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.n4JS.N4JSPackage

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_136_AccessPredefinedTypesTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			var s: string = ''
			s.toLowerCase().toLowerCase()
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_02() {
		val script = '''
			var b: boolean = true
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_03() {
		val script = '''
			var d: Date
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_04() {
		val script = '''
			var u: undefined // may be a type error later
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_05() {
		val script = '''
			var n: number = 1
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_06() {
		val script = '''
			var s: String = ''
			s.toLowerCase().toLowerCase()
		'''.parse
		script.assertError(N4JSPackage.Literals.STRING_LITERAL, "org.eclipse.n4js.xsemantics.SubtypeParameterizedTypeRef")
	}

	@Test
	def void test_07() {
		val script = '''
			var b: Boolean = true
		'''.parse
		script.assertError(N4JSPackage.Literals.BOOLEAN_LITERAL, "org.eclipse.n4js.xsemantics.SubtypeParameterizedTypeRef")
	}

	@Test
	def void test_08() {
		val script = '''
			var r: RegExp = /abc/g
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_09() {
		val script = '''
			var n: Number = 1
		'''.parse
		script.assertError(N4JSPackage.Literals.INT_LITERAL, "org.eclipse.n4js.xsemantics.SubtypeParameterizedTypeRef")
	}
}
