/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.contentAssist;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for bug GH-2204.
 */
public class ContentAssistBugGH2204IdeTest extends AbstractIdeTest {

	@Test
	public void testContentAssistForClassMemberOfDefinitionProjectDoesNotAddImport()
			throws InterruptedException, ExecutionException, TimeoutException {

		testWorkspaceManager.createTestOnDisk(
				Pair.of(CFG_NODE_MODULES + "@n4jsd/immutable", List.of(
						Pair.of("immutable.n4jsd", """
									export external public class SomeClass {
										public someMethod()
									}
								"""),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "@n4jsd/immutable",
										"version": "0.0.1",
										"n4js": {
											"projectType": "definition",
											"definesPackage": "immutable",
											"mainModule": "immutable",
											"vendorId": "org.eclipse.n4js",
											"sources": {
												"source": [
													"src"
												]
											}
										}
									}
								"""))),
				Pair.of(CFG_NODE_MODULES + "immutable", List.of(
						Pair.of("immutable.js", """
									// content does not matter
								"""),
						Pair.of(CFG_SOURCE_FOLDER, "."),
						Pair.of(PACKAGE_JSON, """
									{
										"name": "immutable",
										"version": "0.0.1"
									}
								"""))),
				Pair.of("N4jsProject", List.of(
						Pair.of("N4jsModule", """
									import {SomeClass} from "immutable";
									let x: SomeClass;
									x.s
								"""),
						Pair.of(CFG_DEPENDENCIES, """
									@n4jsd/immutable,
									immutable
								"""))));
		startAndWaitForLspServer();
		assertIssues2(List.of(
				Pair.of("N4jsModule", List.of(
						"(Error, [2:3 - 2:4], Couldn't resolve reference to IdentifiableElement 's'.)"))));

		FileURI fileURI = getFileURIFromModuleName("N4jsModule");
		openFile(fileURI);

		Either<List<CompletionItem>, CompletionList> result = callCompletion(fileURI.toString(), 2, 4).get(5,
				TimeUnit.SECONDS);
		List<CompletionItem> items = result.isLeft() ? result.getLeft() : result.getRight().getItems();
		String actualItemsStr = Strings.join("\n", item -> getStringLSP4J().toString(item), items);

		// ensure content assist won't add an import:
		Assert.assertEquals("(someMethod, Method, someMethod, , , 00000, , , , ([2:3 - 2:4], someMethod), [], [], , )",
				actualItemsStr);
		// bogus result was: (someMethod, Method, via new alias Alias_SomeMethod for someMethod\n\nIntroduces the new
		// alias 'Alias_SomeMethod' for element someMethod, , , 00000, , , , ([2:2 - 2:3], Alias_SomeMethod), [([0:36 -
		// 0:36], import {someMethod as Alias_SomeMethod} from "immutable";)], [], , )
	}
}
