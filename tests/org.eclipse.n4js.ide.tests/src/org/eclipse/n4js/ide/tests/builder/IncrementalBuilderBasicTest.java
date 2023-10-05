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
package org.eclipse.n4js.ide.tests.builder;

import java.util.Map;

import org.junit.Test;

/**
 * Basic tests for the incremental builder.
 */

public class IncrementalBuilderBasicTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testBuildAfterServerInitialization() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"OtherProject", Map.of(
						"Other", """
									export public class Other {
										public mOther() {}
									}
								"""),
				"MainProject", Map.of(
						"A", """
									export public class A {
										public ma() {}
									}
								""",
						"Main", """
									import {A} from "A";
									import {Other} from "Other";

									new A().ma();
									new Other().mOther();
								""",
						CFG_DEPENDENCIES, """
									OtherProject
								""")));
		startAndWaitForLspServer();

		assertNoIssues();

		assertOutputFileExists("OtherProject", "Other");
		assertOutputFileExists("MainProject", "A");
		assertOutputFileExists("MainProject", "Main");
	}
}
