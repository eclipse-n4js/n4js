/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.XIWorkspaceConfigFactory;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIWorkspaceConfig;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
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
@SuppressWarnings({ "deprecation" })
@Singleton
public class XWorkspaceManager {

	@Inject
	private XIWorkspaceConfigFactory workspaceConfigFactory;

	@Inject
	private Provider<ProjectBuilder> projectBuilderProvider;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private ConcurrentIndex workspaceIndex;

	private final Map<String, ProjectBuilder> projectName2ProjectBuilder = new HashMap<>();

	private XIWorkspaceConfig workspaceConfig;

	private WorkspaceConfigSnapshot workspaceConfigSnapshot;

	/** The result of a {@link XWorkspaceManager#update(Set, Set, boolean) workspace update}. */
	public static class UpdateResult {
		/** The workspace changes. */
		public final WorkspaceChanges changes;
		/** Former contents of the projects that were removed (per project name). */
		public final Multimap<String, IResourceDescription> removedProjectsContents;

		/** Creates a new {@link UpdateResult}. */
		public UpdateResult(WorkspaceChanges changes, Multimap<String, IResourceDescription> removedProjectsContents) {
			this.changes = changes;
			this.removedProjectsContents = ImmutableMultimap.copyOf(removedProjectsContents);
		}
	}

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
	private void refreshWorkspaceConfig(URI newBaseDir) {
		setWorkspaceConfig(workspaceConfigFactory.createWorkspaceConfig(newBaseDir));
	}

	/**
	 * @param workspaceConfig
	 *            the new workspace configuration.
	 */
	private void setWorkspaceConfig(XIWorkspaceConfig workspaceConfig) {
		if (this.workspaceConfig != null && workspaceConfig != null &&
				this.workspaceConfig == workspaceConfig) {
			return;
		}

		projectName2ProjectBuilder.values().forEach(b -> b.doClearWithNotification());
		projectName2ProjectBuilder.clear();

		this.workspaceConfig = workspaceConfig;
		this.workspaceConfigSnapshot = workspaceConfig.toSnapshot();

		// init projects
		addProjects(this.workspaceConfigSnapshot.getProjects());
		workspaceIndex.initialize(this.workspaceConfigSnapshot);
	}

	/**
	 * Return the project configurations.
	 */
	public Set<? extends ProjectConfigSnapshot> getProjectConfigs() {
		WorkspaceConfigSnapshot config = getWorkspaceConfig();
		if (config == null) {
			return Collections.emptySet();
		}
		return config.getProjects();
	}

	/**
	 * Updates the workspace according to the updated information in the files with the given URIs.
	 *
	 * @param refresh
	 *            see {@link XIWorkspaceConfig#update(WorkspaceConfigSnapshot, Set, Set, boolean)}.
	 */
	public UpdateResult update(Set<URI> dirtyFiles, Set<URI> deletedFiles, boolean refresh) {
		if (workspaceConfig == null) {
			return new UpdateResult(WorkspaceChanges.NO_CHANGES, ImmutableListMultimap.of());
		}

		WorkspaceChanges changes = workspaceConfig.update(workspaceConfigSnapshot, dirtyFiles, deletedFiles, refresh);
		return applyWorkspaceChanges(changes);
	}

	/**
	 * Apply the given workspace changes to this manager's internal state and notify the workspace index.
	 */
	protected UpdateResult applyWorkspaceChanges(WorkspaceChanges changes) {
		// collects contents of removed projects before actually removing anything
		Multimap<String, IResourceDescription> removedProjectsContents = collectAllResourceDescriptions(
				changes.getRemovedProjects());

		removeProjects(changes.getRemovedProjects());
		updateProjects(changes.getChangedProjects());
		addProjects(changes.getAddedProjects());

		workspaceConfigSnapshot = workspaceIndex.changeOrRemoveProjects(
				Iterables.concat(changes.getAddedProjects(), changes.getChangedProjects()),
				changes.getRemovedProjects().stream()
						.map(ProjectConfigSnapshot::getName)
						.collect(Collectors.toList()));

		return new UpdateResult(changes, removedProjectsContents);
	}

