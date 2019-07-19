/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.externalPackages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.io.FileCopier;
import org.junit.Test;

import com.google.common.io.Files;
import com.google.inject.Inject;

/**
 * Side by side projects that belong to a Yarn workspace tests
 */
public class SideBySideYarnProjectsInstallNpmPluginUITest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String YARN_INSTALL_YARN_WORKSPACE = "YarnInstallYarnWorkspace";

	@Inject
	private ProjectDescriptionLoader prjDescLoader;

	/**
	 * Installs an npm in a local project of yarn workspace will install it in the node_modules folder of yarn root
	 * workspace
	 */
	@Test
	public void installInLocalProjectLeadToInstallingInYarnRootWorkspaceTest() throws CoreException, IOException {

		// Setup probands
		File tmpDir = Files.createTempDir();
		tmpDir.deleteOnExit();
		final File probandsLocation = new File(getResourceUri(PROBANDS, YARN_INSTALL_YARN_WORKSPACE));
		FileCopier.copy(probandsLocation.toPath(), tmpDir.toPath());

		// Setup workspace
		final IProject projP1 = ProjectTestsUtils.createProjectWithLocation(tmpDir,
				"R/packages/P1", "P1");
		ProjectTestsUtils.createProjectWithLocation(tmpDir,
				"R/packages/P2", "P2");

		// Project root and node_modules in root
		Path prjRoot = tmpDir.toPath().resolve("R");
		File prjRootFile = prjRoot.toFile();
		assertFalse(Arrays.stream(prjRootFile.listFiles())
				.filter((subFolder) -> subFolder.getName().equals(N4JSGlobals.NODE_MODULES))
				.findFirst().isPresent());

		FileURI prjP1URI = new PlatformResourceURI(projP1).toFileURI();
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM(new N4JSProjectName("lodash"), lodashVersion, prjP1URI,
				new NullProgressMonitor());
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());

		assertTrue(Arrays.stream(prjRootFile.listFiles())
				.filter((subFolder) -> subFolder.getName().equals(N4JSGlobals.NODE_MODULES))
				.findFirst().isPresent());

		// TODO FIX ME: Due to a yarn bug (https://github.com/yarnpkg/yarn/issues/6997), no lodash is installed
		// Assert that lodash is not installed in node_modules of the root folder
		IFile pkgJsonP1 = projP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		assertIssues(pkgJsonP1, "line 16: Project does not exist with project ID: lodash.");
	}

	private String getDependencyVersion(SafeURI<?> loc, String dependencyName) {
		ProjectDescription prjDesc1 = prjDescLoader.loadProjectDescriptionAtLocation(loc);
		Optional<ProjectDependency> depPrj = prjDesc1.getProjectDependencies().stream()
				.filter(prjDep -> prjDep.getProjectName().equals(dependencyName))
				.findFirst();
		if (depPrj.isPresent()) {
			return depPrj.get().getVersionRequirementString();
		} else {
			return null;
		}
	}
}
