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

import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.eclipse.core.resources.IResourceDelta.ADDED;
import static org.eclipse.core.resources.IResourceDelta.CHANGED;
import static org.eclipse.core.resources.IResourceDelta.OPEN;
import static org.eclipse.core.resources.IResourceDelta.REMOVED;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import com.google.inject.Inject;

/**
 * Resource change listener implementation for listening project open/close event and running the
 * {@link ExternalLibraryBuilder external library build helper} according to the changes. Also got notified if new
 * workspace project is being created or an existing, accessible project is being deleted.
 */
public class ProjectStateChangeListener implements IResourceChangeListener {
	private static final Logger logger = Logger.getLogger(ProjectStateChangeListener.class);

	@Inject
	private EclipseExternalIndexSynchronizer indexSynchronizer;

	@Inject
	private ExternalLibraryBuilder builder;

	final private Collection<IProject> projectsChanged = newLinkedHashSet();

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (null == event || null == event.getDelta()) {
			return;
		}

		try {
			projectsChanged.clear();
			event.getDelta().accept(this::visit); // fill projectsChanged

			if (!projectsChanged.isEmpty()) {

				Job job = new Job("Update locations of node_modules folders") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						ISchedulingRule rule = builder.getRule();
						Job.getJobManager().beginRule(rule, monitor);
						try {
							indexSynchronizer.checkAndClearIndex(monitor);
						} finally {
							Job.getJobManager().endRule(rule);
						}
						return Status.OK_STATUS;
					}
				};
				job.schedule();
			}

		} catch (final CoreException e) {
			logger.error("Error while visiting resource change event.", e);
		}
	}

	boolean visit(IResourceDelta delta) {
		if (projectChanged(delta)) {
			IResource resource = delta.getResource();
			String name = resource.getName();
			if (resource instanceof IProject && !"RemoteSystemsTempFiles".equals(name)) {
				IProject project = (IProject) resource;
				projectsChanged.add(project);
			}
		}

		return true;
	}

	private boolean projectChanged(IResourceDelta delta) {
		int kind = delta.getKind();
		int flags = delta.getFlags();

		switch (kind) {
		case ADDED:
		case REMOVED:
			return true;

		case CHANGED:
			if ((flags & (OPEN)) != 0) {
				return true;
			}
		}

		return false;
	}

}
