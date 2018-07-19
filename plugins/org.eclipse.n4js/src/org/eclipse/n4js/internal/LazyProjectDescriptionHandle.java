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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;

/**
 * Lazy handle that is registered as a proxy at runtime.
 */
@SuppressWarnings("javadoc")
public class LazyProjectDescriptionHandle {

	private final URI location;
	private final ProjectDescriptionHelper descriptionHelper;

	private ProjectDescription resolved;

	protected LazyProjectDescriptionHandle(URI location, ProjectDescriptionHelper descriptionHelper) {
		this.location = location;
		this.descriptionHelper = descriptionHelper;
	}

	/**
	 * Resolves the lazy handle and returns the actually underlying {@link ProjectDescription}
	 */
	ProjectDescription resolve() {
		if (resolved != null) {
			return resolved;
		}
		return resolved = loadProjectDescriptionFromLocation(this.getLocation());
	}

	URI getLocation() {
		return location;
	}

	/**
	 * Loads the {@link ProjectDescription} for the project at the given {@code projectLocation}.
	 */
	protected ProjectDescription loadProjectDescriptionFromLocation(URI projectLocation) {
		return descriptionHelper.loadProjectDescriptionAtLocation(projectLocation);
	}

}
