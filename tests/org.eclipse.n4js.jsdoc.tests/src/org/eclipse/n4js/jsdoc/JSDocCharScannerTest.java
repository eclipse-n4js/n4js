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


public class JSDocCharScannerTest {

	
	@Test
	public void testEmpty() {

		assertFalse(new JSDocCharScanner("").hasNext());
		assertFalse(new JSDocCharScanner("/**").hasNext());
		assertFalse(new JSDocCharScanner("*/").hasNext());
		assertFalse(new JSDocCharScanner("/** */").hasNext());
		assertFalse(new JSDocCharScanner("/** \n * */").hasNext());
	}

	
	@Test
	public void testContent() {
		doTestContent("Test", new JSDocCharScanner("Test"));
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n */"));
		doTestContent("Test\nEin Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */"));
	}

	
	@Test
	public void testInitialOffset() {
		doTestContent("\nEin Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 11));
		doTestContent("Ein Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 15));
	}

	
	@Test
	public void testMaxOffset() {
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 0, 11));
		doTestContent("Ein", new JSDocCharScanner("/**\n * Test\n * Ein Test\n */", 15, 19));
	}

	
	@Test
	public void testWhitespaces() {
		doTestContent(" Test", new JSDocCharScanner(" Test"), "preserve leading ws");
		doTestContent("Test", new JSDocCharScanner("Test "), "remove trailing ws at end");
		doTestContent(" Test", new JSDocCharScanner(" Test "), "preserve leading ws only");
		doTestContent(" Test", new JSDocCharScanner("/**\n *  Test\n */"), "only skip one space after *");
		doTestContent("Test", new JSDocCharScanner("/**\n * Test\n */"), "start and end");
	}

	/**
	 * Tests whether next scanner token is as expected.
	 */
	protected void doTestContent(String expected, JSDocCharScanner scanner) {
		doTestContent(expected, scanner, null);
	}

	/**
	 * Tests whether next scanner token is as expected, shows message in case of failure.
	 */
	protected void doTestContent(String expected, JSDocCharScanner scanner, String message) {
		String actual = "";
		while (scanner.hasNext()) {
			actual += scanner.next();
		}

		assertEquals(message, expected, actual);

	}

}
