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

import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.junit.Test

class TypeAliasParserTest extends AbstractParserTest {

	@Test
	def void testSimple() {
		val script = '''
			type A = string;
		'''.parseESSuccessfully;

		val aliasDecl = script.scriptElements.get(0) as N4TypeAliasDeclaration;
		assertNotNull(aliasDecl);
		assertEquals("A", aliasDecl.name);
	}

	@Test
	def void testExported() {
		val script = '''
			export type A = string;
		'''.parseESSuccessfully;

		val exportDecl = script.scriptElements.get(0) as ExportDeclaration;
		val aliasDecl = exportDecl.exportedElement as N4TypeAliasDeclaration;
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.exported);
		assertEquals("A", aliasDecl.name);
		assertEquals("A", aliasDecl.exportedName);
		assertEquals(#[], aliasDecl.declaredModifiers);
	}

	@Test
	def void testExportedPublic() {
		val script = '''
			export public type A = string;
		'''.parseESSuccessfully;

		val exportDecl = script.scriptElements.get(0) as ExportDeclaration;
		val aliasDecl = exportDecl.exportedElement as N4TypeAliasDeclaration;
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.exported);
		assertEquals("A", aliasDecl.name);
		assertEquals("A", aliasDecl.exportedName);
		assertEquals(#[ N4Modifier.PUBLIC ], aliasDecl.declaredModifiers);
	}

	@Test
	def void testExportedPublicInternal() {
		val script = '''
			@Internal
			export public type A = string;
		'''.parseESSuccessfully;

		val exportDecl = script.scriptElements.get(0) as ExportDeclaration;
		val aliasDecl = exportDecl.exportedElement as N4TypeAliasDeclaration;
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.exported);
		assertEquals("A", aliasDecl.name);
		assertEquals("A", aliasDecl.exportedName);
		assertEquals(#[ N4Modifier.PUBLIC ], aliasDecl.declaredModifiers);
		assertTrue(AnnotationDefinition.INTERNAL.hasAnnotation(aliasDecl));
	}

	@Test
	def void testExportedDefault() {
		val script = '''
			export default type A = string;
		'''.parseESSuccessfully;

		val exportDecl = script.scriptElements.get(0) as ExportDeclaration;
		val aliasDecl = exportDecl.exportedElement as N4TypeAliasDeclaration;
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.exported);
		assertEquals("A", aliasDecl.name);
		assertEquals("default", aliasDecl.exportedName);
		assertEquals(#[], aliasDecl.declaredModifiers);
	}

	@Test
	def void testVarIsStillAVar() {
		val script = '''
			class C {}
			var type = C;
		'''.parseESSuccessfully;

		val firstElem = script.scriptElements.get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		val varStmnt = firstElem as VariableStatement;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.VAR, varStmnt.varStmtKeyword);

		val varDecl = varStmnt.varDeclsOrBindings.get(0) as VariableDeclaration;
		assertNotNull(varDecl);
		assertEquals("type", varDecl.name);
	}

	@Test
	def void testLetIsStillALet() {
		val script = '''
			class C {}
			let type = C;
		'''.parseESSuccessfully;

		val firstElem = script.scriptElements.get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		val varStmnt = firstElem as VariableStatement;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.LET, varStmnt.varStmtKeyword);

		val varDecl = varStmnt.varDeclsOrBindings.get(0) as VariableDeclaration;
		assertNotNull(varDecl);
		assertEquals("type", varDecl.name);
	}

	@Test
	def void testConstIsStillAConst() {
		val script = '''
			class C {}
			const type = C;
		'''.parseESSuccessfully;

		val firstElem = script.scriptElements.get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		val varStmnt = firstElem as VariableStatement;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.CONST, varStmnt.varStmtKeyword);

		val varDecl = varStmnt.varDeclsOrBindings.get(0) as VariableDeclaration;
		assertNotNull(varDecl);
		assertEquals("type", varDecl.name);
	}

	@Test
	def void testConstIsStillAConstExported() {
		val script = '''
			class C {}
			export public const type = C;
		'''.parseESSuccessfully;

		val exportDecl = script.scriptElements.get(1) as ExportDeclaration;
		val exportedElem = exportDecl.exportedElement;
		assertFalse(exportedElem instanceof N4TypeAliasDeclaration);
		assertTrue(exportedElem instanceof VariableStatement);

		val varStmnt = exportedElem as VariableStatement;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.CONST, varStmnt.varStmtKeyword);

		val varDecl = varStmnt.varDeclsOrBindings.get(0) as VariableDeclaration;
		assertNotNull(varDecl);
		assertEquals("type", varDecl.name);
	}
}
