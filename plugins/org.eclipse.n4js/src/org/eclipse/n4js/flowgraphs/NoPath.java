/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs;

import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 *
 */
public class NoPath extends Path {

	public NoPath(Node start, Node end, boolean forward) {
		super(start, end, Lists.newLinkedList(), forward);
	}

	@Override
	public boolean isConnecting() {
		return false;
	}

	@Override
	public int getLength() {
		return -1;
	}

}
