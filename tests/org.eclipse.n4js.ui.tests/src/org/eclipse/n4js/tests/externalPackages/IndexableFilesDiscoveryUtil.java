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
package org.eclipse.n4js.tests.externalPackages;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.n4js.N4JSGlobals;

/**
 */
public class IndexableFilesDiscoveryUtil {
	private static final Logger LOGGER = Logger.getLogger(IndexableFilesDiscoveryUtil.class);

	private static List<String> INDEXABLE_FILTERS = Arrays.asList(N4JSGlobals.N4JS_FILE_EXTENSION,
			N4JSGlobals.N4JSD_FILE_EXTENSION);

	private static List<String> INDEXABLE_FILENAMES = Arrays.asList(N4JSGlobals.PACKAGE_JSON);

	/**
	 * Scans given location for files that may end up in XtextIndex when given location is processed by the builder.
	 * This is naive filtering based on {@link IndexableFilesDiscoveryUtil#INDEXABLE_FILTERS file extensions}. Symlinks
	 * are not followed.
	 *
	 * @param location
	 *            to scan
	 * @return collection of indexable locations
	 * @throws IOException
	 *             if scanning of the locations is not possible.
	 */
	public static Collection<String> collectIndexableFiles(Path location) throws IOException {
		Set<String> result = new HashSet<>();
		File dirToBeScanned = location.toFile();
		if (!dirToBeScanned.exists()) {
			LOGGER.warn("provided location does not exists: " + location.toAbsolutePath());
			return result;
		}

		if (dirToBeScanned.isFile()) {
			LOGGER.warn("provided location is not a directory " + dirToBeScanned.getAbsolutePath());
			return result;
		}

		Files.walkFileTree(location, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {

				if (filter(file)) {
					result.add(location.relativize(file).toString());
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file,
					IOException exc) throws IOException {

				LOGGER.error("failed to scan path :" + file, exc);
				return FileVisitResult.CONTINUE;

			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir,
					IOException exc) {

				if (exc != null) {
					LOGGER.error("failed to scan :" + dir, exc);
				}
				return FileVisitResult.CONTINUE;
			}

			private boolean filter(Path p) {
				if (INDEXABLE_FILENAMES.contains(p.getName(p.getNameCount() - 1).toString())) {
					return true;
				}

				final String abs = p.toAbsolutePath().toString();
				int index = abs.lastIndexOf(".");
				if (index == -1)
					return false;

				final String end = abs.substring(index + 1);
				return INDEXABLE_FILTERS.contains(end.toLowerCase());
			}
		});

		return result;
	}

	/**
	 * Scans workspace for files that may end up in XtextIndex when given location is processed by the builder. This is
	 * naive filtering based on {@link IndexableFilesDiscoveryUtil#INDEXABLE_FILTERS file extensions}. Symlinks are not
	 * followed.
	 *
	 * @param workspace
	 *            to scan
	 * @return collection of indexable locations
	 * @throws CoreException
	 *             if scanning of the workspace is not possible.
	 */
	public static Collection<String> collectIndexableFiles(IWorkspace workspace) throws CoreException {
		Set<String> result = new HashSet<>();
		workspace.getRoot().accept(new IResourceVisitor() {

			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource.getType() == IResource.FILE) {
					IFile file = (IFile) resource;
					boolean isIndexableFileExtension = false;
					String extension = file.getFileExtension();
					if (extension != null) {
						isIndexableFileExtension = INDEXABLE_FILTERS.contains(extension.toLowerCase());
					}
					boolean isIndexableFilename = INDEXABLE_FILENAMES.contains(file.getName());

					if (isIndexableFileExtension || isIndexableFilename) {
						result.add(file.getFullPath().toString());
					}
				}
				return true;
			}
		});
		return result;
	}
}
