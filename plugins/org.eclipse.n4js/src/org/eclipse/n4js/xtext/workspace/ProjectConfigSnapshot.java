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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.collect.ImmutableSet;

/**
 * An immutable equivalent to {@link IProjectConfig}.
 */
@SuppressWarnings("restriction")
public class ProjectConfigSnapshot {

	private final String name;
	private final URI path;
	private final ImmutableSet<String> dependencies;
	private final ImmutableSet<SourceFolderSnapshot> sourceFolders;

	/** See {@link ProjectConfigSnapshot}. */
	public ProjectConfigSnapshot(XIProjectConfig project) {
		this(project.getName(), project.getPath(), project.getDependencies(),
				project.getSourceFolders().stream().map(SourceFolderSnapshot::new)
						.collect(ImmutableSet.toImmutableSet()));
	}

	/** See {@link ProjectConfigSnapshot}. */
	public ProjectConfigSnapshot(String name, URI path, Iterable<String> dependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders) {
		this.name = name;
		this.path = path;
		this.dependencies = ImmutableSet.copyOf(dependencies);
		this.sourceFolders = ImmutableSet.copyOf(sourceFolders);
	}

	/**
	 * Return the project name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the project path.
	 */
	public URI getPath() {
		return path;
	}

	/**
	 * Return the project dependencies.
	 */
	public ImmutableSet<String> getDependencies() {
		return dependencies;
	}

	/**
	 * Return the project source folders.
	 */
	public ImmutableSet<? extends SourceFolderSnapshot> getSourceFolders() {
		return sourceFolders;
	}

}
