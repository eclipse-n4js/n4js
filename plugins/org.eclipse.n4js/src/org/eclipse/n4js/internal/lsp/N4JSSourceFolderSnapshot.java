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
package org.eclipse.n4js.internal.lsp;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.base.Optional;
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
		return "N4JS" + super.toString();
	}

	// ==============================================================================================================
	// Convenience and utility methods (do not introduce additional data)

	// FIXME reconsider!
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

	// FIXME reconsider: (1) maybe move elsewhere (2) why not return a FileURI (or at least a SafeURI<>)?
	public Iterable<URI> getContents() {
		return new Iterable<>() {
			@Override
			public Iterator<URI> iterator() {
				return Iterators.transform(getPathAsFileURI().getAllChildren(), pl -> pl.toURI());
			}
		};
	}

	// FIXME reconsider: maybe move elsewhere (e.g. in FindArtifactHelper)
	/**
	 * If the receiving source folder actually contains a file for the given fully qualified name and file extension on
	 * disk, this method will return a URI for this file of the same format as the URIs returned by method
	 * {@link #getContents()}. Otherwise, this method returns <code>null</code>.
	 * <p>
	 * The file extension may but need not contain a leading '.'.
	 * <p>
	 * Implementations are expected to be optimized for fast look-up (in particular, they should avoid iterating over
	 * all URIs returned by method {@link #getContents()}).
	 */
	public FileURI findArtifact(QualifiedName name, Optional<String> fileExtension) {
		final List<String> nameSegments = name.getSegments();
		if (nameSegments.isEmpty()) {
			return null;
		}
		final String[] nameSegmentsCpy = nameSegments.toArray(new String[nameSegments.size()]);
		final String ext = fileExtension.or("").trim();
		final String extWithDot = !ext.isEmpty() && !ext.startsWith(".") ? "." + ext : ext;
		final int idxLast = nameSegmentsCpy.length - 1;
		nameSegmentsCpy[idxLast] = nameSegmentsCpy[idxLast] + extWithDot;

		FileURI sourceFolderPath = getPathAsFileURI();
		FileURI result = sourceFolderPath.appendSegments(nameSegmentsCpy);
		if (result.exists()) {
			return result;
		}
		return null;
	}
}
