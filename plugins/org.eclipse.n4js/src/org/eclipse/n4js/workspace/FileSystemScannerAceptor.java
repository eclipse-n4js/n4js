/**
 * Copyright (c) 2024 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.utils.FileSystemScanner.IFileSystemScannerAcceptor;

/**
 * File acceptor that skips nested workspace projects.
 */
public class FileSystemScannerAceptor implements IFileSystemScannerAcceptor<URI> {
	private final List<PathMatcher> pathMatchers;
	private List<URI> sources = new ArrayList<>();

	/**
	 * Constructor
	 */
	@SuppressWarnings("resource")
	public FileSystemScannerAceptor(URI uri, List<String> workspaces) {
		super();
		this.pathMatchers = new ArrayList<>();
		Path root = Path.of(uri.toFileString());
		if (workspaces != null) {
			for (String glob : workspaces) {
				pathMatchers.add(FileSystems.getDefault().getPathMatcher("glob:" + root.resolve(glob)));
			}
		}
	}

	@Override
	public void accept(URI uri) {
		if (sources != null && N4JSGlobals.ALL_N4_FILE_EXTENSIONS.contains(URIUtils.fileExtension(uri))) {
			sources.add(uri);
		}
	}

	@Override
	public FileVisitResult acceptFile(URI uri) {
		accept(uri);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult acceptDirectory(URI uri) {
		for (PathMatcher pathMatcher : pathMatchers) {
			if (pathMatcher.matches(Path.of(uri.toFileString()))) {
				return FileVisitResult.SKIP_SUBTREE;
			}
		}
		return FileVisitResult.CONTINUE;
	}

	/** Returns the sources and resets the internal sources list. */
	public List<URI> getSources() {
		try {
			return sources;
		} finally {
			this.sources = new ArrayList<>();
		}
	}

	/**  */
	public List<PathMatcher> getPathMatchers() {
		return pathMatchers;
	}
}
