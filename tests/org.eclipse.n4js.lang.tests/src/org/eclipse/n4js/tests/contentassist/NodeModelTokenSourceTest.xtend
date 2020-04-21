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

import com.google.inject.Inject
import java.io.StringReader
import java.util.List
import org.antlr.runtime.CharStream
import org.antlr.runtime.Token
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.ide.editor.contentassist.TokenSourceFactory
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.parser.N4JSSemicolonInjectingParser
import org.eclipse.n4js.parser.RegExLiteralAwareLexer
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.TerminalRule
import org.eclipse.xtext.grammaranalysis.impl.GrammarElementTitleSwitch
import org.eclipse.xtext.nodemodel.impl.InvariantChecker
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class NodeModelTokenSourceTest implements Procedures.Procedure1<Integer> {

	@Inject extension ParseHelper<Script>

	@Inject extension TokenSourceFactory

	@Inject AccessibleProductionParser accessibleParser

	@Inject N4JSGrammarAccess grammarAccess

	List<Integer> expectedTokenTypes

	@Before
	def void setUp() {
		expectedTokenTypes = newArrayList
		accessibleParser.callback = this
	}
	@After
	def void tearDown() {
		expectedTokenTypes = null
		accessibleParser = null
	}

	@Test
	def void test_01() {
		val script = '1'

		assertEqualTokenTypes('1;', script)
	}

	@Test
	def void test_02() {
		val script = '1/*\n*/2'

		assertEqualTokenTypes('1;2;', script)
	}

	@Test
	def void test_03() {
		val script = '''
			{ 1
			2 } 3
		'''

		assertEqualTokenTypes('{1;2;}3;', script)
	}

	@Test
	def void test_04() {
		val script = '''
			{ 1
			2 }
		'''

		assertEqualTokenTypes('{1;2;}\n', script)
	}

	@Test
	def void test_05() {
		val script = '''
			1
			+2
		'''

		assertEqualTokenTypes('1+2;', script)
	}

	@Test
	def void test_06() {
		val script = '''
			1;
			-2
		'''

		assertEqualTokenTypes('1;-2;', script)
	}

	@Test
	def void test_07() {
		val script = '''
			1/*
			*/2
		'''
		assertEqualTokenTypes('1;2;', script)
	}

	@Test
	def void test_08() {
		val script = '1+'
		assertEqualTokenTypes('1+', script)
	}

	@Test
	def void test_09() {
		val script = '''
			1
			+
		'''
		assertEqualTokenTypes('1+\n', script)
	}

	@Test
	def void test_10() {
		val script = '''
			1+<|>2
		'''
		assertEqualTokenTypes('1+', script)
	}

	@Test
	def void test_11() {
		val script = '''
			1/*
			*/<|>2
		'''
		assertEqualTokenTypes('1;', script)
	}

	@Test
	def void test_12() {
		val script = '''
			1/*
			*/<|>+2
		'''
		assertEqualTokenTypes('1/**/', script)
	}

	@Test
	def void test_13() {
		val script = '''
			1/*
			*/+2
		'''
		assertEqualTokenTypes('1+2;', script)
	}

	@Test
	def void test_14() {
		val script = '''
			1<|>/*
			*/2
		'''
		assertEqualTokenTypes('1', script)
	}

	@Test
	def void test_15() {
		val script = '''
			1
			<|>
			2
		'''
		assertEqualTokenTypes('1;', script)
	}

	@Test
	def void test_16() {
		val script = '''
			1

			<|>2
		'''
		assertEqualTokenTypes('1;\n', script)
	}

	@Test
	def void test_17() {
		val script = '''
			<|>/* some copyright header */
			1+2
		'''
		assertEqualTokenTypes('', script)
	}

	@Test
	def void test_18() {
		val script = '1\n'

		assertEqualTokenTypes('1;', script)
	}

	@Test
	def void test_19() {
		val script = '1<|>'

		assertEqualTokenTypes('1', script)
	}

	@Test
	def void test_20() {
		val script = '1\n<|>'

		assertEqualTokenTypes('1;', script)
	}

	@Test
	def void test_21() {
		val script = 'a.\n<|>'

		assertEqualTokenTypes('a.\n', script)
	}

	@Test
	def void test_22() {
		val script = 'a.\n'
		assertEqualTokenTypes('a.\n', script)
	}

	@Test
	def void test_23() {
		val script = 'return'
		assertEqualTokenTypes('return;', script)
	}

	@Test
	def void test_24() {
		val script = 'ret<|>urn'
		assertEqualTokenTypes('ret', script)
	}

	@Test
	def void test_25() {
		val script = 'if (true) "st<|>ring"'
		assertEqualTokenTypes('if(true)"st', script)
	}

	@Test
	def void test_26() {
		val script = 'f(): .. {<|>}'
		assertEqualTokenTypes('f():..{', script)
	}

	/**
	 * Tests that no right curly braces are added to the grammar without noticing that they may have an
	 * impact on the NodeModelTokenSource.
	 */
	@Test
	def void testKnownRightCurlies() {
		val curlies = grammarAccess.findKeywords('}').filter[!(GrammarUtil.containingRule(it) instanceof TerminalRule)].toSet

		Assert.assertTrue(curlies.remove(grammarAccess.exportClauseAccess.rightCurlyBracketKeyword_2))
		Assert.assertTrue(curlies.remove(grammarAccess.annotatedScriptElementAccess.rightCurlyBracketKeyword_1_4_8))

		Assert.assertTrue(curlies.remove(grammarAccess.annotatedExportableElementAccess.rightCurlyBracketKeyword_1_3_7))

		Assert.assertTrue(curlies.remove(grammarAccess.importSpecifiersExceptDefaultAccess.rightCurlyBracketKeyword_1_2))

		Assert.assertTrue(curlies.remove(grammarAccess.functionTypeExpressionOLDAccess.rightCurlyBracketKeyword_9))

		Assert.assertTrue(curlies.remove(grammarAccess.arrowExpressionAccess.rightCurlyBracketKeyword_1_0_2))

		Assert.assertTrue(curlies.remove(grammarAccess.blockAccess.rightCurlyBracketKeyword_2))

		Assert.assertTrue(curlies.remove(grammarAccess.membersAccess.rightCurlyBracketKeyword_2))

		Assert.assertTrue(curlies.remove(grammarAccess.switchStatementAccess.rightCurlyBracketKeyword_7))

		Assert.assertTrue(curlies.remove(grammarAccess.objectLiteralAccess.rightCurlyBracketKeyword_3))

		Assert.assertTrue(curlies.remove(grammarAccess.templateExpressionEndAccess.rightCurlyBracketKeyword))

		Assert.assertTrue(curlies.remove(grammarAccess.n4EnumDeclarationAccess.rightCurlyBracketKeyword_3))

		Assert.assertTrue(curlies.remove(grammarAccess.objectBindingPatternAccess.rightCurlyBracketKeyword_3))

		Assert.assertTrue(curlies.remove(grammarAccess.TStructMemberListAccess.rightCurlyBracketKeyword_2))

		Assert.assertTrue(curlies.remove(grammarAccess.unionTypeExpressionOLDAccess.rightCurlyBracketKeyword_5))
		Assert.assertTrue(curlies.remove(grammarAccess.intersectionTypeExpressionOLDAccess.rightCurlyBracketKeyword_5))
		Assert.assertTrue(curlies.remove(grammarAccess.typeTypeRefAccess.rightCurlyBracketKeyword_4))
		
		Assert.assertTrue(curlies.remove(grammarAccess.JSXExpressionAccess.rightCurlyBracketKeyword_2))
		Assert.assertTrue(curlies.remove(grammarAccess.JSXSpreadAttributeAccess.rightCurlyBracketKeyword_3))
		Assert.assertTrue(curlies.remove(grammarAccess.JSXPropertyAttributeAccess.rightCurlyBracketKeyword_1_1_3_2))

		for (c : curlies) {
			println(new GrammarElementTitleSwitch().showRule.showAssignments.showQualified.apply(c))
		}

		Assert.assertEquals(0, curlies.size)
	}

	def assertEqualTokenTypes(String expectation, String script) {
		val cursor = script.indexOf('<|>')
		val parsed = (if (cursor == -1)
			script
		else
			script.substring(0, cursor) + script.substring(cursor + 3)
		).parse
		val node = NodeModelUtils.getNode(parsed).rootNode
		new InvariantChecker().checkInvariant(node);
		Assert.assertTrue(expectedTokenTypes.empty)
		if (expectation.length > 0) {
			accessibleParser.parse(new StringReader(expectation))
			Assert.assertFalse(expectedTokenTypes.empty)
		}
		val mappingSource = {
			if (cursor == -1) {
				node.toTokenSource(false)
			} else {
				node.toTokenSource(cursor, false)
			}
		}
		val actual = newArrayList
		var token = mappingSource.nextToken
		while(token !== Token.EOF_TOKEN) {
			actual += token.type
			token = mappingSource.nextToken
		}
		Assert.assertEquals(expectedTokenTypes.toTokenTypeList, actual.toTokenTypeList)
	}

	protected def toTokenTypeList(List<Integer> tokenTypes) {
		tokenTypes.join('\n') [ it + ' - ' + InternalN4JSParser.tokenNames.get(it) ]
	}

	override apply(Integer p) {
		expectedTokenTypes += p
	}

}

class AccessibleLexer extends RegExLiteralAwareLexer {

	(int)=>void callback

	new((int)=>void callback) {
		this.callback = callback
	}

	override nextToken() {
		val result = super.nextToken()
		if (result !== Token.EOF_TOKEN)
			callback.apply(result.type)
		return result
	}

}

class AccessibleProductionParser extends N4JSSemicolonInjectingParser {

	(int)=>void callback

	def void setCallback((int)=>void callback) {
		this.callback = callback
	}

	override protected createLexer(CharStream stream) {
		val result = new AccessibleLexer(callback)
		result.charStream = stream
		return result
	}

}
