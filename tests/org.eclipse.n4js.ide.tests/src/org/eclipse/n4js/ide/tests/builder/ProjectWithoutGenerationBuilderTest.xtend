/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import com.google.common.base.Optional
import java.io.File
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.projectDescription.ProjectType
import org.junit.Test

import static org.eclipse.n4js.projectDescription.ProjectType.*
import static org.junit.Assert.*

/**
 * Tests the builder with project types for which the generator is disabled,
 * see {@link N4JSGlobals#PROJECT_TYPES_WITHOUT_GENERATION}.
 */
class ProjectWithoutGenerationBuilderTest extends AbstractIdeTest {

	@Test
	def void testDefinitionProject_sourceFilesAreValidated_sourceFolderUnspecified() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.absent());
	}

	@Test
	def void testDefinitionProject_sourceFilesAreValidated_sourceFolderDot() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.of("."));
	}

	@Test
	def void testDefinitionProject_sourceFilesAreValidated_sourceFolderCustom() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.of("src"));
	}

	@Test
	def void testValidationProject_sourceFilesAreValidated_sourceFolderUnspecified() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.absent());
	}

	@Test
	def void testValidationProject_sourceFilesAreValidated_sourceFolderDot() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.of("."));
	}

	@Test
	def void testValidationProject_sourceFilesAreValidated_sourceFolderCustom() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.of("src"));
	}

	def private void doTestSourceFilesAreValidated(ProjectType projectType, Optional<String> sourceFolder) {
		createSampleProject(projectType, sourceFolder, true);
		startAndWaitForLspServer();
		assertIssues(
			"Test.n4jsd" -> #[
				"(Error, [1:0 - 1:7], Only classes, interfaces, enums and functions declared as external as well as structural typed interfaces are allowed in n4jsd files.)"
			]
		);
	}

	@Test
	def void testPlainjsProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(PLAINJS, Optional.absent());
	}

	@Test
	def void testPlainjsProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(PLAINJS, Optional.of("."));
	}

	@Test
	def void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(DEFINITION, Optional.absent());
	}

	@Test
	def void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(DEFINITION, Optional.of("."));
	}

	@Test
	def void testValidationProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(VALIDATION, Optional.absent());
	}

	@Test
	def void testValidationProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(VALIDATION, Optional.of("."));
	}

	def private void doTestCleanDoesNotDeleteEntireProject(ProjectType projectType, Optional<String> sourceFolder) {
		createSampleProject(projectType, sourceFolder, false);
		startAndWaitForLspServer();
		assertNoIssues();

		val projectRoot = getProjectRoot();
		val testFileName = if (projectType === PLAINJS) "Test.js" else "Test.n4jsd";

		cleanBuildAndWait();
		assertTrue(projectRoot.isDirectory);
		assertTrue(new File(projectRoot, testFileName).isFile);
		assertTrue(new File(projectRoot, PACKAGE_JSON).isFile);
	}

	def private void createSampleProject(ProjectType projectType, Optional<String> sourceFolder, boolean withValidationError) {
		if (projectType === PLAINJS) {
			testWorkspaceManager.createTestProjectOnDisk(
				"Test.js" -> '''
					console.log('hello world');
				''',
				CFG_SOURCE_FOLDER -> sourceFolder.or("."), // don't use TestWorkspaceManager#DEFAULT_SOURCE_FOLDER here!
				PACKAGE_JSON -> '''
					{
						"name": "«DEFAULT_PROJECT_NAME»",
						"version": "0.0.1",
						"n4js": {
							"projectType": "plainjs"«IF sourceFolder.present»,
							"sources": {
								"source": [
									"«sourceFolder.get»"
								]
							}«ENDIF»
						}
					}
				'''
			);
		} else if (projectType === DEFINITION || projectType === VALIDATION) {
			val projectTypeKeyword = if (projectType === DEFINITION) "definition" else "validation";
			testWorkspaceManager.createTestProjectOnDisk(
				"Test.n4jsd" -> '''
					export external public class Cls {}
					«IF withValidationError»Object; // intentional error«ENDIF»
				''',
				CFG_SOURCE_FOLDER -> sourceFolder.or("."), // don't use TestWorkspaceManager#DEFAULT_SOURCE_FOLDER here!
				PACKAGE_JSON -> '''
					{
						"name": "«DEFAULT_PROJECT_NAME»",
						"version": "0.0.1",
						"n4js": {
							"projectType": "«projectTypeKeyword»"«IF projectType === DEFINITION»,
							"definesPackage": "defined_project"«ENDIF»«IF sourceFolder.present»,
							"sources": {
								"source": [
									"«sourceFolder.get»"
								]
							}«ENDIF»
						}
					}
				'''
			);
		} else {
			throw new IllegalStateException("project type not supported by this method: " + projectType);
		}
	}
}
