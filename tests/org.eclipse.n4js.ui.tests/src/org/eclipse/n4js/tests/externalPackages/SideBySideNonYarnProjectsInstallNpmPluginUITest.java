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

import java.io.File;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;
import org.junit.Test;

import com.google.inject.Inject;

/**
 *
 * Setup: P1, P2 in Eclipse workspace<br>
 * - no dependency between P1, P2;<br>
 * - dependency P1 -> lodash;<br>
 * - dependency P2 -> immutable
 * <p>
 * a) full build -> errors in both projects<br>
 * b) run "npm install" in P1 -> errors in P1 gone<br>
 * c) run "npm install" in P2 -> all errors gone<br>
 */
public class SideBySideNonYarnProjectsInstallNpmPluginUITest extends AbstractBuilderParticipantTest {

	private static final String PROBANDS = "probands";
	private static final String DIFFERENT_NPM_SUBFOLDER = "InstallDifferentNpms";
	private static final String SAME_NPM_SUBFOLDER = "InstallSameNpm";
	private static final String DIFFERENT_NPMS_DEPENDENT_PROJECTS_SUBFOLDER = "InstallDifferentNpmsInDependentProjects";

	@Inject
	private ProjectDescriptionLoader prjDescLoader;

	/**
	 * Install different npm packages in two independent projects.
	 *
	 * <pre>
	 * 1) P1, P2 in Eclipse workspace; no dependency between P1, P2; P1 -> lodash; P2 -> immutable
	 * 		a) full build -> errors in both projects
	 * 		b) run "npm install" in P1 -> errors in P1 gone
	 * 		c) run "npm install" in P2 -> all errors gone
	 * </pre>
	 */
	@Test
	public void installDifferentNpmInTwoIndependentProjects() throws CoreException {

		File prjDir = new File(getResourceUri(PROBANDS, DIFFERENT_NPM_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: lodash.",
				"line 17: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.");
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: immutable.");

		URI prjP1URI = URI.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("n4js-runtime", prjP1URI, new NullProgressMonitor());
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();

		// no lodash error anymore
		assertIssues(pkgJsonP1,
				"line 15: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.");
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: immutable.");

		URI prjP2URI = URI.createFileURI(prjP2.getLocation().toString());
		String immutableVersion = getDependencyVersion(prjP2URI, "immutable");
		libraryManager.installNPM("n4js-runtime", prjP2URI, new NullProgressMonitor());
		libraryManager.installNPM("immutable", immutableVersion, prjP2URI, new NullProgressMonitor());
		waitForAutoBuild();

		assertIssues(pkgJsonP1,
				"line 15: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.");
		assertIssues(pkgJsonP2); // No errors in P2 anymore
	}

	/**
	 * Install the same npm package in two independent projects.
	 *
	 * <pre>
	 * 2) P1, P2 in Eclipse workspace; no dependency between P1, P2; P1 -> lodash; P2 -> lodash
	 * 	  a) full build -> errors in both projects
	 *	  b) run "npm install" in P1 -> error of P1 gone
	 * </pre>
	 */
	@Test
	public void installSamepNpmInTwoIndependentProjects() throws CoreException {

		File prjDir = new File(getResourceUri(PROBANDS, SAME_NPM_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: lodash.");
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: lodash.");

		URI prjP1URI = URI.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("n4js-runtime", prjP1URI, new NullProgressMonitor());
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();
		assertIssues(pkgJsonP1); // No errors in P1 anymore
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: lodash.");
	}

	/**
	 * Install different npm packages. There is dependency between projects.
	 *
	 * <pre>
	 * 3) P1, P2 im Eclipse workspace; P1 -> P2; P1 -> lodash; P2 -> immutable like 1) above
	 * 	  but also test executing P1 with runner (reference to P2 must work at runtime)
	 * </pre>
	 *
	 * NOTE: execution of code in the (invalid) side-by-side use case is no longer supported; hence, the execution step
	 * in this test was removed.
	 */
	@Test
	public void installDifferentNpmsInTwoDependentProjects() throws CoreException {
		System.out.println("start");

		File prjDir = new File(getResourceUri(PROBANDS, DIFFERENT_NPMS_DEPENDENT_PROJECTS_SUBFOLDER));
		IProject prjP1 = ProjectTestsUtils.importProject(prjDir, "P1");
		IProject prjP2 = ProjectTestsUtils.importProject(prjDir, "P2");
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		IFile pkgJsonP1 = prjP1.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));
		IFile pkgJsonP2 = prjP2.getFile(getResourceName(N4JSGlobals.PACKAGE_JSON));

		assertIssues(pkgJsonP1,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 17: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.",
				"line 16: Project does not exist with project ID: lodash.");
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: immutable.");

		URI prjP1URI = URI.createFileURI(prjP1.getLocation().toString());
		String lodashVersion = getDependencyVersion(prjP1URI, "lodash");
		libraryManager.installNPM("n4js-runtime", prjP1URI, new NullProgressMonitor());
		libraryManager.installNPM("lodash", lodashVersion, prjP1URI, new NullProgressMonitor());
		waitForAutoBuild();

		// lodash error gone
		assertIssues(pkgJsonP1,
				"line 15: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.");
		assertIssues(pkgJsonP2,
				"line 15: Project does not exist with project ID: n4js-runtime.",
				"line 16: Project does not exist with project ID: immutable.");

		URI prjP2URI = URI.createFileURI(prjP2.getLocation().toString());
		String immutableVersion = getDependencyVersion(prjP2URI, "immutable");
		libraryManager.installNPM("n4js-runtime", prjP2URI, new NullProgressMonitor());
		libraryManager.installNPM("immutable", immutableVersion, prjP2URI, new NullProgressMonitor());
		IResourcesSetupUtil.fullBuild();
		waitForAutoBuild();

		assertIssues(pkgJsonP1,
				"line 15: Project depends on workspace project P2 which is missing in the node_modules folder. "
						+ "Either install project P2 or introduce a yarn workspace of both of the projects.");
		assertIssues(pkgJsonP2); // No errors in P2 anymore
	}

	private String getDependencyVersion(URI prjURI, String dependencyName) {
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
