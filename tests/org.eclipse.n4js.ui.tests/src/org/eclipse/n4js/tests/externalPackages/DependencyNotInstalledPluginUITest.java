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

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Test if a npm is installed in the node_modules folder of the project.
 * <p>
 * This test is necessary since the IDE puts all type information of npms to a single index. Hence, it suffices to
 * install an npm only once in any project to make it available to all other projects. At runtime, one of the other
 * projects would not be executable since the npm was not installed in his node_modules folder.
 */
public class DependencyNotInstalledPluginUITest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS_DIR = "probands/DependencyNotInstalled";
	private static final String PROBAND_A = "A";
	private static final String PROBAND_B = "B";

	@Inject
	private EclipseExternalLibraryWorkspace extLibWS;

	@Override
	protected boolean provideShippedCode() {
		return true;
	}

	/**
	 * Checks that there is an issue in case an npm is not installed in the project's node_modules folder.
	 */
	@Test
	public void checkErrorOfMissingInstallationProjects() throws CoreException {
		Path projectsRoot = new File(getResourceUri(PROBANDS_DIR)).toPath();
		IProject prjA = ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_A);
		ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_B);
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertNotNull(extLibWS.getProject("n4js.lang"));

		IFile packageJsonA = prjA.getFile(IN4JSProject.PACKAGE_JSON);
		// line 14: Project does not exist with project ID: n4js.lang
		assertMarkers("package.json of project A should have 1 issue", packageJsonA, 1);

		libraryManager.installNPM("n4js.lang", URIUtils.convert(prjA), new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertMarkers("package.json of project A should have no issues", packageJsonA, 0);
	}

	/**
	 * Checks that there is an issue in case of a non-yarn dependency to another workspace project.
	 */
	@Test
	public void checkErrorOfMissingYarnWorkspaceProjects() throws CoreException {
		Path projectsRoot = new File(getResourceUri(PROBANDS_DIR)).toPath();
		ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_A);
		IProject prjB = ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_B);
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile packageJsonB = prjB.getFile(IN4JSProject.PACKAGE_JSON);
		// line 14: Project depends on workspace project A which is missing in the node_modules folder.
		// Either install project A or introduce a yarn workspace of both of the projects.
		assertMarkers("package.json of project B should have 1 issue", packageJsonB, 1);
	}
}
