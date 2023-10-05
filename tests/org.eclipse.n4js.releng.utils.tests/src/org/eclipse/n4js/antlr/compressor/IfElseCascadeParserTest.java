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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.eclipse.n4js.antlr.compressor.IfElseCascade.Replacement;

/**
 */

public class IfElseCascadeParserTest {

	static final String snippet = "pre else if ( (LA450_383==Solidus) && (synpred265_InternalN4JSParser())) {s = 282;}\n"
			+ " else if ( (LA450_383==PercentSign) && (synpred265_InternalN4JSParser())) {s = 283;}\n"
			+ " else if ( (LA450_383==PlusSign) && (synpred265_InternalN4JSParser())) {s = 284;}\n" + " post";
	static final String snippet2 = "pre if ( (LA450_383==Solidus) ) {s = 282;}\n"
			+ " else if ( (LA450_383==PercentSign) ) {s = 283;}\n" + " else if ( (LA450_383==PlusSign) ) {s = 284;}\n"
			+ " post";
	static final String snippet3 = "pre if ( (LA450_383==Solidus) ) {s = 282;}\n"
			+ " else if ( (LA450_383==PercentSign) && (synpred265_InternalN4JSParser())) {s = 283;}\n"
			+ " else if ( (LA450_383==PlusSign) && (synpred265_InternalN4JSParser())) {s = 284;}\n" + " post";

	@Test
	public void testParsingWithCondition() {
		IfElseCascadeParser parser = new IfElseCascadeParser(snippet);
		List<IfElseCascade> cascades = parser.findCascades(3);
		assertNotNull(cascades);
		assertEquals(1, cascades.size());
		IfElseCascade cascade = cascades.get(0);
		assertTrue(cascade.bElse);
		assertEquals("LA450_383", cascade.tokenVarName);
		assertEquals("&& (synpred265_InternalN4JSParser())", cascade.condition);
		assertEquals(3, cascade.tokenToStateMap.size());
		String[] tokens = { "Solidus", "PercentSign", "PlusSign" };
		int[] values = { 282, 283, 284 };
		for (int i = 0; i < 3; i++) {
			assertTrue(cascade.tokenToStateMap.containsKey(tokens[i]));
			assertEquals(values[i], cascade.tokenToStateMap.get(tokens[i]).intValue());
		}
	}

	@Test
	public void testParsingWithoutCondition() {
		IfElseCascadeParser parser = new IfElseCascadeParser(snippet2);
		List<IfElseCascade> cascades = parser.findCascades(3);
		assertNotNull(cascades);
		assertEquals(1, cascades.size());
		IfElseCascade cascade = cascades.get(0);
		assertFalse(cascade.bElse);
		assertEquals("LA450_383", cascade.tokenVarName);
		assertEquals("", cascade.condition);
		assertEquals(3, cascade.tokenToStateMap.size());
		String[] tokens = { "Solidus", "PercentSign", "PlusSign" };
		int[] values = { 282, 283, 284 };
		for (int i = 0; i < 3; i++) {
			assertTrue(cascade.tokenToStateMap.containsKey(tokens[i]));
			assertEquals(values[i], cascade.tokenToStateMap.get(tokens[i]).intValue());
		}
	}

	@Test
	public void testParsingWithoutAndWithCondition() {
		IfElseCascadeParser parser = new IfElseCascadeParser(snippet3);
		List<IfElseCascade> cascades = parser.findCascades(3);
		assertNotNull(cascades);
		assertEquals(1, cascades.size());
		IfElseCascade cascade = cascades.get(0);
		assertTrue(cascade.bElse);
		assertEquals("LA450_383", cascade.tokenVarName);
		assertEquals("&& (synpred265_InternalN4JSParser())", cascade.condition);

		String[] tokens = { "PercentSign", "PlusSign" };
		int[] values = { 283, 284 };
		for (int i = 0; i < 2; i++) {
			assertTrue(cascade.tokenToStateMap.containsKey(tokens[i]));
			assertEquals(values[i], cascade.tokenToStateMap.get(tokens[i]).intValue());
		}
	}

	@Test
	public void testReplacement() {
		IfElseCascadeParser parser = new IfElseCascadeParser(snippet);
		List<IfElseCascade> cascades = parser.findCascades(3);
		assertNotNull(cascades);
		IfElseCascade cascade = cascades.get(0);
		Map<String, Integer> tokensToValues = new HashMap<>();
		tokensToValues.put("Solidus", 10);
		tokensToValues.put("PercentSign", 12);
		tokensToValues.put("PlusSign", 15);
		Replacement repl = cascade.getReplacements(tokensToValues, 1);
		assertEquals("final static int[] M_1 = { 282, -1, 283, -1, -1, 284};", repl.getMatrixDefinition());
		assertEquals(
				"else if ((LA450_383>=10 && LA450_383<=15 && (s=T2S.M_1[LA450_383-(10)])>=0) && (synpred265_InternalN4JSParser())) { /* 3 cases */ }",
				repl.getStatement());
	}

	/**
	 * This demos and tests the generated logic.
	 */
	@Test
	public void testLogic() {
		int[] matrix = { 30, -1, 50 };
		int[] expected = { -1, 30, -1, 50, 66 };
		for (int token = 2; token <= 6; token++) {
			int s = -1;
			if ((token >= 3 && token <= 5 && (s = matrix[token - 3]) >= 0)) {
				// ignore
			} else if (token == 6) {
				s = 66;
			}
			assertEquals(expected[token - 2], s);
		}

	}
}