	private Multimap<String, IResourceDescription> collectAllResourceDescriptions(
			Iterable<? extends ProjectConfigSnapshot> projects) {

		Multimap<String, IResourceDescription> result = ArrayListMultimap.create();
		for (ProjectConfigSnapshot pc : projects) {
			String projectName = pc.getName();
			ResourceDescriptionsData data = workspaceIndex.getProjectIndex(projectName);
			if (data != null) {
				result.putAll(projectName, data.getAllResourceDescriptions());
			}
		}
		return result;
	}

	/** Adds a project to the workspace */
	protected void addProjects(Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		for (ProjectConfigSnapshot projectConfig : withoutDuplicates(projectConfigs)) {
			ProjectBuilder projectBuilder = projectBuilderProvider.get();
			projectBuilder.initialize(projectConfig);
			projectName2ProjectBuilder.put(projectConfig.getName(), projectBuilder);
		}
	}

	/** Removes a project from the workspace */
	protected void removeProjects(Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		for (ProjectConfigSnapshot projectConfig : projectConfigs) { // no need for #withoutDuplicates() here
			String projectName = projectConfig.getName();
			ProjectBuilder projectBuilder = projectName2ProjectBuilder.remove(projectName);
			if (projectBuilder != null) {
				projectBuilder.doClearWithoutNotification();
			}
		}
	}

	/** Updates projects after their project configuration has changed. */
	protected void updateProjects(Iterable<? extends ProjectConfigSnapshot> projectConfigs) {
		for (ProjectConfigSnapshot projectConfig : withoutDuplicates(projectConfigs)) {
			ProjectBuilder pb = getProjectBuilder(projectConfig.getName());
			if (pb != null) {
				pb.setProjectConfig(projectConfig);
			}
		}
	}

	/**
	 * @return the workspace configuration
	 */
	public WorkspaceConfigSnapshot getWorkspaceConfig() {
		return workspaceConfigSnapshot;
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
	 * @param nestedURI
	 *            the contained uri
	 * @return the project builder.
	 */
	public ProjectBuilder getProjectBuilder(URI nestedURI) {
		ProjectConfigSnapshot projectConfig = getProjectConfig(nestedURI);
		String name = null;
		if (projectConfig != null) {
			name = projectConfig.getName();
		}
		return getProjectBuilder(name);
	}

	/** Find the project that contains the uri. */
	public ProjectConfigSnapshot getProjectConfig(URI uri) {
		WorkspaceConfigSnapshot config = getWorkspaceConfig();
		if (config == null) {
			return null;
		}
		return config.findProjectByNestedLocation(uri);
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

	/**
	 * Clears the resource set of all project builders.
	 *
	 * @see ProjectBuilder#clearResourceSet()
	 */
	public void clearResourceSets() {
		for (ProjectBuilder pb : projectName2ProjectBuilder.values()) {
			pb.clearResourceSet();
		}
	}

	/** @return a workspace relative URI for a given URI */
	public URI makeWorkspaceRelative(URI uri) {
		URI withEmptyAuthority = uriExtensions.withEmptyAuthority(uri);
		URI relativeUri = withEmptyAuthority.deresolve(getBaseDir());
		return relativeUri;
	}

	/**
	 * Returns the workspace issues known for the given URI.
	 */
	public ImmutableList<? extends LSPIssue> getValidationIssues(URI uri) {
		ProjectBuilder projectBuilder = getProjectBuilder(uri);
		if (projectBuilder != null) {
			return projectBuilder.getValidationIssues(uri);
		}
		return ImmutableList.of();
	}

	/**
	 * Removes duplicates from the given iterable of projects (based on path), retaining in each case the <em>last</em>
	 * project.
	 */
	protected Iterable<? extends ProjectConfigSnapshot> withoutDuplicates(
			Iterable<? extends ProjectConfigSnapshot> projectConfigs) {

		if (IterableExtensions.isEmpty(projectConfigs)) {
			return projectConfigs;
		}
		return IterableExtensions.toMap(projectConfigs, ProjectConfigSnapshot::getPath).values();
	}
}
