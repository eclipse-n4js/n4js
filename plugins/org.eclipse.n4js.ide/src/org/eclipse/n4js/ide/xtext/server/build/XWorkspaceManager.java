/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.XIProjectDescriptionFactory;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;

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
		fullIndex.removeAllProjectsIndices();

		// init projects
		this.workspaceConfig = workspaceConfig;
		for (XIProjectConfig projectConfig : getProjectConfigs()) {
			addProject(projectConfig);
		}
	}

	/**
	 * Return the project configurations.
	 */
	public Set<? extends XIProjectConfig> getProjectConfigs() {
		XIWorkspaceConfig config = getWorkspaceConfig();
		if (config == null) {
			return Collections.emptySet();
		}
		return config.getProjects();
	}

	/**
	 * Updates the workspace according to the updated information in the file with the given URI.
	 */
	public WorkspaceChanges update(URI changedFile) {
		XIWorkspaceConfig config = getWorkspaceConfig();
		if (config == null) {
			return WorkspaceChanges.NO_CHANGES;
		}
		return config.update(changedFile,
				projectName -> getProjectBuilder(projectName).getProjectDescription());
	}

	/**
	 * Answers true, if the uri is from a source folder.
	 */
	public boolean isSourceFile(URI uri) {
		XIWorkspaceConfig config = getWorkspaceConfig();
		if (config == null) {
			return false;
		}
		IProjectConfig projectConfig = config.findProjectContaining(uri);
		if (projectConfig != null) {
			ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
			if (sourceFolder != null) {
				return true;
			}
		}
		return false;
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
		fullIndex.removeProjectIndex(projectName);
	}

	/**
	 * @return the workspace configuration
	 */
	public synchronized XIWorkspaceConfig getWorkspaceConfig() {
		return workspaceConfig;
	}

	/*
	 * Review feedback:
	 *
	 * Future versions won't have a single base directory. We ran out-of-sync with the Xtext default implementation.
	 */
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
		XIWorkspaceConfig config = getWorkspaceConfig();
		if (config == null) {
			return null;
		}
		return config.findProjectContaining(uri);
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
		for (IProjectConfig projectConfig : getProjectConfigs()) {
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
