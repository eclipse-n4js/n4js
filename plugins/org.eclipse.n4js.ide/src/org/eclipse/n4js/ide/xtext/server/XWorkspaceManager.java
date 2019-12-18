/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IExternalContentSupport;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("restriction")
@Singleton
public class XWorkspaceManager implements DocumentResourceProvider {
	private static final Logger LOG = Logger.getLogger(XWorkspaceManager.class);

	@Inject
	private Provider<XProjectManager> projectManagerProvider;

	@Inject
	private XIWorkspaceConfigFactory workspaceConfigFactory;

	@Inject
	private XIProjectDescriptionFactory projectDescriptionFactory;

	@Inject
	private IssueAcceptor issueAcceptor;

	@Inject
	private UriExtensions uriExtensions;

	private XBuildManager buildManager;

	private final Map<String, XProjectManager> projectName2ProjectManager = new HashMap<>();

	private URI baseDir;

	private IWorkspaceConfig workspaceConfig;

	private final List<ILanguageServerAccess.IBuildListener> buildListeners = new ArrayList<>();

	// GH-1552: concurrent map
	private final Map<String, ResourceDescriptionsData> fullIndex = new ConcurrentHashMap<>();

	private final Map<URI, Document> openDocuments = new HashMap<>();

	/**
	 * Add the listener to this workspace.
	 *
	 * @param listener
	 *            the new listener.
	 */
	public void addBuildListener(ILanguageServerAccess.IBuildListener listener) {
		buildListeners.add(listener);
	}

	/**
	 * Removes a build listener if it was previously registered
	 *
	 * @since 2.18
	 */
	public void removeBuildListener(ILanguageServerAccess.IBuildListener listener) {
		buildListeners.remove(listener);
	}

	private final IExternalContentSupport.IExternalContentProvider openedDocumentsContentProvider = new IExternalContentSupport.IExternalContentProvider() {
		@Override
		public IExternalContentSupport.IExternalContentProvider getActualContentProvider() {
			return this;
		}

		@Override
		public String getContent(URI uri) {
			Document document = openDocuments.get(uri);
			if (document != null) {
				return document.getContents();
			}
			return null;
		}

		@Override
		public boolean hasContent(URI uri) {
			return isDocumentOpen(uri);
		}
	};

	/**
	 * Inject the build manager and establish circular dependency.
	 *
	 * @param buildManager
	 *            the build manager.
	 */
	@Inject
	public void setBuildManager(XBuildManager buildManager) {
		buildManager.setWorkspaceManager(this);
		this.buildManager = buildManager;
	}

	/** @return the {@link XBuildManager} */
	public XBuildManager getBuildManager() {
		return buildManager;
	}

	/** @return true iff the given uri is contained in the set of opened documents */
	public boolean isOpenedFile(URI uri) {
		return openDocuments.containsKey(uri);
	}

	/**
	 * Initialize a workspace at the given location.
	 *
	 * @param baseDir
	 *            the location
	 */
	@SuppressWarnings("hiding")
	public void initialize(URI baseDir) {
		this.baseDir = baseDir;
		setWorkspaceConfig(workspaceConfigFactory.getWorkspaceConfig(baseDir));
	}

	/**
	 * Refresh the workspace.
	 */
	public void refreshWorkspaceConfig() {
		setWorkspaceConfig(workspaceConfigFactory.getWorkspaceConfig(baseDir));
		Set<String> projectNames = projectName2ProjectManager.keySet();
		Set<String> remainingProjectNames = new HashSet<>(projectNames);
		for (IProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			if (projectName2ProjectManager.containsKey(projectConfig.getName())) {
				remainingProjectNames.remove(projectConfig.getName());
			} else {
				XProjectManager projectManager = projectManagerProvider.get();
				ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
				projectManager.initialize(projectDescription, projectConfig, openedDocumentsContentProvider,
						() -> fullIndex);
				projectName2ProjectManager.put(projectDescription.getName(), projectManager);
			}
		}
		for (String deletedProject : remainingProjectNames) {
			projectName2ProjectManager.remove(deletedProject);
			fullIndex.remove(deletedProject);
		}
	}

	/** Cleans all projects in the workspace */
	public void clean(CancelIndicator cancelIndicator) {
		buildManager.doClean(cancelIndicator);
	}

	/** @see XBuildManager#persistProjectState(CancelIndicator) */
	public void persistProjectState(CancelIndicator indicator) {
		buildManager.persistProjectState(indicator);
	}

	/** @return the current base directory {@link URI} */
	public URI getBaseDir() {
		return this.baseDir;
	}

