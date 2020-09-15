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
package org.eclipse.n4js.internal.lsp;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfig.SourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.xtext.workspace.SourceFolderScanner;
import org.eclipse.n4js.xtext.workspace.SourceFolderSnapshot;
import org.eclipse.xtext.util.IFileSystemScanner;

/**
 * N4JS-specific implementation of {@link SourceFolderScanner}.
 */
public class N4JSSourceFolderScanner extends SourceFolderScanner {

	@Override
	public List<URI> findAllSourceFiles(SourceFolderSnapshot sourceFolder, IFileSystemScanner scanner) {
		if (sourceFolder instanceof SourceFolderSnapshotForPackageJson) {
			return Collections.singletonList(((SourceFolderSnapshotForPackageJson) sourceFolder).getPackageJsonURI());
		}
		return findAllSourceFilesInFolder(sourceFolder.getPath(), scanner);
	}

	/**
	 * Assuming 'baseFolder' is the path of an N4JS source folder, this method returns all source files in this source
	 * folder. Excludes all files below sub-folders called {@link N4JSGlobals#NODE_MODULES "node_modules"}.
	 *
	 * @return a list of URIs, each representing a source file.
	 */
	public static List<URI> findAllSourceFilesInFolder(URI baseFolder, IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>();
		scanner.scan(baseFolder, new FileVisitingAcceptor() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
					return FileVisitResult.SKIP_SUBTREE;
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public void accept(URI uri) {
				uris.add(uri);
			}

		});
		return uris;
	}
}
