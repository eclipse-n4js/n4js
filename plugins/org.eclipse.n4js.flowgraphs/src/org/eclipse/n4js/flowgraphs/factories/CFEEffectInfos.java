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

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

class CFEEffectInfos {

	static void add(ComplexNode cNode, ControlFlowElement cfe) {
		new InternalEffectInfos(cNode).doSwitch(cfe);
	}

	static private class InternalEffectInfos extends N4JSSwitch<Void> {
		final ComplexNode cNode;

		InternalEffectInfos(ComplexNode cNode) {
			this.cNode = cNode;
		}

		@Override
		public Void caseVariableDeclaration(VariableDeclaration feature) {
			Node entryNode = cNode.getNode(NodeNames.ENTRY);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);
			Node exitNode = cNode.getNode(NodeNames.EXIT);

			Symbol symbol = SymbolFactory.create(feature);
			EffectInfo eiDecl = new EffectInfo(EffectType.Declaration, symbol);
			entryNode.addEffectInfo(eiDecl);

			if (expressionNode != null) {
				symbol = SymbolFactory.create(feature);
				eiDecl = new EffectInfo(EffectType.Write, symbol);
				exitNode.addEffectInfo(eiDecl);
			}

			return null;
		}

		@Override
		public Void caseAssignmentExpression(AssignmentExpression feature) {
			Node entryNode = cNode.getNode(NodeNames.ENTRY);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);
			Node exitNode = cNode.getNode(NodeNames.EXIT);

			Symbol symbol = SymbolFactory.create(feature);
			EffectInfo eiDecl = new EffectInfo(EffectType.Declaration, symbol);
			entryNode.addEffectInfo(eiDecl);

			if (expressionNode != null) {
				symbol = SymbolFactory.create(feature);
				eiDecl = new EffectInfo(EffectType.Write, symbol);
				exitNode.addEffectInfo(eiDecl);
			}

			return null;
		}

		@Override
		public Void caseParameterizedCallExpression(ParameterizedCallExpression feature) {
			for (Argument arg : feature.getArguments()) {
				Expression argExpr = arg.getExpression();
				if (argExpr instanceof IdentifierRef) {
					int argPos = feature.getArguments().indexOf(arg);
					String nodeName = "arg_" + argPos;
					Node argNode = cNode.getNode(nodeName);
					EffectInfo effectInfo = new EffectInfo();
					Symbol symbol = null; // TODO: add Symbol provider
					effectInfo.references = symbol;
					effMap.put(argNode, effectInfo);
				}
			}
		}
	}

}
