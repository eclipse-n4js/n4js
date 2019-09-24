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

import org.eclipse.n4js.cli.N4jscMain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendLspTest extends AbstractCliFrontendTest {
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
	public void testLspNoOpts() {
		String args[] = { "lsp" };
		String consoleLog = main(args, 0);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void testLspPortMissingOpt() {
		String args[] = { "lsp", "--port" };
		String consoleLog = main(args, 10);
		assertEquals("ERROR-10 (Invalid command line string):  Option \"--port (-p)\" takes an operand",
				consoleLog);
	}

	/**  */
	@Test
	public void testLspPortOk() {
		String args[] = { "lsp", "--port", "9415" };
		String consoleLog = main(args);
		assertEquals("", consoleLog);
	}

	/**  */
	@Test
	public void testLspPortNegative() {
		String args[] = { "lsp", "--port", "-42" };
		String consoleLog = main(args, 13);
		assertEquals("ERROR-13 (Invalid option):  Port is out of range: -42", consoleLog);
	}

	/**  */
	@Test
	public void testLspPortTooHigh() {
		String args[] = { "lsp", "--port", "65537" };
		String consoleLog = main(args, 13);
		assertEquals("ERROR-13 (Invalid option):  Port is out of range: 65537", consoleLog);
	}

}
