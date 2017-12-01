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

import java.util.Map;

import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.EffectInfo;
import org.eclipse.n4js.flowgraphs.model.EffectType;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.flowgraphs.model.Symbol;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;

class CFEEffectInfos {

	static void set(Map<ControlFlowElement, ComplexNode> cnMap, ComplexNode cNode, ControlFlowElement cfe) {
		new InternalEffectInfos(cnMap, cNode).doSwitch(cfe);
	}

	static private class InternalEffectInfos extends N4JSSwitch<Void> {
		final Map<ControlFlowElement, ComplexNode> cnMap;
		final ComplexNode cNode;

		InternalEffectInfos(Map<ControlFlowElement, ComplexNode> cnMap, ComplexNode cNode) {
			this.cnMap = cnMap;
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
			clearEffectsOfExitNode(feature.getLhs());

			Node exitNode = cNode.getNode(NodeNames.EXIT);

			Symbol symbol = SymbolFactory.create(feature.getLhs());
			EffectInfo eiDecl = new EffectInfo(EffectType.Write, symbol);
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void casePostfixExpression(PostfixExpression feature) {
			clearEffectsOfExitNode(feature.getExpression());

			Node exitNode = cNode.getNode(NodeNames.EXIT);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);

			Symbol symbol = SymbolFactory.create(feature.getExpression());
			EffectInfo eiDecl = new EffectInfo(EffectType.Read, symbol);
			expressionNode.addEffectInfo(eiDecl);

			symbol = SymbolFactory.create(feature.getExpression());
			eiDecl = new EffectInfo(EffectType.Write, symbol);
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseUnaryExpression(UnaryExpression feature) {
			clearEffectsOfExitNode(feature.getExpression());

			boolean addEffects = false;
			addEffects |= feature.getOp() == UnaryOperator.INC;
			addEffects |= feature.getOp() == UnaryOperator.DEC;
			if (!addEffects) {
				return null;
			}

			Node exitNode = cNode.getNode(NodeNames.EXIT);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);

			Symbol symbol = SymbolFactory.create(feature.getExpression());
			EffectInfo eiDecl = new EffectInfo(EffectType.Write, symbol);
			expressionNode.addEffectInfo(eiDecl);

			symbol = SymbolFactory.create(feature.getExpression());
			eiDecl = new EffectInfo(EffectType.Read, symbol);
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseParameterizedCallExpression(ParameterizedCallExpression feature) {
			clearEffectsOfExitNode(feature.getTarget());

			Node exitNode = cNode.getNode(NodeNames.EXIT);

			Symbol symbol = SymbolFactory.create(feature.getTarget());
			EffectInfo eiDecl = new EffectInfo(EffectType.MethodCall, symbol);
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression feature) {
			setRead(feature);
			return null;
		}

		@Override
		public Void caseIdentifierRef(IdentifierRef feature) {
			setRead(feature, NodeNames.ENTRY_EXIT);
			return null;
		}

		@Override
		public Void caseIndexedAccessExpression(IndexedAccessExpression feature) {
			setRead(feature);
			return null;
		}

		private void setRead(Expression feature) {
			setRead(feature, NodeNames.EXIT);
		}

		private void setRead(Expression feature, String nodeName) {
			Node exitNode = cNode.getNode(nodeName);
			Symbol symbol = SymbolFactory.create(feature);
			EffectInfo eiDecl = new EffectInfo(EffectType.Read, symbol);
			exitNode.addEffectInfo(eiDecl);
		}

		private void clearEffectsOfExitNode(Expression feature) {
			ComplexNode cn = cnMap.get(feature);
			Node exitNode = cn.getNode(NodeNames.EXIT);
			if (exitNode == null) {
				exitNode = cn.getNode(NodeNames.ENTRY_EXIT);
			}
			exitNode.effectInfos.clear();
		}
	}

}
