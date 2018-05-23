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
package org.eclipse.n4js.smith.ui.graph;

import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Node of a control flow graph
 */
public class CFNode extends Node {
	/** Node ID */
	final public int nodeIdx;
	/** true iff this node represents dead code */
	final public boolean isDeadCode;
	/** true iff this node is an entry node of a control flow container */
	final public boolean isEntry;
	/** true iff this node is an exit node of a control flow container */
	final public boolean isExit;

	/** Constructor */
	public CFNode(ControlFlowElement cfe, String label, String description, int nodeIdx, boolean isDeadCode,
			boolean isEntry, boolean isExit) {

		super(cfe, label, description);
		this.nodeIdx = nodeIdx;
		this.isDeadCode = isDeadCode;
		this.isEntry = isEntry;
		this.isExit = isExit;
	}

	@Override
	public Color getBackgroundColor() {
		Color col = null;
		if (isDeadCode) {
			Display displ = Display.getCurrent();
			col = displ.getSystemColor(SWT.COLOR_GRAY);
		} else {
			col = super.getBackgroundColor();
		}
		return col;
	}

	/** @return the {@link ControlFlowElement} of this node */
	public ControlFlowElement getControlFlowElement() {
		return (ControlFlowElement) element;
	}

}
