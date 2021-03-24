/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.utils

import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PostfixExpression
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.AssignmentOperator
import org.eclipse.xtext.EcoreUtil2

/**
 * General utitility methods for expressions.
 */
class ExpressionExtensions {

	/**
	 * Returns true if the subExpression actually is the left hand side expression of an assignment expression, that is
	 * its value could be changed when the assignment is evaluated. I.e. the subExpression
	 * could be returned by ancestor expression, e.g., if the subExpression is
	 * an operand of a binary logical expression.
	 * This method returns false, if the subExpression is an assignment expression itself!
	 * <p>
	 * Note that neither assignments nor conditional expression return any of their operands!
	 */
	static def boolean isLeftHandSide(EObject subExpression) {
		if (subExpression === null || subExpression instanceof AssignmentExpression) {
			return false;
		}
		var EObject expr = subExpression;
		while (expr.eContainer!==null && isPotentialEvalResult(expr.eContainer, expr)) {
			expr = expr.eContainer;
		}
		return expr !== null && expr.eContainer instanceof AssignmentExpression &&
			(expr.eContainer as AssignmentExpression).lhs == expr
	}


	/**
	 * Does the argument occur as operand in a (prefix or postfix) ++ or -- operation?
	 * <p>
	 * The increment and decrement operators "conceptually" involve both read- and write-access,
	 * unlike the LHS of a simple assignment. Granted, '+=' and similar compound assignments can be seen
	 * as comprising read- and write-access.
	 */
	static def boolean isIncOrDecTarget(EObject subExpression) {
		if (subExpression === null || subExpression instanceof AssignmentExpression) {
			return false;
		}
		var EObject expr = subExpression;
		while (expr.eContainer!==null && isPotentialEvalResult(expr.eContainer, expr)) {
			expr = expr.eContainer;
		}
		if (expr.eContainer instanceof PostfixExpression) {
			return true
		}
		if (expr.eContainer instanceof UnaryExpression) {
			val ue = expr.eContainer as UnaryExpression;
			if (ue.op == UnaryOperator.INC || ue.op === UnaryOperator.DEC) {
				return true
			}
		}
		return false
	}

	static def boolean isBothReadFromAndWrittenTo(EObject expr) {
		if (isLeftHandSide(expr)) {
			val a = EcoreUtil2.getContainerOfType(expr, AssignmentExpression)
			return a.op !== AssignmentOperator.ASSIGN
		}
		return isIncOrDecTarget(expr)
	}

	/**
	 * Returns true if the (value of the) subExpression could be returned by container expression, e.g., if the subExpression is
	 * an operand of a binary logical expression.
	 * <p>
	 * Most types of expressions return false here. Note that neither assignments nor conditional expression return any of their operands, thus, both type of expressions
	 * always return false as well.
	 * <p>
	 * Example: the unparenthesized {@code arr[0]} below {@link #isLeftHandSide} although its direct container isn't an assignment but a {@link ParenExpression}
	 * <pre>
	 * {@code
	 * var arr = [1];
	 * (arr[0]) = 6;
	 * console.log(arr[0])
	 * }
	 * </pre>
	 */
	private static def dispatch boolean isPotentialEvalResult(EObject container, EObject childExpression) {
		return false;
	}

	private static def dispatch boolean isPotentialEvalResult(ParenExpression container, Expression childExpression) {
		return childExpression !== null && childExpression.eContainer === container;
	}

	private static def dispatch boolean isPotentialEvalResult(BinaryLogicalExpression container, Expression childExpression) {
		return childExpression !== null && childExpression.eContainer === container;
	}

	private static def dispatch boolean isPotentialEvalResult(CommaExpression container, Expression childExpression) {
		return container !== null && container.exprs.last === childExpression;
	}
}
