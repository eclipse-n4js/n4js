/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.dataflow;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;

/**
 *
 */
public enum GuardAssertion {
	AlwaysHolds, NeverHolds, MayHold;

	public boolean canHold() {
		return this == AlwaysHolds || this == MayHold;
	}

	private static enum BooleanExpression {
		or, xor, and, not, eq, neq
	}

	static GuardAssertion get(Expression condition, boolean negateTree, boolean negateCondition) {
		ArrayList<BooleanExpression> beList = getBooleanExpressions(condition, negateTree, negateCondition);
		HashSet<BooleanExpression> beSet = new HashSet<>(beList);
		boolean mayHolds = false;
		mayHolds |= beSet.contains(BooleanExpression.xor);
		mayHolds |= beSet.contains(BooleanExpression.eq);
		mayHolds |= beSet.contains(BooleanExpression.neq);
		if (mayHolds) {
			return GuardAssertion.MayHold;
		}

		simplify(beList, 0);
		return get(beList);
	}

	static private ArrayList<BooleanExpression> getBooleanExpressions(Expression condition, boolean negateTree,
			boolean negateCondition) {

		ArrayList<BooleanExpression> bExprs = new ArrayList<>();
		if (negateCondition) {
			bExprs.add(BooleanExpression.not);
		}
		addBooleanExpressions(bExprs, condition);
		if (negateTree) {
			bExprs.add(BooleanExpression.not);
		}
		return bExprs;
	}

	static private void addBooleanExpressions(ArrayList<BooleanExpression> bExprs, Expression condition) {
		BooleanExpression nextValue = null;
		if (condition instanceof UnaryExpression) {
			UnaryExpression ue = (UnaryExpression) condition;
			if (ue.getOp() == UnaryOperator.NOT) {
				nextValue = BooleanExpression.not;
			}
		} else if (condition instanceof BinaryBitwiseExpression) {
			BinaryBitwiseExpression bbe = (BinaryBitwiseExpression) condition;
			switch (bbe.getOp()) {
			case AND:
				nextValue = BooleanExpression.and; // can also be ignored since it's removed later anyway
				break;
			case OR:
				nextValue = BooleanExpression.or;
				break;
			case XOR:
				nextValue = BooleanExpression.xor;
				break;
			}
		} else if (condition instanceof EqualityExpression) {
			EqualityExpression ee = (EqualityExpression) condition;
			switch (ee.getOp()) {
			case EQ:
			case SAME:
				nextValue = BooleanExpression.eq;
				break;
			case NEQ:
			case NSAME:
				nextValue = BooleanExpression.neq;
				break;
			}
		}
		if (nextValue != null) {
			bExprs.add(nextValue);
		}

		EObject parent = condition.eContainer();
		if (parent instanceof Expression) {
			Expression parentCondition = (Expression) parent;
			addBooleanExpressions(bExprs, parentCondition); // tail recursion
		}
	}

	static private void simplify(ArrayList<BooleanExpression> bExpressions, int startIdx) {
		BooleanExpression be0 = (bExpressions.size() > startIdx + 0) ? bExpressions.get(startIdx + 0) : null;
		BooleanExpression be1 = (bExpressions.size() > startIdx + 1) ? bExpressions.get(startIdx + 1) : null;
		BooleanExpression be2 = (bExpressions.size() > startIdx + 2) ? bExpressions.get(startIdx + 2) : null;

		int reverseStartIdx = Math.max(0, startIdx - 2);
		if (be0 == BooleanExpression.and) {
			bExpressions.remove(startIdx);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}
		if (be0 == BooleanExpression.not && be1 == BooleanExpression.not) {
			bExpressions.remove(startIdx);
			bExpressions.remove(startIdx);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}
		if (be0 == BooleanExpression.not && be1 == BooleanExpression.or && be2 == BooleanExpression.not) {
			bExpressions.remove(startIdx);
			bExpressions.remove(startIdx);
			bExpressions.remove(startIdx);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}

		if (startIdx + 1 < bExpressions.size()) {
			simplify(bExpressions, startIdx + 1); // tail recursion
		}
	}

	static private GuardAssertion get(ArrayList<BooleanExpression> beList) {
		int notCount = 0;
		for (BooleanExpression be : beList) {
			if (be == BooleanExpression.or) {
				return MayHold;
			}
			if (be == BooleanExpression.not) {
				notCount++;
			}
		}

		if (notCount % 2 == 0) {
			return AlwaysHolds;
		} else {
			return NeverHolds;
		}
	}
}
