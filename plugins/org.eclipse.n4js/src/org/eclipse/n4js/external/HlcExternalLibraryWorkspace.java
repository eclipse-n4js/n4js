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
package org.eclipse.n4js.external;

import static java.util.Collections.emptyIterator;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * NOOP implementation of the external library workspace.
 */
@Singleton
public class HlcExternalLibraryWorkspace extends ExternalLibraryWorkspace {

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private FileBasedExternalPackageManager packageManager;

	@Override
	public RegisterResult registerProjects(IProgressMonitor monitor, Set<FileURI> toBeUpdated) {
		return new RegisterResult();
	}

	@Override
	public RegisterResult deregisterProjects(IProgressMonitor monitor, Set<FileURI> toBeDeleted) {
		return new RegisterResult();
	}

	@Override
	public RegisterResult deregisterAllProjects(IProgressMonitor monitor) {
		return new RegisterResult();
	}

	@Override
	public void scheduleWorkspaceProjects(IProgressMonitor monitor, Set<SafeURI<?>> toBeScheduled) {
		// NOOP
	}

	@Override
	public List<Pair<FileURI, ProjectDescription>> computeProjectsIncludingUnnecessary() {
		return emptyList();
	}

	@Override
	public Collection<N4JSExternalProject> getProjects() {
		return emptyList();
	}

	@Override
	public boolean isNecessary(SafeURI<?> location) {
		return true;
	}

	@Override
	public Map<N4JSProjectName, VersionNumber> getProjectNameVersionMap() {
		return emptyMap();
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(FileURI rootLocation) {
		return emptyList();
	}

	@Override
	public Collection<N4JSExternalProject> getProjectsIn(Collection<FileURI> rootLocations) {
		return emptyList();
	}

	@Override
	public N4JSExternalProject getProject(N4JSProjectName projectName) {
		return null;
	}

	@Override
	public N4JSExternalProject getProject(FileURI projectName) {
		return null;
	}

	@Override
	public void updateState() {
		// NOOP
	}

	@Override
	public ProjectDescription getProjectDescription(FileURI location) {
		ProjectDescription projectDescription = packageManager.loadProjectDescriptionFromProjectRoot(location);
		return projectDescription;
	}

	@Override
	public FileURI getLocation(ProjectReference reference) {
		return null;
	}

	@Override
	public Iterator<FileURI> getFolderIterator(FileURI folderLocation) {
		return emptyIterator();
	}

	@Override
	public FileURI findArtifactInFolder(FileURI folderLocation,
			final String folderRelativePath) {
		return null;
	}

	@Override
	public FileURI findProjectWith(FileURI nestedLocation) {
		return null;
	}

	@Override
	public List<Pair<FileURI, ProjectDescription>> getProjectsIncludingUnnecessary() {
		Iterable<FileURI> projectLocations = externalLibraryPreferenceStore.getNodeModulesLocations();
		List<Pair<FileURI, ProjectDescription>> projects = new LinkedList<>();
		for (FileURI npmLibraryLocation : projectLocations) {
			ProjectDescription pd = getProjectDescription(npmLibraryLocation);
			if (pd != null) {
				projects.add(Tuples.pair(npmLibraryLocation, pd));
			}
		}

		return projects;
	}

	@Override
	public List<N4JSExternalProject> getProjectsForName(N4JSProjectName projectName) {
		return emptyList();
	}

	@Override
	public Collection<FileURI> getAllProjectLocations() {
		return emptyList();
	}

}
