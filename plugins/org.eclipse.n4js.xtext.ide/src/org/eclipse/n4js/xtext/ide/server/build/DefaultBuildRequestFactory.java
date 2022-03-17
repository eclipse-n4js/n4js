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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class DefaultBuildRequestFactory implements IBuildRequestFactory {

	/** Create the build request. */
	protected XBuildRequest getBuildRequest(String projectID) {
		XBuildRequest result = new XBuildRequest(projectID);
		return result;
	}

	@Override
	public XBuildRequest getBuildRequest(WorkspaceConfigSnapshot workspaceConfig, ProjectConfigSnapshot projectConfig,
			Set<URI> changedFiles, Set<URI> deletedFiles, List<Delta> externalDeltas) {

		String projectID = projectConfig.getName();
		XBuildRequest result = getBuildRequest(projectID);

		result.setIndexOnly(projectConfig.indexOnly());
		result.setGeneratorEnabled(projectConfig.isGeneratorEnabled());

		if (workspaceConfig.isInDependencyCycle(projectID)) {
			changedFiles = new HashSet<>(changedFiles);
			changedFiles.retainAll(projectConfig.getProjectDescriptionUris());
			deletedFiles = new HashSet<>(deletedFiles);
			deletedFiles.retainAll(projectConfig.getProjectDescriptionUris());
		}
		result.setDirtyFiles(changedFiles);
		result.setDeletedFiles(deletedFiles);
		result.setExternalDeltas(externalDeltas);

		return result;
	}

}
