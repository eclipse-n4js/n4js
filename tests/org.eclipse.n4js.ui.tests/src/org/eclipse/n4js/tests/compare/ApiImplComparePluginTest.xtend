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
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.n4js.ApiImplCompareTestHelper
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.n4js.compare.ProjectCompareHelper
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.n4js.ui.compare.ProjectCompareTreeHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.XtextProjectHelper
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.addNature

import static extension org.eclipse.n4js.tests.util.ProjectTestsUtils.*
import org.eclipse.n4js.external.LibraryManager
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.emf.common.util.URI

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


	@BeforeClass
	public static def void setupEclipseWorkspace() {
		IResourcesSetupUtil.cleanWorkspace
		val parentFolder = new File("probands/ApiImplCompare");
		ProjectTestsUtils.importYarnWorkspace(null, parentFolder, YARN_PROJECT);
	}


	// note: main test contained in super class (common for UI and headless cases)


	// some tests only applicable when running in UI mode:

	@Test
	public def void testApiNoteDocumentation() {
		val yarnProject = ResourcesPlugin.workspace.root.getProject(YARN_PROJECT);
		val yarnUri = URI.createFileURI(yarnProject.location.toString);
		libraryManager.runNpmYarnInstall(yarnUri, new NullProgressMonitor);
		ProjectTestsUtils.waitForAllJobs();

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

		waitForAutoBuild
	}

	def private static void deleteProjects( IProject[] projects) {
		for ( IProject project : projects) {
			if (project.exists()) {
					project.delete(true, true, new NullProgressMonitor());
			}
		}
	}

	/**
	 * Imports a project from the probands/ApiImplCompare folder
	 */
	private static def IProject importTestProject(String name) {
		val project = importProject(new File("probands/ApiImplCompare"), name);
		addNature(project, XtextProjectHelper.NATURE_ID);
		IResourcesSetupUtil.waitForBuild
		assertMarkers("imported test project '"+name+"' should have no errors/warnings", project, 0)
		return project;
	}
}
