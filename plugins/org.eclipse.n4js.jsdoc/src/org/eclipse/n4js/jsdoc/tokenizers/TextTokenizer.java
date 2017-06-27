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
 * Returns arbitrary text, i.e. text including whitespaces but no inline tags. Trailing newline characters are not
 * omitted if the next line is not a new line tag (or is the end of the comment), but in any case scan is stopped then.
 * That is, a newline character can only be the last character of a text token.
 */
public class TextTokenizer extends AbstractJSDocTokenizer {

	@SuppressWarnings("javadoc")
	public final static TextTokenizer INSTANCE = new TextTokenizer();

	@Override
	public JSDocToken nextToken(JSDocCharScanner scanner) {
		if (!scanner.hasNext()) {
			return null;
		}

		if (nextIsTagTitle(scanner)) {
			return null;
		}

		int start = scanner.nextOffset();
		int end = start;

		StringBuilder strb = new StringBuilder();
		while (scanner.hasNext()) {
			char c = scanner.peek();
			if (c == '{' && InlineTagTokenizer.INSTANCE.nextToken(scanner.copy()) != null) {
				if (start == end) {
					return null;
				}
				break;
			}
			scanner.next(); // consume c
			if (JSDocCharScanner.isNL(c)) {
				if (scanner.hasNext() && !nextIsTagTitle(scanner)) {
					strb.append(c);
					end = scanner.offset();
				}
				break;
			}
			strb.append(c);
			end = scanner.offset();

		}

		if (end < start) {
			return null;
		}

		return new JSDocToken(strb.toString(), start, end);

	}
}
