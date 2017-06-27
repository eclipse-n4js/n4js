/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.utils.io.IDirectoryDiffAcceptor;
import org.eclipse.n4js.utils.io.IDirectoryDiffAcceptor.CollectingDirectoryDiffAcceptor;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests method {@link FileUtils#compareDirectories(File, File, IDirectoryDiffAcceptor)}.
 */
public class FileUtilsCompareDirectoriesTest {

	/** Tests comparison of two directories with differences. */
	@Test
	public void testCompareDirectoriesWithDifferences() throws IOException {
		final CollectingDirectoryDiffAcceptor acceptor = performComparison("expectedRoot", "actualRoot");

		Assert.assertFalse(acceptor.isEmpty());

		assertPaths(acceptor.unexpected,
				"unexpectedFolder",
				"unexpectedFolder/dummy",
				"folder/unexpectedFile.txt",
				"conflicts/expectedFileGotFolder/fileInConflictingFolder.txt");
		assertPaths(acceptor.missing,
				"missingFolder",
				"missingFolder/dummy",
				"folder/missingFile.txt",
				"conflicts/expectedFolderGotFile/fileInConflictingFolder.txt");
		assertPaths(acceptor.fileInsteadOfDir,
				"conflicts/expectedFolderGotFile");
		assertPaths(acceptor.dirInsteadOfFile,
				"conflicts/expectedFileGotFolder");
		assertPaths(acceptor.diffLength,
				"folder/differentFileLength.txt");
		assertPathsInPairs(acceptor.diffContent,
				"folder/differentFileContent.txt");

		final long posWhereFilesDiffer = acceptor.diffContent.get(0).getValue();
		Assert.assertEquals(14L, posWhereFilesDiffer);
	}

	/** Tests comparison of a directory with itself (to test comparison of directories without differences. */
	@Test
	public void testCompareDirectoryToItself() throws IOException {
		final CollectingDirectoryDiffAcceptor acceptor = performComparison("expectedRoot",
				// using "expectedRoot" also for 'actual' to test comparison of identical file trees (not a typo):
				"expectedRoot");

		Assert.assertTrue(acceptor.isEmpty());
	}

	private CollectingDirectoryDiffAcceptor performComparison(String expectedFolderName, String actualFolderName)
			throws IOException {
		final File root = new File("testdata/compareDirectories");
		final File expected = new File(root, expectedFolderName);
		final File actual = new File(root, actualFolderName);

		final CollectingDirectoryDiffAcceptor acceptor = new CollectingDirectoryDiffAcceptor();
		FileUtils.compareDirectories(expected, actual, acceptor);
		return acceptor;
	}

	private void assertPathsInPairs(List<Pair<Path, Long>> actualPaths, String... expectedPaths) {
		assertPaths(actualPaths.stream().map(p -> p.getKey()).collect(Collectors.toList()), expectedPaths);
	}

	private void assertPaths(List<Path> actualPaths, String... expectedPaths) {
		final Set<String> expected = new HashSet<>(Arrays.asList(expectedPaths));
		final Set<String> actual = actualPaths.stream().map(Path::toString).collect(Collectors.toSet());
		if (!actual.equals(expected)) {
			final String msg = "paths do not match:\n"
					+ "EXPECTED: " + sort(expected) + "\n"
					+ "ACTUAL  : " + sort(actual);
			System.out.println(msg);
			Assert.fail(msg);
		}
	}

	private <T extends Comparable<? super T>> List<T> sort(Collection<T> collection) {
		final List<T> l = new ArrayList<>(collection);
		Collections.sort(l);
		return l;
	}
}
