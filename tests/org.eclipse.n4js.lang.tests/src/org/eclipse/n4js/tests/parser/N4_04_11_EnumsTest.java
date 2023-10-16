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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_04_11_EnumsTest extends AbstractParserTest {

	@Test
	public void testEmptyDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					enum E{  } // cannot be empty
				""");

		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		assertTrue(script.getScriptElements().get(0) instanceof N4EnumDeclaration);
	}

	@Test
	public void testCorrectDeclarations() throws Exception {
		Script script = parseHelper.parse("""
					enum E{ LITERAL } // cannot be empty
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		assertTrue(script.getScriptElements().get(0) instanceof N4EnumDeclaration);
	}

	@Test
	public void testEnumExample() throws Exception {
		Script script = parseHelper.parse("""
					enum Color {
						RED, GREEN, BLUE
					}

					var c: Color = Color.RED;
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertTrue(script.getScriptElements().get(0) instanceof N4EnumDeclaration);
		N4EnumDeclaration enumDecl = (N4EnumDeclaration) script.getScriptElements().get(0);
		assertEquals(3, enumDecl.getLiterals().size());
		assertEquals("RED", enumDecl.getLiterals().get(0).getName());
		assertEquals("GREEN", enumDecl.getLiterals().get(1).getName());
		assertEquals("BLUE", enumDecl.getLiterals().get(2).getName());

	}

}
