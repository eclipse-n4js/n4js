/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link DelegatingNode} does provide a {@link ControlFlowElement} delegate. Also, it does represents a CFE and
 * thus returns its {@link ControlFlowElement} when asked for it in
 * {@link #getRepresentingOrSucceeding(List, ControlFlowType...)} or
 * {@link #getRepresentingOrPreceeding(List, ControlFlowType...)}.
 */
public class RepresentingNode extends DelegatingNode {

	/**
	 * Constructor. Sets the delegated {@link ControlFlowElement} to cfe
	 */
	public RepresentingNode(String name, ControlFlowElement cfe) {
		super(name, cfe, cfe);
	}

	@Override
	protected List<RepresentingNode> getRepresentingOrSucceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges) {

		LinkedList<RepresentingNode> cfeInAList = new LinkedList<>();
		cfeInAList.add(this);
		return cfeInAList;
	}

	@Override
	protected List<RepresentingNode> getRepresentingOrPreceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges) {

		LinkedList<RepresentingNode> cfeInAList = new LinkedList<>();
		cfeInAList.add(this);
		return cfeInAList;
	}

	@Override
	public ControlFlowElement getRepresentedControlFlowElement() {
		return getDelegatedControlFlowElement();
	}

}
