/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper that based on provided {@link URI}s resolved the {@link IN4JSProject}, or related package / file name.
 */
@Singleton
public class ProjectResolveHelper {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolveProject(URI)}, for which {@link URI} is resolved based
	 * on the provided {@link Resource}
	 *
	 * @see #resolveProject(URI)
	 */
	public IN4JSProject resolveProject(Resource resource) {
		return resolveProject(resource.getURI());
	}

	/**
	 * Resolves project from provided URI.
	 */
	public IN4JSProject resolveProject(URI n4jsSourceURI) {
		final Optional<? extends IN4JSProject> optionalProject = n4jsCore.findProject(n4jsSourceURI);
		if (!optionalProject.isPresent()) {
			throw new RuntimeException(
					"Cannot handle resource without containing project. Resource URI was: " + n4jsSourceURI + ".");
		}
		return optionalProject.get();
	}

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolvePackageAndFileName(URI)}, for which {@link URI} is
	 * resolved based on the provided {@link Resource}
	 *
	 * @see #resolvePackageAndFileName(URI)
	 */
	public String resolvePackageAndFileName(Resource resource) {
		return resolvePackageAndFileName(resource.getURI());
	}

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolvePackageAndFileName(URI, IN4JSProject)}, for which
	 * {@link IN4JSProject} is resolved based on the provided {@link URI}
	 *
	 * @see #resolvePackageAndFileName(URI, IN4JSProject)
	 */
	public String resolvePackageAndFileName(URI uri) {
		final IN4JSProject project = n4jsCore.findProject(uri).orNull();
		return resolvePackageAndFileName(uri, project);
	}

	/**
	 * Resolves package and filename from provided {@link URI} against {@link IN4JSProject}. Provided project must be
	 * {@link IN4JSProject#exists()}. In returned string file extension of the actual file is trimmed.
	 */
	public String resolvePackageAndFileName(URI uri, IN4JSProject project) {
		final String msg = "Cannot locate source container for module " + uri + ".";
		if (null == project) {
			throw new RuntimeException(msg + " Provided project was null.");
		}
		if (!project.exists()) {
			throw new RuntimeException(
					msg + " Does project '" + project.getProjectName() + "' exists and opened in the workspace?");
		}

		final IN4JSSourceContainer sourceContainer = project.findSourceContainerWith(uri);
		if (sourceContainer == null) {
			throw new RuntimeException(msg);
		}

		return uri.deresolve(sourceContainer.getLocation().withTrailingPathDelimiter().toURI())
				.trimFileExtension()
				.toString();
	}
}
