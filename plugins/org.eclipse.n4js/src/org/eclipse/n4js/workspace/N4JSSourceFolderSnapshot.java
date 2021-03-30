/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace;

import java.util.Iterator;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.collect.Iterators;

/**
 * N4JS-specific adjustments to {@link SourceFolderSnapshot}.
 */
public class N4JSSourceFolderSnapshot extends SourceFolderSnapshot {

	private final SourceContainerType type;
	private final String relativeLocation;

	/** Creates a new {@link N4JSSourceFolderSnapshot}. */
	public N4JSSourceFolderSnapshot(String name, URI path, SourceContainerType type, String relativeLocation) {
		super(name, path);
		this.type = type;
		this.relativeLocation = relativeLocation;
	}

	/** The {@link SourceContainerType type}. */
	public SourceContainerType getType() {
		return type;
	}

	/** The relative location of this source folder inside its parent project. */
	public String getRelativeLocation() {
		return relativeLocation;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(
				super.computeHashCode(),
				type,
				relativeLocation);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		N4JSSourceFolderSnapshot other = (N4JSSourceFolderSnapshot) obj;
		return super.computeEquals(obj)
				&& type == other.type
				&& Objects.equals(relativeLocation, other.relativeLocation);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " { name: " + getName()
				+ ", type: " + type
				+ ", relativeLocation: " + relativeLocation
				+ ", path: " + getPath() + " }";
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	/** Returns this source folder's {@link #getPath() path} as a {@link FileURI}. */
	public FileURI getPathAsFileURI() {
		return new FileURI(new UriExtensions().withEmptyAuthority(getPath()));
	}

	/** Tells whether this is a folder for N4JS source files. */
	public boolean isSource() {
		return type == SourceContainerType.SOURCE;
	}

	/** Tells whether this is a folder for external source files. */
	public boolean isExternal() {
		return type == SourceContainerType.EXTERNAL;
	}

	/** Tells whether this is a folder for test source files. */
	public boolean isTest() {
		return type == SourceContainerType.TEST;
	}

	/**
	 * Convenience method. Returns an iterable of all files in this source folder.
	 * <p>
	 * IMPORTANT: will access the disk and returns the <em>current</em> state on disk; repeated invocations of the
	 * returned iterable's {@link Iterable#iterator() #iterator()} method may yield different results!
	 *
	 * @see SafeURI#getAllChildren()
	 */
	public Iterable<URI> getContents() {
		return new Iterable<>() {
			@Override
			public Iterator<URI> iterator() {
				return Iterators.transform(getPathAsFileURI().getAllChildren(), pl -> pl.toURI());
			}
		};
	}
}
