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
 * Processing step that injects the necessary code to deal with template literals.
 */
public class TemplateLiteralDisambiguationInjector implements CodeIntoGrammarInjector {

	@Override
	public String processLexerGrammar(String grammarContent) {
		String result = addInTemplateSegment(grammarContent);
		result = addPredicate(result);
		return result;
	}

	@Override
	public String processParserGrammar(String grammarContent) {
		String result = replace(grammarContent,
				"protected boolean forcedRewind(int marker, boolean advance) {",
				"protected void setInTemplateSegment() {}\n" +
						"protected boolean forcedRewind(int marker, boolean advance) {");
		result = replace(result,
				"ruleTemplateExpressionEnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]\n" +
						"@init {\n" +
						"	enterRule();\n",
				"ruleTemplateExpressionEnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]\n" +
						"@init {\n" +
						"	enterRule();\n" +
						"	setInTemplateSegment();");
		return result;
	}

	private String addPredicate(String grammarContent) {
		// effectively disables these rules but we need them to have the right token types
		String result = replace(grammarContent, "RULE_TEMPLATE_MIDDLE :", "RULE_TEMPLATE_MIDDLE : { false }?=>");
		result = replace(result, "RULE_TEMPLATE_END :", "RULE_TEMPLATE_END : { false }?=>");
		return result;
	}

	private String addInTemplateSegment(String grammarContent) {
		String replacement = "@members {\n\n" +
				"protected boolean inTemplateSegment = false;";

		return replaceFirst(grammarContent, "@members \\{", replacement);
	}

}
