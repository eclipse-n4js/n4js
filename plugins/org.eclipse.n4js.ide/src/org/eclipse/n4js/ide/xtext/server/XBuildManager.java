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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.XIncrementalBuilder;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ide.server.TopologicalSorter;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionDelta;
import org.eclipse.xtext.resource.impl.ProjectDescription;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Jan Koehnlein - Initial contribution and API
 * @since 2.11
 */
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
		/** Run the build */
		List<IResourceDescription.Delta> build(CancelIndicator cancelIndicator);

		/** No build is going to happen. */
		XBuildable NO_BUILD = (cancelIndicator) -> Collections.emptyList();
	}

	/** Issue key for cyclic dependencies */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = XBuildManager.class.getName()
			+ ".cyclicProjectDependencies";

	private XWorkspaceManager workspaceManager;

	@Inject
	private Provider<TopologicalSorter> sorterProvider;

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
			XProjectManager projectManager = workspaceManager.getProjectManager(description.getName());
			XIncrementalBuilder.XResult partialresult = projectManager.doInitialBuild(indicator);
			result.addAll(partialresult.getAffectedResources());
		}
		return result;
	}

	/**
	 * Enqueue the given file collections.
	 *
	 * @return a buildable.
	 */
	@SuppressWarnings("hiding")
	public XBuildable doIncrementalBuild(List<URI> dirtyFiles, List<URI> deletedFiles) {
		queue(this.dirtyFiles, deletedFiles, dirtyFiles);
		queue(this.deletedFiles, dirtyFiles, deletedFiles);
		return this::internalIncrementalBuild;
	}

	/** Update the contents of the given set. */
	protected void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
		files.removeAll(toRemove);
		files.addAll(toAdd);
	}

	/** Run the build on the workspace */
	protected List<IResourceDescription.Delta> internalIncrementalBuild(CancelIndicator cancelIndicator) {
		Map<ProjectDescription, Set<URI>> project2dirty = computeProjectToUriMap(dirtyFiles);
		Map<ProjectDescription, Set<URI>> project2deleted = computeProjectToUriMap(deletedFiles);

		SetView<ProjectDescription> allDescriptions = Sets.union(project2dirty.keySet(), project2deleted.keySet());
		List<ProjectDescription> sortedDescriptions = sortByDependencies(allDescriptions);

		for (ProjectDescription descr : sortedDescriptions) {
			XProjectManager projectManager = workspaceManager.getProjectManager(descr.getName());
			Set<URI> projectDirty = project2dirty.getOrDefault(descr, Collections.emptySet());
			Set<URI> projectDeleted = project2deleted.getOrDefault(descr, Collections.emptySet());
			XIncrementalBuilder.XResult partialResult = projectManager.doIncrementalBuild(projectDirty, projectDeleted,
					unreportedDeltas, cancelIndicator);

			dirtyFiles.removeAll(projectDirty);
			deletedFiles.removeAll(projectDeleted);
			mergeWithUnreportedDeltas(partialResult.getAffectedResources());
		}
		List<IResourceDescription.Delta> result = unreportedDeltas;
		unreportedDeltas = new ArrayList<>();
		return result;
	}

	private Map<ProjectDescription, Set<URI>> computeProjectToUriMap(Collection<URI> uris) {
		Map<ProjectDescription, Set<URI>> project2uris = new HashMap<>();
		for (URI deleted : uris) {
			ProjectDescription projectDescription = workspaceManager.getProjectManager(deleted).getProjectDescription();
			if (!project2uris.containsKey(projectDescription)) {
				project2uris.put(projectDescription, new HashSet<>());
			}
			project2uris.get(projectDescription).add(deleted);
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
		return sorterProvider.get().sortByDependencies(projectDescriptions, (it) -> {
			XProjectManager projectManager = workspaceManager.getProjectManager(it.getName());
			reportDependencyCycle(projectManager);
		});
	}

	/** Report cycle. */
	protected void reportDependencyCycle(XProjectManager manager) {
		String msg = "Project has cyclic dependencies";
		manager.reportProjectIssue(msg, XBuildManager.CYCLIC_PROJECT_DEPENDENCIES, Severity.ERROR);
	}

	/** Setter. */
	public void setWorkspaceManager(XWorkspaceManager workspaceManager) {
		this.workspaceManager = workspaceManager;
	}

	/**  */
	public void persistProjectState(CancelIndicator indicator) {
		for (XProjectManager prjManager : workspaceManager.getProjectManagers()) {
			prjManager.persistProjectState();
			if (indicator.isCanceled()) {
				return;
			}
		}
	}
}
