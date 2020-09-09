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
package org.eclipse.n4js.ide.tests.builder

import java.io.File
import org.junit.Test

/**
 * Test how the builder handles N4JS files that are NOT contained in a source folder.
 */
class IncrementalBuilderNonSourceFileTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testValidationOfNonSourceFile() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				console.log('hello');
			'''
		);
		val projectRoot = getProjectRoot();
		val nonSourceModule = new File(projectRoot, "NonSourceModule.n4js").toFileURI;
		createFileOnDiskWithoutNotification(nonSourceModule, '''
			let bad: string = 42; // error intended
		''');

		startAndWaitForLspServer();
		assertNoIssues(); // initial build must not show issues in non-source files

		sendDidChangeWatchedFiles(nonSourceModule);
		joinServerRequests();
		assertNoIssues(); // incremental build must not show issues in non-source files

		openFile(nonSourceModule);
		joinServerRequests();
		assertIssues( // but an open editor shows issues even in non-source files
			"NonSourceModule" -> #[
				"(Error, [0:18 - 0:20], int is not a subtype of string.)"
			]
		);

		closeFile(nonSourceModule);
		joinServerRequests();
		assertNoIssues(); // issues of non-source files disappear after closing the editor
	}
}
