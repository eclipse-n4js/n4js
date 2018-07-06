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
import static org.eclipse.n4js.tests.externalPackages.ExternalWorkspaceTestUtils.removeExternalLibrariesPreferenceStoreLocations;
import static org.eclipse.n4js.tests.externalPackages.ExternalWorkspaceTestUtils.setExternalLibrariesPreferenceStoreLocations;
import static org.eclipse.n4js.tests.externalPackages.IndexableFilesDiscoveryUtil.collectIndexableFiles;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest;
import org.eclipse.n4js.tests.builder.BuilderUtil;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.resource.IResourceDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

/**
 */
public class ExternalPackagesPluginTest extends AbstractBuilderParticipantTest {

	private static final String PROBAND_LIBFOO = "LibFoo";
	private static final String PROBAND_LIBBAR = "LibBar";
	private static final String PROBAND_LIBBAZ = "LibBaz";

	private Path externalLibrariesRoot;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		waitForAutoBuild();
		externalLibrariesRoot = FileUtils.createTempDirectory();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (externalLibrariesRoot != null) {
			FileDeleter.delete(externalLibrariesRoot);
			externalLibrariesRoot = null;
		}
		super.tearDown();
	}

	/**
	 * Check if index is populated with external project content when external location with single project is
	 * registered.
	 */
	@Test
	public void testOneProjectInExternalLibrary() throws Exception {

		copyProjectsToLocation(externalLibrariesRoot, PROBAND_LIBFOO);

		setExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

		Collection<String> expected = collectIndexableFiles(externalLibrariesRoot);

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());

		removeExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

	}

	/**
	 * Check if index is populated with external projects content when external location with multiple project
	 * (dependent) are registered.
	 */
	@Test
	public void testThreeDependendExternalProjects() throws Exception {

		copyProjectsToLocation(externalLibrariesRoot, PROBAND_LIBFOO, PROBAND_LIBBAR, PROBAND_LIBBAZ);

		setExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

		Collection<String> expected = collectIndexableFiles(externalLibrariesRoot);

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());

		removeExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

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

		copyProjectsToLocation(externalLibrariesRoot);
		waitForAutoBuild();
		setExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

		Collection<String> expectedExternal = collectIndexableFiles(externalLibrariesRoot);
		Collection<String> expectedWorkspace = collectIndexableFiles(ResourcesPlugin.getWorkspace());
		Collection<String> expected = new HashSet<>();
		expected.addAll(expectedExternal);
		expected.addAll(expectedWorkspace);

		assertResourceDescriptions(expected, BuilderUtil.getAllResourceDescriptions());

		removeExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

	}

	/**
	 * Check if index is populated only with workspace project content when the external location with single project is
	 * registered and workspace contains project with the same name. External library is registered before project is
	 * created in the workspace.
	 */
	public void testWorkspaceProjectHidingExternalProject_after() throws Exception {

		IProject createJSProject = ProjectTestsUtils.createJSProject("LibFoo");
		IFolder src = configureProjectWithXtext(createJSProject);
		IFile packageJson = createJSProject.getProject().getFile(IN4JSProject.PACKAGE_JSON);
		assertMarkers("package.json of first project should have no errors", packageJson, 0);

		createTestFile(src, "Foo", "console.log('hi')");
		createTestFile(src, "AAAA", "console.log('hi')");
		createTestFile(src, "BBB", "console.log('hi')");
		waitForAutoBuild();

		copyProjectsToLocation(externalLibrariesRoot, "LibFoo");
		setExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

		Collection<String> expectedWorkspace = collectIndexableFiles(ResourcesPlugin.getWorkspace());

		assertResourceDescriptions(expectedWorkspace, BuilderUtil.getAllResourceDescriptions());

		removeExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

	}

	/**
	 * Check if index is populated only with workspace project content when the external location with single project is
	 * registered and workspace contains project with the same name. External library is registered after project is
	 * created in the workspace.
	 */
	@Test
	public void testWorkspaceProjectHidingExternalProject_before() throws Exception {

		copyProjectsToLocation(externalLibrariesRoot, "LibFoo");
		setExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

		IProject createJSProject = ProjectTestsUtils.createJSProject("LibFoo");
		IFolder src = configureProjectWithXtext(createJSProject);
		IFile packageJson = createJSProject.getProject().getFile(IN4JSProject.PACKAGE_JSON);
		assertMarkers("package.json of first project should have no errors", packageJson, 0);

		createTestFile(src, "Foo", "console.log('hi')");
		createTestFile(src, "AAAA", "console.log('hi')");
		createTestFile(src, "BBB", "console.log('hi')");
		waitForAutoBuild();

		Collection<String> expectedWorkspace = collectIndexableFiles(ResourcesPlugin.getWorkspace());

		assertResourceDescriptions(expectedWorkspace, BuilderUtil.getAllResourceDescriptions());

		removeExternalLibrariesPreferenceStoreLocations(externalLibraryPreferenceStore, externalLibrariesRoot);

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
			if (!missingDescriptions.contains(stringUri)) {
				extraDescriptions.add(stringUri);
			} else {
				missingDescriptions.remove(stringUri);
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
