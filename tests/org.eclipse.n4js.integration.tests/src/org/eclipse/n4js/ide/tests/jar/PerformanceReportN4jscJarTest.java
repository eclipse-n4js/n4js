/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.jar;

import static org.eclipse.n4js.cli.N4jscExitCode.VALIDATION_ERRORS;
import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.N4jscTestOptions;
import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.junit.After;
import org.junit.Test;

import com.google.common.io.CharStreams;

/**
 * Tests to verify performance report files.
 */
public class PerformanceReportN4jscJarTest extends AbstractCliJarTest {
	static final Path WORKSPACE = Path.of(TARGET, WORKSPACE_FOLDER);
	static final File PROJECT = WORKSPACE.resolve("performance-report").toAbsolutePath().toFile();
	static final String PERFORMANCE_REPORT_FILE_NAME_WITHOUT_EXTENSION = "report";
	static final String PERFORMANCE_REPORT_FILE_EXTENSION = ".csv";
	static final String PERFORMANCE_REPORT_FILE_NAME = PERFORMANCE_REPORT_FILE_NAME_WITHOUT_EXTENSION
			+ PERFORMANCE_REPORT_FILE_EXTENSION;
	static final File PERFORMANCE_REPORT_FILE = WORKSPACE.resolve(PERFORMANCE_REPORT_FILE_NAME).toAbsolutePath()
			.toFile();

	/** Initializes test workspace data. */
	public PerformanceReportN4jscJarTest() {
		super("probands/GH-1062", false);
	}

	/** Clean report file */
	@After
	public void deleteReport() {
		if (PERFORMANCE_REPORT_FILE.exists()) {
			PERFORMANCE_REPORT_FILE.delete();
		}
	}

	/**
	 * Enables performance data logging in the headless compiler via the parameter
	 * {@link N4jscOptions#getPerformanceReport()} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaParameter() throws IOException {
		N4jscTestOptions options = COMPILE(PROJECT)
				.performanceReport(PERFORMANCE_REPORT_FILE)
				.performanceKey(N4JSDataCollectors.dcN4JSResource.getId()); // must use other than the default dcBuild!

		CliCompileResult cliResult = n4jsc(options, VALIDATION_ERRORS);
		makeAssertions(cliResult);
	}

	/**
	 * Enables performance data logging in the headless compiler via the system environment variable
	 * {@code N4JSC_PERFORMANCE_REPORT} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaEnvironmemtVariable() throws IOException {
		// setup system environment variables
		setEnvironmentVariable(N4jscOptions.N4JSC_PERFORMANCE_REPORT_ENV, PERFORMANCE_REPORT_FILE.toString());

		N4jscTestOptions options = COMPILE(PROJECT)
				.performanceKey(N4JSDataCollectors.dcN4JSResource.getId()); // must use other than the default dcBuild!
		CliCompileResult cliResult = n4jsc(options, VALIDATION_ERRORS);
		makeAssertions(cliResult);
	}

	private void makeAssertions(CliCompileResult cliResult) throws IOException {
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		// find the actual report file (i.e. the one with the time stamp added to its name)
		File folder = PERFORMANCE_REPORT_FILE.getParentFile();
		File[] matches = folder.listFiles((dir, name) -> name.startsWith(PERFORMANCE_REPORT_FILE_NAME_WITHOUT_EXTENSION)
				&& name.endsWith(PERFORMANCE_REPORT_FILE_EXTENSION));
		assertTrue("Report file is missing", matches.length > 0);
		assertEquals("expected exactly 1 matching file but got: " + matches.length, 1, matches.length);
		File actualReportFile = matches[0];

		// check performance report
		try (FileReader reader = new FileReader(actualReportFile)) {
			final List<String> rows = CharStreams.readLines(reader);
			assertEquals("Performance report contains 2 rows", 2, rows.size());
			String substring = rows.get(1).substring(0, 1);
			assertNotEquals("Performance report has measurement different from 0 in first column of second row", "0",
					substring);
		}
	}
}
