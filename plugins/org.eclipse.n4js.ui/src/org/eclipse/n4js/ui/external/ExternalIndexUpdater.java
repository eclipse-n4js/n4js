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
package org.eclipse.n4js.ui.external;

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.core.runtime.SubMonitor.convert;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.external.N4JSExternalProjectProvider;
import org.eclipse.n4js.external.RebuildWorkspaceProjectsScheduler;
import org.eclipse.n4js.utils.resources.ExternalProject;

import com.google.inject.Inject;

/**
 *
 */
public class ExternalIndexUpdater implements ExternalLocationsUpdatedListener {
	private Collection<N4JSExternalProject> extProjectsDependingOnRemovedProjects;
	private Collection<IProject> wsProjectsDependingOnRemovedProjects;

	@Inject
	private ExternalLibraryBuilder builder;

	/** This provider creates {@link ExternalProject}s */
	@Inject
	private ExternalProjectProvider extPP;

	/** This provider wraps {@link ExternalProject}s into {@link N4JSExternalProject}s */
	@Inject
	private N4JSExternalProjectProvider n4extPP;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private RebuildWorkspaceProjectsScheduler scheduler;

	@Override
	public void beforeLocationsUpdated(Set<URI> removedLocations, IProgressMonitor monitor) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);

			extPP.ensureInitialized();
			cleanRemovedLocations(removedLocations, monitor);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	@Override
	public void afterLocationsUpdated(Set<URI> addedLocations, IProgressMonitor monitor) {
		ISchedulingRule rule = builder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);

			buildAddedLocations(addedLocations, monitor);
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	/** Removes projects from Index that were in a removed location */
	private void cleanRemovedLocations(Set<URI> removedLocations, IProgressMonitor monitor) {
		Set<N4JSExternalProject> removedProjects = n4extPP.getProjectsIn(removedLocations);
		SubMonitor subMonitor = convert(monitor, 1);

		// Clean removed projects from Index
		builder.clean(removedProjects, subMonitor.newChild(1));
		subMonitor.worked(1);

		// Remember affected workspace and external projects
		extProjectsDependingOnRemovedProjects = collector.getExtProjectsDependendingOn(removedProjects);
		extProjectsDependingOnRemovedProjects.removeAll(removedProjects);

		wsProjectsDependingOnRemovedProjects = collector.getWSProjectsDependendingOn(removedProjects);
		wsProjectsDependingOnRemovedProjects.removeAll(removedProjects);
	}

	/** Adds projects to Index that are in a added location or depend on removed/added projects */
	private void buildAddedLocations(Set<URI> addedLocations, IProgressMonitor monitor) {
		Set<N4JSExternalProject> addedProjects = n4extPP.getProjectsIn(addedLocations);
		SubMonitor subMonitor = convert(monitor, 2);

		// Build external projects that depend on added projects. (only non-user-workspace)
		Set<N4JSExternalProject> extProjectsToBuild = newHashSet();
		extProjectsToBuild.addAll(extProjectsDependingOnRemovedProjects);
		extProjectsToBuild.addAll(addedProjects);
		extProjectsToBuild.addAll(collector.getExtProjectsDependendingOn(addedProjects));
		builder.build(extProjectsToBuild, subMonitor.newChild(1));
		subMonitor.worked(1);

		// Schedule rebuild of workspace projects
		Set<IProject> wsProjectsToRebuild = newHashSet();
		wsProjectsToRebuild.addAll(wsProjectsDependingOnRemovedProjects);
		wsProjectsToRebuild.addAll(collector.getWSProjectsDependendingOn(addedProjects));
		wsProjectsToRebuild.addAll(collector.getWSProjectsDependendingOn(extProjectsToBuild));
		scheduler.scheduleBuildIfNecessary(wsProjectsToRebuild);
	}

}
