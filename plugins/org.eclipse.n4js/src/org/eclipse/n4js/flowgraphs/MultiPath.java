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

import java.util.LinkedList;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.model.Node;

/**
 *
 */
public class MultiPath {
	final private LinkedList<Path> paths;

	MultiPath(LinkedList<Path> paths) {
		this.paths = paths;
	}

	public Node getStart() {
		return paths.getFirst().start;
	}

	public TreeSet<Node> getEnds() {
		TreeSet<Node> ends = new TreeSet<>();
		for (Path path : paths) {
			ends.add(path.end);
		}
		return ends;
	}

	public TreeSet<ControlFlowType> getControlFLowTypes() {
		TreeSet<ControlFlowType> cfTypes = new TreeSet<>();
		for (Path path : paths) {
			cfTypes.addAll(path.getControlFlowTypes());
		}
		return cfTypes;
	}

	public int getPathCount() {
		return paths.size();
	}

	public boolean isInternal() {
		boolean isInternal = true;
		for (Path path : paths) {
			isInternal &= path.isInternal();
		}
		return isInternal;
	}

	public boolean isConnecting() {
		return true;
	}

}
