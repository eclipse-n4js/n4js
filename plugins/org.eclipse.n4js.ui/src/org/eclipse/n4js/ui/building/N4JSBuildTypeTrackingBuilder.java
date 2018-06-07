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

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.external.EclipseExternalIndexSynchronizer;
import org.eclipse.n4js.ui.internal.ProjectDescriptionLoadListener;
import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.XtextBuilder;

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
		try {
			updateProjectReferencesIfNecessary();
			checkExternalLibraries();
			N4JSBuildTypeTracker.setBuildType(getProject(), BuildType.CLEAN);
			super.doClean(toBeBuilt, monitor);
			getProject().touch(monitor);
		} finally {
			N4JSBuildTypeTracker.clearBuildType(getProject());
		}
	}

	@Override
	protected void doBuild(ToBeBuilt toBeBuilt, IProgressMonitor monitor, BuildType type) throws CoreException,
			OperationCanceledException {
		try {
			updateProjectReferencesIfNecessary();
			N4JSBuildTypeTracker.setBuildType(getProject(), type);
			super.doBuild(toBeBuilt, monitor, type);
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

	private void updateProjectReferencesIfNecessary() {
		final Injector injector = new N4JSInjectorSupplier().get();
		final ProjectDescriptionLoadListener loadListener = injector.getInstance(ProjectDescriptionLoadListener.class);
		loadListener.updateProjectReferencesIfNecessary(getProject());
	}

	private void checkExternalLibraries() {
		final Injector injector = new N4JSInjectorSupplier().get();
		final ExternalIndexSynchronizer indexSynchronizer = injector.getInstance(ExternalIndexSynchronizer.class);
		if (indexSynchronizer instanceof EclipseExternalIndexSynchronizer) {
			((EclipseExternalIndexSynchronizer) indexSynchronizer).checkAndSetOutOfSyncMarkers();
		}
	}

}
