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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependencies == null) ? 0 : dependencies.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((sourceFolders == null) ? 0 : sourceFolders.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectConfigSnapshot other = (ProjectConfigSnapshot) obj;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		} else if (!dependencies.equals(other.dependencies))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (sourceFolders == null) {
			if (other.sourceFolders != null)
				return false;
		} else if (!sourceFolders.equals(other.sourceFolders))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectConfigSnapshot [name=" + name + ", path=" + path + ", dependencies=" + dependencies
				+ ", sourceFolders=" + sourceFolders + "]";
	}

}
