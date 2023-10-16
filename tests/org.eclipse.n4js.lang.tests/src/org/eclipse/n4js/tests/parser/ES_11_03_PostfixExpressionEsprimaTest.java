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
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PostfixOperator;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_03_PostfixExpressionEsprimaTest extends AbstractParserTest {

	@Test
	public void testInc_01() {
		Script script = parseESSuccessfully("x++");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.INC, postfix.getOp());
		IdentifierRef x = (IdentifierRef) postfix.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testInc_02() {
		Script script = parseESSuccessfully("eval++");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.INC, postfix.getOp());
		IdentifierRef eval = (IdentifierRef) postfix.getExpression();
		assertEquals("eval", getText(eval));
	}

	@Test
	public void testInc_03() {
		Script script = parseESSuccessfully("arguments++");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.INC, postfix.getOp());
	}

	@Test
	public void testDec_01() {
		Script script = parseESSuccessfully("x--");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.DEC, postfix.getOp());
	}

	@Test
	public void testDec_02() {
		Script script = parseESSuccessfully("eval--");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.DEC, postfix.getOp());
	}

	@Test
	public void testDec_03() {
		Script script = parseESSuccessfully("arguments--");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		PostfixExpression postfix = (PostfixExpression) statement.getExpression();
		assertEquals(PostfixOperator.DEC, postfix.getOp());
	}

}
