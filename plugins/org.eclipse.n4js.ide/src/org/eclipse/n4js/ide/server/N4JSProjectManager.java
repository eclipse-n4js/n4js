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
package org.eclipse.n4js.ide.server;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.lsp.ex.IProjectConfigEx;
import org.eclipse.n4js.projectModel.lsp.ex.ISourceFolderEx;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder.Result;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Provider;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectManager extends ProjectManager {

	Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;
	private IProjectConfigEx projectConfig;

	@Override
	public void initialize(ProjectDescription description, IProjectConfig pProjectConfig,
			Procedure2<? super URI, ? super Iterable<Issue>> acceptor,
			IExternalContentProvider openedDocumentsContentProvider,
			Provider<Map<String, ResourceDescriptionsData>> indexProvider, CancelIndicator cancelIndicator) {

		super.initialize(description, pProjectConfig, acceptor, openedDocumentsContentProvider, indexProvider,
				cancelIndicator);

		issueAcceptor = acceptor;
		projectConfig = (IProjectConfigEx) pProjectConfig;
	}

	@Override
	public Result doInitialBuild(CancelIndicator cancelIndicator) {
		List<URI> uris = new LinkedList<>();
		for (ISourceFolder srcFolder : this.projectConfig.getSourceFolders()) {
			ISourceFolderEx srcFolderEx = (ISourceFolderEx) srcFolder;
			uris.addAll(srcFolderEx.getAllResources());
		}
		return doBuild(uris, Collections.emptyList(), Collections.emptyList(), cancelIndicator);
	}

	@Override
	public BuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {

		// changedFiles = changedFiles.stream()
		// .filter(uri -> !projectConfig.isInOutputFolder(uri))
		// .collect(Collectors.toList());
		//
		// deletedFiles = deletedFiles.stream()
		// .filter(uri -> !projectConfig.isInOutputFolder(uri))
		// .collect(Collectors.toList());

		return super.newBuildRequest(changedFiles, deletedFiles, externalDeltas, cancelIndicator);
	}

}
