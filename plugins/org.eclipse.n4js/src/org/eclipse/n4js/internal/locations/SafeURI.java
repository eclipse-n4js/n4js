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
package org.eclipse.n4js.internal.locations;

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
 * A project location is a URI that does not end with an empty segment, e.g it points to the name of a directory.
 */
public abstract class SafeURI extends AbstractUriWrapper {

	/**
	 * Constructor.
	 */
	protected SafeURI(URI location) {
		super(location);
	}

	@Override
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		super.validate(given);
		// Preconditions.checkArgument(!given.hasTrailingPathSeparator(), "%s must have a trailing path separator",
		// given);
		return given;
	}

	public abstract boolean isFile();

	public abstract String getName();

	public abstract boolean exists();

	public abstract boolean isDirectory();

	public abstract Iterable<? extends SafeURI> getChildren();

	public abstract InputStream getContents() throws IOException;

	public abstract String getAbsolutePath();

	public abstract SafeURI resolve(String relativePath);

	/*
	 * // make sure workspace location has trailing path separator (for correct resolution) if
	 * (!workspaceLocation.hasTrailingPathSeparator()) { workspaceLocation = workspaceLocation.appendSegment(""); }
	 */
	public List<String> deresolve(SafeURI base) {
		Preconditions.checkArgument(base.getClass().equals(getClass()));
		URI result = toURI().deresolve(base.toURI());
		return result.segmentsList();
	}

	public abstract SafeURI appendPath(String path);

	public abstract SafeURI appendSegment(String segment);

	public abstract SafeURI appendSegments(String[] segments);

	public boolean isEmpty() {
		return toURI().isEmpty();
	}

	public abstract SafeURI getParent();

	public abstract SafeURI resolveSymLinks();

	public abstract Iterator<? extends SafeURI> getAllChildren();

	public boolean isParent(SafeURI nestedLocation) {
		return toFileSystemPath().startsWith(nestedLocation.toFileSystemPath());
	}

	public abstract void delete(Consumer<? super IOException> errorHandler);

	public abstract Path toFileSystemPath();

	public SafeURI getParentOf(Predicate<? super String> predicate) {
		SafeURI result = this;
		while (result != null && !predicate.test(result.getName())) {
			result = result.getParent();
		}
		if (result != null) {
			return result.getParent();
		}
		return null;

	}

}
