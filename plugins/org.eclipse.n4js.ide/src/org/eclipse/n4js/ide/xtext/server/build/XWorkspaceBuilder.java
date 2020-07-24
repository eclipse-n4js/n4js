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
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

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

	/** Holds all deltas of all projects. In case of a cancelled build, this set is not empty at start of next build. */
	private List<IResourceDescription.Delta> allBuildDeltas = new ArrayList<>();

	/**
	 * Announce dirty and deleted files and provide means to start a build.
	 *
	 * @param dirtyFiles
	 *            the dirty files
	 * @param deletedFiles
	 *            the deleted files
	 * @return a build command that can be triggered
	 */
	public BuildTask incrementalBuildTask(List<URI> dirtyFiles, List<URI> deletedFiles) {
		WorkspaceChanges changes = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);
		// TODO pass all URIs at once.
		for (URI uri : dirtyFiles) {
			changes = changes.merge(workspaceManager.update(uri));
		}
		if (!changes.getProjectsWithChangedDependencies().isEmpty()) {
			Iterable<ProjectConfigSnapshot> projectsWithChangedDeps = IterableExtensions.map(
					changes.getProjectsWithChangedDependencies(),
					XIProjectConfig::toSnapshot);
			fullIndex.setProjectConfigSnapshots(projectsWithChangedDeps);
		}
		return toBuildTask(changes);
	}

	/**
	 * Re-initializes the workspace and triggers the equivalent to an initial build.
	 */
	public BuildTask initialBuild() {
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
	public BuildTask clean() {
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
	private BuildTask toBuildTask(WorkspaceChanges workspaceChanges) {
		List<URI> dirtyFiles = workspaceChanges.scanAllAddedAndChangedURIs(scanner);
		List<URI> deletedFiles = getAllRemovedURIs(workspaceChanges);
		queue(this.dirtyFiles, deletedFiles, dirtyFiles);
		queue(this.deletedFiles, dirtyFiles, deletedFiles);

		for (XIProjectConfig prjConfig : workspaceChanges.getRemovedProjects()) {
			workspaceManager.removeProject(prjConfig);
		}
		for (XIProjectConfig prjConfig : workspaceChanges.getAddedProjects()) {
			workspaceManager.addProject(prjConfig);
		}

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

			while (pboIterator.hasNext()) {
				ProjectDescription descr = pboIterator.next();
				ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder(descr.getName());
				Set<URI> projectDirty = project2dirty.getOrDefault(descr, Collections.emptySet());
				Set<URI> projectDeleted = project2deleted.getOrDefault(descr, Collections.emptySet());

				// the projectResult might contain partial information in case the build was cancelled
				XBuildResult projectResult;
				try {
					projectResult = projectBuilder.doIncrementalBuild(buildRequestFactory, projectDirty, projectDeleted,
							allBuildDeltas, cancelIndicator);
				} catch (BuildCanceledException e) {
					// TODO GH-1793 remove this temporary solution
					dirtyFiles.addAll(e.incompletelyBuiltFiles);
					e.rethrowOriginalCancellation();
					throw new IllegalStateException("should never get here");
				}

				List<Delta> newlyBuiltDeltas = projectResult.getAffectedResources();
				recordBuildProgress(newlyBuiltDeltas);

				pboIterator.visitAffected(newlyBuiltDeltas);
			}

			List<IResourceDescription.Delta> result = allBuildDeltas;
			allBuildDeltas = new ArrayList<>();

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
		if (allBuildDeltas.isEmpty()) {
			allBuildDeltas.addAll(newDeltas);
		} else {
			Map<URI, IResourceDescription.Delta> unreportedByUri = IterableExtensions.toMap(
					allBuildDeltas, IResourceDescription.Delta::getUri);

			for (IResourceDescription.Delta newDelta : newDeltas) {
				IResourceDescription.Delta unreportedDelta = unreportedByUri.get(newDelta.getUri());
				if (unreportedDelta == null) {
					allBuildDeltas.add(newDelta);
				} else {
					allBuildDeltas.remove(unreportedDelta);
					IResourceDescription _old = unreportedDelta.getOld();
					IResourceDescription _new = newDelta.getNew();
					allBuildDeltas.add(new DefaultResourceDescriptionDelta(_old, _new));
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
