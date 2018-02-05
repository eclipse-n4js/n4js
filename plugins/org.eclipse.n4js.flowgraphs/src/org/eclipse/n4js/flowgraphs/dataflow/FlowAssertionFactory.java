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
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;

/**
 * Creates {@link GuardAssertion}s from {@link Expression}s used as conditions in the source code.
 */
public class FlowAssertionFactory {

	private static enum BooleanExpression {
		or, and, not, eq, neq
	}

	/** @return an {@link GuardAssertion} derived from a condition. */
	public static GuardAssertion getGuard(EObject topContainer, EObject condition, boolean negateTree,
			boolean negateCondition) {

		EObject conditionParent = condition.eContainer();
		ArrayList<BooleanExpression> beList = getBooleanExpressions(topContainer, conditionParent, negateCondition);
		HashSet<BooleanExpression> beSet = new HashSet<>(beList);
		boolean mayHolds = false;
		mayHolds |= beSet.contains(BooleanExpression.eq);
		mayHolds |= beSet.contains(BooleanExpression.neq);
		if (mayHolds) {
			return GuardAssertion.MayHolds;
		}

		return get(beList, negateTree);
	}

	static private ArrayList<BooleanExpression> getBooleanExpressions(EObject topContainer, EObject condition,
			boolean negateCondition) {

		ArrayList<BooleanExpression> bExprs = new ArrayList<>();
		if (negateCondition) {
			bExprs.add(BooleanExpression.not);
		}
		addBooleanExpressions(topContainer, bExprs, condition);

		return bExprs;
	}

	static private void addBooleanExpressions(EObject topContainer, ArrayList<BooleanExpression> bExprs,
			EObject condition) {

		if (topContainer == condition) {
			return;
		}

		BooleanExpression nextValue = null;
		if (condition instanceof UnaryExpression) {
			UnaryExpression ue = (UnaryExpression) condition;
			if (ue.getOp() == UnaryOperator.NOT) {
				nextValue = BooleanExpression.not;
			}
		} else if (condition instanceof BinaryLogicalExpression) {
			BinaryLogicalExpression ble = (BinaryLogicalExpression) condition;
			switch (ble.getOp()) {
			case AND:
				nextValue = BooleanExpression.and;
				break;
			case OR:
				nextValue = BooleanExpression.or;
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
		addBooleanExpressions(topContainer, bExprs, parent); // tail recursion
	}

	static private void simplify(ArrayList<BooleanExpression> bExpressions, int startIdx) {
		BooleanExpression be0 = (bExpressions.size() > startIdx + 0) ? bExpressions.get(startIdx + 0) : null;
		BooleanExpression be1 = (bExpressions.size() > startIdx + 1) ? bExpressions.get(startIdx + 1) : null;

		int reverseStartIdx = Math.max(0, startIdx - 2);
		if (be0 == BooleanExpression.and && be1 == BooleanExpression.and) {
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
		if (be0 == BooleanExpression.or && be1 == BooleanExpression.or) {
			bExpressions.remove(startIdx);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}
		if (be0 == BooleanExpression.not && be1 == BooleanExpression.or) {
			bExpressions.remove(startIdx + 1);
			bExpressions.add(startIdx, BooleanExpression.and);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}
		if (be0 == BooleanExpression.not && be1 == BooleanExpression.and) {
			bExpressions.remove(startIdx + 1);
			bExpressions.add(startIdx, BooleanExpression.or);
			simplify(bExpressions, reverseStartIdx); // tail recursion
			return;
		}

		if (startIdx + 1 < bExpressions.size()) {
			simplify(bExpressions, startIdx + 1); // tail recursion
		}
	}

	static private GuardAssertion get(ArrayList<BooleanExpression> beList, boolean negateTree) {
		if (negateTree) {
			beList.add(BooleanExpression.not);
		}
		simplify(beList, 0);

		if (beList.size() == 0) {
			return GuardAssertion.AlwaysHolds;
		}
		if (beList.size() == 1) {
			switch (beList.get(0)) {
			case not:
				return GuardAssertion.NeverHolds;
			case and:
				return GuardAssertion.AlwaysHolds;
			case or:
				return GuardAssertion.MayHolds;
			default:
				return null;
			}
		}
		if (beList.size() == 2) {
			BooleanExpression be0 = beList.get(0);
			BooleanExpression be1 = beList.get(1);

			if (be0 == BooleanExpression.and && be1 == BooleanExpression.not) {
				return GuardAssertion.MayHolds;
			}

			if (be0 == BooleanExpression.or && be1 == BooleanExpression.not) {
				return GuardAssertion.NeverHolds;
			}
		}
		if (beList.size() > 2) {
			return null;
		}

		return null;
	}

}
