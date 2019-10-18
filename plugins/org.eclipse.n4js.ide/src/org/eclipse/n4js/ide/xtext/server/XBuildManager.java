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
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
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

		/**
		 * Constructor
		 */
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

		/**
		 * The dirty files.
		 */
		public List<URI> getDirtyFiles() {
			return this.dirtyFiles;
		}

		/**
		 * The deleted files.
		 */
		public List<URI> getDeletedFiles() {
			return this.deletedFiles;
		}
	}

	/**
	 * A handle that can be used to trigger a build.
	 */
	public interface XBuildable {
		/**
		 * Run the build
		 */
		List<IResourceDescription.Delta> build(CancelIndicator cancelIndicator);
	}

	/**
	 * Issue key for cyclic dependencies
	 */
	public static final String CYCLIC_PROJECT_DEPENDENCIES = (XBuildManager.class.getCanonicalName()
			+ ".cyclicProjectDependencies");

	private XWorkspaceManager workspaceManager;

	@Inject
	private Provider<TopologicalSorter> sorterProvider;

	private final LinkedHashSet<URI> dirtyFiles = CollectionLiterals.<URI> newLinkedHashSet();

	private final LinkedHashSet<URI> deletedFiles = CollectionLiterals.<URI> newLinkedHashSet();

	private List<IResourceDescription.Delta> unreportedDeltas = CollectionLiterals.<IResourceDescription.Delta> newArrayList();

	/**
	 * Enqueue the given file collections.
	 *
	 * @return a buildable.
	 */
	@SuppressWarnings("hiding")
	public XBuildable submit(List<URI> dirtyFiles, List<URI> deletedFiles) {
		queue(this.dirtyFiles, deletedFiles, dirtyFiles);
		queue(this.deletedFiles, dirtyFiles, deletedFiles);
		return this::internalBuild;
	}

	/**
	 * @deprecated this method is no longer used
	 */
	@SuppressWarnings("hiding")
	@Deprecated
	public List<IResourceDescription.Delta> doBuild(List<URI> dirtyFiles, List<URI> deletedFiles,
			CancelIndicator cancelIndicator) {
		return submit(dirtyFiles, deletedFiles).build(cancelIndicator);
	}

	/**
	 * Update the contents of the given set.
	 */
	protected void queue(Set<URI> files, Collection<URI> toRemove, Collection<URI> toAdd) {
		Iterables.removeAll(files, toRemove);
		Iterables.<URI> addAll(files, toAdd);
	}

	/**
	 * Run an initial build
	 *
	 * @return the delta.
	 */
	public List<IResourceDescription.Delta> doInitialBuild(List<ProjectDescription> projects,
			CancelIndicator indicator) {
		List<ProjectDescription> sortedDescriptions = sortByDependencies(projects);
		ArrayList<IResourceDescription.Delta> result = CollectionLiterals.<IResourceDescription.Delta> newArrayList();
		for (ProjectDescription description : sortedDescriptions) {
			XIncrementalBuilder.XResult partialresult = workspaceManager.getProjectManager(description.getName())
					.doInitialBuild(indicator);
			result.addAll(partialresult.getAffectedResources());
		}
		return result;
	}

	/**
	 * Run the build on all projects.
	 */
	protected List<IResourceDescription.Delta> internalBuild(CancelIndicator cancelIndicator) {
		ArrayList<URI> allDirty = new ArrayList<>(dirtyFiles);
		HashMultimap<ProjectDescription, URI> project2dirty = HashMultimap.create();
		for (URI dirty : allDirty) {
			project2dirty.put(workspaceManager.getProjectManager(dirty).getProjectDescription(), dirty);
		}
		HashMultimap<ProjectDescription, URI> project2deleted = HashMultimap.create();
		for (URI deleted : deletedFiles) {
			ProjectDescription projectManager = workspaceManager.getProjectManager(deleted)
					.getProjectDescription();
			project2deleted.put(projectManager, deleted);
		}
		List<ProjectDescription> sortedDescriptions = sortByDependencies(Sets.union(project2dirty.keySet(),
				project2deleted.keySet()));
		for (ProjectDescription it : sortedDescriptions) {
			XProjectManager projectManager = workspaceManager.getProjectManager(it.getName());
			List<URI> projectDirty = new ArrayList<>(project2dirty.get(it));
			List<URI> projectDeleted = new ArrayList<>(project2deleted.get(it));
			XIncrementalBuilder.XResult partialResult = projectManager.doBuild(projectDirty, projectDeleted,
					unreportedDeltas, cancelIndicator);
			allDirty.addAll(
					ListExtensions.map(partialResult.getAffectedResources(), IResourceDescription.Delta::getUri));
			dirtyFiles.removeAll(projectDirty);
			deletedFiles.removeAll(projectDeleted);
			mergeWithUnreportedDeltas(partialResult.getAffectedResources());
		}
		List<IResourceDescription.Delta> result = unreportedDeltas;
		unreportedDeltas = new ArrayList<>();
		return result;
	}

	/**
	 * @since 2.18
	 */
	protected void mergeWithUnreportedDeltas(List<IResourceDescription.Delta> newDeltas) {
		if (this.unreportedDeltas.isEmpty()) {
			unreportedDeltas.addAll(newDeltas);
		} else {
			Map<URI, IResourceDescription.Delta> unreportedByUri = IterableExtensions.toMap(
					unreportedDeltas, IResourceDescription.Delta::getUri);
			newDeltas.forEach((newDelta) -> {
				IResourceDescription.Delta unreportedDelta = unreportedByUri.get(newDelta.getUri());
				if ((unreportedDelta == null)) {
					unreportedDeltas.add(newDelta);
				} else {
					unreportedDeltas.remove(unreportedDelta);
					IResourceDescription _old = unreportedDelta.getOld();
					IResourceDescription _new = newDelta.getNew();
					unreportedDeltas.add(new DefaultResourceDescriptionDelta(_old, _new));
				}
			});
		}
	}

	/**
	 * Get a sorted list of projects to be build.
	 */
	protected List<ProjectDescription> sortByDependencies(Iterable<ProjectDescription> projectDescriptions) {
		return sorterProvider.get().sortByDependencies(projectDescriptions, (it) -> {
			reportDependencyCycle(workspaceManager.getProjectManager(it.getName()));
		});
	}

	/**
	 * Report cycle.
	 */
	protected void reportDependencyCycle(XProjectManager manager) {
		manager.reportProjectIssue("Project has cyclic dependencies", XBuildManager.CYCLIC_PROJECT_DEPENDENCIES,
				Severity.ERROR);
	}

	/**
	 * Setter.
	 */
	public void setWorkspaceManager(XWorkspaceManager workspaceManager) {
		this.workspaceManager = workspaceManager;
	}
}
