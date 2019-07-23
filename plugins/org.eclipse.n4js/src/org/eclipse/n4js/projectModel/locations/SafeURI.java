/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.projectModel.locations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.common.base.Preconditions;

/**
 * A data type that denotes a location based on a URI. The structure of the wrapped URI is asserted by
 * {@link #validate(URI)}.
 */
public abstract class SafeURI<U extends SafeURI<U>> {

	/**
	 * The bare wrapped URI.
	 */
	private final URI wrapped;

	/**
	 * Wraps the given URI.
	 *
	 * @param wrapped
	 *            the bare URI.
	 */
	protected SafeURI(URI wrapped) {
		this.wrapped = validate(wrapped);
	}

	/**
	 * Validate the given URI to be of the correct shape.
	 *
	 * @param given
	 *            the URI.
	 * @return the URI if it
	 */
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		// Preconditions.checkArgument(!given.hasTrailingPathSeparator(), "%s must have a trailing path separator",
		// given);
		return Preconditions.checkNotNull(given);
	}

	/**
	 * Returns the bare URI.
	 *
	 * @return the wrapped uri.
	 */
	public URI toURI() {
		return wrapped;
	}

	@Override
	public int hashCode() {
		return wrapped.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SafeURI<?> other = (SafeURI<?>) obj;
		return wrapped.equals(other.wrapped);
	}

	@Override
	public String toString() {
		return toURI().toString();
	}

	/**
	 * Returns true if this URI points to a file rather than a directory.
	 */
	public abstract boolean isFile();

	/**
	 * Returns the name of the file or folder this URI points to.
	 */
	public abstract String getName();

	/**
	 * Returns true if the location exists.
	 */
	public abstract boolean exists();

	/**
	 * Returns true if this URI points to a folder rather than a file.
	 */
	public abstract boolean isDirectory();

	/**
	 * Returns all the direct children of this location. Files do never have children.
	 */
	public abstract Iterable<? extends U> getChildren();

	/**
	 * Attempts to read the content of the file this URI is pointing to.
	 */
	public abstract InputStream getContents() throws IOException;

	/**
	 * Returns the absolute path this URI is refering to.
	 */
	public abstract String getAbsolutePath();

	/**
	 * Returns a represenation of this URI that has a trailing path delimiter.
	 */
	public final U withTrailingPathDelimiter() {
		URI uri = toURI();
		if (uri.hasTrailingPathSeparator()) {
			@SuppressWarnings("unchecked")
			U result = (U) this;
			return result;
		}
		return createFrom(uri.appendSegment(""));
	}

	/**
	 * Resolve the given relative path against the current URI.
	 */
	public final U resolve(String relativePath) {
		URI base = withTrailingPathDelimiter().toURI();
		URI result = URI.createURI(relativePath).resolve(base);
		return createFrom(result);
	}

	/**
	 * Wrap the given URI in the same typesafe representation as this URI.
	 */
	protected abstract U createFrom(URI uri);

	/**
	 * Deresolve against the given base URI. E.g. return the segments that would lead to the current location when
	 * resolved against the given base.
	 */
	public List<String> deresolve(U base) {
		Preconditions.checkArgument(base.getClass().equals(getClass()));
		URI baseURI = base.withTrailingPathDelimiter().toURI();
		URI result = toURI().deresolve(baseURI, false, true, true);
		return result.segmentsList();
	}

	/**
	 * Append the given path to this location. The result is normalized.
	 */
	public final U appendPath(String path) {
		return appendRelativeURI(URI.createURI(path));
	}

	private U appendRelativeURI(URI relativeURI) {
		if (!URI.validSegments(relativeURI.segments())) {
			throw new IllegalArgumentException(String.valueOf(relativeURI));
		}
		URI base = withTrailingPathDelimiter().toURI();
		URI result = relativeURI.resolve(base);
		return createFrom(result);
	}

	/**
	 * Append the given segment to this location. The result is normalized.
	 */
	public final U appendSegment(String segment) {
		return appendSegments(new String[] { segment });
	}

	/**
	 * Append the given segments to this location. The result is normalized.
	 */
	public final U appendSegments(String[] segments) {
		return appendRelativeURI(URI.createHierarchicalURI(segments, null, null));
	}

	/**
	 * Return true, if the location is representing the empty URI.
	 */
	public final boolean isEmpty() {
		return toURI().isEmpty();
	}

	/**
	 * Return the parent of this location.
	 */
	public U getParent() {
		URI uri = toURI();
		if (uri.segmentCount() > 0) {
			return createFrom(uri.trimSegments(1));
		}
		return null;
	}

	/**
	 * Finds the nearest project root for the current location and attempt to derive the project name from the found
	 * root. May return null.
	 *
	 * @return the name or null.
	 */
	public N4JSProjectName findProjectName() {
		U root = getProjectRoot();
		if (root != null) {
			return root.getProjectName();
		}
		return null;
	}

	/**
	 * Assumes that this location is a valid project root and derives the project name from the location. root. May
	 * return null.
	 */
	public N4JSProjectName getProjectName() {
		String guess = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(this);
		if (guess != null) {
			return new N4JSProjectName(guess);
		}
		return null;
	}

	/**
	 * Return true if this is a directory that contains a {@link IN4JSProject#PACKAGE_JSON package.json} file.
	 */
	public boolean isProjectRootDirectory() {
		return isDirectory() &&
				(appendSegment(IN4JSProject.PACKAGE_JSON).isFile() ||
						appendSegment(IN4JSProject.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION).isFile());
	}

	/**
	 * Ascends the the given file-system location, until a directory is detected that qualifies as N4JS project location
	 * (e.g. contains an {@link IN4JSProject#PACKAGE_JSON} file). Returns null if this location is not nested in a
	 * project.
	 *
	 * @return the nearest project root or null, if none.
	 */
	public U getProjectRoot() {
		@SuppressWarnings("unchecked")
		U result = (U) this;
		if (isFile()) {
			result = result.getParent();
		}
		while (result != null) {
			if (result.isProjectRootDirectory()) {
				return result;
			}
			result = result.getParent();
		}
		return null;
	}

	/**
	 * Follow symbolic links and return the result.
	 */
	public abstract FileURI resolveSymLinks();

	/**
	 * Return all the files transitively contained at the current location without the files in a nested node_modules
	 * folder.
	 */
	public abstract Iterator<U> getAllChildren();

	/**
	 * Delete the current location and all contained elements.
	 */
	public abstract void delete(Consumer<? super IOException> errorHandler);

	/**
	 * Return the file system path representation of this location.
	 */
	public final Path toFileSystemPath() {
		return toJavaIoFile().toPath();
	}

	/**
	 * Return the {@link File} equivalent to this location.
	 */
	public abstract File toJavaIoFile();

	/**
	 * Obtain the equivalent file URI.
	 */
	public abstract FileURI toFileURI();

}
