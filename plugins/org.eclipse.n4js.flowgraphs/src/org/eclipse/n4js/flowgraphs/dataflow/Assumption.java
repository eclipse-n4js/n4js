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

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IfStatement;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * {@link Assumption}s are used to check if a specific {@link Symbol} or its aliases are used in a specific way or not.
 * For this checking, the {@code hold} methods can be used.<br/>
 * The life-cycle of {@link Assumption}s is aligned to the life-cycle of a branch. With every fork, the
 * {@link Assumption} is forked and with every merge, the method {@link #mergeWith(Assumption)} is called.
 */
abstract public class Assumption {
	private final AssumptionGroup assumptionGroup;
	/** {@link ControlFlowElement} where this {@link Assumption} was created */
	public final ControlFlowElement creationSite;
	/** Initial symbol this assumption refers to */
	public final Symbol symbol;
	/** Set of all symbols that are transitively assigned to {@link #symbol} */
	public final Set<Symbol> aliases = new HashSet<>();

	/** {@link GuardType}s that definitely hold for this {@link Assumption} */
	public final Multimap<GuardType, Guard> guardsThatNeverHold = HashMultimap.create();
	/** {@link GuardType}s that definitely not hold for this {@link Assumption} */
	public final Multimap<GuardType, Guard> guardsThatAlwaysHold = HashMultimap.create();
	/**  */
	public final Set<HoldResult> failedBranches = new HashSet<>();
	/**  */
	public final Set<HoldResult> passedBranches = new HashSet<>();

	/**   */
	public HoldResult terminatingGuard;

	private boolean openBranch = true;
	private DataFlowVisitor dataFlowVisitor;

	private boolean FLAG_WAS_MERGED = false;

	/** Constructor */
	public Assumption(ControlFlowElement cfe, Symbol symbol) {
		checkState(cfe != null);
		checkState(symbol != null);

		this.assumptionGroup = new AssumptionGroup();
		this.assumptionGroup.add(this);

		this.creationSite = cfe;
		this.symbol = symbol;
		addAlias(symbol);
	}

	/** @return a copy of this instance */
	abstract public Assumption copy();

	/** Constructor to create a copy */
	public Assumption(Assumption assumption) {
		this.assumptionGroup = assumption.assumptionGroup;
		this.assumptionGroup.add(this);

		this.creationSite = assumption.creationSite;
		this.symbol = assumption.symbol;
		this.aliases.addAll(assumption.aliases);
		this.dataFlowVisitor = assumption.dataFlowVisitor;
		this.guardsThatNeverHold.putAll(assumption.guardsThatNeverHold);
		this.guardsThatAlwaysHold.putAll(assumption.guardsThatAlwaysHold);

		this.failedBranches.addAll(assumption.failedBranches);
		this.passedBranches.addAll(assumption.passedBranches);
		this.terminatingGuard = assumption.terminatingGuard;
		this.openBranch = assumption.openBranch;
	}

	/** @return a key that is based on the given objects */
	Object getKey() {
		return assumptionGroup;
	}

	int getParallelHash() {
		return Objects.hash(symbol, creationSite, this.getClass());
	}

	boolean isParallel(Assumption assumption) {
		if (assumptionGroup == assumption.assumptionGroup) {
			return true;
		}
		return getParallelHash() == assumption.getParallelHash();
	}

	/**
	 * <b>Note:</b> Call this method when overwriting it.
	 *
	 * @param assumption
	 *            the {@link Assumption} this {@link Assumption} will be merged with
	 */
	public void mergeWith(Assumption assumption) {
		if (assumption.FLAG_WAS_MERGED) {
			return;
		}
		checkState(this.symbol == assumption.symbol);
		// checkState(!assumption.FLAG_WAS_MERGED);

		mergeData(assumption);
		this.assumptionGroup.remove(assumption);
		this.assumptionGroup.assure(this);

		assumption.FLAG_WAS_MERGED = true;

		checkAndFinalize();
	}

	private void mergeData(Assumption assumption) {
		this.aliases.addAll(assumption.aliases);
		this.failedBranches.addAll(assumption.failedBranches);
		this.passedBranches.addAll(assumption.passedBranches);

		if (openBranch && assumption.openBranch) {
			this.guardsThatNeverHold.keySet().retainAll(assumption.guardsThatNeverHold.keySet());
			this.guardsThatAlwaysHold.keySet().retainAll(assumption.guardsThatAlwaysHold.keySet());
		} else if (assumption.openBranch) {
			this.guardsThatNeverHold.clear();
			this.guardsThatNeverHold.putAll(assumption.guardsThatNeverHold);
			this.guardsThatAlwaysHold.clear();
			this.guardsThatAlwaysHold.putAll(assumption.guardsThatAlwaysHold);
		}

		this.openBranch = this.openBranch || assumption.openBranch;
	}

	boolean isOpen() {
		return openBranch;
	}

	boolean isApplicable(Symbol sym) {
		if (isOpen()) {
			return aliases.contains(sym);
		}
		return false;
	}

	/** Called only from {@link DataFlowVisitor#assume(Assumption)} */
	void setDataFlowVisitor(DataFlowVisitor dataFlowVisitor) {
		this.dataFlowVisitor = dataFlowVisitor;
	}

	/** Adds a new {@link Symbol} that is aliases with this assumption */
	protected void addAlias(Symbol alias) {
		this.aliases.add(alias);
	}

	/** @return the {@link DataFlowVisitor} this {@link Assumption} belongs to */
	public DataFlowVisitor getDataFlowVisitor() {
		return dataFlowVisitor;
	}

	final void remove() {
		assumptionGroup.remove(this);
	}

	final boolean isFailed() {
		if (isDone()) {
			boolean branchFailed = !failedBranches.isEmpty();
			boolean guardsFailed = terminatingGuard != null && terminatingGuard.type == FlowAssertion.NeverHolds;
			return branchFailed || guardsFailed;
		}
		return false;
	}

	final boolean isPassed() {
		if (isDone()) {
			boolean branchPassed = failedBranches.isEmpty();
			boolean guardsPassed = terminatingGuard == null || terminatingGuard.type != FlowAssertion.NeverHolds;
			return branchPassed && guardsPassed;
		}
		return false;
	}

	final boolean isDone() {
		return assumptionGroup.noCopies() && !openBranch;
	}

	void callHoldsOnDataflow(Symbol lhs, Collection<Object> rhss) {
		checkState(isOpen());

		Set<FlowAssertion> resultSet = new HashSet<>();

		for (Object rhs : rhss) {
			Symbol rSymbol = null;
			HoldResult result = null;

			if (rhs instanceof Symbol) {
				rSymbol = (Symbol) rhs;
				result = holdsOnDataflow(lhs, rSymbol, null);
				aliases.remove(lhs);
				if (rSymbol.isVariableSymbol()) {
					aliases.add(rSymbol);
				}

			} else if (rhs instanceof Expression) {
				Expression rValue = (Expression) rhs;
				result = holdsOnDataflow(lhs, null, rValue);
			}

			if (result != null) {
				resultSet.add(result.type);
				handleHoldResult(result);
			}
		}

		if (resultSet.size() > 1 && resultSet.contains(FlowAssertion.MayHold)) {
			openBranch = true;
		}
	}

	/**
	 * This method gets called on {@link Expression}s that trigger the value of {@code rhs} to be assigned to
	 * {@code lhs}. For instance, an {@link AssignmentExpression} can trigger such a dataflow.
	 *
	 * @param lhs
	 *            {@link Symbol} on the left hand side of the assignment
	 * @param rSymbol
	 *            {@link Symbol} whose value is assigned. Either rSymbol xor rValue is null.
	 * @param rValue
	 *            {@link Expression} whose return value is assigned. Either rSymbol xor rValue is null.
	 *
	 * @return true iff the assumption holds on the given dataflow
	 */
	public HoldResult holdsOnDataflow(Symbol lhs, Symbol rSymbol, Expression rValue) {
		return HoldResult.MayHold;
	}

	void callHoldsOnEffect(EffectInfo effect, ControlFlowElement cfe) {
		checkState(isOpen());

		HoldResult holds = holdsOnEffect(effect, cfe);
		handleHoldResult(holds);
		checkAndFinalize();
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
	public HoldResult holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
		return HoldResult.MayHold;
	}

	void callHoldsOnGuards(Guard guard) {
		checkState(isOpen());

		switch (guard.asserts) {
		case AlwaysHolds:
			guardsThatAlwaysHold.put(guard.type, guard);
			break;
		case NeverHolds:
			guardsThatNeverHold.put(guard.type, guard);
			break;
		default:
			break;
		}
	}

	/**
	 * This method gets called on {@link Expression}s that guard branches such as in {@link IfStatement},
	 * {@link ConditionalExpression} and loops.
	 *
	 * @param neverHolding
	 *            {@link GuardType}s that definitely hold for this {@link Assumption}
	 * @param alwaysHolding
	 *            {@link GuardType}s that definitely not hold for this {@link Assumption}
	 *
	 * @return true iff the assumption holds
	 */
	public HoldResult holdsOnGuards(Multimap<GuardType, Guard> neverHolding,
			Multimap<GuardType, Guard> alwaysHolding) {

		return HoldResult.MayHold;
	}

	/**
	 *
	 * @return true iff the assumption holds
	 */
	public boolean holdsAfterall() {
		return true;
	}

	private void handleHoldResult(HoldResult holds) {
		if (holds.type == FlowAssertion.NeverHolds) {
			failedBranches.add(holds);
			openBranch = false;
		}
		if (holds.type == FlowAssertion.AlwaysHolds) {
			passedBranches.add(holds);
			openBranch = false;
		}
		if (aliases.isEmpty()) {
			openBranch = false;
		}
	}

	void checkAndFinalize() {
		if (assumptionGroup.noCopies()) {
			if (isOpen()) {
				finalizeGuards();
			}
			if (!isOpen() && isFailed()) {
				propagateFailed();
			}
		}
	}

	void finalizeGuards() {
		if (isOpen() && assumptionGroup.noCopies()) {
			HoldResult holds = holdsOnGuards(guardsThatNeverHold, guardsThatAlwaysHold);

			if (holds.type == FlowAssertion.AlwaysHolds) {
				terminatingGuard = holds;
				openBranch = false;
			}
			if (holds.type == FlowAssertion.NeverHolds) {
				terminatingGuard = holds;
				openBranch = false;
			}
			if (aliases.isEmpty()) {
				openBranch = false;
			}
		}
	}

	void propagateFailed() {
		if (getDataFlowVisitor().failedAssumptions.containsKey(this.getParallelHash())) {
			Assumption failedParallel = getDataFlowVisitor().failedAssumptions.get(getParallelHash());
			failedParallel.mergeData(this);
		} else {
			getDataFlowVisitor().failedAssumptions.put(this.getParallelHash(), this);
		}
	}

	@Override
	public String toString() {
		return symbol.toString() + " " + getClass().getSimpleName();
	}

}
