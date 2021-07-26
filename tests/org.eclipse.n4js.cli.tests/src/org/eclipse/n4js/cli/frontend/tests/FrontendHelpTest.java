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
	public void testTwoGoals() {
		String args[] = { "version", "help" };
		CliCompileResult result = n4jsc(args, 10, false);
		assertEquals(result.toString(),
				"ERROR-10 (Invalid command line string):  No argument is allowed: help",
				result.getStdOut());
	}

	/**  */
	@Test
	public void testGoalVersion() {
		String args[] = { "version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getVersionExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testOptionVersionAsGoal() {
		String args[] = { "--version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getVersionExpectation(), result.getStdOut());
	}

	/**  */
	@Test
	public void testAnotherGoalWithOptionVersion() {
		String args[] = { "help", "--version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(),
				getVersionExpectation() + "\n" + getUsageExpectationImplicitCompile(),
				result.getStdOut());
	}

	/**  */
	@Test
	public void testAnotherGoalWithOptionVersionWrongOrder() {
		String args[] = { "--help", "lsp", "--version" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(),
				getVersionExpectation() + "\n" + getUsageExpectationLSP(),
				result.getStdOut());
	}

	/**  */
	@Test
	public void testOptionHelp() {
		String args[] = { "--help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationImplicitCompile(), result.getStdOut());
	}

	/**  */
	@Test
	public void testHelp() {
		String args[] = { "help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationImplicitCompile(), result.getStdOut());
	}

	/**  */
	@Test
	public void testCompileOptionHelp() {
		String args[] = { "compile", "--help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationExplicitCompile(), result.getStdOut());
	}

	/**  */
	@Test
	public void testHelpArgumentCompile() {
		String args[] = { "help", "compile" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationExplicitCompile(), result.getStdOut());
	}

	/**  */
	@Test
	public void testLspOptionHelp() {
		String args[] = { "lsp", "--help" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationLSP(), result.getStdOut());
	}

	/**  */
	@Test
	public void testLspOptionHelpWrongOrder() {
		String args[] = { "--help", "lsp" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationLSP(), result.getStdOut());
	}

	/**  */
	@Test
	public void testHelpArgumentLsp() {
		String args[] = { "help", "lsp" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(), getUsageExpectationLSP(), result.getStdOut());
	}

	private String getVersionExpectation() {
		return N4JSLanguageUtils.DEFAULT_LANGUAGE_VERSION + " (commit " + N4JSLanguageUtils.getLanguageCommit() + ")";
	}

	private String getUsageExpectationImplicitCompile() {
		return "Usage: n4jsc [GOAL] [DIR] [OPTION(s)]\n"
				+ " GOAL              : Goals of n4jsc (default: compile)\n"
				+ "   compile             Compile src folders\n"
				+ "   clean               Clean output folders and type index\n"
				+ "   lsp                 Start LSP server\n"
				+ "   set-versions        Set versions of n4js-related dependencies\n"
				+ "   init                Create an empty n4js project\n"
				+ "   help                Show help\n"
				+ "   version             Print version of this tool\n"
				+ " DIR               : name of n4js project or workspace directory (default: .)\n"
				+ " --clean (-c)      : clean output folders at start (default: false)\n"
				+ " --help (-h)       : prints help and exits. Define a goal for goal-specific\n"
				+ "                     help. (default: false)\n"
				+ " --maxErrs N       : set the maximum number of errors to print (default: 0)\n"
				+ " --maxWarns N      : set the maximum number of warnings to print (default: 0)\n"
				+ " --noPersist (-np) : disable persisting of type index to disk. (default: false)\n"
				+ " --noTests         : don't process test folders (default: false)\n"
				+ " --showSetup       : prints n4jsc setup (default: false)\n"
				+ " --testOnly        : only transpile test folders (default: false)\n"
				+ " --verbose         : enables verbose output (default: false)\n"
				+ " --version (-v)    : prints version and exits (default: false)";
	}

	private String getUsageExpectationExplicitCompile() {
		return "Usage: n4jsc compile [DIR] [OPTION(s)]\n"
				+ " DIR               : name of n4js project or workspace directory (default: .)\n"
				+ " --clean (-c)      : clean output folders at start (default: false)\n"
				+ " --help (-h)       : prints help and exits. Define a goal for goal-specific\n"
				+ "                     help. (default: false)\n"
				+ " --maxErrs N       : set the maximum number of errors to print (default: 0)\n"
				+ " --maxWarns N      : set the maximum number of warnings to print (default: 0)\n"
				+ " --noPersist (-np) : disable persisting of type index to disk. (default: false)\n"
				+ " --noTests         : don't process test folders (default: false)\n"
				+ " --showSetup       : prints n4jsc setup (default: false)\n"
				+ " --testOnly        : only transpile test folders (default: false)\n"
				+ " --verbose         : enables verbose output (default: false)\n"
				+ " --version (-v)    : prints version and exits (default: false)";
	}

	private String getUsageExpectationLSP() {
		return "Usage: n4jsc lsp [OPTION(s)]\n"
				+ " --help (-h)    : prints help and exits. Define a goal for goal-specific help.\n"
				+ "                  (default: false)\n"
				+ " --port (-p) N  : set the port of the lsp server (default: 5007)\n"
				+ " --showSetup    : prints n4jsc setup (default: false)\n"
				+ " --stdio        : uses stdin/stdout for communication instead of sockets\n"
				+ "                  (default: false)\n"
				+ " --verbose      : enables verbose output (default: false)\n"
				+ " --version (-v) : prints version and exits (default: false)";
	}

	/**  */
	@Test
	public void testShowSetup() {
		String args[] = { "lsp", "--showSetup" };
		CliCompileResult result = n4jsc(args, 0, false);
		assertEquals(result.toString(),
				"N4jsc.options=\n"
						+ "  Current execution directory=.../.\n"
						+ "  goal=lsp\n"
						+ "  dir=null\n"
						+ "  showSetup=true\n"
						+ "  verbose=false\n"
						+ "  performanceReport=performance-report.txt\n"
						+ "  performanceKey=Build\n"
						+ "  showSetup=true\n"
						+ "  verbose=false\n"
						+ "  log=false\n"
						+ "  logFile=n4jsc.log\n"
						+ "  version=false\n"
						+ "  help=false\n"
						+ "  port=5007\n"
						+ "  stdio=false\n"
						+ "  exec=null",
				result.getStdOut());
	}

}
