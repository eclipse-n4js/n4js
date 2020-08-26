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
package org.eclipse.n4js.ide.tests.rename

import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.junit.Test

import static org.junit.Assert.*

/**
 * Test cases in which rename refactoring is disallowed.
 */
class RenameDisallowedTest extends AbstractIdeTest {

	@Test
	def void testDisallowRenamingBuiltInType() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				let s: string;
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		assertRenameIsDisallowedAt("Main", 0, 10); // zero-based
	}

	@Test
	def void testDisallowRenamingTypeFromNodeModules() throws Exception {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			TestWorkspaceManager.CFG_NODE_MODULES + "ProjectInNodeModules" -> #[
				"ModuleInNodeModules" -> '''
					export public class SomeClass {}
					export public const someConst = 42;
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {SomeClass, someConst} from "ModuleInNodeModules";
					SomeClass;
					someConst;
				''',
				"#DEPENDENCY" -> '''
					n4js-runtime,
					ProjectInNodeModules
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		assertRenameIsDisallowedAt("Main", 1, 4); // zero-based
		assertRenameIsDisallowedAt("Main", 2, 4); // zero-based
	}

	def private void assertRenameIsDisallowedAt(String moduleName, int line, int column) throws Exception {
		if (!isOpen(moduleName)) {
			// file must be open before a rename can be initiated
			openFile(moduleName);
			joinServerRequests();
		}
		val fileURI = getFileURIFromModuleName(moduleName);
		val textDocumentPositionParams = new TextDocumentPositionParams();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(fileURI.toString()));
		textDocumentPositionParams.setPosition(new Position(line, column));
		val result = languageServer.prepareRename(textDocumentPositionParams).get();
		assertTrue("expected rename to be disallowed but it is allowed",
			result === null || (result.getLeft() === null && result.getRight() === null)
		);
	}
}
