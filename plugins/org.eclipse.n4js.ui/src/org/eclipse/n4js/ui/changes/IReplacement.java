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
package org.eclipse.n4js.ui.changes;


/**
 * An {@link IChange} to a text document that replaces a region of the text by a given replacement string. The region to
 * be replaced is defined by offset and length.
 * <p>
 * Note that this notion includes insertions (if length is 0 and the string is non-empty) and deletions (if length is
 * greater 0 and the string is empty).
 */
public interface IReplacement extends IAtomicChange {

	/**
	 * The replacement text that is to be inserted at the region defined by {@link #getOffset() offset} and
	 * {@link #getLength() length}.
	 */
	String getText();
}
