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

import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendHelpTest extends AbstractCliFrontendTest {

	/**  */
	@Test
	public void testGoalVersion() {
		String args[] = { "version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getVersionExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testOptionVersion() {
		String args[] = { "--version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getVersionExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testAnotherGoalWithOptionVersion() {
		String args[] = { "lsp", "--version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getVersionExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testGoalHelp() {
		String args[] = { "help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testOptionHelp() {
		String args[] = { "--help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testAnotherGoalWithOptionHelp() {
		String args[] = { "lsp", "--help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectation(), result.getStdOut());
	}

	private String getVersionExpectation() {
		return N4JSLanguageUtils.DEFAULT_LANGUAGE_VERSION;
	}

	private String getUsageExpectation() {
		return "Usage: java -jar n4jsc.jar [GOAL] [FILE(s)] [OPTION(s)]\n" +
				" GOAL                           : Goals are:\n" +
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
				" --help (-h)                    : prints help and exits (default: false)\n" +
				" --maxErrs N                    : [compile] set the maximum number of errors to\n" +
				"                                  print (default: 0)\n" +
				" --maxWarns N                   : [compile] set the maximum number of warnings\n" +
				"                                  to print (default: 0)\n" +
				" --noTests                      : [compile] don't process test folders\n" +
				"                                  (default: false)\n" +
				" --port (-p) N                  : [lsp] set the port of the lsp server\n" +
				"                                  (default: 5007)\n" +
				" --showSetup                    : prints n4jsc setup (default: false)\n" +
				" --stdio                        : [lsp] uses stdin/stdout for communication\n" +
				"                                  instead of sockets (default: false)\n" +
				" --testCatalog (-tc) FILE       : [compile] generates a test catalog file to\n" +
				"                                  the given location. The test catalog lists\n" +
				"                                  all available tests among the compiled\n" +
				"                                  sources. Existing test catalog files will be\n" +
				"                                  replaced.\n" +
				" --testOnly                     : [compile] only transpile contents of test\n" +
				"                                  folders (default: false)\n" +
				" --verbose                      : enables verbose output (default: false)\n" +
				" --version (-v)                 : prints version and exits (default: false)";
	}

	/**  */
	@Test
	public void testShowSetup() {
		String args[] = { "lsp", "--showSetup" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(),
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
						"  Current execution directory=.../.",
				result.getStdOut());
	}

}
