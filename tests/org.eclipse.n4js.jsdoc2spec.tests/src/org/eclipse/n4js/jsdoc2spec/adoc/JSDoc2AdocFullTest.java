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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSRuntimeCore;
import org.eclipse.n4js.jsdoc2spec.JSDoc2SpecProcessorFullTest;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SubMonitorMsg;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.util.Files;
import org.junit.runner.RunWith;

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
	protected void fullTest(String projectId)
			throws IOException, InterruptedException, InterruptedException {

		String systemSeparator = System.getProperty("line.separator", "\n");
		try {
			for (String lsep : new String[] { "\n", "\r\n", "\r" }) {
				System.setProperty("line.separator", lsep);
				String expectationFileName = projectId + "/expected.adoc";
				workspace = new FileBasedWorkspace(resourceSetProvider, classpathPackageManager);

				URI uriProject = URI.createFileURI(new File(TESTRESOURCES + projectId).getAbsolutePath());
				workspace.registerProject(uriProject);
				N4JSModel model = new N4JSModel(workspace);
				injector.injectMembers(model);
				runtimeCore = new N4JSRuntimeCore(workspace, model);
				IN4JSProject project = runtimeCore.findProject(uriProject).get();

				assertNotNull("Project not found", project);

				Collection<SpecFile> specChangeSet = jSDoc2SpecProcessor.convert(
						new File(TESTRESOURCES),
						Collections.singleton(project),
						(p) -> resourceSetProvider.get(),
						SubMonitorMsg.nullProgressMonitor());

				String adocRootName = TESTRESOURCES + projectId + "/expectedADoc";
				List<String> expectedFileNames = getExpectedFileNames(adocRootName, specChangeSet);
				assertFalse(expectedFileNames.isEmpty());

				File adocRoot = new File(adocRootName);
				String completeActual = "";
				String completeExpected = "";
				for (SpecFile specFile : specChangeSet) {
					String fileName = specFile.getFile().getName().toString();
					if (!expectedFileNames.contains(fileName))
						continue;

					String fullExpectationFileName = adocRoot.toPath().resolve(fileName).toString();
					String expectedADoc = Files.readFileIntoString(fullExpectationFileName);
					String actualADoc = specFile.getNewContent();

					if (UPDATE_EXPECTION && !actualADoc.equals(expectedADoc)) {
						expectedADoc = actualADoc;
						Files.writeStringIntoFile(fullExpectationFileName, expectedADoc);
						System.out.println("Updated expectation " + fullExpectationFileName);
					}

					completeActual += "\n//////// " + fileName + " ////////\n";
					completeActual += actualADoc;
					completeExpected += "\n//////// " + fileName + " ////////\n";
					completeExpected += expectedADoc;
				}

				assertEqualsIgnoreWS(completeExpected, completeActual);
			}
		} finally {
			System.setProperty("line.separator", systemSeparator);
		}
	}

	private List<String> getExpectedFileNames(String expectationFilesRoot, Collection<SpecFile> specFiles) {
		if (specFiles == null)
			return Collections.emptyList();

		File expFilesRoot = new File(expectationFilesRoot);
		String[] expFileNames = expFilesRoot.list();
		if (expFileNames == null)
			return Collections.emptyList();

		List<String> expFiles = Arrays.asList(expFileNames)
				.stream()
				.filter(fName -> specFiles.stream().anyMatch(sFile -> fName.equals(sFile.getFile().getName())))
				.collect(Collectors.toList());

		return expFiles;
	}

}
