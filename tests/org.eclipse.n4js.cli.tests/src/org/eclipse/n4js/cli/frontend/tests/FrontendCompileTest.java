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
package org.eclipse.n4js.cli.frontend.tests;

import static org.eclipse.n4js.smith.N4JSDataCollectors.N4JS_CLI_COLLECTOR_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendCompileTest extends AbstractCliFrontendTest {
	private static final String FILE_TC = "file.tc";
	private static final String REPORT_FILE_NAME = "report.csv";
	private static final File REPORT_FILE = new File(REPORT_FILE_NAME);

	/**  */
	@Test
	public void testNoArgsImplicitGoal() {
		String args[] = {};
		CliCompileResult result = n4jsc(args, 12);
		assertEquals(result.toString(),
				"ERROR-12 (Invalid dir(s)):  n4js directory(s) missing",
				result.getStdOut());
	}

	/**  */
	@Test
	public void testNoArgs() {
		String args[] = { "compile" };
		CliCompileResult result = n4jsc(args, 12);
		assertEquals(result.toString(),
				"ERROR-12 (Invalid dir(s)):  n4js directory(s) missing",
				result.getStdOut());
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
		assertEquals(result.toString(), "", result.getStdOut());
	}

	/**  */
	@Test
	public void testArgsCurDir() {
		String args[] = { "compile", "." };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "", result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceReportGiven() {
		if (REPORT_FILE.exists()) {
			REPORT_FILE.delete();
		}

		String args[] = { "compile", "--performanceReport", REPORT_FILE_NAME };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  option \"--performanceReport (-pR)\" requires the option(s) [--performanceKey]",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceReportGivenPerformanceKeyGiven() {
		if (REPORT_FILE.exists()) {
			REPORT_FILE.delete();
		}

		String args[] = { "compile", ".", "--performanceReport", REPORT_FILE_NAME, "--performanceKey",
				N4JS_CLI_COLLECTOR_NAME };

		CliCompileResult result = n4jsc(args, 0);
		assertEquals(result.toString(),
				"Performance Data Collection is enabled.\nWriting performance report: .../report.csv",
				result.getStdOut());
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
	public void checkPerformanceReportMissing() {
		String args[] = { "compile", "--performanceReport", " " };
		CliCompileResult result = n4jsc(args, 10);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  option \"--performanceReport (-pR)\" requires the option(s) [--performanceKey]",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkPerformanceKeyGiven() {
		File reportFile = new File("performance-report.csv");
		if (reportFile.exists()) {
			reportFile.delete();
		}

		String args[] = { "compile", ".", "--performanceKey", N4JS_CLI_COLLECTOR_NAME };
		CliCompileResult result = n4jsc(args, 0);
		assertEquals(result.toString(),
				"Performance Data Collection is enabled.\nWriting performance report: .../performance-report.csv",
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
		assertEquals(result.toString(), "", result.getStdOut());
	}

	/**  */
	@Test
	public void checkNoTestsWrongGoal() {
		String args[] = { "lsp", ".", "--noTests" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Given option --noTests requires goal(s) compile, but goal lsp was given.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestOnlyOk() {
		String args[] = { ".", "--testOnly" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "", result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestOnlyWrongGoal() {
		String args[] = { "lsp", ".", "--testOnly" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Given option --testOnly requires goal(s) compile, but goal lsp was given.",
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
		assertEquals(result.toString(), "", result.getStdOut());
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
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Given option --maxErrs requires goal(s) compile, but goal lsp was given.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkMaxWarnsOk() {
		String args[] = { ".", "--maxErrs", "1" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "", result.getStdOut());
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
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Given option --maxWarns requires goal(s) compile, but goal lsp was given.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkCleanOk() {
		String args[] = { ".", "--clean" };
		CliCompileResult result = n4jsc(args);
		assertEquals(result.toString(), "", result.getStdOut());
	}

	/**  */
	@Test
	public void checkCleanWrongGoal() {
		String args[] = { "lsp", ".", "--clean" };
		CliCompileResult result = n4jsc(args, 13);
		assertEquals(result.toString(),
				"ERROR-13 (Invalid option):  Given option --clean requires goal(s) compile, but goal lsp was given.",
				result.getStdOut());
	}

	/**  */
	@Test
	public void checkTestCatalogOk() {
		try {
			String args[] = { ".", "--testCatalog", FILE_TC };
			CliCompileResult result = n4jsc(args);
			assertEquals(result.toString(), "", result.getStdOut());

		} finally {
			tryDeleteTC();
		}
	}

	/**  */
	@Test
	public void checkTestCatalogMissingOp() {
		try {
			String args[] = { ".", "--testCatalog" };
			CliCompileResult result = n4jsc(args, 10);
			assertEquals(result.toString(),
					"ERROR-10 (Invalid command line string):  Option \"--testCatalog (-tc)\" takes an operand",
					result.getStdOut());

		} finally {
			tryDeleteTC();
		}
	}

	/**  */
	@Test
	public void checkTestCatalogWrongGoal() {
		try {
			String args[] = { "lsp", ".", "--testCatalog", FILE_TC };
			CliCompileResult result = n4jsc(args, 13);
			assertEquals(result.toString(),
					"ERROR-13 (Invalid option):  Given option --testCatalog requires goal(s) compile, but goal lsp was given.",
					result.getStdOut());

		} finally {
			tryDeleteTC();
		}
	}

	private void tryDeleteTC() {
		File tc = new File(FILE_TC);
		if (tc.exists()) {
			try {
				tc.delete();
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
