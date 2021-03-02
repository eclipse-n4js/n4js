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

import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.UriUtil;
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * Immutable equivalent to an {@link ISourceFolder}.
 */
@SuppressWarnings("restriction")
public class SourceFolderSnapshot extends Snapshot {

	private final String name;
	private final URI path;

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

	/**
	 * Tells whether the given URI is a source file that belongs to this source folder. By default, this returns
	 * <code>true</code> iff this source folder's {@link #getPath() path} is a prefix of the given URI. However,
	 * language-specific implementations may decide to return <code>true</code> for fewer URIs, resulting in those URIs
	 * to be excluded from builds.
	 *
	 * @see ISourceFolder#contains(URI)
	 */
	public boolean contains(URI uri) {
		return UriUtil.isPrefixOf(getPath(), uri);
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				name,
				path);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		SourceFolderSnapshot other = (SourceFolderSnapshot) obj;
		return Objects.equals(name, other.name)
				&& Objects.equals(path, other.path);
	}

	@Override
	public String toString() {
		return "SourceFolderSnapshot [name=" + name + ", path=" + path + "]";
	}
}
