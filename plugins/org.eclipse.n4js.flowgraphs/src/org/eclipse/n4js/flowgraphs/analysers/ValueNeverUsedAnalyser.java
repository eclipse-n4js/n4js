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

import org.eclipse.n4js.flowgraphs.analyses.Assumption;
import org.eclipse.n4js.flowgraphs.analyses.DataFlowVisitor;
import org.eclipse.n4js.flowgraphs.analyses.TraverseDirection;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.ControlFlowElement;

/**
 * Analysis to detect obsolete variable writes. In other words, the analysis detects variable definition sites that are
 * not followed by read sites of the same variable. In these cases, the defined value is never used.
 */
public class ValueNeverUsedAnalyser extends DataFlowVisitor {

	ValueNeverUsedAnalyser() {
		super(TraverseDirection.Forward);
	}

	@Override
	public void visitEffect(EffectInfo effect, ControlFlowElement cfe) {
		if (effect.type == EffectType.Write) {
			IsUsedSubsequently isUsedSubsequently = new IsUsedSubsequently(effect.symbol);
			assume(isUsedSubsequently);
		}
	}

	static class IsUsedSubsequently extends Assumption {
		private boolean isUsedSubsequently = false;

		IsUsedSubsequently(Symbol symbol) {
			super(symbol);
		}

		@Override
		public boolean holdsOnEffect(EffectInfo effect, ControlFlowElement container) {
			if (symbol.is(effect.symbol)) {
				isUsedSubsequently = true;
				deactivate();
			}
			return true;
		}

		@Override
		public boolean holdsAfterall() {
			return isUsedSubsequently;
		}
	}
}
