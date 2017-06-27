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
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ES_07_08_5_RegExParserTest extends Assert {
	@Inject
	extension ParseHelper<Script>

	def parseAsBinaryOp(CharSequence js) {
		val script = js.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertFalse(script.eAllContents.exists[ it instanceof RegularExpressionLiteral ])
		return script
	}

	def parseWithRegEx(CharSequence js) {
		val script = js.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertTrue(script.eResource.warnings.toString, script.eResource.warnings.empty)
		assertTrue(script.eAllContents.exists[ it instanceof RegularExpressionLiteral ])
		return script
	}

	def parseWithInvalidRegEx(CharSequence js) {
		val script = js.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertFalse(script.eResource.warnings.toString, script.eResource.warnings.empty)
		assertTrue(script.eAllContents.exists[ it instanceof RegularExpressionLiteral ])
		return script
	}

	def void failToParse(CharSequence js) {
		val script = js.parse
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	def void hasChildren(Script script, int count) {
		assertEquals(count, script.scriptElements.size)
	}

	@Test
	def void testRegExFirstToken() {
		"/1/".parseWithRegEx
	}
	@Test
	def void testRegExAfterComment_01() {
		"//\n/1/".parseWithRegEx
	}
	@Test
	def void testRegExAfterComment_02() {
		"/**//1/".parseWithRegEx
	}
	@Test
	def void testRegExAfterKeyword_01() {
		"null/a/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAfterKeyword_02() {
		"true/a/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAfterKeyword_03() {
		"false/a/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAfterKeyword_04() {
		"this/a/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAfterKeyword_05() {
		"from/as/get/set/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAccess_01() {
		"a/b/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAccess_02() {
		"a()/b/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAccess_03() {
		"a[b]/c/g".parseAsBinaryOp
	}
	@Test
	def void testRegExAsArgument_01() {
		"a[/c/]".parseWithRegEx
	}
	@Test
	def void testRegExAsArgument_02() {
		"a(/c/)".parseWithRegEx
	}
	@Test
	def void testRegExAsArgument_03() {
		"a +/c/".parseWithRegEx
	}
	@Test
	def void testRegExAsArgument_04() {
		"var e=/^/g;".parseWithRegEx
	}
	@Test
	def void testBinaryOpInParen_01() {
		"({}/function(){return 1})".parseAsBinaryOp
	}
	@Test
	def void testBinaryOpWithoutParen() {
		// TODO improve error message
		"{}/function(){return 1}".failToParse
	}

	@Test
	def void testRegExTwoBackspaces() {
		'''/\\/'''.parseWithRegEx
	}

	@Test
	def void testTwoOpeningBrackets() {
		'''/[[]/'''.parseWithRegEx
	}

	@Test
	def void testTwoClosingBrackets() {
		'''/[]]/'''.parseWithRegEx
	}

	@Test
	def void testUnclosedBrace() {
		'''/{/'''.parseWithRegEx
	}

	@Test
	def void testUnclosedBracket() {
		'''/[/'''.parseWithInvalidRegEx
	}

	@Test
	def void testUnclosedParentheses() {
		'''/(/'''.parseWithInvalidRegEx
	}

	@Test
	def void testObjectLiteral_01() {
		'''({}/s)'''.parseAsBinaryOp
	}

	@Test
	def void testObjectLiteral_02() {
		'''({})/s'''.parseAsBinaryOp
	}

	@Test
	def void tesEqualsSignInRegEx_01() {
		'''/=1/g'''.parseWithRegEx
	}

	@Test
	def void tesEqualsSignInRegEx_02() {
		'''i/=1/2'''.parseAsBinaryOp
	}
}
