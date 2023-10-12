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

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FinallyBlock;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TryStatement;
import org.junit.Test;

public class ES_12_14_TryStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testThrow_Simple() {
		Script script = parseESSuccessfully("try { } catch (e) { }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());
		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		assertTrue(catchBlock.getBlock().getStatements().isEmpty());
	}

	@Test
	public void testThrow_withType() {
		Script script = parseESSuccessfully("try { } catch (e: any) { }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());
		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		assertTrue(catchBlock.getBlock().getStatements().isEmpty());
	}

	@Test
	public void testThrow_SimpleEval() {
		Script script = parseESSuccessfully("try { } catch (eval) { }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());
		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("eval", catchBlock.getCatchVariable().getName());
		assertTrue(catchBlock.getBlock().getStatements().isEmpty());
	}

	@Test
	public void testThrow_SimpleArguments() {
		Script script = parseESSuccessfully("try { } catch (arguments) { }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());
		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("arguments", catchBlock.getCatchVariable().getName());
		assertTrue(catchBlock.getBlock().getStatements().isEmpty());
	}

	@Test
	public void testThrow_WithCatchBlock() {
		Script script = parseESSuccessfully("try { } catch (e) { say(e) }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());
		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) ((ExpressionStatement) catchBlock
				.getBlock().getStatements().get(0)).getExpression();
		assertEquals("say", getText(callExpr.getTarget()));
		assertEquals("e", getText(callExpr.getArguments().get(0).getExpression()));
	}

	@Test
	public void testThrow_WithFinallyBlock() {
		Script script = parseESSuccessfully("try { } finally { cleanup(stuff) }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();
		assertTrue(block.getStatements().isEmpty());

		CatchBlock catchBlock = tryStmt.getCatch();
		assertNull(catchBlock);

		FinallyBlock finallyBlock = tryStmt.getFinally();
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) ((ExpressionStatement) finallyBlock
				.getBlock().getStatements().get(0)).getExpression();
		assertEquals("cleanup", getText(callExpr.getTarget()));
		assertEquals("stuff", getText(callExpr.getArguments().get(0).getExpression()));
	}

	@Test
	public void testThrow_WithCatchBlock2() {
		Script script = parseESSuccessfully("try { doThat(); } catch (e) { say(e) }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();

		assertEquals(1, block.getStatements().size());
		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("doThat", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());

		assertNull(tryStmt.getFinally());
		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) ((ExpressionStatement) catchBlock
				.getBlock().getStatements().get(0)).getExpression();
		assertEquals("say", getText(callExpr.getTarget()));
		assertEquals("e", getText(callExpr.getArguments().get(0).getExpression()));
	}

	@Test
	public void testThrow_WithCatchAndFinallyBlock() {
		Script script = parseESSuccessfully("try { doThat(); } catch (e) { say(e) } finally { cleanup(stuff) }");
		TryStatement tryStmt = (TryStatement) script.getScriptElements().get(0);
		Block block = tryStmt.getBlock();

		assertEquals(1, block.getStatements().size());
		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("doThat", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());

		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) ((ExpressionStatement) catchBlock
				.getBlock().getStatements().get(0)).getExpression();
		assertEquals("say", getText(callExpr.getTarget()));
		assertEquals("e", getText(callExpr.getArguments().get(0).getExpression()));

		FinallyBlock finallyBlock = tryStmt.getFinally();
		ParameterizedCallExpression callExpr1 = (ParameterizedCallExpression) ((ExpressionStatement) finallyBlock
				.getBlock().getStatements().get(0)).getExpression();
		assertEquals("cleanup", getText(callExpr1.getTarget()));
		assertEquals("stuff", getText(callExpr1.getArguments().get(0).getExpression()));
	}

	@Test
	public void testFunctionInCatchBlockStrictMode() {
		Script script = parseESSuccessfully("\"use strict\"; try {} catch (e) { function x() {} }");
		TryStatement tryStmt = (TryStatement) last(script.getScriptElements());

		CatchBlock catchBlock = tryStmt.getCatch();
		assertEquals("e", catchBlock.getCatchVariable().getName());
		FunctionDeclaration funDecl = (FunctionDeclaration) catchBlock.getBlock().getStatements().get(0);
		assertEquals("x", funDecl.getName());
	}

	@Test
	public void testFunctionInFinallyBlockStrictMode() {
		parseESWithError("\"use strict\"; try {} finally { function x() {} }");
	}

	@Test
	public void testFunctionInFinallyBlock() {
		parseESSuccessfully("try {} finally { function x() {} }");
	}

}
