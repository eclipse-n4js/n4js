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
package org.eclipse.n4js.ide.tests.codeActions

import org.eclipse.lsp4j.CodeActionParams
import org.eclipse.n4js.ide.tests.helper.server.AbstractCodeActionTest
import org.junit.Test

/**
 * Tests the quick fix for adding missing imports for an unresolved reference.
 */
class AddMissingImportQuickfixTest extends AbstractCodeActionTest {

	private static val testModules = #[
		"A" -> '''
			export public class SomeClass {}
		''',
		"Main*" -> '''
			// comment
			SomeClass;
		'''
	];

	private static val expectedCodeActionsStr = '''
		title      : Add import from module A
		kind       : quickfix
		command    : 
		diagnostics: (CODE:org.eclipse.xtext.diagnostics.Diagnostic.Linking, Error, , [1:0 - 1:9], Couldn't resolve reference to IdentifiableElement 'SomeClass'., [])
		edit       : ([], 
		   test-project/src/Main.n4js -> [([0:10 - 0:10], 
		import {SomeClass} from "A";)])
		-----
		title      : N4JS: Organize Imports
		kind       : source.organizeImports
		command    : (N4JS: Organize Imports, n4js.organizeImports, [test-project/src/Main.n4js])
		diagnostics: 
		edit       : 
	''';

	/**
	 * When the client requests available quick fixes for the hover, it sets the
	 * {@link CodeActionParams}'s range to the range of the issue being hovered over.
	 */
	@Test
	def void testAddImportViaHover() {
		test(testModules, new N4JSTestCodeActionConfiguration => [
			// non-empty region denoting the region of the issue
			line = 1
			column = 0
			endLine = 1
			endColumn = "SomeClass".length
			expectedCodeActions = expectedCodeActionsStr
		]);
	}

	/**
	 * When the client requests available quick fixes for the quick fix keyboard shortcut (i.e. CMD + '.'),
	 * it sets the {@link CodeActionParams}'s range to the cursor position, i.e. an empty range.
	 */
	@Test
	def void testAddImportViaQuickfixKeyboadShortcut() {
		test(testModules, new N4JSTestCodeActionConfiguration => [
			// empty region denoting cursor position somewhere inside "SomeClass"
			line = 1
			column = 3
			endLine = 1
			endColumn = 3
			expectedCodeActions = expectedCodeActionsStr
		]);
	}
}
