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

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Test how the builder handles N4JS files that are NOT contained in a source folder.
 */
@SuppressWarnings("javadoc")
public class IncrementalBuilderNonSourceFileTest extends AbstractIncrementalBuilderTest {

	@Test
	public void testValidationOfNonSourceFile() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							console.log('hello');
						"""));
		File projectRoot = getProjectRoot();
		FileURI nonSourceModule = toFileURI(new File(projectRoot, "NonSourceModule.n4js"));
		createFileOnDiskWithoutNotification(nonSourceModule, """
					let bad: string = 42; // error intended
				""");

		startAndWaitForLspServer();
		assertNoIssues(); // initial build must not show issues in non-source files

		sendDidChangeWatchedFiles(nonSourceModule);
		joinServerRequests();
		assertNoIssues(); // incremental build must not show issues in non-source files

		openFile(nonSourceModule);
		joinServerRequests();
		assertIssues2( // but an open editor shows issues even in non-source files
				Pair.of("NonSourceModule", List.of(
						"(Error, [0:19 - 0:21], 42 is not a subtype of string.)")));

		closeFile(nonSourceModule);
		joinServerRequests();
		assertNoIssues(); // issues of non-source files disappear after closing the editor
	}
}
