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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.jsdoc2spec.JSDoc2SpecProcessorFullTest;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SubMonitorMsg;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.UriExtensions;
import org.junit.runner.RunWith;

import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Runs a full test, that is, reading n4js projects and generate new spec.
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class JSDoc2AdocFullTest extends JSDoc2SpecProcessorFullTest {
	private final static String TESTRESOURCES = "testresourcesADoc/";

	@Override
	public void testSample1() throws IOException, InterruptedException {
		super.testSample1();
	}

	@Inject
	private JSDoc2ADocSpecProcessor jSDoc2SpecProcessor;

	@Override
	@SuppressWarnings("unused")
	protected void fullTest(String projectName)
			throws IOException, InterruptedException, InterruptedException {

		String systemSeparator = System.getProperty("line.separator", "\n");
		try {
			for (String lsep : new String[] { "\n", "\r\n", "\r" }) {
				System.setProperty("line.separator", lsep);
				String expectationFileName = projectName + "/expected.adoc";
				workspace = new FileBasedWorkspace(projectDescriptionLoader, new UriExtensions());

				FileURI uriProject = new FileURI(new File(TESTRESOURCES + projectName));
				workspace.registerProject(uriProject);
				N4JSModel<FileURI> model = new N4JSModel<>(workspace);
				injector.injectMembers(model);
				runtimeCore = new N4JSRuntimeCore(workspace, model);
				IN4JSProject project = runtimeCore.findProject(uriProject.toURI()).get();

				assertNotNull("Project not found", project);

				Collection<SpecFile> specChangeSet = jSDoc2SpecProcessor.convert(
						new File(TESTRESOURCES),
						Collections.singleton(project),
						(p) -> resourceSetProvider.get(),
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
					String expectedADoc = Files.toString(fullExpectationFile, Charset.defaultCharset());
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
