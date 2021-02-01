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
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_21_ClassDeclarationsWithStructuralTypingTest extends AbstractParserTest {

	@Test
	def void testDefinitionSiteStructuralTypeWithClasses() {
		val script = '''
			class C {}
			class ~D {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithClasses(script)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithClassesAnnotated() {
		val script = '''
			@Deprectated class C {}
			@Deprectated class ~D {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithClasses(script)
	}

	def doTestDefinitionSiteStructuralTypeWithClasses(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);
		assertTrue(script.scriptElements.get(0) instanceof N4ClassDeclaration)

		val C = script.scriptElements.get(0) as N4ClassDeclaration
		val D = script.scriptElements.get(1) as N4ClassDeclaration

		assertEquals(TypingStrategy.DEFAULT, C.typingStrategy)
		assertEquals(TypingStrategy.STRUCTURAL, D.typingStrategy)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithClassesExported() {
		val script = '''
			export class C {}
			export class ~D {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithClassesExport(script);
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithClassesAnnotatedExported() {
		val script = '''
			export @Deprecated class C {}
			export @Deprecated class ~D {}
		'''.parse
		doTestDefinitionSiteStructuralTypeWithClassesExport(script);
	}

	def doTestDefinitionSiteStructuralTypeWithClassesExport(Script script) {
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);

		val exp1 = script.scriptElements.get(0) as ExportDeclaration
		val exp2 = script.scriptElements.get(1) as ExportDeclaration
		assertTrue("Expected Interface, was " + exp1.exportedElement.class.simpleName,
			exp1.exportedElement instanceof N4ClassDeclaration)
		assertTrue("Expected Interface, was " + exp2.exportedElement.class.simpleName,
			exp2.exportedElement instanceof N4ClassDeclaration)

		val C = exp1.exportedElement as N4ClassDeclaration
		val D = exp2.exportedElement as N4ClassDeclaration

		assertEquals(TypingStrategy.DEFAULT, C.typingStrategy)
		assertEquals(TypingStrategy.STRUCTURAL, D.typingStrategy)
	}

	@Test
	def void testDefinitionSiteStructuralTypeWithClasses_NoStructuralField() {
		val script = '''
			public class ~~D {}
		'''.parse;
		assertNotNull(script);
		assertFalse("At definition site, structural field typing must not be possible", script.eResource.errors.empty)
	}

	@Test
	def void testUseSiteStructuralTypeWithClasses() {
		val script = '''
			public class C {
				public fNominal(p: C): void
				public fStructual(p: ~C): void
				public fStucturalField(p: ~~C): void
			}
		'''.parse;
		assertNotNull(script);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)

		val C = script.scriptElements.get(0) as N4ClassDeclaration
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

	/*
	 * @see ES_11_04_UnaryOperatorsEsprimaTest
	 */
	@Test
	def void testBitwiseNot() {

		// simple
		val script = '~x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INV, unary.op)
		val x = unary.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testBitwiseNotDouble() {

		// double
		val script = '~~x'.parseESSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val unary = statement.expression as UnaryExpression
		assertEquals(UnaryOperator.INV, unary.op)
		val nested = unary.expression as UnaryExpression
		assertEquals(UnaryOperator.INV, nested.op)
		val x = nested.expression as IdentifierRef
		assertEquals('x', x.text)
	}

	@Test
	def void testBitwiseNotAndStructural() {
		'''
			class C{};
			var c: ~~C, i = ~~1, i = ~~~1;
		'''.parseESSuccessfully
	}

}
