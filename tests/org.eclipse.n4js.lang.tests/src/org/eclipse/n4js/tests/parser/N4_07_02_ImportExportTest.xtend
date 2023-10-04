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
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.junit.Test

class N4_07_02_ImportExportTest extends AbstractParserTest{

	@Test
	def void testImportExample() {
		val script = parseHelper.parse('''
			import A from "p/A"
			import {C,D,E} from "p/E"
			import * as F from "p/F"
			import {A as G} from "p/G"
			import {A as H, B as I} from "p/H"
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(5, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ImportDeclaration])
	}

	@Test
	def void testES6Imports_01() {
		val script = parseESSuccessfully('''
			import 'p/A' /*
			*/ import 'p/A';import 'p/A'
			import "p/A"''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(4, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ImportDeclaration])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[!it.isImportFrom])
	}

	@Test
	def void testES6Imports_02() {
		val script = parseHelper.parse('''
			import { ImportedBinding } from 'p/A'
			import * as ImportedBinding from 'p/A'
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ImportDeclaration])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[it.isImportFrom])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[!it.firstIsDefault])
	}

	@Test
	def void testES6Imports_03() {
		val script = parseHelper.parse('''
			import {} from 'p/A'
			import { ImportsList } from 'p/A'
			import { ImportsList as X } from 'p/A'
			import { ImportsList, } from 'p/A'
			import { ImportsList as X, } from 'p/A'
			import { ImportsList, Second } from 'p/A'
			import { ImportsList as X, Second } from 'p/A'
			import { ImportsList, Second, } from 'p/A'
			import { ImportsList as X, Second as Y, } from 'p/A'
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(9, script.scriptElements.size);
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[it.isImportFrom])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[!it.firstIsDefault])
	}

	@Test
	def void testES6Imports_04() {
		val script = parseHelper.parse('''
			import ImportedBinding, * as NameSpaceImport from 'p/A'
			import ImportedBinding, {} from 'p/A'
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(2, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ImportDeclaration])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[it.isImportFrom])
		assertTrue(script.scriptElements.filter(ImportDeclaration).forall[it.firstIsDefault])
	}

	@Test
	def void testExportExample() {
		val script = parseHelper.parse('''
			export @Export public class A{}
			export interface B{}
			export function foo() {}
			export var a;
			export const c="Hello";
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(5, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ExportDeclaration])
	}

	@Test
	def void testES6Exports_01() {
		val script = parseESSuccessfully('''
			export * from 'p/A'/*
			*/export * from 'p/A'; export * from 'p/A'
			export * from "p/A"''');

		assertEquals(4, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ExportDeclaration])
	}

	@Test
	def void testES6Exports_02() {
		val script = parseESSuccessfully('''
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
		''');

		assertEquals(16, script.scriptElements.size);
		assertTrue(script.scriptElements.forall[it instanceof ExportDeclaration])
	}

	def private boolean firstIsDefault(ImportDeclaration importDecl) {
		val firstImportSpec = importDecl.importSpecifiers.head;
		return if (firstImportSpec instanceof NamedImportSpecifier) firstImportSpec.isDefaultImport else false;
	}
}
