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

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;

import com.google.inject.Inject;

/**
 * Compute the project dependencies based on the content of the package.json files.
 */
public class N4JSProjectDependencyStrategy implements ProjectDescriptionLoadListener.Strategy {

	private final IN4JSEclipseCore core;

	@Inject
	N4JSProjectDependencyStrategy(final IN4JSEclipseCore core) {
		this.core = core;
	}

	/**
	 * Returns the project dependencies without the unresolved deps.
	 */
	@Override
	public List<IProject> getProjectDependencies(final IProject project) {
		return getProjectDependencies(project, false);
	}

	/**
	 * @param project
	 *            the project
	 * @param includeMissingOrClosedProjects
	 *            true if declared but unresolved deps should be returned, too.
	 * @return the required dependencies.
	 */
	public List<IProject> getProjectDependencies(final IProject project, boolean includeMissingOrClosedProjects) {
		final IN4JSEclipseProject n4Project = core.create(project).orNull();
		if (null != n4Project) {
			// This covers project dependencies, tests (if any), required REs and required RLs and APIs.
			final Collection<? extends IN4JSEclipseProject> dependencies = n4Project
					.getDependenciesAndImplementedApis(includeMissingOrClosedProjects);

			// This is for the provided runtime libraries.
			final Collection<IN4JSEclipseProject> providedRuntimeLibs = from(n4Project.getProvidedRuntimeLibraries())
					.filter(IN4JSEclipseProject.class).toList();

			// This is for the extended RE, if specified.
			final IN4JSProject runtimeEnvironmentProject = n4Project.getExtendedRuntimeEnvironment().orNull();
			final IN4JSEclipseProject extendedRE = runtimeEnvironmentProject instanceof IN4JSEclipseProject
					? (IN4JSEclipseProject) runtimeEnvironmentProject
					: null;

			int projectCount = dependencies.size() + providedRuntimeLibs.size();
			if (null != extendedRE) {
				projectCount++;
			}

			final IProject[] projects = new IProject[projectCount];
			int i = 0;
			for (final IN4JSEclipseProject dependency : dependencies) {
				projects[i++] = dependency.getProject();
			}

			for (final IN4JSEclipseProject providedRuntimeLib : providedRuntimeLibs) {
				projects[i++] = providedRuntimeLib.getProject();
			}

			if (null != extendedRE) {
				projects[i] = extendedRE.getProject();
			}

			return from(asList(projects)).filter(p -> !(p instanceof ExternalProject)).toList();
		} else {
			return emptyList();
		}
	}
}
