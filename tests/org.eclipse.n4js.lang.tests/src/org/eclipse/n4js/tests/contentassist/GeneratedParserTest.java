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

import java.util.Collection;

import org.antlr.runtime.CharStream;
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser;
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.RequiredRuleNameComputer;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.eclipse.xtext.xtext.RuleNames;
import org.junit.Ignore;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Used to document bogus behavior of the default parser
 */
@SuppressWarnings("restriction")
@Ignore
class GeneratedParserTest extends AbstractContentAssistParserTest implements Provider<IUnorderedGroupHelper> {

	@Inject
	IUnorderedGroupHelper unorderedGroupHelper;
	@Inject
	RequiredRuleNameComputer ruleNameComputer;
	@Inject
	RuleNames ruleNames;

	@Inject
	ReflectExtensions reflExtensions;

	public TestableN4JSParser getParser() {
		TestableN4JSParser parser = new TestableN4JSParser();
		parser.setGrammarAccess(grammarAccess);
		parser.setNameMappings(nameMappings);
		parser.setUnorderedGroupHelper(this);
		try {
			reflExtensions.set(parser, "requiredRuleNameComputer", ruleNameComputer);
			reflExtensions.set(parser, "ruleNames", ruleNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		parser.initializeFor(ruleNames.getAllParserRules().iterator().next());
		return parser;
	}

	@Override
	public Collection<FollowElement> getFollowElements(INode node, int start, int end, boolean strict) {
		String s = node.getText().substring(start, end);
		return getParser().getFollowElements(s, strict);
	}

	@Override
	public Collection<FollowElement> getFollowElements(FollowElement prev) {
		return getParser().getFollowElements(prev);
	}

	@Override
	public IUnorderedGroupHelper get() {
		return unorderedGroupHelper;
	}

	static public class TestableN4JSParser extends N4JSParser {

		@Override
		protected InternalN4JSLexer createLexer(CharStream stream) {
			InternalN4JSLexer lexer = new InternalN4JSLexer();
			lexer.setCharStream(stream);
			return lexer;
		}

	}
}