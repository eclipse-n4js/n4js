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
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;

import com.google.common.hash.Funnels;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

/**
 * A naive mapping of URIs to content hashes.
 */
public class HashedFileContent {

	private static final HashFunction hashFunction = Hashing.murmur3_128();

	private final URI uri;
	private final long hash;

	/** Create a fingerprint of the given file at the given location. */
	public HashedFileContent(URI uri, File file) throws IOException {
		this.uri = uri;
		String ext = uri.fileExtension();
		if (ext == null || "ts".equals(ext) || "js".equals(ext) || "jsx".equals(ext) || "map".equals(ext)
				|| "md".equals(ext)
				|| "hbs".equals(ext)
				|| "json".equals(ext) && !"package.json".equals(uri.lastSegment())) {
			this.hash = file.length();
		} else {
			try (InputStream s = new FileInputStream(file)) {
				Hasher hasher = hashFunction.newHasher();
				s.transferTo(Funnels.asOutputStream(hasher));
				this.hash = hasher.hash().asLong();
			}
		}
	}

	/** Assign the given hash to the given uri */
	public HashedFileContent(URI uri, long hash) {
		this.uri = uri;
		this.hash = hash;
	}

	HashedFileContent(DataInput input) throws IOException {
		this.uri = URI.createURI(input.readUTF());
		this.hash = input.readLong();
	}

	void write(DataOutput out) throws IOException {
		out.writeUTF(uri.toString());
		out.writeLong(hash);
	}

	/** The URI of the file. */
	public URI getUri() {
		return uri;
	}

	/** The fingerprint. */
	public long getHash() {
		return hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (hash ^ (hash >>> 32));
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashedFileContent other = (HashedFileContent) obj;
		if (hash != other.hash)
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return uri + ":" + Long.toUnsignedString(hash);
	}
}
