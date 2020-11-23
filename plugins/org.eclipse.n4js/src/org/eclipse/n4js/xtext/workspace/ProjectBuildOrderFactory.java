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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

/**
 * Implementation for sorted projects according to their build order.
 */
public class ProjectBuildOrderFactory {

	/** Computes the build order of the given {@link WorkspaceConfigSnapshot} */
	public ProjectBuildOrderInfo compute(WorkspaceConfigSnapshot workspaceConfig) {
		Multimap<String, ProjectConfigSnapshot> pInversedDependencies = HashMultimap.create();
		List<ProjectConfigSnapshot> pSortedProjects = new ArrayList<>();
		Set<ImmutableList<String>> pProjectCycles = new HashSet<>();

		init(workspaceConfig, pInversedDependencies, pSortedProjects, pProjectCycles);

		return createNewProjectBuildOrderInfo(workspaceConfig, pInversedDependencies, pSortedProjects, pProjectCycles);
	}

	/** Creates new instance of {@link ProjectBuildOrderInfo} */
	protected ProjectBuildOrderInfo createNewProjectBuildOrderInfo(WorkspaceConfigSnapshot workspaceConfig,
			Multimap<String, ProjectConfigSnapshot> pInversedDependencies, List<ProjectConfigSnapshot> pSortedProjects,
			Set<ImmutableList<String>> pProjectCycles) {

		return new ProjectBuildOrderInfo(workspaceConfig, pInversedDependencies, pSortedProjects, pProjectCycles);
	}

	/** Populates {@code #pSortedProjects}, {@code #pInversedDependencies} and {@code pProjectCycles} */
	protected void init(WorkspaceConfigSnapshot workspaceConfig,
			Multimap<String, ProjectConfigSnapshot> pInversedDependencies,
			List<ProjectConfigSnapshot> pSortedProjects, Collection<ImmutableList<String>> pProjectCycles) {

		LinkedHashSet<String> orderedProjectNames = new LinkedHashSet<>();
		for (ProjectConfigSnapshot pc : getAllProjects(workspaceConfig)) {
			for (String dependencyName : getDependencies(pc)) {
				pInversedDependencies.put(dependencyName, pc);
			}
			computeOrder(workspaceConfig, pProjectCycles, pc, orderedProjectNames, new LinkedHashSet<>());
		}

		for (String projectName : orderedProjectNames) {
			ProjectConfigSnapshot pc = findProjectByName(workspaceConfig, projectName);
			if (pc != null) {
				pSortedProjects.add(pc);
			}
		}
	}

	/** Computes the build order of all projects in the workspace */
	protected void computeOrder(WorkspaceConfigSnapshot workspaceConfig,
			Collection<ImmutableList<String>> pProjectCycles,
			ProjectConfigSnapshot pc, LinkedHashSet<String> orderedProjects,
			LinkedHashSet<String> projectStack) {

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
				ProjectConfigSnapshot depPC = findProjectByName(workspaceConfig, depName);
				if (depPC != null) {
					computeOrder(workspaceConfig, pProjectCycles, depPC, orderedProjects, projectStack);
				}
			}

			orderedProjects.add(pdName);
			projectStack.remove(pdName);
		}
	}

	/** Returns all projects of the given {@link WorkspaceConfigSnapshot}. */
	protected Collection<? extends ProjectConfigSnapshot> getAllProjects(WorkspaceConfigSnapshot workspaceConfig) {
		return workspaceConfig.getProjects();
	}

	/** Find the project with the given name. */
	protected ProjectConfigSnapshot findProjectByName(WorkspaceConfigSnapshot workspaceConfig, String name) {
		return workspaceConfig.findProjectByName(name);
	}

	/**
	 * Returns the project dependencies to be considered for build order computation. By default, simply returns
	 * {@link ProjectConfigSnapshot#getDependencies()}; subclasses may override to customize this.
	 */
	protected Set<String> getDependencies(ProjectConfigSnapshot pc) {
		return pc.getDependencies();
	}
}