/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.helper.mock;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfig;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.xtext.util.UriExtensions;

/**
 * An {@link N4JSWorkspaceConfig} that does not access the file system but instead simply provides a single standard
 * test project.
 */
public class MockWorkspaceConfig extends N4JSWorkspaceConfig {

	/** Default {@code baseURI} used for the mocked workspace in a {@link MockWorkspaceConfig}. */
	public static final URI TEST_WORKSPACE__BASE_URI = URI.createFileURI("/test_workspace");
	/** Default {@code projectName} used for the mocked project in a {@link MockWorkspaceConfig}. */
	public static final String TEST_PROJECT__PROJECT_NAME = "test";
	/** Default {@code path} used for the mocked project in a {@link MockWorkspaceConfig}. */
	public static final FileURI TEST_PROJECT__PATH = new FileURI(TEST_WORKSPACE__BASE_URI)
			.appendSegment(TEST_PROJECT__PROJECT_NAME);
	/** Default {@code vendorId} used for the the mocked project in {@link MockWorkspaceConfig}. */
	public static final String TEST_PROJECT__VENDOR_ID = "tester.id";

	private final ProjectDescription projectDescription;

	/** Creates a new {@link MockWorkspaceConfig}. */
	public MockWorkspaceConfig(URI baseDirectory, ProjectDiscoveryHelper projectDiscoveryHelper,
			ProjectDescriptionLoader projectDescriptionLoader, ConfigSnapshotFactory configSnapshotFactory,
			UriExtensions uriExtensions) {

		super(baseDirectory, projectDiscoveryHelper, projectDescriptionLoader, configSnapshotFactory,
				uriExtensions);

		VersionNumber versionNumber = SemverUtils.createVersionNumber(1, 0, 0);
		this.projectDescription = ProjectDescription.builder()
				.setProjectName(TEST_PROJECT__PROJECT_NAME)
				.setVendorName("tester")
				.setVendorId(TEST_PROJECT__VENDOR_ID)
				.setProjectType(ProjectType.APPLICATION)
				.setProjectVersion(versionNumber)
				.build();
	}

	@Override
	protected N4JSProjectConfig createProjectConfig(FileURI path, ProjectDescription pd) {
		return new MockProjectConfig(this, path, pd, projectDescriptionLoader);
	}

	@Override
	protected void scanDiskForProjects() {
		// disable disk access, because this test workspace does not exist on disk
		deregisterAllProjects();
		registerProject(TEST_PROJECT__PATH, projectDescription);
	}
}
