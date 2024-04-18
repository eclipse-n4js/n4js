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
 * A file system scanner that is aware of {@link IFileSystemScannerAcceptor} to cut the traversal short.
 *
 * Compared to the default, it relies internally on {@link Files#walkFileTree(Path, java.nio.file.FileVisitor)} rather
 * than manual traversal based on the {@link File file API}.
 */
@Singleton
public class FileSystemScanner implements IFileSystemScanner {

	/** An {@link IAcceptor} that supports skipping subtrees when walking the file tree. */
	public interface IFileSystemScannerAcceptor<T> extends IAcceptor<T> {
		/** Callback on every visited directory */
		default FileVisitResult acceptDirectory(T t) {
			accept(t);
			return FileVisitResult.CONTINUE;
		}

		/** Callback on every visited file */
		default FileVisitResult acceptFile(T t) {
			accept(t);
			return FileVisitResult.CONTINUE;
		}
	}

	@Override
	public void scan(URI root, IAcceptor<URI> acceptor) {
		File rootFile = URIUtils.toFile(root);
		if (!rootFile.isDirectory()) {
			return;
		}
		try {
			Path rootPath = rootFile.toPath();
			Set<FileVisitOption> options = Collections.singleton(FileVisitOption.FOLLOW_LINKS);
			Files.walkFileTree(rootPath, options, Integer.MAX_VALUE, new N4JSFileVisitor(acceptor));
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	static class N4JSFileVisitor implements FileVisitor<Path> {
		final IFileSystemScannerAcceptor<URI> fssAcceptor;
		final IAcceptor<URI> acceptor;

		N4JSFileVisitor(IAcceptor<URI> acceptor) {
			this.acceptor = acceptor;
			this.fssAcceptor = (acceptor instanceof IFileSystemScannerAcceptor)
					? (IFileSystemScannerAcceptor<URI>) acceptor
					: null;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
				return FileVisitResult.SKIP_SUBTREE;
			}
			if (fssAcceptor != null) {
				return fssAcceptor.acceptDirectory(new FileURI(dir.toFile()).toURI());
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			URI uri = new FileURI(file.toFile()).toURI();
			if (fssAcceptor != null) {
				return fssAcceptor.acceptFile(uri);
			} else {
				acceptor.accept(uri);
				return FileVisitResult.CONTINUE;
			}
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			throw exc;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			if (exc != null) {
				throw exc;
			}
			return FileVisitResult.CONTINUE;
		}
	}
}
