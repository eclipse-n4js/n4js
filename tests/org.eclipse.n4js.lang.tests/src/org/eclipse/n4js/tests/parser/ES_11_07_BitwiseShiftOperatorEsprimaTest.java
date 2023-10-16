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
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.ShiftOperator;
import org.junit.Test;

public class ES_11_07_BitwiseShiftOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testLeftShift() {
		Script program = parseESSuccessfully("x << y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ShiftExpression shift = (ShiftExpression) statement.getExpression();
		ShiftOperator op = shift.getOp();
		assertEquals(ShiftOperator.SHL, op);

		IdentifierRef left = (IdentifierRef) shift.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) shift.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testRightShift() {
		Script program = parseESSuccessfully("x >> y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ShiftExpression shift = (ShiftExpression) statement.getExpression();
		ShiftOperator op = shift.getOp();
		assertEquals(ShiftOperator.SHR, op);

		IdentifierRef left = (IdentifierRef) shift.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) shift.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testUnsignedRightShift() {
		Script program = parseESSuccessfully("x >>> y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ShiftExpression shift = (ShiftExpression) statement.getExpression();
		ShiftOperator op = shift.getOp();
		assertEquals(ShiftOperator.USHR, op);

		IdentifierRef left = (IdentifierRef) shift.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) shift.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testLeftShiftWithTrailingIdentifierRef() {
		Script program = parseESSuccessfully("""
				a<<b
				c
				""");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		ShiftExpression shift = (ShiftExpression) statement.getExpression();
		ShiftOperator op = shift.getOp();
		assertEquals(ShiftOperator.SHL, op);

		IdentifierRef left = (IdentifierRef) shift.getLhs();
		assertEquals("a", getText(left));
		IdentifierRef right = (IdentifierRef) shift.getRhs();
		assertEquals("b", getText(right));
	}

}
