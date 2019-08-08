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
package org.eclipse.n4js.validation.helper;

import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * A helper class to compute whether two folders (source folders and output folder specifically) are contained within
 * each other.
 */
public class FolderContainmentHelper {
	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Returns {@code true} iff the given URI refers to a resource that is located in the declared output folder of the
	 * corresponding {@link IN4JSProject}.
	 *
	 * Returns {@code false} if the given URI is not contained by any known {@link IN4JSProject}.
	 */
	public boolean isContainedInOutputFolder(URI uri) {
		final IN4JSProject project = n4jsCore.findProject(uri).orNull();
		if (project != null) {
			final SafeURI<?> absoluteOutputLocation = getOutputURI(project);
			if (absoluteOutputLocation != null) {
				if (isContained(n4jsCore.toProjectLocation(uri), absoluteOutputLocation)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the given URI refers to a resource that is located in one of the
	 * {@link IN4JSSourceContainer}ss of an {@link IN4JSProject}.
	 *
	 * Returns {@code false} if the given URI is not contained by any known {@link IN4JSProject}.
	 */
	public boolean isContainedInSourceContainer(SafeURI<?> location) {
		final Optional<? extends IN4JSSourceContainer> container = n4jsCore.findN4JSSourceContainer(location.toURI());
		if (container.isPresent()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the output folder of the given {@code project} is contained by any known
	 * {@link IN4JSSourceContainer}.
	 *
	 * Returns {@code false} if the given project declares no output path.
	 */
	public boolean isOutputContainedInSourceContainer(IN4JSProject project) {
		final SafeURI<?> absoluteOutputLocation = getOutputURI(project);
		if (absoluteOutputLocation != null) {
			return isContainedInSourceContainer(absoluteOutputLocation);
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
	 * @See {@link IN4JSProject#getOutputPath()}
	 */
	private static SafeURI<?> getOutputURI(IN4JSProject project) {
		final String outputPathName = project.getOutputPath();
		if (outputPathName != null) {
			return project.getLocation().resolve(outputPathName);
		}
		return null;
	}

}
