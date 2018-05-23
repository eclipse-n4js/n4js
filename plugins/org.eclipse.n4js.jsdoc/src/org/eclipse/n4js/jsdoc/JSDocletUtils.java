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
package org.eclipse.n4js.jsdoc;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagValue;

/**
 */
public class JSDocletUtils {

	/**
	 * Utility methods finding the tag enclosing the given offset. This is either a line tag if the offset in in a
	 * simple text, or an inline tag. Basically used for content assist.
	 *
	 * @return The tag or null, if no such tag has been found.
	 */
	public static Tag getTagAtOffset(Doclet doclet, int offset) {
		LineTag tag = null;
		for (LineTag t : doclet.getLineTags()) {
			if (t.getBegin() <= offset) {
				tag = t;
			} else {
				break;
			}
		}
		if (tag != null) {
			for (TagValue v : tag.getValues()) {
				if (v.covers(offset)) {
					if (v instanceof InlineTag) {
						return (Tag) v;
					}
				}
			}
		}
		return tag;

	}

}
