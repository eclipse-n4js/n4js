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
import org.eclipse.n4js.ide.xtext.server.ParallelBuildManager.ParallelJob;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings("hiding")
public class XBuildManager {

	/**
	 * The resources that are about to be build.
	 */
	protected static class XProjectBuildData {
		private final List<URI> dirtyFiles;

		private final List<URI> deletedFiles;

		/** Constructor */
		public XProjectBuildData(List<URI> dirtyFiles, List<URI> deletedFiles) {
			super();
			this.dirtyFiles = dirtyFiles;
			this.deletedFiles = deletedFiles;
		}

		@Override
		public int hashCode() {
			int prime = 31;
			int result = 1;
			result = prime * result + ((this.dirtyFiles == null) ? 0 : this.dirtyFiles.hashCode());
			return prime * result + ((this.deletedFiles == null) ? 0 : this.deletedFiles.hashCode());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			XBuildManager.XProjectBuildData other = (XBuildManager.XProjectBuildData) obj;
			if (this.dirtyFiles == null) {
				if (other.dirtyFiles != null)
					return false;
			} else if (!this.dirtyFiles.equals(other.dirtyFiles))
				return false;
			if (this.deletedFiles == null) {
				if (other.deletedFiles != null)
					return false;
			} else if (!this.deletedFiles.equals(other.deletedFiles))
				return false;
			return true;
		}

		@Override
		public String toString() {
			ToStringBuilder b = new ToStringBuilder(this);
			b.add("dirtyFiles", this.dirtyFiles);
			b.add("deletedFiles", this.deletedFiles);
			return b.toString();
		}

		/** The dirty files. */
		public List<URI> getDirtyFiles() {
			return this.dirtyFiles;
		}

		/** The deleted files. */
		public List<URI> getDeletedFiles() {
			return this.deletedFiles;
		}
	}

	/** A handle that can be used to trigger a build. */
	public interface XBuildable {
		/** No build is going to happen. */
		XBuildable NO_BUILD = (cancelIndicator) -> Collections.emptyList();

		/** Run the build */
		List<IResourceDescription.Delta> build(CancelIndicator cancelIndicator);
	}

	private static final Logger LOG = LogManager.getLogger(XBuildManager.class);

	/** Issue key for cyclic dependencies */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = XBuildManager.class.getName()
			+ ".cyclicProjectDependencies";

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private XTopologicalSorter topoSorter;

	private final LinkedHashSet<URI> dirtyFiles = new LinkedHashSet<>();

	private final LinkedHashSet<URI> deletedFiles = new LinkedHashSet<>();

	private List<IResourceDescription.Delta> unreportedDeltas = new ArrayList<>();

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	public List<IResourceDescription.Delta> doInitialBuild(List<ProjectDescription> projects,
			CancelIndicator indicator) {

		List<ProjectDescription> sortedDescriptions = sortByDependencies(projects);
		List<IResourceDescription.Delta> result = new ArrayList<>();

		for (ProjectDescription description : sortedDescriptions) {
			String projectName = description.getName();
			XProjectManager projectManager = workspaceManager.getProjectManager(projectName);
			XBuildResult partialresult = projectManager.doInitialBuild(indicator);
			result.addAll(partialresult.getAffectedResources());
		}

		return result;
	}

