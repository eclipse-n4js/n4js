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

import java.util.List;

import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link DelegatingNode} does provide a {@link ControlFlowElement} delegate. However, it does not represent a CFE
 * and thus does not return its {@link ControlFlowElement} when asked for it in
 * {@link #getRepresentingOrSucceeding(List, ControlFlowType...)} or
 * {@link #getRepresentingOrPreceeding(List, ControlFlowType...)}. Instead, it passes these calls to the successor or
 * predecessor, respectively.
 */
public class DelegatingNode extends Node {
	final private ControlFlowElement cfeDelegate;

	/**
	 * Constructor. Sets the delegated {@link ControlFlowElement} to cfe
	 */
	public DelegatingNode(String name, ControlFlowElement cfe) {
		this(name, cfe, cfe);
	}

	/**
	 * Constructor
	 */
	public DelegatingNode(String name, ControlFlowElement cfe, ControlFlowElement cfeDelegate) {
		super(name, cfe);
		this.cfeDelegate = cfeDelegate;
	}

	@Override
	protected List<RepresentingNode> getRepresentingOrSucceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges) {

		return getSuccessors(loopEdges, followEdges);
	}

	@Override
	protected List<RepresentingNode> getRepresentingOrPreceeding(List<ControlFlowEdge> loopEdges,
			ControlFlowType... followEdges) {

		return getPredecessors(loopEdges, followEdges);
	}

	@Override
	public ControlFlowElement getDelegatedControlFlowElement() {
		if (cfeDelegate == null)
			return null; // can be missing when the AST is incomplete
		return CFEMapper.map(cfeDelegate);
	}

	@Override
	public ControlFlowElement getRepresentedControlFlowElement() {
		return null;
	}

}
