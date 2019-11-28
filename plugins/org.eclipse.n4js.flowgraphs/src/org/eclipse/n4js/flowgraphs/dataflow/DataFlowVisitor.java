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
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.FlowAnalyser;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.flowgraphs.dataflow.guards.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Base class for implementing data flow analyses.
 */
abstract public class DataFlowVisitor implements FlowAnalyser {
	/** {@link TraverseDirection} of this visitor */
	protected final TraverseDirection direction;
	Collection<Assumption> newAssumptions = new LinkedList<>();
	/** All {@link Assumption}s created by this {@link DataFlowVisitor} */
	protected Collection<Assumption> allAssumptions = new LinkedList<>();
	/** All {@link Assumption}s that failed */
	protected Map<Object, Assumption> failedAssumptions = new HashMap<>();
	/** Reference to the {@link SymbolFactory} */
	protected SymbolFactory symbolFactory;

	/** Constructor. Default {@link TraverseDirection} is {@link TraverseDirection#Backward} */
	public DataFlowVisitor() {
		this(TraverseDirection.Backward);
	}

	/** Constructor. */
	public DataFlowVisitor(TraverseDirection mode) {
		this.direction = mode;
	}

	/** @return the {@link TraverseDirection} of this visitor */
	final public TraverseDirection getDirection() {
		return direction;
	}

	/*
	 * Called from internal classes
	 */

	/** Called from {@link DataFlowVisitorHost} only */
	final void setSymbolFactory(SymbolFactory symbolFactory) {
		this.symbolFactory = symbolFactory;
	}

	/**
	 * Moves {@link #newAssumptions} to {@link #allAssumptions}.
	 *
	 * @return new assumptions that were created during the last calls to
	 *         {@link #visitEffect(EffectInfo, ControlFlowElement)} or {@link #visitGuard(Guard)}
	 */
	final Collection<Assumption> moveNewAssumptions() {
		if (newAssumptions.isEmpty()) {
			return Collections.emptyList();
		}
		allAssumptions.addAll(newAssumptions);
		Collection<Assumption> newAssumptionsTmp = new LinkedList<>(newAssumptions);
		newAssumptions.clear();
		return newAssumptionsTmp;
	}

	@Override
	public void clean() {
		allAssumptions.clear();
	}

	/*
	 * Methods for client analyses
	 */

	/** @return the {@link SymbolFactory} */
	final protected SymbolFactory getSymbolFactory() {
		return symbolFactory;
	}

	/** Adds an assumption to the data flow engine */
	final protected void assume(Assumption assumption) {
		assumption.setDataFlowVisitor(this);
		newAssumptions.add(assumption);
	}

	/**
	 * Called when a {@link ControlFlowElement} is visited that has an effect on a symbol.
	 *
	 * @param effect
	 *            the {@link EffectInfo}
	 * @param cfe
	 *            the {@link ControlFlowElement} that causes the effect
	 */
	protected void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		// overwrite me
	}

	/**
	 * Called when a {@link ControlFlowElement} which is part of a condition is related to a symbol.
	 *
	 * @param guard
	 *            guard that is visited
	 */
	protected void visitGuard(Guard guard) {
		// overwrite me
	}

}
