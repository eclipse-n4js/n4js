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

import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * The {@link HelperNode} does not provide a {@link ControlFlowElement} delegate. Moreover, it does not represent a CFE
 * and thus does not return its {@link ControlFlowElement} when asked for it in {@link #getCFEOrSucceeding()} or
 * {@link #getCFEOrPreceeding()}. Instead, it passes these calls to the successor or predecessor, respectively.
 */
public class HelperNode extends Node {

	/**
	 * Constructor
	 */
	public HelperNode(String name, ControlFlowElement cfe) {
		super(name, cfe);
	}

	@Override
	protected List<ControlFlowElement> getCFEOrSucceeding() {
		return getSuccessors();
	}

	@Override
	protected List<ControlFlowElement> getCFEOrPreceeding() {
		return getPredecessors();
	}

	@Override
	public ControlFlowElement getDelegatedControlFlowElement() {
		return null;
	}

}
