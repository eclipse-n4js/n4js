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

import org.eclipse.n4js.flowgraphs.factories.CFEMapper;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link DelegatingNode} used to model nested {@link ComplexNode}s: The {@link ComplexNode} of the delegated
 * {@link ControlFlowElement} is nested within its {@link ComplexNode}. does provide a {@link ControlFlowElement}
 * delegate. However, it does not represent a CFE and thus does not return its {@link ControlFlowElement} when asked for
 * it in {@link #getRepresentedControlFlowElement()}.
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
	public ControlFlowElement getDelegatedControlFlowElement() {
		return CFEMapper.map(cfeDelegate);
	}

	@Override
	public ControlFlowElement getRepresentedControlFlowElement() {
		return null;
	}

}
