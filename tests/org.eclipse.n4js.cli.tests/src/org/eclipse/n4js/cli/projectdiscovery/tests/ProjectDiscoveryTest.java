/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.projectdiscovery.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.projectdiscovery.tests.CreateProjectStructureUtils.ProjectDiscoveryTestData;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.inject.Injector;

/**
 *
 */
@RunWith(Parameterized.class)
public class ProjectDiscoveryTest {

	/** Init the {@link ProjectDiscoveryHelper} */
	@BeforeClass
	public static void init() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		projectDiscoveryHelper = injector.getInstance(ProjectDiscoveryHelper.class);
	}

	/** Find test data files */
	@Parameters(name = "{index}: {0}")
	public static Collection<String> testData() {
		ArrayList<String> testFiles = new ArrayList<>();
		for (String testFileName : testFolder.list()) {
			if (testFileName.endsWith(".pdt")) {
				testFiles.add(testFileName);
			}
		}
		return testFiles;
	}

	static File testFolder = new File("ProjectDiscoveryTests");
	static ProjectDiscoveryHelper projectDiscoveryHelper;

	final String testFileName;

	/** Constructor */
	public ProjectDiscoveryTest(String testFileName) {
		this.testFileName = testFileName;
	}

	/** Execute parameterized test */
	@Test
	public void test() {
		Path tmpDir = FileUtils.createTempDirectory(testFileName + "_");

		File testFile = new File(testFolder, testFileName);
		ProjectDiscoveryTestData pdtd = CreateProjectStructureUtils.readPDTFile(testFile);
		CreateProjectStructureUtils.createFolderStructure(tmpDir.toFile(), pdtd);

		Path workspaceRoot = new File(tmpDir.toFile(), pdtd.workingDir.getPath()).toPath();
		LinkedHashSet<Path> projectDirs = projectDiscoveryHelper.collectAllProjectDirs(workspaceRoot);

		ArrayList<String> actualFolders = new ArrayList<>();
		for (Path projectDir : projectDirs) {
			String relativeFolder = tmpDir.relativize(projectDir).toString();
			actualFolders.add(relativeFolder);
		}

		assertEquals(pdtd.expectedProjects, actualFolders);
	}
}
