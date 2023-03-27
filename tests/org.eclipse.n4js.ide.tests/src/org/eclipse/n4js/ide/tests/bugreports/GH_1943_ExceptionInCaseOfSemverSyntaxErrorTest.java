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
package org.eclipse.n4js.ide.tests.bugreports;

import java.util.List;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * This test is required, because the Xpect tests and Xpect UI tests for package.json files are green even when the
 * exceptions reported in GH-1943 are thrown.
 */
@SuppressWarnings("javadoc")
public class GH_1943_ExceptionInCaseOfSemverSyntaxErrorTest extends AbstractIdeTest {

	@Test
	public void testVersionConstraintWithSyntaxError() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(),
				"ProjectB", Map.of(),
				"ProjectMain", Map.of(
						PACKAGE_JSON, """
									{
										"name": "ProjectMain",
										"version": "0.0.1",
										"n4js": {
											"projectType": "library",
											"output": "src-gen",
											"sources": { "source": ["src"] }
										},
										"dependencies": {
											"n4js-runtime": "*",
											"ProjectA": "^1..3",
											"ProjectB": "^.2.3"
										}
									}
								""")));
		startAndWaitForLspServer();
		assertIssues2(Pair.of(
				"ProjectMain/" + PACKAGE_JSON, List.of(
						"(Error, [10:19 - 10:21], Invalid version number \"^1..3\": no viable alternative at input '.'.)",
						"(Error, [11:17 - 11:21], Invalid version number \"^.2.3\": no viable alternative at input '.'.)")));
		assertNoErrorsInLogOrOutput();
	}
}
