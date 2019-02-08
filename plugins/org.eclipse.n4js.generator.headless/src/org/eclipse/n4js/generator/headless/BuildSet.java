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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSProject;

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
	final Set<N4JSProject> requestedProjects;

	/**
	 * The projects which were discovered as dependencies of the above projects, without having been requested to be
	 * compiled by the user. In other words, these projects are only being compiled because a requested project depends
	 * on them.
	 */
	final Set<N4JSProject> discoveredProjects;

	/**
	 * A predicate that indicates whether or not a given resource, identified by its URI, should be processed. If the
	 * user requested compilation of specific single files, then this predicate applies only to those files, and no
	 * others. In all other cases, the predicate applies to every file, i.e., it always returns <code>true</code>.
	 */
	final Predicate<URI> resourceFilter;

	/**
	 * Returns a set of all {@link N4JSProject}s this build set contains.
	 */
	public Set<N4JSProject> getAllProjects() {
		return Sets.union(this.requestedProjects, this.discoveredProjects);
	}

	BuildSet(Collection<N4JSProject> requestedProjects, Collection<N4JSProject> discoveredProjects,
			Predicate<URI> projectFilter) {
		this.requestedProjects = Collections.unmodifiableSet(new LinkedHashSet<>(requestedProjects));
		this.discoveredProjects = Collections.unmodifiableSet(new LinkedHashSet<>(discoveredProjects));
		this.resourceFilter = projectFilter;
	}

	BuildSet(Set<N4JSProject> requestedProjects, Set<N4JSProject> discoveredProjects,
			Predicate<URI> projectFilter) {
		this.requestedProjects = Collections.unmodifiableSet(requestedProjects);
		this.discoveredProjects = Collections.unmodifiableSet(discoveredProjects);
		this.resourceFilter = projectFilter;
	}

}
