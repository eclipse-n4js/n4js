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

import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.junit.Before
import org.junit.Test

/**
 */
class SingleProjectPluginTest extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder src2
	IFile projectDescriptionFile

	@Before
	def void setUp2() {
		projectUnderTest = createJSProject("singleProjectTest")
		src = configureProjectWithXtext(projectUnderTest)
		projectDescriptionFile = projectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		src2 = projectUnderTest.project.getFolder("src2");
		src2.create(false, true, null)
		waitForAutoBuild
	}

	private def void addSrc2ToSources() {
		updateSourceContainers("src", "src2");
	}

	private def void removeSrc2FromSource() {
		updateSourceContainers("src");
	}

	private def void addSrc3ToSources() {
		updateSourceContainers("src", "src3");
	}

	private def void updateSourceContainers(String... sourceContainers) {
		projectDescriptionTestHelper.updateProjectDescription(projectDescriptionFile, [ root |
			// add 'src2' to the end of the list of SOURCE source containers
			PackageJSONTestUtils.setSourceContainerSpecifiers(root, SourceContainerType.SOURCE, sourceContainers);
		])
		waitForAutoBuild();
	}

	private def void useSourceFolder(String name) {
		projectDescriptionTestHelper.updateProjectDescription(projectDescriptionFile) [ root |
			PackageJSONTestUtils.setSourceContainerSpecifiers(
				root,
				SourceContainerType.SOURCE,
				#[name]
			)
		]
		waitForAutoBuild();
	}

	private def rename(IFolder folder, String newName) {
		folder.move(folder.fullPath.removeLastSegments(1).append(newName), false, null)
		waitForAutoBuild
	}

	@Test
	def void testFileInSrcNoError() throws Exception {
		val file = createTestFile(src, "C", "class C {}");
		assertMarkers("file should have no errors", file, 0);
	}

	@Test
	def void testBrokenFileInSrc2NoError() throws Exception {
		val file = createTestFile(src2, "C", "class C extends Unknown {}");
		assertMarkers("file should have no errors", file, 0);
	}

	@Test
	def void testBrokenFileInSrc2ShowsErrorAfterProjectDescriptionChange() throws Exception {
		val file = createTestFile(src2, "C", "class C extends Unknown {}");
		assertMarkers("file should have no errors", file, 0);
		addSrc2ToSources
		assertMarkers("file should have an error", file, 1);
	}

	@Test
	def void testFileInSrcWithError() throws Exception {
		val file = createTestFile(src, "C", "class C extends Unknown {}");
		assertMarkers("file should have an error", file, 1);
	}

	@Test
	def void testFileInSrcWithMissingDep() throws Exception {
		val c = createTestFile(
			src,
			"C",
			'''
				import { D } from "D"
				class C extends D {}
			'''
		);

		assertIssues("file should have two errors", c,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
			"line 2: Couldn't resolve reference to Type 'D'."
		);
		createTestFile(src, "D", "export class D {}");
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testFileInSrcWithMissingDepInOtherFolder() throws Exception {
		val c = createTestFile(
			src,
			"C",
			'''
				import { D } from "D"
				class C extends D {}
			'''
		);

		assertIssues("file should have two errors", c,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
			"line 2: Couldn't resolve reference to Type 'D'."
		);
		createTestFile(src2, "D", "export class D {}");
		// Same as above, src2 folder is not set as source folder yet.
		assertIssues("file should have two errors", c,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
			"line 2: Couldn't resolve reference to Type 'D'."
		);
		addSrc2ToSources
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testDuplicateModuleInOtherFolder() throws Exception {
		val c1 = createTestFile(src, "C", "class C1 {}")
		val c2 = createTestFile(src2, "C", "class C2 {}");
		assertMarkers("file should have no errors", c1, 0);
		assertMarkers("file should have no errors", c2, 0);
		addSrc2ToSources
		assertMarkers("file should have a single error", c1, 1);
		assertMarkers("file should have a single error", c2, 1);
		removeSrc2FromSource
		assertMarkers("file should have no errors", c1, 0);
		// since src2 is no longer a source folder, it is not validated - markers remain
		assertMarkers("file should have one error", c2, 1);
	}

	@Test
	def void testDuplicateN4JSDInOtherFolder() throws Exception {
		addSrc2ToSources
		val c1 = createTestFile(src, "C", "class C {}")
		val c2 = doCreateTestFile(src2, "C.n4jsd", "export external public class C {}");
		assertMarkers("file should have a single error", c1, 1);
		assertMarkers("file should have a single error", c2, 1);
	}

	@Test
	def void testJSIsNoDuplicate_01() throws Exception {
		addSrc2ToSources
		val c1 = doCreateTestFile(src, "C.js", "var c = {}")
		val c2 = doCreateTestFile(src2, "C.n4jsd", "export external public class C {}");
		assertMarkers("file should have no errors", c1, 0);
		assertMarkers("file should have no errors", c2, 0);
	}

	@Test
	def void testJSIsNoDuplicate_02() throws Exception {
		val c1 = doCreateTestFile(src, "C.js", "var c = {}")
		val c2 = doCreateTestFile(src, "C.n4js", "export public class C {}");
		assertMarkers("file should have no errors", c1, 0);
		assertMarkers("file should have no errors", c2, 0);
	}

	@Test
	def void testTwoFilesSourceFolderRemovedFromProjectDescription() throws Exception {
		addSrc2ToSources
		val c = createTestFile(
			src,
			"C",
			'''
				import { D } from "D"
				class C extends D {}
			'''
		);
		createTestFile(src2, "D", "export class D {}");
		assertMarkers("file should have no errors", c, 0);
		removeSrc2FromSource

		assertIssues("file should have two errors", c,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
			"line 2: Couldn't resolve reference to Type 'D'."
		);
	}

	@Test
	def void testTwoFilesSourceFolderRenamed() throws Exception {
		addSrc3ToSources();
		val c = createTestFile(
			src,
			"C",
			'''
				import { D } from "D"
				class C extends D {}
			'''
		);
		createTestFile(src2, "D", "export class D {}");

		assertIssues("file should have two errors", c,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found.",
			"line 2: Couldn't resolve reference to Type 'D'."
		);
		src2.rename("src3")
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testTwoFilesMovedToDifferentSourceFolder() throws Exception {
		val cFolder = src.createFolder("a/b/c")
		var c = createTestFile(cFolder, "C", "export class C {}");
		var d = createTestFile(cFolder, "D", "import * as C from 'a/b/c/C'");
		waitForAutoBuild
		assertMarkers("file should have no errors", c, 0);

		// The import of * as C from a/b/c/C is unused.
		assertMarkers("file should have one warning", d, 1);
		val mainSrc = projectUnderTest.project.createFolder("main/src")
		src.getFolder("a").move(mainSrc.fullPath.append("a"), false, false, null)
		useSourceFolder("main/src")
		waitForAutoBuild
		c = mainSrc.findMember("a/b/c/" + c.name) as IFile
		d = mainSrc.findMember("a/b/c/" + d.name) as IFile
		assertMarkers("file should have no errors", c, 0);
		// The import of * as C from a/b/c/C is unused.
		assertMarkers("file should have one warning", d, 1);
		mainSrc.getFolder("a/b").rename("d")
		waitForAutoBuild
		d = mainSrc.findMember("a/d/c/" + d.name) as IFile

		assertIssues("file should have one error", d,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found."
		);
	}

	@Test
	def void testTwoFilesMovedToDifferentSourceFolderAndPackage() throws Exception {
		val cFolder = src.createFolder("a/b/c")
		var c = createTestFile(cFolder, "C", "export class C {}");
		var d = createTestFile(cFolder, "D", "import { C } from 'a/b/c/C'");
		waitForAutoBuild
		assertMarkers("file should have no errors", c, 0);
		assertMarkers("file should have one warning", d, 1); // Expecting "unused import"-warning
		val mainSrcX = projectUnderTest.project.createFolder("main/src/x")
		src.getFolder("a").move(mainSrcX.fullPath.append("a"), false, false, null)
		useSourceFolder("main/src")
		waitForAutoBuild
		c = mainSrcX.findMember("a/b/c/" + c.name) as IFile
		d = mainSrcX.findMember("a/b/c/" + d.name) as IFile
		assertMarkers("file should have no errors", c, 0);

		assertIssues("file should have one error", d,
			"line 1: Cannot resolve plain module specifier (without project name as first segment): no matching module found."
		);
	}

	@Test
	def void testProjectDescriptionFileRemoved() throws Exception {
		val file = createTestFile(src, "C", "class C extends Unknown {}");
		assertMarkers("file should have an error", file, 1);
		projectDescriptionFile.delete(false, null)
		waitForAutoBuild
		assertMarkers("file should have no errors because it is no longer validated", file, 0);
	}

	@Test
	def void testProjectDescriptionFileRecreated() throws Exception {
		projectDescriptionFile.delete(false, null)
		val file = createTestFile(src, "C", "class C extends Unknown {}");
		ProjectTestsUtils.createProjectDescriptionFile(projectUnderTest)
		testedWorkspace.build
		assertMarkers("file should have an error", file, 1);
	}

}
