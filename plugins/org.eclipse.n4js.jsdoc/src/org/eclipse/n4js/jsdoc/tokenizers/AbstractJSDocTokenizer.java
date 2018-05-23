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
import org.eclipse.n4js.jsdoc.JSDocCharScanner.ScannerState;

/**
 * Base class for {@link IJSDocTokenizer} implmenetations.
 */
public abstract class AbstractJSDocTokenizer implements IJSDocTokenizer {

	/**
	 * Returns true, if a tag title (including leading '@' character) is found next, omitting leading whitespaces.
	 *
	 * @param scanner
	 *            scanner with current offset_, scanner's state is not changed
	 */
	static boolean nextIsTagTitle(JSDocCharScanner scanner) {
		ScannerState state = scanner.saveState();
		try {
			scanner.skipWS();
			if (scanner.hasNext() && scanner.peek() == JSDocCharScanner.TAG_START) {
				return TagTitleTokenizer.INSTANCE.nextToken(scanner.copy()) != null;
			}
		} finally {
			scanner.restoreState(state);
		}
		return false;
	}

}
