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

import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * Highlighting configuration for N4JS.
 */
public class HighlightingConfiguration extends DefaultHighlightingConfiguration {

	/**
	 * Style ident for reg ex literals.
	 */
	public static final String REGEX_ID = "REGEX_ID";
	/**
	 * Style ident for template text.
	 */
	public static final String TEMPLATE_ID = "TEMPLATE_ID";
	/**
	 * Style ident for template text.
	 */
	public static final String JSX_TEXT_ID = "JSX_TEXT_ID";
	/**
	 * Style ident for template delimiter, e.g. ${ and }
	 */
	public static final String TEMPLATE_DELIMITER_ID = "TEMPLATE_DELIMITER_ID";
	/**
	 * Style ident for annotation references.
	 */
	public static final String ANNOTATION_ID = "ANNOTATION_ID";
	/**
	 * Style ident for type references.
	 */
	public static final String TYPE_REF_ID = "TYPE_REF_ID";
	/**
	 * Style ident for type references.
	 */
	public static final String IDENTIFIER_ID = "IDENTIFIER_ID";
	/**
	 * Style ident for type references.
	 */
	public static final String IDENTIFIER_REF_ID = "IDENTIFIER_REF_ID";
	/**
	 * Style ident for type variable declarations.
	 */
	public static final String TYPE_VARIABLE_ID = "TYPE_VARIABLE_ID";
	/**
	 * Style ident for JS doc comments.
	 */
	public static final String JS_DOC_ID = "JS_DOC_ID";
	/**
	 * Style ident for ES5 keywords.
	 */
	public static final String ES5_KW_ID = "ES5_KW_ID";
	/**
	 * Style ident for ES6 keywords.
	 */
	public static final String ES6_KW_ID = "ES6_KW_ID";
	/**
	 * Style ident for future reserved keywords.
	 */
	public static final String FUTURE_KW_ID = "FUTURE_KW_ID";
	/**
	 * Style ident for null literals.
	 */
	public static final String NULL_LITERAL_ID = "NULL_LITERAL_ID";
	/**
	 * Style ident for boolean literals.
	 */
	public static final String BOOLEAN_LITERAL_ID = "BOOLEAN_LITERAL_ID";
	/**
	 * Style ident for N4JS keywords.
	 */
	public static final String N4JS_KW_ID = "N4JS_KW_ID";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		// acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation character", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(TASK_ID, "Task Tag", taskTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());
		acceptor.acceptDefaultHighlighting(REGEX_ID, "Regular Expression", regExTextStyle());
		acceptor.acceptDefaultHighlighting(JSX_TEXT_ID, "JSX Text", literalTextStyle());
		acceptor.acceptDefaultHighlighting(TEMPLATE_ID, "Template Text", literalTextStyle());
		acceptor.acceptDefaultHighlighting(TEMPLATE_DELIMITER_ID, "Template Delimiter", templateDelimiterTextStyle());
		acceptor.acceptDefaultHighlighting(ANNOTATION_ID, "Annotation", annotationTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_REF_ID, "Type Reference", typeRefTextStyle());
		acceptor.acceptDefaultHighlighting(IDENTIFIER_ID, "Identifier", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(IDENTIFIER_REF_ID, "Reference", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(TYPE_VARIABLE_ID, "Type Variable", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(JS_DOC_ID, "JSDoc Comment", jsDocTextStyle());
		acceptor.acceptDefaultHighlighting(ES5_KW_ID, "ES5 Keywords", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(ES6_KW_ID, "ES6 Keywords", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(FUTURE_KW_ID, "Reserved words", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(N4JS_KW_ID, "N4JS Keywords", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(NULL_LITERAL_ID, "Null literals", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(BOOLEAN_LITERAL_ID, "Boolean literals", keywordTextStyle());
	}

	/**
	 * Default style for annotations, colored magenta.
	 */
	private TextStyle annotationTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 0, 255));
		return textStyle;
	}

	/**
	 * Default style for type references, colored dark grey.
	 */
	private TextStyle typeRefTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(140, 140, 140));
		return textStyle;
	}

	/**
	 * Default style for all reg-ex literals, colored orange.
	 */
	public TextStyle regExTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 128, 64)); // orange
		// textStyle.setBackgroundColor(new RGB(233, 242, 248)); // very light blue/greenish thing
		return textStyle;
	}

	/**
	 * Default style for template delimiters, colored orange.
	 */
	public TextStyle templateDelimiterTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 183, 51)); // light orange
		// textStyle.setBackgroundColor(new RGB(233, 242, 248)); // very light blue/greenish thing
		return textStyle;
	}

	/**
	 * Default style for number and string literals, colored blue.
	 */
	public TextStyle literalTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 255));
		return textStyle;
	}

	/**
	 * Default style for number and string literals, colored blue.
	 */
	public TextStyle jsDocTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(90, 139, 227));
		return textStyle;
	}

	@Override
	public TextStyle numberTextStyle() {
		return literalTextStyle();
	}

}
