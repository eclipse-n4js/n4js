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
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
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
import org.eclipse.n4js.N4JSGlobals;
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

	/** @return the eclipse workspace root */
	public IWorkspaceRoot getWorkspace() {
		return workspace;
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		if (nestedLocation.isPlatformResource()) {
			return URI.createPlatformResourceURI(nestedLocation.segment(1), true);
		}
		// this might happen if the URI was located from non-platform information, e.g. in case
		// of a source file location found in a source map
		// FIXME: This loop and the call 'toFile()' / 'isFile()' are very expensive
		// FIXME: since this method is called for a lot of external files
		if (nestedLocation.toString().startsWith("file:/")) {
			String nested = nestedLocation.toFileString();
			java.nio.file.Path nestedPath = Paths.get(nested);

			for (IProject proj : workspace.getProjects()) {
				String locationStr = proj.getLocation().toString();
				java.nio.file.Path locationPath = Paths.get(locationStr);

				if (nestedPath.startsWith(locationPath)) {
					java.nio.file.Path nodeModulesPath = locationPath.resolve(N4JSGlobals.NODE_MODULES);

					if (!nestedPath.startsWith(nodeModulesPath) || nestedPath.equals(nodeModulesPath)) {
						// Note: There can be projects in nested node_modules folder.
						// The node_modules folder is still part of a project, but all
						// elements below the node_modules folder are not part of this project.
						return URIUtils.toFileUri(proj);
					}
				}
			}
		}
		return null;

	}

	/** @return the {@link URI} for a project with the given n4js project name */
	public URI findProjectForName(String projectName) {
		if (projectName == null) {
			return null;
		}
		for (IProject prj : workspace.getProjects()) {
			URI uri = URIUtils.convert(prj);
			String n4jsProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(uri);
			if (projectName.equals(n4jsProjectName)) {
				return uri;
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
		ProjectDescription existing = cache.get(supplier, MultiCleartriggerCache.CACHE_KEY_PROJECT_DESCRIPTIONS,
				location);

		return existing;
	}

	@Override
	public Collection<URI> getAllProjectLocations() {
		Collection<URI> prjLocations = new LinkedList<>();
		for (IProject prj : workspace.getProjects()) {
			URI uri = URIUtils.convert(prj);
			prjLocations.add(uri);
		}
		return prjLocations;
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
						// do not iterate over contents of nested node_modules folders
						if (resource.getType() == IResource.FOLDER &&
								resource.getName().equals(N4JSGlobals.NODE_MODULES)) {
							return false;
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
