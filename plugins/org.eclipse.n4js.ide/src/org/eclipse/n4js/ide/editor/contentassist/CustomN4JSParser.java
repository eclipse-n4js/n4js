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

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser;
import org.eclipse.n4js.ide.contentassist.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Condition;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.NamedArgument;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.UnorderedGroup;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IPartialContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.LookAheadTerminal;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ObservableXtextTokenStream;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.InfiniteRecursion;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.ParseResult;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper;
import org.eclipse.xtext.xtext.ConditionEvaluator;
import org.eclipse.xtext.xtext.ParameterConfigHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.inject.Inject;

/**
 * Specialized content assist parser entry point. Rather than parsing text from scratch, it uses an Antlr
 * {@link TokenSource} that is recreated from the node model which was produced by the 'real' parser. That one is aware
 * of regular expression literals and automatically inserted semicolons. The {@link CustomN4JSParser} piggy backs on
 * that functionality so it doesn't need to recompute all that stuff. This allows to stick to default generated content
 * assist parser.
 */
@SuppressWarnings("restriction")
public class CustomN4JSParser extends N4JSParser implements IPartialContentAssistParser {

	/**
	 * Overridden to make protected member accessible from within the {@link CustomN4JSParser}.
	 */
	protected static class CustomInternalN4JSParser extends InternalN4JSParser {

		/**
		 * Default constructor.
		 */
		public CustomInternalN4JSParser() {
			super(null);
		}

		/**
		 * Allows to access the protected state of the super type.
		 */
		public RecognizerSharedState getState() {
			return state;
		}

	}

	/**
	 * Create our {@link CustomInternalN4JSParser} instead of the plain {@link InternalN4JSParser} for visibility
	 * reasons.
	 */
	@Override
	protected CustomInternalN4JSParser createParser() {
		CustomInternalN4JSParser result = new CustomInternalN4JSParser();
		result.setGrammarAccess(getGrammarAccess());
		return result;
	}

	/**
	 * Workaround for Xpect which messes with the injectors and singletons.
	 *
	 * Rather than trying to find the rule name in the cached instance of ruleNames, we invoke the entry rule directly.
	 */
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			InternalN4JSParser casted = (InternalN4JSParser) parser;
			casted.entryRuleScript();
			return casted.getFollowElements();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private TokenSourceFactory tokenSourceFactory;

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Inject
	private ParamAwareEntryPointFinder entryPointFinder;

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
	 * @param parseResult
	 *            the parse result
	 * @param startOffset
	 *            the start offset to consider
	 * @param endOffset
	 *            the exclusive end offset
	 * @param strict
	 *            if true the parser will not use error recovery on the very last token of the input.
	 * @return a collection of follow elements.
	 */
	public Collection<FollowElement> getFollowElements(IParseResult parseResult, int startOffset, int endOffset,
			boolean strict) {
		return getFollowElements(parseResult.getRootNode(), startOffset, endOffset, strict);
	}

	/* This is run in parallel thus it cannot be easily tracked with a data collector */
	@Override
	public Collection<FollowElement> getFollowElements(IParseResult parseResult, int offset, boolean strict) {
		try (Measurement m = dataCollectors.dcParseContexts().getMeasurement()) {
			Set<FollowElement> result = Sets.newLinkedHashSet();
			ICompositeNode entryPoint = entryPointFinder.findEntryPoint(parseResult, offset);
			String ruleName;
			if (entryPoint == null) {
				entryPoint = parseResult.getRootNode();
				ruleName = getRuleNames().getAntlrRuleName(getEntryRule());
			} else {
				ruleName = getRuleName(entryPoint);
			}
			TokenSource tokenSource = tokenSourceFactory.toTokenSource(entryPoint, entryPoint.getOffset(), offset,
					true);
			CustomInternalN4JSParser parser = collectFollowElements(tokenSource, ruleName, strict, result);
			adjustASIAndCollectFollowElements(parser, ruleName, strict, result);

			/*
			 * Lists are easier to debug
			 */
			return Lists.newArrayList(result);
		}
	}

	String getRuleName(ICompositeNode entryPoint) {
		EObject grammarElement = entryPoint.getGrammarElement();
		if (grammarElement instanceof RuleCall) {
			RuleCall rc = (RuleCall) grammarElement;
			AbstractRule rule = rc.getRule();
			if (rule instanceof ParserRule) {
				if (!GrammarUtil.isMultipleCardinality(rule.getAlternatives())) {
					grammarElement = rule.getAlternatives();
					String[][] names = getRequiredRuleNames(getRuleName((AbstractElement) grammarElement),
							Ints.asList(getParamConfig(entryPoint)),
							(AbstractElement) grammarElement);
					return names[0][0];
				}
			}
		}
		if (grammarElement instanceof ParserRule) {
			return getRuleName(((ParserRule) grammarElement).getAlternatives());
		}
		AbstractElement result = (AbstractElement) grammarElement;
		if (result instanceof Action) {
			return getRuleName((ICompositeNode) entryPoint.getFirstChild());
		}
		return getRuleName(result);
	}

