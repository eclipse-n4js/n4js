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

/** Abstraction of {@link Node}s and {@link ComplexNode}s. Used in {@link EdgeUtils}. */
public interface ControlFlowable extends GraphElement {

	/** @return the entry node */
	public Node getEntry();

	/** @return the exit node */
	public Node getExit();

	/** @return the {@link ControlFlowElement} of this instance */
	public ControlFlowElement getControlFlowElement();

}
