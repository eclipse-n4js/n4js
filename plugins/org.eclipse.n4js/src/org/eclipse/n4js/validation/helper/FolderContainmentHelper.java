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

import java.io.File;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

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
			final URI absoluteOutputLocation = getOutputURI(project);
			if (absoluteOutputLocation != null) {
				if (isContained(uri, absoluteOutputLocation)) {
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
	public boolean isContainedInSourceContainer(URI uri) {
		final Optional<? extends IN4JSSourceContainer> container = n4jsCore.findN4JSSourceContainer(uri);
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
		final URI absoluteOutputLocation = getOutputURI(project);
		if (absoluteOutputLocation != null) {
			return isContainedInSourceContainer(absoluteOutputLocation);
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the given {@code uri} is contained by {@code container}. Returns {@code false}
	 * otherwise.
	 */
	public boolean isContained(URI uri, URI container) {
		final Path path = new File(uri.toString()).toPath().normalize();
		final Path containerPath = new File(container.toString()).toPath().normalize();

		return path.startsWith(containerPath);
	}

	/**
	 * Computes the absolute URI of the output folder of the given {@code project}.
	 *
	 * Returns {@code null} if the given {@code project} declares no output folder.
	 *
	 * @See {@link IN4JSProject#getOutputPath()}
	 */
	private static URI getOutputURI(IN4JSProject project) {
		final String outputPathName = project.getOutputPath();
		if (outputPathName != null) {
			final URI projectLocation = appendTrailingSlash(project.getLocation());

			final URI relativeOutputURI = URI.createURI(outputPathName);
			final URI absoluteOutputLocation = relativeOutputURI.resolve(projectLocation);

			return absoluteOutputLocation;
		}
		return null;
	}

	/**
	 * Returns an URI which represent {@code uri} but with an additional empty segment (trailing slash) at the end.
	 *
	 * @See {@link URI#isPrefix()}
	 */
	private static URI appendTrailingSlash(URI uri) {
		if (!uri.isPrefix()) {
			return uri.appendSegment("");
		} else {
			return uri;
		}
	}
}
