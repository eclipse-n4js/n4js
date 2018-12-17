/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.ui.navigation;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.google.common.base.Charsets;

/**
 * Allows to create a storage for a given URI. Encoding is assumed to be UTF-8.
 *
 * TODO consider reading the encoding from the project data (pass it into the constructor).
 */
@SuppressWarnings("javadoc")
public class URIBasedStorage extends PlatformObject implements IURIBasedStorage {

	private final URI uri;

	public URIBasedStorage(URI uri) {
		this.uri = uri;
	}

	@Override
	public URI getURI() {
		return uri;
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return URIConverter.INSTANCE.createInputStream(uri);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, "org.eclipse.n4js.ts.ui", "Cannot load "
					+ getFullPath(), e));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof IURIBasedStorage))
			return false;
		IURIBasedStorage other = (IURIBasedStorage) obj;
		if (uri == null) {
			if (other.getURI() != null)
				return false;
		} else if (!uri.equals(other.getURI()))
			return false;
		return true;
	}

	@Override
	public IPath getFullPath() {
		return new Path(uri.path());
	}

	@Override
	public String getName() {
		return uri.lastSegment();
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	public String getCharset() throws CoreException {
		// when improving this, also adjust {@link ResourceNode#getCharset() accordingly
		return Charsets.UTF_8.name();
	}

}
