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
package org.eclipse.n4js.tests.staticpolyfill;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Before;

/**
 */
public abstract class AbstractStaticPolyfillBuilderIdeTest extends ConvertedIdeTest {

	protected FileURI src;
	protected FileURI src2;
	protected FileURI projectDescriptionFile;

	@Before
	public void setUp2() {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();

		projectDescriptionFile = toFileURI(getPackageJsonFile());
		src = toFileURI(getProjectRoot()).appendSegment(DEFAULT_SOURCE_FOLDER);
		src2 = toFileURI(getProjectRoot()).appendSegment("src2");
		src2.toFile().mkdirs();
	}

	/**
	 * Updates project description file to declare another source container 'src2' in addition to the existing container
	 * 'src'.
	 */
	public void addSrc2ToSources() throws IOException {
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(projectDescriptionFile.toPath(), SourceContainerType.SOURCE,
				src2.getName());
		sendDidChangeWatchedFiles(projectDescriptionFile);
		joinServerRequests();
	}

	static int matchCount(Pattern pattern, CharSequence s) {
		Matcher matcher = pattern.matcher(s);
		int matchCount = 0;
		while (matcher.find()) {
			matchCount++;
		}
		return matchCount;
	}

}
