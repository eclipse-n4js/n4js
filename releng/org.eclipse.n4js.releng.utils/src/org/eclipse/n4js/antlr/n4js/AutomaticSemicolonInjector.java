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

import static org.eclipse.n4js.antlr.replacements.Replacements.applyReplacement;
import static org.eclipse.n4js.antlr.replacements.Replacements.replace;

import org.eclipse.n4js.antlr.CodeIntoGrammarInjector;

/**
 * Processing step that injects the necessary code to deal with automatic semicolon injection and trailing line breaks.
 */
public class AutomaticSemicolonInjector implements CodeIntoGrammarInjector {

	@Override
	public String processParserGrammar(String grammarContent) {
		String result = addCustomMethodsToParser(grammarContent);
		result = alterRuleSemi(result);
		result = injectPromoteEOLCalls(result);
		return result;
	}

	@Override
	public String processLexerGrammar(String grammarContent) {
		return grammarContent;
	}

	/**
	 * Alters rules in which no line terminators are allowed.
	 */
	private String injectPromoteEOLCalls(String grammarContents) {
		String result = replace(grammarContents, "1=Return",
				"1=Return { promoteEOL(); }");
		result = replace(result, "0=Throw", "0=Throw { promoteEOL(); }");
		result = replace(result, "1=Break", "1=Break { promoteEOL(); }");
		result = replace(result, "1=Continue", "1=Continue { promoteEOL(); }");
		result = replace(result, "1=Yield", "1=Yield { promoteEOL(); }");
		result = replace(result,
				"this_LeftHandSideExpression_0=ruleLeftHandSideExpression",
				"this_LeftHandSideExpression_0=ruleLeftHandSideExpression  { if (input.LA(1) == PlusSignPlusSign || input.LA(1) == HyphenMinusHyphenMinus) promoteEOL(); }");
		return result;
	}

	private String alterRuleSemi(String grammarContent) {
		return applyReplacement(grammarContent, "ruleSemi.g.replacement");
	}

	/**
	 * Adds custom methods to generated parser. These custom methods are only stub implementations, but they can be
	 * overridden in subclass with specific code. They are called from some rules, which are also modified in the
	 * post-procesing phase.
	 */
	private String addCustomMethodsToParser(String grammarContent) {
		return replace(grammarContent, "@members {",
				"@members {\n\n" +
						"// injected by AutomaticSemicolonInjector\n" +
						"protected boolean forcedRewind(int marker) { return true; } // overridden in subtype\n" +
						"protected void promoteEOL() {} // overridden in subtype\n" +
						"protected void addASIMessage() {} // overridden in subtype\n" +
						"protected boolean hasDisallowedEOL() { return false; } // overridden in subtype\n" +
						"// end of injection");
	}
}
