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
package org.eclipse.n4js.antlr.n4js;

import static org.eclipse.n4js.antlr.replacements.Replacements.replace;

import org.eclipse.n4js.antlr.CodeIntoGrammarInjector;

/**
 * Processing step that injects the necessary code to deal with no line terminator situations.
 *
 * @see org.eclipse.n4js.antlr.AntlrGeneratorWithCustomKeywordLogic#fixIdentifierAsKeywordWithEOLAwareness(String)
 */
@SuppressWarnings("javadoc") // I really want to link to a private method!
public class NoLineTerminatorHandlingInjector implements CodeIntoGrammarInjector {

	@Override
	public String processLexerGrammar(String grammarContent) {
		String result = addPredicate(grammarContent);
		return result;
	}

	@Override
	public String processParserGrammar(String grammarContent) {

		/**
		 * <pre>
		int alt242=13;
		try {
		alt242 = dfa242.predict(input);
		} catch (NoViableAltException re) {
			int as = input.LA(1);
			if (as==Async) { alt242=3; }
			else throw re;
		}
		switch (alt242) {
		 * </pre>
		 */

		return grammarContent;
	}

	private String addPredicate(String grammarContent) {
		// effectively disables these rules but we need them to have the right token types
		String result = replace(grammarContent, "RULE_NO_LINE_TERMINATOR :", "RULE_NO_LINE_TERMINATOR : { false }?=>");
		return result;
	}

}
