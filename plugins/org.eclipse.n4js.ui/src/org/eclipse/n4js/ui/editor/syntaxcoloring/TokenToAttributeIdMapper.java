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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.CommercialAt;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.CommercialAtCommercialAt;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_IDENTIFIER;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_JSX_TEXT;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_REGEX_START;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_REGEX_TAIL;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_CONTINUATION;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_END;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_HEAD;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

import com.google.inject.Singleton;

/**
 * Maps the token types to highlighting styles.
 */
@Singleton
public class TokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper implements PseudoTokens {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		switch (tokenType) {
		case RULE_TEMPLATE_HEAD:
		case RULE_TEMPLATE_END:
		case RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL:
			return HighlightingConfiguration.TEMPLATE_ID;
		case RULE_TEMPLATE_CONTINUATION:
			return HighlightingConfiguration.TEMPLATE_DELIMITER_ID;
		case RULE_JSX_TEXT:
			return HighlightingConfiguration.JSX_TEXT_ID;
		case RULE_REGEX_START:
		case RULE_REGEX_TAIL:
			return HighlightingConfiguration.REGEX_ID;
		case CommercialAt:
		case CommercialAtCommercialAt:
			return HighlightingConfiguration.ANNOTATION_ID;
		case TYPE_REF_TOKEN:
			return HighlightingConfiguration.TYPE_REF_ID;
		case NUMBER_LITERAL_TOKEN:
			return DefaultHighlightingConfiguration.NUMBER_ID;
		case RULE_IDENTIFIER:
			return HighlightingConfiguration.IDENTIFIER_ID;
		case IDENTIFIER_REF_TOKEN:
			return HighlightingConfiguration.IDENTIFIER_REF_ID;
		case TYPE_VARIABLE_TOKEN:
			return HighlightingConfiguration.TYPE_VARIABLE_ID;
		case JS_DOC_TOKEN:
			return HighlightingConfiguration.JS_DOC_ID;
		case ES5_KW_TOKEN:
			return HighlightingConfiguration.ES5_KW_ID;
		case ES6_KW_TOKEN:
			return HighlightingConfiguration.ES6_KW_ID;
		case FUTURE_KW_TOKEN:
			return HighlightingConfiguration.FUTURE_KW_ID;
		case NULL_TOKEN:
			return HighlightingConfiguration.NULL_LITERAL_ID;
		case BOOLEAN_TOKEN:
			return HighlightingConfiguration.BOOLEAN_LITERAL_ID;
		case N4JS_KW_TOKEN:
			return HighlightingConfiguration.N4JS_KW_ID;
		default:
			return super.calculateId(tokenName, tokenType);
		}
	}

	@Override
	public String getId(int tokenType) {
		// make sure we don't throw ArrayIndexOutOfBounds in the supertype
		if (tokenType >= PseudoTokens.PSEUDO_TOKEN_START) {
			return calculateId(null, tokenType);
		}
		return super.getId(tokenType);
	}
}
