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
package org.eclipse.n4js.ui.projectModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.common.base.Optional;

/**
 * The plug-in runtime facade for the n4js model containing the core (UI-free) support for n4js projects.
 * <p>
 * The single instance of this interface can be accessed via dependency injection.
 * </p>
 */
public interface IN4JSEclipseCore extends IN4JSCore {

	/**
	 * Returns the N4JS project corresponding to the given Eclipse project.
	 * <p>
	 * Note that no check is done at this time on the existence or the Xtext nature of this project or the presence of
	 * the N4JS manifest.
	 * </p>
	 *
	 * @param eclipseProject
	 *            the backing project
	 * @return the n4js project corresponding to the given project, null if the given project is null
	 */
	Optional<? extends IN4JSEclipseProject> create(IProject eclipseProject);

	/**
	 * Returns the source container this file belongs to.
	 */
	Optional<? extends IN4JSSourceContainer> create(IFile eclipseFile);

	/**
	 * Returns the source container this file belongs to.
	 */
	Optional<? extends IN4JSSourceContainer> create(IFolder eclipseFolder);

	/**
	 * Returns the N4JS project corresponding to the given Eclipse project.
	 * <p>
	 * Note that no check is done at this time on the existence or the Xtext nature of this project or the presence of
	 * the N4JS manifest.
	 * </p>
	 *
	 * @param location
	 *            the project location
	 * @return the n4js project corresponding to the given project, null if the given project is null
	 */
	@Override
	IN4JSEclipseProject create(URI location);

	@Override
	Optional<? extends IN4JSEclipseProject> findProject(URI nestedLocation);

	/**
	 * Maps a file URI from a resource of the external workspace project to a platform URI of a user-space workspace
	 * project.
	 *
	 * @param fileUri
	 *            a file URI of an external workspace project
	 * @return the platform URI for a given file URI
	 */
	Optional<URI> mapExternalResourceToUserWorkspaceLocalResource(URI fileUri);

}
