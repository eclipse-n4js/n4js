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

import com.google.common.base.Predicate
import org.eclipse.n4js.projectModel.IN4JSProject
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.n4mf.N4mfFactory
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceFragmentType
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit
import org.junit.Ignore

/**
 */
class MultiProjectPluginTest extends AbstractBuilderParticipantTest {

	IProject firstProjectUnderTest
	IProject secondProjectUnderTest
	IFolder src
	IFolder src2
	IFile manifest
	IFile manifest2
	Predicate<IMarker> errorMarkerPredicate = [
		val value = getAttribute(IMarker.SEVERITY);
		if (value instanceof Integer) {
			return IMarker.SEVERITY_ERROR === value.intValue;
		}
		return false;
	]

	@Before
	override void setUp() {
		super.setUp
		firstProjectUnderTest = createJSProject("multiProjectTest.first")
		secondProjectUnderTest = createJSProject("multiProjectTest.second")
		src = configureProjectWithXtext(firstProjectUnderTest)
		src2 = configureProjectWithXtext(secondProjectUnderTest)
		manifest = firstProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers(
			"manifest of first project file should have no errors",
			firstProjectUnderTest.project.getFile("manifest.n4mf"),
			0,
			errorMarkerPredicate);

		manifest2 = secondProjectUnderTest.project.getFile("manifest.n4mf")
		assertMarkers(
			"manifest file of second project should have no errors",
			secondProjectUnderTest.project.getFile("manifest.n4mf"),
			0,
			errorMarkerPredicate);
		waitForAutoBuild
	}

	private def void addSecondProjectToDependencies() {
		addProjectToDependencies(secondProjectUnderTest.project.name)
	}

	private def void addProjectToDependencies(String projectId) {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		val dependency = N4mfFactory.eINSTANCE.createProjectDependency
		dependency.setProjectId(projectId)
		pd.projectDependencies.add(dependency)
		resource.save(null)
		waitForAutoBuild();
	}

	private def void changeProjectType(IProject toChange, ProjectType desiredProjectType) {
		val uri = URI.createPlatformResourceURI(toChange.getFile(IN4JSProject.N4MF_MANIFEST).fullPath.toString, true);
		val resourceSet = getResourceSet(toChange);
		val resource = resourceSet.getResource(uri, true);
		val description = resource.contents.head as ProjectDescription;
		description.projectType = desiredProjectType;
		resource.save(null);
		waitForAutoBuild;
	}

	private def void removeProjectDependencies(IProject toChange) {
		val uri = URI.createPlatformResourceURI(toChange.getFile(IN4JSProject.N4MF_MANIFEST).fullPath.toString, true);
		val resourceSet = getResourceSet(toChange);
		val resource = resourceSet.getResource(uri, true);
		val description = resource.contents.head as ProjectDescription;
		description.projectDependencies.clear();
		resource.save(null);
		waitForAutoBuild;
	}

	private def void removeDependency() {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.projectDependencies.remove(pd.projectDependencies.last)
		resource.save(null)
		waitForAutoBuild();
	}

	private def rename(IFile manifest, String newName) {
		val uri = URI.createPlatformResourceURI(manifest.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest.project);
		val resource = rs.getResource(uri, true);
		val ProjectDescription pd = resource.contents.head as ProjectDescription
		pd.setProjectId(newName)
		resource.save(null)
		waitForAutoBuild
	}

	@Test
	def void testFileInSrcWithMissingDepInOtherProject() throws Exception {
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
		createTestFile(src2, "D", "export public class D {}");
		// Same as above, errors are not resolved by just exporting class, it should be added as a dependency.
		assertMarkers("file should have four errors", c, 4);
		addSecondProjectToDependencies
		assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
	}

