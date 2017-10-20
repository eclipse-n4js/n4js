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

import static org.eclipse.n4js.ui.internal.N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;

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
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.internal.ProjectDescriptionLoadListener;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.BuildData;
import org.eclipse.xtext.builder.impl.QueuedBuildData;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.XtextBuilder;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.common.base.Stopwatch;
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

	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		try {
			return super.build(kind, args, toBuilderMonitor(monitor).split(1, SubMonitor.SUPPRESS_SETTASKNAME));
		} finally {
			stopwatch.stop();
			System.out.println(
					"Building " + getProject().getName() + " took " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds");
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
		final ProjectDescriptionLoadListener loadListener = N4JSActivator.getInstance()
				.getInjector(ORG_ECLIPSE_N4JS_N4JS)
				.getInstance(ProjectDescriptionLoadListener.class);
		loadListener.updateProjectReferencesIfNecessary(getProject());
	}
}
