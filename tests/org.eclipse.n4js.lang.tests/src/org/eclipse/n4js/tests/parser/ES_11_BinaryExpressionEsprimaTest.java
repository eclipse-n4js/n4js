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

import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.MultiplicativeOperator;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_BinaryExpressionEsprimaTest extends AbstractParserTest {

	/**
	 * Expression is hung up left due to operator precedence. That is
	 *
	 * <pre>
	 *     x + y * z:
	 *      +
	 *     / \
	 *    x   *
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testAddMul() {
		Script program = parseESSuccessfully("x + y * z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		MultiplicativeExpression rightTop = (MultiplicativeExpression) expression.getRhs();
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

	/**
	 * Expression is hung up left due to operator precedence. That is
	 *
	 * <pre>
	 *     x + y / z:
	 *      +
	 *     / \
	 *    x   /
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testAddDiv() {
		Script program = parseESSuccessfully("x + y / z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		MultiplicativeExpression rightTop = (MultiplicativeExpression) expression.getRhs();
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

	/**
	 * Expression is hung up left due to operator precedence. That is
	 *
	 * <pre>
	 *     x + y % z:
	 *      +
	 *     / \
	 *    x   %
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testAddMod() {
		Script program = parseESSuccessfully("x + y % z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		assertEquals(AdditiveOperator.ADD, expression.getOp());
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		MultiplicativeExpression rightTop = (MultiplicativeExpression) expression.getRhs();
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

	/**
	 * Expression is hung up left due to operator precedence. That is
	 *
	 * <pre>
	 *     x + y % z:
	 *      -
	 *     / \
	 *    x   %
	 *   	 / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testSubMod() {
		Script program = parseESSuccessfully("x - y % z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		assertEquals(AdditiveOperator.SUB, expression.getOp());
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		MultiplicativeExpression rightTop = (MultiplicativeExpression) expression.getRhs();
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 *     x * y + z:
	 *      +
	 *     / \
	 *    *   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testTimesAdd() {
		Script program = parseESSuccessfully("x * y + z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		AdditiveOperator op = expression.getOp();
		assertEquals(AdditiveOperator.ADD, op);

		MultiplicativeExpression left = (MultiplicativeExpression) expression.getLhs();
		MultiplicativeOperator leftOp = left.getOp();
		assertEquals(MultiplicativeOperator.TIMES, leftOp);
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));

		IdentifierRef rightTop = (IdentifierRef) expression.getRhs();
		assertEquals("z", getText(rightTop));
	}
}
