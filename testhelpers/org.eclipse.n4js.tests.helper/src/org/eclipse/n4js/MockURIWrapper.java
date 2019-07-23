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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;

/**
 *
 */
public class MockURIWrapper extends SafeURI<MockURIWrapper> {

	MockURIWrapper() {
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
	public Iterable<? extends MockURIWrapper> getChildren() {
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
	protected MockURIWrapper createFrom(URI uri) {
		throw new UnsupportedOperationException();
	}

	@Override
	public MockURIWrapper getParent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileURI resolveSymLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<MockURIWrapper> getAllChildren() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(Consumer<? super IOException> errorHandler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileURI toFileURI() {
		throw new UnsupportedOperationException();
	}

	@Override
	public File toJavaIoFile() {
		throw new UnsupportedOperationException();
	}

}
