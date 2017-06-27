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

import static org.eclipse.n4js.external.N4JSExternalProject.BUILDER_ID;
import static org.eclipse.n4js.external.N4JSExternalProject.NATURE_ID;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Optional;
import com.google.common.cache.CacheLoader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Cache loader for creating a {@link ExternalProject external project} instance for the project root location. This
 * loader ensures that the {@link ProjectDescription project description} (N4JS manifest) exists for the project.
 */
@Singleton
public class ExternalProjectCacheLoader
		extends CacheLoader<URI, Optional<Pair<ExternalProject, ProjectDescription>>> {

	@Inject
	private FileBasedExternalPackageManager packageManager;

	@Override
	public Optional<Pair<ExternalProject, ProjectDescription>> load(final URI rootLocation) throws Exception {

		if (null != rootLocation && rootLocation.isFile()) {
			final File projectRoot = new File(rootLocation.toFileString());
			if (projectRoot.exists() && projectRoot.isDirectory()) {
				final URI manifestLocation = rootLocation.appendSegment(IN4JSProject.N4MF_MANIFEST);
				final ProjectDescription projectDescription = packageManager.loadManifest(manifestLocation);
				if (null != projectDescription) {
					final ExternalProject project = new ExternalProject(projectRoot, NATURE_ID, BUILDER_ID);
					return Optional.of(Tuples.create(project, projectDescription));
				}
			}
		}

		return Optional.absent();
	}

}
