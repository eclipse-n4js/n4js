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

import org.eclipse.n4js.n4JS.BreakStatement
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.LabelledStatement
import org.eclipse.n4js.n4JS.WhileStatement
import org.junit.Test

class ES_12_12_LabelledStatementEsprimaTest extends AbstractParserTest {

	@Test
	def void testFor() {
		val program = 'start: for (;;) break start'.parseSuccessfully
		val statement = program.scriptElements.head as LabelledStatement
		val nested = statement.statement as ForStatement
		assertEquals('start', statement.name)
		val break = nested.statement as BreakStatement
		assertSame(statement, break.label)
	}

	@Test
	def void testWhile() {
		val program = 'start: while (true) break start'.parseSuccessfully
		val statement = program.scriptElements.head as LabelledStatement
		val nested = statement.statement as WhileStatement
		assertEquals('start', statement.name)
		val break = nested.statement as BreakStatement
		assertSame(statement, break.label)
	}

	@Test
	def void testExpression() {
		val program = '__proto__: test'.parseSuccessfully
		val statement = program.scriptElements.head as LabelledStatement
		val nested = statement.statement as ExpressionStatement
		assertNotNull(nested)
	}

}
