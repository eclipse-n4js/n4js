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
		delete(resourceToDelete, false);
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
		if (resourceToDelete.toFile().exists()) {
			Files.walkFileTree(resourceToDelete, new FileDeleter(logToStdErr));
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
				Files.walkFileTree(resourceToDelete.toPath(), new FileDeleter(false));
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
				Files.walkFileTree(resourceToDelete, new FileDeleter(false));
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

	private final boolean logToStdErr;

	/**
	 * Creates a new file deleter instance.
	 *
	 * @param logToStdErr
	 *            {@code true} if any error messages should be logged to the standard out instead of the logger
	 *            instance. This could be useful when using file deleted in the headless tool.
	 */
	public FileDeleter(boolean logToStdErr) {
		this.logToStdErr = logToStdErr;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Files.delete(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		logError(file, exc);
		return CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		Files.delete(dir);
		return CONTINUE;
	}

	private void logError(Path path, IOException e) {
		if (logToStdErr) {
			System.err.println("Unexpected file visiting failure" + path);
			e.printStackTrace();
		} else {
			LOGGER.error("Unexpected file visiting failure" + path, e);
		}
	}

}
