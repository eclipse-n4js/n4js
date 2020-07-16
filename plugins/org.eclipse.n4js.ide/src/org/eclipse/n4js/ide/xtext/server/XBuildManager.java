/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.LspLogger;
import org.eclipse.n4js.ide.xtext.server.ParallelBuildManager.ParallelJob;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo.ProjectBuildOrderIterator;
import org.eclipse.n4js.ide.xtext.server.build.BuildCanceledException;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIndex;
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceChanges;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
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
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings({ "hiding", "restriction" })
public class XBuildManager {
	private static final Logger LOG = LogManager.getLogger(XBuildManager.class);

	/** A handle that can be used to trigger a build. */
	public interface XBuildable {
		/** No build is going to happen. */
		XBuildable NO_BUILD = (cancelIndicator) -> Collections.emptyList();

		/** Run the build */
		List<IResourceDescription.Delta> build(CancelIndicator cancelIndicator);
	}

	/** Issue key for cyclic dependencies */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = XBuildManager.class.getName()
			+ ".cyclicProjectDependencies";

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
	private ConcurrentIndex fullIndex;

	private final LinkedHashSet<URI> dirtyFiles = new LinkedHashSet<>();
	private final LinkedHashSet<URI> deletedFiles = new LinkedHashSet<>();

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
	public XBuildable didChangeFiles(List<URI> dirtyFiles, List<URI> deletedFiles) {
		WorkspaceChanges workspaceChanges = WorkspaceChanges.createUrisRemovedAndChanged(deletedFiles, dirtyFiles);
		return getIncrementalGenerateBuildable(workspaceChanges);
	}

	/**
	 * Perform a build on all projects
	 *
	 * @param cancelIndicator
	 *            cancellation support
	 */
	public List<Delta> doInitialBuild(CancelIndicator cancelIndicator) {
		List<ProjectDescription> newProjects = workspaceManager.getProjectDescriptions();
		List<Delta> deltas = doInitialBuild(newProjects, cancelIndicator);
		return deltas;
	}

	/** Triggers an incremental build, and will generate output files. */
	protected XBuildable getIncrementalGenerateBuildable(WorkspaceChanges workspaceChanges) {
		return getIncrementalBuildable(workspaceChanges);
	}

	/** Mark the given document as saved. */
	public XBuildManager.XBuildable didSave(URI uri) {
		WorkspaceChanges notifiedChanges = WorkspaceChanges.createUrisChanged(ImmutableList.of(uri));
		WorkspaceChanges workspaceChanges = workspaceManager.getWorkspaceConfig().update(uri,
				projectName -> workspaceManager.getProjectBuilder(projectName).getProjectDescription());

		Iterable<IProjectConfigSnapshot> projectsWithChangedDeps = IterableExtensions.map(
				workspaceChanges.getProjectsWithChangedDependencies(),
				XIProjectConfig::toSnapshot);
		fullIndex.setProjectConfigSnapshots(projectsWithChangedDeps);

		workspaceChanges.merge(notifiedChanges);
		return getIncrementalGenerateBuildable(workspaceChanges);
	}

