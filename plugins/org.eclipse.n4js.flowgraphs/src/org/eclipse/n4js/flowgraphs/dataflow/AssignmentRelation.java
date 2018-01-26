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
import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.n4JS.Expression;

/**
 * Holds information about an assignment regarding exactly one {@link Symbol} whose value gets written.
 */
public class AssignmentRelation {
	/** {@link Symbol} whose value is changed */
	final public Symbol leftSymbol;
	/**
	 * List of all possible right hand sides that are assigned to the {@link #leftSymbol}.<br/>
	 * Either of type {@link Symbol} or {@link Expression}.
	 */
	final public Object[] rhsObjects;

	/** Constructor */
	AssignmentRelation(Symbol leftSymbol, Object... rhsObjects) {
		this.leftSymbol = leftSymbol;
		this.rhsObjects = rhsObjects;
	}

	@Override
	public String toString() {
		String str = "";
		str += leftSymbol.toString() + " := ";

		int count = 0;
		for (Object obj : rhsObjects) {
			if (count++ > 0) {
				str += " | ";
			}
			str += (obj instanceof Expression) ? FGUtils.getSourceText((EObject) obj) : obj;
		}
		return str;
	}

}
