/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.workspace;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * Default implementation of an {@link ISourceFolderSnapshot}.
 */
@SuppressWarnings("restriction")
public class SourceFolderSnapshot implements ISourceFolderSnapshot {

	private final String name;
	private final URI path;

	/** See {@link SourceFolderSnapshot}. */
	public SourceFolderSnapshot(ISourceFolder sourceFolder) {
		this(sourceFolder.getName(), sourceFolder.getPath());
	}

	/** See {@link SourceFolderSnapshot}. */
	public SourceFolderSnapshot(String name, URI path) {
		this.name = name;
		this.path = path;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public URI getPath() {
		return path;
	}
}
