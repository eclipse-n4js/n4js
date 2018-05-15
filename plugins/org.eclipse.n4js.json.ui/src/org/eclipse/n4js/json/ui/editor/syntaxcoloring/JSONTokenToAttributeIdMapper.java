package org.eclipse.n4js.json.ui.editor.syntaxcoloring;

import org.eclipse.n4js.json.ide.contentassist.antlr.internal.InternalJSONLexer;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

/**
 * Custom token to attribute ID mapper for the syntax highlighting of JSON text.
 */
public class JSONTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper {

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		switch (tokenType) {
		case InternalJSONLexer.RULE_NUMBER:
			return JSONHighlightingConfiguration.NUMBER_ID;
		case InternalJSONLexer.RULE_STRING:
			// At first, classify all string literals as property names. Later
			// in the {@link JSONSemanticHighlightingCalculator}, this is overridden
			// for string literals that appear as values.
			return JSONHighlightingConfiguration.PROPERTY_NAME_ID;
		default:
			return super.calculateId(tokenName, tokenType);
		}
	}

}
