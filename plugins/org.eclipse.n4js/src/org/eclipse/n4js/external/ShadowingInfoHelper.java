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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.inject.Inject;

/**
 * Provides methods to compute which projects are shadowed by a given project.
 */
public class ShadowingInfoHelper {
	@Inject
	private N4JSModel model;

	@Inject
	private InternalN4JSWorkspace userWorkspace;

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
		String projectName = project.getProjectName();

		List<IN4JSProject> shadowingProjects = new LinkedList<>();
		if (!project.isExternal()) {
			return shadowingProjects;
		}

		List<N4JSExternalProject> projectsForName = externalLibraryWorkspace.getProjectsForName(projectName);
		if (projectsForName != null && !projectsForName.isEmpty()) {
			org.eclipse.emf.common.util.URI prjLoc = project.getLocation();

			List<N4JSExternalProject> projectsForNameReversed = new LinkedList<>(projectsForName);
			Collections.reverse(projectsForNameReversed);
			Iterator<N4JSExternalProject> sameNamedPrjsIter = projectsForNameReversed.iterator();
			while (sameNamedPrjsIter.hasNext()) {
				N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
				org.eclipse.emf.common.util.URI otherPrjLoc = sameNamePrj.getIProject().getLocation();
				if (prjLoc == otherPrjLoc) {
					break;
				}
			}
			while (sameNamedPrjsIter.hasNext()) {
				N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
				shadowingProjects.add(sameNamePrj.getIProject());
			}
		}

		for (URI loc : userWorkspace.getAllProjectLocations()) {
			String prjName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(loc);
			if (projectName.equals(prjName)) {
				N4JSProject n4jsProject = model.getN4JSProject(loc);
				shadowingProjects.add(n4jsProject);
				break;
			}
		}

		return shadowingProjects;
	}

	/** @return all projects that are shadowed by the given project */
	public List<N4JSExternalProject> findShadowedProjects(IN4JSProject project) {
		String projectName = project.getProjectName();

		List<N4JSExternalProject> projectsForName = externalLibraryWorkspace.getProjectsForName(projectName);
		if (projectsForName == null || projectsForName.isEmpty()) {
			return Collections.emptyList();
		}
		if (!project.isExternal()) {
			return projectsForName;
		}

		List<N4JSExternalProject> shadowedProjects = new LinkedList<>();
		org.eclipse.emf.common.util.URI prjLoc = project.getLocation();

		projectsForName = new LinkedList<>(projectsForName);
		Iterator<N4JSExternalProject> sameNamedPrjsIter = projectsForName.iterator();
		while (sameNamedPrjsIter.hasNext()) {
			N4JSExternalProject sameNamePrj = sameNamedPrjsIter.next();
			org.eclipse.emf.common.util.URI otherPrjLoc = sameNamePrj.getIProject().getLocation();
			if (prjLoc == otherPrjLoc) {
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
