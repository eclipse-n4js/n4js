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
package org.eclipse.n4js.utils.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Testing the logic for finding node_modules folders, including related yarn workspace support.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class NodeModulesDiscoveryHelperTest {

	private static Path root;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	@BeforeClass
	public static void beforeClass() throws IOException {
		root = Files.createTempDirectory(NodeModulesDiscoveryHelperTest.class.getSimpleName());
	}

	@Before
	public void beforeEachTest() throws IOException {
		Files.createDirectories(root);
	}

	@After
	public void afterEachTest() throws IOException {
		if (root != null) {
			FileDeleter.delete(root);
		}
	}

	@Test
	public void testYarnWorkspace_simple() {
		String[] projectFolders = createProjectFolders(
				"P0");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "P0" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"P0/node_modules");

		assertIsYarnWorkspace("P0", true);
	}

	@Test
	public void testYarnWorkspace_returnRootOnlyOnce() {
		String[] projectFolders = createProjectFolders(
				"P0",
				"P1");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "P0", "P1" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"P0/node_modules",
				"P1/node_modules");

		assertIsYarnWorkspace("P0", true);
		assertIsYarnWorkspace("P1", true);
	}

	@Test
	public void testYarnWorkspace_notAMember() {
		String[] projectFolders = createProjectFolders(
				"PX");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "P0" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"PX/node_modules");

		assertIsYarnWorkspace("PX", false);
	}

	@Test
	public void testYarnWorkspace_wildcard1() {
		String[] projectFolders = createProjectFolders(
				"P0",
				"packages/P1",
				"packages/P2",
				"packages/sub/P3",
				"packages/sub/sub/P4");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "packages/*" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"P0/node_modules",
				"packages/P1/node_modules",
				"packages/P2/node_modules",
				"packages/sub/P3/node_modules",
				"packages/sub/sub/P4/node_modules");

		assertIsYarnWorkspace("P0", false);
		assertIsYarnWorkspace("packages/P1", true);
		assertIsYarnWorkspace("packages/P2", true);
		assertIsYarnWorkspace("packages/sub/P3", false);
		assertIsYarnWorkspace("packages/sub/sub/P4", false);

		// ---------------------------------------------------------------
		// same as above, but now with "**" wildcard:
		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "packages/**" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"P0/node_modules",
				"packages/P1/node_modules",
				"packages/P2/node_modules",
				"packages/sub/P3/node_modules",
				"packages/sub/sub/P4/node_modules");

		assertIsYarnWorkspace("P0", false);
		assertIsYarnWorkspace("packages/P1", true);
		assertIsYarnWorkspace("packages/P2", true);
		assertIsYarnWorkspace("packages/sub/P3", true);
		assertIsYarnWorkspace("packages/sub/sub/P4", true);
	}

	@Test
	public void testYarnWorkspace_wildcard2() {
		String[] projectFolders = createProjectFolders(
				"packages/sub/P0",
				"packages/sub/P1__",
				"packages/sxb/P2",
				"packages/sub/X0",
				"packages/xub/P3");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "pack*/s?b/P*" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"packages/sub/P0/node_modules",
				"packages/sub/P1__/node_modules",
				"packages/sxb/P2/node_modules",
				"packages/sub/X0/node_modules",
				"packages/xub/P3/node_modules");

		assertIsYarnWorkspace("packages/sub/P0", true);
		assertIsYarnWorkspace("packages/sub/P1__", true);
		assertIsYarnWorkspace("packages/sxb/P2", true);
		assertIsYarnWorkspace("packages/sub/X0", false);
		assertIsYarnWorkspace("packages/xub/P3", false);
	}

	@Test
	public void testYarnWorkspace_wildcard3() {
		String[] projectFolders = createProjectFolders(
				"packages/P0",
				"packages/P1",
				"packages/@myScope/X1",
				"packages/@myScope/X2");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "packages/*" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"packages/P0/node_modules",
				"packages/P1/node_modules",
				"packages/@myScope/X1/node_modules",
				"packages/@myScope/X2/node_modules");

		assertIsYarnWorkspace("packages/P0", true);
		assertIsYarnWorkspace("packages/P1", true);
		assertIsYarnWorkspace("packages/@myScope/X1", true);
		assertIsYarnWorkspace("packages/@myScope/X2", true);
	}

	@Test
	public void testYarnWorkspace_wildcard4() {
		String[] projectFolders = createProjectFolders(
				"packages/P0",
				"packages/P1",
				"packages/@myScope/X1",
				"packages/@myScope/X2");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "packages/*", "packages/@*/*" ]
				}
				""");

		assertNodeModulesPaths(projectFolders,
				"node_modules",
				"packages/P0/node_modules",
				"packages/P1/node_modules",
				"packages/@myScope/X1/node_modules",
				"packages/@myScope/X2/node_modules");

		assertIsYarnWorkspace("packages/P0", true);
		assertIsYarnWorkspace("packages/P1", true);
		assertIsYarnWorkspace("packages/@myScope/X1", true);
		assertIsYarnWorkspace("packages/@myScope/X2", true);
	}

	/** It's legal to pass in the yarn workspace root to the methods in {@link NodeModulesDiscoveryHelper}. */
	@Test
	public void testYarnWorkspace_RootAsArgument() {
		String[] projectFolders = createProjectFolders(
				"P0");

		createPackageJson("""
				{
					"private": true,
					"workspaces": [ "P0" ]
				}
				""");

		ArrayList<String> projectFoldersPlusRoot = new ArrayList<>(Arrays.asList(projectFolders));
		projectFoldersPlusRoot.add("");
		assertNodeModulesPaths(projectFoldersPlusRoot.toArray(new String[0]),
				"node_modules",
				"P0/node_modules");

		assertIsYarnWorkspace("", true);
		assertIsYarnWorkspace("P0", true);
	}

	// --------------------------------------------------------------------------------------------
	// utility methods:

	private void createPackageJson(CharSequence content) {
		createPackageJson("", content);
	}

	private void createPackageJson(String relativeProjectPath, CharSequence content) {
		String[] lines = content.toString().split(System.lineSeparator(), -1);
		try {
			Files.write(root.resolve(relativeProjectPath).resolve(N4JSGlobals.PACKAGE_JSON), Arrays.asList(lines));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	private String[] createProjectFolders(String... relativeProjectPaths) {
		try {
			for (String relativeProjectPath : relativeProjectPaths) {
				Path path = root.resolve(relativeProjectPath);
				Files.createDirectories(path);
				createPackageJson(relativeProjectPath, """
						{
							"name": "«path.fileName»"
						}
						""");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return relativeProjectPaths;
	}

	private void assertNodeModulesPaths(String[] relativeProjectPaths, String... expectedNodeModulesPaths) {

		List<Path> absoluteProjectPaths = toList(map(Arrays.asList(relativeProjectPaths), rpp -> root.resolve(rpp)));
		List<Path> absoluteNodeModulesPaths = nodeModulesDiscoveryHelper.findNodeModulesFolders(absoluteProjectPaths);

		Set<String> relativeNodeModulesPaths = toSet(map(absoluteNodeModulesPaths, p -> root.relativize(p).toString()));

		assertEquals("different node_modules paths than expected", toSet(Arrays.asList(expectedNodeModulesPaths)),
				relativeNodeModulesPaths);
	}

	private void assertIsYarnWorkspace(String relativeProjectPath, boolean expectYarnWorkspaceMember) {
		Path absoluteProjectPath = root.resolve(relativeProjectPath);
		NodeModulesFolder nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(absoluteProjectPath);

		if (expectYarnWorkspaceMember) {
			assertTrue("expected project to be a yarn workspace member: " + relativeProjectPath,
					nodeModulesFolder.isYarnWorkspace());
		} else {
			assertFalse("expected project *not* to be a yarn workspace member: " + relativeProjectPath,
					nodeModulesFolder.isYarnWorkspace());
		}
	}
}
