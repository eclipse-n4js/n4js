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

import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.VariableStatement
import org.junit.Test

class ES_12_02_VariableDeclarationEsprimaTest extends AbstractParserTest {

	@Test
	def void testSimpleIdentifier() {
		val script = 'var x'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('x', declaration.name)
	}

	@Test
	def void testEscapedIdentifier() {
		val script = 'var \\u006F'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('o', declaration.name)
	}

	@Test
	def void testBrokenEscapedIdentifier_01() {
		val script = 'var \\u06F'.parseESWithError
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('u06F', declaration.name)
	}

	@Test
	def void testBrokenEscapedIdentifier_02() {
		val script = 'var \\u06Fh'.parseESWithError
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('u06Fh', declaration.name)
	}

	@Test
	def void testTwoDeclarations() {
		val script = 'var x, y;'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(2, statement.varDecl.size)
		val first = statement.varDecl.head
		assertEquals('x', first.name)
		val second = statement.varDecl.last
		assertEquals('y', second.name)
	}

	@Test
	def void testWithInitializer() {
		val script = 'var x = 42'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val declaration = statement.varDecl.head
		assertEquals('x', declaration.name)
		val number = declaration.expression as IntLiteral
		assertEquals(42, number.toInt)
	}

	@Test
	def void testTwoInitializer() {
		val script = 'var eval = 42, arguments = 23'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		val first = statement.varDecl.head
		assertEquals('eval', first.name)
		val firstNumber = first.expression as IntLiteral
		assertEquals(42, firstNumber.toInt)
		val second = statement.varDecl.last
		assertEquals('arguments', second.name)
		val secondNumber = second.expression as IntLiteral
		assertEquals(23, secondNumber.toInt)
	}

	@Test
	def void testThreeInitializer() {
		val script = 'var x = 14, y = 3, z = 1977'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(3, statement.varDecl.size)
		val first = statement.varDecl.head
		assertEquals('x', first.name)
		val firstNumber = first.expression as IntLiteral
		assertEquals(14, firstNumber.toInt)
		val second = statement.varDecl.get(1)
		assertEquals('y', second.name)
		val secondNumber = second.expression as IntLiteral
		assertEquals(3, secondNumber.toInt)
		val third = statement.varDecl.last
		assertEquals('z', third.name)
		val thirdNumber = third.expression as IntLiteral
		assertEquals(1977, thirdNumber.toInt)
	}

	@Test
	def void testKeywordsAsIdentifier_01() {
		val script = 'var implements, interface, package'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(3, statement.varDecl.size)
		val first = statement.varDecl.head
		assertEquals('implements', first.name)
		val second = statement.varDecl.get(1)
		assertEquals('interface', second.name)
		val third = statement.varDecl.last
		assertEquals('package', third.name)
	}

	@Test
	def void testKeywordsAsIdentifier_02() {
		val script = 'var private, protected, public, static'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(4, statement.varDecl.size)
		val first = statement.varDecl.head
		assertEquals('private', first.name)
		val second = statement.varDecl.get(1)
		assertEquals('protected', second.name)
		val third = statement.varDecl.get(2)
		assertEquals('public', third.name)
		val forth = statement.varDecl.last
		assertEquals('static', forth.name)
	}

	@Test
	def void testUnicodeName() {
		val script = 'var ಠ_ಠ'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(1, statement.varDecl.size)
		val ಠ_ಠ = statement.varDecl.head
		assertEquals('ಠ_ಠ', ಠ_ಠ.name)
	}

	@Test
	def void testCircularReference() {
		val script = 'var v0 = v0;'.parseESSuccessfully
		val statement = script.scriptElements.head as VariableStatement
		assertEquals(1, statement.varDecl.size)
		val v0 = statement.varDecl.head
		assertEquals('v0', v0.name)
		val initializer = v0.expression as IdentifierRef
		assertEquals('v0', initializer.idAsText)
		assertEquals(v0.definedVariable, initializer.id)
	}
}
