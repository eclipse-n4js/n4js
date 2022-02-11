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

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test for package.json property n4js->generator->source-maps
 */
public class ModuleCollisionSingleProjectTest extends AbstractIdeTest {

	/** */
	@Test
	public void testModuleCollision2N4JS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule",
						"export public class C1 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src2/MyModule",
						"export public class C2 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\",\n"
								+ "				\"src2\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src2/MyModule.n4js.)")),
				Pair.of("src2/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src1/MyModule.n4js.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/src1/MyModule.n4js, test-workspace/test-project/src2/MyModule.n4js.)")));
	}

	/** */
	@Test
	public void testModuleCollision3N4JS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule",
						"export public class C1 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src2/MyModule",
						"export public class C2 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("tst/MyModule",
						"export public class C3 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\",\n"
								+ "				\"src2\"\n"
								+ "			],\n"
								+ "			\"test\": [\n"
								+ "				\"tst\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src2/MyModule.n4js; test-project/tst/MyModule.n4js.)")),
				Pair.of("src2/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src1/MyModule.n4js; test-project/tst/MyModule.n4js.)")),
				Pair.of("tst/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src1/MyModule.n4js; test-project/src2/MyModule.n4js.)")),

				Pair.of("test-project/package.json", List.of(
						"(Error, [1:9 - 1:23], Project with source folder of type test should depend on org.eclipse.n4js.mangelhaft.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/tst/MyModule.n4js, test-workspace/test-project/src1/MyModule.n4js, test-workspace/test-project/src2/MyModule.n4js.)")));

	}

	/** */
	@Test
	public void testModuleCollisionN4JSD() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule",
						"export public class C1 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src2/MyModule.n4jsd",
						"export external public class C2 {\n"
								+ "	public field: string;\n"
								+ "}"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\",\n"
								+ "				\"src2\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src2/MyModule.n4jsd.)")),
				Pair.of("src2/MyModule.n4jsd", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src1/MyModule.n4js.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/src1/MyModule.n4js, test-workspace/test-project/src2/MyModule.n4jsd.)")));

	}

	/** */
	@Test
	public void testModuleCollisionN4JSX() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule",
						"export public class C1 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("src2/MyModule.n4jsx",
						"export public class C2 {\n"
								+ "	public field: string;\n"
								+ "}"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\",\n"
								+ "				\"src2\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src2/MyModule.n4jsx.)")),
				Pair.of("src2/MyModule.n4jsx", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/src1/MyModule.n4js.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/src1/MyModule.n4js, test-workspace/test-project/src2/MyModule.n4jsx.)")));

	}

	/** */
	@Test
	public void testModuleCollisionJS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule",
						"export public class C1 {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of("ext/MyModule.js",
						"export const K = 0;"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\"\n"
								+ "			],\n"
								+ "			\"external\": [\n"
								+ "				\"ext\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4js", List.of(
						"(Error, [0:0 - 2:1], A duplicate module MyModule is also defined in test-project/ext/MyModule.js.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/src1/MyModule.n4js, test-workspace/test-project/ext/MyModule.js.)")));

	}

	/** */
	@Test
	public void testModuleNoCollisionJS_N4JSD() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule.n4jsd",
						"export external public const K : number;"),
				Pair.of("ext/MyModule.js",
						"export const K = 0;"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\"\n"
								+ "			],\n"
								+ "			\"external\": [\n"
								+ "				\"ext\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertNoErrors();
	}

	/** */
	@Test
	public void testModuleNoCollisionJS_DTS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule.d.ts",
						"export const K : number;"),
				Pair.of("ext/MyModule.js",
						"export const K = 0;"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\"\n"
								+ "			],\n"
								+ "			\"external\": [\n"
								+ "				\"ext\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertNoErrors();
	}

	/** */
	@Test
	public void testModuleCollisionJS_N4JSD_DTS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule.d.ts",
						"export const K : number;"),
				Pair.of("src1/MyModule.n4jsd",
						"export external public const K : number;"),
				Pair.of("ext/MyModule.js",
						"export const K = 0;"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\"\n"
								+ "			],\n"
								+ "			\"external\": [\n"
								+ "				\"ext\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4jsd", List.of(
						"(Error, [0:0 - 0:40], A duplicate definition module MyModule for test-project/ext/MyModule.js is also defined in test-project/src1/MyModule.d.ts.)")));
		// note that in this case, the import in Client.n4js will bind to MyModule.n4jsd
	}

	/** */
	@Test
	public void testModuleCollisionN4JSD_DTS() {
		test(
				Pair.of(CFG_SOURCE_FOLDER, ""), // manually define source folder in module names

				Pair.of("src1/MyModule.d.ts",
						"export const K : number;"),
				Pair.of("src1/MyModule.n4jsd",
						"export external public const K : number;"),
				Pair.of("src1/Client",
						"import * as A from \"MyModule\";\n"
								+ "A;"),

				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"test-project\",\n"
								+ "	\"version\": \"0.0.1\",\n"
								+ "	\"dependencies\": {\n"
								+ "        \"n4js-runtime\": \"*\"\n"
								+ "	},"
								+ "	\"n4js\": {\n"
								+ "		\"projectType\": \"library\",\n"
								+ "		\"vendorId\": \"org.eclipse.n4js\",\n"
								+ "		\"vendorName\": \"Eclipse N4JS Project\",\n"
								+ "		\"output\": \"src-gen\",\n"
								+ "		\"sources\": {\n"
								+ "			\"source\": [\n"
								+ "				\"src1\"\n"
								+ "			]\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertIssues(
				Pair.of("src1/MyModule.n4jsd", List.of(
						"(Error, [0:0 - 0:40], A duplicate definition module MyModule for unknown js module is also defined in test-project/src1/MyModule.d.ts.)")),
				Pair.of("src1/Client.n4js", List.of(
						"(Error, [0:19 - 0:29], Cannot resolve plain module specifier (without project name as first segment): multiple matching modules found: test-workspace/test-project/src1/MyModule.d.ts, test-workspace/test-project/src1/MyModule.n4jsd.)")));

	}

	@SafeVarargs
	final void test(Pair<String, String>... projectsModulesContents) {
		testWorkspaceManager.createTestProjectOnDisk(projectsModulesContents);
		startAndWaitForLspServer();
	}
}
