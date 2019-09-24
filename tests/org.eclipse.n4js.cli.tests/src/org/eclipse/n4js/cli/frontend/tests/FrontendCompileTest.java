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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.n4js.cli.N4jscMain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendCompileTest extends AbstractCliFrontendTest {
	private static final String FILE_TC = "file.tc";

	/** Set the flag */
	@Before
	public void before2() {
		N4jscMain.TESTFLAG_NO_PERFORM = true;
	}

	/** Restore the flag */
	@After
	public void after2() {
		N4jscMain.TESTFLAG_NO_PERFORM = false;
	}

	/**  */
	@Test
	public void testNoArgsImplicitGoal() {
		String args[] = {};
		String consoleLog = main(args, 12);
		assertEquals(
				"ERROR-12 (Invalid file(s)):  n4js file(s) or project(s) missing",
				consoleLog);
	}

	/**  */
	@Test
	public void testNoArgs() {
		String args[] = { "compile" };
		String consoleLog = main(args, 12);
		assertEquals(
				"ERROR-12 (Invalid file(s)):  n4js file(s) or project(s) missing",
				consoleLog);
	}

	/**  */
	@Test
	public void testArgsFileImplicitGoal() {
		String args[] = { "compile", "test.n4js" };
		String consoleLog = main(args, 12);
		assertEquals(
				"ERROR-12 (Invalid file(s)):  file(s) do not exist: test.n4js",
				consoleLog);
	}

	/**  */
	@Test
	public void testArgsFile() {
		String args[] = { "test.n4js" };
		String consoleLog = main(args, 12);
		assertEquals(
				"ERROR-12 (Invalid file(s)):  file(s) do not exist: test.n4js",
				consoleLog);
	}

	/**  */
	@Test
	public void testArgsCurDirImplicitGoal() {
		String args[] = { "." };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void testArgsCurDir() {
		String args[] = { "compile", "." };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkNoTestsOk() {
		String args[] = { ".", "--noTests" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkNoTestsWrongGoal() {
		String args[] = { "lsp", ".", "--noTests" };
		String consoleLog = main(args, 13);
		assertEquals(
				"ERROR-13 (Invalid option):  Given option --noTests requires goal(s) compile, but goal lsp was given.",
				consoleLog);
	}

	/**  */
	@Test
	public void checkTestOnlyOk() {
		String args[] = { ".", "--testOnly" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkTestOnlyWrongGoal() {
		String args[] = { "lsp", ".", "--testOnly" };
		String consoleLog = main(args, 13);
		assertEquals(
				"ERROR-13 (Invalid option):  Given option --testOnly requires goal(s) compile, but goal lsp was given.",
				consoleLog);
	}

	/**  */
	@Test
	public void checkTestOnlyPlusNoTests() {
		String args[] = { ".", "--testOnly", "--noTests" };
		String consoleLog = main(args, 10);

		assertTrue(consoleLog.startsWith("ERROR-10 (Invalid command line string):  option"));
		assertTrue(consoleLog.contains("cannot be used with the option(s)"));
	}

	/**  */
	@Test
	public void checkMaxErrsOk() {
		String args[] = { ".", "--maxErrs", "1" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkMaxErrsMissingOp() {
		String args[] = { ".", "--maxErrs" };
		String consoleLog = main(args, 10);
		assertEquals("ERROR-10 (Invalid command line string):  Option \"--maxErrs\" takes an operand", consoleLog);
	}

	/**  */
	@Test
	public void checkMaxErrsWrongGoal() {
		String args[] = { "lsp", ".", "--maxErrs", "1" };
		String consoleLog = main(args, 13);
		assertEquals(
				"ERROR-13 (Invalid option):  Given option --maxErrs requires goal(s) compile, but goal lsp was given.",
				consoleLog);
	}

	/**  */
	@Test
	public void checkMaxWarnsOk() {
		String args[] = { ".", "--maxErrs", "1" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkMaxWarnsMissingOp() {
		String args[] = { ".", "--maxWarns" };
		String consoleLog = main(args, 10);
		assertEquals("ERROR-10 (Invalid command line string):  Option \"--maxWarns\" takes an operand", consoleLog);
	}

	/**  */
	@Test
	public void checkMaxWarnsWrongGoal() {
		String args[] = { "lsp", ".", "--maxWarns", "1" };
		String consoleLog = main(args, 13);
		assertEquals(
				"ERROR-13 (Invalid option):  Given option --maxWarns requires goal(s) compile, but goal lsp was given.",
				consoleLog);
	}

	/**  */
	@Test
	public void checkCleanOk() {
		String args[] = { ".", "--clean" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void checkCleanWrongGoal() {
		String args[] = { "lsp", ".", "--clean" };
		String consoleLog = main(args, 13);
		assertEquals(
				"ERROR-13 (Invalid option):  Given option --clean requires goal(s) compile, but goal lsp was given.",
				consoleLog);
	}

	/**  */
	@Test
	public void checkTestCatalogOk() {
		try {
			String args[] = { ".", "--testCatalog", FILE_TC };
			String consoleLog = main(args);
			assertEquals("", consoleLog);

		} finally {
			tryDeleteTC();
		}
	}

	/**  */
	@Test
	public void checkTestCatalogMissingOp() {
		try {
			String args[] = { ".", "--testCatalog" };
			String consoleLog = main(args, 10);
			assertEquals("ERROR-10 (Invalid command line string):  Option \"--testCatalog (-tc)\" takes an operand",
					consoleLog);

		} finally {
			tryDeleteTC();
		}
	}

	/**  */
	@Test
	public void checkTestCatalogWrongGoal() {
		try {
			String args[] = { "lsp", ".", "--testCatalog", FILE_TC };
			String consoleLog = main(args, 13);
			assertEquals(
					"ERROR-13 (Invalid option):  Given option --testCatalog requires goal(s) compile, but goal lsp was given.",
					consoleLog);

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
