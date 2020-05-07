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

import static java.util.Collections.emptyList;

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
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.n4js.xtext.workspace.WorkspaceUpdateChanges;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.workspace.IWorkspaceConfig;

import com.google.common.collect.ImmutableList;

/**
 * Wrapper around {@link IN4JSProject}.
 */
@SuppressWarnings("restriction")
public class N4JSProjectConfig implements IProjectConfig {

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

	private class SourceContainerForPackageJson implements IN4JSSourceFolder {
		private final URI pckjsonURI;

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
		return Collections.singletonList(delegate.getLocation().appendPath(delegate.getOutputPath()).toURI());
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
	public boolean indexOnly() {
		String outputPath = delegate.getOutputPath();
		if (".".equals(outputPath)) {
			return true;
		}

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

	/** @return true iff the output folder of this project can be cleaned */
	public boolean canClean() {
		return !indexOnly();
	}

	/**
	 * This methods handles changes from
	 * <ul>
	 * <li>existing -> existing (w/o project modifications) and from
	 * <li>existing -> non-existing (project deletion)
	 * </ul>
	 */
	public WorkspaceUpdateChanges update(URI changedResource) {
		SafeURI<?> pckjsonSafeUri = delegate.getProjectDescriptionLocation();
		if (pckjsonSafeUri == null || !delegate.exists()) {
			// project was deleted
			return WorkspaceUpdateChanges.createProjectRemoved(this);
		}

		URI pckjson = pckjsonSafeUri.toURI();
		if (!pckjson.equals(changedResource)) {
			// different file was saved/modified (not package.json)
			return WorkspaceUpdateChanges.NO_CHANGES;
		}

		// package.json was modified

		Set<? extends IN4JSSourceFolder> oldSourceFolders = getSourceFolders();
		ImmutableList<String> oldDeps = ((N4JSProject) delegate).getAllDependenciesAndImplementedApiNames();
		((N4JSProject) delegate).invalidate();
		Set<? extends IN4JSSourceFolder> newSourceFolders = getSourceFolders();
		ImmutableList<String> newDeps = ((N4JSProject) delegate).getAllDependenciesAndImplementedApiNames();

		// detect added/removed source folders
		Map<URI, IN4JSSourceFolder> oldSFs = new HashMap<>();
		Map<URI, IN4JSSourceFolder> newSFs = new HashMap<>();
		for (IN4JSSourceFolder sourceFolder : oldSourceFolders) {
			oldSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		for (IN4JSSourceFolder sourceFolder : newSourceFolders) {
			newSFs.put(sourceFolder.getPath(), sourceFolder);
		}
		List<ISourceFolder> addedSourceFolders = new ArrayList<>();
		List<ISourceFolder> removedSourceFolders = new ArrayList<>();
		for (URI sfUri : newSFs.keySet()) {
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

		// detect changes in dependencies
		// note that a change of the name attribute is not relevant since the folder name is used
		boolean dependencyChanged = !Objects.equals(oldDeps, newDeps);

		return new WorkspaceUpdateChanges(dependencyChanged, emptyList(), emptyList(), removedSourceFolders,
				addedSourceFolders, emptyList(), emptyList());
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
