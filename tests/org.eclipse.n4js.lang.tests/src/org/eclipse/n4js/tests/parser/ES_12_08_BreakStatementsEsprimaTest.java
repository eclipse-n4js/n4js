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

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.junit.Test;

public class ES_12_08_BreakStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testBreak_Simple() {
		Script script = parseESSuccessfully("while (true) { break }");
		WhileStatement statement = (WhileStatement) script.getScriptElements().get(0);

		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
		Block block = (Block) statement.getStatement();
		assertEquals(1, block.getStatements().size());
		BreakStatement breakStmt = (BreakStatement) block.getStatements().get(0);
		assertNull(breakStmt.getLabel());
	}

	@Test
	public void testBreak_SimpleASI() {
		Script script = parseESSuccessfully("while (true) { break }");
		WhileStatement statement = (WhileStatement) script.getScriptElements().get(0);

		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
		Block block = (Block) statement.getStatement();
		assertEquals(1, block.getStatements().size());
		BreakStatement breakStmt = (BreakStatement) block.getStatements().get(0);
		assertNull(breakStmt.getLabel());
	}

	@Test
	public void testBreak_LabelASI() {
		Script script = parseESSuccessfully("done: while (true) { break done }");
		LabelledStatement labeledStatement = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement statement = (WhileStatement) labeledStatement.getStatement();

		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
		Block block = (Block) statement.getStatement();
		assertEquals(1, block.getStatements().size());
		BreakStatement breakStmt = (BreakStatement) block.getStatements().get(0);
		assertEquals("done", breakStmt.getLabel().getName());
	}

	@Test
	public void testBreak_Label() {
		Script script = parseESSuccessfully("done: while (true) { break done; }");
		LabelledStatement labeledStatement = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement statement = (WhileStatement) labeledStatement.getStatement();

		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
		Block block = (Block) statement.getStatement();
		assertEquals(1, block.getStatements().size());
		BreakStatement breakStmt = (BreakStatement) block.getStatements().get(0);
		assertEquals("done", breakStmt.getLabel().getName());
	}

	@Test
	public void testBreak_LabelProto() {
		Script script = parseESSuccessfully("__proto__: while (true) { break __proto__; }");
		LabelledStatement labeledStatement = (LabelledStatement) script.getScriptElements().get(0);
		WhileStatement statement = (WhileStatement) labeledStatement.getStatement();

		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
		Block block = (Block) statement.getStatement();
		assertEquals(1, block.getStatements().size());
		BreakStatement breakStmt = (BreakStatement) block.getStatements().get(0);
		assertEquals("__proto__", breakStmt.getLabel().getName());
	}

}
