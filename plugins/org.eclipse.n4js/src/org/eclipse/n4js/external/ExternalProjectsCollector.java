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
package org.eclipse.n4js.external;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.EclipseProjectName;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Service for collecting available {@link N4JSExternalProject external project} instances based on the configured
 * external library locations.
 */
@Singleton
public class ExternalProjectsCollector {

	@Inject
	private ExternalLibraryWorkspace extWS;

	@Inject
	private IN4JSCore core;

	/** @return a new set that contains only those projects of the given set that <b>are</b> in the workspace */
	public <P extends IProject> Set<P> filterWSProjects(Iterable<P> addedProjects) {
		return filterWSProjects(addedProjects, true);
	}

	/** @return a new set that contains only those projects of the given set that <b>are not</b> in the workspace */
	public <P extends IProject> Set<P> filterNonWSProjects(Iterable<P> projects) {
		return filterWSProjects(projects, false);
	}

	private <P extends IProject> Set<P> filterWSProjects(Iterable<P> addedProjects, boolean positive) {
		Set<String> eclipseWorkspaceProjectNames = getAllEclipseWorkspaceProjectNames();
		Set<P> projectsToBuild = newHashSet();
		for (P addedProject : addedProjects) {
			if (positive == eclipseWorkspaceProjectNames.contains(addedProject.getName())) {
				projectsToBuild.add(addedProject);
			}
		}
		return projectsToBuild;
	}

	private Set<String> getAllEclipseWorkspaceProjectNames() {
		if (Platform.isRunning()) {
			return from(Arrays.asList(getWorkspace().getRoot().getProjects()))
					.filter(p -> p.isAccessible())
					.transform(p -> p.getName())
					.toSet();
		}
		return Collections.emptySet();
	}

	/**
	 * Returns with all {@link IProject project} instances that are available from the {@link IWorkspaceRoot workspace
	 * root} and has direct dependency to any external projects.
	 *
	 * <p>
	 * This method neither considers, nor modifies the {@link IProjectDescription#getDynamicReferences() dynamic
	 * references} of the workspace projects. Instead it gathers the dependency information from the {@link IN4JSProject
	 * N4JS project} using the {@link IN4JSCore N4JS core} service.
	 *
	 * <p>
	 * No transitive dependencies will be considered. For instance if an accessible Eclipse workspace project B depends
	 * on another accessible Eclipse project A and project A depends on an external project X, then this method will
	 * return with only A and *NOT* A and B.
	 *
	 * @return an iterable of Eclipse workspace projects that has direct dependency any external projects.
	 */
	public Collection<IProject> getWSProjectsDependendingOn() {
		return getWSProjectsDependendingOn(extWS.getProjects());
	}

	/**
	 * Sugar for collecting {@link IWorkspace Eclipse workspace} projects that have any direct dependency to any
	 * external projects. Same as {@link #getWSProjectsDependendingOn()} but does not considers all the available
	 * projects but only those that are given as the argument.
	 *
	 * @param externalProjects
	 *            the external projects that has to be considered as a possible dependency of an Eclipse workspace based
	 *            project.
	 * @return an iterable of Eclipse workspace projects that has direct dependency to an external project given as the
	 *         argument.
	 */
	public Collection<IProject> getWSProjectsDependendingOn(Iterable<N4JSExternalProject> externalProjects) {
		return getProjectsDependendingOn(asList(getWorkspace().getRoot().getProjects()), externalProjects);
	}

	/**
	 *
	 * @param externalProjects
	 *            the external projects that has to be considered as a possible dependency of an Eclipse workspace based
	 *            project.
	 * @return an iterable of external workspace projects that has direct dependency to an external project given as the
	 *         argument.
	 */
	public Collection<N4JSExternalProject> getExtProjectsDependendingOn(
			Iterable<N4JSExternalProject> externalProjects) {
		return getProjectsDependendingOn(extWS.getProjects(), externalProjects);
	}

	/**
	 * Sugar for collecting {@link IWorkspace Eclipse workspace} projects that have any direct dependency to any
	 * external projects. Same as {@link #getWSProjectsDependendingOn()} but does not considers all the available
	 * projects but only those that are given as the argument.
	 *
	 * @param externalProjects
	 *            the external projects that has to be considered as a possible dependency of an Eclipse workspace based
	 *            project.
	 * @return an iterable of Eclipse workspace projects that has direct dependency to an external project given as the
	 *         argument.
	 */
	private <P extends IProject> Collection<P> getProjectsDependendingOn(Iterable<P> wsProjects,
			Iterable<N4JSExternalProject> externalProjects) {

		if (!Platform.isRunning()) {
			return emptyList();
		}

		Set<N4JSProjectName> externalIds = from(externalProjects)
				.transform(p -> new EclipseProjectName(p.getName()).toN4JSProjectName())
				.toSet();
		LinkedList<P> filteredProjects = new LinkedList<>();

		Map<N4JSProjectName, IProject> externalsMapping = new HashMap<>();
		for (IProject prj : externalProjects) {
			externalsMapping.put(new EclipseProjectName(prj.getName()).toN4JSProjectName(), prj);
		}

		for (P prj : wsProjects) {

			if (prj instanceof N4JSExternalProject) {
				N4JSExternalProject extPrj = (N4JSExternalProject) prj;
				for (N4JSProjectName eID : extPrj.getAllDirectDependencyIds()) {
					IProject externalDependency = externalsMapping.get(eID);
					if (externalDependency != null) {
						filteredProjects.add(prj);
					}
				}
			} else {

				Set<N4JSProjectName> deps = Sets.newHashSet(getDirectExternalDependencyIds(prj));
				Iterables.retainAll(deps, externalIds);
				if (!Iterables.isEmpty(deps)) {
					filteredProjects.add(prj);
				}
			}
		}

		return filteredProjects;
	}

