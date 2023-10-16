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
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Test;

public class ES_13_FunctionDefinitionEsprimaTest extends AbstractParserTest {

	@Test
	public void testFunctionDefinition() {
		Script script = parseESSuccessfully("function hello() { sayHi(); }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("hello", funcDecl.getName());

		Block block = funcDecl.getBody();
		assertEquals(1, block.getStatements().size());

		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("sayHi", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());
	}

	@Test
	public void testFunctionDefinitionEval() {
		Script script = parseESSuccessfully("function eval() { }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("eval", funcDecl.getName());

		Block block = funcDecl.getBody();
		assertEquals(0, block.getStatements().size());
	}

	@Test
	public void testFunctionDefinitionArguments() {
		Script script = parseESSuccessfully("function arguments() { }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("arguments", funcDecl.getName());

		Block block = funcDecl.getBody();
		assertEquals(0, block.getStatements().size());
	}

	@Test
	public void testFunctionDefinitionWithFpars() {
		Script script = parseESSuccessfully("function test(t, t) { }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("test", funcDecl.getName());

		assertEquals(2, funcDecl.getFpars().size());
		assertEquals("t", funcDecl.getFpars().get(0).getName());
		assertEquals("t", funcDecl.getFpars().get(1).getName());

		Block block = funcDecl.getBody();
		assertEquals(0, block.getStatements().size());
	}

	@Test
	public void testFunctionExpressionWithFpars() {
		Script script = parseESSuccessfully("(function test(t, t) { })");
		assertEquals(1, script.getScriptElements().size());
		ParenExpression parenExpr = (ParenExpression) ((ExpressionStatement) script.getScriptElements().get(0))
				.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		assertEquals("test", funcExpr.getName());

		assertEquals(2, funcExpr.getFpars().size());
		assertEquals("t", funcExpr.getFpars().get(0).getName());
		assertEquals("t", funcExpr.getFpars().get(1).getName());

		Block block = funcExpr.getBody();
		assertEquals(0, block.getStatements().size());
	}

	@Test
	public void testFunctionDefinitionEvalWithInner() {
		Script script = parseESSuccessfully("function eval() { function inner() { \"use strict\" } }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("eval", funcDecl.getName());

		Block block = funcDecl.getBody();
		assertEquals(1, block.getStatements().size());

		FunctionDeclaration innerFunc = (FunctionDeclaration) block.getStatements().get(0);
		assertEquals("inner", innerFunc.getName());
		Block blockInner = innerFunc.getBody();
		assertEquals(1, blockInner.getStatements().size());
		assertEquals("\"use strict\"",
				getText(((ExpressionStatement) blockInner.getStatements().get(0)).getExpression()));
	}

	@Test
	public void testFunctionDefinition1() {
		Script script = parseESSuccessfully("function hello(a) { sayHi(); }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("hello", funcDecl.getName());

		assertEquals(1, funcDecl.getFpars().size());
		assertEquals("a", funcDecl.getFpars().get(0).getName());

		Block block = funcDecl.getBody();
		assertEquals(1, block.getStatements().size());

		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("sayHi", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());
	}

	@Test
	public void testFunctionDefinition2() {
		Script script = parseESSuccessfully("function hello(a, b) { sayHi(); }");
		assertEquals(1, script.getScriptElements().size());
		FunctionDeclaration funcDecl = (FunctionDeclaration) script.getScriptElements().get(0);
		assertEquals("hello", funcDecl.getName());

		assertEquals(2, funcDecl.getFpars().size());
		assertEquals("a", funcDecl.getFpars().get(0).getName());
		assertEquals("b", funcDecl.getFpars().get(1).getName());

		Block block = funcDecl.getBody();
		assertEquals(1, block.getStatements().size());

		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("sayHi", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());
	}

	@Test
	public void testFunctionDefinitionVar() {
		Script script = parseESSuccessfully("var hi = function() { sayHi() };");
		assertEquals(1, script.getScriptElements().size());
		VariableDeclaration varDecl = ((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0);
		assertEquals("hi", varDecl.getName());
		FunctionExpression funcExpr = (FunctionExpression) varDecl.getExpression();

		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());

		ParameterizedCallExpression callExpr0 = (ParameterizedCallExpression) ((ExpressionStatement) block
				.getStatements().get(0)).getExpression();
		assertEquals("sayHi", getText(callExpr0.getTarget()));
		assertTrue(callExpr0.getArguments().isEmpty());
	}

	@Test
	public void testFunctionDefinitionVar2() {
		Script script = parseESSuccessfully("var hi = function eval() { };");
		assertEquals(1, script.getScriptElements().size());
		VariableDeclaration varDecl = ((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0);
		assertEquals("hi", varDecl.getName());
		FunctionExpression funcExpr = (FunctionExpression) varDecl.getExpression();

		Block block = funcExpr.getBody();
		assertEquals(0, block.getStatements().size());
		assertEquals("eval", funcExpr.getName());
	}

	@Test
	public void testFunctionDefinitionVar3() {
		Script script = parseESSuccessfully("var hi = function arguments() { };");
		assertEquals(1, script.getScriptElements().size());
		VariableDeclaration varDecl = ((VariableStatement) script.getScriptElements().get(0)).getVarDecl().get(0);
		assertEquals("hi", varDecl.getName());
		FunctionExpression funcExpr = (FunctionExpression) varDecl.getExpression();

		Block block = funcExpr.getBody();
		assertEquals(0, block.getStatements().size());
		assertEquals("arguments", funcExpr.getName());
	}

}
