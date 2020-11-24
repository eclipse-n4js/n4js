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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
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
		return createBuildOrderInfo(workspaceConfig.name2Project);
	}

	/**
	 * Creates a new instance of {@link BuildOrderInfo}. Computes the build order of the given
	 * {@link WorkspaceConfigSnapshot}
	 */
	public BuildOrderInfo createBuildOrderInfo(ImmutableBiMap<String, ProjectConfigSnapshot> name2Project) {
		Multimap<String, ProjectConfigSnapshot> pInversedDependencies = HashMultimap.create();
		List<ProjectConfigSnapshot> pSortedProjects = new ArrayList<>();
		Set<ImmutableList<String>> pProjectCycles = new HashSet<>();

		buildOrderInfoComputer.init(name2Project, pInversedDependencies, pSortedProjects, pProjectCycles);

		return createNewProjectBuildOrderInfo(pInversedDependencies, pSortedProjects, pProjectCycles);
	}

	/** Creates new instance of {@link BuildOrderInfo} */
	protected BuildOrderInfo createNewProjectBuildOrderInfo(
			Multimap<String, ProjectConfigSnapshot> pInversedDependencies,
			List<ProjectConfigSnapshot> pSortedProjects, Set<ImmutableList<String>> pProjectCycles) {

		return new BuildOrderInfo(pInversedDependencies, pSortedProjects, pProjectCycles);
	}

	/** Computes all collections for {@link BuildOrderInfo} */
	public static class BuildOrderInfoComputer {

		/** Populates {@code #pSortedProjects}, {@code #pInversedDependencies} and {@code pProjectCycles} */
		protected void init(ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
				Multimap<String, ProjectConfigSnapshot> pInversedDependencies,
				List<ProjectConfigSnapshot> pSortedProjects,
				Collection<ImmutableList<String>> pProjectCycles) {

			LinkedHashSet<String> orderedProjectNames = new LinkedHashSet<>();
			for (ProjectConfigSnapshot pc : getAllProjects(name2Project)) {
				for (String dependencyName : getDependencies(pc)) {
					pInversedDependencies.put(dependencyName, pc);
				}
				computeOrder(name2Project, pProjectCycles, pc, orderedProjectNames, new LinkedHashSet<>());
			}

			for (String projectName : orderedProjectNames) {
				ProjectConfigSnapshot pc = findProjectByName(name2Project, projectName);
				if (pc != null) {
					pSortedProjects.add(pc);
				}
			}
		}

		/** Computes the build order of all projects in the workspace */
		protected void computeOrder(ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
				Collection<ImmutableList<String>> pProjectCycles, ProjectConfigSnapshot pc,
				LinkedHashSet<String> orderedProjects, LinkedHashSet<String> projectStack) {

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
					ProjectConfigSnapshot depPC = findProjectByName(name2Project, depName);
					if (depPC != null) {
						computeOrder(name2Project, pProjectCycles, depPC, orderedProjects, projectStack);
					}
				}

				orderedProjects.add(pdName);
				projectStack.remove(pdName);
			}
		}

		/** Returns all projects of the given {@link WorkspaceConfigSnapshot}. */
		protected Collection<? extends ProjectConfigSnapshot> getAllProjects(
				ImmutableBiMap<String, ProjectConfigSnapshot> name2Project) {

			return name2Project.values();
		}

		/** Find the project with the given name. */
		protected ProjectConfigSnapshot findProjectByName(ImmutableBiMap<String, ProjectConfigSnapshot> name2Project,
				String name) {

			return name2Project.get(name);
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