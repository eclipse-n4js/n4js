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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
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
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 *
 */
@SuppressWarnings("restriction")
public class N4JSProjectManager extends ProjectManager {

	@Inject
	private ProjectStatePersister projectStatePersister;
	@Inject
	private N4JSWorkspaceManager workspaceManager;

	private Map<URI, HashedFileContent> hashFileContents = new HashMap<>();
	private Map<URI, HashedFileContent> newFileContents = new HashMap<>();
	private IProjectConfigEx projectConfig;

	@Override
	public void initialize(ProjectDescription description, IProjectConfig pProjectConfig,
			Procedure2<? super URI, ? super Iterable<Issue>> acceptor,
			IExternalContentProvider openedDocumentsContentProvider,
			Provider<Map<String, ResourceDescriptionsData>> indexProvider, CancelIndicator cancelIndicator) {

		// Stopwatch sw = Stopwatch.createStarted();

		// System.out.println("Initializing [" + pProjectConfig.getName() + "]");
		super.initialize(description, pProjectConfig, acceptor, openedDocumentsContentProvider, indexProvider,
				cancelIndicator);

		// System.out.println(" super.initialize " + sw);

		projectConfig = (IProjectConfigEx) pProjectConfig;

		projectStatePersister.readProjectState(projectConfig, (indexState, fingerprints) -> {
			setIndexState(indexState);
			fingerprints.forEach(fp -> hashFileContents.put(fp.getUri(), fp));
		});

		// System.out.println(" readProjectState " + sw);

		Set<URI> newOrChanged = new HashSet<>();
		Set<URI> allUris = new HashSet<>();
		Source2GeneratedMapping sourceFileMappings = getIndexState().getFileMappings();
		for (ISourceFolder srcFolder : this.projectConfig.getSourceFolders()) {
			ISourceFolderEx srcFolderEx = (ISourceFolderEx) srcFolder;
			List<URI> allResources = srcFolderEx.getAllResources();
			// System.out.println(" getAllResources from " + srcFolderEx.getName() + ": " + sw);
			for (URI sourceURI : allResources) {
				if (!ProjectStatePersister.FILENAME.equals(sourceURI.lastSegment()) && allUris.add(sourceURI)) {
					HashedFileContent fingerprint = hashFileContents.get(sourceURI);
					if (fingerprint != null) {
						HashedFileContent newHash = doHash(sourceURI);
						if (newHash == null || fingerprint.getHash() != newHash.getHash()) {
							newOrChanged.add(sourceURI);
						} else {
							List<URI> prevGenerated = sourceFileMappings.getGenerated(sourceURI);
							for (URI generated : prevGenerated) {
								HashedFileContent genFingerprint = hashFileContents.get(generated);
								if (genFingerprint != null) {
									HashedFileContent generatedHash = doHash(generated);
									if (generatedHash == null || generatedHash.getHash() != genFingerprint.getHash()) {
										newOrChanged.add(sourceURI);
										break;
									}
								}
							}
						}
					} else {
						newOrChanged.add(sourceURI);
					}
				}
			}
		}
		// System.out.println(" getAllResources: " + sw);
		List<URI> deleted = new ArrayList<>();
		for (URI inIndex : getIndexState().getResourceDescriptions().getAllURIs()) {
			if (!allUris.contains(inIndex)) {
				deleted.add(inIndex);
			}
		}
		// System.out.println(" processDeleted: " + sw);
		List<URI> newOrChangedList = new ArrayList<>(newOrChanged);
		// System.out.println("Initializing [" + getProjectConfig().getName() + "] " + newOrChangedList + "/" +
		// deleted);
		workspaceManager.getBuildManager().enqueue(description, newOrChangedList, deleted);
		// make sure we have a resource set assigned
		doBuild(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), cancelIndicator);

		// System.out.println(" took " + sw);
	}

	@Override
	public Result doInitialBuild(CancelIndicator cancelIndicator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Result doBuild(List<URI> dirtyFiles, List<URI> deletedFiles, List<Delta> externalDeltas,
			CancelIndicator cancelIndicator) {

		// Stopwatch sw = Stopwatch.createStarted();

		// System.out.println("Now building [" + getProjectConfig().getName() + "]: " + dirtyFiles + "/" +
		// deletedFiles);
		// }

		newFileContents = new HashMap<>(hashFileContents);
		newFileContents.keySet().removeAll(deletedFiles);

		/*
		 * We create build request that will alter newFileContents when a file is created / removed
		 */
		Result result = super.doBuild(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator);
		if (!cancelIndicator.isCanceled() && !result.getAffectedResources().isEmpty()) {
			dirtyFiles.forEach(this::storeHash);
			newFileContents.replaceAll((uri, hash) -> {
				if (hash == null) {
					hash = doHash(uri);
				}
				return hash;
			});
			projectStatePersister.writeProjectState(projectConfig, result.getIndexState(),
					newFileContents.values());
			hashFileContents = newFileContents;
			newFileContents = null;
		}

		// System.out.println(" took " + sw);

		return result;
	}

	@Override
	public BuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {
		BuildRequest result = super.newBuildRequest(changedFiles, deletedFiles, externalDeltas, cancelIndicator);
		Procedure1<? super URI> afterDeleteFile = result.getAfterDeleteFile();
		Procedure2<? super URI, ? super URI> afterGenerateFile = result.getAfterGenerateFile();
		result.setAfterDeleteFile(removed -> {
			afterDeleteFile.apply(removed);
			newFileContents.remove(removed);
		});
		result.setAfterGenerateFile((source, target) -> {
			afterGenerateFile.apply(source, target);
			// System.out.println("Generating: " + target + " from " + source);
			scheduleHash(target);
		});
		if (projectConfig.getPath().segmentsList().contains(N4JSGlobals.NODE_MODULES)) {
			result.setAfterValidate((uri, issues) -> false);
		}
		return result;
	}

	private void scheduleHash(URI uri) {
		newFileContents.put(uri, null);
	}

	private void storeHash(URI uri) {
		HashedFileContent generatedTargetContent = doHash(uri);
		if (generatedTargetContent != null)
			newFileContents.put(uri, generatedTargetContent);
	}

	private HashedFileContent doHash(URI uri) {
		try {
			HashedFileContent generatedTargetContent = new HashedFileContent(uri,
					new File(new java.net.URI(uri.toString())));
			return generatedTargetContent;
		} catch (IOException | URISyntaxException e) {
			return null;
		}
	}

}
