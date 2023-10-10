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
package org.eclipse.n4js.antlr.compressor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.regex.Matcher;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

/**
 */

public class ParserCompressorFragmentTest {

	@Test
	public void testStateInitializerPattern() {
		String javaContent = "		int index3_0 = input.index();\n" + "        input.rewind();\n" + "        s = -1;\n"
				+ "        if ( (LA3_0==CommercialAt) ) {s = 1;}\n" + "\n"
				+ "        else if ( (LA3_0==Private) ) {s = 2;}";
		Matcher matcher = ParserCompressorFragment2.STATE_CHANGE_INITIALIZER_PATTERN.matcher(javaContent);
		assertTrue(matcher.find());
		assertFalse(matcher.find());
	}

	@Test
	public void testConstDefPattern() {
		String javaContent = "			public class InternalN4JSParser extends AbstractInternalHighlightingAntlrParser {\n"
				+ "		    public static final String[] tokenNames = new String[] {\n" + "		    };\n"
				+ "		    public static final int Delete=21;\n"
				+ "		    public static final int RULE_REGEX_CHAR=134;\n"
				+ "		    public static final int EOF=-1;\n" + "		    public static final int Import=23;\n"
				+ "			something else}	";

		Matcher matcher = ParserCompressorFragment2.CONST_DEF_PATTERN.matcher(javaContent);
		assertTrue(matcher.find());
		assertTrue(matcher.find());
		assertTrue(matcher.find());
		assertTrue(matcher.find());
		assertFalse(matcher.find());
	}

	@Test
	public void testCreateConstMap() {
		String javaContent = "			public class InternalN4JSParser extends AbstractInternalHighlightingAntlrParser {\n"
				+ "		    public static final String[] tokenNames = new String[] {\n" + "		    };\n"
				+ "		    public static final int Delete=21;\n"
				+ "		    public static final int RULE_REGEX_CHAR=134;\n"
				+ "		    public static final int EOF=-1;\n" + "		    public static final int Import=23;\n"
				+ "			something else}	";
		ParserCompressorFragment2 pci = new ParserCompressorFragment2();
		Map<String, Integer> constMap = pci.createConstMap(javaContent);
		assertEquals(4, constMap.size());
		String[] tokenNames = { "Delete", "RULE_REGEX_CHAR", "EOF", "Import" };
		int[] tokenValues = { 21, 134, -1, 23 };
		for (int i = 0; i < 3; i++) {
			assertTrue(constMap.containsKey(tokenNames[i]));
			assertEquals(tokenValues[i], constMap.get(tokenNames[i]).intValue());
		}
	}

	@Test
	public void testCompressor() throws IOException {
		String javaContent = loadFile("SampleGrammarFile.txt");
		ParserCompressorFragment2 pci = new ParserCompressorFragment2();
		pci.process(javaContent, new File("test"));
	}

	@Test
	public void testCompressorTooLongMethod() throws IOException {
		ParserCompressorFragment2 pci = new ParserCompressorFragment2();
		String tokenValues = loadFile("TokenValues.txt");
		Map<String, Integer> constMap = pci.createConstMap(tokenValues);
		String javaContent = loadFile("TooLongMethodSnippet1.txt");
		String result = pci.processCascades(javaContent, constMap);
		assertTrue(result.length() < javaContent.length());
	}

	String loadFile(String simpleFileName) throws IOException {
		InputStream is = ParserCompressorFragmentTest.class.getResourceAsStream(simpleFileName);
		try (Reader in = new InputStreamReader(is, Charsets.UTF_8)) {
			return CharStreams.toString(in);
		}
	}
}
