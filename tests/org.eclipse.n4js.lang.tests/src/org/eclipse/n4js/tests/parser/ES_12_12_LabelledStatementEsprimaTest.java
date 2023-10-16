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

import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.junit.Test;

public class ES_12_12_LabelledStatementEsprimaTest extends AbstractParserTest {

	@Test
	public void testFor() {
		Script program = parseESSuccessfully("start: for (;;) break start");
		LabelledStatement statement = (LabelledStatement) program.getScriptElements().get(0);
		ForStatement nested = (ForStatement) statement.getStatement();
		assertEquals("start", statement.getName());
		BreakStatement breakStmt = (BreakStatement) nested.getStatement();
		assertSame(statement, breakStmt.getLabel());
	}

	@Test
	public void testWhile() {
		Script program = parseESSuccessfully("start: while (true) break start");
		LabelledStatement statement = (LabelledStatement) program.getScriptElements().get(0);
		WhileStatement nested = (WhileStatement) statement.getStatement();
		assertEquals("start", statement.getName());
		BreakStatement breakStmt = (BreakStatement) nested.getStatement();
		assertSame(statement, breakStmt.getLabel());
	}

	@Test
	public void testExpression() {
		Script program = parseESSuccessfully("__proto__: test");
		LabelledStatement statement = (LabelledStatement) program.getScriptElements().get(0);
		ExpressionStatement nested = (ExpressionStatement) statement.getStatement();
		assertNotNull(nested);
	}

}
