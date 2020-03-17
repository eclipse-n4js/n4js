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

import org.eclipse.n4js.ide.tests.server.AbstractCompletionTest;
import org.eclipse.xtext.testing.TestCompletionConfiguration;
import org.junit.Test;

/**
 * Code completion tests for camel case scenarios
 */
public class CamelCaseCompletionTest extends AbstractCompletionTest {

	/***/
	@Test
	public void testCamelCasePrefix_01() throws Exception {
		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel("EvE");
		tcc.setColumn(tcc.getModel().length());
		tcc.setExpectedCompletionItems("[(EvalError, Text, , , , 00000, , , , ([0:0 - 0:3], EvalError), [], [], , )]");
		test(tcc);
	}

	/***/
	@Test
	public void testCamelCasePrefix_02() throws Exception {
		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel("eURIC");
		tcc.setColumn(tcc.getModel().length());
		tcc.setExpectedCompletionItems(
				"[(encodeURIComponent, Text, , , , 00000, , , , ([0:0 - 0:5], encodeURIComponent), [], [], , )]");
		test(tcc);
	}

	/***/
	@Test
	public void testCamelCasePrefix_03() throws Exception {
		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel("eUC");
		tcc.setColumn(tcc.getModel().length());
		tcc.setExpectedCompletionItems(
				"[(encodeURIComponent, Text, , , , 00000, , , , ([0:0 - 0:3], encodeURIComponent), [], [], , )]");
		test(tcc);
	}

	/***/
	@Test
	public void testCamelCasePrefix_04() throws Exception {
		TestCompletionConfiguration tcc = new TestCompletionConfiguration();
		tcc.setModel("eC");
		tcc.setColumn(tcc.getModel().length());
		tcc.setExpectedCompletionItems("[]");
		test(tcc);
	}
}
