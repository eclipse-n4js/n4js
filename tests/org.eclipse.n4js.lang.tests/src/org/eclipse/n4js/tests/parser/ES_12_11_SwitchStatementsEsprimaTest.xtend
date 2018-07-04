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
import org.eclipse.n4js.n4JS.SwitchStatement
import org.junit.Test
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.BreakStatement
import org.eclipse.n4js.n4JS.ExpressionStatement

class ES_12_11_SwitchStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testSwitch_Min() {
		val script = 'switch (x) {}'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val switchStmt = script.scriptElements.head as SwitchStatement
		val identifier = switchStmt.expression as IdentifierRef
		assertEquals("x", identifier.text)

		assertNull(switchStmt.defaultClause)
		assertTrue(switchStmt.caseClauses.empty)
		assertTrue(switchStmt.cases.empty)
	}

	@Test
	def void testSwitch_Simple() {
		val script = 'switch (answer) { case 42: hi(); break; }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val switchStmt = script.scriptElements.head as SwitchStatement
		val identifier = switchStmt.expression as IdentifierRef
		assertEquals("answer", identifier.text)

		assertNull(switchStmt.defaultClause)
		assertEquals(1, switchStmt.caseClauses.size)

		val caseClause = switchStmt.caseClauses.head
		assertEquals("42", (caseClause.expression as NumericLiteral).text);
		val stmts = caseClause.statements;
		assertEquals(2, stmts.size)
		val call = (stmts.get(0) as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("hi", (call.target as IdentifierRef).text)
		assertTrue(call.arguments.empty)
		val breakStmt = stmts.get(1) as BreakStatement
		assertNull(breakStmt.label)
	}

	@Test
	def void testSwitch_SimpleWithDefault() {
		val script = 'switch (answer) { case 42: hi(); break; default: break }'.parseESSuccessfully
		assertEquals(1, script.scriptElements.size)
		val switchStmt = script.scriptElements.head as SwitchStatement
		val identifier = switchStmt.expression as IdentifierRef
		assertEquals("answer", identifier.text)

		assertEquals(2, switchStmt.cases.size)

		val caseClause = switchStmt.caseClauses.get(0)
		assertEquals("42", (caseClause.expression as NumericLiteral).text);
		val stmts = caseClause.statements;
		assertEquals(2, stmts.size)
		val call = (stmts.get(0) as ExpressionStatement).expression as ParameterizedCallExpression
		assertEquals("hi", (call.target as IdentifierRef).text)
		assertTrue(call.arguments.empty)
		val breakStmt = stmts.get(1) as BreakStatement
		assertNull(breakStmt.label)

		val defaultClause = switchStmt.cases.get(1)
		val breakStmt2 = defaultClause.statements.get(0) as BreakStatement
		assertNull(breakStmt2.label)

		assertEquals(switchStmt.defaultClause, defaultClause)
	}

}
