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


public class TagTitleTokenizerTest {

	
	@Test
	public void test() {
		TagTitleTokenizer tokenizer = new TagTitleTokenizer();

		assertEquals(new JSDocToken("Test", 1, 4), tokenizer.nextToken(new JSDocCharScanner("@Test Hello World")));
		assertEquals(new JSDocToken("Test", 1, 4), tokenizer.nextToken(new JSDocCharScanner("@Test\nHello World")));
		assertEquals(new JSDocToken("Test", 1, 4), tokenizer.nextToken(new JSDocCharScanner("@Test\n * Hello World")));
		assertNull(tokenizer.nextToken(new JSDocCharScanner("@ Hello World")));
		assertNull(tokenizer.nextToken(new JSDocCharScanner("Test Hello World")));
		assertNull(tokenizer.nextToken(new JSDocCharScanner("Test @Hello World")));

	}
}
