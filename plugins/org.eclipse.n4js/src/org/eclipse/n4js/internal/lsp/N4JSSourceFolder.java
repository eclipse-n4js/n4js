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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.lsp.IN4JSProjectConfig;
import org.eclipse.n4js.projectModel.lsp.IN4JSSourceFolder;

import com.google.common.collect.FluentIterable;

/**
 * Wrapper around {@link IN4JSSourceContainer}.
 */
public class N4JSSourceFolder implements IN4JSSourceFolder {

	private final IN4JSSourceContainer delegate;
	private final IN4JSProjectConfig project;

	/**
	 * Constructor
	 */
	public N4JSSourceFolder(IN4JSProjectConfig project, IN4JSSourceContainer delegate) {
		this.project = project;
		this.delegate = delegate;
	}

	@Override
	public IN4JSProjectConfig getProject() {
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

	@Override
	public boolean contains(URI uri) {
		URI path = getPath();
		uri = uri.hasTrailingPathSeparator() ? uri : uri.appendSegment("");
		if (!uri.toFileString().startsWith(path.toFileString())) {
			return false;
		}
		URI relUri = uri.deresolve(path);
		if (relUri.segmentCount() > 0) {
			boolean uriInsideNodeModules = relUri.segment(0).equals(N4JSGlobals.NODE_MODULES);
			return !uriInsideNodeModules;
		}
		return true;
	}

	@Override
	public List<URI> getAllResources() {
		return FluentIterable.from(delegate).toList();
	}

}
