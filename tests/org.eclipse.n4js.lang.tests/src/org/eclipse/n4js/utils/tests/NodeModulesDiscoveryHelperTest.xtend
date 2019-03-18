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
package org.eclipse.n4js.utils.tests

import com.google.inject.Inject
import java.nio.file.Files
import java.nio.file.Path
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper
import org.eclipse.n4js.utils.io.FileDeleter
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Testing the logic for finding node_modules folders, including related yarn workspace support.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class NodeModulesDiscoveryHelperTest {

	private static Path root;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	@BeforeClass
	def static void beforeClass() {
		root = Files.createTempDirectory(NodeModulesDiscoveryHelperTest.simpleName);
	}

	@Before
	def void beforeEachTest() {
		Files.createDirectories(root);
	}

	@After
	def void afterEachTest() {
		if (root !== null) {
			FileDeleter.delete(root);
		}
	}

	@Test
	def void testYarnWorkspace_simple() {
		val projectFolders = createProjectFolders(
			"P0"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "P0" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"P0/node_modules"
		);

		"P0".assertIsYarnWorkspace(true);
	}

	@Test
	def void testYarnWorkspace_returnRootOnlyOnce() {
		val projectFolders = createProjectFolders(
			"P0",
			"P1"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "P0", "P1" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"P0/node_modules",
			"P1/node_modules"
		);

		"P0".assertIsYarnWorkspace(true);
		"P1".assertIsYarnWorkspace(true);
	}

	@Test
	def void testYarnWorkspace_notAMember() {
		val projectFolders = createProjectFolders(
			"PX"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "P0" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"PX/node_modules"
		);

		"PX".assertIsYarnWorkspace(false);
	}

	@Test
	def void testYarnWorkspace_wildcard1() {
		val projectFolders = createProjectFolders(
			"P0",
			"packages/P1",
			"packages/P2",
			"packages/sub/P3",
			"packages/sub/sub/P4"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "packages/*" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"P0/node_modules",
			"packages/P1/node_modules",
			"packages/P2/node_modules",
			"packages/sub/P3/node_modules",
			"packages/sub/sub/P4/node_modules"
		);

		"P0".assertIsYarnWorkspace(false);
		"packages/P1".assertIsYarnWorkspace(true);
		"packages/P2".assertIsYarnWorkspace(true);
		"packages/sub/P3".assertIsYarnWorkspace(false);
		"packages/sub/sub/P4".assertIsYarnWorkspace(false);

		// ---------------------------------------------------------------
		// same as above, but now with "**" wildcard:
		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "packages/**" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"P0/node_modules",
			"packages/P1/node_modules",
			"packages/P2/node_modules",
			"packages/sub/P3/node_modules",
			"packages/sub/sub/P4/node_modules"
		);

		"P0".assertIsYarnWorkspace(false);
		"packages/P1".assertIsYarnWorkspace(true);
		"packages/P2".assertIsYarnWorkspace(true);
		"packages/sub/P3".assertIsYarnWorkspace(true);
		"packages/sub/sub/P4".assertIsYarnWorkspace(true);
	}

	@Test
	def void testYarnWorkspace_wildcard2() {
		val projectFolders = createProjectFolders(
			"packages/sub/P0",
			"packages/sub/P1__",
			"packages/sxb/P2",
			"packages/sub/X0",
			"packages/xub/P3"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "pack*/s?b/P*" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"packages/sub/P0/node_modules",
			"packages/sub/P1__/node_modules",
			"packages/sxb/P2/node_modules",
			"packages/sub/X0/node_modules",
			"packages/xub/P3/node_modules"
		);

		"packages/sub/P0".assertIsYarnWorkspace(true);
		"packages/sub/P1__".assertIsYarnWorkspace(true);
		"packages/sxb/P2".assertIsYarnWorkspace(true);
		"packages/sub/X0".assertIsYarnWorkspace(false);
		"packages/xub/P3".assertIsYarnWorkspace(false);
	}

	@Test
	def void testYarnWorkspace_wildcard3() {
		val projectFolders = createProjectFolders(
			"packages/P0",
			"packages/P1",
			"packages/@myScope/X1",
			"packages/@myScope/X2"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "packages/*" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"packages/P0/node_modules",
			"packages/P1/node_modules",
			"packages/@myScope/X1/node_modules",
			"packages/@myScope/X2/node_modules"
		);

		"packages/P0".assertIsYarnWorkspace(true);
		"packages/P1".assertIsYarnWorkspace(true);
		"packages/@myScope/X1".assertIsYarnWorkspace(false);
		"packages/@myScope/X2".assertIsYarnWorkspace(false);
	}

	@Test
	def void testYarnWorkspace_wildcard4() {
		val projectFolders = createProjectFolders(
			"packages/P0",
			"packages/P1",
			"packages/@myScope/X1",
			"packages/@myScope/X2"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "packages/*", "packages/@*/*" ]
			}
		''');

		projectFolders.assertNodeModulesPaths(
			"node_modules",
			"packages/P0/node_modules",
			"packages/P1/node_modules",
			"packages/@myScope/X1/node_modules",
			"packages/@myScope/X2/node_modules"
		);

		"packages/P0".assertIsYarnWorkspace(true);
		"packages/P1".assertIsYarnWorkspace(true);
		"packages/@myScope/X1".assertIsYarnWorkspace(true);
		"packages/@myScope/X2".assertIsYarnWorkspace(true);
	}

	/** It's legal to pass in the yarn workspace root to the methods in {@link NodeModulesDiscoveryHelper}. */
	@Test
	def void testYarnWorkspace_RootAsArgument() {
		val projectFolders = createProjectFolders(
			"P0"
		);

		createPackageJson('''
			{
				"private": true,
				"workspaces": [ "P0" ]
			}
		''');

		val projectFoldersPlusRoot = projectFolders + #[""];
		projectFoldersPlusRoot.assertNodeModulesPaths(
			"node_modules",
			"P0/node_modules"
		);

		"".assertIsYarnWorkspace(true);
		"P0".assertIsYarnWorkspace(true);
	}

	// --------------------------------------------------------------------------------------------
	// utility methods:

	def private void createPackageJson(CharSequence content) {
		createPackageJson("", content);
	}

	def private void createPackageJson(String relativeProjectPath, CharSequence content) {
		val lines = content.toString.split(System.lineSeparator, -1);
		Files.write(root.resolve(relativeProjectPath).resolve(N4JSGlobals.PACKAGE_JSON), lines);
	}

	def private String[] createProjectFolders(String... relativeProjectPaths) {
		for (String relativeProjectPath : relativeProjectPaths) {
			val path = root.resolve(relativeProjectPath);
			Files.createDirectories(path);
			relativeProjectPath.createPackageJson('''
				{
					"name": "«path.fileName»"
				}
			''');
		}
		return relativeProjectPaths;
	}

	def private void assertNodeModulesPaths(String[] relativeProjectPaths, String... expectedNodeModulesPaths) {

		val absoluteProjectPaths = relativeProjectPaths.map[root.resolve(it)].toList;
		val absoluteNodeModulesPaths = nodeModulesDiscoveryHelper.findNodeModulesFolders(absoluteProjectPaths);

		val relativeNodeModulesPaths = absoluteNodeModulesPaths.map[root.relativize(it)].map[toString].toSet;

		assertEquals("different node_modules paths than expected", expectedNodeModulesPaths.toSet,
			relativeNodeModulesPaths);
	}

	def private void assertIsYarnWorkspace(String relativeProjectPath, boolean expectYarnWorkspaceMember) {
		val absoluteProjectPath = root.resolve(relativeProjectPath);
		val nodeModulesFolder = nodeModulesDiscoveryHelper.getNodeModulesFolder(absoluteProjectPath);

		if (expectYarnWorkspaceMember) {
			assertTrue("expected project to be a yarn workspace member: " + relativeProjectPath,
				nodeModulesFolder.isYarnWorkspace);
		} else {
			assertFalse("expected project *not* to be a yarn workspace member: " + relativeProjectPath,
				nodeModulesFolder.isYarnWorkspace);
		}
	}
}
