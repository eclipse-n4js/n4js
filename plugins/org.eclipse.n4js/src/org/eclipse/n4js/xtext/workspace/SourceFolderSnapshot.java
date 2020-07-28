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
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * Immutable equivalent to an {@link ISourceFolder}.
 */
@SuppressWarnings("restriction")
public class SourceFolderSnapshot {

	private final String name;
	private final URI path;

	/** See {@link SourceFolderSnapshot}. */
	public SourceFolderSnapshot(ISourceFolder sourceFolder) {
		this(sourceFolder.getName(), sourceFolder.getPath());
	}

	/** See {@link SourceFolderSnapshot}. */
	public SourceFolderSnapshot(String name, URI path) {
		this.name = name;
		this.path = path;
	}

	/**
	 * Return the name of the source folder.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the path of the source folder.
	 */
	public URI getPath() {
		return path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		SourceFolderSnapshot other = (SourceFolderSnapshot) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "SourceFolderSnapshot [name=" + name + ", path=" + path + "]";
	}

}
