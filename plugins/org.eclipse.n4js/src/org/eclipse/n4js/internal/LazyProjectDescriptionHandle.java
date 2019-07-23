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

import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;

/**
 * Lazy handle that is registered as a proxy at runtime.
 */
public class LazyProjectDescriptionHandle {

	private final FileURI projectLocation;
	private final ProjectDescriptionLoader descriptionLoader;

	private ProjectDescription resolved;

	/** */
	protected LazyProjectDescriptionHandle(FileURI location,
			ProjectDescriptionLoader descriptionLoader) {
		this.projectLocation = location;
		this.descriptionLoader = descriptionLoader;
	}

	/**
	 * Resolves the lazy handle and returns the actually underlying {@link ProjectDescription}
	 */
	ProjectDescription resolve() {
		if (resolved != null) {
			return resolved;
		}
		return resolved = descriptionLoader.loadProjectDescriptionAtLocation(this.getLocation());
	}

	/**
	 * Returns the project location this handle represents.
	 */
	FileURI getLocation() {
		return projectLocation;
	}
}
