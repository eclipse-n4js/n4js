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
package org.eclipse.n4js.tests.parser

import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_04_11_EnumsTest extends AbstractParserTest{

	@Test
	def void testEmptyDeclarations() {
		val script = parseHelper.parse('''
			enum E{  } // cannot be empty
		''');

		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		assertTrue(script.scriptElements.get(0) instanceof N4EnumDeclaration)
	}

	@Test
	def void testCorrectDeclarations() {
		val script = parseHelper.parse('''
			enum E{ LITERAL } // cannot be empty
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		assertTrue(script.scriptElements.get(0) instanceof N4EnumDeclaration)
	}


	@Test
	def void testEnumExample() {
		val script = parseHelper.parse('''
			enum Color {
				RED, GREEN, BLUE
			}

			var c: Color = Color.RED;
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertTrue(script.scriptElements.get(0) instanceof N4EnumDeclaration)
		val enumDecl = script.scriptElements.get(0) as N4EnumDeclaration
		assertEquals(3, enumDecl.literals.size);
		assertEquals("RED", enumDecl.literals.get(0).name)
		assertEquals("GREEN", enumDecl.literals.get(1).name)
		assertEquals("BLUE", enumDecl.literals.get(2).name)

	}



}
