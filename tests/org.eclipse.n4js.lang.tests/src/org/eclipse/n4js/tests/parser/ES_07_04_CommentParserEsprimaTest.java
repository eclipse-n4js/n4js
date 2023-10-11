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
import org.eclipse.n4js.n4JS.CaseClause;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.junit.Test;

import com.google.inject.Inject;

public class ES_07_04_CommentParserEsprimaTest extends AbstractParserTest {

	@Inject
	IEObjectDocumentationProvider odProvider;

	@Test
	public void testBlockComment_01() {
		Script script = parseESSuccessfully("/* block comment */ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
		assertNull("No jsdoc-style comment here.", odProvider.getDocumentation(i));
	}

	@Test
	public void testBlockComment_01_b() {
		Script script = parseESSuccessfully("/** jsdoc block comment */ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
		assertEquals("jsdoc block comment", odProvider.getDocumentation(i));
	}

	@Test
	public void testBlockComment_02() {
		Script script = parseESSuccessfully("42 /*The*/ /*Answer*/");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockComment_03() {
		Script script = parseESSuccessfully("42 /*The * Answer*/");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockComment_04() {
		Script script = parseESSuccessfully("/* multiline\ncomment\nshould\nbe\nignored */ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
		assertNull("simple multiline comment should be ignored", odProvider.getDocumentation(i));
	}

	@Test
	public void testBlockComment_04_b() {
		Script script = parseESSuccessfully("/** jsdoc multiline\ncomment\nshould\nbe\nignored */ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
		assertEquals("jsdoc multiline\ncomment\nshould\nbe\nignored", odProvider.getDocumentation(i));
	}

	@Test
	public void testBlockComment_05() {
		Script script = parseESSuccessfully("/*a\r\nb*/ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockComment_06() {
		Script script = parseESSuccessfully("/*a\rb*/ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockComment_07() {
		Script script = parseESSuccessfully("/*a\nb*/ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockComment_08() {
		Script script = parseESSuccessfully("/**/ 42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineComment_01() {
		Script script = parseESSuccessfully("// line comment\n42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineComment_02() {
		Script script = parseESSuccessfully("42// line comment");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineComment_03() {
		Script script = parseESSuccessfully("// Hello, world!\n42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineComment_04() {
		Script script = parseESSuccessfully("//\n42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineComment_05() {
		Script script = parseESSuccessfully("// Hello, world!\n\n//   Another hello\n42");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IntLiteral i = (IntLiteral) statement.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testLineCommentInBlock() {
		Script script = parseESSuccessfully("if (x) { // Some comment\n42 }");
		IfStatement statement = (IfStatement) script.getScriptElements().get(0);
		Block block = (Block) statement.getIfStmt();
		ExpressionStatement expressionStmt = (ExpressionStatement) block.getStatements().get(0);
		IntLiteral i = (IntLiteral) expressionStmt.getExpression();
		assertEquals(42, i.toInt());
	}

	@Test
	public void testBlockCommentInBlock() {
		Script script = parseESSuccessfully("switch (answer) { case 42: /* perfect */ 42; }");
		SwitchStatement statement = (SwitchStatement) script.getScriptElements().get(0);
		CaseClause caseClause = statement.getCaseClauses().get(0);
		ExpressionStatement caseStatement = (ExpressionStatement) caseClause.getStatements().get(0);
		IntLiteral i = (IntLiteral) caseStatement.getExpression();
		assertEquals(42, i.toInt());
	}

}
