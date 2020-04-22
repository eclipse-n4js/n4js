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
import static org.eclipse.n4js.antlr.replacements.Replacements.replaceFirst;

import org.eclipse.n4js.antlr.CodeIntoGrammarInjector;

/**
 * Processing step that injects the necessary code to deal with regular expression literals.
 */
public class RegExDisambiguationInjector implements CodeIntoGrammarInjector {

	@Override
	public String processLexerGrammar(String grammarContent) {
		String result = addInRegularExpression(grammarContent);
		result = addPredicate(result);
		return result;
	}

	@Override
	public String processParserGrammar(String grammarContent) {
		String result = replace(grammarContent,
				"protected boolean forcedRewind(int position) {",
				"protected void setInRegularExpression() {}\n" +
						"protected boolean forcedRewind(int position) {");
		result = replace(result,
				"ruleREGEX_LITERAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]\n" +
						"@init {\n" +
						"	enterRule();",
				"ruleREGEX_LITERAL returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]\n" +
						"@init {\n" +
						"	enterRule();\n" +
						"	setInRegularExpression();");
		return result;
	}

	private String addPredicate(String grammarContent) {
		// effectively disables this rule but we need it to have the right token type
		String result = replace(grammarContent, "RULE_REGEX_TAIL :", "RULE_REGEX_TAIL : { false }?=>");
		return result;
	}

	private String addInRegularExpression(String grammarContent) {
		String replacement = "}\n" +
				"@members {\n\n" +
				"protected boolean inRegularExpression = false;\n" +
				"}\n";

		return replaceFirst(grammarContent, "}", replacement);
	}

}
