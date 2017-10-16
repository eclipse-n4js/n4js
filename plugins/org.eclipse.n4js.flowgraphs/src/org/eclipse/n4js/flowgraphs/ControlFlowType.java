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
package org.eclipse.n4js.flowgraphs;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.model.CatchToken;
import org.eclipse.n4js.flowgraphs.model.JumpToken;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;

/**
 * Defines the kind of the control flow. Control flow types are also used to specify {@link JumpToken}s and
 * {@link CatchToken}s.
 */
public enum ControlFlowType {
	/** Successor edges are default edges */
	Successor,
	/** Return edges are caused by {@link ReturnStatement}s */
	Return,
	/** Throw edges are caused by {@link ThrowStatement}s */
	Throw,
	/** Break edges are caused by {@link BreakStatement}s */
	Break,
	/** Continue edges are caused by {@link ContinueStatement}s */
	Continue,
	/** Repeat edges are caused by loops and flow from the condition to the body */
	Repeat,
	/** Used to mark {@link CatchToken}s that can catch {@link JumpToken} due to thrown N4JS errors */
	CatchesErrors,
	/** Used to mark {@link CatchToken}s that can catch any {@link JumpToken} */
	CatchesAll;

	/** Set of all control flow types except for {@literal ControlFlowType.Repeat} */
	static public final ControlFlowType[] NonRepeatTypes = { Successor, Break, Continue, Throw, Return };

	/** @return a filtered list that contains only {@link ControlFlowType}s of the given types */
	static public List<ControlFlowType> filter(Iterable<ControlFlowType> list, ControlFlowType... onlyThese) {
		List<ControlFlowType> result = new LinkedList<>();
		for (ControlFlowType cft : list) {
			for (ControlFlowType onlyThis : onlyThese) {
				if (onlyThis == cft) {
					result.add(cft);
				}
			}
		}
		return result;
	}

	/** @return true, iff the given array is null or empty, or iff it contains the {@code this} enum element */
	public boolean isInOrEmpty(ControlFlowType[] flowTypes) {
		if (flowTypes == null || flowTypes.length == 0) {
			return true;
		}
		for (ControlFlowType cft : flowTypes) {
			if (this == cft) {
				return true;
			}
		}
		return false;
	}

}
