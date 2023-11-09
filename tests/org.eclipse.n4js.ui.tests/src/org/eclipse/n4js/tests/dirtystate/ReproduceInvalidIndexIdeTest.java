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
package org.eclipse.n4js.tests.dirtystate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.Parameters;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.testing.RepeatedTest;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableList;

/**
 * Yet another test that ensures a valid index after the build was run in consequence of certain simulated user
 * interaction.
 */
@RunWith(XtextParametrizedRunner.class)
@RepeatedTest(times = ReproduceInvalidIndexIdeTest.REPETITIONS)
public class ReproduceInvalidIndexIdeTest extends ConvertedIdeTest {

	public static final int REPETITIONS = 3;

	private static final String PROBANDS = "probands";
	private static final String PROBANDS_SUBFOLDER = "reproduce-invalid-index";

	@Rule
	public RepeatedTest.Rule repeatedTestRule = new RepeatedTest.Rule(false);
	private final List<String> projectsToImport;

	@Parameters(name = "{0}")
	public static List<List<String>> projectsToImport() {
		return ImmutableList.of(
				ImmutableList.of("Client", "Def", "Impl"),
				ImmutableList.of("Client", "Impl", "Def"),
				ImmutableList.of("Def", "Client", "Impl"),
				ImmutableList.of("Def", "Impl", "Client"),
				ImmutableList.of("Impl", "Client", "Def"),
				ImmutableList.of("Impl", "Def", "Client"));
	}

	public ReproduceInvalidIndexIdeTest(List<String> projectsToImport) {
		this.projectsToImport = projectsToImport;
	}

	@Test
	public void tryToCorruptIndexWithIncrementalBuild() throws Exception {
		importProjects(true);

		assertIndexState();
	}

	@Test
	public void tryToCorruptIndexWithFullBuild() throws Exception {
		importProjects(false);

		assertIndexState();
	}

	private void assertIndexState() throws Exception, IOException, CoreException {
		assertCientDescriptionUpToDate();
		assertAllDescriptionsHaveModuleData();
		assertNoIssues();
	}

	private void importProjects(boolean incremental) {
		testWorkspaceManager.createTestOnDisk();
		startAndWaitForLspServer();
		// import the projects
		File testdataLocation = new File(PROBANDS, PROBANDS_SUBFOLDER);
		// not executing anything, so a dummy n4js-runtime is sufficient:
		importProject(testdataLocation, N4JSGlobals.N4JS_RUNTIME);
		for (String projectName : projectsToImport) {
			importProject(testdataLocation, new N4JSPackageName(projectName));
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
	private void assertCientDescriptionUpToDate() throws Exception {
		// import {A} from "A";
		// import * as B+ from "B";
		//
		// export export const a = new A();
		// B;a;

		Iterable<IResourceDescription> index = concurrentIndex.getProjectIndex("yarn-test-project/packages/Client")
				.getAllResourceDescriptions();
		IResourceDescription description = IterableExtensions.findFirst(index,
				rd -> rd.getURI().toString().endsWith("/Client/src/Client.n4js"));
		Assert.assertNotNull(description);
		IEObjectDescription moduleDescription = description.getExportedObjectsByType(TypesPackage.Literals.TMODULE)
				.iterator().next();
		Assert.assertNotNull(moduleDescription);
		String moduleAsString = UserDataMapper.getDeserializedModuleFromDescriptionAsString(moduleDescription,
				description.getURI());
		Assert.assertNotNull(moduleAsString);
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
				+ "<types:TModule xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:typeRefs=\"http://www.eclipse.org/n4js/ts/TypeRefs\" xmlns:types=\"http://www.eclipse.org/n4js/ts/Types\" simpleName=\"Client\" qualifiedName=\"Client\" packageName=\"Client\" projectID=\"yarn-test-project/packages/Client\" vendorID=\"org.eclipse.n4js\">\n"
				+ "  <exportDefinitions xsi:type=\"types:ElementExportDefinition\" exportedName=\"a\" exportedElement=\"//@exportedVariables.0\"/>\n"
				+ "  <exportedVariables name=\"a\" directlyExported=\"true\" exportingExportDefinitions=\"//@exportDefinitions.0\" const=\"true\" compileTimeValue=\"!a new expression is never a compile-time expression\" newExpression=\"true\">\n"
				+ "    <typeRef xsi:type=\"typeRefs:ParameterizedTypeRef\">\n"
				+ "      <declaredType href=\"../../Def/src/A.n4jsd#/1/@types.0\"/>\n"
				+ "    </typeRef>\n"
				+ "    <astElement href=\"#/0/@scriptElements.2/@exportedElement/@varDeclsOrBindings.0\"/>\n"
				+ "  </exportedVariables>\n"
				+ "  <astElement href=\"#/0\"/>\n"
				+ "</types:TModule>\n"
				+ "", moduleAsString);
	}

}
