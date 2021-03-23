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
package org.eclipse.n4js.jsdoc2spec.adoc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.jsdoc2spec.JSDoc2SpecProcessorFullTest;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SubMonitorMsg;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.workspace.IN4JSCoreNEW;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Runs a full test, that is, reading n4js projects and generate new spec.
 */
public class JSDoc2AdocFullTest extends JSDoc2SpecProcessorFullTest {
	private final static String TESTRESOURCES = "testresourcesADoc/";

	@Override
	public void testSample1() throws IOException, InterruptedException {
		super.testSample1();
	}

	@Inject
	private IN4JSCoreNEW n4jsCore;
	@Inject
	private JSDoc2ADocSpecProcessor jSDoc2SpecProcessor;

	@Override
	@SuppressWarnings("unused")
	protected void fullTest(String projectName)
			throws IOException, InterruptedException, InterruptedException {

		testWorkspaceManager.createTestOnDisk(); // create an empty yarn workspace
		startAndWaitForLspServer();
		assertNoIssues();

		File probandFolder = new File(TESTRESOURCES + projectName);
		File projectFolder = new File(getProjectLocation(), projectName);
		assertTrue("proband folder not found", probandFolder.isDirectory());
		FileCopier.copy(probandFolder, projectFolder);
		cleanBuildAndWait();
		FileURI packageJsonURI = toFileURI(getProjectLocation()).appendSegments(projectName, N4JSGlobals.PACKAGE_JSON);
		assertIssues(Map.of(
				packageJsonURI, Lists.newArrayList(
						"(Error, [4:17 - 4:26], Missing dependency to n4js-runtime (mandatory for all N4JS projects of type library, application, test).)")));

		String systemSeparator = System.getProperty("line.separator", "\n");
		try {
			for (String lsep : new String[] { "\n", "\r\n", "\r" }) {
				System.setProperty("line.separator", lsep);
				String expectationFileName = projectName + "/expected.adoc";

				ResourceSet resourceSet = n4jsCore.createResourceSet();
				N4JSProjectConfigSnapshot project = n4jsCore.findProject(resourceSet, new N4JSProjectName(projectName))
						.orNull();
				assertNotNull("Project not found", project);

				Collection<SpecFile> specChangeSet = jSDoc2SpecProcessor.convert(
						new File(TESTRESOURCES),
						Collections.singleton(project),
						(p) -> resourceSet,
						SubMonitorMsg.nullProgressMonitor());

				String adocRootName = TESTRESOURCES + projectName + "/expectedADoc";
				Collection<String> expectedFileNames = getExpectedFileNames(adocRootName, specChangeSet);
				assertFalse(expectedFileNames.isEmpty());

				File adocRoot = new File(adocRootName);
				String completeActual = "";
				String completeExpected = "";
				for (SpecFile specFile : specChangeSet) {
					String expectedFile = getExpectedFile(expectedFileNames, specFile);
					if (expectedFile == null)
						continue;

					Path fullExpectationPath = adocRoot.toPath().resolve(expectedFile);
					File fullExpectationFile = fullExpectationPath.toFile();
					String fullExpectationPathStr = fullExpectationPath.toString();
					String expectedADoc = Files.asCharSource(fullExpectationFile, Charset.defaultCharset()).read();
					String actualADoc = specFile.getNewContent();

					if (UPDATE_EXPECTION && !actualADoc.equals(expectedADoc)) {
						expectedADoc = actualADoc;
						org.eclipse.xtext.util.Files.writeStringIntoFile(fullExpectationPathStr, expectedADoc);
						System.out.println("Updated expectation " + fullExpectationPathStr);
					}

					completeActual += "\n//////// " + expectedFile + " ////////\n";
					completeActual += actualADoc;
					completeExpected += "\n//////// " + expectedFile + " ////////\n";
					completeExpected += expectedADoc;
				}

				assertEqualsIgnoreWS(completeExpected, completeActual);
			}
		} finally {
			System.setProperty("line.separator", systemSeparator);
		}
	}

	private String getExpectedFile(Collection<String> expectedFileNames, SpecFile specFile) {
		String specFileName = specFile.getFile().getName().toString();
		if (expectedFileNames.contains(specFileName)) {
			return specFileName;
		}
		specFileName = getFolderIncludingFileName(specFile);
		if (expectedFileNames.contains(specFileName)) {
			return specFileName;
		}
		return null;
	}

	private Collection<String> getExpectedFileNames(String expectationFilesRoot, Collection<SpecFile> specFiles) {
		if (specFiles == null)
			return Collections.emptyList();

		File expFilesRoot = new File(expectationFilesRoot);
		String[] expFileNames = expFilesRoot.list();
		if (expFileNames == null)
			return Collections.emptyList();

		Set<String> expFiles = new HashSet<>(Arrays.asList(expFileNames));
		for (SpecFile specFile : specFiles) {
			String fileName = specFile.getFile().getName();
			if (expFiles.contains(fileName)) {
				expFiles.add(fileName);
			} else {
				fileName = getFolderIncludingFileName(specFile);
				if (expFiles.contains(fileName)) {
					expFiles.add(fileName);
				}
			}
		}

		return expFiles;
	}

	private String getFolderIncludingFileName(SpecFile specFile) {
		String fileName = specFile.getFile().getName();
		String parentFileName = specFile.getFile().getParentFile().getName();
		if ("NO_PACKAGE".equals(parentFileName)) {
			parentFileName = specFile.getFile().getParentFile().getParentFile().getName();
		}
		int hashIdx = parentFileName.indexOf("#");
		if (hashIdx > 0) {
			parentFileName = parentFileName.substring(hashIdx + 1);
		}
		fileName = parentFileName + "." + fileName;
		return fileName;
	}

}
