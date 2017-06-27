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
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.IntLiteral
import org.eclipse.n4js.n4JS.SwitchStatement
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.junit.Test

class ES_07_04_CommentParserEsprimaTest extends AbstractParserTest {

	@Inject extension IEObjectDocumentationProvider

	@Test
	def void testBlockComment_01() {
		val script = '/* block comment */ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
		assertNull("No jsdoc-style comment here.", i.documentation)
	}
	@Test
	def void testBlockComment_01_b() {
		val script = '/** jsdoc block comment */ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
		assertEquals("jsdoc block comment", i.documentation)
	}

	@Test
	def void testBlockComment_02() {
		val script = '42 /*The*/ /*Answer*/'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockComment_03() {
		val script = '42 /*The * Answer*/'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockComment_04() {
		val script = '/* multiline\ncomment\nshould\nbe\nignored */ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
		assertNull("simple multiline comment should be ignored", i.documentation)
	}
	@Test
	def void testBlockComment_04_b() {
		val script = '/** jsdoc multiline\ncomment\nshould\nbe\nignored */ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
		assertEquals("jsdoc multiline\ncomment\nshould\nbe\nignored", i.documentation)
	}

	@Test
	def void testBlockComment_05() {
		val script = '/*a\r\nb*/ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockComment_06() {
		val script = '/*a\rb*/ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockComment_07() {
		val script = '/*a\nb*/ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockComment_08() {
		val script = '/**/ 42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineComment_01() {
		val script = '// line comment\n42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineComment_02() {
		val script = '42// line comment'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineComment_03() {
		val script = '// Hello, world!\n42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineComment_04() {
		val script = '//\n42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineComment_05() {
		val script = '// Hello, world!\n\n//   Another hello\n42'.parseSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val i = statement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testLineCommentInBlock() {
		val script = 'if (x) { // Some comment\n42 }'.parseSuccessfully
		val statement = script.scriptElements.head as IfStatement
		val block = statement.ifStmt as Block
		val expressionStmt = block.statements.head as ExpressionStatement
		val i = expressionStmt.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

	@Test
	def void testBlockCommentInBlock() {
		val script = 'switch (answer) { case 42: /* perfect */ 42; }'.parseSuccessfully
		val statement = script.scriptElements.head as SwitchStatement
		val caseClause = statement.caseClauses.head
		val caseStatement = caseClause.statements.head as ExpressionStatement
		val i = caseStatement.expression as IntLiteral
		assertEquals(42, i.toInt)
	}

}
