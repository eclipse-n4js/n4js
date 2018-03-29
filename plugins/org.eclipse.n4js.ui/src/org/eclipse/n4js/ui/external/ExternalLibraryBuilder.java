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
import static com.google.common.collect.Maps.newHashMap;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.eclipse.core.runtime.SubMonitor.SUPPRESS_BEGINTASK;
import static org.eclipse.core.runtime.SubMonitor.SUPPRESS_NONE;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.events.BuildManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.smith.ClosableMeasurement;
import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.ui.external.ComputeProjectOrder.VertexOrder;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.ToBeBuiltComputer;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Exceptions;

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
public class ExternalLibraryBuilder {
	@SuppressWarnings("unused") // necessary for dcBuildExt
	private static DataCollector dcLibMngr = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Library Manager");
	private static DataCollector dcBuildExt = DataCollectors.INSTANCE
			.getOrCreateDataCollector("Build External Library", "Library Manager");

	private static Logger LOGGER = Logger.getLogger(ExternalLibraryBuilder.class);

	@Inject
	private IN4JSCore core;

	@Inject
	private IBuilderState builderState;

	@Inject
	private QueuedBuildData queuedBuildData;

	@Inject
	private BuildOrderComputer builtOrderComputer;

	@Inject
	private ToBeBuiltComputer builtComputer;

	@Inject
	private ExternalProjectProvider projectProvider;

	/**
	 * Performs a full build on all registered and available external libraries.
	 * <p>
	 * This is a blocking operation.
	 */
	public List<IProject> build() {
		return build(new NullProgressMonitor());
	}

	/**
	 * Builds all external projects. The operation can be monitored via the monitor argument.
	 *
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public List<IProject> build(IProgressMonitor monitor) {
		return build(projectProvider.getProjects(), monitor);
	}

	/**
	 * Full builds the project.
	 *
	 * @param project
	 *            the project to build.
	 */
	public List<IProject> build(N4JSExternalProject project) {
		return build(project, new NullProgressMonitor());
	}

	/**
	 * Performs a full build on the given project. Same as {@link #build(N4JSExternalProject)} but a monitor can be
	 * provided for the running process.
	 *
	 * @param project
	 *            the project to build.
	 * @param monitor
	 *            the monitor for the full build operation.
	 */
	public List<IProject> build(N4JSExternalProject project, IProgressMonitor monitor) {
		return build(new N4JSExternalProject[] { project }, monitor);
	}

	/**
	 * Sugar for performing a full build on multiple {@link IProject project} instances.
	 *
	 * @param projects
	 *            the projects that has to be build.
	 * @param monitor
	 *            monitor for the build process.
	 */
	public List<IProject> build(Collection<N4JSExternalProject> projects, IProgressMonitor monitor) {
		return build(projects.toArray(new N4JSExternalProject[0]), monitor);
	}

	/**
	 * Full builds the projects given as an array of build configuration.
	 * <p>
	 * For information on locking see {@link #getRule()}.
	 *
	 * @param buildConfigs
	 *            the build configurations representing the projects to be built.
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 *
	 * @throws IllegalArgumentException
	 *             if a scheduling rule is in effect that does not contain the rule returned by {@link #getRule()}. For
	 *             more info, see {@link #getRule() here}.
	 */
	public List<IProject> build(N4JSExternalProject[] buildConfigs, IProgressMonitor monitor) {
		return doPerformOperation(buildConfigs, BuildOperation.BUILD, monitor);
	}

	/**
	 * Cleans all registered and available external libraries.
	 * <p>
	 * This is a blocking operation.
	 */
	public List<IProject> clean() {
		return clean(new NullProgressMonitor());
	}

	/**
	 * Cleans all external projects. The operation can be monitored via the monitor argument.
	 *
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 */
	public List<IProject> clean(IProgressMonitor monitor) {
		return clean(projectProvider.getProjects(), monitor);
	}

	/**
	 * Cleans the given project without rebuilding it.
	 *
	 * @param project
	 *            the project that has to be cleaned (without rebuilding it).
	 */
	public List<IProject> clean(N4JSExternalProject project) {
		return clean(project, new NullProgressMonitor());
	}

	/**
	 * Cleans the given project. Same as {@link #clean(N4JSExternalProject)} but additional {@link IProgressMonitor
	 * monitor} can be provided for the operation.
	 *
	 * @param project
	 *            the project that has to be cleaned (without rebuilding it).
	 * @param monitor
	 *            monitor for the clean process.
	 */
	public List<IProject> clean(N4JSExternalProject project, IProgressMonitor monitor) {
		return clean(new N4JSExternalProject[] { project }, monitor);
	}

