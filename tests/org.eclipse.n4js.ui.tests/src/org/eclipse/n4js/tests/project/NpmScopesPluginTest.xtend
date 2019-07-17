/** 
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.project

import com.google.common.collect.Lists
import com.google.inject.Inject
import java.io.File
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IProjectDescription
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.ProjectTestsHelper
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.n4js.utils.URIUtils
import org.junit.Before
import org.junit.Test

import static org.eclipse.emf.common.util.URI.createPlatformResourceURI
import static org.junit.Assert.*
import org.eclipse.n4js.projectModel.names.N4JSProjectName

/**
 * Testing the use of npm scopes as part of N4JS project names, i.e. project names of
 * the form "@myScope/myProject".
 */
class NpmScopesPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String YARN_WORKSPACE_BASE = "npmScopes";
	private static final N4JSProjectName YARN_WORKSPACE_PROJECT = new N4JSProjectName("YarnWorkspaceProject");

	private IProject yarnProject;
	private IProject scopedProject;
	private IProject nonScopedProject;
	private IProject clientProject;
	private URI clientModuleURI;
	private IFile clientModule;

	@Inject private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;
	@Inject private ProjectTestsHelper projectTestsHelper;

	@Before
	def void before() {
		val workspace = ResourcesPlugin.workspace;
		val parentFolder = new File(getResourceUri(PROBANDS, YARN_WORKSPACE_BASE));
		yarnProject = ProjectTestsUtils.importYarnWorkspace(libraryManager, parentFolder, YARN_WORKSPACE_PROJECT,
			Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		testedWorkspace.fullBuild

		scopedProject = workspace.root.getProject("@myScope:Lib");
		nonScopedProject = workspace.root.getProject("Lib");
		clientProject = workspace.root.getProject("XClient");

		assertTrue(scopedProject.exists);
		assertTrue(nonScopedProject.exists);
		assertTrue(clientProject.exists);
		clientModule = clientProject.getFolder("src").getFile("ClientModule.n4js");
		assertNotNull(clientModule);
		assertTrue(clientModule.exists);
		clientModuleURI = createPlatformResourceURI(clientProject.name + "/src/" + clientModule.name, true);
	}

	def static void importProject(IWorkspace workspace, File rootFolder, IProgressMonitor progressMonitor)
			throws CoreException {

		val IPath path = new Path(new File(rootFolder, "_project").getAbsolutePath());
		val IProjectDescription desc = workspace.loadProjectDescription(path);
		val IProject project = workspace.getRoot().getProject(desc.getName());
		project.create(desc, progressMonitor);
		project.open(progressMonitor);
	}

	@Test
	def void testImportModuleThatExistsOnlyInScopedProject() throws CoreException {
		setContentsOfClientModule('''
			import {A} from "A"
			new A().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from A in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {A} from "@myScope/Lib/A"     // <-- should have same result as above
			new A().foo();
		''');
		assertNoIssues();
		assertCorrectOutput('''
			Hello from A in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {A} from "Lib/A"              // <-- must *not* work (because module A not contained in non-scoped project "Lib")
		''');
		assertIssues(
			"line 1: Cannot resolve import target :: resolving full module import : found no matching modules",
			"line 1: Couldn't resolve reference to TExportableElement 'A'.",
			"line 1: Import of A cannot be resolved."
		);
	}

	@Test
	def void testImportModuleThatExistsOnlyInNonScopedProject() throws CoreException {
		setContentsOfClientModule('''
			import {B} from "B"
			new B().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from B in Lib!
		''')

		setContentsOfClientModule('''
			import {B} from "@myScope/Lib/B"     // <-- must *not* work (because module B not contained in scoped project "@myScope/Lib")
		''')
		assertIssues(
			"line 1: Cannot resolve import target :: resolving full module import : found no matching modules",
			"line 1: Couldn't resolve reference to TExportableElement 'B'.",
			"line 1: Import of B cannot be resolved."
		);

		setContentsOfClientModule('''
			import {B} from "Lib/B"              // <-- should have same result as first import above
			new B().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from B in Lib!
		''')
	}

	@Test
	def void testImportModuleThatExistsInBothProjects() throws CoreException {
		setContentsOfClientModule('''
			import {C} from "C"
			new C().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from C in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {C as C_scoped} from "@myScope/Lib/C"
			import {C as C_nonScoped} from "Lib/C"
			new C_scoped().foo();
			new C_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from C in @myScope/Lib!
			Hello from C in Lib!
		''')
	}

	@Test
	def void testImportModuleThatExistsInBothProjectsInSubFolders() throws CoreException {
		setContentsOfClientModule('''
			import {C} from "folder1/folder2/C"
			new C().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from folder1/folder2/C in @myScope/Lib!
		''')

		setContentsOfClientModule('''
			import {C as C_scoped} from "@myScope/Lib/folder1/folder2/C"
			import {C as C_nonScoped} from "Lib/folder1/folder2/C"
			new C_scoped().foo();
			new C_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from folder1/folder2/C in @myScope/Lib!
			Hello from folder1/folder2/C in Lib!
		''')
	}

	@Test
	def void testImportMainModule() throws CoreException {
		setContentsOfClientModule('''
			import {D as D_scoped} from "@myScope/Lib"
			import {D as D_nonScoped} from "Lib"
			new D_scoped().foo();
			new D_nonScoped().foo();
		''')
		assertNoIssues();
		assertCorrectOutput('''
			Hello from D in @myScope/Lib!
			Hello from D in Lib!
		''')
	}

	/**
	 * This test of method {@link URIUtils#convert(String)} is included here to avoid having to create
	 * a new bundle for this test alone (no bundle for UI tests of 'org.eclipse.n4js.utils' yet).
	 */
	@Test
	def void testURIUtils_convert() {
		val f = scopedProject.getFile("package.json");
		assertTrue(f.exists);
		val uri = URIUtils.convert(f);
		assertTrue(uri.isPlatformResource);
		val expectedSegments = #[
			"resource",
			"@myScope" + ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR_ECLIPSE + "Lib",
			"package.json"
		];
		assertArrayEquals(expectedSegments, uri.segments);
	}

	def private void setContentsOfClientModule(CharSequence source) {
		changeTestFile(clientModule, source);
		waitForAutoBuild();
	}

	def private void assertCorrectOutput(CharSequence expectedOutput) {
		val result = projectTestsHelper.runWithNodeRunnerUI(clientModuleURI);
		val actualOutput = result.stdOut.trim;
		val expectedOutputTrimmed = expectedOutput.toString.trim;
		assertEquals("incorrect output when running " + clientModule.name, expectedOutputTrimmed, actualOutput);
	}
}
