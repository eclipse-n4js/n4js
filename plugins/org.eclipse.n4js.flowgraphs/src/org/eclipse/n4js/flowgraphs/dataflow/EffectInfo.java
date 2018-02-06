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
package org.eclipse.n4js.flowgraphs.dataflow;

import static com.google.common.base.Preconditions.checkState;

import org.eclipse.n4js.n4JS.ControlFlowElement;

/** Holds information of one effect to a single variable */
public class EffectInfo {
	/** The type of the effect */
	public final EffectType type;
	/** The location where the effect is triggered */
	public final ControlFlowElement location;
	/** The symbol that is affected */
	public final Symbol symbol;

	/** Constructor */
	public EffectInfo(EffectType type, ControlFlowElement location, Symbol symbol) {
		checkState(symbol != null);

		this.type = type;
		this.location = location;
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		String s = "";
		s += type.name();
		s += " " + symbol.toString();

		return s;
	}
}
