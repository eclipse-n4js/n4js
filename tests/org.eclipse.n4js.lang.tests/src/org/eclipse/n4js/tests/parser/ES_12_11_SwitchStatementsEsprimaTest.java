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

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.AbstractCaseClause;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.junit.Test;

public class ES_12_11_SwitchStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testSwitch_Min() {
		Script script = parseESSuccessfully("switch (x) {}");
		assertEquals(1, script.getScriptElements().size());
		SwitchStatement switchStmt = (SwitchStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) switchStmt.getExpression();
		assertEquals("x", getText(identifier));

		assertNull(switchStmt.getDefaultClause());
		assertTrue(switchStmt.getCaseClauses().isEmpty());
		assertTrue(switchStmt.getCases().isEmpty());
	}

	@Test
	public void testSwitch_Simple() {
		Script script = parseESSuccessfully("switch (answer) { case 42: hi(); break; }");
		assertEquals(1, script.getScriptElements().size());
		SwitchStatement switchStmt = (SwitchStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) switchStmt.getExpression();
		assertEquals("answer", getText(identifier));

		assertNull(switchStmt.getDefaultClause());
		assertEquals(1, switchStmt.getCaseClauses().size());

		CaseClause caseClause = switchStmt.getCaseClauses().get(0);
		assertEquals("42", getText(caseClause.getExpression()));
		EList<Statement> stmts = caseClause.getStatements();
		assertEquals(2, stmts.size());
		ParameterizedCallExpression call = (ParameterizedCallExpression) ((ExpressionStatement) stmts.get(0))
				.getExpression();
		assertEquals("hi", getText(call.getTarget()));
		assertTrue(call.getArguments().isEmpty());
		BreakStatement breakStmt = (BreakStatement) stmts.get(1);
		assertNull(breakStmt.getLabel());
	}

	@Test
	public void testSwitch_SimpleWithDefault() {
		Script script = parseESSuccessfully("switch (answer) { case 42: hi(); break; default: break }");
		assertEquals(1, script.getScriptElements().size());
		SwitchStatement switchStmt = (SwitchStatement) script.getScriptElements().get(0);
		IdentifierRef identifier = (IdentifierRef) switchStmt.getExpression();
		assertEquals("answer", getText(identifier));

		assertEquals(2, switchStmt.getCases().size());

		CaseClause caseClause = switchStmt.getCaseClauses().get(0);
		assertEquals("42", getText(caseClause.getExpression()));
		EList<Statement> stmts = caseClause.getStatements();
		assertEquals(2, stmts.size());
		ParameterizedCallExpression call = (ParameterizedCallExpression) ((ExpressionStatement) stmts.get(0))
				.getExpression();
		assertEquals("hi", getText(call.getTarget()));
		assertTrue(call.getArguments().isEmpty());
		BreakStatement breakStmt = (BreakStatement) stmts.get(1);
		assertNull(breakStmt.getLabel());

		AbstractCaseClause defaultClause = switchStmt.getCases().get(1);
		BreakStatement breakStmt2 = (BreakStatement) defaultClause.getStatements().get(0);
		assertNull(breakStmt2.getLabel());

		assertEquals(switchStmt.getDefaultClause(), defaultClause);
	}

}
