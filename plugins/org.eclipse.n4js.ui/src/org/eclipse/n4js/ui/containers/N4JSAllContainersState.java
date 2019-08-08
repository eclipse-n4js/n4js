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
package org.eclipse.n4js.ui.containers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.external.ProjectStateChangeListener;
import org.eclipse.n4js.ui.internal.EclipseBasedN4JSWorkspace;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JSAllContainersState returns the visible elements for global scoping. The implementation is basically separated in
 * {@link N4JSProjectsStateHelper}.
 */
@Singleton
public class N4JSAllContainersState extends AbstractAllContainersState {

	private static final Logger LOGGER = Logger.getLogger(N4JSAllContainersState.class);

	@Inject
	private N4JSProjectsStateHelper projectsHelper;

	@Inject
	private FileExtensionTypeHelper fileExtensionTypeHelper;

	@Inject
	private IN4JSCore core;

	@Override
	protected String doInitHandle(URI uri) {
		String handle = projectsHelper.initHandle(uri);
		return handle;
	}

	@Override
	protected Collection<URI> doInitContainedURIs(String containerHandle) {
		Collection<URI> initContainedURIs = projectsHelper.initContainedURIs(containerHandle);
		return initContainedURIs;
	}

	@Override
	protected List<String> doInitVisibleHandles(String handle) {
		List<String> visibleHandles = projectsHelper.initVisibleHandles(handle);
		return visibleHandles;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// first update the project state based on the resource change event
		updateProjectState(event);
		// then go on checking for container state changes (this may trigger a build)
		super.resourceChanged(event);
	}

	/**
	 * Handles the given {@link IResourceChangeEvent} and updates the project state (cache of available projects and
	 * project descriptions) accordingly.
	 *
	 * If the event contains a {@link IResourceDelta}, this method traverses the resource delta tree and invokes
	 * {@link #updateProjectState(IResourceDelta)} accordingly.
	 */
	private void updateProjectState(IResourceChangeEvent event) {
		if (event.getDelta() != null) {
			IResourceDelta eventDelta = event.getDelta();
			try {
				AtomicBoolean mustClear = new AtomicBoolean(false);
				// traverse all resource deltas to a depth of 2 (projects and their direct children)
				eventDelta.accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(IResourceDelta delta) throws CoreException {
						final IResource resource = delta.getResource();
						if (resource == null) {
							return true;
						}
						final IPath path = resource.getFullPath();
						final int pathLength = path.segmentCount();

						if (pathLength <= 2) {
							if (updateProjectState(delta)) {
								mustClear.set(true);
								return false;
							}
						}
						// only traverse the children of project-deltas (e.g. project description files)
						return pathLength == 0 || resource instanceof IProject;
					}
				});
				if (mustClear.get()) {
					projectsHelper.clearProjectCache();
				}
			} catch (CoreException e) {
				LOGGER.error("Failed to process IResourceDelta", e);
			}
		}
	}

	/**
	 * Handles the given {@link IResourceDelta} and updates the project state (cache of available projects and project
	 * descriptions) accordingly.
	 *
	 * If so, invalidates the {@link EclipseBasedN4JSWorkspace} cache of project descriptions for the updated projects.
	 */
	private boolean updateProjectState(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
			if (IN4JSProject.PACKAGE_JSON.equals(delta.getFullPath().lastSegment())) {
				return true;
			}
			if (delta.getResource() instanceof IProject) {
				return true;
			}
			if (delta.getResource() instanceof IFolder) {
				if (isSourceContainerModification(delta)) {
					return true;
				}
			}
			return false;
		} else if (delta.getKind() == IResourceDelta.CHANGED) {
			if (delta.getResource() instanceof IProject) {
				if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
					return true;
				}
				return false;
			}
			if (delta.getResource() instanceof IFolder) {
				if ("node_modules".equals(delta.getFullPath().lastSegment())) {
					return true;
				}
				return false;
			}
		}
		if (packageJSONFileHasBeenChanged(delta)) {
			return true;
		}
		return false;
	}

	/**
	 * Indicates whether the resource change represented by the given {@code delta} does affect this container state.
	 *
	 * Note that this method will only be invoked until a container-state-affecting delta is found. Thus, it is not
	 * suitable for listening for all resource changes that may occur in the workspace.
	 */
	@Override
	protected boolean isAffectingContainerState(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
			String fileExtension = delta.getFullPath().getFileExtension();
			if (null != fileExtension && fileExtensionTypeHelper.isTypable(fileExtension)) {
				return true;
			}
			if (IN4JSProject.PACKAGE_JSON.equals(delta.getFullPath().lastSegment())) {
				return true;
			}

			if (delta.getResource() instanceof IProject) {
				return true;
			}
			if (delta.getResource() instanceof IFolder && isSourceContainerModification(delta)) {
				return true;
			}
			return false;
		} else if (delta.getKind() == IResourceDelta.CHANGED && delta.getResource() instanceof IProject) {
			if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
				return true;
			}
			if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
				return true;
			}
			return false;
		}
		if (packageJSONFileHasBeenChanged(delta)) {
			return true;
		}
		return false;
	}

	private boolean isSourceContainerModification(final IResourceDelta delta) {
		return ProjectStateChangeListener.isSourceContainerModification(core, delta.getFullPath());
	}

	private boolean packageJSONFileHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSProject.PACKAGE_JSON.equalsIgnoreCase(delta.getFullPath().lastSegment());
	}
}
