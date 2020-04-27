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

import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_JSX_TEXT;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_REGEX_START;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_REGEX_TAIL;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_CONTINUATION;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_END;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_HEAD;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser.RULE_TEMPLATE_MIDDLE;

import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.inject.Singleton;

/**
 * Maps token types from Antlr or from the {@link PseudoTokens} to document partition types.
 *
 * See {@link org.eclipse.jface.text.IDocumentPartitioner} for details on partitions.
 */
@Singleton
public class TokenTypeToPartitionMapper extends TerminalsTokenTypeToPartitionMapper {

	/**
	 * The partition type for JSDoc comments.
	 */
	public final static String JS_DOC_PARTITION = "__jsdoc";
	/**
	 * The partition type for regular expression literals.
	 */
	public final static String REG_EX_PARTITION = "__regex";
	/**
	 * The partition type for the literal part of template literals.
	 */
	public final static String TEMPLATE_LITERAL_PARTITION = "__template";
	/**
	 * The partition type for the literal part of template literals.
	 */
	public final static String JSX_TEXT_PARTITION = "__jsxtext";

	/**
	 * All supported partition types.
	 */
	public static final String[] SUPPORTED_PARTITION_TYPES = new String[] {
			COMMENT_PARTITION,
			JS_DOC_PARTITION,
			SL_COMMENT_PARTITION,
			REG_EX_PARTITION,
			STRING_LITERAL_PARTITION,
			TEMPLATE_LITERAL_PARTITION,
			JSX_TEXT_PARTITION,
			IDocument.DEFAULT_CONTENT_TYPE
	};

	/**
	 * All {@link PseudoTokens} are considered to be contained in the default partition except for
	 * {@link PseudoTokens#JS_DOC_TOKEN}.
	 */
	@Override
	public String getPartitionType(int antlrTokenType) {
		if (antlrTokenType == PseudoTokens.JS_DOC_TOKEN) {
			return JS_DOC_PARTITION;
		}
		if (antlrTokenType >= PseudoTokens.PSEUDO_TOKEN_START) {
			return IDocument.DEFAULT_CONTENT_TYPE;
		}
		return super.getPartitionType(antlrTokenType);
	}

	@Override
	protected String calculateId(String tokenName, int tokenType) {
		switch (tokenType) {
		case RULE_TEMPLATE_HEAD:
		case RULE_TEMPLATE_MIDDLE:
		case RULE_TEMPLATE_END:
		case RULE_NO_SUBSTITUTION_TEMPLATE_LITERAL:
		case RULE_TEMPLATE_CONTINUATION:
			return TEMPLATE_LITERAL_PARTITION;
		case RULE_REGEX_START:
		case RULE_REGEX_TAIL:
			return REG_EX_PARTITION;
		case RULE_JSX_TEXT:
			return JSX_TEXT_PARTITION;
		}
		return super.calculateId(tokenName, tokenType);
	}

	@Override
	public String[] getSupportedPartitionTypes() {
		return SUPPORTED_PARTITION_TYPES;
	}

	@Override
	public boolean isMultiLineComment(String partitionType) {
		return super.isMultiLineComment(partitionType) || JS_DOC_PARTITION.equals(partitionType);
	}

}
