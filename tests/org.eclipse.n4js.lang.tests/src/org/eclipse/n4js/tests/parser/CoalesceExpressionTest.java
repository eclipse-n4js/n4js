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

import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.CoalesceExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class CoalesceExpressionTest extends AbstractParserTest {

	@Test
	public void testCoalesce_01() {
		Script program = parseESSuccessfully("y ?? 1");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		CoalesceExpression coalesce = (CoalesceExpression) statement.getExpression();
		assertNotNull(coalesce);

		IdentifierRef expression = (IdentifierRef) coalesce.getExpression();
		assertEquals("y", getText(expression));
		NumericLiteral dfltExpr = (NumericLiteral) coalesce.getDefaultExpression();
		assertEquals("1", getText(dfltExpr));
	}

	@Test
	public void testCoalesce_02() {
		Script program = parseESSuccessfully("x|y ?? 1&2");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		CoalesceExpression coalesce = (CoalesceExpression) statement.getExpression();
		assertNotNull(coalesce);

		BinaryBitwiseExpression expression = (BinaryBitwiseExpression) coalesce.getExpression();
		assertEquals("x|y", getText(expression));
		BinaryBitwiseExpression dfltExpr = (BinaryBitwiseExpression) coalesce.getDefaultExpression();
		assertEquals("1&2", getText(dfltExpr));
	}

	@Test
	public void testCoalesce_03() {
		Script program = parseESWithError("x||y ?? zonk");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		CoalesceExpression coalesce = (CoalesceExpression) statement.getExpression();
		assertNotNull(coalesce);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) coalesce.getExpression();
		assertEquals("x||y", getText(expression));
		IdentifierRef dfltExpr = (IdentifierRef) coalesce.getDefaultExpression();
		assertEquals("zonk", dfltExpr.getIdAsText());

		String message = program.eResource().getErrors().get(0).getMessage();
		assertEquals("Nullish coalescing expressions cannot immediately contain an || operation.", message);
	}

	@Test
	public void testCoalesce_04() {
		parseESSuccessfully("false || (a ?? b)");
	}

	@Test
	public void testCoalesce_05() {
		Script program = parseESWithError("false || a ?? b");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		CoalesceExpression coalesce = (CoalesceExpression) statement.getExpression();
		assertNotNull(coalesce);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) coalesce.getExpression();
		assertEquals("false || a", getText(expression));
		IdentifierRef dfltExpr = (IdentifierRef) coalesce.getDefaultExpression();
		assertEquals("b", dfltExpr.getIdAsText());

		String message = program.eResource().getErrors().get(0).getMessage();
		assertEquals("Nullish coalescing expressions cannot immediately contain an || operation.", message);
	}

	@Test
	public void testCoalesce_06() {
		Script program = parseESWithError("false && a ?? b");
		ExpressionStatement statement = (ExpressionStatement) program.getScriptElements().get(0);
		CoalesceExpression coalesce = (CoalesceExpression) statement.getExpression();
		assertNotNull(coalesce);

		BinaryLogicalExpression expression = (BinaryLogicalExpression) coalesce.getExpression();
		assertEquals("false && a", getText(expression));
		IdentifierRef dfltExpr = (IdentifierRef) coalesce.getDefaultExpression();
		assertEquals("b", dfltExpr.getIdAsText());

		String message = program.eResource().getErrors().get(0).getMessage();
		assertEquals("Nullish coalescing expressions cannot immediately contain an && operation.", message);
	}

	@Test
	public void testCoalesce_07() {
		parseESSuccessfully("(x||y) ?? zonk");
	}

	@Test
	public void testCoalesce_08() {
		parseESSuccessfully("(false && a) ?? b");
	}

	@Test
	public void testCoalesce_09() {
		parseESSuccessfully("false ?? (a && b)");
	}
}
