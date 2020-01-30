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
package org.eclipse.n4js.ide.editor.contentassist.imports;

import org.eclipse.xtext.util.TextRegion;

/**
 * Extended information about where to insert new text. It holds a general total offset in the document - similar to a
 * {@link TextRegion} instance, but contrary to that has no length as it describes the point after that insertion should
 * happen.
 *
 * It also carries information if the insertion has to be recomputed. The properties {@link #notBeforeTotalOffset} and
 * {@link #notAfterTotalOffset} mark the domain of valid recomputed insertion points.
 *
 * The property {@linkp #isBeforeJsdocDocumentation} marks insertion points associated with active jsdoc-style
 * ML-comments.
 *
 */
class InsertionPoint {

	/** Insertion point, a value of {@code -1} means not set. Zero-based total offset. */
	public int offset = -1;

	/**
	 * Flag, if set indicates that the textRegion is just in Front of an active jsdoc region for the first statement
	 */
	public boolean isBeforeJsdocDocumentation = false;

	/** Lowest offset for possible insertion. Usually marked by existing ScriptAnnotation(s) or directives in prolog */
	public int notBeforeTotalOffset = 0;

	/** Highest possible insertion point in the document. */
	public int notAfterTotalOffset = Integer.MAX_VALUE;

}
