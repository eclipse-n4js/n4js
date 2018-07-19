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
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ArchiveURIUtil;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;

/**
 * Internal representation of the known projects. At runtime, it's implemented based on registered project locations, in
 * Eclipse the workspace is used as the backing store.
 *
 * It's not meant to be used by clients different from the {@link N4JSModel} since the contract for URI schemes etc is
 * implemented there and shared with the used implementation of the {@link InternalN4JSWorkspace}.
 */
public abstract class InternalN4JSWorkspace {

	/**
	 * Obtains the project description for the project at the given location.
	 */
	public abstract ProjectDescription getProjectDescription(URI location);

	/**
	 * Find the location of the target project for the given project reference, e.g. a project dependency, depending on
	 * whether it resolves to an archive or not. Returns null if nothing can be found.
	 *
	 * @param projectURI
	 *            the uri of the project that declares the reference
	 * @param reference
	 *            the project reference
	 */
	public abstract URI getLocation(URI projectURI, ProjectReference reference);

	/**
	 * Iterates the contents of an archive.
	 */
	public abstract Iterator<URI> getArchiveIterator(URI archiveLocation, String archiveRelativeLocation);

	/**
	 * Iterates all entries in the given archive data that are nested under the relative location.
	 *
	 * @param archive
	 *            the input stream with the zip entries. Will not be closed by this method, but has to be closed by the
	 *            caller.
	 */
	protected Iterator<ZipEntry> getArchiveIterator(final ZipInputStream archive,
			String archiveRelativeLocation) {
		final String relativeLocation = archiveRelativeLocation + '/';
		final Iterator<ZipEntry> iterator = new AbstractIterator<ZipEntry>() {

			@Override
			protected ZipEntry computeNext() {
				try {
					ZipEntry candidate = archive.getNextEntry();
					while (candidate != null) {
						if (!candidate.isDirectory()) {
							String name = candidate.getName();
							if (name.startsWith(relativeLocation)) {
								return candidate;
							}
						}
						candidate = archive.getNextEntry();
					}
				} catch (IOException e) {
					// ignore
				}
				return endOfData();
			}
		};
		return ImmutableList.copyOf(iterator).iterator();
	}

	/**
	 * Convert the entries to URIs.
	 */
	protected Iterator<URI> toArchiveURIs(final URI archiveURI, final Iterator<ZipEntry> base) {
		return new AbstractIterator<URI>() {
			@Override
			protected URI computeNext() {
				if (base.hasNext()) {
					ZipEntry next = base.next();
					return ArchiveURIUtil.createURI(archiveURI, next);
				}
				return endOfData();
			}
		};
	}

	/**
	 * Iterates the contents of a folder.
	 */
	public abstract Iterator<URI> getFolderIterator(URI folderLocation);

	/**
	 * Checks if the given folder contains a resource of any type at the given folder-relative path and returns the URI
	 * for it or <code>null</code> if not found. If 'folderRelativePath' points to a folder instead of a file, this
	 * method will return <code>null</code> as well. Both '/' and the system-dependent name separator (cf.
	 * {@link File#separator}) can be used in 'folderRelativePath'.
	 */
	public abstract URI findArtifactInFolder(URI folderLocation, String folderRelativePath);

	/**
	 * Returns the location of the project that contains the given nested location.
	 */
	public abstract URI findProjectWith(URI nestedLocation);
}
