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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig.SourceContainerForPackageJson;

/**
 * An {@link N4JSSourceFolderSnapshot} that only contains a single <code>package.json</code> file.
 */
public class N4JSSourceFolderSnapshotForPackageJson extends N4JSSourceFolderSnapshot {

	private final URI packageJsonURI;

	/** Creates a new {@link N4JSSourceFolderSnapshotForPackageJson}. */
	public N4JSSourceFolderSnapshotForPackageJson(SourceContainerForPackageJson sourceFolder) {
		super(sourceFolder.getName(), sourceFolder.getPath());
		this.packageJsonURI = sourceFolder.pckjsonURI;
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
	protected int computeHashCode() {
		return super.computeHashCode(); // packageJsonURI is derived, so no need to add anything here
	}

	@Override
	protected boolean computeEquals(Object obj) {
		return super.computeEquals(obj); // packageJsonURI is derived, so no need to add anything here
	}
}
