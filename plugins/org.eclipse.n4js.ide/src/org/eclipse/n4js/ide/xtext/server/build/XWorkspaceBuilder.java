/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server.build;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.LspLogger;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo.ProjectBuildOrderIterator;
import org.eclipse.n4js.ide.xtext.server.ResourceChangeSet;
import org.eclipse.n4js.ide.xtext.server.build.ParallelBuildManager.ParallelJob;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceManager.UpdateResult;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.SourceFolderScanner;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.CoarseGrainedChangeEvent;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionChangeEvent;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * The workspace builder is the owner of the build index information. It requests builds per project and maintains a
 * consistent index which is made accessible to the build projects via the build requests.
 *
 * In a project context, it is possible to request a refreshed index representation.
 */
@SuppressWarnings({ "hiding" })
public class XWorkspaceBuilder {

	private static class AffectedResourcesRecordingFactory implements IBuildRequestFactory {
		private final IBuildRequestFactory delegate;
		private final Set<URI> affected;

		private AffectedResourcesRecordingFactory(IBuildRequestFactory delegate) {
			this.delegate = delegate;
			this.affected = new HashSet<>();
		}

		@Override
		public XBuildRequest getBuildRequest(String projectName, Set<URI> changedFiles,
				Set<URI> deletedFiles, List<Delta> externalDeltas) {
			XBuildRequest result = delegate.getBuildRequest(projectName, changedFiles, deletedFiles, externalDeltas);
			result.addAffectedListener(affected::add);
			return result;
		}

		/**
		 * Return all resources that have been identified as affected so far by a request created from this factory.
		 */
		public Set<URI> getAffectedResources() {
			return affected;
		}
	}

	private static final Logger LOG = LogManager.getLogger(XWorkspaceBuilder.class);

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private ProjectBuildOrderInfo.Provider projectBuildOrderInfoProvider;

	@Inject
	private LspLogger lspLogger;

	@Inject
	private SourceFolderScanner sourceFolderScanner;

	@Inject
	private IFileSystemScanner scanner;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private IBuildRequestFactory buildRequestFactory;

	private final Set<URI> newDirtyFiles = new LinkedHashSet<>();
	private final Set<URI> newDeletedFiles = new LinkedHashSet<>();
	private boolean newRefreshRequest = false;

	private final Set<URI> dirtyFiles = new LinkedHashSet<>();
	private final Set<URI> deletedFiles = new LinkedHashSet<>();
	private final Set<String> deletedProjects = new LinkedHashSet<>();

	/** Holds all deltas of all projects. In case of a cancelled build, this set is not empty at start of next build. */
	private final List<IResourceDescription.Delta> toBeConsideredDeltas = new ArrayList<>();

	/**
	 * Initializes the workspace and triggers an initial build (always non-cancelable).
	 */
	public BuildTask createInitialBuildTask() {
		return (cancelIndicator) -> this.doInitialBuild();
	}

	/**
	 * Re-initializes the workspace and triggers the equivalent to an initial build (also non-cancelable).
	 */
	public BuildTask createReinitialBuildTask() {
		// because we are about to re-initialize the workspace and build everything anyway, we can get rid of all
		// changes reported up to this point (but do not clear #newDirty|DeletedFiles at then end of the initial build,
		// because changes that are reported during the initial build must not be overlooked!)
		newDirtyFiles.clear();
		newDeletedFiles.clear();

		workspaceManager.reinitialize();
		return (cancelIndicator) -> this.doInitialBuild();
	}

