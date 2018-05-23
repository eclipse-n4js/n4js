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

import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSSourceContainerAware;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.utils.resources.ExternalProject;

/**
 */
public class N4MFProjectDependencyStrategy implements ProjectDescriptionLoadListener.Strategy {

	private final IN4JSEclipseCore core;

	@Inject
	N4MFProjectDependencyStrategy(final IN4JSEclipseCore core) {
		this.core = core;
	}

	@Override
	public List<IProject> getProjectDependencies(final IProject project) {
		final IN4JSEclipseProject n4Project = core.create(project).orNull();
		if (null != n4Project) {
			// This covers project dependencies, tests (if any), required REs and required RLs and APIs.
			final Collection<? extends IN4JSEclipseProject> dependencies = n4Project
					.getDependenciesAndImplementedApis();

			// This is for the provided runtime libraries.
			final Collection<IN4JSEclipseProject> providedRuntimeLibs = from(n4Project.getProvidedRuntimeLibraries())
					.filter(IN4JSEclipseProject.class).toList();

			// This is for the extended RE, if specified.
			final IN4JSSourceContainerAware srcContainerAware = n4Project.getExtendedRuntimeEnvironment().orNull();
			final IN4JSEclipseProject extendedRE = srcContainerAware instanceof IN4JSEclipseProject
					? (IN4JSEclipseProject) srcContainerAware
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
