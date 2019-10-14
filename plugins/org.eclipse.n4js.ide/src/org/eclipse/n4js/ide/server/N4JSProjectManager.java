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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.lsp.ex.IProjectConfigEx;
import org.eclipse.n4js.projectModel.lsp.ex.ISourceFolderEx;
import org.eclipse.xtext.build.BuildRequest;
import org.eclipse.xtext.build.IncrementalBuilder.Result;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.ide.server.ProjectManager;
import org.eclipse.xtext.resource.IExternalContentSupport.IExternalContentProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectManager extends ProjectManager {

	@Inject
	ProjectStatePersister projectStatePersister;

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

		projectStatePersister.readProjectState(projectConfig, this::setIndexState);
	}

	@Override
	public Result doInitialBuild(CancelIndicator cancelIndicator) {
		Set<URI> uris = new HashSet<>();
		for (ISourceFolder srcFolder : this.projectConfig.getSourceFolders()) {
			ISourceFolderEx srcFolderEx = (ISourceFolderEx) srcFolder;
			uris.addAll(srcFolderEx.getAllResources());
		}
		Iterator<URI> iter = uris.iterator();
		Source2GeneratedMapping fileMappings = getIndexState().getFileMappings();
		while (iter.hasNext()) {
			URI next = iter.next();
			if ("js".equals(next.fileExtension()) || "n4jsd".equals(next.fileExtension())
					|| "json".equals(next.fileExtension())) {
				if (getIndexState().getResourceDescriptions().getResourceDescription(next) != null) {
					iter.remove();
				}
			} else {
				// TODO really check if things are in sync
				if (!fileMappings.getGenerated(next).isEmpty()) {
					iter.remove();
				}
			}
		}
		System.out.println("Now building initially: " + uris);
		return doBuild(new ArrayList<>(uris), Collections.emptyList(), Collections.emptyList(), cancelIndicator);
	}

	@Override
	public Result doBuild(List<URI> dirtyFiles, List<URI> deletedFiles, List<Delta> externalDeltas,
			CancelIndicator cancelIndicator) {
		Result result = super.doBuild(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator);
		if (!cancelIndicator.isCanceled()) {
			if (dirtyFiles.size() != 1 || !ProjectStatePersister.FILENAME.equals(dirtyFiles.get(0).lastSegment())) {
				projectStatePersister.writeProjectState(projectConfig, result.getIndexState());
			}
		}
		return result;
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
