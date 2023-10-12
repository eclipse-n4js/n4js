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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalOperator;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_12_ConditionalExpressionEsprimaTest extends AbstractParserTest {

	@Test
	public void testConditional_1() {
		Script program = parseESSuccessfully("y ? 1 : 2");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ConditionalExpression cond = (ConditionalExpression) statement.getExpression();
		assertNotNull(cond);

		IdentifierRef expression = (IdentifierRef) cond.getExpression();
		assertEquals("y", getText(expression));
		NumericLiteral trueExpr = (NumericLiteral) cond.getTrueExpression();
		assertEquals("1", getText(trueExpr));
		NumericLiteral falseExpr = (NumericLiteral) cond.getFalseExpression();
		assertEquals("2", getText(falseExpr));
	}

	@Test
	public void testConditional_2() {
		Script program = parseESSuccessfully("x && y ? 1 : 2");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ConditionalExpression cond = (ConditionalExpression) statement.getExpression();
		assertNotNull(cond);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) cond.getExpression();

		assertEquals(BinaryLogicalOperator.AND, expression.getOp());
		assertEquals("x", getText(expression.getLhs()));

		assertEquals("y", getText(expression.getRhs()));

		NumericLiteral trueExpr = (NumericLiteral) cond.getTrueExpression();
		assertEquals("1", getText(trueExpr));
		NumericLiteral falseExpr = (NumericLiteral) cond.getFalseExpression();
		assertEquals("2", getText(falseExpr));
	}

}
