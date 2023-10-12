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

import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.MultiplicativeOperator;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_05_MultiplicativeOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testTimes() {
		Script program = parseESSuccessfully("x * y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		MultiplicativeExpression expression = (MultiplicativeExpression) statement.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.TIMES, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testDiv() {
		Script program = parseESSuccessfully("x / y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		MultiplicativeExpression expression = (MultiplicativeExpression) statement.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.DIV, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testMod() {
		Script program = parseESSuccessfully("x % y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		MultiplicativeExpression expression = (MultiplicativeExpression) statement.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.MOD, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 * 		x + y + z:
	 *      *
	 *     / \
	 *    *   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testTimesTimes() {
		Script program = parseESSuccessfully("x * y * z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		MultiplicativeExpression expression = (MultiplicativeExpression) statement.getExpression();
		MultiplicativeOperator op = expression.getOp();
		IdentifierRef rightTop = (IdentifierRef) expression.getRhs();
		assertEquals("z", getText(rightTop));
		assertEquals(MultiplicativeOperator.TIMES, op);

		MultiplicativeExpression left = (MultiplicativeExpression) expression.getLhs();
		MultiplicativeOperator leftOp = left.getOp();
		assertEquals(MultiplicativeOperator.TIMES, leftOp);
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));
	}

}
