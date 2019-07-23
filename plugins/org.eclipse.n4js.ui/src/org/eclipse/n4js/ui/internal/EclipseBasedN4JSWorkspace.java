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
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
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
		if (!uri.isPlatformResource()) {
			return null;
		}
		return new PlatformResourceURI(uri);
	}

	@Override
	public PlatformResourceURI findProjectWith(PlatformResourceURI nestedLocation) {
		return new PlatformResourceURI(URI.createPlatformResourceURI(nestedLocation.toURI().segment(1), true));
	}

	@Override
	public ProjectDescription getProjectDescription(PlatformResourceURI location) {
		ProjectDescriptionLoaderAndNotifier supplier = new ProjectDescriptionLoaderAndNotifier(location);
		ProjectDescription existing = cache.get(supplier, MultiCleartriggerCache.CACHE_KEY_PROJECT_DESCRIPTIONS,
				location.toURI());
		return existing;
	}

	@Override
	public PlatformResourceURI getProjectLocation(N4JSProjectName name) {
		IProject project = workspace.getProject(name.toEclipseProjectName().getRawName());
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
	}

	void setProjectDescriptionLoadListener(ProjectDescriptionLoadListener listener) {
		this.listener = listener;
	}

}
