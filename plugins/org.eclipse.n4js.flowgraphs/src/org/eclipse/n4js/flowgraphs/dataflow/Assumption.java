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

import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;

/**
 * {@link Assumption}s are used to check if a specific {@link Symbol} or its aliases are used in a specific way or not.
 * For this checking, the {@code hold} methods can be used.<br/>
 * The life-cycle of {@link Assumption}s is aligned to the life-cycle of a branch. With every fork, the
 * {@link Assumption} is forked and with every merge, the method {@link #mergeWith(Assumption)} is called.
 */
abstract public class Assumption {
	/** Key for identification of this {@link Assumption}. Use {@link #getKey(ControlFlowElement, Symbol)} */
	public final Object key;
	/** {@link ControlFlowElement} where this {@link Assumption} was created */
	public final ControlFlowElement creationSite;
	/** Initial symbol this assumption refers to */
	public final Symbol symbol;
	/** Set of all symbols that are transitively assigned to {@link #symbol} */
	public final Set<Symbol> aliases = new HashSet<>();
	/***/
	public final Set<Symbol> failingStructuralAliases = new HashSet<>();
	/** Set of all symbols that might be indirectly assigned to {@link #symbol} through receiver change */
	public final Map<EObject, Symbol> symbolForDeclaration = new HashMap<>();
	/** The {@link Symbol} that caused this {@link Assumption} to fail */
	public Symbol failedSymbol;

	private boolean active = true;
	private boolean failed = false;
	private DataFlowVisitor dataFlowVisitor;
	private final Assumption originalAssumption;
	private int meetCount = 0;

	/** Constructor */
	public Assumption(ControlFlowElement cfe, Symbol symbol) {
		checkState(cfe != null);
		checkState(symbol != null);

		this.key = getKey(cfe, symbol);
		this.creationSite = cfe;
		this.symbol = symbol;
		addAlias(symbol);
		this.originalAssumption = this;
	}

	/** Constructor to create a copy */
	public Assumption(Assumption assumption) {
		this.key = assumption.key;
		this.creationSite = assumption.creationSite;
		this.symbol = assumption.symbol;
		this.aliases.addAll(assumption.aliases);
		this.failingStructuralAliases.addAll(assumption.failingStructuralAliases);
		this.symbolForDeclaration.putAll(assumption.symbolForDeclaration);
		this.dataFlowVisitor = assumption.dataFlowVisitor;
		this.originalAssumption = assumption.originalAssumption;
	}

	/** @return a key that is based on the given objects */
	protected Object getKey(ControlFlowElement cfe, Symbol pSymbol) {
		return Objects.hash(cfe, pSymbol, getClass());
	}

	/** @return a copy of this instance */
	abstract public Assumption copy();

	/**
	 * <b>Note:</b> Call this method when overwriting it.
	 *
	 * @param assumption
	 *            the {@link Assumption} this {@link Assumption} will be merged with
	 */
	public void mergeWith(Assumption assumption) {
		checkState(this.symbol == assumption.symbol);

		this.aliases.addAll(assumption.aliases);
		this.failingStructuralAliases.addAll(assumption.failingStructuralAliases);
		this.symbolForDeclaration.putAll(assumption.symbolForDeclaration);
	}

	/** Called only from {@link DataFlowVisitor#assume(Assumption)} */
	void setDataFlowVisitor(DataFlowVisitor dataFlowVisitor) {
		this.dataFlowVisitor = dataFlowVisitor;
	}

	/** Adds a new {@link Symbol} that is aliases with this assumption */
	protected void addAlias(Symbol alias) {
		this.aliases.add(alias);
		if (alias.getContext() != null) {
			this.symbolForDeclaration.put(alias.getDeclaration(), alias);
		}
	}

	/** @return the {@link DataFlowVisitor} this {@link Assumption} belongs to */
	public DataFlowVisitor getDataFlowVisitor() {
		return dataFlowVisitor;
	}

	/** Deactivates the given {@link Symbol}, i.e. further aliases of this {@link Symbol} are ignored */
	public void deactivateAlias(Symbol ignoreSymbol) {
		this.aliases.remove(ignoreSymbol);
		this.originalAssumption.meetCount++;
	}

	/** Deactivates this {@link Assumption} */
	protected void deactivate() {
		active = false;
	}

	/** @return true iff this assumption failed */
	public boolean isFailed() {
		return failed;
	}