	/**
	 * Performs a clean (without rebuild) on the projects given as an array of build configuration. The clean order is
	 * identical with the order of the elements in the {@code buildOrder} argument.
	 * <p>
	 * For information on locking see {@link #getRule()}.
	 *
	 * @param projects
	 *            the projects representing the the projects to be cleaned.
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 *
	 * @throws IllegalArgumentException
	 *             if a scheduling rule is in effect that does not contain the rule returned by {@link #getRule()}. For
	 *             more info, see {@link #getRule() here}.
	 */
	public List<IProject> clean(Collection<N4JSExternalProject> projects, IProgressMonitor monitor) {
		return clean(projects.toArray(new N4JSExternalProject[0]), monitor);
	}

	/**
	 * Performs a clean (without rebuild) on the projects given as an array of build configuration. The clean order is
	 * identical with the order of the elements in the {@code buildOrder} argument.
	 * <p>
	 * For information on locking see {@link #getRule()}.
	 *
	 * @param projects
	 *            the projects representing the the projects to be cleaned.
	 * @param monitor
	 *            the monitor for the progress. Must not be {@code null}.
	 *
	 * @throws IllegalArgumentException
	 *             if a scheduling rule is in effect that does not contain the rule returned by {@link #getRule()}. For
	 *             more info, see {@link #getRule() here}.
	 */
	public List<IProject> clean(N4JSExternalProject[] projects, IProgressMonitor monitor) {
		return doPerformOperation(projects, BuildOperation.CLEAN, monitor);
	}

	private List<IProject> doPerformOperation(N4JSExternalProject[] projects, BuildOperation operation,
			IProgressMonitor monitor) {

		if (projects == null || projects.length == 0) {
			return Collections.emptyList();
		}

		ISchedulingRule rule = getRule();
		try {
			Job.getJobManager().beginRule(rule, monitor);

			VertexOrder<IN4JSProject> buildOrder = builtOrderComputer.getBuildOrder(projects);
			// wrap as Arrays.asList returns immutable list
			List<IN4JSProject> buildOrderList = new ArrayList<>(Arrays.asList(buildOrder.vertexes));
			if (BuildOperation.CLEAN.equals(operation)) {
				Collections.reverse(buildOrderList);
			}

			// Remove external projects that have the workspace counterpart if it is a build operation.
			if (BuildOperation.BUILD.equals(operation)) {
				for (Iterator<IN4JSProject> itr = buildOrderList.iterator(); itr.hasNext();) {
					if (hasWorkspaceCounterpart(itr.next())) {
						itr.remove();
					}
				}
			}

			ensureDynamicDependenciesSetForWorkspaceProjects();
			String prefix = Strings.toFirstUpper(operation.toString().toLowerCase());
			String projectNames = getProjectNames(buildOrderList);
			LOGGER.info(prefix + "ing external libraries: " + projectNames);
			SubMonitor subMonitor = SubMonitor.convert(monitor, buildOrderList.size());

			List<IProject> actualBuildOrderList = new LinkedList<>();
			for (IN4JSProject project : buildOrderList) {
				LOGGER.info(prefix + "ing external library: " + project.getProjectId());

				N4JSEclipseProject n4EclPrj = (N4JSEclipseProject) project; // bold cast
				operation.run(this, n4EclPrj, subMonitor.newChild(1));

				IProject iProject = n4EclPrj.getProject();
				actualBuildOrderList.add(iProject);
			}

			return actualBuildOrderList;
		} finally {
			Job.getJobManager().endRule(rule);
		}
	}

