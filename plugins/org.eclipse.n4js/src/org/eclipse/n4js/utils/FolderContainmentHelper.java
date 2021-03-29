/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.eclipse.xtext.util.UriExtensions;

/**
 * A helper class to compute whether two folders (source folders and output folder specifically) are contained within
 * each other.
 */
public class FolderContainmentHelper {

	/**
	 * Returns {@code true} iff the given URI refers to a resource that is located in the declared output folder of the
	 * corresponding {@link N4JSProjectConfigSnapshot}.
	 *
	 * Returns {@code false} if the given URI is not contained by any known {@link N4JSProjectConfigSnapshot}.
	 */
	public boolean isContainedInOutputFolder(N4JSWorkspaceConfigSnapshot ws, URI uri) {
		final N4JSProjectConfigSnapshot project = ws.findProjectByNestedLocation(uri);
		if (project != null) {
			final SafeURI<?> absoluteOutputLocation = getOutputURI(project);
			if (absoluteOutputLocation != null) {
				FileURI fileURI = new FileURI(new UriExtensions().withEmptyAuthority(uri));
				if (isContained(fileURI, absoluteOutputLocation)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the given URI refers to a resource that is located in one of the
	 * {@link N4JSSourceFolderSnapshot}s of an {@link N4JSProjectConfigSnapshot}.
	 *
	 * Returns {@code false} if the given URI is not contained by any known {@link N4JSProjectConfigSnapshot}.
	 */
	public boolean isContainedInSourceContainer(N4JSWorkspaceConfigSnapshot ws, SafeURI<?> location) {
		final N4JSSourceFolderSnapshot container = ws.findSourceFolderContaining(location.toURI());
		if (container != null) {
			return true;
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the output folder of the given {@code project} is contained by any known
	 * {@link N4JSSourceFolderSnapshot}.
	 *
	 * Returns {@code false} if the given project declares no output path.
	 */
	public boolean isOutputContainedInSourceContainer(N4JSWorkspaceConfigSnapshot ws,
			N4JSProjectConfigSnapshot project) {
		final SafeURI<?> absoluteOutputLocation = getOutputURI(project);
		if (absoluteOutputLocation != null) {
			return isContainedInSourceContainer(ws, absoluteOutputLocation);
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the given {@code uri} is contained by {@code container}. Returns {@code false}
	 * otherwise.
	 */
	public boolean isContained(SafeURI<?> uri, SafeURI<?> container) {
		final Path path = uri.toFileSystemPath();
		final Path containerPath = container.toFileSystemPath();

		return path.startsWith(containerPath);
	}

	/**
	 * Computes the absolute URI of the output folder of the given {@code project}.
	 *
	 * Returns {@code null} if the given {@code project} declares no output folder.
	 *
	 * @See {@link N4JSProjectConfigSnapshot#getOutputPath()}
	 */
	private static SafeURI<?> getOutputURI(N4JSProjectConfigSnapshot project) {
		final String outputPathName = project.getOutputPath();
		if (outputPathName != null) {
			return project.getPathAsFileURI().resolve(outputPathName);
		}
		return null;
	}

}
