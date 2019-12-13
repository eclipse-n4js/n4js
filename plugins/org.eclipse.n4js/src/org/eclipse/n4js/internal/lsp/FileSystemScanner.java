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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.IFileSystemScanner;
import org.eclipse.xtext.util.RuntimeIOException;

import com.google.inject.Singleton;

/**
 * A file system scanner that is aware of {@link FileVisitingAcceptor} to cut the traversal short.
 *
 * Compared to the default, it relies internally on {@link Files#walkFileTree(Path, java.nio.file.FileVisitor)} rather
 * than manual traversal based on the {@link File file API}.
 */
@Singleton
public class FileSystemScanner implements IFileSystemScanner {

	@Override
	public void scan(URI root, IAcceptor<URI> acceptor) {
		File rootFile = new File(root.toFileString());
		if (rootFile.isDirectory()) {
			try {
				Path rootPath = rootFile.toPath();
				if (acceptor instanceof FileVisitingAcceptor) {
					Files.walkFileTree(rootPath, (FileVisitingAcceptor) acceptor);
				} else {
					Files.walk(rootPath).forEach(p -> {
						File file = p.toFile();
						if (!file.isDirectory()) {
							acceptor.accept(new FileURI(p.toFile()).toURI());
						}
					});
				}
			} catch (IOException e) {
				throw new RuntimeIOException(e);
			}
		}
	}
}
