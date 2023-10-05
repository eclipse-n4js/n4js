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

import org.junit.Test;

/**
 * Test for build order
 */

public class ProjectNameDiffersFromFolderTest extends AbstractBuildOrderTest {

	@Test
	public void testDiffName() {
		test("yarn-test-project, yarn-test-project/packages/P1",
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"P1", Map.of(
								PACKAGE_JSON, """
											{
												"name": "Not-P1",
												"version": "0.0.1",
												"n4js": {
													"projectType": "library",
													"sources": {
														"source": [
															"src"
														]
													}
												}
											}
										""")));
	}

	@Test
	public void testScopedProjectDiffName1() {
		test("yarn-test-project, yarn-test-project/packages/@MyScope/P1",
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"@MyScope/P1", Map.of(
								PACKAGE_JSON, """
											{
												"name": "@OtherScope/Not-P1",
												"version": "0.0.1",
												"n4js": {
													"projectType": "library",
													"sources": {
														"source": [
															"src"
														]
													}
												}
											}
										""")));
	}

	@Test
	public void testScopedProjectDiffName2() {
		test("yarn-test-project, yarn-test-project/packages/MissingScope-P1",
				Map.of(
						CFG_NODE_MODULES + "n4js-runtime", Map.of(),
						"MissingScope-P1", Map.of(
								PACKAGE_JSON, """
											{
												"name": "@OtherScope/P1",
												"version": "0.0.1",
												"n4js": {
													"projectType": "library",
													"sources": {
														"source": [
															"src"
														]
													}
												}
											}
										""")));
	}

}
