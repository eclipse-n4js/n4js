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

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.xtext.ui.shared.contribution.IEagerContribution;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * An internal notification mechanism to announce loaded project descriptions.
 */
@Singleton
public class ProjectDescriptionLoadListener implements IEagerContribution {

	interface Strategy {
		List<IProject> getProjectDependencies(IProject project);
	}

	private final IN4JSEclipseCore eclipseCore;
	private final EclipseBasedN4JSWorkspace internalWorkspace;
	private ImmutableList<? extends Strategy> strategies;

	@Inject
	private ProjectDescriptionLoadListener(
			IN4JSEclipseCore eclipseCore,
			EclipseBasedN4JSWorkspace internalWorkspace) {
		this.eclipseCore = eclipseCore;
		this.internalWorkspace = internalWorkspace;
	}

	@Inject
	private void injectStrategies(ISharedStateContributionRegistry registry) {
		this.strategies = registry.getContributedInstances(Strategy.class);
	}

	void onDescriptionLoaded(URI location) {
		if (location.isPlatformResource() && location.segmentCount() == 2) {
			IN4JSEclipseProject n4project = eclipseCore.create(location);
			IProject eclipseProject = n4project.getProject();
			updateProjectReferencesIfNecessary(eclipseProject);
		}
	}

	@Override
	public void initialize() {
		internalWorkspace.setProjectDescriptionLoadListener(this);
	}

	@Override
	public void discard() {
		internalWorkspace.setProjectDescriptionLoadListener(null);
	}

	/**
	 * We have to set dynamic dependencies in the project meta data to ensure that the builder is correctly triggered
	 * according to the declared dependencies in the N4JS manifest files.
	 *
	 * @param project
	 *            the project to update in respect of its dynamic references.
	 */
	public void updateProjectReferencesIfNecessary(final IProject project) {
		if (project instanceof ExternalProject) {
			return; // No need to adjust any dynamic references.
		}

		try {
			IProject[] eclipseRequired = project.getDescription().getDynamicReferences();
			Set<IProject> currentRequires = Sets.newHashSet(eclipseRequired);

			final Set<IProject> newRequires = getProjectDependenciesAsSet(project);
			if (currentRequires.equals(newRequires)) {
				return;
			}
			final IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					IProjectDescription description = project.getDescription();
					IProject[] array = newRequires.toArray(new IProject[newRequires.size()]);
					description.setDynamicReferences(array);
					project.setDescription(description, IResource.AVOID_NATURE_CONFIG, monitor);
				}
			};
			IWorkspace workspace = internalWorkspace.getWorkspace().getWorkspace();
			if (workspace.isTreeLocked()) {
				new Job("Update project description for " + project.getName()) {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						try {
							workspace.run(runnable, null /*
															 * cannot use a scheduling rule here since this is triggered
															 * lazily by the linker
															 */,
									IWorkspace.AVOID_UPDATE, null);
						} catch (CoreException e) {
							return e.getStatus();
						}
						return Status.OK_STATUS;
					}

				}.schedule();
			} else {
				workspace.run(runnable, null /*
												 * cannot use a scheduling rule here since this is triggered lazily by
												 * the linker
												 */,
						IWorkspace.AVOID_UPDATE, null);
			}
		} catch (CoreException e) {
			// ignore
		}
	}

	/**
	 * Returns the to-be-set dynamic project dependencies as a sorted set. This ensures stable order in the project
	 * meta-data.
	 *
	 * @return the set of expected project dependencies.
	 */
	protected SortedSet<IProject> getProjectDependenciesAsSet(IProject project) {
		SortedSet<IProject> result = new TreeSet<>(new Comparator<IProject>() {
			@Override
			public int compare(IProject o1, IProject o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for (Strategy strategy : strategies) {
			result.addAll(strategy.getProjectDependencies(project));
		}
		return result;
	}

}
