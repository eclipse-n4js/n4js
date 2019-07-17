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
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

/**
 * Internal representation of the known projects. At runtime, it's implemented based on registered project locations, in
 * Eclipse the workspace is used as the backing store.
 *
 * It's not meant to be used by clients different from the {@link N4JSModel} since the contract for URI schemes etc is
 * implemented there and shared with the used implementation of the {@link InternalN4JSWorkspace}.
 */
public abstract class InternalN4JSWorkspace<Loc extends SafeURI<Loc>> {

	/**
	 * Wrap the given location.
	 */
	public abstract Loc fromURI(URI uri);

	/**
	 * Obtains the project description for the project at the given location.
	 */
	public abstract ProjectDescription getProjectDescription(Loc location);

	/**
	 * Find the location of the target project for the given project reference, e.g. a project dependency
	 *
	 * Returns {@code null} if nothing can be found.
	 *
	 * @param reference
	 *            the project reference
	 */
	public abstract Loc getLocation(ProjectReference reference);

	/**
	 * Iterates the contents of a folder.
	 *
	 * This method will never list the contents of a nested {@link N4JSGlobals#NODE_MODULES} folder.
	 */
	public abstract Iterator<? extends Loc> getFolderIterator(Loc folderLocation);

	/**
	 * Checks if the given folder contains a resource of any type at the given folder-relative path and returns the URI
	 * for it or <code>null</code> if not found. Both '/' and the system-dependent name separator (cf.
	 * {@link File#separator}) can be used in 'folderRelativePath'.
	 */
	public abstract Loc findArtifactInFolder(Loc folder, String folderRelativePath);

	/**
	 * Returns the location of the project that contains the given nested location.
	 */
	public abstract Loc findProjectWith(Loc nestedLocation);

	/**
	 * Returns the location of the project that contains the given nested location.
	 */
	public abstract Collection<Loc> getAllProjectLocations();

	/**
	 * Returns the location of the project with the given name.
	 *
	 * The name is the real project name and not necessarily a valid eclipse project name.
	 *
	 * @see ProjectDescriptionUtils#convertN4JSProjectNameToEclipseProjectName(String)
	 */
	public abstract Loc getProjectLocation(N4JSProjectName name);
}
