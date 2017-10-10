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

// TODO GH-235
@SuppressWarnings("javadoc")
public class DependencyEdge extends AbstractEdge {
	public final DependencyEdgeType type;
	public final Symbol symbol;
	public final boolean loopCarried;

	public DependencyEdge(DependencyEdgeType type, Node start, Node end) {
		this(type, start, end, null, false);
	}

	public DependencyEdge(DependencyEdgeType type, Node start, Node end, Symbol symbol, boolean loopCarried) {
		super(start, end);
		this.type = type;
		this.symbol = symbol;
		this.loopCarried = loopCarried;
	}

	@Override
	public String toString() {
		String s = "";
		s += start;
		s += "---" + type.toString().toUpperCase();
		s += ":" + symbol;
		if (loopCarried)
			s += "-|";
		s += "--->";
		s += end;
		return s;
	}

}
