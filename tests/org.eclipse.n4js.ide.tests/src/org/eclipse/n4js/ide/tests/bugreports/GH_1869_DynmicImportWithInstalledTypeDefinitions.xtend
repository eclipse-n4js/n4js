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
package org.eclipse.n4js.ide.tests.bugreports

import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.junit.Test

class GH_1869_DynmicImportWithInstalledTypeDefinitions extends AbstractIdeTest {

	@Test
	def void testGH1869() {
		testWorkspaceManager.createTestOnDisk(
			CFG_NODE_MODULES + "some-pkg" -> #[
				"PlainJSModuleWithTypeDefs.js" -> '''
					export const valueWithTypeDef = "hello from plain JS";
				''',
				"PlainJSModule.js" -> '''
					export const value = "hello from plain JS";
				''',
				"MainModule.js" -> '''
					export const value = "hello from the main module";
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "some-pkg",
						"version": "0.0.1",
						"main": "MainModule.js"
					}
				'''
			],
			CFG_NODE_MODULES + "@n4jsd/some-pkg" -> #[
				"PlainJSModuleWithTypeDefs.n4jsd" -> '''
					export external public const valueWithTypeDef: string;
				''',
				CFG_SOURCE_FOLDER -> ".",
				PACKAGE_JSON -> '''
					{
						"name": "@n4jsd/some-pkg",
						"version": "0.0.1",
						"n4js": {
							"projectType": "definition",
							"definesPackage": "some-pkg",
							"sources": {
								"source": [
									"."
								]
							}
						}
					}
				'''
			],
			"MainProject" -> #[
				// just to make sure the n4jsd-project is correctly configured:
				"Test1" -> '''
					import {valueWithTypeDef} from "PlainJSModuleWithTypeDefs";
					let v: string = valueWithTypeDef;
				''',
				// this module specifier form worked before:
				"Test2" -> '''
					import * as N+ from "PlainJSModule";
					let v: any = N.value;
				''',
				// this module specifier form did not work before the fix:
				"Test3" -> '''
					// XPECT noerrors --> "Cannot resolve complete module specifier (with project name as first segment): no matching module found."
					import * as N+ from "some-pkg/PlainJSModule";
					let v: any = N.value;
				''',
				// a main module specified explicitly (worked before):
				"Test4" -> '''
					import * as N+ from "MainModule";
					let v: any = N.value;
				''',
				// a main module specified via a project import (did not work before the fix):
				"Test5" -> '''
					// XPECT noerrors --> "Cannot resolve project import: no matching module found."
					import * as N+ from "some-pkg";
					let v: any = N.value;
				''',
				CFG_DEPENDENCIES -> '''
					@n4jsd/some-pkg,
					some-pkg
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();
	}
}
