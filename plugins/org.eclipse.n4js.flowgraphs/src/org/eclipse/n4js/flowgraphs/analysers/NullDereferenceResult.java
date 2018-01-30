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
import org.eclipse.n4js.flowgraphs.dataflow.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.HoldAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/** Result of a null or undefined dereference */
public class NullDereferenceResult {
	/** AST location */
	public final ControlFlowElement cfe;
	/** {@link Symbol} that was checked for null or undefined */
	public final Symbol checkedSymbol;
	/** Aliased {@link Symbol} that failed a check */
	public final Symbol failedAlias;
	/** Assigned null or undefined */
	public final GuardType type;
	/** Either {@link HoldAssertion#AlwaysHolds} or {@link HoldAssertion#MayHold}. */
	public final HoldAssertion assertion;

	NullDereferenceResult(ControlFlowElement cfe, IsNotNull inn) {
		this.cfe = cfe;
		this.checkedSymbol = inn.symbol;
		this.failedAlias = inn.failedSymbol;
		this.type = getType(inn);
		this.assertion = getAssertion(inn);
	}

	private GuardType getType(IsNotNull inn) {
		if (inn.failedGuard != null) {
			return inn.failedGuard.expectation;

		} else if (inn.nullOrUndefinedSymbols.size() == 1) {
			Symbol symbol = inn.nullOrUndefinedSymbols.get(0);
			if (symbol.isNullLiteral()) {
				return GuardType.IsNull;
			}
			if (symbol.isUndefinedLiteral()) {
				return GuardType.IsUndefined;
			}
		}
		return GuardType.IsTruthy;
	}

	private HoldAssertion getAssertion(IsNotNull inn) {
		if (inn.noAliasPassed()) {
			return HoldAssertion.AlwaysHolds;
		}
		return HoldAssertion.MayHold;
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
