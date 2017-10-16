/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.LabelledStatement;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

/**
 * Some {@link ControlFlowElement}s are not represented in the control flow graph but are replaced by another
 * {@link ControlFlowElement} as an optimization. For example, {@link ExpressionStatement}s are replaced by their
 * {@link Expression}s.
 */
public class CFEMapper {

	/**
	 * Maps the given {@link ControlFlowElement} to another {@link ControlFlowElement} which will be used in the control
	 * flow graph. This method invokes the internal mapping methods repeatedly until a fixpoint is reached.
	 */
	static public ControlFlowElement map(ControlFlowElement cfe) {
		ControlFlowElement lastCFE = null;
		while (cfe != null && lastCFE != cfe) {
			lastCFE = cfe;
			cfe = mapInternal(cfe);
		}
		return lastCFE;
	}

	/**
	 * Maps the given {@link ControlFlowElement} to another {@link ControlFlowElement} which will be used in the control
	 * flow graph.
	 */
	static private ControlFlowElement mapInternal(ControlFlowElement expr) {
		return new InternalCFEMapper().doSwitch(expr);
	}

	static private class InternalCFEMapper extends N4JSSwitch<ControlFlowElement> {
		@Override
		public ControlFlowElement caseFunctionDeclaration(FunctionDeclaration feature) {
			return feature.getBody();
		}

		@Override
		public ControlFlowElement caseLabelledStatement(LabelledStatement feature) {
			return feature.getStatement();
		}

		@Override
		public ControlFlowElement caseExpressionStatement(ExpressionStatement feature) {
			return feature.getExpression();
		}

		@Override
		public ControlFlowElement defaultCase(EObject feature) {
			return (ControlFlowElement) feature;
		}
	}

}
