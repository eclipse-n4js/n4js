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

import com.google.common.base.Predicate
import java.util.concurrent.TimeUnit
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IFolder
import org.eclipse.core.resources.IMarker
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONFactory
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerType
import org.eclipse.n4js.tests.builder.AbstractBuilderParticipantTest
import org.eclipse.n4js.tests.util.PackageJSONTestUtils
import org.eclipse.n4js.tests.util.ProjectTestsUtils
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES
import static org.junit.Assert.assertTrue

/**
 */
class MultiProjectPluginTest extends AbstractBuilderParticipantTest {

	IProject firstProjectUnderTest
	IProject secondProjectUnderTest
	IFolder src
	IFolder src2
	IFile projectDescriptionFile
	IFile projectDescriptionFile2
	Predicate<IMarker> errorMarkerPredicate = [
		val value = getAttribute(IMarker.SEVERITY);
		if (value instanceof Integer) {
			return IMarker.SEVERITY_ERROR === value.intValue;
		}
		return false;
	]

	@Before
	def void setUp2() {
		firstProjectUnderTest = createJSProject("multiProjectTest.first")
		secondProjectUnderTest = createJSProject("multiProjectTest.second")
		src = configureProjectWithXtext(firstProjectUnderTest)
		src2 = configureProjectWithXtext(secondProjectUnderTest)
		projectDescriptionFile = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) of first project file should have no errors",
			firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON), 0, errorMarkerPredicate);

		projectDescriptionFile2 = secondProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) file of second project should have no errors",
			secondProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON), 0, errorMarkerPredicate);
		waitForAutoBuild
	}

	private def void addSecondProjectToDependencies() {
		addProjectToDependencies(secondProjectUnderTest.project.name)
	}

	private def void addProjectToDependencies(String projectName) {
		val uri = URI.createPlatformResourceURI(projectDescriptionFile.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest);
		val resource = rs.getResource(uri, true);

		val JSONObject packageJSONRoot = PackageJSONTestUtils.getPackageJSONRoot(resource);
		PackageJSONTestUtils.addProjectDependency(packageJSONRoot, projectName, "*");

		resource.save(null)
		waitForAutoBuild();
	}

	private def void changeProjectType(IProject toChange, ProjectType desiredProjectType) {
		val uri = URI.createPlatformResourceURI(toChange.getFile(N4JSGlobals.PACKAGE_JSON).fullPath.toString, true);
		val resourceSet = getResourceSet(toChange);
		val resource = resourceSet.getResource(uri, true);
		val projectDescriptionDocument = resource.contents.head as JSONDocument;
		val projectDescriptionObject = projectDescriptionDocument.content as JSONObject;

		PackageJSONTestUtils.setProjectType(projectDescriptionObject, desiredProjectType)

		resource.save(null);
		waitForAutoBuild;
	}

	private def void removeProjectDependencies(IProject toChange) {
		val uri = URI.createPlatformResourceURI(toChange.getFile(N4JSGlobals.PACKAGE_JSON).fullPath.toString, true);
		val resourceSet = getResourceSet(toChange);
		val resource = resourceSet.getResource(uri, true);

		val packageJSONRoot = PackageJSONTestUtils.getPackageJSONRoot(resource);
		// set the 'dependencies' property of the package.json file to an empty object
		JSONModelUtils.setProperty(packageJSONRoot, DEPENDENCIES.name, JSONFactory.eINSTANCE.createJSONObject);

		resource.save(null);
		waitForAutoBuild;
	}

	private def void removeDependency() {
		val uri = URI.createPlatformResourceURI(projectDescriptionFile.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest.project);
		val resource = rs.getResource(uri, true);

		val packageJSONRoot = PackageJSONTestUtils.getPackageJSONRoot(resource);
		val dependenciesSection = JSONModelUtils.getProperty(packageJSONRoot, "dependencies").orElse(null) as JSONObject;
		// remove last dependency entry
		dependenciesSection?.nameValuePairs.removeAll(dependenciesSection?.nameValuePairs.last);
		
		resource.save(null)
		waitForAutoBuild();
	}

	private def rename(IFile projectDescriptionFile, String newName) {
		val uri = URI.createPlatformResourceURI(projectDescriptionFile.fullPath.toString, true);
		val rs = getResourceSet(firstProjectUnderTest.project);
		val resource = rs.getResource(uri, true);
		
		val packageJSONRoot = PackageJSONTestUtils.getPackageJSONRoot(resource);
		PackageJSONTestUtils.setProjectName(packageJSONRoot, newName);

		resource.save(null)
		waitForAutoBuild
	}

	@Test
	def void testFileInSrcWithMissingDepInOtherProject() throws Exception {
		val c = createTestFile(
			src,
			"C",
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
	def void testTwoFilesSourceFolderRemovedFromProjectDescriptionFile() throws Exception {
		addSecondProjectToDependencies
		assertMarkers("project description file (package.json) should have no errors after adding dependency",
			firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON), 0, errorMarkerPredicate);
		val c = createTestFile(
			src,
			"C",
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
	
//	@Rule
//	public RepeatedTestRule rule = new RepeatedTestRule();

	@Test
//	@RepeatTest(times=20)
	def void testTwoFilesProjectNewlyCreated() throws Exception {
		addProjectToDependencies("thirdProject")
		val c = createTestFile(
			src,
			"C",
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
		waitForAutoBuild
		assertMarkers("file should have no errors", c, 0, errorMarkerPredicate);
	}

	@Test
	def void testProjectDescriptionFileRecreated() throws Exception {
		projectDescriptionFile.delete(false, null)
		getResourceSet(firstProjectUnderTest.project).resources.clear
		val file = createTestFile(
			src,
			"C",
			'''
				import { D } from "D"
				class C extends D {}
			'''
		);
		createTestFile(src2, "D", "export public class D {}");
		waitForAutoBuild
		ProjectTestsUtils.createProjectDescriptionFile(firstProjectUnderTest)
		waitForAutoBuild

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
		val c1 = createTestFile(src, "C", "class C1 {}")
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
		val c1 = createTestFile(src, "C", "class C {}")
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

		assertIssues(projectDescriptionFile,
			"line 21: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);


		changeProjectType(secondProjectUnderTest, ProjectType.RUNTIME_LIBRARY);
		assertIssues(projectDescriptionFile,
			"line 21: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);

		changeProjectType(secondProjectUnderTest, ProjectType.LIBRARY);
		assertIssues(projectDescriptionFile,
			"line 21: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);
	}

	@Ignore("random") // Disabled due to timing issues. The project description file (package.json) re-validation is not triggered as the part of the build job but from a common job.
	@Test
	def void testDeleteExternalFolderValidateProjectDescriptionFileWithoutOpenedEditors() {

		val project = createJSProject('multiProjectTest.third', 'src', 'src-gen', [ builder |
			builder.withSourceContainer(SourceContainerType.EXTERNAL, "ext");
		]);
		configureProjectWithXtext(project);
		waitForAutoBuild;
		val projectDescriptionFile = project.getFile(N4JSGlobals.PACKAGE_JSON);

		removeProjectDependencies(project);
		changeProjectType(project, ProjectType.LIBRARY);
		val extFolder = project.getFolder('ext');
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('project description file (package.json) file should have exactly one error.', projectDescriptionFile, 1);

		// Wait after resource changes to be able to re-run the validation job.
		// This is not tracked by the builder.
		extFolder.create(true, true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('project description file (package.json) file should have zero errors.', projectDescriptionFile, 0);

		extFolder.delete(true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('project description file (package.json) file should have exactly one error.', projectDescriptionFile, 1);

		extFolder.create(true, true, null);
		Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('project description file (package.json) file should have zero errors.', projectDescriptionFile, 0);
	}

}
