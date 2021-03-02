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

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.collect.ImmutableSet;

/**
 * An immutable equivalent to {@link IProjectConfig}.
 */
@SuppressWarnings("restriction")
public class ProjectConfigSnapshot extends Snapshot {

	private final String name;
	private final URI path;
	private final ImmutableSet<URI> projectDescriptionUris;
	private final boolean indexOnly;
	private final boolean generatorEnabled;
	private final ImmutableSet<String> dependencies;
	private final ImmutableSet<SourceFolderSnapshot> sourceFolders;

	/** See {@link ProjectConfigSnapshot}. */
	public ProjectConfigSnapshot(String name, URI path, Iterable<? extends URI> projectDescriptionUris,
			boolean indexOnly, boolean generatorEnabled, Iterable<String> dependencies,
			Iterable<? extends SourceFolderSnapshot> sourceFolders) {

		this.name = name;
		this.path = path;
		this.projectDescriptionUris = ImmutableSet.copyOf(projectDescriptionUris);
		this.indexOnly = indexOnly;
		this.generatorEnabled = generatorEnabled;
		this.dependencies = ImmutableSet.copyOf(dependencies);
		this.sourceFolders = ImmutableSet.copyOf(sourceFolders);
	}

	/** Return the project name. */
	public String getName() {
		return name;
	}

	/** Return the project path. */
	public URI getPath() {
		return path;
	}

	/** Return all {@link URI} of files that describe the project. */
	public Collection<URI> getProjectDescriptionUris() {
		return projectDescriptionUris;
	}

	/**
	 * Returns true iff this project will be indexed only, i.e. neither validated nor generated.
	 *
	 * @see XIProjectConfig#indexOnly()
	 */
	public boolean indexOnly() {
		return indexOnly;
	}

	/** Tells whether this project emits output files for its source files. */
	public boolean isGeneratorEnabled() {
		return generatorEnabled;
	}

	/** Return the project dependencies. */
	public ImmutableSet<String> getDependencies() {
		return dependencies;
	}

	/** Return the project source folders. */
	public ImmutableSet<? extends SourceFolderSnapshot> getSourceFolders() {
		return sourceFolders;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				name,
				path,
				projectDescriptionUris,
				indexOnly,
				generatorEnabled,
				dependencies,
				sourceFolders);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectConfigSnapshot other = (ProjectConfigSnapshot) obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(path, other.path)
				&& Objects.equals(projectDescriptionUris, other.projectDescriptionUris)
				&& indexOnly == other.indexOnly
				&& generatorEnabled == other.generatorEnabled
				&& Objects.equals(dependencies, other.dependencies)
				&& Objects.equals(sourceFolders, other.sourceFolders);
	}

	@Override
	public String toString() {
		return "ProjectConfigSnapshot [name=" + name + ", path=" + path + ", dependencies=" + dependencies
				+ ", sourceFolders=" + sourceFolders + "]";
	}

}
