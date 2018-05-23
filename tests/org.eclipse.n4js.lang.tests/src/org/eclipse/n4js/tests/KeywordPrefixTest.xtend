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
package org.eclipse.n4js.tests

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.XtextParametrizedRunner
import org.eclipse.n4js.XtextParametrizedRunner.ParametersProvider
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.GrammarUtil
import org.eclipse.xtext.testing.InjectWith
import org.junit.runner.RunWith
import org.junit.Test
import org.eclipse.xtext.parser.antlr.Lexer
import org.eclipse.xtext.parser.antlr.LexerBindings
import com.google.inject.name.Named
import org.eclipse.n4js.parser.AntlrStreamWithToString
import java.io.StringReader
import org.antlr.runtime.CommonTokenStream
import org.antlr.runtime.CommonToken
import org.junit.Assert

/**
 * Check that there is no keyword prefix that will bail out from the lexer
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextParametrizedRunner)
@FinalFieldsConstructor
class KeywordPrefixTest {

	@Inject
	@Named(LexerBindings.RUNTIME)
	Provider<Lexer> lexerProvider

	val String keywordValue

	@ParametersProvider(name='{0}')
	static def Provider<Iterable<Object[]>> getKeywords() {
		return new Provider<Iterable<Object[]>>() {
			@Inject
			N4JSGrammarAccess grammarAccess
			override get() {
				GrammarUtil.getAllKeywords(grammarAccess.grammar).filter[ length > 1].map[ val Object[] arr = #[it] return arr ]
			}
		}
	}

	@Test def void testAllPrefixes() {
		(1..<keywordValue.length).map[keywordValue.substring(0, it)].forEach[ prefix |
			val lexer = lexerProvider.get
			lexer.charStream = new AntlrStreamWithToString(new StringReader(prefix))
			val tokenStream = new CommonTokenStream(lexer)
			tokenStream.tokens.forEach[
				Assert.assertFalse(prefix + " - " + keywordValue, (it as CommonToken).type == 0)
			]
		]
	}

}
