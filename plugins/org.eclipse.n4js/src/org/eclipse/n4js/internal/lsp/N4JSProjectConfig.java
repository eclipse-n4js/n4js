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
package org.eclipse.n4js.internal.lsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.n4js.xtext.workspace.ConfigSnapshotFactory;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Wrapper around {@link IN4JSProject}.
 */
@SuppressWarnings("restriction")
public class N4JSProjectConfig implements XIProjectConfig {

	private final IWorkspaceConfig workspace;
	private final IN4JSProject delegate;

	/**
	 * Constructor
	 */
	public N4JSProjectConfig(IWorkspaceConfig workspace, IN4JSProject delegate) {
		this.workspace = workspace;
		this.delegate = delegate;
	}

	@Override
	public String getName() {
		return delegate.getProjectName().getRawName();
	}

	/** @return the wrapped n4js project. */
	public IN4JSProject toProject() {
		return delegate;
	}

	@Override
	public URI getPath() {
		return delegate.getLocation().withTrailingPathDelimiter().toURI();
	}

	@Override
	public Set<String> getDependencies() {
		// note: it is important to return a list that contains names of unresolved (i.e. non-existing) projects, to
		// avoid the need to recompute the list of dependencies of all existing projects whenever a project is added!
		List<String> deps = ((N4JSProject) delegate).getDependenciesUnresolved();
		return new LinkedHashSet<>(deps);
	}

	/** Special implementation for package.json files */
	public class SourceContainerForPackageJson implements IN4JSSourceFolder {
		final URI pckjsonURI;

		SourceContainerForPackageJson() {
			pckjsonURI = delegate.getLocation().appendSegment(N4JSGlobals.PACKAGE_JSON).toURI();
		}

		@Override
		public String getName() {
			return N4JSGlobals.PACKAGE_JSON;
		}

		@Override
		public List<URI> getAllResources(IFileSystemScanner scanner) {
			return Collections.singletonList(pckjsonURI);
		}

		@Override
		public IProjectConfig getProject() {
			return N4JSProjectConfig.this;
		}

		@Override
		public boolean contains(URI uri) {
			return pckjsonURI.equals(uri);
		}

		@Override
		public URI getPath() {
			return delegate.getLocation().toURI();
		}
	}

	@Override
	public Set<? extends IN4JSSourceFolder> getSourceFolders() {
		Set<IN4JSSourceFolder> sourceFolders = new LinkedHashSet<>();
		delegate.getSourceContainers().forEach(container -> sourceFolders.add(new N4JSSourceFolder(this, container)));
		sourceFolders.add(new SourceContainerForPackageJson());
		return sourceFolders;
	}

	@Override
	public IN4JSSourceFolder findSourceFolderContaining(URI member) {
		IN4JSSourceContainer sourceContainer = delegate.findSourceContainerWith(member);
		if (sourceContainer == null) {
			SourceContainerForPackageJson pckJsonSrcContainer = new SourceContainerForPackageJson();
			if (pckJsonSrcContainer.contains(member)) {
				return pckJsonSrcContainer;
			}
			return null;
		}
		return new N4JSSourceFolder(this, sourceContainer);
	}

	/** @return the output folders of this project */
	public List<URI> getOutputFolders() {
		return Collections.singletonList(
				delegate.getLocation().appendPath(delegate.getOutputPath()).withTrailingPathDelimiter().toURI());
	}

	@Override
	public IWorkspaceConfig getWorkspaceConfig() {
		return workspace;
	}

	/**
	 * Projects are indexed but not transpiled if they have '.' as the output path or if they are located in
	 * node_modules folders.
	 *
	 * @return true iff this project should be indexed only
	 */
	@Override
	public boolean indexOnly() {
		URI projectBase = getPath();
		String lastSegment = projectBase.lastSegment();
		if (lastSegment == null || lastSegment.isBlank()) {
			projectBase = projectBase.trimSegments(1);
		}
		projectBase = projectBase.trimSegments(1); // trim the project name
		lastSegment = projectBase.lastSegment();
		if (lastSegment != null && lastSegment.startsWith("@")) {
			projectBase = projectBase.trimSegments(1);
			lastSegment = projectBase.lastSegment();
		}
		if (lastSegment != null && N4JSGlobals.NODE_MODULES.equals(lastSegment)) {
			// index only true for npm libraries
			return true;
		}
		return false;
	}

