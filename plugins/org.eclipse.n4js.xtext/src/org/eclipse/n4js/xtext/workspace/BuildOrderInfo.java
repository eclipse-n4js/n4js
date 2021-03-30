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

import java.util.List;
import java.util.Set;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Implementation for sorted projects according to their build order.
 */
public class BuildOrderInfo {

	/** Empty build order instance */
	public static final BuildOrderInfo NULL = new BuildOrderInfo(Lists.newArrayList(), Sets.newHashSet());

	/** Build order of projects */
	final protected ImmutableList<ProjectConfigSnapshot> sortedProjects;
	/** All project cycles, each cycle given as a list of project names */
	final protected ImmutableCollection<ImmutableList<String>> projectCycles;

	/** Constructor */
	public BuildOrderInfo(
			List<ProjectConfigSnapshot> pSortedProjects,
			Set<ImmutableList<String>> pProjectCycles) {

		sortedProjects = ImmutableList.copyOf(pSortedProjects);
		projectCycles = ImmutableList.copyOf(pProjectCycles);
	}

	/** @return all project cycles as lists of project names */
	public ImmutableCollection<ImmutableList<String>> getProjectCycles() {
		return this.projectCycles;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" {\n");

		// sorted projects
		sb.append("    sortedProjects: [");
		if (!sortedProjects.isEmpty()) {
			sb.append(" ");
			final int maxProjects = 15;
			Iterable<String> projectsToShow = IterableExtensions.map(
					IterableExtensions.take(sortedProjects, maxProjects),
					ProjectConfigSnapshot::getName);
			sb.append(Joiner.on(", ").join(projectsToShow));
			int numRemaining = sortedProjects.size() - maxProjects;
			if (numRemaining > 0) {
				sb.append(", ... and " + numRemaining + " more ...");
			}
			sb.append(" ");
		}
		sb.append("]\n");

		// project cycles
		sb.append("    projectCycles: [");
		if (!projectCycles.isEmpty()) {
			for (List<String> cycle : projectCycles) {
				sb.append("\n        ");
				sb.append(Joiner.on(" --> ").join(cycle));
			}
			sb.append("\n    ]\n");
		} else {
			sb.append("]\n");
		}

		sb.append("}");
		return sb.toString();
	}
}
