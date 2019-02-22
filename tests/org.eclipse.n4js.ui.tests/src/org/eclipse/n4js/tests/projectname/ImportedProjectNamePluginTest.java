/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.projectname;

import java.io.File;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;

/**
 * Test that imports various different test projects that have a special configuration with regard to the following
 * properties:
 *
 * <ul>
 * <li>folder name on the file system</li>
 * <li>the declared project name in the package.json file</li>
 * <li>their project name in the Eclipse workspace</li>
 * <ul>
 *
 * This test asserts that the corresponding package.json validation checks that all of these three names match up.
 */
public class ImportedProjectNamePluginTest extends AbstractBuilderParticipantTest {
	private static final String PROBANDS = "probands";
	private static final String WORKSPACE_LOC = "GH-998-project-name";

	// reinitialized for each test method in #setUp
	private File projectsRoot;

	/**
	 * Copies all projects in probands location into a temporary projects root (cf. #projectsRoot) in which the
	 * tear-down code may delete projects.
	 *
	 * Without this extra step, the tear-code of this test would actually delete files in the 'probands' directory.
	 */
	@Before
	public void setUp2() throws Exception {
		projectsRoot = Files.createTempDir();
		projectsRoot.deleteOnExit();
		final File probandsLocation = new File(getResourceUri(PROBANDS, WORKSPACE_LOC));
		FileCopier.copy(probandsLocation.toPath(), projectsRoot.toPath());

		super.setUp();
	}

	/**
	 * Tests the following project name configuration.
	 *
	 * <pre>
	 * File System: 	file-other-system
	 * Package.json: 	file-system
	 * Eclipse: 		file-system
	 * </pre>
	 */
	@Test
	public void testDifferentFileSystemName() throws CoreException {
		// workspace setup
		final IProject testProject = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"file-other-system", "file-system");
		configureProjectWithXtext(testProject);
		waitForAutoBuild();

		// obtain package.json resource
		final IResource packageJsonResource = testProject.findMember(IN4JSProject.PACKAGE_JSON);

		// assert project name markers
		assertHasMarker(packageJsonResource, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		assertHasNoMarker(packageJsonResource, IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);

		// tear down
		testProject.delete(false, true, new NullProgressMonitor());
	}

	/**
	 * Tests the following project name configuration.
	 *
	 * <pre>
	 * File System: 	eclipse-file-other-system
	 * Package.json: 	eclipse-file-system
	 * Eclipse: 		eclipse-system
	 * </pre>
	 */
	@Test
	public void testDifferentFileSystemAndEclipseName() throws CoreException {
		// workspace setup
		final IProject testProject = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"eclipse-file-other-system", "eclipse-system");
		configureProjectWithXtext(testProject);
		waitForAutoBuild();

		// obtain package.json resource
		final IResource packageJsonResource = testProject.findMember(IN4JSProject.PACKAGE_JSON);

		// assert project name markers
		assertHasMarker(packageJsonResource, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		assertHasMarker(packageJsonResource, IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);

		// tear down
		testProject.delete(false, true, new NullProgressMonitor());
	}

	/**
	 * Tests the following project name configuration.
	 *
	 * <pre>
	 * File System: 	eclipse
	 * Package.json: 	eclipse
	 * Eclipse: 		eclipse-other
	 * </pre>
	 */
	@Test
	public void testDifferentEclipseName() throws CoreException {
		// workspace setup
		final IProject testProject = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"eclipse", "eclipse-other");
		configureProjectWithXtext(testProject);
		waitForAutoBuild();

		// obtain package.json resource
		final IResource packageJsonResource = testProject.findMember(IN4JSProject.PACKAGE_JSON);

		// assert project name markers
		assertHasNoMarker(packageJsonResource, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		assertHasMarker(packageJsonResource, IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);

		// tear down
		testProject.delete(false, true, new NullProgressMonitor());
	}

	/**
	 * Tests the following project name configuration.
	 *
	 * <pre>
	 * File System: 	all-match
	 * Package.json: 	all-match
	 * Eclipse: 		all-match
	 * </pre>
	 */
	@Test
	public void testAllNamesMatch() throws CoreException {
		// workspace setup
		final IProject testProject = ProjectTestsUtils.createProjectWithLocation(projectsRoot,
				"all-match", "all-match");
		configureProjectWithXtext(testProject);
		waitForAutoBuild();

		// obtain package.json resource
		final IResource packageJsonResource = testProject.findMember(IN4JSProject.PACKAGE_JSON);

		// assert project name markers
		assertHasNoMarker(packageJsonResource, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		assertHasNoMarker(packageJsonResource, IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);

		// tear down
		testProject.delete(false, true, new NullProgressMonitor());
	}

	/**
	 * Asserts that the given {@code resource} does not have a marker with the given {@code issueCode}.
	 */
	private static void assertHasNoMarker(IResource resource, String issueCode) throws CoreException {
		final IMarker[] markers = resource.findMarkers(null, true, IResource.DEPTH_INFINITE);
		for (IMarker marker : markers) {
			final String markerIssueCode = marker.getAttribute(Issue.CODE_KEY, "");
			if (issueCode.equals(markerIssueCode)) {
				Assert.fail("Resource had marker " + marker.getAttribute(IMarker.MESSAGE, "<no message>")
						+ " with issue code " + issueCode);
			}
		}
	}

	/**
	 * Asserts that the given {@code resource} has at least one marker with the given {@code issueCode}.
	 */
	private static void assertHasMarker(IResource resource, String issueCode) throws CoreException {
		final IMarker[] markers = resource.findMarkers(null, true, IResource.DEPTH_INFINITE);
		final StringBuilder allMarkersDescription = new StringBuilder();
		for (IMarker marker : markers) {
			final String markerIssueCode = marker.getAttribute(Issue.CODE_KEY, "");
			if (issueCode.equals(markerIssueCode)) {
				// assertion fulfilled
				return;
			}
			allMarkersDescription.append("\n");
			allMarkersDescription.append("line " + MarkerUtilities.getLineNumber(marker) + ": ");
			allMarkersDescription.append(marker.getAttribute(IMarker.MESSAGE, "<no message>"));
		}
		Assert.fail("Expected resource " + resource.getFullPath() + " to have at least one marker with issue code " +
				issueCode + " but was " + allMarkersDescription.toString());
	}
}
