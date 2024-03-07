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
package org.eclipse.n4js.scoping.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.xtext.EcoreUtil2;

/**
 * General utility methods for expressions.
 */
public class ExpressionExtensions {

	/**
	 * Returns true if the subExpression actually is the left hand side expression of an assignment expression, that is
	 * its value could be changed when the assignment is evaluated. I.e. the subExpression could be returned by ancestor
	 * expression, e.g., if the subExpression is an operand of a binary logical expression. This method returns false,
	 * if the subExpression is an assignment expression itself!
	 * <p>
	 * Note that neither assignments nor conditional expression return any of their operands!
	 */
	public static boolean isLeftHandSide(EObject subExpression) {
		if (subExpression == null || subExpression instanceof AssignmentExpression) {
			return false;
		}
		EObject expr = subExpression;
		while (expr.eContainer() != null && isPotentialEvalResult(expr.eContainer(), expr)) {
			expr = expr.eContainer();
		}
		return expr.eContainer() instanceof AssignmentExpression &&
				((AssignmentExpression) expr.eContainer()).getLhs() == expr;
	}

	/**
	 * Does the argument occur as operand in a (prefix or postfix) ++ or -- operation?
	 * <p>
	 * The increment and decrement operators "conceptually" involve both read- and write-access, unlike the LHS of a
	 * simple assignment. Granted, '+=' and similar compound assignments can be seen as comprising read- and
	 * write-access.
	 */
	public static boolean isIncOrDecTarget(EObject subExpression) {
		if (subExpression == null || subExpression instanceof AssignmentExpression) {
			return false;
		}
		EObject expr = subExpression;
		while (expr.eContainer() != null && isPotentialEvalResult(expr.eContainer(), expr)) {
			expr = expr.eContainer();
		}
		if (expr.eContainer() instanceof PostfixExpression) {
			return true;
		}
		if (expr.eContainer() instanceof UnaryExpression) {
			UnaryExpression ue = (UnaryExpression) expr.eContainer();
			if (ue.getOp() == UnaryOperator.INC || ue.getOp() == UnaryOperator.DEC) {
				return true;
			}
		}
		return false;
	}

	/***/
	public static boolean isBothReadFromAndWrittenTo(EObject expr) {
		if (isLeftHandSide(expr)) {
			AssignmentExpression a = EcoreUtil2.getContainerOfType(expr, AssignmentExpression.class);
			return a.getOp() != AssignmentOperator.ASSIGN;
		}
		return isIncOrDecTarget(expr);
	}

	/**
	 * Returns true if the (value of the) subExpression could be returned by container expression, e.g., if the
	 * subExpression is an operand of a binary logical expression.
	 * <p>
	 * Most types of expressions return false here. Note that neither assignments nor conditional expression return any
	 * of their operands, thus, both type of expressions always return false as well.
	 * <p>
	 * Example: the unparenthesized {@code arr[0]} below {@link #isLeftHandSide} although its direct container isn't an
	 * assignment but a {@link ParenExpression}
	 *
	 * <pre>
	 * {@code
	 * var arr = [1];
	 * (arr[0]) = 6;
	 * console.log(arr[0])
	 * }
	 * </pre>
	 */
	private static boolean isPotentialEvalResult(final EObject container, final EObject childExpression) {
		if (container instanceof ParenExpression && childExpression instanceof Expression) {
			return isPotentialEvalResult((ParenExpression) container, (Expression) childExpression);
		} else if (container instanceof BinaryLogicalExpression && childExpression instanceof Expression) {
			return isPotentialEvalResult((BinaryLogicalExpression) container, (Expression) childExpression);
		} else if (container instanceof CommaExpression && childExpression instanceof Expression) {
			return isPotentialEvalResult((CommaExpression) container, (Expression) childExpression);
		} else if (container != null && childExpression != null) {
			return false;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(container, childExpression).toString());
		}
	}

	private static boolean isPotentialEvalResult(ParenExpression container, Expression childExpression) {
		return childExpression != null && childExpression.eContainer() == container;
	}

	private static boolean isPotentialEvalResult(BinaryLogicalExpression container, Expression childExpression) {
		return childExpression != null && childExpression.eContainer() == container;
	}

	private static boolean isPotentialEvalResult(CommaExpression container, Expression childExpression) {
		return container != null && last(container.getExprs()) == childExpression;
	}

}
