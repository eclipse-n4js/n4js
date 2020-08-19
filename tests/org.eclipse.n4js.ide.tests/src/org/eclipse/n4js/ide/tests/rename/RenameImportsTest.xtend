/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.rename

import org.eclipse.n4js.ide.tests.server.AbstractRenameTest
import org.junit.Test

/**
 * Test rename refactoring related to imports.
 */
class RenameImportsTest extends AbstractRenameTest {

	@Test
	def void testNamedImport() {
		testAtCursors(#[
			"Cls" -> '''
				export class <|>Na<|>me<|> {}
			''',
			"Main" -> '''
				import {<|>Na<|>me<|>} from "Cls"
				new <|>Na<|>me<|>();
			'''
		], "NameNew", #[
			"Cls" -> '''
				export class NameNew {}
			''',
			"Main" -> '''
				import {NameNew} from "Cls"
				new NameNew();
			'''
		]);
	}

	@Test
	def void testNamedImport_withAlias_renameElement() {
		testAtCursors(#[
			"Cls" -> '''
				export class <|>Na<|>me<|> {}
			''',
			"Main" -> '''
				import {<|>Na<|>me<|> as Alias} from "Cls"
				new Alias();
			'''
		], "NameNew", #[
			"Cls" -> '''
				export class NameNew {}
			''',
			"Main" -> '''
				import {NameNew as Alias} from "Cls"
				new Alias();
			'''
		]);
	}

	@Test
	def void testNamedImport_withAlias_renameAlias() {
		testAtCursors(#[
			"Cls" -> '''
				export class Cls {}
			''',
			"Main" -> '''
				import {Cls as <|>Na<|>me<|>} from "Cls"
				new <|>Na<|>me<|>();
			'''
		], "NameNew", #[
			"Main" -> '''
				import {Cls as NameNew} from "Cls"
				new NameNew();
			'''
		]);
	}

	@Test
	def void testDefaultImport_equalAlias_renameElement() {
		testAtCursors(#[
			"Cls" -> '''
				export default class <|>Na<|>me<|> {}
			''',
			"Main" -> '''
				import Name from "Cls"
				new Name();
			'''
		], "NameNew", #[
			"Cls" -> '''
				export default class NameNew {}
			'''
			// NOTE: "Main" must remain unchanged!
		]);
	}

	@Test
	def void testDefaultImport_equalAlias_renameAlias() {
		testAtCursors(#[
			"Cls" -> '''
				export default class Name {}
			''',
			"Main" -> '''
				import <|>Na<|>me<|> from "Cls"
				new <|>Na<|>me<|>();
			'''
		], "NameNew", #[
			// NOTE: "Cls" must remain unchanged!
			"Main" -> '''
				import NameNew from "Cls"
				new NameNew();
			'''
		]);
	}

	@Test
	def void testDefaultImport_differentAlias_renameElement() {
		testAtCursors(#[
			"Cls" -> '''
				export default class <|>Na<|>me<|> {}
			''',
			"Main" -> '''
				import Alias from "Cls"
				new Alias();
			'''
		], "NameNew", #[
			"Cls" -> '''
				export default class NameNew {}
			'''
			// NOTE: "Main" must remain unchanged!
		]);
	}

	@Test
	def void testDefaultImport_differentAlias_renameAlias() {
		testAtCursors(#[
			"Cls" -> '''
				export default class Cls {}
			''',
			"Main" -> '''
				import <|>Na<|>me<|> from "Cls"
				new <|>Na<|>me<|>();
			'''
		], "NameNew", #[
			// NOTE: "Cls" must remain unchanged!
			"Main" -> '''
				import NameNew from "Cls"
				new NameNew();
			'''
		]);
	}

	@Test
	def void testNamespaceImport_renameElement() {
		testAtCursors(#[
			"Cls" -> '''
				export class <|>Na<|>me<|> {}
			''',
			"Main" -> '''
				import * as NS from "Cls"
				new NS.<|>Na<|>me<|>();
			'''
		], "NameNew", #[
			"Cls" -> '''
				export class NameNew {}
			''',
			"Main" -> '''
				import * as NS from "Cls"
				new NS.NameNew();
			'''
		]);
	}

	@Test
	def void testNamespaceImport_renameNamespace() {
		testAtCursors(#[
			"Cls" -> '''
				export class Cls {}
			''',
			"Main" -> '''
				import * as <|>Na<|>me<|> from "Cls"
				new <|>Na<|>me<|>.Cls();
			'''
		], "NameNew", #[
			"Main" -> '''
				import * as NameNew from "Cls"
				new NameNew.Cls();
			'''
		]);
	}
}
