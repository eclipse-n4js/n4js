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
package org.eclipse.n4js.hlc;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants;
import org.junit.After;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 */
public abstract class AbstractN4jscTest {

	/**
	 * name of sub-directory of the compiled result. Used to count files having this string as directory name on it's
	 * {@link #assertFilesCompiledToES(int, String)}
	 */
	private static final String SUBGENERATOR_PATH = N4JSLanguageConstants.TRANSPILER_SUBFOLDER_FOR_TESTS;
	/** name of target folder */
	protected static final String TARGET = "target";
	/** name of workspace sub-folder (inside target folder) */
	protected static final String WSP = "wsp";
	/** name of package containing the test resources */
	protected static final String FIXTURE = "src/test/resources";
	/** name of default test data set */
	protected static final String TEST_DATA_SET__BASIC = "basic";
	/** name of test data set for launching testers from the command line */
	protected static final String TEST_DATA_SET__TESTERS = "testers";

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataSet) throws IOException {
		return setupWorkspace(testDataSet, Predicates.alwaysFalse());
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataRoot, String testDataSet) throws IOException {
		return setupWorkspace(testDataRoot, testDataSet, Predicates.alwaysFalse());
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests. Also includes all N4JS
	 * libraries from the {@code n4js} Git repository which name provides {@code true} value for the given predicate.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataSet,
			Predicate<String> n4jsLibrariesPredicate)
			throws IOException {
		return setupWorkspace(FIXTURE, testDataSet, n4jsLibrariesPredicate);
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests. Also includes all N4JS
	 * libraries from the {@code n4js} Git repository which name provides {@code true} value for the given predicate.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	protected static File setupWorkspace(String testDataRoot, String testDataSet,
			Predicate<String> n4jsLibrariesPredicate)
			throws IOException {
		File wsp = new File(TARGET, WSP);
		File fixture = new File(testDataRoot, testDataSet);
		// clean
		// System.out.println("Workspace : " + wsp.getAbsolutePath());
		// Files.deleteIfExists(wsp.toPath());
		if (wsp.exists()) {
			FileDeleter.delete(wsp.toPath(), true);
		}

		// copy fixtures to workspace
		FileCopier.copy(fixture.toPath(), wsp.toPath(), true);

		final File gitRoot = new File(new File("").getAbsolutePath()).getParentFile().getParentFile();
		final File n4jsLibraryRoot = new File(gitRoot, N4JSGlobals.SHIPPED_CODE_SOURCES_FOLDER_NAME);
		final File[] n4jsLibraries = n4jsLibraryRoot.listFiles();
		// copy N4JS libraries on demand
		if (!Arrays2.isEmpty(n4jsLibraries)) {
			for (final File n4jsLibrary : n4jsLibraries) {
				if (n4jsLibrariesPredicate.apply(n4jsLibrary.getName())) {
					System.out.println("Including N4JS library in workspace: '" + n4jsLibrary.getName() + "'.");
					final File libFolder = new File(wsp, n4jsLibrary.getName());
					libFolder.mkdir();
					checkState(libFolder.isDirectory(),
							"Error while copying N4JS library '" + n4jsLibrary.getName() + "' to workspace.");
					FileCopier.copy(n4jsLibrary.toPath(), libFolder.toPath(), true);
				}
			}
		}

		return wsp;
	}

	/**
	 * Flush outputs after tests.
	 */
	@After
	public void flush() {
		System.out.flush();
		System.err.flush();
		System.out.println("======= ======= ======= ======= ======= ======= ======= ======= ======= ======= ");
		System.out.flush();

	}

	/**
	 * @param expectedCompiledModuleCount
	 *            expected number of compiled '.js' files in the {@value #SUBGENERATOR_PATH} folder.
	 * @param workspaceRootPath
	 *            subtree to search in passed as argument to {@link File}
	 */
	protected void assertFilesCompiledToES(int expectedCompiledModuleCount, String workspaceRootPath) {
		assertEquals("Files expected", expectedCompiledModuleCount, countFilesCompiledToES(workspaceRootPath));
	}

	/**
	 * Counts the number of files ending in .js in the {@value #SUBGENERATOR_PATH} folder.
	 *
	 * @param workspaceRootPath
	 *            the directory to recursively search
	 * @return the number of files ending in .js
	 */
	protected int countFilesCompiledToES(String workspaceRootPath) {
		final File workspaceRoot = new File(workspaceRootPath);

		final File gitRoot = new File(new File("").getAbsolutePath()).getParentFile().getParentFile();
		final File n4jsLibrariesRoot = new File(gitRoot, N4JSGlobals.SHIPPED_CODE_SOURCES_FOLDER_NAME);
		final Collection<String> n4jsLibraryNames = newHashSet(n4jsLibrariesRoot.list());

		final AtomicInteger counter = new AtomicInteger();
		try {
			Files.walkFileTree(workspaceRoot.toPath(), new FileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

					// skip src
					if ("src".equals(dir.getFileName())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (n4jsLibraryNames.contains(dir.toFile().getName())) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.getFileName().toString().endsWith(".js")) {
						for (int j = 0; j < file.getNameCount() - 1; j++) {
							if (SUBGENERATOR_PATH.equals(file.getName(j).toString())) {
								counter.incrementAndGet();
								return FileVisitResult.CONTINUE;
							}
						}
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					if (SUBGENERATOR_PATH.equals(dir.getFileName()))
						return FileVisitResult.SKIP_SIBLINGS;
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue("Could not parse file tree of path '" + workspaceRootPath + "' exc = " + e.getMessage(), false);
		}

		return counter.get();
	}

	/**
	 * Determine class.method of the direct caller by inspecting the current thread's stack trace.
	 *
	 * @return Class + Method name of the caller.
	 */
	protected String logMethodname() {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "";
		return "INSIDE of " + name;
	}

}
