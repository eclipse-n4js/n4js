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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.SymbolFactory;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Utilities for working with destructuring patterns
 */
public class DestructureUtilsForSymbols {

	/** @return the {@link EObject} that is assigned to the given lhs element in the pattern. Defaults are respected. */
	public static EObject getValueFromDestructuring(SymbolFactory symbolFactory, EObject nodeElem) {
		Pair<EObject, EObject> values = DestructNode.getValueFromDestructuring(nodeElem);
		if (values == null) {
			return null;
		}

		EObject assignedValue = values.getKey();
		EObject defaultValue = values.getValue();

		return respectDefaultValue(symbolFactory, assignedValue, defaultValue);
	}

	/**
	 * @return the {@link EObject} that is assigned to the given {@link DestructNode} in the pattern. Defaults are
	 *         respected.
	 */
	public static EObject getValueFromDestructuring(SymbolFactory symbolFactory, DestructNode dNode) {
		if (dNode == null) {
			return null;
		}

		EObject assignedValue = dNode.getAssignedElem();
		EObject defaultValue = dNode.getDefaultExpr();

		return respectDefaultValue(symbolFactory, assignedValue, defaultValue);
	}

	private static EObject respectDefaultValue(SymbolFactory symbolFactory, EObject assignedValue,
			EObject defaultValue) {

		if (assignedValue == null) {
			return defaultValue;
		}
		if (assignedValue instanceof Expression && symbolFactory.isUndefined((Expression) assignedValue)) {
			if (defaultValue != null) {
				return defaultValue;
			} else {
				return assignedValue;
			}
		}
		return assignedValue;
	}
}
