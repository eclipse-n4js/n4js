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
package org.eclipse.n4js.tests.parser;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.services.TypeExpressionsGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Ensure that constants for reserved words, etc. are up-to-date.
 */
public class GrammarRelatedConstantsTest extends AbstractParserTest {

	@Inject
	private TypeExpressionsGrammarAccess typeExpressionsGrammarAccess;

	@Test
	public void testReservedWords() {
		ParserRule rule = typeExpressionsGrammarAccess.getReservedWordRule();
		EList<AbstractElement> elements = ((Alternatives) rule.getAlternatives()).getElements();
		HashSet<Object> reservedWordsPerGrammar = Sets.newHashSet(map(elements, it -> ((Keyword) it).getValue()));
		reservedWordsPerGrammar.removeAll(N4JSLanguageConstants.FUTURE_RESERVED_WORDS);

		assertEquals(reservedWordsPerGrammar, N4JSLanguageConstants.RESERVED_WORDS);
	}

	@Test
	public void testN4Keywords() {
		ParserRule rule = typeExpressionsGrammarAccess.getN4KeywordRule();
		EList<AbstractElement> elements = ((Alternatives) rule.getAlternatives()).getElements();
		HashSet<Object> n4KeywordsPerGrammar = Sets.newHashSet(map(elements, it -> ((Keyword) it).getValue()));

		assertEquals(n4KeywordsPerGrammar, N4JSLanguageConstants.N4_KEYWORDS);
	}
}
