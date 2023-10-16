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
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_10_BitwiseOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testAnd() {
		Script program = parseESSuccessfully("x & y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryBitwiseExpression expression = (BinaryBitwiseExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryBitwiseOperator.AND, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testOr() {
		Script program = parseESSuccessfully("x | y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryBitwiseExpression expression = (BinaryBitwiseExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryBitwiseOperator.OR, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testXOR() {
		Script program = parseESSuccessfully("x ^ y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryBitwiseExpression expression = (BinaryBitwiseExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryBitwiseOperator.XOR, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testXORWithoutSpace() {
		Script program = parseESSuccessfully("x^y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		BinaryBitwiseExpression expression = (BinaryBitwiseExpression) statement.getExpression();
		assertNotNull(expression);

		assertEquals(BinaryBitwiseOperator.XOR, expression.getOp());
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

}
