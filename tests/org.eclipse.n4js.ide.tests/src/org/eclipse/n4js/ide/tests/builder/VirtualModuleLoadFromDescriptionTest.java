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
package org.eclipse.n4js.ide.tests.builder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test to ensure that virtual modules from dependencies are loaded from description correctly.
 */

public class VirtualModuleLoadFromDescriptionTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testChangeFileInSingleProject() throws InterruptedException, ExecutionException {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(Map.of(
				CFG_NODE_MODULES + "mydep", Map.of(
						"index.d.ts", """
									declare module "dep" {
										/** My Docu of K */
										export const K : number;
									}
								""",
						"index.js", """
									// dummy
								"""),
				"myproject", Map.of(
						"MyModule", """
									import {K} from "dep";
									K;
								""",
						CFG_DEPENDENCIES, """
									mydep
								""")));
		getFileURIFromModuleName("index.d.ts").toURI();

		startAndWaitForLspServer();
		assertNoIssues();

		openFile("MyModule");

		HoverParams params = new HoverParams();
		String uri = getFileURIFromModuleName("MyModule").toString();
		params.setTextDocument(new TextDocumentIdentifier(uri));
		params.setPosition(new Position(0, 8));

		CompletableFuture<Hover> future = languageServer.hover(params);
		Hover hoverResult = future.get();

		Assert.assertNotNull(hoverResult.getRange());
		Assert.assertNotNull(hoverResult.getRange().getStart());
		Assert.assertNotNull(hoverResult.getRange().getEnd());

		joinServerRequests();
		assertNoIssues();
	}

}
