/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest;
import org.junit.Test;

/**
 * Code completion tests for camel case scenarios
 */
public class CamelCaseCompletionTest extends AbstractCompletionTest {

	/***/
	@Test
	public void testCamelCasePrefix_01() {
		testAtCursor("EvE<|>", "(EvalError, Class, EvalError, , , 00000, , , , ([0:0 - 0:3], EvalError), [], [], , )");
	}

	/***/
	@Test
	public void testCamelCasePrefix_02() {
		testAtCursor("eURIC<|>",
				"(encodeURIComponent, Method, encodeURIComponent, , , 00000, , , , ([0:0 - 0:5], encodeURIComponent), [], [], , )");
	}

	/***/
	@Test
	public void testCamelCasePrefix_03() {
		testAtCursor("eUC<|>",
				"(encodeURIComponent, Method, encodeURIComponent, , , 00000, , , , ([0:0 - 0:3], encodeURIComponent), [], [], , )");
	}

	/***/
	@Test
	public void testCamelCasePrefix_04() {
		testAtCursor("eC<|>", "");
	}
}
