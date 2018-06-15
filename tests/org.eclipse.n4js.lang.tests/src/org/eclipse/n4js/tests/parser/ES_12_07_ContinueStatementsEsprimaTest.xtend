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

import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.ContinueStatement
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.WhileStatement
import org.junit.Test

class ES_12_07_ContinueStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testContinue_Simple() {
		val script = 'while (true) { continue; }'.parseESSuccessfully
		val statement = script.scriptElements.head as WhileStatement

		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
		val block = statement.statement as Block
		assertEquals(1, block.statements.size)
		val cont = block.statements.get(0) as ContinueStatement
		assertNull(cont.label)
	}

	@Test
	def void testContinue_SimpleASI() {
		val script = 'while (true) { continue }'.parseESSuccessfully
		val statement = script.scriptElements.head as WhileStatement

		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
		val block = statement.statement as Block
		assertEquals(1, block.statements.size)
		val cont = block.statements.get(0) as ContinueStatement
		assertNull(cont.label)
	}

	@Test
	def void testContinue_LabelASI() {
		val script = 'done: while (true) { continue done }'.parseESSuccessfully
		val labeledStatement = script.scriptElements.head as LabelledStatement
		val statement = labeledStatement.statement as WhileStatement

		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
		val block = statement.statement as Block
		assertEquals(1, block.statements.size)
		val cont = block.statements.get(0) as ContinueStatement
		assertEquals("done", cont.label.name)
	}

	@Test
	def void testContinue_Label() {
		val script = 'done: while (true) { continue done; }'.parseESSuccessfully
		val labeledStatement = script.scriptElements.head as LabelledStatement
		val statement = labeledStatement.statement as WhileStatement

		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
		val block = statement.statement as Block
		assertEquals(1, block.statements.size)
		val cont = block.statements.get(0) as ContinueStatement
		assertEquals("done", cont.label.name)
	}

	@Test
	def void testContinue_LabelProto() {
		val script = '__proto__: while (true) { continue __proto__; }'.parseESSuccessfully
		val labeledStatement = script.scriptElements.head as LabelledStatement
		val statement = labeledStatement.statement as WhileStatement

		val bool = statement.expression as BooleanLiteral
		assertEquals(true, bool.^true)
		val block = statement.statement as Block
		assertEquals(1, block.statements.size)
		val cont = block.statements.get(0) as ContinueStatement
		assertEquals("__proto__", cont.label.name)
	}

}
