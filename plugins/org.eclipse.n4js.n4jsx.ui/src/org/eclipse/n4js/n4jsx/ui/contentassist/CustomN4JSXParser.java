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
package org.eclipse.n4js.n4jsx.ui.contentassist;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ObservableXtextTokenStream;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.InfiniteRecursion;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import org.eclipse.n4js.n4jsx.services.N4JSXGrammarAccess;
import org.eclipse.n4js.n4jsx.ui.contentassist.antlr.N4JSXParser;
import org.eclipse.n4js.n4jsx.ui.contentassist.antlr.internal.InternalN4JSXParser;

/**
 * Specialized content assist parser entry point. Rather than parsing text from scratch, it uses an Antlr
 * {@link TokenSource} that is recreated from the node model which was produced by the 'real' parser. That one is aware
 * of regular expression literals and automatically inserted semicolons. The {@link CustomN4JSXParser} piggy backs on
 * that functionality so it doesn't need to recompute all that stuff. This allows to stick to default generated content
 * assist parser.
 */
// copy of org.eclipse.n4js.ui.contentassist.CustomN4JSParser
public class CustomN4JSXParser extends N4JSXParser {

	/**
	 * Overridden to make protected member accessible from within the {@link CustomN4JSXParser}.
	 */
	protected static class CustomInternalN4JSXParser extends InternalN4JSXParser {

		/**
		 * Default constructor.
		 */
		public CustomInternalN4JSXParser() {
			super(null);
		}

		/**
		 * Allows to access the protected state of the super type.
		 */
		public RecognizerSharedState getState() {
			return state;
		}

		/*
		 * TODO temporary override: remove as soon as the applied fix is available in Xtext itself
		 */
		@Override
		public void announceRewind(int marker) {
			int useLookAhead = -1;
			if (marker != 0 && delegate == null && strict && predictionLevel != 0 && lookAheadAddOn > 0
					&& state.syntaxErrors == 0
					&& input.index() == input.size()
					&& marker + lookAheadAddOn <= input.size()
					&& isBacktracking()) {
				useLookAhead = lookAheadAddOn;
				delegate = createNotErrorRecoveryStrategy();
				wasErrorCount = state.syntaxErrors;
			}
			currentMarker = marker;
			lookAheadAddOn = currentMarker - firstMarker;
			if (useLookAhead != -1) {
				if (useLookAhead + firstMarker >= input.index()) {
					announceEof(useLookAhead);
				}
			}
			marked--;
		}

	}

	/**
	 * Create our {@link CustomInternalN4JSXParser} instead of the plain {@link InternalN4JSXParser} for visibility
	 * reasons.
	 */
	@Override
	protected CustomInternalN4JSXParser createParser() {
		CustomInternalN4JSXParser result = new CustomInternalN4JSXParser();
		result.setGrammarAccess(getGrammarAccess());
		return result;
	}

	@Inject
	private TokenSourceFactory tokenSourceFactory;

	/**
	 * Token types that would require a mandatory semicolon if followed by a NL.
	 */
	private BitSet mandatoryASI;

	/**
	 * The token type for the EOL rule. It is used by the {@link NodeModelTokenSource} for hidden tokens at the right
	 * boundary (before the EOF).
	 */
	private int eol;

	/**
	 * The token type for the semicolon.
	 */
	private int semi;

	/**
	 * The grammar element for the postfix operators.
	 */
	private Group postfixGroup;

	/**
	 * @param node
	 *            the root node of the model to parse
	 * @param startOffset
	 *            the start offset to consider
	 * @param endOffset
	 *            the exclusive end offset
	 * @param strict
	 *            if true the parser will not use error recovery on the very last token of the input.
	 * @return a collection of follow elements.
	 */
	public Collection<FollowElement> getFollowElements(INode node, int startOffset, int endOffset, boolean strict) {
		Set<FollowElement> result = Sets.newLinkedHashSet();

		TokenSource tokenSource = tokenSourceFactory.toTokenSource(node, startOffset, endOffset);
		CustomInternalN4JSXParser parser = collectFollowElements(tokenSource, strict, result);
		adjustASIAndCollectFollowElements(parser, strict, result);

		/*
		 * Lists are easier to debug
		 */
		return Lists.newArrayList(result);
	}

	/**
	 * The second pass over the given input. If the input ends with an ASI, the semicolon is removed and it is parsed
	 * again since the production may have skipped the ASI if more input was present. Same applies for the opposite
	 * direction, e.g if it does not end with an ASI but the prev token suggests that there may have been a semicolon,
	 * it is inserted.
	 */
	private void adjustASIAndCollectFollowElements(CustomInternalN4JSXParser previousParser, boolean strict,
			Set<FollowElement> result) {
		ObservableXtextTokenStream tokens = (ObservableXtextTokenStream) previousParser.getTokenStream();
		int lastTokenIndex = tokens.size() - 1;
		if (lastTokenIndex >= 0) {
			CommonToken lastToken = (CommonToken) tokens.LT(-1);
			@SuppressWarnings("unchecked")
			List<Token> tokenList = tokens.getTokens();
			if (lastToken == null) {
				return; // TODO ask Sebastian why and how this can happen... it happens!
			}

			if (shouldSkipASI(lastToken)) {
				// Some statements may not span multiple lines, e.g between the return keyword
				// and the returned expression, there may not be an ASI. Filter these locations.
				if (maySkipASI(lastToken, tokens)) {
					tokenList.remove(lastTokenIndex);
					result.addAll(resetAndGetFollowElements(tokens, strict));
					// If a postfix operator sneaked into the result, remove it since
					// we'd have produce an ASI before that
					removePostfixOperator(result);
				}
			} else if (shouldAddSyntheticSemicolon(previousParser, lastTokenIndex, lastToken)) {
				CommonToken token = new CommonToken(semi);
				tokenList.add(token);
				result.addAll(resetAndGetFollowElements(tokens, strict));
				// Same here, if we had added an ASI, the postfix operator would be rendered
				// invalid, remove it.
				removePostfixOperator(result);
			}
		}
	}

