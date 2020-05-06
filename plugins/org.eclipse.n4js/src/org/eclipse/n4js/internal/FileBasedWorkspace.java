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
package org.eclipse.n4js.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@SuppressWarnings("javadoc")
@Singleton
public class FileBasedWorkspace extends InternalN4JSWorkspace<FileURI> {

	/** container-prefix for file-based projects */
	public final static String N4FBPRJ = "n4fbprj:";

	private final Map<FileURI, LazyProjectDescriptionHandle> projectElementHandles = Maps.newConcurrentMap();
	private final Map<N4JSProjectName, FileURI> nameToLocation = Maps.newConcurrentMap();

	private final ProjectDescriptionLoader projectDescriptionLoader;

	private final UriExtensions uriExtensions;

	@Inject
	public FileBasedWorkspace(ProjectDescriptionLoader projectDescriptionLoader, UriExtensions uriExtensions) {
		this.projectDescriptionLoader = projectDescriptionLoader;
		this.uriExtensions = uriExtensions;
	}

	@Override
	public FileURI fromURI(URI unsafe) {
		return safeFromURI(unsafe);
	}

	private FileURI safeFromURI(URI uri) {
		return new FileURI(uriExtensions.withEmptyAuthority(uri));
	}

	/**
	 *
	 * @param location
	 *            project directory containing package.json directly
	 */
	public void registerProject(FileURI location) {
		// URI location = URIUtils.normalize(unsafeLocation);
		if (!isRegistered(location)) {
			LazyProjectDescriptionHandle lazyDescriptionHandle = createLazyDescriptionHandle(location);
			projectElementHandles.put(location, lazyDescriptionHandle);
		}
		nameToLocation.putIfAbsent(location.getProjectName(), location);
	}

	public void registerProject(FileURI location, ProjectDescription resolvedDescription) {
		if (!isRegistered(location)) {
			LazyProjectDescriptionHandle lazyDescriptionHandle = createDescriptionHandle(location, resolvedDescription);
			projectElementHandles.put(location, lazyDescriptionHandle);
		}
		nameToLocation.putIfAbsent(location.getProjectName(), location);
	}

	@Override
	public FileURI getProjectLocation(N4JSProjectName name) {
		return nameToLocation.get(name);
	}

	/** Remove all entries from this workspace. */
	public void clear() {
		projectElementHandles.clear();
	}

	/** @return true iff the project at the given location was registered before */
	public boolean isRegistered(FileURI location) {
		return projectElementHandles.containsKey(location);
	}

	/** Deregisters the project at the given location */
	public void deregister(FileURI location) {
		projectElementHandles.remove(location);
		String prjName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
		nameToLocation.remove(new N4JSProjectName(prjName));
	}

	/** Deregisters all projects */
	public void deregisterAll() {
		projectElementHandles.clear();
		nameToLocation.clear();
	}

	protected LazyProjectDescriptionHandle createLazyDescriptionHandle(FileURI location) {
		return new LazyProjectDescriptionHandle(location, projectDescriptionLoader);
	}

	protected LazyProjectDescriptionHandle createDescriptionHandle(FileURI location, ProjectDescription description) {
		return new LazyProjectDescriptionHandle(location, description);
	}

	@Override
	public FileURI findProjectWith(FileURI nestedLocation) {
		FileURI key = nestedLocation.trimFragment();

		// determine longest registered project location, that is a prefix of 'key'
		do {
			LazyProjectDescriptionHandle match = this.projectElementHandles.get(key);
			if (match != null) {
				return key;
			}
			key = key.getParent();
		} while (key != null);

		return null;
	}

	@Override
	public ProjectDescription getProjectDescription(FileURI location) {
		// URI location = URIUtils.normalize(unsafeLocation);
		LazyProjectDescriptionHandle handle = projectElementHandles.get(location);
		if (handle == null) {
			return null;
		}

		ProjectDescription description = handle.resolve();
		return description;
	}

	@Override
	public void invalidateProject(IN4JSProject project) {
		LazyProjectDescriptionHandle handle = projectElementHandles.get(project.getProjectDescriptionLocation());
		handle.invalidate();
	}

	public Iterator<FileURI> getAllProjectLocationsIterator() {
		return projectElementHandles.values().stream().map(handle -> handle.getLocation()).iterator();
	}

	@Override
	public Collection<FileURI> getAllProjectLocations() {
		return projectElementHandles.values().stream().map(handle -> handle.getLocation()).collect(Collectors.toList());
	}

	@Override
	public FileURI getLocation(ProjectReference projectReference) {
		String projectName = projectReference.getProjectName();
		for (FileURI siblingProject : projectElementHandles.keySet()) {
			String candidateProjectName = siblingProject.getProjectName().getRawName();
			if (candidateProjectName.equals(projectName)) {
				LazyProjectDescriptionHandle lazyHandle = projectElementHandles.get(siblingProject);
				if (lazyHandle != null) {
					return lazyHandle.getLocation();
				}
			}
		}
		return null;
	}

	@Override
	public Iterator<? extends FileURI> getFolderIterator(FileURI folderLocation) {
		return Iterators.filter(folderLocation.getAllChildren(), loc -> !loc.isDirectory());
	}

	@Override
	public FileURI findArtifactInFolder(FileURI folder, String relativePath) {
		FileURI result = folder.appendPath(relativePath);
		if (result != null && result.exists()) {
			return result;
		}
		return null;
	}

	/**
	 * Convert container-handle to URI
	 *
	 * @see #handleFrom(URI)
	 * @param handle
	 *            "n4fbprj:"-prefixed uri
	 * @return the uri-part of the handle as uri
	 */
	public static URI uriFrom(String handle) {
		if (handle.startsWith(N4FBPRJ)) {
			return URI.createURI(handle.substring(N4FBPRJ.length()));
		}
		return null;
	}

	/**
	 * Create container-handle form uri.
	 *
	 * @see #uriFrom(String)
	 * @param uri
	 *            URI denoting a project
	 * @return string-representation of uri prefixed with "n4fbprj:"
	 */
	public static String handleFrom(URI uri) {
		return N4FBPRJ + uri.toString();
	}

}