	/**
	 * By default, {@link Assumption}s are active, i.e. the holdOn methods get called. In case the method
	 * {@link #deactivate()} was called, this {@link Assumption} is inactive.
	 *
	 * @return true iff {@link #deactivate()} was never called before on this {@link Assumption}
	 */
	final public boolean isActive() {
		return active;
	}

	void callHoldsOnDataflow(Symbol lhs, Symbol rhs, ControlFlowElement cfe) {
		if (failingStructuralAliases.contains(rhs)) {
			handleHolds(rhs, false);
		} else {
			boolean holds = holdsOnDataflow(lhs, rhs, cfe);
			handleHolds(rhs, holds);
			aliases.remove(lhs);
			aliases.add(rhs);
		}
	}

	/**
	 * This method gets called on {@link Expression}s that trigger the value of {@code rhs} to be assigned to
	 * {@code lhs}. For instance, an {@link AssignmentExpression} can trigger such a dataflow.
	 *
	 * @param lhs
	 *            the {@link Symbol} whose value is overwritten
	 * @param rhs
	 *            the {@link Symbol} that provides the new value
	 * @param cfe
	 *            the {@link ControlFlowElement} that triggers the dataflow
	 * @return true iff the assumption holds on the given dataflow
	 */
	public boolean holdsOnDataflow(Symbol lhs, Symbol rhs, ControlFlowElement cfe) {
		return true;
	}

	void callHoldsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
		boolean holds = holdsOnEffect(effect, cfe);
		handleHolds(effect.symbol, holds);
	}

	/**
	 * This method gets called on {@link Expression}s that have an effect on the given alias.
	 *
	 * @param effect
	 *            the {@link EffectInfo} that is performed on the aliased symbol due to the container
	 * @param container
	 *            the {@link ControlFlowElement} that contains the given alias
	 * @return true iff the assumption holds on the given alias symbol and its container
	 */
	public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
		return true;
	}

	void callHoldsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		boolean holds = holdsOnGuard(effect, cfe, must, inverse);
		handleHolds(effect.symbol, holds);
	}

	/**
	 * This method gets called on {@link Expression}s that guard branches such as in {@link IfStatement},
	 * {@link ConditionalExpression} and loops.
	 *
	 * @param effect
	 *            the {@link EffectInfo} that is performed on the aliased symbol due to the container
	 * @param cfe
	 *            the {@link ControlFlowElement} that contains the given alias. The {@code container} is always an
	 *            {@link Expression} with a boolean return type.
	 * @param must
	 *            true iff the container must be true, false iff the container can be true
	 * @param inverse
	 *            true iff the semantics of the container has to be inverted (i.e. in 'else' branch of 'if' statements),
	 *            otherwise false
	 * @return true iff the assumption holds
	 */
	@Deprecated // not implemented yet
	public boolean holdsOnGuard(EffectInfo effect, ControlFlowElement cfe, boolean must, boolean inverse) {
		return true;
	}

	void callHoldsAfterall() {
		boolean holds = holdsAfterall();
		handleHolds(null, holds);
	}

	/**
	 * Called finally after the last copy of this {@link Assumption} was deactivated. Ignores {@link #isActive()}.
	 *
	 * @return true iff the assumption holds
	 */
	public boolean holdsAfterall() {
		return true;
	}

	/** Deactivates this {@link Assumption} */
	private void failed() {
		failed = true;
	}

	/** Handles behavior of the assumption based on the result of the holdOn methods */
	protected void handleHolds(Symbol pFailedSymbol, boolean holds) {
		if (!holds) {
			if (aliases.contains(pFailedSymbol)) {
				getDataFlowVisitor().failedAssumptions.add(this);
				failed();
				failedSymbol = pFailedSymbol;
				deactivate();
			} else {
				// assume pFailedSymbol is a structural alias to one of the aliases, i.e.:
				// aliases.stream().anyMatch(a -> pFailedSymbol.isStrucuralAlias(a));
				failingStructuralAliases.add(pFailedSymbol);
			}
			return;
		}
		if (aliases.isEmpty()) {
			deactivate();
			return;
		}
	}

	/** @return true iff all {@link Assumption}s failed that were copies from each other */
	public boolean allFailed() {
		return originalAssumption.meetCount == 0;
	}

	@Override
	public String toString() {
		return symbol.toString() + " " + getClass().getSimpleName();
	}

	/**
	 */
	public void failOnStructuralAlias(Symbol lSymbol) {
		getDataFlowVisitor().failedAssumptions.add(this);
		failed();
		failedSymbol = lSymbol;
		deactivate();
	}
}
