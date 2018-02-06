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
package org.eclipse.n4js.tests.manifest

import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IProject
import org.eclipse.emf.common.util.URI
import org.junit.Before
import org.junit.Test

/**
 */
class SingleProjectPluginTest extends AbstractBuilderParticipantTest {

	IProject projectUnderTest
	IFolder src
	IFolder src2
	IFile manifest

	@Before
	override void setUp() {
		super.setUp
		projectUnderTest = createJSProject("singleProjectTest")
		src = configureProjectWithXtext(projectUnderTest)
		manifest = projectUnderTest.project.getFile("manifest.n4mf")
		src2 = projectUnderTest.project.getFolder("src2");
		src2.create(false, true, null)
		waitForAutoBuild
	}

	private def void addSrc2ToSources() {
		addSrc2ToSources("src2")
	}

	private def void addSrc2ToSources(String folderName) {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.sourceFragment.head.paths.add(folderName)
		resource.save(null)
		waitForAutoBuild();
	}

	private def void removeSrc2FromSource() {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.sourceFragment.head.paths.remove(1)
		resource.save(null)
		waitForAutoBuild();
	}

	private def void useSourceFolder(String name) {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(projectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.sourceFragment.head.paths.clear
		pd.sourceFragment.head.paths.add(name)
		resource.save(null)
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
	def void testBrokenFileInSrc2ShowsErrorAfterManifestChange() throws Exception {
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
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of D cannot be resolved.
		assertMarkers("file should have four errors", c, 4);
		createTestFile(src, "D", "export class D {}");
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testFileInSrcWithMissingDepInOtherFolder() throws Exception {
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of D cannot be resolved.
		assertMarkers("file should have four errors", c, 4);
		createTestFile(src2, "D", "export class D {}");
		//Same as above, src2 folder is not set as source folder yet.
		assertMarkers("file should have four errors", c, 4);
		addSrc2ToSources
		assertMarkers("file should have no errors", c, 0);
	}

	@Test
	def void testDuplicateModuleInOtherFolder() throws Exception {
		val c1 = createTestFile(src,  "C", "class C1 {}")
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
		val c1 = createTestFile(src,  "C", "class C {}")
		val c2 = doCreateTestFile(src2, "C.n4jsd", "export external public class C {}");
		assertMarkers("file should have a single error", c1, 1);
		assertMarkers("file should have a single error", c2, 1);
	}

	@Test
	def void testJSIsNoDuplicate_01() throws Exception {
		addSrc2ToSources
		val c1 = doCreateTestFile(src,  "C.js", "var c = {}")
		val c2 = doCreateTestFile(src2, "C.n4jsd", "export external public class C {}");
		assertMarkers("file should have no errors", c1, 0);
		assertMarkers("file should have no errors", c2, 0);
	}

	@Test
	def void testJSIsNoDuplicate_02() throws Exception {
		val c1 = doCreateTestFile(src,  "C.js", "var c = {}")
		val c2 = doCreateTestFile(src, "C.n4js", "export public class C {}");
		assertMarkers("file should have no errors", c1, 0);
		assertMarkers("file should have no errors", c2, 0);
	}

	@Test
	def void testTwoFilesSourceFolderRemovedFromManifest() throws Exception {
		addSrc2ToSources
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);
		createTestFile(src2, "D", "export class D {}");
		assertMarkers("file should have no errors", c, 0);
		removeSrc2FromSource

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of  D cannot be resolved.
		assertMarkers("file should have four errors", c, 4);
	}

	@Test
	def void testTwoFilesSourceFolderRenamed() throws Exception {
		addSrc2ToSources("src3")
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);
		createTestFile(src2, "D", "export class D {}");

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of  D cannot be resolved.
		assertMarkers("file should have four errors", c, 4);
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

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Import of * as C from module was a proxy cannot be resolved.
		assertMarkers("file should have two errors", d, 2);
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

		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'C'.
		// Import of C cannot be resolved.
		assertMarkers("file should have three errors", d, 3);
	}

	@Test
	def void testManifestRemoved() throws Exception {
		val file = createTestFile(src, "C", "class C extends Unknown {}");
		assertMarkers("file should have an error", file, 1);
		manifest.delete(false, null)
		waitForAutoBuild
		assertMarkers("file should have no errors because it is no longer validated", file, 0);
	}

	@Test
	def void testManifestRecreated() throws Exception {
		manifest.delete(false, null)
		getResourceSet(projectUnderTest.project).resources.clear
		val file = createTestFile(src, "C", "class C extends Unknown {}");
		waitForAutoBuild
		createManifestN4MFFile(projectUnderTest)
		assertMarkers("file should have an error", file, 1);
	}

}
