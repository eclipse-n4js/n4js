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
 * Immutable equivalent to an {@link ISourceFolder}.
 */
@SuppressWarnings("restriction")
public class SourceFolderSnapshot {

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

	/**
	 * Return the name of the source folder.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the path of the source folder.
	 */
	public URI getPath() {
		return path;
	}
}
