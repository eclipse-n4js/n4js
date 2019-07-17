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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.MultiCleartriggerCache;
import org.eclipse.n4js.internal.MultiCleartriggerCache.CleartriggerSupplier;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class EclipseBasedN4JSWorkspace extends InternalN4JSWorkspace<PlatformResourceURI> {

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
	public PlatformResourceURI fromURI(URI uri) {
		return new PlatformResourceURI(uri);
	}

	@Override
	public PlatformResourceURI findProjectWith(PlatformResourceURI nestedLocation) {
		return new PlatformResourceURI(URI.createPlatformResourceURI(nestedLocation.toURI().segment(1), true));

		// // this might happen if the URI was located from non-platform information, e.g. in case
		// // of a source file location found in a source map
		// // FIXME: This loop and the call 'toFile()' / 'isFile()' are very expensive
		// // FIXME: since this method is called for a lot of external files
		// if (nestedLocation.toString().startsWith("file:/")) {
		// String nested = nestedLocation.toFileString();
		// java.nio.file.Path nestedPath = Paths.get(nested);
		//
		// for (IProject proj : workspace.getProjects()) {
		// String locationStr = proj.getLocation().toString();
		// java.nio.file.Path locationPath = Paths.get(locationStr);
		//
		// if (nestedPath.startsWith(locationPath)) {
		// java.nio.file.Path nodeModulesPath = locationPath.resolve(N4JSGlobals.NODE_MODULES);
		//
		// if (!nestedPath.startsWith(nodeModulesPath) || nestedPath.equals(nodeModulesPath)) {
		// // Note: There can be projects in nested node_modules folder.
		// // The node_modules folder is still part of a project, but all
		// // elements below the node_modules folder are not part of this project.
		// return URIUtils.toFileUri(proj);
		// }
		// }
		// }
		// }
		// return null;

	}

	/** @return the {@link URI} for a project with the given n4js project name */
	// public URI findProjectForName(String projectName) {
	// if (projectName == null) {
	// return null;
	// }
	// for (IProject prj : workspace.getProjects()) {
	// URI uri = URIUtils.convert(prj);
	// String n4jsProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(uri);
	// if (projectName.equals(n4jsProjectName)) {
	// return uri;
	// }
	// }
	// return null;
	// }

	@Override
	public ProjectDescription getProjectDescription(PlatformResourceURI location) {
		ProjectDescriptionLoaderAndNotifier supplier = new ProjectDescriptionLoaderAndNotifier(location);
		ProjectDescription existing = cache.get(supplier, MultiCleartriggerCache.CACHE_KEY_PROJECT_DESCRIPTIONS,
				location.toURI());
		return existing;
	}

	@Override
	public PlatformResourceURI getProjectLocation(String name) {
		String eclipseName = ProjectDescriptionUtils.convertN4JSProjectNameToEclipseProjectName(name);
		IProject project = workspace.getProject(eclipseName);
		if (project.exists()) {
			return new PlatformResourceURI(project);
		}
		return null;
	}

	@Override
	public Collection<PlatformResourceURI> getAllProjectLocations() {
		Collection<PlatformResourceURI> prjLocations = new ArrayList<>();
		for (IProject prj : workspace.getProjects()) {
			prjLocations.add(new PlatformResourceURI(prj));
		}
		return prjLocations;
	}

	/** Loads the project description and notifies the listener */
	private class ProjectDescriptionLoaderAndNotifier implements CleartriggerSupplier<ProjectDescription> {
		final PlatformResourceURI location;

		public ProjectDescriptionLoaderAndNotifier(PlatformResourceURI location) {
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
				listener.onDescriptionLoaded(location.toURI());
			}
		}
	}

	@Override
	public PlatformResourceURI getLocation(ProjectReference projectReference) {
		String expectedProjectName = projectReference.getProjectName();
		if (expectedProjectName != null && expectedProjectName.length() > 0) {
			// the below call to workspace.getProject(name) will search the Eclipse IProject by name, using the
			// Eclipse project name (not the N4JS project name); thus, we have to convert from N4JS project name
			// to Eclipse project name, first (see ProjectDescriptionUtils#isProjectNameWithScope(String)):
			String expectedEclipseProjectName = ProjectDescriptionUtils
					.convertN4JSProjectNameToEclipseProjectName(expectedProjectName);
			IProject existingProject = workspace.getProject(expectedEclipseProjectName);
			if (existingProject.isAccessible()) {
				return new PlatformResourceURI(existingProject);
			}
		}
		return null;
	}

	@Override
	public Iterator<? extends PlatformResourceURI> getFolderIterator(
			PlatformResourceURI folderLocation) {
		return folderLocation.getAllChildren();
	}

	@Override
	public PlatformResourceURI findArtifactInFolder(PlatformResourceURI folderLocation, String folderRelativePath) {
		PlatformResourceURI result = folderLocation.appendPath(folderRelativePath);
		if (result.exists()) {
			return result;
		}
		return null;
		/*
		 * final String folderLocationString = folderLocation.toPlatformString(true); if (null != folderLocationString)
		 * { final IFolder folder = workspace.getFolder(new Path(folderLocationString)); final String subPathStr =
		 * folderRelativePath.replace(File.separator, "/"); final IPath subPath = new Path(subPathStr); final IFile file
		 * = folder != null ? folder.getFile(subPath) : null; if (file != null && file.exists()) { return
		 * folderLocation.appendSegments(subPathStr.split("/")); } } return null;
		 */
	}

	void setProjectDescriptionLoadListener(ProjectDescriptionLoadListener listener) {
		this.listener = listener;
	}

}
