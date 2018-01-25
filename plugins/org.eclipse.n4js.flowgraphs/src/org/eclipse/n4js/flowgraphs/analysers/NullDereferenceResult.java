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
package org.eclipse.n4js.flowgraphs.analysers;

import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceAnalyser.IsNotNull;
import org.eclipse.n4js.flowgraphs.dataflow.Guard;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/** Result of a null or undefined dereference */
public class NullDereferenceResult {
	/** AST location */
	public final ControlFlowElement cfe;
	/** {@link Symbol} that was checked for null or undefined */
	public final Symbol checkedSymbol;
	/** Aliased {@link Symbol} that failed a check */
	public final Symbol causingSymbol;
	/** Undefined or Null {@link Symbol} that was assigned to {@link #causingSymbol} */
	public final Symbol nullOrUndefinedSymbol;
	/** One of the failed {@link Guard}s */
	public final Guard failedGuard;
	/** True iff the symbol must be null or undefined. False iff it can be null or undefined. */
	public final boolean must;

	NullDereferenceResult(ControlFlowElement cfe, IsNotNull inn) {
		this.cfe = cfe;
		this.checkedSymbol = inn.symbol;
		this.causingSymbol = inn.failedSymbol;
		this.nullOrUndefinedSymbol = inn.failedSymbol;
		this.failedGuard = inn.failedGuard;
		this.must = inn.noAliasPassed() && !inn.failedAssignment.mayHappen;
	}

	@Override
	public int hashCode() {
		return cfe.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NullDereferenceResult)) {
			return false;
		}
		NullDereferenceResult ndr = (NullDereferenceResult) o;
		return cfe.equals(ndr.cfe);
	}
}
