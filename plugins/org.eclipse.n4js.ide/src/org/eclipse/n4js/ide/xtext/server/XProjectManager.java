/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
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
	/**
	 * The builder.
	 */
	@Inject
	protected XIncrementalBuilder incrementalBuilder;

	/**
	 * Creates a new resource set.
	 */
	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;

	/**
	 * All languages
	 */
	@Inject
	protected IResourceServiceProvider.Registry languagesRegistry;

	/**
	 * Scans the file system.
	 */
	@Inject
	protected IFileSystemScanner fileSystemScanner;

	/**
	 * External content support for the resource set.
	 */
	@Inject
	protected IExternalContentSupport externalContentSupport;

	private XIndexState indexState = new XIndexState();

	private URI baseDir;

	private Procedure2<? super URI, ? super Iterable<Issue>> issueAcceptor;

	private Provider<Map<String, ResourceDescriptionsData>> indexProvider;

	private IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider;

	private XtextResourceSet resourceSet;

	private ProjectDescription projectDescription;

	private IProjectConfig projectConfig;

	/**
	 * Initialize this project.
	 */
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

	/**
	 * Initial build of this project.
	 */
	public XIncrementalBuilder.XResult doInitialBuild(CancelIndicator cancelIndicator) {
		List<URI> allUris = CollectionLiterals.<URI> newArrayList();
		for (ISourceFolder srcFolder : projectConfig.getSourceFolders()) {
			allUris.addAll(srcFolder.getAllResources(fileSystemScanner));
		}
		return doBuild(allUris, CollectionLiterals.emptyList(), CollectionLiterals.emptyList(), cancelIndicator);
	}

	/**
	 * Build this project.
	 */
	public XIncrementalBuilder.XResult doBuild(List<URI> dirtyFiles, List<URI> deletedFiles,
			List<IResourceDescription.Delta> externalDeltas, CancelIndicator cancelIndicator) {
		XBuildRequest request = newBuildRequest(dirtyFiles, deletedFiles, externalDeltas, cancelIndicator);
		XIncrementalBuilder.XResult result = incrementalBuilder.build(request,
				languagesRegistry::getResourceServiceProvider);
		indexState = result.getIndexState();
		resourceSet = request.getResourceSet();
		indexProvider.get().put(projectDescription.getName(), indexState.getResourceDescriptions());
		return result;
	}

	/**
	 * Creates a new build request for this project.
	 */
	protected XBuildRequest newBuildRequest(List<URI> changedFiles, List<URI> deletedFiles,
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
			N4JSProjectConfig n4pc = (N4JSProjectConfig) projectConfig;
			result.setIndexOnly(n4pc.indexOnly());
		}

		this.getBaseDir();
		return result;
	}

	/**
	 * Create and configure a new resource set for this project.
	 */
	public XtextResourceSet createNewResourceSet(ResourceDescriptionsData newIndex) {
		XtextResourceSet result = resourceSetProvider.get();
		projectDescription.attachToEmfObject(result);
		ProjectConfigAdapter.install(result, projectConfig);
		ChunkedResourceDescriptions index = new ChunkedResourceDescriptions(indexProvider.get(), result);
		index.setContainer(projectDescription.getName(), newIndex);
		externalContentSupport.configureResourceSet(result, openedDocumentsContentProvider);
		return result;
	}

	/**
	 * Create an empty resource set.
	 */
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

	/**
	 * Get the resource with the given URI.
	 */
	public Resource getResource(URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		resource.getContents();
		return resource;
	}

	/**
	 * Report an issue.
	 */
	public void reportProjectIssue(String message, String code, Severity severity) {
		Issue.IssueImpl result = new Issue.IssueImpl();
		result.setMessage(message);
		result.setCode(code);
		result.setSeverity(severity);
		result.setUriToProblem(baseDir);
		issueAcceptor.apply(baseDir, ImmutableList.of(result));
	}

	/**
	 * Getter
	 */
	public XIndexState getIndexState() {
		return indexState;
	}

	/**
	 * Setter
	 */
	protected void setIndexState(XIndexState indexState) {
		this.indexState = indexState;
	}

	/**
	 * Getter
	 */
	public URI getBaseDir() {
		return baseDir;
	}

	/**
	 * Getter
	 */
	protected Procedure2<? super URI, ? super Iterable<Issue>> getIssueAcceptor() {
		return issueAcceptor;
	}

	/**
	 * Getter
	 */
	protected Provider<Map<String, ResourceDescriptionsData>> getIndexProvider() {
		return indexProvider;
	}

	/**
	 * Getter
	 */
	protected IExternalContentSupport.IExternalContentProvider getOpenedDocumentsContentProvider() {
		return openedDocumentsContentProvider;
	}

	/**
	 * Getter
	 */
	public XtextResourceSet getResourceSet() {
		return resourceSet;
	}

	/**
	 * Getter
	 */
	public ProjectDescription getProjectDescription() {
		return projectDescription;
	}

	/**
	 * Getter
	 */
	public IProjectConfig getProjectConfig() {
		return projectConfig;
	}
}
