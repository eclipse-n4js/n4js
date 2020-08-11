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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.LspLogger;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo.ProjectBuildOrderIterator;
import org.eclipse.n4js.ide.xtext.server.build.ParallelBuildManager.ParallelJob;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.CoarseGrainedChangeEvent;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionChangeEvent;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.Inject;

/**
 * The workspace builder is the owner of the build index information. It requests builds per project and maintains a
 * consistent index which is made accessible to the build projects via the build requests.
 *
 * In a project context, it is possible to request a refreshed index representation.
 */
@SuppressWarnings({ "hiding", "restriction" })
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
	private IFileSystemScanner scanner;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Inject
	private IBuildRequestFactory buildRequestFactory;

	@Inject
	private ConcurrentIndex fullIndex;

	private final Set<URI> dirtyFiles = new LinkedHashSet<>();
	private final Set<URI> deletedFiles = new LinkedHashSet<>();
	private final Set<String> deletedProjects = new LinkedHashSet<>();

	/** Holds all deltas of all projects. In case of a cancelled build, this set is not empty at start of next build. */
	private List<IResourceDescription.Delta> toBeConsideredDeltas = new ArrayList<>();

	/**
	 * Announce dirty and deleted files and provide means to start a build.
	 *
	 * @param dirtyFiles
	 *            the dirty files
	 * @param deletedFiles
	 *            the deleted files
	 * @return a build command that can be triggered
	 */
	public BuildTask createIncrementalBuildTask(List<URI> dirtyFiles, List<URI> deletedFiles) {
		WorkspaceChanges changes = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);
		List<URI> allFiles = ImmutableList.<URI> builder().addAll(dirtyFiles).addAll(deletedFiles).build();
		changes = changes.merge(workspaceManager.update(allFiles));
		if (!changes.getProjectsWithChangedDependencies().isEmpty()) {
			Iterable<ProjectConfigSnapshot> projectsWithChangedDeps = IterableExtensions.map(
					changes.getProjectsWithChangedDependencies(),
					XIProjectConfig::toSnapshot);
			fullIndex.setProjectConfigSnapshots(projectsWithChangedDeps);
		}
		return createBuildTask(changes);
	}

	/**
	 * Initializes the workspace and triggers the equivalent to an initial build.
	 */
	public BuildTask createInitialBuildTask() {
		return this::doInitialBuild;
	}

	/**
	 * Re-initializes the workspace and triggers the equivalent to an initial build.
	 */
	public BuildTask createReinitialBuildTask() {
		workspaceManager.reinitialize();
		return this::doInitialBuild;
	}

	/**
	 * Perform a build on all projects
	 *
	 * @param cancelIndicator
	 *            cancellation support
	 */
	private IResourceDescription.Event doInitialBuild(CancelIndicator cancelIndicator) {
		List<ProjectDescription> allProjects = workspaceManager.getProjectDescriptions();
		return doInitialBuild(allProjects, cancelIndicator);
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	private IResourceDescription.Event doInitialBuild(List<ProjectDescription> projects,
			CancelIndicator indicator) {

		lspLogger.log("Initial build ...");

		ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
		ProjectBuildOrderIterator pboIterator = projectBuildOrderInfo.getIterator(projects);
		logBuildOrder();

		List<IResourceDescription.Delta> result = new ArrayList<>();

		while (pboIterator.hasNext()) {
			ProjectDescription description = pboIterator.next();
			String projectName = description.getName();
			ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(projectName);
			XBuildResult partialresult = projectBuilder.doInitialBuild(buildRequestFactory, indicator);
			result.addAll(partialresult.getAffectedResources());
		}

		lspLogger.log("... initial build done.");

		return new ResourceDescriptionChangeEvent(result);
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	@SuppressWarnings("unused")
	@Deprecated // GH-1552: Experimental parallelization
	// TODO: use ProjectBuildOrderProvider
	private List<IResourceDescription.Delta> doInitialBuild2(List<ProjectDescription> projects,
			CancelIndicator indicator) {

		class BuildInitialProjectJob extends ParallelJob<String> {
			ProjectBuilder projectManager;
			List<IResourceDescription.Delta> result;

			BuildInitialProjectJob(ProjectBuilder projectManager, List<IResourceDescription.Delta> result) {
				this.projectManager = projectManager;
				this.result = result;
			}

			@Override
			public void runJob() {
				XBuildResult partialresult = projectManager.doInitialBuild(buildRequestFactory, indicator);
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
				return projectManager.getProjectDescription().getDependencies();
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
	 * Enqueue a build for the given file changes.
	 *
	 * @return a buildable.
	 */
	private BuildTask createBuildTask(WorkspaceChanges workspaceChanges) {
		List<URI> dirtyFiles = workspaceChanges.scanAllAddedAndChangedURIs(scanner);
		List<URI> deletedFiles = getAllRemovedURIs(workspaceChanges);
		queue(this.dirtyFiles, deletedFiles, dirtyFiles);
		queue(this.deletedFiles, dirtyFiles, deletedFiles);

		for (XIProjectConfig prjConfig : workspaceChanges.getRemovedProjects()) {
			this.deletedProjects.add(prjConfig.getName());
			handleDeletedProject(prjConfig);
		}
		workspaceManager.removeProjects(workspaceChanges.getRemovedProjects());
		for (XIProjectConfig prjConfig : workspaceChanges.getAddedProjects()) {
			this.deletedProjects.remove(prjConfig.getName()); // in case a deleted project is being re-created
		}
		workspaceManager.addProjects(workspaceChanges.getAddedProjects());

		return (cancelIndicator) -> doIncrementalBuild(cancelIndicator);
	}

	/** @return list of all {@link URI} that have been removed by the given changes */
	private List<URI> getAllRemovedURIs(WorkspaceChanges workspaceChanges) {
		List<URI> deleted = new ArrayList<>(workspaceChanges.getRemovedURIs());
		for (ISourceFolder sourceFolder : workspaceChanges.getAllRemovedSourceFolders()) {
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
	 * Adds deltas to {@link #toBeConsideredDeltas} representing the deletion of all files in the deleted project to
	 * ensure correct "isAffected"-computation on resource level during the next build (the deltas created here will be
	 * considered as "external deltas" by {@link XStatefulIncrementalBuilder#launch()}).
	 */
	protected void handleDeletedProject(XIProjectConfig projectConfig) {
		String projectName = projectConfig.getName();
		ResourceDescriptionsData data = fullIndex.getProjectIndex(projectName);
		if (data != null) {
			List<IResourceDescription.Delta> deltas = new ArrayList<>();
			for (IResourceDescription oldDesc : data.getAllResourceDescriptions()) {
				if (oldDesc != null) {
					// because the delta represents a deletion only, we need not create it via the
					// IResourceDescriptionManager:
					deltas.add(new DefaultResourceDescriptionDelta(oldDesc, null));
				}
			}
			mergeWithUnreportedDeltas(deltas);
		}
	}

	/** Run the build on the workspace */
	private IResourceDescription.Event doIncrementalBuild(CancelIndicator cancelIndicator) {
		lspLogger.log("Building ...");
		try {
			Set<URI> dirtyFilesToBuild = new LinkedHashSet<>(this.dirtyFiles);
			Set<URI> deletedFilesToBuild = new LinkedHashSet<>(this.deletedFiles);

			Map<ProjectDescription, Set<URI>> project2dirty = computeProjectToUriMap(dirtyFilesToBuild);
			Map<ProjectDescription, Set<URI>> project2deleted = computeProjectToUriMap(deletedFilesToBuild);
			SetView<ProjectDescription> changedPDs = Sets.union(project2dirty.keySet(), project2deleted.keySet());

			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator pboIterator = projectBuildOrderInfo.getIterator(changedPDs);

			for (String deletedProjectName : deletedProjects) {
				pboIterator.visitAffected(deletedProjectName);
			}

			while (pboIterator.hasNext()) {

				ProjectDescription descr = pboIterator.next();
				ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(descr.getName());
				Set<URI> projectDirty = project2dirty.getOrDefault(descr, Collections.emptySet());
				Set<URI> projectDeleted = project2deleted.getOrDefault(descr, Collections.emptySet());

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

			deletedProjects.clear();

			List<IResourceDescription.Delta> result = toBeConsideredDeltas;
			toBeConsideredDeltas = new ArrayList<>();

			lspLogger.log("... build done.");

			return new ResourceDescriptionChangeEvent(result);
		} catch (Throwable th) {
			if (operationCanceledManager.isOperationCanceledException(th)) {
				lspLogger.log("... build canceled.");
				operationCanceledManager.propagateIfCancelException(th);
			}
			// unknown exception or error (and not a cancellation case):
			// recover and also discard the build queue - state is undefined afterwards.
			this.dirtyFiles.clear();
			this.deletedFiles.clear();
			this.deletedProjects.clear();
			lspLogger.error("... build ABORTED due to exception:", th);
			throw th;
		}
	}

	/** Update the contents of the given set. */
	private void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
		files.removeAll(toRemove);
		files.addAll(toAdd);
	}

	private Map<ProjectDescription, Set<URI>> computeProjectToUriMap(Collection<URI> uris) {
		Map<ProjectDescription, Set<URI>> project2uris = new HashMap<>();
		for (URI uri : uris) {
			ProjectBuilder projectManager = workspaceManager.getProjectBuilder(uri);
			if (projectManager == null) {
				continue; // happens when editing a package.json file in a newly created project
			}
			ProjectDescription projectDescription = projectManager.getProjectDescription();
			if (!project2uris.containsKey(projectDescription)) {
				project2uris.put(projectDescription, new HashSet<>());
			}
			project2uris.get(projectDescription).add(uri);
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
					toBeConsideredDeltas.add(new DefaultResourceDescriptionDelta(_old, _new));
				}
			}
		}
	}

	/** Prints build order */
	private void logBuildOrder() {
		if (LOG.isInfoEnabled()) {
			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator visitAll = projectBuildOrderInfo.getIterator().visitAll();
			String output = "Project build order:\n  "
					+ IteratorExtensions.join(visitAll, "\n  ", ProjectDescription::getName);
			LOG.info(output);
		}
	}

}
