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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class N4_07_02_ImportExportTest extends AbstractParserTest {

	@Test
	public void testImportExample() throws Exception {
		Script script = parseHelper.parse("""
					import A from "p/A"
					import {C,D,E} from "p/E"
					import * as F from "p/F"
					import {A as G} from "p/G"
					import {A as H, B as I} from "p/H"
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(5, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ImportDeclaration));
	}

	@Test
	public void testES6Imports_01() throws Exception {
		Script script = parseESSuccessfully("""
				import 'p/A' /*
				*/ import 'p/A';import 'p/A'
				import "p/A"
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(4, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ImportDeclaration));
		assertTrue(forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> !it.isImportFrom()));
	}

	@Test
	public void testES6Imports_02() throws Exception {
		Script script = parseHelper.parse("""
					import { ImportedBinding } from 'p/A'
					import * as ImportedBinding from 'p/A'
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ImportDeclaration));
		assertTrue(
				forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> it.isImportFrom()));
		assertTrue(forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> !firstIsDefault(it)));
	}

	@Test
	public void testES6Imports_03() throws Exception {
		Script script = parseHelper.parse("""
				import {} from 'p/A'
				import { ImportsList } from 'p/A'
				import { ImportsList as X } from 'p/A'
				import { ImportsList, } from 'p/A'
				import { ImportsList as X, } from 'p/A'
				import { ImportsList, Second } from 'p/A'
				import { ImportsList as X, Second } from 'p/A'
				import { ImportsList, Second, } from 'p/A'
				import { ImportsList as X, Second as Y, } from 'p/A'
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(9, script.getScriptElements().size());
		assertTrue(
				forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> it.isImportFrom()));
		assertTrue(forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> !firstIsDefault(it)));
	}

	@Test
	public void testES6Imports_04() throws Exception {
		Script script = parseHelper.parse("""
					import ImportedBinding, * as NameSpaceImport from 'p/A'
					import ImportedBinding, {} from 'p/A'
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(2, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ImportDeclaration));
		assertTrue(
				forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> it.isImportFrom()));
		assertTrue(forall(filter(script.getScriptElements(), ImportDeclaration.class), it -> firstIsDefault(it)));
	}

	@Test
	public void testExportExample() throws Exception {
		Script script = parseHelper.parse("""
					export @Export public class A{}
					export interface B{}
					export function foo() {}
					export var a;
					export const c="Hello";
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(5, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ExportDeclaration));
	}

	@Test
	public void testES6Exports_01() throws Exception {
		Script script = parseESSuccessfully("""
				export * from 'p/A'/*
				*/export * from 'p/A'; export * from 'p/A'
				export * from "p/A"
				""");

		assertEquals(4, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ExportDeclaration));
	}

	@Test
	public void testES6Exports_02() throws Exception {
		Script script = parseESSuccessfully("""
					export * from 'p/A'
					export {} from 'p/A'
					export { exported } from 'p/A'
					export { exported as alias, }
					export var a, b
					export class A {}
					export let a = 7
					export const a = 7
					export function * f() {}
					export function f() {}
					export default function * f() {}
					export default function f() {}
					export default function * () {}
					export default function () {}
					export default class {}
					export default x = 7
				""");

		assertEquals(16, script.getScriptElements().size());
		assertTrue(forall(script.getScriptElements(), it -> it instanceof ExportDeclaration));
	}

	private boolean firstIsDefault(ImportDeclaration importDecl) {
		ImportSpecifier firstImportSpec = head(importDecl.getImportSpecifiers());
		return (firstImportSpec instanceof NamedImportSpecifier)
				&& ((NamedImportSpecifier) firstImportSpec).isDefaultImport();
	}
}
