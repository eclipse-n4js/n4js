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
package org.eclipse.n4js.tests.bugs;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.fullBuild;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.tests.util.ProjectUtils;
import org.junit.Test;

/**
 * Class for checking whether the order of the source containers influences the indexing order and the scoping for
 * imports.
 */
public class IDEBUG_745_PluginUITest extends AbstractIDEBUG_Test {

	/***/
	@Test
	public void buildCheckGeneratedFileExists_AssertExists() throws CoreException {
		LOGGER.info("Running full clean build for all projects...");
		fullBuild();
		LOGGER.info("Full clean build successfully completed.");

		final IProject project = getWorkspace().getRoot().getProject("IDEBUG_745");

		final IFile clientOne = project.getFile("/client.src/ClientOne.n4js");
		assertNotNull("Cannot find file: client.src/ClientOne.n4js.", clientOne);
		assertTrue("File: client.src/ClientOne.n4js dose not exist.", clientOne.exists());

		final IFile clientTwo = project.getFile("/client.src/ClientTwo.n4js");
		assertNotNull("Cannot find file: client.src/ClientTwo.n4js.", clientTwo);
		assertTrue("File: client.src/ClientTwo.n4js dose not exist.", clientTwo.exists());

		ProjectUtils.assertMarkers("Expected 0 validation issues.", clientOne, 0);
		ProjectUtils.assertMarkers("Expected 0 validation issues.", clientTwo, 0);
	}

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/IDEBUG_745/").getAbsolutePath()));
	}

}
