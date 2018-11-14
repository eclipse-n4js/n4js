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
import java.util.Arrays
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
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
		script.assertErrors(
			"ERROR:string is not a subtype of String. (__synthetic0.n4js line : 1 column : 17)"
		)
	}

	@Test
	def void test_07() {
		val script = '''
			var b: Boolean = true
		'''.parse
		script.assertErrors(
			"ERROR:boolean is not a subtype of Boolean. (__synthetic0.n4js line : 1 column : 18)"
		)
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
		script.assertErrors(
			"ERROR:int is not a subtype of Number. (__synthetic0.n4js line : 1 column : 17)"
		)
	}

	def private void assertErrors(EObject model, String... errorMsgs) {
		val issues = model.eResource.validate;
		Assert.assertEquals(issues.map[toString], Arrays.asList(errorMsgs));
	}
}
