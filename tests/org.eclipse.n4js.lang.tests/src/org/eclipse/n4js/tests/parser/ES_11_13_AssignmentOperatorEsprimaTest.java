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

import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_13_AssignmentOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testAssignment_01() {
		Script program = parseESSuccessfully("x = 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testAssignment_02() {
		Script program = parseESSuccessfully("eval = 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("eval", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testAssignment_03() {
		Script program = parseESSuccessfully("arguments = 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("arguments", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testMultAssignment() {
		Script program = parseESSuccessfully("x *= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.MUL_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testDivAssignment() {
		Script program = parseESSuccessfully("x /= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.DIV_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testModAssignment() {
		Script program = parseESSuccessfully("x %= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.MOD_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testAddAssignment() {
		Script program = parseESSuccessfully("x += 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.ADD_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testSubAssignment() {
		Script program = parseESSuccessfully("x -= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.SUB_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testLeftShiftAssignment() {
		Script program = parseESSuccessfully("x <<= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.SHL_ASSIGN, op);
	}

	@Test
	public void testRightShiftAssignment() {
		Script program = parseESSuccessfully("x >>= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.SHR_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testUnsignedRightShiftAssignment() {
		Script program = parseESSuccessfully("x >>>= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.USHR_ASSIGN, op);
	}

	@Test
	public void testAndAssignment() {
		Script program = parseESSuccessfully("x &= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.AND_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testXorAssignment() {
		Script program = parseESSuccessfully("x ^= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.XOR_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	@Test
	public void testOrAssignment() {
		Script program = parseESSuccessfully("x |= 42");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		assertEquals(AssignmentOperator.OR_ASSIGN, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		NumericLiteral right = (NumericLiteral) expression.getRhs();
		assertEquals("42", getText(right));
	}

	/**
	 * Assignment is hung up left. That is
	 *
	 * <pre>
	 *     x = y = z:
	 *      =
	 *     / \
	 *    x   =
	 *       / \
	 *      y   z
	 * </pre>
	 */
	@Test
	public void testAssignmentAssignment() {
		Script program = parseESSuccessfully("x = y = z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AssignmentExpression expression = (AssignmentExpression) statement.getExpression();
		AssignmentOperator op = expression.getOp();
		IdentifierRef rightTop = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(rightTop));
		assertEquals(AssignmentOperator.ASSIGN, op);

		AssignmentExpression right = (AssignmentExpression) expression.getRhs();
		AssignmentOperator rightOp = right.getOp();
		assertEquals(AssignmentOperator.ASSIGN, rightOp);
		IdentifierRef leftBot = (IdentifierRef) right.getLhs();
		assertEquals("y", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) right.getRhs();
		assertEquals("z", getText(rightBot));
	}
}
