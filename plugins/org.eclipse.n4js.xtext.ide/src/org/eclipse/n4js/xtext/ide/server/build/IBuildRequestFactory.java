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

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

/**
 * Factory to create instances of {@link XBuildRequest}s
 */
public interface IBuildRequestFactory {

	/**
	 * Create the build request for the given sets of files and deltas.
	 *
	 * @param workspaceConfig
	 *            workspace configuration snapshot containing the project
	 * @param projectConfig
	 *            project configuration snapshot containing the {@link URI}s and {@link Delta}s
	 * @param changedFiles
	 *            set of changed files
	 * @param deletedFiles
	 *            set of deleted files
	 * @param externalDeltas
	 *            set of external deltas
	 * @return the build request used during the build process.
	 */
	XBuildRequest getBuildRequest(
			WorkspaceConfigSnapshot workspaceConfig,
			ProjectConfigSnapshot projectConfig,
			Set<URI> changedFiles,
			Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas);

}
