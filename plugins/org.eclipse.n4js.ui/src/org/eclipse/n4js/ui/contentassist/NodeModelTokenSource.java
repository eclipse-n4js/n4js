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
package org.eclipse.n4js.ui.contentassist;

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED;

import java.util.Collections;
import java.util.Iterator;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.util.Strings;

/**
 * An implementation of an Antlr {@link TokenSource} that is backed by a previously parsed node model. The leafs are
 * iterated and their grammar elements are used to produce a token sequence. These tokens are minimal in the sense that
 * they only carry a proper token type but not a text, index, start or end. This minimal information is sufficient for
 * the content assist parser.
 */
public class NodeModelTokenSource implements TokenSource {

	private Iterator<ILeafNode> leafNodes;
	private Token next;
	private final ContentAssistTokenTypeMapper tokenTypeMapper;
	private final Keyword semicolon;
	private final Keyword rightCurlyInBlock;
	private final Keyword rightCurlyInArrowExpression;
	/**
	 * The start of the requested region (inclusive).
	 */
	private final int startOffset;
	/**
	 * The end of the requested region (exclusive).
	 */
	private final int endOffset;

	NodeModelTokenSource(INode node, ContentAssistTokenTypeMapper tokenTypeMapper, N4JSGrammarAccess grammarAccess) {
		this(node, 0, Integer.MAX_VALUE, tokenTypeMapper, grammarAccess);
	}

	NodeModelTokenSource(INode node, int startOffset, int endOffset, ContentAssistTokenTypeMapper tokenTypeMapper,
			N4JSGrammarAccess grammarAccess) {
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.tokenTypeMapper = tokenTypeMapper;
		this.leafNodes = node.getLeafNodes().iterator();
		this.rightCurlyInBlock = grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2();
		this.rightCurlyInArrowExpression = grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2();
		this.semicolon = grammarAccess.getSemiAccess().getSemicolonKeyword();
	}

	/**
	 * Implementation of the {@link TokenSource} interface. Return new tokens as long as there are some, afterwards
	 * return {@link Token#EOF_TOKEN}.
	 */
	@Override
	public Token nextToken() {
		if (next != null) {
			Token result = next;
			next = null;
			return result;
		}
		if (!leafNodes.hasNext()) {
			return Token.EOF_TOKEN;
		}
		ILeafNode leaf = leafNodes.next();
		if (leaf.getTotalOffset() >= endOffset) {
			leafNodes = Collections.emptyIterator();
			return Token.EOF_TOKEN;
		}
		if (leaf.getTotalEndOffset() <= startOffset) {
			return nextToken();
		}
		if (leaf.getTotalEndOffset() > endOffset) {
			return toPrefixToken(leaf);
		}
		SyntaxErrorMessage syntaxErrorMessage = leaf.getSyntaxErrorMessage();
		if (syntaxErrorMessage != null && SEMICOLON_INSERTED.equals(syntaxErrorMessage.getIssueCode())) {
			return toASIToken(leaf);
		}
		if (leaf.isHidden()) {
			return processHiddenToken(leaf);
		}
		int tokenType = tokenTypeMapper.getInternalTokenType(leaf);
		return new CommonToken(tokenType, leaf.getText());
	}

	/**
	 * Skips the given leaf as it's hidden. If it was the last token to be returned, a hidden token may be syntesized if
	 * would affect the semicolon insertion.
	 */
	private Token processHiddenToken(ILeafNode leaf) {
		Token result = nextToken();
		if (result == Token.EOF_TOKEN && Strings.countLineBreaks(leaf.getText()) > 0) {
			next = result;
			CommonToken hidden = new CommonToken(tokenTypeMapper.getInternalTokenType(leaf), leaf.getText());
			hidden.setChannel(Token.HIDDEN_CHANNEL);
			return hidden;
		}
		return result;
	}

	/**
	 * Produces either one or two tokens from the given leaf which represents a location where the production parser
	 * inserted a semicolon.
	 */
	private Token toASIToken(ILeafNode leaf) {
		if (leaf.isHidden()) {
			return newSemicolonToken(leaf);
		} else {
			if (!leafNodes.hasNext()) {
				int tokenType = tokenTypeMapper.getInternalTokenType(leaf);
				int semicolonTokenType = tokenTypeMapper.getInternalTokenType(semicolon);
				if (tokenType == semicolonTokenType) {
					return new CommonToken(semicolonTokenType, leaf.getText());
				}
				if (leaf.getTotalEndOffset() == endOffset) {
					leafNodes = Collections.emptyIterator();
					return new CommonToken(tokenType, leaf.getText());
				}
				next = new CommonToken(semicolonTokenType, leaf.getText());
				return new CommonToken(tokenType, leaf.getText());
			} else if (leaf.getGrammarElement() == rightCurlyInBlock
					|| leaf.getGrammarElement() == rightCurlyInArrowExpression) {
				int tokenType = tokenTypeMapper.getInternalTokenType(leaf);
				next = new CommonToken(tokenType);
				return new CommonToken(tokenTypeMapper.getInternalTokenType(semicolon), leaf.getText());
			} else {
				return newSemicolonToken(leaf);
			}
		}
	}

	/**
	 * Creates a new semicolon token with the text of the given leaf. It is put on the default channel.
	 */
	private Token newSemicolonToken(ILeafNode leaf) {
		int tokenType = tokenTypeMapper.getInternalTokenType(semicolon);
		return new CommonToken(tokenType, leaf.getText());
	}

	/**
	 * Produce an Antlr token for the prefix of the given leaf that overlaps the requested region
	 *
	 * @see #endOffset
	 */
	private Token toPrefixToken(ILeafNode leaf) {
		Lexer lexer = new InternalN4JSLexer();
		String text = leaf.getText();
		String prefix = text.substring(0, endOffset - leaf.getTotalOffset());
		ANTLRStringStream stream = new ANTLRStringStream(prefix);
		lexer.setCharStream(stream);
		Token nextToken = lexer.nextToken();
		// copy to get rid of the reference to the stream again
		return new CommonToken(nextToken.getType(), nextToken.getText());
	}

	@Override
	public String getSourceName() {
		return "NodeModelTokenSource";
	}

}
