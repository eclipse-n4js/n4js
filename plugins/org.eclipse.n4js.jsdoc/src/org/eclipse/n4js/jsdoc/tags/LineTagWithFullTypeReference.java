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
package org.eclipse.n4js.jsdoc.tags;

import static org.eclipse.n4js.jsdoc.JSDocCompletionHint.CompletionKind.FQTYPE;

import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocCompletionHint;

/**
 * Tag which consists of a full type reference.
 */
public class LineTagWithFullTypeReference extends LineTagWithFullElementReference {

	/**
	 * @param title
	 *            Tag title
	 */
	public LineTagWithFullTypeReference(String title) {
		super(title);
	}

	@Override
	public JSDocCompletionHint completionHint(JSDocCharScanner scanner) {
		JSDocCompletionHint hint = super.completionHint(scanner);
		return new JSDocCompletionHint(FQTYPE, hint.prefix, hint.nodeToBeCompleted);
	}
}
