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
package org.eclipse.n4js.tests.typesbuilder

import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class ClassDeclarationsTypeBuilderTest extends AbstractParserTest {

	@Test
	def void testClassDeclarations3() {
		val script = '''
			public class C {
				p: C;
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val cdecl = script.scriptElements.get(0) as N4ClassDeclaration
		assertEquals("C", cdecl.getName)

		val fieldDecl = cdecl.getOwnedMembers.get(0) as N4FieldDeclaration
		assertEquals("p", fieldDecl.getName);

		val typeOfP = fieldDecl.declaredTypeRefInAST as ParameterizedTypeRef
		assertFalse(typeOfP.eIsProxy)

		assertEquals("C", typeOfP.declaredType.name)
	}

	@Test
	def void testClassDeclarationsWithSructuralTyping() {
		val script = '''
			public class ~C {}
			public class D {}
		'''.parse
		doTestClassDeclarationsWithSructuralTyping(script);
	}

	@Test
	def void testClassDeclarationsWithSructuralTypingAnnotated() {
		val script = '''
			@Deprectated public class ~C {}
			@Deprectated public class D {}
		'''.parse
		doTestClassDeclarationsWithSructuralTyping(script);
	}

	def doTestClassDeclarationsWithSructuralTyping(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val cdecl = script.scriptElements.get(0) as N4ClassDeclaration
		assertEquals("C", cdecl.getName)
		val c = cdecl.definedType as TClass;
		assertEquals("C", c.getName)
		assertEquals(TypingStrategy.STRUCTURAL, c.typingStrategy)

		val ddecl = script.scriptElements.get(1) as N4ClassDeclaration
		assertEquals("D", ddecl.getName)
		val d = ddecl.definedType as TClass;
		assertEquals("D", d.getName)
		assertEquals(TypingStrategy.DEFAULT, d.typingStrategy)
	}
}
