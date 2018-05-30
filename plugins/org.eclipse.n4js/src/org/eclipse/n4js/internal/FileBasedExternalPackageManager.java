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

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.projectModel.IExternalPackageManager;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class FileBasedExternalPackageManager implements IExternalPackageManager {

	@Inject
	private ProjectDescriptionHelper projectDescriptionHelper;

	@Override
	public boolean isN4ProjectRoot(URI rootLocation) {
		return loadManifestFromProjectRoot(rootLocation) != null;
	}

	@Override
	public ProjectDescription loadManifestFromProjectRoot(URI rootLocation) {
		if (null != rootLocation && rootLocation.isFile()) {
			File projectRoot = new File(rootLocation.toFileString());
			if (projectRoot.exists() && projectRoot.isDirectory()) {
				return projectDescriptionHelper.loadProjectDescriptionAtLocation(rootLocation);
			}
		}
		return null;
	}

}
