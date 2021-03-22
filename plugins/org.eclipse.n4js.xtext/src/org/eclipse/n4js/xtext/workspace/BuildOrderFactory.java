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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Implementation for sorted projects according to their build order.
 */
public class BuildOrderFactory {

	/** Computes {@link BuildOrderInfo} */
	@Inject
	protected BuildOrderInfoComputer buildOrderInfoComputer;

	/** Creates a new instance of {@link BuildOrderIterator}. The given set of projects will be visited only. */
	public BuildOrderIterator createBuildOrderIterator(WorkspaceConfigSnapshot workspaceConfig,
			Collection<? extends ProjectConfigSnapshot> projectDescriptions) {

		BuildOrderIterator iterator = createBuildOrderIterator(workspaceConfig);
		iterator.visit(projectDescriptions);
		return iterator;
	}

	/**
	 * Creates a new instance of {@link BuildOrderIterator}. Assumes a succeeding call to
	 * {@link BuildOrderIterator#visit(Collection)} method.
	 */
	public BuildOrderIterator createBuildOrderIterator(WorkspaceConfigSnapshot workspaceConfig) {
		return new BuildOrderIterator(workspaceConfig);
	}

	/**
	 * Creates a new instance of {@link BuildOrderInfo}. Computes the build order of the given
	 * {@link WorkspaceConfigSnapshot}
	 */
	public BuildOrderInfo createBuildOrderInfo(WorkspaceConfigSnapshot workspaceConfig) {
		return createBuildOrderInfo(workspaceConfig.projects);
	}

	/**
	 * Creates a new instance of {@link BuildOrderInfo}. Computes the build order of the given
	 * {@link WorkspaceConfigSnapshot}
	 */
	public BuildOrderInfo createBuildOrderInfo(ProjectSet projects) {
		List<ProjectConfigSnapshot> pSortedProjects = new ArrayList<>();
		Set<ImmutableList<String>> pProjectCycles = new HashSet<>();

		buildOrderInfoComputer.init(projects, pSortedProjects, pProjectCycles);

		return createNewProjectBuildOrderInfo(pSortedProjects, pProjectCycles);
	}

	/** Creates new instance of {@link BuildOrderInfo} */
	protected BuildOrderInfo createNewProjectBuildOrderInfo(
			List<ProjectConfigSnapshot> pSortedProjects, Set<ImmutableList<String>> pProjectCycles) {

		return new BuildOrderInfo(pSortedProjects, pProjectCycles);
	}

	/** Computes all collections for {@link BuildOrderInfo} */
	public static class BuildOrderInfoComputer {

		/** Populates {@code #pSortedProjects}, {@code #pInversedDependencies} and {@code pProjectCycles} */
		protected void init(ProjectSet projects,
				List<ProjectConfigSnapshot> pSortedProjects,
				Collection<ImmutableList<String>> pProjectCycles) {

			LinkedHashSet<String> orderedProjectNames = new LinkedHashSet<>();
			for (ProjectConfigSnapshot pc : getAllProjects(projects)) {
				computeOrder(projects, pProjectCycles, pc, orderedProjectNames, new LinkedHashSet<>());
			}

			for (String projectName : orderedProjectNames) {
				ProjectConfigSnapshot pc = findProjectByName(projects, projectName);
				if (pc != null) {
					pSortedProjects.add(pc);
				}
			}
		}

		/** Computes the build order of all projects in the workspace */
		protected void computeOrder(ProjectSet projects, Collection<ImmutableList<String>> pProjectCycles,
				ProjectConfigSnapshot pc, LinkedHashSet<String> orderedProjects, LinkedHashSet<String> projectStack) {

			String pdName = pc.getName();
			if (orderedProjects.contains(pdName)) {
				return;
			}

			if (projectStack.contains(pdName)) {
				ArrayList<String> listStack = new ArrayList<>(projectStack);
				List<String> cycle = listStack.subList(listStack.indexOf(pdName), listStack.size());
				pProjectCycles.add(ImmutableList.copyOf(cycle));
			} else {
				projectStack.add(pdName);

				for (String depName : getDependencies(pc)) {
					ProjectConfigSnapshot depPC = findProjectByName(projects, depName);
					if (depPC != null) {
						computeOrder(projects, pProjectCycles, depPC, orderedProjects, projectStack);
					}
				}

				orderedProjects.add(pdName);
				projectStack.remove(pdName);
			}
		}

		/** Returns all projects of the given {@link WorkspaceConfigSnapshot}. */
		protected Collection<? extends ProjectConfigSnapshot> getAllProjects(ProjectSet projects) {
			return projects.getProjects();
		}

		/** Find the project with the given name. */
		protected ProjectConfigSnapshot findProjectByName(ProjectSet projects, String name) {
			return projects.findProjectByName(name);
		}

		/**
		 * Returns the project dependencies to be considered for build order computation. By default, simply returns
		 * {@link ProjectConfigSnapshot#getDependencies()}; subclasses may override to customize this.
		 */
		protected Set<String> getDependencies(ProjectConfigSnapshot pc) {
			return pc.getDependencies();
		}
	}
}