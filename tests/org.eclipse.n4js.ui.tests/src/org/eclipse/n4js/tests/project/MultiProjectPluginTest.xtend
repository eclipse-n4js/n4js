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
import org.junit.Test

import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES
import static org.junit.Assert.assertTrue
import org.eclipse.n4js.projectModel.names.N4JSProjectName
import org.eclipse.n4js.projectModel.names.EclipseProjectName

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

		addProjectToDependencies(firstProjectUnderTest, N4JSGlobals.N4JS_RUNTIME, ProjectTestsUtils.N4JS_RUNTIME_DUMMY_VERSION);
		addProjectToDependencies(secondProjectUnderTest, N4JSGlobals.N4JS_RUNTIME, ProjectTestsUtils.N4JS_RUNTIME_DUMMY_VERSION);
		createAndRegisterDummyN4JSRuntime(firstProjectUnderTest)
		createAndRegisterDummyN4JSRuntime(secondProjectUnderTest)
		libraryManager.registerAllExternalProjects(new NullProgressMonitor())
		waitForAutoBuild

		projectDescriptionFile = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) of first project file should have no errors",
			firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON), 0, errorMarkerPredicate);

		projectDescriptionFile2 = secondProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON)
		assertMarkers("project description file (package.json) file of second project should have no errors",
			secondProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON), 0, errorMarkerPredicate);
	}

	private def void addSecondProjectToDependencies() {
		addProjectToDependencies(new EclipseProjectName(secondProjectUnderTest.project.name).toN4JSProjectName)
	}

	private def void addProjectToDependencies(N4JSProjectName projectName) {
		addProjectToDependencies(firstProjectUnderTest, projectName, "*");
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
		val packageJsonOfFirstProject = firstProjectUnderTest.project.getFile(N4JSGlobals.PACKAGE_JSON);
		assertMarkers("project description file (package.json) should have no errors before adding dependency",
			packageJsonOfFirstProject, 0, errorMarkerPredicate);
		addSecondProjectToDependencies
		assertIssues("project description file (package.json) should have 1 error after adding dependency",
			packageJsonOfFirstProject,
			"line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
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
		assertIssues("file should have four errors", c,
			"line 1: Cannot resolve import target :: resolving simple module import : found no matching modules",
			"line 1: Couldn't resolve reference to TExportableElement 'D'.",
			"line 2: Couldn't resolve reference to Type 'D'.",
			"line 1: Import of D cannot be resolved.");
	}
	
	@Test
	def void testTwoFilesProjectNewlyCreated() throws Exception {
		addProjectToDependencies(new N4JSProjectName("thirdProject"))
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
		addProjectToDependencies(new EclipseProjectName(secondProjectUnderTest.name).toN4JSProjectName);

		assertIssues(projectDescriptionFile,
			"line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);


		changeProjectType(secondProjectUnderTest, ProjectType.RUNTIME_LIBRARY);
		assertIssues(projectDescriptionFile,
			"line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);

		changeProjectType(secondProjectUnderTest, ProjectType.LIBRARY);
		assertIssues(projectDescriptionFile,
			"line 18: Project depends on workspace project multiProjectTest.second which is missing in the node_modules folder. " +
			"Either install project multiProjectTest.second or introduce a yarn workspace of both of the projects.");
		assertMarkers('project description file (package.json) file should have no errors.', projectDescriptionFile2, 0);
	}

	@Test
	def void testDeleteExternalFolderValidateProjectDescriptionFileWithoutOpenedEditors() {
		val project = createJSProject('multiProjectTest.third', 'src', 'src-gen', [ builder |
			builder.withSourceContainer(SourceContainerType.EXTERNAL, "ext");
		]);
		createAndRegisterDummyN4JSRuntime(project);
		addProjectToDependencies(project, N4JSGlobals.N4JS_RUNTIME, "*");
		configureProjectWithXtext(project);
		waitForAutoBuild;
		val projectDescriptionFile = project.getFile(N4JSGlobals.PACKAGE_JSON);

		changeProjectType(project, ProjectType.LIBRARY);
		val extFolder = project.getFolder('ext');
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('project description file (package.json) file should have exactly one error.', projectDescriptionFile, 1);

		// Wait after resource changes to be able to re-run the validation job.
		// This is not tracked by the builder.
		extFolder.create(true, true, null);
		testedWorkspace.build
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('project description file (package.json) file should have zero errors.', projectDescriptionFile, 0);

		extFolder.delete(true, null);
		testedWorkspace.build
		assertTrue('External folder \'ext\' should be missing', !extFolder.exists);
		assertMarkers('project description file (package.json) file should have exactly one error.', projectDescriptionFile, 1);

		extFolder.create(true, true, null);
		testedWorkspace.build
		assertTrue('External folder \'ext\' should be missing', extFolder.exists);
		assertMarkers('project description file (package.json) file should have zero errors.', projectDescriptionFile, 0);
	}

}
