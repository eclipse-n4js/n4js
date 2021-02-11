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
package org.eclipse.n4js.tests.project

import java.nio.file.Files
import org.eclipse.n4js.packagejson.PackageJsonUtils
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

/**
 */
// converted from SingleProjectPluginTest
class SingleProjectIdeTest extends ConvertedIdeTest {

	private FileURI src
	private FileURI src2
	private FileURI projectDescriptionFile

	@Before
	def void setUp2() {
		testWorkspaceManager.createTestProjectOnDisk();
		startAndWaitForLspServer();
		assertNoIssues();
		projectDescriptionFile = getPackageJsonFile().toFileURI;
		src = getProjectRoot().toFileURI.appendSegment("src");
		src2 = getProjectRoot().toFileURI.appendSegment("src2");
		src2.toFile.mkdirs;
	}

	private def void addSrc2ToSources() {
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(getPackageJsonFile().toPath, SourceContainerType.SOURCE, "src2");
		joinServerRequests();
	}

	private def void removeSrc2FromSource() {
		PackageJsonUtils.removeSourceFoldersFromPackageJsonFile(getPackageJsonFile().toPath, "src2");
		joinServerRequests();
	}

	private def void addSrc3ToSources() {
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(getPackageJsonFile().toPath, SourceContainerType.SOURCE, "src3");
		joinServerRequests();
	}

	private def void addMainSrcToSources() {
		PackageJsonUtils.addSourceFoldersToPackageJsonFile(getPackageJsonFile().toPath, SourceContainerType.SOURCE, "main/src");
		joinServerRequests();
	}

	@Test
	def void testFileInSrcNoError() throws Exception {
		createFile("C", "class C {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testBrokenFileInSrc2NoError() throws Exception {
		createFile(src2.appendSegment("C.n4js"), "class C extends Unknown {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testBrokenFileInSrc2ShowsErrorAfterProjectDescriptionChange() throws Exception {
		createFile(src2.appendSegment("C.n4js"), "class C extends Unknown {}");
		joinServerRequests();
		assertNoIssues();
		addSrc2ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"C" -> #[
				"(Error, [0:16 - 0:23], Couldn't resolve reference to Type 'Unknown'.)"
			]
		);
	}