	/**
	 * Run a full build on the entire workspace, i.e. build all projects.
	 *
	 * @return the delta.
	 */
	private IResourceDescription.Event doInitialBuild() {
		lspLogger.log("Initial build ...");
		Stopwatch stopwatch = Stopwatch.createStarted();

		try {
			Collection<? extends ProjectConfigSnapshot> allProjects = workspaceManager.getProjectConfigs();
			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator pboIterator = projectBuildOrderInfo.getIterator(allProjects);
			logBuildOrder();

			List<IResourceDescription.Delta> allDeltas = new ArrayList<>();

			while (pboIterator.hasNext()) {
				ProjectConfigSnapshot projectConfig = pboIterator.next();
				String projectName = projectConfig.getName();
				ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(projectName);
				XBuildResult partialresult = projectBuilder.doInitialBuild(buildRequestFactory, allDeltas);
				allDeltas.addAll(partialresult.getAffectedResources());
			}

			onBuildDone(true, false, Optional.absent());

			stopwatch.stop();
			lspLogger.log("... initial build done (" + stopwatch.toString() + ").");

			return new ResourceDescriptionChangeEvent(allDeltas);
		} catch (Throwable th) {
			boolean wasCanceled = operationCanceledManager.isOperationCanceledException(th);

			onBuildDone(true, wasCanceled, Optional.of(th));

			if (wasCanceled) {
				lspLogger.log("... initial build canceled.");
				operationCanceledManager.propagateIfCancelException(th);
			}

			lspLogger.error("... initial build ABORTED due to exception:", th);
			throw th;
		}
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	@SuppressWarnings("unused")
	@Deprecated // GH-1552: Experimental parallelization
	// TODO: use ProjectBuildOrderProvider
	private List<IResourceDescription.Delta> doInitialBuild2(List<ProjectDescription> projects) {

		class BuildInitialProjectJob extends ParallelJob<String> {
			ProjectBuilder projectManager;
			List<IResourceDescription.Delta> result;

			BuildInitialProjectJob(ProjectBuilder projectManager, List<IResourceDescription.Delta> result) {
				this.projectManager = projectManager;
				this.result = result;
			}

			@Override
			public void runJob() {
				// TODO: properly propagate external deltas across jobs
				List<IResourceDescription.Delta> externalDeltas = Collections.emptyList();
				XBuildResult partialresult = projectManager.doInitialBuild(buildRequestFactory, externalDeltas);
				synchronized (result) {
					result.addAll(partialresult.getAffectedResources());
				}
			}

			@Override
			public String getID() {
				return projectManager.getProjectConfig().getName();
			}

			@Override
			public Collection<String> getDependencyIDs() {
				return projectManager.getProjectConfig().getDependencies();
			}
		}

		List<IResourceDescription.Delta> result = Collections.synchronizedList(new ArrayList<>());
		List<BuildInitialProjectJob> jobs = new ArrayList<>();

		for (ProjectDescription description : projects) {
			String projectName = description.getName();
			ProjectBuilder projectManager = workspaceManager.getProjectBuilder(projectName);
			BuildInitialProjectJob bpj = new BuildInitialProjectJob(projectManager, result);
			jobs.add(bpj);
		}

		ParallelBuildManager parallelBuildManager = new ParallelBuildManager(jobs);
		parallelBuildManager.run();

		return result;
	}

	/** Cleans all projects in the workspace */
	public BuildTask createCleanTask() {
		return cancelIndicator -> {
			for (ProjectBuilder projectBuilder : workspaceManager.getProjectBuilders()) {

				XBuildRequest buildRequest = buildRequestFactory.getBuildRequest(
						projectBuilder.getProjectConfig().getName(),
						Collections.emptySet(), Collections.emptySet(), Collections.emptyList());

				projectBuilder.doClean(buildRequest::afterDelete, CancelIndicator.NullImpl);
			}
			return new CoarseGrainedChangeEvent();
		};
	}

	/**
	 * Announce dirty and deleted files and perform an incremental build.
	 *
	 * @param newDirtyFiles
	 *            the dirty files, i.e. added and changes files.
	 * @param newDeletedFiles
	 *            the deleted files.
	 * @return a build task that can be triggered.
	 */
	public BuildTask createIncrementalBuildTask(List<URI> newDirtyFiles, List<URI> newDeletedFiles,
			boolean newRefreshRequest) {
		queue(this.newDirtyFiles, newDeletedFiles, newDirtyFiles);
		queue(this.newDeletedFiles, newDirtyFiles, newDeletedFiles);
		this.newRefreshRequest |= newRefreshRequest;
		return this::doIncrementalWorkspaceUpdateAndBuild;
	}

	/**
	 * Based on the raw, "reported changes" accumulated in {@link #newDirtyFiles} / {@link #newDeletedFiles}, do the
	 * following:
	 * <ol>
	 * <li>perform an update of the workspace configuration, if necessary, which may lead to additional "discovered
	 * changes" (e.g. resources in newly added source folders),
	 * <li><em>move</em> the "reported changes" together with the "discovered changes" to {@link #dirtyFiles} /
	 * {@link #deletedFiles} / {@link #deletedProjects},
	 * <li>then trigger an incremental build.
	 * </ol>
	 */
	protected IResourceDescription.Event doIncrementalWorkspaceUpdateAndBuild(CancelIndicator cancelIndicator) {

		// in case many incremental build tasks pile up in the queue (e.g. while a non-cancelable initial build is
		// running), we don't want to repeatedly invoke IWorkspaceManager#update() in each of those tasks but only in
		// the last one; therefore, we here check for a cancellation:
		operationCanceledManager.checkCanceled(cancelIndicator);

		Set<URI> newDirtyFiles = new LinkedHashSet<>(this.newDirtyFiles);
		Set<URI> newDeletedFiles = new LinkedHashSet<>(this.newDeletedFiles);
		boolean newRefreshRequest = this.newRefreshRequest;
		this.newDirtyFiles.clear();
		this.newDeletedFiles.clear();
		this.newRefreshRequest = false;

		Stopwatch stopwatch = Stopwatch.createStarted();
		if (newRefreshRequest) {
			lspLogger.log("Refreshing ...");
		}

		UpdateResult updateResult = workspaceManager.update(newDirtyFiles, newDeletedFiles, newRefreshRequest);
		WorkspaceChanges changes = updateResult.changes;

		List<URI> actualDirtyFiles = scanAllAddedAndChangedURIs(changes, scanner);
		List<URI> actualDeletedFiles = getAllRemovedURIs(changes); // n.b.: not including URIs of removed projects
		if (newRefreshRequest) {
			// scan for source file changes
			for (ProjectBuilder projectBuilder : workspaceManager.getProjectBuilders()) {
				ResourceChangeSet dirtySourceFiles = projectBuilder.searchForModifiedAndDeletedSourceFiles();
				actualDirtyFiles.addAll(dirtySourceFiles.getModified());
				actualDeletedFiles.addAll(dirtySourceFiles.getDeleted());
			}
		}

		queue(this.dirtyFiles, actualDeletedFiles, actualDirtyFiles);
		queue(this.deletedFiles, actualDirtyFiles, actualDeletedFiles);

		// take care of removed projects
		for (ProjectConfigSnapshot prjConfig : changes.getRemovedProjects()) {
			this.deletedProjects.add(prjConfig.getName());
		}
		for (ProjectConfigSnapshot prjConfig : changes.getAddedProjects()) {
			this.deletedProjects.remove(prjConfig.getName()); // in case a deleted project is being re-created
		}
		handleContentsOfRemovedProjects(updateResult.removedProjectsContents);

		if (newRefreshRequest) {
			lspLogger.log("... refresh done (" + stopwatch.toString() + "; "
					+ "projects added/removed: " + changes.getAddedProjects().size() + "/"
					+ changes.getRemovedProjects().size() + "; "
					+ "files dirty/deleted: " + dirtyFiles.size() + "/" + deletedFiles.size() + ").");
		}

		if (dirtyFiles.isEmpty() && deletedFiles.isEmpty() && deletedProjects.isEmpty()) {
			return new ResourceDescriptionChangeEvent(Collections.emptyList());
		}

		return doIncrementalBuild(cancelIndicator);
	}

	/** @return list of all added/changed URIs (including URIs of added projects). */
	private List<URI> scanAllAddedAndChangedURIs(WorkspaceChanges changes, IFileSystemScanner scanner) {
		List<URI> allAddedURIs = new ArrayList<>(changes.getAddedURIs());
		for (SourceFolderSnapshot sourceFolder : changes.getAllAddedSourceFolders()) {
			List<URI> sourceFilesOnDisk = sourceFolderScanner.findAllSourceFiles(sourceFolder, scanner);
			allAddedURIs.addAll(sourceFilesOnDisk);
		}
		List<URI> allChangedURIs = Lists.newArrayList(Iterables.concat(allAddedURIs, changes.getChangedURIs()));
		return allChangedURIs;
	}

	/** @return list of all removed URIs (<em>not</em> including URIs of removed projects). */
	private List<URI> getAllRemovedURIs(WorkspaceChanges workspaceChanges) {
		List<URI> deleted = new ArrayList<>(workspaceChanges.getRemovedURIs());
		for (SourceFolderSnapshot sourceFolder : workspaceChanges.getRemovedSourceFolders()) {
			URI prefix = sourceFolder.getPath();
			ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(prefix);
			if (projectBuilder != null) {
				List<URI> matchedURIs = projectBuilder.findResourcesStartingWithPrefix(prefix);
				deleted.addAll(matchedURIs);
			}
		}
		return deleted;
	}

	/**
	 * Adds deltas to {@link #toBeConsideredDeltas} representing the deletion of all files in the removed projects to
	 * ensure correct "isAffected"-computation on resource level during the next build (the deltas created here will be
	 * considered as "external deltas" by {@link XStatefulIncrementalBuilder#launch()}).
	 */
	protected void handleContentsOfRemovedProjects(Iterable<? extends IResourceDescription> removedContents) {
		List<IResourceDescription.Delta> deltas = new ArrayList<>();
		for (IResourceDescription oldDesc : removedContents) {
			if (oldDesc != null) {
				// because the delta represents a deletion only, we need not create it via the
				// IResourceDescriptionManager:
				deltas.add(new DefaultResourceDescriptionDelta(oldDesc, null));
			}
		}
		mergeWithUnreportedDeltas(deltas);
	}

	/** Run the build on the workspace */
	private IResourceDescription.Event doIncrementalBuild(CancelIndicator cancelIndicator) {
		lspLogger.log("Building ...");
		try {
			Set<URI> dirtyFilesToBuild = new LinkedHashSet<>(this.dirtyFiles);
			Set<URI> deletedFilesToBuild = new LinkedHashSet<>(this.deletedFiles);

			Map<String, Set<URI>> project2dirty = computeProjectToUriMap(dirtyFilesToBuild);
			Map<String, Set<URI>> project2deleted = computeProjectToUriMap(deletedFilesToBuild);
			Set<String> changedProjects = Sets.union(project2dirty.keySet(), project2deleted.keySet());
			WorkspaceConfigSnapshot workspaceConfig = workspaceManager.getWorkspaceConfig();
			List<ProjectConfigSnapshot> changedPCs = changedProjects.stream()
					.map(workspaceConfig::findProjectByName).collect(Collectors.toList());

			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator pboIterator = projectBuildOrderInfo.getIterator(changedPCs);

			for (String deletedProjectName : deletedProjects) {
				pboIterator.visitAffected(deletedProjectName);
			}

			while (pboIterator.hasNext()) {

				ProjectConfigSnapshot projectConfig = pboIterator.next();
				String projectName = projectConfig.getName();
				ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(projectName);
				Set<URI> projectDirty = project2dirty.getOrDefault(projectName, Collections.emptySet());
				Set<URI> projectDeleted = project2deleted.getOrDefault(projectName, Collections.emptySet());

				XBuildResult projectResult;
				AffectedResourcesRecordingFactory recordingFactory = new AffectedResourcesRecordingFactory(
						buildRequestFactory);
				try {
					projectResult = projectBuilder.doIncrementalBuild(
							recordingFactory,
							projectDirty,
							projectDeleted,
							toBeConsideredDeltas,
							cancelIndicator);
				} catch (Throwable t) {
					// re-schedule the affected files since a subsequent build may not detect those as affected
					// anymore but we may have produced artifacts for these already
					this.dirtyFiles.addAll(recordingFactory.getAffectedResources());
					throw t;
				}

				List<Delta> newlyBuiltDeltas = projectResult.getAffectedResources();
				recordBuildProgress(newlyBuiltDeltas);

				pboIterator.visitAffected(newlyBuiltDeltas);
			}

			List<IResourceDescription.Delta> result = toBeConsideredDeltas;

			onBuildDone(false, false, Optional.absent());

			lspLogger.log("... build done.");

			return new ResourceDescriptionChangeEvent(result);
		} catch (Throwable th) {
			boolean wasCanceled = operationCanceledManager.isOperationCanceledException(th);

			onBuildDone(false, wasCanceled, Optional.of(th));

			if (wasCanceled) {
				lspLogger.log("... build canceled.");
				operationCanceledManager.propagateIfCancelException(th);
			}

			// unknown exception or error (and not a cancellation case):
			// QueueExecutorService will log this as an error with stack trace, so here we just use #log():
			lspLogger.log("... build ABORTED due to exception: " + th.getMessage());
			throw th;
		}
	}

	/** Update the contents of the given set. */
	private void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
		files.removeAll(toRemove);
		files.addAll(toAdd);
	}

