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
package org.eclipse.n4js.utils.io

import com.google.common.base.Preconditions
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.Collection
import org.apache.log4j.Logger

import static java.nio.file.FileVisitResult.CONTINUE

import static extension com.google.common.base.Preconditions.checkNotNull

/**
 * File visitor implementation for collecting files (with their path) that matches a specified pattern.
 */
class FileMatcher extends SimpleFileVisitor<Path> {

	static val LOGGER = Logger.getLogger(FileMatcher);
	static val GLOB_SYNTAX_PREFIX = 'glob:';

	val PathMatcher matcher
	val Collection<Path> matchedPaths
	val FileMatchingMode mode

	/**
	 * Recursively scans for files in the given root location. Returns with a collection of file paths
	 * that fulfills the given file name filter pattern.
	 */
	static def scanFiles(Path root, String pattern) {
		scan(root, pattern, FileMatchingMode.FILES);
	}

	/**
	 * Recursively scans for directories in the given root location. Returns with a collection of directory paths
	 * that fulfills the given directory name filter pattern.
	 */
	static def scanDirectories(Path root, String pattern) {
		scan(root, pattern, FileMatchingMode.DIRECTORIES);
	}

	private static def scan(Path root, String pattern, FileMatchingMode mode) {
		try {
			Preconditions.checkArgument(root.checkNotNull.toFile.exists, '''File does not exist: «root».''');
			val matcher = new FileMatcher(pattern.checkNotNull, mode);
			Files.walkFileTree(root, matcher);
			return matcher.matchedPaths.map[root.relativize(it)].toSet;
		} catch (IOException e) {
			throw new RuntimeException('''Error while recursively scanning for files in «root» with pattern: «pattern».''', e);
		}
	}

	private new(String pattern, FileMatchingMode mode) {
		val escapedPattern = pattern.checkNotNull.replaceAll('\\\\', '/');
		this.mode = mode;
		matcher = FileSystems.^default.getPathMatcher('''«FileMatcher.GLOB_SYNTAX_PREFIX»**«escapedPattern»''');
		matchedPaths = newArrayList;
	}

	override preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		dir.match;
		CONTINUE;
	}

	override visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		file.match;
		CONTINUE;
	}

	override visitFileFailed(Path file, IOException e) throws IOException {
		LOGGER.error('''Error while visiting file at: «file».''', e);
		CONTINUE;
	}

	def match(Path path) {
		if (null !== path && mode.apply(path) && matcher.matches(path)) {
			matchedPaths.add(path);
		}
	}
}
