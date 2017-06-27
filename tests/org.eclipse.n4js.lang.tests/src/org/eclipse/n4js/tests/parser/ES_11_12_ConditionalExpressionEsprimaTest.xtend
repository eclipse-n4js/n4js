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

import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.BinaryLogicalOperator
import org.eclipse.n4js.n4JS.ConditionalExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.NumericLiteral
import org.junit.Test

class ES_11_12_ConditionalExpressionEsprimaTest extends AbstractParserTest {

	@Test
	def void testConditional_1() {
		val program = 'y ? 1 : 2'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val cond = statement.expression as ConditionalExpression
		assertNotNull(cond)

		val expression = cond.expression as IdentifierRef
		assertEquals('y', expression.text)
		val trueExpr = cond.trueExpression as NumericLiteral
		assertEquals('1', trueExpr.text)
		val falseExpr = cond.falseExpression as NumericLiteral
		assertEquals('2', falseExpr.text)
	}

	@Test
	def void testConditional_2() {
		val program = 'x && y ? 1 : 2'.parseSuccessfully
		val statement = program.scriptElements.head as ExpressionStatement
		val cond = statement.expression as ConditionalExpression
		assertNotNull(cond)

		val expression = cond.expression as BinaryLogicalExpression;
		assertEquals(BinaryLogicalOperator.AND, expression.op)
		assertEquals('x', (expression.lhs as IdentifierRef).text);
		assertEquals('y', (expression.rhs as IdentifierRef).text);
		val trueExpr = cond.trueExpression as NumericLiteral
		assertEquals('1', trueExpr.text)
		val falseExpr = cond.falseExpression as NumericLiteral
		assertEquals('2', falseExpr.text)
	}

}
