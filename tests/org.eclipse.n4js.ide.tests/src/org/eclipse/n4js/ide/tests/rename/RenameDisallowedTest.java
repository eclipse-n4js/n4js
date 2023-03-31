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
package org.eclipse.n4js.ide.tests.rename;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameDefaultBehavior;
import org.eclipse.lsp4j.PrepareRenameParams;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Test;

/**
 * Test cases in which rename refactoring is disallowed.
 */
@SuppressWarnings("javadoc")
public class RenameDisallowedTest extends AbstractIdeTest {

	@Test
	public void testDisallowRenamingBuiltInType() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Main", """
						let s: string;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		assertRenameIsDisallowedAt("Main", 0, 10); // zero-based
	}

	@Test
	public void testDisallowRenamingTypeFromNodeModules() throws Exception {
		testWorkspaceManager.createTestOnDisk(Map.of(
				CFG_NODE_MODULES + "ProjectInNodeModules", Map.of(
						"ModuleInNodeModules", """
								export public class SomeClass {}
								export public const someConst = 42;
								"""),
				"MainProject", Map.of(
						"Main", """
								import {SomeClass, someConst} from "ModuleInNodeModules";
								SomeClass;
								someConst;
								""",
						CFG_DEPENDENCIES, """
								ProjectInNodeModules
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		assertRenameIsDisallowedAt("Main", 1, 4); // zero-based
		assertRenameIsDisallowedAt("Main", 2, 4); // zero-based
	}

	private void assertRenameIsDisallowedAt(String moduleName, int line, int column) throws Exception {
		if (!isOpen(moduleName)) {
			// file must be open before a rename can be initiated
			openFile(moduleName);
			joinServerRequests();
		}
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		PrepareRenameParams prepareRenameParams = new PrepareRenameParams();
		prepareRenameParams.setTextDocument(new TextDocumentIdentifier(fileURI.toString()));
		prepareRenameParams.setPosition(new Position(line, column));
		Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior> result = languageServer
				.prepareRename(prepareRenameParams).get();
		assertTrue("expected rename to be disallowed but it is allowed",
				result == null || (result.getLeft() == null && result.getRight() == null));
	}
}
