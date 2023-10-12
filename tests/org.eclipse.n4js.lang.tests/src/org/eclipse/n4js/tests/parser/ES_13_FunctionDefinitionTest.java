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

import static org.eclipse.n4js.utils.Strings.join;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.Statement;
import org.junit.Test;

public class ES_13_FunctionDefinitionTest extends AbstractParserTest {

	/**
	 * This tests the absence of a predicate in the grammar that"d yield a FunctionExpression rather than a
	 * FunctionDeclaration
	 */
	@Test
	public void testFunctionDefinitionVar3() {
		Script script = parseESSuccessfully("""
				function testcase() {
				        "use strict";

				        try {
				            eval("var yield = 1;")
				            return false;
				        } catch (e) {
				            return e instanceof SyntaxError;
				        }
				}
				""");
		assertTrue(script.getScriptElements().get(0) instanceof FunctionDeclaration);
	}

	@Test
	public void testFunctionDeclarationInToplevelBlock() {
		Script script = parseESSuccessfully("{ function testcase() {} }");
		assertTrue(join("\n", script.eResource().getErrors()),
				script.eResource().getErrors().isEmpty());

		ScriptElement seleHead = script.getScriptElements().get(0);
		assertTrue(seleHead instanceof Block);

		Statement blockStmtsHead = ((Block) seleHead).getStatements().get(0);
		assertTrue(blockStmtsHead instanceof FunctionDeclaration);

		assertEquals("testcase", ((FunctionDeclaration) blockStmtsHead).getName());
	}

	@Test
	public void testFunctionDeclarationInToplevelBlockN4JS() throws Exception {
		Script script = parseHelper.parse("{ function testcase() {} }");
		assertEquals(join("\n", script.eResource().getErrors()),
				1, script.eResource().getErrors().size());
		assertTrue(script.getScriptElements().get(0) instanceof Block);
	}

	@Test
	public void testAnnotatedFunctionDeclaration() {
		Script script = parseESSuccessfully("@Annotation function testcase() {}");
		assertTrue(script.getScriptElements().get(0) instanceof FunctionDeclaration);
	}

	@Test
	public void testFunctionExpression() {
		Script script = parseESSuccessfully("(function testcase() {})");
		ParenExpression expression = (ParenExpression) ((ExpressionStatement) script.getScriptElements().get(0))
				.getExpression();
		assertTrue(expression.getExpression() instanceof FunctionExpression);
	}

	@Test
	public void testAnnotatedFunctionExpression() {
		Script script = parseESSuccessfully("(@Dummy function testcase() {})");
		ParenExpression expression = (ParenExpression) ((ExpressionStatement) script.getScriptElements().get(0))
				.getExpression();
		assertTrue(expression.getExpression() instanceof FunctionExpression);
	}

	@Test
	public void testNamelessFunctionDeclaration() throws Exception {
		Script script = parseHelper.parse("""
				(@Dummy function testcase() {
					function innerFDecl () { return 7; }
				})
				""");
		ScriptElement seleHead = script.getScriptElements().get(0);
		FunctionExpression fe = (FunctionExpression) ((ParenExpression) ((ExpressionStatement) seleHead)
				.getExpression()).getExpression();
		Statement fstmtsHead = fe.getBody().getStatements().get(0);
		assertTrue(fstmtsHead instanceof FunctionDeclaration);
	}
}
