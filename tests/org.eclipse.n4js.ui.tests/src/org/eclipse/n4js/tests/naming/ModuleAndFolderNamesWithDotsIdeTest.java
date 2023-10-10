/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.naming;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Testing module and folder names containing dots.
 */
// converted from ModuleAndFolderNamesWithDotsPluginTest
public class ModuleAndFolderNamesWithDotsIdeTest extends ConvertedIdeTest {

	private static final String PROBANDS = "probands";
	private static final String SUBFOLDER = "ModuleAndFolderNamesWithDots";
	private static final N4JSPackageName PROJECT_NAME = new N4JSPackageName("ModuleAndFolderNamesWithDots");

	@Test
	public void testModuleAndFolderNamesWithDots() {
		importProband(new File(PROBANDS, SUBFOLDER), Lists.newArrayList(N4JSGlobals.N4JS_RUNTIME));
		assertNoIssues();

		FileURI mainModule = new FileURI(getProjectRootForImportedProject(PROJECT_NAME.getRawName()).toPath()
				.resolve("src-gen").resolve("Main.js").toFile());
		assertTrue("file Main.js not found", mainModule.exists());

		assertOutput(mainModule, """
				hello from C#m() located in module: sub/module.with.dots
				hello from D#m() located in module: folder.with.many.dots/another.module.with.dots
				hello from D#m() located in module: folder/with/many/dots/another.module.with.dots
				hello from D#m() located in module: folder/with/many/dots/another/module/with/dots
				""");
	}
}
