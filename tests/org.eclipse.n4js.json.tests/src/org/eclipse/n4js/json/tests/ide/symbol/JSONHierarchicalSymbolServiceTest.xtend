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
package org.eclipse.n4js.json.tests.ide.symbol

import org.eclipse.lsp4j.ClientCapabilities
import org.eclipse.lsp4j.DocumentSymbolCapabilities
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.TextDocumentClientCapabilities
import org.eclipse.xtext.testing.AbstractLanguageServerTest
import org.junit.Test

/**
 *
 */
class JSONHierarchicalSymbolServiceTest extends AbstractLanguageServerTest {
	new() {
		super("json")
	}
 
	static val (InitializeParams)=>void INITIALIZER = [
		capabilities = new ClientCapabilities() => [
			textDocument = new TextDocumentClientCapabilities() => [
				documentSymbol = new DocumentSymbolCapabilities() => [
					it.hierarchicalDocumentSymbolSupport = true;
				];
			];
		];
	];

	@Test
	def void testDocumentSymbol_01() {
		testDocumentSymbol[
			initializer = INITIALIZER
			model = '''
				{
					"name1" : "string",
					"name2" : true,
					"name3" : 123,
					"name4" : {
						"child" : null
					},
					"name5" : [ "value1", "value2" ]
				}
			'''
			expectedSymbols = '''
				symbol "name1 : "string"" {
				    kind: 8
				    range: [[1, 1] .. [1, 19]]
				    selectionRange: [[1, 1] .. [1, 8]]
				    details: 
				    deprecated: false
				}
				symbol "name2 : true" {
				    kind: 16
				    range: [[2, 1] .. [2, 15]]
				    selectionRange: [[2, 1] .. [2, 8]]
				    details: 
				    deprecated: false
				}
				symbol "name3 : 123" {
				    kind: 16
				    range: [[3, 1] .. [3, 14]]
				    selectionRange: [[3, 1] .. [3, 8]]
				    details: 
				    deprecated: false
				}
				symbol "name4" {
				    kind: 19
				    range: [[4, 1] .. [6, 2]]
				    selectionRange: [[4, 1] .. [4, 8]]
				    details: 
				    deprecated: false
				    children: [
				        symbol "child : null" {
				            kind: 21
				            range: [[5, 2] .. [5, 16]]
				            selectionRange: [[5, 2] .. [5, 9]]
				            details: 
				            deprecated: false
				        }
				    ]
				}
				symbol "name5" {
				    kind: 18
				    range: [[7, 1] .. [7, 33]]
				    selectionRange: [[7, 1] .. [7, 8]]
				    details: 
				    deprecated: false
				    children: [
				        symbol ""value1"" {
				            kind: 15
				            range: [[7, 13] .. [7, 21]]
				            selectionRange: [[7, 13] .. [7, 21]]
				            details: 
				            deprecated: false
				        }
				        symbol ""value2"" {
				            kind: 15
				            range: [[7, 23] .. [7, 31]]
				            selectionRange: [[7, 23] .. [7, 31]]
				            details: 
				            deprecated: false
				        }
				    ]
				}
			'''
		]
	}
}