	/**
	 * Filter all follow elements that point to a postfix operator.
	 */
	private void removePostfixOperator(Set<FollowElement> result) {
		Iterator<FollowElement> iter = result.iterator();
		while (iter.hasNext()) {
			FollowElement fe = iter.next();
			if (postfixGroup == fe.getGrammarElement()) {
				iter.remove();
			}
		}
	}

	/**
	 * Prevent ASIs to be skipped at certain locations, e.g. after a return keyword.
	 */
	private boolean maySkipASI(CommonToken lastToken, ObservableXtextTokenStream tokens) {
		int countDownFrom = lastToken.getTokenIndex();
		for (int i = countDownFrom - 1; i >= 0; i--) {
			Token prevToken = tokens.get(i);
			if (prevToken.getChannel() == Token.DEFAULT_CHANNEL) {
				if (mandatoryASI.get(prevToken.getType())) {
					return false;
				}
				return true;
			}
		}
		return true;
	}

	/**
	 * Returns true if the ASI at the end should be skipped for a second pass of the CA parser. Otherwise false.
	 */
	private boolean shouldSkipASI(CommonToken lastToken) {
		if (lastToken.getType() == eol) {
			return true;
		}
		if (lastToken.getType() == semi && lastToken.getText() != null && !";".equals(lastToken.getText())) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if a synthetic ASI token should be added for a second pass. This is usually the case if the input
	 * ends with a NL token.
	 */
	private boolean shouldAddSyntheticSemicolon(CustomInternalN4JSXParser previousParser, int lastTokenIndex,
			CommonToken lastNonHiddenToken) {
		return lastTokenIndex != lastNonHiddenToken.getTokenIndex()
				&& previousParser.getState().lastErrorIndex != lastNonHiddenToken.getTokenIndex();
	}

	/**
	 * Create a fresh parser instance and process the tokens for a second pass.
	 */
	private Collection<FollowElement> resetAndGetFollowElements(ObservableXtextTokenStream tokens, boolean strict) {
		CustomInternalN4JSXParser parser = createParser();
		parser.setStrict(strict);
		tokens.reset();
		return doGetFollowElements(parser, tokens);
	}

	/**
	 * First pass. Use the tokens that have been computed from the production parser's result and collect the follow
	 * elements from those.
	 */
	private CustomInternalN4JSXParser collectFollowElements(TokenSource tokens, boolean strict,
			Set<FollowElement> result) {
		CustomInternalN4JSXParser parser = createParser();
		parser.setStrict(strict);
		try {
			ObservableXtextTokenStream tokenStream = new ObservableXtextTokenStream(tokens, parser);
			result.addAll(doGetFollowElements(parser, tokenStream));
		} catch (InfiniteRecursion infinite) {
			// this guards against erroneous infinite recovery loops in Antlr.
			// Grammar dependent and not expected something that is expected for N4JS
			// is used in the base class thus also used here.
			result.addAll(parser.getFollowElements());
		}
		return parser;
	}

	/**
	 * Initialize the parser properly with the given tokens and process it.
	 */
	private Collection<FollowElement> doGetFollowElements(AbstractInternalContentAssistParser parser,
			ObservableXtextTokenStream tokens) {
		tokens.setInitialHiddenTokens(getInitialHiddenTokens());
		parser.setTokenStream(tokens);
		IUnorderedGroupHelper helper = getUnorderedGroupHelper().get();
		parser.setUnorderedGroupHelper(helper);
		helper.initializeWith(parser);
		tokens.setListener(parser);
		Collection<FollowElement> followElements = getFollowElements(parser);
		return followElements;
	}

	/**
	 * @throws UnsupportedOperationException
	 *             always
	 */
	@Override
	public Collection<FollowElement> getFollowElements(String input, boolean strict) {
		throw new UnsupportedOperationException("Use getFollowElements(INode, int, int, boolean) instead");
	}

	/**
	 * Public setter to allow easier testing.
	 */
	public void setTokenSourceFactory(TokenSourceFactory tokenSourceFactory) {
		this.tokenSourceFactory = tokenSourceFactory;
	}

	/**
	 * Public getter to be symmetric to the setter.
	 */
	public TokenSourceFactory getTokenSourceFactory() {
		return tokenSourceFactory;
	}

	/**
	 * Initializes some local fields from the given grammarAcess and the given mapper.
	 *
	 * Public for testing purpose.
	 */
	@Inject
	public void initializeTokenTypes(ContentAssistTokenTypeMapper mapper, N4JSXGrammarAccess grammarAccess) {
		BitSet bits = new BitSet();
		bits.set(mapper.getInternalTokenType(grammarAccess.getReturnStatementAccess().getReturnKeyword_1()));
		bits.set(mapper.getInternalTokenType(grammarAccess.getThrowStatementAccess().getThrowKeyword_0()));
		bits.set(mapper.getInternalTokenType(grammarAccess.getBreakStatementAccess().getBreakKeyword_1()));
		bits.set(mapper.getInternalTokenType(grammarAccess.getContinueStatementAccess().getContinueKeyword_1()));
		this.mandatoryASI = bits;
		this.eol = mapper.getInternalTokenType(getGrammarAccess().getEOLRule());
		this.semi = mapper.getInternalTokenType(getGrammarAccess().getSemiAccess().getSemicolonKeyword());
		this.postfixGroup = grammarAccess.getPostfixExpressionAccess().getGroup_1();
	}

}
