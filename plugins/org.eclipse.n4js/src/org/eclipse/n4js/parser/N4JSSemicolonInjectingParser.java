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
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

import com.google.inject.Singleton;

/**
 * <p>
 * This specialization of the {@link N4JSParser} changes the handling of hidden tokens. Effectively the changeable
 * hidden tokens are disabled in favor of automatic semicolon injection.
 * </p>
 */
@Singleton
public class N4JSSemicolonInjectingParser extends N4JSParser {

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
		try (Measurement m = N4JSDataCollectors.dcParser.getMeasurement()) {
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
		return new LazyTokenStream(tokenSource, getTokenDefProvider());
	}

}
