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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.SubMonitor.SUPPRESS_BEGINTASK;
import static org.eclipse.core.runtime.SubMonitor.SUPPRESS_NONE;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;
import static org.eclipse.n4js.utils.collections.Arrays2.transform;
import static org.eclipse.n4js.utils.resources.ExternalProjectBuildOrderProvider.getBuildOrder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.events.BuildManager;
import org.eclipse.core.internal.resources.BuildConfiguration;
import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.n4js.external.ExternalProjectsCollector;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.ToBeBuiltComputer;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Exceptions;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper class for building projects outside from the {@link IWorkspace Eclipse workspace} directly with the
 * {@link BuildManager build manager}.
 */
@SuppressWarnings("restriction")
@Singleton
public class ExternalLibraryBuilderHelper {

	private static final Logger LOGGER = Logger.getLogger(ExternalLibraryBuilderHelper.class);

	/**
	 * Function for converting a {@link IProject project} into the corresponding {@link IBuildConfiguration build
	 * configuration}.
	 */
	private static Function<IProject, IBuildConfiguration> TO_CONFIG_FUNC = project -> {
		try {
			return project.getActiveBuildConfig();
		} catch (final CoreException e) {
			LOGGER.error("Error while getting active build configuration for project: " + project, e);
			return new BuildConfiguration(project);
		}
	};

	/**
	 * Function for converting several {@link IProject project} instances into their corresponding
	 * {@link IBuildConfiguration build configuration} representations. The order of the build configuration elements
	 * will be identical with the order of the projects argument.
	 */
	private static Function<Iterable<? extends IProject>, IBuildConfiguration[]> TO_CONFIGS_FUNC = projects -> {
		return from(projects).transform(TO_CONFIG_FUNC).toArray(IBuildConfiguration.class);
	};

	@Inject
	private IN4JSCore core;

	@Inject
	private IBuilderState builderState;

	@Inject
	private QueuedBuildData queuedBuildData;

	@Inject
	private ToBeBuiltComputer builtComputer;

	@Inject
	private ExternalProjectsCollector collector;

	/**
	 * Performs a full build on all registered and available external libraries.
	 * <p>
	 * This is a blocking operation.
	 */
	public void build() {
		build(new NullProgressMonitor());
	}

	/**
	 * Sugar for {@link #build()} but the operation can be monitored via the monitor argument.
	 *
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public void build(final IProgressMonitor monitor) {
		build(collector.collectExternalProjects(), monitor);
	}

	/**
	 * Full builds the project.
	 *
	 * @param project
	 *            the project to build.
	 */
	public void build(final IProject project) {
		build(project, new NullProgressMonitor());
	}

	/**
	 * Performs a full build on the given project. Same as {@link #build(IProject)} but a monitor can be provided for
	 * the running process.
	 *
	 * @param project
	 *            the project to build.
	 * @param monitor
	 *            the monitor for the full build operation.
	 */
	public void build(final IProject project, final IProgressMonitor monitor) {
		build(new IBuildConfiguration[] { new BuildConfiguration(project) }, monitor);
	}

	/**
	 * Sugar for performing a full build on multiple {@link IProject project} instances.
	 *
	 * @param projects
	 *            the projects that has to be build.
	 * @param monitor
	 *            monitor for the build process.
	 */
	public void build(final Iterable<? extends IProject> projects, final IProgressMonitor monitor) {
		build(TO_CONFIGS_FUNC.apply(projects), monitor);
	}

	/**
	 * Full builds the projects given as an array of build configuration.
	 *
	 * @param buildConfigs
	 *            the build configurations representing the projects to be built.
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public void build(final IBuildConfiguration[] buildConfigs, final IProgressMonitor monitor) {
		doPerformOperation(buildConfigs, BuildOperation.BUILD, monitor);
	}

	/**
	 * Cleans all registered and available external libraries.
	 * <p>
	 * This is a blocking operation.
	 */
	public void clean() {
		clean(new NullProgressMonitor());
	}

	/**
	 * Sugar for {@link #clean()} but the operation can be monitored via the monitor argument.
	 *
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public void clean(final IProgressMonitor monitor) {
		clean(collector.collectExternalProjects(), monitor);
	}

	/**
	 * Cleans the given project without rebuilding it.
	 *
	 * @param project
	 *            the project that has to be cleaned (without rebuilding it).
	 */
	public void clean(final IProject project) {
		clean(project, new NullProgressMonitor());
	}

