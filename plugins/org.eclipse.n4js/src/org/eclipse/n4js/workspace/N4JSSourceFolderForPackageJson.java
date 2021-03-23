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
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.xtext.util.IFileSystemScanner;

/**
 * Special implementation of {@link IN4JSSourceFolder} for <code>package.json</code> files, not {@link #contains(URI)
 * containing} anything except the single <code>package.json</code> file located in a project's root folder.
 */
public class N4JSSourceFolderForPackageJson implements IN4JSSourceFolder {

	private final N4JSProjectConfig project;
	private final URI packageJsonURI;

	/** Creates a new {@link N4JSSourceFolderForPackageJson}. */
	public N4JSSourceFolderForPackageJson(N4JSProjectConfig project) {
		this.project = project;
		this.packageJsonURI = project.getPathAsFileURI().appendSegment(N4JSGlobals.PACKAGE_JSON).toURI();
	}

	@Override
	public N4JSProjectConfig getProject() {
		return project;
	}

	@Override
	public URI getPath() {
		return project.getPath();
	}

	/** URI of the <code>package.json</code> file. */
	public URI getPackageJsonURI() {
		return packageJsonURI;
	}

	@Override
	public String getName() {
		return N4JSGlobals.PACKAGE_JSON;
	}

	@Override
	public SourceContainerType getType() {
		return SourceContainerType.SOURCE;
	}

	@Override
	public String getRelativePath() {
		return ".";
	}

	@Override
	public List<URI> getAllResources(IFileSystemScanner scanner) {
		return Collections.singletonList(packageJsonURI);
	}

	@Override
	public boolean contains(URI uri) {
		return packageJsonURI.equals(uri);
	}
}