	@Override
	public boolean isGeneratorEnabled() {
		ProjectType projectType = delegate.getProjectType();
		return !N4JSGlobals.PROJECT_TYPES_WITHOUT_GENERATION.contains(projectType);
	}

	/**
	 * Updates this project configuration's internal state. In addition, the given {@link ProjectDescription} is also
	 * updated accordingly.
	 * <p>
	 * This methods handles changes from
	 * <ul>
	 * <li>existing -> existing (w/o project modifications) and from
	 * <li>existing -> non-existing (project deletion)
	 * </ul>
	 */
	public WorkspaceChanges update(WorkspaceConfigSnapshot oldWorkspaceConfig, URI changedResource,
			ConfigSnapshotFactory configSnapshotFactory) {

		String projectName = getName();
		ProjectConfigSnapshot oldProjectConfig = projectName != null ? oldWorkspaceConfig.findProjectByName(projectName)
				: null;

		SafeURI<?> pckjsonSafeUri = delegate.getProjectDescriptionLocation();
		if (pckjsonSafeUri == null || !delegate.exists()) {
			// project was deleted
			return oldProjectConfig != null ? WorkspaceChanges.createProjectRemoved(oldProjectConfig)
					: WorkspaceChanges.NO_CHANGES;
		}

		URI pckjson = pckjsonSafeUri.toURI();
		if (!pckjson.equals(changedResource)) {
			// different file was saved/modified (not package.json)
			return WorkspaceChanges.NO_CHANGES;
		}

		// package.json was modified

		((N4JSProject) delegate).invalidate();
		ProjectConfigSnapshot newProjectConfig = configSnapshotFactory.createProjectConfigSnapshot(this);

		if (oldProjectConfig == null) {
			return WorkspaceChanges.createProjectAdded(newProjectConfig);
		}

		return computeChanges(oldProjectConfig, newProjectConfig);
	}

	/**
	 * Given two project configuration snapshots representing the old and new state of the receiving project, this
	 * method computes the corresponding workspace changes. If no changes occurred, an empty {@link WorkspaceChanges}
	 * instance will be returned.
	 */
	public static WorkspaceChanges computeChanges(ProjectConfigSnapshot oldProjectConfig,
			ProjectConfigSnapshot newProjectConfig) {

		Set<? extends SourceFolderSnapshot> oldSourceFolders = oldProjectConfig.getSourceFolders();
		Set<? extends SourceFolderSnapshot> newSourceFolders = newProjectConfig.getSourceFolders();

		// detect added/removed source folders
		Map<URI, SourceFolderSnapshot> oldSFs = new HashMap<>();
		Map<URI, SourceFolderSnapshot> newSFs = new HashMap<>();
		for (SourceFolderSnapshot sourceFolder : oldSourceFolders) {
			oldSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		for (SourceFolderSnapshot sourceFolder : newSourceFolders) {
			newSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		List<SourceFolderSnapshot> addedSourceFolders = new ArrayList<>();
		List<SourceFolderSnapshot> removedSourceFolders = new ArrayList<>();
		for (URI sfUri : Iterables.concat(oldSFs.keySet(), newSFs.keySet())) {
			boolean isOld = oldSFs.containsKey(sfUri);
			boolean isNew = newSFs.containsKey(sfUri);
			if (isOld && isNew) {
				// unchanged
			} else if (isOld && !isNew) {
				removedSourceFolders.add(oldSFs.get(sfUri));
			} else if (!isOld && isNew) {
				addedSourceFolders.add(newSFs.get(sfUri));
			}
		}

		// detect changes in project properties
		boolean propertiesChanged = !addedSourceFolders.isEmpty() || !removedSourceFolders.isEmpty()
				|| !Objects.equals(oldProjectConfig, newProjectConfig);

		return new WorkspaceChanges(ImmutableList.of(), ImmutableList.of(), ImmutableList.of(),
				ImmutableList.copyOf(removedSourceFolders),
				ImmutableList.copyOf(addedSourceFolders), ImmutableList.of(), ImmutableList.of(),
				propertiesChanged ? ImmutableList.of(newProjectConfig) : ImmutableList.of());
	}

	/** @see N4JSProject#getWorkspaces() */
	public List<String> getWorkspaces() {
		return ((N4JSProject) delegate).getWorkspaces();
	}

	/** @see N4JSProject#isWorkspacesProject() */
	public boolean isWorkspacesProject() {
		return ((N4JSProject) delegate).isWorkspacesProject();
	}

}
