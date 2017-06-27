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

import org.antlr.runtime.RecognitionException;

import org.eclipse.n4js.parser.LazyTokenStream;
import org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingParser;
import org.eclipse.n4js.ui.editor.syntaxcoloring.InternalHighlightingParser;
import org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser;

/**
 */
public class ThrowingHighlightingParser extends HighlightingParser {

	@Override
	protected InternalN4JSParser createParser(LazyTokenStream stream) {
		return new InternalHighlightingParser(stream, getGrammarAccess(), getRewriter()) {
			@Override
			public void reportError(RecognitionException e) {
				throw new RuntimeException(e);
			}
		};
	}

}
