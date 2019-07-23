/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.preferences.external;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ui.external.EclipseExternalLibraryWorkspace;
import org.eclipse.xtext.util.Pair;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Lazy tree content provider implementation for the external libraries preference page's viewer.
 */
public class ExternalLibraryTreeContentProvider implements ILazyTreeContentProvider {

	private Optional<TreeViewer> treeViewerRef;
	// cache list of projects per location (invalidated on #inputChanged)
	private final Map<FileURI, List<IN4JSProject>> locationProjectsCache = new HashMap<>();

	@Inject
	private EclipseExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Override
	public void dispose() {
		// Nothing to dispose.
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (viewer instanceof TreeViewer) {
			this.treeViewerRef = Optional.of((TreeViewer) viewer);
		} else {
			this.treeViewerRef = Optional.absent();
		}
		// clear projects cache when input changes
		this.locationProjectsCache.clear();
	}

	@Override
	public void updateElement(final Object parent, final int index) {
		if (treeViewerRef.isPresent()) {
			final TreeViewer treeViewer = treeViewerRef.get();
			if (parent instanceof Iterable) {
				final Object child = Iterables.get((Iterable<?>) parent, index);
				treeViewer.replace(parent, index, child);
				if (child instanceof FileURI) {
					treeViewer.setChildCount(child, getProjects((FileURI) child).size());
				}
			} else if (parent instanceof FileURI) {
				final IN4JSProject child = getProjects((FileURI) parent).get(index);
				treeViewer.replace(parent, index, child);
			}
		}
	}

	@Override
	public void updateChildCount(final Object element, final int currentChildCount) {
		if (treeViewerRef.isPresent()) {
			final TreeViewer treeViewer = treeViewerRef.get();
			if (element instanceof Iterable) {
				treeViewer.setChildCount(element, Iterables.size((Iterable<?>) element));
			} else if (element instanceof FileURI) {
				treeViewer.setChildCount(element, getProjects((FileURI) element).size());
			} else {
				treeViewer.setChildCount(element, 0);
			}
		}
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	/** Returns the list of {@link IN4JSProject} to be found in the given external location. */
	private List<IN4JSProject> getProjects(FileURI location) {
		if (locationProjectsCache.isEmpty()) {
			initCache();
		}

		return locationProjectsCache.get(location);
	}

	private void initCache() {
		locationProjectsCache.clear();
		for (FileURI extLoc : externalLibraryPreferenceStore.getLocations()) {
			locationProjectsCache.putIfAbsent(extLoc, new LinkedList<>());
		}

		for (Pair<FileURI, ProjectDescription> pair : externalLibraryWorkspace
				.getProjectsIncludingUnnecessary()) {

			FileURI prjLocation = pair.getFirst();
			FileURI rootLocation = externalLibraryWorkspace.getRootLocationForResource(prjLocation);
			N4JSExternalProject project = externalLibraryWorkspace.getProject(prjLocation);
			locationProjectsCache.putIfAbsent(rootLocation, new LinkedList<>());
			List<IN4JSProject> list = locationProjectsCache.get(rootLocation);
			IN4JSProject iProject = project.getIProject();
			list.add(iProject);
		}
	}

}
