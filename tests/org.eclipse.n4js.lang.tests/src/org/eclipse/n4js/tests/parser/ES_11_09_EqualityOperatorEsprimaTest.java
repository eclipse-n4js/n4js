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

import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_11_09_EqualityOperatorEsprimaTest extends AbstractParserTest {

	@Test
	public void testEquals() {
		Script program = parseESSuccessfully("x == y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		EqualityExpression equality = (EqualityExpression) statement.getExpression();
		EqualityOperator op = equality.getOp();
		assertEquals(EqualityOperator.EQ, op);

		IdentifierRef left = (IdentifierRef) equality.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) equality.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testEqualsNot() {
		Script program = parseESSuccessfully("x != y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		EqualityExpression equality = (EqualityExpression) statement.getExpression();
		EqualityOperator op = equality.getOp();
		assertEquals(EqualityOperator.NEQ, op);

		IdentifierRef left = (IdentifierRef) equality.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) equality.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testSame() {
		Script program = parseESSuccessfully("x === y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		EqualityExpression equality = (EqualityExpression) statement.getExpression();
		EqualityOperator op = equality.getOp();
		assertEquals(EqualityOperator.SAME, op);

		IdentifierRef left = (IdentifierRef) equality.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) equality.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testNotSame() {
		Script program = parseESSuccessfully("x !== y");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		EqualityExpression equality = (EqualityExpression) statement.getExpression();
		EqualityOperator op = equality.getOp();
		assertEquals(EqualityOperator.NSAME, op);

		IdentifierRef left = (IdentifierRef) equality.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) equality.getRhs();
		assertEquals("y", getText(right));
	}

}
