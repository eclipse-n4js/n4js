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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.hlc.base.BuildType;
import org.junit.Test;

import com.google.common.io.CharStreams;

/**
 * IMPORTANT: for info on how to run this test locally, see {@link AbstractN4jscJarTest}!
 */
public class PerformanceReportN4jscJarTest extends AbstractN4jscJarTest {
	/** Initializes test workspace data. */
	public PerformanceReportN4jscJarTest() {
		super("probands/GH-1062", false);
	}

	/**
	 * Enables performance data logging in the headless compiler via the system environment variable
	 * {@code N4JSC_PERFORMANCE_REPORT} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaParameter() throws FileNotFoundException, IOException, InterruptedException {
		logFile();

		final String performanceReportLocation = WORKSPACE_FOLDER + "/report.csv";

		final String[] args = {
				"--projectlocations", WORKSPACE_FOLDER,
				"--performanceReport", performanceReportLocation,
				"--buildType", BuildType.allprojects.toString()
		};

		// run n4jsc.jar in process
		Process p = createAndStartProcess(args);
		int exitCode = p.waitFor();
		assertEquals("Could not successfully execute N4JSC with performance report option.", 0, exitCode);

		// check performance report
		try (FileReader reader = new FileReader(HlcTestingConstants.TARGET_FOLDER + performanceReportLocation)) {
			final List<String> rows = CharStreams.readLines(reader);
			assertEquals("Performance report contains 2 rows", 2, rows.size());
			assertNotEquals("Performance report has measurement different from 0 in first column of second row",
					rows.get(1).substring(0, 1), "0");
		}
	}

	/**
	 * Enables performance data logging in the headless compiler via the system environment variable
	 * {@code N4JSC_PERFORMANCE_REPORT} and asserts the output format of the performance report.
	 */
	@Test
	public void testPerformanceReportViaEnvironmemtVariable()
			throws FileNotFoundException, IOException, InterruptedException {
		logFile();

		final String performanceReportLocation = WORKSPACE_FOLDER + "/report.csv";

		final String[] args = {
				"--projectlocations", WORKSPACE_FOLDER,
				"--buildType", BuildType.allprojects.toString()
		};

		// setup system environment variables
		Map<String, String> env = new HashMap<>();
		env.put("N4JSC_PERFORMANCE_REPORT", performanceReportLocation);

		// run n4jsc.jar in process
		Process p = createAndStartProcess(env, args);
		int exitCode = p.waitFor();
		assertEquals("Could not successfully execute N4JSC with performance report option.", 0, exitCode);

		// check performance report
		try (FileReader reader = new FileReader(HlcTestingConstants.TARGET_FOLDER + performanceReportLocation)) {
			final List<String> rows = CharStreams.readLines(reader);
			assertEquals("Performance report contains 2 rows", 2, rows.size());
			assertNotEquals("Performance report has measurement different from 0 in first column of second row",
					rows.get(1).substring(0, 1), "0");
		}
	}
}
