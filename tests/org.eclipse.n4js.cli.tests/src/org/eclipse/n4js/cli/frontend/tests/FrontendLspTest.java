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

import org.eclipse.n4js.cli.helper.CliResult;
import org.junit.Test;

/** Front end tests for the CLI interface */
public class FrontendLspTest extends AbstractCliFrontendTest {

	/**  */
	@Test
	public void testLspNoOpts() {
		String args[] = { "lsp" };
		CliResult result = main(args, 0);
		assertEquals("", result.getStdOut());
	}

	/**  */
	@Test
	public void testLspPortMissingOpt() {
		String args[] = { "lsp", "--port" };
		CliResult result = main(args, 10);
		assertEquals("ERROR-10 (Invalid command line string):  Option \"--port (-p)\" takes an operand",
				result.getStdOut());
	}

	/**  */
	@Test
	public void testLspPortOk() {
		String args[] = { "lsp", "--port", "9415" };
		CliResult result = main(args);
		assertEquals("", result.getStdOut());
	}

	/**  */
	@Test
	public void testLspPortNegative() {
		String args[] = { "lsp", "--port", "-42" };
		CliResult result = main(args, 13);
		assertEquals("ERROR-13 (Invalid option):  Port is out of range: -42", result.getStdOut());
	}

	/**  */
	@Test
	public void testLspPortTooHigh() {
		String args[] = { "lsp", "--port", "65537" };
		CliResult result = main(args, 13);
		assertEquals("ERROR-13 (Invalid option):  Port is out of range: 65537", result.getStdOut());
	}

}
