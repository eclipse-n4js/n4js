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
package org.eclipse.n4js.tests.projectModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
public abstract class AbstractProjectModelTest<Loc extends SafeURI<Loc>> {

	@Inject
	private ValidationTestHelper validationTestHelper;
	@Inject
	private Provider<ResourceSet> resourceSetProvider;
	@Inject
	private LibraryManager libraryManager;

	/***/
	protected abstract AbstractProjectModelSetup<Loc> createSetup();

	/**
	 * Returns expected issues in the initial state of the test project with the given name. Checked by test method
	 * {@link #testSetup()}.
	 */
	protected abstract String[] getExpectedIssuesInInitialSetup(N4JSProjectName projectName);

	/***/
	public final N4JSProjectName myProjectName = new N4JSProjectName("myProject");
	/***/
	protected Loc myProjectURI;

	/***/
	public final N4JSProjectName libProjectName = new N4JSProjectName("libProject");
	/***/
	protected Loc libProjectURI;

	private AbstractProjectModelSetup<Loc> setup;

	/***/
	public void setMyProjectURI(Loc myProjectURI) {
		this.myProjectURI = myProjectURI;
	}

	/***/
	public void setLibProjectURI(Loc libProjectURI) {
		this.libProjectURI = libProjectURI;
	}

	/** Setup test data using {@link #setup}. */
	@Before
	public void setUp() {
		setup = createSetup();
		createTempProjects();
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
		assertNotNull(myProjectURI);
		assertNotNull(libProjectURI);
	}

	/** Validates the project description of all temporarily created test projects. */
	private void validateTempProjects() throws IOException {
		validateProjectDescription(myProjectURI, getExpectedIssuesInInitialSetup(myProjectName));
		validateProjectDescription(libProjectURI, getExpectedIssuesInInitialSetup(libProjectName));
	}

	/**
	 * Validates the project description file of the project to be found at {@code projectLocation}.
	 *
	 * @throws IOException
	 *             If loading the project description resource fails
	 */
	private void validateProjectDescription(SafeURI<?> projectLocation, String... expectedIssues)
			throws IOException {
		final ResourceSet resourceSet = resourceSetProvider.get();
		final SafeURI<?> projectDescriptionURI = projectLocation
				.appendSegment(AbstractProjectModelSetup.PROJECT_DESCRIPTION_FILENAME);
		// obtain resource for file project description file
		final Resource projectDescriptionResource = resourceSet
				.createResource(projectDescriptionURI.toURI());
		projectDescriptionResource.load(Collections.emptyMap());
		List<Issue> issues = validationTestHelper.validate(projectDescriptionResource);
		String allIssuesStr = issues.stream()
				.map(issue -> "line " + issue.getLineNumber() + ": " + issue.getMessage())
				.collect(Collectors.joining("\n"));
		String expectedIssuesStr = Joiner.on("\n").join(expectedIssues);
		assertEquals(
				"Unexpected issues in project description file " + projectDescriptionURI.toString() + ".",
				expectedIssuesStr, allIssuesStr);
	}

	private void deleteTempProjects() {
		setup.deleteTempProjects();
		libraryManager.registerAllExternalProjects(new NullProgressMonitor());
	}

	private void createTempProjects() {
		setup.createTempProjects();
	}

	/***/
	@After
	public void tearDown() {
		deleteTempProjects();
		setup = null;
		myProjectURI = null;
		libProjectURI = null;
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSetup() throws IOException {
		assertEquals(myProjectName.getRawName(), myProjectURI.getName());
		assertEquals(libProjectName.getRawName(), libProjectURI.getName());

		// make sure temporary projects have valid project descriptions
		validateTempProjects();
	}

}
