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

import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_21_3_StructuralTypingWithAdditionalMembersTest extends AbstractStructuralTypingTest {

	@Test
	def void testUseSiteWithAdditionalMember() {
		val script = '''
			class C {}
			class D {
				x: ~C with { s: string; };
				f(p: ~C with { s: string; }): void {}
			}
			function f(p: ~C with { s: string; }) {}
		'''.parse

		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val D = script.scriptElements.get(1) as N4ClassDeclaration
		assertEquals(TypingStrategy.DEFAULT, D.typingStrategy)

		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "string"],
			(D.ownedMembers.get(0) as N4FieldDeclaration).declaredTypeRefInAST);
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "string"],
			(D.ownedMembers.get(1) as N4MethodDeclaration).fpars.get(0).declaredTypeRefInAST);
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "string"],
			(script.scriptElements.get(2) as FunctionDeclaration).fpars.get(0).declaredTypeRefInAST);
	}

	@Test
	def void testUseSiteWithAdditionalMemberWOTypes() {
		val script = '''
			class C {}
			class D {
				x: ~C with { s; };
				f(p: ~C with { s; }): void {}
			}
			function f(p: ~C with { s; }) {}
		'''.parse

		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val D = script.scriptElements.get(1) as N4ClassDeclaration
		assertEquals(TypingStrategy.DEFAULT, D.typingStrategy)

		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "any"],
			(D.ownedMembers.get(0) as N4FieldDeclaration).declaredTypeRefInAST);
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "any"],
			(D.ownedMembers.get(1) as N4MethodDeclaration).fpars.get(0).declaredTypeRefInAST);
		assertAdditionalFieldsPTR(TypingStrategy.STRUCTURAL, #["s" -> "any"],
			(script.scriptElements.get(2) as FunctionDeclaration).fpars.get(0).declaredTypeRefInAST);
	}



	static val additionalMemberScriptSrc = '''
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
		''';


	@Test
	def void testUseSiteWithDifferentAdditionalMemberSetUpCheck() {
		val script = additionalMemberScriptSrc.parse
		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		EcoreUtil.resolveAll(script)
		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val D = script.scriptElements.get(0) as N4ClassDeclaration
		assertEquals(TypingStrategy.DEFAULT, D.typingStrategy)
	}

	def TMember setupAdditionalMember(int index) {
		val script = additionalMemberScriptSrc.parse
		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		EcoreUtil.resolveAll(script)
		val structTypeRef = (script.scriptElements.get(1) as FunctionDeclaration).fpars.get(0).declaredTypeRefInAST as ParameterizedTypeRefStructural
		return structTypeRef.structuralMembers.get(index)
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberFieldWithType() {
		assertField("string", "s", setupAdditionalMember(0));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberFieldWOType() {
		assertField("any", "n", setupAdditionalMember(1));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberMethodNoParam() {
		assertMethod("void", #["string"], "f1", setupAdditionalMember(2));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberMethodVoid() {
		assertMethod("string", #[], "f2", setupAdditionalMember(3));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberMethodParams() {
		assertMethod("number", #["boolean", "C"], "f3", setupAdditionalMember(4));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberGetteWithType() {
		assertGetter("C", "y", setupAdditionalMember(5));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberGetterWOType() {
		assertGetter("any", "z", setupAdditionalMember(6));
	}

	@Test
	def void testUseSiteWithDifferentAdditionalMemberSetter() {
		assertSetter("boolean", "a", setupAdditionalMember(7));
	}



}
