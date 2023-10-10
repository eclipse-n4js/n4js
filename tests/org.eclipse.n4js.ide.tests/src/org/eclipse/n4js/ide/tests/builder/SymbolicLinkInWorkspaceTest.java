/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.utils.io.FileUtils;
import org.junit.Test;

/**
 * Tests workspaces in which one or more projects have a path that crosses symbolic links, both taking into account
 * workspace projects and projects located in the "node_modules" folder.
 *
 * Hard links are not tested, for now.
 */

public class SymbolicLinkInWorkspaceTest extends AbstractIdeTest {

	@Test
	public void testSymLinkInNodeModulesFolder() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("ProjectOther");
		Path other = createProjectOutsideWorkspace("ProjectOther", "Other");
		Path nodeModulesFolder = getNodeModulesFolder().toPath();
		Files.createSymbolicLink(nodeModulesFolder.resolve("ProjectOther"), other);
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInNodeModulesFolder_withScope01() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("@someScope/ProjectOther");
		Path other = createProjectOutsideWorkspace("@someScope/ProjectOther", "Other");
		Path nodeModulesFolder = getNodeModulesFolder().toPath();
		Files.createSymbolicLink(nodeModulesFolder.resolve("@someScope"), other.getParent());
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/@someScope/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInNodeModulesFolder_withScope02() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("@someScope/ProjectOther");
		Path other = createProjectOutsideWorkspace("@someScope/ProjectOther", "Other");
		Path nodeModulesFolder = getNodeModulesFolder().toPath();
		Files.createDirectory(nodeModulesFolder.resolve("@someScope"));
		Files.createSymbolicLink(nodeModulesFolder.resolve("@someScope").resolve("ProjectOther"), other);
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/node_modules/@someScope/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInPackagesFolder() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("ProjectOther");
		Path other = createProjectOutsideWorkspace("ProjectOther", "Other");
		Path packagesFolder = getProjectLocation().toPath();
		Files.createSymbolicLink(packagesFolder.resolve("ProjectOther"), other);
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/packages/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInPackagesFolder_withScope01() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("@someScope/ProjectOther");
		Path other = createProjectOutsideWorkspace("@someScope/ProjectOther", "Other");
		Path packagesFolder = getProjectLocation().toPath();
		Files.createSymbolicLink(packagesFolder.resolve("@someScope"), other.getParent());
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/packages/@someScope/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInPackagesFolder_withScope02() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("@someScope/ProjectOther");
		Path other = createProjectOutsideWorkspace("@someScope/ProjectOther", "Other");
		Path packagesFolder = getProjectLocation().toPath();
		Files.createDirectory(packagesFolder.resolve("@someScope"));
		Files.createSymbolicLink(packagesFolder.resolve("@someScope").resolve("ProjectOther"), other);
		startAndWaitForLspServer();

		assertProjectsInWorkspace(
				"yarn-test-project",
				"yarn-test-project/node_modules/n4js-runtime",
				"yarn-test-project/packages/@someScope/ProjectOther",
				"yarn-test-project/packages/ProjectMain");
		assertNoIssues();
	}

	@Test
	public void testSymLinkInProject_sourceFolderIsSymLink() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("ProjectOther");
		Path other = createProjectOutsideWorkspace("ProjectOther", "Other");
		Path packagesFolder = getProjectLocation().toPath();
		Path otherInWorkspace = packagesFolder.resolve("ProjectOther");
		FileCopier.copy(other, otherInWorkspace);
		FileDeleter.delete(otherInWorkspace.resolve("src"));
		Files.createSymbolicLink(otherInWorkspace.resolve("src"), other.resolve("src"));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	@Test
	public void testSymLinkInProject_subFolderOfSourceFolderIsSymLink() throws Exception {
		createYarnWorkspaceWithProjectMainWithDependencyTo("ProjectOther");
		Path other = createProjectOutsideWorkspace("ProjectOther", "Other");
		Path packagesFolder = getProjectLocation().toPath();
		Path otherInWorkspace = packagesFolder.resolve("ProjectOther");
		FileCopier.copy(other, otherInWorkspace);
		Path folderB = otherInWorkspace.resolve("src").resolve("a").resolve("b");
		FileDeleter.delete(folderB);
		Files.createSymbolicLink(folderB, other.resolve("src").resolve("a").resolve("b"));
		startAndWaitForLspServer();
		assertNoIssues();
	}

	private void createYarnWorkspaceWithProjectMainWithDependencyTo(CharSequence dependenciesOfProjectMain) {
		testWorkspaceManager.createTestYarnWorkspaceOnDisk(Map.of("ProjectMain", Map.of(
				"Main", """
							import {ClassOther} from "ModuleOther"
							import {NestedClassOther} from "a/b/c/NestedModuleOther"
							new ClassOther();
							new NestedClassOther();
						""",
				CFG_DEPENDENCIES, dependenciesOfProjectMain)));
	}

	private Path createProjectOutsideWorkspace(String projectName, String nameSuffix) throws IOException {
		Path tempFolder = FileUtils.createTempDirectory(this.getClass().getSimpleName() + "_");
		Path projectFolder = tempFolder.resolve(projectName); // note: supports npm scopes!
		Path srcFolder = projectFolder.resolve("src");
		Path srcFolderABC = srcFolder.resolve("a").resolve("b").resolve("c");
		Files.createDirectories(projectFolder);
		Files.createDirectories(srcFolderABC);
		Files.writeString(projectFolder.resolve(PACKAGE_JSON), """
					{
						"name": "%s",
						"version": "0.0.1",
						"n4js": {
							"projectType": "library",
							"output": "src-gen",
							"sources": { "source": [ "src" ] }
						},
						"dependencies": {
							"n4js-runtime": "*"
						}
					}
				""".formatted(projectName));
		Files.writeString(srcFolder.resolve("Module" + nameSuffix + ".n4js"), """
					export public class Class%s {}
				""".formatted(nameSuffix));
		Files.writeString(srcFolderABC.resolve("NestedModule" + nameSuffix + ".n4js"), """
					export public class NestedClass%s {}
				""".formatted(nameSuffix));
		return projectFolder;
	}
}
