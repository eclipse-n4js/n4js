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
package org.eclipse.n4js.ui.external;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.external.LibraryManager;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.utils.Cancelable;
import org.eclipse.xtext.builder.impl.BuilderStateDiscarder;
import org.eclipse.xtext.builder.impl.IBuildFlag;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for reloading the registered external libraries by recreating the Xtext index for them.
 */
@Singleton
@SuppressWarnings("restriction")
public class ExternalLibrariesReloadHelper {

	private static final Logger LOGGER = Logger.getLogger(ExternalLibrariesReloadHelper.class);

	@Inject
	private LibraryManager libManager;

	@Inject
	private ExternalProjectsCollector collector;

	@Inject
	private ExternalProjectProvider projectProvider;

	@Inject
	private ExternalLibraryBuilder externalBuilder;

	@Inject
	private BuilderStateDiscarder builderStateDiscarder;

	/**
	 * Reloads the external libraries by re-indexing all registered external projects that are do not exist in the
	 * workspace.
	 *
	 * @param refreshNpmDefinitions
	 *            if {@code true}, then the type definition files will be reloaded/refreshed for all {@code npm}
	 *            packages.
	 * @param monitor
	 *            the monitor for the process.
	 * @throws InvocationTargetException
	 *             if any unexpected error occurs during the refresh process.
	 */
	public void reloadLibraries(final boolean refreshNpmDefinitions, final IProgressMonitor monitor)
			throws InvocationTargetException {

		final ISchedulingRule rule = externalBuilder.getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);
			reloadLibrariesInternal(refreshNpmDefinitions, monitor);
		} catch (final OperationCanceledException e) {
			LOGGER.info("User abort.");
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	private void reloadLibrariesInternal(final boolean refreshNpmDefinitions, final IProgressMonitor monitor)
			throws InvocationTargetException {

		final SubMonitor subMonitor = SubMonitor.convert(monitor, refreshNpmDefinitions ? 2 : 1);

		if (monitor instanceof Cancelable) {
			((Cancelable) monitor).setCancelable(false); // No cancel is allowed from now on.
		}

		if (monitor.isCanceled()) {
			return;
		}

		// Refresh the type definitions for the npm packages if required.
		if (refreshNpmDefinitions) {
			final IStatus refreshStatus = libManager.registerAllExternalProjects(subMonitor.newChild(1));
			if (!refreshStatus.isOK()) {
				throw new InvocationTargetException(new CoreException(refreshStatus));
			}
		}

		// Make sure to rebuild only those external ones that are not in the workspace.
		// Get all accessible workspace projects...
		final Collection<String> workspaceProjectNames = from(asList(getWorkspace().getRoot().getProjects()))
				.filter(p -> p.isAccessible())
				.transform(p -> p.getName())
				.toSet();

		// And build all those externals that has no corresponding workspace project.
		final Collection<N4JSExternalProject> toBuild = from(projectProvider.getProjects())
				.filter(p -> !workspaceProjectNames.contains(p.getName())).toList();

		final Collection<IProject> workspaceProjectsToRebuild = collector.getWSProjectsDependendingOn(toBuild);

		externalBuilder.build(toBuild, subMonitor.newChild(1));

		Map<String, String> args = new HashMap<>();
		IBuildFlag.FORGET_BUILD_STATE_ONLY.addToMap(args);
		builderStateDiscarder.forgetLastBuildState(workspaceProjectsToRebuild, args);
	}
}