	/**
	 * @return the workspace configuration
	 * @throws ResponseErrorException
	 *             if the workspace is not yet initialized
	 */
	public IWorkspaceConfig getWorkspaceConfig() throws ResponseErrorException {
		if (workspaceConfig == null) {
			ResponseError error = new ResponseError(ResponseErrorCode.serverNotInitialized,
					"Workspace has not been initialized yet.", null);
			throw new ResponseErrorException(error);
		}
		return workspaceConfig;
	}

	/**
	 * @param workspaceConfig
	 *            the new workspace configuration.
	 */
	protected void setWorkspaceConfig(IWorkspaceConfig workspaceConfig) {
		this.workspaceConfig = workspaceConfig;
	}

	/**
	 * Callback after a build was performed
	 */
	protected void afterBuild(List<IResourceDescription.Delta> deltas) {
		for (ILanguageServerAccess.IBuildListener listener : buildListeners) {
			listener.afterBuild(deltas);
		}
	}

	/**
	 * Announce dirty and deleted files and provide means to start a build.
	 *
	 * @param dirtyFiles
	 *            the dirty files
	 * @param deletedFiles
	 *            the deleted files
	 * @return a build command that can be triggered
	 */
	public XBuildable didChangeFiles(List<URI> dirtyFiles, List<URI> deletedFiles) {
		XBuildManager.XBuildable buildable = buildManager.doIncrementalBuild(dirtyFiles, deletedFiles);
		return (cancelIndicator) -> {
			List<IResourceDescription.Delta> deltas = buildable.build(cancelIndicator);
			afterBuild(deltas);
			return deltas;
		};
	}

	/** Clears all issues of the given URI */
	public void clearIssues(URI uri) {
		issueAcceptor.publishDiagnostics(uri, Collections.emptyList());
	}

	/**
	 * Perform a build on all projects
	 *
	 * @param cancelIndicator
	 *            cancellation support
	 */
	public void doInitialBuild(CancelIndicator cancelIndicator) {
		List<ProjectDescription> newProjects = new ArrayList<>();
		for (IProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
			newProjects.add(projectDescription);
		}
		List<Delta> deltas = buildManager.doInitialBuild(newProjects, cancelIndicator);
		afterBuild(deltas);
	}

	/**
	 * Announce dirty and deleted files and perform a build.
	 *
	 * @param dirtyFiles
	 *            the dirty files
	 * @param deletedFiles
	 *            the deleted files
	 * @param cancelIndicator
	 *            cancellation support
	 * @return the delta
	 */
	public List<IResourceDescription.Delta> doBuild(List<URI> dirtyFiles, List<URI> deletedFiles,
			CancelIndicator cancelIndicator) {
		return didChangeFiles(dirtyFiles, deletedFiles).build(cancelIndicator);
	}

	/**
	 * Returns the current index.
	 */
	public IResourceDescriptions getIndex() {
		return new ChunkedResourceDescriptions(fullIndex);
	}

	/**
	 * Returns the project that contains the given URI.
	 *
	 * @param uri
	 *            the contained uri
	 * @return the project base uri.
	 */
	public URI getProjectBaseDir(URI uri) {
		IProjectConfig projectConfig = getProjectConfig(uri);
		if (projectConfig != null) {
			return projectConfig.getPath();
		}
		return null;
	}

	/**
	 * @param uri
	 *            the contained uri
	 * @return the project manager.
	 */
	public XProjectManager getProjectManager(URI uri) {
		IProjectConfig projectConfig = getProjectConfig(uri);
		String name = null;
		if (projectConfig != null) {
			name = projectConfig.getName();
		}
		return getProjectManager(name);
	}

	/**
	 * Find the project that contains the uri.
	 */
	public IProjectConfig getProjectConfig(URI uri) {
		return getWorkspaceConfig().findProjectContaining(uri);
	}

	/**
	 * Return the project manager for the project with the given name.
	 *
	 * @param projectName
	 *            the project name
	 * @return the project manager
	 */
	public XProjectManager getProjectManager(String projectName) {
		return projectName2ProjectManager.get(projectName);
	}

	/**
	 * Return all project managers.
	 *
	 * @return all project managers.
	 */
	public Collection<XProjectManager> getProjectManagers() {
		return Collections.unmodifiableCollection(projectName2ProjectManager.values());
	}

	/**
	 * @deprecated the server should not apply {@link TextEdit}s but {@link TextDocumentContentChangeEvent}s. Use
	 *             {@link #didChangeTextDocumentContent(URI, Integer, Iterable)} instead.
	 */
	@Deprecated
	public List<IResourceDescription.Delta> didChange(URI uri, Integer version,
			Iterable<TextEdit> changes, CancelIndicator cancelIndicator) {
		return didChange(uri, version, changes).build(cancelIndicator);
	}

