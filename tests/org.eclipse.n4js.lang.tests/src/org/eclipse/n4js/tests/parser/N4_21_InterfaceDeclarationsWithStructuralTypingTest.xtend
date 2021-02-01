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

import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_21_InterfaceDeclarationsWithStructuralTypingTest extends AbstractParserTest {

	@Test
	def void testDefinitionSiteStructuralTypeWithInterfaces() {
		val script = '''
			interface I {}
			interface ~J {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithInterfaces(script)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithInterfacesAnnotated() {
		val script = '''
			@Deprecated interface I {}
			@Deprecated	interface ~J {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithInterfaces(script)
	}

	def doTestDefinitionSiteStructuralTypeWithInterfaces(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);
		assertTrue("Expected Interface, was " + script.scriptElements.get(0).class.simpleName,
			script.scriptElements.get(0) instanceof N4InterfaceDeclaration)
		assertTrue("Expected Interface, was " + script.scriptElements.get(1).class.simpleName,
			script.scriptElements.get(1) instanceof N4InterfaceDeclaration)

		val I = script.scriptElements.get(0) as N4InterfaceDeclaration
		val J = script.scriptElements.get(1) as N4InterfaceDeclaration

		assertEquals(TypingStrategy.DEFAULT, I.typingStrategy)
		assertEquals(TypingStrategy.STRUCTURAL, J.typingStrategy)

	}

	@Test
	def void testDefinitionSiteStructuralTypeWithInterfacesExport() {
		val script = '''
			export interface I {}
			export interface ~J {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithInterfacesExport(script);
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithInterfacesAnnotatedExport() {
		val script = '''
			export @Deprecated interface I {}
			export @Deprecated interface ~J {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithInterfacesExport(script);
	}

	def doTestDefinitionSiteStructuralTypeWithInterfacesExport(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);
		val exp1 = script.scriptElements.get(0) as ExportDeclaration
		val exp2 = script.scriptElements.get(1) as ExportDeclaration
		assertTrue("Expected Interface, was " + exp1.exportedElement.class.simpleName,
			exp1.exportedElement instanceof N4InterfaceDeclaration)
		assertTrue("Expected Interface, was " + exp2.exportedElement.class.simpleName,
			exp2.exportedElement instanceof N4InterfaceDeclaration)

		val I = exp1.exportedElement as N4InterfaceDeclaration
		val J = exp2.exportedElement as N4InterfaceDeclaration

		assertEquals(TypingStrategy.DEFAULT, I.typingStrategy)
		assertEquals(TypingStrategy.STRUCTURAL, J.typingStrategy)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithInterfaces_NoStructuralField() {
		val script = '''
			public interface ~~I {}
		'''.parse;
		assertNotNull(script);
		assertFalse("At definition site, structural field typing must not be possible", script.eResource.errors.empty)
	}

	@Test
	def void testUseSiteStructuralTypeWithInterfaces() {
		val script = '''
			public interface I {}
			public class C {
				public fNominal(p: I): void
				public fStructual(p: ~I): void
				public fStucturalField(p: ~~I): void
			}
		'''.parse;
		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val C = script.scriptElements.get(1) as N4ClassDeclaration
		assertEquals(TypingStrategy.DEFAULT, C.typingStrategy)
		assertTypingStrategyOfFPar(TypingStrategy.NOMINAL, C.ownedMembers.get(0) as N4MethodDeclaration)
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, C.ownedMembers.get(1) as N4MethodDeclaration)
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL_FIELDS, C.ownedMembers.get(2) as N4MethodDeclaration)
	}

	def void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, N4MethodDeclaration method) {
		val ptrOfFPar = method.fpars.head.declaredTypeRefInAST as ParameterizedTypeRef
		assertEquals("Expected " + expectedStrategy.getName + " but was " + ptrOfFPar.typingStrategy?.getName + ": ",
			expectedStrategy, ptrOfFPar.typingStrategy);
	}

}