	/**
	 * Cleans the given project. Same as {@link #clean(IProject)} but additional {@link IProgressMonitor monitor} can be
	 * provided for the operation.
	 *
	 * @param project
	 *            the project that has to be cleaned (without rebuilding it).
	 * @param monitor
	 *            monitor for the clean process.
	 */
	public void clean(final IProject project, final IProgressMonitor monitor) {
		clean(new IBuildConfiguration[] { getBuildConfiguration(project) }, monitor);
	}

	/**
	 * Sugar for cleaning multiple {@link IProject project} instances.
	 *
	 * @param projects
	 *            the projects that has to be cleaned (without rebuilding it).
	 * @param monitor
	 *            monitor for the clean process.
	 */
	public void clean(final Iterable<? extends IProject> projects, final IProgressMonitor monitor) {
		clean(TO_CONFIGS_FUNC.apply(projects), monitor);
	}

	/**
	 * Performs a clean (without rebuild) on the projects given as an array of build configuration. The clean order is
	 * identical with the order of the elements in the {@code buildOrder} argument.
	 *
	 * @param buildConfigs
	 *            the build configurations representing the the projects to be cleaned.
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public void clean(final IBuildConfiguration[] buildConfigs, final IProgressMonitor monitor) {
		doPerformOperation(buildConfigs, BuildOperation.CLEAN, monitor);
	}

	private void doPerformOperation(final IBuildConfiguration[] configs, final BuildOperation operation,
			final IProgressMonitor monitor) {

		if (Arrays2.isEmpty(configs)) {
			return;
		}

		final ISchedulingRule rule = getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);

			final List<ExternalProject> projects = transform(configs, config -> (ExternalProject) config.getProject());
			final List<IBuildConfiguration> buildOrder = newArrayList(getBuildOrder(projects));
			if (BuildOperation.CLEAN.equals(operation)) {
				Collections.reverse(buildOrder);
			}

			// Remove external projects that have the workspace counterpart if it is a build operation.
			if (BuildOperation.BUILD.equals(operation)) {
				for (final Iterator<IBuildConfiguration> itr = buildOrder.iterator(); itr.hasNext(); /**/) {
					if (hasWorkspaceCounterpart(itr.next())) {
						itr.remove();
					}
				}
			}

			checkState(buildOrder.size() == configs.length,
					"Inconsistency between build configuration and the ordered projects:" +
							"\n\tInput was: " + getProjectNames(configs) +
							"\n\tOrdered was: " + getProjectNames(buildOrder));

			ensureDynamicDependenciesSetForWorkspaceProjects();
			final String prefix = Strings.toFirstUpper(operation.toString().toLowerCase());
			final String projectNames = getProjectNames(buildOrder);
			LOGGER.info(prefix + "ing external libraries: " + projectNames);
			final SubMonitor subMonitor = SubMonitor.convert(monitor, buildOrder.size());
			for (final IBuildConfiguration configuration : buildOrder) {
				final IProject project = configuration.getProject();
				LOGGER.info(prefix + "ing external library: " + project.getName());
				operation.run(this, project, subMonitor.newChild(1));
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	/**
	 * Returns the {@link ISchedulingRule scheduling rule} used by {@link ExternalLibraryBuilderHelper} while
	 * {@link #clean(IBuildConfiguration[], IProgressMonitor) cleaning} or
	 * {@link #build(IBuildConfiguration[], IProgressMonitor) building} external libraries.
	 *
	 * This method corresponds to {@link IncrementalProjectBuilder#getRule(int, Map)}.
	 */
	public ISchedulingRule getRule() {
		// FIXME add comment explaining rationale of this scheduling rule
		return ResourcesPlugin.getWorkspace().getRoot();
		// return ResourcesPlugin.getWorkspace().getRuleFactory().buildRule(); // FIXME explain why not using this
	}

	/**
	 * Returns with {@code true} if the external project is accessible in the workspace as well.
	 */
	private boolean hasWorkspaceCounterpart(IBuildConfiguration config) {
		final URI uri = URI.createPlatformResourceURI(config.getProject().getName(), true);
		final IN4JSProject n4Project = core.findProject(uri).orNull();
		return null != n4Project && n4Project.exists() && !n4Project.isExternal();
	}

	private String getProjectNames(final Iterable<IBuildConfiguration> buildOrder) {
		return Iterables.toString(from(buildOrder).transform(c -> c.getProject().getName()));
	}

	private String getProjectNames(final IBuildConfiguration[] buildOrder) {
		return Iterables.toString(transform(buildOrder, c -> c.getProject().getName()));
	}

	private IBuildConfiguration getBuildConfiguration(final IProject project) {
		return TO_CONFIG_FUNC.apply(project);
	}

	/**
	 * Make sure the project description is available and cached for each workspace projects. This is important to avoid
	 * performing a workspace operation (with no scheduling rule) when setting the dynamic project references for each
	 * project.
	 *
	 * See: ProjectDescriptionLoadListener#updateProjectReferencesIfNecessary(IProject)
	 */
	private void ensureDynamicDependenciesSetForWorkspaceProjects() {
		for (final IProject project : getWorkspace().getRoot().getProjects()) {
			final org.eclipse.emf.common.util.URI uri = createPlatformResourceURI(project.getName(), true);
			final IN4JSProject n4Project = core.findProject(uri).get();
			if (null != n4Project) {
				n4Project.getProjectId(); // This will trigger dynamic project reference update.
			}
		}
	}

	/**
	 * Enumeration of strategies to encapsulate the clean/build operations.
	 */
	private enum BuildOperation {

		/**
		 * Operation for indexing external library.
		 */
		BUILD {

			@Override
			protected ToBeBuilt getToBeBuilt(ToBeBuiltComputer computer, IProject project, IProgressMonitor monitor) {
				try {
					return computer.updateProject(project, monitor);
				} catch (OperationCanceledException | CoreException e) {
					final String name = project.getName();
					LOGGER.error("Error occurred while calculating to be build data for '" + name + "' project.", e);
					throw Exceptions.sneakyThrow(e);
				}
			}

		},

		/**
		 * Operation for removing the Xtext index entries for a particular external project.
		 */
		CLEAN {

			@Override
			protected ToBeBuilt getToBeBuilt(ToBeBuiltComputer computer, IProject project, IProgressMonitor monitor) {
				return computer.removeProject(project, monitor);
			}

		};

		/**
		 * Calculates the {@link ToBeBuilt} for the project.
		 *
		 * @param computer
		 *            the computer for the calculation.
		 * @param project
		 *            the object of the operation.
		 * @param monitor
		 *            the monitor for the process.
		 * @return the calculated {@link ToBeBuilt} instance.
		 */
		abstract ToBeBuilt getToBeBuilt(ToBeBuiltComputer computer, IProject project, IProgressMonitor monitor);

		/**
		 * Runs the operation in a blocking fashion.
		 *
		 * @param helper
		 *            the build helper to get the injected services.
		 * @param project
		 *            the project to clean/build.
		 * @param monitor
		 *            monitor for the operation.
		 */
		private void run(final ExternalLibraryBuilderHelper helper, IProject project, IProgressMonitor monitor) {

			checkArgument(project instanceof ExternalProject, "Expected external project: " + project);

			final SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
			final ToBeBuiltComputer computer = helper.builtComputer;
			final IProgressMonitor computeMonitor = subMonitor.newChild(1, SUPPRESS_BEGINTASK);
			monitor.setTaskName("Collecting resource for '" + project.getName() + "'...");
			final ToBeBuilt toBeBuilt = getToBeBuilt(computer, project, computeMonitor);

			if (toBeBuilt.getToBeDeleted().isEmpty() && toBeBuilt.getToBeUpdated().isEmpty()) {
				subMonitor.newChild(1, SUPPRESS_NONE).worked(1);
				return;
			}

			try {

				final IN4JSCore core = helper.core;
				final QueuedBuildData queuedBuildData = helper.queuedBuildData;
				final IBuilderState builderState = helper.builderState;

				final ExternalProject externalProject = (ExternalProject) project;
				final String path = externalProject.getExternalResource().getAbsolutePath();
				final URI uri = URI.createFileURI(path);
				final IN4JSProject n4Project = core.findProject(uri).orNull();

				ResourceSet resourceSet = null;

				try {

					resourceSet = core.createResourceSet(Optional.of(n4Project));
					if (!resourceSet.getLoadOptions().isEmpty()) {
						resourceSet.getLoadOptions().clear();
					}
					resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE, Boolean.TRUE);
					if (resourceSet instanceof ResourceSetImpl) {
						((ResourceSetImpl) resourceSet).setURIResourceMap(newHashMap());
					}

					final BuildData buildData = new BuildData(
							project.getName(),
							resourceSet,
							toBeBuilt,
							queuedBuildData,
							true /* indexingOnly */);

					monitor.setTaskName("Building '" + project.getName() + "'...");
					final IProgressMonitor buildMonitor = subMonitor.newChild(1, SUPPRESS_BEGINTASK);
					builderState.update(buildData, buildMonitor);

				} finally {

					if (null != resourceSet) {
						resourceSet.getResources().clear();
						resourceSet.eAdapters().clear();
					}

				}

			} catch (Exception e) {
				final String message = "Error occurred while " + toString().toLowerCase() + "ing external library "
						+ project.getName() + ".";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			}

		}

	}

}
