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

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.junit.Test

class N4_03_AccessModifiersTest extends AbstractParserTest {

	@Test
	def void testTypeAccessModifiers() {
		val script = '''
			class								Cprv {}
			export class						Cdef {}
			export project class				Cpro {}
			export @Internal public class		Cpub {}
			export public class 				Cep {}
			interface 							Iprv {}
			export interface 					Idef {}
			export project interface 			Ipro {}
			export @Internal public interface	Ipub {}
			export public interface				Iep {}
			interface 							Rprv {}
			export interface 					Rdef {}
			export project interface 			Rpro {}
			export @Internal public interface	Rpub {}
			export public interface				Rep {}
			enum 								Eprv {L}
			export enum 						Edef {L}
			export project enum 				Epro {L}
			export @Internal public enum		Epub {L}
			export public enum					Eep {L}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		var expectedMT = #[N4ClassDeclaration, N4InterfaceDeclaration, N4InterfaceDeclaration, N4EnumDeclaration]
		val expectedAM = #[TypeAccessModifier.PRIVATE, TypeAccessModifier.PROJECT, TypeAccessModifier.PROJECT,
			TypeAccessModifier.PUBLIC_INTERNAL, TypeAccessModifier.PUBLIC]
		var expectedAnno = #[null, null, null, "Internal", null]

		assertEquals(expectedMT.size * expectedAM.size, script.scriptElements.size)

		var annoIndex = -1;

		var iter = script.scriptElements.iterator
		for (emt : expectedMT) {
			for (eam : expectedAM) {
				annoIndex = (annoIndex + 1) % expectedAM.size
				var EObject stmt = iter.next
				stmt = if(stmt instanceof ExportDeclaration) stmt.exportedElement else stmt
				assertTrue(emt.isAssignableFrom(stmt.class))
				assertTrue(stmt instanceof N4TypeDeclaration)
				val decl = stmt as N4TypeDeclaration
				assertEquals("expected modifier for " + decl.name, eam, decl.definedType.typeAccessModifier);
				if (expectedAnno.get(annoIndex) === null) {
					assertTrue(decl.annotations.empty)
				} else {
					assertEquals(1, decl.annotations.size)
					assertEquals(expectedAnno.get(annoIndex), decl.annotations.get(0).name)
				}
			}
		}
	}

	@Test
	def void testMemberAccessModifiers() {
		val script = '''
			public class C {
				f0;
				private f1;
				project f2;
				@Internal protected f4;
				protected f5;
				@Internal public f6;
				public f7;
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val expectedAM = #[MemberAccessModifier.PROJECT, MemberAccessModifier.PRIVATE, MemberAccessModifier.PROJECT,
			MemberAccessModifier.PROTECTED_INTERNAL, MemberAccessModifier.PROTECTED,
			MemberAccessModifier.PUBLIC_INTERNAL, MemberAccessModifier.PUBLIC]
		var expectedAnno = #[null, null, null, "Internal", null, "Internal", null]

		val cdecl = script.scriptElements.get(0) as N4ClassDeclaration
		assertEquals(expectedAM.size, cdecl.ownedMembers.size)

		var annoIndex = -1;
		var iter = cdecl.ownedMembers.iterator
		for (eam : expectedAM) {
			annoIndex = (annoIndex + 1) % expectedAM.size
			val member = iter.next
			assertTrue(member instanceof N4FieldDeclaration)

			assertEquals(eam, member.definedTypeElement.memberAccessModifier);
			if (expectedAnno.get(annoIndex) === null) {
				assertTrue(member.annotations.empty)
			} else {
				assertEquals(1, member.annotations.size)
				assertEquals(expectedAnno.get(annoIndex), member.annotations.get(0).name)
			}
		}

	}

	@Test
	def void testVarAccessModifiers_01() {
		val script = '''
			export @Internal public var x = ""
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PUBLIC))
	}

	@Test
	def void testVarAccessModifiers_02() {
		val script = '''
			export project var x = ""
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PROJECT))
	}

	@Test
	def void testVarAccessModifiers_03() {
		val script = '''
			export @Internal public const x = ""
		'''.parseESSuccessfully

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PUBLIC))
	}

	@Test
	def void testVarAccessModifiers_04() {
		val script = '''
			export project const x = ""
		'''.parseESSuccessfully

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PROJECT))
	}

	@Test
	def void testVarAccessModifiers_05() {
		val script = '''
			export @Internal public var x = ""
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PUBLIC))
	}

	@Test
	def void testVarAccessModifiers_06() {
		val script = '''
			export project var x = ""
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val varStatement = (script.scriptElements.head as ExportDeclaration).exportedElement as ExportableVariableStatement
		assertTrue(varStatement.declaredModifiers.contains(N4Modifier.PROJECT))
	}

	@Test
	def void testFunctionAccessModifiers_01() {
		val script = '''
			export function x() {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val functionDecl = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		assertTrue(functionDecl.declaredModifiers.empty)
		val accessModifier = functionDecl.definedType.typeAccessModifier
		assertEquals(TypeAccessModifier.PROJECT, accessModifier)
	}

	@Test
	def void testFunctionAccessModifiers_02() {
		val script = '''
			export @Internal public function x() {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val functionDecl = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		assertTrue(functionDecl.declaredModifiers.contains(N4Modifier.PUBLIC))
		val accessModifier = functionDecl.definedType.typeAccessModifier
		assertEquals(TypeAccessModifier.PUBLIC_INTERNAL, accessModifier)
	}

	@Test
	def void testFunctionAccessModifiers_03() {
		val script = '''
			export public function x() {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val functionDecl = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		assertTrue(functionDecl.declaredModifiers.contains(N4Modifier.PUBLIC))
		val accessModifier = functionDecl.definedType.typeAccessModifier
		assertEquals(TypeAccessModifier.PUBLIC, accessModifier)
	}

	@Test
	def void testFunctionAccessModifiers_04() {
		val script = '''
			export @Internal public function x() {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val functionDecl = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		assertTrue(functionDecl.declaredModifiers.contains(N4Modifier.PUBLIC))
		val accessModifier = functionDecl.definedType.typeAccessModifier
		assertEquals(TypeAccessModifier.PUBLIC_INTERNAL, accessModifier)
	}

	@Test
	def void testFunctionAccessModifiers_05() {
		val script = '''
			export project function x() {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		val functionDecl = (script.scriptElements.head as ExportDeclaration).exportedElement as FunctionDeclaration
		assertTrue(functionDecl.declaredModifiers.contains(N4Modifier.PROJECT))
		val accessModifier = functionDecl.definedType.typeAccessModifier
		assertEquals(TypeAccessModifier.PROJECT, accessModifier)
	}
}
