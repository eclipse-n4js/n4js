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
package org.eclipse.n4js.hlc.integrationtests;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.n4js.cli.N4jscOptions;
import org.eclipse.n4js.cli.helper.AbstractCliJarTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.junit.Test;

import com.google.common.io.CharStreams;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class PerformanceReportN4jscJarTest extends AbstractCliJarTest {
	/** Initializes test workspace data. */
	public PerformanceReportN4jscJarTest() {
		super("probands/GH-1062", false);
	}

	/**
	 * Enables performance data logging in the headless compiler via the parameter
	 * {@link N4jscOptions#getPerformanceReport()} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaParameter() throws IOException {
		File project = Path.of(TARGET, WORKSPACE_FOLDER, "performance-report").toAbsolutePath().toFile();
		File performanceReportLocation = new File(WORKSPACE_FOLDER, "report.csv");

		CliCompileResult cliResult = n4jsc(COMPILE(project).performanceReport(performanceReportLocation));
		makeAssertions(performanceReportLocation, cliResult);
	}

	/**
	 * Enables performance data logging in the headless compiler via the system environment variable
	 * {@code N4JSC_PERFORMANCE_REPORT} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaEnvironmemtVariable() throws IOException {
		File project = Path.of(TARGET, WORKSPACE_FOLDER, "performance-report").toAbsolutePath().toFile();
		File performanceReportLocation = new File(WORKSPACE_FOLDER, "report.csv");

		// setup system environment variables
		setEnvironmentVariable("N4JSC_PERFORMANCE_REPORT", performanceReportLocation.toString());

		CliCompileResult cliResult = n4jsc(COMPILE(project));
		makeAssertions(performanceReportLocation, cliResult);
	}

	private void makeAssertions(File performanceReportLocation, CliCompileResult cliResult) throws IOException {
		assertEquals(cliResult.toString(), 1, cliResult.getTranspiledFilesCount());

		// check performance report
		try (FileReader reader = new FileReader(new File(TARGET_FOLDER, performanceReportLocation.toString()))) {
			final List<String> rows = CharStreams.readLines(reader);
			assertEquals("Performance report contains 2 rows", 2, rows.size());
			assertNotEquals("Performance report has measurement different from 0 in first column of second row",
					rows.get(1).substring(0, 1), "0");
		}
	}
}
