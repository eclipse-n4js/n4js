/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.build;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class DefaultBuildRequestFactory implements IBuildRequestFactory {

	@Override
	public XBuildRequest createEmptyBuildRequest(WorkspaceConfigSnapshot workspaceConfig,
			ProjectConfigSnapshot projectConfig) {

		return createBuildRequest(workspaceConfig, projectConfig,
				Collections.emptySet(), Collections.emptySet(), Collections.emptyList(),
				null, null, null, true, false);
	}

	/** Create the build request. */
	@Override
	public XBuildRequest createBuildRequest(
			WorkspaceConfigSnapshot workspaceConfig,
			ProjectConfigSnapshot projectConfig,
			Collection<URI> dirtyFiles,
			Collection<URI> deletedFiles,
			Collection<IResourceDescription.Delta> externalDeltas,
			ResourceDescriptionsData index,
			WorkspaceAwareResourceSet resourceSet,
			XSource2GeneratedMapping fileMappings,
			boolean doValidate,
			boolean writeStorageResources) {

		String projectID = projectConfig.getName();
		if (workspaceConfig.isInDependencyCycle(projectID)) {
			// remove all resources (except for the project description) from the build
			// since cycles are not supported
			dirtyFiles = new HashSet<>(dirtyFiles);
			dirtyFiles.retainAll(projectConfig.getProjectDescriptionUris());
			deletedFiles = new HashSet<>(deletedFiles);
			deletedFiles.retainAll(projectConfig.getProjectDescriptionUris());
		}

		XBuildRequest request = new XBuildRequest(projectID, projectConfig.getPath(),
				dirtyFiles, deletedFiles, externalDeltas,
				index, resourceSet, fileMappings,
				projectConfig.isGeneratorEnabled(), doValidate, projectConfig.indexOnly(), writeStorageResources);

		onPostCreate(request);
		return request;
	}

	@Override
	public void onPostCreate(XBuildRequest request) {
		// overwrite me
	}

}
