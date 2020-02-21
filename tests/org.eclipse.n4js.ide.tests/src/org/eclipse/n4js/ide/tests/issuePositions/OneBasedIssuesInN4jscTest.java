/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.issuePositions;

import static org.eclipse.n4js.cli.N4jscTestOptions.COMPILE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.cli.helper.AbstractCliCompileTest;
import org.eclipse.n4js.cli.helper.CliCompileResult;
import org.eclipse.n4js.ide.tests.server.StringLSP4J;
import org.eclipse.n4js.utils.io.FileDeleter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Validates the issue locations when used as command line compiler server
 */
public class OneBasedIssuesInN4jscTest extends AbstractCliCompileTest {
	File workspace;

	/** Prepare workspace. */
	@Before
	public void setupWorkspace() throws IOException {
		workspace = setupWorkspace("oneBasedWSP", false);
	}

	/** Delete workspace. */
	@After
	public void deleteWorkspace() throws IOException {
		FileDeleter.delete(workspace.toPath(), true);
	}

	/** test case */
	@Test
	public void testCompile() {
		File projectRoot = new File(workspace, "oneBased");

		CliCompileResult cliResult = n4jsc(COMPILE(workspace));
		assertEquals(cliResult.toString(), 1, cliResult.getIssues().size());
		Diagnostic diag = cliResult.getIssues().values().iterator().next();
		assertEquals("Couldn't resolve reference to IdentifiableElement 'x'.", diag.getMessage());
		assertEquals("[1:0 - 1:1]", new StringLSP4J(projectRoot).toString(diag.getRange()));
	}

}
