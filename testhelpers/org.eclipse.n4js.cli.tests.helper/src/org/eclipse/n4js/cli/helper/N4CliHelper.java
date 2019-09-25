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
package org.eclipse.n4js.test.helper.hlc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.hlc.base.N4jscBase;
import org.eclipse.n4js.json.JSONStandaloneSetup;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.utils.io.FileCopier;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.testing.GlobalRegistries.GlobalStateMemento;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * Central helper in running tests with the command line tools (e.g. {@code n4jsc.jar} or {@link N4jscBase}}.
 */
public class N4CliHelper {

	/**
	 * name of subfolder containing the actual projects iff a yarn workspace was created, see
	 * {@link #setupWorkspace(Path, Path, Predicate, boolean)}
	 */
	public static final String PACKAGES = "packages";

	/**
	 * A black list of n4js-libs that are never copied into a headless compiler test workspace.
	 */
	private static final Set<N4JSProjectName> N4JS_LIBS_BLACKLIST = new HashSet<>(
			Arrays.asList(
					new N4JSProjectName("n4js-cli"),
					new N4JSProjectName("org.eclipse.n4js.mangelhaft.test"),
					new N4JSProjectName("org.eclipse.n4js.mangelhaft.assert.test"),
					new N4JSProjectName("org.eclipse.n4js.mangelhaft.reporter.ide.test")));

	/**
	 * @param expectedString
	 *            exact String
	 * @param log
	 *            outputfile file to take process-output from.
	 */
	public static void assertExpectedOutput(String expectedString, File log) {
		assertExpectedOutput(expectedString, readLogfile(log));
	}

	/**
	 * @param expectedString
	 *            exact String
	 * @param out
	 *            received String from output
	 */
	public static void assertExpectedOutput(String expectedString, String out) {
		assertEquals("Output differs", expectedString, out);
	}

	/**
	 * Ensures that the file-content ends the expected string.
	 *
	 * @param expectedString
	 *            exact expected ending
	 * @param log
	 *            file to read from.
	 * @deprecated brittle since logging information messes up the output. better use
	 *             {@link #assertEndOfOutputExpectedToContain(String, String, File)}
	 */
	@Deprecated
	public static void assertExpectedOutputEndsWith(String expectedString, File log) {
		String x = readLogfile(log);
		assertExpectedOutputEndsWith(expectedString, x);
	}

	/**
	 * Scans output starting from endMarker
	 *
	 * @param endMarker
	 *            first occurrence is taken as snip-through, rest will be search for expectedString
	 * @param expectedString
	 *            the search-string
	 * @param log
	 *            the file to load.
	 */
	public static void assertEndOfOutputExpectedToContain(String endMarker, String expectedString, File log) {
		String r = readLogfile(log);
		int idx = r.indexOf(endMarker);
		if (idx == -1)
			fail("Log did not contain endmarker'" + endMarker + "' expected '" + expectedString + "' not found.");
		String x = r.substring(idx);
		assertExpectedOutputContains(expectedString, x);
	}

	/**
	 * Fails if expectedString is not found in x
	 *
	 * @param expectedString
	 *            string searched for in x
	 * @param x
	 *            what can be searched.
	 */
	public static void assertExpectedOutputContains(String expectedString, String x) {

		if (!x.contains(expectedString)) {
			fail("expected '" + expectedString + "' not found in '" + x + "'");
		}
	}

	/**
	 * Fails if expectedString is found in x
	 *
	 * @param expectedString
	 *            string searched for in x
	 * @param x
	 *            what can be searched.
	 */
	public static void assertExpectedOutputNotContains(String expectedString, String x) {

		if (x.contains(expectedString)) {
			fail("expected '" + expectedString + "' found in '" + x + "'");
		}
	}

	/**
	 * Ensured String x ends with expectedString
	 *
	 * @param expectedString
	 *            to match
	 * @param x
	 *            what we got.
	 */
	public static void assertExpectedOutputEndsWith(String expectedString, String x) {
		int beginIndex = x.length() - expectedString.length();
		if (beginIndex < 0)
			beginIndex = 0;
		String gotThisOutput = x.substring(beginIndex);
		assertEquals("Output differs", expectedString, gotThisOutput);
	}