	int getParamConfig(ICompositeNode node) {
		List<RuleCall> callStack = new ArrayList<>();
		recreateCallStack(node, callStack);
		Set<Parameter> params = getParamConfig(callStack);
		int result = ParameterConfigHelper.getParameterConfig(params);
		return result;
	}

	private Set<Parameter> getParamConfig(List<RuleCall> callStack) {
		Set<Parameter> assignedParams = new HashSet<>();
		for (int i = callStack.size() - 1; i >= 0; i--) {
			RuleCall rc = callStack.get(i);
			ConditionEvaluator evaluator = new ConditionEvaluator(assignedParams);
			assignedParams = new HashSet<>();
			for (NamedArgument arg : rc.getArguments()) {
				Condition value = arg.getValue();
				if (evaluator.evaluate(value)) {
					assignedParams.add(arg.getParameter());
				}
			}
		}
		return assignedParams;
	}

	void recreateCallStack(ICompositeNode node, List<RuleCall> stack) {
		EObject element = node.getGrammarElement();
		if (element instanceof RuleCall) {
			RuleCall rc = (RuleCall) element;
			if (rc.getArguments().isEmpty()) {
				return;
			}
			stack.add(rc);
			recreateCallStack(node.getParent(), stack);
		} else if (element instanceof Action) {
			INode firstChild = node.getFirstChild();
			while (firstChild.getGrammarElement() instanceof Action) {
				firstChild = ((ICompositeNode) firstChild).getFirstChild();
			}
			element = firstChild.getGrammarElement();
			if (element instanceof RuleCall) {
				RuleCall rc = (RuleCall) element;
				if (rc.getArguments().isEmpty()) {
					return;
				}
				stack.add(rc);
				recreateCallStack(node.getParent(), stack);
			}
		}
	}

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
		return getFollowElements(new ParseResult(null, (ICompositeNode) node, false), endOffset, strict);
	}

	/**
	 * The second pass over the given input. If the input ends with an ASI, the semicolon is removed and it is parsed
	 * again since the production may have skipped the ASI if more input was present. Same applies for the opposite
	 * direction, e.g if it does not end with an ASI but the prev token suggests that there may have been a semicolon,
	 * it is inserted.
	 */
	private void adjustASIAndCollectFollowElements(CustomInternalN4JSParser previousParser,
			String ruleName,
			boolean strict,
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
					result.addAll(resetAndGetFollowElements(tokens, ruleName, strict));
					// If a postfix operator sneaked into the result, remove it since
					// we'd have produce an ASI before that
					removePostfixOperator(result);
				}
			} else if (shouldAddSyntheticSemicolon(previousParser, lastTokenIndex, lastToken)) {
				CommonToken token = new CommonToken(semi);
				tokenList.add(token);
				result.addAll(resetAndGetFollowElements(tokens, ruleName, strict));
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
	private boolean shouldAddSyntheticSemicolon(CustomInternalN4JSParser previousParser, int lastTokenIndex,
			CommonToken lastNonHiddenToken) {
		return lastTokenIndex != lastNonHiddenToken.getTokenIndex()
				&& previousParser.getState().lastErrorIndex != lastNonHiddenToken.getTokenIndex();
	}

	/**
	 * Create a fresh parser instance and process the tokens for a second pass.
	 */
	private Collection<FollowElement> resetAndGetFollowElements(ObservableXtextTokenStream tokens,
			String ruleName, boolean strict) {
		CustomInternalN4JSParser parser = createParser();
		parser.setStrict(strict);
		tokens.reset();
		return doGetFollowElements(parser, tokens, ruleName);
	}

	/**
	 * First pass. Use the tokens that have been computed from the production parser's result and collect the follow
	 * elements from those.
	 */
	private CustomInternalN4JSParser collectFollowElements(
			TokenSource tokens,
			String ruleName,
			boolean strict,
			Set<FollowElement> result) {
		CustomInternalN4JSParser parser = createParser();
		parser.setStrict(strict);
		try {
			ObservableXtextTokenStream tokenStream = new ObservableXtextTokenStream(tokens, parser);
			result.addAll(doGetFollowElements(parser, tokenStream, ruleName));
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
	private Collection<FollowElement> doGetFollowElements(
			AbstractInternalContentAssistParser parser,
			ObservableXtextTokenStream tokens,
			String ruleName) {
		tokens.setInitialHiddenTokens(getInitialHiddenTokens());
		parser.setTokenStream(tokens);
		IUnorderedGroupHelper helper = getUnorderedGroupHelper().get();
		parser.setUnorderedGroupHelper(helper);
		helper.initializeWith(parser);
		tokens.setListener(parser);
		Collection<FollowElement> followElements = getFollowElements(parser, ruleName);
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
	 * Public setter to allow easier testing.
	 */
	public void setDataCollectors(ContentAssistDataCollectors dataCollectors) {
		this.dataCollectors = dataCollectors;
	}

	/**
	 * Public getter to be symmetric to the setter.
	 */
	public ContentAssistDataCollectors getDataCollectors() {
		return dataCollectors;
	}

	/**
	 * Public setter to allow easier testing.
	 */
	public void setEntryPointFinder(ParamAwareEntryPointFinder entryPointFinder) {
		this.entryPointFinder = entryPointFinder;
	}

	/**
	 * Public getter to be symmetric to the setter.
	 */
	public ParamAwareEntryPointFinder getEntryPointFinder() {
		return entryPointFinder;
	}

	/**
	 * Initializes some local fields from the given grammarAcess and the given mapper.
	 *
	 * Public for testing purpose.
	 */
	@Inject
	public void initializeTokenTypes(ContentAssistTokenTypeMapper mapper, N4JSGrammarAccess grammarAccess) {
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

	@SuppressWarnings("hiding")
	@Override
	public Collection<FollowElement> getFollowElements(FollowElement element) {
		try (Measurement m = dataCollectors.dcParseFollowElements().getMeasurement()) {
			if (element.getLookAhead() <= 1)
				throw new IllegalArgumentException("lookahead may not be less than or equal to 1");
			Collection<FollowElement> result = new ArrayList<>();
			Collection<AbstractElement> elementsToParse = getElementsToParse(element);
			for (AbstractElement elementToParse : elementsToParse) {
				// fix is here
				elementToParse = unwrapSingleElementGroups(elementToParse);
				// done
				String ruleName = getRuleName(elementToParse);
				String[][] allRuleNames = getRequiredRuleNames(ruleName, element.getParamStack(),
						elementToParse);
				for (String[] ruleNames : allRuleNames) {
					for (int i = 0; i < ruleNames.length; i++) {
						AbstractInternalContentAssistParser parser = createParser();
						parser.setUnorderedGroupHelper(getUnorderedGroupHelper().get());
						parser.getUnorderedGroupHelper().initializeWith(parser);
						final Iterator<LookAheadTerminal> iter = element.getLookAheadTerminals().iterator();
						ObservableXtextTokenStream tokens = new ObservableXtextTokenStream(new TokenSource() {
							@Override
							public Token nextToken() {
								if (iter.hasNext()) {
									LookAheadTerminal lookAhead = iter.next();
									return lookAhead.getToken();
								}
								return Token.EOF_TOKEN;
							}

							@Override
							public String getSourceName() {
								return "LookAheadTerminalTokenSource";
							}
						}, parser);
						parser.setTokenStream(tokens);
						tokens.setListener(parser);
						parser.getGrammarElements().addAll(element.getTrace());
						parser.getGrammarElements().add(elementToParse);
						parser.getLocalTrace().addAll(element.getLocalTrace());
						parser.getLocalTrace().add(elementToParse);
						parser.getParamStack().addAll(element.getParamStack());
						if (elementToParse instanceof UnorderedGroup && element.getGrammarElement() == elementToParse) {
							UnorderedGroup group = (UnorderedGroup) elementToParse;
							final IUnorderedGroupHelper helper = parser.getUnorderedGroupHelper();
							helper.enter(group);
							for (AbstractElement consumed : element.getHandledUnorderedGroupElements()) {
								parser.before(consumed);
								helper.select(group, group.getElements().indexOf(consumed));
								helper.returnFromSelection(group);
								parser.after(consumed);
							}
							parser.setUnorderedGroupHelper(new IUnorderedGroupHelper() {

								boolean first = true;

								@Override
								public void initializeWith(BaseRecognizer recognizer) {
									helper.initializeWith(recognizer);
								}

								@Override
								public void enter(UnorderedGroup group) {
									if (!first)
										helper.enter(group);
									first = false;
								}

								@Override
								public void leave(UnorderedGroup group) {
									helper.leave(group);
								}

								@Override
								public boolean canSelect(UnorderedGroup group, int index) {
									return helper.canSelect(group, index);
								}

								@Override
								public void select(UnorderedGroup group, int index) {
									helper.select(group, index);
								}

								@Override
								public void returnFromSelection(UnorderedGroup group) {
									helper.returnFromSelection(group);
								}

								@Override
								public boolean canLeave(UnorderedGroup group) {
									return helper.canLeave(group);
								}

								@Override
								public UnorderedGroupState snapShot(UnorderedGroup... groups) {
									return helper.snapShot(groups);
								}

							});
						}
						Collection<FollowElement> elements = getFollowElements(parser, elementToParse,
								ruleNames, i);
						result.addAll(elements);
					}
				}
			}
			return result;
		}
	}
}
