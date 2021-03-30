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
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterBuildListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterGenerateListener;
import org.eclipse.n4js.xtext.ide.server.build.XBuildRequest.AfterValidateListener;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
// TODO this should not be a stateful singleton
@Singleton
public class DefaultBuildRequestFactory implements IBuildRequestFactory {
	@Inject(optional = true)
	private AfterValidateListener afterValidateListener;
	@Inject(optional = true)
	private AfterGenerateListener afterGenerateListener;
	@Inject(optional = true)
	private AfterDeleteListener afterDeleteListener;
	@Inject(optional = true)
	private AfterBuildListener afterBuildListener;

	/** Create the build request. */
	protected XBuildRequest getBuildRequest(String projectName) {
		XBuildRequest result = new XBuildRequest(projectName);
		if (afterDeleteListener != null) {
			result.addAfterDeleteListener(afterDeleteListener);
		}
		if (afterValidateListener != null) {
			result.addAfterValidateListener(afterValidateListener);
		}
		if (afterGenerateListener != null) {
			result.addAfterGenerateListener(afterGenerateListener);
		}
		if (afterBuildListener != null) {
			result.addAfterBuildListener(afterBuildListener);
		}
		return result;
	}

	@Override
	public XBuildRequest getBuildRequest(WorkspaceConfigSnapshot workspaceConfig, ProjectConfigSnapshot projectConfig,
			Set<URI> changedFiles, Set<URI> deletedFiles, List<Delta> externalDeltas) {

		String projectName = projectConfig.getName();
		XBuildRequest result = getBuildRequest(projectName);

		result.setIndexOnly(projectConfig.indexOnly());
		result.setGeneratorEnabled(projectConfig.isGeneratorEnabled());

		if (workspaceConfig.isInDependencyCycle(projectName)) {
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

	/** @return {@link AfterValidateListener} */
	public AfterValidateListener getAfterValidateListener() {
		return afterValidateListener;
	}

	/** Set {@link #afterValidateListener} */
	public void setAfterValidateListener(AfterValidateListener afterValidateListener) {
		this.afterValidateListener = afterValidateListener;
	}

	/** @return {@link AfterGenerateListener} */
	public AfterGenerateListener getAfterGenerateListener() {
		return afterGenerateListener;
	}

	/** Set {@link #afterGenerateListener} */
	public void setAfterGenerateListener(AfterGenerateListener afterGenerateListener) {
		this.afterGenerateListener = afterGenerateListener;
	}

	/** @return {@link AfterDeleteListener} */
	public AfterDeleteListener getAfterDeleteListener() {
		return afterDeleteListener;
	}

	/** Set {@link #afterDeleteListener} */
	public void setAfterDeleteListener(AfterDeleteListener afterDeleteListener) {
		this.afterDeleteListener = afterDeleteListener;
	}

	/** @return {@link AfterBuildListener} */
	public AfterBuildListener getAfterBuildListener() {
		return afterBuildListener;
	}

	/** Set {@link #afterBuildListener} */
	public void setAfterBuildListener(AfterBuildListener afterBuildListener) {
		this.afterBuildListener = afterBuildListener;
	}

}
