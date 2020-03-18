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

import java.util.Collection;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.ide.tests.server.AbstractStructuredIdeTest;
import org.eclipse.n4js.tests.codegen.Project;
import org.junit.Test;

/**
 * Validates the issue locations when used as LSP server
 */
public class ZeroBasedIssuesInLspTest extends AbstractStructuredIdeTest<Void> {

	/** test case */
	@Test
	public void testZeroBased() throws Exception {
		test("x");
	}

	@Override
	protected void performTest(Project project, Void t) throws Exception {
		Collection<Diagnostic> allDiagnostics = getIssues().values();
		assertEquals(1, allDiagnostics.size());
		Diagnostic diag = allDiagnostics.iterator().next();
		assertEquals("Couldn't resolve reference to IdentifiableElement 'x'.", diag.getMessage());
		assertEquals("[0:0 - 0:1]", getStringLSP4J().toString(diag.getRange()));
	}

}
