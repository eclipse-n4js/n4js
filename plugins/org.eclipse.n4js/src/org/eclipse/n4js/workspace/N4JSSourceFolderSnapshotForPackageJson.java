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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IFileSystemScanner;

/**
 * An {@link N4JSSourceFolderSnapshot} that only contains a single <code>package.json</code> file.
 */
public class N4JSSourceFolderSnapshotForPackageJson extends N4JSSourceFolderSnapshot {

	private final URI packageJsonURI;

	/** Creates a new {@link N4JSSourceFolderSnapshotForPackageJson}. */
	public N4JSSourceFolderSnapshotForPackageJson(N4JSSourceFolderForPackageJson sourceFolder) {
		super(sourceFolder.getName(), sourceFolder.getPath(), sourceFolder.getType(),
				sourceFolder.getRelativePath(), null);
		this.packageJsonURI = sourceFolder.getPackageJsonURI();
	}

	/** @return the URI of the <code>package.json</code> file of this source folder. */
	public URI getPackageJsonURI() {
		return packageJsonURI;
	}

	@Override
	public boolean contains(URI uri) {
		return packageJsonURI.equals(uri);
	}

	@Override
	public List<URI> getAllResources(IFileSystemScanner scanner) {
		return Collections.singletonList(packageJsonURI);
	}

	@Override
	protected int computeHashCode() {
		return super.computeHashCode(); // packageJsonURI is derived, so no need to add anything here
	}

	@Override
	protected boolean computeEquals(Object obj) {
		return super.computeEquals(obj); // packageJsonURI is derived, so no need to add anything here
	}
}
