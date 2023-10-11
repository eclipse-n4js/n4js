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

import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.junit.Test;

public class ES_07_08_4_StringLiteralErrorTest extends AbstractParserTest {

	@Test
	public void testEmpty_01() {
		parseESSuccessfully("\"\"");
	}

	@Test
	public void testEmpty_02() {
		parseESSuccessfully("''");
	}

	@Test
	public void testBogusEscape_01() {
		Script script = parseESWithWarning("'\\123'");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("S", stringLiteral.getValue());
	}

	@Test
	public void testBogusEscape_02() {
		Script script = parseESSuccessfully("'\\0123'");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("\n3", stringLiteral.getValue());
	}

	@Override
	protected Script parseESSuccessfully(CharSequence js) {
		Script script = super.parseESSuccessfully(js);
		assertTrue(script.eResource().getWarnings().toString(), script.eResource().getWarnings().isEmpty());
		return script;
	}

	Script parseESWithWarning(CharSequence js) {
		try {
			Script script = parseHelper.parseUnrestricted(js);
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
			assertFalse(script.eResource().getWarnings().toString(), script.eResource().getWarnings().isEmpty());
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	@Test
	public void testUnclosed_01() {
		Script script = parseESWithError("'");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("", stringLiteral.getValue());
		assertEquals("'", stringLiteral.getRawValue());
	}

	@Test
	public void testUnclosed_02() {
		Script script = parseESWithError("\"");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("", stringLiteral.getValue());
	}

	@Test
	public void testUnclosed_03() {
		Script script = parseESWithError("'\\'");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("'", stringLiteral.getValue());
	}

	@Test
	public void testUnclosed_04() {
		Script script = parseESWithError("\"\\\"");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("\"", stringLiteral.getValue());
	}

	@Test
	public void testUnclosedWithASI() {
		Script script = parseESWithError("""
				"abc
				1+1""");
		ExpressionStatement first = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) first.getExpression();
		assertEquals("abc", stringLiteral.getValue());
		ExpressionStatement last = (ExpressionStatement) last(script.getScriptElements());
		AdditiveExpression sum = (AdditiveExpression) last.getExpression();
		assertEquals("1+1", getText(sum));
	}

	@Test
	public void testProperlyClosed_01() {
		Script script = parseESSuccessfully("'\\\\'");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("\\", stringLiteral.getValue());
	}

	@Test
	public void testProperlyClosed_02() {
		Script script = parseESSuccessfully("\"\\\\\"");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral stringLiteral = (StringLiteral) statement.getExpression();
		assertEquals("\\", stringLiteral.getValue());
	}

	@Test
	public void testBadEscapement_01() {
		parseESWithWarning("\"Hello\\112World\"");
	}

	@Test
	public void testBadEscapement_02() {
		parseESWithWarning("\"Hello\\212World\"");
	}

	@Test
	public void testBadEscapement_03() {
		parseESWithWarning("\"Hello\\312World\"");
	}

	@Test
	public void testBadEscapement_04() {
		parseESWithWarning("\"Hello\\412World\"");
	}

	@Test
	public void testBadEscapement_05() {
		parseESWithWarning("\"Hello\\512World\"");
	}

	@Test
	public void testBadEscapement_06() {
		parseESWithWarning("\"Hello\\612World\"");
	}

	@Test
	public void testBadEscapement_07() {
		parseESWithWarning("\"Hello\\712World\"");
	}

}
