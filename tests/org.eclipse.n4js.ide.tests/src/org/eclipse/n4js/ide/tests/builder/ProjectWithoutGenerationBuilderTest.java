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
package org.eclipse.n4js.ide.tests.builder;

import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.DEFINITION;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.PLAINJS;
import static org.eclipse.n4js.packagejson.projectDescription.ProjectType.VALIDATION;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * Tests the builder with project types for which the generator is disabled, see
 * {@link N4JSGlobals#PROJECT_TYPES_WITHOUT_GENERATION}.
 */
@SuppressWarnings("javadoc")
public class ProjectWithoutGenerationBuilderTest extends AbstractIdeTest {

	@Test
	public void testDefinitionProject_sourceFilesAreValidated_sourceFolderUnspecified() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.absent());
	}

	@Test
	public void testDefinitionProject_sourceFilesAreValidated_sourceFolderDot() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.of("."));
	}

	@Test
	public void testDefinitionProject_sourceFilesAreValidated_sourceFolderCustom() {
		doTestSourceFilesAreValidated(DEFINITION, Optional.of("src"));
	}

	@Test
	public void testValidationProject_sourceFilesAreValidated_sourceFolderUnspecified() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.absent());
	}

	@Test
	public void testValidationProject_sourceFilesAreValidated_sourceFolderDot() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.of("."));
	}

	@Test
	public void testValidationProject_sourceFilesAreValidated_sourceFolderCustom() {
		doTestSourceFilesAreValidated(VALIDATION, Optional.of("src"));
	}

	private void doTestSourceFilesAreValidated(ProjectType projectType, Optional<String> sourceFolder) {
		createSampleProject(projectType, sourceFolder, true);
		startAndWaitForLspServer();
		assertIssues2(
				Pair.of("Test.n4jsd", List.of(
						"(Error, [1:1 - 1:8], Only namespaces, classes, interfaces, enums, type aliases and functions declared as external as well as structurally typed interfaces are allowed in n4jsd files.)")));
	}

	@Test
	public void testPlainjsProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(PLAINJS, Optional.absent());
	}

	@Test
	public void testPlainjsProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(PLAINJS, Optional.of("."));
	}

	@Test
	public void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(DEFINITION, Optional.absent());
	}

	@Test
	public void testDefinitionProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(DEFINITION, Optional.of("."));
	}

	@Test
	public void testValidationProject_cleanDoesNotDeleteEntireProject_sourceFolderUnspecified() {
		doTestCleanDoesNotDeleteEntireProject(VALIDATION, Optional.absent());
	}

	@Test
	public void testValidationProject_cleanDoesNotDeleteEntireProject_sourceFolderDot() {
		doTestCleanDoesNotDeleteEntireProject(VALIDATION, Optional.of("."));
	}

	private void doTestCleanDoesNotDeleteEntireProject(ProjectType projectType, Optional<String> sourceFolder) {
		createSampleProject(projectType, sourceFolder, false);
		startAndWaitForLspServer();
		assertNoIssues();

		File projectRoot = getProjectRoot();
		String testFileName = (projectType == PLAINJS) ? "Test.js" : "Test.n4jsd";

		cleanBuildAndWait();
		assertTrue(projectRoot.isDirectory());
		assertTrue(new File(projectRoot, testFileName).isFile());
		assertTrue(new File(projectRoot, PACKAGE_JSON).isFile());
	}

	private void createSampleProject(ProjectType projectType, Optional<String> sourceFolder,
			boolean withValidationError) {

		String withValidationErrorN4js = withValidationError ? """
				Object; // intentional error
				""" : "";
		String definesPackageProp = projectType == DEFINITION ? """
				, "definesPackage": "defined_project"
				""" : "";
		String sourcesProp = sourceFolder.isPresent() ? """
				, "sources": {
							"source": [
								"%s"
							]
						}
				""".formatted(sourceFolder.get()) : "";

		if (projectType == PLAINJS) {
			testWorkspaceManager.createTestProjectOnDisk(Map.of(
					"Test.js", """
								console.log('hello world');
							""",
					// don't use TestWorkspaceManager#DEFAULT_SOURCE_FOLDER here!
					CFG_SOURCE_FOLDER, sourceFolder.or("."),
					PACKAGE_JSON, """
								{
									"name": "%s",
									"version": "0.0.1",
									"n4js": {
										"projectType": "plainjs"
										%s
									}
								}
							""".formatted(DEFAULT_PROJECT_NAME, sourcesProp)));
		} else if (projectType == DEFINITION || projectType == VALIDATION) {
			String projectTypeKeyword = (projectType == DEFINITION) ? "definition" : "validation";
			testWorkspaceManager.createTestProjectOnDisk(Map.of(
					"Test.n4jsd", """
								export external public class Cls {}
								%s
							""".formatted(withValidationErrorN4js),
					// don't use TestWorkspaceManager#DEFAULT_SOURCE_FOLDER here!
					CFG_SOURCE_FOLDER, sourceFolder.or("."),
					PACKAGE_JSON, """
								{
									"name": "%s",
									"version": "0.0.1",
									"n4js": {
										"projectType": "%s"
										%s
										%s
									}
								}
							""".formatted(DEFAULT_PROJECT_NAME, projectTypeKeyword, definesPackageProp, sourcesProp)));
		} else {
			throw new IllegalStateException("project type not supported by this method: " + projectType);
		}
	}
}
