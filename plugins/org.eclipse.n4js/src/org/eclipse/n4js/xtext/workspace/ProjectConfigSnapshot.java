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

import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Default implementation of an {@link IProjectConfigSnapshot}.
 */
@SuppressWarnings("restriction")
public class ProjectConfigSnapshot implements IProjectConfigSnapshot {

	private final String name;
	private final URI path;
	private final ImmutableSet<String> dependencies;
	private final ImmutableList<ISourceFolderSnapshot> sourceFolders;

	/** See {@link ProjectConfigSnapshot}. */
	public ProjectConfigSnapshot(XIProjectConfig project) {
		this(project.getName(), project.getPath(), project.getDependencies(),
				project.getSourceFolders().stream().map(SourceFolderSnapshot::new).collect(Collectors.toList()));
	}

	/** See {@link ProjectConfigSnapshot}. */
	public ProjectConfigSnapshot(String name, URI path, Iterable<String> dependencies,
			Iterable<? extends ISourceFolderSnapshot> sourceFolders) {
		this.name = name;
		this.path = path;
		this.dependencies = ImmutableSet.copyOf(dependencies);
		this.sourceFolders = ImmutableList.copyOf(sourceFolders);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public URI getPath() {
		return path;
	}

	@Override
	public ImmutableSet<String> getDependencies() {
		return dependencies;
	}

	@Override
	public ImmutableCollection<? extends ISourceFolderSnapshot> getSourceFolders() {
		return sourceFolders;
	}
}
