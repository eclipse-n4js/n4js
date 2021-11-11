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
package org.eclipse.n4js.integration.tests.cli.frontend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendCompileTest extends AbstractCliFrontendTest {
	private static final String REPORT_FILE_NAME_WITHOUT_EXTENSTION = "report";
	private static final String REPORT_FILE_NAME_EXTENSTION = ".csv";
	private static final String REPORT_FILE_NAME = REPORT_FILE_NAME_WITHOUT_EXTENSTION + REPORT_FILE_NAME_EXTENSTION;
	private static final File REPORT_FILE = new File(REPORT_FILE_NAME);

	/**  */
	@Test
	public void testNoArgsImplicitGoal() {
		String args[] = {};
		n4jsc(args);
	}

	/**  */
	@Test
	public void testNoArgs() {
		String args[] = { "compile" };
		n4jsc(args);
	}

	/**  */
	@Test
	public void testArgsFileImplicitGoal() {
		String args[] = { "compile", "test" };
		CliCompileResult result = n4jsc(args, 12);
		assertEquals(result.toString(),
				"ERROR-12 (Invalid dir(s)):  directory(s) do not exist: .../test",
				result.getStdOut());
	}

	/**  */
	@Test
	public void testArgsFile() {
		String args[] = { "test" };
		CliCompileResult result = n4jsc(args, 12);
		assertEquals(result.toString(),
				"ERROR-12 (Invalid dir(s)):  directory(s) do not exist: .../test",
				result.getStdOut());
	}

	/**  */
	@Test
	public void testArgsCurDirImplicitGoal() {
		String args[] = { "." };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void testArgsCurDir() {
		String args[] = { "compile", "." };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceReportGiven() {
		if (REPORT_FILE.exists()) {
			REPORT_FILE.delete();
		}

		String args[] = { "compile", ".", "--performanceReport", REPORT_FILE_NAME };
		CliCompileResult result = n4jsc(args, 0);
		String stdout = result.getStdOut();
		assertTrue(result.toString(), stdout.startsWith(
				"Performance Data Collection is enabled.\n"
						+ "Noop backend skips goal compile\n"
						+ "Writing performance report: .../"
						+ REPORT_FILE_NAME_WITHOUT_EXTENSTION));
	}

	/**  */
	@Test
	public void checkPerformanceReportGivenPerformanceKeyGiven() {
		if (REPORT_FILE.exists()) {
			REPORT_FILE.delete();
		}

		String args[] = { "compile", ".", "--performanceReport", REPORT_FILE_NAME, "--performanceKey",
				N4JSDataCollectors.dcBuild.getId() };

		CliCompileResult result = n4jsc(args, 0);
		String stdout = result.getStdOut();
		assertTrue(result.toString(), stdout.startsWith(
				"Performance Data Collection is enabled.\n"
						+ "Noop backend skips goal compile\n"
						+ "Writing performance report: .../"
						+ REPORT_FILE_NAME_WITHOUT_EXTENSTION));
	}

	/**  */
	@Test
	public void checkPerformanceReportGivenPerformanceKeyMissing() {
		if (REPORT_FILE.exists()) {
			REPORT_FILE.delete();
		}

		String args[] = { "compile", ".", "--performanceReport", REPORT_FILE_NAME, "--performanceKey", "" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Missing performance key.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceKeyGiven() {
		File reportFile = new File("performance-report.csv");
		if (reportFile.exists()) {
			reportFile.delete();
		}

		String args[] = { "compile", ".", "--performanceKey", N4JSDataCollectors.dcBuild.getId() };
		CliCompileResult result = n4jsc(args, 0);
		String stdout = result.getStdOut();
		assertTrue(result.toString(), stdout.startsWith(
				"Performance Data Collection is enabled.\n"
						+ "Noop backend skips goal compile\n"
						+ "Writing performance report: .../performance-report"));
	}

	/**  */
	@Test
	public void checkPerformanceKeyIncompatible() {
		String args[] = { "compile", ".",
				"--performanceReport", "someFile.csv",
				"--performanceKey", "*" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Asterisk as performance key not supported when exporting to CSV format.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceKeyMissing() {
		String args[] = { "compile", ".", "--performanceKey", "" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(), "ERROR-13 (Invalid option):  Missing performance key.", result.getStdOut());
	}

	/**  */
	@Test
	public void checkNoTestsOk() {
		String args[] = { ".", "--noTests" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkNoTestsWrongGoal() {
		String args[] = { "lsp", ".", "--noTests" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  No argument is allowed: .",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestOnlyOk() {
		String args[] = { ".", "--testOnly" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestOnlyWrongGoal() {
		String args[] = { "lsp", ".", "--testOnly" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  No argument is allowed: .",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestOnlyPlusNoTests() {
		String args[] = { ".", "--testOnly", "--noTests" };
		CliCompileResult result = n4jsc(args, 10);

		String stdOut = result.getStdOut();
		assertTrue(stdOut.startsWith("ERROR-10 (Invalid command line string):  option"));
		assertTrue(stdOut.contains("cannot be used with the option(s)"));
	}

	/**  */
	@Test
	public void checkMaxErrsOk() {
		String args[] = { ".", "--maxErrs", "1" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxErrsMissingOp() {
		String args[] = { ".", "--maxErrs" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  Option \"--maxErrs\" takes an operand",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxErrsWrongGoal() {
		String args[] = { "lsp", ".", "--maxErrs", "1" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  No argument is allowed: .",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxWarnsOk() {
		String args[] = { ".", "--maxErrs", "1" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxWarnsMissingOp() {
		String args[] = { ".", "--maxWarns" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  Option \"--maxWarns\" takes an operand",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxWarnsWrongGoal() {
		String args[] = { "lsp", ".", "--maxWarns", "1" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  No argument is allowed: .",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkCleanOk() {
		String args[] = { ".", "--clean" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "Noop backend skips goal compile", result.getStdOut());
	}

	/**  */
	@Test
	public void checkOptionWrongGoal() {
		String args[] = { "lsp", "--noTests" };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  \"--noTests\" is not a valid option",
				result.getStdOut());
	}

}
