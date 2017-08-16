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

import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
public class RepresentingNode extends Node {

	public RepresentingNode(String name, ControlFlowElement cfe, Node... internalSuccessors) {
		this(name, cfe, -1, internalSuccessors);
	}

	public RepresentingNode(String name, ControlFlowElement cfe, int opPos, Node... internalSuccessors) {
		super(name, cfe, opPos, internalSuccessors);
	}

	@Override
	protected List<ControlFlowElement> getCFEOrSucceeding() {
		ControlFlowElement cfe = getControlFlowElement();
		LinkedList<ControlFlowElement> cfeInAList = new LinkedList<>();
		if (cfe != null) {
			cfeInAList.add(cfe);
		}
		return cfeInAList;
	}

	@Override
	protected List<ControlFlowElement> getCFEOrPreceeding() {
		ControlFlowElement cfe = getControlFlowElement();
		LinkedList<ControlFlowElement> cfeInAList = new LinkedList<>();
		if (cfe != null) {
			cfeInAList.add(cfe);
		}
		return cfeInAList;
	}

	@Override
	public ControlFlowElement getDelegatedControlFlowElement() {
		return getControlFlowElement();
	}

}
