/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.server.HashedFileContent;
import org.eclipse.n4js.ide.server.ProjectStatePersister;
import org.eclipse.n4js.ide.server.ProjectStatePersister.PersistedState;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder.XResult;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.ProjectConfigAdapter;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
public class XProjectManager {
	/** The builder. */
	@Inject
	protected XIncrementalBuilder incrementalBuilder;

	/** Creates a new resource set. */
	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;

	/** All languages */
	@Inject
	protected IResourceServiceProvider.Registry languagesRegistry;

	/** Scans the file system. */
	@Inject
	protected IFileSystemScanner fileSystemScanner;

	/** External content support for the resource set. */
	@Inject
	protected IExternalContentSupport externalContentSupport;

	/** Reads and writes the type index from/to disk */
	@Inject
	protected ProjectStatePersister projectStatePersister;

	private XIndexState indexState = new XIndexState();

	private URI baseDir;

	private Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;

	private Provider<Map<String, ResourceDescriptionsData>> indexProvider;

	private IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider;

	private XtextResourceSet resourceSet;

	private ProjectDescription projectDescription;

	private IProjectConfig projectConfig;

	private final Map<URI, HashedFileContent> hashFileContents = new HashMap<>();

	/** Initialize this project. */
	@SuppressWarnings("hiding")
	public void initialize(ProjectDescription description, IProjectConfig projectConfig,
			Procedure2<? super URI, ? super Iterable<Issue>> acceptor,
			IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider,
			Provider<Map<String, ResourceDescriptionsData>> indexProvider,
			@SuppressWarnings("unused") CancelIndicator cancelIndicator) {

		this.projectDescription = description;
		this.projectConfig = projectConfig;
		this.baseDir = projectConfig.getPath();
		this.issueAcceptor = acceptor;
		this.openedDocumentsContentProvider = openedDocumentsContentProvider;
		this.indexProvider = indexProvider;
	}

	/** Reads the persisted project state from disk */
	private void readProjectState(CancelIndicator cancelIndicator) {
		doClear();

		PersistedState persistedState = projectStatePersister.readProjectState(projectConfig);
		if (persistedState != null) {
			for (HashedFileContent hfc : persistedState.fileHashs.values()) {
				URI uri = hfc.getUri();
				if (isSourceUnchanged(hfc, persistedState)) {
					hashFileContents.put(uri, hfc);
				} else {
					persistedState.indexState.getFileMappings().deleteSource(uri);
				}
			}
			setIndexState(persistedState.indexState);
		}

		doIncrementalBuild(Collections.emptySet(), Collections.emptySet(), Collections.emptyList(), cancelIndicator);
	}

	private boolean isSourceUnchanged(HashedFileContent hfc, PersistedState persistedState) {
		URI sourceUri = hfc.getUri();
		long loadedHash = hfc.getHash();

		HashedFileContent newHash = doHash(sourceUri);
		if (newHash == null || loadedHash != newHash.getHash()) {
			return false;
		}

		XSource2GeneratedMapping sourceFileMappings = persistedState.indexState.getFileMappings();
		List<URI> prevGenerated = sourceFileMappings.getGenerated(sourceUri);
		for (URI generated : prevGenerated) {
			HashedFileContent genFingerprint = persistedState.fileHashs.get(generated);
			if (genFingerprint != null) {
				HashedFileContent generatedHash = doHash(generated);
				if (generatedHash == null || generatedHash.getHash() != genFingerprint.getHash()) {
					return false;
				}
			}
		}

		return true;
	}

	private HashedFileContent doHash(URI uri) {
		try {
			File srcFile = new File(new java.net.URI(uri.toString()));
			HashedFileContent generatedTargetContent = new HashedFileContent(uri, srcFile);
			return generatedTargetContent;
		} catch (IOException | URISyntaxException e) {
			return null;
		}
	}

	/** Persists the project state to disk */
	public void writeProjectState() {
		projectStatePersister.writeProjectState(projectConfig, getIndexState(), hashFileContents.values());
	}

	/** Initial build reads the project state and resolves changes. */
	public XIncrementalBuilder.XResult doInitialBuild(CancelIndicator cancelIndicator) {
		readProjectState(cancelIndicator);

		Set<URI> changedSources = new HashSet<>();
		Set<URI> allIndexedUris = getIndexState().getResourceDescriptions().getAllURIs();

		for (ISourceFolder srcFolder : projectConfig.getSourceFolders()) {
			List<URI> allSourceFolderUris = srcFolder.getAllResources(fileSystemScanner);
			for (URI srcFolderUri : allSourceFolderUris) {
				if (!allIndexedUris.contains(srcFolderUri)) {
					changedSources.add(srcFolderUri);
				}
			}
		}

		XResult result = doIncrementalBuild(changedSources, CollectionLiterals.emptySet(),
				CollectionLiterals.emptyList(), cancelIndicator);

		writeProjectState();
		return result;
	}

