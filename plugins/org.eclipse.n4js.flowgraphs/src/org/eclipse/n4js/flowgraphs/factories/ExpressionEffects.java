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
package org.eclipse.n4js.flowgraphs.factories;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;

class ExpressionEffects {
	// TODO GH-235

	Map<Node, EffectInfo> get(Expression expr, ComplexNode cNode) {
		Map<Node, EffectInfo> effMap = new HashMap<>();

		if (expr instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) expr;
			for (Argument arg : pce.getArguments()) {
				Expression argExpr = arg.getExpression();
				if (argExpr instanceof IdentifierRef) {
					int argPos = pce.getArguments().indexOf(arg);
					String nodeName = "arg_" + argPos;
					Node argNode = cNode.getNode(nodeName);
					EffectInfo effectInfo = new EffectInfo();
					Symbol symbol = null; // TODO: add Symbol provider
					effectInfo.references = symbol;
					effMap.put(argNode, effectInfo);
				}
			}
		}

		return effMap;
	}

}
