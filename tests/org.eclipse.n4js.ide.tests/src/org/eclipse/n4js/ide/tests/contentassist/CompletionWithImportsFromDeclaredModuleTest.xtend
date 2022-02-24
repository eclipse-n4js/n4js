/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import java.util.List
import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest
import org.eclipse.n4js.ts.types.TDeclaredModule
import org.junit.Test

/**
 * Like {@link CompletionWithImportsTest}, but for the special case that we import from a {@link TDeclaredModule declared module}.
 */
public class CompletionWithImportsFromDeclaredModuleTest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	override final List<Pair<String, String>> getDefaultTestProject() {
		return #[
			"def.d.ts"  -> '''
				declare module "a/b/myDeclModule" {
					export class MyCls {}
				}
			'''
		];
	}

	@Test
	def void testAddNewImport() {
		testAtCursor('''
			let x = new MyC<|>
		''', '''
			(MyCls, Class, a/b/myDeclModule, , , 00000, , , , ([0:12 - 0:15], MyCls), [([0:0 - 0:0], import {MyCls} from "a/b/myDeclModule";
			)], [], , )
		''');
	}

	@Test
	def void testUseExistingNamespaceImport() {
		testAtCursor('''
			import * as N from "a/b/myDeclModule"
			let x = new MyC<|>
		''', '''
			(N.MyCls, Class, a/b/myDeclModule/MyCls, , , 00000, , , , ([1:12 - 1:15], N.MyCls), [], [], , )
		''');
	}

	@Test
	def void testUseExistingNamedImport() {
		testAtCursor('''
			import { MyCls } from "a/b/myDeclModule"
			let x = new MyC<|>
		''', '''
			(MyCls, Class, a/b/myDeclModule, , , 00000, , , , ([1:12 - 1:15], MyCls), [], [], , )
		''');
	}

	@Test
	def void testUseExistingNamedImportWithAlias() {
		testAtCursor('''
			import { MyCls as MyAlias } from "a/b/myDeclModule"
			let x = new MyC<|>
		''', '''
			(MyAlias, Class, alias for a/b/myDeclModule/MyCls, , , 00000, , , , ([1:12 - 1:15], MyAlias), [], [], , )
		''');
	}
}
