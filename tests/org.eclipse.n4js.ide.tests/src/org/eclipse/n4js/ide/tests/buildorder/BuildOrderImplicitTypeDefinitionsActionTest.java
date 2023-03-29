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
package org.eclipse.n4js.ide.tests.buildorder;

import java.util.Map;

import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test for build order
 */
@SuppressWarnings("javadoc")
public class BuildOrderImplicitTypeDefinitionsActionTest extends AbstractBuildOrderTest {

	@Test
	public void testRemoveDepToPlainJS() {
		init(Map.of(
				"P", Map.of(
						CFG_DEPENDENCIES, """
									JS1
								"""),
				"JS1", Map.of(
						"package.json", """
									{
										"name": "JS1"
									}
								"""),
				"@n4jsd/DEF1", Map.of(
						"package.json", """
									{
										"name": "@n4jsd/DEF1",
										"n4js": {
											"projectType": "definition",
											"definesPackage": "JS1"
										},
										"dependencies": {
											"n4js-runtime": ""
										}
									}
								""")));

		assertBuildOrder(
				"yarn-test-project, yarn-test-project/node_modules/n4js-runtime, yarn-test-project/packages/@n4jsd/DEF1, yarn-test-project/packages/JS1, yarn-test-project/packages/P");

		changeNonOpenedFile("P/package.json", Pair.of("\"JS1\"", "JS_UNAVAILABLE"));

		assertBuildOrder(
				"yarn-test-project, yarn-test-project/node_modules/n4js-runtime, yarn-test-project/packages/@n4jsd/DEF1, yarn-test-project/packages/JS1, yarn-test-project/packages/P");
	}

	@Test
	public void testChangeDefinesProperty() {
		init(Map.of(
				"P", Map.of(
						CFG_DEPENDENCIES, """
									JS1
								"""),
				"JS1", Map.of(
						"package.json", """
									{
										"name": "JS1"
									}
								"""),
				"@n4jsd/DEF1", Map.of(
						"package.json", """
									{
										"name": "@n4jsd/DEF1",
										"n4js": {
											"projectType": "definition",
											"definesPackage": "JS1"
										},
										"dependencies": {
											"n4js-runtime": ""
										}
									}
								""")));

		assertBuildOrder(
				"yarn-test-project, yarn-test-project/node_modules/n4js-runtime, yarn-test-project/packages/@n4jsd/DEF1, yarn-test-project/packages/JS1, yarn-test-project/packages/P");

		changeNonOpenedFile("@n4jsd/DEF1/package.json",
				Pair.of("\"definesPackage\": \"JS1\"", "\"definesPackage\": \"JS_UNAVAILABLE\""));

		assertBuildOrder(
				"yarn-test-project, yarn-test-project/node_modules/n4js-runtime, yarn-test-project/packages/@n4jsd/DEF1, yarn-test-project/packages/JS1, yarn-test-project/packages/P");
	}
	// */
}
