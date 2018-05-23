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
package org.eclipse.n4js.editor.syntaxcoloring;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.junit.Assert;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingParser;

/**
 */
public class HighlightingParserTester {

	@Inject
	private IParser parser;
	@Inject
	private ThrowingHighlightingParser throwingHighlightingParser;
	@Inject
	private HighlightingParser highlightingParser;

	/**
	 * Parses the input and returns a list of lexer tokens. Asserts that the produced tokens are equal to the tokens
	 * that the production parser produced.
	 *
	 * @return the tokens for the highlighting.
	 */
	public List<Token> getTokens(CharSequence input) {
		List<Token> result;
		IParseResult parseResult = parser.parse(new StringReader(input.toString()));
		if (!parseResult.hasSyntaxErrors()) {
			result = throwingHighlightingParser.getTokens(input);
		} else {
			result = highlightingParser.getTokens(input);
		}
		// assert equal tokens
		Iterator<Token> iter = result.iterator();
		for (ILeafNode leaf : parseResult.getRootNode().getLeafNodes()) {
			Assert.assertTrue("hasNext at index " + leaf.getTotalOffset() + " for leaf '" + leaf.getText() + "'",
					iter.hasNext());
			Token token = iter.next();
			// TODO: assert token type
			Assert.assertEquals(leaf.getText(), token.getText());
		}
		return result;
	}

}
