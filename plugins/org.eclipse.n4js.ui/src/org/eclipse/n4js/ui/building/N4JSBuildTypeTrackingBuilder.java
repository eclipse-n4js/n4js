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

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ui.building.BuilderStateLogger.BuilderState;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.internal.ProjectDescriptionLoadListener;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.builder.IXtextBuilderParticipant.BuildType;
import org.eclipse.xtext.builder.debug.IBuildLogger;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.builder.impl.XtextBuilder;

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

			SubMonitor sm = SubMonitor.convert(monitor, 100);
			SubMonitor monitorMF = sm.newChild(1);
			SubMonitor monitorPJ = sm.newChild(99);
			super.doBuild(getManifestTBB(toBeBuilt), monitorMF, type);
			if (isErrorFree()) {
				// Only build the project if the manifest file is error free
				super.doBuild(toBeBuilt, monitorPJ, type);
			}

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
		final ProjectDescriptionLoadListener loadListener = N4JSActivator.getInstance()
				.getInjector(ORG_ECLIPSE_N4JS_N4JS)
				.getInstance(ProjectDescriptionLoadListener.class);
		loadListener.updateProjectReferencesIfNecessary(getProject());
	}

	private ToBeBuilt getManifestTBB(ToBeBuilt toBeBuilt) {
		ToBeBuilt manifestTBB = new ToBeBuilt();
		for (URI uri : toBeBuilt.getToBeDeleted()) {
			ResourceType rType = ResourceType.getResourceType(uri);
			if (rType == ResourceType.N4MF) {
				manifestTBB.getToBeDeleted().add(uri);
			}
		}
		for (URI uri : toBeBuilt.getToBeUpdated()) {
			ResourceType rType = ResourceType.getResourceType(uri);
			if (rType == ResourceType.N4MF) {
				manifestTBB.getToBeUpdated().add(uri);
			}
		}
		return manifestTBB;
	}

	private boolean isErrorFree() throws CoreException {
		IMarker[] markersInManifests = getProject().findMarkers(null, true, IResource.DEPTH_ONE);
		for (IMarker marker : markersInManifests) {
			Integer severityType = (Integer) marker.getAttribute(IMarker.SEVERITY);
			ResourceType rType = ResourceType.getResourceType(marker.getResource());
			if (rType == ResourceType.N4MF && severityType.intValue() == IMarker.SEVERITY_ERROR) {
				return false;
			}
		}
		return true;
	}
}
