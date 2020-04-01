/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse def License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import org.junit.Test

/**
 * Hover test class for converting html in JSDoc to adoc
 */
public class HoverJSDocMarkupTest extends AbstractHoverTest {

	/** html br> */
	@Test
	def void testHtmlBR() throws Exception {
		testAtCursor(
			'''/** JSDoc <br> CC */class C<|>C {};''',
			"[0:26 - 0:28] [[n4js] class CC, [markdown] JSDoc \n\n CC]");
	}

	/** html br/> */
	@Test
	def void testHtmlBR_() throws Exception {
		testAtCursor(
			'''/** JSDoc <br/> CC */class C<|>C {};''',
			"[0:27 - 0:29] [[n4js] class CC, [markdown] JSDoc \n\n CC]");
	}

	/** html p> */
	@Test
	def void testHtmlP() throws Exception {
		testAtCursor(
			'''/** JSDoc <p> CC */class C<|>C {};''',
			"[0:25 - 0:27] [[n4js] class CC, [markdown] JSDoc \n\n CC]");
	}

	/** html b> */
	@Test
	def void testHtmlB() throws Exception {
		testAtCursor(
			'''/** JSDoc <b>CC</b> */class C<|>C {};''',
			"[0:28 - 0:30] [[n4js] class CC, [markdown] JSDoc **CC**]");
	}

	/** html it> */
	@Test
	def void testHtmlI() throws Exception {
		testAtCursor(
			'''/** JSDoc <i>CC</i> */class C<|>C {};''',
			"[0:28 - 0:30] [[n4js] class CC, [markdown] JSDoc __CC__]");
	}

	/** html code> */
	@Test
	def void testHtmlCODE() throws Exception {
		testAtCursor(
			'''/** JSDoc <code>CC</code> */class C<|>C {};''',
			"[0:34 - 0:36] [[n4js] class CC, [markdown] JSDoc ``++CC++``]");
	}

}
