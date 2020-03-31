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
package org.eclipse.n4js.ide.tests.server

import org.eclipse.lsp4j.CodeAction
import org.eclipse.n4js.ide.xtext.server.XDocument
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Test for code actions in n4js
 */
class CodeActionTest extends AbstractCodeActionTest {

	@Test
	def void test_01() throws Exception {
		test(new TestCodeActionConfiguration2 => [
			model = 'class X { int m() { return 1 } }';
			column = 10;
			expectedCodeActions = '''
				title      : Convert to colon style
				kind       : quickfix
				command    : 
				diagnostics: (CODE:TYS_INVALID_TYPE_SYNTAX, Error, , [0:10 - 0:13], Wrong type syntax: Use foo:Type rather than Type foo., [])
				edit       : ([], 
				   test-project/src/MyModule.n4js -> [([0:17 - 0:17], : int), ([0:10 - 0:14], )])''';
			assertCodeActions = [ list |
				 assertEquals('class X { m(): int { return 1 } }', model.modify(list.head.getRight));
				 assertEquals(expectedCodeActions, getStringLSP4J().toString(list.head.getRight));
			];
		]);
	}
	
	@Test
	def void test_02() throws Exception {
		test(new TestCodeActionConfiguration2 => [
			model = 'class X { i: int?; }';
			column = 13;
			expectedCodeActions = '''
				title      : Change to new syntax
				kind       : quickfix
				command    : 
				diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:13 - 0:17], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
				edit       : ([], 
				   test-project/src/MyModule.n4js -> [([0:16 - 0:17], ), ([0:11 - 0:11], ?)])''';
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int; }', model.modify(list.head.getRight));
				 assertEquals(expectedCodeActions, getStringLSP4J().toString(list.head.getRight));
			];
		]);
	}
	
	@Test
	def void test_03() throws Exception {
		test(new TestCodeActionConfiguration2 => [
			model = 'class X { i?: int?; }';
			column = 14;
			expectedCodeActions = '''
				title      : Change to new syntax
				kind       : quickfix
				command    : 
				diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:14 - 0:18], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
				edit       : ([], 
				   test-project/src/MyModule.n4js -> [([0:17 - 0:18], )])''';
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int; }', model.modify(list.head.getRight));
				 assertEquals(expectedCodeActions, getStringLSP4J().toString(list.head.getRight));
			];
		]);
	}
	
	@Test
	def void test_04() throws Exception {
		test(new TestCodeActionConfiguration2 => [
			model = 'class X { i?: int ? ; }';
			column = 14;
			expectedCodeActions = '''
				title      : Change to new syntax
				kind       : quickfix
				command    : 
				diagnostics: (CODE:CLF_FIELD_OPTIONAL_OLD_SYNTAX, Error, , [0:14 - 0:19], This syntax for optional fields is no longer allowed. Place the ? right after the field's name., [])
				edit       : ([], 
				   test-project/src/MyModule.n4js -> [([0:18 - 0:19], )])''';
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int  ; }', model.modify(list.head.getRight));
				 assertEquals(expectedCodeActions, getStringLSP4J().toString(list.head.getRight));
			];
		]);
	}
	
	private def String modify(String model, CodeAction action) {
		return new XDocument(0, model).applyChanges(action.edit.changes.values.flatten).contents
	}
		
}