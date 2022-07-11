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
package org.eclipse.n4js.ide.tests.builder

import java.util.concurrent.ExecutionException
import org.eclipse.lsp4j.HoverParams
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.junit.Assert
import org.junit.Test

/**
 * Test to ensure that virtual modules from dependencies are loaded from description correctly.
 */
class VirtualModuleLoadFromDescriptionTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testChangeFileInSingleProject() throws InterruptedException, ExecutionException {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(
			#[
				CFG_NODE_MODULES + "mydep" -> #[
					"index.d.ts" -> '''
						declare module "dep" {
							/** My Docu of K */
							export const K : number;
						}
					''',
					"index.js" -> '''
						// dummy
					'''
				],
				"myproject" -> #[
					"MyModule" -> '''
						import {K} from "dep";
						K;
					''',
					CFG_DEPENDENCIES -> '''
						mydep
					'''
				]
			]
		);
		getFileURIFromModuleName("index.d.ts").toURI;	
		
		startAndWaitForLspServer();
		assertNoIssues();
		
	
		openFile("MyModule");
		
		val params = new HoverParams();
		val uri = getFileURIFromModuleName("MyModule").toString;
		params.textDocument = new TextDocumentIdentifier(uri);
		params.position = new Position(0, 8);

		val future = languageServer.hover(params);
		val hoverResult = future.get;
		
		Assert.assertNotNull(hoverResult.range)
		Assert.assertNotNull(hoverResult.range.start)
		Assert.assertNotNull(hoverResult.range.end)

		joinServerRequests();
		assertNoIssues();
	}
	
}
