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
package org.eclipse.n4js.external;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;

import com.google.inject.Inject;

/**
 *
 */
public class ShadowingInfoHelper {

	@Inject
	private ExternalLibraryPreferenceStore externalLibraryPreferenceStore;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	/** @return true iff the given project is shadowing another project */
	public boolean isShadowingProject(IN4JSProject project) {
		return !findShadowedProject(project).isEmpty();
	}

	/** @return all projects that are shadowed by the given project (unsorted) */
	public List<N4JSExternalProject> findShadowedProject(IN4JSProject project) {
		List<N4JSExternalProject> shadowedProjects = new LinkedList<>();
		String projectName = project.getProjectName();
		org.eclipse.emf.common.util.URI prjLocation = project.getLocation();

		Collection<URI> locations = externalLibraryPreferenceStore.getLocations();
		Iterable<URI> projectRoots = externalLibraryPreferenceStore.convertToProjectRootLocations(locations);
		for (URI otherPrjLocation : projectRoots) {
			org.eclipse.emf.common.util.URI emfURI = URIUtils.toFileUri(otherPrjLocation);
			String n4jsProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(emfURI);
			if (projectName.equals(n4jsProjectName) && !prjLocation.equals(emfURI)) {
				N4JSExternalProject n4jsExternalProject = externalLibraryWorkspace.getProject(emfURI);
				shadowedProjects.add(n4jsExternalProject);
			}
		}

		return shadowedProjects;
	}

}