	/**
	 * Reads log file into single String.
	 *
	 * @param log
	 *            file to read
	 * @return content of log
	 */
	public static String readLogfile(File log) {
		final Set<Charset> charsets = new LinkedHashSet<>();
		charsets.add(Charset.defaultCharset());
		if (isWindows()) {
			charsets.add(Charset.forName("CP1252"));
			charsets.add(Charset.forName("CP437"));
		}
		charsets.add(StandardCharsets.UTF_8);
		charsets.add(StandardCharsets.UTF_16);
		for (Charset currCharset : charsets) {
			try {
				return readLogfile(log, currCharset);
			} catch (Throwable th) {
				System.err.println(th.getMessage());
			}
		}
		fail("Cannot read log-file with any of the following charsets: " + Joiner.on(", ").join(charsets));
		return null;
	}

	/**
	 * Reads log file into single String using the given character set.
	 *
	 * @param log
	 *            file to read
	 * @return content of log
	 */
	public static String readLogfile(File log, Charset charset) {
		try {
			System.out.println(
					"Trying to load log file \"" + log + "\" with charset \"" + charset.name() + "\" ...");
			final String result = Joiner.on("\n").join(Files.readAllLines(log.toPath(), charset));
			System.out.println(
					"... done.");
			return result;
		} catch (IOException e) {
			System.out.println("... failed:");
			e.printStackTrace();
			fail("Problems reading log-file \"" + log + "\" with charset \"" + charset.name() + "\": " + e);
		}
		return null;
	}

	/**
	 * Search for existence of substring in logfile.
	 *
	 * @param toFind
	 *            string to search for
	 *
	 */
	public static void assertContainsString(String toFind, File log) {
		assertNotEquals("expected string '" + toFind + "' not found in log", -1, readLogfile(log).indexOf(toFind));
	}

	/**
	 * Ensure to not find substring {@code toFind} in {@code log} file.
	 *
	 * @param toFind
	 *            string to search for
	 * @param log
	 *            file to scan
	 */
	public static void assertNotContainsString(String toFind, File log) {
		assertThat("unexpected string '" + toFind + "' found in log", readLogfile(log).indexOf(toFind) < 0);
	}

	/**
	 * Tells if running on Windows.
	 */
	public static boolean isWindows() {
		return (N4CliHelper.OS_NAME.indexOf("win") >= 0);
	}

