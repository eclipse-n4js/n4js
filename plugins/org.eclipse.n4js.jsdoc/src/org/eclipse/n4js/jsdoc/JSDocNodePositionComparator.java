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

import java.util.Comparator;

import org.eclipse.n4js.jsdoc.dom.JSDocNode;

/**
 * Compares position markers in given two instances of {@link JSDocNode}
 */
public class JSDocNodePositionComparator implements Comparator<JSDocNode> {

	/**
	 * static ref to instance of this class
	 */
	public static final JSDocNodePositionComparator INSTANCE = new JSDocNodePositionComparator();

	@Override
	public int compare(JSDocNode n1, JSDocNode n2) {
		if (n1 == null) {
			return n2 == null ? 0 : -1;
		}
		if (n2 == null) {
			return 1;
		}
		int diff = n1.getBegin() - n2.getBegin();
		if (diff == 0) {
			diff = n1.getEnd() - n2.getEnd();
		}
		return diff;
	}

}
