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
package org.eclipse.n4js.projectModel;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.workspace.ISourceFolder;

/**
 * Should be moved to org.eclipse.xtext.ide.server!
 */
@SuppressWarnings("restriction")
public interface ISourceFolderEx extends ISourceFolder {

	default boolean contains(URI uri) {
		URI path = getPath();
		path = path.hasTrailingPathSeparator() ? path : path.appendSegment("");
		uri = uri.hasTrailingPathSeparator() ? uri : uri.appendSegment("");
		return uri.toFileString().startsWith(uri.toFileString());
	}

	default List<URI> getAllResources() {
		List<URI> uris = new LinkedList<>();
		Path srcPath = Paths.get(getPath().toFileString());

		FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				URI uri = URI.createFileURI(file.toString());
				uri = addEmptyAuthority(uri);
				uris.add(uri);
				return FileVisitResult.CONTINUE;
			}
		};

		try {
			Files.walkFileTree(srcPath, fv);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return uris;
	}

	/** Adds empty authority to the given URI. Necessary for windows platform. */
	static public URI addEmptyAuthority(URI uri) {
		uri = URI.createHierarchicalURI(uri.scheme(), "", uri.device(), uri.segments(), uri.query(), uri.fragment());
		return uri;
	}
}
