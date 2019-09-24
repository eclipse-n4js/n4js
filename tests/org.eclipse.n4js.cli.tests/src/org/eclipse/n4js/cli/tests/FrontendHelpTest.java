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
package org.eclipse.n4js.cli.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.cli.N4jscMain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendHelpTest extends AbstractCliTest {
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
	public void testGoalHelp() {
		String args[] = { "help" };
		String consoleLog = main(args, 0, false);
		assertEquals(getUsageExpectation(), consoleLog);
	}

	/**  */
	@Test
	public void testOptionHelp() {
		String args[] = { "--help" };
		String consoleLog = main(args, 0, false);
		assertEquals(getUsageExpectation(), consoleLog);
	}

	/**  */
	@Test
	public void testGoalWithOptionHelp() {
		String args[] = { "lsp", "--help" };
		String consoleLog = main(args, 0, false);
		assertEquals(getUsageExpectation(), consoleLog);
	}

	private String getUsageExpectation() {
		return "Usage: java -jar n4jsc.jar [GOAL] [FILE(s)] [OPTION(s)]\n" +
				" GOAL                           : Goals are:\n" +
				"                                  	 help     Prints help\n" +
				"                                  	 compile  Compiles with given options\n" +
				"                                  	 clean    Cleans with given options\n" +
				"                                  	 lsp      Starts LSP server\n" +
				"                                  	 watch    Starts compiler daemon that\n" +
				"                                  watches the given folder(s)\n" +
				"                                  	 api      Generates API documentation from\n" +
				"                                  n4js files\n" +
				"                                  	 (default: compile)\n" +
				" FILE(s)                        : names of either n4js files or n4js project\n" +
				"                                  directories\n" +
				" --clean (-c)                   : [compile] output folders are cleaned before\n" +
				"                                  compilation. (default: false)\n" +
				" --help (-h)                    : same as goal help (default: false)\n" +
				" --maxErrs N                    : [compile] set the maximum number of errors to\n" +
				"                                  print (default: 0)\n" +
				" --maxWarns N                   : [compile] set the maximum number of warnings\n" +
				"                                  to print (default: 0)\n" +
				" --noTests                      : [compile] don't process test folders\n" +
				"                                  (default: false)\n" +
				" --port (-p) N                  : [lsp] set the port of the lsp server\n" +
				"                                  (default: 5007)\n" +
				" --showSetup                    : print information about the current n4jsc\n" +
				"                                  setup (default: false)\n" +
				" --testCatalog (-tc) FILE       : [compile] generates a test catalog file to\n" +
				"                                  the given location. The test catalog lists\n" +
				"                                  all available tests among the compiled\n" +
				"                                  sources. Existing test catalog files will be\n" +
				"                                  replaced.\n" +
				" --testOnly                     : [compile] only transpile contents of test\n" +
				"                                  folders (default: false)\n" +
				" --verbose (-v)                 : enables verbose output (default: false)";
	}

	/**  */
	@Test
	public void testShowSetup() {
		String args[] = { "lsp", "--showSetup" };
		String consoleLog = main(args, 0, false);
		assertTrue(consoleLog.startsWith(
				"N4jsc.options=\n" +
						"  goal=lsp\n" +
						"  showSetup=true\n" +
						"  verbose=false\n" +
						"  maxErrs=0\n" +
						"  maxWarns=0\n" +
						"  testCatalogFile=null\n" +
						"  testOnly=false\n" +
						"  noTests=false\n" +
						"  port=5007\n" +
						"  srcFiles=Optional.empty\n" +
						"  Current execution directory="));
	}

}
