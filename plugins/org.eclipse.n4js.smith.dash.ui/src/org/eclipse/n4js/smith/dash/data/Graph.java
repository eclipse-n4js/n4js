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
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public class Graph {

	private final Set<GraphNode> nodes = new LinkedHashSet<>();

	public Set<GraphNode> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public void addNode(GraphNode node) {
		nodes.add(node);
	}

}
