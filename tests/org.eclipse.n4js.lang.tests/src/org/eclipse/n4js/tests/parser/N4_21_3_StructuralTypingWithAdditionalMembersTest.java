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

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_21_3_StructuralTypingWithAdditionalMembersTest extends AbstractStructuralTypingTest {

	@Test
	public void testUseSiteWithAdditionalMember() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				class D {
					x: ~C with { s: string; };
					f(p: ~C with { s: string; }): void {}
				}
				function f(p: ~C with { s: string; }) {}
				""");

		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration D = (N4ClassDeclaration) script.getScriptElements().get(1);
		assertEquals(TypingStrategy.DEFAULT, D.getTypingStrategy());

		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "string")),
				((N4FieldDeclaration) D.getOwnedMembers().get(0)).getDeclaredTypeRefInAST());
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "string")),
				((N4MethodDeclaration) D.getOwnedMembers().get(1)).getFpars().get(0).getDeclaredTypeRefInAST());
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "string")),
				((FunctionDeclaration) script.getScriptElements().get(2)).getFpars().get(0).getDeclaredTypeRefInAST());
	}

	@Test
	public void testUseSiteWithAdditionalMemberWOTypes() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				class D {
					x: ~C with { s; };
					f(p: ~C with { s; }): void {}
				}
				function f(p: ~C with { s; }) {}
				""");

		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration D = (N4ClassDeclaration) script.getScriptElements().get(1);
		assertEquals(TypingStrategy.DEFAULT, D.getTypingStrategy());

		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "any")),
				((N4FieldDeclaration) D.getOwnedMembers().get(0)).getDeclaredTypeRefInAST());
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "any")),
				((N4MethodDeclaration) D.getOwnedMembers().get(1)).getFpars().get(0).getDeclaredTypeRefInAST());
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "any")),
				((FunctionDeclaration) script.getScriptElements().get(2)).getFpars().get(0).getDeclaredTypeRefInAST());
	}

	static String additionalMemberScriptSrc = """
				class C {}
				function f(p: ~C with {
					s: string;
					n;
					f1(string)
					f2(): string
					f3(boolean, C): number
					get y(): C
					get z()
					set a(boolean)
				}) {}
			""";

	@Test
	public void testUseSiteWithDifferentAdditionalMemberSetUpCheck() throws Exception {
		Script script = parseHelper.parse(additionalMemberScriptSrc);
		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(script);
		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		N4ClassDeclaration D = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertEquals(TypingStrategy.DEFAULT, D.getTypingStrategy());
	}

	TMember setupAdditionalMember(int index) throws Exception {
		Script script = parseHelper.parse(additionalMemberScriptSrc);
		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(script);
		ParameterizedTypeRefStructural structTypeRef = (ParameterizedTypeRefStructural) ((FunctionDeclaration) script
				.getScriptElements().get(1)).getFpars().get(0).getDeclaredTypeRefInAST();
		return structTypeRef.getStructuralMembers().get(index);
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberFieldWithType() throws Exception {
		assertField("string", "s", setupAdditionalMember(0));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberFieldWOType() throws Exception {
		assertField("any", "n", setupAdditionalMember(1));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberMethodNoParam() throws Exception {
		assertMethod("void", List.of("string"), "f1", setupAdditionalMember(2));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberMethodVoid() throws Exception {
		assertMethod("string", List.of(), "f2", setupAdditionalMember(3));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberMethodParams() throws Exception {
		assertMethod("number", List.of("boolean", "C"), "f3", setupAdditionalMember(4));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberGetteWithType() throws Exception {
		assertGetter("C", "y", setupAdditionalMember(5));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberGetterWOType() throws Exception {
		assertGetter("any", "z", setupAdditionalMember(6));
	}

	@Test
	public void testUseSiteWithDifferentAdditionalMemberSetter() throws Exception {
		assertSetter("boolean", "a", setupAdditionalMember(7));
	}

}
