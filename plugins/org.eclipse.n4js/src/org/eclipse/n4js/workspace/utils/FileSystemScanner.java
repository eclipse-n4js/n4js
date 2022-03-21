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
package org.eclipse.n4js.workspace.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.locations.FileURI;
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
		File rootFile = URIUtils.toFile(root);
		if (!rootFile.isDirectory()) {
			return;
		}
		try {
			Path rootPath = rootFile.toPath();
			// if (acceptor instanceof FileVisitingAcceptor) {
			Set<FileVisitOption> options = Collections.singleton(FileVisitOption.FOLLOW_LINKS);
			Files.walkFileTree(rootPath, options, Integer.MAX_VALUE, new FileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						return FileVisitResult.SKIP_SUBTREE;
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					acceptor.accept(new FileURI(file.toFile()).toURI());
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					throw exc;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					if (exc != null)
						throw exc;
					return FileVisitResult.CONTINUE;
				}
			});
			// } else {
			// Iterator<Path> pathIter = Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS).iterator();
			// while (pathIter.hasNext()) {
			// Path p = pathIter.next();
			// File file = p.toFile();
			// if (!file.isDirectory()) {
			// acceptor.accept(new FileURI(p.toFile()).toURI());
			// }
			// }
			// }
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}
}
