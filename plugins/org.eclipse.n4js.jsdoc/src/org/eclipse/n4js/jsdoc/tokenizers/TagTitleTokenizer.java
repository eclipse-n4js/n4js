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
 * Specialized tokenizer for tag titles. Expects next non whitespace character in {@link JSDocCharScanner} to be '@'
 * sign marking start of the tag title. It will parse following characters untill char that is not '.' or '-' or fails
 * to pass {@link Character#isLetterOrDigit} is encountered. When this happens it will return all read characters
 * (including '@') sign as {@link JSDocToken}.
 */
public class TagTitleTokenizer extends AbstractJSDocTokenizer {

	@SuppressWarnings("javadoc")
	public final static TagTitleTokenizer INSTANCE = new TagTitleTokenizer();

	@Override
	public JSDocToken nextToken(JSDocCharScanner scanner) {
		char x = scanner.nextNonWS();
		if (x != JSDocCharScanner.TAG_START) {
			return null;
		}
		int start = scanner.offset() + 1;
		int end = scanner.offset();
		StringBuilder strb = new StringBuilder();

		while (scanner.hasNext() && !scanner.skipped()) {
			char c = scanner.next();
			if (scanner.offset() == start) {
				if (!Character.isLetter(c)) {
					break;
				}
			} else {
				if (!(c == '.' || c == '-' || Character.isLetterOrDigit(c))) {
					break;
				}
			}
			strb.append(c);
			end = scanner.offset();
		}

		if (end <= start) {
			return null;
		}

		return new JSDocToken(strb.toString(), start, end);

	}

}
