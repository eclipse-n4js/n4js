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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.junit.Test;

public class ES_11_06_AdditiveOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testAdd() {
		Script program = parseESSuccessfully("x + y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		AdditiveOperator op = expression.getOp();
		assertEquals(AdditiveOperator.ADD, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testSub() {
		Script program = parseESSuccessfully("x - y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		AdditiveOperator op = expression.getOp();
		assertEquals(AdditiveOperator.SUB, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testAddString() {
		Script program = parseESSuccessfully("\"use strict\" + y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		AdditiveOperator op = expression.getOp();
		assertEquals(AdditiveOperator.ADD, op);

		StringLiteral left = (StringLiteral) expression.getLhs();
		assertEquals("\"use strict\"", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 *		x + y + z:
	 *      +
	 *     / \
	 *    +   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testAddAdd() {
		Script program = parseESSuccessfully("x + y + z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		AdditiveExpression expression = (AdditiveExpression) statement.getExpression();
		AdditiveOperator op = expression.getOp();
		IdentifierRef rightTop = (IdentifierRef) expression.getRhs();
		assertEquals("z", getText(rightTop));
		assertEquals(AdditiveOperator.ADD, op);

		AdditiveExpression left = (AdditiveExpression) expression.getLhs();
		AdditiveOperator leftOp = left.getOp();
		assertEquals(AdditiveOperator.ADD, leftOp);
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));
	}

}
