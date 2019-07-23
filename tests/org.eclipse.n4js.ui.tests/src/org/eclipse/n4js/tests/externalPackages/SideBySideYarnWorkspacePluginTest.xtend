/** 
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.externalPackages

import com.google.inject.Inject
import java.io.File
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IResource
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.ProjectTestsHelper
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.xtext.util.Arrays
import org.junit.Test

import static org.eclipse.emf.common.util.URI.createPlatformResourceURI
import static org.junit.Assert.*
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Testing the use of a yarn workspace inside the N4JS IDE, including the case that only
 * some of the packages contained in the yarn workspace are imported as Eclipse projects
 * into the Eclipse workspace.
 */
class SideBySideYarnWorkspacePluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String YARN_WORKSPACE_BASE = "YarnWorkspaceExample";
	private static final N4JSProjectName YARN_WORKSPACE_PROJECT = new N4JSProjectName("YarnWorkspaceProject");

	private static final String expectedOutput = '''
		Hello from C in Lib!
		Hello from C in @myScope/Lib!
	''';

	private IProject yarnProject;
	private IProject scopedProject;
	private IProject nonScopedProject;
	private IProject clientProject;
	private URI clientModuleURI;
	private IFile clientModule;

	@Inject private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;
	@Inject private ProjectTestsHelper projectTestsHelper;

	/** Standard case is importing all packages contained in the yarn workspace. */
	@Test
	def void testStandard() throws CoreException {
		importYarnWorkspace("XClient".toProjectName, "Lib".toProjectName, "@myScope/Lib".toProjectName);
		assertNoIssues;
		assertCorrectOutput(expectedOutput);

		yarnProject.close(null);
		testedWorkspace.fullBuild;
		assertNoIssues;
		assertCorrectOutput(expectedOutput);

		yarnProject.open(null);
		testedWorkspace.fullBuild;
		assertNoIssues;
		assertCorrectOutput(expectedOutput);
	}
	
	/**
	 * Test the case that only some of the projects contained in a yarn workspace are imported into
	 * the Eclipse workspace. Sub case: a non-scoped project is missing.
	 */
	@Test
	def void testPartialOmitNonScoped() throws CoreException {
		importYarnWorkspace("XClient".toProjectName, "Lib".toProjectName, "@myScope/Lib".toProjectName); // importing 'Lib' only to have it compiled!
		assertNoIssues;
		nonScopedProject.delete(IResource.NEVER_DELETE_PROJECT_CONTENT, null);
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		testedWorkspace.fullBuild();
		assertNoIssues;
		assertCorrectOutput(expectedOutput);
	}

	/**
	 * Test the case that only some of the projects contained in a yarn workspace are imported into
	 * the Eclipse workspace. Sub case: a scoped project is missing.
	 */
	@Test
	def void testPartialOmitScoped() throws CoreException {
		importYarnWorkspace("XClient".toProjectName, "Lib".toProjectName, "@myScope/Lib".toProjectName); // importing '@myScope/Lib' only to have it compiled!
		assertNoIssues;
		scopedProject.delete(IResource.NEVER_DELETE_PROJECT_CONTENT, null);
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		testedWorkspace.fullBuild();
		assertNoIssues;
		assertCorrectOutput(expectedOutput);
	}

	@Test
	def void testOnlyWorkspaceProject() throws CoreException {
		importYarnWorkspace();
		assertNoIssues;
		// cannot use IDE runners for execution, in this case
	}

	@Test
	def void testCloseAndReopen() throws CoreException {
		importYarnWorkspace("XClient".toProjectName, "Lib".toProjectName, "@myScope/Lib".toProjectName);
		assertNoIssues;
		assertCorrectOutput(expectedOutput);

		scopedProject.close(null);
		testedWorkspace.fullBuild;
		assertIssues(
			"line 6: Project does not exist with project ID: @myScope/Lib.",
			"line 2: Import of C as C2 cannot be resolved.",
			"line 2: Couldn't resolve reference to TExportableElement 'C'.",
			"line 5: Couldn't resolve reference to IdentifiableElement 'C2'.",
			"line 2: Cannot resolve import target :: resolving simple module import : found no matching modules");
		
		scopedProject.open(null);
		testedWorkspace.fullBuild;
		assertNoIssues;
	}

	def private void importYarnWorkspace(N4JSProjectName... packagesToImport) {
		val workspace = ResourcesPlugin.workspace;
		val parentFolder = new File(getResourceUri(PROBANDS, YARN_WORKSPACE_BASE));
		yarnProject = ProjectTestsUtils.importYarnWorkspace(libraryManager, parentFolder, YARN_WORKSPACE_PROJECT, [pkgName|
			return Arrays.contains(packagesToImport, pkgName);
		], #[
			N4JSGlobals.N4JS_RUNTIME
		]);
		testedWorkspace.fullBuild

		for(N4JSProjectName packageName : packagesToImport) {
			val eclipsePackageName = packageName.toEclipseProjectName.rawName;
			assertTrue("package wasn't imported: " + packageName, workspace.root.getProject(eclipsePackageName).accessible);
		}

		scopedProject = workspace.root.getProject("@myScope:Lib");
		nonScopedProject = workspace.root.getProject("Lib");
		clientProject = workspace.root.getProject("XClient");

		clientModule = clientProject.getFolder("src").getFile("ClientModule.n4js");
		clientModuleURI = createPlatformResourceURI(clientProject.name + "/src/" + clientModule.name, true);
	}

	def private void assertCorrectOutput(CharSequence expectedOutput) {
		assertTrue("module to be run does not exist: " + clientModule.name, clientModule.exists);
		val result = projectTestsHelper.runWithNodeRunnerUI(clientModuleURI);
		val actualOutput = result.stdOut.trim;
		val expectedOutputTrimmed = expectedOutput.toString.trim;
		assertEquals("incorrect output when running " + clientModule.name, expectedOutputTrimmed, actualOutput);
	}
	
	private def toProjectName(String name) {
		return new N4JSProjectName(name)
	}
}
