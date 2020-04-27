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
package org.eclipse.n4js.antlr.n4js;

import static org.eclipse.n4js.antlr.replacements.Replacements.replace;
import static org.eclipse.n4js.antlr.replacements.Replacements.replaceFirst;

import org.eclipse.n4js.antlr.CodeIntoGrammarInjector;

/**
 * Processing step that injects the necessary code to deal with automatic semicolon injection and trailing line breaks.
 */
public class JSXTextInjector implements CodeIntoGrammarInjector {

	@Override
	public String processParserGrammar(String grammarContent) {
		String result = replace(grammarContent,
				"protected boolean forcedRewind(int position) {",
				"protected void setInJsxChildren() {}\n" +
						"protected boolean forcedRewind(int position) {");
		result = replace(result, "// Rule JSXChildren\n" +
				"ruleJSXChildren[EObject in_current]  returns [EObject current=in_current]\n" +
				"@init {\n" +
				"	enterRule();\n" +
				"}",
				"// Rule JSXChildren\n" +
						"ruleJSXChildren[EObject in_current]  returns [EObject current=in_current]\n" +
						"@init {\n" +
						"	setInJsxChildren();\n" +
						"	enterRule();\n" +
						"}");
		result = replace(result, "this_JSXText_3=ruleJSXText\n" +
				"		{\n" +
				"			$current = $this_JSXText_3.current;\n" +
				"			afterParserOrEnumRuleCall();\n" +
				"		}\n" +
				"	)\n" +
				";",
				"this_JSXText_3=ruleJSXText\n" +
						"		{\n" +
						"			$current = $this_JSXText_3.current;\n" +
						"			afterParserOrEnumRuleCall();\n" +
						"		}\n" +
						"	)\n" +
						"; finally { setInJsxChildren(); }");
		return result;
	}

	@Override
	public String processLexerGrammar(String grammarContent) {
		String result = replace(grammarContent, "RULE_JSX_TEXT : '//6';", "RULE_JSX_TEXT : { false }?=> '//6';");
		return replaceFirst(result, "@members \\{", "@members {\n\n" +
				"protected boolean inJsxChildren = false;");
	}
}
