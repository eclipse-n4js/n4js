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
package org.eclipse.n4js.flowgraphs.analyses;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 *
 */
abstract public class DataFlowVisitor {
	/** {@link TraverseDirection} of this visitor */
	protected final TraverseDirection mode;
	Collection<Assumption> assumptions = new LinkedList<>();

	/** Constructor. Default {@link TraverseDirection} is {@link TraverseDirection#Backward} */
	public DataFlowVisitor() {
		this(TraverseDirection.Backward);
	}

	/** Constructor. */
	public DataFlowVisitor(TraverseDirection mode) {
		this.mode = mode;
	}

	/**
	 * Called when a {@link ControlFlowElement} is visited that has an effect on a symbol
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
	 * Called when a {@link ControlFlowElement} which is part of a condition is related to a symbol
	 *
	 * @param effect
	 *            the {@link EffectInfo}
	 * @param cfe
	 *            the {@link ControlFlowElement} that causes the effect
	 * @param must
	 *            is true iff the expression {@code cfe} is mandatory
	 * @param inverse
	 *            is true iff the expression is negated (due to negation or due to the else branch)
	 */
	protected void visitGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		// overwrite me
	}

	/** Adds an assumption to the data flow engine */
	protected void assume(Assumption assumption) {
		assumptions.add(assumption);
	}
}
