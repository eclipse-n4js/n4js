/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.htm
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.MultiplicativeOperator;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_12_09_ReturnStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testReturn_SimpleASI() {
		Script script = parseESSuccessfully("(function(){ return })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);
		assertNull(returnStmt.getExpression());
	}

	@Test
	public void testReturn_Simple() {
		Script script = parseESSuccessfully("(function(){ return; })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);
		assertNull(returnStmt.getExpression());
	}

	@Test
	public void testReturn_WithArgument() {
		Script script = parseESSuccessfully("(function(){ return x; })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);
		IdentifierRef identifier = (IdentifierRef) returnStmt.getExpression();
		assertEquals("x", getText(identifier));
	}

	@Test
	public void testReturn_WithArgumentASI() {
		Script script = parseESSuccessfully("(function(){ return x })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);
		IdentifierRef identifier = (IdentifierRef) returnStmt.getExpression();
		assertEquals("x", getText(identifier));
	}

	@Test
	public void testReturn_WithArgumentASI2() {
		Script script = parseESSuccessfully("(function(){ return \n x*y })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(2, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);
		assertNull(returnStmt.getExpression());
	}

	@Test
	public void testReturn_WithArgumentMult() {
		Script script = parseESSuccessfully("(function(){ return x * y })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);

		MultiplicativeExpression expression = (MultiplicativeExpression) returnStmt.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.TIMES, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

	@Test
	public void testReturn_WithArgumentMultASI() {
		Script script = parseESSuccessfully("(function(){ return x\n * y })");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		// paren expressions are explicitly modeled (in Esprima, they are not modeled explicitly)
		ParenExpression parenExpr = (ParenExpression) statement.getExpression();
		FunctionExpression funcExpr = (FunctionExpression) parenExpr.getExpression();
		Block block = funcExpr.getBody();
		assertEquals(1, block.getStatements().size());
		ReturnStatement returnStmt = (ReturnStatement) block.getStatements().get(0);

		MultiplicativeExpression expression = (MultiplicativeExpression) returnStmt.getExpression();
		MultiplicativeOperator op = expression.getOp();
		assertEquals(MultiplicativeOperator.TIMES, op);

		IdentifierRef left = (IdentifierRef) expression.getLhs();
		assertEquals("x", getText(left));
		IdentifierRef right = (IdentifierRef) expression.getRhs();
		assertEquals("y", getText(right));
	}

}
