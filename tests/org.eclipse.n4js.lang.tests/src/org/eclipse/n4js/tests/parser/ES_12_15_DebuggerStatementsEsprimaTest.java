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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.DebuggerStatement;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ES_12_15_DebuggerStatementsEsprimaTest extends AbstractParserTest {

	@Test
	public void testDebugger_Simple() {
		Script script = parseESSuccessfully("debugger;");
		assertEquals("Expected on element, was: " + script.getScriptElements(), 1, script.getScriptElements().size());
		DebuggerStatement dbgStmt = (DebuggerStatement) script.getScriptElements().get(0);
		assertNotNull(dbgStmt);
	}

	@Test
	public void testDebugger_SimpleASI() {
		Script script = parseESSuccessfully("debugger");
		assertEquals(1, script.getScriptElements().size());
		DebuggerStatement dbgStmt = (DebuggerStatement) script.getScriptElements().get(0);
		assertNotNull(dbgStmt);
	}

}
