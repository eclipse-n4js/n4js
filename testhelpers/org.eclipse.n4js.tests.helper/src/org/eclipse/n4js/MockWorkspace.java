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
package org.eclipse.n4js;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectDescriptionFactory;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 */
public class MockWorkspace extends InternalN4JSWorkspace {

	/** Default {@code projectName} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__PROJECT_NAME = "test";
	/** Default {@code vendorId} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__VENDOR_ID = "tester.id";

	final ProjectDescription projectDescription;

	/***/
	public MockWorkspace() {
		projectDescription = ProjectDescriptionFactory.eINSTANCE.createProjectDescription();
		projectDescription.setProjectName(TEST_PROJECT__PROJECT_NAME);
		projectDescription.setVendorName("tester");
		projectDescription.setVendorId(TEST_PROJECT__VENDOR_ID);
		projectDescription.setProjectType(ProjectType.APPLICATION);
		VersionNumber versionNumber = SemverUtils.createVersionNumber(1, 0, 0);
		projectDescription.setProjectVersion(versionNumber);
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		return projectDescription;
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference reference) {
		return MockProject.MOCK_URI;
	}

	@Override
	public UnmodifiableIterator<URI> getFolderIterator(URI folderLocation) {
		return Iterators.unmodifiableIterator(Collections.emptyIterator());
	}

	@Override
	public URI findArtifactInFolder(URI folderLocation, String folderRelativePath) {
		return null;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		return MockProject.MOCK_URI;
	}

	@Override
	public Collection<URI> getAllProjectLocations() {
		return emptyList();
	}

}