	/** The OS name. */
	public static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	/**
	 * Helper to create any Process based on args and redirecting output to log. The process is started in the
	 * TARGET-folder.
	 *
	 * @param log
	 *            file to redirect to
	 * @param workingDir
	 *            path of working directory
	 * @param args
	 *            process arguments
	 * @return started process
	 * @throws IOException
	 *             if errored.
	 */
	public static Process createAndStartProcessIntern(File log, String workingDir, Map<String, String> environment,
			String... args) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(args);
		/*- // Environment can be actively modified, e.g.:
			Map<String, String> env = pb.environment();
			env.put("VAR1", "myValue");
			env.remove("OTHERVAR");
			env.put("VAR2", env.get("VAR1") + "suffix");
		 */
		// include user-provided environment variables
		pb.environment().putAll(environment);
		pb.directory(new File(workingDir));
		EnvironmentVariableUtils.inheritNodeJsPathEnvVariableUtils(pb);

		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.to(log));
		Process p = pb.start();
		assert pb.redirectInput() == Redirect.PIPE;
		assert pb.redirectOutput().file() == log;
		assert p.getInputStream().read() == -1;
		return p;
	}

	/**
	 *
	 */
	public static void appendExternalOutputToStdout(File outputLogFile) {
		System.out.println("===== <= Content of external Process-output below (@see: " + outputLogFile + ") => =====");
		String output = readLogfile(outputLogFile);
		System.out.println(output);
	}

	/**
	 * Copy a fresh fixture to the workspace area. Deleting old leftovers from former tests. Also includes all N4JS
	 * libraries from the {@code n4js} Git repository which name provides {@code true} value for the given predicate.
	 *
	 * @param createYarnWorkspace
	 *            if true, a yarn workspace will be created, i.e. projects will be put into a subfolder
	 *            {@value #PACKAGES} and an appropriate package.json file will be generated.
	 *
	 * @returns file indicating the relative path to the copied data set
	 */
	public static void setupWorkspace(Path sourceLocation, Path destinationLocation,
			Predicate<N4JSProjectName> n4jsLibrariesPredicate, boolean createYarnWorkspace) throws IOException {

		Path projectLocation = createYarnWorkspace ? destinationLocation.resolve(PACKAGES) : destinationLocation;

		// clean
		if (Files.exists(destinationLocation)) {
			FileDeleter.delete(destinationLocation, true);
		}
		Files.createDirectories(destinationLocation);
		Files.createDirectories(projectLocation);

		// copy fixtures to workspace
		FileCopier.copy(sourceLocation, projectLocation, true);

		// copy required n4js libraries to workspace / node_modules location
		Path libsLocation;
		if (createYarnWorkspace) {
			// in case of a yarn workspace, we install the n4js-libs as siblings of the main project(s)
			libsLocation = projectLocation;
		} else {
			// otherwise, we install the n4js-libs in the main project's node_modules folder
			// (note: we assume fixture contains only a single project (i.e. only a single sub folder))
			libsLocation = Files.list(projectLocation).findFirst().get().resolve(N4JSGlobals.NODE_MODULES);
		}
		N4CliHelper.copyN4jsLibsToLocation(libsLocation, n4jsLibrariesPredicate);

		// create yarn workspace
		if (createYarnWorkspace) {
			// create package.json
			List<String> packageJsonLines = Lists.newArrayList(
					"{",
					"\t\"private\": true,",
					"\t\"workspaces\": [ \"packages/*\" ]",
					"}");
			Files.write(destinationLocation.resolve(N4JSGlobals.PACKAGE_JSON), packageJsonLines);

			// create node_modules folder
			Path nodeModulesFolder = destinationLocation.resolve(N4JSGlobals.NODE_MODULES);
			Files.createDirectories(nodeModulesFolder);
			for (Path project : Files.list(projectLocation).collect(Collectors.toList())) {
				if (Files.isDirectory(project)) {
					Files.createSymbolicLink(
							nodeModulesFolder.resolve(project.getFileName()),
							project.toAbsolutePath());
				}
			}
		}
	}

	/**
	 * Same as {@link #copyN4jsLibsToLocation(Path, Predicate)}, but the n4js libraries that are to be installed can be
	 * provided by name instead of a predicate.
	 */
	public static void copyN4jsLibsToLocation(Path location, N4JSProjectName... n4jsLibs) throws IOException {
		copyN4jsLibsToLocation(location, libName -> org.eclipse.xtext.util.Arrays.contains(n4jsLibs, libName));
	}

	/**
	 * Copies the n4js libraries to the given testing workspace {@code location}.
	 *
	 * Only includes n4js libraries, for whose project name {@code n4jsLibrariesPredicate} returns {@code true}.
	 *
	 * @throws IOException
	 *             In case the copying is not successful.
	 */
	public static void copyN4jsLibsToLocation(Path location, Predicate<N4JSProjectName> n4jsLibrariesPredicate)
			throws IOException {

		GlobalStateMemento originalGlobalState = null;
		if (!JSONStandaloneSetup.isSetUp()) {
			originalGlobalState = GlobalRegistries.makeCopyOfGlobalState();
			JSONStandaloneSetup.doSetup();
		}

		try {
			N4jsLibsAccess.installN4jsLibs(
					location,
					true,
					false, // do not use symbolic links (because some tests modify the files in the destination folder)
					false, // do not delete on exit (because tests using this method are responsible for cleaning up)
					libName -> !N4JS_LIBS_BLACKLIST.contains(libName) && n4jsLibrariesPredicate.test(libName));
		} finally {
			if (originalGlobalState != null) {
				// Restore the original global state (if we had to change it)
				// This is important for these cases in particular:
				// 1) tests that invoke N4jscBase#doMain() should run *without* any global setup, because #doMain()
				// should work if invoked from a plain Java main() method; if we provided the JSON setup as a
				// side-effect of this utility method we would "help" the implementation of #doMain() and might overlook
				// problems in #doMain()'s own setup.
				// 2) some tests deliberately run without any or with an incomplete setup in order to test some failure
				// behavior in this broken case.
				originalGlobalState.restoreGlobalState();
			}
		}
	}

}
