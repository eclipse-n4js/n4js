/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.issuePositions;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest;
import org.eclipse.n4js.tests.codegen.Project;
import org.junit.Test;

/**
 * Validates the issue locations when used as LSP server
 */
public class ZeroBasedIssuesInLspTest extends AbstractIdeTest<Void> {

	/** test case */
	@Test
	public void test() throws Exception {
		test("x");
	}

	@Override
	protected void performTest(File root, Project project, Void t) throws Exception {
		Collection<Diagnostic> allDiagnostics = getAllDiagnostics();
		assertEquals(1, allDiagnostics.size());
		Diagnostic diag = allDiagnostics.iterator().next();
		assertEquals("Couldn't resolve reference to IdentifiableElement 'x'.", diag.getMessage());
		assertEquals("[1:0 - 1:1]", getStringLSP4J().toString(diag.getRange()));
	}

}
