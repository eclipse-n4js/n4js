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

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

/**
 * For deleting a file resource or recursively a folder and it content.
 */
public class FileDeleter implements FileVisitor<Path> {

	private static final Logger LOGGER = Logger.getLogger(FileDeleter.class);

	/**
	 * Deletes a single file or recursively a folder with its content.
	 *
	 * @param resourceToDelete
	 *            the file resource or to the folder to delete.
	 * @throws IOException
	 *             in unrecognized cases.
	 * @see #delete(Path)
	 */
	public static void delete(File resourceToDelete) throws IOException {
		if (resourceToDelete.exists()) {
			delete(resourceToDelete.toPath());
		}
	}

	/**
	 * Deletes a single file or recursively a folder with its content.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @throws IOException
	 *             in unrecognized cases.
	 */
	public static void delete(Path resourceToDelete) throws IOException {
		delete(resourceToDelete, null, false);
	}

	/**
	 * Sugar for {@link #delete(Path)}. The error output can be configured with the boolean argument.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @param logToStdErr
	 *            {@code true} if any error messages should be logged to the standard error output instead of the
	 *            logger's output.
	 * @throws IOException
	 *             in unrecognized cases.
	 */
	public static void delete(Path resourceToDelete, boolean logToStdErr) throws IOException {
		delete(resourceToDelete, null, logToStdErr);
	}

	/**
	 * Deletes a single file or recursively a folder with its content.
	 *
	 * @param root
	 *            path to the file or folder to delete.
	 * @param predicate
	 *            if non-<code>null</code>, only files/folders for which this predicate returns <code>true</code> will
	 *            be deleted (and all contents of such folders). If <code>null</code>, all files/folders will be
	 *            deleted.
	 * @throws IOException
	 *             in unrecognized cases.
	 */
	public static void deleteSome(Path root, Predicate<Path> predicate) throws IOException {
		delete(root, predicate, false);
	}

	private static void delete(Path resourceToDelete, Predicate<Path> predicate, boolean logToStdErr)
			throws IOException {
		if (resourceToDelete.toFile().exists()) {
			Files.walkFileTree(resourceToDelete, new FileDeleter(predicate, logToStdErr));
		}
	}

	/**
	 * Sugar for {@link #delete(File)}, allows to pass custom error handler. If exception is caught handler will be
	 * invoked, and exception will not be propagated to the caller.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @param errorHandler
	 *            error handler invoked in case of {@link IOException} is caught
	 */
	public static void delete(File resourceToDelete, Consumer<? super IOException> errorHandler) {
		if (resourceToDelete.exists()) {
			try {
				Files.walkFileTree(resourceToDelete.toPath(), new FileDeleter(null, false));
			} catch (IOException ioe) {
				errorHandler.accept(ioe);
			}
		}
	}

	/**
	 * Sugar for {@link #delete(Path)}, allows to pass custom error handler. If exception is caught handler will be
	 * invoked, and exception will not be propagated to the caller.
	 *
	 * @param resourceToDelete
	 *            path to the file resource or to the folder to delete.
	 * @param errorHandler
	 *            error handler invoked in case of {@link IOException} is caught
	 */
	public static void delete(Path resourceToDelete, Consumer<IOException> errorHandler) {
		if (resourceToDelete.toFile().exists()) {
			try {
				Files.walkFileTree(resourceToDelete, new FileDeleter(null, false));
			} catch (IOException ioe) {
				errorHandler.accept(ioe);
			}
		}
	}

	/**
	 * Requests that the resource denoted by the given path be deleted upon (normal) termination of the Java virtual
	 * machine. If the given path denotes a non-empty directory, its entire contents will be deleted as well.
	 */
	public static void deleteOnExit(Path resourceToDelete) throws IOException {
		final File resourceToDeleteAsFile = resourceToDelete.toFile();
		if (resourceToDeleteAsFile.isDirectory()) {
			try (Stream<Path> walker = Files.walk(resourceToDelete)) {
				walker.forEachOrdered(path -> path.toFile().deleteOnExit());
			}
		} else {
			resourceToDeleteAsFile.deleteOnExit();
		}
	}

	private final Predicate<Path> predicate;
	private final boolean logToStdErr;

	private Path containingDeletedDir = null;

	/**
	 * Creates a new file deleter instance.
	 *
	 * @param predicate
	 *            if non-<code>null</code>, only files/folders for which this predicate returns <code>true</code> will
	 *            be deleted (and all contents of such folders). If <code>null</code>, all files/folders will be
	 *            deleted.
	 * @param logToStdErr
	 *            {@code true} if any error messages should be logged to the standard out instead of the logger
	 *            instance. This could be useful when using file deleted in the headless tool.
	 */
	public FileDeleter(Predicate<Path> predicate, boolean logToStdErr) {
		this.predicate = predicate;
		this.logToStdErr = logToStdErr;
	}

	private boolean isToBeDeleted(Path dirOrFile) {
		return predicate == null || predicate.test(dirOrFile);
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		if (containingDeletedDir == null && isToBeDeleted(dir)) {
			containingDeletedDir = dir;
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (containingDeletedDir != null || isToBeDeleted(file)) {
			Files.delete(file);
		}
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		logError(file, exc);
		return CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (containingDeletedDir != null) { // no need to check "isToBeDeleted(dir)" again
			Files.delete(dir);
			if (containingDeletedDir.equals(dir)) {
				containingDeletedDir = null;
			}
		}
		return CONTINUE;
	}

	private void logError(Path path, IOException e) {
		if (logToStdErr) {
			System.err.println("Unexpected file visiting failure: " + path);
			e.printStackTrace();
		} else {
			LOGGER.error("Unexpected file visiting failure: " + path, e);
		}
	}

}
