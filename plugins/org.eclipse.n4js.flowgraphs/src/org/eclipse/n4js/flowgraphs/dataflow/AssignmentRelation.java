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

import org.eclipse.n4js.n4JS.Expression;

/**
 *
 */
public class AssignmentRelation {
	final public Symbol leftSymbol;
	final public Symbol rightSymbol;
	final public Expression assignedValue;

	AssignmentRelation(Symbol leftSymbol, Symbol rightSymbol, Expression assignedValue) {
		this.leftSymbol = leftSymbol;
		this.rightSymbol = rightSymbol;
		this.assignedValue = assignedValue;
	}

	@Override
	public String toString() {
		return leftSymbol.toString() + " := " + rightSymbol.toString();
	}
}
