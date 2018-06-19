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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.internal.RaceDetectionHelper;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.ui.external.ExternalLibraryBuildScheduler;
import org.eclipse.n4js.ui.internal.N4MFProjectDependencyStrategy;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.Messages;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.ToBeBuiltComputer;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.ui.XtextProjectHelper;
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

	private N4MFProjectDependencyStrategy projectDependencyStrategy;

	@Inject
	private void injectSharedContributions(ISharedStateContributionRegistry registry) {
		this.externalLibraryBuildJobProvider = registry
				.getSingleContributedInstance(ExternalLibraryBuildScheduler.class);
		this.externalIndexSynchronizer = registry
				.getSingleContributedInstance(EclipseExternalIndexSynchronizer.class);
		try {
			this.projectDependencyStrategy = registry.getSingleContributedInstance(N4MFProjectDependencyStrategy.class);
		} catch (RuntimeException e) {
			// happens if the contribution is not part of the loaded bundles, e.g. in types specific tests
			LOGGER.warn("Building projects based on default dependencies but without "
					+ N4MFProjectDependencyStrategy.class);
		}
	}

	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			RaceDetectionHelper.log("About to build %s", getProject());

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
					? projectDependencyStrategy.getProjectDependencies(getProject(), true)
					: null;
			if (dependencies == null) {
				RaceDetectionHelper.log("Returning project results since dependencies cannot be determined");
				RaceDetectionHelper.log("%s depends on %s", getProject(), Arrays.toString(result));
				return result;
			}
			/*
			 * And merge them with the static project references that are persisted to disc.
			 */
			IProject[] staticReferences = getProject().getDescription().getReferencedProjects();
			if (dependencies.isEmpty()) {
				RaceDetectionHelper.log("Returning static project results since dependencies are empty");
				RaceDetectionHelper.log("%s depends on %s", getProject(), Arrays.toString(result));
				return staticReferences;
			}
			Set<IProject> asSet = Sets.newLinkedHashSet(FluentIterable.from(dependencies).append(staticReferences));
			result = asSet.toArray(new IProject[0]);
			RaceDetectionHelper.log("Returning computed results");
			RaceDetectionHelper.log("%s depends on %s", getProject(), Arrays.toString(result));
			return result;
		} finally {
			stopwatch.stop();
			if (LOGGER.isDebugEnabled()) {
				final String msg = "Building " + getProject().getName() + " took " + stopwatch.elapsed(TimeUnit.SECONDS)
						+ " seconds";
				LOGGER.debug(msg);
				System.out.println(msg);
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
		superIncrementalBuild(delta, monitor);
		builderStateLogger.log("N4JSBuildTypeTrackingBuilder.incrementalBuild() <<<");
	}

	private void superIncrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, Messages.XtextBuilder_CollectingResources, 10);
		progress.subTask(Messages.XtextBuilder_CollectingResources);

		if (getQueuedBuildData().needRebuild(getProject())) {
			needRebuild();
		}

		final ToBeBuilt toBeBuilt = new ToBeBuilt();
		final ToBeBuiltComputer toBeBuiltComputer = getToBeBuiltComputer();
		// change is here
		IResourceDeltaVisitor visitor = createDeltaVisitor(toBeBuiltComputer, toBeBuilt, progress);
		delta.accept(visitor);
		if (progress.isCanceled())
			throw new OperationCanceledException();
		progress.worked(2);
		// and here
		processedAbsentReferencedProjects(visitor);

		doBuild(toBeBuilt, progress.newChild(8), BuildType.INCREMENTAL);
	}

	/**
	 * Creates a visitor that is used to traverse the information that is obtained from {@link #getDelta(IProject)}. It
	 * accumulates its findings in the given <code>toBeBuilt</code>.
	 */
	private IResourceDeltaVisitor createDeltaVisitor(ToBeBuiltComputer toBeBuiltComputer, final ToBeBuilt toBeBuilt,
			final SubMonitor progress) {
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
			@Override
			public boolean visit(IResourceDelta delta) throws CoreException {
				if (progress.isCanceled())
					throw new OperationCanceledException();
				if (delta.getResource() instanceof IProject) {
					return true;
				}
				if (delta.getResource() instanceof IStorage) {
					if (delta.getKind() == IResourceDelta.REMOVED) {
						return toBeBuiltComputer.removeStorage(null, toBeBuilt, (IStorage) delta.getResource());
					} else if (delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.CHANGED) {
						return toBeBuiltComputer.updateStorage(null, toBeBuilt, (IStorage) delta.getResource());
					}
				}
				return true;
			}
		};
		return visitor;
	}

	/**
	 * Obtain the deltas for the projects we were previously interested in and process them too, if they do no longer
	 * have the xtext nature or if they are no longer accessible.
	 *
	 * @param visitor
	 *            the processor.
	 * @throws CoreException
	 *             if something goes down the drain.
	 */
	private void processedAbsentReferencedProjects(IResourceDeltaVisitor visitor) throws CoreException {
		final IProject[] interestingProjects = optimisticInvoke("getInterestingProjects");
		for (IProject more : interestingProjects) {
			if (!XtextProjectHelper.hasNature(more)) {
				IResourceDelta interestingDelta = getDelta(more);
				if (interestingDelta != null) {
					interestingDelta.accept(visitor);
				}
			}
		}
	}

	@Override
	protected void fullBuild(final IProgressMonitor monitor, boolean isRecoveryBuild) throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, 10);
		final ToBeBuiltComputer toBeBuiltComputer = getToBeBuiltComputer();
		IProject project = getProject();
		ToBeBuilt toBeBuilt = isRecoveryBuild
				? toBeBuiltComputer.updateProjectNewResourcesOnly(project, progress.newChild(2))
				: toBeBuiltComputer.updateProject(project, progress.newChild(2));

		// change is here
		IResourceDeltaVisitor visitor = createDeltaVisitor(toBeBuiltComputer, toBeBuilt, progress);
		processedAbsentReferencedProjects(visitor);

		doBuild(toBeBuilt, progress.newChild(8),
				isRecoveryBuild
						? BuildType.RECOVERY
						: BuildType.FULL);
	}

	@Override
	protected void doClean(ToBeBuilt toBeBuilt, IProgressMonitor monitor)
			throws CoreException {

		runWithBuildType(monitor, BuildType.CLEAN, (m) -> super.doClean(toBeBuilt, m));
	}

	@Override
	protected void doBuild(ToBeBuilt toBeBuilt, IProgressMonitor monitor, BuildType type) throws CoreException,
			OperationCanceledException {
		// if we built it, we don't have to treat it as deleted first
		toBeBuilt.getToBeDeleted().removeAll(toBeBuilt.getToBeUpdated());

		RaceDetectionHelper.log("%s", toBeBuilt);
		runWithBuildType(monitor, type, (m) -> superDoBuild(toBeBuilt, m, type));
	}

	private void runWithBuildType(IProgressMonitor monitor, BuildType type, IWorkspaceRunnable runMe)
			throws CoreException,
			OperationCanceledException {
		try {
			checkExternalLibraries();
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

	private ToBeBuiltComputer getToBeBuiltComputer() {
		return optimisticGet("toBeBuiltComputer");
	}

	private <T> T optimisticGet(String fieldName) {
		try {
			return reflector.get(this, fieldName);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T optimisticInvoke(String methodName) {
		try {
			return (T) reflector.invoke(this, methodName);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private void superDoBuild(ToBeBuilt toBeBuilt, IProgressMonitor monitor, BuildType type) {
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
		BuildData buildData = new BuildData(project.getName(), resourceSet, toBeBuilt, queuedBuildData,
				indexingOnly);
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
		return new BuildData(getProject().getName(), null, toBeBuilt, queuedBuildData, indexingOnly).isEmpty();
	}

	private void checkExternalLibraries() {
		externalIndexSynchronizer.checkAndSetOutOfSyncMarkers();
	}
}
