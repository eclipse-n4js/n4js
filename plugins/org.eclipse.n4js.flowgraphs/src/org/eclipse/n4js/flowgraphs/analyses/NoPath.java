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
package org.eclipse.n4js.flowgraphs.analyses;

import org.eclipse.n4js.flowgraphs.model.Node;

import com.google.common.collect.Lists;

/**
 * Instances of {@link NoPath} represent non-existing paths between two nodes.
 */
public class NoPath extends Path {

	/**
	 * Constructor
	 *
	 * @param start
	 *            start node of the path
	 * @param end
	 *            end node of the path
	 * @param nextEdgesProvider
	 *            of the path
	 */
	public NoPath(Node start, Node end, NextEdgesProvider nextEdgesProvider) {
		super(start, end, Lists.newLinkedList(), nextEdgesProvider);
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
