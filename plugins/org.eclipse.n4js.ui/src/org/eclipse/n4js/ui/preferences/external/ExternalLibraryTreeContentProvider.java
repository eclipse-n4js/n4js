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

import static com.google.common.collect.FluentIterable.from;

import java.io.File;
import java.net.URI;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Lazy tree content provider implementation for the external libraries preference page's viewer.
 */
public class ExternalLibraryTreeContentProvider implements ILazyTreeContentProvider {

	private Optional<TreeViewer> treeViewerRef;

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
	}

	@Override
	public void updateElement(final Object parent, final int index) {
		if (treeViewerRef.isPresent()) {
			final TreeViewer treeViewer = treeViewerRef.get();
			if (parent instanceof Iterable) {
				final Object child = Iterables.get((Iterable<?>) parent, index);
				treeViewer.replace(parent, index, child);
				if (child instanceof URI) {
					treeViewer.setChildCount(child, Iterables.size(getProjects((URI) child)));
				}
			} else if (parent instanceof URI) {
				final IN4JSProject child = Iterables.get(getProjects((URI) parent), index);
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
				treeViewer.setChildCount(element, Iterables.size(getProjects((URI) element)));
			} else {
				treeViewer.setChildCount(element, 0);
			}
		}
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	private Iterable<IN4JSProject> getProjects(URI location) {
		return from(externalLibraryWorkspace.getProjects(location))
				.filter(p -> p.exists())
				.transform(p -> core.findProject(convertUri(p.getLocationURI())).orNull())
				.filter(IN4JSProject.class)
				.filter(p -> p.exists() && p.isExternal());
	}

	private org.eclipse.emf.common.util.URI convertUri(URI uri) {
		return org.eclipse.emf.common.util.URI.createFileURI(new File(uri).getAbsolutePath());
	}

}
