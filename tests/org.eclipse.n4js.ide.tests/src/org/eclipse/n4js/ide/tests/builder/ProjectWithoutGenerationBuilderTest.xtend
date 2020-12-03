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
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests the builder with project types for which the generator is disabled,
 * see {@link N4JSGlobals#PROJECT_TYPES_WITHOUT_GENERATION}.
 */
class ProjectWithoutGenerationBuilderTest extends AbstractIdeTest {

	@Test
	def void testDefinitionProject_n4jsdFilesAreValidated_sourceFolderUnspecified() {
		doTestDefinitionProject_n4jsdFilesAreValidated(Optional.absent());
	}

	@Test
	def void testDefinitionProject_n4jsdFilesAreValidated_sourceFolderDot() {
		doTestDefinitionProject_n4jsdFilesAreValidated(Optional.of("."));
	}

	@Test
	def void testDefinitionProject_n4jsdFilesAreValidated_sourceFolderCustom() {
		doTestDefinitionProject_n4jsdFilesAreValidated(Optional.of("src"));
	}

	def private void doTestDefinitionProject_n4jsdFilesAreValidated(Optional<String> sourceFolder) {
		createSampleDefinitionProject(sourceFolder, true);
		startAndWaitForLspServer();
		assertIssues(
			"Test.n4jsd" -> #[
				"(Error, [1:0 - 1:7], Only classes, interfaces, enums and functions declared as external as well as structural typed interfaces are allowed in n4jsd files.)"
			]
		);
	}

	@Test
	def void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestDefinitionProject_cleanDoesNotDeleteEntireProject(Optional.absent());
	}

	@Test
	def void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestDefinitionProject_cleanDoesNotDeleteEntireProject(Optional.of("."));
	}

	def private void doTestDefinitionProject_cleanDoesNotDeleteEntireProject(Optional<String> sourceFolder) {
		createSampleDefinitionProject(sourceFolder, false);
		startAndWaitForLspServer();
		assertNoIssues();

		val projectRoot = getProjectRoot();

		cleanBuildAndWait();
		assertTrue(projectRoot.isDirectory);
		assertTrue(new File(projectRoot, "Test.n4jsd").isFile);
		assertTrue(new File(projectRoot, PACKAGE_JSON).isFile);
	}

	def private void createSampleDefinitionProject(Optional<String> sourceFolder, boolean withValidationError) {
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
						"projectType": "definition",
						"definesPackage": "defined_project"«IF sourceFolder.present»,
						"sources": {
							"source": [
								"«sourceFolder.get»"
							]
						}«ENDIF»
					}
				}
			'''
		);
	}
}
