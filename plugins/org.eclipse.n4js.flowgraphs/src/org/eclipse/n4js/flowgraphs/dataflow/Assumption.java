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
	@Deprecated
	public final Set<Symbol> failingStructuralAliases = new HashSet<>();
	/** Set of all symbols that might be indirectly assigned to {@link #symbol} through receiver change */
	@Deprecated
	public final Map<EObject, Symbol> symbolForDeclaration = new HashMap<>();
	/** The {@link Symbol} that caused this {@link Assumption} to fail. Can be null. */
	public Symbol failedSymbol;
	/** The {@link Guard} that caused this {@link Assumption} to fail. Can be null. */
	public Guard failedGuard;

	private boolean active = true;
	private DataFlowVisitor dataFlowVisitor;
	private final Assumption originalAssumption;
	private int aliasPassedCount = 0;
	private int guardPassedCount = 0;
	private int copyCount = 0;

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
		checkState(assumption.active);

		this.key = assumption.key;
		this.creationSite = assumption.creationSite;
		this.symbol = assumption.symbol;
		this.aliases.addAll(assumption.aliases);
		this.failingStructuralAliases.addAll(assumption.failingStructuralAliases);
		this.symbolForDeclaration.putAll(assumption.symbolForDeclaration);
		this.dataFlowVisitor = assumption.dataFlowVisitor;
		this.originalAssumption = assumption.originalAssumption;
		this.failedSymbol = assumption.failedSymbol;
		this.failedGuard = assumption.failedGuard;
		this.originalAssumption.copyCount++;
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
		if (this.failedSymbol == null) {
			this.failedSymbol = assumption.failedSymbol;
		}
		if (this.failedGuard == null || assumption.failedGuard == null) {
			this.failedGuard = null;
		}
		if (this.active && assumption.active) {
			this.originalAssumption.copyCount--;
		}
		this.active = this.active || assumption.active;
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
	public void aliasPassed(Symbol ignoreSymbol) {
		this.aliases.remove(ignoreSymbol);
		this.originalAssumption.aliasPassedCount++;
	}

	/** Deactivates this {@link Assumption} */
	protected void deactivate() {
		active = false;
		originalAssumption.copyCount--;
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

	void callHoldsOnDataflow(AssignmentRelation assignRelation) {
		checkState(isActive());

		Symbol lhs = assignRelation.leftSymbol;
		Symbol rhs = assignRelation.rightSymbol;
		if (failingStructuralAliases.contains(rhs)) {
			handleHolds(rhs, HoldAssertion.NeverHolds);
		} else if (failingStructuralAliases.contains(lhs)) {
			failingStructuralAliases.remove(lhs);
			failingStructuralAliases.add(rhs);
		} else {
			HoldAssertion holds = holdsOnDataflow(assignRelation);
			handleHolds(lhs, holds);
			if (rhs != null && rhs.isVariableSymbol()) {
				aliases.remove(lhs);
				aliases.add(rhs);
			}
		}
	}

	/**
	 * This method gets called on {@link Expression}s that trigger the value of {@code rhs} to be assigned to
	 * {@code lhs}. For instance, an {@link AssignmentExpression} can trigger such a dataflow.
	 *
	 * @param assignRelation
	 *            TODO
	 *
	 * @return true iff the assumption holds on the given dataflow
	 */
	public HoldAssertion holdsOnDataflow(AssignmentRelation assignRelation) {
		return HoldAssertion.MayHold;
	}

	void callHoldsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
		checkState(isActive());

		HoldAssertion holds = holdsOnEffect(effect, cfe);
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
	public HoldAssertion holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
		return HoldAssertion.MayHold;
	}

	void callHoldsOnGuard(Guard guard) {
		checkState(isActive());

		HoldAssertion holds = holdsOnGuard(guard);
		handleHolds(guard, holds);
	}

	/**
	 * This method gets called on {@link Expression}s that guard branches such as in {@link IfStatement},
	 * {@link ConditionalExpression} and loops.
	 *
	 * @param guard
	 *            the {@link Guard} that holds on one of the aliases of this {@link Assumption}
	 *
	 * @return true iff the assumption holds
	 */
	public HoldAssertion holdsOnGuard(Guard guard) {
		return HoldAssertion.MayHold;
	}

	void callHoldsAfterall() {
		checkState(isActive());

		boolean holds = holdsAfterall();
		if (failedSymbol != null) {
			handleHolds(failedSymbol, HoldAssertion.NeverHolds);
		} else if (failedGuard != null) {
			handleHolds(failedGuard, HoldAssertion.NeverHolds);
		} else {
			handleHolds(holds);
		}
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
		getDataFlowVisitor().failedAssumptions.add(this);
		deactivate();
	}

	private void handleHolds(Symbol pSymbol, HoldAssertion holds) {
		if (holds == HoldAssertion.NeverHolds) {
			if (aliases.contains(pSymbol)) {
				failed();
				failedSymbol = pSymbol;
			} else {
				// assume pFailedSymbol is a structural alias to one of the aliases, i.e.:
				// aliases.stream().anyMatch(a -> pFailedSymbol.isStrucuralAlias(a));
				failingStructuralAliases.add(pSymbol);
			}
			return;
		}
		if (holds == HoldAssertion.AlwaysHolds) {
			aliasPassed(pSymbol);
		}
		if (aliases.isEmpty()) {
			deactivate();
		}
	}

	private void handleHolds(Guard guard, HoldAssertion holds) {
		if (holds == HoldAssertion.NeverHolds) {
			failedGuard = guard;
			if (noCopies() && noGuardPassed()) {
				failed();
			}
		}
		if (holds == HoldAssertion.AlwaysHolds) {
			originalAssumption.guardPassedCount++;
			deactivate();
		}

		if (aliases.isEmpty()) {
			deactivate();
		}
	}

	private void handleHolds(boolean holds) {
		if (!holds) {
			failed();
			return;
		}
		if (aliases.isEmpty()) {
			deactivate();
		}
	}

	/** @return true iff all {@link Assumption}s failed */
	public boolean noAliasPassed() {
		return originalAssumption.aliasPassedCount == 0;
	}

	/** @return true iff all {@link Guard}s failed */
	public boolean noGuardPassed() {
		return originalAssumption.guardPassedCount == 0;
	}

	/** @return true iff there are no copies of this {@link Assumption} on other {@link DataFlowBranchWalker}s */
	private boolean noCopies() {
		return originalAssumption.copyCount == 0;
	}

	@Override
	public String toString() {
		return symbol.toString() + " " + getClass().getSimpleName();
	}

	/**
	 */
	public void failOnStructuralAlias(Symbol lSymbol) {
		failed();
		failedSymbol = lSymbol;
	}
}
