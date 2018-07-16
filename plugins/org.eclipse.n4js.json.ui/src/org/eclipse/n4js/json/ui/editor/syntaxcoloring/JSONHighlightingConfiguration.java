package org.eclipse.n4js.json.ui.editor.syntaxcoloring;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * Custom highlighting configuration for JSON files.
 */
public class JSONHighlightingConfiguration extends DefaultHighlightingConfiguration {
	public static final String PROPERTY_NAME_ID = "propertyName";

	public static final String VALUE_LITERAL_ID = "valueLiteral";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(PROPERTY_NAME_ID, "Property Name", propertyNameTextStyle());
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Boolean/Null Literals", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation character", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());
	}

	/** TextStyle for property names of JSON objects */
	private TextStyle propertyNameTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 0, 85));
		return textStyle;
	}

	/** Configure numbers to appear in the same color as string literals do. */
	@Override
	public TextStyle numberTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(42, 0, 255));
		return textStyle;
	}
}
