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

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSSourceContainerType;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProjectType;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 */
public class MockWorkspace extends InternalN4JSWorkspace {

	/** Default {@code projectId} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__PROJECT_ID = "test";
	/** Default {@code vendorId} used for the {@link MockProject}s in {@link MockWorkspace}. */
	public static final String TEST_PROJECT__VENDOR_ID = "tester.id";

	final ProjectDescription projectDescription;

	/***/
	public MockWorkspace() {
		projectDescription = N4mfFactory.eINSTANCE.createProjectDescription();
		projectDescription.setVendorName("tester");
		projectDescription.setProjectId(TEST_PROJECT__PROJECT_ID);
		projectDescription.setDeclaredVendorId(TEST_PROJECT__VENDOR_ID);
		projectDescription.setProjectType(ProjectType.APPLICATION);
		DeclaredVersion declaredVersion = N4mfFactory.eINSTANCE.createDeclaredVersion();
		declaredVersion.setMajor(1);
		declaredVersion.setMicro(0);
		declaredVersion.setMinor(0);
		declaredVersion.setQualifier("");
		projectDescription.setProjectVersion(declaredVersion);
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		return projectDescription;
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference reference,
			N4JSSourceContainerType expectedSourceContainerType) {
		return MockProject.MOCK_URI;
	}

	@Override
	public UnmodifiableIterator<URI> getArchiveIterator(URI archiveLocation, String archiveRelativeLocation) {
		return Iterators.unmodifiableIterator(Collections.emptyIterator());
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

}
