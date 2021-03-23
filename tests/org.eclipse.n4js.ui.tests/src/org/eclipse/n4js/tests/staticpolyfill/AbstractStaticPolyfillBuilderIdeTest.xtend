/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.staticpolyfill

import java.util.regex.Pattern
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.workspace.locations.FileURI
import org.junit.Before

/**
 */
public abstract class AbstractStaticPolyfillBuilderIdeTest extends ConvertedIdeTest {

	protected FileURI src
	protected FileURI src2
	protected FileURI projectDescriptionFile

	@Before
	def void setUp2() {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();
		
		projectDescriptionFile = getPackageJsonFile().toFileURI;
		src = getProjectRoot().toFileURI.appendSegment(DEFAULT_SOURCE_FOLDER);
		src2 = getProjectRoot().toFileURI.appendSegment("src2");
		src2.toFile.mkdirs();
	}

	/** 
	 * Updates project description file to declare another source container 'src2' 
	 * in addition to the existing container 'src'.
	 */
	def void addSrc2ToSources() {
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(projectDescriptionFile.toPath, SourceContainerType.SOURCE, src2.name);
		sendDidChangeWatchedFiles(projectDescriptionFile);
		joinServerRequests();
	}

	static def int matchCount(Pattern pattern, CharSequence s) {
		val matcher = pattern.matcher(s)
		var matchCount = 0;
		while (matcher.find) {
			matchCount++;
		}
		return matchCount
	}

}
