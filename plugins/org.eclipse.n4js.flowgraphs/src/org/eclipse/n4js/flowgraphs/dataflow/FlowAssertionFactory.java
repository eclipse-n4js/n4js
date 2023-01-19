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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
		LinkedList<BooleanExpression> beList = getBooleanExpressions(topContainer, conditionParent, negateCondition);
		if (beList.size() == 1) {
			// early return for performance
			BooleanExpression bExpr = beList.get(0);
			if (bExpr == BooleanExpression.eq || bExpr == BooleanExpression.neq) {
				return GuardAssertion.MayHolds;
			}
		}

		return get(beList, negateTree);
	}

	static private LinkedList<BooleanExpression> getBooleanExpressions(EObject topContainer, EObject condition,
			boolean negateCondition) {

		LinkedList<BooleanExpression> bExprs = new LinkedList<>();
		if (negateCondition) {
			bExprs.add(BooleanExpression.not);
		}
		addBooleanExpressions(topContainer, bExprs, condition);

		return bExprs;
	}

	static private void addBooleanExpressions(EObject topContainer, List<BooleanExpression> bExprs, EObject condition) {
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

			if (nextValue == BooleanExpression.eq || nextValue == BooleanExpression.neq) {
				// early return for performance
				bExprs.clear();
				bExprs.add(nextValue);
				return;
			}
		}

		EObject parent = condition.eContainer();
		addBooleanExpressions(topContainer, bExprs, parent); // tail recursion
	}

	static private void simplify(LinkedList<BooleanExpression> bExpressions) {

		ListIterator<BooleanExpression> iter = bExpressions.listIterator();

		while (iter.hasNext()) {
			BooleanExpression be0 = iter.next();
			if (!iter.hasNext()) {
				return;
			}
			BooleanExpression be1 = iter.next();

			if (be0 == BooleanExpression.and && be1 == BooleanExpression.and) {
				iter.remove();
				iter.previous();
				continue;
			}
			if (be0 == BooleanExpression.or && be1 == BooleanExpression.or) {
				iter.remove();
				iter.previous();
				continue;
			}
			if (be0 == BooleanExpression.not && be1 == BooleanExpression.not) {
				iter.remove();
				iter.previous();
				iter.remove();
				continue;
			}
			if (be0 == BooleanExpression.not && be1 == BooleanExpression.or) {
				iter.remove();
				iter.previous();
				iter.remove();
				iter.add(BooleanExpression.and);
				iter.add(BooleanExpression.not);
				iter.previous();
				continue;
			}
			if (be0 == BooleanExpression.not && be1 == BooleanExpression.and) {
				iter.remove();
				iter.previous();
				iter.remove();
				iter.add(BooleanExpression.or);
				iter.add(BooleanExpression.not);
				iter.previous();
				continue;
			}
		}
	}

	static private GuardAssertion get(LinkedList<BooleanExpression> beList, boolean negateTree) {
		if (negateTree) {
			beList.add(BooleanExpression.not);
		}
		simplify(beList);

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

		return GuardAssertion.MayHolds;
	}

}
