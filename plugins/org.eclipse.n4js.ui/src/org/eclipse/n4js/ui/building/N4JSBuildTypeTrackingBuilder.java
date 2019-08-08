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
package org.eclipse.n4js.ui.building;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.RaceDetectionHelper;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.containers.N4JSProjectsStateHelper;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildScheduler;
import org.eclipse.n4js.ui.external.OutdatedPackageJsonQueue;
import org.eclipse.n4js.ui.internal.N4JSProjectDependencyStrategy;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * A customized XtextBuilder that uses the {@link N4JSBuildTypeTracker} so other clients can get access to the build
 * type during the currently run build.
 */
@SuppressWarnings("restriction")
public class N4JSBuildTypeTrackingBuilder extends XtextBuilder {

	private final static Logger LOGGER = Logger.getLogger(N4JSBuildTypeTrackingBuilder.class);

	@Inject
	@BuilderState
	private IBuildLogger builderStateLogger;

	private EclipseExternalIndexSynchronizer externalIndexSynchronizer;
	private ExternalLibraryBuildScheduler externalLibraryBuildJobProvider;
	private N4JSProjectDependencyStrategy projectDependencyStrategy;
	private N4JSProjectsStateHelper projectsStateHelper;
	private OutdatedPackageJsonQueue outdatedPackageJsonQueue;

