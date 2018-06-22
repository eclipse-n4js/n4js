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
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * Class for checking API implementation compilation for static polyfilled modules.
 */
public class IDEBUG_647_PluginTest extends AbstractIDEBUG_Test {

	/***/
	@Test
	public void buildCheckGeneratedFileExists_AssertExists() throws CoreException {
		LOGGER.info("Running full clean build for all projects...");
		fullBuild();
		LOGGER.info("Full clean build successfully completed.");
		final IProject project = getWorkspace().getRoot().getProject("org.eclipse.n4js.lib.model.common");

		final IFile file = project.getFile(
				"src-gen/n4/model/common/TimezoneRegion.js");
		assertTrue("TimezoneRegion.js compiled file does not exist.", file.exists());
	}

	@Override
	protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File("probands/IDEBUG_647/").getAbsolutePath()));
	}

}
