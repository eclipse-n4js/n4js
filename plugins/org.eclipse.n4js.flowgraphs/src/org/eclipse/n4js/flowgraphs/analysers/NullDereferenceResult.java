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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.n4js.flowgraphs.analysers.NullDereferenceAnalyser.IsNotNull;
import org.eclipse.n4js.flowgraphs.dataflow.FlowAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.HoldResult;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;

import com.google.common.collect.Sets;

/** Result of a null or undefined dereference */
public class NullDereferenceResult {
	/** AST location */
	public final ControlFlowElement cfe;
	/** {@link Symbol} that was checked for null or undefined */
	public final Symbol checkedSymbol;
	/** Aliased {@link Symbol} that failed a check */
	public final Symbol failedAlias;
	/** Assigned null or undefined */
	public final Set<GuardType> types;
	/** Either {@link FlowAssertion#AlwaysHolds} or {@link FlowAssertion#MayHold}. */
	public final FlowAssertion assertion;

	NullDereferenceResult(ControlFlowElement cfe, IsNotNull inn) {
		this.cfe = cfe;
		this.checkedSymbol = inn.symbol;
		this.failedAlias = getFailedSymbol(inn);
		this.types = getTypes(inn);
		this.assertion = getAssertion(inn);
	}

	private Symbol getFailedSymbol(IsNotNull inn) {
		if (inn.failedBranches.isEmpty()) {
			return null;
		}
		Set<Symbol> failedSymbols = new HashSet<>();
		for (HoldResult result : inn.failedBranches) {
			failedSymbols.add(result.symbol);
		}
		if (failedSymbols.size() == 1) {
			return failedSymbols.iterator().next();
		}
		return null;
	}

	private Set<GuardType> getTypes(IsNotNull inn) {
		if (inn.terminatingGuard != null) {
			return Sets.newHashSet(inn.terminatingGuard.expectation);

		} else if (!inn.failedBranches.isEmpty()) {
			Set<GuardType> results = new TreeSet<>();
			for (HoldResult result : inn.failedBranches) {
				results.add(result.expectation);
			}
			return results;
		}
		return Collections.emptySet();
	}

	private FlowAssertion getAssertion(IsNotNull inn) {
		if (inn.passedBranches.isEmpty()) {
			return FlowAssertion.AlwaysHolds;
		}
		return FlowAssertion.MayHold;
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
