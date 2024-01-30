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

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.UriExtensions;

/**
 * N4JS-specific adjustments to {@link SourceFolderSnapshot}.
 */
public class N4JSSourceFolderSnapshot extends SourceFolderSnapshot {

	private final SourceContainerType type;
	private final String relativeLocation;
	private final FileSystemScannerAceptor fssAcceptor;

	/** Creates a new {@link N4JSSourceFolderSnapshot}. */
	public N4JSSourceFolderSnapshot(String name, URI path, SourceContainerType type, String relativeLocation,
			List<String> workspaces) {

		super(name, path);
		this.type = type;
		this.relativeLocation = relativeLocation;
		this.fssAcceptor = new FileSystemScannerAceptor(path, workspaces);
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

	@Override
	public List<URI> getAllResources(IFileSystemScanner scanner) {
		scanner.scan(getPath(), fssAcceptor);
		return fssAcceptor.getSources();
	}

	@Override
	public boolean contains(URI uri) {
		if (!super.contains(uri)) {
			return false;
		}
		for (PathMatcher pathMatcher : fssAcceptor.getPathMatchers()) {
			if (pathMatcher.matches(Path.of(uri.toFileString()))) {
				return false;
			}
		}

		return true;
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

}
