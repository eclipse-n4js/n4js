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

import org.eclipse.n4js.external.ExternalLibraryHelper;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IExternalPackageManager;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class FileBasedExternalPackageManager implements IExternalPackageManager {

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	@Inject
	private ExternalLibraryHelper externalLibraryHelper;

	@Override
	public boolean isN4ProjectRoot(FileURI rootLocation) {
		if (null != rootLocation && rootLocation.exists()) {
			return externalLibraryHelper.isExternalProjectDirectory(rootLocation);
		}
		return false;
	}

	/**
	 * Loads the project description from the given external library root location.
	 *
	 * Returns {@code null} if no valid project description can be read from the given project location.
	 */
	@Override
	public ProjectDescription loadProjectDescriptionFromProjectRoot(FileURI rootLocation) {
		if (null != rootLocation && rootLocation.isDirectory()) {
			return projectDescriptionLoader.loadProjectDescriptionAtLocation(rootLocation);
		}
		return null;
	}

}
