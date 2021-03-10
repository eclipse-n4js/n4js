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

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.projectModel.IN4JSCoreNEW;
import org.eclipse.n4js.projectModel.IN4JSProject;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper that based on provided {@link URI}s resolved the {@link IN4JSProject}, or related package / file name.
 */
@Singleton
public class ProjectResolveHelper {

	@Inject
	private IN4JSCoreNEW n4jsCore;

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolveProject(Notifier, URI)}, for which {@link URI} is
	 * resolved based on the provided {@link Resource}
	 *
	 * @see #resolveProject(Notifier, URI)
	 */
	public N4JSProjectConfigSnapshot resolveProject(Resource resource) {
		return resolveProject(resource, resource.getURI());
	}

	/**
	 * Resolves project from provided URI.
	 */
	public N4JSProjectConfigSnapshot resolveProject(Notifier context, URI n4jsSourceURI) {
		final Optional<? extends N4JSProjectConfigSnapshot> optionalProject = n4jsCore.findProject(context,
				n4jsSourceURI);
		if (!optionalProject.isPresent()) {
			throw new RuntimeException(
					"Cannot handle resource without containing project. Resource URI was: " + n4jsSourceURI + ".");
		}
		return optionalProject.get();
	}

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolvePackageAndFileName(Notifier, URI)}, for which
	 * {@link URI} is resolved based on the provided {@link Resource}
	 *
	 * @see #resolvePackageAndFileName(Notifier, URI)
	 */
	public String resolvePackageAndFileName(Resource resource) {
		return resolvePackageAndFileName(resource, resource.getURI());
	}

	/**
	 * Convenience method for {@link ProjectResolveHelper#resolvePackageAndFileName(URI, N4JSProjectConfigSnapshot)},
	 * for which {@link IN4JSProject} is resolved based on the provided {@link URI}
	 *
	 * @see #resolvePackageAndFileName(URI, N4JSProjectConfigSnapshot)
	 */
	public String resolvePackageAndFileName(Notifier context, URI uri) {
		final N4JSProjectConfigSnapshot project = n4jsCore.findProject(context, uri).orNull();
		return resolvePackageAndFileName(uri, project);
	}

	/**
	 * Resolves package and filename from provided {@link URI} against {@link IN4JSProject}. Provided project must be
	 * {@link IN4JSProject#exists()}. In returned string file extension of the actual file is trimmed.
	 */
	public String resolvePackageAndFileName(URI uri, N4JSProjectConfigSnapshot project) {
		final String msg = "Cannot locate source container for module " + uri + ".";
		if (null == project) {
			throw new RuntimeException(msg + " Provided project was null.");
		}

		final N4JSSourceFolderSnapshot sourceContainer = project.findSourceFolderContaining(uri);
		if (sourceContainer == null) {
			throw new RuntimeException(msg);
		}

		return uri.deresolve(sourceContainer.getPathAsFileURI().withTrailingPathDelimiter().toURI())
				.trimFileExtension()
				.toString();
	}
}
