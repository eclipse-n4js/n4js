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
package org.eclipse.n4js.jsdoc.tokenizers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocToken;


public class TextTokenizerTest {

	@SuppressWarnings("javadoc")
	@Test
	public void test() {
		TextTokenizer TEXT = TextTokenizer.INSTANCE;

		assertEquals(new JSDocToken("Hello World", 0, 10), TEXT.nextToken(new JSDocCharScanner("Hello World")));
		assertEquals(new JSDocToken("Hello World", 7, 17), TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World")));

		assertEquals(new JSDocToken("Hello World", 7, 17),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World", 7)));
		assertEquals(new JSDocToken("Hello World", 7, 17),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World */", 7)));

		assertEquals(
				new JSDocToken("Hello World ", 7, 18),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World {@code sample} */")));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testLinebreak() {
		TextTokenizer TEXT = TextTokenizer.INSTANCE;

		assertEquals(
				new JSDocToken("Hello World\n", 7, 18),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World\n * Skipped */")));
		assertEquals(
				new JSDocToken("Hello World", 7, 17),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World\n * @tag Skipped */")));
		assertEquals(new JSDocToken("Hello World", 7, 17),
				TEXT.nextToken(new JSDocCharScanner("/**\n * Hello World\n */")));

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testEmpty() {
		TextTokenizer TEXT = TextTokenizer.INSTANCE;

		assertEquals(new JSDocToken("\n", 1, 1), TEXT.nextToken(new JSDocCharScanner("x\n cont", 1)));
		assertNull(TEXT.nextToken(new JSDocCharScanner("x*/", 1)));
	}

}
