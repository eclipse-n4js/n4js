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

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSProject;

/**
 * Container for projects used by {@link N4HeadlessCompiler} to discover projects and calculate build orders.
 */
class BuildSet {

	/**
	 * The projects which the user explicitly requested to be compiled. If the user requested compilation of specific
	 * single files, then this list contains the projects containing the files.
	 */
	final List<N4JSProject> requestedProjects;

	/**
	 * The projects which were discovered as dependencies of the above projects, without having been requested to be
	 * compiled by the user. In other words, these projects are only being compiled because a requested project depends
	 * on them.
	 */
	final List<N4JSProject> discoveredProjects;

	/**
	 * A predicate that indicates whether or not a given resource, identified by its URI, should be processed. If the
	 * user requested compilation of specific single files, then this predicate applies only to those files, and no
	 * others. In all other cases, the predicate applies to every file, i.e., it always returns <code>true</code>.
	 */
	final Predicate<URI> resourceFilter;

	public BuildSet(List<N4JSProject> requestedProjects, List<N4JSProject> discoveredProjects,
			Predicate<URI> projectFilter) {
		this.requestedProjects = Collections.unmodifiableList(requestedProjects);
		this.discoveredProjects = Collections.unmodifiableList(discoveredProjects);
		this.resourceFilter = projectFilter;
	}

}