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
package org.eclipse.n4js.tests.externalPackages;

import static org.eclipse.n4js.tests.externalPackages.ExternalProjectsTestUtil.copyProjectsToLocation;
import static org.eclipse.n4js.tests.externalPackages.IndexableFilesDiscoveryUtil.collectIndexableFiles;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.builder.BuilderUtil;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.external.ExternalProjectMappings;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.resource.IResourceDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class ExternalPackagesPluginTest extends AbstractBuilderParticipantTest {

	// the probands folder
	private static final String PROBANDS_DIR = "probands/ExternalPackages/";
	private static final N4JSProjectName PROBAND_CLIENT = new N4JSProjectName("Client");

	private static final String PROBAND_LIBFOO = "LibFoo";
	private static final String PROBAND_LIBBAR = "LibBar";
	private static final String PROBAND_LIBBAZ = "LibBaz";

	private Path externalLibrariesRoot;
	private Path projectsRoot;

	/** */
	@Before
	public void setUp2() throws Exception {
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = false;

		waitForAutoBuild();
		externalLibrariesRoot = FileUtils.createTempDirectory();
		projectsRoot = new File(getResourceUri(PROBANDS_DIR)).toPath();
	}

	/** */
	@After
	public void tearDown2() throws Exception {
		ExternalProjectMappings.REDUCE_REGISTERED_NPMS = true;

		if (externalLibrariesRoot != null) {
			FileDeleter.delete(externalLibrariesRoot);
			externalLibrariesRoot = null;
		}
	}

	/**
	 * Check if index is populated with external project content when external location with single project is
	 * registered.
	 */
	@Test
	public void testOneProjectInExternalLibrary() throws Exception {
		ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_CLIENT);
		copyProjectsToLocation(projectsRoot, externalLibrariesRoot, PROBAND_LIBFOO);
		ProjectTestsUtils.importDependencies(PROBAND_CLIENT, externalLibrariesRoot.toUri(), libraryManager);

		waitForAutoBuild();

		Collection<String> expected = collectIndexableFiles(externalLibrariesRoot);
		// add user workspace project files
		expected.add("/Client/src/ABC.n4js");
		expected.add("/Client/package.json");

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());
	}

	/**
	 * Check if index is populated with external projects content when external location with multiple project
	 * (dependent) are registered.
	 */
	@Test
	public void testThreeDependendExternalProjects() throws Exception {
		ProjectTestsUtils.importProject(projectsRoot.toFile(), PROBAND_CLIENT);
		copyProjectsToLocation(projectsRoot, externalLibrariesRoot, PROBAND_LIBFOO, PROBAND_LIBBAR, PROBAND_LIBBAZ);
		ProjectTestsUtils.importDependencies(PROBAND_CLIENT, externalLibrariesRoot.toUri(), libraryManager);

		waitForAutoBuild();

		Collection<String> expected = collectIndexableFiles(externalLibrariesRoot);
		// add user workspace project files
		expected.add("/Client/src/ABC.n4js");
		expected.add("/Client/package.json");

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());
	}

	/**
	 * Check if index is populated with external project content and workspace project content when the external
	 * location with single project is registered and workspace contains project with different name.
	 */
	@Test
	public void testWorkspaceProjectAndExternalProject() throws Exception {
		IProject createJSProject = ProjectTestsUtils.createJSProject("LibFoo2");
		IFolder src = configureProjectWithXtext(createJSProject);
		IFile packageJson = createJSProject.getProject().getFile(IN4JSProject.PACKAGE_JSON);
		assertMarkers("package.json of first project should have no errors", packageJson, 0);
		createTestFile(src, "Foo", "console.log('hi')");

		waitForAutoBuild();

		Collection<String> expectedExternal = collectIndexableFiles(externalLibrariesRoot);

		Collection<String> expectedWorkspace = collectIndexableFiles(ResourcesPlugin.getWorkspace());
		Collection<String> expected = new HashSet<>();
		expected.addAll(expectedExternal);
		expected.addAll(expectedWorkspace);

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());
	}

	/**
	 * Check if index is populated only with workspace project content when the external location with single project is
	 * registered and workspace contains project with the same name. External library is registered before project is
	 * created in the workspace.
	 */
	@Test
	public void testWorkspaceProjectHidingExternalProject_after() throws Exception {
		IProject createJSProject = ProjectTestsUtils.createJSProject(PROBAND_LIBFOO);
		IFolder src = configureProjectWithXtext(createJSProject);
		IFile packageJson = createJSProject.getProject().getFile(IN4JSProject.PACKAGE_JSON);
		assertMarkers("package.json of first project should have no errors", packageJson, 0);

		createTestFile(src, "Foo", "console.log('hi')");
		createTestFile(src, "AAAA", "console.log('hi')");
		createTestFile(src, "BBB", "console.log('hi')");
		waitForAutoBuild();

		copyProjectsToLocation(projectsRoot, externalLibrariesRoot, PROBAND_LIBFOO);
		ProjectTestsUtils.importDependencies(new EclipseProjectName(createJSProject.getName()).toN4JSProjectName(),
				externalLibrariesRoot.toUri(), libraryManager);

		Collection<String> expectedWorkspace = collectIndexableFiles(ResourcesPlugin.getWorkspace());
		// remove those that are shadowed
		expectedWorkspace.remove("/LibFoo/node_modules/LibFoo/src/Foo.n4js");
		expectedWorkspace.remove("/LibFoo/node_modules/LibFoo/package.json");

		assertResourceDescriptions(expectedWorkspace, BuilderUtil.getAllResourceDescriptions());
	}

	/**
	 * Checks if expected list of stringified file locations matches
	 *
	 * @param expected
	 *            collection of entries
	 * @param actual
	 *            collection of entries
	 */
	public void assertResourceDescriptions(Collection<String> expected, Iterable<IResourceDescription> actual) {
		Set<String> extraDescriptions = new HashSet<>();
		Set<String> missingDescriptions = new HashSet<>(expected);

		for (IResourceDescription iResourceDescription : actual) {
			URI uri = iResourceDescription.getURI();
			String stringUri = uri.isPlatform() ? uri.toPlatformString(false) : uri.toFileString();

			String missingDescription = "";
			for (String missingDescr : missingDescriptions) {
				if (stringUri.endsWith(missingDescr)) {
					missingDescription = missingDescr;
					break;
				}
			}

			if (missingDescription.isEmpty()) {
				extraDescriptions.add(stringUri);
			} else {
				missingDescriptions.remove(missingDescription);
			}
		}

		if (missingDescriptions.isEmpty() && extraDescriptions.isEmpty()) {
			return;
		}

		StringBuilder msg = new StringBuilder("unexpected actual resources" + "\n");

		if (!extraDescriptions.isEmpty()) {
			msg.append("actual contains " + extraDescriptions.size() + " extra resources" + "\n");
		}

		if (!missingDescriptions.isEmpty()) {
			msg.append("actual is missing  " + missingDescriptions.size() + " expected resources" + "\n");
		}

		for (String extra : extraDescriptions) {
			msg.append("[extra] " + extra + "\n");
		}
		for (String missing : missingDescriptions) {
			msg.append("[missing] " + missing + "\n");
		}
		fail(msg.toString());
	}
}