	private Map<String, Set<URI>> computeProjectToUriMap(Collection<URI> uris) {
		Map<String, Set<URI>> project2uris = new HashMap<>();
		for (URI uri : uris) {
			ProjectBuilder projectManager = workspaceManager.getProjectBuilder(uri);
			if (projectManager == null) {
				continue; // happens when editing a package.json file in a newly created project
			}
			String projectName = projectManager.getName();
			if (!project2uris.containsKey(projectName)) {
				project2uris.put(projectName, new HashSet<>());
			}
			project2uris.get(projectName).add(uri);
		}
		return project2uris;
	}

	/** Update this build manager's state after the resources represented by the given deltas have been built. */
	private void recordBuildProgress(List<IResourceDescription.Delta> newlyBuiltDeltas) {
		for (Delta delta : newlyBuiltDeltas) {
			URI uri = delta.getUri();
			this.dirtyFiles.remove(uri);
			this.deletedFiles.remove(uri);
		}

		mergeWithUnreportedDeltas(newlyBuiltDeltas);
	}

	private void mergeWithUnreportedDeltas(List<IResourceDescription.Delta> newDeltas) {
		if (toBeConsideredDeltas.isEmpty()) {
			toBeConsideredDeltas.addAll(newDeltas);
		} else {
			Map<URI, IResourceDescription.Delta> deltasByURI = IterableExtensions.toMap(
					toBeConsideredDeltas, IResourceDescription.Delta::getUri);

			for (IResourceDescription.Delta newDelta : newDeltas) {
				IResourceDescription.Delta unreportedDelta = deltasByURI.get(newDelta.getUri());
				if (unreportedDelta == null) {
					toBeConsideredDeltas.add(newDelta);
				} else {
					toBeConsideredDeltas.remove(unreportedDelta);

					IResourceDescription _old = unreportedDelta.getOld();
					IResourceDescription _new = newDelta.getNew();
					if (_old == null && _new == null) {
						// happens in case a resource was created, the build was cancelled and deleted again
						// before the next build
						_old = newDelta.getOld();
					}

					IResourceDescription.Delta mergedDesc = new DefaultResourceDescriptionDelta(_old, _new);

					if (!mergedDesc.haveEObjectDescriptionsChanged()) {
						// happens in case a resource was changed, the build was cancelled, and the resource was changed
						// back to its original state before the next build
						mergedDesc = newDelta;
					}

					toBeConsideredDeltas.add(mergedDesc);
				}
			}
		}
	}

