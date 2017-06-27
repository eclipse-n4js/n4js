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
package org.eclipse.n4js.jsdoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/***/
public class JSDocCharScannerTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testEmpty() {

		assertFalse(new JSDocCharScanner("").hasNext());
		assertFalse(new JSDocCharScanner("/**").hasNext());
		assertFalse(new JSDocCharScanner("*/").hasNext());
		assertFalse(new JSDocCharScanner("/** */").hasNext());
		assertFalse(new JSDocCharScanner("/** \n * */").hasNext());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testContent() {
		doTestContent("Test", new JSDocCharScanner("Test"));
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n */"));
		doTestContent("Test\nEin Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */"));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testInitialOffset() {
		doTestContent("\nEin Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 11));
		doTestContent("Ein Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 15));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testMaxOffset() {
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 0, 11));
		doTestContent("Ein", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 15, 19));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testWhitespaces() {
		doTestContent(" Test", new JSDocCharScanner(" Test")); // preserve leading ws
		doTestContent("Test", new JSDocCharScanner("Test ")); // remove trailing ws at end
		doTestContent(" Test", new JSDocCharScanner(" Test ")); // preserve leading ws only
		doTestContent(" Test", new JSDocCharScanner("/**\n *  Test\n */")); // end of JSDoc
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n */")); // start and end
	}

	/***/
	protected void doTestContent(String expected, JSDocCharScanner scanner) {
		String actual = "";
		while (scanner.hasNext()) {
			actual += scanner.next();
		}

		assertEquals(expected, actual);

	}

}
