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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A {@link FileBasedWorkspace} that automatically discovery new projects on the fly (e.g. when invoking
 * {@link #findProjectWith(FileURI)}).
 */
@Singleton
public class AutoDiscoveryFileBasedWorkspace extends FileBasedWorkspace {

	/** Initializes the workspace. */
	@Inject
	public AutoDiscoveryFileBasedWorkspace(ProjectDescriptionLoader projectDescriptionLoader,
			UriExtensions uriExtensions) {
		super(projectDescriptionLoader, uriExtensions);
	}

	@Override
	public FileURI fromURI(URI unsafe) {
		if (!unsafe.isFile() || unsafe.isRelative()) {
			unsafe = URIUtils.normalize(unsafe);
			if (unsafe.isRelative()) {
				return null;
			}
		}
		return super.fromURI(unsafe);
	}

	@Override
	public FileURI findProjectWith(FileURI nestedLocation) {
		final FileURI closestProjectLocation = findClosestProjectLocation(nestedLocation);
		final FileURI knownProjectLocation = super.findProjectWith(nestedLocation);

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
	private static FileURI findClosestProjectLocation(FileURI location) {
		return location.getProjectRoot();
	}

}