	/** Clears type index of this project. */
	public void doClear() {
		hashFileContents.clear();
		setIndexState(new XIndexState());
	}

	/** Build this project. */
	public XIncrementalBuilder.XResult doIncrementalBuild(Set<URI> dirtyFiles, Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {

		URI persistanceFile = this.projectStatePersister.getFileName(projectConfig);
		dirtyFiles.remove(persistanceFile);
		deletedFiles.remove(persistanceFile);

		XBuildRequest request = newBuildRequest(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator);
		XIncrementalBuilder.XResult result = incrementalBuilder.build(request,
				languagesRegistry::getResourceServiceProvider);

		indexState = result.getIndexState();
		resourceSet = request.getResourceSet();
		indexProvider.get().put(projectDescription.getName(), indexState.getResourceDescriptions());
		return result;
	}

	/** Creates a new build request for this project. */
	protected XBuildRequest newBuildRequest(Set<URI> changedFiles, Set<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {

		XBuildRequest result = new XBuildRequest();
		result.setBaseDir(baseDir);
		result.setState(new XIndexState(indexState.getResourceDescriptions().copy(),
				indexState.getFileMappings().copy()));
		result.setResourceSet(createFreshResourceSet(result.getState().getResourceDescriptions()));
		result.setDirtyFiles(changedFiles);
		result.setDeletedFiles(deletedFiles);
		result.setExternalDeltas(externalDeltas);
		result.setAfterValidate((URI uri, Iterable<Issue> issues) -> {
			issueAcceptor.apply(uri, issues);
			return true;
		});
		result.setCancelIndicator(cancelIndicator);

		if (projectConfig instanceof N4JSProjectConfig) {
			// TODO: merge N4JSProjectConfig#indexOnly() to IProjectConfig
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			result.setIndexOnly(n4pc.indexOnly());
		}

		this.getBaseDir();
		return result;
	}

	/** Create and configure a new resource set for this project. */
	public XtextResourceSet createNewResourceSet(ResourceDescriptionsData newIndex) {
		XtextResourceSet result = resourceSetProvider.get();
		projectDescription.attachToEmfObject(result);
		ProjectConfigAdapter.install(result, projectConfig);
		ChunkedResourceDescriptions index = new ChunkedResourceDescriptions(indexProvider.get(), result);
		index.setContainer(projectDescription.getName(), newIndex);
		externalContentSupport.configureResourceSet(result, openedDocumentsContentProvider);
		return result;
	}

	/** Create an empty resource set. */
	protected XtextResourceSet createFreshResourceSet(ResourceDescriptionsData newIndex) {
		if (resourceSet == null) {
			resourceSet = createNewResourceSet(newIndex);
		} else {
			ChunkedResourceDescriptions resDescs = ChunkedResourceDescriptions.findInEmfObject(resourceSet);
			for (Map.Entry<String, ResourceDescriptionsData> entry : indexProvider.get().entrySet()) {
				resDescs.setContainer(entry.getKey(), entry.getValue());
			}
			resDescs.setContainer(projectDescription.getName(), newIndex);
		}
		return resourceSet;
	}

	/** Get the resource with the given URI. */
	public Resource getResource(URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		resource.getContents();
		return resource;
	}

	/** Report an issue. */
	public void reportProjectIssue(String message, String code, Severity severity) {
		Issue.IssueImpl result = new Issue.IssueImpl();
		result.setMessage(message);
		result.setCode(code);
		result.setSeverity(severity);
		result.setUriToProblem(baseDir);
		issueAcceptor.apply(baseDir, ImmutableList.of(result));
	}

	/** Setter */
	protected void setIndexState(XIndexState indexState) {
		this.indexState = indexState;
	}

	/** Getter */
	public XIndexState getIndexState() {
		return indexState;
	}

	/** Getter */
	public URI getBaseDir() {
		return baseDir;
	}

	/** Getter */
	protected Procedure2<? super URI, ? super Iterable<Issue>> getIssueAcceptor() {
		return issueAcceptor;
	}

	/** Getter */
	protected Provider<Map<String, ResourceDescriptionsData>> getIndexProvider() {
		return indexProvider;
	}

	/** Getter */
	protected IExternalContentSupport.IExternalContentProvider getOpenedDocumentsContentProvider() {
		return openedDocumentsContentProvider;
	}

	/** Getter */
	public XtextResourceSet getResourceSet() {
		return resourceSet;
	}

	/** Getter */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/** Getter */
	public IProjectConfig getProjectConfig() {
		return projectConfig;
	}
}
