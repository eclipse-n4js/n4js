/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc2spec.adoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import com.google.common.base.StandardSystemProperty;

/**
 * Provides static methods and values regarding file system properties.
 */
public class FileSystem {
	/** New line string */
	public static final String NL = StandardSystemProperty.LINE_SEPARATOR.value();
	/** The location where adoc module files are placed (using their hierarchy in the repository). */
	public static final String DIR_MODULES = "modules";
	/** The location where adoc files are placed which list all modules of each package. */
	public static final String DIR_PACKAGES = "packages";
	/** Generated adoc files */
	public static final String DIR_ADOC_GEN = "api-gen";
	/** File separator for convenience */
	public static final String SEP = File.separator;
	/** File name of the index file */
	public static final String INDEX_FILE_NAME = "index.idx";

	/**
	 * Copies the template file structure to the chosen export destination directory. In case a file already exists, it
	 * wont be overwritten. Files named 'dummy' will be ignored. They exists to ensure that otherwise empty directories
	 * are copied to GitHub.
	 *
	 * @param root
	 *            chosen destination root of the exported files
	 * @throws IOException
	 *             error during copying
	 */
	static public void ensureFileStructure(File root) throws IOException {
		if (!root.exists()) {
			root.mkdir();
		}

		if (!DIR_ADOC_GEN.equals(root.getName())) {
			root.toPath().resolve(DIR_ADOC_GEN).toFile().mkdir();
		}

	}

	static class CopyFileVisitor extends SimpleFileVisitor<Path> {
		private final Path targetPath;
		private Path sourcePath = null;

		public CopyFileVisitor(Path targetPath) {
			this.targetPath = targetPath;
		}

		@Override
		public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
			if (sourcePath == null) {
				sourcePath = dir;
			} else {
				Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
			if (file.getFileName().endsWith("dummy"))
				return FileVisitResult.CONTINUE;
			Path tgtPath = targetPath.resolve(sourcePath.relativize(file));
			if (tgtPath.toFile().exists())
				return FileVisitResult.CONTINUE;

			Files.copy(file, tgtPath);
			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * @param dirPath
	 *            the directory to start the search in
	 * @return the number of all adoc files in a directory and all its subdirectories
	 */
	public static int getAdocFileCount(String dirPath) {
		return getAdocFileNames(dirPath).size();
	}

	/**
	 * @param dirPath
	 *            the directory to start the search in
	 * @return a list of all adoc files in a directory and all its subdirectories
	 */
	public static ArrayList<String> getAdocFileNames(String dirPath) {
		ArrayList<String> fileNames = new ArrayList<>();
		getAdocFileNames(Paths.get(dirPath), dirPath, fileNames);
		return fileNames;
	}

	private static void getAdocFileNames(Path rootDirPath, String dirPath, ArrayList<String> fileNames) {
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.getName().endsWith(".adoc")) {
					Path filePath = file.toPath();
					String relFileName = rootDirPath.relativize(filePath).toString();
					fileNames.add(relFileName);
				}

				if (file.isDirectory()) {
					getAdocFileNames(rootDirPath, file.getAbsolutePath(), fileNames);
				}
			}
		}
	}
}
