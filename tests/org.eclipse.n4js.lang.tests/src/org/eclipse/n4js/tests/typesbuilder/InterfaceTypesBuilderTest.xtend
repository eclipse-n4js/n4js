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

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

class InterfaceTypesBuilderTest extends AbstractParserTest {

	@Test
	def testFieldsInInterfaces() {
		val script = parseHelper.parse('''
			public interface I {
				f: string;
			}
			class C {}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val idecl = script.scriptElements.get(0) as N4InterfaceDeclaration
		assertEquals("I", idecl.getName)

		val fdecl = idecl.ownedMembers.get(0) as N4FieldDeclaration
		assertEquals("f", fdecl.getName)

		val i = idecl.getDefinedTypeAsInterface()
		assertEquals("I", i.name)

		val f = i.ownedMembers.get(0) as TField
		assertEquals("f", f.name)
	}

	@Test
	def testMembersImplicitAbstract() {
		val script = parseHelper.parse('''
			export public interface I {
				m(): string;
				get g(): string;
				set s(p: string);
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val expdecl = script.scriptElements.get(0) as ExportDeclaration
		val idecl = expdecl.exportedElement as N4InterfaceDeclaration
		assertEquals("I", idecl.getName)

		val mdecl = idecl.ownedMembers.get(0) as N4MethodDeclaration
		assertEquals("m", mdecl.getName)

		val gdecl = idecl.ownedMembers.get(1) as N4GetterDeclaration
		assertEquals("g", gdecl.getName)

		val sdecl = idecl.ownedMembers.get(2) as N4SetterDeclaration
		assertEquals("s", sdecl.getName)

		val i = idecl.definedTypeAsInterface
		assertEquals("I", i.name)

		val m = i.ownedMembers.get(0) as TMethod
		assertEquals("m", m.name)

		val g = i.ownedMembers.get(1) as TGetter
		assertEquals("g", g.name)

		val s = i.ownedMembers.get(2) as TSetter
		assertEquals("s", s.name)

		assertFalse(mdecl.declaredModifiers.contains(N4Modifier.ABSTRACT))
		assertFalse(gdecl.declaredModifiers.contains(N4Modifier.ABSTRACT))
		assertFalse(sdecl.declaredModifiers.contains(N4Modifier.ABSTRACT))

		assertTrue(mdecl.abstract)
		assertTrue(gdecl.abstract)
		assertTrue(sdecl.abstract)

		assertTrue(m.abstract);
		assertTrue(g.abstract);
		assertTrue(s.abstract);

		assertTrue(mdecl.declaredModifiers.empty)
		assertTrue(gdecl.declaredModifiers.empty)
		assertTrue(sdecl.declaredModifiers.empty)

		assertEquals(MemberAccessModifier.PUBLIC, m.memberAccessModifier);
		assertEquals(MemberAccessModifier.PUBLIC, g.memberAccessModifier);
		assertEquals(MemberAccessModifier.PUBLIC, s.memberAccessModifier);
	}

	@Test
	def void testInterfaceDeclarationsWithSructuralTyping() {
		val script = parseHelper.parse('''
			public interface ~I {}
			public interface J {}
		''');
		doTestInterfaceDeclarationsWithSructuralTyping(script);
	}

	@Test
	def void testInterfaceDeclarationsWithSructuralTypingAnnotated() {
		val script = parseHelper.parse('''
			@Deprectated interface ~I {}
			@Deprectated interface J {}
		''');
		doTestInterfaceDeclarationsWithSructuralTyping(script);
	}

	def doTestInterfaceDeclarationsWithSructuralTyping(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val idecl = script.scriptElements.get(0) as N4InterfaceDeclaration
		assertEquals("I", idecl.getName)
		val i = idecl.definedType as TInterface;
		assertEquals("I", i.getName)
		assertEquals(TypingStrategy.STRUCTURAL, i.typingStrategy)

		val jdecl = script.scriptElements.get(1) as N4InterfaceDeclaration
		assertEquals("J", jdecl.getName)
		val j = jdecl.definedType as TInterface;
		assertEquals("J", j.getName)
		assertEquals(TypingStrategy.DEFAULT, j.typingStrategy)
	}
}
