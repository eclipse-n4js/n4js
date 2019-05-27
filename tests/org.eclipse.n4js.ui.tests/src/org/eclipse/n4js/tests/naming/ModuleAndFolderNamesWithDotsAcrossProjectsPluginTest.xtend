/** 
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.naming

import com.google.common.collect.Lists
import com.google.inject.Inject
import java.io.File
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.external.LibraryManager
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.junit.Test

import static org.junit.Assert.*

/**
 * Testing module and folder names containing dots.
 */
class ModuleAndFolderNamesWithDotsAcrossProjectsPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "ModuleAndFolderNamesWithDotsAcrossProjects";
	private static final String YARN_WORKSPACE_PROJECT = "YarnWorkspaceProject";
	private static final String PROJECT_NAME = "Main";

	@Inject
	private LibraryManager libraryManager;

	@Test
	def void testModuleAndFolderNamesWithDots() {
		val root = new File(getResourceUri(PROBANDS, SUBFOLDER));
		ProjectTestsUtils.importYarnWorkspace(libraryManager, root, YARN_WORKSPACE_PROJECT,
			Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		val project = ResourcesPlugin.workspace.root.getProject(PROJECT_NAME);

		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		testedWorkspace.fullBuild;
		assertNoIssues();

		val mainModule = project.getFolder("src").getFile("Main.n4js");
		assertTrue("file Main.n4js not found", mainModule.exists);

		assertCorrectOutput(mainModule, '''
			hello from C#m() located in module: sub/module.with.dots
			hello from D#m() located in module: folder.with.many.dots/another.module.with.dots
			hello from D#m() located in module: folder/with/many/dots/another.module.with.dots
			hello from D#m() located in module: folder/with/many/dots/another/module/with/dots
		''');
	}
}
