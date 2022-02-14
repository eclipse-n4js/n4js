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
package org.eclipse.n4js.ide.tests.misc;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test for package.json property n4js->generator->source-maps
 */
public class ModuleCollisionMultipleProjectsTest extends AbstractIdeTest {

	/** */
	@Test
	public void testModuleCollision2N4JS() {
		test(Map.of("P1", Map.of(
				"MyModule",
				"export public class C1 {\n"
						+ "	public field: string = \"test value\";\n"
						+ "}"),
				"P2", Map.of(
						"MyModule",
						"export public class C2 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				"Client", Map.of(
						"Client",
						"import * as A from \"MyModule\";\n"
								+ "A;",

						CFG_DEPENDENCIES, "P1, P2")));

		assertIssues(
				Pair.of("Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/MyModule.n4js, yarn-test-project/packages/P2/src/MyModule.n4js.)")));
	}

	/** */
	@Test
	public void testModuleCollisionN4JSD() {
		test(Map.of("P1", Map.of(
				"MyModule",
				"export public class C1 {\n"
						+ "	public field: string = \"test value\";\n"
						+ "}"),
				"P2", Map.of(
						"MyModule.n4jsd",
						"export external public class C2 {\n"
								+ "	public field: string;\n"
								+ "}"),
				"Client", Map.of(
						"Client",
						"import * as A from \"MyModule\";\n"
								+ "A;",

						CFG_DEPENDENCIES, "P1, P2")));

		assertIssues(
				Pair.of("Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/MyModule.n4js, yarn-test-project/packages/P2/src/MyModule.n4jsd.)")));
	}

	/** */
	@Test
	public void testModuleCollisionN4JSX() {
		test(Map.of("P1", Map.of(
				"MyModule",
				"export public class C1 {\n"
						+ "	public field: string = \"test value\";\n"
						+ "}"),
				"P2", Map.of(
						"MyModule.n4jsx",
						"export public class C2 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				"Client", Map.of(
						"Client",
						"import * as A from \"MyModule\";\n"
								+ "A;",

						CFG_DEPENDENCIES, "P1, P2")));

		assertIssues(
				Pair.of("Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/MyModule.n4js, yarn-test-project/packages/P2/src/MyModule.n4jsx.)")));
	}

	/** */
	@Test
	public void testModuleCollisionJS_N4JSD() {
		test(Map.of("P1", Map.of(
				"MyModule.js",
				"export const K = 0;"),
				"P2", Map.of(
						"MyModule.n4jsd",
						"export external public const K;"),
				"Client", Map.of(
						"Client",
						"import * as A from \"MyModule\";\n"
								+ "A;",

						CFG_DEPENDENCIES, "P1, P2")));

		assertIssues(
				Pair.of("Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: yarn-test-project/packages/P1/src/MyModule.js, yarn-test-project/packages/P2/src/MyModule.n4jsd.)")));
	}

	/** */
	@Test
	public void testModuleNoCollisionJS_N4JSD() {
		test(Map.of("P1", Map.of(
				"MyModule.js",
				"export const K = 0;"),
				"P2", Map.of(
						"MyModule.n4jsd",
						"export external public const K;",
						PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"P2\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"definition\",\n"
								+ "		\"definesPackage\": \"P1\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}"),
				"Client", Map.of(
						"Client",
						"import * as A from \"MyModule\";\n"
								+ "A;",

						CFG_DEPENDENCIES, "P1, P2")));

		assertNoErrors();
	}

	/** */
	@Test
	public void testModulePrioritizeNode() {
		test(Map.of("P1", Map.of(
				"util.n4js",
				"export const K = 0;"),
				CFG_NODE_MODULES + "n4js-runtime-node", Map.of(
						"util.n4jsd",
						"export external public const K_Other;"),
				"Client", Map.of(
						"Client",
						"import {K_Other} from \"util\";\n"
								+ "K_Other;",

						CFG_DEPENDENCIES, "P1, n4js-runtime-node")));

		assertNoErrors();
	}

	// FIXME: add collision test of P1/MyModule and local/MyModule

	final void test(Map<String, ? extends Map<String, ? extends CharSequence>> projectsModulesContents) {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(projectsModulesContents);
		startAndWaitForLspServer();
	}
}
