/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for SourceMap including the SourceMapParser
 */

public class SourceMapTest {

	static final String sampleMap = "{\n"
			+ "\t\"version\": 3,\n"
			+ "\t\"file\": \"C.js\",\n"
			+ "\t\"sourceRoot\": \"\",\n"
			+ "\t\"sources\": [\"C.ts\"],\n"
			+ "\t\"names\": [],\n"
			+ "\t\"mappings\": \""
			+ "AAAA"
			+ ";IACC,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA"
			+ ";IACpB,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA"
			+ ";AACrB,CAAC"
			+ ";AAED,CAAC,EAAE,CAAC\"\n"
			+ "}";

	String[] lineMappings = new String[] {
			"0: 0->0:0",
			"1: 4->1:1 11->1:8 12->1:9 15->1:12 16->1:13 23->1:20 24->1:21 25->1:21",
			"2: 4->2:1 11->2:8 12->2:9 15->2:12 16->2:13 23->2:20 24->2:21 25->2:21",
			"3: 0->3:0 1->3:1",
			"4: 0->5:0 1->5:1 3->5:3 4->5:4"
	};

	@Test
	public void testParserHeader() {
		SourceMap map = SourceMap.parse(sampleMap);
		assertEquals("3", map.version);
		assertEquals("C.js", map.file);
		assertEquals("", map.sourceRoot);
		assertEquals(map.sources.toString(), 1, map.sources.size());
		assertEquals("C.ts", map.sources.get(0));
		assertEquals(map.names.toString(), 0, map.names.size());
	}

	@Test
	public void testParserMappings() {
		SourceMap map = SourceMap.parse(sampleMap);
		assertEquals("lines", lineMappings.length, map.genMappings.size());
		for (int i = 0; i < lineMappings.length; i++) {
			String actual = map.genMappings.get(i).toString();
			String expected = lineMappings[i];
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testParserRoundtrip() {
		SourceMap map = SourceMap.parse(sampleMap);
		String actual = map.toString();
		assertEquals(sampleMap, actual);
		String dactual = SourceMap.parse(actual).toString();
		assertEquals(actual, dactual);
	}

}
