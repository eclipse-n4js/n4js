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
package org.eclipse.n4js.tests.contentassist;

import java.util.Arrays;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.contentassist.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistTokenTypeMapper;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ContentAssistTokenTypeMapperTest {

	@Inject
	ContentAssistTokenTypeMapper testMe;

	@Inject
	N4JSGrammarAccess grammarAccess;

	@Test
	public void testRule() {
		TerminalRule rule = grammarAccess.getIDENTIFIERRule();
		int i = testMe.getInternalTokenType(rule);
		Assert.assertEquals(Arrays.asList(InternalN4JSParser.tokenNames).indexOf("RULE_IDENTIFIER"), i);
	}

	@Test
	public void testKeyword_01() {
		Keyword kw = grammarAccess.getUnaryOperatorAccess().getNotExclamationMarkKeyword_8_0();
		int i = testMe.getInternalTokenType(kw);
		AccessibleInternalN4JSParserStub p = new AccessibleInternalN4JSParserStub();
		String v = p.getValueForTokenName("ExclamationMark");
		Assert.assertEquals("\"!\"", v);
		Assert.assertEquals(InternalN4JSParser.ExclamationMark, i);
	}

	@Test
	public void testKeyword_02() {
		Keyword kw = grammarAccess.getIntersectionTypeExpressionOLDAccess().getIntersectionKeyword_1();
		int i = testMe.getInternalTokenType(kw);
		Assert.assertEquals(4, i);
	}

	@Test
	public void testRuleCall() {
		RuleCall ruleCall = grammarAccess.getStringLiteralAccess().getValueSTRINGTerminalRuleCall_0();
		int i = testMe.getInternalTokenType(ruleCall);
		Assert.assertEquals(Arrays.asList(InternalN4JSParser.tokenNames).indexOf("RULE_STRING"), i);
	}

	static public class AccessibleInternalN4JSParserStub extends InternalN4JSParser {
		public AccessibleInternalN4JSParserStub() {
			super(null);
		}

		@Override
		protected String getValueForTokenName(String tokenName) {
			return super.getValueForTokenName(tokenName);
		}

	}
}
