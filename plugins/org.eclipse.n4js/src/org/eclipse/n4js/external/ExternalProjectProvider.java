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

import static org.eclipse.n4js.projectModel.IN4JSProject.N4MF_MANIFEST;

import java.io.File;
import java.net.URI;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.utils.resources.ExternalProject;

/**
 * Service for providing {@link ExternalProject external project} instances.
 */
@Singleton
public class ExternalProjectProvider {

	private static final Logger LOGGER = Logger.getLogger(ExternalProjectProvider.class);

	@Inject
	private IN4JSCore core;

	@Inject
	private FileBasedExternalPackageManager packageManager;

	/**
	 * Optionally returns with an {@link N4JSExternalProject N4JS external project} for the given project root location.
	 * If the project cannot be resolved, the this method will return with an {@link Optional#absent() absent} instance.
	 *
	 * @param projectRootLocation
	 *            the project root location on the file system. Must not be {@code null}.
	 * @return the N4JS external project instance or {@link Optional#absent() missing} if cannot be located.
	 */
	/* default */Optional<N4JSExternalProject> get(final URI projectRootLocation) {
		final File projectRoot = new File(projectRootLocation);
		if (projectRoot.exists() && projectRoot.isDirectory()) {
			final File manifest = new File(projectRoot, N4MF_MANIFEST);
			if (manifest.exists() && manifest.isFile()) {
				final ProjectDescription projectDescription = tryLoadProjectDescription(manifest);
				if (null != projectDescription) {
					final IN4JSProject externalPackage = findExternalPackage(toFileUri(projectRootLocation));
					if (null != externalPackage) {
						return Optional.of(new N4JSExternalProject(projectRoot, externalPackage));
					}
				}
			}
		}
		return Optional.absent();
	}

	private IN4JSProject findExternalPackage(final org.eclipse.emf.common.util.URI nestedLocation) {
		return core.findProject(nestedLocation).orNull();
	}

	private ProjectDescription tryLoadProjectDescription(final File manifest) {
		try {
			return packageManager.loadManifest(toFileUri(manifest.toURI()));
		} catch (final Exception e) {
			LOGGER.error("Error while trying to load project description: " + manifest, e);
			return null;
		}
	}

	/** Converts the {@link File} resource into an EMF file URI. */
	private org.eclipse.emf.common.util.URI toFileUri(final URI resourceURI) {
		return org.eclipse.emf.common.util.URI.createFileURI(new File(resourceURI).getAbsolutePath());
	}

}
