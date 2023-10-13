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

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_21_ClassDeclarationsWithStructuralTypingTest extends AbstractParserTest {

	@Test
	public void testDefinitionSiteStructuralTypeWithClasses() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				class ~D {}
				""");
		doTestDefinitionSiteStructuralTypeWithClasses(script);
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithClassesAnnotated() throws Exception {
		Script script = parseHelper.parse("""
				@Deprectated class C {}
				@Deprectated class ~D {}
				""");
		doTestDefinitionSiteStructuralTypeWithClasses(script);
	}

	void doTestDefinitionSiteStructuralTypeWithClasses(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());
		assertTrue(script.getScriptElements().get(0) instanceof N4ClassDeclaration);

		N4ClassDeclaration C = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4ClassDeclaration D = (N4ClassDeclaration) script.getScriptElements().get(1);

		assertEquals(TypingStrategy.DEFAULT, C.getTypingStrategy());
		assertEquals(TypingStrategy.STRUCTURAL, D.getTypingStrategy());
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithClassesExported() throws Exception {
		Script script = parseHelper.parse("""
				export class C {}
				export class ~D {}
				""");
		doTestDefinitionSiteStructuralTypeWithClassesExport(script);
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithClassesAnnotatedExported() throws Exception {
		Script script = parseHelper.parse("""
				export @Deprecated class C {}
				export @Deprecated class ~D {}
				""");
		doTestDefinitionSiteStructuralTypeWithClassesExport(script);
	}

	void doTestDefinitionSiteStructuralTypeWithClassesExport(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());

		ExportDeclaration exp1 = (ExportDeclaration) script.getScriptElements().get(0);
		ExportDeclaration exp2 = (ExportDeclaration) script.getScriptElements().get(1);
		assertTrue("Expected Interface, was " + exp1.getExportedElement().getClass().getSimpleName(),
				exp1.getExportedElement() instanceof N4ClassDeclaration);
		assertTrue("Expected Interface, was " + exp2.getExportedElement().getClass().getSimpleName(),
				exp2.getExportedElement() instanceof N4ClassDeclaration);

		N4ClassDeclaration C = (N4ClassDeclaration) exp1.getExportedElement();
		N4ClassDeclaration D = (N4ClassDeclaration) exp2.getExportedElement();

		assertEquals(TypingStrategy.DEFAULT, C.getTypingStrategy());
		assertEquals(TypingStrategy.STRUCTURAL, D.getTypingStrategy());
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithClasses_NoStructuralField() throws Exception {
		Script script = parseHelper.parse("""
				public class ~~D {}
				""");
		assertNotNull(script);
		assertFalse("At definition site, structural field typing must not be possible",
				script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testUseSiteStructuralTypeWithClasses() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					public fNominal(p: C): void
					public fStructual(p: ~C): void
					public fStucturalField(p: ~~C): void
				}
				""");
		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration C = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertEquals(TypingStrategy.DEFAULT, C.getTypingStrategy());
		assertTypingStrategyOfFPar(TypingStrategy.NOMINAL, (N4MethodDeclaration) C.getOwnedMembers().get(0));
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, (N4MethodDeclaration) C.getOwnedMembers().get(1));
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL_FIELDS, (N4MethodDeclaration) C.getOwnedMembers().get(2));

	}

	public void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, N4MethodDeclaration method)
			throws Exception {
		ParameterizedTypeRef ptrOfFPar = (ParameterizedTypeRef) method.getFpars().get(0).getDeclaredTypeRefInAST();
		assertEquals(
				"Expected " + expectedStrategy.getName() + " but was " + ptrOfFPar.getTypingStrategy().getName() + ": ",
				expectedStrategy, ptrOfFPar.getTypingStrategy());
	}

	/*
	 * @see ES_11_04_UnaryOperatorsEsprimaTest
	 */
	@Test
	public void testBitwiseNot() throws Exception {

		// simple
		Script script = parseESSuccessfully("~x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INV, unary.getOp());
		IdentifierRef x = (IdentifierRef) unary.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testBitwiseNotDouble() throws Exception {

		// double
		Script script = parseESSuccessfully("~~x");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		UnaryExpression unary = (UnaryExpression) statement.getExpression();
		assertEquals(UnaryOperator.INV, unary.getOp());
		UnaryExpression nested = (UnaryExpression) unary.getExpression();
		assertEquals(UnaryOperator.INV, nested.getOp());
		IdentifierRef x = (IdentifierRef) nested.getExpression();
		assertEquals("x", getText(x));
	}

	@Test
	public void testBitwiseNotAndStructural() throws Exception {
		parseESSuccessfully("""
					class C{};
					var c: ~~C, i = ~~1, i = ~~~1;
				""");
	}

}
