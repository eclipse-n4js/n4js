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
package org.eclipse.n4js.tests.highlighting

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.runner.RunWith
import org.junit.Test
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeRewriter
import com.google.common.collect.ImmutableSet
import org.eclipse.n4js.services.N4JSGrammarAccess
import com.google.inject.Inject
import org.junit.Assert
import static org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeRewriter.N4JSKeywords.*
import org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser
import org.eclipse.xtext.GrammarUtil

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TokenTypeRewriterTest {

	@Inject N4JSGrammarAccess grammarAccess

	@Test def void testNoDuplicateKeywordsDefined() {
		val allKeywords = (booleanLiteral +
			es5Keywords +
			es6Keywords +
			futureKeywords +
			n4jsKeyword +
			nullLiteral)
		Assert.assertEquals(allKeywords.size, ImmutableSet.copyOf(allKeywords).size)
	}

	@Test def void testAllKeywordsAreFromGrammar() {
		for(keyword : booleanLiteral +
			es5Keywords +
			es6Keywords +
			futureKeywords +
			n4jsKeyword +
			nullLiteral) {
			val grammarKeywords = grammarAccess.findKeywords(keyword)
			Assert.assertFalse('keyword:' + keyword, grammarKeywords.isEmpty)
		}
	}

	@Test def void testAllGrammarKeywordsAreMapped() {
		val mappedKeywords = (booleanLiteral +
			es5Keywords +
			es6Keywords +
			futureKeywords +
			n4jsKeyword +
			nullLiteral +
			keywordFromTypeRef +
			hardcodedAnnotationAndSemanticKeywords).toSet
		val grammar = grammarAccess.grammar
		val keywordsFromGrammar = GrammarUtil.getAllKeywords(grammar).filter[matches('.*\\w+.*')].toSet
		val unmappedKeywords = keywordsFromGrammar.filter[!mappedKeywords.contains(it)].toSet
		Assert.assertTrue("The following keywords are not mapped: " + unmappedKeywords.toString, unmappedKeywords.isEmpty)
	}

	@Test def void testInstantiatesSuccessfully() {
		// no exception thrown
		new TokenTypeRewriter(grammarAccess)
	}


	@Test def void testNullTokenDoesntThrow() {
		val rewriter = new TokenTypeRewriter(grammarAccess)
		val tokenType = rewriter.rewrite(null, grammarAccess.templateExpressionEndAccess.rightCurlyBracketKeyword)
		Assert.assertEquals(InternalN4JSParser.RULE_TEMPLATE_CONTINUATION, tokenType)
	}

	@Test def void testUnmappedGrammarElementYieldsNull() {
		val rewriter = new TokenTypeRewriter(grammarAccess)
		val tokenType = rewriter.rewrite(null, grammarAccess.templateHeadAccess.group)
		Assert.assertNull(tokenType)
	}
}
