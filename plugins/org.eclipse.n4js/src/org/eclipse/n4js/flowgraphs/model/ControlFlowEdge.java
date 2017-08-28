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

import org.eclipse.n4js.flowgraphs.ControlFlowType;

public class ControlFlowEdge extends AbstractEdge {
	public final ControlFlowType cfType;
	public final boolean isNested;

	public ControlFlowEdge(Node start, Node end) {
		this(start, end, ControlFlowType.Successor);
	}

	public ControlFlowEdge(Node start, Node end, ControlFlowType cfType) {
		this(start, end, cfType, false);
	}

	public ControlFlowEdge(Node start, Node end, ControlFlowType cfType, boolean nested) {
		super(start, end);
		this.cfType = cfType;
		this.isNested = nested;
	}

	@Override
	public String toString() {
		String s = " (" + start + ") ";
		if (cfType != ControlFlowType.Successor) {
			s += "-" + cfType.name();
		}
		s += "-> ";
		s += "(" + end + ")";
		return s;
	}

	/**
	 * @return true iff {@link #cfType} is either {@literal ControlFlowType.Continue} or {@literal ControlFlowType.Loop}
	 */
	public boolean isLoopCarried() {
		switch (cfType) {
		case Continue:
		case Loop:
			return true;
		default:
			return false;
		}
	}

	/**
	 * @return true iff {@link #cfType} is {@literal ControlFlowType.Repeat}
	 */
	public boolean isRepeat() {
		switch (cfType) {
		case Repeat:
			return true;
		default:
			return false;
		}
	}

}
