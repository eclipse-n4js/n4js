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
package org.eclipse.n4js.ui.internal;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class EclipseBasedN4JSWorkspace extends InternalN4JSWorkspace {
	/** Key for {@link MultiCleartriggerCache} */
	public static final String PROJECT_DESCRIPTIONS = "projectDescriptions";

	private final IWorkspaceRoot workspace;

	private final ProjectDescriptionLoader projectDescriptionLoader;

	private final MultiCleartriggerCache cache;

	private ProjectDescriptionLoadListener listener;

	/**
	 * Public for testing purpose.
	 */
	@Inject
	public EclipseBasedN4JSWorkspace(
			IWorkspaceRoot workspace,
			ProjectDescriptionLoader projectDescriptionLoader,
			MultiCleartriggerCache cache) {

		this.workspace = workspace;
		this.projectDescriptionLoader = projectDescriptionLoader;
		this.cache = cache;
	}

	IWorkspaceRoot getWorkspace() {
		return workspace;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		if (nestedLocation.isPlatformResource()) {
			return URI.createPlatformResourceURI(nestedLocation.segment(1), true);
		}
		// this might happen if the URI was located from non-platform information, e.g. in case
		// of a source file location found in a soure map
		if (nestedLocation.isFile()) {
			String nested = nestedLocation.toString();
			for (IProject proj : workspace.getProjects()) {
				URI projURI = URI.createFileURI(proj.getLocation().toFile().toString());
				String ps = projURI.toString();
				if (nested.startsWith(ps)) {
					return projURI;
				}
			}
		}
		return null;
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		if (!location.isPlatformResource()) {
			return null;
		}
		ProjectDescriptionLoaderAndNotifier supplier = new ProjectDescriptionLoaderAndNotifier(location);
		ProjectDescription existing = cache.get(supplier, PROJECT_DESCRIPTIONS, location);

		return existing;
	}

	/** Loads the project description and notifies the listener */
	private class ProjectDescriptionLoaderAndNotifier implements CleartriggerSupplier<ProjectDescription> {
		final URI location;

		public ProjectDescriptionLoaderAndNotifier(URI location) {
			this.location = location;
		}

		@Override
		public ProjectDescription get() {
			ProjectDescription pd = projectDescriptionLoader.loadProjectDescriptionAtLocation(location);
			return pd;
		}

		@Override
		public void postSupply() {
			if (listener != null) {
				// happens in test scenarios
				listener.onDescriptionLoaded(location);
			}
		}
	}

	@Override
	public URI getLocation(URI projectURI, ProjectReference projectReference) {
		String expectedProjectName = projectReference.getProjectName();
		if (expectedProjectName != null && expectedProjectName.length() > 0) {
			// the below call to workspace.getProject(name) will search the Eclipse IProject by name, using the
			// Eclipse project name (not the N4JS project name); thus, we have to convert from N4JS project name
			// to Eclipse project name, first (see ProjectDescriptionUtils#isProjectNameWithScope(String)):
			String expectedEclipseProjectName = ProjectDescriptionUtils
					.convertN4JSProjectNameToEclipseProjectName(expectedProjectName);
			IProject existingProject = workspace.getProject(expectedEclipseProjectName);
			if (existingProject.isAccessible()) {
				return URI.createPlatformResourceURI(expectedEclipseProjectName, true);
			}
		}
		return null;
	}

	@Override
	public UnmodifiableIterator<URI> getFolderIterator(URI folderLocation) {
		final IContainer container;
		if (URIUtils.isPlatformResourceUriPointingToProject(folderLocation)) {
			String n4jsProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(folderLocation);
			String eclipseProjectName = ProjectDescriptionUtils
					.convertN4JSProjectNameToEclipseProjectName(n4jsProjectName);
			container = workspace.getProject(eclipseProjectName);
		} else {
			container = workspace.getFolder(new Path(folderLocation.toPlatformString(true)));
		}
		if (container != null && container.exists()) {
			final List<URI> result = Lists.newLinkedList();
			try {
				container.accept(new IResourceVisitor() {
					@Override
					public boolean visit(IResource resource) throws CoreException {
						if (resource.getType() == IResource.FILE) {
							result.add(URI.createPlatformResourceURI(resource.getFullPath().toString(), true));
						}
						return true;
					}
				});
				return Iterators.unmodifiableIterator(result.iterator());
			} catch (CoreException e) {
				return Iterators.unmodifiableIterator(result.iterator());
			}
		}
		return Iterators.unmodifiableIterator(Collections.emptyIterator());
	}

	@Override
	public URI findArtifactInFolder(URI folderLocation, String folderRelativePath) {
		final String folderLocationString = folderLocation.toPlatformString(true);
		if (null != folderLocationString) {
			final IFolder folder = workspace.getFolder(new Path(folderLocationString));
			final String subPathStr = folderRelativePath.replace(File.separator, "/");
			final IPath subPath = new Path(subPathStr);
			final IFile file = folder != null ? folder.getFile(subPath) : null;
			if (file != null && file.exists()) {
				return folderLocation.appendSegments(subPathStr.split("/"));
			}
		}
		return null;
	}

	void setProjectDescriptionLoadListener(ProjectDescriptionLoadListener listener) {
		this.listener = listener;
	}

}
