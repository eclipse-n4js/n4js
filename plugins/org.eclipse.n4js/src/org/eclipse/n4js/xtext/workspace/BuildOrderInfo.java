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
public class BuildOrderInfo {

	/** Empty build order instance */
	public static final BuildOrderInfo NULL = new BuildOrderInfo(HashMultimap.create(),
			Lists.newArrayList(), Sets.newHashSet());

	/** Inverse set of project dependency information */
	final protected ImmutableMultimap<String, ProjectConfigSnapshot> inversedDependencies;
	/** Build order of projects */
	final protected ImmutableList<ProjectConfigSnapshot> sortedProjects;
	/** All project cycles, each cycle given as a list of project names */
	final protected ImmutableCollection<ImmutableList<String>> projectCycles;

	/** Constructor */
	public BuildOrderInfo(
			Multimap<String, ProjectConfigSnapshot> pInversedDependencies,
			List<ProjectConfigSnapshot> pSortedProjects,
			Set<ImmutableList<String>> pProjectCycles) {

		inversedDependencies = ImmutableMultimap.copyOf(pInversedDependencies);
		sortedProjects = ImmutableList.copyOf(pSortedProjects);
		projectCycles = ImmutableList.copyOf(pProjectCycles);
	}

	/** @return all project cycles as lists of project names */
	public ImmutableCollection<ImmutableList<String>> getProjectCycles() {
		return this.projectCycles;
	}

}