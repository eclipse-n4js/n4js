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
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;

/**
 * Counts all js files of all (sub-)directories starting at a given root folder.
 */
public class GeneratedJSFilesCounter {

	/**
	 * Counts the number of files ending in .js in the provided folder. Assumes original sources are in
	 * {@link N4JSLanguageConstants#DEFAULT_PROJECT_SRC} and output in
	 * {@link N4JSLanguageConstants#DEFAULT_PROJECT_OUTPUT}.
	 *
	 * @param workspaceRoot
	 *            the directory to recursively search
	 * @return the number of files ending in .js
	 */
	static public NavigableMap<Path, Set<File>> getTranspiledFiles(final Path workspaceRoot) {
		final AtomicReference<NavigableMap<Path, Set<File>>> genFilesRef = new AtomicReference<>();

		final File n4jsLibrariesRoot = findN4jsLibRoot();
		if (n4jsLibrariesRoot == null) {
			System.out.println("Folder not found: " + N4JSGlobals.N4JS_LIBS_SOURCES_PATH);
			return genFilesRef.get();
		}
		final Collection<String> n4jsLibraryNames = new HashSet<>(Arrays.asList(n4jsLibrariesRoot.list()));

		genFilesRef.set(new TreeMap<>());

		try {
			Files.walkFileTree(workspaceRoot, new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

					// skip src
					if (N4JSLanguageConstants.DEFAULT_PROJECT_SRC.equals(dir.getFileName().toString())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (n4jsLibraryNames.contains(dir.toFile().getName())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					String fName = file.getFileName().toString();
					if ((fName.endsWith(".js") || fName.endsWith(".jsx")) && pathContainsSrcGen(file)) {
						NavigableMap<Path, Set<File>> fileMap = genFilesRef.get();
						Path directory = file.getParent();
						if (!fileMap.containsKey(directory)) {
							fileMap.put(directory, new HashSet<>());
						}

						Path relativeFile = workspaceRoot.relativize(file);
						fileMap.get(directory).add(relativeFile.toFile());
						return FileVisitResult.CONTINUE;
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					if (N4JSLanguageConstants.DEFAULT_PROJECT_OUTPUT.equals(dir.getFileName().toString()))
						return FileVisitResult.SKIP_SIBLINGS;
					return FileVisitResult.CONTINUE;
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return genFilesRef.get();
	}

	static private boolean pathContainsSrcGen(Path path) {
		if (path == null || path.getNameCount() == 0) {
			return false;
		}
		Path pathName = path.getName(path.getNameCount() - 1);
		if ("src-gen".equals(pathName.toString())) {
			return true;
		}
		return pathContainsSrcGen(path.getParent());
	}

	/**
	 * Searching for the folder {@link N4JSGlobals#N4JS_LIBS_SOURCES_PATH} is necessary because the current path depends
	 * on how this method gets called, e.g. from local testing in the Eclipse IDE, or from mvn.
	 */
	static private File findN4jsLibRoot() {
		File base = new File(new File("").getAbsolutePath());
		File n4jsLibrariesRoot = new File(base, N4JSGlobals.N4JS_LIBS_SOURCES_PATH);

		while (base.getParentFile() != null && !n4jsLibrariesRoot.isDirectory()) {
			base = base.getParentFile();
			n4jsLibrariesRoot = new File(base, N4JSGlobals.N4JS_LIBS_SOURCES_PATH);
		}
		return n4jsLibrariesRoot;
	}
}
