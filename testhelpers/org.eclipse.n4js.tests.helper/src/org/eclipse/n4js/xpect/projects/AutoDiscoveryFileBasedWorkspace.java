/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.projects;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.ClasspathPackageManager;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.URIUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A {@link FileBasedWorkspace} that automatically discovery new projects on the fly (e.g. when invoking
 * {@link #findProjectWith(org.eclipse.emf.common.util.URI)}).
 */
@Singleton
public class AutoDiscoveryFileBasedWorkspace extends FileBasedWorkspace {

	/** Initializes the workspace. */
	@Inject
	public AutoDiscoveryFileBasedWorkspace(ClasspathPackageManager packageManager,
			ProjectDescriptionHelper projectDescriptionHelper) {
		super(packageManager, projectDescriptionHelper);
	}

	@Override
	public URI findProjectWith(URI unsafeLocation) {
		final URI closestProjectLocation = findClosestProjectLocation(unsafeLocation);
		final URI knownProjectLocation = super.findProjectWith(unsafeLocation);

		if (closestProjectLocation != null
				&& (knownProjectLocation == null || !knownProjectLocation.equals(closestProjectLocation))) {
			registerProject(closestProjectLocation);
		}

		return closestProjectLocation;
	}

	/**
	 * Automatically discovery the closest project location based on the given {@code location}.
	 *
	 * Ascends the file hierarchy starting from {@code location}, until it finds a directory that contains a
	 * {@link IN4JSProject#PACKAGE_JSON} file.
	 */
	private static URI findClosestProjectLocation(URI location) {
		URI nestedLocation = URIUtils.normalize(location);
		int segmentCount = 0;
		if (nestedLocation.isFile()) { // Here, unlike java.io.File, #isFile can mean directory as well.
			File directory = new File(nestedLocation.toFileString());
			while (directory != null) {
				if (isProjectDirectory(directory)) {
					URI projectLocation = URI.createFileURI(directory.getAbsolutePath());
					return projectLocation;
				}
				nestedLocation = nestedLocation.trimSegments(segmentCount++);
				directory = directory.getParentFile();
			}
		}
		return null;
	}

	/** Determines whether {@code directory} may be regarded as valid N4JS project directory. */
	private static boolean isProjectDirectory(File directory) {
		return directory.toPath().isAbsolute() &&
				N4JSProject.isN4JSProjectDirectory(URI.createFileURI(directory.getPath()));
	}
}
