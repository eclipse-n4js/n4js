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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.flowgraphs.dataflow.EffectInfo;
import org.eclipse.n4js.flowgraphs.dataflow.EffectType;
import org.eclipse.n4js.flowgraphs.dataflow.Symbol;
import org.eclipse.n4js.flowgraphs.dataflow.SymbolFactory;
import org.eclipse.n4js.flowgraphs.model.ComplexNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
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
			Node exitNode = cNode.getNode(NodeNames.EXIT);

			EffectInfo eiDecl = new EffectInfo(EffectType.Declaration, feature);
			entryNode.addEffectInfo(eiDecl);

			eiDecl = new EffectInfo(EffectType.Write, feature);
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseAssignmentExpression(AssignmentExpression feature) {
			Expression lhs = feature.getLhs();
			if (lhs == null) {
				return null;
			}

			List<Expression> idRefs = new LinkedList<>();
			if (N4JSASTUtils.isDestructuringAssignment(feature)) {
				idRefs.addAll(DestructNode.getAllDeclaredIdRefs(feature));
			} else {
				idRefs.add(lhs);
			}

			Node exitNode = cNode.getNode(NodeNames.EXIT);
			for (Expression assignedVar : idRefs) {
				clearEffectsOfExitNode(assignedVar);
				EffectInfo eiDecl = new EffectInfo(EffectType.Write, assignedVar);
				exitNode.addEffectInfo(eiDecl);
			}
			return null;
		}

		@Override
		public Void casePostfixExpression(PostfixExpression feature) {
			if (feature.getExpression() == null) {
				return null;
			}

			clearEffectsOfExitNode(feature.getExpression());
			Node exitNode = cNode.getNode(NodeNames.EXIT);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);

			EffectInfo eiDecl = new EffectInfo(EffectType.Read, feature.getExpression());
			expressionNode.addEffectInfo(eiDecl);

			eiDecl = new EffectInfo(EffectType.Write, feature.getExpression());
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseUnaryExpression(UnaryExpression feature) {
			if (feature.getExpression() == null) {
				return null;
			}

			boolean addEffects = false;
			addEffects |= feature.getOp() == UnaryOperator.INC;
			addEffects |= feature.getOp() == UnaryOperator.DEC;
			if (!addEffects) {
				return null;
			}

			clearEffectsOfExitNode(feature.getExpression());
			Node exitNode = cNode.getNode(NodeNames.EXIT);
			Node expressionNode = cNode.getNode(NodeNames.EXPRESSION);

			EffectInfo eiDecl = new EffectInfo(EffectType.Write, feature.getExpression());
			expressionNode.addEffectInfo(eiDecl);

			eiDecl = new EffectInfo(EffectType.Read, feature.getExpression());
			exitNode.addEffectInfo(eiDecl);

			return null;
		}

		@Override
		public Void caseParameterizedCallExpression(ParameterizedCallExpression feature) {
			Expression targetExpr = feature.getTarget();
			if (targetExpr == null) {
				return null;
			}

			Node exitNode = cNode.getNode(NodeNames.EXIT);

			Symbol symbol = SymbolFactory.create(targetExpr);
			if (symbol != null) {
				EffectInfo eiDecl = new EffectInfo(EffectType.MethodCall, targetExpr, symbol);
				exitNode.addEffectInfo(eiDecl);
			}

			return null;
		}

		@Override
		public Void caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression feature) {
			setRead(feature);
			return null;
		}

		@Override
		public Void caseIdentifierRef(IdentifierRef feature) {
			setRead(feature);
			return null;
		}

		@Override
		public Void caseIndexedAccessExpression(IndexedAccessExpression feature) {
			setRead(feature);
			return null;
		}

		private void setRead(Expression feature) {
			Node exitNode = cNode.getExit();
			EffectInfo eiDecl = new EffectInfo(EffectType.Read, feature);
			exitNode.addEffectInfo(eiDecl);
		}

		private void clearEffectsOfExitNode(Expression feature) {
			ComplexNode cn = cnMap.get(feature);
			Node exitNode = cn.getExit();
			exitNode.effectInfos.clear();
		}
	}

}
