/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.analysers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.flowgraphs.FGUtils;
import org.eclipse.n4js.flowgraphs.dataflow.Assumption;
import org.eclipse.n4js.flowgraphs.dataflow.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.PartialResult;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.IdentifierRef;

/**
 * This is a test implementation using the data flow API. It is not executed.
 * <p>
 * Analysis to detect uses of {@link IdentifierRef}s that are located in the control flow before their corresponding
 * variables are declared.
 */
public class UsedBeforeDeclaredAnalyserOnDataflow extends DataFlowVisitor {

	/** @return all {@link IdentifierRef}s that are used before declared */
	public List<ControlFlowElement> getUsedButNotDeclaredIdentifierRefs() {
		List<ControlFlowElement> idRefs = new LinkedList<>();
		for (Assumption ass : failedAssumptions.values()) {
			for (PartialResult result : ass.failedBranches) {
				UsedBeforeFailed ubf = (UsedBeforeFailed) result;
				idRefs.add(ubf.useLocation);
			}
		}
		return idRefs;
	}

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (effect.type == EffectType.Declaration) {
			IsNotUsedBefore symbolNotUsedBefore = new IsNotUsedBefore(effect);
			assume(symbolNotUsedBefore);
		}
	}

	class IsNotUsedBefore extends Assumption {
		IsNotUsedBefore(EffectInfo effect) {
			super(effect.location, effect.symbol);
		}

		IsNotUsedBefore(IsNotUsedBefore copy) {
			super(copy);
		}

		@Override
		public Assumption copy() {
			return new IsNotUsedBefore(this);
		}

		@Override
		protected boolean followAliases() {
			return false;
		}

		@Override
		protected PartialResult holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			return new UsedBeforeFailed(effect.location);
		}
	}

	static class UsedBeforeFailed extends PartialResult.Failed {
		/** The use site location */
		public final ControlFlowElement useLocation;

		UsedBeforeFailed(ControlFlowElement useLocation) {
			this.useLocation = useLocation;
		}

		@Override
		public String toString() {
			return type + ": used at " + FGUtils.getSourceText(useLocation);
		}

		@Override
		public Object[] getEqualityProperties() {
			return new Object[] { useLocation };
		}

	}
}
