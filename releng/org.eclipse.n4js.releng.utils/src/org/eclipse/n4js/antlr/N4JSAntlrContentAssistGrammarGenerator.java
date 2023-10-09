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
package org.eclipse.n4js.antlr;

import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrContentAssistGrammarGenerator;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrOptions;

/**
 * Customized content assist grammar generation.
 */
public class N4JSAntlrContentAssistGrammarGenerator extends AntlrContentAssistGrammarGenerator {

	/**
	 * Replace specified extensions with custom implementation for unicode keyword lexer rules
	 */
	@Override
	protected String toAntlrKeywordRule(String keyword, AntlrOptions options) {
		return UnicodeKeywordHelper.toUnicodeKeyword(keyword);
	}
}