	/**
	 * Invoked when a build ends.
	 * <p>
	 * If this method is invoked with 'throwable' being absent and it throws an exception or error, it will be invoked
	 * again with the exception/error being passed in as 'throwable'.
	 *
	 * @param wasInitialBuild
	 *            <code>true</code> if the build was an initial build, <code>false</code> if the build was an
	 *            incremental build.
	 * @param wasCanceled
	 *            <code>true</code> iff the build was canceled.
	 * @param throwable
	 *            absent if the build completed normally, present if the build ended early due to cancellation or some
	 *            other exception.
	 */
	protected void onBuildDone(boolean wasInitialBuild, boolean wasCanceled, Optional<Throwable> throwable) {
		workspaceManager.clearResourceSets();
		if (!wasCanceled) {
			discardIncrementalBuildQueue();
		}
	}

	/**
	 * Discards the internal collections of pending dirty/deleted files/projects and to-be-considered deltas used by the
	 * incremental builder to track its progress. Does <em>not</em> discard the sets of still unprocessed changes
	 * reported from the outside (i.e. {@link #newDirtyFiles}, {@link #newDeletedFiles}).
	 */
	protected void discardIncrementalBuildQueue() {
		dirtyFiles.clear();
		deletedFiles.clear();
		deletedProjects.clear();
		toBeConsideredDeltas.clear();
	}

	/** Prints build order */
	private void logBuildOrder() {
		if (LOG.isInfoEnabled()) {
			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator visitAll = projectBuildOrderInfo.getIterator().visitAll();
			String output = "Project build order:\n  "
					+ IteratorExtensions.join(visitAll, "\n  ", ProjectConfigSnapshot::getName);
			LOG.info(output);
		}
	}

}
