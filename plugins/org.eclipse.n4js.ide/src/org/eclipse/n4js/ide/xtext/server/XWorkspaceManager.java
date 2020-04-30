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
 * The workspace manager receives file events from the LSP client and triggers builds. On startup it will trigger an
 * initial build, and after that it will trigger incremental builds. Depending on the file events, those incremental
 * builds will also generate output files.
 * <p>
 * Please note:
 * <ul>
 * <li>Builds will keep the source and output files (and the .projectstate file) on the file system in sync.
 * <li>In case there are source files that have unsaved changes:
 * <ul>
 * <li>output files are not generated.
 * <li>issues found during validation refer to the file version that has unsaved changes.
 * </ul>
 * </ul>
 *
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
	private UriExtensions uriExtensions;

	@Inject
	private XBuildManager buildManager;

	private final Map<String, XProjectManager> projectName2ProjectManager = new HashMap<>();

	private URI baseDir;

	private IWorkspaceConfig workspaceConfig;

	private final List<ILanguageServerAccess.IBuildListener> buildListeners = new ArrayList<>();

	// GH-1552: concurrent map
	private final Map<String, ResourceDescriptionsData> fullIndex = new ConcurrentHashMap<>();

	private final Map<URI, XDocument> openDocuments = new ConcurrentHashMap<>();

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
			XDocument document = openDocuments.get(uri);
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
	 * Tells whether the workspace is in dirty state. The workspace is said to be in dirty state iff at least one file
	 * is open AND is dirty, i.e. has unsaved changes.
	 */
	public boolean isDirty() {
		for (XDocument doc : openDocuments.values()) {
			if (doc.isDirty()) {
				return true;
			}
		}
		return false;
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

	/** Refresh the workspace. */
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

	/**
	 * @param workspaceConfig
	 *            the new workspace configuration.
	 */
	protected void setWorkspaceConfig(IWorkspaceConfig workspaceConfig) {
		this.workspaceConfig = workspaceConfig;
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

	/** @return the current base directory {@link URI} */
	public URI getBaseDir() {
		return this.baseDir;
	}

	/** Callback after a build was performed */
	protected void afterBuild(List<IResourceDescription.Delta> deltas) {
		for (ILanguageServerAccess.IBuildListener listener : buildListeners) {
			listener.afterBuild(deltas);
		}
	}

	/** Mark the given document as open. */
	public XBuildManager.XBuildable didOpen(URI uri, Integer version, String contents) {
		openDocuments.put(uri, new XDocument(version, contents));

		// return getIncrementalDirtyBuildable(ImmutableList.of(uri), Collections.emptyList()); // necessary at all?
		return getIncrementalDirtyBuildable(Collections.emptyList(), Collections.emptyList());
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
		return tryIncrementalGenerateBuildable(dirtyFiles, deletedFiles);
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

	/** Triggers an incremental build, but will not generate output files */
	protected XBuildable getIncrementalDirtyBuildable(List<URI> dirtyFiles, List<URI> deletedFiles) {
		XBuildManager.XBuildable buildable = buildManager.getIncrementalDirtyBuildable(dirtyFiles, deletedFiles);
		return (cancelIndicator) -> {
			List<IResourceDescription.Delta> deltas = buildable.build(cancelIndicator);
			afterBuild(deltas);
			return deltas;
		};
	}

	/**
	 * Generation of output files is only triggered if no source files contain unsaved changes (so-called dirty files).
	 */
	protected XBuildable tryIncrementalGenerateBuildable(List<URI> dirtyFiles, List<URI> deletedFiles) {
		if (isDirty()) {
			return getIncrementalDirtyBuildable(dirtyFiles, deletedFiles);
		} else {
			return getIncrementalGenerateBuildable(dirtyFiles, deletedFiles);
		}
	}

	/** Triggers an incremental build, and will generate output files. */
	protected XBuildable getIncrementalGenerateBuildable(List<URI> dirtyFiles, List<URI> deletedFiles) {
		XBuildManager.XBuildable buildable = buildManager.getIncrementalGenerateBuildable(dirtyFiles, deletedFiles);
		return (cancelIndicator) -> {
			List<IResourceDescription.Delta> deltas = buildable.build(cancelIndicator);
			afterBuild(deltas);
			LOG.info("Output files generated.");
			return deltas;
		};
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

		XDocument contents = openDocuments.get(uri);
		if (contents == null) {
			LOG.error("The document " + uri + " has not been opened.");
			return XBuildable.NO_BUILD;
		}
		openDocuments.put(uri, contents.applyTextDocumentChanges(changes));
		return getIncrementalDirtyBuildable(ImmutableList.of(uri), Collections.emptyList());
	}

	/** Mark the given document as saved. */
	public XBuildManager.XBuildable didSave(URI uri) {
		XDocument document = openDocuments.computeIfPresent(uri, (any, doc) -> {
			if (doc.isDirty()) {
				return doc.save();
			}
			return doc;
		});
		if (document == null) {
			LOG.error("The document " + uri + " has not been opened.");
			return XBuildable.NO_BUILD;
		}
		return tryIncrementalGenerateBuildable(ImmutableList.of(uri), Collections.emptyList());
	}

	/** Mark the given document as closed. */
	public XBuildManager.XBuildable didClose(URI uri) {
		openDocuments.remove(uri);
		if (exists(uri)) {
			return tryIncrementalGenerateBuildable(ImmutableList.of(uri), Collections.emptyList());
		}
		return tryIncrementalGenerateBuildable(Collections.emptyList(), ImmutableList.of(uri));
	}

	/** Mark all documents as closed. */
	public XBuildManager.XBuildable closeAll() {
		ImmutableList<URI> closed = ImmutableList.copyOf(openDocuments.keySet());
		openDocuments.clear();
		return tryIncrementalGenerateBuildable(closed, Collections.emptyList());
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

	/** Find the project that contains the uri. */
	public IProjectConfig getProjectConfig(URI uri) {
		return getWorkspaceConfig().findProjectContaining(uri);
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
	 * Return the project manager for the project with the given name.
	 *
	 * @param projectName
	 *            the project name
	 * @return the project manager
	 */
	public XProjectManager getProjectManager(String projectName) {
		return projectName2ProjectManager.get(projectName);
	}

	/** Cleans all projects in the workspace */
	public void clean(CancelIndicator cancelIndicator) {
		buildManager.doClean(cancelIndicator);
	}

	/** @see XBuildManager#persistProjectState(CancelIndicator) */
	public void persistProjectState(CancelIndicator indicator) {
		buildManager.persistProjectState(indicator);
	}

	/** Returns the current index. */
	public IResourceDescriptions getIndex() {
		return new ChunkedResourceDescriptions(fullIndex);
	}

	/** Return true if the given resource still exists. */
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
	public XDocument getDocument(URI uri) {
		XDocument doc = openDocuments.get(uri);
		if (doc != null) {
			return doc;
		}
		XtextResource resource = getResource(uri);
		if (resource == null) {
			return null;
		}
		return getDocument(resource);
	}

	@Override
	public XDocument getDocument(XtextResource resource) {
		if (resource == null) {
			return null;
		}
		XDocument doc = openDocuments.get(resource.getURI());
		if (doc != null) {
			return doc;
		}
		String text = resource.getParseResult().getRootNode().getText();
		return new XDocument(Integer.valueOf(1), text);
	}

	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		URI withEmptyAuthority = uriExtensions.withEmptyAuthority(uri);
		URI relativeUri = withEmptyAuthority.deresolve(getBaseDir());
		return relativeUri;
	}

}
