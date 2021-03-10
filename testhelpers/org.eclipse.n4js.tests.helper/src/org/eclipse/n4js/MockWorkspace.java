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
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 */
public class MockWorkspace extends InternalN4JSWorkspace<MockURIWrapper> {

	/** Default {@code projectName} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__PROJECT_NAME = "test";
	/** Default {@code vendorId} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__VENDOR_ID = "tester.id";

	final ProjectDescription projectDescription;

	/***/
	public MockWorkspace() {
		VersionNumber versionNumber = SemverUtils.createVersionNumber(1, 0, 0);
		projectDescription = ProjectDescription.builder()
				.setProjectName(TEST_PROJECT__PROJECT_NAME)
				.setVendorName("tester")
				.setVendorId(TEST_PROJECT__VENDOR_ID)
				.setProjectType(ProjectType.APPLICATION)
				.setProjectVersion(versionNumber)
				.build();
	}

	@Override
	public MockURIWrapper fromURI(URI uri) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ProjectDescription getProjectDescription(MockURIWrapper location) {
		return projectDescription;
	}

	@Override
	public MockURIWrapper getLocation(ProjectReference reference) {
		return MockProject.MOCK_URI;
	}

	@Override
	public UnmodifiableIterator<MockURIWrapper> getFolderIterator(MockURIWrapper folderLocation) {
		return Iterators.unmodifiableIterator(Collections.emptyIterator());
	}

	@Override
	public MockURIWrapper findArtifactInFolder(MockURIWrapper folderLocation, String folderRelativePath) {
		return null;
	}

	@Override
	public MockURIWrapper getProjectLocation(N4JSProjectName name) {
		return MockProject.MOCK_URI;
	}

	@Override
	public MockURIWrapper findProjectWith(MockURIWrapper nestedLocation) {
		return MockProject.MOCK_URI;
	}

	@Override
	public Collection<MockURIWrapper> getAllProjectLocations() {
		return emptyList();
	}

	@Override
	public void invalidateProject(MockURIWrapper location) {
		throw new IllegalStateException("Not supported for MockWorkspace");
	}
}
