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
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult.Type;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardAssertion;
import org.eclipse.n4js.flowgraphs.dataflow.guards.GuardType;
import org.eclipse.n4js.flowgraphs.dataflow.symbols.Symbol;
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
	/** Either {@link GuardAssertion#AlwaysHolds} or {@link GuardAssertion#MayHolds}. */
	public final GuardAssertion assertion;

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
		for (PartialResult result : inn.failedBranches) {
			NullDereferenceFailed ndFailed = (NullDereferenceFailed) result;
			failedSymbols.add(ndFailed.symbol);
		}
		if (failedSymbols.size() == 1) {
			return failedSymbols.iterator().next();
		}
		return null;
	}

	private Set<GuardType> getTypes(IsNotNull inn) {
		if (inn.terminatingGuard != null) {
			NullDereferenceFailed ndFailed = (NullDereferenceFailed) inn.terminatingGuard;
			return Sets.newHashSet(ndFailed.expectation);

		} else if (!inn.failedBranches.isEmpty()) {
			Set<GuardType> results = new TreeSet<>();
			for (PartialResult result : inn.failedBranches) {
				NullDereferenceFailed ndFailed = (NullDereferenceFailed) result;
				results.add(ndFailed.expectation);
			}
			return results;
		}
		return Collections.emptySet();
	}

	private GuardAssertion getAssertion(IsNotNull inn) {
		if (inn.passedBranches.isEmpty() && inn.aliases.isEmpty()) {
			boolean allMayFailed = true;
			for (PartialResult pr : inn.failedBranches) {
				allMayFailed &= (pr.type == Type.MayFailed);
			}
			if (allMayFailed) {
				return GuardAssertion.MayHolds;
			} else {
				return GuardAssertion.AlwaysHolds;
			}
		}
		if (inn.terminatingGuard != null) {
			if (inn.terminatingGuard.type == Type.MayFailed) {
				return GuardAssertion.MayHolds;
			} else {
				return GuardAssertion.AlwaysHolds;
			}
		}
		return GuardAssertion.MayHolds;
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
