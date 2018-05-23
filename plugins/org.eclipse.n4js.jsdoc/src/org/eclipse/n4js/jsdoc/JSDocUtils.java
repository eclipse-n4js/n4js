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

import java.util.Collection;

import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.JSDocNode;

/**
 * Small collection of {@link JSDocNode} utilities.
 */
public class JSDocUtils {

	/**
	 * Calculate most start position from all nodes.
	 */
	public static int getBegin(Collection<? extends ContentNode> contents) {
		int begin = -1;
		for (ContentNode c : contents) {
			if (begin == -1) {
				begin = c.getBegin();
			} else {
				begin = Math.min(begin, c.getBegin());
			}
		}
		return begin;
	}

	/**
	 * Calculate most end position from all nodes.
	 */
	public static int getEnd(Collection<? extends ContentNode> contents) {
		int end = -1;
		for (ContentNode c : contents) {
			end = Math.max(end, c.getEnd());
		}
		return end;
	}

}
