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
package org.eclipse.n4js.ide.tests.symbol

import java.io.File
import java.util.List
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.LocationLink
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.n4js.ide.tests.server.AbstractDefinitionTest
import org.eclipse.n4js.ide.tests.server.StringLSP4J
import org.eclipse.xtext.testing.DefinitionTestConfiguration
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Test for document symbols.
 */
class DefinitionTest extends AbstractDefinitionTest {

	@Test
	def void testDefinition_01() throws Exception {
		test [
			model = '''
				var s: string = '';
				s.length
			'''
			line = 0
			column = 7
			expectedDefinitions = '''
				(n4scheme:/primitives_js.n4ts, [33:10 - 33:16])
			'''
		]
	}
	
	@Test
	def void testDefinition_02() throws Exception {
		test [
			model = '''
				var s: string = '';
				s.length
			'''
			line = 1
			column = 0
			expectedDefinitions = '''
				(test-project/src/MyModule.n4js, [0:4 - 0:5])
			'''
		]
	}
	
	@Test
	def void testDefinition_03() throws Exception {
		test [
			model = '''
				var s: string = '';
				s.length
			'''
			line = 1
			column = 3
			expectedDefinitions = '''
				(n4scheme:/builtin_js.n4ts, [838:15 - 838:21])
			'''
		]
	}

	@Test
	def void testDefinition_04() throws Exception {
		var File root=getRoot() 
		createTestProjectOnDisk(root, #{}) 
		createInjector() 
		startLspServer(root) 

		
		var TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams()
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier('n4scheme:/builtin_js.n4ts'))
		// see position from test above
		textDocumentPositionParams.setPosition(new Position(838, 15))
		var CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definitionsFuture = languageServer.
			definition(textDocumentPositionParams)
		var Either<List<? extends Location>, List<? extends LocationLink>> definitions = definitionsFuture.get()
		
		var String actualSignatureHelp = new StringLSP4J(root).toString(definitions)
		assertEquals('(n4scheme:/builtin_js.n4ts, [838:15 - 838:21])', actualSignatureHelp.trim())
	}

	protected def void test(Consumer<DefinitionTestConfiguration> init) throws Exception {
		val config = new DefinitionTestConfiguration()
		init.accept(config)
		test(config)
	}

}
