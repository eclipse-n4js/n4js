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
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.external.ExternalLibraryUtils;
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
		if (null != rootLocation && rootLocation.isFile()) {
			File projectRoot = new File(rootLocation.toFileString());
			return ExternalLibraryUtils.isExternalProjectDirectory(projectRoot);
		}
		return false;
	}

	/**
	 * Returns {@code true} iff the given {@code rootLocation} refers to an external library project location that
	 * contains a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON}.
	 */
	@Override
	public boolean isExternalProjectWithFragment(URI rootLocation) {
		if (null != rootLocation && rootLocation.isFile()) {
			File projectRoot = new File(rootLocation.toFileString());
			return new File(projectRoot, N4JSGlobals.PACKAGE_FRAGMENT_JSON).exists();
		}
		return false;
	}

	/**
	 * Loads a project description from the given external library root location, purely based on the contents of the
	 * {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON} file.
	 *
	 * Returns {@code null} if no fragment can be found at the given location.
	 */
	@Override
	public ProjectDescription loadFragmentProjectDescriptionFromProjectRoot(URI rootLocation) {
		if (null != rootLocation && rootLocation.isFile()) {
			File projectRoot = new File(rootLocation.toFileString());
			if (projectRoot.exists() && projectRoot.isDirectory()) {
				return projectDescriptionHelper.loadProjectDescriptionAtLocation(rootLocation);
			}
		}
		return null;
	}

	/**
	 * Loads the project description from the given external library root location.
	 *
	 * For plain npm packages with {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON} this returns a project description that
	 * represents the original {@code package.json} merged with the contents of the fragment.
	 *
	 * Returns {@code null} if no valid project description can be read from the given project location.
	 */
	@Override
	public ProjectDescription loadProjectDescriptionFromProjectRoot(URI rootLocation) {
		if (null != rootLocation && rootLocation.isFile()) {
			File projectRoot = new File(rootLocation.toFileString());
			if (projectRoot.exists() && projectRoot.isDirectory()) {
				return projectDescriptionHelper.loadProjectDescriptionAtLocation(rootLocation);
			}
		}
		return null;
	}

}
