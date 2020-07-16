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
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIndex;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

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
	private Provider<ProjectBuilder> projectBuilderProvider;

	@Inject
	private XIProjectDescriptionFactory projectDescriptionFactory;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private ConcurrentIndex fullIndex;

	private final Map<String, ProjectBuilder> projectName2ProjectBuilder = new HashMap<>();

	private XIWorkspaceConfig workspaceConfig;

	/** Reinitialize a workspace at the current location. */
	public void reinitialize() {
		initialize(getBaseDir());
	}

	/**
	 * Initialize a workspace at the given location.
	 *
	 * @param newBaseDir
	 *            the location
	 */
	public void initialize(URI newBaseDir) {
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
		Collection<ProjectBuilder> pbCopy = new ArrayList<>(getProjectBuilders());
		for (ProjectBuilder projectBuilder : pbCopy) {
			removeProject(projectBuilder);
		}
		projectName2ProjectBuilder.clear();
		fullIndex.removeAllContainers();

		// init projects
		this.workspaceConfig = workspaceConfig;
		for (XIProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			addProject(projectConfig);
		}
	}

	/** Adds a project to the workspace */
	synchronized public void addProject(XIProjectConfig projectConfig) {
		ProjectBuilder projectBuilder = projectBuilderProvider.get();
		ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
		projectBuilder.initialize(projectDescription, projectConfig);
		projectName2ProjectBuilder.put(projectDescription.getName(), projectBuilder);
		fullIndex.setProjectConfigSnapshot(projectConfig.toSnapshot());
	}

	/** Removes a project from the workspace */
	synchronized protected void removeProject(ProjectBuilder projectBuilder) {
		removeProject(projectBuilder.getProjectConfig());
	}

	/** Removes a project from the workspace */
	synchronized public void removeProject(IProjectConfig projectConfig) {
		String projectName = projectConfig.getName();
		ProjectBuilder projectBuilder = getProjectBuilder(projectName);
		XtextResourceSet resourceSet = projectBuilder.getResourceSet();
		boolean wasDeliver = resourceSet.eDeliver();
		try {
			resourceSet.eSetDeliver(false);
			resourceSet.getResources().clear();
		} finally {
			resourceSet.eSetDeliver(wasDeliver);
		}
		projectName2ProjectBuilder.remove(projectName);
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
	 * @param uri
	 *            the contained uri
	 * @return the project builder.
	 */
	public ProjectBuilder getProjectBuilder(URI uri) {
		IProjectConfig projectConfig = getProjectConfig(uri);
		String name = null;
		if (projectConfig != null) {
			name = projectConfig.getName();
		}
		return getProjectBuilder(name);
	}

	/** Find the project that contains the uri. */
	public IProjectConfig getProjectConfig(URI uri) {
		return getWorkspaceConfig().findProjectContaining(uri);
	}

	/**
	 * Return all project builders.
	 *
	 * @return all project builders.
	 */
	public Collection<ProjectBuilder> getProjectBuilders() {
		return Collections.unmodifiableCollection(projectName2ProjectBuilder.values());
	}

	/**
	 * Return the project builder for the project with the given name.
	 *
	 * @param projectName
	 *            the project name
	 * @return the project builder
	 */
	public ProjectBuilder getProjectBuilder(String projectName) {
		return projectName2ProjectBuilder.get(projectName);
	}

	/** @return all project descriptions. */
	public List<ProjectDescription> getProjectDescriptions() {
		List<ProjectDescription> newProjects = new ArrayList<>();
		for (IProjectConfig projectConfig : getWorkspaceConfig().getProjects()) {
			ProjectDescription projectDescription = projectDescriptionFactory.getProjectDescription(projectConfig);
			newProjects.add(projectDescription);
		}
		return newProjects;
	}

	/** Return true if the given resource still exists. */
	protected boolean exists(URI uri) {
		ProjectBuilder projectBuilder = getProjectBuilder(uri);
		if (projectBuilder != null) {
			XtextResourceSet rs = projectBuilder.getResourceSet();
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
