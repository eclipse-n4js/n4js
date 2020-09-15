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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IFileSystemScanner;

/**
 * Service to obtain all source files in a {@link SourceFolderSnapshot source folder}.
 */
public class SourceFolderScanner {

	/**
	 * Accesses the file system to search for all source files contained in the given source folder.
	 */
	public List<URI> findAllSourceFiles(SourceFolderSnapshot sourceFolder, IFileSystemScanner scanner) {
		List<URI> uris = new ArrayList<>();
		scanner.scan(sourceFolder.getPath(), uris::add);
		return uris;
	}
}
