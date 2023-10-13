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
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_21_InterfaceDeclarationsWithStructuralTypingTest extends AbstractParserTest {

	@Test
	public void testDefinitionSiteStructuralTypeWithInterfaces() throws Exception {
		Script script = parseHelper.parse("""
				interface I {}
				interface ~J {}
				""");
		doTestDefinitionSiteStructuralTypeWithInterfaces(script);
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithInterfacesAnnotated() throws Exception {
		Script script = parseHelper.parse("""
				@Deprecated interface I {}
				@Deprecated	interface ~J {}
				""");
		doTestDefinitionSiteStructuralTypeWithInterfaces(script);
	}

	void doTestDefinitionSiteStructuralTypeWithInterfaces(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());
		assertTrue("Expected Interface, was " + script.getScriptElements().get(0).getClass().getSimpleName(),
				script.getScriptElements().get(0) instanceof N4InterfaceDeclaration);
		assertTrue("Expected Interface, was " + script.getScriptElements().get(1).getClass().getSimpleName(),
				script.getScriptElements().get(1) instanceof N4InterfaceDeclaration);

		N4InterfaceDeclaration I = (N4InterfaceDeclaration) script.getScriptElements().get(0);
		N4InterfaceDeclaration J = (N4InterfaceDeclaration) script.getScriptElements().get(1);

		assertEquals(TypingStrategy.DEFAULT, I.getTypingStrategy());
		assertEquals(TypingStrategy.STRUCTURAL, J.getTypingStrategy());

	}

	@Test
	public void testDefinitionSiteStructuralTypeWithInterfacesExport() throws Exception {
		Script script = parseHelper.parse("""
				export interface I {}
				export interface ~J {}
				""");
		doTestDefinitionSiteStructuralTypeWithInterfacesExport(script);
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithInterfacesAnnotatedExport() throws Exception {
		Script script = parseHelper.parse("""
				export @Deprecated interface I {}
				export @Deprecated interface ~J {}
				""");
		doTestDefinitionSiteStructuralTypeWithInterfacesExport(script);
	}

	void doTestDefinitionSiteStructuralTypeWithInterfacesExport(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());
		ExportDeclaration exp1 = (ExportDeclaration) script.getScriptElements().get(0);
		ExportDeclaration exp2 = (ExportDeclaration) script.getScriptElements().get(1);
		assertTrue("Expected Interface, was " + exp1.getExportedElement().getClass().getSimpleName(),
				exp1.getExportedElement() instanceof N4InterfaceDeclaration);
		assertTrue("Expected Interface, was " + exp2.getExportedElement().getClass().getSimpleName(),
				exp2.getExportedElement() instanceof N4InterfaceDeclaration);

		N4InterfaceDeclaration I = (N4InterfaceDeclaration) exp1.getExportedElement();
		N4InterfaceDeclaration J = (N4InterfaceDeclaration) exp2.getExportedElement();

		assertEquals(TypingStrategy.DEFAULT, I.getTypingStrategy());
		assertEquals(TypingStrategy.STRUCTURAL, J.getTypingStrategy());
	}

	@Test
	public void testDefinitionSiteStructuralTypeWithInterfaces_NoStructuralField() throws Exception {
		Script script = parseHelper.parse("""
				public interface ~~I {}
				""");
		assertNotNull(script);
		assertFalse("At definition site, structural field typing must not be possible",
				script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testUseSiteStructuralTypeWithInterfaces() throws Exception {
		Script script = parseHelper.parse("""
				public interface I {}
				public class C {
					public fNominal(p: I): void
					public fStructual(p: ~I): void
					public fStucturalField(p: ~~I): void
				}
				""");
		assertNotNull(script);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration C = (N4ClassDeclaration) script.getScriptElements().get(1);
		assertEquals(TypingStrategy.DEFAULT, C.getTypingStrategy());
		assertTypingStrategyOfFPar(TypingStrategy.NOMINAL, (N4MethodDeclaration) C.getOwnedMembers().get(0));
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, (N4MethodDeclaration) C.getOwnedMembers().get(1));
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL_FIELDS, (N4MethodDeclaration) C.getOwnedMembers().get(2));
	}

	public void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, N4MethodDeclaration method) {
		ParameterizedTypeRef ptrOfFPar = (ParameterizedTypeRef) method.getFpars().get(0).getDeclaredTypeRefInAST();
		assertEquals(
				"Expected " + expectedStrategy.getName() + " but was " + ptrOfFPar.getTypingStrategy().getName() + ": ",
				expectedStrategy, ptrOfFPar.getTypingStrategy());
	}

}
