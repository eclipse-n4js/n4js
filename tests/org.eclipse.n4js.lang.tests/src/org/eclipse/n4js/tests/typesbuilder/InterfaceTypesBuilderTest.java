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
package org.eclipse.n4js.tests.typesbuilder;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.junit.Test;

public class InterfaceTypesBuilderTest extends AbstractParserTest {

	@Test
	public void testFieldsInInterfaces() throws Exception {
		Script script = parseHelper.parse("""
				public interface I {
					f: string;
				}
				class C {}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4InterfaceDeclaration idecl = (N4InterfaceDeclaration) script.getScriptElements().get(0);
		assertEquals("I", idecl.getName());

		N4FieldDeclaration fdecl = (N4FieldDeclaration) idecl.getOwnedMembers().get(0);
		assertEquals("f", fdecl.getName());

		TInterface i = idecl.getDefinedTypeAsInterface();
		assertEquals("I", i.getName());

		TField f = (TField) i.getOwnedMembers().get(0);
		assertEquals("f", f.getName());
	}

	@Test
	public void testMembersImplicitAbstract() throws Exception {
		Script script = parseHelper.parse("""
				export public interface I {
					m(): string;
					get g(): string;
					set s(p: string);
				}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		ExportDeclaration expdecl = (ExportDeclaration) script.getScriptElements().get(0);
		N4InterfaceDeclaration idecl = (N4InterfaceDeclaration) expdecl.getExportedElement();
		assertEquals("I", idecl.getName());

		N4MethodDeclaration mdecl = (N4MethodDeclaration) idecl.getOwnedMembers().get(0);
		assertEquals("m", mdecl.getName());

		N4GetterDeclaration gdecl = (N4GetterDeclaration) idecl.getOwnedMembers().get(1);
		assertEquals("g", gdecl.getName());

		N4SetterDeclaration sdecl = (N4SetterDeclaration) idecl.getOwnedMembers().get(2);
		assertEquals("s", sdecl.getName());

		TInterface i = idecl.getDefinedTypeAsInterface();
		assertEquals("I", i.getName());

		TMethod m = (TMethod) i.getOwnedMembers().get(0);
		assertEquals("m", m.getName());

		TGetter g = (TGetter) i.getOwnedMembers().get(1);
		assertEquals("g", g.getName());

		TSetter s = (TSetter) i.getOwnedMembers().get(2);
		assertEquals("s", s.getName());

		assertFalse(mdecl.getDeclaredModifiers().contains(N4Modifier.ABSTRACT));
		assertFalse(gdecl.getDeclaredModifiers().contains(N4Modifier.ABSTRACT));
		assertFalse(sdecl.getDeclaredModifiers().contains(N4Modifier.ABSTRACT));

		assertTrue(mdecl.isAbstract());
		assertTrue(gdecl.isAbstract());
		assertTrue(sdecl.isAbstract());

		assertTrue(m.isAbstract());
		assertTrue(g.isAbstract());
		assertTrue(s.isAbstract());

		assertTrue(mdecl.getDeclaredModifiers().isEmpty());
		assertTrue(gdecl.getDeclaredModifiers().isEmpty());
		assertTrue(sdecl.getDeclaredModifiers().isEmpty());

		assertEquals(MemberAccessModifier.PUBLIC, m.getMemberAccessModifier());
		assertEquals(MemberAccessModifier.PUBLIC, g.getMemberAccessModifier());
		assertEquals(MemberAccessModifier.PUBLIC, s.getMemberAccessModifier());
	}

	@Test
	public void testInterfaceDeclarationsWithSructuralTyping() throws Exception {
		Script script = parseHelper.parse("""
				public interface ~I {}
				public interface J {}
				""");
		doTestInterfaceDeclarationsWithSructuralTyping(script);
	}

	@Test
	public void testInterfaceDeclarationsWithSructuralTypingAnnotated() throws Exception {
		Script script = parseHelper.parse("""
				@Deprectated interface ~I {}
				@Deprectated interface J {}
				""");
		doTestInterfaceDeclarationsWithSructuralTyping(script);
	}

	void doTestInterfaceDeclarationsWithSructuralTyping(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4InterfaceDeclaration idecl = (N4InterfaceDeclaration) script.getScriptElements().get(0);
		assertEquals("I", idecl.getName());
		TInterface i = (TInterface) idecl.getDefinedType();
		assertEquals("I", i.getName());
		assertEquals(TypingStrategy.STRUCTURAL, i.getTypingStrategy());

		N4InterfaceDeclaration jdecl = (N4InterfaceDeclaration) script.getScriptElements().get(1);
		assertEquals("J", jdecl.getName());
		TInterface j = (TInterface) jdecl.getDefinedType();
		assertEquals("J", j.getName());
		assertEquals(TypingStrategy.DEFAULT, j.getTypingStrategy());
	}
}
