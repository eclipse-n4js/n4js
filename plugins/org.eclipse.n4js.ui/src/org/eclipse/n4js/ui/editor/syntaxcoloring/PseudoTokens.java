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

/**
 * Defines token types that are not produced by Antlr but are assigned in the {@link TokenTypeRewriter}.
 */
public interface PseudoTokens {
	/**
	 * Pointer to the biggest normal token type (highest ORD value).
	 */
	int PSEUDO_TOKEN_START = InternalN4JSParser.RULE_ANY_OTHER + 1;

	/**
	 * Token type for type references.
	 */
	int TYPE_REF_TOKEN = PSEUDO_TOKEN_START;
	/**
	 * Token type for number literals.
	 */
	int NUMBER_LITERAL_TOKEN = TYPE_REF_TOKEN + 1;
	/**
	 * Token type for identifier references.
	 */
	int IDENTIFIER_REF_TOKEN = NUMBER_LITERAL_TOKEN + 1;
	/**
	 * Token type for type variable declarations.
	 */
	int TYPE_VARIABLE_TOKEN = IDENTIFIER_REF_TOKEN + 1;
	/**
	 * Token type for JS Doc comments.
	 */
	int JS_DOC_TOKEN = TYPE_VARIABLE_TOKEN + 1;
	/**
	 * Token type for ES5 keywords.
	 */
	int ES5_KW_TOKEN = JS_DOC_TOKEN + 1;
	/**
	 * Token type for ES6 keywords.
	 */
	int ES6_KW_TOKEN = ES5_KW_TOKEN + 1;
	/**
	 * Token type for ES6 keywords.
	 */
	int FUTURE_KW_TOKEN = ES6_KW_TOKEN + 1;
	/**
	 * Token type for 'null' keyword.
	 */
	int NULL_TOKEN = FUTURE_KW_TOKEN + 1;
	/**
	 * Token type for boolean 'true' / 'false' keywords.
	 */
	int BOOLEAN_TOKEN = NULL_TOKEN + 1;
	/**
	 * Token type for N4JS keywords.
	 */
	int N4JS_KW_TOKEN = BOOLEAN_TOKEN + 1;
}
