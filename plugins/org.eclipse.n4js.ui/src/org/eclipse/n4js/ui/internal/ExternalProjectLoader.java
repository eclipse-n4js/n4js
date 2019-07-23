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
package org.eclipse.n4js.ui.internal;

import static org.eclipse.n4js.external.N4JSExternalProject.BUILDER_ID;
import static org.eclipse.n4js.external.N4JSExternalProject.NATURE_ID;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.internal.FileBasedExternalPackageManager;
import org.eclipse.n4js.internal.N4JSBrokenProjectException;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Cache loader for creating a {@link ExternalProject external project} instance for the project root location. This
 * loader ensures that the {@link ProjectDescription project description} (N4JS manifest) exists for the project.
 */
@Singleton
public class ExternalProjectLoader {
	private static final Logger LOGGER = Logger.getLogger(ExternalProjectLoader.class);

	@Inject
	private N4JSEclipseModel model;

	@Inject
	private FileBasedExternalPackageManager packageManager;

	/** @return load project and description from given URI. Returns null if not available. */
	public Pair<N4JSExternalProject, ProjectDescription> load(final FileURI rootLocation) {
		try {
			ProjectDescription projectDescription = packageManager.loadProjectDescriptionFromProjectRoot(rootLocation);

			if (null != projectDescription) {
				File projectRoot = new File(rootLocation.toFileString());
				ExternalProject p = new ExternalProject(projectRoot, NATURE_ID, BUILDER_ID);
				IN4JSProject pp = new N4JSEclipseProject(p, rootLocation, model);
				N4JSExternalProject ppp = new N4JSExternalProject(projectRoot, pp);

				return Tuples.create(ppp, projectDescription);
			}
		} catch (N4JSBrokenProjectException e) {
			LOGGER.error("Failed to obtain project description for external library.");
		}
		return null;
	}

}
