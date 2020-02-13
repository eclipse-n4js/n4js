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
package org.eclipse.n4js.ide.tests.codeActions

import org.eclipse.lsp4j.CodeAction
import org.eclipse.n4js.ide.tests.AbstractN4JSIdeTest
import org.eclipse.n4js.ide.xtext.server.XDocument
import org.junit.Test

/**
 * Test for code actions in n4js
 */
class CodeActionTest extends AbstractN4JSIdeTest {

	@Test def void test_01() {
		testCodeAction[ 
			model = 'class X { int m() { return 1 } }'
			column = 10
			expectedCodeActions = '''
				title : Convert to colon style
				kind : quickfix
				command : 
				codes : TYS_INVALID_TYPE_SYNTAX
				edit : changes :
				    MyModel.n4js : : int [[0, 17] .. [0, 17]]
				     [[0, 10] .. [0, 14]]
				documentChanges : 
			'''
			assertCodeActions = [ list |
				 assertEquals(expectedCodeActions, list.head.getRight.toExpectation)
				 assertEquals('class X { m(): int { return 1 } }', model.modify(list.head.getRight))
			]
		]
	}
	
	@Test def void test_02() {
		testCodeAction[ 
			model = 'class X { i: int?; }'
			column = 13
			expectedCodeActions = '''
				title : Change to new syntax
				kind : quickfix
				command : 
				codes : CLF_FIELD_OPTIONAL_OLD_SYNTAX
				edit : changes :
				    MyModel.n4js :  [[0, 16] .. [0, 17]]
				    ? [[0, 11] .. [0, 11]]
				documentChanges : 
			'''
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int; }', model.modify(list.head.getRight))
				 assertEquals(expectedCodeActions, list.head.getRight.toExpectation)
			]
		]
	}
	
	@Test def void test_03() {
		testCodeAction[ 
			model = 'class X { i?: int?; }'
			column = 14
			expectedCodeActions = '''
				title : Change to new syntax
				kind : quickfix
				command : 
				codes : CLF_FIELD_OPTIONAL_OLD_SYNTAX
				edit : changes :
				    MyModel.n4js :  [[0, 17] .. [0, 18]]
				documentChanges : 
			'''
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int; }', model.modify(list.head.getRight))
				 assertEquals(expectedCodeActions, list.head.getRight.toExpectation)
			]
		]
	}
	
	@Test def void test_04() {
		testCodeAction[ 
			model = 'class X { i?: int ? ; }'
			column = 14
			expectedCodeActions = '''
				title : Change to new syntax
				kind : quickfix
				command : 
				codes : CLF_FIELD_OPTIONAL_OLD_SYNTAX
				edit : changes :
				    MyModel.n4js :  [[0, 18] .. [0, 19]]
				documentChanges : 
			'''
			assertCodeActions = [ list |
				 assertEquals('class X { i?: int  ; }', model.modify(list.head.getRight))
				 assertEquals(expectedCodeActions, list.head.getRight.toExpectation)
			]
		]
	}
	
	private def String modify(String model, CodeAction action) {
		return new XDocument(0, model).applyChanges(action.edit.changes.values.flatten).contents
	}
		
}