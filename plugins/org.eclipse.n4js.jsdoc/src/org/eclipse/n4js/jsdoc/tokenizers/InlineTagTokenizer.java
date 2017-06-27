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
package org.eclipse.n4js.jsdoc.tokenizers;

import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocToken;

/**
 * Returns an inline tag token, excluding the enclosing curly brackets.
 */
public class InlineTagTokenizer extends RegionTokenizer {

	@SuppressWarnings("javadoc")
	public final static InlineTagTokenizer INSTANCE = new InlineTagTokenizer();

	/**
	 * Constructor with default values: for start/end markers ({/}), allows nesting, escape chars (\\), doesn't include
	 * markers in token returned in from parsing, doesn't allow for line breaks. For details refer to super type
	 * {@link RegionTokenizer}
	 */
	public InlineTagTokenizer() {
		super("{", "}", false, '\\', false, true);
	}

	@Override
	public JSDocToken nextToken(JSDocCharScanner scanner) {
		JSDocToken token = super.nextToken(scanner);
		if (token != null) {
			if (TagTitleTokenizer.INSTANCE.nextToken(new JSDocCharScanner(token.token)) != null) {
				return token;
			}
		}
		return null;
	}
}
