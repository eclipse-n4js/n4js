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
package org.eclipse.n4js.ide.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.projectModel.locations.FileURI;

/**
 * Given a workspace root directory, this class finds npm and yarn projects as follows:
 * <ol>
 * <li>Find projects:
 * <ol>
 * <li/>Find a package.json in the workspace root directory or above (called root package.json file).
 * <li/>Iff no root package.json could be found, find all package json in sub-directories (called project package.json
 * files). Skip directories called node_modules.
 * </ol>
 * </li>
 * <li>Sort projects by name (ascending)
 * <li>Add projects:
 * <ol>
 * <li/>Add a found project to the result set of all projects
 * <li/>Determine if a found project is a yarn or npm project. In case of a yarn project, add also all referenced
 * projects (referenced in the workspace property).
 * <li/>Do not overwrite already found projects.
 * </ol>
 * </li>
 * <li>Add dependencies:
 * <ol>
 * <li/>Find the node_modules folder of a project and add all direct dependencies to the set of dependencies.
 * <li/>Do not overwrite already found dependencies.
 * <li/>Projects inside of dependencies are never considered.
 * </ol>
 * </li>
 * <li>Merge projects and dependencies
 * <ol>
 * <li/>All projects are added to the resulting set.
 * <li/>Those dependencies are added to the resulting set that do not clash with already existing projects.
 * </ol>
 * </li>
 * <li/>Emits warnings for all projects that were not found in the given root directory or one of its sub-directories.
 * </ol>
 */
public class ProjectFinder {

	// private List<FileURI> collectAllProjectDirs(File workspaceRoot) {
	//
	// }

	List<FileURI> collectAllProjectDirs(File workspaceRoot) {
		final List<FileURI> result = new ArrayList<>();

		Path start = workspaceRoot.toPath();

		try {
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
				int nodeModuleFolderCounter = 0;

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						if (nodeModuleFolderCounter > 0) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						nodeModuleFolderCounter++;
					}

					return super.preVisitDirectory(dir, attrs);
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (dir.endsWith(N4JSGlobals.NODE_MODULES)) {
						nodeModuleFolderCounter--;
					}
					File pckJson = dir.resolve(N4JSGlobals.PACKAGE_JSON).toFile();
					if (pckJson.isFile()) {
						result.add(new FileURI(dir.toFile()));
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
