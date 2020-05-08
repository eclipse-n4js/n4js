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
import java.io.IOException
import java.nio.file.Files
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.projectModel.locations.FileURI
import org.junit.Test

/**
 * Builder tests for package.json files.
 */
class IncrementalBuilderPackageJsonTest extends AbstractIncrementalBuilderTest {

	@Test
	def void testChangeInPackageJson() throws IOException {
		testWorkspaceManager.createTestProjectOnDisk(
			"Main" -> '''
				console.log("hello");
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		// create a second source folder with a file "Other" containing an error
		val otherFile = new File(getProjectRoot(), "src2" + File.separator + "Other.n4js");
		otherFile.parentFile.mkdirs;
		Files.writeString(otherFile.toPath, '''
			let x: number = "bad";
		''');

		cleanBuildAndWait();
		assertNoIssues(); // new source folder not registered in package.json yet, so error does not show up

		// register new source folder in package.json
		val packageJsonFileURI = new FileURI(new File(getProjectRoot(), N4JSGlobals.PACKAGE_JSON));
		openFile(packageJsonFileURI);
		changeOpenedFile(packageJsonFileURI,
			'"src"' -> '"src", "src2"'
		);
		joinServerRequests();

		assertNoIssues(); // changes in package.json not saved yet, so error still does not show up

		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertIssues("Other" -> #[ "(Error, [0:16 - 0:21], string is not a subtype of number.)" ]); // now the error shows up

		changeOpenedFile(packageJsonFileURI,
			'"src", "src2"' -> '"src"'
		);
		saveOpenedFile(packageJsonFileURI);
		joinServerRequests();

		assertNoIssues();
	}
}
