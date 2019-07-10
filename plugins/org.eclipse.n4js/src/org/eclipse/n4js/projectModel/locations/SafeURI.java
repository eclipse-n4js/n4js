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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.URI;

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

	public abstract boolean isFile();

	public abstract String getName();

	public abstract boolean exists();

	public abstract boolean isDirectory();

	public abstract Iterable<? extends U> getChildren();

	public abstract InputStream getContents() throws IOException;

	public abstract String getAbsolutePath();

	public U withTrailingPathDelimiter() {
		if (toURI().hasTrailingPathSeparator()) {
			return self();
		}
		return appendSegment("");
	}

	public abstract U resolve(String relativePath);

	/*
	 * // make sure workspace location has trailing path separator (for correct resolution) if
	 * (!workspaceLocation.hasTrailingPathSeparator()) { workspaceLocation = workspaceLocation.appendSegment(""); }
	 */
	public List<String> deresolve(U base) {
		Preconditions.checkArgument(base.getClass().equals(getClass()));
		URI result = toURI().deresolve(base.toURI());
		return result.segmentsList();
	}

	public abstract U appendPath(String path);

	public abstract U appendSegment(String segment);

	public abstract U appendSegments(String[] segments);

	public boolean isEmpty() {
		return toURI().isEmpty();
	}

	public abstract U getParent();

	public abstract FileURI resolveSymLinks();

	public abstract Iterator<? extends U> getAllChildren();

	public boolean isParent(U nestedLocation) {
		return toFileSystemPath().startsWith(nestedLocation.toFileSystemPath());
	}

	public abstract void delete(Consumer<? super IOException> errorHandler);

	public abstract Path toFileSystemPath();

	protected abstract U self();

	public U getParentOf(Predicate<? super String> predicate) {
		U result = self();
		while (result != null && !predicate.test(result.getName())) {
			result = result.getParent();
		}
		if (result != null) {
			return result.getParent();
		}
		return null;

	}

}
