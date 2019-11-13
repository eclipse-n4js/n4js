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
package org.eclipse.n4js.internal.lsp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IFileSystemScanner;

/**
 * Wrapper around {@link IN4JSSourceContainer}.
 */
public class N4JSSourceFolder implements IN4JSSourceFolder {

	private final IN4JSSourceContainer delegate;
	private final N4JSProjectConfig project;

	/**
	 * Constructor
	 */
	public N4JSSourceFolder(N4JSProjectConfig project, IN4JSSourceContainer delegate) {
		this.project = project;
		this.delegate = delegate;
	}

	@Override
	public N4JSProjectConfig getProject() {
		return project;
	}

	@Override
	public String getName() {
		return delegate.getRelativeLocation();
	}

	@Override
	public URI getPath() {
		return delegate.getLocation().withTrailingPathDelimiter().toURI();
	}

	/**
	 * Excludes all files in the folder {@link N4JSGlobals#NODE_MODULES}
	 *
	 * @return a list of all URIs that are passed to the acceptor of {@link IFileSystemScanner#scan(URI, IAcceptor)}
	 */
	@Override
	public List<URI> getAllResources(IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>();
		URI projectBase = getPath();
		scanner.scan(projectBase, (uri) -> {
			URI relativeUri = uri.deresolve(projectBase);
			if (relativeUri.segmentCount() > 0 && !relativeUri.segment(0).equals(N4JSGlobals.NODE_MODULES)) {
				uris.add(uri);
			}
		});

		return uris;
	}

}
