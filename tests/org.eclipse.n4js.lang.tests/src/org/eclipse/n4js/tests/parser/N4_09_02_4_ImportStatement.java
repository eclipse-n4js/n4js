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

import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class N4_09_02_4_ImportStatement extends AbstractParserTest {

	@Test
	public void testNamedImport() {
		Script script = parseESSuccessfully("import {X} from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamedImportSpecifier nis = (NamedImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertNull(nis.getAlias());
		assertFalse(nis.isDeclaredDynamic());
	}

	@Test
	public void testNamedImportDynamic() {
		Script script = parseESSuccessfully("import {X+} from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamedImportSpecifier nis = (NamedImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertNull(nis.getAlias());
		assertTrue(nis.isDeclaredDynamic());
	}

	@Test
	public void testNamedImportWithAlias() {
		Script script = parseESSuccessfully("import {X as Y} from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamedImportSpecifier nis = (NamedImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("Y", nis.getAlias());
		assertFalse(nis.isDeclaredDynamic());
	}

	@Test
	public void testNamedImportWithAliasDynamic() {
		Script script = parseESSuccessfully("import {X as Y+} from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamedImportSpecifier nis = (NamedImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("Y", nis.getAlias());
		assertTrue(nis.isDeclaredDynamic());
	}

	@Test
	public void testNamespaceImport() {
		Script script = parseESSuccessfully("import * as X from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamespaceImportSpecifier nsis = (NamespaceImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("X", nsis.getAlias());
		assertFalse(nsis.isDeclaredDynamic());
	}

	@Test
	public void testNamespaceImportDynnamic() {
		Script script = parseESSuccessfully("import * as X+ from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		NamespaceImportSpecifier nsis = (NamespaceImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("X", nsis.getAlias());
		assertTrue(nsis.isDeclaredDynamic());
	}

	@Test
	public void testDefaultImport() {
		Script script = parseESSuccessfully("import X from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		DefaultImportSpecifier dis = (DefaultImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("X", dis.getAlias());
		assertFalse(dis.isDeclaredDynamic());
	}

	@Test
	public void testDefaultImportDynamic() {
		Script script = parseESSuccessfully("import X+ from \"X\"");
		ImportDeclaration importDecl = (ImportDeclaration) script.getScriptElements().get(0);
		DefaultImportSpecifier dis = (DefaultImportSpecifier) importDecl.getImportSpecifiers().get(0);

		assertEquals("X", dis.getAlias());
		assertTrue(dis.isDeclaredDynamic());
	}

}