	/**
	 * Returns the {@link ISchedulingRule scheduling rule} used by {@link ExternalLibraryBuilder} while
	 * {@link #clean(N4JSExternalProject[], IProgressMonitor) cleaning} or
	 * {@link #build(N4JSExternalProject[], IProgressMonitor) building} external libraries.
	 * <p>
	 * Clients that want to use a custom scheduling rule around several invocations of the clean/build methods of
	 * {@code ExternalLibraryBuilderHelper} must use a scheduling rule at least as wide as the rule returned by this
	 * method (custom rule must "contain" rule returned by this method); otherwise an {@link IllegalArgumentException}
	 * will be thrown as per specification of method {@link IJobManager#beginRule(ISchedulingRule, IProgressMonitor)
	 * beginRule()}. However, this is optional, i.e. client code need not use a custom scheduling rule at all if there
	 * is only a single call to a clean/build method or if no locking is required between subsequent calls.
	 * <p>
	 * This method corresponds to {@link IncrementalProjectBuilder#getRule(int, Map)}.
	 */
	public ISchedulingRule getRule() {
		// Rationale for using workspace root as scheduling rule:
		// 1) the external libraries are not in the ordinary Eclipse workspace, so in theory it would be tempting to say
		// we do not need a lock on the workspace; however, when cleaning/building external libraries we use the Xtext
		// builder and because it is working on state shared across the entire Eclipse instance (e.g. the singleton
		// QueuedBuildData), we have to make sure no other Xtext build is running in parallel while we are building
		// the external libraries. Otherwise we might run into ConcurrentModificationExcpetions, etc.
		// 2) we do not use IResourceRuleFactory#buildRule() because we want to control the scope of the scheduling rule
		// ourselves to make sure no other build is happening anywhere at the same time (within same Eclipse instance).
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	/**
	 * Returns with {@code true} if the external project is accessible in the workspace as well.
	 */
	private boolean hasWorkspaceCounterpart(IN4JSProject project) {
		URI uri = URI.createPlatformResourceURI(project.getProjectId(), true);
		IN4JSProject n4Project = core.findProject(uri).orNull();
		return null != n4Project && n4Project.exists() && !n4Project.isExternal();
	}

	private String getProjectNames(Iterable<IN4JSProject> projects) {
		return Iterables.toString(from(projects).transform(p -> p.getProjectId()));
	}

	/**
	 * Make sure the project description is available and cached for each workspace projects. This is important to avoid
	 * performing a workspace operation (with no scheduling rule) when setting the dynamic project references for each
	 * project.
	 *
	 * See: ProjectDescriptionLoadListener#updateProjectReferencesIfNecessary(IProject)
	 */
	private void ensureDynamicDependenciesSetForWorkspaceProjects() {
		for (IProject project : getWorkspace().getRoot().getProjects()) {
			org.eclipse.emf.common.util.URI uri = createPlatformResourceURI(project.getName(), true);
			IN4JSProject n4Project = core.findProject(uri).get();
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
					String name = project.getName();
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
		 * @param n4EclPrj
		 *            the project to clean/build.
		 * @param monitor
		 *            monitor for the operation.
		 */
		private void run(ExternalLibraryBuilder helper, N4JSEclipseProject n4EclPrj, IProgressMonitor monitor) {

			monitor.setTaskName("Collecting resource for '" + n4EclPrj.getProjectId() + "'...");
			SubMonitor subMonitor = SubMonitor.convert(monitor, 2);
			IProgressMonitor computeMonitor = subMonitor.newChild(1, SUPPRESS_BEGINTASK);

			IProject project = n4EclPrj.getProject();
			ToBeBuiltComputer computer = helper.builtComputer;
			ToBeBuilt toBeBuilt = getToBeBuilt(computer, project, computeMonitor);

			if (toBeBuilt.getToBeDeleted().isEmpty() && toBeBuilt.getToBeUpdated().isEmpty()) {
				subMonitor.newChild(1, SUPPRESS_NONE).worked(1);
				return;
			}

			try (ClosableMeasurement mesBE = dcBuildExt.getClosableMeasurement("BuildExt_" + n4EclPrj.getProjectId())) {
				IN4JSCore core = helper.core;
				QueuedBuildData queuedBuildData = helper.queuedBuildData;
				IBuilderState builderState = helper.builderState;
				ResourceSet resourceSet = null;

				try {

					resourceSet = core.createResourceSet(Optional.of(n4EclPrj));
					if (!resourceSet.getLoadOptions().isEmpty()) {
						resourceSet.getLoadOptions().clear();
					}
					resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE, Boolean.TRUE);
					if (resourceSet instanceof ResourceSetImpl) {
						((ResourceSetImpl) resourceSet).setURIResourceMap(newHashMap());
					}

					BuildData buildData = new BuildData(
							project.getName(),
							resourceSet,
							toBeBuilt,
							queuedBuildData,
							true /* indexingOnly */);

					monitor.setTaskName("Building '" + project.getName() + "'...");
					IProgressMonitor buildMonitor = subMonitor.newChild(1, SUPPRESS_BEGINTASK);
					builderState.update(buildData, buildMonitor);

				} finally {

					if (null != resourceSet) {
						resourceSet.getResources().clear();
						resourceSet.eAdapters().clear();
					}
				}

			} catch (Exception e) {
				String message = "Error occurred while " + toString().toLowerCase() + "ing external library "
						+ project.getName() + ".";
				LOGGER.error(message, e);
				throw new RuntimeException(message, e);
			}
		}

	}

}
