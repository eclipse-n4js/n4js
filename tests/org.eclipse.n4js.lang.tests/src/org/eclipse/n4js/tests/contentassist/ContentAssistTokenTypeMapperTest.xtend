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

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import org.eclipse.n4js.ui.contentassist.ContentAssistTokenTypeMapper
import com.google.inject.Inject
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.junit.Test
import org.eclipse.n4js.ide.contentassist.antlr.internal.InternalN4JSParser
import org.junit.Assert

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ContentAssistTokenTypeMapperTest {

	@Inject
	ContentAssistTokenTypeMapper testMe

	@Inject
	N4JSGrammarAccess grammarAccess

	@Test
	def void testRule() {
		val rule = grammarAccess.IDENTIFIERRule
		val i = testMe.getInternalTokenType(rule)
		Assert.assertEquals(InternalN4JSParser.tokenNames.indexOf('RULE_IDENTIFIER'), i)
	}

	@Test
	def void testKeyword_01() {
		val kw = grammarAccess.unaryOperatorAccess.notExclamationMarkKeyword_8_0
		val i = testMe.getInternalTokenType(kw)
		val p = new AccessibleInternalN4JSParserStub()
		val v = p.getValueForTokenName('ExclamationMark')
		Assert.assertEquals("'!'", v)
		Assert.assertEquals(InternalN4JSParser.ExclamationMark, i)
	}

	@Test
	def void testKeyword_02() {
		val kw = grammarAccess.intersectionTypeExpressionOLDAccess.intersectionKeyword_1
		val i = testMe.getInternalTokenType(kw)
		Assert.assertEquals(4, i)
	}

	@Test
	def void testRuleCall() {
		val ruleCall = grammarAccess.stringLiteralAccess.valueSTRINGTerminalRuleCall_0
		val i = testMe.getInternalTokenType(ruleCall)
		Assert.assertEquals(InternalN4JSParser.tokenNames.indexOf('RULE_STRING'), i)
	}
}

class AccessibleInternalN4JSParserStub extends InternalN4JSParser {
	new() {
		super(null)
	}

	override protected getValueForTokenName(String tokenName) {
		super.getValueForTokenName(tokenName)
	}

}
