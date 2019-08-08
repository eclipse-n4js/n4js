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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.PlatformResourceURI;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.ui.XtextProjectHelper;
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

	private final IN4JSCore n4jsCore;

	private final SyncIndexJob syncIndexJob = new SyncIndexJob();

	private final OutdatedPackageJsonQueue packageJsonQueue;

	private class SyncIndexJob extends WorkspaceJob {

		public SyncIndexJob() {
			super("Updating npm index");
			setRule(ResourcesPlugin.getWorkspace().getRoot());
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == ResourcesPlugin.FAMILY_AUTO_BUILD || family == ResourcesPlugin.FAMILY_MANUAL_BUILD;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			updateNpmIndex(monitor);
			return Status.OK_STATUS;
		}
	}

	/***/
	@Inject
	public ProjectStateChangeListener(ISharedStateContributionRegistry registry,
			OutdatedPackageJsonQueue packageJsonQueue) {
		this.indexSynchronizer = registry.getSingleContributedInstance(ExternalIndexSynchronizer.class);
		this.n4jsCore = registry.getSingleContributedInstance(IN4JSCore.class);
		this.packageJsonQueue = packageJsonQueue;
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
					final Set<IProject> affectedProjects = Sets.newLinkedHashSet();
					event.getDelta().accept(projectCollector(affectedProjects));
					if (!affectedProjects.isEmpty()) {
						ToBeBuilt toBeBuilt = new ToBeBuilt();
						Set<URI> toBeUpdated = toBeBuilt.getToBeUpdated();
						Set<String> projectNames = new LinkedHashSet<>();
						for (IProject project : affectedProjects) {
							IFile file = project.getFile("package.json");
							if (file.exists()) {
								projectNames.add(project.getName());
								toBeUpdated.add(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
							}

						}
						packageJsonQueue.enqueue(projectNames, toBeBuilt, false);
						syncIndexJob.schedule();
					}
				} catch (CoreException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		super.resourceChanged(event);
	}

	/**
	 * Enqueue an index sync
	 */
	public void forceIndexSync() {
		packageJsonQueue.enqueue(Collections.singleton("npm index sync"), new ToBeBuilt(), true);
	}

	private IResourceDeltaVisitor projectCollector(final Set<IProject> accumutor) {
		return new IResourceDeltaVisitor() {
			@Override
			public boolean visit(IResourceDelta delta) throws CoreException {
				return collectAffectedProjects(delta, accumutor);
			}
		};
	}

	private boolean collectAffectedProjects(IResourceDelta delta, Set<IProject> accumulator) {
		IResource resource = delta.getResource();
		if (resource instanceof IProject && "RemoteSystemsTempFiles".equals(resource.getName())) {
			return false;
		}
		if (resource instanceof IWorkspaceRoot)
			return true;
		if (resource instanceof IProject) {
			IProject project = (IProject) resource;
			if ("RemoteSystemsTempFiles".equals(resource.getName())) {
				return false;
			}
			if ((delta.getKind() & IResourceDelta.CHANGED) != 0 && project.isOpen()) {
				if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
					accumulator.add(project);
				}
				if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
					if ((delta.findMember(new Path(".project")) != null) && XtextProjectHelper.hasNature(project)
							&& XtextProjectHelper.hasBuilder(project)) {
						accumulator.add(project);
					}
				}
			}
			return true;
		}
		if (resource instanceof IFolder) {
			if ("node_modules".equals(resource.getName())) {
				accumulator.add(resource.getProject());
			} else if ((delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED)
					&& isSourceContainerModification(resource)) {
				accumulator.add(resource.getProject());
			}
		}
		return false;
	}

	private boolean isSourceContainerModification(final IResource folder) {
		return isSourceContainerModification(n4jsCore, folder.getFullPath());
	}

	/**
	 * Returns whether a modification at the given path is a source container modification.
	 */
	public static boolean isSourceContainerModification(IN4JSCore n4jsCore, IPath changedPath) {
		final String fullPathStr = changedPath.toString();
		final URI folderUri = URI.createPlatformResourceURI(fullPathStr, true);
		final IN4JSProject project = n4jsCore.findProject(folderUri).orNull();
		if (null != project && project.exists()) {
			return from(project.getSourceContainers())
					.transform(container -> container.getLocation())
					.filter(PlatformResourceURI.class)
					.transform(uri -> uri.getAbsolutePath())
					.firstMatch(path -> path.equals(fullPathStr))
					.isPresent();
		}
		return false;
	}

	private void updateNpmIndex(IProgressMonitor monitor) throws CoreException {
		OutdatedPackageJsonQueue.Task task = packageJsonQueue.exhaust();
		if (task.isEmpty()) {
			return;
		}
		try {
			indexSynchronizer.checkAndClearIndex(monitor);
			Set<URI> toBeUpdated = task.getToBeBuilt().getToBeUpdated();
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			for (URI touchMe : toBeUpdated) {
				if (touchMe.isPlatformResource()) {
					IFile file = workspaceRoot.getFile(new Path(touchMe.toPlatformString(true)));
					// could have been deleted in the meantime
					if (file.exists()) {
						file.touch(monitor);
					}
				}
			}
		} catch (Error | RuntimeException | CoreException e) {
			task.reschedule();
			throw e;
		} finally {
			monitor.done();
		}
	}

	@Override
	protected boolean visitResourceDelta(IResourceDelta delta, Set<IProject> accumulator) {
		IResource resource = delta.getResource();
		if (resource instanceof IProject && "RemoteSystemsTempFiles".equals(resource.getName())) {
			return false;
		}
		return super.visitResourceDelta(delta, accumulator);
	}

	@Override
	public void joinRemoveProjectJob() {
		try {
			// Pseudo fence to make sure that we see what we want to see.
			synchronized (this) {
				wait(1);
			}
			syncIndexJob.join();
		} catch (InterruptedException e) {
			// ignore
			e.printStackTrace();
		}
		super.joinRemoveProjectJob();
	}

}