	@Test
	def void testFileInSrcWithError() throws Exception {
		createFile("C", "class C extends Unknown {}");
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:16 - 0:23], Couldn't resolve reference to Type 'Unknown'.)"
			]
		);
	}

	@Test
	def void testFileInSrcWithMissingDep() throws Exception {
		createFile("C", '''
			import { D } from "D"
			class C extends D {}
		''');
		joinServerRequests();

		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);
		createFile("D", "export class D {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testFileInSrcWithMissingDepInOtherFolder() throws Exception {
		createFile("C", '''
			import { D } from "D"
			class C extends D {}
		''');
		joinServerRequests();

		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);
		createFile(src2.appendSegment("D.n4js"), "export class D {}");
		joinServerRequests();
		// Same as above, src2 folder is not set as source folder yet.
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);
		addSrc2ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testDuplicateModuleInOtherFolder() throws Exception {
		createFile("C", "class C1 {}");
		createFile(src2.appendSegment("C.n4js"), "class C2 {}");
		joinServerRequests();
		assertNoIssues();
		addSrc2ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertEquals("unexpected number of issues in workspace", 2, getIssues().size);
		assertDuplicateModuleIssue(src.appendSegments("C.n4js"), DEFAULT_PROJECT_NAME, "src2/C.n4js");
		assertDuplicateModuleIssue(src2.appendSegments("C.n4js"), DEFAULT_PROJECT_NAME, "src/C.n4js");

		removeSrc2FromSource();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
		// note: in Eclipse the issue in file src2/C.n4js remained after removing the source folder,
		// so this would also be acceptable (but the issue in src/C.n4js must be gone)
	}

	@Test
	def void testDuplicateN4JSDInOtherFolder() throws Exception {
		addSrc2ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		createFile("C", "class C {}")
		createFile(src2.appendSegment("C.n4jsd"), "export external public class C {}");
		joinServerRequests();
		assertEquals("unexpected number of issues in workspace", 2, getIssues().size);
		assertDuplicateModuleIssue(src.appendSegments("C.n4js"), DEFAULT_PROJECT_NAME, "src2/C.n4jsd");
		assertDuplicateModuleIssue(src2.appendSegments("C.n4jsd"), DEFAULT_PROJECT_NAME, "src/C.n4js");
	}

	@Test
	def void testJSIsNoDuplicate_01() throws Exception {
		addSrc2ToSources();
		createFile(src.appendSegment("C.js"), "var c = {}");
		createFile(src.appendSegment("C.n4jsd"), "export external public class C {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testJSIsNoDuplicate_02() throws Exception {
		addSrc2ToSources();
		createFile(src.appendSegment("C.js"), "var c = {}");
		createFile(src2.appendSegment("C.n4jsd"), "export external public class C {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testJSIsNoDuplicate_03() throws Exception {
		createFile(src.appendSegment("C.js"), "var c = {}")
		createFile(src.appendSegment("C.n4js"), "export public class C {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testJSIsNoDuplicate_04() throws Exception {
		createFile(src.appendSegment("C.js"), "var c = {}")
		createFile(src2.appendSegment("C.n4js"), "export public class C {}");
		joinServerRequests();
		assertNoIssues();
	}

	@Test
	def void testTwoFilesSourceFolderRemovedFromProjectDescription() throws Exception {
		addSrc2ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		createFile("C", '''
			import { D } from "D"
			class C extends D {}
		''');
		createFile(src2.appendSegment("D.n4js"), "export class D {}");
		joinServerRequests();
		assertNoIssues();

		removeSrc2FromSource();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);
	}

	@Test
	def void testTwoFilesSourceFolderRenamed() throws Exception {
		addSrc3ToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		createFile("C", '''
			import { D } from "D"
			class C extends D {}
		''');
		createFile(src2.appendSegment("D.n4js"), "export class D {}");
		joinServerRequests();

		assertIssues(
			DEFAULT_PROJECT_NAME + "/package.json" -> #[
				"(Warning, [11:8 - 11:14], Source container path src3 does not exist.)"
			],
			"C" -> #[
				"(Error, [0:18 - 0:21], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)",
				"(Error, [1:16 - 1:17], Couldn't resolve reference to Type 'D'.)"
			]
		);
		renameFile(src2, "src3");
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertNoIssues();
	}

	@Test
	def void testTwoFilesMovedToDifferentSourceFolder() throws Exception {
		createFile("a/b/c/C", "export class C {}");
		createFile("a/b/c/D", "import * as C from 'a/b/c/C'");
		joinServerRequests();
		assertIssues(
			"a/b/c/D" -> #[
				"(Warning, [0:7 - 0:13], The import of * as C from a/b/c/C is unused.)"
			]
		);

		val mainSrc = src.parent.appendSegments("main", "src");
		mainSrc.toFile.mkdirs();
		moveFile(src.appendSegment("a"), mainSrc.toFile);
		joinServerRequests();
		assertNoIssues();

		addMainSrcToSources();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"D" -> #[
				"(Warning, [0:7 - 0:13], The import of * as C from a/b/c/C is unused.)"
			]
		);

		renameFile(mainSrc.appendSegments("a", "b"), "d");
		joinServerRequests();
		assertIssues(
			"D" -> #[
				"(Error, [0:19 - 0:28], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testTwoFilesMovedToDifferentSourceFolderAndPackage() throws Exception {
		createFile("a/b/c/C", "export class C {}");
		createFile("a/b/c/D", "import { C } from 'a/b/c/C'");
		joinServerRequests();
		assertIssues(
			"a/b/c/D" -> #[
				"(Warning, [0:9 - 0:10], The import of C is unused.)"
			]
		);

		val mainSrcX = src.parent.appendSegments("main", "src", "x");
		mainSrcX.toFile.mkdirs();
		moveFile(src.appendSegment("a"), mainSrcX.toFile);
		joinServerRequests();
		assertNoIssues();

		addMainSrcToSources(); // note: using main/src as source folder, not main/src/x
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"D" -> #[
				"(Error, [0:18 - 0:27], Cannot resolve plain module specifier (without project name as first segment): no matching module found.)"
			]
		);
	}

	@Test
	def void testProjectDescriptionFileRemoved() throws Exception {
		createFile("C", "class C extends Unknown {}");
		joinServerRequests();
		assertIssues(
			"C" -> #[
				"(Error, [0:16 - 0:23], Couldn't resolve reference to Type 'Unknown'.)"
			]
		);

		deleteFile(projectDescriptionFile);
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
languageClient.clearIssues();
cleanBuildAndWait();
		// file should have no errors because it is no longer validated
		assertNoIssues();
	}

	@Test
	def void testProjectDescriptionFileRecreated() throws Exception {
		val packageJsonContent = Files.readString(projectDescriptionFile.toPath);

		createFile("C", "class C extends Unknown {}");
		deleteFile(projectDescriptionFile);
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
languageClient.clearIssues();
cleanBuildAndWait();
		assertNoIssues();

		createFile(projectDescriptionFile, packageJsonContent);
		joinServerRequests();
// TODO GH-2060 next line should not be necessary
cleanBuildAndWait();
		assertIssues(
			"C" -> #[
				"(Error, [0:16 - 0:23], Couldn't resolve reference to Type 'Unknown'.)"
			]
		);
	}
}
