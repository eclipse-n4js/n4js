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
package org.eclipse.n4js.ide.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.eclipse.emf.common.util.URI;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

/**
 * A naive mapping of URIs to content hashes.
 */
public class HashedFileContent {

	private final URI uri;
	private final long hash;

	/**
	 * Create a fingerprint of the given file at the given location.
	 */
	public HashedFileContent(URI uri, File file) throws IOException {
		this.uri = uri;
		this.hash = Files.asByteSource(file).hash(Hashing.farmHashFingerprint64()).asLong();
	}

	HashedFileContent(ObjectInput input) throws IOException {
		this.uri = URI.createURI(input.readUTF());
		this.hash = input.readLong();
	}

	void write(ObjectOutput out) throws IOException {
		out.writeUTF(uri.toString());
		out.writeLong(hash);
	}

	/**
	 * The URI of the file.
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * The fingerprint.
	 */
	public long getHash() {
		return hash;
	}

	@Override
	public String toString() {
		return uri + ":" + Long.toUnsignedString(hash);
	}
}
