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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ES_11_ExpressionParserTest extends Assert {

	@Inject
	extension ParseHelper<Script>

	@Test
	def void testPostfixExpression_01() {
		val program = '''
			var x = 1;
			x++;
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testPostfixExpression_02() {
		val program = '''
			var x = 1
			x++
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testPrefixExpression_01() {
		val program = '''
			var x = 1;
			++x;
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testPrefixExpression_02() {
		val program = '''
			var x = 1
			++x
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testPrefixExpression_03() {
		val program = '''
			var x = 1
			--x
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

	@Test
	def void testPrefixExpression_04() {
		val program = '''
			var x = 1
			~x
		'''.parse
		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)
	}

}
