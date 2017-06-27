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

import org.eclipse.n4js.jsdoc.IJSDocTokenizer;
import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocToken;

/**
 * Naive JS identifier tokenizer. Will return next sequenc of chars in scanner, that is passign
 * {@link Character#isLetter}.
 */
public class JSIdentifierTokenizer implements IJSDocTokenizer {

	@Override
	public JSDocToken nextToken(JSDocCharScanner scanner) {
		int start = scanner.nextOffset();
		int end = start;

		StringBuilder strb = new StringBuilder();
		while (scanner.hasNext() && !scanner.skipped()) {
			char c = scanner.peek();
			if (Character.isLetter(c)) {
				strb.append(c);
				scanner.next(); // consume c
			} else {
				break;
			}
		}
		end = scanner.offset();

		if (end < start) {
			return null;
		}

		return new JSDocToken(strb.toString(), start, end);

	}

}
