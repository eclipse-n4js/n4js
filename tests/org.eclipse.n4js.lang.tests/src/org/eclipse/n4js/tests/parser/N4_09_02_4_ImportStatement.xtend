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

import org.eclipse.n4js.n4JS.DefaultImportSpecifier
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.junit.Test

class N4_09_02_4_ImportStatement extends AbstractParserTest {

	@Test
	def void testNamedImport() {
		val script = 'import {X} from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nis = importDecl.importSpecifiers.get(0) as NamedImportSpecifier;

		assertNull(nis.alias);
		assertFalse(nis.declaredDynamic);
	}

	@Test
	def void testNamedImportDynamic() {
		val script = 'import {X+} from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nis = importDecl.importSpecifiers.get(0) as NamedImportSpecifier;

		assertNull(nis.alias);
		assertTrue(nis.declaredDynamic);
	}

	@Test
	def void testNamedImportWithAlias() {
		val script = 'import {X as Y} from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nis = importDecl.importSpecifiers.get(0) as NamedImportSpecifier;

		assertEquals("Y", nis.alias);
		assertFalse(nis.declaredDynamic);
	}

	@Test
	def void testNamedImportWithAliasDynamic() {
		val script = 'import {X as Y+} from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nis = importDecl.importSpecifiers.get(0) as NamedImportSpecifier;

		assertEquals("Y", nis.alias);
		assertTrue(nis.declaredDynamic);
	}

	@Test
	def void testNamespaceImport() {
		val script = 'import * as X from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nsis = importDecl.importSpecifiers.get(0) as NamespaceImportSpecifier;

		assertEquals("X", nsis.alias);
		assertFalse(nsis.declaredDynamic);
	}

	@Test
	def void testNamespaceImportDynnamic() {
		val script = 'import * as X+ from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val nsis = importDecl.importSpecifiers.get(0) as NamespaceImportSpecifier;

		assertEquals("X", nsis.alias);
		assertTrue(nsis.declaredDynamic);
	}

	@Test
	def void testDefaultImport() {
		val script = 'import X from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val dis = importDecl.importSpecifiers.get(0) as DefaultImportSpecifier;

		assertEquals("X", dis.alias);
		assertFalse(dis.declaredDynamic);
	}

	@Test
	def void testDefaultImportDynamic() {
		val script = 'import X+ from "X"'.parseESSuccessfully
		val importDecl = script.scriptElements.get(0) as ImportDeclaration
		val dis = importDecl.importSpecifiers.get(0) as DefaultImportSpecifier;

		assertEquals("X", dis.alias);
		assertTrue(dis.declaredDynamic);
	}

}
