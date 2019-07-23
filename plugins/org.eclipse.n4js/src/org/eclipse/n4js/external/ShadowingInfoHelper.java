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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.inject.Inject;

/**
 * Provides methods to compute which projects are shadowed by a given project.
 */
public class ShadowingInfoHelper {
	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ExternalLibraryWorkspace externalLibraryWorkspace;

	/** @return true iff the given project is shadowing another project */
	public boolean isShadowingProject(IN4JSProject project) {
		return !findShadowedProjects(project).isEmpty();
	}

	/** @return true iff the given project is shadowing another project */
	public boolean isShadowedProject(IN4JSProject project) {
		return !findShadowingProjects(project).isEmpty();
	}

	/** @return all projects that are shadowing the given project */
	public List<IN4JSProject> findShadowingProjects(IN4JSProject project) {
		N4JSProjectName projectName = project.getProjectName();

		List<IN4JSProject> shadowingProjects = new LinkedList<>();
		if (!project.isExternal()) {
			return shadowingProjects;
		}

		List<N4JSExternalProject> projectsForName = externalLibraryWorkspace.getProjectsForName(projectName);
		if (projectsForName != null && !projectsForName.isEmpty()) {
			SafeURI<?> prjLoc = project.getLocation();

			List<N4JSExternalProject> projectsForNameReversed = new LinkedList<>(projectsForName);
			Collections.reverse(projectsForNameReversed);
			Iterator<N4JSExternalProject> sameNamedPrjsIter = projectsForNameReversed.iterator();
			while (sameNamedPrjsIter.hasNext()) {
				N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
				SafeURI<?> otherPrjLoc = sameNamePrj.getSafeLocation();
				if (prjLoc.equals(otherPrjLoc)) {
					break;
				}
			}
			while (sameNamedPrjsIter.hasNext()) {
				N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
				shadowingProjects.add(sameNamePrj.getIProject());
			}
		}

		IN4JSProject shadowingProject = n4jsCore.findProject(projectName).orNull();
		if (shadowingProject != null && shadowingProject.exists() && !shadowingProject.isExternal()) {
			shadowingProjects.add(shadowingProject);
		}
		return shadowingProjects;
	}

	/** @return all projects that are shadowed by the given project */
	public List<N4JSExternalProject> findShadowedProjects(IN4JSProject project) {
		N4JSProjectName projectName = project.getProjectName();

		List<N4JSExternalProject> projectsForName = externalLibraryWorkspace.getProjectsForName(projectName);
		if (projectsForName == null || projectsForName.isEmpty()) {
			return Collections.emptyList();
		}
		if (!project.isExternal()) {
			return projectsForName;
		}

		List<N4JSExternalProject> shadowedProjects = new LinkedList<>();
		SafeURI<?> prjLoc = project.getLocation();

		projectsForName = new LinkedList<>(projectsForName);
		Iterator<N4JSExternalProject> sameNamedPrjsIter = projectsForName.iterator();
		while (sameNamedPrjsIter.hasNext()) {
			N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
			SafeURI<?> otherPrjLoc = sameNamePrj.getIProject().getLocation();
			if (prjLoc.equals(otherPrjLoc)) {
				break;
			}
		}
		while (sameNamedPrjsIter.hasNext()) {
			N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
			shadowedProjects.add(sameNamePrj);
		}

		return shadowedProjects;
	}

}
