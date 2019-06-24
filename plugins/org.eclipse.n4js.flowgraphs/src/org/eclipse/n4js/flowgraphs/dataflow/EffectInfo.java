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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
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

	/**
	 * Filters the given {@link Collection} of {@link EffectInfo}s and returns a new collection that only contains
	 * {@link EffectInfo} of the given {@link EffectType}.
	 */
	static public Collection<EffectInfo> findAll(Collection<EffectInfo> eInfos, EffectType type) {
		List<EffectInfo> filteredEInfos = new LinkedList<>();
		for (EffectInfo ei : eInfos) {
			if (ei.type == type) {
				filteredEInfos.add(ei);
			}
		}
		return filteredEInfos;
	}

	/**
	 * Filters the given {@link Collection} of {@link EffectInfo}s and returns a new collection that only contains
	 * {@link EffectInfo} of the given {@link EffectType} and given {@link Symbol}.
	 */
	static public EffectInfo findFirst(Collection<EffectInfo> eInfos, EffectType type, Symbol symbol) {
		for (EffectInfo ei : eInfos) {
			if (ei.type == type && ei.symbol == symbol) {
				return ei;
			}
		}
		return null;
	}

	/**
	 * Filters the given {@link Collection} of {@link EffectInfo}s and returns a new collection that only contains
	 * {@link EffectInfo} of the given {@link EffectType}.
	 */
	static public EffectInfo findFirst(Collection<EffectInfo> eInfos, EffectType type) {
		for (EffectInfo ei : eInfos) {
			if (ei.type == type) {
				return ei;
			}
		}
		return null;
	}

}
