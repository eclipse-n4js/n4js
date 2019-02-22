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
package org.eclipse.n4js.tests.compare

import com.google.inject.Inject
import java.io.File
import org.eclipse.core.resources.IProject
import org.eclipse.n4js.ApiImplCompareTestHelper
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.compare.ProjectCompareHelper
import org.eclipse.n4js.external.LibraryManager
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.n4js.ui.compare.ProjectCompareTreeHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.tests.builder.BuilderUtil.countResourcesInIndex
import static org.eclipse.n4js.tests.builder.BuilderUtil.getAllResourceDescriptionsAsString
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.root

import static extension org.eclipse.n4js.tests.util.ProjectTestsUtils.*

/**
 * Tests for the API / implementation compare logic (not for the UI part!).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class ApiImplComparePluginTest extends AbstractApiImplCompareTest {

	@Inject
	private ProjectCompareHelper projectCompareHelper;
	@Inject
	private ProjectCompareTreeHelper projectCompareTreeHelper;
	@Inject
	private LibraryManager libraryManager;
	@Inject
	private extension ApiImplCompareTestHelper;

	private IProject yarnProject;

	@Before
	public def void setupEclipseWorkspace() {
		IResourcesSetupUtil.cleanWorkspace
		val parentFolder = new File("probands/ApiImplCompare");
		yarnProject = ProjectTestsUtils.importYarnWorkspace(libraryManager, parentFolder, YARN_PROJECT);
	}

	@After
	public def void teardownEclipseWorkspace() {
		ProjectTestsUtils.closeAllProjectsInWorkspace();
		IResourcesSetupUtil.cleanWorkspace();
		IResourcesSetupUtil.cleanBuild();
		waitForAutoBuild();
		ProjectTestsUtils.waitForAllJobs;
		assertEquals(0, root().getProjects().length);
		assertEquals("Resources in index:\n" + getAllResourceDescriptionsAsString() + "\n", 0,
				countResourcesInIndex());
	}


	// note: main test contained in super class (common for UI and headless cases)


	// some tests only applicable when running in UI mode:

	@Test
	public def void testApiNoteDocumentation() {
		val errMsgs = newArrayList
		val comparison = projectCompareHelper.createComparison(true,errMsgs)
		assertEquals(0, errMsgs.size)

		// checking @apiNote documentation

		val doc = projectCompareTreeHelper.readDocumentation(comparison, #[0]);

		val clazz = comparison.findEntryForType("x/y/M/Clazz")
		assertNotNull(clazz)
		assertEquals("Clazz",clazz.elementNameForEntry)
		assertEquals(
			"documentation for class Clazz was not correctly derived from JSdoc tag @apiNote",
			"hello from API! | hello from implementation!",
			doc.get(clazz))
	}


	@Test
	public def void testMultipleImplementationsWithSameId() {
		val clashIdApi = "org.eclipse.clash.api";
		val IProject pApi = createJSProject(clashIdApi)
		val IProject pImpl1 = ProjectTestsUtils.createJSProject("org.eclipse.clash.n4js","src","src-gen", [ builder |
			builder.withImplementationId("impl.n4js");
			builder.withImplementedProject(clashIdApi);
		])
		val IProject pImpl2 = ProjectTestsUtils.createJSProject("org.eclipse.clash.ios" ,"src","src-gen",[ builder |
			builder.withImplementationId("impl.n4js");
			builder.withImplementedProject(clashIdApi);
		])
		pApi.configureProjectWithXtext
		pImpl1.configureProjectWithXtext
		pImpl2.configureProjectWithXtext

		waitForAutoBuild

		val errMsgs = newArrayList
		projectCompareHelper.createComparison(true,errMsgs)

		assertEquals(1, errMsgs.size)
		assertEquals(
			"several projects define an implementation for API project '"+clashIdApi+"' with implementation ID 'impl.n4js':\n" +
			" - org.eclipse.clash.ios\n" +
			" - org.eclipse.clash.n4js", errMsgs.get(0))

		pImpl2.deleteProject
		pImpl1.deleteProject
		pApi.deleteProject
	}

}
