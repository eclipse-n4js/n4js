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
package org.eclipse.n4js.ide.tests.codeActions;

import java.util.Map;

import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.n4js.ide.tests.helper.server.AbstractCodeActionTest;
import org.junit.Test;

/**
 * Tests the quick fix for adding missing imports for an unresolved reference.
 */
public class AddMissingImportQuickfixTest extends AbstractCodeActionTest {

	private static Map<String, String> testModules = Map.of(
			"A", """
					export public class SomeClass {}
					""",
			"Main*", """
					// comment
					SomeClass;
					""");

	// @formatter:off
	private static String expectedCodeActionsStr = """
			title      : Add import from module A
			kind       : quickfix
			command    :%s
			diagnostics: (CODE:org.eclipse.xtext.diagnostics.Diagnostic.Linking, Error, , [1:0 - 1:9], Couldn't resolve reference to IdentifiableElement 'SomeClass'., [])
			edit       : ([],%s
			   test-project/src/Main.n4js -> [([0:10 - 0:10],%s
			import {SomeClass} from "A";)])
			-----
			title      : N4JS: Organize Imports
			kind       : source.organizeImports
			command    : (N4JS: Organize Imports, n4js.organizeImports, [test-project/src/Main.n4js])
			diagnostics:%s
			edit       :
			""".formatted(" ", " ", " ", " "); // auto-formatting bug in Java removes trailing whitespace inside text blocks
	// @formatter:on

	/**
	 * When the client requests available quick fixes for the hover, it sets the {@link CodeActionParams}'s range to the
	 * range of the issue being hovered over.
	 */
	@Test
	public void testAddImportViaHover() {
		// non-empty region denoting the region of the issue
		N4JSTestCodeActionConfiguration config = new N4JSTestCodeActionConfiguration();
		config.setLine(1);
		config.setColumn(0);
		config.setEndLine(1);
		config.setEndColumn("SomeClass".length());
		config.setExpectedCodeActions(expectedCodeActionsStr);
		test(testModules, config);
	}

	/**
	 * When the client requests available quick fixes for the quick fix keyboard shortcut (i.e. CMD + '.'), it sets the
	 * {@link CodeActionParams}'s range to the cursor position, i.e. an empty range.
	 */
	@Test
	public void testAddImportViaQuickfixKeyboadShortcut() {
		// empty region denoting cursor position somewhere inside "SomeClass"
		N4JSTestCodeActionConfiguration config = new N4JSTestCodeActionConfiguration();
		config.setLine(1);
		config.setColumn(3);
		config.setEndLine(1);
		config.setEndColumn(3);
		config.setExpectedCodeActions(expectedCodeActionsStr);
		test(testModules, config);
	}
}
