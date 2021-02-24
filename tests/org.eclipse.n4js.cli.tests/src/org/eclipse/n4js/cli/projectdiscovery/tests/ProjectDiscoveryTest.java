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
import java.util.Collections;
import java.util.List;

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
 * Abstract test to find all projects and their dependencies.
 */
@RunWith(Parameterized.class)
public class ProjectDiscoveryTest {
	static ProjectDiscoveryHelper projectDiscoveryHelper;

	/** Find test data files */
	@Parameters(name = "{0}")
	public static Collection<File> testData() {
		File folder = new File("PDTs");
		ArrayList<File> testFiles = new ArrayList<>();
		for (String testFileName : folder.list()) {
			if (testFileName.endsWith(".pdt")) {
				testFiles.add(new File(folder, testFileName));
			}
		}
		return testFiles;
	}

	/** Init the {@link ProjectDiscoveryHelper} */
	@BeforeClass
	public static void init() {
		Injector injector = N4jscFactory.getOrCreateInjector();
		projectDiscoveryHelper = injector.getInstance(ProjectDiscoveryHelper.class);
	}

	final File testFile;

	/** Constructor */
	public ProjectDiscoveryTest(File testFile) {
		this.testFile = testFile;
	}

	/** Execute parameterized test */
	@Test
	public void test() {
		String fileName = testFile.getName();
		Path tmpDir = FileUtils.createTempDirectory(fileName + "_");

		ProjectDiscoveryTestData pdtd = CreateProjectStructureUtils.readPDTFile(testFile);
		CreateProjectStructureUtils.createFolderStructure(tmpDir.toFile(), pdtd);

		Path workspaceRoot = new File(tmpDir.toFile(), pdtd.workingDir.getPath()).toPath();

		ArrayList<String> actualFolders = getActualFolders(tmpDir, workspaceRoot);
		assertEquals(String.join(",\n", pdtd.expectedProjects), String.join(",\n", actualFolders));
	}

	private ArrayList<String> getActualFolders(Path tmpDir, Path workspaceRoot) {
		List<Path> projectDirs = projectDiscoveryHelper.collectAllProjectDirs(workspaceRoot);
		ArrayList<String> actualFolders = new ArrayList<>();
		for (Path projectDir : projectDirs) {
			String relativeFolder = tmpDir.relativize(projectDir).toString();
			actualFolders.add(relativeFolder);
		}
		Collections.sort(actualFolders);
		return actualFolders;
	}
}
