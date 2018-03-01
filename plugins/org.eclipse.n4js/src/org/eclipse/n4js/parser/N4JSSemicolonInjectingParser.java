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
package org.eclipse.n4js.parser;

import java.io.IOException;
import java.io.Reader;

import org.antlr.runtime.TokenSource;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.n4js.parser.antlr.N4JSParser;
import org.eclipse.n4js.parser.antlr.internal.InternalN4JSParser;
import org.eclipse.n4js.validation.N4JSJavaScriptVariantHelper;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

/**
 * <p>
 * This specialization of the {@link N4JSParser} changes the handling of hidden tokens. Effectively the changeable
 * hidden tokens are disabled in favor of automatic semicolon injection.
 * </p>
 */
public class N4JSSemicolonInjectingParser extends N4JSParser {

	/** The N4JS-variant to parse. */
	private String variant = N4JSJavaScriptVariantHelper.EXT_N4JS;

	/**
	 * Disable partial parsing.
	 */
	@Override
	protected boolean isReparseSupported() {
		return false;
	}

	/**
	 * @param stream
	 *            the given stream, must be a {@link LazyTokenStream}
	 * @see #createTokenStream(TokenSource)
	 */
	@Override
	protected InternalN4JSParser createParser(XtextTokenStream stream) {
		if (!(stream instanceof LazyTokenStream)) {
			throw new IllegalArgumentException("token stream must be an instance of LazyTokenStream");
		}
		return new InternalSemicolonInjectingParser(stream, getGrammarAccess());
	}

	/**
	 * Use a custom {@link AntlrStreamWithToString} rather than the default stream.
	 */
	@Override
	public IParseResult doParse(Reader reader) {
		try {
			return parse(getDefaultRuleName(), new AntlrStreamWithToString(reader));
		} catch (IOException e) {
			throw new WrappedException(e);
		}
	}

	/**
	 * Creates a custom {@link XtextTokenStream} which does not fill its buffer eager but pauses on occurrences of the
	 * {@code '/'}.
	 */
	@Override
	protected LazyTokenStream createTokenStream(TokenSource tokenSource) {
		TokenSource source = tokenSource;

		if (!variant.equals(N4JSJavaScriptVariantHelper.EXT_N4IDL)) {
			source = new N4IDLKeywordFilteringTokenSource(source);
		}

		return new LazyTokenStream(source, getTokenDefProvider());
	}

	/**
	 * Sets the N4JS-variant this parser expects.
	 *
	 * Default is {@link N4JSJavaScriptVariantHelper#EXT_N4JS}.
	 */
	protected void setVariant(String variant) {
		this.variant = variant;
	}

}
