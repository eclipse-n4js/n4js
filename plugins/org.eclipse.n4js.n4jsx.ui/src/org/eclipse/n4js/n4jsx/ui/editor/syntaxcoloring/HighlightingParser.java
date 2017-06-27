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
package org.eclipse.n4js.n4jsx.ui.editor.syntaxcoloring;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xtext.parser.ParseException;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.parser.antlr.LexerBindings;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.eclipse.n4js.n4jsx.parser.LazyTokenStream;
import org.eclipse.n4js.n4jsx.services.N4JSXGrammarAccess;
import org.eclipse.n4js.parser.AntlrStreamWithToString;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeRewriter;

/**
 * Facade for the highlighting parser.
 */
// copy of org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingParser
@Singleton
public class HighlightingParser {

	private static final Logger LOGGER = Logger.getLogger(HighlightingParser.class);

	@Inject
	private ITokenDefProvider tokenDefProvider;

	@Inject
	private Provider<IUnorderedGroupHelper> unorderedGroupHelper;

	@Inject
	@Named(LexerBindings.RUNTIME)
	private Provider<Lexer> lexerProvider;

	@Inject
	private N4JSXGrammarAccess grammarAccess;

	@Inject
	private TokenTypeRewriter rewriter;

	private void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens(
				InternalN4JSXParser.tokenNames[InternalN4JSXParser.RULE_WS],
				InternalN4JSXParser.tokenNames[InternalN4JSXParser.RULE_ML_COMMENT],
				InternalN4JSXParser.tokenNames[InternalN4JSXParser.RULE_SL_COMMENT],
				InternalN4JSXParser.tokenNames[InternalN4JSXParser.RULE_EOL]);
	}

	/**
	 * Creates a new parser. Protected to allow to override in tests.
	 */
	protected InternalN4JSXParser createParser(LazyTokenStream stream) {
		return new InternalHighlightingParser(stream, getGrammarAccess(), getRewriter());
	}

	/**
	 * Obtain the grammar access. Protected to allow access from subtypes.
	 */
	protected N4JSXGrammarAccess getGrammarAccess() {
		return grammarAccess;
	}

	/**
	 * Obtain the token type rewriter. Protected to allow access from subtypes.
	 */
	protected TokenTypeRewriter getRewriter() {
		return rewriter;
	}

	/**
	 * Obtain the tokens from the given reader.
	 */
	public List<Token> getTokens(Reader reader) {
		try {
			return doParse(new AntlrStreamWithToString(reader));
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	/**
	 * Obtain the tokens from the given input.
	 */
	public List<Token> getTokens(CharSequence input) {
		return getTokens(new StringReader(input.toString()));
	}

	private List<Token> doParse(CharStream in) {
		TokenSource tokenSource = createLexer(in);
		LazyTokenStream tokenStream = createTokenStream(tokenSource);
		setInitialHiddenTokens(tokenStream);
		InternalN4JSXParser parser = createParser(tokenStream);
		IUnorderedGroupHelper helper = unorderedGroupHelper.get();
		if (!(helper instanceof IUnorderedGroupHelper.Null)) {
			throw new IllegalStateException("Unexpected usage of unordered groups.");
		}
		Stopwatch stopwatch = null;
		boolean debug = LOGGER.isDebugEnabled();
		// boolean debug = true;
		if (debug) {
			stopwatch = Stopwatch.createStarted();
		}
		try {
			parser.entryRuleIDLScript();
			while (tokenStream.LT(1) != Token.EOF_TOKEN) {
				tokenStream.consume();
			}
			@SuppressWarnings("unchecked")
			List<Token> result = tokenStream.getTokens();
			return result;
		} catch (Exception re) {
			throw new ParseException(re.getMessage(), re);
		} finally {
			if (debug) {
				assert stopwatch != null;
				long elapsed = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
				if (elapsed > 5) {
					LOGGER.warn("Coloring parser took: " + elapsed);
				}
			}
		}
	}

	/**
	 * Create a new lexer for the given input.
	 */
	protected TokenSource createLexer(CharStream stream) {
		Lexer lexer = lexerProvider.get();
		lexer.setCharStream(stream);
		return lexer;
	}

	/**
	 * Creates a custom {@link XtextTokenStream} which does not fill its buffer eager but pauses on occurrences of the
	 * {@code '/'}.
	 */
	private LazyTokenStream createTokenStream(TokenSource tokenSource) {
		return new LazyTokenStream(tokenSource, tokenDefProvider);
	}

}
