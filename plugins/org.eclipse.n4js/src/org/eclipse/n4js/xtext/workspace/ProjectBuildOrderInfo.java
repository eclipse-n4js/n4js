/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * Implementation for sorted projects according to their build order.
 */
public class ProjectBuildOrderInfo implements IOrderInfo<ProjectConfigSnapshot> {

	/**
	 * {@link Iterator} that iterates over {@link ProjectBuildOrderInfo#sortedProjects}.
	 */
	public class ProjectBuildOrderIterator implements IOrderIterator<ProjectConfigSnapshot> {
		/**
		 * Subset of {@link #sortedProjects}: when {@link ProjectBuildOrderInfo} is used as an iterator, only those
		 * projects are iterated over that are contained in this set
		 */
		final protected Set<String> visitProjectNames = new HashSet<>();
		/** Set of all projects that have already visited by this iterator */
		final protected LinkedHashSet<ProjectConfigSnapshot> visitedAlready = new LinkedHashSet<>();
		/** The last visited project or null */
		protected ProjectConfigSnapshot lastVisited;
		/** The iterator index of the next project to visit */
		protected int iteratorIndex = -1;

		@Override
		public ProjectBuildOrderIterator visit(Collection<? extends ProjectConfigSnapshot> projectConfigs) {
			for (ProjectConfigSnapshot pc : projectConfigs) {
				String projectName = pc.getName();

				if (!visitedAlready.contains(pc) && sortedProjects.indexOf(pc) < sortedProjects.indexOf(lastVisited)) {
					String currentProjectName = current().getName();
					throw new IllegalStateException("Dependency-inverse visit order not supported: from "
							+ currentProjectName + " to " + projectName);
				}

				visitProjectNames.add(projectName);
				iteratorIndex = sortedProjects.indexOf(lastVisited);
				moveNext();
			}
			return this;
		}

		/** Mark all projects as to be visited that are affected by a change in the given project. */
		public IOrderIterator<ProjectConfigSnapshot> visitAffected(String projectName) {
			visit(inversedDependencies.get(projectName));
			return this;
		}

		@Override
		public ProjectBuildOrderIterator visitAffected(List<IResourceDescription.Delta> changes) {
			visit(getAffectedProjects(changes));
			return this;
		}

		@Override
		public ProjectBuildOrderIterator visitAll() {
			visit(sortedProjects);
			return this;
		}

		/** @return the set of projects that may contain resources that need to be rebuild given the list of changes */
		protected Set<ProjectConfigSnapshot> getAffectedProjects(List<IResourceDescription.Delta> changes) {
			Set<String> changedProjectsNames = new HashSet<>();
			for (IResourceDescription.Delta change : changes) {
				ProjectConfigSnapshot projectConfig = workspaceConfig.findProjectByNestedLocation(change.getUri());
				changedProjectsNames.add(projectConfig.getName());
			}

			Set<ProjectConfigSnapshot> affectedProjects = new HashSet<>();
			for (String changedProjectName : changedProjectsNames) {
				affectedProjects.addAll(inversedDependencies.get(changedProjectName));
			}

			return affectedProjects;
		}

		/** @return the current {@link ProjectConfigSnapshot} of this iterator */
		public ProjectConfigSnapshot current() {
			return lastVisited;
		}

		@Override
		public boolean hasNext() {
			if (notStarted()) {
				moveNext();
			}
			return iteratorIndex < sortedProjects.size();
		}

		@Override
		public ProjectConfigSnapshot next() {
			if (notStarted()) {
				moveNext();
			}

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			ProjectConfigSnapshot next = atIndex();
			visitedAlready.add(next);
			lastVisited = next;

			moveNext();

			return next;
		}

		private ProjectConfigSnapshot atIndex() {
			return sortedProjects.get(iteratorIndex);

		}

		private boolean notStarted() {
			return iteratorIndex < 0;
		}

		private void moveNext() {
			iteratorIndex++;
			while (hasNext() && !visitProjectNames.contains(atIndex().getName())) {
				iteratorIndex++;
			}
		}
	}

	/** Empty build order instance */
	public static final ProjectBuildOrderInfo NULL = new ProjectBuildOrderInfo(null, HashMultimap.create(),
			Lists.newArrayList(), Sets.newHashSet());

	/** Workspace configuration related to this build order */
	final protected WorkspaceConfigSnapshot workspaceConfig;
	/** Inverse set of project dependency information */
	final protected ImmutableMultimap<String, ProjectConfigSnapshot> inversedDependencies;
	/** Build order of projects */
	final protected ImmutableList<ProjectConfigSnapshot> sortedProjects;
	/** All project cycles, each cycle given as a list of project names */
	final protected ImmutableCollection<ImmutableList<String>> projectCycles;

	/** Constructor */
	public ProjectBuildOrderInfo(WorkspaceConfigSnapshot pWorkspaceConfig,
			Multimap<String, ProjectConfigSnapshot> pInversedDependencies,
			List<ProjectConfigSnapshot> pSortedProjects,
			Set<ImmutableList<String>> pProjectCycles) {

		workspaceConfig = pWorkspaceConfig;
		inversedDependencies = ImmutableMultimap.copyOf(pInversedDependencies);
		sortedProjects = ImmutableList.copyOf(pSortedProjects);
		projectCycles = ImmutableList.copyOf(pProjectCycles);
	}

	/** Creates a new instance of {@link ProjectBuildOrderIterator}. The given set of projects will be visited only. */
	@Override
	public ProjectBuildOrderIterator getIterator(Collection<? extends ProjectConfigSnapshot> projectDescriptions) {
		ProjectBuildOrderIterator iterator = getIterator();
		iterator.visit(projectDescriptions);
		return iterator;
	}

	/**
	 * Creates a new instance of {@link ProjectBuildOrderIterator}. Assumes a succeeding call to
	 * {@link ProjectBuildOrderIterator#visit(Collection)} method.
	 */
	@Override
	public ProjectBuildOrderIterator getIterator() {
		return new ProjectBuildOrderIterator();
	}

	/** @return all project cycles as lists of project names */
	public ImmutableCollection<ImmutableList<String>> getProjectCycles() {
		return this.projectCycles;
	}

}