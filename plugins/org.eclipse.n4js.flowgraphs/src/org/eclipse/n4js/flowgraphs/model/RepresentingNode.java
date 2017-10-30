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

import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link RepresentingNode} of a {@link ComplexNode} marks the actual location of a {@link ControlFlowElement}
 * within its {@link ComplexNode}. The {@link RepresentingNode} does provide a {@link ControlFlowElement} delegate.
 * Also, it does represents a CFE and thus returns its {@link ControlFlowElement} when asked for it in
 * {@link #getRepresentedControlFlowElement()}.
 */
public class RepresentingNode extends DelegatingNode {

	/**
	 * Constructor. Sets the delegated {@link ControlFlowElement} to cfe
	 */
	public RepresentingNode(String name, ControlFlowElement cfe) {
		super(name, cfe, cfe);
	}

	@Override
	public ControlFlowElement getRepresentedControlFlowElement() {
		return getDelegatedControlFlowElement();
	}

}
