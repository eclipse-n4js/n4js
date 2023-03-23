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
package org.eclipse.n4js.ide.tests.server;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCallHierarchyTest;
import org.junit.Test;

/**
 * Test class for incoming call hierarchy
 */
public class IncomingCallHierarchyTest extends AbstractCallHierarchyTest {

	/** */
	@Test
	public void test_function_1() throws Exception {
		testIncomingCallsAtCursor("""
				function f<|>f() { g(); }
				function g() {}
				""", "");
	}

}