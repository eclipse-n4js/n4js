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
package org.eclipse.n4js.tests;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.XtextParametrizedRunner;
import org.eclipse.n4js.XtextParametrizedRunner.ParametersProvider;
import org.eclipse.n4js.parser.AntlrStreamWithToString;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.parser.antlr.Lexer;
import org.eclipse.xtext.parser.antlr.LexerBindings;
import org.eclipse.xtext.testing.InjectWith;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Check that there is no keyword prefix that will bail out from the lexer
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextParametrizedRunner.class)
@FinalFieldsConstructor
public class KeywordPrefixTest {

	@Inject
	@Named(LexerBindings.RUNTIME)
	Provider<Lexer> lexerProvider;

	String keywordValue;

	public KeywordPrefixTest(String keywordValue) {
		this.keywordValue = keywordValue;
	}

	@ParametersProvider(name = "{0}")
	public static Provider<Iterable<Object[]>> getKeywords() {
		return new Provider<>() {
			@Inject
			N4JSGrammarAccess grammarAccess;

			@Override
			public Iterable<Object[]> get() {
				var keywords = GrammarUtil.getAllKeywords(grammarAccess.getGrammar());
				return map(filter(keywords, (k -> k.length() > 1)), (it) -> new Object[] { it });
			}
		};
	}

	@Test
	public void testAllPrefixes() throws IOException {
		for (int i = 1; i < keywordValue.length(); i++) {
			String prefix = keywordValue.substring(0, i);
			Lexer lexer = lexerProvider.get();
			lexer.setCharStream(new AntlrStreamWithToString(new StringReader(prefix)));
			CommonTokenStream tokenStream = new CommonTokenStream(lexer);
			for (Object token : tokenStream.getTokens()) {
				Assert.assertFalse(prefix + " - " + keywordValue, ((CommonToken) token).getType() == 0);
			}
		}
	}

}
