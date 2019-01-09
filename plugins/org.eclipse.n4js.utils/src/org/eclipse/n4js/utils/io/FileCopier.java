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
package org.eclipse.n4js.utils.io;

import static com.google.common.base.Predicates.alwaysTrue;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.log4j.Logger;

import com.google.common.base.Predicate;

/**
 * Copies a single file resource or recursively a whole folder with its content to a different location. <br>
 * Call {@link FileCopier#copy(Path, Path)}.
 */
public class FileCopier implements FileVisitor<Path> {

	private static final Logger LOGGER = Logger.getLogger(FileCopier.class);

	private final Path source;
	private final Path target;
	private final Predicate<Path> shouldCopyFile;
	private final boolean logToStdErr;

	private FileCopier(Path source, Path target, Predicate<Path> shouldCopyFile, boolean logToStdErr) {
		this.source = source;
		this.target = target;
		this.shouldCopyFile = shouldCopyFile;
		this.logToStdErr = logToStdErr;
	}

	/**
	 * Copies a single file or a recursively a whole folder with its content to the given destination.
	 *
	 * @param from
	 *            path to the source to copy.
	 * @param to
	 *            path to the target to copy.
	 */
	public static void copy(Path from, Path to) throws IOException {
		copy(from, to, false);
	}

	/**
	 * Copies a single file or a recursively a whole folder with its content to the given destination. Just like
	 * {@link #copy(Path, Path)} but it logs any errors message to the standard error output instead of the logger
	 * instance. This factory method could come handy when using from the headless tool.
	 *
	 * @param from
	 *            path to the source to copy.
	 * @param to
	 *            path to the target to copy.
	 */
	public static void copy(Path from, Path to, boolean logToStdErr) throws IOException {
		Files.walkFileTree(from, new FileCopier(from, to, alwaysTrue(), logToStdErr));
	}

	/**
	 * Copies a single file or a recursively a whole folder with its content to the given destination. Files (not
	 * directories!) are copied only if they pass predicate check.
	 *
	 * @param from
	 *            path to the source to copy.
	 * @param to
	 *            path to the target to copy.
	 * @param shouldCopyFile
	 *            predicate checking if given file should be copied
	 */
	public static void copy(Path from, Path to, Predicate<Path> shouldCopyFile) throws IOException {
		Files.walkFileTree(from, new FileCopier(from, to, shouldCopyFile, false));
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		CopyOption[] options = new CopyOption[0];

		Path newdir = target.resolve(source.relativize(dir));
		try {
			Files.copy(dir, newdir, options);
		} catch (FileAlreadyExistsException e) {
			// ignore
		} catch (IOException e) {
			logError(newdir, e);
			return SKIP_SUBTREE;
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (this.shouldCopyFile.apply(file)) {
			Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		if (exc instanceof FileSystemLoopException) {
			LOGGER.error("File system loop/cycle detected: " + file, exc);
		} else {
			LOGGER.error("Error while copying resource: " + file, exc);
		}
		return CONTINUE;
	}

	private void logError(Path newdir, IOException e) {
		if (logToStdErr) {
			System.err.println("Error while creating folder: " + newdir);
			e.printStackTrace();
		} else {
			LOGGER.error("Error while creating folder: " + newdir, e);
		}
	}
}
