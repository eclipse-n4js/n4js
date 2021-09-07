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
package org.eclipse.n4js.tests.contentAssist

import java.util.concurrent.TimeUnit
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.utils.Strings
import org.junit.Assert
import org.junit.Test

/**
 * Test for bug GH-2204.
 */
class ContentAssistBugGH2204IdeTest extends AbstractIdeTest {

	@Test
	def void testContentAssistForClassMemberOfDefinitionProjectDoesNotAddImport() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "@n4jsd/immutable" -> #[
				"immutable.n4jsd" -> '''
					export external public class SomeClass {
						public someMethod()
					}
				''',
				PACKAGE_JSON -> '''
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
				'''
			],
			CFG_NODE_MODULES + "immutable" -> #[
				"immutable.js" -> '''
					// content does not matter
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "immutable",
						"version": "0.0.1"
					}
				'''
			],
			"N4jsProject" -> #[
				"N4jsModule" -> '''
					import {SomeClass} from "immutable";
					let x: SomeClass;
					x.s
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/immutable,
					immutable
				'''
			]
		);
		startAndWaitForLspServer();
		assertIssues(#[
			"N4jsModule" -> #[
				"(Error, [2:2 - 2:3], Couldn't resolve reference to IdentifiableElement 's'.)"
			]
		]);

		val fileURI = getFileURIFromModuleName("N4jsModule");
		openFile(fileURI);

		val result = callCompletion(fileURI.toString, 2, 3).get(5, TimeUnit.SECONDS);
		val items = result.isLeft() ? result.getLeft() : result.getRight().getItems();
		val actualItemsStr = Strings.join("\n", [getStringLSP4J().toString(it)], items);

		// ensure content assist won't add an import:
		Assert.assertEquals("(someMethod, Method, someMethod, , , 00000, , , , ([2:2 - 2:3], someMethod), [], [], , )", actualItemsStr);
		// bogus result was: (someMethod, Method, via new alias Alias_SomeMethod for someMethod\n\nIntroduces the new alias 'Alias_SomeMethod' for element someMethod, , , 00000, , , , ([2:2 - 2:3], Alias_SomeMethod), [([0:36 - 0:36], import {someMethod as Alias_SomeMethod} from "immutable";)], [], , )
	}
}
