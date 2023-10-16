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
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_08_RelationalOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testLessThan() {
		Script program = parseESSuccessfully("x < y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.LT, op);

		IdentifierRef left = (IdentifierRef) relational.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) relational.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testGreaterThan() {
		Script program = parseESSuccessfully("x > y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.GT, op);

		IdentifierRef left = (IdentifierRef) relational.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) relational.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testLessThanOrEqual() {
		Script program = parseESSuccessfully("x <= y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.LTE, op);

		IdentifierRef left = (IdentifierRef) relational.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) relational.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testGreaterThanOrEqual() {
		Script program = parseESSuccessfully("x >= y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.GTE, op);
	}

	@Test
	public void testIn() {
		Script program = parseESSuccessfully("x in y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.IN, op);

		IdentifierRef left = (IdentifierRef) relational.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) relational.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testInstanceOf() {
		Script program = parseESSuccessfully("x instanceof y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		assertEquals(RelationalOperator.INSTANCEOF, op);

		IdentifierRef left = (IdentifierRef) relational.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) relational.getRhs();
		assertEquals("y", getText(right));
	}

	/**
	 * Expression is hung up right. That is
	 *
	 * <pre>
	 *      x < y < z:
	 *      <
	 *     / \
	 *    <   z
	 *   / \
	 *  x   y
	 * </pre>
	 */
	@Test
	public void testLessThanLessThan() {
		Script program = parseESSuccessfully("x < y < z");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		RelationalExpression relational = (RelationalExpression) statement.getExpression();
		RelationalOperator op = relational.getOp();
		IdentifierRef rightTop = (IdentifierRef) relational.getRhs();
		assertEquals("z", getText(rightTop));
		assertEquals(RelationalOperator.LT, op);

		RelationalExpression left = (RelationalExpression) relational.getLhs();
		RelationalOperator leftOp = left.getOp();
		assertEquals(RelationalOperator.LT, leftOp);
		IdentifierRef leftBot = (IdentifierRef) left.getLhs();
		assertEquals("x", getText(leftBot));
		IdentifierRef rightBot = (IdentifierRef) left.getRhs();
		assertEquals("y", getText(rightBot));
	}
}
