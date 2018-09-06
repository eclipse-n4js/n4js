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

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Lazy tree content provider implementation for the external libraries preference page's viewer.
 */
public class ExternalLibraryTreeContentProvider implements ILazyTreeContentProvider {

	private Optional<TreeViewer> treeViewerRef;
	// cache list of projects per location (invalidated on #inputChanged)
	private final Map<URI, List<IN4JSProject>> locationProjectsCache = new HashMap<>();

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	@Inject
	private IN4JSCore core;

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
				if (child instanceof URI) {
					treeViewer.setChildCount(child, getProjects((URI) child).size());
				}
			} else if (parent instanceof URI) {
				final IN4JSProject child = getProjects((URI) parent).get(index);
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
			} else if (element instanceof URI) {
				treeViewer.setChildCount(element, getProjects((URI) element).size());
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
	private List<IN4JSProject> getProjects(URI location) {
		if (locationProjectsCache.containsKey(location)) {
			return locationProjectsCache.get(location);
		} else {
			final List<IN4JSProject> projects = doGetProjects(location);
			locationProjectsCache.put(location, projects);
			return projects;
		}
	}

	/**
	 * Expensive initialization of the list of contained projects for the given external {@code location}.
	 *
	 * Use the cached variant {@link #getProjects(URI)} for repeated access.
	 */
	private List<IN4JSProject> doGetProjects(URI location) {
		return externalLibraryWorkspace.getProjectsIn(location).stream()
				.filter(p -> p.exists())
				.map(p -> core.create(convertUri(p.getLocationURI())))
				.filter(p -> p.exists() && p.isExternal()).collect(Collectors.toList());
	}

	private org.eclipse.emf.common.util.URI convertUri(URI uri) {
		return org.eclipse.emf.common.util.URI.createFileURI(new File(uri).getAbsolutePath());
	}

}
