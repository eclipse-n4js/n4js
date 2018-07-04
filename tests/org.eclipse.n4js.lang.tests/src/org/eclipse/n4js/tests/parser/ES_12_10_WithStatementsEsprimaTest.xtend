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

import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.WithStatement
import org.junit.Test

class ES_12_10_WithStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testWith_SimpleASI() {
		val script = 'with (x) foo = bar'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val withStmt = script.scriptElements.head as WithStatement
		val identifier = withStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)

		val expressionStatement = withStmt.statement as ExpressionStatement

		val expression = expressionStatement.expression as AssignmentExpression
		val left = expression.lhs as IdentifierRef
		assertEquals("foo", left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals("bar", right.text)
	}

	@Test
	def void testWith_Simple() {
		val script = 'with (x) foo = bar;'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val withStmt = script.scriptElements.head as WithStatement
		val identifier = withStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)

		val expressionStatement = withStmt.statement as ExpressionStatement

		val expression = expressionStatement.expression as AssignmentExpression
		val left = expression.lhs as IdentifierRef
		assertEquals("foo", left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals("bar", right.text)
	}

	@Test
	def void testWith_Block() {
		val script = 'with (x) { foo = bar }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val withStmt = script.scriptElements.head as WithStatement
		val identifier = withStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)

		val block = withStmt.statement as Block
		assertEquals(1, block.statements.size)

		val expressionStatement = block.statements.head as ExpressionStatement
		val expression = expressionStatement.expression as AssignmentExpression
		val left = expression.lhs as IdentifierRef
		assertEquals("foo", left.text)
		val right = expression.rhs as IdentifierRef
		assertEquals("bar", right.text)
	}


}
