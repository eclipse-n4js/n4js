/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import static org.junit.Assert.assertEquals;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.ide.tests.helper.server.AbstractCodeActionTest;
import org.eclipse.n4js.xtext.ide.server.XDocument;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

/**
 * Test for code actions in n4js
 */
@SuppressWarnings("javadoc")
public class CodeActionTest extends AbstractCodeActionTest {

	@Test
	public void test_01() throws Exception {
		N4JSTestCodeActionConfiguration config = new N4JSTestCodeActionConfiguration();
		config.setModel("class X { i: int?; }");
		config.setColumn(13);
		config.setExpectedCodeActions(
				"""
						title      : Change to new syntax
						kind       : quickfix
						command    :\s
						diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:13 - 0:17], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
						edit       : ([],\s
						   test-project/src/MyModule.n4js -> [([0:16 - 0:17], ), ([0:11 - 0:11], ?)])""");

		config.setAssertCodeActions((list) -> {
			assertEquals("class X { i?: int; }", modify(config.getModel(), list.get(0).getRight()));
			assertEquals(config.getExpectedCodeActions(), getStringLSP4J().toString(list.get(0).getRight()));
		});

		test(config);
	}

	@Test
	public void test_02() throws Exception {
		N4JSTestCodeActionConfiguration config = new N4JSTestCodeActionConfiguration();
		config.setModel("class X { i?: int?; }");
		config.setColumn(14);
		config.setExpectedCodeActions(
				"""
						title      : Change to new syntax
						kind       : quickfix
						command    :\s
						diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:14 - 0:18], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
						edit       : ([],\s
						   test-project/src/MyModule.n4js -> [([0:17 - 0:18], )])""");

		config.setAssertCodeActions((list) -> {
			assertEquals("class X { i?: int; }", modify(config.getModel(), list.get(0).getRight()));
			assertEquals(config.getExpectedCodeActions(), getStringLSP4J().toString(list.get(0).getRight()));
		});

		test(config);
	}

	@Test
	public void test_03() throws Exception {
		N4JSTestCodeActionConfiguration config = new N4JSTestCodeActionConfiguration();
		config.setModel("class X { i?: int ? ; }");
		config.setColumn(14);
		config.setExpectedCodeActions(
				"""
						title      : Change to new syntax
						kind       : quickfix
						command    :\s
						diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:14 - 0:19], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
						edit       : ([],\s
						   test-project/src/MyModule.n4js -> [([0:18 - 0:19], )])""");

		config.setAssertCodeActions((list) -> {
			assertEquals("class X { i?: int  ; }", modify(config.getModel(), list.get(0).getRight()));
			assertEquals(config.getExpectedCodeActions(), getStringLSP4J().toString(list.get(0).getRight()));
		});

		test(config);
	}

	private String modify(String model, CodeAction action) {
		Iterable<TextEdit> changes = IterableExtensions.flatten(action.getEdit().getChanges().values());
		return new XDocument(0, model).applyChanges(changes).getContents();
	}

}