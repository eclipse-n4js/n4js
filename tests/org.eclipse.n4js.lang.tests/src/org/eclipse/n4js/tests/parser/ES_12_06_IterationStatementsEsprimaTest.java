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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.math.BigDecimal;

import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IntLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PostfixOperator;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.junit.Test;

public class ES_12_06_IterationStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testDoWhile_01() {
		Script script = parseESSuccessfully("do keep(); while (true)");
		DoStatement statement = (DoStatement) script.getScriptElements().get(0);
		ExpressionStatement keepStatement = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression keep = (ParameterizedCallExpression) keepStatement.getExpression();
		IdentifierRef identifier = (IdentifierRef) keep.getTarget();
		assertEquals("keep", getText(identifier));
		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
	}

	@Test
	public void testDoWhile_02() {
		Script script = parseESSuccessfully("do keep(); while (true);");
		DoStatement statement = (DoStatement) script.getScriptElements().get(0);
		ExpressionStatement keepStatement = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression keep = (ParameterizedCallExpression) keepStatement.getExpression();
		IdentifierRef identifier = (IdentifierRef) keep.getTarget();
		assertEquals("keep", getText(identifier));
		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(true, bool.isTrue());
	}

	@Test
	public void testDoWhile_03() {
		Script script = parseESSuccessfully("do { x++; y--; } while (x < 10)");
		DoStatement statement = (DoStatement) script.getScriptElements().get(0);
		Block block = (Block) statement.getStatement();
		assertEquals(2, block.getStatements().size());
	}

	@Test
	public void testDoWhileSemicolonInsertion_01() {
		Script script = parseESSuccessfully("{ do { } while (false) false }");
		Block block = (Block) script.getScriptElements().get(0);
		DoStatement statement = (DoStatement) block.getStatements().get(0);
		Block innerBlock = (Block) statement.getStatement();
		assertEquals(0, innerBlock.getStatements().size());
	}

	@Test
	public void testDoWhileSemicolonInsertion_02() {
		Script script = parseESSuccessfully("{ do { } while (false)false }");
		Block block = (Block) script.getScriptElements().get(0);
		DoStatement statement = (DoStatement) block.getStatements().get(0);
		Block innerBlock = (Block) statement.getStatement();
		assertEquals(0, innerBlock.getStatements().size());
	}

	@Test
	public void testWhileStatement_01() {
		Script script = parseESSuccessfully("while (false) doSomething()");
		WhileStatement statement = (WhileStatement) script.getScriptElements().get(0);
		ExpressionStatement doSomethingStatement = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression doSomething = (ParameterizedCallExpression) doSomethingStatement.getExpression();
		IdentifierRef identifier = (IdentifierRef) doSomething.getTarget();
		assertEquals("doSomething", getText(identifier));
		BooleanLiteral bool = (BooleanLiteral) statement.getExpression();
		assertEquals(false, bool.isTrue());
	}

	@Test
	public void testWhileStatement_02() {
		Script script = parseESSuccessfully("while (x < 10) { x++; y--; }");
		WhileStatement statement = (WhileStatement) script.getScriptElements().get(0);
		Block block = (Block) statement.getStatement();
		assertEquals(2, block.getStatements().size());
	}

	@Test
	public void testForLoop_01() {
		Script script = parseESSuccessfully("for(;;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		assertTrue(statement.getVarDecl().isEmpty());
		assertNull(statement.getInitExpr());
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_02() {
		Script script = parseESSuccessfully("for(;;){}");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		Block block = (Block) statement.getStatement();
		assertTrue(block.getStatements().isEmpty());
		assertTrue(statement.getVarDecl().isEmpty());
		assertNull(statement.getInitExpr());
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_03() {
		Script script = parseESSuccessfully("for(x = 0;;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		assertTrue(statement.getVarDecl().isEmpty());
		AssignmentExpression init = (AssignmentExpression) statement.getInitExpr();
		IdentifierRef x = (IdentifierRef) init.getLhs();
		assertEquals("x", getText(x));
		IntLiteral value = (IntLiteral) init.getRhs();
		assertEquals(0, value.toInt());
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_04() {
		Script script = parseESSuccessfully("for(var x = 0;;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		VariableDeclaration varDecl = statement.getVarDecl().get(0);
		assertEquals("x", varDecl.getName());
		IntLiteral value = (IntLiteral) varDecl.getExpression();
		assertEquals(0, value.toInt());
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_05() {
		Script script = parseESSuccessfully("for(let x = 0;;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_06() {
		Script script = parseESSuccessfully("for(var x = 0, y = 1;;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		VariableDeclaration xDecl = statement.getVarDecl().get(0);
		assertEquals("x", xDecl.getName());
		IntLiteral x = (IntLiteral) xDecl.getExpression();
		assertEquals(0, x.toInt());
		VariableDeclaration yDecl = last(statement.getVarDecl());
		assertEquals("y", yDecl.getName());
		IntLiteral y = (IntLiteral) yDecl.getExpression();
		assertEquals(BigDecimal.ONE, y.getValue());
		assertNull(statement.getExpression());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_07() {
		Script script = parseESSuccessfully("for(x = 0; x < 42;);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		assertTrue(statement.getVarDecl().isEmpty());
		AssignmentExpression init = (AssignmentExpression) statement.getInitExpr();
		IdentifierRef x = (IdentifierRef) init.getLhs();
		assertEquals("x", getText(x));
		IntLiteral value = (IntLiteral) init.getRhs();
		assertEquals(0, value.toInt());
		RelationalExpression comparison = (RelationalExpression) statement.getExpression();
		assertEquals("x", getText(comparison.getLhs()));
		assertEquals(42, ((IntLiteral) comparison.getRhs()).toInt());
		assertNull(statement.getUpdateExpr());
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_08() {
		Script script = parseESSuccessfully("for(x = 0; x < 42; x++);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		EmptyStatement empty = (EmptyStatement) statement.getStatement();
		assertNotNull(empty);
		assertTrue(statement.getVarDecl().isEmpty());
		assertNotNull(statement.getInitExpr());
		assertNotNull(statement.getExpression());
		PostfixExpression postfix = (PostfixExpression) statement.getUpdateExpr();
		assertEquals(PostfixOperator.INC, postfix.getOp());
		assertEquals("x", getText(postfix.getExpression()));
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_09() {
		Script script = parseESSuccessfully("for(x = 0; x < 42; x++) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		ExpressionStatement body = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression process = (ParameterizedCallExpression) body.getExpression();
		assertEquals("process", getText(process.getTarget()));
		assertFalse(statement.isForIn());
		assertFalse(statement.isForOf());
		assertTrue(statement.isForPlain());
	}

	@Test
	public void testForLoop_typeDecl() {
		Script script = parseESSuccessfully("for(var x: any = 0; ;) {}");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		VariableDeclaration x = statement.getVarDecl().get(0);
		assertNotNull(x.getDeclaredTypeRefInAST());
	}

	@Test
	public void testForInLoop_01() {
		Script script = parseESSuccessfully("for(x in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		ExpressionStatement body = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression process = (ParameterizedCallExpression) body.getExpression();
		assertEquals("process", getText(process.getTarget()));
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_02() {
		Script script = parseESSuccessfully("for(var x in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_03() {
		Script script = parseESSuccessfully("for(var x = 42 in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_04() {
		Script script = parseESSuccessfully("for(let x in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_05() {
		Script script = parseESSuccessfully("for (var i = function() { return 10 in [] } in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_06() {
		Script script = parseESSuccessfully("for (var i = function() {} in list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertTrue(statement.isForIn());
		assertFalse(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForInLoop_07() {
		parseESSuccessfully("""
				const Object_keys = typeof Object.keys === 'function'
				    ? Object.keys
				    : function (obj) {
				        var keys: Array<string> = [];
				        for (var key in obj) keys.push(key as string);
				        return keys;
				    }
				;
				""");
	}

	@Test

	public void testForOfLoop_01() {
		Script script = parseESSuccessfully("for(x of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		ExpressionStatement body = (ExpressionStatement) statement.getStatement();
		ParameterizedCallExpression process = (ParameterizedCallExpression) body.getExpression();
		assertEquals("process", getText(process.getTarget()));
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_02() {
		Script script = parseESSuccessfully("for(var x of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_03() {
		Script script = parseESSuccessfully("for(var x = 42 of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_04() {
		Script script = parseESSuccessfully("for(let x of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
	}

	@Test
	public void testForOfLoop_05() {
		Script script = parseESSuccessfully("for (var i = function() { return 10 in [] } of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_06() {
		Script script = parseESSuccessfully("for (var i = function() {} of list) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_07() {
		Script script = parseESSuccessfully("for (var i = \"\" of [\"a\",\"b\"]) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
	}

	@Test
	public void testForOfLoop_08() {
		Script script = parseESSuccessfully("for (of of []) process(x);");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		// first "of" should be treated as a variable name;
		assertTrue(statement.getInitExpr() instanceof IdentifierRef);
	}

	@Test
	public void testForOfLoop_09() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (var of of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_10() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (var of = \"\" of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_11() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (let of of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_12() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (let of = \"\" of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_13() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (const of of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_14() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for (const of = \"\" of []) process(x);")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("of", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_15() {
		ForStatement statement = (ForStatement) parseESSuccessfully("for(var v = new X() of list) {}")
				.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("v", statement.getVarDecl().get(0).getName());
	}

	@Test
	public void testForOfLoop_17() {
		Script script = parseESSuccessfully("""
				for(var [a2,b2,c2] of arr2) {}
				""");
		ForStatement statement = (ForStatement) script.getScriptElements().get(0);
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("a2", statement.getVarDecl().get(0).getName());
	}

	// NOTE: strict mode is validated in the validation, not in the parser;
	@Test
	public void testForOfLoop_16() {
		ForStatement statement = (ForStatement) last(parseESWithError("\"use strict\"; for(var v = new X() of list) {}")
				.getScriptElements());
		assertFalse(statement.isForIn());
		assertTrue(statement.isForOf());
		assertFalse(statement.isForPlain());
		assertEquals("v", statement.getVarDecl().get(0).getName());
	}
}
