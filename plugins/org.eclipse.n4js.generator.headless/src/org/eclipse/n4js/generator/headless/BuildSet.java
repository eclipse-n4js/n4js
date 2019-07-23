/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.generator.headless;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Container for projects used by {@link N4HeadlessCompiler} to discover projects and calculate build orders.
 *
 * Use the {@link BuildSetComputer} to create {@link BuildSet} instances.
 *
 * This data structure is immutable.
 */
public class BuildSet {

	/**
	 * Returns a new {@link BuildSet} which represent the combination of {@code set1} with {@code set2}, by adding all
	 * {@link #discoveredProjects} of {@code set2} to the discovered projects of {@code set1}.
	 *
	 * The set of requested projects in {@code set2} as well as its {@code #resourceFilter} are ignored.
	 */
	public static BuildSet combineDiscovered(BuildSet set1, BuildSet set2) {
		return new BuildSet(set1.requestedProjects,
				Sets.union(set1.discoveredProjects, set2.discoveredProjects),
				set1.resourceFilter);
	}

	/**
	 * The projects which the user explicitly requested to be compiled. If the user requested compilation of specific
	 * single files, then this list contains the projects containing the files.
	 */
	final Set<IN4JSProject> requestedProjects;

	/**
	 * The projects which were discovered as dependencies of the above projects, without having been requested to be
	 * compiled by the user. In other words, these projects are only being compiled because a requested project depends
	 * on them.
	 */
	final Set<IN4JSProject> discoveredProjects;

	/**
	 * A predicate that indicates whether or not a given resource, identified by its URI, should be processed. If the
	 * user requested compilation of specific single files, then this predicate applies only to those files, and no
	 * others. In all other cases, the predicate applies to every file, i.e., it always returns <code>true</code>.
	 */
	final Predicate<FileURI> resourceFilter;

	/**
	 * Returns a set of all {@link IN4JSProject N4JS projects} this build set contains.
	 */
	public Set<IN4JSProject> getAllProjects() {
		return Sets.union(this.requestedProjects, this.discoveredProjects);
	}

	BuildSet(Collection<IN4JSProject> requestedProjects, Collection<IN4JSProject> discoveredProjects,
			Predicate<FileURI> projectFilter) {
		this.requestedProjects = ImmutableSet.copyOf(requestedProjects);
		this.discoveredProjects = ImmutableSet.copyOf(discoveredProjects);
		this.resourceFilter = projectFilter;
	}

}
