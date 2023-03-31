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
package org.eclipse.n4js.ide.tests.rename;

import java.util.Map;

import org.eclipse.n4js.ide.tests.helper.server.AbstractRenameTest;
import org.junit.Test;

/**
 * Test rename refactoring related to imports.
 */
@SuppressWarnings("javadoc")
public class RenameImportsTest extends AbstractRenameTest {

	@Test
	public void testNamedImport() {
		testAtCursors(Map.of(
				"Cls", """
						export class <|>Na<|>me<|> {}
						""",
				"Main", """
						import {<|>Na<|>me<|>} from "Cls"
						new <|>Na<|>me<|>();
						"""),
				"NameNew", Map.of(
						"Cls", """
								export class NameNew {}
								""",
						"Main", """
								import {NameNew} from "Cls"
								new NameNew();
								"""));
	}

	@Test
	public void testNamedImport_withAlias_renameElement() {
		testAtCursors(Map.of(
				"Cls", """
						export class <|>Na<|>me<|> {}
						""",
				"Main", """
						import {<|>Na<|>me<|> as Alias} from "Cls"
						new Alias();
						"""),
				"NameNew", Map.of(
						"Cls", """
								export class NameNew {}
								""",
						"Main", """
								import {NameNew as Alias} from "Cls"
								new Alias();
								"""));
	}

	@Test
	public void testNamedImport_withAlias_renameAlias() {
		testAtCursors(Map.of(
				"Cls", """
						export class Cls {}
						""",
				"Main", """
						import {Cls as <|>Na<|>me<|>} from "Cls"
						new <|>Na<|>me<|>();
						"""),
				"NameNew", Map.of(
						"Main", """
								import {Cls as NameNew} from "Cls"
								new NameNew();
								"""));
	}

	@Test
	public void testDefaultImport_equalAlias_renameElement() {
		testAtCursors(Map.of(
				"Cls", """
						export default class <|>Na<|>me<|> {}
						""",
				"Main", """
						import Name from "Cls"
						new Name();
						"""),
				"NameNew", Map.of(
						"Cls", """
								export default class NameNew {}
								"""
				// NOTE: "Main" must remain unchanged!
				));
	}

	@Test
	public void testDefaultImport_equalAlias_renameAlias() {
		testAtCursors(Map.of(
				"Cls", """
						export default class Name {}
						""",
				"Main", """
						import <|>Na<|>me<|> from "Cls"
						new <|>Na<|>me<|>();
						"""),
				"NameNew", Map.of(
						// NOTE: "Cls" must remain unchanged!
						"Main", """
								import NameNew from "Cls"
								new NameNew();
								"""));
	}

	@Test
	public void testDefaultImport_differentAlias_renameElement() {
		testAtCursors(Map.of(
				"Cls", """
						export default class <|>Na<|>me<|> {}
						""",
				"Main", """
						import Alias from "Cls"
						new Alias();
						"""),
				"NameNew", Map.of(
						"Cls", """
								export default class NameNew {}
								"""
				// NOTE: "Main" must remain unchanged!
				));
	}

	@Test
	public void testDefaultImport_differentAlias_renameAlias() {
		testAtCursors(Map.of(
				"Cls", """
						export default class Cls {}
						""",
				"Main", """
						import <|>Na<|>me<|> from "Cls"
						new <|>Na<|>me<|>();
						"""),
				"NameNew", Map.of(
						// NOTE: "Cls" must remain unchanged!
						"Main", """
								import NameNew from "Cls"
								new NameNew();
								"""));
	}

	@Test
	public void testNamespaceImport_renameElement() {
		testAtCursors(Map.of(
				"Cls", """
						export class <|>Na<|>me<|> {}
						""",
				"Main", """
						import * as NS from "Cls"
						new NS.<|>Na<|>me<|>();
						"""),
				"NameNew", Map.of(
						"Cls", """
								export class NameNew {}
								""",
						"Main", """
								import * as NS from "Cls"
								new NS.NameNew();
								"""));
	}

	@Test
	public void testNamespaceImport_renameNamespace() {
		testAtCursors(Map.of(
				"Cls", """
						export class Cls {}
						""",
				"Main", """
						import * as <|>Na<|>me<|> from "Cls"
						new <|>Na<|>me<|>.Cls();
						"""),
				"NameNew", Map.of(
						"Main", """
								import * as NameNew from "Cls"
								new NameNew.Cls();
								"""));
	}
}
