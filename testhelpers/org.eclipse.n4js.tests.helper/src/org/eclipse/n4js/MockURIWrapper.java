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
package org.eclipse.n4js;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.locations.SafeURI;

/**
 *
 */
public class MockURIWrapper extends SafeURI {

	public MockURIWrapper() {
		super(URI.createURI("mock://should.be.never.used"));
	}

	@Override
	protected URI validate(URI given) throws IllegalArgumentException, NullPointerException {
		return given;
	}

	@Override
	public boolean isFile() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean exists() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDirectory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<? extends SafeURI> getChildren() {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getContents() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAbsolutePath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI resolve(String relativePath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI appendSegment(String packageJson) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI appendSegments(String[] segments) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI appendPath(String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI getParent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SafeURI resolveSymLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<? extends SafeURI> getAllChildren() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Consumer<? super IOException> errorHandler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path toFileSystemPath() {
		throw new UnsupportedOperationException();
	}

}
