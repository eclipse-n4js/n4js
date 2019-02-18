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
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.io.FileCopier;
import org.junit.After;
import org.junit.Before;
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

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		setupShippedLibraries();
	}

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

		// Project root und node_modules in root
		Path prjRoot = tmpDir.toPath().resolve("R");
		File prjRootFile = prjRoot.toFile();
		assertFalse(Arrays.stream(prjRootFile.listFiles())
				.filter((subFolder) -> subFolder.getName().equals(N4JSGlobals.NODE_MODULES))
				.findFirst().isPresent());

		org.eclipse.emf.common.util.URI prjP1URI = org.eclipse.emf.common.util.URI
				.createFileURI(projP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());

		assertTrue(Arrays.stream(prjRootFile.listFiles())
				.filter((subFolder) -> subFolder.getName().equals(N4JSGlobals.NODE_MODULES))
				.findFirst().isPresent());

		// TODO FIX ME: Due to a yarn bug (https://github.com/yarnpkg/yarn/issues/6997), no lodash is installed
		// Assert that lodash is not installed in node_modules of the root folder
		IFile pkgJsonP1 = projP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		assertIssues(pkgJsonP1, "line 16: Project does not exist with project ID: lodash.");
	}

	@After
	@Override
	public void tearDown() throws Exception {
		tearDownShippedLibraries();
		super.tearDown();

	}

	private String getDependencyVersion(org.eclipse.emf.common.util.URI prjURI, String dependencyName) {
		ProjectDescription prjDesc1 = prjDescLoader.loadProjectDescriptionAtLocation(prjURI);
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
