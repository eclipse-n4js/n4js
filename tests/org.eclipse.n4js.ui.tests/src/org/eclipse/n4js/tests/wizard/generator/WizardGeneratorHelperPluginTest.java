/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.wizard.generator;

import java.util.Arrays;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.N4JSUiInjectorProvider;
import org.eclipse.n4js.packagejson.model.edit.IJSONDocumentModification;
import org.eclipse.n4js.packagejson.model.edit.PackageJsonModificationProvider;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tests.builder.TestedN4JSWorkspace;
import org.eclipse.n4js.tests.util.ProjectTestsUtils;
import org.eclipse.n4js.ui.wizard.generator.WizardGeneratorHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Applies {@link IJSONDocumentModification}s as produced by {@link PackageJsonModificationProvider} to package.json
 * files and checks the contents of the resulting serialized and formatted contents of the edited files.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSUiInjectorProvider.class)
public class WizardGeneratorHelperPluginTest {

	private static final String TEST_PROJECT_NAME = "Test";

	@Inject
	private Provider<ResourceSet> resourceSetProvider;

	@Inject
	private WizardGeneratorHelper generatorHelper;

	/**
	 * Used for proper cleanup
	 */
	@Inject
	@Rule
	public TestedN4JSWorkspace testedWorkspace;

	/**
	 * Edits the AST of a package.json file by inserting a new dependency and asserts the resulting serialized
	 * package.json contents.
	 */
	@Test
	public void testInsertProjectDependencies() throws CoreException {
		final IProject testProject = ProjectTestsUtils.createJSProject(TEST_PROJECT_NAME);

		final ResourceSet resourceSet = this.resourceSetProvider.get();
		final XtextResource packageJson = (XtextResource) resourceSet.getResource(
				URI.createPlatformResourceURI(TEST_PROJECT_NAME + "/" + IN4JSProject.PACKAGE_JSON, true),
				true);

		// insert a first project dependency
		generatorHelper.applyJSONModifications(packageJson,
				Arrays.asList(
						PackageJsonModificationProvider.insertProjectDependencies(Arrays.asList("dep1"))));

		Assert.assertEquals("Formatted document matches expectations after inserting a single project dependency.",
				WizardGeneratorHelperTestExpectations.ONE_DEPENDENCY,
				getPackageJsonContents(testProject));

		// insert another project dependency
		generatorHelper.applyJSONModifications(packageJson,
				Arrays.asList(
						PackageJsonModificationProvider.insertProjectDependencies(Arrays.asList("dep2"))));

		Assert.assertEquals("Formatted document matches expectations after inserting another project dependency.",
				WizardGeneratorHelperTestExpectations.TWO_DEPENDENCIES,
				getPackageJsonContents(testProject));
	}

	/**
	 * Edits the AST of a package.json file by inserting a new required runtime library and asserts the resulting
	 * serialized package.json contents.
	 */
	@Test
	public void testInsertRequiredRuntimeLibraries() throws CoreException {
		final IProject testProject = ProjectTestsUtils.createJSProject(TEST_PROJECT_NAME);

		final ResourceSet resourceSet = this.resourceSetProvider.get();
		final XtextResource packageJson = (XtextResource) resourceSet.getResource(
				URI.createPlatformResourceURI(TEST_PROJECT_NAME + "/" + IN4JSProject.PACKAGE_JSON, true),
				true);

		// add required runtime library
		generatorHelper.applyJSONModifications(packageJson,
				Arrays.asList(
						PackageJsonModificationProvider.insertRequiredRuntimeLibraries(Arrays.asList("req-lib"))));

		Assert.assertEquals("Formatted document matches expectations after inserting another project dependency.",
				WizardGeneratorHelperTestExpectations.ONE_REQUIRED_RUNTIME_LIBRARIES,
				getPackageJsonContents(testProject));

		// add another required runtime library
		generatorHelper.applyJSONModifications(packageJson,
				Arrays.asList(
						PackageJsonModificationProvider.insertRequiredRuntimeLibraries(Arrays.asList("req-lib-2"))));

		Assert.assertEquals("Formatted document matches expectations after inserting another project dependency.",
				WizardGeneratorHelperTestExpectations.TWO_REQUIRED_RUNTIME_LIBRARIES,
				getPackageJsonContents(testProject));
	}

	/**
	 * Reads the contents of the {@code package.json} project description file of the given {@code project}.
	 */
	private static String getPackageJsonContents(final IProject project) throws CoreException {
		final IFile packageJsonFile = project.getFile(IN4JSProject.PACKAGE_JSON);
		try (Scanner s = new Scanner(packageJsonFile.getContents())) {
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
	}

}
