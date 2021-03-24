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
package org.eclipse.n4js.workspace;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.utils.OSInfo;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSSourceFolderScanner;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.Strings;

/**
 * Wrapper around {@link SourceContainerDescription}.
 */
public class N4JSSourceFolder implements IN4JSSourceFolder {

	private final N4JSProjectConfig project;
	private final SourceContainerType type;
	private final String relativePath;
	private final FileURI absolutePath;

	/**
	 * Constructor
	 */
	public N4JSSourceFolder(N4JSProjectConfig project, SourceContainerType type, String relativePath) {
		this.project = Objects.requireNonNull(project);
		this.type = Objects.requireNonNull(type);
		this.relativePath = Objects.requireNonNull(relativePath);
		this.absolutePath = createAbsolutePath(project, relativePath);
	}

	protected FileURI createAbsolutePath(N4JSProjectConfig project, String relativePath) {
		if (!Strings.isEmpty(relativePath) && !relativePath.equals(".")) {
			String linuxPath = OSInfo.isWindows() ? relativePath.replace(File.separatorChar, '/') : relativePath;
			return project.getPathAsFileURI().appendPath(linuxPath);
		}
		return project.getPathAsFileURI();
	}

	@Override
	public N4JSProjectConfig getProject() {
		return project;
	}

	@Override
	public String getName() {
		return relativePath;
	}

	@Override
	public SourceContainerType getType() {
		return type;
	}

	@Override
	public String getRelativePath() {
		return relativePath;
	}

	@Override
	public URI getPath() {
		return absolutePath.withTrailingPathDelimiter().toURI();
	}

	public FileURI getPathAsFileURI() {
		return absolutePath;
	}

	@Override
	public List<URI> getAllResources(IFileSystemScanner scanner) {
		return N4JSSourceFolderScanner.findAllSourceFilesInFolder(getPath(), scanner);
	}

}
