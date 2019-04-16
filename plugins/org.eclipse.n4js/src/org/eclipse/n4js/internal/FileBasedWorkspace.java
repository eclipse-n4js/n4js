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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@SuppressWarnings("javadoc")
@Singleton
public class FileBasedWorkspace extends InternalN4JSWorkspace {

	private final ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	public FileBasedWorkspace(ProjectDescriptionLoader projectDescriptionLoader) {
		this.projectDescriptionLoader = projectDescriptionLoader;
	}

	private final Map<URI, LazyProjectDescriptionHandle> projectElementHandles = Maps.newConcurrentMap();

	/** container-prefix for file-based projects */
	public final static String N4FBPRJ = "n4fbprj:";

	/**
	 *
	 * @param location
	 *            project directory containing package.json directly
	 */
	public void registerProject(URI location) {
		if (location.lastSegment().isEmpty()) {
			throw new IllegalArgumentException("lastSegment may not be empty");
		}

		// URI location = URIUtils.normalize(unsafeLocation);
		if (!projectElementHandles.containsKey(location)) {
			LazyProjectDescriptionHandle lazyDescriptionHandle = createLazyDescriptionHandle(location);
			projectElementHandles.put(location, lazyDescriptionHandle);
		}
	}

	/**
	 * Remove all entries from this workspace.
	 */
	public void clear() {
		projectElementHandles.clear();
	}

	protected LazyProjectDescriptionHandle createLazyDescriptionHandle(URI location) {
		return new LazyProjectDescriptionHandle(location, projectDescriptionLoader);
	}

	@Override
	public URI findProjectWith(URI nestedLocation) {
		// URI key = URIUtils.normalize(nestedLocation.trimFragment());

		if (!nestedLocation.hasAuthority()) {
			// TODO: Replace by ProjectFinderUtil#addEmptyAuthority(URI)
			nestedLocation = URI.createHierarchicalURI(nestedLocation.scheme(), "", nestedLocation.device(),
					nestedLocation.segments(), nestedLocation.query(), nestedLocation.fragment());
		}
		URI key = nestedLocation.trimFragment();

		// determine longest registered project location, that is a prefix of 'key'
		while (key.segmentCount() > 0) {
			LazyProjectDescriptionHandle match = this.projectElementHandles.get(key);
			if (match != null) {
				return key;
			}
			key = key.trimSegments(1);
		}

		return null;
	}

	@Override
	public ProjectDescription getProjectDescription(URI location) {
		// URI location = URIUtils.normalize(unsafeLocation);
		LazyProjectDescriptionHandle handle = projectElementHandles.get(location);
		if (handle == null) {
			// check case without trailing path separator
			URI trimmedLocation = location.trimSegments((location.hasTrailingPathSeparator() ? 1 : 0));
			handle = projectElementHandles.get(trimmedLocation);
		}

		if (handle == null) {
			return null;
		}

		ProjectDescription description = handle.resolve();
		return description;
	}

	public Iterator<URI> getAllProjectLocationsIterator() {
		return projectElementHandles.values().stream().map(handle -> handle.getLocation()).iterator();
	}

	@Override
	public Collection<URI> getAllProjectLocations() {
		return projectElementHandles.values().stream().map(handle -> handle.getLocation()).collect(Collectors.toList());
	}

	@Override
	public URI getLocation(URI unsafeLocation, ProjectReference projectReference) {
		String projectName = projectReference.getProjectName();
		for (URI location : projectElementHandles.keySet()) {
			String candidateProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
			if (candidateProjectName.equals(projectName)) {
				LazyProjectDescriptionHandle lazyHandle = projectElementHandles.get(location);
				if (lazyHandle != null) {
					return lazyHandle.getLocation();
				}
			}
		}
		return null;
	}

	@Override
	public Iterator<URI> getFolderIterator(URI unsafeLocation) {
		URI folderLocation = URIUtils.normalize(unsafeLocation);
		java.net.URI create = java.net.URI.create(folderLocation.toString());

		File sourceContainerDirectory = null;
		try {
			sourceContainerDirectory = new File(create);
		} catch (IllegalArgumentException iae) {
			// TODO GH-793 handle broken project data passed to the workspace
			System.err.println(this.getClass().getName() + " invalid URI " + unsafeLocation);
			iae.printStackTrace();
		}
		if (sourceContainerDirectory != null && sourceContainerDirectory.isDirectory()) {
			AbstractTreeIterator<File> treeIterator = new AbstractTreeIterator<File>(sourceContainerDirectory,
					false) {
				@Override
				protected Iterator<? extends File> getChildren(Object root) {
					if (root instanceof File) {
						final File file = (File) root;
						if (file.isDirectory()) {
							// do not iterate over contents of nested node_modules folders
							if (file.getName().equals(N4JSGlobals.NODE_MODULES)) {
								return Collections.emptyIterator();
							}
							return Arrays.asList(((File) root).listFiles()).iterator();
						}
					}
					return Collections.emptyIterator();
				}
			};
			return Iterators.unmodifiableIterator(Iterators.transform(
					Iterators.filter(treeIterator, new Predicate<File>() {
						@Override
						public boolean apply(File input) {
							return !input.isDirectory();
						}
					}), new Function<File, URI>() {
						@Override
						public URI apply(File input) {
							return URI.createURI(input.toURI().toString());
						}
					}));
		}
		return Collections.emptyIterator();
	}

	@Override
	public URI findArtifactInFolder(URI unsafeLocation, String folderRelativePath) {
		URI folderLocation = URIUtils.normalize(unsafeLocation);
		final Path sourceContainerDirectory = Paths.get(java.net.URI.create(folderLocation.toString()));
		final Path subPath = Paths.get(folderRelativePath.replace("/", File.separator));
		final File file = sourceContainerDirectory.resolve(subPath).toFile().getAbsoluteFile();
		if (file.exists())
			return URI.createURI(file.toURI().toString());
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
