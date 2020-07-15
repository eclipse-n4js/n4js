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

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentChunkedIndex;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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

	@Inject
	private XIWorkspaceConfigFactory workspaceConfigFactory;

	@Inject
	private Provider<XProjectManager> projectManagerProvider;

	@Inject
	private XIProjectDescriptionFactory projectDescriptionFactory;

	@Inject
	private XBuildManager buildManager;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private ConcurrentChunkedIndex fullIndex;

	private final Map<String, XProjectManager> projectName2ProjectManager = new HashMap<>();

	private XIWorkspaceConfig workspaceConfig;

	private ConcurrentIssueRegistry issueRegistry;

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
		fullIndex.initialize(newWorkspaceConfig.toSnapshot());
		setWorkspaceConfig(newWorkspaceConfig);
	}

	/**
	 * @param workspaceConfig
	 *            the new workspace configuration.
	 */
	synchronized protected void setWorkspaceConfig(XIWorkspaceConfig workspaceConfig) {
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
		fullIndex.removeAllContainers();

		// init projects
		this.workspaceConfig = workspaceConfig;
		for (XIProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			addProject(projectConfig);
		}
	}

	/** Adds a project to the workspace */
	synchronized public void addProject(XIProjectConfig projectConfig) {
		XProjectManager projectManager = projectManagerProvider.get();
		ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
		projectManager.initialize(projectDescription, projectConfig, fullIndex, issueRegistry);
		projectName2ProjectManager.put(projectDescription.getName(), projectManager);
		fullIndex.setProjectConfigSnapshot(projectConfig.toSnapshot());
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
	public XIWorkspaceConfig getWorkspaceConfig() throws ResponseErrorException {
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
		return this.workspaceConfig.getPath();
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
	public List<Delta> doInitialBuild(CancelIndicator cancelIndicator) {
		List<ProjectDescription> newProjects = new ArrayList<>();
		for (IProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
			newProjects.add(projectDescription);
		}
		List<Delta> deltas = buildManager.doInitialBuild(newProjects, cancelIndicator);
		return deltas;
	}

	/** Triggers an incremental build, and will generate output files. */
	protected XBuildable getIncrementalGenerateBuildable(WorkspaceChanges workspaceChanges) {
		return buildManager.getIncrementalBuildable(workspaceChanges);
	}

	/** Mark the given document as saved. */
	public XBuildManager.XBuildable didSave(URI uri) {
		WorkspaceChanges notifiedChanges = WorkspaceChanges.createUrisChanged(ImmutableList.of(uri));
		WorkspaceChanges workspaceChanges = getWorkspaceConfig().update(uri,
				projectName -> projectName2ProjectManager.get(projectName).getProjectDescription());

		Iterable<IProjectConfigSnapshot> projectsWithChangedDeps = IterableExtensions.map(
				workspaceChanges.getProjectsWithChangedDependencies(),
				XIProjectConfig::toSnapshot);
		fullIndex.setProjectConfigSnapshots(projectsWithChangedDeps);

		workspaceChanges.merge(notifiedChanges);
		return getIncrementalGenerateBuildable(workspaceChanges);
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

	/** Returns the index. */
	public ConcurrentChunkedIndex getIndex() {
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

	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		URI withEmptyAuthority = uriExtensions.withEmptyAuthority(uri);
		URI relativeUri = withEmptyAuthority.deresolve(getBaseDir());
		return relativeUri;
	}
}
