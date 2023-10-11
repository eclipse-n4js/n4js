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

import java.nio.charset.Charset;

import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_06_UnicodeEsprimaTest extends AbstractParserTest {

	@Test
	public void testDefaultEncoding() {
		assertEquals("UTF-8", Charset.defaultCharset().displayName());
	}

	@Test
	public void testUnicodeIdentifier_01() {
		Script script = parseESSuccessfully("日本語 = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("日本語", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testUnicodeIdentifier_02() {
		Script script = parseESSuccessfully("T\u203F = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("T\u203F", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testUnicodeIdentifier_03() {
		Script script = parseESSuccessfully("T\u200C = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("T\u200C", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testUnicodeIdentifier_04() {
		Script script = parseESSuccessfully("T\u200D = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("T\u200D", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testUnicodeIdentifier_05() {
		Script script = parseESSuccessfully("\u2163\u2161 = []");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("\u2163\u2161", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}

	@Test
	public void testUnicodeIdentifier_06() {
		Script script = parseESSuccessfully("\u2163\u2161\u200A=\u2009[]");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		AssignmentExpression assignment = (AssignmentExpression) statement.getExpression();
		IdentifierRef identifier = (IdentifierRef) assignment.getLhs();
		assertEquals("\u2163\u2161", getText(identifier));
		ArrayLiteral array = (ArrayLiteral) assignment.getRhs();
		assertEquals(0, array.getElements().size());
	}
}