	/**
	 * Run a full build on the workspace
	 *
	 * @return the delta.
	 */
	@Deprecated // GH-1552: Experimental parallelization
	public List<IResourceDescription.Delta> doInitialBuild2(List<ProjectDescription> projects,
			CancelIndicator indicator) {

		class BuildInitialProjectJob extends ParallelJob<String> {
			XProjectManager projectManager;
			List<IResourceDescription.Delta> result;

			BuildInitialProjectJob(XProjectManager projectManager, List<IResourceDescription.Delta> result) {
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

			@SuppressWarnings("restriction")
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
			XProjectManager projectManager = workspaceManager.getProjectManager(projectName);
			BuildInitialProjectJob bpj = new BuildInitialProjectJob(projectManager, result);
			jobs.add(bpj);
		}

		ParallelBuildManager parallelBuildManager = new ParallelBuildManager(jobs);
		parallelBuildManager.run();

		return result;
	}

	/** Performs a clean operation in all projects */
	public void doClean(CancelIndicator cancelIndicator) {
		for (XProjectManager projectManager : workspaceManager.getProjectManagers()) {
			projectManager.doClean(cancelIndicator);
		}
	}

	/**
	 * Enqueue the given file collections.
	 *
	 * @return a buildable.
	 */
	public XBuildable getIncrementalDirtyBuildable(List<URI> dirtyFiles, List<URI> deletedFiles) {
		return (cancelIndicator) -> doIncrementalBuild(dirtyFiles, deletedFiles, false, cancelIndicator);
	}

	/**
	 * Enqueue the given file collections.
	 *
	 * @return a buildable.
	 */
	public XBuildable getIncrementalGenerateBuildable(List<URI> dirtyFiles, List<URI> deletedFiles) {
		return (cancelIndicator) -> doIncrementalBuild(dirtyFiles, deletedFiles, true, cancelIndicator);
	}

	/** Run the build on the workspace */
	protected List<IResourceDescription.Delta> doIncrementalBuild(List<URI> dirtyFiles,
			List<URI> deletedFiles, boolean doGenerate, CancelIndicator cancelIndicator) {

		try {
			queue(this.dirtyFiles, deletedFiles, dirtyFiles);
			queue(this.deletedFiles, dirtyFiles, deletedFiles);

			Map<ProjectDescription, Set<URI>> project2dirty = computeProjectToUriMap(this.dirtyFiles);
			Map<ProjectDescription, Set<URI>> project2deleted = computeProjectToUriMap(this.deletedFiles);

			SetView<ProjectDescription> allDescriptions = Sets.union(project2dirty.keySet(), project2deleted.keySet());
			List<ProjectDescription> sortedDescriptions = sortByDependencies(allDescriptions);

			for (ProjectDescription descr : sortedDescriptions) {
				XProjectManager projectManager = workspaceManager.getProjectManager(descr.getName());
				Set<URI> projectDirty = project2dirty.getOrDefault(descr, Collections.emptySet());
				Set<URI> projectDeleted = project2deleted.getOrDefault(descr, Collections.emptySet());

				XBuildResult partialResult = projectManager.doIncrementalBuild(projectDirty, projectDeleted,
						unreportedDeltas, doGenerate, cancelIndicator);

				this.dirtyFiles.removeAll(projectDirty);
				this.deletedFiles.removeAll(projectDeleted);
				mergeWithUnreportedDeltas(partialResult.getAffectedResources());

				if (doGenerate) {
					projectManager.persistProjectState();
				}
			}
			List<IResourceDescription.Delta> result = unreportedDeltas;
			unreportedDeltas = new ArrayList<>();
			return result;

		} catch (CancellationException ce) {
			throw ce;

		} catch (Exception e) {
			// recover
			this.dirtyFiles.clear();
			this.deletedFiles.clear();
			throw e;
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
			XProjectManager projectManager = workspaceManager.getProjectManager(uri);
			ProjectDescription projectDescription = projectManager.getProjectDescription();
			if (!project2uris.containsKey(projectDescription)) {
				project2uris.put(projectDescription, new HashSet<>());
			}
			project2uris.get(projectDescription).add(uri);
		}
		return project2uris;
	}

	/** @since 2.18 */
	protected void mergeWithUnreportedDeltas(List<IResourceDescription.Delta> newDeltas) {
		if (this.unreportedDeltas.isEmpty()) {
			unreportedDeltas.addAll(newDeltas);
		} else {
			Map<URI, IResourceDescription.Delta> unreportedByUri = IterableExtensions.toMap(
					unreportedDeltas, IResourceDescription.Delta::getUri);

			for (IResourceDescription.Delta newDelta : newDeltas) {
				IResourceDescription.Delta unreportedDelta = unreportedByUri.get(newDelta.getUri());
				if (unreportedDelta == null) {
					unreportedDeltas.add(newDelta);
				} else {
					unreportedDeltas.remove(unreportedDelta);
					IResourceDescription _old = unreportedDelta.getOld();
					IResourceDescription _new = newDelta.getNew();
					unreportedDeltas.add(new DefaultResourceDescriptionDelta(_old, _new));
				}
			}
		}
	}

	/** Get a sorted list of projects to be build. */
	protected List<ProjectDescription> sortByDependencies(Iterable<ProjectDescription> projectDescriptions) {
		List<ProjectDescription> sortedProjectDescriptions = topoSorter.sortByDependencies(projectDescriptions,
				this::reportDependencyCycle);

		String output = "Project build order:\n  ";
		output += String.join("\n  ", IterableExtensions.map(sortedProjectDescriptions, pd -> pd.getName()));
		LOG.info(output);

		return sortedProjectDescriptions;
	}

	/** Report cycle. */
	protected void reportDependencyCycle(ProjectDescription prjDescription) {
		XProjectManager projectManager = workspaceManager.getProjectManager(prjDescription.getName());
		String msg = "Project has cyclic dependencies";
		projectManager.reportProjectIssue(msg, XBuildManager.CYCLIC_PROJECT_DEPENDENCIES, Severity.ERROR);
	}

	/** Persists the project state of all projects */
	public void persistProjectState(CancelIndicator indicator) {
		for (XProjectManager prjManager : workspaceManager.getProjectManagers()) {
			prjManager.persistProjectState();
			if (indicator.isCanceled()) {
				return;
			}
		}
	}
}
