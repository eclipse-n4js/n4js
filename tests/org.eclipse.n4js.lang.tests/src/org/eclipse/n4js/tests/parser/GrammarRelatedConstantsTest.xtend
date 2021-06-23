/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser

import com.google.common.collect.Sets
import com.google.inject.Inject
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess
import org.eclipse.xtext.Alternatives
import org.eclipse.xtext.Keyword
import org.junit.Test

/**
 * Ensure that constants for reserved words, etc. are up-to-date.
 */
class GrammarRelatedConstantsTest extends AbstractParserTest {

	@Inject
	private TypeExpressionsGrammarAccess typeExpressionsGrammarAccess;

	@Test
	def void testReservedWords() {
		val rule = typeExpressionsGrammarAccess.reservedWordRule;
		val reservedWordsPerGrammar = Sets.newHashSet((rule.alternatives as Alternatives).elements.map[(it as Keyword).value]);
		reservedWordsPerGrammar.removeAll(N4JSLanguageConstants.FUTURE_RESERVED_WORDS);

		assertEquals(reservedWordsPerGrammar, N4JSLanguageConstants.RESERVED_WORDS);
	}

	@Test
	def void testN4Keywords() {
		val rule = typeExpressionsGrammarAccess.n4KeywordRule;
		val n4KeywordsPerGrammar = Sets.newHashSet((rule.alternatives as Alternatives).elements.map[(it as Keyword).value]);
		
		assertEquals(n4KeywordsPerGrammar, N4JSLanguageConstants.N4_KEYWORDS);
	}
}
