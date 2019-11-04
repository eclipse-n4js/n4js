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
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;

/**
 * Defines the kind of the control flow. Control flow types are also used to specify {@link JumpToken}s and
 * {@link CatchToken}s.
 */
public enum ControlFlowType {
	/** Successor edges are default edges */
	Successor,
	/** IfTrue edges only flow from the condition of an {@link IfStatement} to its then block */
	IfTrue,
	/** IfFalse edges flow from the condition of an {@link IfStatement} to its then else or its exit node */
	IfFalse,
	/**
	 * IfNullishTarget edges flow from the target of an optionally chained ExpressionWithTarget to the end of the chain
	 */
	IfNullishTarget,
	/**
	 * IfNullish edges flow from the expression of an nullish coalesce expression to the default expression.
	 */
	IfNullish,
	/** Return edges are caused by {@link ReturnStatement}s */
	Return,
	/** Throw edges are caused by {@link ThrowStatement}s */
	Throw,
	/** Break edges are caused by {@link BreakStatement}s */
	Break,
	/** Continue edges are caused by {@link ContinueStatement}s */
	Continue,
	/** LoopEnter edges are caused by loops and flow into the body */
	LoopEnter,
	/** LoopExit edges are caused by loops and flow from the condition to the exit of the control statement */
	LoopExit,
	/** LoopRepeat edges are caused by loops and flow from the body to the condition */
	LoopRepeat,
	/** LoopReenter edges are caused by do-loops and flow from the condition to the body */
	LoopReenter,
	/** LoopInfinite edges are caused by for-loops without condition and flows from the update to the body */
	LoopInfinite,
	/** DeadCode edges target a node that is represents dead code */
	DeadCode,
	/** Used to mark {@link CatchToken}s that can catch {@link JumpToken} due to thrown N4JS errors */
	CatchesErrors,
	/** Used to mark {@link CatchToken}s that can catch any {@link JumpToken} */
	CatchesAll;

	/** Set of all control flow types except for {@literal ControlFlowType.DeadCode} */
	static public final ControlFlowType[] NonDeadTypes = { Successor, IfTrue, IfFalse, IfNullishTarget, IfNullish,
			Break, Continue,
			Throw, Return, LoopEnter, LoopExit, LoopRepeat, LoopReenter, LoopInfinite };

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

	/** @return true iff this {@link ControlFlowType} points in backwards direction */
	public boolean isBackwards() {
		switch (this) {
		case LoopRepeat:
		case LoopReenter:
		case LoopInfinite:
			return true;
		default:
			return false;
		}
	}

}
