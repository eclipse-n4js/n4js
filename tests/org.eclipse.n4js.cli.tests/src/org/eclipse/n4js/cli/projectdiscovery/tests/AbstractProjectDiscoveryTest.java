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

import org.eclipse.n4js.cli.projectdiscovery.tests.CreateProjectStructureUtils.ProjectDiscoveryTestData;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Abstract test to find all projects and their dependencies.
 */
@RunWith(Parameterized.class)
abstract public class AbstractProjectDiscoveryTest {
	final File testFile;

	/** @return the list of all pdt test files */
	public static Collection<File> getPDTFilesIn(File folder) {
		ArrayList<File> testFiles = new ArrayList<>();
		for (String testFileName : folder.list()) {
			if (testFileName.endsWith(".pdt")) {
				testFiles.add(new File(folder, testFileName));
			}
		}
		return testFiles;
	}

	/** Constructor */
	public AbstractProjectDiscoveryTest(File testFile) {
		this.testFile = testFile;
	}

	/** Call the {@link ProjectDiscoveryHelper} to find projects in the given workspace directory */
	abstract protected LinkedHashSet<Path> callProjectDiscoveryHelper(Path workspaceRoot);

	/** Execute parameterized test */
	@Test
	public void test() {
		String fileName = testFile.getName();
		Path tmpDir = FileUtils.createTempDirectory(fileName + "_");

		ProjectDiscoveryTestData pdtd = CreateProjectStructureUtils.readPDTFile(testFile);
		CreateProjectStructureUtils.createFolderStructure(tmpDir.toFile(), pdtd);

		Path workspaceRoot = new File(tmpDir.toFile(), pdtd.workingDir.getPath()).toPath();

		LinkedHashSet<Path> projectDirs = callProjectDiscoveryHelper(workspaceRoot);
		ArrayList<String> actualFolders = getActualFolders(tmpDir, projectDirs);
		assertEquals(pdtd.expectedProjects, actualFolders);
	}

	private ArrayList<String> getActualFolders(Path tmpDir, LinkedHashSet<Path> projectDirs) {
		ArrayList<String> actualFolders = new ArrayList<>();
		for (Path projectDir : projectDirs) {
			String relativeFolder = tmpDir.relativize(projectDir).toString();
			actualFolders.add(relativeFolder);
		}
		return actualFolders;
	}
}
