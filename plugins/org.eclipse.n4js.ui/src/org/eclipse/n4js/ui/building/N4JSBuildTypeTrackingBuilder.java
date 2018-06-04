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

import java.util.List;
import java.util.Map;
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
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.internal.N4MFProjectDependencyStrategy;
import org.eclipse.n4js.ui.internal.ProjectDescriptionLoadListener;
import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;
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
import com.google.inject.Injector;

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

	private N4MFProjectDependencyStrategy projectDependencyStrategy;

	@Inject
	private void injectN4MFProjectDependencyStrategy(ISharedStateContributionRegistry registry) {
		this.projectDependencyStrategy = registry.getSingleContributedInstance(N4MFProjectDependencyStrategy.class);
	}

	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			IProject[] result = super.build(kind, args,
					toBuilderMonitor(monitor).split(1, SubMonitor.SUPPRESS_SETTASKNAME));
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
			List<IProject> dependencies = projectDependencyStrategy.getProjectDependencies(getProject());
			if (dependencies.isEmpty()) {
				return result;
			}
			/*
			 * And merge them with the static project references that are persisted to disc.
			 */
			IProject[] staticReferences = getProject().getDescription().getReferencedProjects();
			return FluentIterable.from(dependencies).append(staticReferences).copyInto(Sets.newLinkedHashSet())
					.toArray(new IProject[0]);
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
		super.incrementalBuild(delta, monitor);
		builderStateLogger.log("N4JSBuildTypeTrackingBuilder.incrementalBuild() <<<");
	}

	@Override
	protected void doClean(ToBeBuilt toBeBuilt, IProgressMonitor monitor)
			throws CoreException {
		runWithBuildType(monitor, BuildType.CLEAN, (m) -> super.doClean(toBeBuilt, m));
	}

	@Override
	protected void doBuild(ToBeBuilt toBeBuilt, IProgressMonitor monitor, BuildType type) throws CoreException,
			OperationCanceledException {
		runWithBuildType(monitor, type, (m) -> superDoBuild(toBeBuilt, m, type));
	}

	private void runWithBuildType(IProgressMonitor monitor, BuildType type, IWorkspaceRunnable runMe)
			throws CoreException,
			OperationCanceledException {
		try {
			updateProjectReferencesIfNecessary();
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

	private void superDoBuild(ToBeBuilt toBeBuilt, IProgressMonitor monitor, BuildType type) {
		// return early if there's nothing to do.
		// we reuse the isEmpty() impl from BuildData assuming that it doesnT access the ResourceSet which is still null
		// and would be expensive to create.
		boolean indexingOnly = type == BuildType.RECOVERY;
		QueuedBuildData queuedBuildData = getQueuedBuildData();
		if (isNoop(toBeBuilt, queuedBuildData, indexingOnly))
			return;

		SubMonitor progress = toBuilderMonitor(monitor);

		ResourceSet resourceSet = createResourceSet();
		BuildData buildData = new BuildData(getProject().getName(), resourceSet, toBeBuilt, queuedBuildData,
				indexingOnly);
		getBuilderState().update(buildData, progress.split(1, SubMonitor.SUPPRESS_NONE));
		if (!indexingOnly) {
			try {
				getProject().getWorkspace().checkpoint(false);
			} catch (NoClassDefFoundError e) { // guard against broken Eclipse installations / bogus project
												// configuration
				throw new RuntimeException(e);
			}
		}
		resourceSet.eSetDeliver(false);
		resourceSet.getResources().clear();
		resourceSet.eAdapters().clear();
	}

	private SubMonitor toBuilderMonitor(IProgressMonitor monitor) {
		monitor.subTask("Building " + getProject().getName());
		SubMonitor progress = SubMonitor.convert(monitor, 1);
		return progress;
	}

	private ResourceSet createResourceSet() {
		ResourceSet resourceSet = getResourceSetProvider().get(getProject());
		resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.NAMED_BUILDER_SCOPE, Boolean.TRUE);
		return resourceSet;
	}

	private boolean isNoop(ToBeBuilt toBeBuilt, QueuedBuildData queuedBuildData, boolean indexingOnly) {
		return new BuildData(getProject().getName(), null, toBeBuilt, queuedBuildData, indexingOnly).isEmpty();
	}

	private void updateProjectReferencesIfNecessary() {
		final Injector injector = new N4JSInjectorSupplier().get();
		final ProjectDescriptionLoadListener loadListener = injector.getInstance(ProjectDescriptionLoadListener.class);
		loadListener.updateProjectReferencesIfNecessary(getProject());
	}
}
