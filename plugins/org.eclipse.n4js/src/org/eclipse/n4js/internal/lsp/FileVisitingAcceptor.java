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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.xtext.util.IAcceptor;

/**
 * A specialized acceptor that can be passed into the {@link FileSystemScanner} to get control over the scanned contents
 * of the file tree.
 *
 * It allows skipping parts of the project, e.g. skipping traversal of node modules.
 */
public interface FileVisitingAcceptor extends FileVisitor<Path>, IAcceptor<URI> {
	@Override
	void accept(URI uri);

	@Override
	public default FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public default FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		accept(new FileURI(file.toFile()).toURI());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public default FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		throw exc;
	}

	@Override
	public default FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		if (exc != null)
			throw exc;
		return FileVisitResult.CONTINUE;
	}
}