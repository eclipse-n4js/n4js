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
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.hlc.base.N4jscBase;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;

/**
 * Central helper in running tests with the command line tools (e.g. {@code n4jsc.jar} or {@link N4jscBase}}.
 */
public class N4CliHelper {

	/**
	 * A black list of n4js-libs that are never copied into a headless compiler test workspace.
	 */
	private static final Set<String> N4JS_LIBS_BLACKLIST = new HashSet<>(
			Arrays.asList("org.eclipse.n4js.mangelhaft.reporter.xunit", "n4js-cli", "n4js-mangelhaft-cli"));

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
	 * @param target
	 *            path of target-folder
	 * @param args
	 *            process arguments
	 * @return started process
	 * @throws IOException
	 *             if errored.
	 */
	public static Process createAndStartProcessIntern(File log, String target, Map<String, String> environment,
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
		pb.directory(new File(target));
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
	 * Copies the n4js libraries to the given testing workspace {@code location}.
	 *
	 * Only includes n4js libraries (cf. shipped code), for whose project name {@code n4jsLibrariesPredicate} returns
	 * {@code true}.
	 *
	 * @throws IOException
	 *             In case the copying is not successful.
	 */
	public static void copyN4jsLibsToLocation(Path location,
			Predicate<String> n4jsLibrariesPredicate) throws IOException {
		N4jsLibsAccess.installN4jsLibs(location, true, false, true,
				libName -> !N4JS_LIBS_BLACKLIST.contains(libName) && n4jsLibrariesPredicate.test(libName));
	}

}
