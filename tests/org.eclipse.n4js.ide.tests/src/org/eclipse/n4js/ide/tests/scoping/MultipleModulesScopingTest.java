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
package org.eclipse.n4js.ide.tests.scoping;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Tests for building yarn workspaces, in particular certain corner and special cases such as "yarn link".
 */
@SuppressWarnings("javadoc")
public class MultipleModulesScopingTest extends AbstractIdeTest {

	@Test
	public void testTwoN4jsModulesWithSameName() throws Exception {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"Project", Map.of(
						"Client", """
								import {C_P1} from "Module";
								""",
						CFG_DEPENDENCIES, """
								P1,
								P2
								"""),
				"P1", Map.of(
						"Module", """
								export public class C_P1 {
									b : string
								}
								"""),
				"P2", Map.of(
						"Module", """
								export public class C_P2 {
									b : string
								}
								""")));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("Client", List.of(
						"(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/Module.n4js, yarn-test-project/packages/P2/src/Module.n4js.)")));
	}

	@Test
	public void testN4jsdAndJsModulesWithSameNameInDifferentProjects() throws Exception {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"Project", Map.of(
						"Client", """
								import {C_P1} from "Module";
								""",
						CFG_DEPENDENCIES, """
								P1,
								PLAINJS
								"""),
				"P1", Map.of(
						"Module.n4jsd", """
								export external public class C_P1 {
									b : string
								}
								"""),
				"PLAINJS", Map.of(
						CFG_SOURCE_FOLDER, ".",
						"Module.js", """
								export const C = 2;
								""",
						"package.json", """
								{
									"name": "PLAINJS"
								}
								""")));
		startAndWaitForLspServer();
		assertIssues2(Pair.of("Client", List.of(
				"(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/Module.n4jsd, yarn-test-project/packages/PLAINJS/Module.js.)")));
	}

	@Test
	public void testN4jsAndJsModulesWithSameNameInSameProjectError() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Client", """
						import * as M from "Module";
						M;
						""",
				"Module", """
						export const C = 1;
						""",
				"/src-js/Module.js", """
						export const C = 2;
						""",
				"package.json", """
						{
						    "name": "test-project",
						    "n4js": {
						        "projectType": "library",
						        "output": "src-gen",
						        "sources": {
						            "source": [
						                "src"
						            ],
									"external": [
										"src-js"
									]
						        }
						    },
						    "dependencies": {
						        "n4js-runtime": ""
						    }
						}
						"""));
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("Module", List.of(
						"(Error, [0:0 - 0:19], A duplicate module Module is also defined in test-project/src-js/Module.js.)")),
				Pair.of("Client", List.of(
						"(Error, [0:19 - 0:27], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-project/src/Module.n4js, test-project/src-js/Module.js.)")));
	}

	@Test
	public void testN4jsdAndJsModulesWithSameNameInSameProjectOK() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Client", """
						import * as M from "Module";
						M;
						""",
				"Module.n4jsd", """
						export external const C;
						""",
				"/src-js/Module.js", """
						export const C = 2;
						""",
				"package.json", """
						{
						    "name": "test-project",
						    "n4js": {
						        "projectType": "library",
						        "output": "src-gen",
						        "sources": {
						            "source": [
						                "src"
						            ],
									"external": [
										"src-js"
									]
						        }
						    },
						    "dependencies": {
						        "n4js-runtime": ""
						    }
						}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testN4jsdAndJsModulesWithSameNameInSameSourceFolderOK() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Client", """
						import {C} from "Module";
						C;
						""",
				"Module.n4jsd", """
						export external const C;
						""",
				"Module.js", """
						export const C = 2;
						"""));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testDynamicImportWarning() throws Exception {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"Client", """
						import * as M+ from "Module";
						M.C;
						""",
				"Module.n4jsd", """
						export external const C;
						""",
				"Module.js", """
						export const C = 2;
						"""));
		startAndWaitForLspServer();
		assertIssues2(Pair.of("Client",
				List.of("(Warning, [0:7 - 0:14], The n4jsd module Module should not be imported dynamically.)")));
	}

}