	/** Returns the index. */
	public ConcurrentIndex getIndex() {
		return fullIndex;
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	public List<IResourceDescription.Delta> doInitialBuild(List<ProjectDescription> projects,
			CancelIndicator indicator) {

		lspLogger.log("Initial build ...");

		ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
		ProjectBuildOrderIterator pboIterator = projectBuildOrderInfo.getIterator(projects);
		printBuildOrder();

		List<IResourceDescription.Delta> result = new ArrayList<>();

		while (pboIterator.hasNext()) {
			ProjectDescription description = pboIterator.next();
			String projectName = description.getName();
			ProjectBuilder projectManager = workspaceManager.getProjectBuilder(projectName);
			XBuildResult partialresult = projectManager.doInitialBuild(indicator);
			result.addAll(partialresult.getAffectedResources());
		}

		lspLogger.log("... initial build done.");

		return result;
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	@Deprecated // GH-1552: Experimental parallelization
	// TODO: use ProjectBuildOrderProvider
	public List<IResourceDescription.Delta> doInitialBuild2(List<ProjectDescription> projects,
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
				XBuildResult partialresult = projectManager.doInitialBuild(indicator);
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
	public void clean(CancelIndicator cancelIndicator) {
		doClean(cancelIndicator);
	}

	/** Performs a clean operation in all projects */
	public void doClean(CancelIndicator cancelIndicator) {
		for (ProjectBuilder projectBuilder : workspaceManager.getProjectBuilders()) {
			projectBuilder.doClean(cancelIndicator);
		}
	}

	/**
	 * Enqueue a build for the given file changes.
	 *
	 * @return a buildable.
	 */
	public XBuildable getIncrementalBuildable(WorkspaceChanges workspaceChanges) {
		List<URI> dirtyFiles = workspaceChanges.scanAllAddedAndChangedURIs(scanner);
		List<URI> deletedFiles = getAllRemovedURIs(workspaceChanges);
		queue(this.dirtyFiles, deletedFiles, dirtyFiles);
		queue(this.deletedFiles, dirtyFiles, deletedFiles);

		// TODO: When introducing a "Builder Component" (i.e. a Thread that cares only about building and get notified
		// when workspace changes occur):
		// think about encapsulating WorkspaceManager#projectName2ProjectManager and WorkspaceManager#fullIndex to
		// simplify control flow
		for (XIProjectConfig prjConfig : workspaceChanges.getRemovedProjects()) {
			workspaceManager.removeProject(prjConfig);
		}
		for (XIProjectConfig prjConfig : workspaceChanges.getAddedProjects()) {
			workspaceManager.addProject(prjConfig);
		}

		return (cancelIndicator) -> doIncrementalBuild(cancelIndicator);
	}

	/** @return list of all {@link URI} that have been removed by the given changes */
	protected List<URI> getAllRemovedURIs(WorkspaceChanges workspaceChanges) {
		List<URI> deleted = new ArrayList<>(workspaceChanges.getRemovedURIs());
		for (ISourceFolder sourceFolder : workspaceChanges.getAllRemovedSourceFolders()) {
			URI prefix = sourceFolder.getPath();
			ProjectBuilder projectManager = workspaceManager.getProjectBuilder(prefix);
			List<URI> matchedURIs = projectManager.findResourcesStartingWithPrefix(prefix);
			deleted.addAll(matchedURIs);
		}
		return deleted;
	}

	/** Run the build on the workspace */
	protected List<IResourceDescription.Delta> doIncrementalBuild(CancelIndicator cancelIndicator) {
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
				ProjectBuilder projectManager = workspaceManager.getProjectBuilder(descr.getName());
				Set<URI> projectDirty = project2dirty.getOrDefault(descr, Collections.emptySet());
				Set<URI> projectDeleted = project2deleted.getOrDefault(descr, Collections.emptySet());

				// the projectResult might contain partial information in case the build was cancelled
				XBuildResult projectResult;
				try {
					projectResult = projectManager.doIncrementalBuild(projectDirty, projectDeleted,
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

			return result;

		} catch (CancellationException ce) {
			lspLogger.log("... build canceled.");
			throw ce;
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
	protected void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
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
	protected void recordBuildProgress(List<IResourceDescription.Delta> newlyBuiltDeltas) {
		for (Delta delta : newlyBuiltDeltas) {
			URI uri = delta.getUri();
			this.dirtyFiles.remove(uri);
			this.deletedFiles.remove(uri);
		}

		mergeWithUnreportedDeltas(newlyBuiltDeltas);
	}

	/** @since 2.18 */
	protected void mergeWithUnreportedDeltas(List<IResourceDescription.Delta> newDeltas) {
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
	protected void printBuildOrder() {
		if (LOG.isInfoEnabled()) {
			ProjectBuildOrderInfo projectBuildOrderInfo = projectBuildOrderInfoProvider.get();
			ProjectBuildOrderIterator visitAll = projectBuildOrderInfo.getIterator().visitAll();
			String output = "Project build order:\n  "
					+ IteratorExtensions.join(visitAll, "\n  ", ProjectDescription::getName);
			LOG.info(output);
		}
	}

}
