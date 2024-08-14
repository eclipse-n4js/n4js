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
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.inject.Singleton;

/**
 * Default implementation for {@link IBuildRequestFactory}
 */
@Singleton
public class DefaultBuildRequestFactory implements IBuildRequestFactory {
	private final List<OnPostCreateListener> onPostCreateListeners = new LinkedList<>();

	@Override
	public XBuildRequest createEmptyBuildRequest(WorkspaceConfigSnapshot workspaceConfig,
			ProjectConfigSnapshot projectConfig) {

		return createBuildRequest(workspaceConfig, projectConfig,
				Collections.emptySet(), Collections.emptySet(), Collections.emptyList(),
				null, null, null, true, false);
	}

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
		XBuildRequest request = new XBuildRequest(projectID, projectConfig.getPath(),
				dirtyFiles, deletedFiles, externalDeltas,
				index, resourceSet, fileMappings,
				projectConfig.isGeneratorEnabled(), doValidate, projectConfig.indexOnly(), writeStorageResources);

		for (OnPostCreateListener listener : onPostCreateListeners) {
			listener.onPostCreate(request);
		}

		return request;
	}

	@Override
	public void addOnPostCreateListener(OnPostCreateListener listener) {
		this.onPostCreateListeners.add(listener);
	}

	@Override
	public void removeOnPostCreateListener(OnPostCreateListener listener) {
		this.onPostCreateListeners.remove(listener);
	}
}
