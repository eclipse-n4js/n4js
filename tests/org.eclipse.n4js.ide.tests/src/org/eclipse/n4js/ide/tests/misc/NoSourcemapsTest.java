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

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test for package.json property n4js->generator->source-maps
 */
public class NoSourcemapsTest extends AbstractIdeTest {

	/** Enable source maps */
	@Test
	public void testEnableSourceMaps() {
		test(
				Pair.of("MyModule",
						"export public class C {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"generator-sourcemaps\",\n"
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
								+ "				\"src\"\n"
								+ "			]\n"
								+ "		},\n"
								+ "		\"generator\": {\n"
								+ "			\"source-maps\": true\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertFile("MyModule.map");
	}

	/** Disable source maps */
	@Test
	public void testDisableSourceMaps() {
		test(
				Pair.of("MyModule",
						"export public class C {\n"
								+ "	public field: string = \"test value\";\n"
								+ "}"),
				Pair.of(PACKAGE_JSON,
						"{\n"
								+ "	\"name\": \"generator-sourcemaps\",\n"
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
								+ "				\"src\"\n"
								+ "			]\n"
								+ "		},\n"
								+ "		\"generator\": {\n"
								+ "			\"source-maps\": false\n"
								+ "		}\n"
								+ "	}\n"
								+ "}")

		);

		assertNoFile("MyModule.map");
	}

	@SafeVarargs
	final void test(Pair<String, String>... projectsModulesContents) {
		testWorkspaceManager.createTestProjectOnDisk(projectsModulesContents);
		startAndWaitForLspServer();

		assertNoErrors();
	}
}
