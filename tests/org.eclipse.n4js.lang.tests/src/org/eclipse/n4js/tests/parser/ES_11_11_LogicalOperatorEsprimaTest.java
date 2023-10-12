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

import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseOperator;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalOperator;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_11_LogicalOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testOr() {
		Script program = parseESSuccessfully("x || y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryLogicalOperator.OR, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testAnd() {
		Script program = parseESSuccessfully("x && y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryLogicalOperator.AND, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 *     x || y || z:
	 *      ||
	 *     / \
	 *    ||   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testOrOr() {
		Script program = parseESSuccessfully("x || y || z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertEquals(BinaryLogicalOperator.OR, expression.getOp());

		BinaryLogicalExpression left = (BinaryLogicalExpression) expression.getLhs();
		assertEquals(BinaryLogicalOperator.OR, expression.getOp());
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));

		IdentifierRef rightTop = (IdentifierRef) expression.getRhs();
		assertEquals("z", getText(rightTop));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 *     x && y && z:
	 *      &&
	 *     / \
	 *    &&   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testAndAnd() {
		Script program = parseESSuccessfully("x && y && z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertEquals(BinaryLogicalOperator.AND, expression.getOp());

		BinaryLogicalExpression left = (BinaryLogicalExpression) expression.getLhs();
		assertEquals(BinaryLogicalOperator.AND, left.getOp());
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));

		IdentifierRef rightTop = (IdentifierRef) expression.getRhs();
		assertEquals("z", getText(rightTop));
	}

	/***
	 * Expression is hung up left. That is
	 *
	 * <pre>
	 *
	 *.     x || y && z:
	 *      ||
	 *     / \
	 *    x   &&
	 *        / \
	 *       y   z
	 * </pre>
	 */
	@Test
	public void testOrAnd() {
		Script program = parseESSuccessfully("x || y && z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertEquals(BinaryLogicalOperator.OR, expression.getOp());
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		BinaryLogicalExpression rightTop = (BinaryLogicalExpression) expression.getRhs();
		assertEquals(BinaryLogicalOperator.AND, rightTop.getOp());
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

	/***
	 * Expression is hung up left. That is
	 *
	 * <pre>
	 *
	 *      x || y ^ z:
	 *      ||
	 *     / \
	 *    x   ^
	 *       / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testOrXor() {
		Script program = parseESSuccessfully("x || y ^ z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) statement.getExpression();
		assertEquals(BinaryLogicalOperator.OR, expression.getOp());
		IdentifierRef leftTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(leftTop));

		BinaryBitwiseExpression rightTop = (BinaryBitwiseExpression) expression.getRhs();
		assertEquals(BinaryBitwiseOperator.XOR, rightTop.getOp());
		IdentifierRef leftBot = (IdentifierRef) rightTop.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) rightTop.getRhs();
		assertEquals("z", getText(rightBot));
	}

}