	/**
	 * Returns mapping between all {@link IProject project} instances that are available from the {@link IWorkspaceRoot
	 * workspace root} and its direct dependencies to any external projects.
	 *
	 * <p>
	 * This method neither considers, nor modifies the {@link IProjectDescription#getDynamicReferences() dynamic
	 * references} of the workspace projects. Instead it gathers the dependency information from the {@link IN4JSProject
	 * N4JS project} using the {@link IN4JSCore N4JS core} service.
	 *
	 * <p>
	 * No transitive dependencies will be considered. For instance if an accessible Eclipse workspace project B depends
	 * on another accessible Eclipse project A and project A depends on an external project X and Y, then this method
	 * will return with only {A->[X]} and *NOT* {A->[X,Y], B->[X,Y]}.
	 *
	 * @return a map where each entry maps an external project to the workspace projects that depend on it.
	 */
	public Multimap<N4JSExternalProject, IProject> getWSProjectDependents() {
		return getWSProjectDependents(extWS.getProjects());
	}

	/**
	 * Sugar for collecting {@link IWorkspace Eclipse workspace} projects that have any direct dependency to any
	 * external projects. Same as {@link #getWSProjectDependents()} but does not consider all the available projects but
	 * only those that are given as the argument.
	 *
	 * @param externalProjects
	 *            the external projects that has to be considered as a possible dependency of an Eclipse workspace based
	 *            project.
	 * @return a map where each entry maps an external project to the workspace projects that depend on it.
	 */
	public Multimap<N4JSExternalProject, IProject> getWSProjectDependents(
			Iterable<N4JSExternalProject> externalProjects) {
		return getProjectDependents(asList(getWorkspace().getRoot().getProjects()), externalProjects);
	}

	/***/
	public Multimap<N4JSExternalProject, N4JSExternalProject> getExtProjectDependents(
			Iterable<N4JSExternalProject> externalProjects) {

		return getProjectDependents(extWS.getProjects(), externalProjects);
	}

	/***/
	private <P extends IProject> Multimap<N4JSExternalProject, P> getProjectDependents(Iterable<P> wsProjects,
			Iterable<N4JSExternalProject> externalProjects) {

		Multimap<N4JSExternalProject, P> mapping = HashMultimap.create();

		if (!Platform.isRunning()) {
			return mapping;
		}

		Map<N4JSProjectName, N4JSExternalProject> externalsMapping = new HashMap<>();
		for (N4JSExternalProject prj : externalProjects) {
			externalsMapping.put(new EclipseProjectName(prj.getName()).toN4JSProjectName(), prj);
		}

		for (P prj : wsProjects) {
			if (prj instanceof N4JSExternalProject) {
				N4JSExternalProject extPrj = (N4JSExternalProject) prj;
				for (N4JSProjectName eID : extPrj.getAllDirectDependencyIds()) {
					N4JSExternalProject externalDependency = externalsMapping.get(eID);
					if (externalDependency != null) {
						mapping.put(externalDependency, prj);
					}
				}
			} else {
				for (N4JSProjectName eID : getDirectExternalDependencyIds(prj)) {
					N4JSExternalProject externalDependency = externalsMapping.get(eID);
					if (externalDependency != null) {
						mapping.put(externalDependency, prj);
					}
				}
			}
		}

		return mapping;
	}

	/**
	 * Returns with all external project dependency project IDs for a particular non-external, accessible project.
	 */
	private Iterable<N4JSProjectName> getDirectExternalDependencyIds(IProject project) {

		if (null == project || !project.isAccessible()) {
			return emptyList();
		}

		IN4JSProject n4Project = core.findProject(URI.createPlatformResourceURI(project.getName(), true)).orNull();
		if (null == n4Project || !n4Project.exists() || n4Project.isExternal()) {
			return emptyList();
		}

		return from(n4Project.getAllDirectDependencies())
				.filter(IN4JSProject.class)
				.filter(p -> p.isExternal())
				.transform(p -> p.getProjectName());
	}
}
