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
package org.eclipse.n4js.ide.jartests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.eclipse.n4js.smith.N4JSDataCollectors.N4JS_CLI_COLLECTOR_NAME;
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
import org.junit.After;
import org.junit.Test;

import com.google.common.io.CharStreams;

/**
 * Tests to verify performance report files.
 */
public class PerformanceReportN4jscJarTest extends AbstractCliJarTest {
	static final Path WORKSPACE = Path.of(TARGET, WORKSPACE_FOLDER);
	static final File PROJECT = WORKSPACE.resolve("performance-report").toAbsolutePath().toFile();
	static final File PERFORMANCE_REPORT_FILE = WORKSPACE.resolve("report.csv").toAbsolutePath().toFile();

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
				.performanceKey(N4JS_CLI_COLLECTOR_NAME);

		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
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

		N4jscTestOptions options = COMPILE(PROJECT).performanceKey(N4JS_CLI_COLLECTOR_NAME);
		CliCompileResult cliResult = n4jsc(options);
		assertEquals(cliResult.toString(), 0, cliResult.getExitCode());
		makeAssertions(cliResult);
	}

	private void makeAssertions(CliCompileResult cliResult) throws IOException {
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		// check performance report
		assertTrue("Report file is missing", PERFORMANCE_REPORT_FILE.exists());

		try (FileReader reader = new FileReader(PERFORMANCE_REPORT_FILE)) {
			final List<String> rows = CharStreams.readLines(reader);
			assertEquals("Performance report contains 2 rows", 2, rows.size());
			String substring = rows.get(1).substring(0, 1);
			assertNotEquals("Performance report has measurement different from 0 in first column of second row", "0",
					substring);
		}
	}
}
