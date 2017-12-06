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
 *
 */
public class CFNode extends Node {
	final int nodeIdx;
	final boolean isDeadCode;

	/** Constructor */
	public CFNode(ControlFlowElement cfe, String label, String description, int nodeIdx, boolean isDeadCode) {
		super(cfe, label, description);
		this.nodeIdx = nodeIdx;
		this.isDeadCode = isDeadCode;
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
