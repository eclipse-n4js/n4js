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

import static com.google.common.base.Optional.fromNullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.URIUtils;

import com.google.common.base.Function;
import com.google.common.base.Optional;
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

	private final ProjectDescriptionHelper projectDescriptionHelper;

	private final ClasspathPackageManager packageManager;

	@Inject
	public FileBasedWorkspace(ClasspathPackageManager packageManager,
			ProjectDescriptionHelper projectDescriptionHelper) {
		this.packageManager = packageManager;
		this.projectDescriptionHelper = projectDescriptionHelper;
	}

	private final Map<URI, LazyProjectDescriptionHandle> projectElementHandles = Maps.newConcurrentMap();

	/** container-prefix for file-based projects */
	public final static String N4FBPRJ = "n4fbprj:";

	/**
	 *
	 * @param location
	 *            project directory containing manifest.n4mf directly
	 */
	public void registerProject(URI unsafeLocation) {
		if (unsafeLocation.lastSegment().isEmpty()) {
			throw new IllegalArgumentException("lastSegment may not be empty");
		}

		URI location = URIUtils.normalize(unsafeLocation);
		if (!projectElementHandles.containsKey(location)) {
			LazyProjectDescriptionHandle lazyDescriptionHandle = createLazyDescriptionHandle(location, false);
			projectElementHandles.put(location, lazyDescriptionHandle);
			for (String libraryPath : lazyDescriptionHandle.resolve().getLibraryPaths()) {
				URI libraryFolder = location.appendSegment(libraryPath);
				File lib = new File(java.net.URI.create(libraryFolder.toString()));
				if (lib.isDirectory()) {
					for (File archive : lib.listFiles()) {
						if (archive.getName().endsWith(IN4JSArchive.NFAR_FILE_EXTENSION_WITH_DOT)) {
							URI archiveLocation = URI.createURI(archive.toURI().toString());
							projectElementHandles.put(archiveLocation,
									createLazyDescriptionHandle(archiveLocation, true));
						}
					}
				}
			}
		}
	}

	protected LazyProjectDescriptionHandle createLazyDescriptionHandle(URI location, boolean archive) {
		return new LazyProjectDescriptionHandle(location, archive, projectDescriptionHelper);
	}

	@Override
	public URI findProjectWith(URI unsafeLocation) {
		URI nestedLocation = URIUtils.normalize(unsafeLocation);
		int maxSegments = nestedLocation.segmentCount();
		OUTER: for (URI known : projectElementHandles.keySet()) {
			if (known.segmentCount() <= maxSegments) {
				final URI projectUri = tryFindProjectRecursivelyByManifest(nestedLocation, fromNullable(known));
				if (null != projectUri) {
					return projectUri;
				}
				for (int i = 0; i < known.segmentCount(); i++) {
					if (!known.segment(i).equals(nestedLocation.segment(i))) {
						continue OUTER;
					}
				}
				return known;
			}
		}
		return tryFindProjectRecursivelyByManifest(nestedLocation, Optional.absent());
	}

	private URI tryFindProjectRecursivelyByManifest(URI location, Optional<URI> stopUri) {
		URI nestedLocation = location;
		int segmentCount = 0;
		if (nestedLocation.isFile()) { // Here, unlike java.io.File, #isFile can mean directory as well.
			File directory = new File(nestedLocation.toFileString());
			while (directory != null) {
				if (stopUri.isPresent() && stopUri.get().equals(nestedLocation)) {
					break;
				}
				if (directory.isDirectory()) {
					if (new File(directory, IN4JSProject.N4MF_MANIFEST).exists()) {
						URI projectLocation = URI.createFileURI(directory.getAbsolutePath());
						registerProject(projectLocation);
						return projectLocation;
					}
					if (new File(directory, IN4JSProject.PACKAGE_JSON).exists() ||
							new File(directory, IN4JSProject.PACKAGE_JSON + ".xt").exists()) {
						URI projectLocation = URI.createFileURI(directory.getAbsolutePath());
						registerProject(projectLocation);
						return projectLocation;
					}
				}
				nestedLocation = nestedLocation.trimSegments(segmentCount++);
				directory = directory.getParentFile();
			}
		}
		return null;
	}

	@Override
	public ProjectDescription getProjectDescription(URI unsafeLocation) {
		URI location = URIUtils.normalize(unsafeLocation);
		LazyProjectDescriptionHandle handle = projectElementHandles.get(location);
		if (handle == null) {
			// check case without trailing path separator
			if (location.hasTrailingPathSeparator()) {
				handle = projectElementHandles.get(location.trimSegments(1));
			}
		}

		if (handle == null) {
			return null;
		}

		ProjectDescription description = handle.resolve();
		return description;
	}

	public Iterator<URI> getAllProjectsLocations() {
		return projectElementHandles.values().stream().map(handle -> handle.getLocation()).iterator();
	}

	@Override
	public URI getLocation(URI unsafeLocation, ProjectReference projectReference,
			N4JSSourceContainerType expectedN4JSSourceContainerType) {
		URI projectURI = URIUtils.normalize(unsafeLocation);
		String projectId = projectReference.getProjectId();
		if (expectedN4JSSourceContainerType == N4JSSourceContainerType.ARCHIVE) {
			LazyProjectDescriptionHandle baseHandle = projectElementHandles.get(projectURI);
			if (baseHandle != null && !baseHandle.isArchive()) {
				String archiveFileName = projectId + IN4JSArchive.NFAR_FILE_EXTENSION_WITH_DOT;
				for (String libraryPath : baseHandle.resolve().getLibraryPaths()) {
					URI archiveURI = projectURI.appendSegments(new String[] { libraryPath, archiveFileName });
					if (projectElementHandles.containsKey(archiveURI)) {
						return archiveURI;
					}
				}
			} else {
				String archiveFileName = projectId + IN4JSArchive.NFAR_FILE_EXTENSION_WITH_DOT;
				for (URI location : projectElementHandles.keySet()) {
					if (location.lastSegment().equals(archiveFileName)) {
						LazyProjectDescriptionHandle lazyHandle = projectElementHandles.get(location);
						if (lazyHandle != null) {
							return lazyHandle.getLocation();
						}
					}
				}
			}
			URI location = packageManager.getLocation(projectId);
			if (location != null) {
				if (projectElementHandles.containsKey(location)) {
					return location;
				}
				projectElementHandles.put(location, createLazyDescriptionHandle(location, true));
				return location;
			}
		} else {
			for (URI location : projectElementHandles.keySet()) {
				if (location.lastSegment().equals(projectId)) {
					LazyProjectDescriptionHandle lazyHandle = projectElementHandles.get(location);
					if (lazyHandle != null) {
						return lazyHandle.getLocation();
					}
				}
			}
		}
		return null;
	}

	@Override
	public Iterator<URI> getArchiveIterator(final URI unsafeLocation, String archiveRelativeLocation) {
		URI archiveLocation = URIUtils.normalize(unsafeLocation);
		File archiveFile = new File(java.net.URI.create(archiveLocation.toString()));
		ZipInputStream stream = null;
		try {
			stream = new ZipInputStream(new BufferedInputStream(new FileInputStream(archiveFile)));
			Iterator<ZipEntry> entries = getArchiveIterator(stream, archiveRelativeLocation);
			return toArchiveURIs(archiveLocation, entries);
		} catch (FileNotFoundException e) {
			return Collections.emptyIterator();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
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
					if (root instanceof File && ((File) root).isDirectory()) {
						return Arrays.asList(((File) root).listFiles()).iterator();
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
