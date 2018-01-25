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
 * Hold information about an assignment regarding exactly one {@link Symbol} whose value gets written.
 */
public class AssignmentRelation {
	/** {@link Symbol} whose value is changed */
	final public Symbol leftSymbol;
	/** {@link Symbol} whose value is assigned. Iff null {@link #assignedValue} is not null. */
	final public Symbol rightSymbol;
	/** {@link Expression} whose return value is assigned. Iff null {@link #rightSymbol} is not null. */
	final public Expression assignedValue;
	/** true iff this {@link AssignmentRelation} is on a one of more possible branches, e.g. v=p? 1 : 2; */
	final public boolean mayHappen;

	/** Constructor */
	AssignmentRelation(Symbol leftSymbol, Symbol rightSymbol, Expression assignedValue) {
		this(leftSymbol, rightSymbol, assignedValue, false);
	}

	/** Constructor */
	AssignmentRelation(Symbol leftSymbol, Symbol rightSymbol, Expression assignedValue, boolean mayHappen) {
		this.leftSymbol = leftSymbol;
		this.rightSymbol = rightSymbol;
		this.assignedValue = assignedValue;
		this.mayHappen = mayHappen;
	}

	@Override
	public String toString() {
		String str = "";
		str += leftSymbol.toString() + " := ";
		str += rightSymbol != null ? rightSymbol.toString() : assignedValue;
		return str;
	}

}
