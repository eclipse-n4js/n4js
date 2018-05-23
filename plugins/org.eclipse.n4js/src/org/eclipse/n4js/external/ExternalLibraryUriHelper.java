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

import org.eclipse.emf.common.util.URI;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Helper class to deciding whether a {@link URI URI} represents and external library location or not.
 */
@Singleton
public class ExternalLibraryUriHelper {

	@Inject
	private IN4JSCore core;

	/**
	 * Check if the {@link URI URI} argument represents an external library location or not.
	 *
	 * @param uri
	 *            to check whether external location or not.
	 * @return {@code true} if the argument points to an external library location, otherwise {@code false}.
	 */
	public boolean isExternalLocation(final URI uri) {
		if (null != uri && uri.isFile()) {
			final IN4JSProject project = core.findProject(uri).orNull();
			return null != project && project.exists() && project.isExternal();
		}
		return false;
	}
}
