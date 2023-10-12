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

import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.junit.Test;

public class ES_12_04_ExpressionStatementEsprimaTest extends AbstractParserTest {

	@Test
	public void testIdentifierRef_01() {
		Script script = parseESSuccessfully("x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		IdentifierRef x = (IdentifierRef) statement.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testIdentifierRef_02() {
		Script script = parseESSuccessfully("x, y");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		CommaExpression comma = (CommaExpression) statement.getExpression();
		assertEquals(2, comma.getExprs().size());
		IdentifierRef x = (IdentifierRef) comma.getExprs().get(0);
		assertEquals("x", getText(x));
		IdentifierRef y = (IdentifierRef) last(comma.getExprs());
		assertEquals("y", getText(y));
	}

	@Test
	public void testEscapedIdentifierRef_01() {
		Script script = parseESSuccessfully("var a; \\u0061");
		ExpressionStatement statement = (ExpressionStatement) last(script.getScriptElements());
		IdentifierRef xRef = (IdentifierRef) statement.getExpression();
		assertEquals("\\u0061", getText(xRef));
		IdentifiableElement id = xRef.getId();
		assertEquals("a", id.getName());
	}

	@Test
	public void testEscapedIdentifierRef_02() {
		Script script = parseESSuccessfully("var aa; a\\u0061");
		ExpressionStatement statement = (ExpressionStatement) last(script.getScriptElements());
		IdentifierRef xRef = (IdentifierRef) statement.getExpression();
		assertEquals("a\\u0061", getText(xRef));
		IdentifiableElement id = xRef.getId();
		assertEquals("aa", id.getName());
	}

	@Test
	public void testEscapedIdentifierRef_03() {
		Script script = parseESSuccessfully("var aa; \\u0061a ");
		ExpressionStatement statement = (ExpressionStatement) last(script.getScriptElements());
		IdentifierRef xRef = (IdentifierRef) statement.getExpression();
		assertEquals("\\u0061a", getText(xRef));
		IdentifiableElement id = xRef.getId();
		assertEquals("aa", id.getName());
	}
}