	/**
	 * @param version
	 *            unused
	 * @deprecated the server should not apply {@link TextEdit}s but {@link TextDocumentContentChangeEvent}s. Use
	 *             {@link #didChangeTextDocumentContent(URI, Integer, Iterable)} instead.
	 */
	@Deprecated
	public XBuildManager.XBuildable didChange(URI uri, Integer version, Iterable<TextEdit> changes) {
		Document contents = openDocuments.get(uri);
		if (contents == null) {
			XWorkspaceManager.LOG.error("The document " + uri + " has not been opened.");
			return XBuildable.NO_BUILD;
		}
		openDocuments.put(uri, contents.applyChanges(changes));
		return didChangeFiles(ImmutableList.of(uri), Collections.emptyList());
	}

	/**
	 * As opposed to {@link TextEdit}[] the positions in the edits of a {@link DidChangeTextDocumentParams} refer to the
	 * state after applying the preceding edits. See
	 * https://microsoft.github.io/language-server-protocol/specification#textedit-1 and
	 * https://github.com/microsoft/vscode/issues/23173#issuecomment-289378160 for details.
	 *
	 * In particular, this has to be taken into account when undoing the deletion of multiple characters at the end of a
	 * line.
	 *
	 * @since 2.18
	 * @param version
	 *            unused
	 */
	public XBuildManager.XBuildable didChangeTextDocumentContent(URI uri, Integer version,
			Iterable<TextDocumentContentChangeEvent> changes) {
		Document contents = openDocuments.get(uri);
		if (contents == null) {
			XWorkspaceManager.LOG.error("The document " + uri + " has not been opened.");
			return XBuildable.NO_BUILD;
		}
		openDocuments.put(uri, contents.applyTextDocumentChanges(changes));
		return didChangeFiles(ImmutableList.of(uri), Collections.emptyList());
	}

	/**
	 * Mark the given document as open and build it.
	 */
	public List<IResourceDescription.Delta> didOpen(URI uri, Integer version, String contents,
			CancelIndicator cancelIndicator) {
		return didOpen(uri, version, contents).build(cancelIndicator);
	}

	/**
	 * Mark the given document as open.
	 */
	public XBuildManager.XBuildable didOpen(URI uri, Integer version, String contents) {
		openDocuments.put(uri, new Document(version, contents));
		return didChangeFiles(ImmutableList.of(uri), Collections.emptyList());
	}

	/**
	 * @deprecated this method is no longer called
	 */
	@Deprecated
	public List<IResourceDescription.Delta> didClose(URI uri, CancelIndicator cancelIndicator) {
		return didClose(uri).build(cancelIndicator);
	}

	/**
	 * Mark the given document as cloded.
	 */
	public XBuildManager.XBuildable didClose(URI uri) {
		openDocuments.remove(uri);
		if (exists(uri)) {
			return didChangeFiles(ImmutableList.of(uri), Collections.emptyList());
		}
		return didChangeFiles(Collections.emptyList(), ImmutableList.of(uri));
	}

	/**
	 * Return true if the given resource still exists.
	 */
	protected boolean exists(URI uri) {
		XProjectManager projectManager = getProjectManager(uri);
		if (projectManager != null) {
			XtextResourceSet rs = projectManager.getResourceSet();
			if (rs != null) {
				return rs.getURIConverter().exists(uri, null);
			}
		}
		return false;
	}

	@Override
	public XtextResource getResource(URI uri) {
		URI resourceURI = uri.trimFragment();
		XProjectManager projectMnr = getProjectManager(resourceURI);
		if (projectMnr != null) {
			XtextResource resource = (XtextResource) projectMnr.getResource(resourceURI);
			return resource;
		}
		return null;
	}

	@Override
	public Document getDocument(URI uri) {
		XtextResource resource = getResource(uri);
		if (resource == null) {
			return null;
		}
		Document doc = openDocuments.get(resource.getURI());
		if (doc != null) {
			return doc;
		}
		String text = resource.getParseResult().getRootNode().getText();
		return new Document(Integer.valueOf(1), text);
	}

	@Override
	public Document getDocument(XtextResource resource) {
		if (resource == null) {
			return null;
		}
		Document doc = openDocuments.get(resource.getURI());
		if (doc != null) {
			return doc;
		}
		String text = resource.getParseResult().getRootNode().getText();
		return new Document(Integer.valueOf(1), text);
	}

	/**
	 * Return true if there is a open document with the given URI.
	 *
	 * @param uri
	 *            the URI
	 */
	public boolean isDocumentOpen(URI uri) {
		return openDocuments.containsKey(uri);
	}

	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		URI withEmptyAuthority = uriExtensions.withEmptyAuthority(uri);
		URI relativeUri = withEmptyAuthority.deresolve(getBaseDir());
		return relativeUri;
	}

}
