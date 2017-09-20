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
package org.eclipse.n4js.smith.dash.data;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 *
 */
public class GraphNode {

	private final Set<GraphNode> connectedTo = Collections.newSetFromMap(
			new WeakHashMap<GraphNode, Boolean>());

	public final String name;

	public GraphNode(String name) {
		this.name = name;
	}

	public void addConnectionTo(GraphNode node) {
		connectedTo.add(node);
	}

	public void removeConnectionTo(GraphNode node) {
		connectedTo.remove(node);
	}

}
