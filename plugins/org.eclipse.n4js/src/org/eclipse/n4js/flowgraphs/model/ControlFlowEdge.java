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

public class ControlFlowEdge extends AbstractEdge {
	public final boolean isLoop;
	public final boolean isJump;
	public final boolean isNested;

	public ControlFlowEdge(Node start, Node end) {
		this(start, end, false, false, false);
	}

	public ControlFlowEdge(Node start, Node end, boolean isLoop) {
		this(start, end, isLoop, false, false);
	}

	public ControlFlowEdge(Node start, Node end, boolean isLoop, boolean isBreak) {
		this(start, end, isLoop, isBreak, false);
	}

	public ControlFlowEdge(Node start, Node end, boolean isLoop, boolean isBreak, boolean nested) {
		super(start, end);
		this.isLoop = isLoop;
		this.isJump = isBreak;
		this.isNested = nested;
	}

	@Override
	public String toString() {
		String s = " (" + start + ") ";
		if (isLoop)
			s += "-lc";
		if (isJump)
			s += "-bc";
		s += "-> ";
		s += "(" + end + ")";
		return s;
	}
}
