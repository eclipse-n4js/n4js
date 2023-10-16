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

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_12_03_EmptyStatementTest extends AbstractParserTest {

	@Test
	public void testEmptyFile() {
		Script script = parseESSuccessfully("");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testOnlyLineBreak() {
		Script script = parseESSuccessfully("\n");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testOnlyComment() {
		Script script = parseESSuccessfully("/*\n*/");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testOnlyLineComment() {
		Script script = parseESSuccessfully("// comment ");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testOnlyEmptyLineComment() {
		Script script = parseESSuccessfully("//");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testOnlyLineCommentWithLineBreak() {
		Script script = parseESSuccessfully("// comment\n");
		assertTrue(script.getScriptElements().isEmpty());
	}

	@Test
	public void testEmptyStatement() {
		Script script = parseESSuccessfully(";");
		assertTrue(script.getScriptElements().get(0) instanceof EmptyStatement);
	}

	@Test
	public void testEmptyStatementAfterLineBreak() {
		Script script = parseESSuccessfully("\r\n;");
		assertTrue(script.getScriptElements().get(0) instanceof EmptyStatement);
	}

}
