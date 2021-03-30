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
import java.io.File
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.workspace.locations.FileURI
import org.eclipse.n4js.workspace.utils.N4JSProjectName
import org.junit.Test

import static org.junit.Assert.*

/**
 * Testing module and folder names containing dots.
 */
// converted from ModuleAndFolderNamesWithDotsAcrossProjectsPluginTest
class ModuleAndFolderNamesWithDotsAcrossProjectsIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "ModuleAndFolderNamesWithDotsAcrossProjects";
	private static final N4JSProjectName PROJECT_NAME = new N4JSProjectName("Main");

	@Test
	def void testModuleAndFolderNamesWithDots() {
		importProband(new File(PROBANDS, SUBFOLDER), Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		assertNoIssues();

		val mainModule = new FileURI(getProjectRootForImportedProject(PROJECT_NAME.rawName).toPath
			.resolve("src-gen").resolve("Main.js").toFile);
		assertTrue("file Main.js not found", mainModule.exists);

		assertOutput(mainModule, '''
			hello from C#m() located in module: sub/module.with.dots
			hello from D#m() located in module: folder.with.many.dots/another.module.with.dots
			hello from D#m() located in module: folder/with/many/dots/another.module.with.dots
			hello from D#m() located in module: folder/with/many/dots/another/module/with/dots
		''');
	}
}
