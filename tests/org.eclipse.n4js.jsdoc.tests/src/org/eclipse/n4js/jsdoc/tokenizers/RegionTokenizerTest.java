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


public class RegionTokenizerTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testSimple() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", true, '\\', false, false);
		JSDocCharScanner scanner = new JSDocCharScanner("{simple}");
		assertEquals("simple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple} and more");
		assertEquals("simple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner(" {simple} and more"); // ws are not skipped!
		assertNull(rt.nextToken(scanner));

		scanner = new JSDocCharScanner("{simple and more");
		assertNull(rt.nextToken(scanner));
		scanner = new JSDocCharScanner("simple} and more");
		assertNull(rt.nextToken(scanner));
		scanner = new JSDocCharScanner("x {simple} and more");
		assertNull(rt.nextToken(scanner));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleEscaped() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", true, '\\', false, false);
		JSDocCharScanner scanner = new JSDocCharScanner("{si\\{mp\\}le} and more");
		assertEquals("si\\{mp\\}le", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{si\\{mple} and more");
		assertEquals("si\\{mple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple\\} and more");
		assertNull(rt.nextToken(scanner));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleIncludeTags() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", true, '\\', true, false);
		JSDocCharScanner scanner = new JSDocCharScanner("{simple}");
		assertEquals("{simple}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple} and more");
		assertEquals("{simple}", rt.nextToken(scanner).token);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleNested() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", true, '\\', false, false);
		JSDocCharScanner scanner = new JSDocCharScanner("{simple{nested}cont} and more");
		assertEquals("simple{nested}cont", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{nested}} and more");
		assertEquals("simple{nested}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{nested}simple} and more");
		assertEquals("{nested}simple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{nested}} and more");
		assertEquals("{nested}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{nested}}} and more");
		assertEquals("{nested}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{{nested}}} and more");
		assertEquals("{{nested}}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{and{nested}}} and more");
		assertEquals("simple{and{nested}}", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{{nested}} and more");
		assertNull(rt.nextToken(scanner));
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleNotNested() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", false, '\\', false, false);
		JSDocCharScanner scanner = new JSDocCharScanner("{simple{nested}cont} and more");
		assertEquals("simple{nested", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{nested}} and more");
		assertEquals("simple{nested", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{nested}simple} and more");
		assertEquals("{nested", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{nested}} and more");
		assertEquals("{nested", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{{{nested}}} and more");
		assertEquals("{{nested", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{and{nested}}} and more");
		assertEquals("simple{and{nested", rt.nextToken(scanner).token);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleNestedSkipable() {
		RegionTokenizer rt = new RegionTokenizer("{", "}", true, '\\', false, true);

		JSDocCharScanner scanner = new JSDocCharScanner("{simple{nest\ned}cont} and more");
		assertEquals("simple{nest\ned}cont", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{nest\n *ed}cont} and more");
		assertEquals("simple{nest\ned}cont", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{nest\n * ed}cont} and more");
		assertEquals("simple{nest\ned}cont", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("{simple{nest\n *  ed}cont} and more");
		assertEquals("simple{nest\n ed}cont", rt.nextToken(scanner).token);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testHTML() {
		RegionTokenizer rt = new RegionTokenizer("<pre>", "</pre>", true, '\\', false, true);
		JSDocCharScanner scanner = new JSDocCharScanner("<pre>simple</pre> and more");
		assertEquals("simple", rt.nextToken(scanner).token);
		scanner = new JSDocCharScanner("<pre>si<pre>m</pre>ple</pre> and more");
		assertEquals("si<pre>m</pre>ple", rt.nextToken(scanner).token);

		rt = new RegionTokenizer("<pre>", "</pre>", false, '\\', false, true);
		scanner = new JSDocCharScanner("<pre>si<prem>m</prem>ple</pre> and more");
		assertEquals("si<prem>m</prem>ple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("<pre>si<p\nre></p\n * re>ple</pre> and more");
		assertEquals("si<p\nre></p\nre>ple", rt.nextToken(scanner).token);

		scanner = new JSDocCharScanner("<pre>simple</pr");
		assertNull(rt.nextToken(scanner));
		scanner = new JSDocCharScanner("<pre>simple</p re> and more");
		assertNull(rt.nextToken(scanner));
		scanner = new JSDocCharScanner("<pre>simple</p\n * re> and more");
		assertNull(rt.nextToken(scanner));
	}

}
