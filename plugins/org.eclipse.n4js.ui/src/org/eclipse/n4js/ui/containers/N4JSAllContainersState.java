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

import static com.google.common.collect.FluentIterable.from;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.fileextensions.FileExtensionTypeHelper;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JSAllContainersState returns the visible elements for global scoping. The implementation is basically separated in
 * {@link N4JSProjectsStateHelper}.
 */
@Singleton
public class N4JSAllContainersState extends AbstractAllContainersState {

	private static final String PLATFORM_RESOURCE_SCHEME = "platform:/resource";

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
	protected boolean isAffectingContainerState(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
			String fileExtension = delta.getFullPath().getFileExtension();
			if (null != fileExtension && fileExtensionTypeHelper.isTypable(fileExtension)) {
				return true;
			}
			if (IN4JSProject.PACKAGE_JSON.equals(delta.getFullPath().lastSegment())) {
				return true;
			}
			if (IN4JSArchive.NFAR_FILE_EXTENSION.equals(fileExtension)) {
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
		if (nfarHasBeenChanged(delta) || packageJSONFileHasBeenChanged(delta)) {
			return true;
		}
		return false;
	}

	private boolean isSourceContainerModification(final IResourceDelta delta) {
		final String fullPath = delta.getFullPath().toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPath, true);
		final IN4JSProject project = core.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			return from(project.getSourceContainers())
					.transform(container -> container.getLocation())
					.filter(uri -> uri.isPlatformResource())
					.transform(uri -> uri.toString())
					.transform(uri -> uri.replaceFirst(PLATFORM_RESOURCE_SCHEME, ""))
					.firstMatch(uri -> uri.equals(fullPath))
					.isPresent();
		}
		return false;
	}

	private boolean nfarHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSArchive.NFAR_FILE_EXTENSION.equalsIgnoreCase(delta.getFullPath().getFileExtension());
	}

	private boolean packageJSONFileHasBeenChanged(IResourceDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& delta.getResource().getType() == IResource.FILE
				&& IN4JSProject.PACKAGE_JSON.equalsIgnoreCase(delta.getFullPath().lastSegment());
	}
}
