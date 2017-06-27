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
package org.eclipse.n4js.tests.contentassist

import com.google.common.base.Charsets
import com.google.common.io.CharStreams
import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.parser.AntlrStreamWithToString
import org.eclipse.n4js.ui.contentassist.TokenSourceFactory
import org.eclipse.n4js.ui.contentassist.antlr.lexer.InternalN4JSLexer
import java.io.InputStreamReader
import java.io.StringReader
import org.antlr.runtime.Token
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Small performance tests, since they are quite fast (and they must not be executed as plugin tests),
 * they are using a file name different from the performance test convention.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class NodeModelTokenSourcePerfTest {

	@Inject extension ParseHelper<Script>

	@Inject extension TokenSourceFactory

	var String testData
	var Script parsed
	var ICompositeNode node

	@Before
	def void setUp() {
		testData = CharStreams.toString [|
			new InputStreamReader(NodeModelTokenSourcePerfTest.getResourceAsStream('/org/eclipse/n4js/tests/contentassist/testdata.txt'), Charsets.UTF_8)
		]
		parsed = testData.parse
		node = NodeModelUtils.getNode(parsed).rootNode
	}
	@After
	def void tearDown() {
		testData = null
		parsed = null
		node = null
	}

	@Test
	def void test_nodes_1000() {
		(1..1000).forEach [
			val source = node.toTokenSource
			while(Token.EOF_TOKEN !== source.nextToken) {}
		]
	}

	@Test
	def void test_lexer_1000() {
		(1..1000).forEach [
			val stream = new AntlrStreamWithToString(new StringReader(testData))
			val lexer = new InternalN4JSLexer(stream)
			while(Token.EOF_TOKEN !== lexer.nextToken) {}
		]
	}

	@Ignore
	@Test
	def void test_nodes_1000_Again() {
		test_nodes_1000
	}

	@Ignore
	@Test
	def void test_lexer_1000_Again() {
		test_lexer_1000
	}
}
