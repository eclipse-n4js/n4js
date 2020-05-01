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
package org.eclipse.n4js.ide.editor.contentassist;

import static org.eclipse.n4js.parser.InternalSemicolonInjectingParser.SEMICOLON_INSERTED;

import java.util.Collections;
import java.util.Iterator;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.nodemodel.impl.AbstractNode;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

/**
 * An implementation of an Antlr {@link TokenSource} that is backed by a previously parsed node model. The leafs are
 * iterated and their grammar elements are used to produce a token sequence. These tokens are minimal in the sense that
 * they only carry a proper token type but not a text, index, start or end. This minimal information is sufficient for
 * the content assist parser.
 *
 * This token source has a built in facality to provide a filtered token stream. The purpose of stream filtering is to
 * remove regions from the file that do not have an impact on the decisions at the end of the requested token region.
 * Thereby the content assist parser must consume only a subset of the tokens and benefits from improved performance and
 * less likely impact by large, unnecessary lookahead.
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
	private final RuleCall scriptElementCall;
	private final RuleCall memberDeclarationCall;
	private final RuleCall statementsCall;
	private final RuleCall propertyAssignmentCall1;
	private final RuleCall propertyAssignmentCall2;

	/**
	 * If set to true, use semantically equivalent replacements for regions, that are not relevant for the given offset
	 * to reduce the number of tokens.
	 */

	NodeModelTokenSource(INode node, ContentAssistTokenTypeMapper tokenTypeMapper, N4JSGrammarAccess grammarAccess,
			boolean filter) {
		this(node, 0, Integer.MAX_VALUE, tokenTypeMapper, grammarAccess, filter);
	}

	NodeModelTokenSource(INode node, int startOffset, int endOffset, ContentAssistTokenTypeMapper tokenTypeMapper,
			N4JSGrammarAccess grammarAccess, boolean filter) {
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.tokenTypeMapper = tokenTypeMapper;

		this.leafNodes = getLeafIterator(((AbstractNode) node).basicIterator(), filter);
		this.rightCurlyInBlock = grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2();
		this.rightCurlyInArrowExpression = grammarAccess.getArrowExpressionAccess().getRightCurlyBracketKeyword_1_0_2();
		this.semicolon = grammarAccess.getSemiAccess().getSemicolonKeyword();
		this.scriptElementCall = grammarAccess.getScriptAccess().getScriptElementsScriptElementParserRuleCall_1_1_0();
		this.memberDeclarationCall = grammarAccess.getMembersAccess()
				.getOwnedMembersRawN4MemberDeclarationParserRuleCall_1_0();
		this.statementsCall = grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_1_0();
		this.propertyAssignmentCall1 = grammarAccess.getObjectLiteralAccess()
				.getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_0_0();
		this.propertyAssignmentCall2 = grammarAccess.getObjectLiteralAccess()
				.getPropertyAssignmentsPropertyAssignmentParserRuleCall_2_1_1_0();
	}

	private Iterator<ILeafNode> getLeafIterator(TreeIterator<AbstractNode> iterator, boolean filter) {
		return Iterators.filter(filter ? filterIterator(iterator) : iterator, ILeafNode.class);
	}

	/**
	 * Filter the nodes from the iterator that do not have any impact on the parse result.
	 *
	 * For now we filter mostly regions that do have lookahead 1 and are closed before the requested region starts.
	 */
	private Iterator<INode> filterIterator(TreeIterator<AbstractNode> iterator) {
		return new AbstractIterator<>() {
			@Override
			protected INode computeNext() {
				if (iterator.hasNext()) {
					INode result = iterator.next();
					if (result instanceof ICompositeNode) {
						ICompositeNode casted = (ICompositeNode) result;
						if (casted.getTotalEndOffset() < endOffset - 1) {
							if (casted.hasChildren() && casted.getLookAhead() == 1) {
								AbstractElement grammarElement = (AbstractElement) casted.getGrammarElement();
								// Filter script elements and member declarations to the left of the cursor position.
								if (grammarElement == scriptElementCall || grammarElement == memberDeclarationCall) {
									INode sibling = casted.getNextSibling();
									while (sibling instanceof ILeafNode) {
										ILeafNode siblingLeaf = (ILeafNode) sibling;
										if (siblingLeaf.isHidden()) {
											if (siblingLeaf.getTotalEndOffset() >= endOffset) {
												return result;
											}
										} else {
											break;
										}
										sibling = siblingLeaf.getNextSibling();
									}
									iterator.prune();

									// filter statements that are completed before the cursor position and are not
									// part of the lookahead
								} else if (grammarElement == statementsCall) {
									// check if this is in the parents lookAhead to disambiguate block from object
									// literal
									ICompositeNode parent = casted.getParent();
									if (parent.getLookAhead() > 1) {
										ILeafNode firstLeaf = Iterables.get(casted.getLeafNodes(), 0);
										int remainingLA = parent.getLookAhead();
										Iterator<ILeafNode> parentLeafs = parent.getLeafNodes().iterator();
										while (parentLeafs.hasNext() && remainingLA > 0) {
											ILeafNode leafNode = parentLeafs.next();
											if (leafNode == firstLeaf) {
												break;
											}
											if (!leafNode.isHidden()) {
												remainingLA--;
												if (remainingLA == 0) {
													iterator.prune();
												}
											}
										}
									}

									// Reduce the size of object literals.
								} else if (grammarElement == propertyAssignmentCall1
										|| grammarElement == propertyAssignmentCall2) {
									iterator.prune();
									Iterator<ILeafNode> localLeafs = casted.getLeafNodes().iterator();
									while (localLeafs.hasNext()) {
										ILeafNode leaf = localLeafs.next();
										if (!leaf.isHidden()) {
											return leaf;
										}
									}
								}
							}
						}
					}
					return result;
				}
				return endOfData();
			}
		};

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
