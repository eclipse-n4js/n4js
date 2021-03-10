/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.dirtystate

import com.google.common.collect.ImmutableList
import com.google.common.collect.Iterables
import com.google.inject.Inject
import java.io.File
import java.io.IOException
import java.util.List
import org.eclipse.core.runtime.CoreException
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.XtextParametrizedRunner
import org.eclipse.n4js.XtextParametrizedRunner.Parameters
import org.eclipse.n4js.projectModel.names.N4JSProjectName
import org.eclipse.n4js.resource.UserDataMapper
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.xtext.server.build.ConcurrentIndex
import org.eclipse.xtext.testing.RepeatedTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Yet another test that ensures a valid index after the build was run in consequence of certain simulated user
 * interaction.
 */
@RunWith(XtextParametrizedRunner)
@RepeatedTest(times = ReproduceInvalidIndexIdeTest.REPETITIONS)
public class ReproduceInvalidIndexIdeTest extends ConvertedIdeTest {

	public static final int REPETITIONS = 3;

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "reproduce-invalid-index";

	@Rule
	public RepeatedTest.Rule repeatedTestRule = new RepeatedTest.Rule(false);
	private final List<String> projectsToImport;

	@Parameters(name = "{0}")
	def static List<List<String>> projectsToImport() {
		return ImmutableList.of(
				ImmutableList.of("Client", "Def", "Impl"),
				ImmutableList.of("Client", "Impl", "Def"),
				ImmutableList.of("Def", "Client", "Impl"),
				ImmutableList.of("Def", "Impl", "Client"),
				ImmutableList.of("Impl", "Client", "Def"),
				ImmutableList.of("Impl", "Def", "Client"));
	}

	@Inject
	private ConcurrentIndex concurrentIndex;

	new(List<String> projectsToImport) {
		this.projectsToImport = projectsToImport;
	}

	@Test
	def void tryToCorruptIndexWithIncrementalBuild() throws Exception {
		importProjects(true);

		assertIndexState();
	}

	@Test
	def void tryToCorruptIndexWithFullBuild() throws Exception {
		importProjects(false);

		assertIndexState();
	}

	def private void assertIndexState() throws Exception, IOException, CoreException {
		assertCientDescriptionUpToDate();
		assertAllDescriptionsHaveModuleData();
		assertNoIssues();
	}

	def private void importProjects(boolean incremental) throws CoreException {
		testWorkspaceManager.createTestOnDisk();
		startAndWaitForLspServer();
		// import the projects
		val testdataLocation = new File(PROBANDS, PROBANDS_SUBFOLDER);
		// not executing anything, so a dummy n4js-runtime is sufficient:
		importProject(testdataLocation, N4JSGlobals.N4JS_RUNTIME);
		for (String projectName : projectsToImport) {
			importProject(testdataLocation, new N4JSProjectName(projectName));
		}
		if (incremental) {
			joinServerRequests();
		} else {
			cleanBuildAndWait();
		}
	}

	/**
	 * Assert that the resource description of the Client.n4js resource does contain TModule data
	 */
	def private void assertCientDescriptionUpToDate() throws Exception {
		// import {A} from "A";
		// import * as B+ from "B";
		//
		// export export const a = new A();
		// B;a;

		val description = concurrentIndex.getProjectIndex("Client").allResourceDescriptions
			.findFirst[URI.toString.endsWith("/Client/src/Client.n4js")];
		Assert.assertNotNull(description);
		val moduleDescription = Iterables.getOnlyElement(
			description.getExportedObjectsByType(TypesPackage.Literals.TMODULE));
		Assert.assertNotNull(moduleDescription);
		val moduleAsString = UserDataMapper.getDeserializedModuleFromDescriptionAsString(moduleDescription,
				description.getURI());
		Assert.assertNotNull(moduleAsString);
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n" +
				"<types:TModule xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:typeRefs=\"http://www.eclipse.org/n4js/ts/TypeRefs\" xmlns:types=\"http://www.eclipse.org/n4js/ts/Types\" simpleName=\"Client\" qualifiedName=\"Client\" projectName=\"Client\" vendorID=\"org.eclipse.n4js\">\n"
				+
				"  <astElement href=\"#/0\"/>\n" +
				"  <variables name=\"a\" exportedName=\"a\" const=\"true\" newExpression=\"true\">\n" +
				"    <astElement href=\"#/0/@scriptElements.2/@exportedElement/@varDeclsOrBindings.0\"/>\n" +
				"    <typeRef xsi:type=\"typeRefs:ParameterizedTypeRef\">\n" +
				"      <declaredType href=\"../../Def/src/A.n4jsd#/1/@topLevelTypes.0\"/>\n" +
				"    </typeRef>\n" +
				"  </variables>\n" +
				"</types:TModule>\n" +
				"", moduleAsString);
	}

}
