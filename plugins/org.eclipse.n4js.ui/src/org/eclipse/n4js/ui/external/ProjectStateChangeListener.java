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

import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Enhance the {@link ProjectOpenedOrClosedListener} to trigger the {@link EclipseExternalIndexSynchronizer} when a task
 * is executed.
 */
@SuppressWarnings("restriction")
@Singleton
public class ProjectStateChangeListener extends ProjectOpenedOrClosedListener {

	private final static Logger LOGGER = Logger.getLogger(ProjectStateChangeListener.class);

	private final ExternalIndexSynchronizer indexSynchronizer;

	/***/
	@Inject
	public ProjectStateChangeListener(ISharedStateContributionRegistry registry) {
		this.indexSynchronizer = registry.getSingleContributedInstance(ExternalIndexSynchronizer.class);
	}

	@Override
	protected RemoveProjectsJob createRemoveProjectsJob() {
		return new RemoveProjectsJob() {
			@Override
			public boolean belongsTo(Object family) {
				return super.belongsTo(family) || family == ResourcesPlugin.FAMILY_MANUAL_BUILD;
			}
		};
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		IWorkspace workspace = getWorkspace();
		if (workspace != null) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				try {
					final Set<IProject> affectedProjects = findProjectsToBuild(event);
					if (!affectedProjects.isEmpty()) {
						scheduleJob("npm Index", new ToBeBuilt());
					}
				} catch (CoreException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		super.resourceChanged(event);
	}

	private Set<IProject> findProjectsToBuild(final IResourceChangeEvent event) throws CoreException {
		final Set<IProject> toUpdate = Sets.newLinkedHashSet();
		event.getDelta().accept(projectCollector(toUpdate));
		return toUpdate;
	}

	private IResourceDeltaVisitor projectCollector(final Set<IProject> accumutor) {
		return new IResourceDeltaVisitor() {
			@Override
			public boolean visit(IResourceDelta delta) throws CoreException {
				return visitResourceDelta(delta, accumutor);
			}
		};
	}

	/**
	 * Schedule a job that will ensure that the npm index is up to date.
	 */
	public void scheduleCheckAndClearIndex() {
		scheduleJob("npm Index", new ToBeBuilt());
	}

	@Override
	protected void processClosedProjects(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 10);
		indexSynchronizer.checkAndClearIndex(subMonitor.split(1));
		super.processClosedProjects(subMonitor.split(9));
	}

	@Override
	protected boolean visitResourceDelta(IResourceDelta delta, Set<IProject> accumulator) {
		IResource resource = delta.getResource();
		if (resource instanceof IProject && "RemoteSystemsTempFiles".equals(resource.getName())) {
			return false;
		}
		return super.visitResourceDelta(delta, accumulator);
	}

}
