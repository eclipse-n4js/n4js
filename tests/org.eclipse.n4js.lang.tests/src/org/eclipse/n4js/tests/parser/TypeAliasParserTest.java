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

import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.junit.Test;

public class TypeAliasParserTest extends AbstractParserTest {

	@Test
	public void testSimple() {
		Script script = parseESSuccessfully("""
					type A = string;
				""");

		N4TypeAliasDeclaration aliasDecl = (N4TypeAliasDeclaration) script.getScriptElements().get(0);
		assertNotNull(aliasDecl);
		assertEquals("A", aliasDecl.getName());
	}

	@Test
	public void testExported() {
		Script script = parseESSuccessfully("""
					export type A = string;
				""");

		ExportDeclaration exportDecl = (ExportDeclaration) script.getScriptElements().get(0);
		N4TypeAliasDeclaration aliasDecl = (N4TypeAliasDeclaration) exportDecl.getExportedElement();
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.isDirectlyExported());
		assertEquals("A", aliasDecl.getName());
		assertEquals("A", aliasDecl.getDirectlyExportedName());
		assertEquals(Collections.emptyList(), aliasDecl.getDeclaredModifiers());
	}

	@Test
	public void testExportedPublic() {
		Script script = parseESSuccessfully("""
					export public type A = string;
				""");

		ExportDeclaration exportDecl = (ExportDeclaration) script.getScriptElements().get(0);
		N4TypeAliasDeclaration aliasDecl = (N4TypeAliasDeclaration) exportDecl.getExportedElement();
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.isDirectlyExported());
		assertEquals("A", aliasDecl.getName());
		assertEquals("A", aliasDecl.getDirectlyExportedName());
		assertEquals(List.of(N4Modifier.PUBLIC), aliasDecl.getDeclaredModifiers());
	}

	@Test
	public void testExportedPublicInternal() {
		Script script = parseESSuccessfully("""
					@Internal
					export public type A = string;
				""");

		ExportDeclaration exportDecl = (ExportDeclaration) script.getScriptElements().get(0);
		N4TypeAliasDeclaration aliasDecl = (N4TypeAliasDeclaration) exportDecl.getExportedElement();
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.isDirectlyExported());
		assertEquals("A", aliasDecl.getName());
		assertEquals("A", aliasDecl.getDirectlyExportedName());
		assertEquals(List.of(N4Modifier.PUBLIC), aliasDecl.getDeclaredModifiers());
		assertTrue(AnnotationDefinition.INTERNAL.hasAnnotation(aliasDecl));
	}

	@Test
	public void testExportedDefault() {
		Script script = parseESSuccessfully("""
					export default type A = string;
				""");

		ExportDeclaration exportDecl = (ExportDeclaration) script.getScriptElements().get(0);
		N4TypeAliasDeclaration aliasDecl = (N4TypeAliasDeclaration) exportDecl.getExportedElement();
		assertNotNull(aliasDecl);
		assertTrue(aliasDecl.isDirectlyExported());
		assertEquals("A", aliasDecl.getName());
		assertEquals("default", aliasDecl.getDirectlyExportedName());
		assertEquals(Collections.emptyList(), aliasDecl.getDeclaredModifiers());
	}

	@Test
	public void testVarIsStillAVar() {
		Script script = parseESSuccessfully("""
					class C {}
					var type = C;
				""");

		ScriptElement firstElem = script.getScriptElements().get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		VariableStatement varStmnt = (VariableStatement) firstElem;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.VAR, varStmnt.getVarStmtKeyword());

		VariableDeclaration varDecl = (VariableDeclaration) varStmnt.getVarDeclsOrBindings().get(0);
		assertNotNull(varDecl);
		assertEquals("type", varDecl.getName());
	}

	@Test
	public void testLetIsStillALet() {
		Script script = parseESSuccessfully("""
					class C {}
					let type = C;
				""");

		ScriptElement firstElem = script.getScriptElements().get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		VariableStatement varStmnt = (VariableStatement) firstElem;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.LET, varStmnt.getVarStmtKeyword());

		VariableDeclaration varDecl = (VariableDeclaration) varStmnt.getVarDeclsOrBindings().get(0);
		assertNotNull(varDecl);
		assertEquals("type", varDecl.getName());
	}

	@Test
	public void testConstIsStillAConst() {
		Script script = parseESSuccessfully("""
					class C {}
					const type = C;
				""");

		ScriptElement firstElem = script.getScriptElements().get(1);
		assertFalse(firstElem instanceof N4TypeAliasDeclaration);
		assertTrue(firstElem instanceof VariableStatement);

		VariableStatement varStmnt = (VariableStatement) firstElem;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.CONST, varStmnt.getVarStmtKeyword());

		VariableDeclaration varDecl = (VariableDeclaration) varStmnt.getVarDeclsOrBindings().get(0);
		assertNotNull(varDecl);
		assertEquals("type", varDecl.getName());
	}

	@Test
	public void testConstIsStillAConstExported() {
		Script script = parseESSuccessfully("""
					class C {}
					export public const type = C;
				""");

		ExportDeclaration exportDecl = (ExportDeclaration) script.getScriptElements().get(1);
		ExportableElement exportedElem = exportDecl.getExportedElement();
		assertFalse(exportedElem instanceof N4TypeAliasDeclaration);
		assertTrue(exportedElem instanceof VariableStatement);

		VariableStatement varStmnt = (VariableStatement) exportedElem;
		assertNotNull(varStmnt);
		assertSame(VariableStatementKeyword.CONST, varStmnt.getVarStmtKeyword());

		VariableDeclaration varDecl = (VariableDeclaration) varStmnt.getVarDeclsOrBindings().get(0);
		assertNotNull(varDecl);
		assertEquals("type", varDecl.getName());
	}
}
