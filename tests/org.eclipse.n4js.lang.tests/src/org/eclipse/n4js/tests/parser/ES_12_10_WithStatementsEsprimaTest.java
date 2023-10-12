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
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.WithStatement;
import org.junit.Test;

public class ES_12_10_WithStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testWith_SimpleASI() {
		Script script = parseESSuccessfully("with (x) foo = bar");
		assertEquals(1, script.getScriptElements().size());
		WithStatement withStmt = (WithStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) withStmt.getExpression();
		assertEquals("x", getText(identifier));

		ExpressionStatement expressionStatement = (ExpressionStatement) withStmt.getStatement();

		AssignmentExpression expression = (AssignmentExpression) expressionStatement.getExpression();
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("foo", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("bar", getText(right));
	}

	@Test
	public void testWith_Simple() {
		Script script = parseESSuccessfully("with (x) foo = bar;");
		assertEquals(1, script.getScriptElements().size());
		WithStatement withStmt = (WithStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) withStmt.getExpression();
		assertEquals("x", getText(identifier));

		ExpressionStatement expressionStatement = (ExpressionStatement) withStmt.getStatement();

		AssignmentExpression expression = (AssignmentExpression) expressionStatement.getExpression();
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("foo", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("bar", getText(right));
	}

	@Test
	public void testWith_Block() {
		Script script = parseESSuccessfully("with (x) { foo = bar }");
		assertEquals(1, script.getScriptElements().size());
		WithStatement withStmt = (WithStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) withStmt.getExpression();
		assertEquals("x", getText(identifier));

		Block block = (Block) withStmt.getStatement();
		assertEquals(1, block.getStatements().size());

		ExpressionStatement expressionStatement = (ExpressionStatement) block.getStatements().get(0);
		AssignmentExpression expression = (AssignmentExpression) expressionStatement.getExpression();
		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("foo", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("bar", getText(right));
	}

}
