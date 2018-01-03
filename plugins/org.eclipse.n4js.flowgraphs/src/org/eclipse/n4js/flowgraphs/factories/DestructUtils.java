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
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Utilities for working with destructuring patterns
 */
public class DestructUtils {

	/** @return the top-most instance of VariableBinding within the Statement starting from the given node. */
	public static VariableBinding getTopVariableBinding(EObject node) {
		EObject topExpr = node;
		while (topExpr != null) {
			EObject parent = topExpr.eContainer();
			if (parent instanceof Statement) {
				if (topExpr instanceof VariableBinding) {
					return (VariableBinding) topExpr;
				} else {
					return null;
				}
			}
			topExpr = parent;
		}
		return null;
	}

	/** @return the top-most instance of VariableBinding within the Statement starting from the given node. */
	public static EObject getTopUnderStatement(EObject node) {
		EObject topExpr = node;
		while (topExpr != null) {
			EObject parent = topExpr.eContainer();
			if (parent instanceof Statement) {
				return topExpr;
			}
			topExpr = parent;
		}
		return null;
	}

	/** @return true iff the given {@link Expression} is an {@link ArrayLiteral} the child of an assignment */
	public static boolean isDestructuring(Expression expr) {
		boolean isDestructuring = expr instanceof ArrayLiteral;
		isDestructuring = isDestructuring || expr instanceof ObjectLiteral;
		isDestructuring = isDestructuring && expr.eContainer() instanceof AssignmentExpression;
		return isDestructuring;
	}

	/** @return all {@link IdentifierRef} of variables that are written in the given assignment */
	public static List<Expression> getDeclaredIdRefs(AssignmentExpression assignExpr) {
		List<Expression> idRefs = new LinkedList<>();
		DestructNode dn = DestructNode.unify(assignExpr);
		Iterator<DestructNode> allNestedNodes = dn.stream().iterator();

		while (allNestedNodes.hasNext()) {
			EObject eobj = allNestedNodes.next().getAstElement();
			if (eobj instanceof ArrayElement) {
				Expression expr = ((ArrayElement) eobj).getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add(((AssignmentExpression) expr).getLhs());
				} else {
					idRefs.add(expr);
				}

			} else if (eobj instanceof PropertyNameValuePairSingleName) {
				PropertyNameValuePairSingleName expr = (PropertyNameValuePairSingleName) eobj;
				idRefs.add(expr.getIdentifierRef());

			} else if (eobj instanceof PropertyNameValuePair) {
				Expression expr = ((PropertyNameValuePair) eobj).getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add(((AssignmentExpression) expr).getLhs());
				} else {
					idRefs.add(expr);
				}
			}
		}
		return idRefs;
	}

	/** @return the {@link EObject} that is assigned to the given lhs element in the pattern. Defaults are respected. */
	public static EObject getValueFromDestructuring(EObject nodeElem) {
		Pair<EObject, EObject> values = DestructNode.getValueFromDestructuring(nodeElem);
		if (values == null) {
			return null;
		}

		EObject assignedValue = values.getKey();
		EObject defaultValue = values.getValue();

		if (assignedValue == null
				|| (assignedValue instanceof Expression && SymbolFactory.isUndefined((Expression) assignedValue))) {
			return defaultValue;
		}
		return assignedValue;
	}
}
