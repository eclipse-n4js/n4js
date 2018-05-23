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
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.DebuggerStatement
import org.junit.Test

class ES_12_15_DebuggerStatementsEsprimaTest extends AbstractParserTest {

	@Test
	def void testDebugger_Simple() {
		val script = 'debugger;'.parseSuccessfully
		assertEquals("Expected on element, was: " + script.scriptElements, 1, script.scriptElements.size)
		val dbgStmt = script.scriptElements.head as DebuggerStatement
		assertNotNull(dbgStmt)
	}

	@Test
	def void testDebugger_SimpleASI() {
		val script = 'debugger'.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val dbgStmt = script.scriptElements.head as DebuggerStatement
		assertNotNull(dbgStmt)
	}


}
