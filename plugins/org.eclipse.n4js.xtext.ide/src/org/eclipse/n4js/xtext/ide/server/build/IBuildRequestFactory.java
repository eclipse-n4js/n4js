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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

/**
 * Factory to create instances of {@link XBuildRequest}s
 */
public interface IBuildRequestFactory {

	/** Shortcut to create an empty build request */
	XBuildRequest createEmptyBuildRequest(WorkspaceConfigSnapshot workspaceConfig, ProjectConfigSnapshot projectConfig);

	/**
	 * Create the build request for the given sets of files and deltas.
	 *
	 * @param workspaceConfig
	 *            workspace configuration snapshot containing the project
	 * @param projectConfig
	 *            project configuration snapshot containing the {@link URI}s and {@link Delta}s
	 * @param dirtyFiles
	 *            set of changed files
	 * @param deletedFiles
	 *            set of deleted files
	 * @param externalDeltas
	 *            set of external deltas
	 * @return the build request used during the build process.
	 */
	XBuildRequest createBuildRequest(
			WorkspaceConfigSnapshot workspaceConfig,
			ProjectConfigSnapshot projectConfig,
			Collection<URI> dirtyFiles,
			Collection<URI> deletedFiles,
			Collection<IResourceDescription.Delta> externalDeltas,
			ResourceDescriptionsData index,
			WorkspaceAwareResourceSet resourceSet,
			XSource2GeneratedMapping fileMappings,
			boolean doValidate,
			boolean writeStorageResources);

	/** Adds the {@link OnPostCreateListener} */
	void addOnPostCreateListener(OnPostCreateListener listener);

	/** Removes the {@link OnPostCreateListener} */
	void removeOnPostCreateListener(OnPostCreateListener listener);

	/** Provides callback on the created build request */
	interface OnPostCreateListener {
		void onPostCreate(XBuildRequest request);
	}
}
