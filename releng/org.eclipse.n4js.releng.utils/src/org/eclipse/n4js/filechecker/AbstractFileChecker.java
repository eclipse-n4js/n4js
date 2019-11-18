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
package org.eclipse.n4js.filechecker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Joiner;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * FIXME move more logic to this abstract base class
 */
/* package */ abstract class AbstractFileChecker {

	/** Folders that are disregarded entirely (contained files won't even be counted as "ignored files"). */
	protected static final String[] DISREGARDED_FOLDERS = {
			".git",
			"bin"
	};

	/** Optional file in the root of a git repository that declares some files as third-party files. */
	protected static final String FILE_NAME__THIRD_PARTY = "third-party.txt";

	/**
	 * Names of repositories to check.
	 */
	protected abstract String[] getRepos();

	/**
	 * Returns names of the mandatory repositories to check, i.e. not finding these repositories will be reported as an
	 * error. Should be a subset of the names returned by {@link #getRepos()}.
	 */
	protected abstract String[] getReposMandatory();

	protected abstract boolean isIgnored(Path path, String pathStr);

	protected abstract void checkFile(Path path, String content, boolean isRegisteredAsThirdParty, Report report);

	protected abstract void checkFolder(Path path, int depth, Report report);

	// ################################################################################################################

	protected Path[] findRepoPaths(String[] args) {
		final String[] repoNames = getRepos();
		final String[] repoNamesMandatory = getReposMandatory();
		final Path rootPath = findRootPath(args, repoNames);
		for (String repoMandatory : repoNamesMandatory) {
			if (!rootPath.resolve(repoMandatory).toFile().isDirectory()) {
				System.out.println("ERROR: root folder doesn't contain mandatory sub folder \"" + repoMandatory + "\"");
				System.exit(1);
			}
		}
		final Path[] repoPaths = Arrays.asList(repoNames).stream().map((repoName) -> rootPath.resolve(repoName))
				.toArray((n) -> new Path[n]);
		return repoPaths;
	}

	/**
	 * Same as {@link #findRootPathFailSafe(String[], String[])}, but will show error messages and exit in case of
	 * error. Always returns a non-<code>null</code> path that points to an existing folder.
	 */
	protected static Path findRootPath(String[] args, String[] repoNames) {
		final Path rootPath = findRootPathFailSafe(args, repoNames);
		if (rootPath == null || !rootPath.toFile().exists() || !rootPath.toFile().isDirectory()) {
			System.out.println("ERROR: root path not found or does not point to a folder");
			System.out.println("Root path must either be given as first command line argument\n"
					+ "OR the current working directory must lie in an N4JS git repository.");
			System.exit(1);
		}
		return rootPath;
	}

	/**
	 * The root path is expected to point to the folder containing at least one of the git repositories listed in
	 * <code>repoNames</code>. It must be provided as the first command line argument OR it will be derived from the
	 * current working directory.
	 *
	 * Returns <code>null</code> in case of error.
	 */
	protected static Path findRootPathFailSafe(String[] args, String[] repoNames) {
		try {
			if (args.length > 0) {
				// take root path from 1st command line argument
				return new File(args[0]).getCanonicalFile().toPath();
			} else {
				// derive root path from current working directory
				File curr = new File(".").getCanonicalFile();
				while (curr != null && curr.isDirectory() && !containsSubDir(curr, repoNames)) {
					curr = curr.getParentFile();
				}
				return curr != null ? curr.toPath() : null;
			}
		} catch (IOException e) {
			return null;
		}
	}

	private static boolean containsSubDir(File dir, String[] subDirNames) {
		final String[] actualSubDirNames = dir.list();
		if (actualSubDirNames != null) {
			for (String subDirName : subDirNames) {
				if (org.eclipse.xtext.util.Arrays.contains(actualSubDirNames, subDirName)) {
					return true;
				}
			}
		}
		return false;
	}

	// ################################################################################################################

	protected boolean run(String[] args) {
		final Path[] repoPaths = findRepoPaths(args);

		System.out.println("=====================================================================================");

		final AtomicInteger count = new AtomicInteger(0);
		final AtomicInteger ignored = new AtomicInteger(0);
		final AtomicInteger checked = new AtomicInteger(0);
		final AtomicInteger checkedThirdParty = new AtomicInteger(0);
		final FullReport fullReport = new FullReport();

		try {
			walkFileTree(count, ignored, checked, checkedThirdParty, fullReport, repoPaths);

		} catch (IOException e) {
			System.out.println("ERROR while walking folder tree:");
			e.printStackTrace();
			System.out.println("ABORTING");
			return false;
		}

		System.out.println("-------------------------------------------------------------------------------------");
		if (fullReport.hasInvalidFiles()) {
			printInvalidFiles(fullReport);
		} else {
			System.out.println("No problems.");
		}

		if (fullReport.hasErroneousFiles()) {
			printErrors(fullReport.getErroneousFiles());
		}

		printResults(count, ignored, checked, checkedThirdParty, fullReport);

		CRHStatsPrinter.println(fullReport);

		boolean everythingOK = !fullReport.hasInvalidFiles() && !fullReport.hasErroneousFiles();
		return everythingOK;
	}

	private void walkFileTree(final AtomicInteger count, final AtomicInteger ignored, final AtomicInteger checked,
			final AtomicInteger checkedThirdParty, final FullReport fullReport, Path... repoPaths) throws IOException {

		for (Path repoPath : repoPaths) {
			System.out.println("Asserting file integrity in " + repoPath);
			final Set<Path> thirdPartyFiles = readListOfThirdPartyFiles(repoPath);
			System.out.print("Checking files ...");
			Files.walk(repoPath).forEachOrdered((path) -> {
				File file = path.toFile();
				String pathStr = getCanonicalPath(file);

				if (isBelowFolder(pathStr, DISREGARDED_FOLDERS)) {
					return; // completely ignore these folders (do not even count the files)
				}

				count.incrementAndGet();

				final Report report = new Report(path);
				fullReport.addReport(report);

				if (isIgnored(path, pathStr)) {

					ignored.incrementAndGet();
					report.setToIgnored();

				} else {

					checked.incrementAndGet();

					try {

						checkFile(checkedThirdParty, repoPath, thirdPartyFiles, report);

					} catch (Throwable th) {
						report.setThrowable(th);
						// do not abort entirely, continue with next file
					}
				}
			});
			System.out.println(" done.");
		}
	}

	private void checkFile(final AtomicInteger checkedThirdParty, Path repoPath, final Set<Path> thirdPartyFiles,
			Report report) throws IOException {

		final Path path = report.path;
		if (path.toFile().isDirectory()) {

			checkFolder(path, path.getNameCount() - repoPath.getNameCount(), report);
		} else {
			final String content = readFile(path);
			final boolean isThirdParty = thirdPartyFiles.contains(path);
			if (isThirdParty) {
				checkedThirdParty.incrementAndGet();
				report.setToThirdParty();
			}

			checkFile(path, content, isThirdParty, report);
		}
	}

	private void printInvalidFiles(FullReport fullReport) {
		final Multimap<String, Path> pathsPerError = LinkedHashMultimap.create();
		for (Report report : fullReport.getInvalidReports()) {
			for (String err : report.problems) {
				pathsPerError.put(err, report.path);
			}
		}

		final List<String> errors = new ArrayList<>(pathsPerError.keySet());
		Collections.sort(errors);
		for (String err : errors) {
			final Collection<Path> paths = pathsPerError.get(err);
			System.out.println("PROBLEM in " + paths.size() + " files: " + err);
			for (Path path : paths) {
				System.out.println("    " + path);
			}
		}
	}

	private void printErrors(final Collection<Report> erroneousReports) {
		System.out.flush();
		sleep(500);
		for (Report errReport : erroneousReports) {
			System.err.println("ERROR processing file: " + errReport.path);
			errReport.getError().printStackTrace();
		}
		System.err.flush();
		sleep(500);
	}

	private void printResults(final AtomicInteger count, final AtomicInteger ignored, final AtomicInteger checked,
			final AtomicInteger checkedThirdParty, final FullReport fullReport) {

		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("Checked " + checked + " files, including " + checkedThirdParty + " third-party files ("
				+ ignored + " ignored; " + count + " total).");
		System.out.println("Valid files: " + fullReport.getValidReports().size());
		System.out.println("Invalid files: " + fullReport.getInvalidReports().size());
		System.out.println("Erroneous files: " + fullReport.getErroneousFiles().size());
		System.out.println("=====================================================================================");
	}

	// ################################################################################################################

	/**
	 * <pre>
	 * #
	 * # List of files and folders with third-party copyright.
	 * #
	 * #
	 * # This file is processed automatically by FileChecker.java to ensure the below information is kept up-to-date.
	 * #
	 * # Format:
	 * # every non-empty line in this file either starts with '#' and is then a comment (to be ignored) or must
	 * # contain the relative path to a file with third-party copyright. If a path ends in "/**" it must point to
	 * # a folder and its contents are declared to be third-party files. All paths must be relative to the folder
	 * # containing this file.
	 * #
	 * </pre>
	 */
	private static Set<Path> readListOfThirdPartyFiles(Path rootPath) throws IOException {
		System.out.println("Reading list of third-party files from \"" + FILE_NAME__THIRD_PARTY + "\" ...");
		final Path thirdPartyList = rootPath.resolve(FILE_NAME__THIRD_PARTY);
		if (!thirdPartyList.toFile().exists()) {
			// note: providing a third-party.txt file is optional, so no error here:
			System.out.println("    no such file found, assuming 0 third-party files.");
			return Collections.emptySet();
		}
		final List<String> lines = Files.readAllLines(thirdPartyList, StandardCharsets.UTF_8);
		// trim all lines
		lines.replaceAll((l) -> l.trim());
		// remove empty lines and comments
		lines.removeIf((l) -> l.length() == 0 || l.startsWith("#"));
		// make sure all paths are relative
		if (lines.stream().anyMatch((l) -> l.startsWith("/") || l.startsWith("\\")))
			throw new IOException("paths in " + FILE_NAME__THIRD_PARTY + " must be relative, i.e. not start with '/'");
		// make sure all files/folders exist & are of correct type
		final List<Path> paths = lines.stream().map((l) -> rootPath.resolve(l)).collect(Collectors.toList());
		int files = 0;
		int folders = 0;
		// @formatter:off
		/*
		for (Path p : paths) {
			if (p.endsWith("**")) {
				// folder
				final Path parent = p.getParent();
				if (!parent.toFile().exists()) {
					throw new IOException("folder does not exist: " + parent);
				}
				if (!parent.toFile().isDirectory()) {
					throw new IOException("not a folder: " + parent);
				}
				folders++;
			} else {
				// file
				if (!p.toFile().exists()) {
					throw new IOException("file does not exist: " + p);
				}
				if (!p.toFile().isFile()) {
					throw new IOException("not a file: " + p);
				}
				files++;
			}
		}
		//*/
		// @formatter:on
		// replace folders by their contained files
		for (int i = 0; i < paths.size(); i++) {
			final Path p = paths.get(i);
			if (p.endsWith("**")) {
				final Path parent = p.getParent();
				final List<Path> allFiles = getAllContainedFiles(parent);
				if (allFiles.isEmpty()) {
					// throw new IOException("folder is empty: " + parent);
				}
				paths.remove(i);
				paths.addAll(i, allFiles);
				i += allFiles.size() - 1;
			}
		}
		// check for duplicates
		final Set<Path> duplicates = collectDuplicates(paths);
		if (!duplicates.isEmpty()) {
			throw new IOException("the following files are declared more than once in " + FILE_NAME__THIRD_PARTY
					+ " (maybe because they are contained in a folder declared with \"/**\"):\n    "
					+ Joiner.on("\n    ").join(duplicates));
		}
		// report to user
		System.out.println("    " + files + " files and " + folders + " folders (for a total of " + paths.size()
				+ " files) declared as third-party artifacts.");
		return new HashSet<>(paths); // order does not matter, so don't need LinkedHashSet
	}

	// ################################################################################################################

	protected static int containsTrailingWhiteSpace(String str) {
		int lineNumber = 0;
		int idx = 0;
		while (idx < str.length()) {
			idx = str.indexOf('\n', idx);
			if (idx < 0) {
				break;
			}
			lineNumber++;
			char ch = idx > 0 ? str.charAt(idx - 1) : 'X';
			if (ch == '\r') {
				ch = idx > 1 ? str.charAt(idx - 2) : 'X';
			}
			if (ch != '\n' && Character.isWhitespace(ch)) {
				return lineNumber;
			}
			idx++;
		}
		return -1;
	}

	protected static String getCanonicalPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	protected static boolean hasExtension(Path path, String... extensions) {
		final Path namePath = path.getFileName();
		if (namePath == null) {
			return false;
		}
		final String name = namePath.toString();
		for (String ext : extensions) {
			if (name.endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isFile(String pathStr, String... fileNames) {
		for (String fileName : fileNames) {
			if (pathStr.endsWith("/" + fileName)) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isBelowFolder(String pathStr, String... folderNames) {
		for (String folderName : folderNames) {
			if (pathStr.contains("/" + folderName + "/")) {
				return true;
			}
		}
		return false;
	}

	protected static boolean inExtendedRepo(Path path) {
		return isBelowFolder(path.toString(), "n4js-extended");
	}

	protected boolean containsFileWithName(Path path, String fileName) {
		final File file = path.toFile();
		final File[] files = file.listFiles();
		return files != null && Stream.of(files).anyMatch(f -> fileName.equals(f.getName()));
	}

	protected boolean containsPattern(String str, Pattern pattern) {
		return pattern.matcher(str).find();
	}

	protected static String fixFileEnding(String content) {
		if (content.length() > 0) {
			int endIndex = content.length();
			while (endIndex > 0 && content.charAt(endIndex - 1) == '\n') {
				--endIndex;
			}
			content = content.substring(0, endIndex) + '\n';
		}
		return content;
	}

	protected static String trimTrailingWhiteSpace(String content) {
		return content.replaceAll("[ \\t\\x0B\\f\\r]+\\n", "\n");
	}

	protected static List<Path> getAllContainedFiles(Path path) throws IOException {
		if (!path.toFile().exists())
			return Collections.emptyList();
		return Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
	}

	protected static String readFile(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	protected static void writeFile(Path path, String content) {
		try {
			Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected static <T> Set<T> collectDuplicates(Collection<? extends T> coll) {
		final Set<T> duplicates = new LinkedHashSet<>();
		final Set<T> seen = new HashSet<>();
		for (T p : coll) {
			if (!seen.add(p)) {
				duplicates.add(p);
			}
		}
		return duplicates;
	}

	protected static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// ignore
		}
	}
}