	@Inject
	private void injectSharedContributions(ISharedStateContributionRegistry registry) {
		this.externalLibraryBuildJobProvider = registry
				.getSingleContributedInstance(ExternalLibraryBuildScheduler.class);
		this.externalIndexSynchronizer = registry
				.getSingleContributedInstance(EclipseExternalIndexSynchronizer.class);
		this.projectsStateHelper = registry
				.getSingleContributedInstance(N4JSProjectsStateHelper.class);
		this.outdatedPackageJsonQueue = registry
				.getSingleContributedInstance(OutdatedPackageJsonQueue.class);

		try {
			this.projectDependencyStrategy = registry.getSingleContributedInstance(N4JSProjectDependencyStrategy.class);
		} catch (RuntimeException e) {
			// happens if the contribution is not part of the loaded bundles, e.g. in types specific tests
			LOGGER.warn("Building projects based on default dependencies but without "
					+ N4JSProjectDependencyStrategy.class);
		}
	}

	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		IProject project = getProject();
		try {
			RaceDetectionHelper.log("About to build %s", project);

			SubMonitor builderMonitor = toBuilderMonitor(monitor, 1100);

			/*
			 * Make sure that announced changes to the libraries have been actually processed
			 */
			externalLibraryBuildJobProvider.buildExternalProjectsNow(builderMonitor.split(50));

			IProject[] result = super.build(kind, args, builderMonitor.split(1000, SubMonitor.SUPPRESS_SETTASKNAME));
			/*
			 * Here we suffer from a race between the builder and the listener to project changes. The listener is
			 * supposed to update the project description on change. The build needs to return the references as
			 * configured in the project description.
			 *
			 * We cannot sync the builder with the listener due to limitations of the eclipse resource model thus we
			 * will bypass this mechanism and obtain the dependencies directly from the project.
			 *
			 * Dynamic references have been superseded in Eclipse Photon anyways :(
			 */
			List<IProject> dependencies = projectDependencyStrategy != null
					? projectDependencyStrategy.getProjectDependencies(project, true)
					: null;
			if (dependencies == null) {
				RaceDetectionHelper.log("Returning project results since dependencies cannot be determined");
				RaceDetectionHelper.log("%s depends on %s", project, Arrays.toString(result));
				return result;
			}
			/*
			 * And merge them with the static project references that are persisted to disc.
			 */
			IProject[] staticReferences = project.getDescription().getReferencedProjects();
			if (dependencies.isEmpty()) {
				RaceDetectionHelper.log("Returning static project results since dependencies are empty");
				RaceDetectionHelper.log("%s depends on %s", project, Arrays.toString(result));
				return staticReferences;
			}
			Set<IProject> asSet = Sets.newLinkedHashSet(FluentIterable.from(dependencies).append(staticReferences));
			result = asSet.toArray(new IProject[0]);
			RaceDetectionHelper.log("Returning computed results");
			RaceDetectionHelper.log("%s depends on %s", project, Arrays.toString(result));
			return result;
		} finally {
			stopwatch.stop();
			if (LOGGER.isDebugEnabled()) {
				final String msg = "Building " + project.getName() + " took " + stopwatch.elapsed(TimeUnit.SECONDS)
						+ " seconds";
				LOGGER.debug(msg);
			}
		}

	}

	/**
	 * Overridden only for logging purposes. Does nothing else but calls super.
	 *
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		builderStateLogger.log("N4JSBuildTypeTrackingBuilder.incrementalBuild() >>>");
		builderStateLogger.log("Resource delta: " + delta);
		super.incrementalBuild(delta, monitor);
		builderStateLogger.log("N4JSBuildTypeTrackingBuilder.incrementalBuild() <<<");
	}

	@Override
	protected void doClean(ToBeBuilt toBeBuilt, Set<String> removedProjects, IProgressMonitor monitor)
			throws CoreException {

		IProject project = getProject();
		projectsStateHelper.clearProjectCache(project);
		runWithBuildType(monitor, BuildType.CLEAN, (m) -> super.doClean(toBeBuilt, removedProjects, m));
	}

	@Override
	protected void doBuild(ToBeBuilt toBeBuilt, Set<String> removedProjects, IProgressMonitor monitor, BuildType type)
			throws CoreException, OperationCanceledException {
		// if we built it, we don't have to treat it as deleted first
		toBeBuilt.getToBeDeleted().removeAll(toBeBuilt.getToBeUpdated());

		OutdatedPackageJsonQueue.Task task = outdatedPackageJsonQueue.exhaust();
		try {
			if (!task.isEmpty()) {
				externalIndexSynchronizer.checkAndClearIndex(monitor);
			}
			toBeBuilt.getToBeDeleted().addAll(task.getToBeBuilt().getToBeDeleted());
			toBeBuilt.getToBeUpdated().addAll(task.getToBeBuilt().getToBeUpdated());
			RaceDetectionHelper.log("%s", toBeBuilt);
			runWithBuildType(monitor, type, (m) -> superDoBuild(toBeBuilt, removedProjects, m, type));
		} catch (Exception e) {
			task.reschedule();
			throw e;
		}
	}

	private void runWithBuildType(IProgressMonitor monitor, BuildType type, IWorkspaceRunnable runMe)
			throws CoreException,
			OperationCanceledException {
		try {
			// externalIndexSynchronizer.checkAndSetOutOfSyncMarkers(); // GH-1124: reconsider this
			N4JSBuildTypeTracker.setBuildType(getProject(), type);
			runMe.run(monitor);
			getProject().touch(monitor);
		} catch (OperationCanceledException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("Error in n4js-build", e);
			throw e;
		} finally {
			N4JSBuildTypeTracker.clearBuildType(getProject());
		}
	}

	@Inject
	private ReflectExtensions reflector;

	private QueuedBuildData getQueuedBuildData() {
		return optimisticGet("queuedBuildData");
	}

	private IBuilderState getBuilderState() {
		return optimisticGet("builderState");
	}

	private <T> T optimisticGet(String fieldName) {
		try {
			return reflector.get(this, fieldName);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private void superDoBuild(ToBeBuilt toBeBuilt, Set<String> removedProjects, IProgressMonitor monitor,
			BuildType type) {
		// return early if there's nothing to do.
		// we reuse the isEmpty() impl from BuildData assuming that it doesnT access the ResourceSet which is still null
		// and would be expensive to create.
		boolean indexingOnly = type == BuildType.RECOVERY;
		QueuedBuildData queuedBuildData = getQueuedBuildData();
		if (isNoop(toBeBuilt, queuedBuildData, indexingOnly))
			return;

		SubMonitor progress = toBuilderMonitor(monitor, 1);

		IProject project = getProject();
		ResourceSet resourceSet = createResourceSet(project);
		BuildData buildData = new BuildData(getProject().getName(), resourceSet, toBeBuilt, queuedBuildData,
				indexingOnly, this::needRebuild, removedProjects);
		getBuilderState().update(buildData, progress.split(1, SubMonitor.SUPPRESS_NONE));
		if (!indexingOnly) {
			try {
				project.getWorkspace().checkpoint(false);
			} catch (NoClassDefFoundError e) { // guard against broken Eclipse installations / bogus project
												// configuration
				throw new RuntimeException(e);
			}
		}
	}

	private SubMonitor toBuilderMonitor(IProgressMonitor monitor, int ticks) {
		monitor.subTask("Building " + getProject().getName());
		SubMonitor progress = SubMonitor.convert(monitor, ticks);
		return progress;
	}

	private ResourceSet createResourceSet(IProject project) {
		ResourceSet resourceSet = getResourceSetProvider().get(project);
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE, Boolean.TRUE);
		return resourceSet;
	}

	private boolean isNoop(ToBeBuilt toBeBuilt, QueuedBuildData queuedBuildData,
			boolean indexingOnly) {
		return new BuildData(getProject().getName(), null, toBeBuilt, queuedBuildData, indexingOnly, this::needRebuild)
				.isEmpty();
	}

}