	@Test
	def void testTwoFilesSourceFolderRemovedFromManifest() throws Exception {
		addSecondProjectToDependencies
		assertMarkers("manifest file should have no errors after adding dependency", firstProjectUnderTest.project.getFile("manifest.n4mf"), 0, errorMarkerPredicate);
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);
		createTestFile(src2, "D", "export public class D {}");
		assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
		removeDependency
		// Cannot resolve import target :: resolving simple module import : found no matching modules
		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of D cannot be resolved.
		assertMarkers("file should have four errors", c, 4);
	}

	@Test
	def void testTwoFilesProjectNewlyCreated() throws Exception {
		addProjectToDependencies("thirdProject")
		val c = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);
		val thirdProject = createJSProject("thirdProject")
		val src3 = configureProjectWithXtext(thirdProject)

		createTestFile(src3, "D", "export public class D {}");
		// waitForAutobuild may wait while the autobuild is also waiting to start
		// thus we trigger the incremental build here
		firstProjectUnderTest.project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, new NullProgressMonitor)
		assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
	}

	@Test
	def void testManifestRecreated() throws Exception {
		manifest.delete(false, null)
		getResourceSet(firstProjectUnderTest.project).resources.clear
		val file = createTestFile(src, "C",
				'''
					import { D } from "D"
					class C extends D {}
				'''
			);
		createTestFile(src2, "D", "export public class D {}");
		waitForAutoBuild
		createManifestN4MFFile(firstProjectUnderTest)

		// Couldn't resolve reference to IdentifiableElement 'D'.
		// Couldn't resolve reference to TModule 'D'.
		// Couldn't resolve reference to Type 'D'.
		// Import of D cannot be resolved.
		assertMarkers("file should have four errors", file, 4);
		addSecondProjectToDependencies
		assertMarkers("file should have no errors", file, 0, errorMarkerPredicate);
	}

	@Test
	def void testDuplicateModuleInOtherFolder() throws Exception {
		val c1 = createTestFile(src,  "C", "class C1 {}")
		val c2 = createTestFile(src2, "C", "class C2 {}");
		assertMarkers("file should have no errors", c1, 0, errorMarkerPredicate);
		assertMarkers("file should have no errors", c2, 0, errorMarkerPredicate);
		addSecondProjectToDependencies
		assertMarkers("file should have a single error", c1, 1);
		assertMarkers("file should have no errors", c2, 0);
		removeDependency
		assertMarkers("file should have no errors", c1, 0, errorMarkerPredicate);
		assertMarkers("file should have no errors", c2, 0, errorMarkerPredicate);
	}

	@Test
	def void testDuplicateN4JSDInOtherFolder() throws Exception {
		addSecondProjectToDependencies
		val c1 = createTestFile(src,  "C", "class C {}")
		val c2 = doCreateTestFile(src2, "C.n4jsd", "export external public class C {}");
		assertMarkers("file should have a single error", c1, 1);
		assertMarkers("file should have no errors", c2, 0);
		removeDependency
		assertMarkers("file should have no errors", c1, 0, errorMarkerPredicate);
		assertMarkers("file should have no errors", c2, 0, errorMarkerPredicate);
	}

	@Test
	def void testChangeProjectTypeWithoutOpenedEditors() {
		changeProjectType(firstProjectUnderTest, ProjectType.LIBRARY);
		changeProjectType(secondProjectUnderTest, ProjectType.LIBRARY);
		removeProjectDependencies(secondProjectUnderTest);
		addProjectToDependencies(secondProjectUnderTest.name);

		assertMarkers('Manifest file should have no errors.', manifest, 0);
		assertMarkers('Manifest file should have no errors.', manifest2, 0);

		changeProjectType(secondProjectUnderTest, ProjectType.RUNTIME_LIBRARY);
		assertMarkers('Manifest file should have one error.', manifest, 1);
		assertMarkers('Manifest file should have no errors.', manifest2, 0);

		changeProjectType(secondProjectUnderTest, ProjectType.LIBRARY);
		assertMarkers('Manifest file should have no errors.', manifest, 0);
		assertMarkers('Manifest file should have no errors.', manifest2, 0);
	}

	@Ignore('Disabled due to timing issues. The manifest re-validation is not triggered as the part of the build job but from a common job.')
	@Test
	def void testDeleteExternalFolderValidateManifestWithoutOpenedEditors() {

		val project = createJSProject('multiProjectTest.third', 'src', 'src-gen', [
			it.sourceFragment += N4mfFactory.eINSTANCE.createSourceFragment => [
				pathsRaw.addAll('ext');
				sourceFragmentType = SourceFragmentType.EXTERNAL;
			];
		]);
		configureProjectWithXtext(project);
		waitForAutoBuild;
		val manifest = project.getFile(IN4JSProject.N4MF_MANIFEST);

		removeProjectDependencies(project);
		changeProjectType(project, ProjectType.LIBRARY);
		val extFolder = project.getFolder('ext');
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('Manifest file should have exactly one error.', manifest, 1);

		// Wait after resource changes to be able to re-run the validation job.
		// This is not tracked by the builder.
		extFolder.create(true, true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('Manifest file should have zero errors.', manifest, 0);

		extFolder.delete(true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('Manifest file should have exactly one error.', manifest, 1);

		extFolder.create(true, true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('Manifest file should have zero errors.', manifest, 0);
	}

}
