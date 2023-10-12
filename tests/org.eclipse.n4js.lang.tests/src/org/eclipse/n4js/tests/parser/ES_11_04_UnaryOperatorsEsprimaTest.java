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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.junit.Test;

public class ES_11_04_UnaryOperatorsEsprimaTest extends AbstractParserTest {

	@Test
	public void testInc_01() {
		Script script = parseESSuccessfully("++x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testInc_02() {
		Script script = parseESSuccessfully("++eval");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("eval", getText(x));
	}

	@Test
	public void testInc_03() {
		Script script = parseESSuccessfully("++arguments");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("arguments", getText(x));
	}

	@Test
	public void testDec_01() {
		Script script = parseESSuccessfully("--x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.DEC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testDec_02() {
		Script script = parseESSuccessfully("--eval");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.DEC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("eval", getText(x));
	}

	@Test
	public void testDec_03() {
		Script script = parseESSuccessfully("--arguments");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.DEC, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("arguments", getText(x));
	}

	@Test
	public void testPos_01() {
		Script script = parseESSuccessfully("+x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.POS, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testNeg_01() {
		Script script = parseESSuccessfully("-x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.NEG, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testInv_01() {
		Script script = parseESSuccessfully("~x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INV, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testNot_01() {
		Script script = parseESSuccessfully("!x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.NOT, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testVoid_01() {
		Script script = parseESSuccessfully("void x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.VOID, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testDelete_01() {
		Script script = parseESSuccessfully("delete x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.DELETE, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testTypeof_01() {
		Script script = parseESSuccessfully("typeof x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.TYPEOF, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testEscapeSequenceInTypeof_01() {
		Script script = parseESSuccessfully("type\\u006ff x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.TYPEOF, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testEscapeSequenceInTypeof_02() {
		Script script = parseESSuccessfully("type\\u006Ff x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.TYPEOF, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}
}
