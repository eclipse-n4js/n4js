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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentChunkedIndex;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
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
public class XWorkspaceManager {
	private static final Logger LOG = Logger.getLogger(XWorkspaceManager.class);

	@Inject
	private XIWorkspaceConfigFactory workspaceConfigFactory;

	@Inject
	private Provider<XProjectManager> projectManagerProvider;

	@Inject
	private XIProjectDescriptionFactory projectDescriptionFactory;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private XBuildManager buildManager;

	private final Map<String, XProjectManager> projectName2ProjectManager = new HashMap<>();

	private IWorkspaceConfig workspaceConfig;

	private final List<ILanguageServerAccess.IBuildListener> buildListeners = new ArrayList<>();

	private final ConcurrentChunkedIndex fullIndex = new ConcurrentChunkedIndex();
	private ConcurrentIssueRegistry issueRegistry;

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

	/** Reinitialize a workspace at the current location. */
	public void reinitialize() {
		initialize(getBaseDir(), getIssueRegistry());
	}

	/**
	 * Initialize a workspace at the given location.
	 *
	 * @param newBaseDir
	 *            the location
	 */
	@SuppressWarnings("hiding")
	public void initialize(URI newBaseDir, ConcurrentIssueRegistry issueRegistry) {
		if (this.issueRegistry != null && issueRegistry != this.issueRegistry) {
			throw new IllegalArgumentException("the issue registry must not be changed");
		}
		this.issueRegistry = issueRegistry;
		refreshWorkspaceConfig(newBaseDir);
	}

	/** Refresh the workspace. */
	public void refreshWorkspaceConfig(URI newBaseDir) {
		XIWorkspaceConfig newWorkspaceConfig = workspaceConfigFactory.createWorkspaceConfig(newBaseDir);
		setWorkspaceConfig(newWorkspaceConfig);
	}

	/**
	 * @param workspaceConfig
	 *            the new workspace configuration.
	 */
	synchronized protected void setWorkspaceConfig(IWorkspaceConfig workspaceConfig) {
		if (this.workspaceConfig != null && workspaceConfig != null &&
				this.workspaceConfig == workspaceConfig) {
			return;
		}

		// clean up old projects
		Collection<XProjectManager> pmCopy = new ArrayList<>(getProjectManagers());
		for (XProjectManager projectManager : pmCopy) {
			removeProject(projectManager);
		}
		projectName2ProjectManager.clear();
		fullIndex.clear();

		// init projects
		this.workspaceConfig = workspaceConfig;
		for (IProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			addProject(projectConfig);
		}
	}

	/** Adds a project to the workspace */
	synchronized public void addProject(IProjectConfig projectConfig) {
		XProjectManager projectManager = projectManagerProvider.get();
		ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
		projectManager.initialize(projectDescription, projectConfig, fullIndex, issueRegistry);
		projectName2ProjectManager.put(projectDescription.getName(), projectManager);
	}

	/** Removes a project from the workspace */
	synchronized protected void removeProject(XProjectManager projectManager) {
		removeProject(projectManager.getProjectConfig());
	}

	/** Removes a project from the workspace */
	synchronized public void removeProject(IProjectConfig projectConfig) {
		String projectName = projectConfig.getName();
		XProjectManager projectManager = getProjectManager(projectName);
		XtextResourceSet resourceSet = projectManager.getResourceSet();
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
		projectName2ProjectManager.remove(projectName);
		fullIndex.removeContainer(projectName);
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
		if (this.workspaceConfig == null) {
			return null;
		}
		return ((XIWorkspaceConfig) this.workspaceConfig).getPath();
	}

	/** Callback after a build was performed */
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
		WorkspaceChanges workspaceChanges = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);
		return getIncrementalGenerateBuildable(workspaceChanges);
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

	/** Triggers an incremental build, and will generate output files. */
	protected XBuildable getIncrementalGenerateBuildable(WorkspaceChanges workspaceChanges) {
		XBuildManager.XBuildable buildable = buildManager.getIncrementalGenerateBuildable(workspaceChanges);

		return (cancelIndicator) -> {
			List<IResourceDescription.Delta> deltas = buildable.build(cancelIndicator);
			afterBuild(deltas);
			LOG.info("Output files generated.");
			return deltas;
		};
	}

	/** Mark the given document as saved. */
	public XBuildManager.XBuildable didSave(URI uri) {
		WorkspaceChanges notifiedChanges = WorkspaceChanges.createUrisChanged(ImmutableList.of(uri));
		WorkspaceChanges workspaceChanges = ((XIWorkspaceConfig) getWorkspaceConfig()).update(uri,
				projectName -> projectName2ProjectManager.get(projectName).getProjectDescription());
		workspaceChanges.merge(notifiedChanges);

		return getIncrementalGenerateBuildable(workspaceChanges);
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

	/** Returns a snapshot of the current index. */
	public IResourceDescriptions getIndex() {
		return fullIndex.toDescriptions();
	}

	/** Returns the index. */
	public ConcurrentChunkedIndex getIndexRaw() {
		return fullIndex;
	}

	/** Returns the issue registry used by this workspace manager. */
	public ConcurrentIssueRegistry getIssueRegistry() {
		return issueRegistry;
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

	// FIXME GH-1774 remove this method!
	public XtextResource getResource(URI uri) {
		URI resourceURI = uri.trimFragment();
		XProjectManager projectMnr = getProjectManager(resourceURI);
		if (projectMnr != null) {
			XtextResource resource = (XtextResource) projectMnr.getResource(resourceURI);
			return resource;
		}
		return null;
	}

	// FIXME GH-1774 remove this method!
	public XDocument getDocument(URI uri) {
		XtextResource resource = getResource(uri);
		if (resource == null) {
			return null;
		}
		return getDocument(resource);
	}

	// FIXME GH-1774 remove this method!
	public XDocument getDocument(XtextResource resource) {
		if (resource == null) {
			return null;
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
